package de.greenrobot.daotest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import de.greenrobot.dao.internal.JDBCUtils;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import de.greenrobot.daotest.ExtendsImplementsEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table EXTENDS_IMPLEMENTS_ENTITY.
*/
public class ExtendsImplementsEntityDao extends AbstractDao<ExtendsImplementsEntity, Long> {

    public static final String TABLENAME = "EXTENDS_IMPLEMENTS_ENTITY";

    /**
     * Properties of entity ExtendsImplementsEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Text = new Property(1, String.class, "text", false, "TEXT");
    };


    public ExtendsImplementsEntityDao(DaoConfig config) {
        super(config);
    }
    
    public ExtendsImplementsEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Connection connection, boolean ifNotExists) throws SQLException {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        JDBCUtils.execute( connection, "CREATE TABLE " + constraint + "'EXTENDS_IMPLEMENTS_ENTITY' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'TEXT' TEXT);"); // 1: text
    }

    /** Drops the underlying database table. */
    public static void dropTable(Connection connection, boolean ifExists) throws SQLException {
        JDBCUtils.execute( connection, "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'EXTENDS_IMPLEMENTS_ENTITY'");
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(PreparedStatement statement, ExtendsImplementsEntity entity) throws SQLException {
 
        Long id = entity.getId();
        if (id != null) {
            statement.setLong(1, id);
        }
 
        String text = entity.getText();
        if (text != null) {
            statement.setString(2, text);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(ResultSet resultSet, int offset) throws SQLException {
        return JDBCUtils.isNull(resultSet, offset + 1) ? null : resultSet.getLong(offset + 1);
    }    

    /** @inheritdoc */
    @Override
    public ExtendsImplementsEntity readEntity(ResultSet resultSet, int offset) throws SQLException {
		int index = 1;
        ExtendsImplementsEntity entity = new ExtendsImplementsEntity(
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++), // id
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++) // text
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(ResultSet resultSet, ExtendsImplementsEntity entity, int offset) throws SQLException {
		int index = 1;
        entity.setId(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++));
        entity.setText(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ExtendsImplementsEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ExtendsImplementsEntity entity) {
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
