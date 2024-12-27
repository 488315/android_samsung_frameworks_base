package android.database.sqlite.trace;

import android.database.sqlite.SQLiteConnection;
import android.database.sqlite.SQLiteDebug;
import android.os.Process;
import android.os.SystemProperties;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class SQLiteTrace {
    private static HashMap<String, SQLiteTraceSession> mCurrentSessions = new HashMap<>();
    private static final int mUid = Process.myUid();

    public static boolean isEnabled(String dbPath) {
        if (SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE) {
            String databaseName = new File(dbPath).getName();
            if (SystemProperties.getBoolean("db.trace.enabled." + databaseName, false)) {
                return true;
            }
        }
        return false;
    }

    public static void trace(SQLiteConnection.Operation op, String dbPath) {
        try {
            SQLiteTraceSession session = getSession(dbPath);
            session.pushOperation(op);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static SQLiteTraceSession getSession(String dbPath) {
        SQLiteTraceSession session = mCurrentSessions.get(dbPath);
        if (session == null || !session.isAlive()) {
            return startSession(dbPath);
        }
        return session;
    }

    private static synchronized SQLiteTraceSession startSession(String dbPath) {
        SQLiteTraceSession session;
        synchronized (SQLiteTrace.class) {
            session = mCurrentSessions.get(dbPath);
            if (session == null || !session.isAlive()) {
                session = new SQLiteTraceSession();
                session.start(dbPath);
                mCurrentSessions.put(dbPath, session);
            }
        }
        return session;
    }

    public static class SQLiteTraceSession {
        private static final int OPERATION_EXPORT_THRESHOLD = 100;
        private static final int TIMEOUT = 100;
        private SQLiteTraceExporter mExporter;
        private ArrayList<TraceOperation> mOperations = new ArrayList<>();
        private AtomicBoolean mIsAlive = new AtomicBoolean(true);

        /* JADX INFO: Access modifiers changed from: private */
        public void start(final String dbPath) {
            Thread session =
                    new Thread(
                            new Runnable() { // from class:
                                // android.database.sqlite.trace.SQLiteTrace$SQLiteTraceSession$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SQLiteTrace.SQLiteTraceSession.this.lambda$start$0(dbPath);
                                }
                            });
            session.start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$start$0(String dbPath) {
            try {
                try {
                    TraceConfiguration configuration = new TraceConfiguration(dbPath);
                    this.mExporter = new SQLiteTraceJsonExporter(configuration);
                    String dbName = configuration.databaseName;
                    while (SQLiteTrace.isEnabled(dbName)) {
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (this.mOperations.size() >= 100) {
                            this.mExporter.writeOperations(clearAndGetOperations());
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } finally {
                end();
            }
        }

        private void end() {
            this.mIsAlive.set(false);
            if (this.mExporter != null) {
                try {
                    this.mExporter.writeOperations(clearAndGetOperations());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    this.mExporter.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        public synchronized void pushOperation(SQLiteConnection.Operation operation) {
            if (this.mIsAlive.get()) {
                if (operation.mKind.equals("prepare")) {
                    return;
                }
                this.mOperations.add(new TraceOperation(operation));
            }
        }

        public boolean isAlive() {
            return this.mIsAlive.get();
        }

        private synchronized List<TraceOperation> clearAndGetOperations() {
            List<TraceOperation> operations;
            operations = this.mOperations;
            this.mOperations = new ArrayList<>();
            return operations;
        }
    }

    public static class TraceConfiguration {
        public final String databaseFilePath;
        public final String databaseName;
        public final String traceFilePath;

        public TraceConfiguration(String dbPath) {
            this.traceFilePath = dbPath + "-sqlite-trace-" + System.currentTimeMillis();
            this.databaseFilePath = new File(dbPath).getAbsolutePath();
            this.databaseName = new File(dbPath).getName();
        }
    }

    public static class TraceOperation {
        public final ArrayList<Object> bindArgs;
        public final int callingPid;
        public final int connectionId;
        public final int countedRows;
        public final long endTime;
        public final Exception exception;
        public final long executionTime;
        public final String sql;
        public final long startTime;
        public final long tid;
        public final int totalRows;

        public TraceOperation(SQLiteConnection.Operation operation) {
            if (operation.mSql == null || operation.mSql.equals("")) {
                this.sql = operation.mKind;
            } else {
                this.sql = operation.mSql;
            }
            if (operation.mBindArgs != null) {
                ArrayList<Object> copiedBindArgs = new ArrayList<>();
                Iterator<Object> it = operation.mBindArgs.iterator();
                while (it.hasNext()) {
                    Object bindArg = it.next();
                    if (bindArg instanceof byte[]) {
                        copiedBindArgs.add(((byte[]) bindArg).clone());
                    } else {
                        copiedBindArgs.add(bindArg);
                    }
                }
                this.bindArgs = copiedBindArgs;
            } else {
                this.bindArgs = null;
            }
            this.startTime = operation.mStartTime;
            this.endTime = operation.mEndTime;
            this.executionTime = operation.mExecutionTime;
            this.callingPid = operation.mCallingPid;
            this.tid = operation.mTid;
            this.connectionId = operation.mConnectionId;
            this.countedRows = operation.mCountedRows;
            this.totalRows = operation.mTotalRows;
            this.exception = operation.mException;
        }
    }
}
