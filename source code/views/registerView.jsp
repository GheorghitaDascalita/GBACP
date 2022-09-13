<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Inregistrare</title>
		<link href="<c:url value="/resources/css/register.css" />" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div>
			<h1>Inregistrare</h1>
			<form method="POST" action="${pageContext.request.contextPath}/register">
				<label for="username">Nume de utilizator: </label>
				<input type="text" name="username" required>
				<label for="password">Parola: </label>
				<input type="password" name="password" required>
				<input type="submit" value="Inregistreaza-te">
			</form>
			<a href="${pageContext.request.contextPath}/login">Conecteaza-te</a>
		</div>
	</body>
</html>