<%-- 
    Document   : agregaPuesto
    Created on : 22/03/2017, 10:22:39 PM
    Author     : nancy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
	<meta charset="utf-8">
	<link type="text/css" rel="stylesheet" href="stylesheet.css"/>
	<title>Agrega Puesto</title>
    </head>
    <body>
    <h1>Agrega Puesto</h1>
    <form>
	<fieldset>
            <label>Nombre del Puesto:</label>
            <input type="text" name="puesto">
            <br><br>
            <label>Descripción:</label>
            <textarea></textarea>
            <br><br>
            <label>Ubicación:</label>
            <input type="text" name="ubicacion">
            <br><br>
            <label>Imagen del Puesto:</label>
            <input type="text" name="imagen">
	</fieldset>
            <br><br>
            <input type="submit" name="agregar" value="Agregar">
    </form>
    </body>
</html>
