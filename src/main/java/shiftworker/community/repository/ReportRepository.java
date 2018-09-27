package shiftworker.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shiftworker.community.domain.Post;
import shiftworker.community.domain.Report;
import shiftworker.community.domain.User;

import java.util.Optional;

/**
 * @author sangsik.kim
 */
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByPostAndUser(Post post, User user);
}
