package de.greenrobot.daotest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.JDBCUtils;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table ABCDEF_ENTITY.
*/
public class AbcdefEntityDao extends AbstractDao<AbcdefEntity, Long> {

    public static final String TABLENAME = "ABCDEF_ENTITY";

    /**
     * Properties of entity AbcdefEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property A = new Property(1, Integer.class, "a", false, "A");
        public final static Property B = new Property(2, Integer.class, "b", false, "B");
        public final static Property C = new Property(3, Integer.class, "c", false, "C");
        public final static Property D = new Property(4, Integer.class, "d", false, "D");
        public final static Property E = new Property(5, Integer.class, "e", false, "E");
        public final static Property F = new Property(6, Integer.class, "f", false, "F");
        public final static Property G = new Property(7, Integer.class, "g", false, "G");
        public final static Property H = new Property(8, Integer.class, "h", false, "H");
        public final static Property J = new Property(9, Integer.class, "j", false, "J");
        public final static Property I = new Property(10, Integer.class, "i", false, "I");
        public final static Property K = new Property(11, Integer.class, "k", false, "K");
    };


    public AbcdefEntityDao(DaoConfig config) {
        super(config);
    }
    
    public AbcdefEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. 
     * @throws SQLException */
    public static void createTable(Connection connection, boolean ifNotExists) throws SQLException {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        JDBCUtils.execute( connection, "CREATE TABLE " + constraint + "'ABCDEF_ENTITY' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'A' INTEGER," + // 1: a
                "'B' INTEGER," + // 2: b
                "'C' INTEGER," + // 3: c
                "'D' INTEGER," + // 4: d
                "'E' INTEGER," + // 5: e
                "'F' INTEGER," + // 6: f
                "'G' INTEGER," + // 7: g
                "'H' INTEGER," + // 8: h
                "'J' INTEGER," + // 9: j
                "'I' INTEGER," + // 10: i
                "'K' INTEGER);"); // 11: k
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ABCDEF_ENTITY'";
        db.execSQL(sql);
    }

    /** @throws SQLException 
     * @inheritdoc */
    @Override
    protected void bindValues(PreparedStatement stmt, AbcdefEntity entity) throws SQLException {
//        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.setLong(1, id);
        }
 
        Integer a = entity.getA();
        if (a != null) {
            stmt.setLong(2, a);
        }
 
        Integer b = entity.getB();
        if (b != null) {
            stmt.setLong(3, b);
        }
 
        Integer c = entity.getC();
        if (c != null) {
            stmt.setLong(4, c);
        }
 
        Integer d = entity.getD();
        if (d != null) {
            stmt.setLong(5, d);
        }
 
        Integer e = entity.getE();
        if (e != null) {
            stmt.setLong(6, e);
        }
 
        Integer f = entity.getF();
        if (f != null) {
            stmt.setLong(7, f);
        }
 
        Integer g = entity.getG();
        if (g != null) {
            stmt.setLong(8, g);
        }
 
        Integer h = entity.getH();
        if (h != null) {
            stmt.setLong(9, h);
        }
 
        Integer j = entity.getJ();
        if (j != null) {
            stmt.setLong(10, j);
        }
 
        Integer i = entity.getI();
        if (i != null) {
            stmt.setLong(11, i);
        }
 
        Integer k = entity.getK();
        if (k != null) {
            stmt.setLong(12, k);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(ResultSet resultSet, int offset) throws SQLException {
        return resultSet.getObject(offset + 0) == null ? null : resultSet.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public AbcdefEntity readEntity(ResultSet resultSet, int offset) throws SQLException {
        AbcdefEntity entity = new AbcdefEntity( //
            resultSet.getObject(offset + 0) == null ? null : resultSet.getLong(offset + 0), // id
            resultSet.getObject(offset + 1) == null ? null : resultSet.getInt(offset + 1), // a
            resultSet.getObject(offset + 2) == null ? null : resultSet.getInt(offset + 2), // b
            resultSet.getObject(offset + 3) == null ? null : resultSet.getInt(offset + 3), // c
            resultSet.getObject(offset + 4) == null ? null : resultSet.getInt(offset + 4), // d
            resultSet.getObject(offset + 5) == null ? null : resultSet.getInt(offset + 5), // e
            resultSet.getObject(offset + 6) == null ? null : resultSet.getInt(offset + 6), // f
            resultSet.getObject(offset + 7) == null ? null : resultSet.getInt(offset + 7), // g
            resultSet.getObject(offset + 8) == null ? null : resultSet.getInt(offset + 8), // h
            resultSet.getObject(offset + 9) == null ? null : resultSet.getInt(offset + 9), // j
            resultSet.getObject(offset + 10) == null ? null : resultSet.getInt(offset + 10), // i
            resultSet.getObject(offset + 11) == null ? null : resultSet.getInt(offset + 11) // k
        );
        return entity;
    }
     
    /** @throws SQLException 
     * @inheritdoc */
    @Override
    public void readEntity(ResultSet resultSet, AbcdefEntity entity, int offset) throws SQLException {
        entity.setId(resultSet.getObject(offset + 0) == null ? null : resultSet.getLong(offset + 0));
        entity.setA(resultSet.getObject(offset + 1) == null ? null : resultSet.getInt(offset + 1));
        entity.setB(resultSet.getObject(offset + 2) == null ? null : resultSet.getInt(offset + 2));
        entity.setC(resultSet.getObject(offset + 3) == null ? null : resultSet.getInt(offset + 3));
        entity.setD(resultSet.getObject(offset + 4) == null ? null : resultSet.getInt(offset + 4));
        entity.setE(resultSet.getObject(offset + 5) == null ? null : resultSet.getInt(offset + 5));
        entity.setF(resultSet.getObject(offset + 6) == null ? null : resultSet.getInt(offset + 6));
        entity.setG(resultSet.getObject(offset + 7) == null ? null : resultSet.getInt(offset + 7));
        entity.setH(resultSet.getObject(offset + 8) == null ? null : resultSet.getInt(offset + 8));
        entity.setJ(resultSet.getObject(offset + 9) == null ? null : resultSet.getInt(offset + 9));
        entity.setI(resultSet.getObject(offset + 10) == null ? null : resultSet.getInt(offset + 10));
        entity.setK(resultSet.getObject(offset + 11) == null ? null : resultSet.getInt(offset + 11));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(AbcdefEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(AbcdefEntity entity) {
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
