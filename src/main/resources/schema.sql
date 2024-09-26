DROP TABLE IF EXISTS ROLE_USER;
DROP TABLE IF EXISTS ROLES;

CREATE TABLE ROLES(
                      id INT GENERATED ALWAYS AS IDENTITY,
                      name VARCHAR(15) NOT NULL,
                      PRIMARY KEY (id)
);

CREATE TABLE ROLE_USER(
                          user_id INT NOT NULL,
                          role_id INT NOT NULL,
                          PRIMARY KEY (user_id, role_id),
                          FOREIGN KEY (user_id) REFERENCES USERS(id),
                          FOREIGN KEY (role_id) REFERENCES ROLES(id)
);

