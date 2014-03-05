package de.greenrobot.daotest.entity;

import de.greenrobot.dao.test.AbstractDaoTestLongPk;

import de.greenrobot.daotest.TestEntity;
import de.greenrobot.daotest.TestEntityDao;

public class TestEntityTest extends AbstractDaoTestLongPk<TestEntityDao, TestEntity> {

    public TestEntityTest() {
        super(TestEntityDao.class);
    }

    @Override
    protected TestEntity createEntity(Long key) {
        TestEntity entity = new TestEntity();
        entity.setId(key);
        entity.setSimpleInt();
        entity.setSimpleStringNotNull();
        return entity;
    }

}
