-- Creamos la base de datos
create database yumyum_ciencias;

-- Indicamos que usaremos la base de datos yumyum_ciencias para crear las tablas
use yumyum_ciencias;

-- Borramos las siguientes tablas si ya existen en la BD
drop table if exists usuario;
drop table if exists evaluacion;
drop table if exists puesto;
drop table if exists comida;
drop table if exists persona;
drop table if exists comida_puesto;

-- Creación de la tabla Usuario
create table if not exists usuario (
  id_usuario serial,
  nombre_usuario varchar(64) not null,
  correo varchar(32) primary key,
  contraseña varchar(32) not null
) engine=InnoDB default charset=utf8;

-- Creación de la tabla evaluación
create table if not exists evaluacion(
   id_evaluacion serial primary key,
   comentario varchar(255),
   calificacion integer not null,
   id_puesto integer,
   id_usuario integer
) engine=InnoDB default charset=utf8;

-- Creación de la tabla Puesto
create table if not exists puesto(
   id_puesto serial, 
   nombre_puesto varchar(64) not null primary key, 
   descripcion varchar(255),
   latitud varchar(16) not null,
   longitud varchar(16) not null,
   rutaImagen varchar(255)
) engine=InnoDB default charset=utf8;

-- Creación de la tabla Comida
create table if not exists comida(
   id_comida serial primary key, 
   nombre_alimento varchar(64)
) engine=InnoDB default charset=utf8;

-- Creación de la tabla Persona
create table if not exists registro_temporal(
  id_usuario serial,
  nombre_usuario varchar(64) not null,
  correo varchar(32) primary key,
  contraseña varchar(32) not null
) engine=InnoDB default charset=utf8;

-- Creación de la tabla que relacionará a la comida con los puestos
create table if not exists comida_puesto(
   id_comida integer, 
   id_puesto integer,
   primary key (id_comida, id_puesto)
) engine=InnoDB default charset=utf8;