DELIMITER $$

-- Obtener todas las funciones con título de película y fecha completa
DROP PROCEDURE IF EXISTS ObtenerTodasFunciones$$
CREATE PROCEDURE ObtenerTodasFunciones()
BEGIN
    SELECT f.ID, p.Titulo AS TituloPelicula, f.SalaID, f.FechaHora,
           f.PrecioBase, f.Estado, f.Formato
    FROM Funciones f
    JOIN Peliculas p ON f.PeliculaID = p.ID;
END $$

-- Obtener una función por ID
DROP PROCEDURE IF EXISTS ObtenerFuncionPorId$$
CREATE PROCEDURE ObtenerFuncionPorId(IN p_ID INT)
BEGIN
    SELECT f.ID, f.PeliculaID, p.Titulo AS TituloPelicula, f.SalaID, f.FechaHora, f.PrecioBase,
           f.Estado, f.Formato
    FROM Funciones f
    JOIN Peliculas p ON f.PeliculaID = p.ID
    WHERE f.ID = p_ID;
END $$

-- Registrar una nueva función
DROP PROCEDURE IF EXISTS RegistrarFuncion$$
CREATE PROCEDURE RegistrarFuncion(
    IN p_PeliculaID INT,
    IN p_SalaID INT,
    IN p_FechaHora DATETIME,
    IN p_PrecioBase DECIMAL(8,2),
    IN p_Estado VARCHAR(20),
    IN p_Formato VARCHAR(50)
)
BEGIN
    DECLARE v_PeliculaExists INT;
    DECLARE v_SalaExists INT;

    -- Verificar que la película existe
    SELECT COUNT(*) INTO v_PeliculaExists FROM Peliculas WHERE ID = p_PeliculaID;
    -- Verificar que la sala existe
    SELECT COUNT(*) INTO v_SalaExists FROM Salas WHERE ID = p_SalaID;

    IF v_PeliculaExists > 0 AND v_SalaExists > 0 THEN
        INSERT INTO Funciones (PeliculaID, SalaID, FechaHora, PrecioBase, Estado, Formato)
        VALUES (p_PeliculaID, p_SalaID, p_FechaHora, p_PrecioBase, p_Estado, p_Formato);
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Película o Sala no válida';
    END IF;
END $$

-- Actualizar una función
DROP PROCEDURE IF EXISTS ActualizarFuncion$$
CREATE PROCEDURE ActualizarFuncion(
    IN p_ID INT,
    IN p_PeliculaID INT,
    IN p_SalaID INT,
    IN p_FechaHora DATETIME,
    IN p_PrecioBase DECIMAL(8,2),
    IN p_Estado VARCHAR(20),
    IN p_Formato VARCHAR(50)
)
BEGIN
    DECLARE v_PeliculaExists INT;
    DECLARE v_SalaExists INT;
    DECLARE v_FuncionExists INT;

    -- Verificar que la función existe
    SELECT COUNT(*) INTO v_FuncionExists FROM Funciones WHERE ID = p_ID;
    -- Verificar que la película existe
    SELECT COUNT(*) INTO v_PeliculaExists FROM Peliculas WHERE ID = p_PeliculaID;
    -- Verificar que la sala existe
    SELECT COUNT(*) INTO v_SalaExists FROM Salas WHERE ID = p_SalaID;

    IF v_FuncionExists > 0 AND v_PeliculaExists > 0 AND v_SalaExists > 0 THEN
        UPDATE Funciones
        SET PeliculaID = p_PeliculaID,
            SalaID = p_SalaID,
            FechaHora = p_FechaHora,
            PrecioBase = p_PrecioBase,
            Estado = p_Estado,
            Formato = p_Formato
        WHERE ID = p_ID;
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Función, Película o Sala no válida';
    END IF;
END $$

-- Eliminar una función (corregido para compatibilidad con only_full_group_by)
DROP PROCEDURE IF EXISTS EliminarFuncion$$
CREATE PROCEDURE EliminarFuncion(IN p_ID INT)
BEGIN
    DECLARE v_FuncionExists INT;
    DECLARE v_Estado VARCHAR(20);
    DECLARE v_EntradasCount INT;

    -- Verificar que la función existe
    SELECT COUNT(*) INTO v_FuncionExists FROM Funciones WHERE ID = p_ID;
    IF v_FuncionExists = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Función no encontrada';
    ELSE
        -- Obtener el estado de la función
        SELECT Estado INTO v_Estado FROM Funciones WHERE ID = p_ID;
        -- Verificar si hay entradas asociadas
        SELECT COUNT(*) INTO v_EntradasCount FROM Entradas WHERE FuncionID = p_ID;

        IF v_Estado = 'Finalizada' THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'No se puede eliminar una función finalizada';
        ELSEIF v_EntradasCount > 0 THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'No se puede eliminar la función porque tiene entradas asociadas';
        ELSE
            DELETE FROM Funciones WHERE ID = p_ID;
        END IF;
    END IF;
