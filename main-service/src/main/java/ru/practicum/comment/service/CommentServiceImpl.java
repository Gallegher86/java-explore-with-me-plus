package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.model.Category;
import ru.practicum.comment.dto.CommentShortDto;
import ru.practicum.comment.dto.CommentStatusUpdateRequest;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.common.EntityFinder;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.event.dto.State;
import ru.practicum.event.model.Event;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final EntityFinder entityFinder;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentShortDto createComment(Long userId, Long eventId, NewCommentDto dto) {
        log.info("CommentService: от пользователя с id {}, получен запрос на добавление комментария: {}.",
                userId, dto);

        User author = entityFinder.getUserOrThrow(userId);
        Event event = entityFinder.getEventOrThrow(eventId);
        LocalDateTime now = LocalDateTime.now();

        Comment comment = commentMapper.toComment(dto);
        comment.setAuthor(author);
        comment.setEvent(event);
        comment.setCreatedOn(now);
        comment.setState(State.PENDING);

        Comment saved = commentRepository.save(comment);
        CommentShortDto result = commentMapper.toCommentShortDto(saved, author.getId(), event.getId());
        log.info("CommentService: комментарий сохранен: {}", result);
        return result;
    }

    @Override
    @Transactional
    public CommentShortDto updateComment(Long userId, Long commentId, NewCommentDto request) {
        log.info("CommentService: от пользователя с id {}, получен запрос на обновление комментария c id {}.",
                userId, commentId);

        Comment saved = entityFinder.getCommentOrThrow(commentId);

        if (!saved.getAuthor().getId().equals(userId)) {
            throw new NotFoundException("Комментарий с id=" + commentId
                    + " от пользователя " + userId + " не найден, обновление невозможно.");
        }

        if (saved.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Нельзя изменить опубликованный комментарий.");
        }

        if (request.getText() != null) {
            saved.setText(request.getText());
        }

        CommentShortDto result = commentMapper.toCommentShortDto(saved, userId, saved.getEvent().getId());

        log.info("CommentService: комментарий обновлен: {}.", result);
        return result;
    }

    @Override
    @Transactional
    public void deleteByUser(Long userId, Long commentId) {
        log.info("CommentService: от пользователя с id {}, получен запрос на удаление комментария с id {}.",
                userId, commentId);

        Comment saved = entityFinder.getCommentOrThrow(commentId);

        if (!saved.getAuthor().getId().equals(userId)) {
            throw new NotFoundException("Комментарий с id=" + commentId +
                    " от пользователя " + userId + " не найден, удаление невозможно.");
        }

        commentRepository.delete(saved);
        log.info("CommentService: комментарий с id: {} удален пользователем.", commentId);
    }

    @Override
    @Transactional
    public List<CommentShortDto> approveComments(CommentStatusUpdateRequest request) {
        return List.of();
    }

    @Override
    @Transactional
    public void deleteByAdmin(Long commentId) {
        log.info("CommentService: получен запрос админа на удаление комментария с id {}.", commentId);
        Comment saved = entityFinder.getCommentOrThrow(commentId);
        commentRepository.delete(saved);
        log.info("CommentService: комментарий с id: {} удален админом.", commentId);
    }

}
