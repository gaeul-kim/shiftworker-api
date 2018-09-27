package shiftworker.community.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;
import shiftworker.community.exception.UnAuthenticationException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(optional = false)
    private User author;

    @OneToMany(mappedBy = "post")
    @Where(clause = "deleted = 'false'")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Report> reports = new ArrayList<>();

    private boolean deleted;

    private int viewCount;

    private Post(String title, String content) {
        setTitle(title);
        setContent(content);
        this.deleted = false;
    }

    public static Post of(String title, String content) {
        return new Post(title, content);
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

    public Post increaseViewCount() {
        this.viewCount++;
        return this;
    }

    public void delete(User user) {
        checkAuthentication(user);
        this.deleted = true;
    }

    public Post update(Post post, User user) {
        checkAuthentication(user);
        setTitle(post.getTitle());
        setContent(post.getContent());
        return this;
    }

    private void checkAuthentication(User user) {
        if (!this.author.matchUsername(user)) {
            throw new UnAuthenticationException();
        }
    }
}
