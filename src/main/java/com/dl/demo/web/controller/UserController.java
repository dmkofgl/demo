package com.dl.demo.web.controller;

import com.dl.demo.domain.entity.User;
import com.dl.demo.domain.entity.UserResponse;
import com.dl.demo.domain.entity.dto.UserDTO;
import com.dl.demo.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final String ID_TEMPLATE_NAME = "userID";
    private static final String ID_TEMPLATE_PATH = "/{" + ID_TEMPLATE_NAME + "}";
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.findAll().stream().map(UserResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserDTO userDTO) {
        User user = userService.create(userDTO);

        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(new UserResponse(user));
    }

    @GetMapping(ID_TEMPLATE_PATH)
    public ResponseEntity<UserResponse> getUser(@PathVariable(ID_TEMPLATE_NAME) Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(new UserResponse(user));
    }

    @PutMapping(ID_TEMPLATE_PATH)
    public ResponseEntity<UserResponse> updateUser(@PathVariable(ID_TEMPLATE_NAME) Long id, @RequestBody UserDTO userDTO) {
        User user = userService.update(id, userDTO);
        return ResponseEntity.ok(new UserResponse(user));
    }

    @DeleteMapping(ID_TEMPLATE_PATH)
    public ResponseEntity deleteUser(@PathVariable(ID_TEMPLATE_NAME) Long id, @RequestBody UserDTO userDTO) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
