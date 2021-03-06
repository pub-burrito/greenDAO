package de.greenrobot.daotest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import de.greenrobot.dao.internal.JDBCUtils;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import de.greenrobot.daotest.SpecialNamesEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table SPECIAL_NAMES_ENTITY.
*/
public class SpecialNamesEntityDao extends AbstractDao<SpecialNamesEntity, Long> {

    public static final String TABLENAME = "SPECIAL_NAMES_ENTITY";

    /**
     * Properties of entity SpecialNamesEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Count = new Property(1, String.class, "count", false, "COUNT");
        public final static Property Select = new Property(2, String.class, "select", false, "SELECT");
        public final static Property Sum = new Property(3, String.class, "sum", false, "SUM");
        public final static Property Avg = new Property(4, String.class, "avg", false, "AVG");
        public final static Property Join = new Property(5, String.class, "join", false, "JOIN");
        public final static Property Distinct = new Property(6, String.class, "distinct", false, "DISTINCT");
        public final static Property On = new Property(7, String.class, "on", false, "ON");
        public final static Property Index = new Property(8, String.class, "index", false, "INDEX");
        public final static Property Order = new Property(9, Integer.class, "order", false, "ORDER");
    };


    public SpecialNamesEntityDao(DaoConfig config) {
        super(config);
    }
    
    public SpecialNamesEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Connection connection, boolean ifNotExists) throws SQLException {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        JDBCUtils.execute( connection, "CREATE TABLE " + constraint + "'SPECIAL_NAMES_ENTITY' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'COUNT' TEXT," + // 1: count
                "'SELECT' TEXT," + // 2: select
                "'SUM' TEXT," + // 3: sum
                "'AVG' TEXT," + // 4: avg
                "'JOIN' TEXT," + // 5: join
                "'DISTINCT' TEXT," + // 6: distinct
                "'ON' TEXT," + // 7: on
                "'INDEX' TEXT," + // 8: index
                "'ORDER' INTEGER);"); // 9: order
    }

    /** Drops the underlying database table. */
    public static void dropTable(Connection connection, boolean ifExists) throws SQLException {
        JDBCUtils.execute( connection, "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'SPECIAL_NAMES_ENTITY'");
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(PreparedStatement statement, SpecialNamesEntity entity) throws SQLException {
 
        Long id = entity.getId();
        if (id != null) {
            statement.setLong(1, id);
        }
 
        String count = entity.getCount();
        if (count != null) {
            statement.setString(2, count);
        }
 
        String select = entity.getSelect();
        if (select != null) {
            statement.setString(3, select);
        }
 
        String sum = entity.getSum();
        if (sum != null) {
            statement.setString(4, sum);
        }
 
        String avg = entity.getAvg();
        if (avg != null) {
            statement.setString(5, avg);
        }
 
        String join = entity.getJoin();
        if (join != null) {
            statement.setString(6, join);
        }
 
        String distinct = entity.getDistinct();
        if (distinct != null) {
            statement.setString(7, distinct);
        }
 
        String on = entity.getOn();
        if (on != null) {
            statement.setString(8, on);
        }
 
        String index = entity.getIndex();
        if (index != null) {
            statement.setString(9, index);
        }
 
        Integer order = entity.getOrder();
        if (order != null) {
            statement.setLong(10, order);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(ResultSet resultSet, int offset) throws SQLException {
        return JDBCUtils.isNull(resultSet, offset + 1) ? null : resultSet.getLong(offset + 1);
    }    

    /** @inheritdoc */
    @Override
    public SpecialNamesEntity readEntity(ResultSet resultSet, int offset) throws SQLException {
		int index = 1;
        SpecialNamesEntity entity = new SpecialNamesEntity(
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++), // id
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++), // count
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++), // select
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++), // sum
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++), // avg
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++), // join
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++), // distinct
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++), // on
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++), // index
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getInt(offset + index++) // order
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(ResultSet resultSet, SpecialNamesEntity entity, int offset) throws SQLException {
		int index = 1;
        entity.setId(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++));
        entity.setCount(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++));
        entity.setSelect(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++));
        entity.setSum(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++));
        entity.setAvg(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++));
        entity.setJoin(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++));
        entity.setDistinct(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++));
        entity.setOn(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++));
        entity.setIndex(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++));
        entity.setOrder(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getInt(offset + index++));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(SpecialNamesEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(SpecialNamesEntity entity) {
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
