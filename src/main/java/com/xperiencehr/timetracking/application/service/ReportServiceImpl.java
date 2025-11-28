package com.xperiencehr.timetracking.application.service;

import com.xperiencehr.timetracking.domain.model.PageResult;
import com.xperiencehr.timetracking.domain.model.WorkHoursReport;
import com.xperiencehr.timetracking.domain.port.EmployeeRepository;
import com.xperiencehr.timetracking.domain.port.ReportService;
import com.xperiencehr.timetracking.domain.port.TimeRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final TimeRecordRepository timeRecordRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public PageResult<WorkHoursReport> generateReport(LocalDateTime startDate,
                                                     LocalDateTime endDate,
                                                     String username,
                                                     boolean isAdmin,
                                                     int page,
                                                     int size) {
        log.info("Generating report for user: {}, isAdmin: {}, period: {} to {}, page: {}, size: {}",
                username, isAdmin, startDate, endDate, page, size);

        if (isAdmin) {
            log.debug("Admin access - fetching all employee records");
            return timeRecordRepository.findWorkHoursReport(startDate, endDate, page, size);
        }

        log.debug("Employee access - fetching records for: {}", username);
        return employeeRepository.findByName(username)
                .map(employee -> timeRecordRepository.findWorkHoursReportByEmployee(
                        employee.getId(), startDate, endDate, page, size))
                .orElseGet(() -> {
                    log.warn("Employee not found: {}", username);
                    return PageResult.<WorkHoursReport>builder()
                            .content(Collections.emptyList())
                            .page(page)
                            .size(size)
                            .totalElements(0)
                            .totalPages(0)
                            .build();
                });
    }
}
