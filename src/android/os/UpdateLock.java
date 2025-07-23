package android.os;

import android.content.Context;
import android.os.IUpdateLock;
import android.util.Log;

/* loaded from: classes3.dex */
public class UpdateLock {
    private static final boolean DEBUG = false;
    public static final String NOW_IS_CONVENIENT = "nowisconvenient";
    private static final String TAG = "UpdateLock";
    public static final String TIMESTAMP = "timestamp";
    public static final String UPDATE_LOCK_CHANGED = "android.os.UpdateLock.UPDATE_LOCK_CHANGED";
    private static IUpdateLock sService;
    final String mTag;
    int mCount = 0;
    boolean mRefCounted = true;
    boolean mHeld = false;
    IBinder mToken = new Binder();

    private static void checkService() {
        if (sService == null) {
            sService = IUpdateLock.Stub.asInterface(ServiceManager.getService(Context.UPDATE_LOCK_SERVICE));
        }
    }

    public UpdateLock(String str) {
        this.mTag = str;
    }

    public void setReferenceCounted(boolean z) {
        this.mRefCounted = z;
    }

    public boolean isHeld() {
        boolean z;
        synchronized (this.mToken) {
            z = this.mHeld;
        }
        return z;
    }

    public void acquire() {
        checkService();
        synchronized (this.mToken) {
            acquireLocked();
        }
    }

    private void acquireLocked() {
        if (this.mRefCounted) {
            int i = this.mCount;
            this.mCount = i + 1;
            if (i != 0) {
                return;
            }
        }
        IUpdateLock iUpdateLock = sService;
        if (iUpdateLock != null) {
            try {
                iUpdateLock.acquireUpdateLock(this.mToken, this.mTag);
            } catch (RemoteException unused) {
                Log.e(TAG, "Unable to contact service to acquire");
            }
        }
        this.mHeld = true;
    }

    public void release() {
        checkService();
        synchronized (this.mToken) {
            releaseLocked();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x000a, code lost:
    
        if (r0 == 0) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void releaseLocked() {
        /*
            r2 = this;
            boolean r0 = r2.mRefCounted
            if (r0 == 0) goto Lc
            int r0 = r2.mCount
            int r0 = r0 + (-1)
            r2.mCount = r0
            if (r0 != 0) goto L20
        Lc:
            android.os.IUpdateLock r0 = android.os.UpdateLock.sService
            if (r0 == 0) goto L1d
            android.os.IBinder r1 = r2.mToken     // Catch: android.os.RemoteException -> L16
            r0.releaseUpdateLock(r1)     // Catch: android.os.RemoteException -> L16
            goto L1d
        L16:
            java.lang.String r0 = "UpdateLock"
            java.lang.String r1 = "Unable to contact service to release"
            android.util.Log.e(r0, r1)
        L1d:
            r0 = 0
            r2.mHeld = r0
        L20:
            int r2 = r2.mCount
            if (r2 < 0) goto L25
            return
        L25:
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            java.lang.String r0 = "UpdateLock under-locked"
            r2.<init>(r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.UpdateLock.releaseLocked():void");
    }

    protected void finalize() throws Throwable {
        synchronized (this.mToken) {
            if (this.mHeld) {
                Log.wtf(TAG, "UpdateLock finalized while still held");
                try {
                    sService.releaseUpdateLock(this.mToken);
                } catch (RemoteException unused) {
                    Log.e(TAG, "Unable to contact service to release");
                }
            }
        }
    }
}
