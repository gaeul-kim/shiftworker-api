package shiftworker.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shiftworker.community.domain.Comment;
import shiftworker.community.repository.CommentRepository;

import java.util.List;

/**
 * @author sangsik.kim
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getAllByPostId(long id) {
        return commentRepository.findByPostId(id);
    }

    public Comment add(Comment comment) {
        return commentRepository.save(comment);
    }
}