END $$

-- Obtener películas disponibles con más detalles
DROP PROCEDURE IF EXISTS ObtenerPeliculasDisponibles$$
CREATE PROCEDURE ObtenerPeliculasDisponibles()
BEGIN
    SELECT ID, Titulo, Genero, IdiomaOriginal, DuracionMinutos, Clasificacion
    FROM Peliculas;
END $$

-- Obtener salas disponibles con más detalles
DROP PROCEDURE IF EXISTS ObtenerSalasDisponibles$$
CREATE PROCEDURE ObtenerSalasDisponibles()
BEGIN
    SELECT s.ID, s.Numero, ts.Nombre AS NombreSala, s.DescripcionAdicional
    FROM Salas s
    JOIN TiposSala ts ON s.TipoSalaID = ts.ID;
END $$

-- Buscar funciones por ID o título de película con fecha completa
DROP PROCEDURE IF EXISTS BuscarFunciones$$
CREATE PROCEDURE BuscarFunciones(IN p_Criterio VARCHAR(255), IN p_TipoBusqueda VARCHAR(10))
BEGIN
    IF p_TipoBusqueda = 'ID' THEN
        SELECT f.ID, p.Titulo AS TituloPelicula, f.SalaID, f.FechaHora,
               f.PrecioBase, f.Estado, f.Formato
        FROM Funciones f
        JOIN Peliculas p ON f.PeliculaID = p.ID
        WHERE f.ID = CAST(p_Criterio AS UNSIGNED);
    ELSEIF p_TipoBusqueda = 'TITULO' THEN
        SELECT f.ID, p.Titulo AS TituloPelicula, f.SalaID, f.FechaHora,
               f.PrecioBase, f.Estado, f.Formato
        FROM Funciones f
        JOIN Peliculas p ON f.PeliculaID = p.ID
        WHERE p.Titulo LIKE CONCAT('%', p_Criterio, '%');
    END IF;
END $$

-- -----------------------------------------------------
-- PROCEDIMIENTOS PARA LA TABLA Cliente
-- -----------------------------------------------------
DROP PROCEDURE IF EXISTS sp_registrar_cliente$$
CREATE PROCEDURE sp_registrar_cliente(
    IN  p_nombre      VARCHAR(100),
    IN  p_apellido    VARCHAR(100),
    IN  p_email       VARCHAR(150),
    IN  p_telefono    VARCHAR(50),
    IN  p_fecha       DATE,
    OUT p_success     TINYINT
)
BEGIN
    IF p_nombre    IS NULL OR TRIM(p_nombre)   = ''
    OR   p_apellido  IS NULL OR TRIM(p_apellido) = ''
    OR   p_email     IS NULL OR TRIM(p_email)    = ''
    OR   p_fecha     IS NULL
    THEN
        SET p_success = 0;
    ELSE
        INSERT INTO Clientes (Nombre, Apellido, Email, Telefono, FechaRegistro)
        VALUES (p_nombre, p_apellido, p_email, p_telefono, p_fecha);
        SET p_success = IF(ROW_COUNT() > 0, 1, 0);
    END IF;
END$$

DROP PROCEDURE IF EXISTS sp_actualizar_cliente$$
CREATE PROCEDURE sp_actualizar_cliente(
    IN  p_id          INT,
    IN  p_nombre      VARCHAR(100),
    IN  p_apellido    VARCHAR(100),
    IN  p_email       VARCHAR(150),
    IN  p_telefono    VARCHAR(50),
    IN  p_fecha       DATE,
    OUT p_success     TINYINT
)
BEGIN
    IF p_id        IS NULL
    OR   p_nombre    IS NULL OR TRIM(p_nombre)   = ''
    OR   p_apellido  IS NULL OR TRIM(p_apellido) = ''
    OR   p_email     IS NULL OR TRIM(p_email)    = ''
    OR   p_fecha     IS NULL
    THEN
        SET p_success = 0;
    ELSE
        UPDATE Clientes
        SET Nombre        = p_nombre,
            Apellido      = p_apellido,
            Email         = p_email,
            Telefono      = p_telefono,
            FechaRegistro = p_fecha
        WHERE ID = p_id;
        SET p_success = IF(ROW_COUNT() > 0, 1, 0);
    END IF;
END$$

