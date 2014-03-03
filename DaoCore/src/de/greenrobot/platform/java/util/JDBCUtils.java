package de.greenrobot.platform.java.util;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUtils
{
	public static String driverName = "org.sqldroid.SQLDroidDriver";
	public static String JDBC_URL_PREFIX = "jdbc:sqlite:";
	public static String packageName = "de.greenrobot";
	public static String DB_DIRECTORY = "/data/data/" + packageName + "/databases/";
	public static String url = JDBC_URL_PREFIX + DB_DIRECTORY;

	public static ResultSet query( Connection connection, String sql ) throws SQLException
	{
		return query( connection, sql, new Object[] {} );
	}
	
	public static ResultSet query( Connection connection, String sql, Object... parameters ) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement( sql );
		for ( int i = 0; i < parameters.length; i++ )
		{
			statement.setObject( i, parameters[i] );
		}
		ResultSet result = statement.executeQuery();
		statement.close();
		return result;
	}
	
	public static boolean execute( Connection connection, String sql ) throws SQLException
	{
		return execute( connection, sql, new Object[] {} );
	}
	
	public static boolean execute( Connection connection, String sql, Object... parameters ) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement( sql );
		for ( int i = 0; i < parameters.length; i++ )
		{
			statement.setObject( i, parameters[i] );
		}
		boolean result = statement.execute();
		statement.close();
		return result;
	}
	
	public static boolean transaction( Connection connection, String sql ) throws SQLException
	{
		return transaction( connection, sql, new Object[] {} );
	}
	
	public static boolean transaction( Connection connection, String sql, Object... parameters ) throws SQLException
	{
		boolean result = false;
		connection.setAutoCommit( false );
		try
		{
			result = execute( connection, sql, parameters );
			connection.commit();
		}
		catch ( SQLException exception )
		{
			if ( connection != null )
			{
				connection.rollback();
			}
			exception.printStackTrace();
		}
		finally
		{
			connection.setAutoCommit( true );
		}
		return result;
	}
	
	public static boolean isNull( ResultSet resultSet, int index ) throws SQLException
	{
		return resultSet.getObject( index ) == null;
	}
	
	public static Connection connect( String db ) throws SQLException
	{
		// setup
		File f = new File( db );
		if ( f.exists() )
		{
			f.delete();
		}
		else
		{
			if ( null != f.getParent() )
			{
				f.getParentFile().mkdirs();
			}
		}
		// Loads and registers the JDBC driver
		try
		{
			DriverManager.registerDriver( (Driver) ( Class.forName( driverName, true, JDBCUtils.class.getClassLoader() ).newInstance() ) );
		}
		catch ( SQLException e )
		{
			e.printStackTrace();
		}
		catch ( IllegalAccessException e )
		{
			e.printStackTrace();
		}
		catch ( InstantiationException e )
		{
			e.printStackTrace();
		}
		catch ( ClassNotFoundException e )
		{
			e.printStackTrace();
		}
		
		System.err.println("conn: "+url+db);
		return DriverManager.getConnection( url + db );
	}
}
