package com.android.systemui.util;

import com.android.systemui.keyguard.DisplayLifecycle;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CarLifeManager_Factory implements Provider {
    private final Provider displayLifecycleProvider;

    public CarLifeManager_Factory(Provider provider) {
        this.displayLifecycleProvider = provider;
    }

    public static CarLifeManager_Factory create(javax.inject.Provider provider) {
        return new CarLifeManager_Factory(Providers.asDaggerProvider(provider));
    }

    public static CarLifeManager newInstance(DisplayLifecycle displayLifecycle) {
        return new CarLifeManager(displayLifecycle);
    }

    public static CarLifeManager_Factory create(Provider provider) {
        return new CarLifeManager_Factory(provider);
    }

    @Override // javax.inject.Provider
    public CarLifeManager get() {
        return newInstance((DisplayLifecycle) this.displayLifecycleProvider.get());
    }
}
