package com.company;

import java.sql.*;

public class DataBase {
    Connection dbconnection;

    public Connection getDbconnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://localhost:3306/message?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbconnection = DriverManager.getConnection(connectionString, "root","root");
        return dbconnection;
    }

    public void setInfo(User user) {
        String insert = "INSERT INTO message(message) VALUES(?)";
        try {
            PreparedStatement prSt = getDbconnection().prepareStatement(insert);
            prSt.setString(1, user.getMsg());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getInfo() {
        ResultSet rs = null;
        String select = "SELECT * FROM message";
        try {
            PreparedStatement prSt = getDbconnection().prepareStatement(select);

            rs = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
