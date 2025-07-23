package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LockScreenMinimalismCoordinatorLogger_Factory implements Provider {
    private final Provider bufferProvider;

    public LockScreenMinimalismCoordinatorLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static LockScreenMinimalismCoordinatorLogger_Factory create(javax.inject.Provider provider) {
        return new LockScreenMinimalismCoordinatorLogger_Factory(Providers.asDaggerProvider(provider));
    }

    public static LockScreenMinimalismCoordinatorLogger newInstance(LogBuffer logBuffer) {
        return new LockScreenMinimalismCoordinatorLogger(logBuffer);
    }

    public static LockScreenMinimalismCoordinatorLogger_Factory create(Provider provider) {
        return new LockScreenMinimalismCoordinatorLogger_Factory(provider);
    }

    @Override // javax.inject.Provider
    public LockScreenMinimalismCoordinatorLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
