package com.backend.api.Dto.Request;


import com.backend.api.Entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private String name;
    private String role;
    private String email;
    private String password;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private User ourUsers;
    private String message;
    private List<User> ourUsersList;
}
