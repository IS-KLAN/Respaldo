<%-- 
    Document   : registro
    Created on : 22/03/2017, 12:55:13 AM
    Author     : nancy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="utf-8">
	<link type="text/css" rel="stylesheet" href="stylesheet.css"/>
	<title>Registro</title>
	</head>

	<body>
		<h1>Registro</h1>
		<form>
			<fieldset>
				<label>Nombre de Usuario:</label>
				<input type="text" name="nombre"><br/>
				<br><br>
				<label>Correo electrónico:</label>
				<input type="text" name="correo"><br/>
				<br><br>
				<label>Contraseña:</label>
				<input type="text" name="contrasena"><br/>
				<br><br>
				<label>Confirma Contraseña:</label>
				<input type="text" name="contrasena2">
			</fieldset>
			<br><br>
			<input id="boton" type="submit" name="registrar" value="Registrar">
		</form>
	</body>
</html>
