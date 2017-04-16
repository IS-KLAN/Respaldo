-- Creamos la base de datos
create database if not exists yumyum_ciencias;

-- Indicamos que usaremos la base de datos yumyum_ciencias para crear las tablas
use yumyum_ciencias;

-- Borramos las siguientes tablas si ya existen en la BD
drop table if exists comida_puesto cascade;
drop table if exists evaluacion cascade;
drop table if exists comida cascade;
drop table if exists puesto cascade;
drop table if exists usuario cascade;
drop table if exists pendiente cascade;

-- Creación de la tabla Usuario
create table if not exists pendiente (
  id_usuario serial,
  nombre_usuario varchar(64) primary key,
  correo varchar(32) not null,
  contraseña varchar(32) not null,
  unique key (correo)
) engine=InnoDB default charset=utf8;

-- Creación de la tabla Persona
create table if not exists usuario (
  id_usuario serial,
  nombre_usuario varchar(64) primary key,
  correo varchar(32) not null,
  contraseña varchar(32) not null,
  unique key (correo)
) engine=InnoDB default charset=utf8;

-- Creación de la tabla Puesto
create table if not exists puesto (
  id_puesto serial,
  nombre_puesto varchar(64) primary key,
  descripcion varchar(255),
  latitud varchar(16) not null,
  longitud varchar(16) not null,
  rutaImagen varchar(255),
  unique key (latitud, longitud)
) engine=InnoDB default charset=utf8;

-- Creación de la tabla Comida
create table if not exists comida (
  id_comida serial, 
  nombre_comida varchar(64) primary key
) engine=InnoDB default charset=utf8;

-- Creación de la tabla evaluación
create table if not exists evaluacion (
  id_evaluacion serial, 
  comentario varchar(255),
  calificacion integer not null,
  nombre_puesto varchar(64) not null,
  nombre_usuario varchar(64) not null,
  primary key(nombre_puesto, nombre_usuario),
  foreign key(nombre_puesto)
  references puesto(nombre_puesto),
  foreign key(nombre_usuario)
  references usuario(nombre_usuario)
) engine=InnoDB default charset=utf8;

-- Creación de la tabla que relacionará a la comida con los puestos
create table if not exists comida_puesto (  
  id_comida_puesto serial, 
  nombre_comida varchar(64) not null,
  nombre_puesto varchar(64) not null,
  primary key(nombre_comida, nombre_puesto),
  foreign key(nombre_comida)
  references comida(nombre_comida),
  foreign key(nombre_puesto)
  references puesto(nombre_puesto)
) engine=InnoDB default charset=utf8;
