package ru.practicum.request.controller;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
public class RequestControllerPrivate {
    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<ParticipationRequestDto> create(
            @PositiveOrZero @PathVariable Long userId,
            @PositiveOrZero @RequestParam Long eventId
    ) {
        ParticipationRequestDto result = requestService.create(userId, eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<List<ParticipationRequestDto>> findAll(
            @PositiveOrZero @PathVariable Long userId
    ) {
        List<ParticipationRequestDto> result = requestService.findAll(userId);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancelRequest(
            @PositiveOrZero @PathVariable Long userId,
            @PositiveOrZero @PathVariable Long requestId
    ) {
        ParticipationRequestDto result = requestService.cancelRequest(userId, requestId);
        return ResponseEntity.ok(result);
    }
}