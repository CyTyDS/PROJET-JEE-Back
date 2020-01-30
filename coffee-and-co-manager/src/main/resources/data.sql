DROP TABLE IF EXISTS connexion;
 
CREATE TABLE IF NOT EXISTS connexion (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL,
  role VARCHAR(250) NOT NULL
);
 
INSERT INTO connexion (username, password, role) 
VALUES ('admin', 'admin', 'admin'),
('user', 'user', 'user');
