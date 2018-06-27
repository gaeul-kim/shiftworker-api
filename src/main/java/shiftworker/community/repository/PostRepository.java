package shiftworker.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shiftworker.community.domain.Post;

import java.util.List;

/**
 * @author sangsik.kim
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByDeleted(boolean isDeleted);
}
