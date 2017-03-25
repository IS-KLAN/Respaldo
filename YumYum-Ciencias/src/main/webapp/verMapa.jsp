<!DOCTYPE html>
<html>

<body>
	<div align="center">
		<h1>Yum Yum Ciencias</h1>
		<div id="googleMap" style="width:900px;height:550px"></div>
	</div>

	<script>
		function myMap() {
			var myCenter = new google.maps.LatLng(19.324683, -99.178633);
			var mapCanvas = document.getElementById("googleMap");
			var mapOptions = {center: myCenter, zoom: 18};
			var map = new google.maps.Map(mapCanvas, mapOptions);
			var marker = new google.maps.Marker({position:myCenter});
			var myLatlng = new google.maps.LatLng(19.324518, -99.179408);
			marker.setMap(map);}
	</script>

	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC5DX4e8Cva-RCVWbNEd36FRuSnaZAGtiQ&callback=myMap"></script>

</body>
</html>

