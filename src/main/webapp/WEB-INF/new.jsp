<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>New Project</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<h1>New Project</h1>
		<form:form action="/create" method="post" modelAttribute="project">
			<div>
				<form:label path="title">Project Title</form:label>
				<form:input path="title" class="form-control" />
				<form:errors path="title" class="text-danger" />
			</div>
			<div>
				<form:label path="description">Project Description</form:label>
				<form:textarea path="description" class="form-control" />
				<form:errors path="description" class="text-danger" />
			</div>
			<div>
				<form:label path="dueDate">Due Date</form:label>
				<!-- Valor por def: fecha de hoy. y que min fecha = fecha de hoy -->
				<form:input path="dueDate" class="form-control" 
				type="date" value="<%= java.time.LocalDate.now() %>" 
				min="<%= java.time.LocalDate.now() %>"/>
				<form:errors path="dueDate" class="text-danger" />
			</div>
			<form:hidden path="lead" value="${userInSession.id}" />
			<input type="submit" class="btn btn-success mt-3" value="Save Project" />
		</form:form>
	</div>
</body>
</html>