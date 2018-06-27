package shiftworker.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shiftworker.community.domain.User;

import java.util.Optional;

/**
 * @author sangsik.kim
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
