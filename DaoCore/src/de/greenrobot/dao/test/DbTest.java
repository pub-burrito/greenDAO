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

package de.greenrobot.dao.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;


import android.app.Application;
import android.app.Instrumentation;
import android.test.AndroidTestCase;
import de.greenrobot.dao.DbUtils;
import de.greenrobot.platform.java.util.JDBCUtils;

/**
 * Base class for database related testing, which prepares an in-memory or an file-based DB (using the test {@link
 * android.content.Context}). Also, offers some convenience methods to create new {@link Application} objects similar to
 * {@link android.test.ApplicationTestCase}.
 * <p/>
 * Unlike ApplicationTestCase, this class should behave more correctly when you call {@link #createApplication(Class)}
 * during {@link #setUp()}: {@link android.test.ApplicationTestCase#testApplicationTestCaseSetUpProperly()} leaves
 * Application objects un-terminated.
 *
 * @author Markus
 */
public abstract class DbTest extends AndroidTestCase {

    public static final String DB_NAME = "greendao-unittest-db.temp";

    protected final Random random;
    protected final boolean inMemory;
    protected Connection connection;

    private Application application;

    public DbTest() {
        this(true);
    }

    public DbTest(boolean inMemory) {
        this.inMemory = inMemory;
        random = new Random();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        connection = createConnection();
    }

    /** Returns a prepared application with the onCreate method already called. */
    public <T extends Application> T createApplication(Class<T> appClass) {
        assertNull("Application already created", application);
        T app;
        try {
            app = (T) Instrumentation.newApplication(appClass, getContext());
        } catch (Exception e) {
            throw new RuntimeException("Could not create application " + appClass, e);
        }
        app.onCreate();
        application = app;
        return app;
    }

    /** Terminates a previously created application. Also called by {@link #tearDown()} if needed. */
    public void terminateApplication() {
        assertNotNull("Application not yet created", application);
        application.onTerminate();
        application = null;
    }

    /** Gets the previously created application. */
    public <T extends Application> T getApplication() {
        assertNotNull("Application not yet created", application);
        return (T) application;
    }

    /** May be overriden by sub classes to set up a different db. 
     * @throws SQLException */
    protected Connection createConnection() throws SQLException {
//        if (inMemory) {
//            return SQLiteDatabase.create(null);
//        } else {
//            getContext().deleteDatabase(DB_NAME);
//            return getContext().openOrCreateDatabase(DB_NAME, 0, null);
//        }
        
    	return JDBCUtils.connect( DB_NAME );
    }

    @Override
    /** Closes the db, and terminates an application, if one was created before. */
    protected void tearDown() throws Exception {
        if (application != null) {
            terminateApplication();
        }
        connection.close();
        if (!inMemory) {
            getContext().deleteDatabase(DB_NAME);
        }
        super.tearDown();
    }

    protected void logTableDump(String tablename) {
        DbUtils.logTableDump(connection, tablename);
    }

}