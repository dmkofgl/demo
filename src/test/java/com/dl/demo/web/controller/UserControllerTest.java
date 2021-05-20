package com.dl.demo.web.controller;

import com.dl.demo.domain.entity.User;
import com.dl.demo.domain.entity.dto.UserDTO;
import com.dl.demo.domain.service.ipml.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
class UserControllerTest {
    private static final String USER_PATH = "/users";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserServiceImpl userService;

    @Test
    public void getAllUsers_whenUsersExist_shouldReturnOk() throws Exception {
        List<User> users = Arrays.asList(
                User.builder().username("test1").id(1L).build(),
                User.builder().username("test2").id(2L).build()
        );

        Mockito.when(userService.findAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get(USER_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("test1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].username").value("test2"));
    }

    @Test
    public void createUser_whenUserCorrect_shouldReturnCreated() throws Exception {
        User user = User.builder().username("test1").id(1L).password("test").build();
        UserDTO userDTO = UserDTO.builder().username("test1").password("test").build();

        Mockito.when(userService.create(userDTO)).thenReturn(user);

        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(USER_PATH).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("test1"));
    }

    @Test
    public void getUserById_whenUserExists_shouldReturnOk() throws Exception {
        Long userId = 1L;
        User user = User.builder().username("tester").id(userId).password("test").email("bla-bla").build();

        Mockito.when(userService.findById(userId)).thenReturn(user);

        String path = USER_PATH + "/" + userId;
        mockMvc.perform(MockMvcRequestBuilders.get(path))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("tester"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("bla-bla"));
    }

    @Test
    public void getUserById_whenUserNotExists_shouldReturnNotFound() throws Exception {
        Long userId = 1L;

        Mockito.when(userService.findById(userId)).thenThrow(new EntityNotFoundException());

        String path = USER_PATH + "/" + userId;
        mockMvc.perform(MockMvcRequestBuilders.get(path))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void updateUser_whenUserCorrect_shouldReturnOk() throws Exception {
        Long userId = 1L;
        UserDTO userDTO = UserDTO.builder().username("investor").password("tset").build();
        User user = User.builder().username(userDTO.getUsername()).id(userId).password(userDTO.getPassword()).build();

        Mockito.when(userService.update(userId, userDTO)).thenReturn(user);

        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(userDTO);

        String path = USER_PATH + "/" + userId;
        mockMvc.perform(MockMvcRequestBuilders.put(path).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("investor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").doesNotExist());
    }

    @Test
    public void updateUser_whenUserNotExists_shouldReturnNotFound() throws Exception {
        Long userId = 1L;
        UserDTO userDTO = UserDTO.builder().username("investor").password("tset").build();

        Mockito.when(userService.update(userId, userDTO)).thenThrow(new EntityNotFoundException());

        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(userDTO);

        String path = USER_PATH + "/" + userId;
        mockMvc.perform(MockMvcRequestBuilders.put(path).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


}