DROP PROCEDURE IF EXISTS sp_eliminar_cliente$$
CREATE PROCEDURE sp_eliminar_cliente(
    IN  p_id       INT,
    OUT p_success  TINYINT
)
BEGIN
    DELETE FROM Clientes
    WHERE ID = p_id;
    SET p_success = IF(ROW_COUNT() > 0, 1, 0);
END$$

DROP PROCEDURE IF EXISTS sp_obtener_cliente_por_id$$
CREATE PROCEDURE sp_obtener_cliente_por_id(
    IN p_id INT
)
BEGIN
    SELECT *
    FROM Clientes
    WHERE ID = p_id;
END$$

DROP PROCEDURE IF EXISTS sp_buscar_cliente_por_nombre$$
CREATE PROCEDURE sp_buscar_cliente_por_nombre(
    IN p_nombre VARCHAR(100)
)
BEGIN
    IF p_nombre IS NULL OR TRIM(p_nombre) = '' THEN
        SELECT * FROM Clientes;
    ELSE
        SELECT *
        FROM Clientes
        WHERE Nombre  LIKE CONCAT('%', p_nombre, '%')
           OR Apellido LIKE CONCAT('%', p_nombre, '%');
    END IF;
END$$

DROP PROCEDURE IF EXISTS sp_obtener_todos_clientes$$
CREATE PROCEDURE sp_obtener_todos_clientes()
BEGIN
    SELECT * FROM Clientes;
END$$

-- -----------------------------------------------------
-- PROCEDIMIENTOS PARA LA TABLA Empleado
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS sp_registrar_empleado$$
CREATE PROCEDURE sp_registrar_empleado(
    IN  p_usuario        VARCHAR(50),
    IN  p_contraseña     VARCHAR(100),
    IN  p_nombre         VARCHAR(100),
    IN  p_apellido       VARCHAR(100),
    IN  p_email          VARCHAR(150),
    IN  p_telefono       VARCHAR(15),
    IN  p_fechaContrato  DATE,
    IN  p_direccion      VARCHAR(255),
    IN  p_rolID          INT,
    OUT p_success        TINYINT
)
BEGIN
    DECLARE v_count INT;

    -- Validaciones básicas
    IF p_usuario    IS NULL OR TRIM(p_usuario)    = '' 
    OR  p_contraseña IS NULL OR TRIM(p_contraseña) = '' 
    OR  p_nombre     IS NULL OR TRIM(p_nombre)     = '' 
    OR  p_apellido   IS NULL OR TRIM(p_apellido)   = '' 
    THEN
        SET p_success = 0;
    ELSE
        -- Verificar unicidad de usuario y email
        SELECT COUNT(*) INTO v_count
        FROM Empleados
        WHERE Usuario = p_usuario
           OR Email   = IF(p_email IS NULL, '', p_email);

        IF v_count > 0 THEN
            SET p_success = 0;
        ELSE
            INSERT INTO Empleados
              (Usuario, Contraseña, Nombre, Apellido, Email, Telefono, FechaContratacion, Direccion, RolID)
            VALUES
              (p_usuario, p_contraseña, p_nombre, p_apellido,
               p_email, p_telefono, p_fechaContrato, p_direccion, p_rolID);
            SET p_success = IF(ROW_COUNT() > 0, 1, 0);
        END IF;
    END IF;
END$$


DROP PROCEDURE IF EXISTS sp_actualizar_empleado$$
CREATE PROCEDURE sp_actualizar_empleado(
    IN  p_id             INT,
    IN  p_nombre         VARCHAR(100),
    IN  p_apellido       VARCHAR(100),
    IN  p_email          VARCHAR(150),
    IN  p_telefono       VARCHAR(15),
    IN  p_fechaContrato  DATE,
    IN  p_direccion      VARCHAR(255),
    OUT p_success        TINYINT
)
BEGIN
    DECLARE v_count INT;

    -- Validaciones
    IF p_id       IS NULL 
    OR  p_nombre   IS NULL OR TRIM(p_nombre)   = '' 
    OR  p_apellido IS NULL OR TRIM(p_apellido) = '' 
    THEN
        SET p_success = 0;
    ELSE
        -- Unicidad de email (excluyendo el registro actual)
        SELECT COUNT(*) INTO v_count
        FROM Empleados
        WHERE Email = IF(p_email IS NULL, '', p_email)
          AND ID    != p_id;

        IF v_count > 0 THEN
            SET p_success = 0;
        ELSE
            UPDATE Empleados
            SET Nombre           = p_nombre,
                Apellido         = p_apellido,
                Email            = p_email,
                Telefono         = p_telefono,
                FechaContratacion= p_fechaContrato,
                Direccion        = p_direccion
            WHERE ID = p_id
              AND RolID = 1;  -- Sólo empleados
            SET p_success = IF(ROW_COUNT() > 0, 1, 0);
        END IF;
    END IF;
