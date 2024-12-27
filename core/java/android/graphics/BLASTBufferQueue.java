package android.graphics;

import android.os.Debug;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceControl;

import java.util.function.Consumer;

public final class BLASTBufferQueue {
    private static final boolean DEBUG_MERGE;
    private static String TAG = "BLASTBufferQueue_Java";
    private String mName;
    public long mNativeObject;

    public interface TransactionHangCallback {
        void onTransactionHang(String str);
    }

    private static native void nativeApplyPendingTransactions(long j, long j2);

    private static native void nativeClearSyncTransaction(long j);

    private static native long nativeCreate(String str, boolean z);

    private static native void nativeDestroy(long j);

    private static native SurfaceControl.Transaction nativeGatherPendingTransactions(
            long j, long j2);

    private static native long nativeGetLastAcquiredFrameNum(long j);

    private static native Surface nativeGetSurface(long j, boolean z);

    private static native boolean nativeIsSameSurfaceControl(long j, long j2);

    private static native void nativeMergeWithNextTransaction(long j, long j2, long j3);

    private static native void nativeSetTransactionHangCallback(
            long j, TransactionHangCallback transactionHangCallback);

    private static native void nativeStopContinuousSyncTransaction(long j);

    private static native boolean nativeSyncNextTransaction(
            long j, Consumer<SurfaceControl.Transaction> consumer, boolean z);

    private static native void nativeUpdate(long j, long j2, long j3, long j4, int i);

    static {
        DEBUG_MERGE = SystemProperties.getInt("blastbuffer.debug.merge", 0) != 0;
    }

    public BLASTBufferQueue(String name, SurfaceControl sc, int width, int height, int format) {
        this(name, true);
        this.mName = !TextUtils.isEmpty(name) ? name : "";
        Log.i(
                TAG,
                "new BLASTBufferQueue, mName= "
                        + this.mName
                        + " mNativeObject= 0x"
                        + Long.toHexString(this.mNativeObject)
                        + " sc.mNativeObject= "
                        + (sc != null ? "0x" + Long.toHexString(sc.mNativeObject) : "0")
                        + " caller= "
                        + Debug.getCallers(10));
        update(sc, width, height, format);
    }

    public BLASTBufferQueue(String name, boolean updateDestinationFrame) {
        this.mNativeObject = nativeCreate(name, updateDestinationFrame);
    }

    public void destroy() {
        nativeDestroy(this.mNativeObject);
        this.mNativeObject = 0L;
    }

    public Surface createSurface() {
        return nativeGetSurface(this.mNativeObject, false);
    }

    public Surface createSurfaceWithHandle() {
        return nativeGetSurface(this.mNativeObject, true);
    }

    public boolean syncNextTransaction(
            boolean acquireSingleBuffer, Consumer<SurfaceControl.Transaction> callback) {
        Log.i(
                TAG,
                "syncNextTransaction, mName= "
                        + this.mName
                        + " acquireSingleBuffer= "
                        + acquireSingleBuffer
                        + " mNativeObject= 0x"
                        + Long.toHexString(this.mNativeObject)
                        + " callback= "
                        + callback
                        + " caller= "
                        + Debug.getCallers(6));
        return nativeSyncNextTransaction(this.mNativeObject, callback, acquireSingleBuffer);
    }

    public boolean syncNextTransaction(Consumer<SurfaceControl.Transaction> callback) {
        return syncNextTransaction(true, callback);
    }

    public void stopContinuousSyncTransaction() {
        Log.i(
                TAG,
                "stopContinuousSyncTransaction, mName= "
                        + this.mName
                        + " mNativeObject= 0x"
                        + Long.toHexString(this.mNativeObject)
                        + " caller= "
                        + Debug.getCallers(6));
        nativeStopContinuousSyncTransaction(this.mNativeObject);
    }

    public void clearSyncTransaction() {
        nativeClearSyncTransaction(this.mNativeObject);
    }

    public void update(SurfaceControl sc, int width, int height, int format) {
        Log.i(
                TAG,
                "update, w= "
                        + width
                        + " h= "
                        + height
                        + " mName = "
                        + this.mName
                        + " mNativeObject= 0x"
                        + Long.toHexString(this.mNativeObject)
                        + " sc.mNativeObject= "
                        + (sc != null ? "0x" + Long.toHexString(sc.mNativeObject) : "0")
                        + " format= "
                        + format
                        + " caller= "
                        + Debug.getCallers(6));
        nativeUpdate(this.mNativeObject, sc.mNativeObject, width, height, format);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mNativeObject != 0) {
                nativeDestroy(this.mNativeObject);
            }
        } finally {
            super.finalize();
        }
    }

    public void mergeWithNextTransaction(SurfaceControl.Transaction t, long frameNumber) {
        if (DEBUG_MERGE) {
            Log.i(
                    TAG,
                    "mergeWithNextTransaction, mName= "
                            + this.mName
                            + " mNativeObject= 0x"
                            + Long.toHexString(this.mNativeObject)
                            + " t.mNativeObject= "
                            + (t != null ? "0x" + Long.toHexString(t.mNativeObject) : "0")
                            + " frameNumber= "
                            + frameNumber
                            + " caller= "
                            + Debug.getCallers(6));
        }
        nativeMergeWithNextTransaction(this.mNativeObject, t.mNativeObject, frameNumber);
    }

    public void mergeWithNextTransaction(long nativeTransaction, long frameNumber) {
        if (DEBUG_MERGE) {
            Log.i(
                    TAG,
                    "mergeWithNextTransaction, mName= "
                            + this.mName
                            + " mNativeObject= 0x"
                            + Long.toHexString(this.mNativeObject)
                            + " nativeTransaction= "
                            + Long.toHexString(nativeTransaction)
                            + " frameNumber= "
                            + frameNumber
                            + " caller= "
                            + Debug.getCallers(6));
        }
        nativeMergeWithNextTransaction(this.mNativeObject, nativeTransaction, frameNumber);
    }

    public void applyPendingTransactions(long frameNumber) {
        Log.i(
                TAG,
                "applyPendingTransactions, mName= "
                        + this.mName
                        + " mNativeObject= 0x"
                        + Long.toHexString(this.mNativeObject)
                        + " frameNumber= "
                        + frameNumber
                        + " caller= "
                        + Debug.getCallers(6));
        nativeApplyPendingTransactions(this.mNativeObject, frameNumber);
    }

    public long getLastAcquiredFrameNum() {
        Log.i(
                TAG,
                "getLastAcquiredFrameNum, mName= "
                        + this.mName
                        + " mNativeObject= 0x"
                        + Long.toHexString(this.mNativeObject)
                        + " caller= "
                        + Debug.getCallers(6));
        return nativeGetLastAcquiredFrameNum(this.mNativeObject);
    }

    public boolean isSameSurfaceControl(SurfaceControl sc) {
        return nativeIsSameSurfaceControl(this.mNativeObject, sc.mNativeObject);
    }

    public SurfaceControl.Transaction gatherPendingTransactions(long frameNumber) {
        Log.i(
                TAG,
                "gatherPendingTransactions, mName= "
                        + this.mName
                        + " mNativeObject= 0x"
                        + Long.toHexString(this.mNativeObject)
                        + " frameNumber= "
                        + frameNumber
                        + " caller= "
                        + Debug.getCallers(6));
        return nativeGatherPendingTransactions(this.mNativeObject, frameNumber);
    }

    public void setTransactionHangCallback(TransactionHangCallback hangCallback) {
        nativeSetTransactionHangCallback(this.mNativeObject, hangCallback);
    }
}
