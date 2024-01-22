CREATE DATABASE employee_management;

USE employee_management;

CREATE TABLE payments (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          title VARCHAR(255) NOT NULL,
                          date VARCHAR(10) NOT NULL,
                          description VARCHAR(255),
                          category VARCHAR(50),
                          amount DOUBLE
);

CREATE TABLE employees_Password (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           username VARCHAR(50) NOT NULL,
                           password VARCHAR(50) NOT NULL
);
