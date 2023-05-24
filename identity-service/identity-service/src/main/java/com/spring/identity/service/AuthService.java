package com.spring.identity.service;

import com.spring.identity.entity.UserCredential;
import com.spring.identity.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserCredentialRepository userCredentialRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public UserCredential saveUser(UserCredential userCredential)
    {
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
       return userCredentialRepository.save(userCredential);
    }

    public String generateToken(String username)
    {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token)
    {
        jwtService.validateToken(token);
    }
}
