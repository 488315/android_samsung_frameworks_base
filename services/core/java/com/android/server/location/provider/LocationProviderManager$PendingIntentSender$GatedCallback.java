package com.android.server.location.provider;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class LocationProviderManager$PendingIntentSender$GatedCallback implements Runnable {
    public Runnable mCallback;
    public boolean mGate;
    public boolean mRun;

    @Override // java.lang.Runnable
    public final void run() {
        Runnable runnable;
        Runnable runnable2;
        synchronized (this) {
            try {
                this.mRun = true;
                runnable = null;
                if (this.mGate && (runnable2 = this.mCallback) != null) {
                    this.mCallback = null;
                    runnable = runnable2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (runnable != null) {
            runnable.run();
        }
    }
}
