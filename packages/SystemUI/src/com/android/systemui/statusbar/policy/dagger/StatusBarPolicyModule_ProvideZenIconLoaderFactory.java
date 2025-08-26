package com.android.systemui.statusbar.policy.dagger;

import com.android.settingslib.notification.modes.ZenIconLoader;
import dagger.internal.Provider;
import java.util.concurrent.ExecutorService;

/* loaded from: classes3.dex */
public final class StatusBarPolicyModule_ProvideZenIconLoaderFactory implements Provider {
    public final Provider backgroundExecutorServiceProvider;

    public StatusBarPolicyModule_ProvideZenIconLoaderFactory(Provider provider) {
        this.backgroundExecutorServiceProvider = provider;
    }

    public static ZenIconLoader provideZenIconLoader(ExecutorService executorService) {
        return new ZenIconLoader(executorService);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ZenIconLoader((ExecutorService) this.backgroundExecutorServiceProvider.get());
    }
}
