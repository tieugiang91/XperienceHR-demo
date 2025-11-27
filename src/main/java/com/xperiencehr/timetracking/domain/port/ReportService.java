package com.xperiencehr.timetracking.domain.port;

import com.xperiencehr.timetracking.domain.model.WorkHoursReport;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {
    List<WorkHoursReport> generateReport(LocalDateTime startDate, LocalDateTime endDate, String username, boolean isAdmin);
}
