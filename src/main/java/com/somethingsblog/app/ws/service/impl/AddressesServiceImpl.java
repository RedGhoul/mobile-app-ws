package com.somethingsblog.app.ws.service.impl;

import com.somethingsblog.app.ws.io.entity.UserEntity;
import com.somethingsblog.app.ws.io.repository.AddressRepository;
import com.somethingsblog.app.ws.io.repository.UserRepository;
import com.somethingsblog.app.ws.service.AddressesService;
import com.somethingsblog.app.ws.shard.dto.AddressDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class AddressesServiceImpl implements AddressesService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<AddressDto> getAddresses(String userId) {
        List<AddressDto> returnValue = new ArrayList<>();
        UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
        if(userEntity == null) return returnValue;
        return null;
    }
}
