package com.dl.demo.web.controller;

import com.dl.demo.domain.entity.UserResponse;
import com.dl.demo.domain.entity.dto.UserDTO;
import com.dl.demo.domain.mapper.UserMapper;
import com.dl.demo.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final String ID_TEMPLATE_NAME = "userID";
    private static final String ID_TEMPLATE_PATH = "/{" + ID_TEMPLATE_NAME + "}";
    private UserService userService;
    private UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<Collection<UserResponse>> getAllUsers() {
        Collection<UserResponse> users = userMapper.toUserResponse(userService.findAll());
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserDTO userDTO) {
        UserResponse userResponse = userMapper.toUserResponse(userService.create(userDTO));
        URI userPath = URI.create("/users/" + userResponse.getId());
        return ResponseEntity.created(userPath).body(userResponse);
    }

    @GetMapping(ID_TEMPLATE_PATH)
    public ResponseEntity<UserResponse> getUser(@PathVariable(ID_TEMPLATE_NAME) Long id) {
        UserResponse userResponse = userMapper.toUserResponse(userService.findById(id));
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping(ID_TEMPLATE_PATH)
    public ResponseEntity<UserResponse> updateUser(@PathVariable(ID_TEMPLATE_NAME) Long id, @RequestBody UserDTO userDTO) {
        UserResponse user = userMapper.toUserResponse(userService.update(id, userDTO));
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(ID_TEMPLATE_PATH)
    public ResponseEntity deleteUser(@PathVariable(ID_TEMPLATE_NAME) Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
