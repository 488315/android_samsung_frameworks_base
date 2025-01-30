package com.samsung.android.server.audio;

import android.os.Debug;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Log;

/* loaded from: classes2.dex */
public abstract class AudioExecutor {
    public static HandlerExecutor sExecutor;

    public static synchronized void init() {
        synchronized (AudioExecutor.class) {
            if (sExecutor == null) {
                HandlerThread handlerThread = new HandlerThread("AS.AudioExecutor");
                handlerThread.start();
                sExecutor = new HandlerExecutor(handlerThread.getThreadHandler());
            }
        }
    }

    public static void execute(final Runnable runnable) {
        init();
        final String caller = Debug.getCaller();
        sExecutor.execute(new Runnable() { // from class: com.samsung.android.server.audio.AudioExecutor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AudioExecutor.lambda$execute$0(runnable, caller);
            }
        });
    }

    public static /* synthetic */ void lambda$execute$0(Runnable runnable, String str) {
        long uptimeMillis = SystemClock.uptimeMillis();
        runnable.run();
        long uptimeMillis2 = SystemClock.uptimeMillis() - uptimeMillis;
        if (uptimeMillis2 > 100) {
            Log.w("AS.AudioExecutor", "Slow " + uptimeMillis2 + " in " + str);
        }
    }
}
