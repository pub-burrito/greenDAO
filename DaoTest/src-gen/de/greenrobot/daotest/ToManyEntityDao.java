package de.greenrobot.daotest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import de.greenrobot.dao.internal.JDBCUtils;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import de.greenrobot.daotest.ToManyEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table TO_MANY_ENTITY.
*/
public class ToManyEntityDao extends AbstractDao<ToManyEntity, Long> {

    public static final String TABLENAME = "TO_MANY_ENTITY";

    /**
     * Properties of entity ToManyEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property SourceJoinProperty = new Property(1, String.class, "sourceJoinProperty", false, "SOURCE_JOIN_PROPERTY");
    };

	private DaoSession daoSession;

    public ToManyEntityDao(DaoConfig config) {
        super(config);
    }
    
    public ToManyEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Connection connection, boolean ifNotExists) throws SQLException {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        JDBCUtils.execute( connection, "CREATE TABLE " + constraint + "'TO_MANY_ENTITY' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'SOURCE_JOIN_PROPERTY' TEXT);"); // 1: sourceJoinProperty
    }

    /** Drops the underlying database table. */
    public static void dropTable(Connection connection, boolean ifExists) throws SQLException {
        JDBCUtils.execute( connection, "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TO_MANY_ENTITY'");
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(PreparedStatement statement, ToManyEntity entity) throws SQLException {
 
        Long id = entity.getId();
        if (id != null) {
            statement.setLong(1, id);
        }
 
        String sourceJoinProperty = entity.getSourceJoinProperty();
        if (sourceJoinProperty != null) {
            statement.setString(2, sourceJoinProperty);
        }
    }

    @Override
    protected void attachEntity(ToManyEntity entity) {
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
    public ToManyEntity readEntity(ResultSet resultSet, int offset) throws SQLException {
		int index = 1;
        ToManyEntity entity = new ToManyEntity(
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++), // id
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++) // sourceJoinProperty
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(ResultSet resultSet, ToManyEntity entity, int offset) throws SQLException {
		int index = 1;
        entity.setId(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++));
        entity.setSourceJoinProperty(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ToManyEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ToManyEntity entity) {
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
