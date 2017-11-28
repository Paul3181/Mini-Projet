<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Espace admin</title>
	</head>
	<body>
		<%--  On montre un éventuel message d'erreur --%>
		<div><h4>${message}</h4></div>
                
                
                <h1>Espace Admin</h1>
                             
                <form action="<c:url value="/"/>" method="POST"> 
			<input type='submit' name='action' value='logout'>
		</form>
                
	</body>        
</html>
