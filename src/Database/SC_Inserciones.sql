-- Insertar Roles
INSERT INTO Roles (Nombre, Descripcion) VALUES
('Empleado', 'Usuario con permisos para operaciones básicas del cine.'),
('Administrador', 'Usuario con todos los permisos del sistema.');

-- Insertar Empleados (10 empleados y 1 administrador)
INSERT INTO Empleados (RolID, Nombre, Apellido, Usuario, Contraseña, Email, Telefono, FechaContratacion, Direccion) VALUES
(1, 'Ana', 'Pérez', 'ana.perez', 'pbkdf2:sha256:600000:salt:hash', 'ana.perez@cineplanet.cl', '+56912345678', '2024-05-15', 'Los Boldos 123, Colina'),
(1, 'Carlos', 'González', 'carlos.gonzalez', 'pbkdf2:sha256:600000:salt:hash', 'carlos.gonzalez@cineplanet.cl', '+56987654321', '2024-06-20', 'San Martín 456, Lampa'),
(1, 'Sofía', 'Martínez', 'sofia.martinez', 'pbkdf2:sha256:600000:salt:hash', 'sofia.martinez@cineplanet.cl', '+56955551212', '2024-07-01', 'Independencia 789, Santiago'),
(1, 'Javier', 'Soto', 'javier.soto', 'pbkdf2:sha256:600000:salt:hash', 'javier.soto@cineplanet.cl', '+56911223344', '2024-08-10', 'Arturo Prat 101, Puente Alto'),
(1, 'Valentina', 'Rojas', 'valentina.rojas', 'pbkdf2:sha256:600000:salt:hash', 'valentina.rojas@cineplanet.cl', '+56999887766', '2024-09-05', 'Avenida La Florida 222, La Florida'),
(1, 'Sebastián', 'Flores', 'sebastian.flores', 'pbkdf2:sha256:600000:salt:hash', 'sebastian.flores@cineplanet.cl', '+56933445566', '2024-10-12', 'Camino El Alba 333, Las Condes'),
(1, 'Camila', 'Vargas', 'camila.vargas', 'pbkdf2:sha256:600000:salt:hash', 'camila.vargas@cineplanet.cl', '+56977889900', '2024-11-18', 'Gran Avenida 444, San Bernardo'),
(1, 'Andrés', 'Silva', 'andres.silva', 'pbkdf2:sha256:600000:salt:hash', 'andres.silva@cineplanet.cl', '+56944556677', '2024-12-01', 'Maipú 555, Maipú'),
(1, 'Isidora', 'López', 'isidora.lopez', 'pbkdf2:sha256:600000:salt:hash', 'isidora.lopez@cineplanet.cl', '+56966778899', '2025-01-15', 'Pedro de Valdivia 666, Providencia'),
(1, 'Martín', 'Núñez', 'martin.nunez', 'pbkdf2:sha256:600000:salt:hash', 'martin.nunez@cineplanet.cl', '+56922334455', '2025-02-20', 'Vitacura 777, Vitacura'),
(2, 'Ricardo', 'Fuentes', 'admin', 'pbkdf2:sha256:600000:adminsalt:adminhash', 'ricardo.fuentes@cineplanet.cl', '+56911112222', '2024-05-01', 'Oficina Central, Santiago');

