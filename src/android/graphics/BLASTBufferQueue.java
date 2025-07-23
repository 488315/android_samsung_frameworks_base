package android.graphics;

import android.os.Debug;
import android.os.IBinder;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceControl;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class BLASTBufferQueue {
    private static final boolean DEBUG_MERGE;
    private static String TAG = "BLASTBufferQueue_Java";
    private String mName;
    public long mNativeObject;

    public interface TransactionHangCallback {
        void onTransactionHang(String str);
    }

    public interface WaitForBufferReleaseCallback {
        void onWaitForBufferRelease(long j);
    }

    private static native void nativeApplyPendingTransactions(long j, long j2);

    private static native void nativeClearSyncTransaction(long j);

    private static native long nativeCreate(String str, boolean z);

    private static native void nativeDestroy(long j);

    private static native SurfaceControl.Transaction nativeGatherPendingTransactions(long j, long j2);

    private static native long nativeGetLastAcquiredFrameNum(long j);

    private static native long nativeGetLastBufferConsumedTime(long j);

    private static native int nativeGetQueuedBufferCount(long j);

    private static native Surface nativeGetSurface(long j, boolean z);

    private static native long nativeGetTimeSpentPreviouslyWithoutBuffer(long j);

    private static native boolean nativeIsSameSurfaceControl(long j, long j2);

    private static native void nativeMergeWithNextTransaction(long j, long j2, long j3);

    private static native void nativeSetApplyToken(long j, IBinder iBinder);

    private static native void nativeSetFlingStbFlag(long j, boolean z);

    private static native void nativeSetTransactionHangCallback(long j, TransactionHangCallback transactionHangCallback);

    private static native void nativeSetWaitForBufferReleaseCallback(long j, WaitForBufferReleaseCallback waitForBufferReleaseCallback);

    private static native void nativeStopContinuousSyncTransaction(long j);

    private static native boolean nativeSyncNextTransaction(long j, Consumer<SurfaceControl.Transaction> consumer, boolean z);

    private static native void nativeUpdate(long j, long j2, long j3, long j4, int i);

    static {
        DEBUG_MERGE = SystemProperties.getInt("blastbuffer.debug.merge", 0) != 0;
    }

    public BLASTBufferQueue(String str, boolean z) {
        this.mNativeObject = nativeCreate(str, z);
        this.mName = TextUtils.isEmpty(str) ? "" : str;
        Log.i(TAG, "new BLASTBufferQueue, mName= " + this.mName + " mNativeObject= 0x" + Long.toHexString(this.mNativeObject) + " caller= " + Debug.getCallers(10));
    }

    public void destroy() {
        nativeDestroy(this.mNativeObject);
        this.mNativeObject = 0L;
    }

    public Surface createSurface() {
        return nativeGetSurface(this.mNativeObject, false);
    }

    public int getQueuedBufferCount() {
        return nativeGetQueuedBufferCount(this.mNativeObject);
    }

    public long getTimeSpentPreviouslyWithoutBuffer() {
        return nativeGetTimeSpentPreviouslyWithoutBuffer(this.mNativeObject);
    }

    public long getLastBufferConsumedTime() {
        return nativeGetLastBufferConsumedTime(this.mNativeObject);
    }

    public void setFlingStbFlag(boolean z) {
        nativeSetFlingStbFlag(this.mNativeObject, z);
    }

    public Surface createSurfaceWithHandle() {
        return nativeGetSurface(this.mNativeObject, true);
    }

    public boolean syncNextTransaction(boolean z, Consumer<SurfaceControl.Transaction> consumer) {
        Log.i(TAG, "syncNextTransaction, mName= " + this.mName + " acquireSingleBuffer= " + z + " mNativeObject= 0x" + Long.toHexString(this.mNativeObject) + " callback= " + consumer + " caller= " + Debug.getCallers(6));
        return nativeSyncNextTransaction(this.mNativeObject, consumer, z);
    }

    public boolean syncNextTransaction(Consumer<SurfaceControl.Transaction> consumer) {
        return syncNextTransaction(true, consumer);
    }

    public void stopContinuousSyncTransaction() {
        Log.i(TAG, "stopContinuousSyncTransaction, mName= " + this.mName + " mNativeObject= 0x" + Long.toHexString(this.mNativeObject) + " caller= " + Debug.getCallers(6));
        nativeStopContinuousSyncTransaction(this.mNativeObject);
    }

    public void clearSyncTransaction() {
        nativeClearSyncTransaction(this.mNativeObject);
    }

    public void update(SurfaceControl surfaceControl, int i, int i2, int i3) {
        String str;
        String str2 = TAG;
        StringBuilder sb = new StringBuilder("update, w= ");
        sb.append(i);
        sb.append(" h= ");
        sb.append(i2);
        sb.append(" mName = ");
        sb.append(this.mName);
        sb.append(" mNativeObject= 0x");
        sb.append(Long.toHexString(this.mNativeObject));
        sb.append(" sc.mNativeObject= ");
        if (surfaceControl != null) {
            str = "0x" + Long.toHexString(surfaceControl.mNativeObject);
        } else {
            str = "0";
        }
        sb.append(str);
        sb.append(" format= ");
        sb.append(i3);
        sb.append(" caller= ");
        sb.append(Debug.getCallers(6));
        Log.i(str2, sb.toString());
        nativeUpdate(this.mNativeObject, surfaceControl.mNativeObject, i, i2, i3);
    }

    protected void finalize() throws Throwable {
        try {
            long j = this.mNativeObject;
            if (j != 0) {
                nativeDestroy(j);
            }
        } finally {
            super.finalize();
        }
    }

    public void mergeWithNextTransaction(SurfaceControl.Transaction transaction, long j) {
        String str;
        if (DEBUG_MERGE) {
            String str2 = TAG;
            StringBuilder sb = new StringBuilder("mergeWithNextTransaction, mName= ");
            sb.append(this.mName);
            sb.append(" mNativeObject= 0x");
            sb.append(Long.toHexString(this.mNativeObject));
            sb.append(" t.mNativeObject= ");
            if (transaction != null) {
                str = "0x" + Long.toHexString(transaction.mNativeObject);
            } else {
                str = "0";
            }
            sb.append(str);
            sb.append(" frameNumber= ");
            sb.append(j);
            sb.append(" caller= ");
            sb.append(Debug.getCallers(6));
            Log.i(str2, sb.toString());
        }
        nativeMergeWithNextTransaction(this.mNativeObject, transaction.mNativeObject, j);
    }

    public void mergeWithNextTransaction(long j, long j2) {
        if (DEBUG_MERGE) {
            Log.i(TAG, "mergeWithNextTransaction, mName= " + this.mName + " mNativeObject= 0x" + Long.toHexString(this.mNativeObject) + " nativeTransaction= " + Long.toHexString(j) + " frameNumber= " + j2 + " caller= " + Debug.getCallers(6));
        }
        nativeMergeWithNextTransaction(this.mNativeObject, j, j2);
    }

    public void applyPendingTransactions(long j) {
        Log.i(TAG, "applyPendingTransactions, mName= " + this.mName + " mNativeObject= 0x" + Long.toHexString(this.mNativeObject) + " frameNumber= " + j + " caller= " + Debug.getCallers(6));
        nativeApplyPendingTransactions(this.mNativeObject, j);
    }

    public long getLastAcquiredFrameNum() {
        Log.i(TAG, "getLastAcquiredFrameNum, mName= " + this.mName + " mNativeObject= 0x" + Long.toHexString(this.mNativeObject) + " caller= " + Debug.getCallers(6));
        return nativeGetLastAcquiredFrameNum(this.mNativeObject);
    }

    public boolean isSameSurfaceControl(SurfaceControl surfaceControl) {
        return nativeIsSameSurfaceControl(this.mNativeObject, surfaceControl.mNativeObject);
    }

    public SurfaceControl.Transaction gatherPendingTransactions(long j) {
        Log.i(TAG, "gatherPendingTransactions, mName= " + this.mName + " mNativeObject= 0x" + Long.toHexString(this.mNativeObject) + " frameNumber= " + j + " caller= " + Debug.getCallers(6));
        return nativeGatherPendingTransactions(this.mNativeObject, j);
    }

    public void setTransactionHangCallback(TransactionHangCallback transactionHangCallback) {
        nativeSetTransactionHangCallback(this.mNativeObject, transactionHangCallback);
    }

    public void setApplyToken(IBinder iBinder) {
        nativeSetApplyToken(this.mNativeObject, iBinder);
    }

    public void setWaitForBufferReleaseCallback(WaitForBufferReleaseCallback waitForBufferReleaseCallback) {
        nativeSetWaitForBufferReleaseCallback(this.mNativeObject, waitForBufferReleaseCallback);
    }
}
