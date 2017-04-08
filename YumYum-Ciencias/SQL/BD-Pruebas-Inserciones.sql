-- Indicamos que usaremos la base de datos yumyum_ciencias para crear las tablas
use yumyum_ciencias;

-- Se insertan personas de prueba.
INSERT INTO persona (nombre_usuario, correo, contraseña) VALUES('karla', 'kaarla', 'passwordk');
INSERT INTO persona (nombre_usuario, correo, contraseña) VALUES('luis', 'patlaniunam', 'passwordl');
INSERT INTO persona (nombre_usuario, correo, contraseña) VALUES('anahí', 'anahíqj', 'passworda');
INSERT INTO persona (nombre_usuario, correo, contraseña) VALUES('nancy', 'nancy69', 'passwordn');

-- Se insertan confirmaciones de usuarios de prueba.
INSERT INTO usuario (id_persona) VALUES(1);
INSERT INTO usuario (id_persona) VALUES(2);
INSERT INTO usuario (id_persona) VALUES(3);
INSERT INTO usuario (id_persona) VALUES(4);

-- Se insertan puestos de prueba.
INSERT INTO puesto (nombre_puesto, latitud, longitud) VALUES('harry', '-1121.0', '992.21');
INSERT INTO puesto (nombre_puesto, latitud, longitud) VALUES('puesto 1', '-1121.0', '432.53');
INSERT INTO puesto (nombre_puesto, latitud, longitud) VALUES('puesto 2', '1212.0', '-292.20');
INSERT INTO puesto (nombre_puesto, latitud, longitud) VALUES('puesto 3', '3221.0', '121.52');

-- Se insertan comidas de prueba.
INSERT INTO comida (nombre_alimento) VALUES('bollos');
INSERT INTO comida (nombre_alimento) VALUES('burritos');
INSERT INTO comida (nombre_alimento) VALUES('crepas');
INSERT INTO comida (nombre_alimento) VALUES('chilaquiles');
INSERT INTO comida (nombre_alimento) VALUES('enchiladas');
INSERT INTO comida (nombre_alimento) VALUES('flautas');
INSERT INTO comida (nombre_alimento) VALUES('gorditas');
INSERT INTO comida (nombre_alimento) VALUES('hamburguesas');
INSERT INTO comida (nombre_alimento) VALUES('huaraches');
INSERT INTO comida (nombre_alimento) VALUES('molletes');
INSERT INTO comida (nombre_alimento) VALUES('nachos');
INSERT INTO comida (nombre_alimento) VALUES('pizza');
INSERT INTO comida (nombre_alimento) VALUES('pambazos');
INSERT INTO comida (nombre_alimento) VALUES('sincronizadas');
INSERT INTO comida (nombre_alimento) VALUES('quesadillas');
INSERT INTO comida (nombre_alimento) VALUES('sandwich');
INSERT INTO comida (nombre_alimento) VALUES('tacos');
INSERT INTO comida (nombre_alimento) VALUES('tortas');
INSERT INTO comida (nombre_alimento) VALUES('tostadas');
-- Bebidas a partir de id 20.
INSERT INTO comida (nombre_alimento) VALUES('agua de sabor');
INSERT INTO comida (nombre_alimento) VALUES('jugo');
INSERT INTO comida (nombre_alimento) VALUES('refresco');
INSERT INTO comida (nombre_alimento) VALUES('café');
INSERT INTO comida (nombre_alimento) VALUES('licuado');

-- Se insertan comida a Harry.
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('1', '1');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('1', '3');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('1', '5');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('1', '7');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('1', '9');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('1', '11');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('1', '13');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('1', '15');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('1', '17');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('1', '19');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('1', '20');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('1', '21');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('1', '22');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('1', '23');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('1', '24');
-- Se insertan comida a puesto 1.
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('2', '2');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('2', '4');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('2', '6');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('2', '8');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('2', '10');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('2', '12');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('2', '14');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('2', '16');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('2', '18');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('2', '20');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('2', '21');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('2', '22');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('2', '23');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('2', '24');
-- Se insertan comida a puesto 2.
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('3', '1');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('3', '2');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('3', '4');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('3', '8');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('3', '16');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('3', '20');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('3', '21');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('3', '22');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('3', '24');
-- Se insertan comida a puesto 3.
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('4', '1');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('4', '2');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('4', '3');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('4', '5');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('4', '8');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('4', '13');
INSERT INTO comida_puesto (id_puesto, id_comida) VALUES('4', '21');

-- Se insertan comentarios a Harry.
INSERT INTO evaluacion (comentario, calificacion, id_puesto, id_usuario) VALUES('No es el lugar más confiable.', 2, 1, 2);
INSERT INTO evaluacion (comentario, calificacion, id_puesto, id_usuario) VALUES('Venden de todo y no es caro.', 4, 1, 3);
-- Se insertan comentarios a puesto 1.
INSERT INTO evaluacion (comentario, calificacion, id_puesto, id_usuario) VALUES('No es el lugar más confiable.', 2, 2, 1);
INSERT INTO evaluacion (comentario, calificacion, id_puesto, id_usuario) VALUES('Venden de todo y no es caro.', 4, 2, 4);