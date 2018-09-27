package shiftworker.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shiftworker.community.domain.Report;
import shiftworker.community.exception.DuplicatedReportException;
import shiftworker.community.repository.ReportRepository;

/**
 * @author sangsik.kim
 */
@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public void report(Report report) {
        reportRepository.findByPostAndUser(report.getPost(), report.getUser())
                .ifPresent(existReport -> {
                    throw new DuplicatedReportException();
                });
        reportRepository.save(report);
    }
}
