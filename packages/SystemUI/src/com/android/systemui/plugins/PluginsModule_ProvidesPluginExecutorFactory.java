package com.android.systemui.plugins;

import com.android.systemui.util.concurrency.ThreadFactory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

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
