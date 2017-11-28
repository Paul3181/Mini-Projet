<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Espace Client</title>
	</head>
	<body>
		<%--  On montre un éventuel message d'erreur --%>
		<div><h4>${message}</h4></div>
                
                
                <h1>Espace Client</h1>
                    
                <div><p>Utilisateur : ${userName}</p></div>
                
                <form action="<c:url value="/"/>" method="POST"> 
			<input type='submit' name='action' value='logout'>
		</form>
                
                <div>
                <h3>Mon panier </h3>
                <%--<h4>${test}</h4>--%>
		<table>
                    <tr><th>Products</th><th>Price</th><th>Quantity</th></tr>
                    <tr>
                        <td></td>
                        <td></td>
                        <td><h4>${test}</h4></td>
                    </tr>	  
                </table>
		<p class="prix ">Montant Total : <span id="prix"></span> €</p>
                </div>

		<%-- On montre la liste des produits --%>
		<div>
                    <form method="GET">
			<table border="1">
				<tr><th>Products</th><th>Price</th><th>Quantity</th></tr>
				<c:forEach var="record" items="${products}">
					<tr>
                                            <td>${record.product}</td>
                                            <td><fmt:formatNumber value="${record.price}"/></td>
                                            <td><input type="number" name="qte" step="0.01"><br>
                                            <input type="hidden" name="action" value="ADD">
                                            <input type="submit" value="Ajouter"></td>
					</tr>	  		    
				</c:forEach>  
			</table>
                    </form>
		</div>
	</body>        
</html>
