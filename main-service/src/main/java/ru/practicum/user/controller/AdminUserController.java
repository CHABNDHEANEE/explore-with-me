package ru.practicum.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.user.service.UserServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserServiceImpl userService;

    @GetMapping
    public List<UserDto> get(@RequestParam(required = false) List<Long> ids,
                             @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                             @Positive @RequestParam(defaultValue = "10") int size) {
        return userService.getUsers(ids, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody UserShortDto userShortDto) {
        return userService.createUser(userShortDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
