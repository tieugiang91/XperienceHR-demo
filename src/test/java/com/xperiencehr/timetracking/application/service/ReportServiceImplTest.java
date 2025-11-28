package com.xperiencehr.timetracking.application.service;

import com.xperiencehr.timetracking.adapter.persistence.repository.JpaEmployeeRepository;
import com.xperiencehr.timetracking.domain.model.WorkHoursReport;
import com.xperiencehr.timetracking.domain.port.TimeRecordRepository;
import com.xperiencehr.timetracking.adapter.persistence.entity.EmployeeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
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
    private JpaEmployeeRepository employeeRepository;

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
        List<WorkHoursReport> expected = List.of(new WorkHoursReport("Alice", "Project X", 40.0));
        when(timeRecordRepository.findWorkHoursReport(startDate, endDate)).thenReturn(expected);

        List<WorkHoursReport> actual = reportService.generateReport(startDate, endDate, "admin", true);

        assertThat(actual).isEqualTo(expected);
        verify(timeRecordRepository).findWorkHoursReport(startDate, endDate);
    }

    @Test
    void generateReport_returnsEmployeeResultsWhenUserFound() {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setId(42L);
        employee.setName("Bob");
        when(employeeRepository.findByName("Bob")).thenReturn(Optional.of(employee));
        List<WorkHoursReport> expected = List.of(new WorkHoursReport("Bob", "Project Y", 12.5));
        when(timeRecordRepository.findWorkHoursReportByEmployee(42L, startDate, endDate)).thenReturn(expected);

        List<WorkHoursReport> actual = reportService.generateReport(startDate, endDate, "Bob", false);

        assertThat(actual).isEqualTo(expected);
        verify(timeRecordRepository).findWorkHoursReportByEmployee(42L, startDate, endDate);
    }

    @Test
    void generateReport_returnsEmptyListWhenEmployeeMissing() {
        when(employeeRepository.findByName("Charlie")).thenReturn(Optional.empty());

        List<WorkHoursReport> actual = reportService.generateReport(startDate, endDate, "Charlie", false);

        assertThat(actual).isEmpty();
        verify(employeeRepository).findByName("Charlie");
    }
}
