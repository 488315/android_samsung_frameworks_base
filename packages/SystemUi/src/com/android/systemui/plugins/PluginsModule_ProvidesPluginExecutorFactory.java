package com.android.systemui.plugins;

import com.android.systemui.util.concurrency.ThreadFactory;
import dagger.internal.Preconditions;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginsModule_ProvidesPluginExecutorFactory implements Provider {
    private final Provider threadFactoryProvider;

    public PluginsModule_ProvidesPluginExecutorFactory(Provider provider) {
        this.threadFactoryProvider = provider;
    }

    public static PluginsModule_ProvidesPluginExecutorFactory create(Provider provider) {
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
