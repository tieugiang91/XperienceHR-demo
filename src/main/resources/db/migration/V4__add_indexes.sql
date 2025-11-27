-- Add indexes for query optimization
CREATE INDEX IF NOT EXISTS idx_time_record_time_from ON time_record(time_from);
CREATE INDEX IF NOT EXISTS idx_time_record_employee_id ON time_record(employee_id);
CREATE INDEX IF NOT EXISTS idx_time_record_project_id ON time_record(project_id);
