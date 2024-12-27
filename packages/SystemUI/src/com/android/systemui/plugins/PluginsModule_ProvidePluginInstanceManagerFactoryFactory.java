package com.android.systemui.plugins;

import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import com.android.systemui.shared.plugins.PluginActionManager;
import com.android.systemui.shared.plugins.PluginEnabler;
import com.android.systemui.shared.plugins.PluginInstance;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.List;
import java.util.concurrent.Executor;

public final class PluginsModule_ProvidePluginInstanceManagerFactoryFactory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider mainExecutorProvider;
    private final javax.inject.Provider notificationManagerProvider;
    private final javax.inject.Provider packageManagerProvider;
    private final javax.inject.Provider pluginEnablerProvider;
    private final javax.inject.Provider pluginExecutorProvider;
    private final javax.inject.Provider pluginInstanceFactoryProvider;
    private final javax.inject.Provider privilegedPluginsProvider;

    public PluginsModule_ProvidePluginInstanceManagerFactoryFactory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8) {
        this.contextProvider = provider;
        this.packageManagerProvider = provider2;
        this.mainExecutorProvider = provider3;
        this.pluginExecutorProvider = provider4;
        this.notificationManagerProvider = provider5;
        this.pluginEnablerProvider = provider6;
        this.privilegedPluginsProvider = provider7;
        this.pluginInstanceFactoryProvider = provider8;
    }

    public static PluginsModule_ProvidePluginInstanceManagerFactoryFactory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8) {
        return new PluginsModule_ProvidePluginInstanceManagerFactoryFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static PluginActionManager.Factory providePluginInstanceManagerFactory(Context context, PackageManager packageManager, Executor executor, Executor executor2, NotificationManager notificationManager, PluginEnabler pluginEnabler, List<String> list, PluginInstance.Factory factory) {
        PluginActionManager.Factory providePluginInstanceManagerFactory = PluginsModule.providePluginInstanceManagerFactory(context, packageManager, executor, executor2, notificationManager, pluginEnabler, list, factory);
        Preconditions.checkNotNullFromProvides(providePluginInstanceManagerFactory);
        return providePluginInstanceManagerFactory;
    }

    @Override // javax.inject.Provider
    public PluginActionManager.Factory get() {
        return providePluginInstanceManagerFactory((Context) this.contextProvider.get(), (PackageManager) this.packageManagerProvider.get(), (Executor) this.mainExecutorProvider.get(), (Executor) this.pluginExecutorProvider.get(), (NotificationManager) this.notificationManagerProvider.get(), (PluginEnabler) this.pluginEnablerProvider.get(), (List) this.privilegedPluginsProvider.get(), (PluginInstance.Factory) this.pluginInstanceFactoryProvider.get());
    }
}
