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

@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String url = "jdbc:mysql://127.0.0.1:3306/payments";
    private static final String user = "root";
    private static final String password = "2002";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Edit Page</title></head><body>");
        out.println("<h2>Edit Page</h2>");

        List<Payment> payments = getPaymentsFromDatabase();
        if (payments != null && !payments.isEmpty()) {
            out.println("<form action='edit' method='post'>");

            for (Payment payment : payments) {
                out.println("ID: " + payment.getId() + "<br>");
                out.println("Title: <input type='text' name='title_" + payment.getId() + "' value='" + payment.getTitle() + "'><br>");
                out.println("Date: " + payment.getDate() + "<br>");
                out.println("Description: <input type='text' name='description_" + payment.getId() + "' value='" + payment.getDescription() + "'><br>");
                out.println("Category: <input type='text' name='category_" + payment.getId() + "' value='" + payment.getCategory() + "'><br>");
                out.println("Amount: <input type='text' name='amount_" + payment.getId() + "' value='" + payment.getAmount() + "'><br>");
                out.println("<input type='submit' name='action' value='Update_" + payment.getId() + "'>");
                out.println("<input type='submit' name='action' value='Delete_" + payment.getId() + "'>");
                out.println("<br><br>");
            }

            out.println("</form>");
        } else {
            out.println("<p>No payments found.</p>");
        }

        out.println("</body></html>");
        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String[] actionParts = action.split("_");
        String actionType = actionParts[0];
        int paymentId = Integer.parseInt(actionParts[1]);

        if ("Update".equals(actionType)) {
            String updatedTitle = request.getParameter("title_" + paymentId);
            String updatedDescription = request.getParameter("description_" + paymentId);
            String updatedCategory = request.getParameter("category_" + paymentId);
            double updatedAmount = Double.parseDouble(request.getParameter("amount_" + paymentId));

            if (updatePayment(paymentId, updatedTitle, updatedDescription, updatedCategory, updatedAmount)) {
                response.sendRedirect("edit?message=Payment%20updated%20successfully");
            } else {
                response.sendRedirect("edit?message=Error%20updating%20payment");
            }
        } else if ("Delete".equals(actionType)) {

            if (deletePayment(paymentId)) {
                response.sendRedirect("edit?message=Payment%20deleted%20successfully");
            } else {
                response.sendRedirect("edit?message=Error%20deleting%20payment");
            }
        }
    }

    private List<Payment> getPaymentsFromDatabase() {
        List<Payment> payments = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM payments";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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

    private boolean updatePayment(int id, String title, String description, String category, double amount) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE payments SET title=?, description=?, category=?, amount=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, description);
                preparedStatement.setString(3, category);
                preparedStatement.setDouble(4, amount);
                preparedStatement.setInt(5, id);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean deletePayment(int id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "DELETE FROM payments WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


}