END$$


DROP PROCEDURE IF EXISTS sp_eliminar_empleado$$
CREATE PROCEDURE sp_eliminar_empleado(
    IN  p_id       INT,
    OUT p_success  TINYINT
)
BEGIN
    DELETE FROM Empleados
    WHERE ID    = p_id
      AND RolID = 1;
    SET p_success = IF(ROW_COUNT() > 0, 1, 0);
END$$


DROP PROCEDURE IF EXISTS sp_obtener_todos_empleados$$
CREATE PROCEDURE sp_obtener_todos_empleados()
BEGIN
    SELECT ID, Usuario, Nombre, Apellido, Email, Telefono,
           FechaContratacion, Direccion, RolID
    FROM Empleados
    WHERE RolID = 1;
END$$

-- Por ID (sin filtrar RolID y con alias 'Contrasena')
DROP PROCEDURE IF EXISTS sp_obtener_empleado_por_id$$
CREATE PROCEDURE sp_obtener_empleado_por_id(
    IN p_id INT
)
BEGIN
    SELECT 
      ID,
      Usuario,
      `Contraseña`    AS Contrasena,
      Nombre,
      Apellido,
      Email,
      Telefono,
      FechaContratacion,
      Direccion,
      RolID
    FROM Empleados
    WHERE ID = p_id;
END$$

-- Por usuario (sin filtrar RolID y con alias 'Contrasena')
DROP PROCEDURE IF EXISTS sp_obtener_empleado_por_usuario$$
CREATE PROCEDURE sp_obtener_empleado_por_usuario(
    IN p_usuario VARCHAR(50)
)
BEGIN
    SELECT 
      ID,
      Usuario,
      `Contraseña`    AS Contrasena,
      Nombre,
      Apellido,
      Email,
      Telefono,
      FechaContratacion,
      Direccion,
      RolID
    FROM Empleados
    WHERE Usuario = p_usuario;
END$$

DROP PROCEDURE IF EXISTS sp_obtener_empleado_por_rol$$
CREATE PROCEDURE sp_obtener_empleado_por_rol(
    IN p_rol VARCHAR(20)
)
BEGIN
    DECLARE v_rolId INT DEFAULT -1;

    IF UPPER(p_rol) = 'EMPLEADO' THEN
        SET v_rolId = 1;
    ELSEIF UPPER(p_rol) = 'ADMINISTRADOR' THEN
        SET v_rolId = 2;
    END IF;

    IF v_rolId = -1 THEN
        -- Rol inválido: devolvemos conjunto vacío
        SELECT * FROM Empleados WHERE 1=0;
    ELSE
        SELECT ID, Usuario, Nombre, Apellido, Email, Telefono,
               FechaContratacion, Direccion, RolID
        FROM Empleados
        WHERE RolID = v_rolId;
    END IF;
END$$

-- Registrar Película
DROP PROCEDURE IF EXISTS sp_registrar_pelicula$$
CREATE PROCEDURE sp_registrar_pelicula(
    IN  p_titulo               VARCHAR(255),
    IN  p_sinopsis             TEXT,
    IN  p_genero               VARCHAR(100),
    IN  p_director             VARCHAR(150),
    IN  p_actoresPrincipales   TEXT,
    IN  p_fechaLanzamiento     DATE,
    IN  p_idiomaOriginal       VARCHAR(50),
    IN  p_subtitulosDisponibles VARCHAR(100),
    IN  p_duracionMinutos      INT,
    IN  p_clasificacion        VARCHAR(50),
    IN  p_posterURL            VARCHAR(255),
    OUT p_success              TINYINT
)
BEGIN
    -- Validaciones básicas
    IF p_titulo               IS NULL OR TRIM(p_titulo)             = ''
    OR  p_genero               IS NULL OR TRIM(p_genero)             = ''
    OR  p_idiomaOriginal       IS NULL OR TRIM(p_idiomaOriginal)     = ''
    OR  p_duracionMinutos      <=  0
    OR  p_clasificacion        IS NULL OR TRIM(p_clasificacion)      = ''
    OR  p_fechaLanzamiento     IS NULL
    THEN
        SET p_success = 0;
    ELSE
        INSERT INTO Peliculas
            (Titulo, Sinopsis, Genero, Director, ActoresPrincipales,
             FechaLanzamiento, IdiomaOriginal, SubtitulosDisponibles,
             DuracionMinutos, Clasificacion, PosterURL)
        VALUES
            (p_titulo, p_sinopsis, p_genero, p_director, p_actoresPrincipales,
             p_fechaLanzamiento, p_idiomaOriginal, p_subtitulosDisponibles,
             p_duracionMinutos, p_clasificacion, p_posterURL);
        SET p_success = IF(ROW_COUNT()>0, 1, 0);
    END IF;
