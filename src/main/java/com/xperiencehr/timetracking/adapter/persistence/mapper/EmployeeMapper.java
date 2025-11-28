package com.xperiencehr.timetracking.adapter.persistence.mapper;

import com.xperiencehr.timetracking.adapter.persistence.entity.EmployeeEntity;
import com.xperiencehr.timetracking.domain.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {

    Employee toDomain(EmployeeEntity entity);

    EmployeeEntity toEntity(Employee domain);
}
