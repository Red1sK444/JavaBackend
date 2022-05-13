package ru.r3d1r4ph.springdb1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.r3d1r4ph.springdb1.dto.createupdate.CreateUpdateUserDto;
import ru.r3d1r4ph.springdb1.dto.createupdate.LoginDto;
import ru.r3d1r4ph.springdb1.dto.createupdate.RegisterDto;
import ru.r3d1r4ph.springdb1.dto.response.UserDto;
import ru.r3d1r4ph.springdb1.entity.Role;
import ru.r3d1r4ph.springdb1.exception.UserNotFoundException;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    private final MyUserDetailsService myUserDetailsService;

    public UserDto register(RegisterDto registerDto) {

        CreateUpdateUserDto createUpdateUserDto = new CreateUpdateUserDto(
                registerDto.getFullName(),
                registerDto.getEmail(),
                registerDto.getPassword(),
                Role.USER
        );

        return userService.createUser(createUpdateUserDto);
    }

    public Collection<? extends GrantedAuthority> login(LoginDto loginDto) {
        UserDetails user = myUserDetailsService.loadUserByUsername(loginDto.getEmail());
        if (BCrypt.checkpw(loginDto.getPassword(), user.getPassword())) {
            return user.getAuthorities();
        } else {
            throw new UserNotFoundException("Пользователь с логином " + loginDto.getEmail() + " и паролем " + loginDto.getPassword() + " не найден");
        }
    }
}
