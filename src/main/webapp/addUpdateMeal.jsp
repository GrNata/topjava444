<%--
  Created by IntelliJ IDEA.
  User: NataGrigoreva
  Date: 13.03.2024
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ page contentType="text/html;charset=UTF-8" %>--%>
<html>
<head>
    <title>Meal</title>
</head>
<body>
    <h3><a href="index.html">Home</a> </h3>
    <hr>
    <h2>Edit meal</h2>

    <form action="addMeal" method="post">
        <label style="width: 600px" for="dateTime">DateTime:           </label>
<%--        <input type="datetime-local" id="dateTime" name="dateTime" value="${meal.dateTime}"><br>--%>
        <input type="text" id="dateTime" name="dateTime" value="${meal.dateTime}"><br>
        <label style="width: 600px" for="description">Description:        </label>
        <input type="text" id="description" name="description" value="${meal.description}"><br>
        <label style="width: 600px" for="calories">Calories:           </label>
        <input type="text" id="calories" name="calories" value="${meal.calories}"><br>
        <button type="submit" style="font-size: medium">Save</button>
        <button type="button" onclick="history.back();" style="font-size: medium">Cancel</button>
<%--        <a href="#" onclick="history.back();">Cancel</a>--%>
<%--        <a href="/meals">Cancel</a>--%>

    </form>


</body>
</html>
