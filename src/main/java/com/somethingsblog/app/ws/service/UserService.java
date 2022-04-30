package com.somethingsblog.app.ws.service;

import com.somethingsblog.app.ws.shard.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto) throws Exception;

}
