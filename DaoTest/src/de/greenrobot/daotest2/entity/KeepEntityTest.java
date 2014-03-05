package de.greenrobot.daotest2.entity;

import de.greenrobot.dao.test.AbstractDaoTestLongPk;

import de.greenrobot.daotest2.KeepEntity;
import de.greenrobot.daotest2.dao.KeepEntityDao;

public class KeepEntityTest extends AbstractDaoTestLongPk<KeepEntityDao, KeepEntity> {

    public KeepEntityTest() {
        super(KeepEntityDao.class);
    }

    @Override
    protected KeepEntity createEntity(Long key) {
        KeepEntity entity = new KeepEntity();
        entity.setId(key);
        return entity;
    }

}
