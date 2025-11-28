package com.xperiencehr.timetracking.adapter.web.mapper;

import com.xperiencehr.timetracking.adapter.web.dto.WorkHoursReportDTO;
import com.xperiencehr.timetracking.domain.model.WorkHoursReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WorkHoursReportMapperTest {

    private WorkHoursReportMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(WorkHoursReportMapper.class);
    }

    @Test
    void toDTO_convertsDomainToDTO() {
        WorkHoursReport domain = new WorkHoursReport("Alice", "Project Alpha", 40.5);

        WorkHoursReportDTO dto = mapper.toDTO(domain);

        assertThat(dto).isNotNull();
        assertThat(dto.getEmployeeName()).isEqualTo("Alice");
        assertThat(dto.getProjectName()).isEqualTo("Project Alpha");
        assertThat(dto.getTotalHours()).isEqualTo(40.5);
    }

    @Test
    void toDTO_returnsNullWhenDomainIsNull() {
        WorkHoursReportDTO dto = mapper.toDTO(null);

        assertThat(dto).isNull();
    }

    @Test
    void toDomain_convertsDTOToDomain() {
        WorkHoursReportDTO dto = new WorkHoursReportDTO("Bob", "Project Beta", 25.0);

        WorkHoursReport domain = mapper.toDomain(dto);

        assertThat(domain).isNotNull();
        assertThat(domain.getEmployeeName()).isEqualTo("Bob");
        assertThat(domain.getProjectName()).isEqualTo("Project Beta");
        assertThat(domain.getTotalHours()).isEqualTo(25.0);
    }

    @Test
    void toDomain_returnsNullWhenDTOIsNull() {
        WorkHoursReport domain = mapper.toDomain(null);

        assertThat(domain).isNull();
    }

    @Test
    void toDTOList_convertsListOfDomainToListOfDTO() {
        List<WorkHoursReport> domainList = List.of(
            new WorkHoursReport("Alice", "Project Alpha", 40.5),
            new WorkHoursReport("Bob", "Project Beta", 25.0)
        );

        List<WorkHoursReportDTO> dtoList = mapper.toDTOList(domainList);

        assertThat(dtoList).hasSize(2);
        assertThat(dtoList.get(0).getEmployeeName()).isEqualTo("Alice");
        assertThat(dtoList.get(1).getEmployeeName()).isEqualTo("Bob");
    }

    @Test
    void toDTOList_returnsNullWhenInputIsNull() {
        List<WorkHoursReportDTO> dtoList = mapper.toDTOList(null);

        assertThat(dtoList).isNull();
    }
}
