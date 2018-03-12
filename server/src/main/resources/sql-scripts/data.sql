INSERT INTO app_role (id, role_name, description) VALUES (1, 'ROLE_USER1', 'Standard User - Has no admin rights');
INSERT INTO app_role (id, role_name, description) VALUES (2, 'ROLE_ADMIN', 'Admin User - Has permission to perform admin tasks');

-- password: password
INSERT INTO app_user (id, first_name, last_name, password, username) VALUES (1, 'John', 'Doe', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user');
INSERT INTO app_user (id, first_name, last_name, password, username) VALUES (2, 'Admin', 'Admin', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'admin');


INSERT INTO user_role(user_id, role_id) VALUES(1,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,2);
