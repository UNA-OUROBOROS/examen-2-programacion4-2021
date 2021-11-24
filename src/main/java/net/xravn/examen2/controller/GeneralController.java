package net.xravn.examen2.controller;

import java.util.List;

import net.xravn.examen2.model.CategoriaProducto;
import net.xravn.examen2.model.Producto;
import net.xravn.examen2.model.dao.CategoriaProductoDAO;
import net.xravn.examen2.model.dao.ProductoDAO;

public class GeneralController {

    private GeneralController() {
    }

    public boolean agregarProducto(Producto producto) {
        return new ProductoDAO().agregarProducto(producto);
    }

    public Producto getProducto(int codigo) {
        return new ProductoDAO().recuperarProducto(codigo);
    }

    public List<Producto> getProductos() {
        return new ProductoDAO().recuperarProductos();
    }

    public boolean actualizarProducto(Producto producto) {
        return new ProductoDAO().actualizarProducto(producto);
    }

    public List<CategoriaProducto> getCategorias() {
        return new CategoriaProductoDAO().recuperarCategorias();
    }

    public CategoriaProducto getCategoria(Integer idCategoria) {
        return new CategoriaProductoDAO().recuperarCategoria(idCategoria);
    }

    private static GeneralController instance;

    public static GeneralController getInstance() {
        if (instance == null) {
            instance = new GeneralController();
        }
        return instance;
    }
}