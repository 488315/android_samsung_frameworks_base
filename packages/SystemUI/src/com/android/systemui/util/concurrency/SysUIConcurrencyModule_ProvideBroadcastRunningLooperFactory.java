package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideBroadcastRunningLooperFactory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final SysUIConcurrencyModule_ProvideBroadcastRunningLooperFactory INSTANCE = new SysUIConcurrencyModule_ProvideBroadcastRunningLooperFactory();

        private InstanceHolder() {
        }
    }

    public static SysUIConcurrencyModule_ProvideBroadcastRunningLooperFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Looper provideBroadcastRunningLooper() {
        Looper provideBroadcastRunningLooper = SysUIConcurrencyModule.INSTANCE.provideBroadcastRunningLooper();
        provideBroadcastRunningLooper.getClass();
        return provideBroadcastRunningLooper;
    }

    @Override // javax.inject.Provider
    public Looper get() {
        return provideBroadcastRunningLooper();
    }
}
