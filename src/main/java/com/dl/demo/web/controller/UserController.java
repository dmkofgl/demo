package com.dl.demo.web.controller;

import com.dl.demo.domain.mapper.UserMapper;
import com.dl.demo.domain.service.UserService;
import com.example.common.api.contract.UserApi;
import com.example.common.api.model.user.UserDTO;
import com.example.common.api.model.user.UserPrincipal;
import com.example.common.api.model.user.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Collection;

@RestController
public class UserController implements UserApi {
    private UserService userService;
    private UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseEntity<Collection<UserResponse>> getAllUsers() {
        Collection<UserResponse> users = userMapper.toUserResponse(userService.findAll());
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<UserResponse> createUser(UserDTO userDTO) {
        UserResponse userResponse = userMapper.toUserResponse(userService.create(userDTO));
        URI userPath = URI.create("/users/" + userResponse.getId());
        return ResponseEntity.created(userPath).body(userResponse);
    }

    @Override
    public ResponseEntity<UserResponse> getUser(Long id) {
        UserResponse userResponse = userMapper.toUserResponse(userService.findById(id));
        return ResponseEntity.ok(userResponse);
    }

    @Override
    public ResponseEntity<UserPrincipal> getUserByEmail(String email) {
        UserPrincipal userResponse = userMapper.toUserPrincipal(userService.findByEmail(email));
        return ResponseEntity.ok(userResponse);
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(Long id, UserDTO userDTO) {
        UserResponse user = userMapper.toUserResponse(userService.update(id, userDTO));
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity deleteUser(Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
