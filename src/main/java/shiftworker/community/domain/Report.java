package shiftworker.community.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shiftworker.community.controller.ReportController;
import shiftworker.community.domain.type.ReportReason;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

/**
 * @author sangsik.kim
 */
@Getter
@Entity
@NoArgsConstructor
public class Report extends BaseEntity {

    @ManyToOne(optional = false)
    private Post post;

    @ManyToOne(optional = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private ReportReason reason;

    private String description;


    public boolean matchUser(User user) {
        return this.user.matchId(user.getId());
    }

    private Report(Post post, User user, ReportReason reason, String description) {
        this.post = post;
        this.user = user;
        this.reason = reason;
        this.description = description;
    }

    public static Report of(Post post, User user, ReportController.ReportDto reportDto) {
        return new Report(post, user, reportDto.getReason(), reportDto.getDescription());
    }
}
