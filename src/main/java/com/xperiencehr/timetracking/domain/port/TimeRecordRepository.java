package com.xperiencehr.timetracking.domain.port;

import com.xperiencehr.timetracking.domain.model.WorkHoursReport;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeRecordRepository {
    List<WorkHoursReport> findWorkHoursReport(LocalDateTime startDate, LocalDateTime endDate);
    List<WorkHoursReport> findWorkHoursReportByEmployee(Long employeeId, LocalDateTime startDate, LocalDateTime endDate);
}
