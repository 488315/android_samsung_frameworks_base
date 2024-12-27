package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class SysUIConcurrencyModule_ProvideBroadcastRunningLooperFactory implements Provider {

    final class InstanceHolder {
        private static final SysUIConcurrencyModule_ProvideBroadcastRunningLooperFactory INSTANCE = new SysUIConcurrencyModule_ProvideBroadcastRunningLooperFactory();

        private InstanceHolder() {
        }
    }

    public static SysUIConcurrencyModule_ProvideBroadcastRunningLooperFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Looper provideBroadcastRunningLooper() {
        Looper provideBroadcastRunningLooper = SysUIConcurrencyModule.INSTANCE.provideBroadcastRunningLooper();
        Preconditions.checkNotNullFromProvides(provideBroadcastRunningLooper);
        return provideBroadcastRunningLooper;
    }

    @Override // javax.inject.Provider
    public Looper get() {
        return provideBroadcastRunningLooper();
    }
}
