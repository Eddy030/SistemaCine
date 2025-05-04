-- Insertar más funciones de ejemplo
INSERT INTO Funciones (PeliculaID, SalaID, FechaHora, PrecioBase, Estado, Formato) VALUES
(6, 6, '2025-11-06 15:00:00', 6000, 'Programada', 'Doblada'),
(7, 7, '2025-12-16 14:30:00', 4800, 'Cancelada', '2D'),
(8, 8, '2026-01-26 16:45:00', 7200, 'Finalizada', '3D'),
(9, 9, '2026-02-21 19:15:00', 8500, 'Programada', 'IMAX'),
(10, 10, '2026-03-11 17:00:00', 7000, 'Programada', 'Subtitulada');

-- Consultas de prueba para verificar los procedimientos
CALL ObtenerTodasFunciones();
CALL ObtenerFuncionPorId(1);
CALL RegistrarFuncion(5, 4, '2025-10-31 20:00:00', 7500, 'Programada', '2D');
CALL ActualizarFuncion(1, 2, 1, '2025-06-15 19:00:00', 6000, 'Programada', '3D');
CALL EliminarFuncion(2); -- Debería fallar con mensaje "No se puede eliminar la función porque tiene entradas asociadas"
CALL EliminarFuncion(12); -- Debería funcionar, ya que no tiene entradas asociadas
CALL ObtenerPeliculasDisponibles();
CALL ObtenerSalasDisponibles();
CALL BuscarFunciones('1', 'ID'); -- Buscar por ID
CALL BuscarFunciones('Aventura', 'TITULO'); -- Buscar por título