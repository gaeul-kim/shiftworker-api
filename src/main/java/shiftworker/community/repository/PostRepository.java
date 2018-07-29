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
    Optional<Post> findByIdAndDeletedFalse(long postId);

    Page<Post> findByDeletedFalse(Pageable pageable);

    Page<Post> findByTitleIgnoreCaseContainingOrContentIgnoreCaseContainingAndDeletedFalse(String title, String content, Pageable pageable);

    Page<Post> findByAuthorUsernameIgnoreCaseContainingAndDeletedFalse(String keyword, Pageable pageable);


}
