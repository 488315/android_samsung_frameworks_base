package com.android.systemui.aibrief.log;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
