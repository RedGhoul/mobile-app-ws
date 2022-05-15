package com.somethingsblog.app.ws.ui.controller;

import com.somethingsblog.app.ws.exceptions.UserServiceException;
import com.somethingsblog.app.ws.service.UserService;
import com.somethingsblog.app.ws.shard.dto.UserDto;
import com.somethingsblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.somethingsblog.app.ws.ui.model.response.*;
import org.apache.tomcat.jni.Error;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserRest getUser(@PathVariable String id){
        UserRest returnValue = new UserRest();
        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto,returnValue);
        return returnValue;
    }

    @GetMapping
    public List<UserRest> getUsers(@RequestParam(value="page", defaultValue = "1") int page,
                                   @RequestParam(value="limit", defaultValue = "25") int limit){
        if(page > 0) page = page-1;
        List<UserRest> returnValue = new ArrayList<>();
        List<UserDto> users = userService.getUsers(page,limit);
        for(UserDto userDto : users){
            UserRest userModel = new UserRest();
            BeanUtils.copyProperties(userDto, userModel);
            returnValue.add(userModel);
        }
        return returnValue;
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
        UserRest returnValue = new UserRest();
        if(userDetails.getFirstName().isBlank()) throw new UserServiceException(
                ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails,userDto);
        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser,returnValue);
        return returnValue;
    }

    @PutMapping(path="/{id}")
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) throws Exception {
        UserRest returnValue = new UserRest();
        if(userDetails.getFirstName().isBlank()) throw new UserServiceException(
                ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails,userDto);
        UserDto updatedUser = userService.updateUser(id,userDto);
        BeanUtils.copyProperties(updatedUser,returnValue);
        return returnValue;
    }

    @DeleteMapping(path="/{id}")
    public OperationStatusModel deleteUser(@PathVariable String id){
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        userService.deleteUser(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

}
