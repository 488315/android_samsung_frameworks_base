package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideNotifInflationLooperFactory implements Provider {

    final class InstanceHolder {
        static final SysUIConcurrencyModule_ProvideNotifInflationLooperFactory INSTANCE = new SysUIConcurrencyModule_ProvideNotifInflationLooperFactory();

        private InstanceHolder() {
        }
    }

    public static SysUIConcurrencyModule_ProvideNotifInflationLooperFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Looper provideNotifInflationLooper() {
        Looper looperProvideNotifInflationLooper = SysUIConcurrencyModule.INSTANCE.provideNotifInflationLooper();
        looperProvideNotifInflationLooper.getClass();
        return looperProvideNotifInflationLooper;
    }

    @Override // javax.inject.Provider
    public Looper get() {
        return provideNotifInflationLooper();
    }
}
