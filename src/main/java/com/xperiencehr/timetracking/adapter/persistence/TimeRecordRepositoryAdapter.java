package com.xperiencehr.timetracking.adapter.persistence;

import com.xperiencehr.timetracking.adapter.persistence.repository.JpaTimeRecordRepository;
import com.xperiencehr.timetracking.domain.model.WorkHoursReport;
import com.xperiencehr.timetracking.domain.port.TimeRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class TimeRecordRepositoryAdapter implements TimeRecordRepository {

    private final JpaTimeRecordRepository jpaRepository;

    @Override
    public List<WorkHoursReport> findWorkHoursReport(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Fetching work hours report from {} to {}", startDate, endDate);
        long startTime = System.currentTimeMillis();
        
        List<Object[]> results = jpaRepository.findWorkHoursReport(startDate, endDate);
        
        long executionTime = System.currentTimeMillis() - startTime;
        log.debug("Query executed in {} ms, returned {} records", executionTime, results.size());
        
        return results.stream()
                .map(row -> new WorkHoursReport(
                        (String) row[0],
                        (String) row[1],
                        ((Number) row[2]).doubleValue()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkHoursReport> findWorkHoursReportByEmployee(Long employeeId, LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Fetching work hours report for employee {} from {} to {}", employeeId, startDate, endDate);
        long startTime = System.currentTimeMillis();
        
        List<Object[]> results = jpaRepository.findWorkHoursReportByEmployee(employeeId, startDate, endDate);
        
        long executionTime = System.currentTimeMillis() - startTime;
        log.debug("Query executed in {} ms, returned {} records", executionTime, results.size());
        
        return results.stream()
                .map(row -> new WorkHoursReport(
                        (String) row[0],
                        (String) row[1],
                        ((Number) row[2]).doubleValue()
                ))
                .collect(Collectors.toList());
    }
}
