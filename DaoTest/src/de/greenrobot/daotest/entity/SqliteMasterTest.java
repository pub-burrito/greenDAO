package de.greenrobot.daotest.entity;

import de.greenrobot.dao.test.AbstractDaoTestLongPk;

import de.greenrobot.daotest.SqliteMaster;
import de.greenrobot.daotest.SqliteMasterDao;

public class SqliteMasterTest extends AbstractDaoTestLongPk<SqliteMasterDao, SqliteMaster> {

	
    public SqliteMasterTest() {
        super(SqliteMasterDao.class);
    }

    @Override
    protected SqliteMaster createEntity(Long key) {
        SqliteMaster entity = new SqliteMaster();
		return entity;
    }

}
