<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<nav class="d-flex justify-content-between align-items-center">
			<h1>Welcome ${userInSession.firstName} !</h1>
			<a href="/logout" class="btn btn-danger">Logout</a>
		</nav>
		<a href="/new" class="btn btn-success">+ New Project</a>
		<div class="row">
			<h2>All Projects</h2>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Project</th>
						<th>Team Lead</th>
						<th>Due Date</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${otherProjects}" var="p">
						<tr>
							<td>${p.title}</td>
							<td>${p.lead.firstName}</td>
							<td>${p.dueDate}</td>
							<td>
								<a href="/join/${p.id}" class="btn btn-info">Join Team</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="row">
			<h2>Your Projects</h2>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Project</th>
						<th>Team Lead</th>
						<th>Due Date</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${myProjects}" var="p">
						<tr>
							<td>${p.title}</td>
							<td>${p.lead.firstName}</td>
							<td>${p.dueDate}</td>
							<td>
								
								<c:if test="${p.lead.id == userInSession.id}">
									<a href="/edit/${p.id}" class="btn btn-warning">Edit</a>
								</c:if>
								
								<c:if test="${p.lead.id != userInSession.id}">
									<a href="/leave/${p.id}" class="btn btn-danger">Leave Team</a>
								</c:if>	
								
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>