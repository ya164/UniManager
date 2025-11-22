package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentServlet extends HttpServlet {
    private List<Group> groups;

    @Override
    public void init() throws ServletException {
        groups = (List<Group>) getServletContext().getAttribute("groups");
        if (groups == null) {
            groups = new ArrayList<>();
            getServletContext().setAttribute("groups", groups);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        groups = (List<Group>) getServletContext().getAttribute("groups");

        String searchType = request.getParameter("searchType");
        List<Student> searchResults = new ArrayList<>();

        if ("byLastName".equals(searchType)) {
            String lastName = request.getParameter("lastName");
            searchResults = findStudentsByLastName(lastName);
        } else if ("byGroupName".equals(searchType)) {
            String groupName = request.getParameter("groupName");
            searchResults = findStudentsByGroupName(groupName);
        } else if ("Отримати список".equals(searchType)) {
            String groupName = request.getParameter("groupName");
            searchResults = findStudentsByGroupName(groupName);
        }

        request.setAttribute("searchResults", searchResults);
        request.setAttribute("groups", groups);
        request.getRequestDispatcher("/student.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("groups", groups);

        request.getRequestDispatcher("/student.jsp").forward(request, response);
    }

    private List<Student> findStudentsByLastName(String lastName) {
        List<Student> studentsWithLastName = new ArrayList<>();
        for (Group group : groups) {
            for (Student student : group.getStudents()) {
                if (student.getLastName().equalsIgnoreCase(lastName)) {
                    studentsWithLastName.add(student);
                }
            }
        }
        return studentsWithLastName;
    }

    private List<Student> findStudentsByGroupName(String groupName) {
        for (Group group : groups) {
            if (group.getName().equalsIgnoreCase(groupName)) {
                return new ArrayList<>(group.getStudents());
            }
        }
        return new ArrayList<>();
    }
}

