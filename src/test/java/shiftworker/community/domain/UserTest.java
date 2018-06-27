package shiftworker.community.domain;

import org.junit.Test;
import shiftworker.community.exception.InvalidPasswordException;
import shiftworker.community.exception.InvalidUsernameException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author sangsik.kim
 */
public class UserTest {

    @Test
    public void 사용자이름_대문자_입력() {
        User user = new User("SangSik", "testPassword");

        assertThat(user.getUsername(), is("sangsik"));
    }

    @Test(expected = InvalidUsernameException.class)
    public void 사용자이름_5글자_미만() {
        new User("abcd", "testPassword");
    }

    @Test(expected = InvalidUsernameException.class)
    public void 사용자이름_20글자_초과() {
        new User("aaaaabbbbbcccccddddde", "testPassword");
    }

    @Test(expected = InvalidPasswordException.class)
    public void 비밀번호_10글자_미만() {
        new User("sangsik", "test");
    }

    @Test(expected = InvalidPasswordException.class)
    public void 비밀번호_20글자_초과() {
        new User("sangsik", "testPassword1234567890");
    }

    @Test
    public void 비밀번호_확인() {
        User user = new User("sangsik", "testPassword");

        user.hashPassword();

        assertThat(user.matchPassword("testPassword"), is(true));
    }
}
