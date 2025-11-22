package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private static final String DEKANAT_PASSWORD = "7171";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");
        if (DEKANAT_PASSWORD.equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("authenticated", true);
            response.sendRedirect("admin");
        } else {
            request.setAttribute("errorMessage", "The password is incorrect.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}


