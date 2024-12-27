package com.android.systemui.plugins;

import com.android.systemui.util.concurrency.ThreadFactory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PluginsModule_ProvidesPluginExecutorFactory implements Provider {
    private final javax.inject.Provider threadFactoryProvider;

    public PluginsModule_ProvidesPluginExecutorFactory(javax.inject.Provider provider) {
        this.threadFactoryProvider = provider;
    }

    public static PluginsModule_ProvidesPluginExecutorFactory create(javax.inject.Provider provider) {
        return new PluginsModule_ProvidesPluginExecutorFactory(provider);
    }

    public static Executor providesPluginExecutor(ThreadFactory threadFactory) {
        Executor providesPluginExecutor = PluginsModule.providesPluginExecutor(threadFactory);
        Preconditions.checkNotNullFromProvides(providesPluginExecutor);
        return providesPluginExecutor;
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return providesPluginExecutor((ThreadFactory) this.threadFactoryProvider.get());
    }
}
