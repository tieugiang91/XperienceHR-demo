package com.xperiencehr.timetracking.adapter.persistence.mapper;

import com.xperiencehr.timetracking.adapter.persistence.entity.TimeRecordEntity;
import com.xperiencehr.timetracking.domain.model.TimeRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TimeRecordMapper {

    TimeRecord toDomain(TimeRecordEntity entity);

    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "project", ignore = true)
    TimeRecordEntity toEntity(TimeRecord domain);
}
