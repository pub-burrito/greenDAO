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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/** Database utils, for example to execute SQL scripts */
// TODO add unit tests
public class DbUtils {

    public static void vacuum(Connection connection) throws SQLException {
        connection.prepareStatement("VACUUM").execute();
    }

    /**
     * Calls {@link #executeSqlScript(Context, SQLiteDatabase, String, boolean)} with transactional set to true.
     * 
     * @return number of statements executed.
     * @throws SQLException 
     */
    public static int executeSqlScript(Context context, Connection connection, String assetFilename) throws IOException, SQLException {
        return executeSqlScript(context, connection, assetFilename, true);
    }

    /**
     * Executes the given SQL asset in the given database (SQL file should be UTF-8). The database file may contain
     * multiple SQL statements. Statements are split using a simple regular expression (something like
     * "semicolon before a line break"), not by analyzing the SQL syntax. This will work for many SQL files, but check
     * yours.
     * 
     * @return number of statements executed.
     * @throws SQLException 
     */
    public static int executeSqlScript(Context context, Connection connection, String assetFilename, boolean transactional)
            throws IOException, SQLException {
        byte[] bytes = readAsset(context, assetFilename);
        String sql = new String(bytes, "UTF-8");
        String[] lines = sql.split(";(\\s)*[\n\r]");
        int count;
        if (transactional) {
            count = executeSqlStatementsInTx(connection, lines);
        } else {
            count = executeSqlStatements(connection, lines);
        }
        DaoLog.i("Executed " + count + " statements from SQL script '" + assetFilename + "'");
        return count;
    }

    public static int executeSqlStatementsInTx(Connection connection, String[] statements) throws SQLException {
    	int count = 0;
    	connection.setAutoCommit( false );
        try {
            count = executeSqlStatements(connection, statements);
            connection.commit();
        } catch(SQLException e) {
        	connection.rollback();
        	e.printStackTrace();
        } finally {
        	connection.setAutoCommit( true );
        }
        return count;
    }

    public static int executeSqlStatements(Connection connection, String[] statements) throws SQLException {
        int count = 0;
        for (String line : statements) {
            line = line.trim();
            if (line.length() > 0) {
            	PreparedStatement statement = connection.prepareStatement( line );
            	statement.execute();
            	statement.close();
                count++;
            }
        }
        return count;
    }

    /**
     * Copies all available data from in to out without closing any stream.
     * 
     * @return number of bytes copied
     */
    public static int copyAllBytes(InputStream in, OutputStream out) throws IOException {
        int byteCount = 0;
        byte[] buffer = new byte[4096];
        while (true) {
            int read = in.read(buffer);
            if (read == -1) {
                break;
            }
            out.write(buffer, 0, read);
            byteCount += read;
        }
        return byteCount;
    }

    public static byte[] readAllBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copyAllBytes(in, out);
        return out.toByteArray();
    }

    public static byte[] readAsset(Context context, String filename) throws IOException {
        InputStream in = context.getResources().getAssets().open(filename);
        try {
            return readAllBytes(in);
        } finally {
            in.close();
        }
    }

    public static void logTableDump(Connection connection, String tablename) {
// FIXME need to JDBCfy this...
//        Cursor cursor = connection.query(tablename, null, null, null, null, null, null);
//        try {
//            String dump = DatabaseUtils.dumpCursorToString(cursor);
//            DaoLog.d(dump);
//        } finally {
//            cursor.close();
//        }
    }

}
