package com.xperiencehr.timetracking.adapter.web.controller;

import com.xperiencehr.timetracking.adapter.web.dto.WorkHoursReportDTO;
import com.xperiencehr.timetracking.adapter.web.dto.WorkHoursReportPageDTO;
import com.xperiencehr.timetracking.adapter.web.mapper.WorkHoursReportMapper;
import com.xperiencehr.timetracking.domain.model.PageResult;
import com.xperiencehr.timetracking.domain.model.WorkHoursReport;
import com.xperiencehr.timetracking.domain.port.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    private final ReportService reportService;
    private final WorkHoursReportMapper reportMapper;

    @GetMapping("/report")
    public String showReport(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Authentication authentication,
            Model model) {

        log.info("Report request received from user: {}", authentication.getName());

        if (startDate == null || endDate == null) {
            LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
            startDate = LocalDateTime.of(LocalDate.now().minusMonths(1), LocalTime.MIN);
            endDate = now;
            log.debug("Using default date range: {} to {}", startDate, endDate);
        } else {
            startDate = startDate.withSecond(0).withNano(0);
            endDate = endDate.withSecond(0).withNano(0);
        }

        boolean isAdmin = authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        int sanitizedPage = Math.max(page, 0);
        int sanitizedSize = size < 1 ? 20 : Math.min(size, 100);

        PageResult<WorkHoursReport> reportResult = reportService.generateReport(
                startDate, endDate, authentication.getName(), isAdmin, sanitizedPage, sanitizedSize);

        WorkHoursReportPageDTO reportPage = reportMapper.toPageDTO(reportResult);
        List<WorkHoursReportDTO> reportData = reportPage.getContent();

        DateTimeFormatter dateTimeInputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        model.addAttribute("reportData", reportData);
        model.addAttribute("reportPage", reportPage);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("startDateInput", startDate.format(dateTimeInputFormatter));
        model.addAttribute("endDateInput", endDate.format(dateTimeInputFormatter));
        model.addAttribute("username", authentication.getName());
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("page", reportPage.getPage());
        model.addAttribute("size", reportPage.getSize());
        model.addAttribute("totalPages", reportPage.getTotalPages());
        model.addAttribute("totalRecords", reportPage.getTotalElements());
        model.addAttribute("pageSizeOptions", List.of(10, 20, 50, 100));

        log.info("Report generated successfully with {} records (page {}/{})", reportData.size(),
                reportPage.getPage() + 1, Math.max(reportPage.getTotalPages(), 1));

        return "work_hours_report";
    }
}
