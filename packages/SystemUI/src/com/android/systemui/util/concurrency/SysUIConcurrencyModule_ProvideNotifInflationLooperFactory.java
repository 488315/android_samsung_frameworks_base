package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class SysUIConcurrencyModule_ProvideNotifInflationLooperFactory implements Provider {
    private final javax.inject.Provider bgLooperProvider;

    public SysUIConcurrencyModule_ProvideNotifInflationLooperFactory(javax.inject.Provider provider) {
        this.bgLooperProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvideNotifInflationLooperFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvideNotifInflationLooperFactory(provider);
    }

    public static Looper provideNotifInflationLooper(Looper looper) {
        Looper provideNotifInflationLooper = SysUIConcurrencyModule.INSTANCE.provideNotifInflationLooper(looper);
        Preconditions.checkNotNullFromProvides(provideNotifInflationLooper);
        return provideNotifInflationLooper;
    }

    @Override // javax.inject.Provider
    public Looper get() {
        return provideNotifInflationLooper((Looper) this.bgLooperProvider.get());
    }
}
