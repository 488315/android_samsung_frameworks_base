package com.samsung.android.globalactions.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class HandlerUtil {
    Handler mHandler = new Handler(Looper.getMainLooper());

    public void post(Runnable r) {
        this.mHandler.post(r);
    }

    public void postDelayed(Runnable r, long delay) {
        this.mHandler.postDelayed(r, delay);
    }

    public void removeCallbacks(Runnable r) {
        this.mHandler.removeCallbacks(r);
    }

    public Message obtainMessage(int what, String sender) {
        return this.mHandler.obtainMessage(what, sender);
    }

    public void sendMessageDelayed(Message msg, long delayMillis) {
        this.mHandler.sendMessageDelayed(msg, delayMillis);
    }
}
