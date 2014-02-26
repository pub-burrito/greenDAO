/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import de.greenrobot.dao.async.AsyncSession;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * DaoSession gives you access to your DAOs, offers convenient persistence methods, and also serves as a session cache.<br/>
 * <br/>
 * To access the DAOs, call the get{entity}Dao methods by the generated DaoSession sub class.<br/>
 * <br/>
 * DaoSession offers many of the available persistence operations on entities as a convenience. Consider using DAOs
 * directly to access all available operations, especially if you call a lot of operations on a single entity type to
 * avoid the overhead imposed by DaoSession (the overhead is small, but it may add up).<br/>
 * <br/>
 * By default, the DaoSession has a session cache (IdentityScopeType.Session). The session cache is not just a plain
 * data cache to improve performance, but also manages object identities. For example, if you load the same entity twice
 * in a query, you will get a single Java object instead of two when using a session cache. This is particular useful
 * for relations pointing to a common set of entities.
 * 
 * This class is thread-safe.
 * 
 * @author Markus
 * 
 */
public class AbstractDaoSession {
    private final Connection connection;
    private final Map<Class<?>, AbstractDao<?, ?>> entityToDao;

    public AbstractDaoSession(Connection connection) {
        this.connection = connection;
        this.entityToDao = new HashMap<Class<?>, AbstractDao<?, ?>>();
    }

    protected <T> void registerDao(Class<T> entityClass, AbstractDao<T, ?> dao) {
        entityToDao.put(entityClass, dao);
    }

    /** Convenient call for {@link AbstractDao#insert(Object)}. 
     * @throws SQLException */
    public <T> long insert(T entity) throws SQLException {
        @SuppressWarnings("unchecked")
        AbstractDao<T, ?> dao = (AbstractDao<T, ?>) getDao(entity.getClass());
        return dao.insert(entity);
    }

    /** Convenient call for {@link AbstractDao#insertOrReplace(Object)}. 
     * @throws SQLException */
    public <T> long insertOrReplace(T entity) throws SQLException {
        @SuppressWarnings("unchecked")
        AbstractDao<T, ?> dao = (AbstractDao<T, ?>) getDao(entity.getClass());
        return dao.insertOrReplace(entity);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. 
     * @throws SQLException */
    public <T> void refresh(T entity) throws SQLException {
        @SuppressWarnings("unchecked")
        AbstractDao<T, ?> dao = (AbstractDao<T, ?>) getDao(entity.getClass());
        dao.refresh(entity);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. 
     * @throws SQLException */
    public <T> void update(T entity) throws SQLException {
        @SuppressWarnings("unchecked")
        AbstractDao<T, ?> dao = (AbstractDao<T, ?>) getDao(entity.getClass());
        dao.update(entity);
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. 
     * @throws SQLException */
    public <T> void delete(T entity) throws SQLException {
        @SuppressWarnings("unchecked")
        AbstractDao<T, ?> dao = (AbstractDao<T, ?>) getDao(entity.getClass());
        dao.delete(entity);
    }

    /** Convenient call for {@link AbstractDao#deleteAll()}. 
     * @throws SQLException */
    public <T> void deleteAll(Class<T> entityClass) throws SQLException {
        @SuppressWarnings("unchecked")
        AbstractDao<T, ?> dao = (AbstractDao<T, ?>) getDao(entityClass);
        dao.deleteAll();
    }

    /** Convenient call for {@link AbstractDao#load(Object)}. 
     * @throws SQLException */
    public <T, K> T load(Class<T> entityClass, K key) throws SQLException {
        @SuppressWarnings("unchecked")
        AbstractDao<T, K> dao = (AbstractDao<T, K>) getDao(entityClass);
        return dao.load(key);
    }

    /** Convenient call for {@link AbstractDao#loadAll()}. 
     * @throws SQLException */
    public <T, K> List<T> loadAll(Class<T> entityClass) throws SQLException {
        @SuppressWarnings("unchecked")
        AbstractDao<T, K> dao = (AbstractDao<T, K>) getDao(entityClass);
        return dao.loadAll();
    }

    /** Convenient call for {@link AbstractDao#queryRaw(String, String...)}. 
     * @throws SQLException */
    public <T, K> List<T> queryRaw(Class<T> entityClass, String where, String... selectionArgs) throws SQLException {
        @SuppressWarnings("unchecked")
        AbstractDao<T, K> dao = (AbstractDao<T, K>) getDao(entityClass);
        return dao.queryRaw(where, selectionArgs);
    }

    /** Convenient call for {@link AbstractDao#queryBuilder()}. */
    public <T> QueryBuilder<T> queryBuilder(Class<T> entityClass) {
        @SuppressWarnings("unchecked")
        AbstractDao<T, ?> dao = (AbstractDao<T, ?>) getDao(entityClass);
        return dao.queryBuilder();
    }

    public AbstractDao<?, ?> getDao(Class<? extends Object> entityClass) {
        AbstractDao<?, ?> dao = entityToDao.get(entityClass);
        if (dao == null) {
            throw new DaoException("No DAO registered for " + entityClass);
        }
        return dao;
    }

    /**
     * Run the given Runnable inside a database transaction. If you except a result, consider callInTx.
     */
    public void runInTx(Runnable runnable) {
    	// TODO transaction
//        connection.beginTransaction();
        try {
            runnable.run();
//            connection.setTransactionSuccessful();
        } finally {
//            connection.endTransaction();
        }
    }

    /**
     * Calls the given Callable inside a database transaction and returns the result of the Callable. If you don't
     * except a result, consider runInTx.
     */
    public <V> V callInTx(Callable<V> callable) throws Exception {
// TODO transaction
//    	connection.beginTransaction();
        try {
            V result = callable.call();
//            connection.setTransactionSuccessful();
            return result;
        } finally {
//            connection.endTransaction();
        }
    }

    /**
     * Like {@link #callInTx(Callable)} but does not require Exception handling (rethrows an Exception as a runtime
     * DaoException).
     */
    public <V> V callInTxNoException(Callable<V> callable) {
// TODO transaction
//    	connection.beginTransaction();
        try {
            V result;
            try {
                result = callable.call();
            } catch (Exception e) {
                throw new DaoException("Callable failed", e);
            }
//            connection.setTransactionSuccessful();
            return result;
        } finally {
//            connection.endTransaction();
        }
    }

    /** Gets the SQLiteDatabase for custom database access. Not needed for greenDAO entities. */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Creates a new {@link AsyncSession} to issue asynchronous entity operations. See {@link AsyncSession} for details.
     */
    public AsyncSession startAsyncSession() {
        return new AsyncSession(this);
    }

}
