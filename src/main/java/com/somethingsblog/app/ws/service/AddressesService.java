package com.somethingsblog.app.ws.service;

import com.somethingsblog.app.ws.shard.dto.AddressDto;

import java.util.List;

public interface AddressesService {
    List<AddressDto> getAddresses(String userId);
}