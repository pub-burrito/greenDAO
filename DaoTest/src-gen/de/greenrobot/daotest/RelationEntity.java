package de.greenrobot.daotest;

import java.sql.SQLException;

import de.greenrobot.daotest.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table RELATION_ENTITY.
 */
public class RelationEntity {

    private Long id;
    private Long parentId;
    private Long testId;
    private long testIdNotNull;
    private String simpleString;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient RelationEntityDao myDao;

    private RelationEntity parent;
    private Long parent__resolvedKey;

    private TestEntity testEntity;
    private Long testEntity__resolvedKey;

    private TestEntity testNotNull;
    private Long testNotNull__resolvedKey;

    private TestEntity testWithoutProperty;
    private boolean testWithoutProperty__refreshed;


    public RelationEntity() {
    }

    public RelationEntity(Long id) {
        this.id = id;
    }

    public RelationEntity(Long id, Long parentId, Long testId, long testIdNotNull, String simpleString) {
        this.id = id;
        this.parentId = parentId;
        this.testId = testId;
        this.testIdNotNull = testIdNotNull;
        this.simpleString = simpleString;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRelationEntityDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public long getTestIdNotNull() {
        return testIdNotNull;
    }

    public void setTestIdNotNull(long testIdNotNull) {
        this.testIdNotNull = testIdNotNull;
    }

    public String getSimpleString() {
        return simpleString;
    }

    public void setSimpleString(String simpleString) {
        this.simpleString = simpleString;
    }

    /** To-one relationship, resolved on first access. 
     * @throws SQLException */
    public RelationEntity getParent() throws SQLException {
        Long __key = this.parentId;
        if (parent__resolvedKey == null || !parent__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RelationEntityDao targetDao = daoSession.getRelationEntityDao();
            RelationEntity parentNew = targetDao.load(__key);
            synchronized (this) {
                parent = parentNew;
            	parent__resolvedKey = __key;
            }
        }
        return parent;
    }

    public void setParent(RelationEntity parent) {
        synchronized (this) {
            this.parent = parent;
            parentId = parent == null ? null : parent.getId();
            parent__resolvedKey = parentId;
        }
    }

    /** To-one relationship, resolved on first access. 
     * @throws SQLException */
    public TestEntity getTestEntity() throws SQLException {
        Long __key = this.testId;
        if (testEntity__resolvedKey == null || !testEntity__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TestEntityDao targetDao = daoSession.getTestEntityDao();
            TestEntity testEntityNew = targetDao.load(__key);
            synchronized (this) {
                testEntity = testEntityNew;
            	testEntity__resolvedKey = __key;
            }
        }
        return testEntity;
    }

    public void setTestEntity(TestEntity testEntity) {
        synchronized (this) {
            this.testEntity = testEntity;
            testId = testEntity == null ? null : testEntity.getId();
            testEntity__resolvedKey = testId;
        }
    }

    /** To-one relationship, resolved on first access. 
     * @throws SQLException */
    public TestEntity getTestNotNull() throws SQLException {
        long __key = this.testIdNotNull;
        if (testNotNull__resolvedKey == null || !testNotNull__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TestEntityDao targetDao = daoSession.getTestEntityDao();
            TestEntity testNotNullNew = targetDao.load(__key);
            synchronized (this) {
                testNotNull = testNotNullNew;
            	testNotNull__resolvedKey = __key;
            }
        }
        return testNotNull;
    }

    public void setTestNotNull(TestEntity testNotNull) {
        if (testNotNull == null) {
            throw new DaoException("To-one property 'testIdNotNull' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.testNotNull = testNotNull;
            testIdNotNull = testNotNull.getId();
            testNotNull__resolvedKey = testIdNotNull;
        }
    }

    /** To-one relationship, resolved on first access. 
     * @throws SQLException */
    public TestEntity getTestWithoutProperty() throws SQLException {
        if (testWithoutProperty != null || !testWithoutProperty__refreshed) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TestEntityDao targetDao = daoSession.getTestEntityDao();
            targetDao.refresh(testWithoutProperty);
            testWithoutProperty__refreshed = true;
        }
        return testWithoutProperty;
    }

    /** To-one relationship, returned entity is not refreshed and may carry only the PK property. */
    public TestEntity peakTestWithoutProperty() {
        return testWithoutProperty;
    }

    public void setTestWithoutProperty(TestEntity testWithoutProperty) {
        synchronized (this) {
            this.testWithoutProperty = testWithoutProperty;
            testWithoutProperty__refreshed = true;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. 
     * @throws SQLException */
    public void delete() throws SQLException {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. 
     * @throws SQLException */
    public void update() throws SQLException {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. 
     * @throws SQLException */
    public void refresh() throws SQLException {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
