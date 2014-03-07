<#--

Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)     
                                                                           
This file is part of greenDAO Generator.                                   
                                                                           
greenDAO Generator is free software: you can redistribute it and/or modify 
it under the terms of the GNU General Public License as published by       
the Free Software Foundation, either version 3 of the License, or          
(at your option) any later version.                                        
greenDAO Generator is distributed in the hope that it will be useful,      
but WITHOUT ANY WARRANTY; without even the implied warranty of             
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              
GNU General Public License for more details.                               
                                                                           
You should have received a copy of the GNU General Public License          
along with greenDAO Generator.  If not, see <http://www.gnu.org/licenses/>.

-->
package ${schema.defaultJavaPackageDao};

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;

import de.greenrobot.dao.internal.JDBCUtils;

import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.DaoException;
import de.greenrobot.dao.identityscope.IdentityScopeType;

<#list schema.entities as entity>
import ${entity.javaPackageDao}.${entity.classNameDao};
</#list>

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version ${schema.version}): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = ${schema.version};

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(Connection connection, boolean ifNotExists) throws SQLException {
<#list schema.entities as entity>
<#if !entity.skipTableCreation>
        ${entity.classNameDao}.createTable(connection, ifNotExists);
</#if>
</#list>
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(Connection connection, boolean ifExists) throws SQLException {
<#list schema.entities as entity>
<#if !entity.skipTableCreation>
        ${entity.classNameDao}.dropTable(connection, ifExists);
</#if>
</#list>
    }
    
    public static abstract class AbstractConnectionManager {
    
    	private String driverName;
    	private String connectionString;
    	private Connection connection;
    	
    	public AbstractConnectionManager(String driverName, String connectionString) {
    		this.driverName = driverName;
    		this.connectionString = connectionString;
    		try {
	    		onCreate();
	    	} catch (SQLException e) {
	    		throw new RuntimeException("Unable to manage this connection", e);
	    	}
    	}
    	
    	// TODO connection pooling
    	public Connection getConnection() throws SQLException {
    		if (this.connection == null) {
		    	this.connection = JDBCUtils.connect(driverName, connectionString);
			}
			return this.connection;    	
    	}
    	
		public void onOpen( Connection connection ) throws SQLException {} // not mandatory

    	public abstract void onCreate(Connection connection) throws SQLException;

    	public void onUpgrade(Connection connection, int oldVersion, int newVersion) throws SQLException {} // not mandatory
    	
    	private void onCreate() throws SQLException {
    		Connection connection = getConnection();
    		if (isBigBang(connection)) {
    			onCreate(connection);
    		}
    	}
    	
    	private boolean isBigBang(Connection connection) throws SQLException {
    		ResultSet resultSet = connection.getMetaData().getCatalogs();
    		boolean result = !resultSet.next();
    		resultSet.close();
    		return result;
    	}
    }
    
    public static abstract class ConnectionManager extends AbstractConnectionManager {

        public ConnectionManager(String driverName, String connectionString) {
            super(driverName, connectionString);
        }

        @Override
        public void onCreate(Connection connection) throws SQLException {
            //Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(connection, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevConnectionManager extends ConnectionManager {
        public DevConnectionManager(String driverName, String connectionString) {
            super(driverName, connectionString);
        }

        @Override
        public void onUpgrade(Connection connection, int oldVersion, int newVersion) throws SQLException {
            //Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(connection, true);
            onCreate(connection);
        }
    }
    
    public DaoMaster(Connection connection) throws DaoException {
        super(connection, SCHEMA_VERSION);
<#list schema.entities as entity>
        registerDaoClass(${entity.classNameDao}.class);
</#list>
    }
    
    public DaoSession newSession() {
        return new DaoSession(connection, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(connection, type, daoConfigMap);
    }
    
}
