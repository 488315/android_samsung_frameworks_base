package android.database.sqlite.trace;

import android.database.sqlite.SQLiteConnection;
import android.database.sqlite.SQLiteDebug;
import android.database.sqlite.trace.SQLiteTrace;
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

    public static boolean isEnabled(String str) {
        if (SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE) {
            if (SystemProperties.getBoolean("db.trace.enabled." + new File(str).getName(), false)) {
                return true;
            }
        }
        return false;
    }

    public static void trace(SQLiteConnection.Operation operation, String str) {
        try {
            getSession(str).pushOperation(operation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static SQLiteTraceSession getSession(String str) {
        SQLiteTraceSession sQLiteTraceSession = mCurrentSessions.get(str);
        return (sQLiteTraceSession == null || !sQLiteTraceSession.isAlive()) ? startSession(str) : sQLiteTraceSession;
    }

    private static synchronized SQLiteTraceSession startSession(String str) {
        SQLiteTraceSession sQLiteTraceSession;
        synchronized (SQLiteTrace.class) {
            sQLiteTraceSession = mCurrentSessions.get(str);
            if (sQLiteTraceSession == null || !sQLiteTraceSession.isAlive()) {
                sQLiteTraceSession = new SQLiteTraceSession();
                sQLiteTraceSession.start(str);
                mCurrentSessions.put(str, sQLiteTraceSession);
            }
        }
        return sQLiteTraceSession;
    }

    public static class SQLiteTraceSession {
        private static final int OPERATION_EXPORT_THRESHOLD = 100;
        private static final int TIMEOUT = 100;
        private SQLiteTraceExporter mExporter;
        private ArrayList<TraceOperation> mOperations = new ArrayList<>();
        private AtomicBoolean mIsAlive = new AtomicBoolean(true);

        /* JADX INFO: Access modifiers changed from: private */
        public void start(final String str) {
            new Thread(new Runnable() { // from class: android.database.sqlite.trace.SQLiteTrace$SQLiteTraceSession$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SQLiteTrace.SQLiteTraceSession.this.lambda$start$0(str);
                }
            }).start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$start$0(String str) {
            try {
                try {
                    TraceConfiguration traceConfiguration = new TraceConfiguration(str);
                    this.mExporter = new SQLiteTraceJsonExporter(traceConfiguration);
                    String str2 = traceConfiguration.databaseName;
                    while (SQLiteTrace.isEnabled(str2)) {
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
            SQLiteTraceExporter sQLiteTraceExporter = this.mExporter;
            if (sQLiteTraceExporter != null) {
                try {
                    sQLiteTraceExporter.writeOperations(clearAndGetOperations());
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
            ArrayList<TraceOperation> arrayList;
            arrayList = this.mOperations;
            this.mOperations = new ArrayList<>();
            return arrayList;
        }
    }

    public static class TraceConfiguration {
        public final String databaseFilePath;
        public final String databaseName;
        public final String traceFilePath;

        public TraceConfiguration(String str) {
            this.traceFilePath = str + "-sqlite-trace-" + System.currentTimeMillis();
            this.databaseFilePath = new File(str).getAbsolutePath();
            this.databaseName = new File(str).getName();
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
                ArrayList<Object> arrayList = new ArrayList<>();
                Iterator<Object> it = operation.mBindArgs.iterator();
                while (it.hasNext()) {
                    Object next = it.next();
                    if (next instanceof byte[]) {
                        arrayList.add(((byte[]) next).clone());
                    } else {
                        arrayList.add(next);
                    }
                }
                this.bindArgs = arrayList;
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
