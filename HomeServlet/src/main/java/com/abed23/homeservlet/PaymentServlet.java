package com.abed23.homeservlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String url = "jdbc:mysql://127.0.0.1:3306/payments";
    private static final String user = "root";
    private static final String password = "2002";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Payment Page</title></head><body align=\"center\">");
        out.println("<h2>Payment Page</h2>");
        out.println("<form action='payment' method='post'>");
        out.println("Title: <input type='text' name='title'><br>");
        out.println("Date: <input type='text' name='date' value='" + getCurrentDate() + "' readonly><br>");
        out.println("Description: <input type='text' name='description'><br>");
        out.println("Category: <input type='text' name='category'><br>");
        out.println("Amount: <input type='text' name='amount'><br>");
        out.println("<input type='submit' value='Submit Payment'>");
        out.println("</form>");


        String message = request.getParameter("message");
        if (message != null && !message.isEmpty()) {
            out.println("<p>" + message + "</p>");
        }

        out.println("</body></html>");

        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        String date = request.getParameter("date");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String amountStr = request.getParameter("amount");

        if (title == null || date == null || description == null || category == null || amountStr == null ||
                title.isEmpty() || date.isEmpty() || description.isEmpty() || category.isEmpty() || amountStr.isEmpty()) {

            response.sendRedirect("payment?message=" + URLEncoder.encode("Please fill all fields", "UTF-8"));
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("payment?message=" + URLEncoder.encode("Invalid amount format", "UTF-8"));
            return;
        }

        if (insertPayment(title, date, description, category, amount)) {
            response.sendRedirect("payment?message=" + URLEncoder.encode("Payment registered successfully", "UTF-8"));
        } else {
            response.sendRedirect("payment?message=" + URLEncoder.encode("Error registering payment", "UTF-8"));
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }
    private boolean insertPayment(String title, String date, String description, String category, double amount) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO payments (title, date, description, category, amount) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, date);
                preparedStatement.setString(3, description);
                preparedStatement.setString(4, category);
                preparedStatement.setDouble(5, amount);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Payment registered successfully");
                    return true;
                } else {
                    System.out.println("Error registering payment. No rows affected.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error inserting payment: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}