package com.android.systemui.plugins;

import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import com.android.systemui.shared.plugins.PluginActionManager;
import com.android.systemui.shared.plugins.PluginEnabler;
import com.android.systemui.shared.plugins.PluginInstance;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class PluginsModule_ProvidePluginInstanceManagerFactoryFactory implements Provider {
    private final Provider contextProvider;
    private final Provider mainExecutorProvider;
    private final Provider notificationManagerProvider;
    private final Provider packageManagerProvider;
    private final Provider pluginEnablerProvider;
    private final Provider pluginExecutorProvider;
    private final Provider pluginInstanceFactoryProvider;
    private final Provider privilegedPluginsProvider;

    public PluginsModule_ProvidePluginInstanceManagerFactoryFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
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
        return new PluginsModule_ProvidePluginInstanceManagerFactoryFactory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5), Providers.asDaggerProvider(provider6), Providers.asDaggerProvider(provider7), Providers.asDaggerProvider(provider8));
    }

    public static PluginActionManager.Factory providePluginInstanceManagerFactory(Context context, PackageManager packageManager, Executor executor, Executor executor2, NotificationManager notificationManager, PluginEnabler pluginEnabler, List<String> list, PluginInstance.Factory factory) {
        PluginActionManager.Factory factoryProvidePluginInstanceManagerFactory = PluginsModule.providePluginInstanceManagerFactory(context, packageManager, executor, executor2, notificationManager, pluginEnabler, list, factory);
        factoryProvidePluginInstanceManagerFactory.getClass();
        return factoryProvidePluginInstanceManagerFactory;
    }

    public static PluginsModule_ProvidePluginInstanceManagerFactoryFactory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        return new PluginsModule_ProvidePluginInstanceManagerFactoryFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    @Override // javax.inject.Provider
    public PluginActionManager.Factory get() {
        return providePluginInstanceManagerFactory((Context) this.contextProvider.get(), (PackageManager) this.packageManagerProvider.get(), (Executor) this.mainExecutorProvider.get(), (Executor) this.pluginExecutorProvider.get(), (NotificationManager) this.notificationManagerProvider.get(), (PluginEnabler) this.pluginEnablerProvider.get(), (List) this.privilegedPluginsProvider.get(), (PluginInstance.Factory) this.pluginInstanceFactoryProvider.get());
    }
}
