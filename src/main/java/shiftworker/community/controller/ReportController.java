package shiftworker.community.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shiftworker.community.annotation.LoginUser;
import shiftworker.community.domain.Report;
import shiftworker.community.domain.User;
import shiftworker.community.domain.type.ReportReason;
import shiftworker.community.service.PostService;
import shiftworker.community.service.ReportService;

/**
 * @author sangsik.kim
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final PostService postService;
    private final ReportService reportService;

    @PatchMapping
    public HttpStatus reportPost(@LoginUser User user, @RequestBody ReportDto reportDto) {
        reportService.report(Report.of(postService.getById(reportDto.getPostId()), user, reportDto));
        return HttpStatus.OK;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReportDto {
        private long postId;
        private ReportReason reason;
        private String description;
    }
}
