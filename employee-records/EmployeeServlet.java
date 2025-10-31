import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * EmployeeServlet - Displays and searches employee records
 * Part b: Display Employee Records with JDBC and Servlet Integration
 * 
 * This servlet integrates with a database using JDBC and displays employee records.
 * Includes search functionality to fetch employee by ID.
 */
public class EmployeeServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/company_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";
    
    /**
     * Initialize JDBC driver
     */
    public void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
        }
    }
    
    /**
     * Handles both GET and POST requests
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Get search parameter (if any)
        String searchId = request.getParameter("empId");
        
        // HTML Header
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Employee Records Management</title>");
        printStyles(out);
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h1>👥 Employee Records System</h1>");
        out.println("<p class='subtitle'>JDBC and Servlet Integration Demo</p>");
        
        // Search Form
        printSearchForm(out);
        
        // Display employees
        if (searchId != null && !searchId.trim().isEmpty()) {
            // Search for specific employee
            searchEmployee(out, searchId);
        } else {
            // Display all employees
            displayAllEmployees(out);
        }
        
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
    
    /**
     * Handles POST requests - redirect to GET
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    /**
     * Print CSS styles
     */
    private void printStyles(PrintWriter out) {
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; padding: 20px; }");
        out.println(".container { max-width: 1000px; margin: auto; background: white; padding: 40px; border-radius: 15px; box-shadow: 0 15px 35px rgba(0,0,0,0.2); }");
        out.println("h1 { color: #333; text-align: center; margin-bottom: 10px; }");
        out.println(".subtitle { text-align: center; color: #666; margin-bottom: 30px; }");
        out.println(".search-box { background: #f8f9fa; padding: 20px; border-radius: 10px; margin-bottom: 30px; }");
        out.println(".search-box h2 { color: #333; font-size: 18px; margin-bottom: 15px; }");
        out.println(".search-form { display: flex; gap: 10px; }");
        out.println(".search-form input { flex: 1; padding: 12px; border: 2px solid #e1e1e1; border-radius: 8px; font-size: 14px; }");
        out.println(".search-form button { padding: 12px 30px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; border: none; border-radius: 8px; cursor: pointer; font-weight: 600; }");
        out.println(".search-form button:hover { transform: translateY(-2px); box-shadow: 0 5px 15px rgba(102,126,234,0.4); }");
        out.println(".reset-btn { padding: 12px 20px; background: #6c757d; color: white; border: none; border-radius: 8px; cursor: pointer; text-decoration: none; display: inline-block; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        out.println("th { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 15px; text-align: left; font-weight: 600; }");
        out.println("td { padding: 15px; border-bottom: 1px solid #e1e1e1; }");
        out.println("tr:hover { background: #f8f9fa; }");
        out.println(".error { color: #dc3545; padding: 15px; background: #f8d7da; border-radius: 8px; margin: 20px 0; }");
        out.println(".success { color: #155724; padding: 15px; background: #d4edda; border-radius: 8px; margin: 20px 0; }");
        out.println(".info { background: #e7f3ff; padding: 15px; border-radius: 8px; border-left: 4px solid #667eea; margin-bottom: 20px; }");
        out.println("</style>");
    }
    
    /**
     * Print search form
     */
    private void printSearchForm(PrintWriter out) {
        out.println("<div class='search-box'>");
        out.println("<h2>🔍 Search Employee</h2>");
        out.println("<form method='GET' action='EmployeeServlet' class='search-form'>");
        out.println("<input type='number' name='empId' placeholder='Enter Employee ID' required>");
        out.println("<button type='submit'>Search</button>");
        out.println("<a href='EmployeeServlet' class='reset-btn'>View All</a>");
        out.println("</form>");
        out.println("</div>");
    }
    
    /**
     * Display all employees from database
     */
    private void displayAllEmployees(PrintWriter out) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            // Establish connection
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            // SQL query to fetch all employees
            String sql = "SELECT EmpID, Name, Salary FROM Employee ORDER BY EmpID";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            // Display results in table
            out.println("<h2>All Employees</h2>");
            out.println("<table>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Employee ID</th>");
            out.println("<th>Name</th>");
            out.println("<th>Salary</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            
            int count = 0;
            while (rs.next()) {
                int empId = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                
                out.println("<tr>");
                out.println("<td>" + empId + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>$" + String.format("%.2f", salary) + "</td>");
                out.println("</tr>");
                count++;
            }
            
            out.println("</tbody>");
            out.println("</table>");
            
            if (count == 0) {
                out.println("<div class='info'>No employees found in the database.</div>");
            } else {
                out.println("<div class='info'>Total Employees: " + count + "</div>");
            }
            
        } catch (SQLException e) {
            out.println("<div class='error'>");
            out.println("<strong>Database Error:</strong><br>");
            out.println(e.getMessage());
            out.println("<br><br><small>Please ensure MySQL is running and database is configured.</small>");
            out.println("</div>");
            e.printStackTrace();
        } finally {
            // Close resources
            closeResources(conn, stmt, rs);
        }
    }
    
    /**
     * Search for specific employee by ID
     */
    private void searchEmployee(PrintWriter out, String empIdStr) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            int empId = Integer.parseInt(empIdStr);
            
            // Establish connection
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            // SQL query to fetch specific employee
            String sql = "SELECT EmpID, Name, Salary FROM Employee WHERE EmpID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, empId);
            rs = stmt.executeQuery();
            
            out.println("<h2>Search Results</h2>");
            
            if (rs.next()) {
                // Employee found
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                
                out.println("<div class='success'>Employee found!</div>");
                out.println("<table>");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th>Employee ID</th>");
                out.println("<th>Name</th>");
                out.println("<th>Salary</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");
                out.println("<tr>");
                out.println("<td>" + empId + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>$" + String.format("%.2f", salary) + "</td>");
                out.println("</tr>");
                out.println("</tbody>");
                out.println("</table>");
            } else {
                // Employee not found
                out.println("<div class='error'>No employee found with ID: " + empId + "</div>");
            }
            
        } catch (NumberFormatException e) {
            out.println("<div class='error'>Invalid Employee ID format. Please enter a valid number.</div>");
        } catch (SQLException e) {
            out.println("<div class='error'>");
            out.println("<strong>Database Error:</strong><br>");
            out.println(e.getMessage());
            out.println("</div>");
            e.printStackTrace();
        } finally {
            // Close resources
            closeResources(conn, stmt, rs);
        }
    }
    
    /**
     * Close database resources
     */
    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
