package android.webkit;

import android.content.Context;
import android.os.Handler;

@Deprecated
/* loaded from: classes4.dex */
abstract class WebSyncManager implements Runnable {
    protected static final String LOGTAG = "websync";
    protected WebViewDatabase mDataBase;
    protected Handler mHandler;

    protected void onSyncInit() {
    }

    public void resetSync() {
    }

    @Override // java.lang.Runnable
    public void run() {
    }

    public void startSync() {
    }

    public void stopSync() {
    }

    public void sync() {
    }

    abstract void syncFromRamToFlash();

    protected WebSyncManager(Context context, String str) {
    }

    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("doesn't implement Cloneable");
    }
}
