package ru.r3d1r4ph.springdb1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.r3d1r4ph.springdb1.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findByEmail(username))
                .map(u -> User.builder()
                        .username(u.getEmail())
                        .password(u.getPassword())
                        .authorities(u.getRole().toString())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не зарегистрирован: " + username));
    }
}
