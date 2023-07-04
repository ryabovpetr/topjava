<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add new meal</title>
</head>
<body>
<form method="POST" action='meals' name="formAddMeal">
    Date : <input type="datetime-local" name="dateTime" value="<c:out value="${meal.dateTime}" />"/> <br/>
    Description : <input type="text" name="description" value="<c:out value="${meal.description}" />"/> <br/>
    Calories : <input type="text" name="calories" value="<c:out value="${meal.calories}" />"/> <br/>
    <input type="hidden" name="id" value="<c:out value="${meal.id}" />"/> <br/>
    <button onclick="window.history.back()" type="button">Cancel</button>
    <input type="submit" value="Save"/>
</form>
</body>
</html>