package com.secudoc.user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.secudoc.user_service.entity.User;
import com.secudoc.user_service.service.UserService;



@RestController
@RequestMapping("/users")

public class UserController {

    private final UserService userService;
    
    public UserController(UserService userService) {
    	
    	this.userService = userService;
    }
    @GetMapping("/me")
    public User getMyProfile(
            @RequestHeader("X-User-Id") String userId) {

        return userService.getUser(userId);
    }
    
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }
    
    @RequestMapping(method = RequestMethod.POST, consumes="application/json")
    public User createUser(@RequestBody User user) {
        return userService.saveProfile(user);
    }
    
    
}