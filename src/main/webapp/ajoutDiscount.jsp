<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Saisie d'un taux de remise</title>
	</head>
	<body>
		<!-- On montre le formulaire de saisie -->
		<h1>Test pour git</h1>
		<form method='GET'>
                    Code : <input name="code" size="1" maxlength="1" pattern="[A-Z]{1}+" title="Une lettre en MAJUSCULES"><br/>
		    Taux : <input name="taux" type="number" step="0.01" min="0.0" max="99.99" size="5"><br/>
			<input type="hidden" name="action" value="ADD">
			<input type="submit" value="Ajouter">
		</form>
		<%--  On montre un éventuel message d'erreur --%>
		<div><h4>${message}</h4></div>
		<%-- On on montre la liste des discount codes --%>
		<div>
			<table border="1">
				<tr><th>Code</th><th>Taux</th><th>Action</th></tr>
				<c:forEach var="record" items="${codes}">
					<tr>
						<td>${record.discountCode}</td>
						<td><fmt:formatNumber value="${record.rate  / 100}"  type="percent" minFractionDigits="2" minIntegerDigits="2" maxFractionDigits="2" maxIntegerDigits="2"/></td>
						<td><a href="?action=DELETE&code=${record.discountCode}">delete</a></td>
					</tr>	  		    
				</c:forEach>  
			</table>
		</div>
	</body>        
</html>
