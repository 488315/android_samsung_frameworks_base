package com.samsung.android.server.audio;

import android.os.Debug;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Log;

public abstract class AudioExecutor {
    public static HandlerExecutor sExecutor;

    public static void execute(final Runnable runnable) {
        synchronized (AudioExecutor.class) {
            if (sExecutor == null) {
                HandlerThread handlerThread = new HandlerThread("AS.AudioExecutor");
                handlerThread.start();
                sExecutor = new HandlerExecutor(handlerThread.getThreadHandler());
            }
        }
        final String caller = Debug.getCaller();
        sExecutor.execute(
                new Runnable() { // from class:
                                 // com.samsung.android.server.audio.AudioExecutor$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        Runnable runnable2 = runnable;
                        String str = caller;
                        long uptimeMillis = SystemClock.uptimeMillis();
                        runnable2.run();
                        long uptimeMillis2 = SystemClock.uptimeMillis() - uptimeMillis;
                        if (uptimeMillis2 > 100) {
                            Log.w("AS.AudioExecutor", "Slow " + uptimeMillis2 + " in " + str);
                        }
                    }
                });
    }
}
