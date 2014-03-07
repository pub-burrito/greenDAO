package de.greenrobot.dao.internal;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUtils
{
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
	
	public static int getCount( ResultSet resultSet )
	{
		int count = 0;
		if ( resultSet != null )
		{
			try
			{
				resultSet.beforeFirst();
				resultSet.last();
				count = resultSet.getRow();
				resultSet.beforeFirst();
			}
			catch ( SQLException e )
			{
				count = 0;
			}
		}
		return count;
	}
	
	public static Connection connect( String driverName, String connectionString ) throws SQLException
	{
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
		
		return DriverManager.getConnection( connectionString );
	}
	
	public static final void appendValueToSql( StringBuilder sql, Object value )
	{
		if ( value == null )
		{
			sql.append( "NULL" );
		}
		else if ( value instanceof Boolean )
		{
			Boolean bool = (Boolean) value;
			if ( bool )
			{
				sql.append( '1' );
			}
			else
			{
				sql.append( '0' );
			}
		}
		else
		{
			appendEscapedSQLString( sql, value.toString() );
		}
	}
	
	public static void appendEscapedSQLString( StringBuilder sb, String sqlString )
	{
		sb.append( '\'' );
		if ( sqlString.indexOf( '\'' ) != -1 )
		{
			int length = sqlString.length();
			for ( int i = 0; i < length; i++ )
			{
				char c = sqlString.charAt( i );
				if ( c == '\'' )
				{
					sb.append( '\'' );
				}
				sb.append( c );
			}
		}
		else
			sb.append( sqlString );
		sb.append( '\'' );
	}
}
