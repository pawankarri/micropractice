package com.spring.user.service.service.serviceImpl;

import com.spring.user.service.entites.Hotel;
import com.spring.user.service.entites.Rating;
import com.spring.user.service.entites.User;
import com.spring.user.service.exception.ResourceNotFoundException;
import com.spring.user.service.repositories.UserRepository;
import com.spring.user.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        //getting single user

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));

        //getting ratings given by particular user
        //http://localhost:1432/ratings/get/cd69a47c-dae6-40c8-96c7-ec671b460d03

        Rating[] ratingsOfUser = client.get().uri("http://RATING-SERVICE/ratings/get/" + userId).retrieve().bodyToMono(Rating[].class).block();

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
       // System.out.println(ratings.toString());


        List<Rating> ratingList = ratings.stream().map(rating -> {
            Hotel hotel = client.get()
                    .uri("http://HOTEL-SERVICE/hotels/" + rating.getHotelId())
                    .retrieve()
                    .bodyToMono(Hotel.class)
                    .block();
            rating.setHotel(hotel)
            ;
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;

    }
}
