package de.greenrobot.daotest.entity;

import de.greenrobot.dao.test.AbstractDaoTestLongPk;

import de.greenrobot.daotest.RelationEntity;
import de.greenrobot.daotest.RelationEntityDao;

public class RelationEntityTest extends AbstractDaoTestLongPk<RelationEntityDao, RelationEntity> {

	private long testIdNotNull;
	
    public RelationEntityTest() {
        super(RelationEntityDao.class);
    }

    @Override
    protected RelationEntity createEntity(Long key) {
        RelationEntity entity = new RelationEntity();
        entity.setId(key);
		entity.setTestIdNotNull(testIdNotNull);
		return entity;
    }

}
