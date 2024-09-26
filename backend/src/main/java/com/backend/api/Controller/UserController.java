package com.backend.api.Controller;


import com.backend.api.Dto.Request.UserDto;
import com.backend.api.Service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        return  ResponseEntity.ok(userService.register(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto) {
        return  ResponseEntity.ok(userService.login(userDto));
    }
}
