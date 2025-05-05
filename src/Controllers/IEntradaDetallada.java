package Controllers;

import Models.EntradaDetallada;
import Repository.IRepositorioGET;
import java.util.List;

public interface IEntradaDetallada extends IRepositorioGET<EntradaDetallada> {

    List<EntradaDetallada> obtenerTodaslasentradas();
}
