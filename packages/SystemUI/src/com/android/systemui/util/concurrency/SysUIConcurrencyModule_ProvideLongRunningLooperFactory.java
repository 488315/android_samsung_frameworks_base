package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideLongRunningLooperFactory implements Provider {

    final class InstanceHolder {
        static final SysUIConcurrencyModule_ProvideLongRunningLooperFactory INSTANCE = new SysUIConcurrencyModule_ProvideLongRunningLooperFactory();

        private InstanceHolder() {
        }
    }

    public static SysUIConcurrencyModule_ProvideLongRunningLooperFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Looper provideLongRunningLooper() {
        Looper looperProvideLongRunningLooper = SysUIConcurrencyModule.INSTANCE.provideLongRunningLooper();
        looperProvideLongRunningLooper.getClass();
        return looperProvideLongRunningLooper;
    }

    @Override // javax.inject.Provider
    public Looper get() {
        return provideLongRunningLooper();
    }
}
