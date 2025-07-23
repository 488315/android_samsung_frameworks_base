package com.android.systemui.dagger;

import android.content.Context;
import com.android.systemui.searcle.SearcleManager;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SamsungServicesModule_ProvideSearcleManagerFactory implements Provider {
    public final Provider contextProvider;

    public SamsungServicesModule_ProvideSearcleManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static SearcleManager provideSearcleManager(Context context) {
        return new SearcleManager(context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new SearcleManager((Context) this.contextProvider.get());
    }
}