END$$

-- Actualizar Película
DROP PROCEDURE IF EXISTS sp_actualizar_pelicula$$
CREATE PROCEDURE sp_actualizar_pelicula(
    IN  p_id                   INT,
    IN  p_titulo               VARCHAR(255),
    IN  p_sinopsis             TEXT,
    IN  p_genero               VARCHAR(100),
    IN  p_director             VARCHAR(150),
    IN  p_actoresPrincipales   TEXT,
    IN  p_fechaLanzamiento     DATE,
    IN  p_idiomaOriginal       VARCHAR(50),
    IN  p_subtitulosDisponibles VARCHAR(100),
    IN  p_duracionMinutos      INT,
    IN  p_clasificacion        VARCHAR(50),
    IN  p_posterURL            VARCHAR(255),
    OUT p_success              TINYINT
)
BEGIN
    -- Validaciones básicas
    IF p_id                   IS NULL
    OR  p_titulo               IS NULL OR TRIM(p_titulo)             = ''
    OR  p_genero               IS NULL OR TRIM(p_genero)             = ''
    OR  p_idiomaOriginal       IS NULL OR TRIM(p_idiomaOriginal)     = ''
    OR  p_duracionMinutos      <=  0
    OR  p_clasificacion        IS NULL OR TRIM(p_clasificacion)      = ''
    OR  p_fechaLanzamiento     IS NULL
    THEN
        SET p_success = 0;
    ELSE
        UPDATE Peliculas
        SET Titulo               = p_titulo,
            Sinopsis             = p_sinopsis,
            Genero               = p_genero,
            Director             = p_director,
            ActoresPrincipales   = p_actoresPrincipales,
            FechaLanzamiento     = p_fechaLanzamiento,
            IdiomaOriginal       = p_idiomaOriginal,
            SubtitulosDisponibles= p_subtitulosDisponibles,
            DuracionMinutos      = p_duracionMinutos,
            Clasificacion        = p_clasificacion,
            PosterURL            = p_posterURL
        WHERE ID = p_id;
        SET p_success = IF(ROW_COUNT()>0, 1, 0);
    END IF;
END$$

-- Eliminar Película
DROP PROCEDURE IF EXISTS sp_eliminar_pelicula$$
CREATE PROCEDURE sp_eliminar_pelicula(
    IN  p_id       INT,
    OUT p_success  TINYINT
)
BEGIN
    DELETE FROM Peliculas
    WHERE ID = p_id;
    SET p_success = IF(ROW_COUNT()>0, 1, 0);
END$$

-- Obtener Película por ID
DROP PROCEDURE IF EXISTS sp_obtener_pelicula_por_id$$
CREATE PROCEDURE sp_obtener_pelicula_por_id(
    IN p_id INT
)
BEGIN
    SELECT *
    FROM Peliculas
    WHERE ID = p_id;
END$$

-- Buscar Películas por Título (o todas si el parámetro está vacío)
DROP PROCEDURE IF EXISTS sp_buscar_pelicula_por_titulo$$
CREATE PROCEDURE sp_buscar_pelicula_por_titulo(
    IN p_titulo VARCHAR(255)
)
BEGIN
    IF p_titulo IS NULL OR TRIM(p_titulo) = '' THEN
        SELECT * FROM Peliculas;
    ELSE
        SELECT *
        FROM Peliculas
        WHERE Titulo LIKE CONCAT('%', p_titulo, '%');
    END IF;
END$$

-- Obtener Todas las Películas
DROP PROCEDURE IF EXISTS sp_obtener_todas_peliculas$$
CREATE PROCEDURE sp_obtener_todas_peliculas()
BEGIN
    SELECT * FROM Peliculas;
END$$

-- Registrar Producto
DROP PROCEDURE IF EXISTS sp_registrar_producto$$
CREATE PROCEDURE sp_registrar_producto(
    IN  p_categoriaID    INT,
    IN  p_nombre         VARCHAR(255),
    IN  p_descripcion    TEXT,
    IN  p_precioUnitario DECIMAL(10,2),
    IN  p_stock          INT,
    OUT p_success        TINYINT
)
BEGIN
    DECLARE v_count INT;

    -- Validaciones básicas
    IF p_nombre IS NULL OR TRIM(p_nombre) = '' 
    OR  p_precioUnitario <= 0
    OR  p_stock < 0
    OR  (SELECT COUNT(*) FROM CategoriasProducto WHERE ID = p_categoriaID) = 0
    THEN
        SET p_success = 0;
    ELSE
        -- Verificar unicidad de nombre
        SELECT COUNT(*) INTO v_count
        FROM Productos
        WHERE Nombre = p_nombre;

        IF v_count > 0 THEN
            SET p_success = 0;
        ELSE
            INSERT INTO Productos
                (CategoriaID, Nombre, Descripcion, PrecioUnitario, Stock)
            VALUES
                (p_categoriaID, p_nombre, p_descripcion, p_precioUnitario, p_stock);
            SET p_success = IF(ROW_COUNT()>0, 1, 0);
        END IF;
    END IF;
