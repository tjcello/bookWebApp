package edu.wctc.tjd.bookwebapp.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import javax.enterprise.context.SessionScoped;

@SessionScoped
public class MySqlDBStrategy implements DBStrategy, Serializable {

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
    public final boolean insertRecord(String tableName, List colDescriptors,
            List colValues) throws SQLException {

        PreparedStatement pstmt = null;
        int recsUpdated = 0;

		// do this in an excpetion handler so that we can depend on the
        // finally clause to close the connection
        try {
            pstmt = buildInsertStatement(conn, tableName, colDescriptors);

            final Iterator i = colValues.iterator();
            int index = 1;
            while (i.hasNext()) {
                final Object obj = i.next();
                pstmt.setObject(index++, obj);
            }
            recsUpdated = pstmt.executeUpdate();

        
        
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                throw new SQLException();
            } // end try
        } // end finally

        if (recsUpdated == 1) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public final Map<String, Object> findById(String tableName, String primaryKeyFieldName,
            Object primaryKeyValue) throws SQLException{

        String sql = "SELECT * FROM " + tableName + " WHERE " + primaryKeyFieldName + " = ?";
        PreparedStatement stmt = null;
        final Map<String, Object> record = new HashMap();

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setObject(1, primaryKeyValue);
            ResultSet rs = stmt.executeQuery();
            final ResultSetMetaData metaData = rs.getMetaData();
            final int fields = metaData.getColumnCount();

            // Retrieve the raw data from the ResultSet and copy the values into a Map
            // with the keys being the column names of the table.
            if (rs.next()) {
                for (int i = 1; i <= fields; i++) {
                    record.put(metaData.getColumnName(i), rs.getObject(i));
                }
            }
            
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                throw new SQLException();
            } // end try
        } // end finally

        return record;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DBStrategy db = new MySqlDBStrategy();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        List<Map<String, Object>> rawData = db.findAllRecords("author", 0);

        System.out.println(rawData);

        db.closeConnection();

        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        List<String> colNames2 = Arrays.asList("author_name", "date_added");
        List<Object> colValues2 = Arrays.asList("Your Mother", "1991-06-05");
        db.insertRecord("author", colNames2, colValues2);
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

}
