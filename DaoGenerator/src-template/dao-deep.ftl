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
<#if entity.toOneRelations?has_content>
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
<#list entity.toOneRelations as toOne>
            SqlUtils.appendColumns(builder, "T${toOne_index}", daoSession.get${toOne.targetEntity.classNameDao}().getAllColumns());
<#if toOne_has_next>
            builder.append(',');
</#if>
</#list>
            builder.append(" FROM ${entity.tableName} T");
<#list entity.toOneRelations as toOne>
            builder.append(" LEFT JOIN ${toOne.targetEntity.tableName} T${toOne_index}<#--
--> ON T.'${toOne.fkProperties[0].columnName}'=T${toOne_index}.'${toOne.targetEntity.pkProperty.columnName}'");
</#list>
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected ${entity.className} loadCurrentDeep(ResultSet resultSet, boolean lock) throws SQLException {
        ${entity.className} entity = loadCurrent(resultSet, 0, lock);
        int offset = getAllColumns().length;

<#list entity.toOneRelations as toOne>
        ${toOne.targetEntity.className} ${toOne.name} = loadCurrentOther(daoSession.get${toOne.targetEntity.classNameDao}(), resultSet, offset);
<#if toOne.fkProperties[0].notNull>         if(${toOne.name} != null) {
    </#if>        entity.set${toOne.name?cap_first}(${toOne.name});
<#if toOne.fkProperties[0].notNull>
        }
</#if>
<#if toOne_has_next>
        offset += daoSession.get${toOne.targetEntity.classNameDao}().getAllColumns().length;
</#if>

</#list>
        return entity;    
    }

    public ${entity.className} loadDeep(Long key) throws SQLException {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        ResultSet resultSet = JDBCUtils.query(connection, sql, (Object[]) keyArray);
        
        try {
            boolean available = resultSet.next();
            if (!available) {
                return null;
            } else if (!resultSet.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + JDBCUtils.getCount(resultSet));
            }
            return loadCurrentDeep(resultSet, true);
        } finally {
            resultSet.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<${entity.className}> loadAllDeepFromResultSet(ResultSet resultSet) throws SQLException {
        int count = JDBCUtils.getCount(resultSet);
        List<${entity.className}> list = new ArrayList<${entity.className}>(count);
        
        if (resultSet.first()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(resultSet, false));
                } while (resultSet.next());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<${entity.className}> loadDeepAllAndCloseResultSet(ResultSet resultSet) throws SQLException {
        try {
            return loadAllDeepFromResultSet(resultSet);
        } finally {
            resultSet.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<${entity.className}> queryDeep(String where, String... selectionArg) throws SQLException {
    
        ResultSet resultSet = JDBCUtils.query( connection, ( getSelectDeep() + where ), (Object[]) selectionArg );
        return loadDeepAllAndCloseResultSet(resultSet);
    }
 
</#if>