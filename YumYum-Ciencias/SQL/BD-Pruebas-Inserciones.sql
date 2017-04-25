-- Indicamos que usaremos la base de datos yumyum_ciencias para crear las tablas
use yumyum_ciencias;

-- Se insertan personas de prueba.
INSERT INTO usuario (nombre_usuario, correo, contraseña) VALUES('hanna', 'hannao', 'passwordh');
INSERT INTO usuario (nombre_usuario, correo, contraseña) VALUES('isael', 'isael', 'passwordi');
INSERT INTO usuario (nombre_usuario, correo, contraseña) VALUES('miguel', 'miguelp', 'passwordmp');

-- Se insertan puestos de prueba.
INSERT INTO puesto (nombre_puesto, descripcion, latitud, longitud, rutaImagen)
VALUES('Cafesin','Este es un puesto ideal para encontrar todo tipo de comida', '19.324927', '-99.182147','N1.jpg');
INSERT INTO puesto (nombre_puesto, descripcion, latitud, longitud, rutaImagen)
VALUES('N2','Es un puesto del estacionamiento', '19.323543', '-99.178098','N2.jpg');
INSERT INTO puesto (nombre_puesto, descripcion, latitud, longitud, rutaImagen)
VALUES('N3','Es un puesto del estacionamiento', '19.323485', '-99.180744','N3.jpg');
INSERT INTO puesto (nombre_puesto, descripcion, latitud, longitud, rutaImagen)
VALUES('Harry', 'Este es un puesto ideal para encontrar todo tipo de comida', '19.324332', ' -99.179154', 'Harry.jpg');

-- Se insertan comida a Harry.
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Harry', 'bollos');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Harry', 'crepas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Harry', 'flautas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Harry', 'hamburguesas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Harry', 'molletes');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Harry', 'nachos');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Harry', 'pizza');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Harry', 'quesadillas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Harry', 'tortas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Harry', 'sandwich');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Harry', 'agua de sabor');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Harry', 'jugo');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Harry', 'refresco');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Harry', 'licuado');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Harry', 'café');
-- Se insertan comida a Cafesin.
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Cafesin', 'burritos');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Cafesin', 'enchiladas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Cafesin', 'gorditas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Cafesin', 'huaraches');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Cafesin', 'pambazos');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Cafesin', 'sincronizadas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Cafesin', 'tostadas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Cafesin', 'tacos');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Cafesin', 'hot-dogs');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Cafesin', 'agua de sabor');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Cafesin', 'café');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Cafesin', 'jugo');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Cafesin', 'licuado');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('Cafesin', 'refresco');
-- Se insertan comida a N2.
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('N2', 'bollos');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('N2', 'burritos');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('N2', 'enchiladas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('N2', 'hamburguesas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('N2', 'molletes');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('N2', 'tacos');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('N2', 'jugo');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('N2', 'refresco');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('N2', 'agua de sabor');

-- Se insertan comentarios a Harry.
INSERT INTO evaluacion (comentario, calificacion, nombre_puesto, nombre_usuario)
VALUES('Es más la mala fama que tiene, porque me gusta su comida.', 1, 'Harry', 'hanna');
-- Se insertan comentarios a Cafesin.
INSERT INTO evaluacion (comentario, calificacion, nombre_puesto, nombre_usuario)
VALUES('La comida es muy buena y barata.', 4, 'Cafesin', 'hanna');
INSERT INTO evaluacion (comentario, calificacion, nombre_puesto, nombre_usuario)
VALUES('No siempre tienen lo que dicen vender.', 3, 'Cafesin', 'miguel');
INSERT INTO evaluacion (comentario, calificacion, nombre_puesto, nombre_usuario)
VALUES('A veces tardan mucho en atender.', 2, 'Cafesin', 'isael');