package com.android.wm.shell.dagger;

import android.os.Handler;
import android.os.HandlerThread;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellConcurrencyModule_ProvideSharedBackgroundHandlerFactory implements Provider {
    public static Handler provideSharedBackgroundHandler() {
        HandlerThread handlerThread = new HandlerThread("wmshell.background", 10);
        handlerThread.start();
        Handler threadHandler = handlerThread.getThreadHandler();
        Preconditions.checkNotNullFromProvides(threadHandler);
        return threadHandler;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideSharedBackgroundHandler();
    }
}
