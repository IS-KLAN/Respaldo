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
drop table if exists persona cascade;

-- Creación de la tabla Persona
create table if not exists persona (
  id_persona serial primary key,
  nombre_usuario varchar(64) not null,
  correo varchar(32),
  contraseña varchar(32) not null,
  unique key (nombre_usuario),
  unique key (correo)
) engine=InnoDB default charset=utf8;

-- Creación de la tabla Usuario
create table if not exists usuario (
  id_usuario serial primary key,
  id_persona bigint(20) unsigned not null,
  unique key (id_persona),
  foreign key (id_persona)
  references persona(id_persona)
) engine=InnoDB default charset=utf8;

-- Creación de la tabla Puesto
create table if not exists puesto (
  id_puesto serial primary key,
  nombre_puesto varchar(64) not null, 
  descripcion varchar(255),
  latitud varchar(16) not null,
  longitud varchar(16) not null,
  rutaImagen varchar(255),
  unique key (nombre_puesto),
  unique key (latitud, longitud)
) engine=InnoDB default charset=utf8;

-- Creación de la tabla Comida
create table if not exists comida (
  id_comida serial primary key, 
  nombre_alimento varchar(64),
  unique key (nombre_alimento)
) engine=InnoDB default charset=utf8;

-- Creación de la tabla evaluación
create table if not exists evaluacion (
  id_evaluacion serial primary key,
  comentario varchar(255),
  calificacion integer not null,
  id_puesto bigint(20) unsigned not null,
  id_usuario bigint(20) unsigned not null,
  foreign key(id_puesto)
  references puesto(id_puesto),
  foreign key(id_usuario)
  references usuario(id_usuario)
) engine=InnoDB default charset=utf8;

-- Creación de la tabla que relacionará a la comida con los puestos
create table if not exists comida_puesto (  
  id_comida bigint(20) unsigned not null,
  id_puesto bigint(20) unsigned not null,
  primary key(id_comida, id_puesto),
  foreign key(id_comida)
  references comida(id_comida),
  foreign key(id_puesto)
  references puesto(id_puesto)
) engine=InnoDB default charset=utf8;