package de.greenrobot.daotest.entity;

import de.greenrobot.dao.test.AbstractDaoTestLongPk;

import de.greenrobot.daotest.AutoincrementEntity;
import de.greenrobot.daotest.AutoincrementEntityDao;

public class AutoincrementEntityTest extends AbstractDaoTestLongPk<AutoincrementEntityDao, AutoincrementEntity> {

	
    public AutoincrementEntityTest() {
        super(AutoincrementEntityDao.class);
    }

    @Override
    protected AutoincrementEntity createEntity(Long key) {
        AutoincrementEntity entity = new AutoincrementEntity();
        entity.setId(key);
		return entity;
    }

}
