package com.xperiencehr.timetracking.adapter.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkHoursReportDTO {
    private String employeeName;
    private String projectName;
    private Double totalHours;
}
