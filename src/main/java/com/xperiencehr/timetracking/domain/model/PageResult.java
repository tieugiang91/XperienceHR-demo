package com.xperiencehr.timetracking.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResult<T> {

    @Builder.Default
    private List<T> content = Collections.emptyList();
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public boolean isHasNext() {
        return page + 1 < totalPages;
    }

    public boolean isHasPrevious() {
        return page > 0;
    }
}
