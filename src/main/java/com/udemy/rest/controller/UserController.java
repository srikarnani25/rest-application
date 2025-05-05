package com.udemy.rest.controller;
import com.udemy.rest.helper.CSVHelper;
import com.udemy.rest.model.User;
import com.udemy.rest.service.UserService;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.udemy.rest.helper.CSVHelper;
import com.udemy.rest.service.CSVService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

   /* @Autowired
    private MeterRegistry meterRegistry;

    @GetMapping("/hello")
    public String sayHello(){
        meterRegistry.counter("api.hello.calls").increment();
        return "Hello Prometheous";
    }
    */
    @Autowired
    public UserService userService;

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public User getUserId(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUSer(@RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody  User user){
        return userService.updateUser(id,user);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
    /*
    @RequestMapping("/hi")
    public String sayhi(){
        return "hi springboot";
    }
    @RequestMapping("/bye")
    public String saybye(){
        return "bye Spring boot!";
    }
    */


}
