package shiftworker.community.controller;

import io.swagger.annotations.ApiImplicitParam;
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
import shiftworker.community.domain.Post;
import shiftworker.community.domain.User;
import shiftworker.community.domain.type.SearchType;
import shiftworker.community.service.PostService;

import java.util.Collections;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static shiftworker.community.controller.CommentController.CommentDto;

/**
 * @author sangsik.kim
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

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
    public PostDto getPost(@PathVariable long id, @RequestParam(required = false, defaultValue = "FALSE") boolean comments) {
        return PostDto.of(postService.getByIdAndIncreaseViewCount(id), comments);

    }

    @PostMapping
    @ApiImplicitParam(name = "Authorization", value = "사용자 인증 토큰", required = true, dataType = "String", paramType = "header")
    public PostDto writePost(@LoginUser User user, @RequestBody PostDto postDto) {
        return PostDto.of(postService.write(Post.of(postDto.getTitle(), postDto.getContent()), user), false);
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostDto {

        @ApiModelProperty(hidden = true)
        private long id;

        private String title;

        private String content;

        @ApiModelProperty(hidden = true)
        private String createdDate;

        @ApiModelProperty(hidden = true)
        private int viewCount;

        @ApiModelProperty(hidden = true)
        private int commentsCount;

        @ApiModelProperty(hidden = true)
        private String author;

        @ApiModelProperty(hidden = true)
        private List<CommentDto> comments;

        static PostDto of(Post post, boolean comments) {
            return PostDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .author(post.getAuthor().getUsername())
                    .createdDate(post.getFormattedCreateDate())
                    .viewCount(post.getViewCount())
                    .commentsCount(Math.toIntExact(post.getComments()
                            .stream()
                            .filter(comment -> !comment.isDeleted())
                            .count()))
                    .comments(comments ? post.getComments()
                            .stream()
                            .filter(comment -> !comment.isDeleted())
                            .map(CommentDto::of)
                            .sorted(comparing(CommentDto::getId).thenComparing(CommentDto::getCreatedDate))
                            .collect(toList()) : Collections.emptyList())
                    .build();
        }

        static PostDto withoutContent(Post post) {
            return PostDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content("")
                    .author(post.getAuthor().getUsername())
                    .createdDate(post.getFormattedCreateDate())
                    .viewCount(post.getViewCount())
                    .commentsCount(post.getComments().size())
                    .comments(Collections.emptyList())
                    .build();
        }
    }
}
