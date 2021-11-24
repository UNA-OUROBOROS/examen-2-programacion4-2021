package net.xravn.examen2.model.sql;

public class QueryBuilder {
    private String query;

    public QueryBuilder() {
        this.query = "";
    }

    public QueryBuilder select(String... columns) {
        this.query += "SELECT ";
        for (int i = 0; i < columns.length; i++) {
            this.query += columns[i];
            if (i < columns.length - 1) {
                this.query += ", ";
            }
        }
        this.query += " ";
        return this;
    }

    public QueryBuilder from(String table) {
        this.query += "FROM " + table + " ";
        return this;
    }

    public QueryBuilder where(String condition) {
        this.query += "WHERE " + condition + " ";
        return this;
    }

    public QueryBuilder orderBy(String column, String order) {
        this.query += "ORDER BY " + column + " " + order + " ";
        return this;
    }

    public QueryBuilder groupBy(String column) {
        this.query += "GROUP BY " + column + " ";
        return this;
    }

    public QueryBuilder having(String condition) {
        this.query += "HAVING " + condition + " ";
        return this;
    }

    public QueryBuilder insertInto(String table) {
        this.query += "INSERT INTO " + table + " ";
        return this;
    }

    public QueryBuilder values(String... values) {
        // primero insertamos los nombres de las columnas
        this.query += "(";
        for (int i = 0; i < values.length; i++) {
            this.query += values[i];
            if (i < values.length - 1) {
                this.query += ", ";
            }
        }
        this.query += ") VALUES (";
        // luego usamos los parametros del query como prepared statement
        for (int i = 0; i < values.length; i++) {
            this.query += "?";
            if (i < values.length - 1) {
                this.query += ", ";
            }
        }
        // y finalmente cerramos el query
        this.query += ") ";
        return this;
    }

    public QueryBuilder update(String table) {
        this.query += "UPDATE " + table + " ";
        return this;
    }

    public QueryBuilder set(String... columnas) {
        // SET columna1 = ?, columna2 = ? ...
        this.query += "SET ";
        for (int i = 0; i < columnas.length; i++) {
            this.query += columnas[i] + " = ?";
            if (i < columnas.length - 1) {
                this.query += ", ";
            }
        }
        this.query += " ";
        return this;
    }
    public QueryBuilder join(String tabla) {
        // SELECT * FROM table1 JOIN table2 ON table1.id = table2.id
        this.query += "JOIN " + tabla + " ";
        return this;
    }

    public QueryBuilder on(String condicion) {
        // ON table1.id = table2.id
        this.query += "ON " + condicion + " ";
        return this;
    }
    public String build() {
        return this.query;
    }


}
