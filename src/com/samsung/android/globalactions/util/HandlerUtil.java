package com.samsung.android.globalactions.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* loaded from: classes6.dex */
public class HandlerUtil {
    Handler mHandler = new Handler(Looper.getMainLooper());

    public void post(Runnable runnable) {
        this.mHandler.post(runnable);
    }

    public void postDelayed(Runnable runnable, long j) {
        this.mHandler.postDelayed(runnable, j);
    }

    public void removeCallbacks(Runnable runnable) {
        this.mHandler.removeCallbacks(runnable);
    }

    public Message obtainMessage(int i, String str) {
        return this.mHandler.obtainMessage(i, str);
    }

    public void sendMessageDelayed(Message message, long j) {
        this.mHandler.sendMessageDelayed(message, j);
    }
}
