package shiftworker.community.controller;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shiftworker.community.annotation.LoginUser;
import shiftworker.community.domain.Post;
import shiftworker.community.domain.User;
import shiftworker.community.service.PostService;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author sangsik.kim
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostDto> list() {
        return postService.getAll()
                .stream()
                .map(PostDto::new)
                .collect(toList());
    }

    @PostMapping
    public Post write(@LoginUser User user, @RequestBody PostDto postDto) {
        return postService.create(postDto.toPost(), user);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PostDto {
        private String title;
        private String content;
        @ApiModelProperty(hidden = true)
        private String author;

        PostDto(Post post) {
            this.title = post.getTitle();
            this.content = post.getContent();
            this.author = post.getAuthor().getUsername();
        }

        Post toPost() {
            return new Post(this.title, this.content);
        }
    }
}
