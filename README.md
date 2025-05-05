# 🎬 Sistema Cine

Sistema Cine es una aplicación de escritorio desarrollada en Java que permite la gestión integral de un cine. Facilita la administración de películas, funciones, ventas de entradas y productos de confitería, así como la información de los clientes. Su objetivo es optimizar las operaciones diarias del cine, desde la programación hasta la venta.

## 🧑‍💻 Autores

* **Eddye Alessandro Mejía**
* **Juan S. Pimentel Lalangui**
* **Andriy L. Pastrana Cajavilca**

Miembros del **Grupo 3**.

## 🗂️ Estructura del Proyecto

El proyecto está organizado de la siguiente manera:

* `src/Models/`: Contiene las clases de modelo que representan las entidades del sistema, como `Pelicula`, `Producto`, `CategoriaProducto`, etc.
* `src/Controllers/`: Incluye las clases que manejan la lógica de negocio y las operaciones CRUD para las diferentes entidades.
* `src/database/`: Contiene los scripts SQL necesarios para la creación y manipulación de la base de datos.
* `src/Views/`: (Si aplica) Contiene las interfaces gráficas de usuario.

## 🛠️ Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado lo siguiente:

* **Java Development Kit (JDK) 14 o superior**
* **Apache NetBeans IDE**
* **MySQL Server**
* **MySQL Connector/J 9.3.0**([GeeksforGeeks][1], [YouTube][2], [MySQL][3])

## 🔌 Configuración del Proyecto en Apache NetBeans

### 1. Clonar el Repositorio

Clona este repositorio en tu máquina local:

```bash
git clone https://github.com/Eddy030/SistemaCine.git
```

### 2. Abrir el Proyecto en NetBeans

* Abre Apache NetBeans.
* Selecciona **File > Open Project**.
* Navega hasta la carpeta donde clonaste el repositorio y abre el proyecto.

### 3. Agregar MySQL Connector/J al Proyecto

Para que el proyecto pueda conectarse a la base de datos MySQL, es necesario agregar el conector JDBC:

1. Descarga **MySQL Connector/J 9.3.0** desde el siguiente enlace:

   * [Descargar MySQL Connector/J 9.3.0](https://dev.mysql.com/downloads/connector/j/)([GitHub][4])

2. En NetBeans, haz clic derecho sobre el proyecto y selecciona **Properties**.

3. Ve a **Libraries > Compile**.

4. Haz clic en **Add JAR/Folder** y selecciona el archivo `mysql-connector-j-9.3.0.jar` que descargaste.

5. Haz clic en **Open** y luego en **OK** para agregar la biblioteca al proyecto.([GeeksforGeeks][1])

### 4. Configurar la Conexión a la Base de Datos

La clase `ConexionMySQL` ubicada en `src/Models/ConexionMySQL.java` maneja la conexión a la base de datos. Asegúrate de configurar los siguientes parámetros según tu entorno:

```java
private final String URL = "jdbc:mysql://localhost:3306/CineBD";
private final String USER = "tu_usuario";
private final String PASSWORD = "tu_contraseña";
```



* Reemplaza `"tu_usuario"` y `"tu_contraseña"` con tus credenciales de MySQL.
* Asegúrate de que la base de datos `sistemacine` exista en tu servidor MySQL.

### 5. Ejecutar los Scripts SQL

En la carpeta `src/database/` encontrarás los scripts SQL necesarios para crear y poblar la base de datos.

1. Abre MySQL Workbench o tu herramienta de gestión de bases de datos preferida.
2. Ejecuta los scripts en el siguiente orden:

   * `SC_Cineplanet-Home.sql`
   * `SC_Inserciones.sql`
   * `SC_Procedimientos-Almacenados.sql` (si aplica)

Esto creará la base de datos y las tablas necesarias para el funcionamiento del sistema.

## 🚀 Ejecutar el Proyecto

Una vez configurado todo:

1. En NetBeans, haz clic derecho sobre el proyecto y selecciona **Run**.
2. La aplicación se compilará y se ejecutará, mostrando la interfaz gráfica del sistema.

## 🧪 Funcionalidades Principales

* Gestión de películas: registrar, actualizar, eliminar y buscar películas.
* Gestión de productos de confitería: registrar, actualizar, eliminar y buscar productos.
* Gestión de funciones y horarios.
* Venta de entradas y productos.
* Gestión de clientes y empleados.

## 📂 Scripts SQL

Los scripts SQL se encuentran en la carpeta `src/database/` e incluyen:

* `SC_Cineplanet-Home.sql`: Crea la base de datos `CineBD y sus tablas`.
* `SC_Inserciones.sql`: Inserta datos de ejemplo en las tablas.
* `SC_Procedimientos-Almacenados.sql`: Crea los procedimientos de almacenado necesarios.

## 📝 Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.

---

Para más información sobre cómo conectar NetBeans con MySQL, puedes consultar el siguiente recurso:([Stack Overflow][5])

* [Conectar Java GUI Application a MySQL Database en NetBeans](https://www.youtube.com/watch?v=0DehzIN_5AM)([YouTube][6])
