package com.samsung.android.localeoverlaymanager;

import android.util.Slog;

public abstract class OverlayChangeObserver {
    public final int mToken;
    public final int mUserId;
    public final Object overlayLock = new Object();
    public boolean callbackCompleted = false;
    public final AnonymousClass1 mTimeoutRunnable =
            new Runnable() { // from class:
                             // com.samsung.android.localeoverlaymanager.OverlayChangeObserver.1
                @Override // java.lang.Runnable
                public final void run() {
                    OverlayChangeObserver overlayChangeObserver;
                    boolean z;
                    synchronized (OverlayChangeObserver.this.overlayLock) {
                        try {
                            Slog.e(
                                    "OverlayChangeObserver",
                                    "Timeout in locale overlay installation.  callback done = "
                                            + OverlayChangeObserver.this.callbackCompleted);
                            overlayChangeObserver = OverlayChangeObserver.this;
                            if (overlayChangeObserver.callbackCompleted) {
                                z = false;
                            } else {
                                z = true;
                                overlayChangeObserver.callbackCompleted = true;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    if (z) {
                        overlayChangeObserver.onChangeCompleted(overlayChangeObserver.mToken);
                    }
                }
            };

    public OverlayChangeObserver(int i, int i2) {
        this.mToken = i;
        this.mUserId = i2;
    }

    public abstract void onChangeCompleted(int i);
}
