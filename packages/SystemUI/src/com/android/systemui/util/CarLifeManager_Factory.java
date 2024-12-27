package com.android.systemui.util;

import com.android.systemui.keyguard.DisplayLifecycle;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CarLifeManager_Factory implements Provider {
    private final javax.inject.Provider displayLifecycleProvider;

    public CarLifeManager_Factory(javax.inject.Provider provider) {
        this.displayLifecycleProvider = provider;
    }

    public static CarLifeManager_Factory create(javax.inject.Provider provider) {
        return new CarLifeManager_Factory(provider);
    }

    public static CarLifeManager newInstance(DisplayLifecycle displayLifecycle) {
        return new CarLifeManager(displayLifecycle);
    }

    @Override // javax.inject.Provider
    public CarLifeManager get() {
        return newInstance((DisplayLifecycle) this.displayLifecycleProvider.get());
    }
}