END$$

-- Actualizar Producto
DROP PROCEDURE IF EXISTS sp_actualizar_producto$$
CREATE PROCEDURE sp_actualizar_producto(
    IN  p_id             INT,
    IN  p_categoriaID    INT,
    IN  p_nombre         VARCHAR(255),
    IN  p_descripcion    TEXT,
    IN  p_precioUnitario DECIMAL(10,2),
    IN  p_stock          INT,
    OUT p_success        TINYINT
)
BEGIN
    DECLARE v_count INT;

    -- Validaciones básicas
    IF p_id      IS NULL
    OR  p_nombre IS NULL OR TRIM(p_nombre) = ''
    OR  p_precioUnitario <= 0
    OR  p_stock < 0
    OR  (SELECT COUNT(*) FROM CategoriasProducto WHERE ID = p_categoriaID) = 0
    THEN
        SET p_success = 0;
    ELSE
        -- Verificar unicidad de nombre (excluyendo el actual)
        SELECT COUNT(*) INTO v_count
        FROM Productos
        WHERE Nombre = p_nombre
          AND ID     != p_id;

        IF v_count > 0 THEN
            SET p_success = 0;
        ELSE
            UPDATE Productos
            SET CategoriaID    = p_categoriaID,
                Nombre         = p_nombre,
                Descripcion    = p_descripcion,
                PrecioUnitario = p_precioUnitario,
                Stock          = p_stock
            WHERE ID = p_id;
            SET p_success = IF(ROW_COUNT()>0, 1, 0);
        END IF;
    END IF;
END$$

-- Eliminar Producto
DROP PROCEDURE IF EXISTS sp_eliminar_producto$$
CREATE PROCEDURE sp_eliminar_producto(
    IN  p_id        INT,
    OUT p_success   TINYINT
)
BEGIN
    DELETE FROM Productos
    WHERE ID = p_id;
    SET p_success = IF(ROW_COUNT()>0, 1, 0);
END$$

-- Obtener Todos los Productos
DROP PROCEDURE IF EXISTS sp_obtener_todos_productos$$
CREATE PROCEDURE sp_obtener_todos_productos()
BEGIN
    SELECT * FROM Productos;
END$$

-- Obtener Producto por ID
DROP PROCEDURE IF EXISTS sp_obtener_producto_por_id$$
CREATE PROCEDURE sp_obtener_producto_por_id(
    IN p_id INT
)
BEGIN
    SELECT * 
    FROM Productos
    WHERE ID = p_id;
END$$

-- Buscar Productos por Nombre
DROP PROCEDURE IF EXISTS sp_buscar_producto_por_nombre$$
CREATE PROCEDURE sp_buscar_producto_por_nombre(
    IN p_nombre VARCHAR(255)
)
BEGIN
    SELECT *
    FROM Productos
    WHERE Nombre LIKE CONCAT('%', p_nombre, '%');
END$$

-- Obtener Todas las Categorías
DROP PROCEDURE IF EXISTS sp_obtener_todas_categorias$$
CREATE PROCEDURE sp_obtener_todas_categorias()
BEGIN
    SELECT * 
    FROM CategoriasProducto
    ORDER BY Nombre;
END$$

-- Obtener Nombre de Categoría por ID
DROP PROCEDURE IF EXISTS sp_obtener_nombre_categoria$$
CREATE PROCEDURE sp_obtener_nombre_categoria(
    IN p_categoriaID INT
)
BEGIN
    SELECT Nombre
    FROM CategoriasProducto
    WHERE ID = p_categoriaID;
END$$

-- Obtener ID de Categoría por Nombre
DROP PROCEDURE IF EXISTS sp_obtener_categoria_id$$
CREATE PROCEDURE sp_obtener_categoria_id(
    IN p_nombre VARCHAR(255)
)
BEGIN
    SELECT ID
    FROM CategoriasProducto
    WHERE Nombre = p_nombre
    LIMIT 1;
END$$

