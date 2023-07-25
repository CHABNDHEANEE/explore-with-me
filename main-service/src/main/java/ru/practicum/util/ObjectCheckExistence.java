package ru.practicum.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.request.model.ParticipationRequest;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ObjectCheckExistence {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final CompilationRepository compilationRepository;

    public Event checkEvent(Long id) {
        return eventRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Event %d not found", id))
        );
    }

    public User checkUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("User с id %d not found", id))
        );
    }

    public Category checkCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Category с id %d not found", id))
        );
    }

    public ParticipationRequest checkRequest(Long id) {
        return requestRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Request id %d not found", id))
        );
    }

    public Compilation checkCompilation(Long id) {
        return compilationRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Compilation id %d not found", id))
        );
    }

    public void checkDateTime(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new ValidationException("Start can't be after the end");
        }
    }
}
