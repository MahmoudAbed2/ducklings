package com.abed23.homeservlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/invoice")
public class InvoiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String url = "jdbc:mysql://127.0.0.1:3306/payments";
    private static final int PORT = 3306;

    private static final String user = "root";
    private static final String password = "2002";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Invoice Page</title></head><body>");
        out.println("<h2>Invoice Page</h2>");

        List<Payment> payments = getPaymentsForEmployee("");
        if (payments != null && !payments.isEmpty()) {
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Title</th><th>Date</th><th>Description</th><th>Category</th><th>Amount</th></tr>");

            for (Payment payment : payments) {
                out.println("<tr>");
                out.println("<td>" + payment.getId() + "</td>");
                out.println("<td>" + payment.getTitle() + "</td>");
                out.println("<td>" + payment.getDate() + "</td>");
                out.println("<td>" + payment.getDescription() + "</td>");
                out.println("<td>" + payment.getCategory() + "</td>");
                out.println("<td>" + payment.getAmount() + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
        } else {
            out.println("<p>No payments found for the employee.</p>");
        }

        out.println("</body></html>");
        out.close();
    }

    private List<Payment> getPaymentsForEmployee(String employeeId) {
        List<Payment> payments = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM payments WHERE employee_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, employeeId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String title = resultSet.getString("title");
                        String date = resultSet.getString("date");
                        String description = resultSet.getString("description");
                        String category = resultSet.getString("category");
                        double amount = resultSet.getDouble("amount");

                        Payment payment = new Payment(id, title, date, description, category, amount);
                        payments.add(payment);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payments;
    }
}
