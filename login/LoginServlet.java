import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LoginServlet - Handles user authentication
 * Part a: User Login Using Servlet and HTML Form
 * 
 * This servlet accepts username and password from an HTML form,
 * validates credentials, and displays appropriate response.
 */
public class LoginServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    // Hardcoded credentials for demonstration
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "admin123";
    
    /**
     * Handles POST requests from login form
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Retrieve form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // HTML header
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Login Response</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 50px; background-color: #f0f0f0; }");
        out.println(".container { max-width: 600px; margin: auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
        out.println(".success { color: green; }");
        out.println(".error { color: red; }");
        out.println("a { display: inline-block; margin-top: 20px; color: #007bff; text-decoration: none; }");
        out.println("a:hover { text-decoration: underline; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        
        // Validate credentials
        if (username != null && password != null) {
            if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
                // Successful login
                out.println("<h2 class='success'>Login Successful!</h2>");
                out.println("<p>Welcome, <strong>" + username + "</strong>!</p>");
                out.println("<p>You have successfully logged into the system.</p>");
                out.println("<p>Login time: " + new java.util.Date() + "</p>");
            } else {
                // Invalid credentials
                out.println("<h2 class='error'>Login Failed!</h2>");
                out.println("<p>Invalid username or password.</p>");
                out.println("<p>Please check your credentials and try again.</p>");
            }
        } else {
            // Missing parameters
            out.println("<h2 class='error'>Error!</h2>");
            out.println("<p>Username and password are required.</p>");
        }
        
        // Link back to login page
        out.println("<a href='login.html'>&larr; Back to Login</a>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
        
        out.close();
    }
    
    /**
     * Handles GET requests - redirects to POST
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
