package com.xperiencehr.timetracking.adapter.web.mapper;

import com.xperiencehr.timetracking.adapter.web.dto.WorkHoursReportDTO;
import com.xperiencehr.timetracking.adapter.web.dto.WorkHoursReportPageDTO;
import com.xperiencehr.timetracking.domain.model.PageResult;
import com.xperiencehr.timetracking.domain.model.WorkHoursReport;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WorkHoursReportMapper {

    WorkHoursReportDTO toDTO(WorkHoursReport domain);

    WorkHoursReport toDomain(WorkHoursReportDTO dto);

    List<WorkHoursReportDTO> toDTOList(List<WorkHoursReport> domainList);

    List<WorkHoursReport> toDomainList(List<WorkHoursReportDTO> dtoList);

    default WorkHoursReportPageDTO toPageDTO(PageResult<WorkHoursReport> pageResult) {
        if (pageResult == null) {
            return WorkHoursReportPageDTO.builder().build();
        }

        List<WorkHoursReport> domainContent = pageResult.getContent();
        List<WorkHoursReportDTO> dtoContent = domainContent == null
                ? List.of()
                : Objects.requireNonNullElse(toDTOList(domainContent), List.of());

        return WorkHoursReportPageDTO.builder()
                .content(dtoContent)
                .page(pageResult.getPage())
                .size(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .hasNext(pageResult.isHasNext())
                .hasPrevious(pageResult.isHasPrevious())
                .build();
    }
}
