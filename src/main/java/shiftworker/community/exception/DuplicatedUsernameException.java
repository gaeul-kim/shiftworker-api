package shiftworker.community.exception;

/**
 * @author sangsik.kim
 */
public class DuplicatedUsernameException extends RuntimeException {
    private static final String EXCEPTION_MESSAGE = "이미 사용중인 아이디입니다";

    public DuplicatedUsernameException() {
        super(EXCEPTION_MESSAGE);
    }
}
