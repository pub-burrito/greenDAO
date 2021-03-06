package de.greenrobot.daotest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import de.greenrobot.dao.internal.JDBCUtils;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import de.greenrobot.daotest.SimpleEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table SIMPLE_ENTITY.
*/
public class SimpleEntityDao extends AbstractDao<SimpleEntity, Long> {

    public static final String TABLENAME = "SIMPLE_ENTITY";

    /**
     * Properties of entity SimpleEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property SimpleBoolean = new Property(1, Boolean.class, "simpleBoolean", false, "SIMPLE_BOOLEAN");
        public final static Property SimpleByte = new Property(2, Byte.class, "simpleByte", false, "SIMPLE_BYTE");
        public final static Property SimpleShort = new Property(3, Short.class, "simpleShort", false, "SIMPLE_SHORT");
        public final static Property SimpleInt = new Property(4, Integer.class, "simpleInt", false, "SIMPLE_INT");
        public final static Property SimpleLong = new Property(5, Long.class, "simpleLong", false, "SIMPLE_LONG");
        public final static Property SimpleFloat = new Property(6, Float.class, "simpleFloat", false, "SIMPLE_FLOAT");
        public final static Property SimpleDouble = new Property(7, Double.class, "simpleDouble", false, "SIMPLE_DOUBLE");
        public final static Property SimpleString = new Property(8, String.class, "simpleString", false, "SIMPLE_STRING");
        public final static Property SimpleByteArray = new Property(9, byte[].class, "simpleByteArray", false, "SIMPLE_BYTE_ARRAY");
    };


    public SimpleEntityDao(DaoConfig config) {
        super(config);
    }
    
    public SimpleEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Connection connection, boolean ifNotExists) throws SQLException {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        JDBCUtils.execute( connection, "CREATE TABLE " + constraint + "'SIMPLE_ENTITY' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'SIMPLE_BOOLEAN' INTEGER," + // 1: simpleBoolean
                "'SIMPLE_BYTE' INTEGER," + // 2: simpleByte
                "'SIMPLE_SHORT' INTEGER," + // 3: simpleShort
                "'SIMPLE_INT' INTEGER," + // 4: simpleInt
                "'SIMPLE_LONG' INTEGER," + // 5: simpleLong
                "'SIMPLE_FLOAT' REAL," + // 6: simpleFloat
                "'SIMPLE_DOUBLE' REAL," + // 7: simpleDouble
                "'SIMPLE_STRING' TEXT," + // 8: simpleString
                "'SIMPLE_BYTE_ARRAY' BLOB);"); // 9: simpleByteArray
    }

    /** Drops the underlying database table. */
    public static void dropTable(Connection connection, boolean ifExists) throws SQLException {
        JDBCUtils.execute( connection, "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'SIMPLE_ENTITY'");
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(PreparedStatement statement, SimpleEntity entity) throws SQLException {
 
        Long id = entity.getId();
        if (id != null) {
            statement.setLong(1, id);
        }
 
        Boolean simpleBoolean = entity.getSimpleBoolean();
        if (simpleBoolean != null) {
            statement.setLong(2, simpleBoolean ? 1l: 0l);
        }
 
        Byte simpleByte = entity.getSimpleByte();
        if (simpleByte != null) {
            statement.setLong(3, simpleByte);
        }
 
        Short simpleShort = entity.getSimpleShort();
        if (simpleShort != null) {
            statement.setLong(4, simpleShort);
        }
 
        Integer simpleInt = entity.getSimpleInt();
        if (simpleInt != null) {
            statement.setLong(5, simpleInt);
        }
 
        Long simpleLong = entity.getSimpleLong();
        if (simpleLong != null) {
            statement.setLong(6, simpleLong);
        }
 
        Float simpleFloat = entity.getSimpleFloat();
        if (simpleFloat != null) {
            statement.setDouble(7, simpleFloat);
        }
 
        Double simpleDouble = entity.getSimpleDouble();
        if (simpleDouble != null) {
            statement.setDouble(8, simpleDouble);
        }
 
        String simpleString = entity.getSimpleString();
        if (simpleString != null) {
            statement.setString(9, simpleString);
        }
 
        byte[] simpleByteArray = entity.getSimpleByteArray();
        if (simpleByteArray != null) {
            statement.setBytes(10, simpleByteArray);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(ResultSet resultSet, int offset) throws SQLException {
        return JDBCUtils.isNull(resultSet, offset + 1) ? null : resultSet.getLong(offset + 1);
    }    

    /** @inheritdoc */
    @Override
    public SimpleEntity readEntity(ResultSet resultSet, int offset) throws SQLException {
		int index = 1;
        SimpleEntity entity = new SimpleEntity(
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++), // id
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getShort(offset + index++) != 0, // simpleBoolean
            JDBCUtils.isNull(resultSet, offset + index) ? null : (byte) resultSet.getShort(offset + index++), // simpleByte
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getShort(offset + index++), // simpleShort
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getInt(offset + index++), // simpleInt
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++), // simpleLong
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getFloat(offset + index++), // simpleFloat
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getDouble(offset + index++), // simpleDouble
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++), // simpleString
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getBytes(offset + index++) // simpleByteArray
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(ResultSet resultSet, SimpleEntity entity, int offset) throws SQLException {
		int index = 1;
        entity.setId(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++));
        entity.setSimpleBoolean(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getShort(offset + index++) != 0);
        entity.setSimpleByte(JDBCUtils.isNull(resultSet, offset + index) ? null : (byte) resultSet.getShort(offset + index++));
        entity.setSimpleShort(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getShort(offset + index++));
        entity.setSimpleInt(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getInt(offset + index++));
        entity.setSimpleLong(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++));
        entity.setSimpleFloat(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getFloat(offset + index++));
        entity.setSimpleDouble(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getDouble(offset + index++));
        entity.setSimpleString(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getString(offset + index++));
        entity.setSimpleByteArray(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getBytes(offset + index++));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(SimpleEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(SimpleEntity entity) {
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
