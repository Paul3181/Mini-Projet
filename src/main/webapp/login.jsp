<%-- 
    Document   : login
    Created on : 21 nov. 2017, 13:29:51
    Author     : pauld
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Espace de connexion</h1>
        
        <%--
	La servlet fait : request.setAttribute("errorMessage", "Login/Password incorrect");
	La JSP récupère cette valeur dans ${errorMessage}
	--%>
	<div style="color:red">${errorMessage}</div>
        
        <form action="<c:url value="/" />" method="POST">
                Identifiant:<input type="text" name="email" ><br>
                Mot de passe:<input type="text" name="pass" ><br>
                <input type='submit' name='action' value='login'>
        </form>

        
    </body>
</html>
