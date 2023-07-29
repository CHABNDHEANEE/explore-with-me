package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.ConflictException;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.util.ObjectCheckExistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.user.mapper.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements ru.practicum.user.service.UserService {

    private final UserRepository repository;
    private final ObjectCheckExistence checkExistence;

    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        PageRequest pageable = PageRequest.of(from / size, size);
        if (ids != null) {
            return repository.findByIdIn(ids, pageable).stream()
                    .map(USER_MAPPER::toUserDto)
                    .collect(Collectors.toList());
        } else {
            return repository.findAll(pageable).stream()
                    .map(USER_MAPPER::toUserDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    @Transactional
    public UserDto createUser(UserShortDto userShortDto) {
        Optional<User> userOpt = repository.findByName(userShortDto.getName());
        if (userOpt.isPresent()) {
            throw new ConflictException(String.format("User with name %s already exists",
                    userShortDto.getName()));
        }
        User user = repository.save(USER_MAPPER.toUser(userShortDto));
        return USER_MAPPER.toUserDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        checkExistence.getUser(userId);
        repository.deleteById(userId);
    }
}
