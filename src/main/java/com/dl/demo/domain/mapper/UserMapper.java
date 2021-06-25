package com.dl.demo.domain.mapper;

import com.dl.demo.domain.entity.User;
import com.dl.demo.domain.entity.UserResponse;
import com.dl.demo.domain.entity.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Collection;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User fromDTO(UserDTO user);

    UserResponse toUserResponse(User user);

    Collection<UserResponse> toUserResponse(Collection<User> user);

    void update(@MappingTarget User user, UserDTO userDTO);
}
