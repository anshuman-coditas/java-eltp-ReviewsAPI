package com.example.demo.controller;

import com.example.demo.config.ResponseHandler;
import com.example.demo.model.User;
import com.example.demo.payload.ReviewRequest;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class Review {
    @Autowired
    private ReviewRepository repository;

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/rating")
    public ResponseEntity<?> reviewUser(@Valid @RequestBody ReviewRequest reviewRequest) {

            com.example.demo.model.Review review = new com.example.demo.model.Review();
            review.setAmbience(reviewRequest.getAmbience());
            review.setClean(reviewRequest.getClean());
            review.setFood(reviewRequest.getFood());
            review.setDrinks(reviewRequest.getDrinks());
            review.setService(reviewRequest.getService());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            User user = userRepository.findByEmail(email).orElseThrow(() ->
                    new UsernameNotFoundException("User not found with email : " + email));

            review.setUser(user);
            repository.save(review);
            return ResponseHandler.generate("SuccessFully Added Review", HttpStatus.CREATED,review,null);

    }


}

