package ru.r3d1r4ph.springdb1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.r3d1r4ph.springdb1.dto.createupdate.CreateUpdateUserDto;
import ru.r3d1r4ph.springdb1.dto.createupdate.UpdateRoleDto;
import ru.r3d1r4ph.springdb1.dto.response.MessageDto;
import ru.r3d1r4ph.springdb1.dto.search.FetchDto;
import ru.r3d1r4ph.springdb1.dto.response.UserDto;
import ru.r3d1r4ph.springdb1.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @PostMapping
//    public UserDto createUser(@RequestBody CreateUpdateUserDto createUpdateUserDto) {
//        return userService.createUser(createUpdateUserDto);
//    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable UUID id) {
        return userService.getUserDtoById(id.toString());
    }

    @GetMapping("/fetch")
    public List<UserDto> fetchUsers(@RequestBody FetchDto dto) {
        return userService.getUserDtosByAllExceptPassword(dto);
    }

    @PatchMapping("/{id}/role")
    public MessageDto changeRole(@PathVariable UUID id, @RequestBody UpdateRoleDto updateRoleDto) {
        return userService.changeRole(id.toString(), updateRoleDto);
    }

    @PutMapping(value = "/{id}")
    public UserDto updateUser(@PathVariable UUID id, @RequestBody CreateUpdateUserDto createUpdateUserDto) {
        return userService.updateUser(id.toString(), createUpdateUserDto);
    }

    @DeleteMapping(value = "/{id}")
    public MessageDto deleteUser(@PathVariable UUID id) {
        return userService.deleteUser(id.toString());
    }
}
