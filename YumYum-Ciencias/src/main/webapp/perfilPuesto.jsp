<!DOCTYPE html>
<html>
<head>
	<title></title>
	<link rel="stylesheet" href="style.css" type="text/css" media="all">
</head>
<body>
<div align="center">
	<h1> PUESTO </h1> </div>


<table>
	<tr>
		<td>
			<img src="cafeteria.jpg"  style="width:350px;height:250px">

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
	
		<td> 
			<h2> COMENTARIOS </h2>

		</td>


	</tr>
</table>

<style>
td {
    font-size: 15px;
    line-height: 20px;
    padding: 0 100px;
    text-align: center;
    vertical-align: top;
    width: 50%;
}
</style>


</body>
</html>