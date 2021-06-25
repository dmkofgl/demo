package com.dl.demo.domain.service;

import com.dl.demo.domain.entity.User;
import com.dl.demo.domain.entity.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    User create(UserDTO user);

    User update(Long userId, UserDTO user);

    void deleteById(Long id);


}
