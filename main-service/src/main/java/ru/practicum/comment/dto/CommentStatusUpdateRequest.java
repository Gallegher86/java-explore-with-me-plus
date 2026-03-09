package ru.practicum.comment.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CommentStatusUpdateRequest {
    @NotEmpty()
    @NotNull()
    private List<Long> commentIds;

    @NotNull()
    private CommentStateActionAdmin stateAction;
}
