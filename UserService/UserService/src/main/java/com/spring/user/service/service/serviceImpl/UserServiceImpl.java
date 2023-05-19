package com.spring.user.service.service.serviceImpl;

import com.spring.user.service.entites.Rating;
import com.spring.user.service.entites.User;
import com.spring.user.service.exception.ResourceNotFoundException;
import com.spring.user.service.repositories.UserRepository;
import com.spring.user.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
   // @Autowired
   // private RestTemplate restTemplate;
    @Autowired
    private WebClient client;
    @Autowired
    private UserRepository userRepository;
    @Override
    public User saveUser(User user) {
        // generate unique userid
        String randomUserId=UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        User user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user with given id is not found"));
       // ArrayList<Rating> forObject=restTemplate.getForObject("http://localhost:8083/ratings/hotels/1fbc850b-b094-43a5-b694-241ddffd1c12", ArrayList.class);
      ArrayList<Rating> forObject= client.get().uri("http://localhost:8083/ratings/hotels/1fbc850b-b094-43a5-b694-241ddffd1c12").retrieve().bodyToMono(ArrayList.class).block();
      user.setRatings(forObject);

        return user;
    }
}
