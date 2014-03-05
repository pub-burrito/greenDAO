package de.greenrobot.daotest.entity;

import de.greenrobot.dao.test.AbstractDaoTestLongPk;

import de.greenrobot.daotest.ExtendsImplementsEntity;
import de.greenrobot.daotest.ExtendsImplementsEntityDao;

public class ExtendsImplementsEntityTest extends AbstractDaoTestLongPk<ExtendsImplementsEntityDao, ExtendsImplementsEntity> {

    public ExtendsImplementsEntityTest() {
        super(ExtendsImplementsEntityDao.class);
    }

    @Override
    protected ExtendsImplementsEntity createEntity(Long key) {
        ExtendsImplementsEntity entity = new ExtendsImplementsEntity();
        entity.setId(key);
        return entity;
    }

}
