package shiftworker.community.controller;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shiftworker.community.annotation.LoginUser;
import shiftworker.community.domain.Comment;
import shiftworker.community.domain.Post;
import shiftworker.community.domain.User;
import shiftworker.community.service.CommentService;
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
    private final CommentService commentService;

    @GetMapping
    public List<PostDto> getPosts() {
        return postService.getAll()
                .stream()
                .map(PostDto::new)
                .collect(toList());
    }

    @PostMapping
    public Post writePost(@LoginUser User user, @RequestBody PostDto postDto) {
        return postService.write(postDto.toPost(), user);
    }

    @GetMapping("/{id}/comments")
    public List<CommentDto> getComments(@PathVariable long id) {
        return commentService.getAllByPostId(id)
                .stream()
                .map(CommentDto::new)
                .collect(toList());
    }

    @PostMapping("/{id}/comments")
    public CommentDto addComment(@PathVariable long id, @LoginUser User user, @RequestBody CommentDto commentDto) {
        Post post = postService.getById(id);
        Comment comment = commentService.add(
                Comment.builder()
                        .author(user)
                        .post(post)
                        .content(commentDto.getContent())
                        .build());
        return new CommentDto(comment);
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

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CommentDto {
        private String content;
        @ApiModelProperty(hidden = true)
        private String author;

        CommentDto(Comment comment) {
            this.content = comment.getContent();
            this.author = comment.getAuthor().getUsername();
        }
    }
}
