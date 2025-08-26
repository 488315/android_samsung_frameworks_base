package com.android.systemui.aibrief.ui;

import android.content.Context;
import com.android.systemui.aibrief.log.BriefLogger;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes.dex */
public final class BriefViewController_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider keyguardStateControllerProvider;
    private final Provider loggerProvider;

    public BriefViewController_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.loggerProvider = provider2;
        this.keyguardStateControllerProvider = provider3;
    }

    public static BriefViewController_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new BriefViewController_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static BriefViewController newInstance(Context context, BriefLogger briefLogger, KeyguardStateController keyguardStateController) {
        return new BriefViewController(context, briefLogger, keyguardStateController);
    }

    public static BriefViewController_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new BriefViewController_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public BriefViewController get() {
        return newInstance((Context) this.contextProvider.get(), (BriefLogger) this.loggerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get());
    }
}
