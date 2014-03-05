package de.greenrobot.daotest.entity;

import de.greenrobot.dao.test.AbstractDaoTestLongPk;

import de.greenrobot.daotest.AnActiveEntity;
import de.greenrobot.daotest.AnActiveEntityDao;

public class AnActiveEntityTest extends AbstractDaoTestLongPk<AnActiveEntityDao, AnActiveEntity> {

	
    public AnActiveEntityTest() {
        super(AnActiveEntityDao.class);
    }

    @Override
    protected AnActiveEntity createEntity(Long key) {
        AnActiveEntity entity = new AnActiveEntity();
        entity.setId(key);
		return entity;
    }

}
