package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class GutsCoordinatorLogger_Factory implements Provider {
    private final Provider bufferProvider;

    public GutsCoordinatorLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static GutsCoordinatorLogger_Factory create(javax.inject.Provider provider) {
        return new GutsCoordinatorLogger_Factory(Providers.asDaggerProvider(provider));
    }

    public static GutsCoordinatorLogger newInstance(LogBuffer logBuffer) {
        return new GutsCoordinatorLogger(logBuffer);
    }

    public static GutsCoordinatorLogger_Factory create(Provider provider) {
        return new GutsCoordinatorLogger_Factory(provider);
    }

    @Override // javax.inject.Provider
    public GutsCoordinatorLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
