<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }

        .container {
            border: 1px solid #ccc;
            padding: 20px;
            /*width: 700px;*/
            margin: 0 auto;
        }

        .input-row {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }

        .input-row label {
            width: 100px;
            margin-right: 10px;
        }

        .input-row input[type="text"] {
            width: 200px;
            padding: 5px;
        }

        .buttons {
            margin-top: 20px;
            text-align: center;
        }

        .buttons button {
            margin-right: 10px;
            padding: 10px 20px;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <hr/>
<%--    фильтрации еды по DateTime--%>
<%--    <form method="post" action="filter">--%>
    <form method="post" action="meals">
        <div class="container">
            <div class="input-row">
                <label for="fromDate">От даты (включая): </label>
                <input type="date" id="fromDate" name="fromDate">
                <label for="toDate">До даты (включая): </label>
                <input type="date" id="toDate" name="toDate">
            </div>
            <div class="input-row">
                <label for="fromTime">От времени (включая): </label>
                <input type="time" id="fromTime" name="fromTime" value="fTime">
                <label for="toTime">До времени (исключая): </label>
                <input type="time" id="toTime" name="toTime">
            </div>
            <div class="button">
                <button><a href="meals?action=cancel" >Отменить</a></button>
                <button type="submit" name="action" value="filter">Отфильтровать</button>
            </div>
        </div>
    </form>

    <hr/>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${requestScope.meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>