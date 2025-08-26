package com.android.systemui.util;

import android.app.IActivityTaskManager;
import android.content.Context;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class AsyncActivityLauncher_Factory implements Provider {
    private final Provider activityTaskManagerProvider;
    private final Provider backgroundExecutorProvider;
    private final Provider contextProvider;
    private final Provider mainExecutorProvider;

    public AsyncActivityLauncher_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.contextProvider = provider;
        this.activityTaskManagerProvider = provider2;
        this.backgroundExecutorProvider = provider3;
        this.mainExecutorProvider = provider4;
    }

    public static AsyncActivityLauncher_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new AsyncActivityLauncher_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4));
    }

    public static AsyncActivityLauncher newInstance(Context context, IActivityTaskManager iActivityTaskManager, Executor executor, Executor executor2) {
        return new AsyncActivityLauncher(context, iActivityTaskManager, executor, executor2);
    }

    public static AsyncActivityLauncher_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new AsyncActivityLauncher_Factory(provider, provider2, provider3, provider4);
    }

    @Override // javax.inject.Provider
    public AsyncActivityLauncher get() {
        return newInstance((Context) this.contextProvider.get(), (IActivityTaskManager) this.activityTaskManagerProvider.get(), (Executor) this.backgroundExecutorProvider.get(), (Executor) this.mainExecutorProvider.get());
    }
}
