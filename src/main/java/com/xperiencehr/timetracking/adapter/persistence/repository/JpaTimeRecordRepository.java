package com.xperiencehr.timetracking.adapter.persistence.repository;

import com.xperiencehr.timetracking.adapter.persistence.entity.TimeRecordEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JpaTimeRecordRepository extends JpaRepository<TimeRecordEntity, Long> {

    @Query(value = "SELECT e.name AS employee_name, p.name AS project_name, " +
           "SUM(EXTRACT(EPOCH FROM (t.time_to - t.time_from)) / 3600) AS total_hours " +
           "FROM time_record t " +
           "JOIN employee e ON t.employee_id = e.id " +
           "JOIN project p ON t.project_id = p.id " +
           "WHERE t.time_from >= :startDate AND t.time_from <= :endDate " +
           "GROUP BY e.name, p.name " +
           "ORDER BY e.name, p.name",
           countQuery = "SELECT COUNT(*) FROM (" +
                   "SELECT e.name, p.name " +
                   "FROM time_record t " +
                   "JOIN employee e ON t.employee_id = e.id " +
                   "JOIN project p ON t.project_id = p.id " +
                   "WHERE t.time_from >= :startDate AND t.time_from <= :endDate " +
                   "GROUP BY e.name, p.name" +
                   ") sub",
           nativeQuery = true)
    Page<Object[]> findWorkHoursReport(@Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate,
                                       Pageable pageable);

    @Query(value = "SELECT e.name AS employee_name, p.name AS project_name, " +
           "SUM(EXTRACT(EPOCH FROM (t.time_to - t.time_from)) / 3600) AS total_hours " +
           "FROM time_record t " +
           "JOIN employee e ON t.employee_id = e.id " +
           "JOIN project p ON t.project_id = p.id " +
           "WHERE t.employee_id = :employeeId " +
           "AND t.time_from >= :startDate AND t.time_from <= :endDate " +
           "GROUP BY e.name, p.name " +
           "ORDER BY e.name, p.name",
           countQuery = "SELECT COUNT(*) FROM (" +
                   "SELECT e.name, p.name " +
                   "FROM time_record t " +
                   "JOIN employee e ON t.employee_id = e.id " +
                   "JOIN project p ON t.project_id = p.id " +
                   "WHERE t.employee_id = :employeeId " +
                   "AND t.time_from >= :startDate AND t.time_from <= :endDate " +
                   "GROUP BY e.name, p.name" +
                   ") sub",
           nativeQuery = true)
    Page<Object[]> findWorkHoursReportByEmployee(@Param("employeeId") Long employeeId,
                                                  @Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate,
                                                  Pageable pageable);
}
