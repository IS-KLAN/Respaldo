<!DOCTYPE html>
<html>
  <head>
	<title>YumYum - Detalles de Puesto</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  </head>  
  <body>
    <div class="container">
	<table>
		<tr>
			<td>
			    <h1>Puesto</h1>
			    <img src="puesto.jpg" class="img-rounded" alt="Cinque Terre" width="330" height="300">
			</td>
			<td> 
			    <h2>Comentarios</h2>
				<div class="list-group">
				  <a href="#" class="list-group-item active">Ejemplo de comentario sobre el puesto 1</a>
				  <a href="#" class="list-group-item active">Ejemplo de comentario sobre el puesto 2</a>
				  <a href="#" class="list-group-item active">Ejemplo de comentario sobre el puesto 3</a>
				  <a href="#" class="list-group-item active">Ejemplo de comentario sobre el puesto 4</a>
				  <a href="#" class="list-group-item active">Ejemplo de comentario sobre el puesto 5</a>
				  <a href="#" class="list-group-item active">Ejemplo de comentario sobre el puesto 6</a>
				  <a href="#" class="list-group-item active">Ejemplo de comentario sobre el puesto 7</a>
				  <a href="#" class="list-group-item active">Ejemplo de comentario sobre el puesto 8</a>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<h2>Ubicacion:</h2>
				<div id="googleMap" style="width:350px;height:300px"></div>
				<script>
					function myMap() {
						var myCenter = new google.maps.LatLng(19.324513, -99.179225);
						var mapCanvas = document.getElementById("googleMap");
						var mapOptions = {center: myCenter, zoom: 19};
						var map = new google.maps.Map(mapCanvas, mapOptions);  
						var myLatlng = new google.maps.LatLng(19.324518, -99.179408);}
				</script>
				<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC5DX4e8Cva-RCVWbNEd36FRuSnaZAGtiQ&callback=myMap"></script>
			</td>
		</tr>
	</table>
    </div>
  </body>

</html>