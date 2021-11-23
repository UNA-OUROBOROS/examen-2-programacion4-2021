package net.xravn.examen2.model.dao;

import net.xravn.examen2.model.sql.DAO;
import net.xravn.examen2.model.sql.QueryBuilder;

public class DAOProductos extends DAO {

    private static String queryAgregarProducto(){
        return new QueryBuilder()
                .insertInto("productos")
                .values("nombre", "descripcion", "url_imagen", "precio", "stock")
                .build();
    }

    private static String queryModificarProducto(){
        return new QueryBuilder()
                .update("productos")
                .set("nombre", "descripcion", "url_imagen", "precio", "stock")
                .where("id = ? and ? + stock >= 0")
                .build();
    }

}
