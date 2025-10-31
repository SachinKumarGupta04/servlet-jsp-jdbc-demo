# servlet-jsp-jdbc-demo

Complete Servlet/JSP/JDBC applications for login, employee records, and attendance portal.

## Project Structure

This repository contains three complete web applications demonstrating Servlet, JSP, and JDBC technologies:

```
servlet-jsp-jdbc-demo/
├── login/
│   ├── LoginServlet.java
│   ├── login.html
│   └── web.xml
├── employee-records/
│   ├── EmployeeServlet.java
│   ├── search.html
│   ├── database.sql
│   └── web.xml
├── attendance-portal/
│   ├── AttendanceServlet.java
│   ├── attendance.jsp
│   ├── success.jsp
│   ├── database.sql
│   └── web.xml
└── README.md
```

## Applications Overview

### 1. Login Application (`login/`)
**Objective:** User authentication using Servlet and HTML form

**Features:**
- HTML form for username and password input
- Servlet validates credentials using POST method
- Displays personalized welcome message on success
- Shows error message on invalid credentials

**Technologies:** Servlet, HTML, HttpServletRequest, PrintWriter

### 2. Employee Records (`employee-records/`)
**Objective:** Display and search employee records using JDBC and Servlet

**Features:**
- Lists all employees from database in HTML table
- Search functionality to find employee by ID
- JDBC integration with MySQL/PostgreSQL
- Dynamic HTML generation

**Technologies:** Servlet, JDBC, HTML, MySQL

### 3. Attendance Portal (`attendance-portal/`)
**Objective:** Student attendance management using JSP and Servlet

**Features:**
- JSP form to collect attendance data (Student ID, Date, Status)
- Servlet processes form submission
- Saves attendance records to database
- Success confirmation page
- Demonstrates MVC pattern separation

**Technologies:** JSP, Servlet, JDBC, MySQL

## Prerequisites

- **JDK:** Java 8 or higher
- **Server:** Apache Tomcat 8.5+
- **Database:** MySQL 5.7+ or PostgreSQL 9.5+
- **IDE:** Eclipse/IntelliJ IDEA (recommended)
- **Build Tool:** Maven or manual JAR management

## Database Setup

### For Employee Records Application
```sql
CREATE DATABASE company_db;
USE company_db;

CREATE TABLE Employee (
    EmpID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    Salary DECIMAL(10, 2) NOT NULL
);

INSERT INTO Employee (Name, Salary) VALUES
('John Doe', 50000),
('Jane Smith', 60000),
('Bob Johnson', 55000);
```

### For Attendance Portal Application
```sql
CREATE DATABASE school_db;
USE school_db;

CREATE TABLE Attendance (
    AttendanceID INT PRIMARY KEY AUTO_INCREMENT,
    StudentID INT NOT NULL,
    AttendanceDate DATE NOT NULL,
    Status VARCHAR(20) NOT NULL
);
```

## Configuration

### JDBC Connection Parameters
Update the following in respective servlet files:

```java
String url = "jdbc:mysql://localhost:3306/your_database";
String username = "your_username";
String password = "your_password";
```

### Required JAR Files
- `servlet-api.jar` (provided by Tomcat)
- `mysql-connector-java-8.0.x.jar` or `postgresql-xx.x.x.jar`
- `jsp-api.jar` (provided by Tomcat)

## Deployment Instructions

### For Each Application:

1. **Compile Java Files**
   ```bash
   javac -cp servlet-api.jar:mysql-connector.jar *.java
   ```

2. **Create WAR Structure**
   ```
   app-name/
   ├── WEB-INF/
   │   ├── web.xml
   │   ├── classes/
   │   │   └── *.class files
   │   └── lib/
   │       └── mysql-connector.jar
   ├── *.html files
   └── *.jsp files
   ```

3. **Deploy to Tomcat**
   - Copy folder to `TOMCAT_HOME/webapps/`
   - Or create WAR file: `jar -cvf app-name.war *`
   - Deploy WAR to Tomcat

4. **Access Application**
   - Login: `http://localhost:8080/login/login.html`
   - Employee Records: `http://localhost:8080/employee-records/EmployeeServlet`
   - Attendance: `http://localhost:8080/attendance-portal/attendance.jsp`

## Testing

### Login Application
1. Open `login.html` in browser
2. Enter username: `admin`, password: `admin123`
3. Submit and verify welcome message

### Employee Records
1. Access the EmployeeServlet URL
2. View all employee records
3. Use search form to find specific employee by ID

### Attendance Portal
1. Open `attendance.jsp`
2. Enter Student ID, Date, and Status
3. Submit and verify database entry
4. Check success page confirmation

## Learning Objectives

- Understanding HTTP request/response cycle
- Form data handling with Servlets
- Database connectivity using JDBC
- ResultSet processing and display
- JSP for view layer
- Servlet for controller logic
- MVC architecture basics
- Transaction management
- Error handling and validation

## Common Issues & Solutions

**ClassNotFoundException for JDBC Driver:**
- Ensure MySQL connector JAR is in `WEB-INF/lib/`
- Load driver: `Class.forName("com.mysql.cj.jdbc.Driver")`

**404 Error:**
- Check servlet mapping in `web.xml`
- Verify application deployment in Tomcat
- Confirm context path in URL

**Database Connection Failed:**
- Verify MySQL service is running
- Check database credentials
- Confirm database exists
- Check firewall/port settings

**Cannot forward after response committed:**
- Don't write to response before forwarding
- Use either forward OR PrintWriter, not both

## Additional Resources

- [Oracle Servlet Tutorial](https://docs.oracle.com/javaee/7/tutorial/servlets.htm)
- [JSP Specification](https://javaee.github.io/javaee-spec/javadocs/)
- [JDBC Tutorial](https://docs.oracle.com/javase/tutorial/jdbc/)
- [Apache Tomcat Documentation](https://tomcat.apache.org/tomcat-9.0-doc/)

## License

This project is for educational purposes.

## Author

SachinKumarGupta04

## Contributing

Feel free to fork this repository and submit pull requests for improvements or bug fixes.
