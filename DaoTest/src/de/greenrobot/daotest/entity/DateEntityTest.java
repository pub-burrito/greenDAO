package de.greenrobot.daotest.entity;

import de.greenrobot.dao.test.AbstractDaoTestLongPk;

import de.greenrobot.daotest.DateEntity;
import de.greenrobot.daotest.DateEntityDao;

public class DateEntityTest extends AbstractDaoTestLongPk<DateEntityDao, DateEntity> {

	private java.util.Date dateNotNull;
	
    public DateEntityTest() {
        super(DateEntityDao.class);
    }

    @Override
    protected DateEntity createEntity(Long key) {
        DateEntity entity = new DateEntity();
        entity.setId(key);
		entity.setDateNotNull(dateNotNull);
		return entity;
    }

}
