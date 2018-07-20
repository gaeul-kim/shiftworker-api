package shiftworker.community.exception;

/**
 * @author sangsik.kim
 */
public class PostNotFoundException extends RuntimeException {
    private static final String EXCEPTION_MESSAGE = "삭제되었거나 존재하지 않는 글입니다";

    public PostNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }
}
