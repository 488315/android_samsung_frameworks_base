package com.android.systemui.wallpaper;

import android.os.Looper;
import android.util.Log;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WallpaperChangeObserver {
    public final Object mLock = new Object();
    public static final BlockingDeque sWaitingQueue = new LinkedBlockingDeque(1);
    public static int sState = -1;

    public final void await() {
        int i;
        synchronized (this.mLock) {
            i = sState;
        }
        if (i == 1) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                throw new IllegalThreadStateException();
            }
            try {
                Log.d("WallpaperChangeObserver", "await start");
                BlockingDeque blockingDeque = sWaitingQueue;
                Integer num = (Integer) ((LinkedBlockingDeque) blockingDeque).poll(3000L, TimeUnit.MILLISECONDS);
                synchronized (this.mLock) {
                    ((LinkedBlockingDeque) blockingDeque).put(Integer.valueOf(sState));
                }
                Log.d("WallpaperChangeObserver", "await done: " + num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public final void updateState(int i) {
        synchronized (this.mLock) {
            Log.d("WallpaperChangeObserver", "updateState: state: " + i);
            if (i == 2 || i == 3) {
                BlockingDeque blockingDeque = sWaitingQueue;
                if (blockingDeque.isEmpty()) {
                    try {
                        ((LinkedBlockingDeque) blockingDeque).put(Integer.valueOf(i));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sState = i;
                }
            }
            if (i == 1) {
                ((LinkedBlockingDeque) sWaitingQueue).clear();
            }
            sState = i;
        }
    }
}
