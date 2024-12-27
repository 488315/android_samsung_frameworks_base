package android.os;

import android.content.Context;
import android.util.Log;

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
            sService =
                    IUpdateLock.Stub.asInterface(
                            ServiceManager.getService(Context.UPDATE_LOCK_SERVICE));
        }
    }

    public UpdateLock(String tag) {
        this.mTag = tag;
    }

    public void setReferenceCounted(boolean isRefCounted) {
        this.mRefCounted = isRefCounted;
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
        if (sService != null) {
            try {
                sService.acquireUpdateLock(this.mToken, this.mTag);
            } catch (RemoteException e) {
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
            r3 = this;
            boolean r0 = r3.mRefCounted
            if (r0 == 0) goto Lc
            int r0 = r3.mCount
            int r0 = r0 + (-1)
            r3.mCount = r0
            if (r0 != 0) goto L23
        Lc:
            android.os.IUpdateLock r0 = android.os.UpdateLock.sService
            if (r0 == 0) goto L20
            android.os.IUpdateLock r0 = android.os.UpdateLock.sService     // Catch: android.os.RemoteException -> L18
            android.os.IBinder r1 = r3.mToken     // Catch: android.os.RemoteException -> L18
            r0.releaseUpdateLock(r1)     // Catch: android.os.RemoteException -> L18
            goto L20
        L18:
            r0 = move-exception
            java.lang.String r1 = "UpdateLock"
            java.lang.String r2 = "Unable to contact service to release"
            android.util.Log.e(r1, r2)
        L20:
            r0 = 0
            r3.mHeld = r0
        L23:
            int r0 = r3.mCount
            if (r0 < 0) goto L28
            return
        L28:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "UpdateLock under-locked"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.os.UpdateLock.releaseLocked():void");
    }

    protected void finalize() throws Throwable {
        synchronized (this.mToken) {
            if (this.mHeld) {
                Log.wtf(TAG, "UpdateLock finalized while still held");
                try {
                    sService.releaseUpdateLock(this.mToken);
                } catch (RemoteException e) {
                    Log.e(TAG, "Unable to contact service to release");
                }
            }
        }
    }
}