-- Insertar Peliculas
INSERT INTO Peliculas (Titulo, Sinopsis, Genero, Director, ActoresPrincipales, FechaLanzamiento, IdiomaOriginal, SubtitulosDisponibles, DuracionMinutos, Clasificacion, PosterURL) VALUES
('Aventura Cósmica', 'Un grupo de exploradores viaja a través de la galaxia en busca de nuevos mundos.', 'Ciencia Ficción', 'Elena Ríos', 'Javier Bardem, Anya Taylor-Joy, Keanu Reeves', '2025-06-10', 'Inglés', 'Español', 145, 'PG-13', '/posters/aventuracosmica.jpg'),
('Misterio en la Mansión Antigua', 'Una joven hereda una misteriosa mansión y debe desentrañar sus secretos.', 'Thriller', 'Diego Soto', 'Florence Pugh, Timothée Chalamet, Olivia Colman', '2025-07-05', 'Inglés', 'Español', 112, 'PG', '/posters/misteriomansion.jpg'),
('El Último Samurai 2.0', 'La leyenda renace en un Japón futurista.', 'Acción', 'Kenji Tanaka', 'Hiroyuki Sanada, Gemma Chan, Tadanobu Asano', '2025-08-20', 'Japonés', 'Español, Inglés', 130, 'R', '/posters/ultimosamurai2.jpg'),
('Comedia de Enredos Familiares', 'Una boda caótica revela los secretos más divertidos de una familia.', 'Comedia', 'Isabel Flores', 'Penélope Cruz, Antonio Banderas, Carmen Maura', '2025-09-15', 'Español', 'Inglés', 98, 'G', '/posters/comediaenredos.jpg'),
('Fantasía y Dragones', 'Un joven campesino descubre que está destinado a salvar un reino mágico.', 'Fantasía', 'Liam O\'Connell', 'Tom Holland, Zendaya, Ralph Fiennes', '2025-10-25', 'Inglés', 'Español', 160, 'PG-13', '/posters/fantasiadragones.jpg'),
('El Secreto del Abismo', 'Un equipo de buzos explora las profundidades del océano y encuentra algo inesperado.', 'Terror', 'Sofía Vargas', 'Charlize Theron, Michael Fassbender, Naomi Scott', '2025-11-01', 'Inglés', 'Español', 105, 'R', '/posters/secretodelabismo.jpg'),
('Animación Aventura en la Selva', 'Un grupo de animales debe trabajar juntos para proteger su hogar.', 'Animación', 'Carlos Ruiz', 'Voces de: Ricardo Darín, Cecilia Roth, Guillermo Francella', '2025-12-12', 'Español', 'Inglés', 90, 'G', '/posters/animacionselva.jpg'),
('Drama Histórico: La Revolución Olvidada', 'La historia de un levantamiento poco conocido en el siglo XIX.', 'Drama', 'Mateo López', 'Daniel Giménez Cacho, Ana de la Reguera, Diego Luna', '2026-01-20', 'Español', 'Inglés', 125, 'PG-13', '/posters/revolucionolvidada.jpg'),
('Musical: La Ciudad de las Estrellas 2', 'El regreso de una historia de amor en la vibrante ciudad.', 'Musical', 'Damien Chazelle', 'Ryan Gosling, Emma Stone, John Legend', '2026-02-14', 'Inglés', 'Español', 150, 'PG-13', '/posters/ciudadestrellas2.jpg'),
('Documental: Los Secretos del Universo', 'Un viaje visual a través de los misterios del cosmos.', 'Documental', 'Varios', 'Narrado por: Morgan Freeman', '2026-03-05', 'Inglés', 'Español', 100, 'G', '/posters/secretosuniverso.jpg');

-- Insertar TiposSala
INSERT INTO TiposSala (Nombre, Descripcion) VALUES
('Estándar', 'Sala de cine tradicional.'),
('3D', 'Sala con tecnología para proyección en tres dimensiones.'),
('IMAX', 'Sala con pantalla de gran formato y sonido inmersivo.'),
('VIP', 'Sala con asientos de lujo y servicios adicionales.'),
('Premium', 'Sala con características mejoradas, como sonido avanzado.');

-- Insertar Salas
INSERT INTO Salas (TipoSalaID, Numero, Capacidad, DescripcionAdicional) VALUES
(1, 1, 150, 'Sala estándar principal.'),
(1, 2, 120, 'Sala estándar secundaria.'),
(2, 3, 100, 'Sala con proyección 3D.'),
(3, 4, 80, 'Sala con pantalla IMAX.'),
(4, 5, 50, 'Sala VIP con asientos reclinables.'),
(1, 6, 130, 'Sala estándar con buen sonido.'),
(2, 7, 90, 'Sala 3D más pequeña.'),
(3, 8, 60, 'Sala IMAX secundaria.'),
(4, 9, 40, 'Sala VIP exclusiva.'),
(5, 10, 110, 'Sala premium con sonido Dolby Atmos.');

