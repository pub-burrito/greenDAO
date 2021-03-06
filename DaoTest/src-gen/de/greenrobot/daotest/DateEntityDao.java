package de.greenrobot.daotest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import de.greenrobot.dao.internal.JDBCUtils;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import de.greenrobot.daotest.DateEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table DATE_ENTITY.
*/
public class DateEntityDao extends AbstractDao<DateEntity, Long> {

    public static final String TABLENAME = "DATE_ENTITY";

    /**
     * Properties of entity DateEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Date = new Property(1, java.util.Date.class, "date", false, "DATE");
        public final static Property DateNotNull = new Property(2, java.util.Date.class, "dateNotNull", false, "DATE_NOT_NULL");
    };


    public DateEntityDao(DaoConfig config) {
        super(config);
    }
    
    public DateEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Connection connection, boolean ifNotExists) throws SQLException {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        JDBCUtils.execute( connection, "CREATE TABLE " + constraint + "'DATE_ENTITY' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'DATE' INTEGER," + // 1: date
                "'DATE_NOT_NULL' INTEGER NOT NULL );"); // 2: dateNotNull
    }

    /** Drops the underlying database table. */
    public static void dropTable(Connection connection, boolean ifExists) throws SQLException {
        JDBCUtils.execute( connection, "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'DATE_ENTITY'");
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(PreparedStatement statement, DateEntity entity) throws SQLException {
 
        Long id = entity.getId();
        if (id != null) {
            statement.setLong(1, id);
        }
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            statement.setLong(2, date.getTime());
        }
        statement.setLong(3, entity.getDateNotNull().getTime());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(ResultSet resultSet, int offset) throws SQLException {
        return JDBCUtils.isNull(resultSet, offset + 1) ? null : resultSet.getLong(offset + 1);
    }    

    /** @inheritdoc */
    @Override
    public DateEntity readEntity(ResultSet resultSet, int offset) throws SQLException {
		int index = 1;
        DateEntity entity = new DateEntity(
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++), // id
            JDBCUtils.isNull(resultSet, offset + index) ? null : new java.util.Date(resultSet.getLong(offset + index++)), // date
            new java.util.Date(resultSet.getLong(offset + index++)) // dateNotNull
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(ResultSet resultSet, DateEntity entity, int offset) throws SQLException {
		int index = 1;
        entity.setId(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++));
        entity.setDate(JDBCUtils.isNull(resultSet, offset + index) ? null : new java.util.Date(resultSet.getLong(offset + index++)));
        entity.setDateNotNull(new java.util.Date(resultSet.getLong(offset + index++)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(DateEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(DateEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
