package shiftworker.community.exception;

/**
 * @author sangsik.kim
 */
public class UnAuthenticationException extends RuntimeException {
    private static final String EXCEPTION_MESSAGE = "사용자 정보가 올바르지 않습니다";

    public UnAuthenticationException() {
        super(EXCEPTION_MESSAGE);
    }
}
