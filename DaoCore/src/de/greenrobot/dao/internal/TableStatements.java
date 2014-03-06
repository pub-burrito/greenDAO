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
package de.greenrobot.dao.internal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.greenrobot.dao.DaoException;

/** Helper class to create SQL statements for specific tables (used by greenDAO internally). */
public class TableStatements {
    private final Connection connection;
    private final String tablename;
    private final String[] allColumns;
    private final String[] pkColumns;

    private PreparedStatement insertStatement;
    private PreparedStatement insertOrReplaceStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;

    private volatile String selectAll;
    private volatile String selectByKey;
    private volatile String selectByRowId;
    private volatile String selectKeys;

    public TableStatements(Connection connection, String tablename, String[] allColumns, String[] pkColumns) {
        this.connection = connection;
        this.tablename = tablename;
        this.allColumns = allColumns;
        this.pkColumns = pkColumns;
    }

    public PreparedStatement getInsertStatement() throws SQLException {
        if (insertStatement == null) {
            String sql = SqlUtils.createSqlInsert("INSERT INTO ", tablename, allColumns);
            insertStatement = connection.prepareStatement( sql );
        }
        return insertStatement;
    }

    public PreparedStatement getInsertOrReplaceStatement() throws SQLException {
        if (insertOrReplaceStatement == null) {
            String sql = SqlUtils.createSqlInsert("INSERT OR REPLACE INTO ", tablename, allColumns);
            insertOrReplaceStatement = connection.prepareStatement(sql);
        }
        return insertOrReplaceStatement;
    }

    public PreparedStatement getDeleteStatement() throws SQLException {
        if (deleteStatement == null) {
            String sql = SqlUtils.createSqlDelete(tablename, pkColumns);
            deleteStatement = connection.prepareStatement(sql);
        }
        return deleteStatement;
    }

    public PreparedStatement getUpdateStatement() throws SQLException {
        if (updateStatement == null) {
            String sql = SqlUtils.createSqlUpdate(tablename, allColumns, pkColumns);
            updateStatement = connection.prepareStatement(sql);
        }
        return updateStatement;
    }

    /** ends with an space to simplify appending to this string. 
     * @throws DaoException */
    public String getSelectAll() throws DaoException {
        if (selectAll == null) {
            selectAll = SqlUtils.createSqlSelect(tablename, "T", allColumns);
        }
        return selectAll;
    }

    /** ends with an space to simplify appending to this string. 
     * @throws DaoException */
    public String getSelectKeys() throws DaoException {
        if (selectKeys == null) {
            selectKeys = SqlUtils.createSqlSelect(tablename, "T", pkColumns);
        }
        return selectKeys;
    }

    // TODO precompile
    public String getSelectByKey() throws DaoException {
        if (selectByKey == null) {
            StringBuilder builder = new StringBuilder(getSelectAll());
            builder.append("WHERE ");
            SqlUtils.appendColumnsEqValue(builder, "T", pkColumns);
            selectByKey = builder.toString();
        }
        return selectByKey;
    }

    public String getSelectByRowId() throws DaoException {
        if (selectByRowId == null) {
            selectByRowId = getSelectAll() + "WHERE ROWID=?";
        }
        return selectByRowId;
    }

}
