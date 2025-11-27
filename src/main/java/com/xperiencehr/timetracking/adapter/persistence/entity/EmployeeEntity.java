package com.xperiencehr.timetracking.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
public class EmployeeEntity {
    @Id
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;
}
