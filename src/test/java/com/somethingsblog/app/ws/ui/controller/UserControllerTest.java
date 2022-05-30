package com.somethingsblog.app.ws.ui.controller;

import com.somethingsblog.app.ws.io.entity.AddressEntity;
import com.somethingsblog.app.ws.io.entity.UserEntity;
import com.somethingsblog.app.ws.io.repository.UserRepository;
import com.somethingsblog.app.ws.service.AddressesService;
import com.somethingsblog.app.ws.service.UserService;
import com.somethingsblog.app.ws.service.UserServiceImpl;
import com.somethingsblog.app.ws.shard.dto.AddressDto;
import com.somethingsblog.app.ws.shard.dto.UserDto;
import com.somethingsblog.app.ws.shard.utils;
import com.somethingsblog.app.ws.ui.model.response.UserRest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {
    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    AddressesService addressesService;

    @Mock
    utils utils;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDto = createUserDto();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getUser() {
        when(userService.getUserByUserId(userDto.getUserId())).thenReturn(userDto);
        UserRest userRest = userController.getUser(userDto.getUserId());
        assertNotNull(userRest);
        assertEquals(userDto.getFirstName(), userRest.getFirstName());
        assertEquals(userDto.getUserId(), userRest.getUserId());
    }

    @Test
    void getUsers() {
    }

    @Test
    void createUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUserAddresses() {
    }

    private UserEntity createUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("asdfasdf");
        userEntity.setId(1L);
        userEntity.setFirstName("Momo");
        userEntity.setLastName("Dam");
        userEntity.setEmail("test@test.com");
        userEntity.setEncryptedPassword("Dam");
        ArrayList<AddressEntity> addressEntities = new ArrayList<>();
        for(int i =0; i < 10; i++){
            AddressEntity sss = new AddressEntity();
            sss.setAddressId(utils.generateAddressId());
            sss.setCity("Cali");
            sss.setCountry("Mexico");
            sss.setPostalCode("L7A2XA");
            sss.setType("COL");
            addressEntities.add(sss);
        }
        userEntity.setAddresses(addressEntities);
        return userEntity;
    }

    private UserDto createUserDto() {
        UserDto userDto = new UserDto();
        userDto.setUserId("asdfasdf");
        userDto.setId(1L);
        userDto.setFirstName("Momo");
        userDto.setLastName("Dam");
        userDto.setEmail("test@test.com");
        userDto.setEncryptedPassword("Dam");
        ArrayList<AddressDto> addressEntities = new ArrayList<>();
        for(int i =0; i < 10; i++){
            AddressDto sss = new AddressDto();
            sss.setAddressId(utils.generateAddressId());
            sss.setCity("Cali");
            sss.setCountry("Mexico");
            sss.setPostalCode("L7A2XA");
            sss.setType("COL");
            addressEntities.add(sss);
        }
        userDto.setAddresses(addressEntities);
        return userDto;
    }
}