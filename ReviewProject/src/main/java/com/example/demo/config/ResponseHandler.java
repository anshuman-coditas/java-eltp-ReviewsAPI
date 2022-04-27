package com.example.demo.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<?> generate(String message, HttpStatus status, Object responseObj, Error error) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);
        map.put("error", error);


        return new ResponseEntity<Object>(map,status);


    }
}

