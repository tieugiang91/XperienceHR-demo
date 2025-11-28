package com.xperiencehr.timetracking.adapter.persistence.mapper;

import com.xperiencehr.timetracking.adapter.persistence.entity.ProjectEntity;
import com.xperiencehr.timetracking.domain.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectMapper {

    Project toDomain(ProjectEntity entity);

    ProjectEntity toEntity(Project domain);
}
