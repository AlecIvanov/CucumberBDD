package tests;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostGres {
    // Path for postgesql databaseurl link to set up a connection
    // jdbc = java database connection
    String demoHR = "jdbc:postgresql://localhost:5432/demoHR"; // always the same

    // create a username (always the same)
    String dbUsername = "postgres";

    // Enter a password for Postgres database. For Mac users this field should be empty
    String dbPassword = "";

    @Test
    public void countTest() throws SQLException {
       // stablish a Database Connection
       // for JDBC connection we use interface Connections
        Connection connection = DriverManager.getConnection(demoHR, dbUsername, dbPassword);

        // Establish an enterface called Statement to read from database
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        //ResultSet interface allows us to query and execute the command
        ResultSet resultSet = statement.executeQuery("select * from employees limit 5");

        // last row of the table
        resultSet.last();

        // verify that query contains more than 0 records(rows)
        int rowsCount = resultSet.getRow(); // 9
        System.out.println(rowsCount);
        Assert.assertTrue(rowsCount > 0);

        resultSet.close();
        statement.close();
        connection.close();
    }

    @Test
    public void testDatabaseConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(demoHR, dbUsername, dbPassword);

        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet resultSet = statement.executeQuery("select * from employees limit 5");

        resultSet.last();

        // navigate inside the table rows and columns to retrieve result
        resultSet.first();
        while(resultSet.next()){
            // print the column with index 1
            System.out.println(resultSet.getString(2) + " " +
                             resultSet.getString("dept_no") + " " +
                             resultSet.getString("dept_name"));
        }
    }

    @Test
    public void metaData() throws SQLException{
        Connection connection = DriverManager.getConnection(demoHR, dbUsername, dbPassword);

        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        String sql = "select * from employees limit 5";
        ResultSet resultSet = statement.executeQuery(sql);

        // Metadata
        // Metadata is the describing the data that is being stored in your Data source
        // Metadata generally includes the name, size and number of rows for each table present
        // in the database
        DatabaseMetaData dbMetadata = connection.getMetaData();

        // return username of the database
        System.out.println("Database User: " + dbMetadata.getUserName());

        // return database ype
        System.out.println("Database type: " + dbMetadata.getDatabaseProductName());

        // ResultSetMetadata will query the results
        ResultSetMetaData rsMetadata = resultSet.getMetaData();
        System.out.println("Column count: " + rsMetadata.getColumnCount());

        for(int i = 1; i <= rsMetadata.getColumnCount(); i++){
            System.out.println(i + " --> " + rsMetadata.getColumnName(i));
        }

        // throw ResultSet into a list of Maps
        // create a list of Maps

        // stavim Object t.k. u nas v table est i int i String i char znacheniya
        // a String v Map, t.k. eto names of the column
        List<Map<String, Object>> list = new ArrayList<>();
        ResultSetMetaData resultSetMetaData2 = resultSet.getMetaData();

        int colCount = resultSetMetaData2.getColumnCount();// numerical value from database

        while (resultSet.next()){
            Map<String, Object> rowMap = new HashMap<>();
            for(int col = 1; col <= colCount; col++){
                rowMap.put(resultSetMetaData2.getColumnName(col), resultSet.getObject(col));
            }
            list.add(rowMap);
        }

        for(Map<String, Object> emp : list){
            System.out.println(emp.get("emp_no"));
        }

        resultSet.close();
        statement.close();
        connection.close();

    }
}
