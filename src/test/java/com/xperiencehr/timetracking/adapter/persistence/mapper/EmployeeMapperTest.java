package com.xperiencehr.timetracking.adapter.persistence.mapper;

import com.xperiencehr.timetracking.adapter.persistence.entity.EmployeeEntity;
import com.xperiencehr.timetracking.domain.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeMapperTest {

    private EmployeeMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(EmployeeMapper.class);
    }

    @Test
    void toDomain_convertsEntityToDomain() {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setId(1L);
        entity.setName("John Doe");

        Employee domain = mapper.toDomain(entity);

        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(1L);
        assertThat(domain.getName()).isEqualTo("John Doe");
    }

    @Test
    void toDomain_returnsNullWhenEntityIsNull() {
        Employee domain = mapper.toDomain(null);

        assertThat(domain).isNull();
    }

    @Test
    void toEntity_convertsDomainToEntity() {
        Employee domain = new Employee(2L, "Jane Smith");

        EmployeeEntity entity = mapper.toEntity(domain);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(2L);
        assertThat(entity.getName()).isEqualTo("Jane Smith");
    }

    @Test
    void toEntity_returnsNullWhenDomainIsNull() {
        EmployeeEntity entity = mapper.toEntity(null);

        assertThat(entity).isNull();
    }
}
