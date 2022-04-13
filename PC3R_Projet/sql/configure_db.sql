DROP USER 'user'@'localhost';
CREATE USER 'user'@'localhost';
GRANT ALL PRIVILEGES ON db.* TO 'user'@'localhost' IDENTIFIED BY 'password';
FLUSH PRIVILEGES;
DROP DATABASE db;
CREATE DATABASE db CHARACTER SET utf8;
USE db;

CREATE TABLE users (
	id INT NOT NULL AUTO_INCREMENT,
	user VARCHAR(128) NOT NULL,
	pass BINARY(64) NOT NULL,
	money BIGINT NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE production (
	user_id INT NOT NULL,
	resource INT NOT NULL,
	count BIGINT NOT NULL,
	production BIGINT NOT NULL,
	research BIGINT NOT NULL,
	FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);
