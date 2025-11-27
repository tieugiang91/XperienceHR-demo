DROP TABLE IF EXISTS project;
CREATE TABLE project (
    id INT8 PRIMARY KEY,
    name VARCHAR(200)
);

DROP TABLE IF EXISTS employee;
CREATE TABLE employee (
    id INT8 PRIMARY KEY,
    name VARCHAR(60)
);

DROP TABLE IF EXISTS time_record;
CREATE TABLE time_record (
    id INT8 PRIMARY KEY,
    employee_id INT8 NOT NULL,
    project_id INT8 NOT NULL,
    time_from TIMESTAMP NOT NULL,
    time_to TIMESTAMP NOT NULL
);
