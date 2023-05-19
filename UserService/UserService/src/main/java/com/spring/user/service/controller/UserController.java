package com.spring.user.service.controller;

import com.spring.user.service.entites.User;
import com.spring.user.service.service.UserService;
import com.spring.user.service.service.serviceImpl.UserServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
User user1=this.userServiceImpl.saveUser(user);
return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }
    @GetMapping("/{userId}")
    @CircuitBreaker(name="ratingBreaker",fallbackMethod = "ratingFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId)
    {
        User user=userServiceImpl.getUser(userId);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<User> ratingFallback(String userId,Exception ex)
    {

       User user= User.builder()
                .email("dummy@email.com").name("Dummy")
                .about("this user is created dummy some service is dowm")
                .userId("141234")
                .build();
      return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUser()
    {
        List<User> allUser=userServiceImpl.getAllUser();
        return ResponseEntity.ok(allUser);
    }
}
