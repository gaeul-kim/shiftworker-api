package shiftworker.community.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shiftworker.community.domain.Comment;

import java.util.Optional;


/**
 * @author sangsik.kim
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPostIdAndDeletedFalse(long id, Pageable pageable);

    Optional<Comment> findByIdAndDeletedFalse(long id);
}
