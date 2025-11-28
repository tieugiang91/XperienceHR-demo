package com.xperiencehr.timetracking.adapter.persistence;

import com.xperiencehr.timetracking.adapter.persistence.mapper.EmployeeMapper;
import com.xperiencehr.timetracking.adapter.persistence.repository.JpaEmployeeRepository;
import com.xperiencehr.timetracking.domain.model.Employee;
import com.xperiencehr.timetracking.domain.port.out.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmployeeRepositoryAdapter implements EmployeeRepository {

    private final JpaEmployeeRepository jpaRepository;
    private final EmployeeMapper mapper;

    @Override
    public Optional<Employee> findByName(String name) {
        return jpaRepository.findByName(name)
            .map(mapper::toDomain);
    }
}
