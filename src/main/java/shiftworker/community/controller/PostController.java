package shiftworker.community.controller;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shiftworker.community.annotation.LoginUser;
import shiftworker.community.domain.Comment;
import shiftworker.community.domain.Post;
import shiftworker.community.domain.User;
import shiftworker.community.domain.type.SearchType;
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
    public List<PostDto> getPosts(@PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                                  @RequestParam(required = false, defaultValue = "NONE") SearchType searchType,
                                  @RequestParam(required = false, defaultValue = "") String keyword) {
        return postService.getAll(searchType, keyword, pageable)
                .stream()
                .map(PostDto::withoutContent)
                .collect(toList());
    }

    @GetMapping("/{id}")
    public PostDto getPost(@PathVariable long id) {
        return PostDto.of(postService.getByIdAndIncreaseViewCount(id));

    }

    @PostMapping
    public Post writePost(@LoginUser User user, @RequestBody PostDto postDto) {
        return postService.write(new Post(postDto.getTitle(), postDto.getContent()), user);
    }

    @GetMapping("/{id}/comments")
    public List<CommentDto> getComments(@PathVariable long id) {
        return commentService.getAllByPostId(id)
                .stream()
                .map(CommentDto::of)
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
        return CommentDto.of(comment);
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostDto {
        private long id;
        private String title;
        private String content;
        private String createdDate;
        private int viewCount;
        private int commentsCount;
        @ApiModelProperty(hidden = true)
        private String author;

        static PostDto of(Post post) {
            return PostDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .author(post.getAuthor().getUsername())
                    .createdDate(post.getFormattedCreateDate())
                    .viewCount(post.getViewCount())
                    .commentsCount(post.getComments().size())
                    .build();
        }

        static PostDto withoutContent(Post post) {
            return PostDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .author(post.getAuthor().getUsername())
                    .createdDate(post.getFormattedCreateDate())
                    .viewCount(post.getViewCount())
                    .commentsCount(post.getComments().size())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentDto {
        private String content;
        private String createdDate;
        @ApiModelProperty(hidden = true)
        private String author;

        static CommentDto of(Comment comment) {
            return CommentDto.builder()
                    .content(comment.getContent())
                    .author(comment.getAuthor().getUsername())
                    .createdDate(comment.getFormattedCreateDate())
                    .build();
        }
    }
}
