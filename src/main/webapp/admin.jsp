<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Espace admin</title>
                
                <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
                <script type="text/javascript">
                  google.charts.load("current", {
                    "packages":["map"],
                    // Note: you will need to get a mapsApiKey for your project.
                    // See: https://developers.google.com/chart/interactive/docs/basic_load_libs#load-settings
                    "mapsApiKey": "AIzaSyD-9tSrke72PouQMnMX-a7eZSW0jkFMBWY"
                });
                  google.charts.setOnLoadCallback(drawChart);
                  function drawChart() {
                    var data = google.visualization.arrayToDataTable([
                      ['Lat', 'Long', 'Name'],
                      [37.4232, -122.0853, 'Work'],
                      [37.4289, -122.1697, 'University'],
                      [37.6153, -122.3900, 'hehe'],
                      [37.4422, -122.1731, 'Shopping']
                    ]);

                    var map = new google.visualization.Map(document.getElementById('map_div'));
                    map.draw(data, {
                      showTooltip: true,
                      showInfoWindow: true
                    });
                  }

                </script>
	</head>
	<body>
		<%--  On montre un éventuel message d'erreur --%>
		<div><h4>${message}</h4></div>
                
                
                <h1>Espace Admin</h1>
                             
                <form action="<c:url value="/"/>" method="POST"> 
			<input type='submit' name='action' value='logout'>
		</form>
                        
                <form action="Admin" method="POST"> 
                    <label for="dateDebut">Date début;</label><input type="date" name="dateDebut" required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}"> <br>
                    <label for="dateFin">Date fin;</label><input type="date" name="dateFin" required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}"><br>
                    <input type='submit' name='action' value='SeeStats'>
		</form>     
                
                        
                <div id="map_div" style="width: 400px; height: 300px"></div>
                
                ${donneesloc}
	</body>        
</html>
