package net.xravn.examen2.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.xravn.examen2.model.CategoriaProducto;
import net.xravn.examen2.model.sql.DAO;
import net.xravn.examen2.model.sql.QueryBuilder;

public class CategoriaProductoDAO extends DAO {

    public CategoriaProductoDAO() {
        super();
    }

    public List<CategoriaProducto> recuperarCategorias() {
        List<Map<String, Object>> consulta //
                = ejecutarConsulta(queryObtenerCategoriasProducto);
        List<CategoriaProducto> resultado = new ArrayList<>();
        if (consulta != null) {
            try {
                for (Map<String, Object> fila : consulta) {
                    CategoriaProducto categoria = recuperarCategoriaProducto(fila);
                    if (categoria != null) {
                        resultado.add(categoria);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error al recuperar las categorias de productos");
            }
        }
        return resultado;
    }

    public CategoriaProducto recuperarCategoria(Integer id) {
        List<Map<String, Object>> consulta //
                = ejecutarConsulta(queryObtenerCategoriaProductoPorId, id);
        if (consulta != null) {
            try {
                if (consulta.size() > 0) {
                    return recuperarCategoriaProducto(consulta.get(0));
                }
            } catch (Exception e) {
                System.err.println("Error al recuperar las categorias de productos");
            }
        }
        return null;
    }

    public List<CategoriaProducto> recuperarCategoriasPorIDProducto(Integer idProducto) {
        List<Map<String, Object>> consulta //
                = ejecutarConsulta(queryRecuperarCategoriasporIDProducto, idProducto);
        List<CategoriaProducto> resultado = new ArrayList<>();
        if (consulta != null) {
            try {
                for (Map<String, Object> fila : consulta) {
                    CategoriaProducto categoria = recuperarCategoriaProducto(fila);
                    if (categoria != null) {
                        resultado.add(categoria);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error al recuperar las categorias de productos");
            }
        }
        return resultado;
    }

    public CategoriaProducto recuperarCategoriaProducto(Map<String, Object> fila) {
        try {
            return new CategoriaProducto( //
                    (Integer) fila.get("id_categoria"), //
                    (String) fila.get("descripcion") //
            );
        } catch (Exception e) {
            System.err.println("Error al recuperar la categoria de producto");
            return null;
        }
    }

    private static final String queryObtenerCategoriaProductoPorId //
            = new QueryBuilder() //
                    .select("*") //
                    .from("categorias") //
                    .where("id_categoria = ?") //
                    .build(); //

    private static final String queryObtenerCategoriasProducto //
            = new QueryBuilder() //
                    .select("*") //
                    .from("categorias") //
                    .build(); //

    private static final String queryRecuperarCategoriasporIDProducto //
            = new QueryBuilder() //
                    .select("c.*") //
                    .from("categorias c") //
                    .join("productos_categorias pc") //
                    .on("c.id_categoria = pc.id_categoria") //
                    .where("pc.id_producto = ?") //
                    .build(); //

}
