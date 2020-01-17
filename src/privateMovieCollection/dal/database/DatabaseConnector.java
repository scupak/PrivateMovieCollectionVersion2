/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privateMovieCollection.dal.database;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import privateMovieCollection.dal.PmcDalException;

/**
 *
 * @author andreasvillumsen
 */
public class DatabaseConnector {

    private SQLServerDataSource dataSource;

    /**
     * DatabaseConnector constructor
     * the class takes the info from the DBSettings.txt file and establishes a connection to the database. 
     */
    public DatabaseConnector() throws PmcDalException{
        try {
            Properties props = new Properties();
            
            props.load(new FileReader("DBSettings.txt"));
            
            dataSource = new SQLServerDataSource();
            dataSource.setDatabaseName(props.getProperty("database"));
            dataSource.setUser(props.getProperty("user"));
            dataSource.setPassword(props.getProperty("password"));
            dataSource.setServerName(props.getProperty("server"));
        } catch (FileNotFoundException ex) {
           throw new PmcDalException("there was a problem with filereading", ex);
        } catch (IOException ex) {
            throw new PmcDalException("there was a problem with filereading", ex);
        }
    }

    /**
     * Get the connection to the database
     *
     * @return Database connection
     */
    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }
}
