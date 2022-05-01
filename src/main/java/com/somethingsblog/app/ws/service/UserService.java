package com.somethingsblog.app.ws.service;

import com.somethingsblog.app.ws.shard.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto) throws Exception;
    UserDto getUser(String email);
}
