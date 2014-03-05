package de.greenrobot.daotest;

import java.sql.SQLException;

import java.util.List;
import de.greenrobot.daotest.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table TREE_ENTITY.
 */
public class TreeEntity {


    private Long id;
    private Long parentId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient TreeEntityDao myDao;

    private TreeEntity parent;
    private Long parent__resolvedKey;

    private List<TreeEntity> children;

    public TreeEntity() {
    }

    public TreeEntity(Long id) {
        this.id = id;
    }

    public TreeEntity(Long id, Long parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = this.daoSession != null ? this.daoSession.getTreeEntityDao() : null;
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

    /** To-one relationship, resolved on first access. */
    public TreeEntity getParent() throws SQLException {
        Long __key = this.parentId;
        if (parent__resolvedKey == null || !parent__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TreeEntityDao targetDao = daoSession.getTreeEntityDao();
            TreeEntity parentNew = targetDao.load(__key);
            synchronized (this) {
                parent = parentNew;
            	parent__resolvedKey = __key;
            }
        }
        return parent;
    }

    public void setParent(TreeEntity parent) {
        synchronized (this) {
            this.parent = parent;
            parentId = parent == null ? null : parent.getId();
            parent__resolvedKey = parentId;
        }
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<TreeEntity> getChildren() throws SQLException {
        if (children == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TreeEntityDao targetDao = daoSession.getTreeEntityDao();
            List<TreeEntity> childrenNew = targetDao._queryTreeEntity_Children(id);
            synchronized (this) {
                if(children == null) {
                    children = childrenNew;
                }
            }
        }
        return children;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetChildren() {
        children = null;
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

}
