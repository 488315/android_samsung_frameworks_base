package com.android.server.location.provider;

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
