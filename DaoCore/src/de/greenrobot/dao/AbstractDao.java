/*
 * Copyright (C) 2011-2013 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.greenrobot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import de.greenrobot.dao.identityscope.IdentityScope;
import de.greenrobot.dao.identityscope.IdentityScopeLong;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.internal.JDBCUtils;
import de.greenrobot.dao.internal.TableStatements;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Base class for all DAOs: Implements entity operations like insert, load, delete, and query.
 * 
 * This class is thread-safe.
 * 
 * @author Markus
 * 
 * @param <T>
 *            Entity type
 * @param <K>
 *            Primary key (PK) type; use Void if entity does not have exactly one PK
 */
/*
 * When operating on TX, statements, or identity scope the following locking order must be met to avoid deadlocks:
 * 
 * 1.) If not inside a TX already, begin a TX to acquire a DB connection (connection is to be handled like a lock)
 * 
 * 2.) The SQLiteStatement
 * 
 * 3.) identityScope
 */
//FIXME: Find all cases of non-inTX methods that call connection.setAutoCommit( false ); and make it conditional (or hide details inside the driver), ie: setting auto commit to false on a connection that already has it to false just creates a nested transaction that when committed doesn't do anything and lets the parent transaction handle it)
public abstract class AbstractDao<T, K> {
    protected final Connection connection;
    protected final DaoConfig config;
    protected IdentityScope<K, T> identityScope;
    protected IdentityScopeLong<T> identityScopeLong;
    protected TableStatements statements;

    protected final AbstractDaoSession session;
    protected final int pkOrdinal;

    public AbstractDao(DaoConfig config) {
        this(config, null);
    }

    @SuppressWarnings("unchecked")
    public AbstractDao(DaoConfig config, AbstractDaoSession daoSession) {
        this.config = config;
        this.session = daoSession;
        connection = config.connection;
        identityScope = (IdentityScope<K, T>) config.getIdentityScope();
        if (identityScope instanceof IdentityScopeLong) {
            identityScopeLong = (IdentityScopeLong<T>) identityScope;
        }
        statements = config.statements;
        pkOrdinal = config.pkProperty != null ? config.pkProperty.ordinal : -1;
    }

    public AbstractDaoSession getSession() {
        return session;
    }

    TableStatements getStatements() {
        return config.statements;
    }

    public String getTablename() {
        return config.tablename;
    }

    public Property[] getProperties() {
        return config.properties;
    }

    public Property getPkProperty() {
        return config.pkProperty;
    }

    public String[] getAllColumns() {
        return config.allColumns;
    }

    public String[] getPkColumns() {
        return config.pkColumns;
    }

    public String[] getNonPkColumns() {
        return config.nonPkColumns;
    }

    /**
     * Loads and entity for the given PK.
     * 
     * @param key
     *            a PK value or null
     * @return The entity or null, if no entity matched the PK value
     * @throws SQLException 
     */
    public T load(K key) throws SQLException {
        assertSinglePk();
        if (key == null) {
            return null;
        }
        if (identityScope != null) {
            T entity = identityScope.get(key);
            if (entity != null) {
                return entity;
            }
        }
        String sql = statements.getSelectByKey();
        PreparedStatement statement = connection.prepareStatement( sql );
        statement.setString( 1, key.toString() );
        ResultSet resultSet = statement.executeQuery();
        return loadUniqueAndCloseCursor(resultSet);
    }

    public T loadByRowId(long rowId) throws SQLException {
        String sql = statements.getSelectByRowId();
        PreparedStatement statement = connection.prepareStatement( sql );
        statement.setString( 1, Long.toString(rowId) );
        ResultSet resultSet = statement.executeQuery();
        return loadUniqueAndCloseCursor(resultSet);
    }

    protected T loadUniqueAndCloseCursor(ResultSet resultSet) throws SQLException {
        try {
            return loadUnique(resultSet);
        } finally {
            resultSet.close();
        }
    }

    protected T loadUnique(ResultSet resultSet) throws SQLException {
        boolean available = resultSet.next();
        if (!available) {
            return null;
        } else if (!resultSet.isLast()) {
            throw new DaoException("Expected unique result, but count was " + resultSet.getFetchSize());
        }
        return loadCurrent(resultSet, 0, true);
    }

