package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
