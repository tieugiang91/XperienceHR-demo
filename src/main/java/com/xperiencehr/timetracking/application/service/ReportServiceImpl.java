package com.xperiencehr.timetracking.application.service;

import com.xperiencehr.timetracking.adapter.persistence.repository.JpaEmployeeRepository;
import com.xperiencehr.timetracking.domain.model.WorkHoursReport;
import com.xperiencehr.timetracking.domain.port.ReportService;
import com.xperiencehr.timetracking.domain.port.TimeRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final TimeRecordRepository timeRecordRepository;
    private final JpaEmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<WorkHoursReport> generateReport(LocalDateTime startDate, LocalDateTime endDate, 
                                                 String username, boolean isAdmin) {
        log.info("Generating report for user: {}, isAdmin: {}, period: {} to {}", 
                 username, isAdmin, startDate, endDate);

        if (isAdmin) {
            log.debug("Admin access - fetching all employee records");
            return timeRecordRepository.findWorkHoursReport(startDate, endDate);
        } else {
            log.debug("Employee access - fetching records for: {}", username);
            return employeeRepository.findByName(username)
                    .map(employee -> timeRecordRepository.findWorkHoursReportByEmployee(
                            employee.getId(), startDate, endDate))
                    .orElseGet(() -> {
                        log.warn("Employee not found: {}", username);
                        return List.of();
                    });
        }
    }
}
