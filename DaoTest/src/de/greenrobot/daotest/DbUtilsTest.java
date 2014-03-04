/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * This file is part of greenDAO Generator.
 * 
 * greenDAO Generator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * greenDAO Generator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with greenDAO Generator.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.greenrobot.daotest;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.greenrobot.dao.DbUtils;
import de.greenrobot.dao.test.DbTest;

public class DbUtilsTest extends DbTest {
    public void testExecuteSqlScript() throws IOException, SQLException {
        DbUtils.executeSqlScript(getContext(), connection, "minimal-entity.sql");
        PreparedStatement statement = connection.prepareStatement( "SELECT count(*) from MINIMAL_ENTITY" );
        ResultSet resultSet = statement.executeQuery();
        try {
            resultSet.next();
            assertEquals(5, resultSet.getInt(1));
        } finally {
            statement.close();
        }
    }

}
