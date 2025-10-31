-- Employee Records Database Schema
-- Part b: Display Employee Records with JDBC and Servlet Integration
--
-- This script creates the database and table structure for the Employee Records application
-- Database: company_db
-- Table: Employee

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS company_db;

-- Use the database
USE company_db;

-- Drop table if exists (for clean setup)
DROP TABLE IF EXISTS Employee;

-- Create Employee table
CREATE TABLE Employee (
    EmpID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    Salary DECIMAL(10, 2) NOT NULL,
    Department VARCHAR(50),
    JoinDate DATE,
    Email VARCHAR(100),
    Phone VARCHAR(20)
);

-- Insert sample employee records
INSERT INTO Employee (Name, Salary, Department, JoinDate, Email, Phone) VALUES
('John Doe', 50000.00, 'Engineering', '2020-01-15', 'john.doe@company.com', '555-0101'),
('Jane Smith', 60000.00, 'Marketing', '2019-06-20', 'jane.smith@company.com', '555-0102'),
('Bob Johnson', 55000.00, 'Sales', '2021-03-10', 'bob.johnson@company.com', '555-0103'),
('Alice Williams', 65000.00, 'Engineering', '2018-09-05', 'alice.williams@company.com', '555-0104'),
('Charlie Brown', 52000.00, 'HR', '2020-11-22', 'charlie.brown@company.com', '555-0105'),
('Diana Prince', 70000.00, 'Engineering', '2017-04-18', 'diana.prince@company.com', '555-0106'),
('Ethan Hunt', 58000.00, 'Operations', '2019-12-08', 'ethan.hunt@company.com', '555-0107'),
('Fiona Green', 62000.00, 'Finance', '2020-07-30', 'fiona.green@company.com', '555-0108'),
('George Wilson', 54000.00, 'Sales', '2021-02-14', 'george.wilson@company.com', '555-0109'),
('Hannah Davis', 67000.00, 'Engineering', '2018-10-25', 'hannah.davis@company.com', '555-0110');

-- Display all records to verify
SELECT * FROM Employee;

-- Create index on Name for faster searches
CREATE INDEX idx_employee_name ON Employee(Name);

-- Create index on Department for department-wise queries
CREATE INDEX idx_employee_dept ON Employee(Department);

-- Display table structure
DESCRIBE Employee;

-- Display count of employees
SELECT COUNT(*) AS TotalEmployees FROM Employee;

-- Display department-wise employee count
SELECT Department, COUNT(*) AS EmployeeCount 
FROM Employee 
GROUP BY Department 
ORDER BY EmployeeCount DESC;

-- Display average salary by department
SELECT Department, AVG(Salary) AS AvgSalary 
FROM Employee 
GROUP BY Department 
ORDER BY AvgSalary DESC;

-- Grant privileges (adjust username as needed)
-- GRANT ALL PRIVILEGES ON company_db.* TO 'root'@'localhost';
-- FLUSH PRIVILEGES;

-- Notes:
-- 1. Make sure MySQL service is running
-- 2. Update DB credentials in EmployeeServlet.java
-- 3. Add mysql-connector-java JAR to WEB-INF/lib/
-- 4. Test connection before deploying servlet
