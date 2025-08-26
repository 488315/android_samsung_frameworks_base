package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class ShadeEventCoordinatorLogger_Factory implements Provider {
    private final Provider bufferProvider;

    public ShadeEventCoordinatorLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static ShadeEventCoordinatorLogger_Factory create(javax.inject.Provider provider) {
        return new ShadeEventCoordinatorLogger_Factory(Providers.asDaggerProvider(provider));
    }

    public static ShadeEventCoordinatorLogger newInstance(LogBuffer logBuffer) {
        return new ShadeEventCoordinatorLogger(logBuffer);
    }

    public static ShadeEventCoordinatorLogger_Factory create(Provider provider) {
        return new ShadeEventCoordinatorLogger_Factory(provider);
    }

    @Override // javax.inject.Provider
    public ShadeEventCoordinatorLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
