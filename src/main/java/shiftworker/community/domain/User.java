package shiftworker.community.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import shiftworker.community.exception.InvalidPasswordException;
import shiftworker.community.exception.InvalidUsernameException;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author sangsik.kim
 */
@Getter
@Entity
@NoArgsConstructor
public class User extends BaseEntity {
    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 20;
    private static final int MIN_PASSWORD_LENGTH = 10;
    private static final int MAX_PASSWORD_LENGTH = 20;

    @Column(unique = true, nullable = false, length = 20)
    private String username;

    @JsonIgnore
    @Column(nullable = false, length = 100)
    private String password;

    @Builder
    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    private void setUsername(String username) {
        validationUsername(username);
        this.username = username.toLowerCase();
    }

    private void setPassword(String password) {
        validationPassword(password);
        this.password = password;
    }

    private void validationUsername(String username) {
        if (username.length() > MAX_USERNAME_LENGTH || username.length() < MIN_USERNAME_LENGTH) {
            throw new InvalidUsernameException();
        }
    }

    private void validationPassword(String password) {
        if (password.length() > MAX_PASSWORD_LENGTH || password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidPasswordException();
        }
    }

    public boolean matchId(long id) {
        return this.id == id;
    }

    public boolean matchPassword(String password) {
        return BCrypt.checkpw(password, this.password);
    }

    public void hashPassword() {
        this.password = BCrypt.hashpw(this.password, BCrypt.gensalt());
    }
}
