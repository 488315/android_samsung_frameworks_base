package com.android.systemui.aibrief.ui;

import android.content.Context;
import com.android.systemui.aibrief.log.BriefLogger;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BriefViewController_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider loggerProvider;

    public BriefViewController_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.contextProvider = provider;
        this.loggerProvider = provider2;
    }

    public static BriefViewController_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new BriefViewController_Factory(provider, provider2);
    }

    public static BriefViewController newInstance(Context context, BriefLogger briefLogger) {
        return new BriefViewController(context, briefLogger);
    }

    @Override // javax.inject.Provider
    public BriefViewController get() {
        return newInstance((Context) this.contextProvider.get(), (BriefLogger) this.loggerProvider.get());
    }
}
