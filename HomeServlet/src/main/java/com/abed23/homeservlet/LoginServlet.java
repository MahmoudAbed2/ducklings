package com.abed23.homeservlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/login")

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String url = "jdbc:mysql://127.0.0.1:3306/payments";

    private static final int PORT = 3306;

    private static final String user = "root";
    private static final String password = "2002";


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");



        if ("root".equals(username) && "2002".equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            response.sendRedirect("payment");
        } else {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Login Error</title></head><body align=\"center\">");
            out.println("<h2>Login Error</h2>");
            out.println("<p>Invalid username or password. Please try again.</p>");
            out.println("</body></html>");
            out.close();
        }
    }

}