-- Insertar Funciones
INSERT INTO Funciones (PeliculaID, SalaID, FechaHora, PrecioBase, Estado, Formato) VALUES
(1, 1, '2025-06-15 18:00:00', 5500, 'Programada', '2D'),
(2, 2, '2025-07-10 20:30:00', 5000, 'Programada', '2D'),
(3, 3, '2025-08-25 19:00:00', 7000, 'Programada', '3D'),
(4, 4, '2025-09-20 21:15:00', 6500, 'Programada', 'IMAX'),
(5, 5, '2025-10-30 17:30:00', 8000, 'Programada', '2D'),
(6, 1, '2025-11-05 22:00:00', 5500, 'Programada', '2D'),
(7, 2, '2025-12-15 16:00:00', 4500, 'Programada', 'Doblada'),
(8, 3, '2026-01-25 20:00:00', 7000, 'Programada', '3D Subtitulada'),
(9, 4, '2026-02-20 18:30:00', 6500, 'Programada', 'IMAX'),
(10, 5, '2026-03-10 21:00:00', 7500, 'Programada', '2D');

-- Insertar Clientes
INSERT INTO Clientes (Nombre, Apellido, Email, Telefono, FechaRegistro) VALUES
('Laura', 'Jiménez', 'laura.jimenez@email.com', '+56998765432', '2025-03-01'),
('Pedro', 'Vargas', 'pedro.vargas@email.com', '+56911223344', '2025-03-10'),
('Catalina', 'Soto', 'catalina.soto@email.com', '+56955556666', '2025-03-15'),
('Andrés', 'Rojas', 'andres.rojas@email.com', '+56977778888', '2025-03-20'),
('Javiera', 'Flores', 'javiera.flores@email.com', '+56933334444', '2025-03-25'),
('Diego', 'Pérez', 'diego.perez@email.com', '+56922225555', '2025-04-01'),
('Valentina', 'González', 'valentina.gonzalez@email.com', '+56988881111', '2025-04-05'),
('Martín', 'Martínez', 'martin.martinez@email.com', '+56944449999', '2025-04-10'),
('Sofía', 'Silva', 'sofia.silva@email.com', '+56966662222', '2025-04-15'),
('Benjamín', 'López', 'benjamin.lopez@email.com', '+56912348765', '2025-04-20');

-- Insertar PrecioEntradas
INSERT INTO PrecioEntradas (Nombre, Precio, DescripcionAdicional) VALUES
('Adulto', 4500, 'Precio general para adultos.'),
('Niño', 3000, 'Precio para niños menores de 12 años.'),
('Estudiante', 3500, 'Precio con credencial de estudiante.'),
('Tercera Edad', 3200, 'Precio para personas mayores de 65 años.'),
('3D Adulto', 6000, 'Precio para adultos en funciones 3D.'),
('3D Niño', 4000, 'Precio para niños en funciones 3D.'),
('IMAX Adulto', 7500, 'Precio para adultos en funciones IMAX.'),
('IMAX Estudiante', 6000, 'Precio para estudiantes en funciones IMAX.'),
('VIP Adulto', 9000, 'Precio para adultos en sala VIP.'),
('Promoción Martes', 3800, 'Precio especial los días martes.');

-- Insertar Entradas
INSERT INTO Entradas (FuncionID, ClienteID, PrecioEntradaID, NumeroFila, NumeroAsiento, FechaVenta, Estado) VALUES
(1, 1, 1, 'A', 5, '2025-06-14 20:00:00', 'Vendida'),
(2, 2, 2, 'B', 10, '2025-07-09 15:30:00', 'Vendida'),
(3, 3, 5, 'C', 3, '2025-08-24 11:00:00', 'Vendida'),
(4, 4, 7, 'D', 15, '2025-09-19 18:45:00', 'Vendida'),
(5, 5, 1, 'E', 8, '2025-10-29 22:15:00', 'Vendida'),
(6, 6, 4, 'A', 7, '2025-11-04 14:00:00', 'Vendida'),
(7, 7, 2, 'B', 12, '2025-12-14 19:30:00', 'Vendida'),
(8, 8, 6, 'C', 5, '2026-01-24 10:00:00', 'Vendida'),
(9, 9, 8, 'D', 18, '2026-02-19 16:15:00', 'Vendida'),
(10, 10, 3, 'E', 2, '2026-03-09 20:45:00', 'Vendida');

