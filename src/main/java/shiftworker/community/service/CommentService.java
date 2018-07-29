package shiftworker.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shiftworker.community.domain.Comment;
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
}
