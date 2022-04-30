package com.somethingsblog.app.ws.ui.controller;

import com.somethingsblog.app.ws.service.UserService;
import com.somethingsblog.app.ws.shard.dto.UserDto;
import com.somethingsblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.somethingsblog.app.ws.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUser(){
        return "Get user was called";
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails,userDto);
        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser,returnValue);
        return returnValue;
    }

    @PutMapping
    public String updateUser(){
        return "Update user was called";
    }

    @DeleteMapping
    public String deleteUser(){
        return "Delete User was called";
    }
}
