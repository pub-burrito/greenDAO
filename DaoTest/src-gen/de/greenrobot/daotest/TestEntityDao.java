package de.greenrobot.daotest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import de.greenrobot.platform.java.util.JDBCUtils;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import de.greenrobot.daotest.TestEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table TEST_ENTITY.
*/
public class TestEntityDao extends AbstractDao<TestEntity, Long> {

    public static final String TABLENAME = "TEST_ENTITY";

    /**
     * Properties of entity TestEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property SimpleInt = new Property(1, int.class, "simpleInt", false, "SIMPLE_INT");
        public final static Property SimpleInteger = new Property(2, Integer.class, "simpleInteger", false, "SIMPLE_INTEGER");
        public final static Property SimpleStringNotNull = new Property(3, String.class, "simpleStringNotNull", false, "SIMPLE_STRING_NOT_NULL");
        public final static Property SimpleString = new Property(4, String.class, "simpleString", false, "SIMPLE_STRING");
        public final static Property IndexedString = new Property(5, String.class, "indexedString", false, "INDEXED_STRING");
        public final static Property IndexedStringAscUnique = new Property(6, String.class, "indexedStringAscUnique", false, "INDEXED_STRING_ASC_UNIQUE");
        public final static Property SimpleDate = new Property(7, java.util.Date.class, "simpleDate", false, "SIMPLE_DATE");
        public final static Property SimpleBoolean = new Property(8, Boolean.class, "simpleBoolean", false, "SIMPLE_BOOLEAN");
    };


    public TestEntityDao(DaoConfig config) {
        super(config);
    }
    
    public TestEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Connection connection, boolean ifNotExists) throws SQLException {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        JDBCUtils.execute( connection, "CREATE TABLE " + constraint + "'TEST_ENTITY' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'SIMPLE_INT' INTEGER NOT NULL ," + // 1: simpleInt
                "'SIMPLE_INTEGER' INTEGER," + // 2: simpleInteger
                "'SIMPLE_STRING_NOT_NULL' TEXT NOT NULL ," + // 3: simpleStringNotNull
                "'SIMPLE_STRING' TEXT," + // 4: simpleString
                "'INDEXED_STRING' TEXT," + // 5: indexedString
                "'INDEXED_STRING_ASC_UNIQUE' TEXT," + // 6: indexedStringAscUnique
                "'SIMPLE_DATE' INTEGER," + // 7: simpleDate
                "'SIMPLE_BOOLEAN' INTEGER);"); // 8: simpleBoolean
        // Add Indexes
        JDBCUtils.execute( connection, "CREATE INDEX " + constraint + "IDX_TEST_ENTITY_INDEXED_STRING ON TEST_ENTITY" +
                " (INDEXED_STRING);");
        JDBCUtils.execute( connection, "CREATE UNIQUE INDEX " + constraint + "IDX_TEST_ENTITY_INDEXED_STRING_ASC_UNIQUE ON TEST_ENTITY" +
                " (INDEXED_STRING_ASC_UNIQUE);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Connection connection, boolean ifExists) throws SQLException {
        JDBCUtils.execute( connection, "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TEST_ENTITY'");
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(PreparedStatement statement, TestEntity entity) throws SQLException {
 
        Long id = entity.getId();
        if (id != null) {
            statement.setLong(1, id);
        }
        statement.setLong(2, entity.getSimpleInt());
 
        Integer simpleInteger = entity.getSimpleInteger();
        if (simpleInteger != null) {
            statement.setLong(3, simpleInteger);
        }
        statement.setString(4, entity.getSimpleStringNotNull());
 
        String simpleString = entity.getSimpleString();
        if (simpleString != null) {
            statement.setString(5, simpleString);
        }
 
        String indexedString = entity.getIndexedString();
        if (indexedString != null) {
            statement.setString(6, indexedString);
        }
 
        String indexedStringAscUnique = entity.getIndexedStringAscUnique();
        if (indexedStringAscUnique != null) {
            statement.setString(7, indexedStringAscUnique);
        }
 
        java.util.Date simpleDate = entity.getSimpleDate();
        if (simpleDate != null) {
            statement.setLong(8, simpleDate.getTime());
        }
 
        Boolean simpleBoolean = entity.getSimpleBoolean();
        if (simpleBoolean != null) {
            statement.setLong(9, simpleBoolean ? 1l: 0l);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(ResultSet resultSet, int offset) throws SQLException {
    	int index = 1;
        return JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++);
    }    

    /** @inheritdoc */
    @Override
    public TestEntity readEntity(ResultSet resultSet, int offset) throws SQLException {
		int index = 1;
        TestEntity entity = new TestEntity(
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++), // id
            resultSet.getInt(offset + index++), // simpleInt
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getInt(offset + index++), // simpleInteger
            resultSet.getString(offset + index++), // simpleStringNotNull
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++), // simpleString
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++), // indexedString
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++), // indexedStringAscUnique
            JDBCUtils.isNull(resultSet, offset + index) ? null : new java.util.Date(resultSet.getLong(offset + index++)), // simpleDate
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getShort(offset + index++) != 0 // simpleBoolean
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(ResultSet resultSet, TestEntity entity, int offset) throws SQLException {
		int index = 1;
        entity.setId(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++));
        entity.setSimpleInt(resultSet.getInt(offset + index++));
        entity.setSimpleInteger(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getInt(offset + index++));
        entity.setSimpleStringNotNull(resultSet.getString(offset + index++));
        entity.setSimpleString(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++));
        entity.setIndexedString(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++));
        entity.setIndexedStringAscUnique(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++));
        entity.setSimpleDate(JDBCUtils.isNull(resultSet, offset + index) ? null : new java.util.Date(resultSet.getLong(offset + index++)));
        entity.setSimpleBoolean(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getShort(offset + index++) != 0);
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TestEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(TestEntity entity) {
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
