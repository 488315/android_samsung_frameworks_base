package com.android.systemui.aibrief.ui;

import android.content.Context;
import com.android.systemui.aibrief.log.BriefLogger;
import dagger.internal.Provider;

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
