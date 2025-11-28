package com.xperiencehr.timetracking.domain.port;

import com.xperiencehr.timetracking.domain.model.PageResult;
import com.xperiencehr.timetracking.domain.model.WorkHoursReport;

import java.time.LocalDateTime;

public interface ReportService {
    PageResult<WorkHoursReport> generateReport(LocalDateTime startDate,
                                              LocalDateTime endDate,
                                              String username,
                                              boolean isAdmin,
                                              int page,
                                              int size);
}
