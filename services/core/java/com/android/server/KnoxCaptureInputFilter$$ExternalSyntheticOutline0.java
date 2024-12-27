package com.android.server;

import android.os.HandlerThread;
import android.util.Slog;

public abstract /* synthetic */ class KnoxCaptureInputFilter$$ExternalSyntheticOutline0 {
    public static HandlerThread m(String str) {
        HandlerThread handlerThread = new HandlerThread(str);
        handlerThread.start();
        return handlerThread;
    }

    public static void m(Exception exc, String str, String str2) {
        Slog.d(str2, str + exc);
    }
}
