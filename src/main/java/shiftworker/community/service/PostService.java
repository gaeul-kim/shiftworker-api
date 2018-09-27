package shiftworker.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shiftworker.community.domain.Post;
import shiftworker.community.domain.User;
import shiftworker.community.domain.type.SearchType;
import shiftworker.community.exception.PostNotFoundException;
import shiftworker.community.repository.PostRepository;

/**
 * @author sangsik.kim
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post getById(long id) {
        return postRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(PostNotFoundException::new);
    }

    @Transactional
    public Post getByIdAndIncreaseViewCount(long id) {
        return getById(id).increaseViewCount();
    }

    public Page<Post> getAll(SearchType searchType, String keyword, Pageable pageable) {
        switch (searchType) {
            case POST:
                return postRepository.findByTitleIgnoreCaseContainingOrContentIgnoreCaseContainingAndDeletedFalse(keyword, keyword, pageable);
            case AUTHOR:
                return postRepository.findByAuthorUsernameIgnoreCaseContainingAndDeletedFalse(keyword, pageable);
            default:
                return postRepository.findByDeletedFalse(pageable);
        }
    }

    public Post write(Post post, User user) {
        return postRepository.save(post.setAuthor(user));
    }

    @Transactional
    public void delete(long id, User user) {
        getById(id).delete(user);
    }
}
