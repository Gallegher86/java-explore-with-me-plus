package ru.practicum.comment.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CommentStatusUpdateRequest {
    @NotEmpty()
    private List<@NotNull Long> commentIds;

    @NotNull()
    private CommentStateActionAdmin stateAction;
}
