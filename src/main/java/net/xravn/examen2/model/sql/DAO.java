package net.xravn.examen2.model.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import net.xravn.examen2.controller.sql.DBConectionController;

public class DAO {
    protected Connection getConnection() {
        return DBConectionController.getInstance().getConnection();
    }

    protected boolean ejecutarUpdate(String query, Object... params) {
        try {
            PreparedStatement stm = getConnection().prepareStatement(query);
            stm.clearParameters();
            for (int i = 0; i < params.length; i++) {
                stm.setObject(i + 1, params[i]);
            }
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    protected List<Map<String, Object>> ejecutarConsulta(String query, Object... params) {
        try (PreparedStatement stm = getConnection().prepareStatement(query)) {
            stm.clearParameters();
            for (int i = 0; i < params.length; i++) {
                stm.setObject(i + 1, params[i]);
            }
            ResultSet rs = stm.executeQuery();
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            Map<String, Object> row = null;

            ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
            Integer columnCount = metaData.getColumnCount();

            while (rs.next()) {
                row = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                resultList.add(row);
            }
            return resultList;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
