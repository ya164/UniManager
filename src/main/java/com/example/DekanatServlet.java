package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DekanatServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("authenticated") != null && (Boolean) session.getAttribute("authenticated")) {
            request.setAttribute("groups", groups);
            request.getRequestDispatcher("/dekanat.jsp").forward(request, response);
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String groupName = request.getParameter("groupName");
        String message = "";
        String messageClass = "message";

        switch (action) {
            case "createGroup":
                if (groupName == null || groupName.trim().isEmpty()) {
                    message = "Group name cannot be empty.";
                    messageClass = "error-message";
                } else if (!isGroupExists(groupName)) {
                    Group group = new Group(groupName);
                    groups.add(group);
                    message = "Group successfully created.";
                } else {
                    message = "This group already exists.";
                    messageClass = "error-message";
                }
                break;
            case "createStudent":
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty()) {
                    message = "Student's first and last names cannot be empty.";
                    messageClass = "error-message";
                } else {
                    Group group = findGroupByName(groupName);
                    if (group != null) {
                        Student student = new Student(firstName, lastName);
                        student.setGroup(group);
                        group.getStudents().add(student);
                        message = "Student successfully created.";
                    } else {
                        message = "Group not found.";
                        messageClass = "error-message";
                    }
                }
                break;
            case "editGroup":
                String newGroupName = request.getParameter("newGroupName");
                if (newGroupName == null || newGroupName.trim().isEmpty()) {
                    message = "New group name cannot be empty.";
                    messageClass = "error-message";
                } else {
                    Group groupToEdit = findGroupByName(groupName);
                    if (groupToEdit != null && !newGroupName.equals(groupName) && !isGroupExists(newGroupName)) {
                        groupToEdit.setName(newGroupName);
                        message = "Group name changed.";
                    } else if (isGroupExists(newGroupName)) {
                        message = "A group with this name already exists.";
                        messageClass = "error-message";
                    } else {
                        message = "Group not found or new name is missing.";
                        messageClass = "error-message";
                    }
                }
                break;
            case "deleteGroup":
                Group groupToDelete = findGroupByName(groupName);
                if (groupToDelete != null) {
                    groups.remove(groupToDelete);
                    message = "Group deleted.";
                } else {
                    message = "Group not found.";
                    messageClass = "error-message";
                }
                break;
            case "editStudent":
                String newFirstName = request.getParameter("newFirstName");
                String newLastName = request.getParameter("newLastName");
                firstName = request.getParameter("firstName");
                lastName = request.getParameter("lastName");
                Group groupWithStudent = findGroupByName(groupName);
                if (groupWithStudent != null) {
                    Student studentToEdit = findStudentInGroup(groupWithStudent, firstName, lastName);
                    if (studentToEdit != null) {
                        if (newFirstName.equals(studentToEdit.getFirstName()) && newLastName.equals(studentToEdit.getLastName())) {
                            message = "Cannot change the name to the current one.";
                            messageClass = "error-message";
                        } else {
                            studentToEdit.setFirstName(newFirstName);
                            studentToEdit.setLastName(newLastName);
                            message = "Student data changed.";
                        }
                    } else {
                        message = "Student not found.";
                        messageClass = "error-message";
                    }
                } else {
                    message = "Group not found.";
                    messageClass = "error-message";
                }
                break;
            case "deleteStudent":
                firstName = request.getParameter("firstName");
                lastName = request.getParameter("lastName");
                Group groupWithStudentToDelete = findGroupByName(groupName);
                if (groupWithStudentToDelete != null) {
                    Student studentToDelete = findStudentInGroup(groupWithStudentToDelete, firstName, lastName);
                    if (studentToDelete != null) {
                        groupWithStudentToDelete.getStudents().remove(studentToDelete);
                        message = "Student deleted.";
                    } else {
                        message = "Student not found.";
                        messageClass = "error-message";
                    }
                } else {
                    message = "Group not found.";
                    messageClass = "error-message";
                }
                break;
        }

        request.setAttribute("message", message);
        request.setAttribute("messageClass", messageClass);
        doGet(request, response);
    }

    private boolean isGroupExists(String groupName) {
        return groups.stream().anyMatch(g -> g.getName().equalsIgnoreCase(groupName));
    }

    private Group findGroupByName(String groupName) {
        return groups.stream().filter(g -> g.getName().equalsIgnoreCase(groupName)).findFirst().orElse(null);
    }

    private Student findStudentInGroup(Group group, String firstName, String lastName) {
        return group.getStudents().stream().filter(s -> s.getFirstName().equalsIgnoreCase(firstName) && s.getLastName().equalsIgnoreCase(lastName)).findFirst().orElse(null);
    }
}