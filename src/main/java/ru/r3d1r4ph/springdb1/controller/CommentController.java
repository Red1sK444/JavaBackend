package ru.r3d1r4ph.springdb1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.r3d1r4ph.springdb1.dto.response.CommentDto;
import ru.r3d1r4ph.springdb1.dto.createupdate.CreateUpdateCommentDto;
import ru.r3d1r4ph.springdb1.dto.response.MessageDto;
import ru.r3d1r4ph.springdb1.service.CommentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentDto createComment(@Validated @RequestBody CreateUpdateCommentDto createUpdateCommentDto) {
        return commentService.createComment(createUpdateCommentDto);
    }

    @GetMapping(value = "/{id}")
    public CommentDto getComment(@PathVariable UUID id) {
        return commentService.getCommentDtoById(id.toString());
    }

    @PutMapping(value = "/{id}")
    public CommentDto updateComment(@PathVariable UUID id, @Validated @RequestBody CreateUpdateCommentDto createUpdateCommentDto) {
        return commentService.updateComment(id.toString(), createUpdateCommentDto);
    }

    @DeleteMapping(value = "/{id}")
    public MessageDto deleteComment(@PathVariable UUID id) {
        return commentService.deleteComment(id.toString());
    }
}
