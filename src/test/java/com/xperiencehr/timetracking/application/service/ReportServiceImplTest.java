package com.xperiencehr.timetracking.application.service;

import com.xperiencehr.timetracking.domain.model.Employee;
import com.xperiencehr.timetracking.domain.model.PageResult;
import com.xperiencehr.timetracking.domain.model.WorkHoursReport;
import com.xperiencehr.timetracking.domain.port.out.EmployeeRepository;
import com.xperiencehr.timetracking.domain.port.out.TimeRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @Mock
    private TimeRecordRepository timeRecordRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @BeforeEach
    void setUp() {
        startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        endDate = LocalDateTime.of(2024, 1, 31, 23, 59);
    }

    @Test
    void generateReport_returnsAllResultsForAdmin() {
        PageResult<WorkHoursReport> expected = PageResult.<WorkHoursReport>builder()
                .content(List.of(new WorkHoursReport("Alice", "Project X", 40.0)))
                .page(0)
                .size(20)
                .totalElements(1)
                .totalPages(1)
                .build();
        when(timeRecordRepository.findWorkHoursReport(startDate, endDate, 0, 20)).thenReturn(expected);

        PageResult<WorkHoursReport> actual = reportService.generateReport(startDate, endDate, "admin", true, 0, 20);

        assertThat(actual).isEqualTo(expected);
        verify(timeRecordRepository).findWorkHoursReport(startDate, endDate, 0, 20);
    }

    @Test
    void generateReport_returnsEmployeeResultsWhenUserFound() {
        Employee employee = new Employee(42L, "Bob");
        when(employeeRepository.findByName("Bob")).thenReturn(Optional.of(employee));
        PageResult<WorkHoursReport> expected = PageResult.<WorkHoursReport>builder()
                .content(List.of(new WorkHoursReport("Bob", "Project Y", 12.5)))
                .page(1)
                .size(10)
                .totalElements(5)
                .totalPages(1)
                .build();
        when(timeRecordRepository.findWorkHoursReportByEmployee(42L, startDate, endDate, 1, 10)).thenReturn(expected);

        PageResult<WorkHoursReport> actual = reportService.generateReport(startDate, endDate, "Bob", false, 1, 10);

        assertThat(actual).isEqualTo(expected);
        verify(timeRecordRepository).findWorkHoursReportByEmployee(42L, startDate, endDate, 1, 10);
    }

    @Test
    void generateReport_returnsEmptyListWhenEmployeeMissing() {
        when(employeeRepository.findByName("Charlie")).thenReturn(Optional.empty());

        PageResult<WorkHoursReport> actual = reportService.generateReport(startDate, endDate, "Charlie", false, 0, 15);

        assertThat(actual.getContent()).isEmpty();
        assertThat(actual.getTotalElements()).isZero();
        assertThat(actual.getPage()).isZero();
        assertThat(actual.getSize()).isEqualTo(15);
        verify(employeeRepository).findByName("Charlie");
    }
}
