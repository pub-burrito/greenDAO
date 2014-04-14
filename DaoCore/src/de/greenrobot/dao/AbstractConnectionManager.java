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
		
		int currentVersion = getCurrentVersion( connection );
		if ( !hasTables( connection ) )
		{
			onCreate( connection );
		}
		else if ( isUpgradable( currentVersion ) )
		{
			onUpgrade( connection, currentVersion, newVersion );
		}
		setCurrentVersion( connection, newVersion );
		
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
	
	// FIXME this will only work on SQLite because it makes use of PRAGMA
	private int getCurrentVersion( Connection connection )
	{
		int version = newVersion;
		try
		{
			ResultSet resultSet = JDBCUtils.query( connection, "PRAGMA user_version" );
			if ( resultSet.next() )
			{
				version = resultSet.getInt( 1 );
			}
		}
		catch ( SQLException e )
		{
			e.printStackTrace();
		}
		return version;
	}
	
	// FIXME Only work on SQLite because it makes use of PRAGMA
	private void setCurrentVersion( Connection connection, int version )
	{
		try
		{
			JDBCUtils.execute( connection, "PRAGMA user_version = " + version );
			this.newVersion = version;
		}
		catch ( SQLException e )
		{
			e.printStackTrace();
		}
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
