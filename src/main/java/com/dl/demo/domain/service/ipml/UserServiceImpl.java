package com.dl.demo.domain.service.ipml;

import com.dl.demo.domain.entity.User;
import com.dl.demo.domain.entity.dto.UserDTO;
import com.dl.demo.domain.repository.UserRepository;
import com.dl.demo.domain.service.UserService;
import com.dl.demo.domain.service.helper.UserConverter;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User create(UserDTO userDTO) {
        User user = UserConverter.fromDto(userDTO);
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, UserDTO userDTO) {
        User user = findById(id);
        UserConverter.FlushDto(user, userDTO);
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
         userRepository.deleteById(id);
    }
}
