-- Indicamos que usaremos la base de datos yumyum_ciencias para crear las tablas
use yumyum_ciencias;

-- Se insertan personas de prueba.
INSERT INTO usuario (nombre_usuario, correo, contraseña) VALUES('karla', 'kaarla', 'passwordk');
INSERT INTO usuario (nombre_usuario, correo, contraseña) VALUES('luis', 'patlaniunam', 'passwordl');
INSERT INTO usuario (nombre_usuario, correo, contraseña) VALUES('anahí', 'anahíqj', 'passworda');
INSERT INTO usuario (nombre_usuario, correo, contraseña) VALUES('nancy', 'nancy69', 'passwordn');
INSERT INTO usuario (nombre_usuario, correo, contraseña) VALUES('hanna', 'hannao', 'passwordh');
INSERT INTO usuario (nombre_usuario, correo, contraseña) VALUES('isael', 'isael1', 'passwordi');
INSERT INTO usuario (nombre_usuario, correo, contraseña) VALUES('miguel', 'miguelpinia', 'passwordmp');

-- Se insertan puestos de prueba.
INSERT INTO puesto (nombre_puesto, descripcion, latitud, longitud, rutaImagen)
VALUES('Cafesin','Este es un puesto ideal para encontrar todo tipo de comida', '19.324927', '-99.182147','N1.jpg');
INSERT INTO puesto (nombre_puesto, descripcion, latitud, longitud, rutaImagen)
VALUES('N2','Es un puesto del estacionamiento', '19.323543', '-99.178098','N2.jpg');
INSERT INTO puesto (nombre_puesto, descripcion, latitud, longitud, rutaImagen)
VALUES('N3','Es un puesto del estacionamiento', '19.323485', '-99.180744','N3.jpg');
INSERT INTO puesto (nombre_puesto, descripcion, latitud, longitud, rutaImagen)
VALUES('Harry', 'Este es un puesto ideal para encontrar todo tipo de comida', '19.324332', ' -99.179154', 'Harry.jpg');


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
-- Se insertan comida a N3.
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('N3', 'bollos');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('N3', 'burritos');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('N3', 'crepas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('N3', 'enchiladas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('N3', 'gorditas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('N3', 'tostadas');
INSERT INTO comida_puesto (nombre_puesto, nombre_comida) VALUES('N3', 'refresco');

-- Se insertan comentarios a Harry.
INSERT INTO evaluacion (comentario, calificacion, nombre_puesto, nombre_usuario)
VALUES('No es el lugar más confiable.', 2, 'Harry', 'luis');
INSERT INTO evaluacion (comentario, calificacion, nombre_puesto, nombre_usuario)
VALUES('Venden de todo y no es caro.', 3, 'Harry', 'anahí');
INSERT INTO evaluacion (comentario, calificacion, nombre_puesto, nombre_usuario)
VALUES('Sólo he ido a comprar una vez y nunca volví.', 1, 'Harry', 'karla');
INSERT INTO evaluacion (comentario, calificacion, nombre_puesto, nombre_usuario)
VALUES('Es más la mala fama que tiene, porque me gusta su comida.', 1, 'Harry', 'hanna');
-- Se insertan comentarios a Cafesin.
INSERT INTO evaluacion (comentario, calificacion, nombre_puesto, nombre_usuario)
VALUES('Tienen comida muy variada.', 4, 'Cafesin', 'karla');
INSERT INTO evaluacion (comentario, calificacion, nombre_puesto, nombre_usuario)
VALUES('Venden mi comida favorita.', 5, 'Cafesin', 'nancy');
INSERT INTO evaluacion (comentario, calificacion, nombre_puesto, nombre_usuario)
VALUES('La comida es muy buena y barata.', 4, 'Cafesin', 'hanna');
INSERT INTO evaluacion (comentario, calificacion, nombre_puesto, nombre_usuario)
VALUES('No siempre tienen lo que dicen vender.', 3, 'Cafesin', 'miguel');
INSERT INTO evaluacion (comentario, calificacion, nombre_puesto, nombre_usuario)
VALUES('A veces tardan mucho en atender.', 2, 'Cafesin', 'isael');
INSERT INTO evaluacion (comentario, calificacion, nombre_puesto, nombre_usuario)
VALUES('Es el mejor de los puestos del estacionamiento.', 5, 'Cafesin', 'anahí');
Contact GitHub API Training Shop Blog About

