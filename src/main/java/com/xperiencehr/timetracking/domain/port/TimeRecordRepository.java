package com.xperiencehr.timetracking.domain.port;

import com.xperiencehr.timetracking.domain.model.PageResult;
import com.xperiencehr.timetracking.domain.model.WorkHoursReport;

import java.time.LocalDateTime;

public interface TimeRecordRepository {
    PageResult<WorkHoursReport> findWorkHoursReport(LocalDateTime startDate, LocalDateTime endDate, int page, int size);

    PageResult<WorkHoursReport> findWorkHoursReportByEmployee(Long employeeId,
                                                             LocalDateTime startDate,
                                                             LocalDateTime endDate,
                                                             int page,
                                                             int size);
}