    /** Loads all available entities from the database. 
     * @throws SQLException */
    public List<T> loadAll() throws SQLException {
        String sql = statements.getSelectAll();
        PreparedStatement statement = connection.prepareStatement( sql );
        ResultSet resultSet = statement.executeQuery();
        return loadAllAndCloseCursor(resultSet);
    }

    /** Detaches an entity from the identity scope (session). Subsequent query results won't return this object. 
     * @throws DaoException */
    public boolean detach(T entity) throws DaoException {
        if (identityScope != null) {
            K key = getKeyVerified(entity);
            return identityScope.detach(key, entity);
        } else {
            return false;
        }
    }

    protected List<T> loadAllAndCloseCursor(ResultSet resultSet) throws SQLException {
        try {
            return loadAllFromCursor(resultSet);
        } finally {
            resultSet.close();
        }
    }

    /**
     * Inserts the given entities in the database using a transaction.
     * 
     * @param entities
     *            The entities to insert.
     * @throws SQLException 
     */
    public void insertInTx(Iterable<T> entities) throws SQLException {
        insertInTx(entities, isEntityUpdateable());
    }

    /**
     * Inserts the given entities in the database using a transaction.
     * 
     * @param entities
     *            The entities to insert.
     * @throws SQLException 
     */
    public void insertInTx(T... entities) throws SQLException {
        insertInTx(Arrays.asList(entities), isEntityUpdateable());
    }

    /**
     * Inserts the given entities in the database using a transaction. The given entities will become tracked if the PK
     * is set.
     * 
     * @param entities
     *            The entities to insert.
     * @param setPrimaryKey
     *            if true, the PKs of the given will be set after the insert; pass false to improve performance.
     * @throws SQLException 
     */
    public void insertInTx(Iterable<T> entities, boolean setPrimaryKey) throws SQLException {
        PreparedStatement stmt = statements.getInsertStatement();
        executeInsertInTx(stmt, entities, setPrimaryKey);
    }

    /**
     * Inserts or replaces the given entities in the database using a transaction. The given entities will become
     * tracked if the PK is set.
     * 
     * @param entities
     *            The entities to insert.
     * @param setPrimaryKey
     *            if true, the PKs of the given will be set after the insert; pass false to improve performance.
     * @throws SQLException 
     */
    public void insertOrReplaceInTx(Iterable<T> entities, boolean setPrimaryKey) throws SQLException {
    	PreparedStatement stmt = statements.getInsertOrReplaceStatement();
        executeInsertInTx(stmt, entities, setPrimaryKey);
    }

    /**
     * Inserts or replaces the given entities in the database using a transaction.
     * 
     * @param entities
     *            The entities to insert.
     * @throws SQLException 
     */
    public void insertOrReplaceInTx(Iterable<T> entities) throws SQLException {
        insertOrReplaceInTx(entities, isEntityUpdateable());
    }

    /**
     * Inserts or replaces the given entities in the database using a transaction.
     * 
     * @param entities
     *            The entities to insert.
     * @throws SQLException 
     */
    public void insertOrReplaceInTx(T... entities) throws SQLException {
        insertOrReplaceInTx(Arrays.asList(entities), isEntityUpdateable());
    }

    private void executeInsertInTx(PreparedStatement stmt, Iterable<T> entities, boolean setPrimaryKey) throws SQLException {
    	connection.setAutoCommit( false );
        try {
            synchronized (stmt) {
                if (identityScope != null) {
                    identityScope.lock();
                }
                try {
                    for (T entity : entities) {
                        bindValues(stmt, entity);
                        if (setPrimaryKey) {
                            long rowId = stmt.executeUpdate();
                            updateKeyAfterInsertAndAttach(entity, rowId, false);
                        } else {
                            stmt.execute();
                        }
                    }
                }
				catch ( SQLException e )
				{
					connection.rollback();
					e.printStackTrace();
				} finally {
                    if (identityScope != null) {
                        identityScope.unlock();
                    }
                }
            }
            connection.commit();
        } finally {
        	connection.setAutoCommit( true );
        }
    }

