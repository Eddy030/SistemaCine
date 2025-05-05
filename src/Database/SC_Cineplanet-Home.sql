CREATE DATABASE CineBD;

-- Usar la base de datos
USE CineBD;

-- Tabla Roles
CREATE TABLE Roles (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(50) UNIQUE,
    Descripcion VARCHAR(255)
);

-- Tabla Empleados
CREATE TABLE Empleados (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    RolID INT,
    Nombre VARCHAR(100),
    Apellido VARCHAR(100),
    Usuario VARCHAR(100) UNIQUE,
    Contraseña VARCHAR(255), -- Almacenar hash de la contraseña
    Email VARCHAR(100) UNIQUE,
    Telefono VARCHAR(15), -- Permitir diferentes formatos
    FechaContratacion DATE,
    Direccion VARCHAR(255),
    FOREIGN KEY (RolID) REFERENCES Roles(ID)
);

-- Tabla Peliculas
CREATE TABLE Peliculas (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Titulo VARCHAR(255),
    Sinopsis TEXT,
    Genero VARCHAR(50),
    Director VARCHAR(100),
    ActoresPrincipales VARCHAR(255),
    FechaLanzamiento DATE,
    IdiomaOriginal VARCHAR(50),
    SubtitulosDisponibles VARCHAR(255),
    DuracionMinutos INT,
    Clasificacion VARCHAR(10),
    PosterURL VARCHAR(255)
);

-- Tabla TiposSala
CREATE TABLE TiposSala (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(50) UNIQUE,
    Descripcion VARCHAR(255)
);

-- Tabla Salas
CREATE TABLE Salas (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    TipoSalaID INT,
    Numero INT UNIQUE,
    Capacidad INT,
    DescripcionAdicional VARCHAR(255),
    FOREIGN KEY (TipoSalaID) REFERENCES TiposSala(ID)
);

-- Tabla Funciones
CREATE TABLE Funciones (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    PeliculaID INT,
    SalaID INT,
    FechaHora DATETIME,
    PrecioBase DECIMAL(8,2),
    Estado VARCHAR(20), -- (Programada, Cancelada, Finalizada)
    Formato VARCHAR(50), -- (2D, 3D, Doblada, Subtitulada, etc.)
    FOREIGN KEY (PeliculaID) REFERENCES Peliculas(ID),
    FOREIGN KEY (SalaID) REFERENCES Salas(ID)
);

-- Tabla Clientes
CREATE TABLE Clientes (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100),
    Apellido VARCHAR(100),
    Email VARCHAR(100) UNIQUE,
    Telefono VARCHAR(15),
    FechaRegistro DATE
);

-- Tabla PrecioEntradas
CREATE TABLE PrecioEntradas(
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(100) UNIQUE,
    Precio DECIMAL(8,2),
    DescripcionAdicional VARCHAR(255)
);

-- Tabla Entradas
CREATE TABLE Entradas (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    FuncionID INT,
    ClienteID INT,
    PrecioEntradaID INT,
    NumeroFila VARCHAR(10),
    NumeroAsiento INT,
    FechaVenta DATETIME,
    Estado VARCHAR(20), -- (Vendida, Reservada, Usada, Cancelada)
    VentaID INT,
    FOREIGN KEY (FuncionID) REFERENCES Funciones(ID),
    FOREIGN KEY (ClienteID) REFERENCES Clientes(ID),
    FOREIGN KEY (PrecioEntradaID) REFERENCES PrecioEntradas(ID),
    FOREIGN KEY (VentaID) REFERENCES Ventas(ID),
    UNIQUE (FuncionID, NumeroFila, NumeroAsiento) -- Asegurar asiento único por función
);

-- Tabla CategoriasProducto
CREATE TABLE CategoriasProducto (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(50) UNIQUE,
    Descripcion VARCHAR(255)
);

-- Tabla Productos
CREATE TABLE Productos (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    CategoriaID INT,
    Nombre VARCHAR(100),
    Descripcion VARCHAR(255),
    PrecioUnitario DECIMAL(8,2),
    Stock INT,
    FOREIGN KEY (CategoriaID) REFERENCES CategoriasProducto(ID)
);

-- Tabla Ventas
CREATE TABLE Ventas (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    EmpleadoID INT,
    FechaHora DATETIME,
    TipoVenta VARCHAR(50), -- (Entradas, Confitería, Combo)
    MetodoPago VARCHAR(50), -- (Efectivo, Tarjeta de Crédito, Tarjeta de Débito)
    PrecioTotal DECIMAL(8,2),
    FOREIGN KEY (EmpleadoID) REFERENCES Empleados(ID)
);

-- Tabla DetallesVenta
CREATE TABLE DetallesVenta (
    VentaID INT,
    ProductoID INT,
    Cantidad INT,
    PrecioUnitarioVenta DECIMAL(8,2), -- Precio al momento de la venta
    Descuento DECIMAL(5,2),
    PRIMARY KEY (VentaID, ProductoID),
    FOREIGN KEY (VentaID) REFERENCES Ventas(ID),
    FOREIGN KEY (ProductoID) REFERENCES Productos(ID)
);

SELECT * FROM empleados;
SELECT * FROM clientes;
SELECT * FROM productos;
SELECT * FROM peliculas;
SELECT * FROM salas;
SELECT * FROM tipossala;
SELECT * FROM funciones;
SELECT * FROM entradas;
SELECT * FROM ventas;
