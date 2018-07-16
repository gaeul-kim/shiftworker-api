package shiftworker.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shiftworker.community.domain.Post;
import shiftworker.community.domain.User;
import shiftworker.community.repository.PostRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * @author sangsik.kim
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post getById(long id) {
        return postRepository.findByIdAndDeleted(id, false)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Post> getAll() {
        return postRepository.findByDeleted(false);
    }

    public Post write(Post post, User user) {
        return postRepository.save(post.setAuthor(user));
    }
}