-- Insertar CategoriasProducto
INSERT INTO CategoriasProducto (Nombre, Descripcion) VALUES
('Popcorn', 'Variedades de palomitas de maíz.'),
('Bebidas', 'Gaseosas, jugos y agua.'),
('Snacks', 'Papas fritas, nachos y otros snacks salados.'),
('Dulces', 'Chocolates, gomitas y otros dulces.'),
('Combos', 'Ofertas que incluyen popcorn y bebida.');

-- Insertar Productos
INSERT INTO Productos (CategoriaID, Nombre, Descripcion, PrecioUnitario, Stock) VALUES
(1, 'Popcorn Salado Pequeño', 'Palomitas de maíz saladas, tamaño pequeño.', 2500, 100),
(1, 'Popcorn Salado Mediano', 'Palomitas de maíz saladas, tamaño mediano.', 3500, 80),
(1, 'Popcorn Dulce Grande', 'Palomitas de maíz dulces, tamaño grande.', 4000, 60),
(2, 'Coca-Cola 500ml', 'Gaseosa Coca-Cola, botella de 500ml.', 2000, 120),
(2, 'Fanta Naranja 500ml', 'Gaseosa Fanta sabor naranja, botella de 500ml.', 2000, 100),
(3, 'Papas Fritas Clásicas', 'Papas fritas corte clásico, porción individual.', 3000, 90),
(3, 'Nachos con Queso', 'Porción de nachos con salsa de queso.', 4500, 70),
(4, 'Chocolate Snickers', 'Barra de chocolate Snickers.', 1500, 150),
(4, 'Gomitas Ositos', 'Bolsa de gomitas de ositos.', 1800, 130),
(5, 'Combo Clásico', 'Popcorn mediano y Coca-Cola 500ml.', 5000, 50);

-- Insertar Ventas
INSERT INTO Ventas (EmpleadoID, FechaHora, TipoVenta, MetodoPago, PrecioTotal) VALUES
(1, '2025-06-14 20:15:00', 'Entradas', 'Tarjeta de Crédito', 9000.00),
(2, '2025-07-09 15:45:00', 'Entradas', 'Efectivo', 3000.00),
(3, '2025-08-24 11:10:00', 'Entradas', 'Tarjeta de Débito', 14000.00),
(4, '2025-09-19 19:00:00', 'Entradas', 'Tarjeta de Crédito', 7500.00),
(5, '2025-10-29 22:25:00', 'Entradas', 'Efectivo', 4500.00),
(1, '2025-11-04 14:10:00', 'Confitería', 'Tarjeta de Débito', 6000.00),
(2, '2025-12-14 19:40:00', 'Confitería', 'Efectivo', 3500.00),
(3, '2026-01-24 10:15:00', 'Combo', 'Tarjeta de Crédito', 5000.00),
(4, '2026-02-19 16:30:00', 'Entradas', 'Efectivo', 12000.00),
(5, '2026-03-09 21:10:00', 'Entradas', 'Tarjeta de Débito', 3500.00);

-- Insertar DetallesVenta
INSERT INTO DetallesVenta (VentaID, ProductoID, Cantidad, PrecioUnitarioVenta, Descuento) VALUES
(6, 1, 1, 2500.00, 0.00),
(6, 4, 2, 2000.00, 0.00),
(7, 3, 1, 4000.00, 0.10),
(7, 8, 1, 1500.00, 0.00),
(8, 10, 1, 5000.00, 0.00),
(6, 7, 1, 4500.00, 0.05),
(7, 5, 1, 2000.00, 0.00),
(8, 2, 1, 3500.00, 0.00),
(9, 1, 2, 2500.00, 0.00),
(10, 9, 1, 1800.00, 0.00);
