package de.greenrobot.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import de.greenrobot.dao.internal.TableStatements;

/** For internal use by greenDAO only. */
public final class InternalQueryDaoAccess<T> {
    private final AbstractDao<T, ?> dao;

    public InternalQueryDaoAccess(AbstractDao<T, ?> abstractDao) {
        dao = abstractDao;
    }

    public T loadCurrent(ResultSet resultSet, int offset, boolean lock) throws SQLException {
        return dao.loadCurrent(resultSet, offset, lock);
    }

    public List<T> loadAllAndCloseCursor(ResultSet resultSet) throws SQLException {
        return dao.loadAllAndCloseCursor(resultSet);
    }

    public T loadUniqueAndCloseCursor(ResultSet resultSet) throws SQLException {
        return dao.loadUniqueAndCloseCursor(resultSet);
    }

    public TableStatements getStatements() {
        return dao.getStatements();
    }

    public static <T2> TableStatements getStatements(AbstractDao<T2, ?> dao) {
        return dao.getStatements();
    }

}