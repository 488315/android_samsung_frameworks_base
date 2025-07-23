package com.android.systemui.aibrief.log;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BriefLogger_Factory implements Provider {
    private final Provider logBufferProvider;

    public BriefLogger_Factory(Provider provider) {
        this.logBufferProvider = provider;
    }

    public static BriefLogger_Factory create(javax.inject.Provider provider) {
        return new BriefLogger_Factory(Providers.asDaggerProvider(provider));
    }

    public static BriefLogger newInstance(LogBuffer logBuffer) {
        return new BriefLogger(logBuffer);
    }

    public static BriefLogger_Factory create(Provider provider) {
        return new BriefLogger_Factory(provider);
    }

    @Override // javax.inject.Provider
    public BriefLogger get() {
        return newInstance((LogBuffer) this.logBufferProvider.get());
    }
}
