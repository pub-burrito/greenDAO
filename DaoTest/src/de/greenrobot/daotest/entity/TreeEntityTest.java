package de.greenrobot.daotest.entity;

import de.greenrobot.dao.test.AbstractDaoTestLongPk;

import de.greenrobot.daotest.TreeEntity;
import de.greenrobot.daotest.TreeEntityDao;

public class TreeEntityTest extends AbstractDaoTestLongPk<TreeEntityDao, TreeEntity> {

    public TreeEntityTest() {
        super(TreeEntityDao.class);
    }

    @Override
    protected TreeEntity createEntity(Long key) {
        TreeEntity entity = new TreeEntity();
        entity.setId(key);
        return entity;
    }

}
