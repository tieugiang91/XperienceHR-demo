package com.xperiencehr.timetracking.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeRecord {
    private Long id;
    private Long employeeId;
    private Long projectId;
    private LocalDateTime timeFrom;
    private LocalDateTime timeTo;

    public double calculateHours() {
        long seconds = java.time.Duration.between(timeFrom, timeTo).getSeconds();
        return seconds / 3600.0;
    }
}
