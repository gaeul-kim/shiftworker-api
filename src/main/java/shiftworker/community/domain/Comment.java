package shiftworker.community.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author sangsik.kim
 */
@Getter
@Entity
@Builder
@AllArgsConstructor
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
}
