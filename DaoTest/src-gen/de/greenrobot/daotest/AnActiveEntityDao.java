package de.greenrobot.daotest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import de.greenrobot.dao.internal.JDBCUtils;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import de.greenrobot.daotest.AnActiveEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table AN_ACTIVE_ENTITY.
*/
public class AnActiveEntityDao extends AbstractDao<AnActiveEntity, Long> {

    public static final String TABLENAME = "AN_ACTIVE_ENTITY";

    /**
     * Properties of entity AnActiveEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Text = new Property(1, String.class, "text", false, "TEXT");
    };

	private DaoSession daoSession;

    public AnActiveEntityDao(DaoConfig config) {
        super(config);
    }
    
    public AnActiveEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Connection connection, boolean ifNotExists) throws SQLException {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        JDBCUtils.execute( connection, "CREATE TABLE " + constraint + "'AN_ACTIVE_ENTITY' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'TEXT' TEXT);"); // 1: text
    }

    /** Drops the underlying database table. */
    public static void dropTable(Connection connection, boolean ifExists) throws SQLException {
        JDBCUtils.execute( connection, "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'AN_ACTIVE_ENTITY'");
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(PreparedStatement statement, AnActiveEntity entity) throws SQLException {
 
        Long id = entity.getId();
        if (id != null) {
            statement.setLong(1, id);
        }
 
        String text = entity.getText();
        if (text != null) {
            statement.setString(2, text);
        }
    }

    @Override
    protected void attachEntity(AnActiveEntity entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(ResultSet resultSet, int offset) throws SQLException {
        return JDBCUtils.isNull(resultSet, offset + 1) ? null : resultSet.getLong(offset + 1);
    }    

    /** @inheritdoc */
    @Override
    public AnActiveEntity readEntity(ResultSet resultSet, int offset) throws SQLException {
		int index = 1;
        AnActiveEntity entity = new AnActiveEntity(
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++), // id
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++) // text
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(ResultSet resultSet, AnActiveEntity entity, int offset) throws SQLException {
		int index = 1;
        entity.setId(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++));
        entity.setText(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(AnActiveEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(AnActiveEntity entity) {
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
