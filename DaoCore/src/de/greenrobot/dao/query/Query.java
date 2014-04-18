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
package de.greenrobot.dao.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoException;

/**
 * A repeatable query returning entities.
 * 
 * @author Markus
 * 
 * @param <T>
 *            The enitity class the query will return results for.
 */
// TODO support long, double and other types, not just Strings, for parameters
// TODO Make parameters setable by Property (if unique in paramaters)
// TODO Query for PKs/ROW IDs
// TODO Make query compilable
public class Query<T> extends AbstractQuery<T> {
    private final static class QueryData<T2> extends AbstractQueryData<T2, Query<T2>> {
        private final int limitPosition;
        private final int offsetPosition;

        QueryData(AbstractDao<T2, ?> dao, String sql, String[] initialValues, int limitPosition, int offsetPosition) {
            super(dao,sql,initialValues);
            this.limitPosition = limitPosition;
            this.offsetPosition = offsetPosition;
        }

        @Override
        protected Query<T2> createQuery() {
            return new Query<T2>(this, dao, sql, initialValues.clone(), limitPosition, offsetPosition);
        }

    }

    /** For internal use by greenDAO only. */
    public static <T2> Query<T2> internalCreate(AbstractDao<T2, ?> dao, String sql, Object[] initialValues) {
        return create(dao, sql, initialValues, -1, -1);
    }

    static <T2> Query<T2> create(AbstractDao<T2, ?> dao, String sql, Object[] initialValues, int limitPosition,
            int offsetPosition) {
        QueryData<T2> queryData = new QueryData<T2>(dao, sql, toStringArray(initialValues), limitPosition,
                offsetPosition);
        return queryData.forCurrentThread();
    }

    private final int limitPosition;
    private final int offsetPosition;
    private final QueryData<T> queryData;

    private Query(QueryData<T> queryData, AbstractDao<T, ?> dao, String sql, String[] initialValues, int limitPosition,
            int offsetPosition) {
        super(dao, sql, initialValues);
        this.queryData = queryData;
        this.limitPosition = limitPosition;
        this.offsetPosition = offsetPosition;
    }

    public Query<T> forCurrentThread() {
        return queryData.forCurrentThread(this);
    }

    /**
     * Sets the parameter (0 based) using the position in which it was added during building the query.
     * @throws DaoException 
     */
    public void setParameter(int index, Object parameter) throws DaoException {
        if (index >= 0 && (index == limitPosition || index == offsetPosition)) {
            throw new IllegalArgumentException("Illegal parameter index: " + index);
        }
        super.setParameter(index, parameter);
    }

    /**
     * Sets the limit of the maximum number of results returned by this Query. {@link QueryBuilder#limit(int)} must have
     * been called on the QueryBuilder that created this Query object.
     * @throws DaoException 
     */
    public void setLimit(int limit) throws DaoException {
        checkThread();
        if (limitPosition == -1) {
            throw new IllegalStateException("Limit must be set with QueryBuilder before it can be used here");
        }
        parameters[limitPosition] = Integer.toString(limit);
    }

    /**
     * Sets the offset for results returned by this Query. {@link QueryBuilder#offset(int)} must have been called on the
     * QueryBuilder that created this Query object.
     * @throws DaoException 
     */
    public void setOffset(int offset) throws DaoException {
        checkThread();
        if (offsetPosition == -1) {
            throw new IllegalStateException("Offset must be set with QueryBuilder before it can be used here");
        }
        parameters[offsetPosition] = Integer.toString(offset);
    }

    /** Executes the query and returns the result as a list containing all entities loaded into memory. 
     * @throws SQLException */
    public List<T> list() throws SQLException {
        checkThread();
        Connection connection = dao.getConnection();
        PreparedStatement statement = connection.prepareStatement( sql );
        for ( int i = 0; i < parameters.length; i++ )
		{
        	int index = i + 1;
			statement.setString( index, parameters[i] );
		}
        ResultSet resultSet = statement.executeQuery();
        return daoAccess.loadAllAndCloseCursor(resultSet);
    }

    /**
     * Executes the query and returns the result as a list that lazy loads the entities on first access. Entities are
     * cached, so accessing the same entity more than once will not result in loading an entity from the underlying
     * cursor again.Make sure to close it to close the underlying cursor.
     * @throws SQLException 
     */
    public LazyList<T> listLazy() throws SQLException {
        checkThread();
        Connection connection = dao.getConnection();
        PreparedStatement statement = connection.prepareStatement( sql );
        for ( int i = 0; i < parameters.length; i++ )
		{
        	int index = i + 1;
			statement.setString( index, parameters[i] );
		}
        ResultSet resultSet = statement.executeQuery();
        return new LazyList<T>(daoAccess, resultSet, true);
    }

    /**
     * Executes the query and returns the result as a list that lazy loads the entities on every access (uncached). Make
     * sure to close the list to close the underlying cursor.
     * @throws SQLException 
     */
    public LazyList<T> listLazyUncached() throws SQLException {
        checkThread();
        Connection connection = dao.getConnection();
        PreparedStatement statement = connection.prepareStatement( sql );
        for ( int i = 0; i < parameters.length; i++ )
		{
        	int index = i + 1;
			statement.setString( index, parameters[i] );
		}
        ResultSet resultSet = statement.executeQuery();
        return new LazyList<T>(daoAccess, resultSet, false);
    }

    /**
     * Executes the query and returns the result as a list iterator; make sure to close it to close the underlying
     * cursor. The cursor is closed once the iterator is fully iterated through.
     * @throws SQLException 
     */
    public CloseableListIterator<T> listIterator() throws SQLException {
        return listLazyUncached().listIteratorAutoClose();
    }

    /**
     * Executes the query and returns the unique result or null.
     * 
     * @throws DaoException
     *             if the result is not unique
     * @return Entity or null if no matching entity was found
     * @throws SQLException 
     */
    public T unique() throws SQLException {
        checkThread();
        Connection connection = dao.getConnection();
        PreparedStatement statement = connection.prepareStatement( sql );
        for ( int i = 0; i < parameters.length; i++ )
		{
        	int index = i + 1;
			statement.setString( index, parameters[i] );
		}
        ResultSet resultSet = statement.executeQuery();
        return daoAccess.loadUniqueAndCloseCursor(resultSet);
    }

    /**
     * Executes the query and returns the unique result (never null).
     * 
     * @throws DaoException
     *             if the result is not unique or no entity was found
     * @return Entity
     * @throws SQLException 
     */
    public T uniqueOrThrow() throws SQLException {
        T entity = unique();
        if (entity == null) {
            throw new DaoException("No entity found for query");
        }
        return entity;
    }

}
