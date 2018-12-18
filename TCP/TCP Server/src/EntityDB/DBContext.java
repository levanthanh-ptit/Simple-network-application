/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ThanhLeVan
 */
public class DBContext{
    private Connection connection;

    public DBContext(String database, String userName, String password) {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName="+database+";user="+userName+";password="+password);    
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void closeContext(){
        try {
            this.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ResultSet executeQuery(String sqlQueryString) throws SQLException{
        Statement statement = connection.createStatement(); 
        return statement.executeQuery(sqlQueryString);
    }
     public ResultSet executeStoredProcedure(String sqlBatchString) throws SQLException{
        Statement statement = connection.createStatement(); 
        statement.addBatch(sqlBatchString);
        statement.executeBatch();
        statement.getMoreResults();
        ResultSet resultSet = statement.getResultSet();
        statement.clearBatch();
        return resultSet;
    }
    
}
