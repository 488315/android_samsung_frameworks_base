package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideBgLooperFactory implements Provider {

    final class InstanceHolder {
        static final SysUIConcurrencyModule_ProvideBgLooperFactory INSTANCE = new SysUIConcurrencyModule_ProvideBgLooperFactory();

        private InstanceHolder() {
        }
    }

    public static SysUIConcurrencyModule_ProvideBgLooperFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Looper provideBgLooper() {
        Looper looperProvideBgLooper = SysUIConcurrencyModule.INSTANCE.provideBgLooper();
        looperProvideBgLooper.getClass();
        return looperProvideBgLooper;
    }

    @Override // javax.inject.Provider
    public Looper get() {
        return provideBgLooper();
    }
}
