package de.greenrobot.dao.async;

import java.util.concurrent.Callable;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.DaoException;
import de.greenrobot.dao.async.AsyncOperation.OperationType;
import de.greenrobot.dao.query.Query;

/**
 * Asynchronous interface to entity operations. All operations will enqueued a @link {@link AsyncOperation} and return
 * immediately (fine to call on the UI/main thread). The queue will be processed in a (single) background thread. The
 * processing order is the call order of the operations. It's possible to start multiple AsyncSessions that will execute
 * concurrently.
 * 
 * @author Markus
 * 
 * @see AbstractDaoSession#startAsyncSession()
 */
// Facade to AsyncOperationExecutor: prepares operations and delegates work to AsyncOperationExecutor.
public class AsyncSession {
    private final AbstractDaoSession daoSession;
    private final AsyncOperationExecutor executor;

    public AsyncSession(AbstractDaoSession daoSession) {
        this.daoSession = daoSession;
        this.executor = new AsyncOperationExecutor();
    }

    public int getMaxOperationCountToMerge() {
        return executor.getMaxOperationCountToMerge();
    }

    public void setMaxOperationCountToMerge(int maxOperationCountToMerge) {
        executor.setMaxOperationCountToMerge(maxOperationCountToMerge);
    }

    public int getWaitForMergeMillis() {
        return executor.getWaitForMergeMillis();
    }

    public void setWaitForMergeMillis(int waitForMergeMillis) {
        executor.setWaitForMergeMillis(waitForMergeMillis);
    }

    public AsyncOperationListener getListener() {
        return executor.getListener();
    }

    public void setListener(AsyncOperationListener listener) {
        executor.setListener(listener);
    }

    public boolean isCompleted() {
        return executor.isCompleted();
    }

    /**
     * Waits until all enqueued operations are complete. If the thread gets interrupted, any
     * {@link InterruptedException} will be rethrown as a {@link DaoException}.
     * @throws DaoException 
     */
    public void waitForCompletion() throws DaoException {
        executor.waitForCompletion();
    }

    /**
     * Waits until all enqueued operations are complete, but at most the given amount of milliseconds. If the thread
     * gets interrupted, any {@link InterruptedException} will be rethrown as a {@link DaoException}.
     * 
     * @return true if operations completed in the given time frame.
     * @throws DaoException 
     */
    public boolean waitForCompletion(int maxMillis) throws DaoException {
        return executor.waitForCompletion(maxMillis);
    }

    /** Asynchronous version of {@link AbstractDao#insert(Object)}. 
     * @throws DaoException */
    public AsyncOperation insert(Object entity) throws DaoException {
        return insert(entity, 0);
    }

    /** Asynchronous version of {@link AbstractDao#insert(Object)}. 
     * @throws DaoException */
    public AsyncOperation insert(Object entity, int flags) throws DaoException {
        return enqueueEntityOperation(OperationType.Insert, entity, flags);
    }

    /** Asynchronous version of {@link AbstractDao#insertInTx(Object...)}. 
     * @throws DaoException */
    public <E> AsyncOperation insertInTx(Class<E> entityClass, E... entities) throws DaoException {
        return insertInTx(entityClass, 0, entities);
    }

    /** Asynchronous version of {@link AbstractDao#insertInTx(Object...)}. 
     * @throws DaoException */
    public <E> AsyncOperation insertInTx(Class<E> entityClass, int flags, E... entities) throws DaoException {
        return enqueEntityOperation(OperationType.InsertInTxArray, entityClass, entities, flags);
    }

    /** Asynchronous version of {@link AbstractDao#insertInTx(Iterable)}. 
     * @throws DaoException */
    public <E> AsyncOperation insertInTx(Class<E> entityClass, Iterable<E> entities) throws DaoException {
        return insertInTx(entityClass, entities, 0);
    }

    /** Asynchronous version of {@link AbstractDao#insertInTx(Iterable)}. 
     * @throws DaoException */
    public <E> AsyncOperation insertInTx(Class<E> entityClass, Iterable<E> entities, int flags) throws DaoException {
        return enqueEntityOperation(OperationType.InsertInTxIterable, entityClass, entities, flags);
    }

    /** Asynchronous version of {@link AbstractDao#insertOrReplace(Object)}. 
     * @throws DaoException */
    public AsyncOperation insertOrReplace(Object entity) throws DaoException {
        return insertOrReplace(entity, 0);
    }

    /** Asynchronous version of {@link AbstractDao#insertOrReplace(Object)}. 
     * @throws DaoException */
    public AsyncOperation insertOrReplace(Object entity, int flags) throws DaoException {
        return enqueueEntityOperation(OperationType.InsertOrReplace, entity, flags);
    }

    /** Asynchronous version of {@link AbstractDao#insertOrReplaceInTx(Object...)}. 
     * @throws DaoException */
    public <E> AsyncOperation insertOrReplaceInTx(Class<E> entityClass, E... entities) throws DaoException {
        return insertOrReplaceInTx(entityClass, 0, entities);
    }

