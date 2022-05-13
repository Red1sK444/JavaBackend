package ru.r3d1r4ph.springdb1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.r3d1r4ph.springdb1.csv.CommentCsv;
import ru.r3d1r4ph.springdb1.dto.createupdate.CreateUpdateCommentDto;
import ru.r3d1r4ph.springdb1.dto.mapper.CommentMapper;
import ru.r3d1r4ph.springdb1.dto.response.CommentDto;
import ru.r3d1r4ph.springdb1.dto.response.MessageDto;
import ru.r3d1r4ph.springdb1.entity.CommentEntity;
import ru.r3d1r4ph.springdb1.exception.CommentNotFoundException;
import ru.r3d1r4ph.springdb1.repository.CommentRepository;
import ru.r3d1r4ph.springdb1.utils.Utils;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    @Transactional
    public CommentDto createComment(CreateUpdateCommentDto createUpdateCommentDto) {
        return commentMapper.toDto(commentRepository.save(commentMapper.toEntity(createUpdateCommentDto)));
    }

    @Transactional
    public CommentDto updateComment(String id, CreateUpdateCommentDto createUpdateCommentDto) {
        CommentEntity commentEntity = commentMapper.toEntity(createUpdateCommentDto);

        commentEntity.setUuid(id);
        commentEntity.setCreateDate(getCommentEntityById(id).getCreateDate());
        return commentMapper.toDto(commentRepository.save(commentEntity));
    }

    @Transactional
    public MessageDto deleteComment(String id) {
        commentRepository.deleteById(id);
        return new MessageDto("OK");
    }

    @Transactional(readOnly = true)
    public CommentDto getCommentDtoById(String uuid) {
        return commentMapper.toDto(getCommentEntityById(uuid));
    }

    @Transactional(readOnly = true)
    public CommentEntity getCommentEntityById(String uuid) {
        return commentRepository.findById(uuid)
                .orElseThrow(() -> new CommentNotFoundException("Комментарий с id " + uuid + " не найден"));
    }

    @Value("${csv.comments:/csv/comments.csv}")
    private String commentsPath;

    @Transactional
    public void loadCommentsFromCsv() {
        Utils.parseToCsv(commentsPath, CommentCsv.class)
                .stream()
                .map(commentMapper::toEntity)
                .forEach(commentRepository::save);
    }
}
