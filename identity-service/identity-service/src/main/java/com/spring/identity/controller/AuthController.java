package com.spring.identity.controller;

import com.spring.identity.dto.AuthRequest;
import com.spring.identity.entity.UserCredential;
import com.spring.identity.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;

@PostMapping("/register")
    public UserCredential addNewUser(@RequestBody UserCredential userCredential)
    {
        return authService.saveUser(userCredential);
    }
    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest)
    {
      Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
      if (authentication.isAuthenticated())
      {
          return authService.generateToken(authRequest.getUsername());
      }
      else {
          throw new UsernameNotFoundException("invalid excess");
      }


    }
@GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token)
    {
        authService.validateToken(token);
        return "Token is valid";
    }
}
