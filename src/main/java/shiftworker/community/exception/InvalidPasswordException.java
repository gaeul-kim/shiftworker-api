package shiftworker.community.exception;

/**
 * @author sangsik.kim
 */
public class InvalidPasswordException extends RuntimeException {
    private static final String EXCEPTION_MESSAGE = "비밀번호의 길이가 올바르지 않습니다";

    public InvalidPasswordException() {
        super(EXCEPTION_MESSAGE);
    }
}
