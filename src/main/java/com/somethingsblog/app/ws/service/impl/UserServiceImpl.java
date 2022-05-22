package com.somethingsblog.app.ws.service.impl;

import com.somethingsblog.app.ws.exceptions.UserServiceException;
import com.somethingsblog.app.ws.io.entity.AddressEntity;
import com.somethingsblog.app.ws.io.repository.UserRepository;
import com.somethingsblog.app.ws.io.entity.UserEntity;
import com.somethingsblog.app.ws.service.UserService;
import com.somethingsblog.app.ws.shard.dto.UserDto;
import com.somethingsblog.app.ws.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final com.somethingsblog.app.ws.shard.utils utils;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           com.somethingsblog.app.ws.shard.utils utils, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.utils = utils;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) throws Exception {
        UserEntity storedUserDetails = userRepository.findUserEntityByEmail(userDto.getEmail());
        if(storedUserDetails != null) throw new Exception("User Already Exists");
        UserEntity userEntity = new ModelMapper().map(userDto,UserEntity.class);
        for(AddressEntity entity: userEntity.getAddresses()){
            entity.setAddressId(utils.generateUserId());
            entity.setUserDetails(userEntity);
        }
        userEntity.setEncryptedPassword(
                bCryptPasswordEncoder.encode(userDto.getPassword()));
        userEntity.setUserId(utils.generateUserId());
        userEntity = userRepository.save(userEntity);

        return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity storedUserDetails = userRepository.findUserEntityByEmail(email);
        if(storedUserDetails == null) throw new UsernameNotFoundException(email);
        return new ModelMapper().map(storedUserDetails, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException("User with Id: " + userId +  " not found");
        return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDto updateUser(String id, UserDto userDto) throws Exception {
        UserEntity storedUser = userRepository.findUserEntityByUserId(id);
        if(storedUser == null) throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        storedUser.setFirstName(userDto.getFirstName().isBlank() ? storedUser.getFirstName() : userDto.getFirstName());
        storedUser.setLastName(userDto.getLastName().isBlank() ? storedUser.getLastName() : userDto.getLastName());
        return new ModelMapper().map(storedUser, UserDto.class);
    }

    @Override
    public void deleteUser(String id) {
        UserEntity userEntity = userRepository.findUserEntityByUserId(id);
        if(userEntity == null){
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
        List<UserEntity> users = usersPage.getContent();

        for(UserEntity userEntity: users){
            returnValue.add(new ModelMapper().map(userEntity, UserDto.class));
        }
        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByEmail(username);
        if(userEntity == null) throw new UsernameNotFoundException(username);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
