package com.android.internal.util;

import android.os.SystemClock;
import android.util.SparseBooleanArray;
import android.util.SparseLongArray;
import java.io.PrintWriter;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
public class ProviderAccessStats {
    private final Object mLock = new Object();
    private final long mStartUptime = SystemClock.uptimeMillis();
    private final SparseBooleanArray mAllCallingUids = new SparseBooleanArray();
    private final SparseLongArray mQueryStats = new SparseLongArray(16);
    private final SparseLongArray mBatchStats = new SparseLongArray(0);
    private final SparseLongArray mInsertStats = new SparseLongArray(0);
    private final SparseLongArray mUpdateStats = new SparseLongArray(0);
    private final SparseLongArray mDeleteStats = new SparseLongArray(0);
    private final SparseLongArray mInsertInBatchStats = new SparseLongArray(0);
    private final SparseLongArray mUpdateInBatchStats = new SparseLongArray(0);
    private final SparseLongArray mDeleteInBatchStats = new SparseLongArray(0);
    private final SparseLongArray mOperationDurationMillis = new SparseLongArray(16);
    private final ThreadLocal<PerThreadData> mThreadLocal = ThreadLocal.withInitial(new Supplier() { // from class: com.android.internal.util.ProviderAccessStats$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return ProviderAccessStats.lambda$new$0();
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    static class PerThreadData {
        public int nestCount;
        public long startUptimeMillis;

        private PerThreadData() {
        }
    }

    static /* synthetic */ PerThreadData lambda$new$0() {
        return new PerThreadData();
    }

    private void incrementStats(int i, SparseLongArray sparseLongArray) {
        synchronized (this.mLock) {
            sparseLongArray.put(i, sparseLongArray.get(i) + 1);
            this.mAllCallingUids.put(i, true);
        }
        PerThreadData perThreadData = this.mThreadLocal.get();
        perThreadData.nestCount++;
        if (perThreadData.nestCount == 1) {
            perThreadData.startUptimeMillis = SystemClock.uptimeMillis();
        }
    }

    private void incrementStats(int i, boolean z, SparseLongArray sparseLongArray, SparseLongArray sparseLongArray2) {
        if (z) {
            sparseLongArray = sparseLongArray2;
        }
        incrementStats(i, sparseLongArray);
    }

    public final void incrementInsertStats(int i, boolean z) {
        incrementStats(i, z, this.mInsertStats, this.mInsertInBatchStats);
    }

    public final void incrementUpdateStats(int i, boolean z) {
        incrementStats(i, z, this.mUpdateStats, this.mUpdateInBatchStats);
    }

    public final void incrementDeleteStats(int i, boolean z) {
        incrementStats(i, z, this.mDeleteStats, this.mDeleteInBatchStats);
    }

    public final void incrementQueryStats(int i) {
        incrementStats(i, this.mQueryStats);
    }

    public final void incrementBatchStats(int i) {
        incrementStats(i, this.mBatchStats);
    }

    public void finishOperation(int i) {
        PerThreadData perThreadData = this.mThreadLocal.get();
        perThreadData.nestCount--;
        if (perThreadData.nestCount == 0) {
            long jMax = Math.max(1L, SystemClock.uptimeMillis() - perThreadData.startUptimeMillis);
            synchronized (this.mLock) {
                SparseLongArray sparseLongArray = this.mOperationDurationMillis;
                sparseLongArray.put(i, sparseLongArray.get(i) + jMax);
            }
        }
    }

    public void dump(PrintWriter printWriter, String str) {
        synchronized (this.mLock) {
            printWriter.print("  Process uptime: ");
            printWriter.print((SystemClock.uptimeMillis() - this.mStartUptime) / 60000);
            printWriter.println(" minutes");
            printWriter.println();
            printWriter.print(str);
            printWriter.println("Client activities:");
            printWriter.print(str);
            printWriter.println("  UID        Query  Insert Update Delete   Batch Insert Update Delete          Sec");
            for (int i = 0; i < this.mAllCallingUids.size(); i++) {
                int iKeyAt = this.mAllCallingUids.keyAt(i);
                printWriter.print(str);
                printWriter.println(String.format("  %-9d %6d  %6d %6d %6d  %6d %6d %6d %6d %12.3f", Integer.valueOf(iKeyAt), Long.valueOf(this.mQueryStats.get(iKeyAt)), Long.valueOf(this.mInsertStats.get(iKeyAt)), Long.valueOf(this.mUpdateStats.get(iKeyAt)), Long.valueOf(this.mDeleteStats.get(iKeyAt)), Long.valueOf(this.mBatchStats.get(iKeyAt)), Long.valueOf(this.mInsertInBatchStats.get(iKeyAt)), Long.valueOf(this.mUpdateInBatchStats.get(iKeyAt)), Long.valueOf(this.mDeleteInBatchStats.get(iKeyAt)), Double.valueOf(this.mOperationDurationMillis.get(iKeyAt) / 1000.0d)));
            }
            printWriter.println();
        }
    }
}
