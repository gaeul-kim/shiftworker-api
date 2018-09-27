package shiftworker.community.exception;

/**
 * @author sangsik.kim
 */
public class CommentNotFoundException extends RuntimeException {
    private static final String EXCEPTION_MESSAGE = "삭제되었거나 존재하지 않는 댓글입니다";

    public CommentNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }
}
