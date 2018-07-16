package shiftworker.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shiftworker.community.domain.Comment;

import java.util.List;

/**
 * @author sangsik.kim
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(long id);
}
