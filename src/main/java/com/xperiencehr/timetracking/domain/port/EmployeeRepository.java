package com.xperiencehr.timetracking.domain.port;

import com.xperiencehr.timetracking.domain.model.Employee;

import java.util.Optional;

public interface EmployeeRepository {
    Optional<Employee> findByName(String name);
}
