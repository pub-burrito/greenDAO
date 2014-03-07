package de.greenrobot.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.greenrobot.dao.internal.JDBCUtils;

public abstract class AbstractConnectionManager
{
	private String driverName;
	private String connectionString;
	private int newVersion;
	private Connection connection;
	
	public AbstractConnectionManager( String driverName, String connectionString, int version )
	{
		this.driverName = driverName;
		this.connectionString = connectionString;
		this.newVersion = version;
	}
	
	// TODO connection pooling
	public Connection getConnection() throws SQLException
	{
		if ( isEmpty( driverName ) || isEmpty( connectionString ) )
		{
			throw new SQLException( String.format( "Invalid driver name: [%s] or connection string: [%s]", driverName, connectionString ) );
		}
		
		if ( !isOpen( connection ) )
		{
			connection = JDBCUtils.connect( driverName, connectionString );
			onOpen( connection );
		}
		
		int currentVersion = getCurrentVersion();
		if ( !hasTables( connection ) )
		{
			onCreate( connection );
		}
		else if ( isUpgradable( currentVersion ) )
		{
			onUpgrade( connection, currentVersion, newVersion );
		}
		
		return connection;
	}
	
	//@formatter:off
	public void onOpen( Connection connection ) throws SQLException {}
	
	public void onCreate( Connection connection ) throws SQLException {}
	
	public void onUpgrade( Connection connection, int oldVersion, int newVersion ) throws SQLException {}
	//@formatter:on
	
	private boolean hasTables( Connection connection ) throws SQLException
	{
		boolean result = false;
		if ( isOpen( connection ) )
		{
			DatabaseMetaData metadata = connection.getMetaData();
			String[] types = { "TABLE" };
			ResultSet resultSet = metadata.getTables( null, null, "%", types );
			result = resultSet.next();
			resultSet.close();
		}
		return result;
	}
	
	// FIXME must return the current version from the database not the newVersion
	private int getCurrentVersion()
	{
		return newVersion;
	}

	private boolean isUpgradable( int currentVersion )
	{
		return currentVersion < newVersion;
	}

	private boolean isOpen( Connection connection ) throws SQLException
	{
		return connection != null && !connection.isClosed();
	}
	
	private boolean isEmpty( String string )
	{
		return string == null || string.trim().length() <= 0;
	}
}
