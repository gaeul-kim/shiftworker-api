package shiftworker.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shiftworker.community.domain.Post;
import shiftworker.community.domain.User;
import shiftworker.community.repository.PostRepository;

import java.util.List;

/**
 * @author sangsik.kim
 */
@Service
@RequiredArgsConstructor
public class PostService {

    final private PostRepository postRepository;

    public List<Post> getAll() {
        return postRepository.findByDeleted(false);
    }

    public Post create(Post post, User user) {
        return postRepository.save(post.setAuthor(user));
    }
}
