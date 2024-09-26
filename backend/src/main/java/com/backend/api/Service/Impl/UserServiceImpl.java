package com.backend.api.Service.Impl;

import com.backend.api.Dto.Request.UserDto;
import com.backend.api.Entity.User;
import com.backend.api.Exception.InvalidUserStateException;
import com.backend.api.Repository.UserRepository;
import com.backend.api.Service.UserService;
import com.backend.api.Util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final static String USER_LOGIN_SUCCESSFULLY = "User Login Successfully";
    private  final static String USER_LOGIN_FALSE = "User Login False";
    private final static String USER_REGISTER_SUCCESSFULLY = "User Register Successfully";
    private final static String USER_REGISTER_FALSE = "User Register False";
    private final static String USER_EMAIL_ALREADY_EXIT = "Email already exists";
    private final static String USER_INVALID_PASSWORD = "Invalid password";

    @Override
    public UserDto register(UserDto registrationRequest) {
        UserDto userDto = new UserDto();
        try {
            if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
                throw new InvalidUserStateException(USER_EMAIL_ALREADY_EXIT);
            }
            User user = new User();
            user.setName(registrationRequest.getName());
            user.setEmail(registrationRequest.getEmail());
            user.setRole(registrationRequest.getRole());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            User savedUser = userRepository.save(user);
            if (savedUser.getId() > 0) {
                userDto.setOurUsers(savedUser);
                userDto.setMessage(USER_REGISTER_SUCCESSFULLY);
            }
        } catch (Exception e) {
            throw new InvalidUserStateException(USER_REGISTER_FALSE);
        }
        return userDto;
    }


    @Override
    public UserDto login(UserDto loginRequest) {
        UserDto response = new UserDto();
        try {
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                throw new InvalidUserStateException(USER_INVALID_PASSWORD);

            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateToken(user);
            response.setToken(jwt);
            response.setRole(user.getRole());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage(USER_LOGIN_SUCCESSFULLY);
        }catch (Exception e){
            response.setMessage(e.getMessage());
            throw  new InvalidUserStateException(USER_LOGIN_FALSE);
        }
        return  response;
    }
}
