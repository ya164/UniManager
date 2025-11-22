<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home Page</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="container">
    <h1>Welcome to our web application!</h1>
    <p><a href="student">Student Panel</a></p>
    <form action="login" method="post">
        <input type="password" name="password" placeholder="Enter password">
        <input type="submit" value="Login to Dean's Office Panel">
    </form>
    <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="error-message">
        <%= request.getAttribute("errorMessage") %>
    </div>
    <% } %>
    <div class="welcome-section">
        <h2>Welcome!</h2>
        <p>Introducing our new web application: experience maximum functionality and convenience at the same time! Our platform is designed with students in mind, providing them with all the necessary tools. No more problems searching for students and getting group lists!</p>
    </div>
    <div class="reviews-section">
        <h2>Student Reviews</h2>
        <div class="review">
            <p><strong>Yaroslav Los</strong> <span class="stars">★★★★★</span>: "Incredible web application, never used such a convenient one before!"</p>
        </div>
        <div class="review">
            <p><strong>Roy Babaytsev</strong> <span class="stars">★★★★☆</span>: "Very satisfied with the functionality, although sometimes there are small glitches, but the support team is always in touch!"</p>
        </div>
        <div class="review">
            <p><strong>Maksym Lyhogub</strong> <span class="stars">★★★★★</span>: "The system is perfect for our academic needs. Everything works quickly and efficiently!"</p>
        </div>
    </div>
</div>
</body>
</html>