package ru.r3d1r4ph.springdb1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.r3d1r4ph.springdb1.dto.createupdate.LoginDto;
import ru.r3d1r4ph.springdb1.dto.createupdate.RegisterDto;
import ru.r3d1r4ph.springdb1.dto.response.UserDto;
import ru.r3d1r4ph.springdb1.service.AuthService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public UserDto register(@Validated @RequestBody RegisterDto registerDto) {
        return authService.register(registerDto);
    }

    @PostMapping("/login")
    public Collection<? extends GrantedAuthority> login(@Validated @RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @GetMapping("/me")
    public String getMe(Authentication authentication) {
        return authentication.getName();
    }
}
