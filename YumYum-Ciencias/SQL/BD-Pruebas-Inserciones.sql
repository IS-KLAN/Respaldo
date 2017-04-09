-- Indicamos que usaremos la base de datos yumyum_ciencias para crear las tablas
use yumyum_ciencias;

-- Se insertan personas de prueba.
INSERT INTO usuario (nombre_usuario, correo, contraseña) VALUES('karla', 'kaarla', 'passwordk');
INSERT INTO usuario (nombre_usuario, correo, contraseña) VALUES('luis', 'patlaniunam', 'passwordl');
INSERT INTO usuario (nombre_usuario, correo, contraseña) VALUES('anahí', 'anahíqj', 'passworda');
INSERT INTO usuario (nombre_usuario, correo, contraseña) VALUES('nancy', 'nancy69', 'passwordn');

-- Se insertan puestos de prueba.
INSERT INTO puesto (nombre_puesto, latitud, longitud) VALUES('harry', '-1121.0', '992.21');
INSERT INTO puesto (nombre_puesto, latitud, longitud) VALUES('puesto 1', '-1121.0', '432.53');
INSERT INTO puesto (nombre_puesto, latitud, longitud) VALUES('puesto 2', '1212.0', '-292.20');
INSERT INTO puesto (nombre_puesto, latitud, longitud) VALUES('puesto 3', '3221.0', '121.52');

-- Se insertan comidas de prueba.
INSERT INTO comida (nombre_comida) VALUES('bollos');
INSERT INTO comida (nombre_comida) VALUES('burritos');
INSERT INTO comida (nombre_comida) VALUES('crepas');
INSERT INTO comida (nombre_comida) VALUES('chilaquiles');
INSERT INTO comida (nombre_comida) VALUES('enchiladas');
INSERT INTO comida (nombre_comida) VALUES('flautas');
INSERT INTO comida (nombre_comida) VALUES('gorditas');
INSERT INTO comida (nombre_comida) VALUES('hamburguesas');
INSERT INTO comida (nombre_comida) VALUES('hot-dogs');
INSERT INTO comida (nombre_comida) VALUES('huaraches');
INSERT INTO comida (nombre_comida) VALUES('molletes');
INSERT INTO comida (nombre_comida) VALUES('nachos');
INSERT INTO comida (nombre_comida) VALUES('pizza');
INSERT INTO comida (nombre_comida) VALUES('pambazos');
INSERT INTO comida (nombre_comida) VALUES('sincronizadas');
INSERT INTO comida (nombre_comida) VALUES('quesadillas');
INSERT INTO comida (nombre_comida) VALUES('sandwich');
INSERT INTO comida (nombre_comida) VALUES('tacos');
INSERT INTO comida (nombre_comida) VALUES('tortas');
INSERT INTO comida (nombre_comida) VALUES('tostadas');
-- Bebidas a partir de id 20.
INSERT INTO comida (nombre_comida) VALUES('agua de sabor');
INSERT INTO comida (nombre_comida) VALUES('jugo');
INSERT INTO comida (nombre_comida) VALUES('refresco');
INSERT INTO comida (nombre_comida) VALUES('café');
INSERT INTO comida (nombre_comida) VALUES('licuado');

-- Se insertan comida a Harry.
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('harry', 'bollos');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('harry', 'crepas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('harry', 'flautas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('harry', 'hamburguesas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('harry', 'molletes');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('harry', 'nachos');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('harry', 'pizza');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('harry', 'quesadillas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('harry', 'tortas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('harry', 'sandwich');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('harry', 'agua de sabor');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('harry', 'jugo');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('harry', 'refresco');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('harry', 'licuado');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('harry', 'café');
-- Se insertan comida a puesto 1.
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 1', 'burritos');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 1', 'enchiladas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 1', 'gorditas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 1', 'huaraches');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 1', 'pambazos');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 1', 'sincronizadas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 1', 'tostadas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 1', 'tacos');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 1', 'hot-dogs');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 1', 'agua de sabor');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 1', 'café');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 1', 'jugo');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 1', 'licuado');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 1', 'refresco');
-- Se insertan comida a puesto 2.
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 2', 'bollos');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 2', 'burritos');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 2', 'enchiladas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 2', 'hamburguesas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 2', 'molletes');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 2', 'tacos');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 2', 'jugo');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 2', 'refresco');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 2', 'agua de sabor');
-- Se insertan comida a puesto 3.
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 3', 'bollos');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 3', 'burritos');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 3', 'crepas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 3', 'enchiladas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 3', 'gorditas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 3', 'tostadas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('puesto 3', 'refresco');

-- Se insertan comentarios a Harry.
INSERT INTO evaluacion (comentario, calificacion, nombre_puesto, nombre_usuario)
VALUES('No es el lugar más confiable.', 2, 'harry', 'luis');
INSERT INTO evaluacion (comentario, calificacion, nombre_puesto, nombre_usuario)
VALUES('Venden de todo y no es caro.', 4, 'harry', 'anahí');
-- Se insertan comentarios a puesto 1.
INSERT INTO evaluacion (comentario, calificacion, nombre_puesto, nombre_usuario)
VALUES('No es el lugar más confiable.', 2, 'puesto 1', 'karla');
INSERT INTO evaluacion (comentario, calificacion, nombre_puesto, nombre_usuario)
VALUES('Venden de todo y no es caro.', 4, 'puesto 1', 'nancy');