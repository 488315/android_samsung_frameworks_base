package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class SysUIConcurrencyModule_ProvideBgLooperFactory implements Provider {

    final class InstanceHolder {
        private static final SysUIConcurrencyModule_ProvideBgLooperFactory INSTANCE = new SysUIConcurrencyModule_ProvideBgLooperFactory();

        private InstanceHolder() {
        }
    }

    public static SysUIConcurrencyModule_ProvideBgLooperFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Looper provideBgLooper() {
        Looper provideBgLooper = SysUIConcurrencyModule.INSTANCE.provideBgLooper();
        Preconditions.checkNotNullFromProvides(provideBgLooper);
        return provideBgLooper;
    }

    @Override // javax.inject.Provider
    public Looper get() {
        return provideBgLooper();
    }
}
