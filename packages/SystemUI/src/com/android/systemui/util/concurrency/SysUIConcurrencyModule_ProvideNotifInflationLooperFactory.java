package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideNotifInflationLooperFactory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final SysUIConcurrencyModule_ProvideNotifInflationLooperFactory INSTANCE = new SysUIConcurrencyModule_ProvideNotifInflationLooperFactory();

        private InstanceHolder() {
        }
    }

    public static SysUIConcurrencyModule_ProvideNotifInflationLooperFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Looper provideNotifInflationLooper() {
        Looper provideNotifInflationLooper = SysUIConcurrencyModule.INSTANCE.provideNotifInflationLooper();
        provideNotifInflationLooper.getClass();
        return provideNotifInflationLooper;
    }

    @Override // javax.inject.Provider
    public Looper get() {
        return provideNotifInflationLooper();
    }
}
