package com.somethingsblog.app.ws.service.impl;

import com.somethingsblog.app.ws.io.entity.AddressEntity;
import com.somethingsblog.app.ws.io.entity.UserEntity;
import com.somethingsblog.app.ws.io.repository.AddressRepository;
import com.somethingsblog.app.ws.io.repository.UserRepository;
import com.somethingsblog.app.ws.service.AddressesService;
import com.somethingsblog.app.ws.shard.dto.AddressDto;
import com.somethingsblog.app.ws.ui.model.response.AddressRest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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
        List<AddressEntity> addressEntities =
                addressRepository.findAddressEntitiesByUserDetails(userEntity);
        if(addressEntities != null && (long) addressEntities.size() > 0){
            java.lang.reflect.Type listType = new TypeToken<List<AddressDto>>() {}.getType();
            return new ModelMapper().map(addressEntities, listType);
        }
        return returnValue;
    }

    @Override
    public AddressDto getAddressByUserId(String userId, String addressId) {
        AddressDto returnValue = new AddressDto();

        AddressEntity addressEntity = addressRepository.
                findAddressEntityByUserDetailsUserIdAndAddressId(userId,addressId);
        if(addressEntity != null){
            return new ModelMapper().map(addressEntity, AddressDto.class);
        }
        return returnValue;
    }
}
