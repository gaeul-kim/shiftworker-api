package shiftworker.community.exception;

/**
 * @author sangsik.kim
 */
public class DuplicatedReportException extends RuntimeException {
    private static final String EXCEPTION_MESSAGE = "이미 신고한 게시물입니다";
    
    public DuplicatedReportException() {
        super(EXCEPTION_MESSAGE);
    }
}
