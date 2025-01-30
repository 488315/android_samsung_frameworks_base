package android.database.sqlite;

import android.util.Log;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public final class SQLiteWalBackgroundCheckpoint {
  private static final int CHECKPOINT_THRESHOLD = 600000;
  private static final long HUGE_WAL_SIZE_THRESHOLD = 104857600;
  private static final String TAG = "SQLiteWalBackgroundCheckpoint";
  private AtomicBoolean mIsCheckpointRunning = new AtomicBoolean(false);
  private long mLastCheckpointTime = -1;

  public void tryBackgroundCheckpoint(SQLiteDatabase db, File walFile) {
    long now = System.currentTimeMillis();
    long j = this.mLastCheckpointTime;
    if ((j > 0 && now - j < 600000)
        || walFile.length() <= HUGE_WAL_SIZE_THRESHOLD
        || !this.mIsCheckpointRunning.compareAndSet(false, true)) {
      return;
    }
    try {
      WalCheckpointExecutor thread = new WalCheckpointExecutor(db);
      thread.start();
    } catch (Exception e) {
      this.mIsCheckpointRunning.set(false);
    }
  }

  private final class WalCheckpointExecutor extends Thread {
    private static final String TAG = "WalCheckpointExecutor";
    private final SQLiteDatabase mDatabase;

    private WalCheckpointExecutor(SQLiteDatabase db) {
      this.mDatabase = db;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      SQLiteStatement statement;
      try {
        try {
          statement = new SQLiteStatement(this.mDatabase, "PRAGMA wal_checkpoint(ADAPTIVE)", null);
        } catch (Exception e) {
          if (SQLiteDebug.NoPreloadHolder.DEBUG_ENABLE) {
            Log.m94d(TAG, "Background Checkpoint has just been failed.");
            e.printStackTrace();
          }
        }
        try {
          statement.simpleQueryForLong();
          SQLiteWalBackgroundCheckpoint.this.mLastCheckpointTime = System.currentTimeMillis();
          statement.close();
        } catch (Throwable th) {
          try {
            statement.close();
          } catch (Throwable th2) {
            th.addSuppressed(th2);
          }
          throw th;
        }
      } finally {
        SQLiteWalBackgroundCheckpoint.this.mIsCheckpointRunning.set(false);
      }
    }
  }
}
