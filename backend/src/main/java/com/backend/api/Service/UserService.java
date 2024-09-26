package com.backend.api.Service;


import com.backend.api.Dto.Request.UserDto;

public interface UserService {
    UserDto register(UserDto registrationRequest);
    UserDto login(UserDto loginRequest);
}
