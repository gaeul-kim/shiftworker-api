package shiftworker.community.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @ManyToOne(targetEntity = User.class)
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
}
