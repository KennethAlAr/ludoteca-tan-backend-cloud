INSERT INTO role(name)
VALUES ('ADMIN');

INSERT INTO app_user(name, password, role_id)
VALUES ('admin', 'admin', 1);
INSERT INTO app_user(name, password, role_id)
VALUES ('Kenneth', 'admin', 1);