<%--
  Created by IntelliJ IDEA.
  User: NataGrigoreva
  Date: 13.03.2024
  Time: 10:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>

    <h3><a href="index.html">Home</a> </h3>
    <hr>
    <h2>Meals</h2>

    <h4><a href="addMeal">Add Meal</a> </h4>

    <table border="1">
        <thead>
            <tr>
                <th>ID</th>

                <th>Date</th>
                <th>Description</th>
                <th>Calories</th>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${meals}" var="meal">
                <tr style="color: ${meal.excess ? 'red' : 'green'};">
<%--                    <td>${meal.dateTime.format(java.time.format.DateTimeFormatter.ofPattern('dd-MM-yyyy  HH:mm:ss'))}</td>--%>
                    <td>${meal.id}</td>

                    <td>${formatter.format(meal.dateTime)}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td><a>Update</a></td>
                    <td><a>Delete</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</body>
</html>
