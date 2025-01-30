package com.android.systemui.util.concurrency;

import android.os.HandlerThread;
import android.os.Looper;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SysUIConcurrencyModule_ProvideBroadcastRunningLooperFactory implements Provider {
    public static Looper provideBroadcastRunningLooper() {
        Long l = SysUIConcurrencyModule.BG_SLOW_DISPATCH_THRESHOLD;
        HandlerThread handlerThread = new HandlerThread("BroadcastRunning", 10);
        handlerThread.start();
        handlerThread.getLooper().setSlowLogThresholdMs(SysUIConcurrencyModule.BROADCAST_SLOW_DISPATCH_THRESHOLD.longValue(), SysUIConcurrencyModule.BROADCAST_SLOW_DELIVERY_THRESHOLD.longValue());
        Looper looper = handlerThread.getLooper();
        Preconditions.checkNotNullFromProvides(looper);
        return looper;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideBroadcastRunningLooper();
    }
}
