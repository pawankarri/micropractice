package com.spring.user.service.service;

import com.spring.user.service.entites.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {
//create
    User saveUser(User user);
    //get all user
    List<User> getAllUser();
    //get single user of given userId
    User getUser(String userId);
}
