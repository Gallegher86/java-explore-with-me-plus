package ru.practicum.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.CreateParticipationRequestDto;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.service.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users")
public class UserController {
    private final RequestService requestService;

    /**
     * Получение информации о заявках текущего пользователя на участие в чужих событиях
     */
    @GetMapping("/{userId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getRequestsByUser(
            @PathVariable Long userId) {
        List<ParticipationRequestDto> requests = requestService.getRequestsByUser(userId);
        return ResponseEntity.ok(requests);
    }
//    /**
//     * Добавление запроса от текущего пользователя на участие в событии
//     */
//    @PostMapping("/{userId}/requests")
//    public ResponseEntity<ParticipationRequestDto> createParticipationRequest(
//            @PathVariable Long userId,
//            @RequestBody @Valid CreateParticipationRequestDto requestDto) {
//        ParticipationRequestDto createdRequest = requestService.createParticipationRequest(userId, requestDto.getEventId());
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
//    }
    

}