    /**
     * Insert an entity into the table associated with a concrete DAO.
     * 
     * @return row ID of newly inserted entity
     * @throws SQLException 
     */
    public long insert(T entity) throws SQLException {
        return executeInsert(entity, statements.getInsertStatement());
    }

    /**
     * Insert an entity into the table associated with a concrete DAO <b>without</b> setting key property. Warning: This
     * may be faster, but the entity should not be used anymore. The entity also won't be attached to identy scope.
     * 
     * @return row ID of newly inserted entity
     * @throws SQLException 
     */
    public long insertWithoutSettingPk(T entity) throws SQLException {
    	PreparedStatement stmt = statements.getInsertStatement();
        long rowId = 0;
        // Do TX to acquire a connection before locking the stmt to avoid deadlocks
        connection.setAutoCommit( false );
        try {
            synchronized (stmt) {
                bindValues(stmt, entity);
                rowId = stmt.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
        	connection.rollback();
        } finally {
        	connection.setAutoCommit( true );
        }
        return rowId;
    }

    /**
     * Insert an entity into the table associated with a concrete DAO.
     * 
     * @return row ID of newly inserted entity
     * @throws SQLException 
     */
    public long insertOrReplace(T entity) throws SQLException {
        return executeInsert(entity, statements.getInsertOrReplaceStatement());
    }

    private long executeInsert(T entity, PreparedStatement stmt) throws SQLException {
        long rowId = 0;
        // Do TX to acquire a connection before locking the stmt to avoid deadlocks
        connection.setAutoCommit( false );
        try {
            synchronized (stmt) {
                bindValues(stmt, entity);
                rowId = stmt.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
        	connection.rollback();
        	e.printStackTrace();
        } finally {
        	connection.setAutoCommit( true );
        }
        updateKeyAfterInsertAndAttach(entity, rowId, true);
        return rowId;
    }

    protected void updateKeyAfterInsertAndAttach(T entity, long rowId, boolean lock) {
        if (rowId != -1) {
            K key = updateKeyAfterInsert(entity, rowId);
            attachEntity(key, entity, lock);
        } else {
            // TODO When does this actually happen? Should we throw instead?
            DaoLog.w("Could not insert row (executeInsert returned -1)");
        }
    }

    /** Reads all available rows from the given cursor and returns a list of entities. 
     * @throws SQLException */
    protected List<T> loadAllFromCursor(ResultSet resultSet) throws SQLException {
        int count = JDBCUtils.getCount(resultSet);
        List<T> list = new ArrayList<T>(count);
        
        if (resultSet.next()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrent(resultSet, 0, false));
                } while (resultSet.next());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }

    /** Internal use only. Considers identity scope. 
     * @throws SQLException */
    final protected T loadCurrent(ResultSet resultSet, int offset, boolean lock) throws SQLException {
        if (identityScopeLong != null) {
            int index = pkOrdinal + offset;
            if (index <= 0) index = 1; // first position
			if (offset != 0) {
                // Occurs with deep loads (left outer joins)
                if (resultSet.getObject(index) == null) {
                    return null;
                }
            }

            long key = resultSet.getLong(index);
            T entity = lock ? identityScopeLong.get2(key) : identityScopeLong.get2NoLock(key);
            if (entity != null) {
                return entity;
            } else {
                entity = readEntity(resultSet, offset);
                attachEntity(entity);
                if (lock) {
                    identityScopeLong.put2(key, entity);
                } else {
                    identityScopeLong.put2NoLock(key, entity);
                }
                return entity;
            }
        } else if (identityScope != null) {
            K key = readKey(resultSet, offset);
            if (offset != 0 && key == null) {
                // Occurs with deep loads (left outer joins)
                return null;
            }
            T entity = lock ? identityScope.get(key) : identityScope.getNoLock(key);
            if (entity != null) {
                return entity;
            } else {
                entity = readEntity(resultSet, offset);
                attachEntity(key, entity, lock);
                return entity;
            }
        } else {
            // Check offset, assume a value !=0 indicating a potential outer join, so check PK
            if (offset != 0) {
                K key = readKey(resultSet, offset);
                if (key == null) {
                    // Occurs with deep loads (left outer joins)
                    return null;
                }
            }
            T entity = readEntity(resultSet, offset);
            attachEntity(entity);
            return entity;
        }
    }

    /** Internal use only. Considers identity scope. 
     * @throws SQLException */
    final protected <O> O loadCurrentOther(AbstractDao<O, ?> dao, ResultSet resultSet, int offset) throws SQLException {
        return dao.loadCurrent(resultSet, offset, /* TODO check this */true);
    }

    /** A raw-style query where you can pass any WHERE clause and arguments. 
     * @throws SQLException */
    public List<T> queryRaw(String where, String... selectionArg) throws SQLException {
		String sql = statements.getSelectAll() + where;
		PreparedStatement statement = connection.prepareStatement( sql );
		for ( int i = 0; i < selectionArg.length; i++ )
		{
			int index = i+1;
			statement.setString( index, selectionArg[i] );
		}
		ResultSet resultSet = statement.executeQuery();
		return loadAllAndCloseCursor(resultSet);
    }

    /**
     * Creates a repeatable {@link Query} object based on the given raw SQL where you can pass any WHERE clause and
     * arguments.
     * @throws DaoException 
     */
    public Query<T> queryRawCreate(String where, Object... selectionArg) throws DaoException {
        List<Object> argList = Arrays.asList(selectionArg);
        return queryRawCreateListArgs(where, argList);
    }

    /**
     * Creates a repeatable {@link Query} object based on the given raw SQL where you can pass any WHERE clause and
     * arguments.
     * @throws DaoException 
     */
    public Query<T> queryRawCreateListArgs(String where, Collection<Object> selectionArg) throws DaoException {
        return Query.internalCreate(this, statements.getSelectAll() + where, selectionArg.toArray());
    }

    public void deleteAll() throws SQLException {
        // String sql = SqlUtils.createSqlDelete(config.tablename, null);
        // db.execSQL(sql);

    	connection.createStatement().execute( "DELETE FROM '" + config.tablename + "'" );
        if (identityScope != null) {
            identityScope.clear();
        }
    }

    /** Deletes the given entity from the database. Currently, only single value PK entities are supported. 
     * @throws SQLException */
    public void delete(T entity) throws SQLException {
        assertSinglePk();
        K key = getKeyVerified(entity);
        deleteByKey(key);
    }

    /** Deletes an entity with the given PK from the database. Currently, only single value PK entities are supported. 
     * @throws SQLException */
    public void deleteByKey(K key) throws SQLException {
        assertSinglePk();
        PreparedStatement stmt = statements.getDeleteStatement();
        // Do TX to acquire a connection before locking the stmt to avoid deadlocks
        connection.setAutoCommit( false );
        try {
            synchronized (stmt) {
                deleteByKeyInsideSynchronized(key, stmt);
            }
            connection.commit();
        } catch (SQLException e) {
        	connection.rollback();
        	e.printStackTrace();
        } finally {
        	connection.setAutoCommit( true );
        }
        if (identityScope != null) {
            identityScope.remove(key);
        }
    }

    private void deleteByKeyInsideSynchronized(K key, PreparedStatement stmt) throws SQLException {
        if (key instanceof Long) {
            stmt.setLong(1, (Long) key);
        } else if (key == null) {
            throw new DaoException("Cannot delete entity, key is null");
        } else {
            stmt.setString(1, key.toString());
        }
        stmt.execute();
    }

    private void deleteInTxInternal(Iterable<T> entities, Iterable<K> keys) throws SQLException {
        assertSinglePk();
        PreparedStatement stmt = statements.getDeleteStatement();
        List<K> keysToRemoveFromIdentityScope = null;
        connection.setAutoCommit( false );
        try {
            synchronized (stmt) {
                if (identityScope != null) {
                    identityScope.lock();
                    keysToRemoveFromIdentityScope = new ArrayList<K>();
                }
                try {
                    if (entities != null) {
                        for (T entity : entities) {
                            K key = getKeyVerified(entity);
                            deleteByKeyInsideSynchronized(key, stmt);
                            if (keysToRemoveFromIdentityScope != null) {
                                keysToRemoveFromIdentityScope.add(key);
                            }
                        }
                    }
                    if (keys != null) {
                        for (K key : keys) {
                            deleteByKeyInsideSynchronized(key, stmt);
                            if (keysToRemoveFromIdentityScope != null) {
                                keysToRemoveFromIdentityScope.add(key);
                            }
                        }
                    }
                } finally {
                    if (identityScope != null) {
                        identityScope.unlock();
                    }
                }
            }
            connection.commit();
            if (keysToRemoveFromIdentityScope != null && identityScope != null) {
                identityScope.remove(keysToRemoveFromIdentityScope);
            }
        } catch (SQLException e) {
        	connection.rollback();
        	e.printStackTrace();
        } finally {
        	connection.setAutoCommit( true );
        }
    }

    /**
     * Deletes the given entities in the database using a transaction.
     * 
     * @param entities
     *            The entities to delete.
     * @throws SQLException 
     */
    public void deleteInTx(Iterable<T> entities) throws SQLException {
        deleteInTxInternal(entities, null);
    }

    /**
     * Deletes the given entities in the database using a transaction.
     * 
     * @param entities
     *            The entities to delete.
     * @throws SQLException 
     */
    public void deleteInTx(T... entities) throws SQLException {
        deleteInTxInternal(Arrays.asList(entities), null);
    }

    /**
     * Deletes all entities with the given keys in the database using a transaction.
     * 
     * @param keys
     *            Keys of the entities to delete.
     * @throws SQLException 
     */
    public void deleteByKeyInTx(Iterable<K> keys) throws SQLException {
        deleteInTxInternal(null, keys);
    }

    /**
     * Deletes all entities with the given keys in the database using a transaction.
     * 
     * @param keys
     *            Keys of the entities to delete.
     * @throws SQLException 
     */
    public void deleteByKeyInTx(K... keys) throws SQLException {
        deleteInTxInternal(null, Arrays.asList(keys));
    }

    /** Resets all locally changed properties of the entity by reloading the values from the database. 
     * @throws SQLException */
    public void refresh(T entity) throws SQLException {
        assertSinglePk();
        K key = getKeyVerified(entity);
        String sql = statements.getSelectByKey();
        PreparedStatement statement = connection.prepareStatement( sql );
        statement.setString( 1, key.toString() );
        ResultSet resultSet = statement.executeQuery();
        try {
            boolean available = resultSet.next();
            if (!available) {
                throw new DaoException("Entity does not exist in the database anymore: " + entity.getClass()
                        + " with key " + key);
            } else if (!resultSet.isLast()) {
                throw new DaoException("Expected unique result, but count was " + resultSet.getFetchSize());
            }
            readEntity(resultSet, entity, 0);
            attachEntity(key, entity, true);
        } finally {
            resultSet.close();
        }
    }

    public void update(T entity) throws SQLException {
        assertSinglePk();
        PreparedStatement stmt = statements.getUpdateStatement();
        // Do TX to acquire a connection before locking the stmt to avoid deadlocks
        connection.setAutoCommit( false );
        try {
            synchronized (stmt) {
                updateInsideSynchronized(entity, stmt, true);
            }
            connection.commit();
        } catch (SQLException e){
        	connection.rollback();
        	e.printStackTrace();
        } finally {
        	connection.setAutoCommit( true );
        }
    }

    public QueryBuilder<T> queryBuilder() {
        return QueryBuilder.internalCreate(this);
    }

    protected void updateInsideSynchronized(T entity, PreparedStatement stmt, boolean lock) throws SQLException {
        // To do? Check if it's worth not to bind PKs here (performance).
        bindValues(stmt, entity);
        int index = config.allColumns.length + 1;
        K key = getKey(entity);
        if (key instanceof Long) {
            stmt.setLong(index, (Long) key);
        } else if (key == null) {
            throw new DaoException("Cannot update entity without key - was it inserted before?");
        } else {
            stmt.setString(index, key.toString());
        }
        stmt.execute();
        attachEntity(key, entity, lock);
    }

    /**
     * Attaches the entity to the identity scope. Calls attachEntity(T entity).
     * 
     * @param key
     *            Needed only for identity scope, pass null if there's none.
     * @param entity
     *            The entitiy to attach
     * */
    protected final void attachEntity(K key, T entity, boolean lock) {
        attachEntity(entity);
        if (identityScope != null && key != null) {
            if (lock) {
                identityScope.put(key, entity);
            } else {
                identityScope.putNoLock(key, entity);
            }
        }
    }

    /**
     * Sub classes with relations additionally set the DaoMaster here. Must be called before the entity is attached to
     * the identity scope.
     * 
     * @param entity
     *            The entitiy to attach
     * */
    protected void attachEntity(T entity) {
    }

    /**
     * Updates the given entities in the database using a transaction.
     * 
     * @param entities
     *            The entities to insert.
     * @throws SQLException 
     */
    public void updateInTx(Iterable<T> entities) throws SQLException {
        PreparedStatement stmt = statements.getUpdateStatement();
    	connection.setAutoCommit( false );
        try {
            synchronized (stmt) {
                if (identityScope != null) {
                    identityScope.lock();
                }
                try {
                    for (T entity : entities) {
                        updateInsideSynchronized(entity, stmt, false);
                    }
                } finally {
                    if (identityScope != null) {
                        identityScope.unlock();
                    }
                }
            }
            connection.commit();
        } catch(SQLException e) {
        	connection.rollback();
        	e.printStackTrace();
        } finally {
        	connection.setAutoCommit( true );
        }
    }

    /**
     * Updates the given entities in the database using a transaction.
     * 
     * @param entities
     *            The entities to update.
     * @throws SQLException 
     */
    public void updateInTx(T... entities) throws SQLException {
        updateInTx(Arrays.asList(entities));
    }

    protected void assertSinglePk() throws DaoException {
        if (config.pkColumns.length != 1) {
            throw new DaoException(this + " (" + config.tablename + ") does not have a single-column primary key");
        }
    }

    public long count() throws SQLException {
    	long result = 0L;
    	try {
    		PreparedStatement statement = connection.prepareStatement( "SELECT count(*) as counter FROM " + config.tablename );
    		ResultSet resultSet = statement.executeQuery();
    		if (resultSet.next())
    		{
    			result = resultSet.getLong( "counter" );
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return result;
//        return DatabaseUtils.queryNumEntries(db, '\'' + config.tablename + '\'');
    }

    /** See {@link #getKey(Object)}, but guarantees that the returned key is never null (throws if null). 
     * @throws DaoException */
    protected K getKeyVerified(T entity) throws DaoException {
        K key = getKey(entity);
        if (key == null) {
            if (entity == null) {
                throw new NullPointerException("Entity may not be null");
            } else {
                throw new DaoException("Entity has no key");
            }
        } else {
            return key;
        }
    }

    /** Gets the SQLiteDatabase for custom database access. Not needed for greenDAO entities. */
    public Connection getConnection() {
        return connection;
    }

    /** Reads the values from the current position of the given cursor and returns a new entity. */
    abstract protected T readEntity(ResultSet resultSet, int offset) throws SQLException;

    /** Reads the key from the current position of the given cursor, or returns null if there's no single-value key. */
    abstract protected K readKey(ResultSet resultSet, int offset) throws SQLException;

    /** Reads the values from the current position of the given cursor into an existing entity. */
    abstract protected void readEntity(ResultSet resultSet, T entity, int offset) throws SQLException;

    /** Binds the entity's values to the statement. Make sure to synchronize the statement outside of the method. */
    abstract protected void bindValues(PreparedStatement stmt, T entity) throws SQLException;

    /**
     * Updates the entity's key if possible (only for Long PKs currently). This method must always return the entity's
     * key regardless of whether the key existed before or not.
     */
    abstract protected K updateKeyAfterInsert(T entity, long rowId);

    /**
     * Returns the value of the primary key, if the entity has a single primary key, or, if not, null. Returns null if
     * entity is null.
     */
    abstract protected K getKey(T entity);

    /** Returns true if the Entity class can be updated, e.g. for setting the PK after insert. */
    abstract protected boolean isEntityUpdateable();

}
