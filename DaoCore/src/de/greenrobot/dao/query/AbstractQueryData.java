package de.greenrobot.dao.query;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.dao.AbstractDao;

abstract class AbstractQueryData<T, Q extends AbstractQuery<T>> {
    final String sql;
    final AbstractDao<T, ?> dao;
    final String[] initialValues;
    final Map<Long, WeakReference<Q>> queriesForThreads;

    AbstractQueryData(AbstractDao<T, ?> dao, String sql, String[] initialValues) {
        this.dao = dao;
        this.sql = sql;
        this.initialValues = initialValues;
        queriesForThreads = new HashMap<Long, WeakReference<Q>>();
    }

    /** Just an optimized version, which performs faster if the current thread is already the query's owner thread. */
    Q forCurrentThread(Q query) {
        if (Thread.currentThread() == query.ownerThread) {
            System.arraycopy(initialValues, 0, query.parameters, 0, initialValues.length);
            return query;
        } else {
            return forCurrentThread();
        }
    }

    Q forCurrentThread() {
        long threadId = Thread.currentThread().getId();
        synchronized (queriesForThreads) {
            WeakReference<Q> queryRef = queriesForThreads.get(threadId);
            Q query = queryRef != null ? queryRef.get() : null;
            if (query == null) {
                gc();
                query = createQuery();
                queriesForThreads.put(threadId, new WeakReference<Q>(query));
            } else {
                System.arraycopy(initialValues, 0, query.parameters, 0, initialValues.length);
            }
            return query;
        }
    }

    abstract protected Q createQuery();

    void gc() {
        synchronized (queriesForThreads) {
            for (int i = queriesForThreads.size() - 1; i >= 0; i--) {
                if (queriesForThreads.get(i) == null) {
                    queriesForThreads.remove(i);
                }
            }
        }
    }

}