-- Validar la categoria por el id
DROP PROCEDURE IF EXISTS sp_validar_categoria_id$$
CREATE PROCEDURE sp_validar_categoria_id(
    IN  p_categoriaID INT,
    OUT p_exists      TINYINT
)
BEGIN
    SELECT COUNT(*) 
      INTO @cnt
    FROM CategoriasProducto
    WHERE ID = p_categoriaID;

    SET p_exists = IF(@cnt > 0, 1, 0);
END$$

-- -----------------------------------------------------
-- PROCEDIMIENTOS PARA LA TABLA Entrada
-- -----------------------------------------------------
DROP PROCEDURE IF EXISTS sp_insertarEntrada$$
CREATE PROCEDURE sp_insertarEntrada(
    IN p_FuncionID       INT,
    IN p_ClienteID       INT,
    IN p_PrecioEntradaID INT,
    IN p_NumeroFila      VARCHAR(10),
    IN p_NumeroAsiento   INT,
    IN p_FechaVenta      DATETIME,
    IN p_Estado          VARCHAR(20)
)
BEGIN
    INSERT INTO Entradas
      (FuncionID, ClienteID, PrecioEntradaID,
       NumeroFila, NumeroAsiento, FechaVenta, Estado)
    VALUES
      (p_FuncionID, p_ClienteID, p_PrecioEntradaID,
       p_NumeroFila, p_NumeroAsiento, p_FechaVenta, p_Estado);
    SELECT LAST_INSERT_ID() AS ID;
END$$

DROP PROCEDURE IF EXISTS sp_actualizarEntrada$$
CREATE PROCEDURE sp_actualizarEntrada(
    IN p_ID               INT,
    IN p_FuncionID        INT,
    IN p_ClienteID        INT,
    IN p_PrecioEntradaID  INT,
    IN p_NumeroFila       VARCHAR(10),
    IN p_NumeroAsiento    INT,
    IN p_FechaVenta       DATETIME,
    IN p_Estado           VARCHAR(20)
)
BEGIN
    UPDATE Entradas
    SET
      FuncionID       = p_FuncionID,
      ClienteID       = p_ClienteID,
      PrecioEntradaID = p_PrecioEntradaID,
      NumeroFila      = p_NumeroFila,
      NumeroAsiento   = p_NumeroAsiento,
      FechaVenta      = p_FechaVenta,
      Estado          = p_Estado
    WHERE ID = p_ID;
END$$

DROP PROCEDURE IF EXISTS sp_eliminarEntrada$$
CREATE PROCEDURE sp_eliminarEntrada(IN p_ID INT)
BEGIN
    DELETE FROM Entradas
    WHERE ID = p_ID;
END$$

DROP PROCEDURE IF EXISTS sp_listarEntradasDetallado$$
CREATE PROCEDURE sp_listarEntradasDetallado()
BEGIN
    SELECT 
        e.ID,
        p.Titulo             AS Pelicula,
        CONCAT(c.Nombre, ' ', c.Apellido) AS Cliente,
        CONCAT(pe.Precio, ' (', pe.Nombre, ')') AS PrecioCategoria,
        e.NumeroFila,
        e.NumeroAsiento,
        e.FechaVenta,
        e.Estado
    FROM Entradas e
    JOIN Funciones f            ON e.FuncionID       = f.ID
    JOIN Peliculas p            ON f.PeliculaID      = p.ID
    JOIN Clientes c             ON e.ClienteID       = c.ID
    JOIN PrecioEntradas pe      ON e.PrecioEntradaID = pe.ID
    ORDER BY e.FechaVenta DESC;
END$$


DROP PROCEDURE IF EXISTS sp_obtenerEntradaPorIdDetallado$$
CREATE PROCEDURE sp_obtenerEntradaPorIdDetallado(
    IN p_ID INT
)
BEGIN
    SELECT 
        e.ID                              AS EntradaID,
        p.Titulo                          AS Pelicula,
        c.ID                              AS ClienteID,
        CONCAT(c.Nombre, ' ', c.Apellido) AS Cliente,
        pe.ID							  AS PrecioEntradaID,
        CONCAT(pe.Precio, ' (', pe.Nombre, ')') AS PrecioCategoria,
        f.ID                              AS FuncionID,
        e.NumeroFila,
        e.NumeroAsiento,
        e.FechaVenta,
        e.Estado
    FROM Entradas e
    JOIN Funciones f        ON e.FuncionID       = f.ID
    JOIN Peliculas p        ON f.PeliculaID      = p.ID
    JOIN Clientes c         ON e.ClienteID       = c.ID
    JOIN PrecioEntradas pe  ON e.PrecioEntradaID = pe.ID
    WHERE e.ID = p_ID;
END$$

