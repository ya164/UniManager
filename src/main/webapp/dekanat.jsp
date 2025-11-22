<%@ page import="com.example.Group" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dean's Office Panel</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="container">
    <h1>Dean's Office Panel</h1>
    <% String message = (String) request.getAttribute("message");
        String messageClass = (String) request.getAttribute("messageClass");
        if (message != null && messageClass != null) { %>
    <div class="<%= messageClass %>">
        <p><%= message %></p>
    </div>
    <% } %>

    <h2>Create Group</h2>
    <form method="post">
        <input type="hidden" name="action" value="createGroup">
        Group Name: <input type="text" name="groupName"><br>
        <input type="submit" value="Create">
    </form>

    <h2>Create Student</h2>
    <form method="post">
        <input type="hidden" name="action" value="createStudent">
        Group Name: <select name="groupName">
        <% for (Group group : (List<Group>) request.getAttribute("groups")) { %>
        <option value="<%= group.getName() %>"><%= group.getName() %></option>
        <% } %>
    </select><br>
        First Name: <input type="text" name="firstName"><br>
        Last Name: <input type="text" name="lastName"><br>
        <input type="submit" value="Create">
    </form>

    <h2>Edit Group</h2>
    <form method="post">
        <input type="hidden" name="action" value="editGroup">
        Group Name: <select name="groupName">
        <% for (Group group : (List<Group>) request.getAttribute("groups")) { %>
        <option value="<%= group.getName() %>"><%= group.getName() %></option>
        <% } %>
    </select><br>
        New Group Name: <input type="text" name="newGroupName"><br>
        <input type="submit" value="Edit">
    </form>

    <h2>Delete Group</h2>
    <form method="post">
        <input type="hidden" name="action" value="deleteGroup">
        Group Name: <select name="groupName">
        <% for (Group group : (List<Group>) request.getAttribute("groups")) { %>
        <option value="<%= group.getName() %>"><%= group.getName() %></option>
        <% } %>
    </select><br>
        <input type="submit" value="Delete">
    </form>

    <h2>Edit Student</h2>
    <form method="post">
        <input type="hidden" name="action" value="editStudent">
        Group Name: <select name="groupName">
        <% for (Group group : (List<Group>) request.getAttribute("groups")) { %>
        <option value="<%= group.getName() %>"><%= group.getName() %></option>
        <% } %>
    </select><br>
        First Name: <input type="text" name="firstName"><br>
        Last Name: <input type="text" name="lastName"><br>
        New First Name: <input type="text" name="newFirstName"><br>
        New Last Name: <input type="text" name="newLastName"><br>
        <input type="submit" value="Edit">
    </form>

    <h2>Delete Student</h2>
    <form method="post">
        <input type="hidden" name="action" value="deleteStudent">
        Group Name: <select name="groupName">
        <% for (Group group : (List<Group>) request.getAttribute("groups")) { %>
        <option value="<%= group.getName() %>"><%= group.getName() %></option>
        <% } %>
    </select><br>
        First Name: <input type="text" name="firstName"><br>
        Last Name: <input type="text" name="lastName"><br>
        <input type="submit" value="Delete">
    </form>

    <a href="${pageContext.request.contextPath}/student">Go to Student Panel</a>
</div>
</body>
</html>