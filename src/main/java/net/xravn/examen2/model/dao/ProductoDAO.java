package net.xravn.examen2.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.xravn.examen2.model.CategoriaProducto;
import net.xravn.examen2.model.Producto;
import net.xravn.examen2.model.sql.DAO;
import net.xravn.examen2.model.sql.QueryBuilder;

public class ProductoDAO extends DAO {

    public boolean agregarProducto(Producto producto) {
        return ejecutarUpdate(queryAgregarProducto(), //
                producto.getNombre(), //
                producto.getDescripcion(), //
                producto.getURLImagen(), //
                producto.getPrecio(), //
                producto.getStock() //
        );
    }

    public Producto recuperarProducto(int id) {
        List<Map<String, Object>> consulta = ejecutarConsulta(queryRecuperarProducto()//
                , id);
        if (consulta != null) {
            try {
                if (consulta.size() > 0) {
                    return recuperarProducto(consulta.get(0));
                }
            } catch (Exception e) {
                System.err.println("Error al recuperar producto");
            }
        }
        return null;
    }

    public List<Producto> recuperarProductos() {
        List<Map<String, Object>> consulta = ejecutarConsulta(queryRecuperarProductos());
        List<Producto> resultado = new ArrayList<>();
        if (consulta != null) {
            try {
                for (Map<String, Object> fila : consulta) {
                    resultado.add(recuperarProducto(fila));
                }
            } catch (Exception e) {
                System.err.println("Error al recuperar usuario");
            }
        }
        return resultado;
    }

    public boolean actualizarProducto(Producto producto) {
        return ejecutarUpdate(queryActualizarProducto(), //
                producto.getNombre(), //
                producto.getDescripcion(), //
                producto.getURLImagen(), //
                producto.getPrecio(), //
                producto.getStock(), //
                producto.getId(), //
                producto.getStock() //
        );
    }

    private Producto recuperarProducto(Map<String, Object> fila) {
        try {
            Producto p = new Producto();
            p.setId((Integer) fila.get("id_producto"));
            p.setNombre((String) fila.get("nombre"));
            p.setDescripcion((String) fila.get("descripcion"));
            p.setURLImagen((String) fila.get("url_imagen"));
            p.setPrecio((Double) fila.get("precio"));
            p.setStock((Integer) fila.get("stock"));
            List<CategoriaProducto> categorias = new CategoriaProductoDAO().recuperarCategoriasPorIDProducto(p.getId());
            p.getCategorias().addAll(categorias);

            return p;
        } catch (Exception e) {
            System.err.println("Error al recuperar usuario");
        }
        return null;
    }

    private static String queryAgregarProducto() {
        return new QueryBuilder() //
                .insertInto("productos") //
                .values("nombre", "descripcion", "url_imagen", "precio", "stock") //
                .build(); //
    }

    private static String queryRecuperarProducto() {
        return new QueryBuilder() //
                .select("*") //
                .from("productos") //
                .where("id_producto = ?") //
                .build(); //
    }

    public static String queryRecuperarProductos() {
        return new QueryBuilder() //
                .select("*") //
                .from("productos") //
                .build(); //
    }

    private static String queryActualizarProducto() {
        return new QueryBuilder() //
                .update("productos") //
                .set("nombre", "descripcion", "url_imagen", "precio", "stock") //
                .where("id_producto = ? and ? + stock >= 0") //
                .build(); //
    }

}
