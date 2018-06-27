package shiftworker.community.exception;

/**
 * @author sangsik.kim
 */
public class InvalidUsernameException extends RuntimeException {
    private static final String EXCEPTION_MESSAGE = "아이디의 길이가 올바르지 않습니다";

    public InvalidUsernameException() {
        super(EXCEPTION_MESSAGE);
    }
}
