package de.greenrobot.daotest.entity;

import de.greenrobot.dao.test.AbstractDaoTestLongPk;

import de.greenrobot.daotest.SimpleEntityNotNull;
import de.greenrobot.daotest.SimpleEntityNotNullDao;

public class SimpleEntityNotNullTest extends AbstractDaoTestLongPk<SimpleEntityNotNullDao, SimpleEntityNotNull> {

	private boolean simpleBoolean;
	private byte simpleByte;
	private short simpleShort;
	private int simpleInt;
	private long simpleLong;
	private float simpleFloat;
	private double simpleDouble;
	private String simpleString;
	private byte[] simpleByteArray;
	
    public SimpleEntityNotNullTest() {
        super(SimpleEntityNotNullDao.class);
    }

    @Override
    protected SimpleEntityNotNull createEntity(Long key) {
        SimpleEntityNotNull entity = new SimpleEntityNotNull();
        entity.setId(key);
		entity.setSimpleBoolean(simpleBoolean);
		entity.setSimpleByte(simpleByte);
		entity.setSimpleShort(simpleShort);
		entity.setSimpleInt(simpleInt);
		entity.setSimpleLong(simpleLong);
		entity.setSimpleFloat(simpleFloat);
		entity.setSimpleDouble(simpleDouble);
		entity.setSimpleString(simpleString);
		entity.setSimpleByteArray(simpleByteArray);
		return entity;
    }

}
