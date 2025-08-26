package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideBroadcastRunningLooperFactory implements Provider {

    final class InstanceHolder {
        static final SysUIConcurrencyModule_ProvideBroadcastRunningLooperFactory INSTANCE = new SysUIConcurrencyModule_ProvideBroadcastRunningLooperFactory();

        private InstanceHolder() {
        }
    }

    public static SysUIConcurrencyModule_ProvideBroadcastRunningLooperFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Looper provideBroadcastRunningLooper() {
        Looper looperProvideBroadcastRunningLooper = SysUIConcurrencyModule.INSTANCE.provideBroadcastRunningLooper();
        looperProvideBroadcastRunningLooper.getClass();
        return looperProvideBroadcastRunningLooper;
    }

    @Override // javax.inject.Provider
    public Looper get() {
        return provideBroadcastRunningLooper();
    }
}
