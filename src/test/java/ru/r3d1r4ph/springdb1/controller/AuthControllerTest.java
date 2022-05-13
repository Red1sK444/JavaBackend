package ru.r3d1r4ph.springdb1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.r3d1r4ph.springdb1.dto.createupdate.LoginDto;
import ru.r3d1r4ph.springdb1.dto.createupdate.RegisterDto;
import ru.r3d1r4ph.springdb1.dto.response.UserDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    @WithMockUser(username = "newUser@mail.ru", password = "123")
    @DisplayName("Пользователю, который только зарегистрировался, а затем прошел аутентикацию, должны вернуться 200 ОК и роль USER")
    void should200WhenLogin() {
        RegisterDto registerDto = new RegisterDto("Aboba abob abobovic", "newUser@mail.ru", "123");
        var jsonCreatedUser = mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registerDto)
                                )
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var dto = objectMapper.readValue(jsonCreatedUser, UserDto.class);

        mockMvc.perform(
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(new LoginDto(registerDto.getEmail(), registerDto.getPassword())))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].authority").value("USER"));

        mockMvc.perform(
                        delete("/user/" + dto.getId())
                )
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DisplayName("Пользователю, с email, которого не существует в базе, должно вернуться 401 Unauthorized")
    void should401WhenFailedLogin() {
        mockMvc.perform(
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        new LoginDto("nosuchmail@mail.ru", "123"))
                                )
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    @DisplayName("Пользователю, с пустым email должно вернуться 400 Bad Request")
    void should401WhenLoginWithEmptyEmail() {
        mockMvc.perform(
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        new LoginDto("", "123"))
                                )
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    @DisplayName("Пользователю, с паролем, состоящим из пробелов, должно вернуться 400 Bad Request")
    void should401WhenLoginWithBlankPassword() {
        mockMvc.perform(
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        new LoginDto("email@mail.ru", "    "))
                                )
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "abs@mail.ru", password = "123")
    @DisplayName("Зарегистрировавшемуся пользователю должно вернуться 200 ОК и его данные")
    void should200WhenRegister() {
        RegisterDto registerDto = new RegisterDto("Aboba abob abobovic", "abs@mail.ru", "123");

        var jsonCreatedUser = mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registerDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(registerDto.getFullName()))
                .andExpect(jsonPath("$.email").value(registerDto.getEmail()))
                .andReturn()
                .getResponse()
                .getContentAsString();
        var dto = objectMapper.readValue(jsonCreatedUser, UserDto.class);

        mockMvc.perform(
                        delete("/user/" + dto.getId())
                )
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DisplayName("Пользователю, который не ввел password, должно вернуться 400 Bad Request")
    void should400WhenRegisterWithoutPassword() {
        RegisterDto registerDto = new RegisterDto("Aboba abob abobovic", "abs@mail.ru", null);

        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registerDto))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    @DisplayName("Пользователю, который не ввел ничего, должно вернуться 400 Bad Request")
    void should400WhenRegisterWithoutAnyRegisterData() {
        RegisterDto registerDto = new RegisterDto(null, null, null);

        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registerDto))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "abs@mail.ru", password = "123")
    @DisplayName("Пользователю, который ввел email, который уже существует в базе, должно вернуться 400 Bad Request")
    void should400WhenRegisterUserWithExistingEmail() {
        RegisterDto firstRegisterDto = new RegisterDto("Aboba abob abobovic", "abs@mail.ru", "123");

        var jsonCreatedUser = mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(firstRegisterDto))
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var dto = objectMapper.readValue(jsonCreatedUser, UserDto.class);

        RegisterDto secondRegisterDto = new RegisterDto("Aboba s", firstRegisterDto.getEmail(), "123");
        mockMvc.perform(
                        post("/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(secondRegisterDto))
                )
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        delete("/user/" + dto.getId())
                )
                .andExpect(status().isOk());
    }
}