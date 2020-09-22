DROP TABLE IF EXISTS companies CASCADE CONSTRAINTS ^;
DROP TABLE IF EXISTS employees ^;

CREATE TABLE companies (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  created_at DATETIME NOT NULL,
  updated_at DATETIME NOT NULL
) ^;

CREATE TABLE employees (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  surname VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  address VARCHAR(250) NOT NULL,
  salary DECIMAL(10 , 2) NOT NULL,
  company_id INT NOT NULL,
  created_at DATETIME NOT NULL,
  updated_at DATETIME NOT NULL,
  FOREIGN KEY (company_id) REFERENCES companies(id)
) ^;

INSERT INTO companies (name, created_at, updated_at) VALUES
  ('Company1', NOW(), NOW()),
  ('Company2', NOW(), NOW()),
  ('Company3', NOW(), NOW()) ^;

INSERT INTO employees (name, surname, email, address, salary, company_id, created_at, updated_at) VALUES
  ('Name11', 'Surname11', 'email11@domain.com', 'Address11', 1150.00, 1, NOW(), NOW()),
  ('Name21', 'Surname21', 'email21@domain.com', 'Address21', 2150.00, 2, NOW(), NOW()),
  ('Name22', 'Surname22', 'email22@domain.com', 'Address22', 2250.00, 2, NOW(), NOW()),
  ('Name31', 'Surname31', 'email31@domain.com', 'Address31', 3150.00, 3, NOW(), NOW()),
  ('Name32', 'Surname32', 'email32@domain.com', 'Address32', 3250.00, 3, NOW(), NOW()),
  ('Name33', 'Surname33', 'email33@domain.com', 'Address33', 3350.00, 3, NOW(), NOW()) ^;