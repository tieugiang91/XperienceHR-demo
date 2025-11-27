INSERT INTO employee (id, name)
SELECT i, 'Employee ' || i
FROM generate_series(1, 1000) AS i
ON CONFLICT (id) DO NOTHING;

INSERT INTO project (id, name)
SELECT i, 'Project ' || i
FROM generate_series(1, 100) AS i
ON CONFLICT (id) DO NOTHING;

INSERT INTO time_record (id, employee_id, project_id, time_from, time_to)
SELECT
    i,
    floor(random() * 1000 + 1)::int8,
    floor(random() * 100 + 1)::int8,
    ts,
    ts + (INTERVAL '1 hour' + random() * INTERVAL '8 hours')
FROM generate_series(1, 100000) AS i,
LATERAL (SELECT NOW() - (random() * INTERVAL '45 days') AS ts) t
ON CONFLICT (id) DO NOTHING;
