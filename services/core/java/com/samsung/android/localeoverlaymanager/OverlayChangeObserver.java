package com.samsung.android.localeoverlaymanager;

import android.util.Slog;

/* loaded from: classes2.dex */
public abstract class OverlayChangeObserver {
    public static final String TAG = "OverlayChangeObserver";
    public int mToken;
    public int mUserId;
    public final Object overlayLock = new Object();
    public boolean callbackCompleted = false;
    public Runnable mTimeoutRunnable = new Runnable() { // from class: com.samsung.android.localeoverlaymanager.OverlayChangeObserver.1
        @Override // java.lang.Runnable
        public void run() {
            OverlayChangeObserver overlayChangeObserver;
            boolean z;
            synchronized (OverlayChangeObserver.this.overlayLock) {
                Slog.e(OverlayChangeObserver.TAG, "Timeout in locale overlay installation.  callback done = " + OverlayChangeObserver.this.callbackCompleted);
                overlayChangeObserver = OverlayChangeObserver.this;
                if (overlayChangeObserver.callbackCompleted) {
                    z = false;
                } else {
                    z = true;
                    overlayChangeObserver.callbackCompleted = true;
                }
            }
            if (z) {
                overlayChangeObserver.onChangeCompleted(false, overlayChangeObserver.mToken);
            }
        }
    };

    public abstract void onChangeCompleted(boolean z, int i);

    public OverlayChangeObserver(int i, int i2) {
        this.mToken = i;
        this.mUserId = i2;
    }
}
