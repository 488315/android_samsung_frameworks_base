package android.database.sqlite;

import android.database.sqlite.SQLiteDebug;
import android.util.Log;
import com.samsung.android.lock.LsConstants;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public final class SQLiteWalBackgroundCheckpoint {
    private static final int CHECKPOINT_THRESHOLD = 600000;
    private static final long HUGE_WAL_SIZE_THRESHOLD = 104857600;
    private static final String TAG = "SQLiteWalBackgroundCheckpoint";
    private AtomicBoolean mIsCheckpointRunning = new AtomicBoolean(false);
    private long mLastCheckpointTime = -1;

    public void tryBackgroundCheckpoint(SQLiteDatabase sQLiteDatabase, File file) {
        long currentTimeMillis = System.currentTimeMillis();
        long j = this.mLastCheckpointTime;
        if ((j <= 0 || currentTimeMillis - j >= LsConstants.SKT_LOCKOUT_ATTEMPT_DEFAULT_TIMEOUT) && file.length() > HUGE_WAL_SIZE_THRESHOLD && this.mIsCheckpointRunning.compareAndSet(false, true)) {
            try {
                new WalCheckpointExecutor(sQLiteDatabase).start();
            } catch (Exception unused) {
                this.mIsCheckpointRunning.set(false);
            }
        }
    }

    private final class WalCheckpointExecutor extends Thread {
        private static final String TAG = "WalCheckpointExecutor";
        private final SQLiteDatabase mDatabase;

        private WalCheckpointExecutor(SQLiteDatabase sQLiteDatabase) {
            this.mDatabase = sQLiteDatabase;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                SQLiteStatement sQLiteStatement = new SQLiteStatement(this.mDatabase, "PRAGMA wal_checkpoint(ADAPTIVE)", null);
                try {
                    sQLiteStatement.simpleQueryForLong();
                    SQLiteWalBackgroundCheckpoint.this.mLastCheckpointTime = System.currentTimeMillis();
                    sQLiteStatement.close();
                } catch (Throwable th) {
                    try {
                        sQLiteStatement.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (Exception e) {
                if (SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE) {
                    Log.d(TAG, "Background Checkpoint has just been failed.");
                    e.printStackTrace();
                }
            } finally {
                SQLiteWalBackgroundCheckpoint.this.mIsCheckpointRunning.set(false);
            }
        }
    }
}
