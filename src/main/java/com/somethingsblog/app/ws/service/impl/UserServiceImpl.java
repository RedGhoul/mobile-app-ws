package com.somethingsblog.app.ws.service.impl;

import com.somethingsblog.app.ws.Repo.UserRepository;
import com.somethingsblog.app.ws.io.entity.UserEntity;
import com.somethingsblog.app.ws.service.UserService;
import com.somethingsblog.app.ws.shard.Utils;
import com.somethingsblog.app.ws.shard.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Utils utils;

    public UserServiceImpl(UserRepository userRepository, Utils utils) {
        this.userRepository = userRepository;
        this.utils = utils;
    }

    @Override
    public UserDto createUser(UserDto userDto) throws Exception {
        UserEntity storedUserDetails = userRepository.findUserEntityByEmail(userDto.getEmail());
        if(storedUserDetails != null) throw new Exception("User Already Exists");
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto,userEntity);
        userEntity.setEncryptedPassword("test");
        userEntity.setUserId(utils.generateUserId());
        userEntity = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity,returnValue);
        return returnValue;
    }
}
