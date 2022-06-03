package com.somethingsblog.app.ws.service;

import com.somethingsblog.app.ws.shared.dto.AddressDto;

import java.util.List;

public interface AddressesService {
    List<AddressDto> getAddresses(String userId);

    AddressDto getAddressByUserId(String userId, String addressId);
}
