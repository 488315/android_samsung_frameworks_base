package com.android.server;

import android.os.HandlerThread;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
