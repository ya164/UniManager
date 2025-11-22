<%@ page import="com.example.Student" %>
<%@ page import="com.example.Group" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student Panel</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="container">
    <h1>Student Panel</h1>

    <h2>Search Students</h2>
    <form method="post" action="<%=request.getContextPath()%>/student">
        Search Type:
        <select name="searchType">
            <option value="byLastName">By Last Name</option>
            <option value="byGroupName">By Group Name</option>
        </select><br>
        Last Name: <input type="text" name="lastName"><br>
        Group Name: <input type="text" name="groupName"><br>
        <input type="submit" value="Search">
    </form>

    <h2>Select Group</h2>
    <form method="post" action="<%=request.getContextPath()%>/student">
        Select a Group to display students:
        <select name="groupName">
            <% List<Group> groups = (List<Group>) request.getAttribute("groups"); %>
            <% if (groups != null) { %>
            <% for (Group group : groups) { %>
            <option value="<%= group.getName() %>"><%= group.getName() %></option>
            <% } %>
            <% } %>
        </select><br>
        <input type="submit" name="searchType" value="Get List">
    </form>

    <% List<Student> searchResults = (List<Student>) request.getAttribute("searchResults"); %>
    <% if (searchResults != null && !searchResults.isEmpty()) { %>
    <h2>Search Results</h2>
    <table border="1">
        <thead>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Group</th>
        </tr>
        </thead>
        <tbody>
        <% for (Student student : searchResults) { %>
        <tr>
            <td><%= student.getFirstName() %></td>
            <td><%= student.getLastName() %></td>
            <td><%= student.getGroup() != null ? student.getGroup().getName() : "No Group" %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } else if (searchResults != null) { %>
    <p>No results found.</p>
    <% } %>

    <a href="${pageContext.request.contextPath}/admin">Go to Dean's Office Panel</a>
</div>
</body>
</html>