package com.android.systemui.pluginlock.utils;

import android.content.Context;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DumpUtils_Factory implements Provider {
    private final Provider contextProvider;

    public DumpUtils_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static DumpUtils_Factory create(javax.inject.Provider provider) {
        return new DumpUtils_Factory(Providers.asDaggerProvider(provider));
    }

    public static DumpUtils newInstance(Context context) {
        return new DumpUtils(context);
    }

    public static DumpUtils_Factory create(Provider provider) {
        return new DumpUtils_Factory(provider);
    }

    @Override // javax.inject.Provider
    public DumpUtils get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
