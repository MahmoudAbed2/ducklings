package com.abed23.homeservlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("username") != null) {
            response.sendRedirect("payment");
        } else {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Home Page</title></head><body align=\"center\">");
            out.println("<h2>Login as Employee</h2>");
            out.println("<form action='login' method='post'>");
            out.println("Username: <input type='text' name='username'><br>");
            out.println("Password: <input type='password' name='password'><br>");
            out.println("<input type='submit' value='Login'>");
            out.println("</form>");
            out.println("</body></html>");
            out.close();
        }if (session.getAttribute("payment") !=null){
            response.sendRedirect("InvoiceServlet");
        }
    }
}
