package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class SysUIConcurrencyModule_ProvideLongRunningLooperFactory implements Provider {

    final class InstanceHolder {
        private static final SysUIConcurrencyModule_ProvideLongRunningLooperFactory INSTANCE = new SysUIConcurrencyModule_ProvideLongRunningLooperFactory();

        private InstanceHolder() {
        }
    }

    public static SysUIConcurrencyModule_ProvideLongRunningLooperFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Looper provideLongRunningLooper() {
        Looper provideLongRunningLooper = SysUIConcurrencyModule.INSTANCE.provideLongRunningLooper();
        Preconditions.checkNotNullFromProvides(provideLongRunningLooper);
        return provideLongRunningLooper;
    }

    @Override // javax.inject.Provider
    public Looper get() {
        return provideLongRunningLooper();
    }
}
