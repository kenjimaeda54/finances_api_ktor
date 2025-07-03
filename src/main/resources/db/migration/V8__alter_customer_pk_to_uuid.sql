ALTER TABLE customer ADD COLUMN uuid UUID DEFAULT gen_random_uuid() NOT NULL;

ALTER TABLE customer DROP CONSTRAINT customer_pkey;

ALTER TABLE customer ADD PRIMARY KEY (uuid);

ALTER TABLE customer DROP COLUMN id;
