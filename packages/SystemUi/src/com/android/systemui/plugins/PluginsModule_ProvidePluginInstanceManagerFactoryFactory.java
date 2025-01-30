package com.android.systemui.plugins;

import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import com.android.systemui.shared.plugins.PluginActionManager;
import com.android.systemui.shared.plugins.PluginEnabler;
import com.android.systemui.shared.plugins.PluginInstance;
import dagger.internal.Preconditions;
import java.util.List;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public static PluginsModule_ProvidePluginInstanceManagerFactoryFactory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
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
