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
        this.query += "VALUES (";
        for (int i = 0; i < values.length; i++) {
            this.query += values[i];
            if (i < values.length - 1) {
                this.query += ", ";
            }
        }
        this.query += ") ";
        return this;
    }

    public QueryBuilder update(String table) {
        this.query += "UPDATE " + table + " ";
        return this;
    }

    public QueryBuilder set(String column, String value) {
        this.query += "SET " + column + " = " + value + " ";
        return this;
    }

    public String build() {
        return this.query;
    }
}