DROP PROCEDURE IF EXISTS RegistrarVenta$$
CREATE PROCEDURE RegistrarVenta(
    IN p_EmpleadoID INT,
    IN p_FechaHora DATETIME,
    IN p_TipoVenta VARCHAR(50),
    IN p_MetodoPago VARCHAR(50),
    IN p_PrecioTotal DECIMAL(8,2),
    OUT p_VentaID INT
)
BEGIN
    INSERT INTO Ventas (EmpleadoID, FechaHora, TipoVenta, MetodoPago, PrecioTotal)
    VALUES (p_EmpleadoID, p_FechaHora, p_TipoVenta, p_MetodoPago, p_PrecioTotal);
    SET p_VentaID = LAST_INSERT_ID();
END$$

DROP PROCEDURE IF EXISTS RegistrarDetalleVenta$$
CREATE PROCEDURE RegistrarDetalleVenta(
    IN p_VentaID INT,
    IN p_ProductoID INT,
    IN p_Cantidad INT,
    IN p_PrecioUnitario DECIMAL(8,2),
    IN p_Descuento DECIMAL(5,2)
)
BEGIN
    INSERT INTO DetallesVenta (VentaID, ProductoID, Cantidad, PrecioUnitarioVenta, Descuento)
    VALUES (p_VentaID, p_ProductoID, p_Cantidad, p_PrecioUnitario, p_Descuento);
    
    UPDATE Productos
    SET Stock = Stock - p_Cantidad
    WHERE ID = p_ProductoID AND Stock >= p_Cantidad;
END$$ 

DROP PROCEDURE IF EXISTS RegistrarEntrada$$
CREATE PROCEDURE RegistrarEntrada(
    IN p_FuncionID INT,
    IN p_ClienteID INT,
    IN p_PrecioEntradaID INT,
    IN p_NumeroFila VARCHAR(10),
    IN p_NumeroAsiento INT,
    IN p_FechaVenta DATETIME,
    OUT p_Success BOOLEAN
)
BEGIN
    DECLARE asientoOcupado INT;
    
    -- Verificar si el asiento está disponible
    SELECT COUNT(*) INTO asientoOcupado
    FROM Entradas
    WHERE FuncionID = p_FuncionID AND NumeroFila = p_NumeroFila AND NumeroAsiento = p_NumeroAsiento AND Estado != 'Cancelada';
    
    IF asientoOcupado = 0 THEN
        INSERT INTO Entradas (FuncionID, ClienteID, PrecioEntradaID, NumeroFila, NumeroAsiento, FechaVenta, Estado)
        VALUES (p_FuncionID, p_ClienteID, p_PrecioEntradaID, p_NumeroFila, p_NumeroAsiento, p_FechaVenta, 'Vendida');
        SET p_Success = TRUE;
    ELSE
        SET p_Success = FALSE;
    END IF;
END$$ 

DROP PROCEDURE IF EXISTS ObtenerFuncionesDisponibles$$
CREATE PROCEDURE ObtenerFuncionesDisponibles()
BEGIN
    SELECT f.ID, p.Titulo, s.Numero AS Sala, f.FechaHora, f.PrecioBase, f.Formato
    FROM Funciones f
    JOIN Peliculas p ON f.PeliculaID = p.ID
    JOIN Salas s ON f.SalaID = s.ID
    WHERE f.Estado = 'Programada'
    AND f.ID NOT IN (
        SELECT DISTINCT FuncionID
        FROM Entradas
        WHERE Estado = 'Vendida'
        GROUP BY FuncionID
        HAVING COUNT(*) >= (SELECT Capacidad FROM Salas WHERE ID = f.SalaID)
    );
END$$

DROP PROCEDURE IF EXISTS ObtenerProductosDisponibles$$
CREATE PROCEDURE ObtenerProductosDisponibles()
BEGIN
    SELECT ID, Nombre, Descripcion, PrecioUnitario, Stock
    FROM Productos
    WHERE Stock > 0;
END$$


DROP PROCEDURE IF EXISTS RestaurarStock$$
CREATE PROCEDURE RestaurarStock(
    IN p_VentaID INT
)
BEGIN
    -- Restaurar el stock basado en los detalles de venta antes de eliminarlos
    UPDATE Productos p
    JOIN DetallesVenta dv ON p.ID = dv.ProductoID
    SET p.Stock = p.Stock + dv.Cantidad
    WHERE dv.VentaID = p_VentaID;
END $$

DROP PROCEDURE IF EXISTS CancelarEntradas$$
CREATE PROCEDURE CancelarEntradas(
    IN p_VentaID INT
)
BEGIN
    UPDATE Entradas
    SET Estado = 'Cancelada'
    WHERE VentaID = p_VentaID AND Estado = 'Vendida';
END $$

DELIMITER ;