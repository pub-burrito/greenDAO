package de.greenrobot.daotest2.specialdao;

import java.util.List;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import de.greenrobot.dao.internal.JDBCUtils;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;

import de.greenrobot.daotest2.dao.DaoSession;

import de.greenrobot.daotest2.to1_specialentity.ToOneTarget2;

import de.greenrobot.daotest2.specialentity.RelationSource2;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table RELATION_SOURCE2.
*/
public class RelationSource2Dao extends AbstractDao<RelationSource2, Long> {

    public static final String TABLENAME = "RELATION_SOURCE2";

    /**
     * Properties of entity RelationSource2.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ToOneId = new Property(1, Long.class, "toOneId", false, "TO_ONE_ID");
    };

	private DaoSession daoSession;

    public RelationSource2Dao(DaoConfig config) {
        super(config);
    }
    
    public RelationSource2Dao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Connection connection, boolean ifNotExists) throws SQLException {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        JDBCUtils.execute( connection, "CREATE TABLE " + constraint + "'RELATION_SOURCE2' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'TO_ONE_ID' INTEGER);"); // 1: toOneId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Connection connection, boolean ifExists) throws SQLException {
        JDBCUtils.execute( connection, "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'RELATION_SOURCE2'");
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(PreparedStatement statement, RelationSource2 entity) throws SQLException {
 
        Long id = entity.getId();
        if (id != null) {
            statement.setLong(1, id);
        }
 
        Long toOneId = entity.getToOneId();
        if (toOneId != null) {
            statement.setLong(2, toOneId);
        }
    }

    @Override
    protected void attachEntity(RelationSource2 entity) {
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
    public RelationSource2 readEntity(ResultSet resultSet, int offset) throws SQLException {
		int index = 1;
        RelationSource2 entity = new RelationSource2(
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++), // id
            JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++) // toOneId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(ResultSet resultSet, RelationSource2 entity, int offset) throws SQLException {
		int index = 1;
        entity.setId(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++));
        entity.setToOneId(JDBCUtils.isNull(resultSet, offset + index) ? null : resultSet.getLong(offset + index++));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(RelationSource2 entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(RelationSource2 entity) {
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
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getToOneTarget2Dao().getAllColumns());
            builder.append(" FROM RELATION_SOURCE2 T");
            builder.append(" LEFT JOIN TO_ONE_TARGET2 T0 ON T.'TO_ONE_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected RelationSource2 loadCurrentDeep(ResultSet resultSet, boolean lock) throws SQLException {
        RelationSource2 entity = loadCurrent(resultSet, 0, lock);
        int offset = getAllColumns().length;

        ToOneTarget2 toOneTarget2 = loadCurrentOther(daoSession.getToOneTarget2Dao(), resultSet, offset);
        entity.setToOneTarget2(toOneTarget2);

        return entity;    
    }

    public RelationSource2 loadDeep(Long key) throws SQLException {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        ResultSet resultSet = JDBCUtils.query(connection, sql, (Object[]) keyArray);
        
        try {
            boolean available = resultSet.next();
            if (!available) {
                return null;
            } else if (!resultSet.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + JDBCUtils.getCount(resultSet));
            }
            return loadCurrentDeep(resultSet, true);
        } finally {
            resultSet.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<RelationSource2> loadAllDeepFromResultSet(ResultSet resultSet) throws SQLException {
        int count = JDBCUtils.getCount(resultSet);
        List<RelationSource2> list = new ArrayList<RelationSource2>(count);
        
        if (resultSet.first()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(resultSet, false));
                } while (resultSet.next());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<RelationSource2> loadDeepAllAndCloseResultSet(ResultSet resultSet) throws SQLException {
        try {
            return loadAllDeepFromResultSet(resultSet);
        } finally {
            resultSet.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<RelationSource2> queryDeep(String where, String... selectionArg) throws SQLException {
    
        ResultSet resultSet = JDBCUtils.query( connection, ( getSelectDeep() + where ), (Object[]) selectionArg );
        return loadDeepAllAndCloseResultSet(resultSet);
    }
 
}
