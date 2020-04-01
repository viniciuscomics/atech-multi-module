CREATE TABLE tbl_user (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  username VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(512) NOT NULL,
  active BOOLEAN DEFAULT TRUE  
);

CREATE TABLE tbl_authority (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE tbl_user_authority (
  id BIGSERIAL PRIMARY KEY NOT NULL,
  id_user BIGSERIAL NOT NULL,
  id_authority BIGSERIAL NOT NULL,
  FOREIGN KEY (id_user) REFERENCES tbl_user (id),
  FOREIGN KEY (id_authority) REFERENCES tbl_authority (id),
  UNIQUE (id_user, id_authority)  
);

CREATE TABLE IF NOT EXISTS tbl_access_token (
  token_id VARCHAR(256) DEFAULT NULL,
  token BYTEA,
  authentication_id VARCHAR(256) DEFAULT NULL,
  user_name VARCHAR(256) DEFAULT NULL,
  client_id VARCHAR(256) DEFAULT NULL,
  authentication BYTEA,
  refresh_token VARCHAR(256) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS tbl_refresh_token (
  token_id VARCHAR(256) DEFAULT NULL,
  token BYTEA,
  authentication BYTEA
);

CREATE TABLE IF NOT EXISTS  tbl_client_details (
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(255)
);
