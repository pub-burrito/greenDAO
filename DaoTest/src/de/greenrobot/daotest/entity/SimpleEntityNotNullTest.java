package de.greenrobot.daotest.entity;

import de.greenrobot.dao.test.AbstractDaoTestLongPk;

import de.greenrobot.daotest.SimpleEntityNotNull;
import de.greenrobot.daotest.SimpleEntityNotNullDao;

public class SimpleEntityNotNullTest extends AbstractDaoTestLongPk<SimpleEntityNotNullDao, SimpleEntityNotNull> {

    public SimpleEntityNotNullTest() {
        super(SimpleEntityNotNullDao.class);
    }

    @Override
    protected SimpleEntityNotNull createEntity(Long key) {
        SimpleEntityNotNull entity = new SimpleEntityNotNull();
        entity.setId(key);
        entity.setId();
        entity.setSimpleBoolean();
        entity.setSimpleByte();
        entity.setSimpleShort();
        entity.setSimpleInt();
        entity.setSimpleLong();
        entity.setSimpleFloat();
        entity.setSimpleDouble();
        entity.setSimpleString();
        entity.setSimpleByteArray();
        return entity;
    }

}
