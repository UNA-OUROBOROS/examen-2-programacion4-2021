package net.xravn.examen2.cli;

import net.xravn.examen2.controller.sql.DBConectionController;

public class testBackend {
    public Integer testDatabase() {
        try {
            DBConectionController controller = DBConectionController.getInstance();
            System.out.println("Connectando a la base de datos");
            if (controller.isConnected()) {
                System.out.println("Conexión exitosa");
                return 0;
            } else {
                System.out.println("Conexión fallida");
                return -1;
            }
        } catch (Exception ex) {
            System.err.println("Error al conectar a la base de datos");
            System.err.println(ex.getMessage());
            return -1;
        }
    }

    public void testAll() {
        System.out.println("=========================================");
        System.out.println("Performing general tests");
        System.out.println("=========================================");
        System.out.println("Testing database connection");
        System.out.println("-----------------------------------------");
        System.out.println("Result:" + testDatabase());
        System.out.println("Testing database connection finished");
        System.out.println("=========================================");
    }

}