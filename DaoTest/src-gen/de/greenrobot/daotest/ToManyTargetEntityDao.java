package de.greenrobot.daotest;

import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import de.greenrobot.dao.internal.JDBCUtils;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import de.greenrobot.daotest.ToManyTargetEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table TO_MANY_TARGET_ENTITY.
*/
public class ToManyTargetEntityDao extends AbstractDao<ToManyTargetEntity, Long> {

    public static final String TABLENAME = "TO_MANY_TARGET_ENTITY";

    /**
     * Properties of entity ToManyTargetEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property ToManyId = new Property(0, Long.class, "toManyId", false, "TO_MANY_ID");
        public final static Property ToManyIdDesc = new Property(1, Long.class, "toManyIdDesc", false, "TO_MANY_ID_DESC");
        public final static Property Id = new Property(2, Long.class, "id", true, "_id");
        public final static Property TargetJoinProperty = new Property(3, String.class, "targetJoinProperty", false, "TARGET_JOIN_PROPERTY");
    };

    private Query<ToManyTargetEntity> toManyEntity_ToManyTargetEntityListQuery;
    private Query<ToManyTargetEntity> toManyEntity_ToManyDescListQuery;
    private Query<ToManyTargetEntity> toManyEntity_ToManyByJoinPropertyQuery;
    private Query<ToManyTargetEntity> toManyEntity_ToManyJoinTwoQuery;

    public ToManyTargetEntityDao(DaoConfig config) {
        super(config);
    }
    
    public ToManyTargetEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Connection connection, boolean ifNotExists) throws SQLException {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        JDBCUtils.execute( connection, "CREATE TABLE " + constraint + "'TO_MANY_TARGET_ENTITY' (" + //
                "'TO_MANY_ID' INTEGER," + // 0: toManyId
                "'TO_MANY_ID_DESC' INTEGER," + // 1: toManyIdDesc
                "'_id' INTEGER PRIMARY KEY ," + // 2: id
                "'TARGET_JOIN_PROPERTY' TEXT);"); // 3: targetJoinProperty
    }

    /** Drops the underlying database table. */
    public static void dropTable(Connection connection, boolean ifExists) throws SQLException {
        JDBCUtils.execute( connection, "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TO_MANY_TARGET_ENTITY'");
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(PreparedStatement statement, ToManyTargetEntity entity) throws SQLException {
 
        Long toManyId = entity.getToManyId();
        if (toManyId != null) {
            statement.setLong(1, toManyId);
        }
 
        Long toManyIdDesc = entity.getToManyIdDesc();
        if (toManyIdDesc != null) {
            statement.setLong(2, toManyIdDesc);
        }
 
        Long id = entity.getId();
        if (id != null) {
            statement.setLong(3, id);
        }
 
        String targetJoinProperty = entity.getTargetJoinProperty();
        if (targetJoinProperty != null) {
            statement.setString(4, targetJoinProperty);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(ResultSet resultSet, int offset) throws SQLException {
        return JDBCUtils.isNull(resultSet, offset + 1) ? null : resultSet.getLong(offset + 1);
    }    

    /** @inheritdoc */
    @Override
    public ToManyTargetEntity readEntity(ResultSet resultSet, int offset) throws SQLException {
		int index = 1;
        ToManyTargetEntity entity = new ToManyTargetEntity(
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++), // toManyId
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++), // toManyIdDesc
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++), // id
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++) // targetJoinProperty
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(ResultSet resultSet, ToManyTargetEntity entity, int offset) throws SQLException {
		int index = 1;
        entity.setToManyId(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++));
        entity.setToManyIdDesc(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++));
        entity.setId(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++));
        entity.setTargetJoinProperty(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ToManyTargetEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ToManyTargetEntity entity) {
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
    
    /** Internal query to resolve the "toManyTargetEntityList" to-many relationship of ToManyEntity. */
    public List<ToManyTargetEntity> _queryToManyEntity_ToManyTargetEntityList(Long toManyId) throws SQLException {
        synchronized (this) {
            if (toManyEntity_ToManyTargetEntityListQuery == null) {
                QueryBuilder<ToManyTargetEntity> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.ToManyId.eq(null));
                queryBuilder.orderRaw("_id ASC");
                toManyEntity_ToManyTargetEntityListQuery = queryBuilder.build();
            }
        }
        Query<ToManyTargetEntity> query = toManyEntity_ToManyTargetEntityListQuery.forCurrentThread();
        query.setParameter(0, toManyId);
        return query.list();
    }

    /** Internal query to resolve the "toManyDescList" to-many relationship of ToManyEntity. */
    public List<ToManyTargetEntity> _queryToManyEntity_ToManyDescList(Long toManyIdDesc) throws SQLException {
        synchronized (this) {
            if (toManyEntity_ToManyDescListQuery == null) {
                QueryBuilder<ToManyTargetEntity> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.ToManyIdDesc.eq(null));
                queryBuilder.orderRaw("_id DESC");
                toManyEntity_ToManyDescListQuery = queryBuilder.build();
            }
        }
        Query<ToManyTargetEntity> query = toManyEntity_ToManyDescListQuery.forCurrentThread();
        query.setParameter(0, toManyIdDesc);
        return query.list();
    }

    /** Internal query to resolve the "toManyByJoinProperty" to-many relationship of ToManyEntity. */
    public List<ToManyTargetEntity> _queryToManyEntity_ToManyByJoinProperty(String targetJoinProperty) throws SQLException {
        synchronized (this) {
            if (toManyEntity_ToManyByJoinPropertyQuery == null) {
                QueryBuilder<ToManyTargetEntity> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.TargetJoinProperty.eq(null));
                queryBuilder.orderRaw("_id ASC");
                toManyEntity_ToManyByJoinPropertyQuery = queryBuilder.build();
            }
        }
        Query<ToManyTargetEntity> query = toManyEntity_ToManyByJoinPropertyQuery.forCurrentThread();
        query.setParameter(0, targetJoinProperty);
        return query.list();
    }

    /** Internal query to resolve the "toManyJoinTwo" to-many relationship of ToManyEntity. */
    public List<ToManyTargetEntity> _queryToManyEntity_ToManyJoinTwo(Long toManyId, String targetJoinProperty) throws SQLException {
        synchronized (this) {
            if (toManyEntity_ToManyJoinTwoQuery == null) {
                QueryBuilder<ToManyTargetEntity> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.ToManyId.eq(null));
                queryBuilder.where(Properties.TargetJoinProperty.eq(null));
                queryBuilder.orderRaw("TARGET_JOIN_PROPERTY DESC,_id DESC");
                toManyEntity_ToManyJoinTwoQuery = queryBuilder.build();
            }
        }
        Query<ToManyTargetEntity> query = toManyEntity_ToManyJoinTwoQuery.forCurrentThread();
        query.setParameter(0, toManyId);
        query.setParameter(1, targetJoinProperty);
        return query.list();
    }

}
