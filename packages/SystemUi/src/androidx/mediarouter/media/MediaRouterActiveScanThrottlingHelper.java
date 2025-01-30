package androidx.mediarouter.media;

import android.os.Handler;
import android.os.Looper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaRouterActiveScanThrottlingHelper {
    public boolean mActiveScan;
    public long mCurrentTime;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public long mSuppressActiveScanTimeout;
    public final Runnable mUpdateDiscoveryRequestRunnable;

    public MediaRouterActiveScanThrottlingHelper(Runnable runnable) {
        this.mUpdateDiscoveryRequestRunnable = runnable;
    }
}
