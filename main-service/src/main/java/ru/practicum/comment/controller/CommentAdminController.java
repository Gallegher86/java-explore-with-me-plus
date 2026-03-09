package ru.practicum.comment.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentFullDto;
import ru.practicum.comment.dto.CommentShortDto;
import ru.practicum.comment.dto.CommentStatusUpdateRequest;
import ru.practicum.comment.service.CommentService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
public class CommentAdminController {

    private final CommentService commentService;

    @PatchMapping
    public List<CommentShortDto> approveComments(@RequestBody @Valid CommentStatusUpdateRequest dto) {
        log.info("CommentAdminController PATCH /admin/comments, dto: {}", dto);
        return commentService.approveComments(dto);
    }

    @GetMapping
    public List<CommentFullDto> get() {
        log.info("CommentAdminController GET /admin/comments");
        return commentService.getComments();
    }

    @GetMapping("/{commentId}")
    public CommentFullDto getById(@Positive @PathVariable Long commentId) {
        log.info("CommentAdminController GET /admin/comments/{}", commentId);
        return commentService.getCommentById(commentId);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable Long commentId) {
        log.info("CommentAdminController DELETE /admin/comments/{}", commentId);
        commentService.deleteByAdmin(commentId);
    }
}
