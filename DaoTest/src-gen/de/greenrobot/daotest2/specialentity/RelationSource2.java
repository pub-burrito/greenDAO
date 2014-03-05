package de.greenrobot.daotest2.specialentity;

import java.sql.SQLException;

import java.util.List;
import de.greenrobot.daotest2.dao.DaoSession;
import de.greenrobot.dao.DaoException;

import de.greenrobot.daotest2.ToManyTarget2;
import de.greenrobot.daotest2.dao.ToManyTarget2Dao;
import de.greenrobot.daotest2.specialdao.RelationSource2Dao;
import de.greenrobot.daotest2.to1_specialdao.ToOneTarget2Dao;
import de.greenrobot.daotest2.to1_specialentity.ToOneTarget2;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table RELATION_SOURCE2.
 */
public class RelationSource2 {


    private Long id;
    private Long toOneId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient RelationSource2Dao myDao;

    private ToOneTarget2 toOneTarget2;
    private Long toOneTarget2__resolvedKey;

    private List<ToManyTarget2> toManyTarget2List;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public RelationSource2() {
    }

    public RelationSource2(Long id) {
        this.id = id;
    }

    public RelationSource2(Long id, Long toOneId) {
        this.id = id;
        this.toOneId = toOneId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = this.daoSession != null ? this.daoSession.getRelationSource2Dao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getToOneId() {
        return toOneId;
    }

    public void setToOneId(Long toOneId) {
        this.toOneId = toOneId;
    }

    /** To-one relationship, resolved on first access. */
    public ToOneTarget2 getToOneTarget2() throws SQLException {
        Long __key = this.toOneId;
        if (toOneTarget2__resolvedKey == null || !toOneTarget2__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ToOneTarget2Dao targetDao = daoSession.getToOneTarget2Dao();
            ToOneTarget2 toOneTarget2New = targetDao.load(__key);
            synchronized (this) {
                toOneTarget2 = toOneTarget2New;
            	toOneTarget2__resolvedKey = __key;
            }
        }
        return toOneTarget2;
    }

    public void setToOneTarget2(ToOneTarget2 toOneTarget2) {
        synchronized (this) {
            this.toOneTarget2 = toOneTarget2;
            toOneId = toOneTarget2 == null ? null : toOneTarget2.getId();
            toOneTarget2__resolvedKey = toOneId;
        }
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<ToManyTarget2> getToManyTarget2List() throws SQLException {
        if (toManyTarget2List == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ToManyTarget2Dao targetDao = daoSession.getToManyTarget2Dao();
            List<ToManyTarget2> toManyTarget2ListNew = targetDao._queryRelationSource2_ToManyTarget2List(id);
            synchronized (this) {
                if(toManyTarget2List == null) {
                    toManyTarget2List = toManyTarget2ListNew;
                }
            }
        }
        return toManyTarget2List;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetToManyTarget2List() {
        toManyTarget2List = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() throws SQLException {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() throws SQLException {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() throws SQLException {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
