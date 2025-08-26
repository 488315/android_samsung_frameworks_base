package com.android.systemui.plugins;

import com.android.systemui.util.concurrency.ThreadFactory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class PluginsModule_ProvidesPluginExecutorFactory implements Provider {
    private final Provider threadFactoryProvider;

    public PluginsModule_ProvidesPluginExecutorFactory(Provider provider) {
        this.threadFactoryProvider = provider;
    }

    public static PluginsModule_ProvidesPluginExecutorFactory create(javax.inject.Provider provider) {
        return new PluginsModule_ProvidesPluginExecutorFactory(Providers.asDaggerProvider(provider));
    }

    public static Executor providesPluginExecutor(ThreadFactory threadFactory) {
        Executor executorProvidesPluginExecutor = PluginsModule.providesPluginExecutor(threadFactory);
        executorProvidesPluginExecutor.getClass();
        return executorProvidesPluginExecutor;
    }

    public static PluginsModule_ProvidesPluginExecutorFactory create(Provider provider) {
        return new PluginsModule_ProvidesPluginExecutorFactory(provider);
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return providesPluginExecutor((ThreadFactory) this.threadFactoryProvider.get());
    }
}
