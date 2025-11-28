package com.xperiencehr.timetracking.adapter.persistence;

import com.xperiencehr.timetracking.adapter.persistence.repository.JpaTimeRecordRepository;
import com.xperiencehr.timetracking.domain.model.PageResult;
import com.xperiencehr.timetracking.domain.model.WorkHoursReport;
import com.xperiencehr.timetracking.domain.port.out.TimeRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class TimeRecordRepositoryAdapter implements TimeRecordRepository {

    private final JpaTimeRecordRepository jpaRepository;

    @Override
    public PageResult<WorkHoursReport> findWorkHoursReport(LocalDateTime startDate,
                                                          LocalDateTime endDate,
                                                          int page,
                                                          int size) {
        log.debug("Fetching work hours report from {} to {} (page={}, size={})", startDate, endDate, page, size);
        long startTime = System.currentTimeMillis();

        Page<Object[]> results = jpaRepository.findWorkHoursReport(startDate, endDate, PageRequest.of(page, size));

        long executionTime = System.currentTimeMillis() - startTime;
        log.debug("Query executed in {} ms, returned {} records (total elements: {})", executionTime,
                results.getNumberOfElements(), results.getTotalElements());

        List<WorkHoursReport> reports = results.getContent().stream()
                .map(row -> new WorkHoursReport(
                        (String) row[0],
                        (String) row[1],
                        ((Number) row[2]).doubleValue()
                ))
                .collect(Collectors.toList());

        return PageResult.<WorkHoursReport>builder()
                .content(reports)
                .page(results.getNumber())
                .size(results.getSize())
                .totalElements(results.getTotalElements())
                .totalPages(results.getTotalPages())
                .build();
    }

    @Override
    public PageResult<WorkHoursReport> findWorkHoursReportByEmployee(Long employeeId,
                                                                    LocalDateTime startDate,
                                                                    LocalDateTime endDate,
                                                                    int page,
                                                                    int size) {
        log.debug("Fetching work hours report for employee {} from {} to {} (page={}, size={})", employeeId, startDate, endDate, page, size);
        long startTime = System.currentTimeMillis();

        Page<Object[]> results = jpaRepository.findWorkHoursReportByEmployee(employeeId, startDate, endDate, PageRequest.of(page, size));

        long executionTime = System.currentTimeMillis() - startTime;
        log.debug("Query executed in {} ms, returned {} records (total elements: {})", executionTime,
                results.getNumberOfElements(), results.getTotalElements());

        List<WorkHoursReport> reports = results.getContent().stream()
                .map(row -> new WorkHoursReport(
                        (String) row[0],
                        (String) row[1],
                        ((Number) row[2]).doubleValue()
                ))
                .collect(Collectors.toList());

        return PageResult.<WorkHoursReport>builder()
                .content(reports)
                .page(results.getNumber())
                .size(results.getSize())
                .totalElements(results.getTotalElements())
                .totalPages(results.getTotalPages())
                .build();
    }
}
