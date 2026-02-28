package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.dto.State;
import ru.practicum.event.model.Event;
import ru.practicum.event.service.EventService;
import ru.practicum.exception.ConflictException;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.model.Request;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final EventService eventService;
    private final UserService userService;

    /**
     * Получение информации о заявках текущего пользователя на участие в чужих событиях
     */
    public List<ParticipationRequestDto> getRequestsByUser(Long userId) {
        List<Request> requests = requestRepository.findAllRequestsByUserId(userId);

        return requests.stream()
                .map(requestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

//    /**
//     * Создание заявки на участие в событии
//     */
//    @Transactional
//    public ParticipationRequestDto createParticipationRequest(Long userId, Long eventId) {
//        // 1. Проверка на дублирование запроса
//        if (requestRepository.existsByRequesterIdAndEventId(userId, eventId)) {
//            log.warn("Запрос на участие уже существует для пользователя {} и события {}", userId, eventId);
//            throw new ConflictException("Запрос на участие уже существует");
//        }
//
//        // 2. Получение сущностей для маппинга и проверок
//        Event eventEntity = eventService.getEventById(eventId);
//        User userEntity = userService.getUserById(userId);
//
//        // 3. Проверка, что пользователь не инициатор
//        if (eventEntity.getInitiator().getId().equals(userId)) {
//            log.warn("Пользователь {} является инициатором события {}, нельзя создать запрос на участие", userId, eventId);
//            throw new ConflictException("Инициатор не может участвовать в собственном событии");
//        }
//
//        // 4. Проверка публикации события
//        if (!State.PUBLISHED.equals(eventEntity.getState())) {
//            log.warn("Событие {} не опубликовано, нельзя создать запрос на участие", eventId);
//            throw new ConflictException("Нельзя участвовать в неопубликованном событии");
//        }
//
//        // 5. Создаём сущность Request через маппер
//        Request request = requestMapper.toRequest(userEntity, eventEntity);

//        // 6. Корректируем статус, если пре‑модерация отключена
//        if (Boolean.FALSE.equals(eventEntity.getRequestModeration())) {
//            request.setStatus(Status.CONFIRMED);
//            log.debug("Для события {} отключена пре‑модерация, устанавливаем статус запроса «ПОДТВЕРЖДЁН»", eventId);
//        } else {
//            log.debug("Для события {} включена пре‑модерация, оставляем статус запроса «ОЖИДАЕТ РАССМОТРЕНИЯ»", eventId);
//        }

//        // 7. Сохраняем сущность в БД через репозиторий
//        Request savedRequest = requestRepository.save(request);
//
//        // 8. Конвертируем сохранённую сущность в DTO
//        ParticipationRequestDto resultDto = requestMapper.toParticipationRequestDto(savedRequest);
//
//        log.info("Запрос на участие успешно создан для пользователя {} на событие {}, ID запроса: {}",
//                userId, eventId, savedRequest.getId());
//
//        return resultDto;
//    }
//
//
}
