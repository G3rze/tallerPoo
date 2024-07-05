package com.example.demo.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    private Connection con;

    public Db() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/TallerPoo", "root", "Mrdl2005");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
}