    /** Asynchronous version of {@link AbstractDao#insertOrReplaceInTx(Object...)}. 
     * @throws DaoException */
    public <E> AsyncOperation insertOrReplaceInTx(Class<E> entityClass, int flags, E... entities) throws DaoException {
        return enqueEntityOperation(OperationType.InsertOrReplaceInTxArray, entityClass, entities, flags);
    }

    /** Asynchronous version of {@link AbstractDao#insertOrReplaceInTx(Iterable)}. 
     * @throws DaoException */
    public <E> AsyncOperation insertOrReplaceInTx(Class<E> entityClass, Iterable<E> entities) throws DaoException {
        return insertOrReplaceInTx(entityClass, entities, 0);
    }

    /** Asynchronous version of {@link AbstractDao#insertOrReplaceInTx(Iterable)}. 
     * @throws DaoException */
    public <E> AsyncOperation insertOrReplaceInTx(Class<E> entityClass, Iterable<E> entities, int flags) throws DaoException {
        return enqueEntityOperation(OperationType.InsertOrReplaceInTxIterable, entityClass, entities, flags);
    }

    /** Asynchronous version of {@link AbstractDao#update(Object)}. 
     * @throws DaoException */
    public AsyncOperation update(Object entity) throws DaoException {
        return update(entity, 0);
    }

    /** Asynchronous version of {@link AbstractDao#update(Object)}. 
     * @throws DaoException */
    public AsyncOperation update(Object entity, int flags) throws DaoException {
        return enqueueEntityOperation(OperationType.Update, entity, flags);
    }

    /** Asynchronous version of {@link AbstractDao#updateInTx(Object...)}. 
     * @throws DaoException */
    public <E> AsyncOperation updateInTx(Class<E> entityClass, E... entities) throws DaoException {
        return updateInTx(entityClass, 0, entities);
    }

    /** Asynchronous version of {@link AbstractDao#updateInTx(Object...)}. 
     * @throws DaoException */
    public <E> AsyncOperation updateInTx(Class<E> entityClass, int flags, E... entities) throws DaoException {
        return enqueEntityOperation(OperationType.UpdateInTxArray, entityClass, entities, flags);
    }

    /** Asynchronous version of {@link AbstractDao#updateInTx(Iterable)}. 
     * @throws DaoException */
    public <E> AsyncOperation updateInTx(Class<E> entityClass, Iterable<E> entities) throws DaoException {
        return updateInTx(entityClass, entities, 0);
    }

    /** Asynchronous version of {@link AbstractDao#updateInTx(Iterable)}. 
     * @throws DaoException */
    public <E> AsyncOperation updateInTx(Class<E> entityClass, Iterable<E> entities, int flags) throws DaoException {
        return enqueEntityOperation(OperationType.UpdateInTxIterable, entityClass, entities, flags);
    }

    /** Asynchronous version of {@link AbstractDao#delete(Object)}. 
     * @throws DaoException */
    public AsyncOperation delete(Object entity) throws DaoException {
        return delete(entity, 0);
    }

    /** Asynchronous version of {@link AbstractDao#delete(Object)}. 
     * @throws DaoException */
    public AsyncOperation delete(Object entity, int flags) throws DaoException {
        return enqueueEntityOperation(OperationType.Delete, entity, flags);
    }

    /** Asynchronous version of {@link AbstractDao#deleteByKey(Object)}. 
     * @throws DaoException */
    public AsyncOperation deleteByKey(Object key) throws DaoException {
        return deleteByKey(key, 0);
    }

    /** Asynchronous version of {@link AbstractDao#deleteByKey(Object)}. 
     * @throws DaoException */
    public AsyncOperation deleteByKey(Object key, int flags) throws DaoException {
        return enqueueEntityOperation(OperationType.DeleteByKey, key, flags);
    }

    /** Asynchronous version of {@link AbstractDao#deleteInTx(Object...)}. 
     * @throws DaoException */
    public <E> AsyncOperation deleteInTx(Class<E> entityClass, E... entities) throws DaoException {
        return deleteInTx(entityClass, 0, entities);
    }

    /** Asynchronous version of {@link AbstractDao#deleteInTx(Object...)}. 
     * @throws DaoException */
    public <E> AsyncOperation deleteInTx(Class<E> entityClass, int flags, E... entities) throws DaoException {
        return enqueEntityOperation(OperationType.DeleteInTxArray, entityClass, entities, flags);
    }

    /** Asynchronous version of {@link AbstractDao#deleteInTx(Iterable)}. 
     * @throws DaoException */
    public <E> AsyncOperation deleteInTx(Class<E> entityClass, Iterable<E> entities) throws DaoException {
        return deleteInTx(entityClass, entities, 0);
    }

    /** Asynchronous version of {@link AbstractDao#deleteInTx(Iterable)}. 
     * @throws DaoException */
    public <E> AsyncOperation deleteInTx(Class<E> entityClass, Iterable<E> entities, int flags) throws DaoException {
        return enqueEntityOperation(OperationType.DeleteInTxIterable, entityClass, entities, flags);
    }

