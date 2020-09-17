package utils;

import com.google.common.collect.Maps;

import java.sql.*;
import java.util.*;

public class DBUtility {
    private static String propertyPath = "src/test/resources/conf/configuration.properties";

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    //                                        PostgreSQL           demoHR
    public static void establishConnection(Database dbType, String database) { // Database is Database class

        try {
            switch (dbType) {
                case POSTGRESQL:
                    connection = DriverManager.getConnection(ConfigReader.readProperty(database, propertyPath),
                            ConfigReader.readProperty("dbUserName", propertyPath),
                            ConfigReader.readProperty("dbPassword", propertyPath));
                    if(connection != null){
                        System.out.println("Postgres DB connection Successfull");
                    }else{
                        System.out.println("Failed to connect Postgres Database");
                    }
                    break;

                case ORACLE:
                    break;

                case MONGO:
                    break;

                default:
                    connection = null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void getRowsCount(String query) throws SQLException {
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        resultSet = statement.executeQuery(query);

        resultSet.last();
        System.out.println("Total Count: " + resultSet.getRow());

    }

    public static List<Map<String, Object>> getQueryResults(String query) {
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(query);
            List<Map<String, Object>> list = new ArrayList<>();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> rowMap = new HashMap<>();
                for (int col = 1; col <= columnCount; col++) {
                    rowMap.put(resultSetMetaData.getColumnName(col), resultSet.getObject(col));
                }
                list.add(rowMap);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnection(){
        try{
            if(resultSet != null){
                resultSet.close();
            }if(statement != null){
                statement.close();
            }if(connection != null){
                connection.close();
            }
            System.out.println("Database closed successfully");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
