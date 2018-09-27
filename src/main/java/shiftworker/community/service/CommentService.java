package shiftworker.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shiftworker.community.domain.Comment;
import shiftworker.community.domain.User;
import shiftworker.community.exception.CommentNotFoundException;
import shiftworker.community.repository.CommentRepository;

/**
 * @author sangsik.kim
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Page<Comment> getAllByPostId(long id, Pageable pageable) {
        return commentRepository.findByPostIdAndDeletedFalse(id, pageable);
    }

    public Comment add(Comment comment) {
        return commentRepository.save(comment);
    }

    private Comment getById(long id) {
        return commentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(CommentNotFoundException::new);
    }

    @Transactional
    public void delete(long id, User user) {
        getById(id).delete(user);
    }

    @Transactional
    public Comment update(long id, String content, User user) {
        return getById(id).update(content, user);
    }
}