    /** Asynchronous version of {@link AbstractDao#deleteAll()}. 
     * @throws DaoException */
    public <E> AsyncOperation deleteAll(Class<E> entityClass) throws DaoException {
        return deleteAll(entityClass, 0);
    }

    /** Asynchronous version of {@link AbstractDao#deleteAll()}. 
     * @throws DaoException */
    public <E> AsyncOperation deleteAll(Class<E> entityClass, int flags) throws DaoException {
        return enqueEntityOperation(OperationType.DeleteAll, entityClass, null, flags);
    }

    /** Asynchronous version of {@link AbstractDaoSession#runInTx(Runnable)}. */
    public AsyncOperation runInTx(Runnable runnable) {
        return runInTx(runnable, 0);
    }

    /** Asynchronous version of {@link AbstractDaoSession#runInTx(Runnable)}. */
    public AsyncOperation runInTx(Runnable runnable, int flags) {
        return enqueueDatabaseOperation(OperationType.TransactionRunnable, runnable, flags);
    }

    /** Asynchronous version of {@link AbstractDaoSession#callInTx(Callable)}. */
    public AsyncOperation callInTx(Callable<?> callable) {
        return callInTx(callable, 0);
    }

    /** Asynchronous version of {@link AbstractDaoSession#callInTx(Callable)}. */
    public AsyncOperation callInTx(Callable<?> callable, int flags) {
        return enqueueDatabaseOperation(OperationType.TransactionCallable, callable, flags);
    }

    /** Asynchronous version of {@link Query#list()}. */
    public AsyncOperation queryList(Query<?> query) {
        return queryList(query, 0);
    }

    /** Asynchronous version of {@link Query#list()}. */
    public AsyncOperation queryList(Query<?> query, int flags) {
        return enqueueDatabaseOperation(OperationType.QueryList, query, flags);
    }

    /** Asynchronous version of {@link Query#unique()}. */
    public AsyncOperation queryUnique(Query<?> query) {
        return queryUnique(query, 0);
    }

    /** Asynchronous version of {@link Query#unique()}. */
    public AsyncOperation queryUnique(Query<?> query, int flags) {
        return enqueueDatabaseOperation(OperationType.QueryUnique, query, flags);
    }

    /** Asynchronous version of {@link AbstractDao#load(Object)}. 
     * @throws DaoException */
    public AsyncOperation load(Class<?> entityClass, Object key) throws DaoException {
        return load(entityClass, key, 0);
    }

    /** Asynchronous version of {@link AbstractDao#load(Object)}. 
     * @throws DaoException */
    public AsyncOperation load(Class<?> entityClass, Object key, int flags) throws DaoException {
        return enqueEntityOperation(OperationType.Load, entityClass, key, flags);
    }

    /** Asynchronous version of {@link AbstractDao#loadAll()}. 
     * @throws DaoException */
    public AsyncOperation loadAll(Class<?> entityClass) throws DaoException {
        return loadAll(entityClass, 0);
    }

    /** Asynchronous version of {@link AbstractDao#loadAll()}. 
     * @throws DaoException */
    public AsyncOperation loadAll(Class<?> entityClass, int flags) throws DaoException {
        return enqueEntityOperation(OperationType.LoadAll, entityClass, null, flags);
    }

    /** Asynchronous version of {@link AbstractDao#count()}. 
     * @throws DaoException */
    public AsyncOperation count(Class<?> entityClass) throws DaoException {
        return count(entityClass, 0);
    }

    /** Asynchronous version of {@link AbstractDao#count()}. 
     * @throws DaoException */
    public AsyncOperation count(Class<?> entityClass, int flags) throws DaoException {
        return enqueEntityOperation(OperationType.Count, entityClass, null, flags);
    }

    /** Asynchronous version of {@link AbstractDao#refresh(Object)}. 
     * @throws DaoException */
    public AsyncOperation refresh(Object entity) throws DaoException {
        return refresh(entity, 0);
    }

    /** Asynchronous version of {@link AbstractDao#refresh(Object)}. 
     * @throws DaoException */
    public AsyncOperation refresh(Object entity, int flags) throws DaoException {
        return enqueueEntityOperation(OperationType.Refresh, entity, flags);
    }

    private AsyncOperation enqueueDatabaseOperation(OperationType type, Object param, int flags) {
        AsyncOperation operation = new AsyncOperation(type, daoSession.getConnection(), param, flags);
        executor.enqueue(operation);
        return operation;
    }

    private AsyncOperation enqueueEntityOperation(OperationType type, Object entity, int flags) throws DaoException {
        return enqueEntityOperation(type, entity.getClass(), entity, flags);
    }

    private <E> AsyncOperation enqueEntityOperation(OperationType type, Class<E> entityClass, Object param, int flags) throws DaoException {
        AbstractDao<?, ?> dao = daoSession.getDao(entityClass);
        AsyncOperation operation = new AsyncOperation(type, dao, param, flags);
        executor.enqueue(operation);
        return operation;
    }

}
