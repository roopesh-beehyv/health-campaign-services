CREATE INDEX IF NOT EXISTS idx_id ON PROJECT_BENEFICIARY (id);
CREATE INDEX IF NOT EXISTS idx_tenantId ON PROJECT_BENEFICIARY (tenantId);
CREATE INDEX IF NOT EXISTS idx_projectId ON PROJECT_BENEFICIARY (projectId);
CREATE INDEX IF NOT EXISTS idx_beneficiaryId ON PROJECT_BENEFICIARY (beneficiaryId);
CREATE INDEX IF NOT EXISTS idx_clientReferenceId ON PROJECT_BENEFICIARY (clientReferenceId);
CREATE INDEX IF NOT EXISTS idx_dateOfRegistration ON PROJECT_BENEFICIARY (dateOfRegistration);
