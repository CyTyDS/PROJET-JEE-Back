DROP TABLE IF EXISTS connexion;
 
CREATE TABLE IF NOT EXISTS connexion (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  email VARCHAR(250) NOT NULL,
  username VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL,
  role VARCHAR(250) NOT NULL
);
 
/*
INSERT INTO connexion (email, username, password, role) 
VALUES ('admin@admin.fr', 'admin', 'admin', 'admin'),
('user@user.fr', 'user', 'user', 'user');
*/
