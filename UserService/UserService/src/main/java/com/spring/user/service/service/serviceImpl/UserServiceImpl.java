package com.spring.user.service.service.serviceImpl;

import com.spring.user.service.entites.User;
import com.spring.user.service.exception.ResourceNotFoundException;
import com.spring.user.service.repositories.UserRepository;
import com.spring.user.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebClient webClient;

    private Logger loger= LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public User saveUser(User user) {
        // generate unique userid
        String randomUserId=UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        loger.info("user details "+user);
        return userRepository.save(user);

    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user with given id is not found"));
    }
}
