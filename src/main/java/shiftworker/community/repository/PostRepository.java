package shiftworker.community.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shiftworker.community.domain.Post;

import java.util.Optional;

/**
 * @author sangsik.kim
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByDeletedFalse(Pageable pageable);

    Optional<Post> findByIdAndDeleted(long postId, boolean isDeleted);
}
