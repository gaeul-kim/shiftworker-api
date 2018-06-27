package shiftworker.community.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author sangsik.kim
 */
@Getter(AccessLevel.PUBLIC)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    private static final String DEFAULT_DATE_FORMAT = "yyyy.MM.dd HH:mm:ss";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @CreatedDate
    private LocalDateTime createdDate;

    @JsonIgnore
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @JsonIgnore
    public String getFormattedCreateDate() {
        return getFormattedDate(this.createdDate);
    }

    @JsonIgnore
    public String getFormattedModifiedDate() {
        return getFormattedDate(this.modifiedDate);
    }

    private String getFormattedDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
    }
}
