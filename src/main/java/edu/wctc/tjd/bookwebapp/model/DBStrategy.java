package edu.wctc.tjd.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

public interface DBStrategy {

    public abstract void openConnection(String driverClass, String url,
            String userName, String password)
            throws ClassNotFoundException, SQLException;

    public abstract void closeConnection() throws SQLException;

    public List<Map<String, Object>> findAllRecords(String tableName,
            int maxRecords) throws SQLException;

    public int deleteRecordbyPrimaryKey(String tableName, String primaryKey, Object primaryKeyValue) throws SQLException;

    public int updateRecordById(String tableName, List<String> colNames, List<Object> colValues, String pkColName, Object value) throws SQLException;

    public int updatebyID(String tableName, List<String> colNames, List<Object> colValues, String pkColName, Object value) throws SQLException;

    public boolean insertRecord(String tableName, List colDescriptors,
            List colValues) throws SQLException;
     public Map<String, Object> findById(String tableName, String primaryKeyFieldName,
            Object primaryKeyValue) throws SQLException;
     
      void openConnection(DataSource ds) throws SQLException;

}
