package ru.practicum.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiError {

    private String message;

    private String reason;

    private String status;

    private String timestamp;
}
