package com.dl.demo.domain.entity;


import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
}
