
INSERT INTO USERS(username, password) VALUES('user', '$2a$10$xszlj1pbFnz6kKreb3X9pugjNUg0MAcC1ff/NR43oKNthJYYcCRj6'); --password
INSERT INTO USERS(username, password) VALUES('admin', '$2a$10$xszlj1pbFnz6kKreb3X9pugjNUg0MAcC1ff/NR43oKNthJYYcCRj6'); --password
INSERT INTO USERS(username, password) VALUES('read_only', '$2a$10$xszlj1pbFnz6kKreb3X9pugjNUg0MAcC1ff/NR43oKNthJYYcCRj6'); --password
INSERT INTO ROLES(name) VALUES ('USER');
INSERT INTO ROLES(name) VALUES ('ADMIN');
INSERT INTO ROLES(name) VALUES ('READ_ONLY');

INSERT INTO ROLE_USER(user_id, role_id) VALUES (1, 1);
INSERT INTO ROLE_USER(user_id, role_id) VALUES (2, 1);
INSERT INTO ROLE_USER(user_id, role_id) VALUES (2, 2);
INSERT INTO ROLE_USER(user_id, role_id) VALUES (3, 3);