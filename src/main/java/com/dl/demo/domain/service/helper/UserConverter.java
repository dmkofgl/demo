package com.dl.demo.domain.service.helper;

import com.dl.demo.domain.entity.User;
import com.dl.demo.domain.entity.dto.UserDTO;
import org.apache.logging.log4j.util.Strings;

public class UserConverter {

    public static User fromDto(UserDTO user) {
        return User.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public static void FlushDto(User destination, UserDTO source) {
        if (!Strings.isEmpty(source.getUsername())) {
            destination.setUsername(source.getUsername());
        }
        if (!Strings.isEmpty(source.getEmail())) {
            destination.setEmail(source.getEmail());
        }
        if (!Strings.isEmpty(source.getPassword())) {
            destination.setPassword(source.getPassword());
        }
    }
}
