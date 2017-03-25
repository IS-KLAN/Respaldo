create database proyecto;

	--Creación de la tabla Usuario
	create table usuario(idUsuario integer, nombreUsuario varchar(24), correo varchar(32), contrasena varchar(16));

	--Creación de la tabla Puesto
	create table puesto(idPuesto integer, nombrePuesto varchar(32), descripcion varchar(256), enlaceUbicacion varchar(256), rutaImagen varchar(256));

	--Creación de la tabla Comida
	create table comida(idComida integer, nombreAlimento varchar(64));

	--Creación de la tabla que relacionará a la comida con los puestos
	create table comida_puesto(idComida integer, idPuesto integer);
