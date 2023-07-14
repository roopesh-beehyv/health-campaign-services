CREATE TABLE eg_sch_registration(
  id character varying(64),
  tenantId character varying(64),
  applicationNumber character varying(64),
  name character varying(64),
  land character varying(64),
  createdBy character varying(64),
  lastModifiedBy character varying(64),
  createdTime bigint,
  lastModifiedTime bigint,
 CONSTRAINT uk_eg_bt_registration UNIQUE (id)
);
