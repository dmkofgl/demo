package com.dl.demo.domain.entity.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserDTO {
    private String username;
    private String email;
    private String password;


}
