package ru.practicum.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.service.RequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class PrivateRequestController {

    private final RequestService requestService;

    @GetMapping
    public List<ParticipationRequestDto> get(@PathVariable Long userId) {
        return requestService.findByRequestorId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto create(@PathVariable Long userId,
                                          @RequestParam Long eventId) {
        return requestService.addRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto update(@PathVariable Long userId,
                                          @PathVariable Long requestId) {
        return requestService.cancelRequest(userId, requestId);
    }
}
