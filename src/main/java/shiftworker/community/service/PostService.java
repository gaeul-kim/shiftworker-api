package shiftworker.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shiftworker.community.domain.Post;
import shiftworker.community.domain.User;
import shiftworker.community.exception.PostNotFoundException;
import shiftworker.community.repository.PostRepository;

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
                .orElseThrow(PostNotFoundException::new);
    }

    @Transactional
    public Post getByIdAndIncreaseViewCount(long id) {
        return getById(id).increaseViewCount();
    }

    public List<Post> getAll() {
        return postRepository.findByDeleted(false);
    }

    public Post write(Post post, User user) {
        return postRepository.save(post.setAuthor(user));
    }
}
