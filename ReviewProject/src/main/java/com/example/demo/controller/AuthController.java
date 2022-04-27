package com.example.demo.controller;

import com.example.demo.config.ResponseHandler;
import com.example.demo.jwt.JwtUtils;
import com.example.demo.model.Review;
import com.example.demo.model.Role;
import com.example.demo.model.RoleName;
import com.example.demo.model.User;
import com.example.demo.payload.*;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ReviewService;
import com.example.demo.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ReviewService service;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ReviewRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateJwtToken(authentication);
            return ResponseHandler.generate("Successfully Logged In", HttpStatus.CREATED, null, null);
        } catch (Exception e) {
            return ResponseHandler.generate("Login Unsuccessful", HttpStatus.BAD_REQUEST, null, new Error("Wrong Credentials"));
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {


            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                ResponseHandler.generate("Already Exits", HttpStatus.CONFLICT, null, new Error("Username Exists"));
            }

            // Creating user's account
            User user = new User();
            user.setUsername(signUpRequest.getUsername());
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

            if (signUpRequest.getEmail().split("@")[1].equals("ak.in")) {
                user.setRoles(new HashSet<>(Arrays.asList(new Role(RoleName.ADMIN))));
            } else
                user.setRoles(new HashSet<>(Arrays.asList(new Role(RoleName.USER))));

            User result = userRepository.save(user);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/api/users/{username}")
                    .buildAndExpand(result.getUsername()).toUri();

            return ResponseHandler.generate("Successfully Saved", HttpStatus.CREATED, user, null);
        } catch (Exception e) {
            return ResponseHandler.generate("Not Saved", HttpStatus.BAD_REQUEST, null, new Error("Bad Request"));
        }
    }
    @PostMapping("user/review")
    public ResponseEntity<?> reviewUser(@Valid @RequestBody ReviewRequest reviewRequest) {
        try {
            Review review = new Review();
            review.setAmbience(reviewRequest.getAmbience());
            review.setClean(reviewRequest.getClean());
            review.setFood(reviewRequest.getFood());
            review.setDrinks(reviewRequest.getDrinks());
            review.setService(reviewRequest.getService());
            repository.save(review);
            return ResponseHandler.generate("SuccessFully Added Review",HttpStatus.CREATED,review,null);
        } catch (Exception e) {
return ResponseHandler.generate("Unsuccessful",HttpStatus.BAD_REQUEST,null,new Error("Unable tpo Add"));
        }
    }
    @PostMapping("admin/average")
    public ResponseEntity<?> average(@Valid @RequestBody FetchRequest fr){
        try{
        List r=service.find(fr.getAmbience(),fr.getClean(),fr.getFood(),fr.getDrinks(),fr.getService());
        return ResponseHandler.generate("SuccessFully Fetched",HttpStatus.CREATED,r,null);
        }catch (Exception e){
            return ResponseHandler.generate("Not Fetched",HttpStatus.BAD_REQUEST,null,new Error("Bad Request"));
        }
    }

    @GetMapping("admin/ambaverage")
    public ResponseEntity<?> all() {
        try {
            List a = service.ambienceAvg();
            return ResponseHandler.generate("SuccessFully Fetched",HttpStatus.CREATED,a,null);
        } catch (Exception e){
            return ResponseHandler.generate("Not Fetched",HttpStatus.BAD_REQUEST,null,new Error("Bad Request"));
        }
    }
    @GetMapping("admin/claverage")
    public ResponseEntity<?> all1() {
        try {
            List b = service.cleanAvg();
            return ResponseHandler.generate("SuccessFully Fetched", HttpStatus.CREATED, b, null);
        } catch (Exception e) {
            return ResponseHandler.generate("Not Fetched", HttpStatus.BAD_REQUEST, null, new Error("Bad Request"));
        }
    }
    @GetMapping("admin/faverage")
    public ResponseEntity<?> all2() {
        try {
            List b = service.foodAvg();
            return ResponseHandler.generate("SuccessFully Fetched", HttpStatus.CREATED, b, null);
        } catch (Exception e) {
            return ResponseHandler.generate("Not Fetched", HttpStatus.BAD_REQUEST, null, new Error("Bad Request"));
        }
    }
    @GetMapping("admin/daverage")
    public ResponseEntity<?> all3() {
        try {

            List b = service.drinksAvg();
            return ResponseHandler.generate("SuccessFully Fetched", HttpStatus.CREATED, b, null);
        } catch (Exception e) {
            return ResponseHandler.generate("Not Fetched", HttpStatus.BAD_REQUEST, null, new Error("Bad Request"));
        }
    }
    @GetMapping("admin/saverage")
    public ResponseEntity<?> all4() {
        try {


            List b = service.serviceAvg();
            return ResponseHandler.generate("SuccessFully Fetched", HttpStatus.CREATED, b, null);
        } catch (Exception e) {
            return ResponseHandler.generate("Not Fetched", HttpStatus.BAD_REQUEST, null, new Error("Bad Request"));
        }
    }
    @GetMapping("admin/oaverage")
    public ResponseEntity<?> all5() {
        try {


            List b = service.overAllAvg();
            return ResponseHandler.generate("SuccessFully Fetched", HttpStatus.CREATED, b, null);
        } catch (Exception e) {
            return ResponseHandler.generate("Not Fetched", HttpStatus.BAD_REQUEST, null, new Error("Bad Request"));
        }
    }


}