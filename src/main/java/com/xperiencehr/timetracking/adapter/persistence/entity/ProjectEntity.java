package com.xperiencehr.timetracking.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "project")
@Getter
@Setter
@NoArgsConstructor
public class ProjectEntity {
    @Id
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;
}
