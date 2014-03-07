package de.greenrobot.daotest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.greenrobot.dao.internal.JDBCUtils;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import de.greenrobot.daotest.SqliteMaster;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table SQLITE_MASTER.
*/
public class SqliteMasterDao extends AbstractDao<SqliteMaster, Long> {

    public static final String TABLENAME = "SQLITE_MASTER";

    /**
     * Properties of entity SqliteMaster.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Type = new Property(0, String.class, "type", false, "TYPE");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property TableName = new Property(2, String.class, "tableName", false, "tbl_name");
        public final static Property Rootpage = new Property(3, Long.class, "rootpage", false, "ROOTPAGE");
        public final static Property Sql = new Property(4, String.class, "sql", false, "SQL");
    };


    public SqliteMasterDao(DaoConfig config) {
        super(config);
    }
    
    public SqliteMasterDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(PreparedStatement statement, SqliteMaster entity) throws SQLException {
 
        String type = entity.getType();
        if (type != null) {
            statement.setString(1, type);
        }
 
        String name = entity.getName();
        if (name != null) {
            statement.setString(2, name);
        }
 
        String tableName = entity.getTableName();
        if (tableName != null) {
            statement.setString(3, tableName);
        }
 
        Long rootpage = entity.getRootpage();
        if (rootpage != null) {
            statement.setLong(4, rootpage);
        }
 
        String sql = entity.getSql();
        if (sql != null) {
            statement.setString(5, sql);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(ResultSet resultSet, int offset) throws SQLException {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public SqliteMaster readEntity(ResultSet resultSet, int offset) throws SQLException {
		int index = 1;
        SqliteMaster entity = new SqliteMaster(
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++), // type
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++), // name
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++), // tableName
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++), // rootpage
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++) // sql
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(ResultSet resultSet, SqliteMaster entity, int offset) throws SQLException {
		int index = 1;
        entity.setType(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++));
        entity.setName(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++));
        entity.setTableName(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++));
        entity.setRootpage(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++));
        entity.setSql(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(SqliteMaster entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(SqliteMaster entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
