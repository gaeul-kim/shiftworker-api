package shiftworker.community.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import shiftworker.community.exception.UnAuthenticationException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author sangsik.kim
 */
@Getter
@Entity
@NoArgsConstructor
public class Comment extends BaseEntity {

    @JsonIgnore
    @ManyToOne(optional = false)
    private Post post;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @ManyToOne(optional = false)
    private User author;

    private boolean deleted;

    private Comment(Post post, User author, String content) {
        this.post = post;
        this.author = author;
        this.deleted = false;
        setContent(content);
    }

    public static Comment of(Post post, User author, String content) {
        return new Comment(post, author, content);
    }

    private void setContent(String content) {
        if (StringUtils.isEmpty(content)) {
            throw new IllegalArgumentException("내용이 입력되지 않았습니다");
        }
        this.content = content;
    }

    public void delete(User user) {
        checkAuthentication(user);
        this.deleted = true;
    }

    public Comment update(String content, User user) {
        checkAuthentication(user);
        setContent(content);
        return this;
    }

    private void checkAuthentication(User user) {
        if (!this.author.matchUsername(user)) {
            throw new UnAuthenticationException();
        }
    }
}
