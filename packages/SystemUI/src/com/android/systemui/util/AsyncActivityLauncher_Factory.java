package com.android.systemui.util;

import android.app.IActivityTaskManager;
import android.content.Context;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AsyncActivityLauncher_Factory implements Provider {
    private final javax.inject.Provider activityTaskManagerProvider;
    private final javax.inject.Provider backgroundExecutorProvider;
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider mainExecutorProvider;

    public AsyncActivityLauncher_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        this.contextProvider = provider;
        this.activityTaskManagerProvider = provider2;
        this.backgroundExecutorProvider = provider3;
        this.mainExecutorProvider = provider4;
    }

    public static AsyncActivityLauncher_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new AsyncActivityLauncher_Factory(provider, provider2, provider3, provider4);
    }

    public static AsyncActivityLauncher newInstance(Context context, IActivityTaskManager iActivityTaskManager, Executor executor, Executor executor2) {
        return new AsyncActivityLauncher(context, iActivityTaskManager, executor, executor2);
    }

    @Override // javax.inject.Provider
    public AsyncActivityLauncher get() {
        return newInstance((Context) this.contextProvider.get(), (IActivityTaskManager) this.activityTaskManagerProvider.get(), (Executor) this.backgroundExecutorProvider.get(), (Executor) this.mainExecutorProvider.get());
    }
}
