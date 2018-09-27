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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shiftworker.community.annotation.LoginUser;
import shiftworker.community.domain.Comment;
import shiftworker.community.domain.User;
import shiftworker.community.service.CommentService;
import shiftworker.community.service.PostService;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * @author sangsik.kim
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @GetMapping
    public List<CommentDto> getComments(@PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                                        @RequestParam long postId) {
        return commentService.getAllByPostId(postId, pageable)
                .stream()
                .map(CommentDto::of)
                .sorted(comparing(CommentDto::getId).thenComparing(CommentDto::getCreatedDate))
                .collect(toList());
    }

    @PostMapping
    @ApiImplicitParam(name = "Authorization", value = "사용자 인증 토큰", required = true, dataType = "String", paramType = "header")
    public CommentDto addComment(@LoginUser User user, @RequestBody CommentDto commentDto) {
        return CommentDto.of(commentService.add(Comment.of(postService.getById(commentDto.getPostId()), user, commentDto.getContent())));
    }

    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "Authorization", value = "사용자 인증 토큰", required = true, dataType = "String", paramType = "header")
    public HttpStatus deleteComment(@LoginUser User user, @PathVariable long id) {
        commentService.delete(id, user);
        return HttpStatus.NO_CONTENT;
    }

    @PutMapping("/{id}")
    @ApiImplicitParam(name = "Authorization", value = "사용자 인증 토큰", required = true, dataType = "String", paramType = "header")
    public CommentDto updateComment(@LoginUser User user, @PathVariable long id, @RequestBody CommentUpdateDto commentUpdateDto) {
        return CommentDto.of(commentService.update(id, commentUpdateDto.getContent(), user));
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentDto {

        @ApiModelProperty(hidden = true)
        private long id;

        private long postId;

        private String content;

        @ApiModelProperty(hidden = true)
        private String createdDate;

        @ApiModelProperty(hidden = true)
        private String author;

        static CommentDto of(Comment comment) {
            return CommentDto.builder()
                    .id(comment.getId())
                    .postId(comment.getPost().getId())
                    .content(comment.getContent())
                    .author(comment.getAuthor().getUsername())
                    .createdDate(comment.getFormattedCreateDate())
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CommentUpdateDto {
        private String content;
    }
}
