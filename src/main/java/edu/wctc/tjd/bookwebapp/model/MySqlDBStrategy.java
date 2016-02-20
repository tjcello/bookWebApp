package edu.wctc.tjd.bookwebapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class MySqlDBStrategy implements DBStrategy {

    private Connection conn;

    public MySqlDBStrategy(String driverClass, String url, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, username, password);

    }

    MySqlDBStrategy() {

    }

    @Override
    public void openConnection(String driverClass, String url, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, username, password);

    }

    @Override
    public void closeConnection() throws SQLException {
        conn.close();

    }

    @Override
    public int updateRecordById(String tableName, List<String> colNames, List<Object> colValues, String pkColName, Object value) throws SQLException {
        PreparedStatement pstmt = null;
        int recsUpdated = 0;

        
        try {
            pstmt = buildUpdateStatement(conn, tableName, colNames, pkColName);

            final Iterator i = colValues.iterator();
            int index = 1;
            Object obj = null;

           
            while (i.hasNext()) {
                obj = i.next();
                pstmt.setObject(index++, obj);
            }
           
            pstmt.setObject(index, value);

            recsUpdated = pstmt.executeUpdate();

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                throw e;
            } 
        } 

        return recsUpdated;
    }

   
    @Override
    public List<Map<String, Object>> findAllRecords(String tableName, int maxRecords) throws SQLException {
        String sql;
        if (maxRecords < 1) {
            sql = "select * from " + tableName;
        } else {
            sql = ("select * from " + tableName + " limit " + maxRecords);
        }

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        List<Map<String, Object>> records = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> record = new HashMap<>();
            for (int colNo = 1; colNo <= columnCount; colNo++) {
                Object colData = rs.getObject(colNo);
                String colName = rsmd.getColumnName(colNo);
                record.put(colName, colData);
            }
            records.add(record);
        }
        return records;
    }


    @Override
    public int deleteRecordbyPrimaryKey(String tableName, String primaryKey, Object primaryKeyValue) throws SQLException {
        int recordsDeleted = 0;
        PreparedStatement pstmt = null;

        final String sql = "Delete FROM " + tableName + " WHERE " + primaryKey + " = ?";

        pstmt = conn.prepareStatement(sql);

        if (primaryKey != null) {
            if (primaryKeyValue instanceof String) {
                pstmt.setString(1, (String) primaryKeyValue);
            } else {
                pstmt.setObject(1, primaryKeyValue);
            }
            recordsDeleted = pstmt.executeUpdate();

        }
        return recordsDeleted;
    }

    private PreparedStatement buildUpdateStatement(Connection connection, String tableName,
            List colDescriptors, String whereField)
            throws SQLException {
        StringBuffer sql = new StringBuffer("UPDATE ");
        (sql.append(tableName)).append(" SET ");
        final Iterator i = colDescriptors.iterator();
        while (i.hasNext()) {
            (sql.append((String) i.next())).append(" = ?, ");
        }
        sql = new StringBuffer((sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")));
        ((sql.append(" WHERE ")).append(whereField)).append(" = ?");
        final String finalSQL = sql.toString();
        return connection.prepareStatement(finalSQL);
    }

    private PreparedStatement buildInsertStatement(Connection conn, String tableName, List columnNames) throws SQLException { 
        StringBuffer sql = new StringBuffer("Insert Into " + tableName + " (");
        final Iterator i = columnNames.iterator(); 
        while (i.hasNext()) {
            sql.append(i.next() + ", ");
        }
        sql = new StringBuffer((sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")) + ") Values ("); 
        for (int m = 0; m < columnNames.size(); m++) {
            sql.append("?, ");
        }
        final String finalSQL = ((sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")) + ")");
        return conn.prepareStatement(finalSQL);
    }

    @Override
    public int updatebyID(String tableName, List<String> colNames, List<Object> colValues, String pkColName, Object value) throws SQLException {
        PreparedStatement pstmt = null;
        int recsUpdated = 0;

        try {
            pstmt = buildUpdateStatement(conn, tableName, colNames, pkColName);

            final Iterator i = colValues.iterator();
            int index = 1;
            Object obj = null;

            while (i.hasNext()) {
                obj = i.next();
                pstmt.setObject(index++, obj);
            }
            
            pstmt.setObject(index, value);

            recsUpdated = pstmt.executeUpdate();

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                throw e;
            } 
        }

        return recsUpdated;
    }

    
    @Override
    public int insertRecord(String tableName, List<String> columnNames, List<Object> columnValues) throws SQLException {
        int recordsInserted = 0;
        PreparedStatement preSmt = null;

        
        try {
            preSmt = buildInsertStatement(conn, tableName, columnNames);

            final Iterator i = columnValues.iterator();
            int index = 1; 
            while (i.hasNext()) {
                final Object obj = i.next();
                preSmt.setObject(index++, obj);
            }

            recordsInserted = preSmt.executeUpdate();
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        } finally {
            try {
                preSmt.close();
                conn.close();
            } catch (SQLException e) {
                throw e;
            } 
        } 

        return recordsInserted;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DBStrategy db = new MySqlDBStrategy();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        List<Map<String, Object>> rawData = db.findAllRecords("author", 0);

        System.out.println(rawData);

        db.closeConnection();

        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");

        List<String> colNames = Arrays.asList("author_name");
        List<Object> colValues = Arrays.asList("Lucifer");
        int result = db.updateRecordById("author", colNames, colValues, "author_id", 1);

        db.closeConnection();

        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");

        rawData = db.findAllRecords("author", 0);

        System.out.println(rawData);

        db.closeConnection();

    }

    @Override
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteById(String author, String author_id, Object id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
