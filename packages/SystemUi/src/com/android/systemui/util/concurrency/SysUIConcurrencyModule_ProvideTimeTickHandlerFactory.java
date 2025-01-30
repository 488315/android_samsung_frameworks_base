package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.HandlerThread;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SysUIConcurrencyModule_ProvideTimeTickHandlerFactory implements Provider {
    public static Handler provideTimeTickHandler() {
        Long l = SysUIConcurrencyModule.BG_SLOW_DISPATCH_THRESHOLD;
        HandlerThread handlerThread = new HandlerThread("TimeTick");
        handlerThread.start();
        return new Handler(handlerThread.getLooper());
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideTimeTickHandler();
    }
}
