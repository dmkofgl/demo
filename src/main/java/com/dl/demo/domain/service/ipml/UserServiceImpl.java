package com.dl.demo.domain.service.ipml;

import com.dl.demo.domain.entity.User;
import com.dl.demo.domain.mapper.UserMapper;
import com.dl.demo.domain.repository.UserRepository;
import com.dl.demo.domain.service.UserService;
import com.example.common.api.model.user.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

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
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User create(UserDTO userDTO) {
        User user = userMapper.fromDTO(userDTO);
        User savedUser = userRepository.save(user);

        log.debug("Created new user:" + savedUser.toString());
        return savedUser;
    }

    @Override
    public User update(Long id, UserDTO userDTO) {
        User user = findById(id);
        log.debug("Updated user with ID:" + user.getId() + " from:" + user.toString());
        userMapper.update(user, userDTO);
        log.debug("To:" + user.toString());
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
        log.debug("Deleted user with id:" + id);
    }
}
