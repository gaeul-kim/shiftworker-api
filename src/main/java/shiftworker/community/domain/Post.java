package shiftworker.community.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author sangsik.kim
 */
@Getter
@Entity
@NoArgsConstructor
public class Post extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @ManyToOne(targetEntity = User.class)
    private User author;

    private boolean deleted;

    public Post(String title, String content) {
        setTitle(title);
        setContent(content);
        setDeletedFalse();
    }

    public Post setAuthor(User author) {
        this.author = author;
        return this;
    }

    private void setTitle(String title) {
        if (StringUtils.isEmpty(title)) {
            throw new IllegalArgumentException("제목이 입력되지 않았습니다");
        }
        this.title = title;
    }

    private void setContent(String content) {
        if (StringUtils.isEmpty(content)) {
            throw new IllegalArgumentException("내용이 입력되지 않았습니다");
        }
        this.content = content;
    }

    private void setDeletedFalse() {
        this.deleted = false;
    }

}
