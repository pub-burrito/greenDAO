package de.greenrobot.daotest.entity;

import de.greenrobot.dao.test.AbstractDaoTestLongPk;

import de.greenrobot.daotest.ToManyEntity;
import de.greenrobot.daotest.ToManyEntityDao;

public class ToManyEntityTest extends AbstractDaoTestLongPk<ToManyEntityDao, ToManyEntity> {

	
    public ToManyEntityTest() {
        super(ToManyEntityDao.class);
    }

    @Override
    protected ToManyEntity createEntity(Long key) {
        ToManyEntity entity = new ToManyEntity();
        entity.setId(key);
		return entity;
    }

}
