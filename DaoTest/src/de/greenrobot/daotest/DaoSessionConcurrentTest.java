package de.greenrobot.daotest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

import android.os.SystemClock;
import de.greenrobot.dao.DaoLog;
import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.test.AbstractDaoSessionTest;

public class DaoSessionConcurrentTest extends AbstractDaoSessionTest<DaoMaster, DaoSession> {
    class TestThread extends Thread {
        final Runnable runnable;

        public TestThread(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            latchThreadsReady.countDown();
            try {
                latchInsideTx.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            runnable.run();
            latchThreadsDone.countDown();
        }

    }

    private final static int TIME_TO_WAIT_FOR_THREAD = 100; // Use 1000 to be on the safe side, 100 once stable

    protected TestEntityDao dao;

    protected CountDownLatch latchThreadsReady;
    protected CountDownLatch latchInsideTx;
    protected CountDownLatch latchThreadsDone;

    public DaoSessionConcurrentTest() {
        super(DaoMaster.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        dao = daoSession.getTestEntityDao();
    }

    protected void initThreads(Runnable... runnables) throws InterruptedException {
        latchThreadsReady = new CountDownLatch(runnables.length);
        latchInsideTx = new CountDownLatch(1);
        latchThreadsDone = new CountDownLatch(runnables.length);
        for (Runnable runnable : runnables) {
            new TestThread(runnable).start();
        }
        latchThreadsReady.await();
    }

    public void testConcurrentInsertDuringTx() throws InterruptedException, SQLException {
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                try
				{
					dao.insert(createEntity(null));
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        };
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                try
				{
					dao.insertInTx(createEntity(null));
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        };
        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                try
				{
					daoSession.runInTx(new Runnable() {
					    @Override
					    public void run() {
					        try
							{
								dao.insert(createEntity(null));
							}
							catch ( SQLException e )
							{
								e.printStackTrace();
							}
					    }
					});
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        };
        Runnable runnable4 = new Runnable() {
            @Override
            public void run() {
                try
				{
					dao.insertWithoutSettingPk(createEntity(null));
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        };
        Runnable runnable5 = new Runnable() {
            @Override
            public void run() {
                try
				{
					dao.insertOrReplace(createEntity(null));
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        };
        initThreads(runnable1, runnable2, runnable3, runnable4, runnable5);
        // Builds the statement so it is ready immediately in the thread
        dao.insert(createEntity(null));
        doTx(new Runnable() {
            @Override
            public void run() {
                try
				{
					dao.insert(createEntity(null));
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        });
        latchThreadsDone.await();
        assertEquals(7, dao.count());
    }

    public void testConcurrentUpdateDuringTx() throws InterruptedException, SQLException {
        final TestEntity entity = createEntity(null);
        dao.insert(entity);
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                try
				{
					dao.update(entity);
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        };
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                try
				{
					dao.updateInTx(entity);
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        };
        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                try
				{
					daoSession.runInTx(new Runnable() {
					    @Override
					    public void run() {
					        try
							{
								dao.update(entity);
							}
							catch ( SQLException e )
							{
								e.printStackTrace();
							}
					    }
					});
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        };
        initThreads(runnable1, runnable2, runnable3);
        // Builds the statement so it is ready immediately in the thread
        dao.update(entity);
        doTx(new Runnable() {
            @Override
            public void run() {
                try
				{
					dao.update(entity);
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        });
        latchThreadsDone.await();
    }

    public void testConcurrentDeleteDuringTx() throws InterruptedException, SQLException {
        final TestEntity entity = createEntity(null);
        dao.insert(entity);
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                try
				{
					dao.delete(entity);
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        };
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                try
				{
					dao.deleteInTx(entity);
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        };
        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                try
				{
					daoSession.runInTx(new Runnable() {
					    @Override
					    public void run() {
					        try
							{
								dao.delete(entity);
							}
							catch ( SQLException e )
							{
								e.printStackTrace();
							}
					    }
					});
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        };
        initThreads(runnable1, runnable2, runnable3);
        // Builds the statement so it is ready immediately in the thread
        dao.delete(entity);
        doTx(new Runnable() {
            @Override
            public void run() {
                try
				{
					dao.delete(entity);
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        });
        latchThreadsDone.await();
    }

    // Query doesn't involve any statement locking currently, but just to stay on the safe side...
    public void testConcurrentQueryDuringTx() throws InterruptedException, SQLException {
        final TestEntity entity = createEntity(null);
        dao.insert(entity);
        final Query<TestEntity> query = dao.queryBuilder().build();
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                try
				{
					query.forCurrentThread().list();
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        };

        initThreads(runnable1);
        // Builds the statement so it is ready immediately in the thread
        query.list();
        doTx(new Runnable() {
            @Override
            public void run() {
                try
				{
					query.list();
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        });
        latchThreadsDone.await();
    }

    // No connection for read can be acquired while TX is active; this will deadlock!
    public void _testConcurrentLockAndQueryDuringTx() throws InterruptedException, SQLException {
        final TestEntity entity = createEntity(null);
        dao.insert(entity);
        final Query<TestEntity> query = dao.queryBuilder().build();
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                synchronized (query) {
                    try
					{
						query.list();
					}
					catch ( SQLException e )
					{
						e.printStackTrace();
					}
                }
            }
        };

        initThreads(runnable1);
        // Builds the statement so it is ready immediately in the thread
        query.list();
        doTx(new Runnable() {
            @Override
            public void run() {
                synchronized (query) {
                    try
					{
						query.list();
					}
					catch ( SQLException e )
					{
						e.printStackTrace();
					}
                }
            }
        });
        latchThreadsDone.await();
    }

    public void testConcurrentDeleteQueryDuringTx() throws InterruptedException, SQLException {
        final TestEntity entity = createEntity(null);
        dao.insert(entity);
        final DeleteQuery<TestEntity> query = dao.queryBuilder().buildDelete();
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                try
				{
					query.forCurrentThread().executeDeleteWithoutDetachingEntities();
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        };

        initThreads(runnable1);
        // Builds the statement so it is ready immediately in the thread
        query.executeDeleteWithoutDetachingEntities();
        doTx(new Runnable() {
            @Override
            public void run() {
                try
				{
					query.executeDeleteWithoutDetachingEntities();
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        });
        latchThreadsDone.await();
    }

    public void testConcurrentResolveToMany() throws InterruptedException, SQLException {
        final ToManyEntity entity = new ToManyEntity();
        ToManyEntityDao toManyDao = daoSession.getToManyEntityDao();
        toManyDao.insert(entity);

        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                try
				{
					entity.getToManyTargetEntityList();
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        };

        initThreads(runnable1);
        doTx(new Runnable() {
            @Override
            public void run() {
                try
				{
					entity.getToManyTargetEntityList();
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        });
        latchThreadsDone.await();
    }

    public void testConcurrentResolveToOne() throws InterruptedException, SQLException {
        final TreeEntity entity = new TreeEntity();
        TreeEntityDao toOneDao = daoSession.getTreeEntityDao();
        toOneDao.insert(entity);

        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                try
				{
					entity.getParent();
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        };

        initThreads(runnable1);
        doTx(new Runnable() {
            @Override
            public void run() {
                try
				{
					entity.getParent();
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
				}
            }
        });
        latchThreadsDone.await();
    }

    /**
     * We could put the statements inside ThreadLocals (fast enough), but it comes with initialization penalty for new
     * threads and costs more memory.
     */
    public void _testThreadLocalSpeed() {
        final Connection connection = dao.getConnection();
        ThreadLocal<PreparedStatement> threadLocal = new ThreadLocal<PreparedStatement>() {
            @Override
            protected PreparedStatement initialValue() {
                try
				{
					return connection.prepareStatement("SELECT 42");
				}
				catch ( SQLException e )
				{
					e.printStackTrace();
					return null;
				}
            }
        };
        threadLocal.get();
        long start = SystemClock.currentThreadTimeMillis();
        for (int i = 0; i < 1000; i++) {
        	PreparedStatement sqLiteStatement = threadLocal.get();
            assertNotNull(sqLiteStatement);
        }
        Long time = SystemClock.currentThreadTimeMillis() - start;
        DaoLog.d("TIME: " + time + "ms");
        // Around 1ms on a S3
        assertTrue(time < 10);
    }

    protected void doTx(final Runnable runnableInsideTx) throws SQLException {
        daoSession.runInTx(new Runnable() {
            @Override
            public void run() {
                latchInsideTx.countDown();
                // Give the concurrent thread time so it will try to acquire locks
                try {
                    Thread.sleep(TIME_TO_WAIT_FOR_THREAD);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                runnableInsideTx.run();
            }
        });
    }

    protected TestEntity createEntity(Long key) {
        TestEntity entity = new TestEntity(key);
        entity.setSimpleStringNotNull("green");
        return entity;
    }
}
