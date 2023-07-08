CREATE TABLE LOAN
(
    id                character varying(64),
    tenantId          character varying(1000),
    type              character varying(200),
    name              character varying(250),
    interestRate      double precision,
    durationPeriod              character varying(250),
    provider      character varying(100),
    contact      character varying(100),
    additionalDetails text,
    createdBy         character varying(64),
    lastModifiedBy    character varying(64),
    createdTime       bigint,
    lastModifiedTime  bigint,
    rowVersion        bigint,
    isDeleted         boolean,
    CONSTRAINT uk_loan_id PRIMARY KEY (id)
);

CREATE TABLE LOAN_APPLICATION
(
    id                character varying(64),
    tenantId          character varying(1000),
    loanId            character varying(64),
    applicantId       character varying(64),
    tenure            int,
    hasConsented       boolean,
    defaultedMonths   character varying(8192),
    hasDefaulted      boolean,
    status            character varying(100),
    rejectionReason  character varying(1000),
    additionalDetails text,
    createdBy         character varying(64),
    lastModifiedBy    character varying(64),
    createdTime       bigint,
    lastModifiedTime  bigint,
    rowVersion        bigint,
    isDeleted         boolean,
    CONSTRAINT uk_loan_application_id PRIMARY KEY (id)
);