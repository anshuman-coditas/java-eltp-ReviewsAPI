package com.example.demo.controller;

import com.example.demo.config.ResponseHandler;
import com.example.demo.payload.FetchRequest;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

   @Autowired
    private ReviewService service;

    @Autowired
    private ReviewRepository reportRepository;


    //Assuming this URL : /admin/filter?ambiance=1&food=2&service=3&cleanliness=4&drink=5

    @PostMapping("/filter")
    public ResponseEntity<?> average(@Valid @RequestBody FetchRequest fr){
        try{
            List r=service.find(fr.getAmbience(),fr.getClean(),fr.getFood(),fr.getDrinks(),fr.getService());
            return ResponseHandler.generate("SuccessFully Fetched", HttpStatus.CREATED,r,null);
        }catch (Exception e){
            return ResponseHandler.generate("Not Fetched",HttpStatus.FORBIDDEN,null,new Error("Bad Request"));
        }
    }

    @GetMapping("/reports")
    public  ResponseEntity<?> all() {
        try {
            Map m=service.ambienceAvg();
            return ResponseHandler.generate("SuccessFully Fetched",HttpStatus.CREATED,m,null);
        } catch (Exception e){
            return ResponseHandler.generate("Not Fetched",HttpStatus.FORBIDDEN,null,new Error("Bad Request"));
        }
    }

}

