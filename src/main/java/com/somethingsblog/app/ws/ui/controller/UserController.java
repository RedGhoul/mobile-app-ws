package com.somethingsblog.app.ws.ui.controller;

import com.somethingsblog.app.ws.exceptions.UserServiceException;
import com.somethingsblog.app.ws.service.AddressesService;
import com.somethingsblog.app.ws.service.UserService;
import com.somethingsblog.app.ws.shard.dto.AddressDto;
import com.somethingsblog.app.ws.shard.dto.UserDto;
import com.somethingsblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.somethingsblog.app.ws.ui.model.response.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final AddressesService addressesService;

    public UserController(UserService userService, AddressesService addressesService) {
        this.userService = userService;
        this.addressesService = addressesService;
    }

    @GetMapping("/{id}")
    public UserRest getUser(@PathVariable String id){
        UserDto userDto = userService.getUserByUserId(id);
        return new ModelMapper().map(userDto, UserRest.class);
    }

    @GetMapping
    public List<UserRest> getUsers(@RequestParam(value="page", defaultValue = "1") int page,
                                   @RequestParam(value="limit", defaultValue = "25") int limit){
        if(page > 0) page = page-1;
        List<UserRest> returnValue = new ArrayList<>();
        List<UserDto> users = userService.getUsers(page,limit);
        for(UserDto userDto : users){
            returnValue.add(new ModelMapper().map(userDto, UserRest.class));
        }
        return returnValue;
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
        if(userDetails.getFirstName().isBlank()) throw new UserServiceException(
                ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        UserDto userDto = new ModelMapper().map(userDetails,UserDto.class);
        UserDto createdUser = userService.createUser(userDto);
        return new ModelMapper().map(createdUser,UserRest.class);
    }

    @PutMapping(path="/{id}")
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails)
            throws Exception {
        if(userDetails.getFirstName().isBlank()) throw new UserServiceException(
                ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        UserDto userDto = new ModelMapper().map(userDetails,UserDto.class);
        UserDto updatedUser = userService.updateUser(id,userDto);
        return new ModelMapper().map(updatedUser,UserRest.class);
    }

    @DeleteMapping(path="/{id}")
    public OperationStatusModel deleteUser(@PathVariable String id){
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        userService.deleteUser(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @GetMapping(path="/{id}/addresses")
    public List<AddressRest> getUserAddresses(@PathVariable String id){
        List<AddressRest> returnValue = new ArrayList<>();
        List<AddressDto> addressDtos = addressesService.getAddresses(id);
        if(addressDtos != null && (long) addressDtos.size() > 0){
            java.lang.reflect.Type listType = new TypeToken<List<AddressRest>>() {}.getType();
            return new ModelMapper().map(addressDtos, listType);
        }
        return returnValue;
    }

}
