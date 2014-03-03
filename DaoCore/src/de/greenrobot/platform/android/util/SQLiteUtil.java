package de.greenrobot.platform.android.util;

import java.sql.Connection;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteUtil
{	
	public static SQLiteDatabase from(Connection connection, Context context)
	{
		SQLiteDatabase db = null;
		if (connection != null)
		{
			try
			{
				String dbname = connection.getCatalog();
				db = context.openOrCreateDatabase(dbname, 0, null);
			}
			catch ( SQLException e )
			{
				throw new RuntimeException( "can not get database name", e );
			}
		}
		return db;
	}
	
}
