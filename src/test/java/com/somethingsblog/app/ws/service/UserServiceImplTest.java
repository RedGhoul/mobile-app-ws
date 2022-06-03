package com.somethingsblog.app.ws.service;

import com.somethingsblog.app.ws.io.entity.AddressEntity;
import com.somethingsblog.app.ws.io.entity.UserEntity;
import com.somethingsblog.app.ws.io.repository.UserRepository;
import com.somethingsblog.app.ws.shared.dto.AddressDto;
import com.somethingsblog.app.ws.shared.dto.UserDto;
import com.somethingsblog.app.ws.shared.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    // creates a new instance and injects the required classes that have
    // already been mocked
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;
    @Mock
    Utils utils;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getUser() {
        UserEntity userEntity = createUserEntity();
        when(userRepository.findUserEntityByEmail(anyString())).thenReturn(userEntity);
        UserDto userDto = userService.getUser("test@test.com");
        assertNotNull(userDto);
        assertEquals("Momo", userDto.getFirstName());
    }

    @Test
    void getUserThrowsExceptionIfEmailAddressNotFound() {
        when(userRepository.findUserEntityByEmail(anyString())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, ()->{
            userService.getUser("test@test.com");
        });
    }

    @Test
    void createUserThrowsExceptionIfUserAlreadyExists() {
        UserEntity user = createUserEntity();
        when(userRepository.findUserEntityByEmail(anyString())).thenReturn(user);
        Exception thrownException = assertThrows(Exception.class, ()->{
            userService.createUser(createUserDto());
        });
        assertEquals("User Already Exists", thrownException.getMessage());
    }

    @Test
    void createUser() throws Exception {
        UserEntity user = createUserEntity();
        UserDto userDto = createUserDto();
        when(userRepository.findUserEntityByEmail(anyString())).thenReturn(null);
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        when(utils.generateAddressId(anyInt())).thenReturn("asdfasdfa");
        when(utils.generateUserId(anyInt())).thenReturn("asdfasdf123a");
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("asdfasdf123a");
        UserDto newUser = userService.createUser(userDto);
        assertNotNull(newUser);
        assertEquals(userDto.getUserId(), newUser.getUserId());
        verify(bCryptPasswordEncoder, times(1)).encode(userDto.getPassword());
        // you can use doNothing().when() if you don't want a service or another object to
        // do something when called in the test
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