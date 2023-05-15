package com.spring.user.service.controller;

import com.spring.user.service.entites.User;
import com.spring.user.service.service.UserService;
import com.spring.user.service.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
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
    public ResponseEntity<User> getSingleUser(@PathVariable String userId)
    {
        User user=userServiceImpl.getUser(userId);
        return ResponseEntity.ok(user);
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUser()
    {
        List<User> allUser=userServiceImpl.getAllUser();
        return ResponseEntity.ok(allUser);
    }
}
