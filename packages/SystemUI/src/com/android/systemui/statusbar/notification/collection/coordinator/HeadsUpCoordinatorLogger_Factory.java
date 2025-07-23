package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HeadsUpCoordinatorLogger_Factory implements Provider {
    private final Provider bufferProvider;

    public HeadsUpCoordinatorLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static HeadsUpCoordinatorLogger_Factory create(javax.inject.Provider provider) {
        return new HeadsUpCoordinatorLogger_Factory(Providers.asDaggerProvider(provider));
    }

    public static HeadsUpCoordinatorLogger newInstance(LogBuffer logBuffer) {
        return new HeadsUpCoordinatorLogger(logBuffer);
    }

    public static HeadsUpCoordinatorLogger_Factory create(Provider provider) {
        return new HeadsUpCoordinatorLogger_Factory(provider);
    }

    @Override // javax.inject.Provider
    public HeadsUpCoordinatorLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
