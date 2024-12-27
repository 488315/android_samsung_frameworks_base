package com.android.systemui.aibrief.log;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Provider;

public final class BriefLogger_Factory implements Provider {
    private final javax.inject.Provider logBufferProvider;

    public BriefLogger_Factory(javax.inject.Provider provider) {
        this.logBufferProvider = provider;
    }

    public static BriefLogger_Factory create(javax.inject.Provider provider) {
        return new BriefLogger_Factory(provider);
    }

    public static BriefLogger newInstance(LogBuffer logBuffer) {
        return new BriefLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public BriefLogger get() {
        return newInstance((LogBuffer) this.logBufferProvider.get());
    }
}
