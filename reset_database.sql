
DROP TABLE IF EXISTS time_record CASCADE;
DROP TABLE IF EXISTS employee CASCADE;
DROP TABLE IF EXISTS project CASCADE;

DROP TABLE IF EXISTS flyway_schema_history CASCADE;

\echo 'Database reset complete. All tables dropped.'
\echo 'Flyway will run all migrations: V1 (schema), V2 (sample data), V3 (large dataset), V4 (indexes)'
