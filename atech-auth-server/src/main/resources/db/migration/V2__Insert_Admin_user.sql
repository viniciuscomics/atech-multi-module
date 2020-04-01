DELETE FROM tbl_client_details;
DELETE FROM tbl_user_authority;

--password=atech
INSERT INTO tbl_user (username, email, password, active)
SELECT * FROM (SELECT 'admin', 'admin@admin.com', '$2y$12$fLHGxkqq6OFEnvU30g0Fk.BHpmf/ouZpBOSbLpjevG5fN9M5AahRy', true) AS tmp
WHERE NOT EXISTS (
    SELECT username FROM tbl_user WHERE username = 'admin'
) LIMIT 1;

INSERT INTO tbl_authority (name)
SELECT * FROM (SELECT 'ROLE_USER') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM tbl_authority WHERE name = 'ROLE_USER'
) LIMIT 1;

INSERT INTO tbl_authority (name)
SELECT * FROM (SELECT 'ROLE_ADMIN') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM tbl_authority WHERE name = 'ROLE_ADMIN'
) LIMIT 1;

INSERT INTO tbl_user_authority (id_user, id_authority)
SELECT u.id,a.id FROM tbl_user AS u, tbl_authority AS a WHERE u.username = 'admin' AND a.name = 'ROLE_USER';

INSERT INTO tbl_user_authority (id_user, id_authority)
SELECT u.id,a.id FROM tbl_user AS u, tbl_authority AS a WHERE u.username = 'admin' AND a.name = 'ROLE_ADMIN';
