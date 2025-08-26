package com.android.systemui.plugins;

import android.content.Context;
import com.android.systemui.shared.plugins.PluginActionManager;
import com.android.systemui.shared.plugins.PluginEnabler;
import com.android.systemui.shared.plugins.PluginInstance;
import com.android.systemui.shared.plugins.PluginPrefs;
import com.android.systemui.shared.system.UncaughtExceptionPreHandlerManager;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.List;

/* loaded from: classes2.dex */
public final class PluginsModule_ProvidesPluginManagerFactory implements Provider {
    private final Provider contextProvider;
    private final Provider debugProvider;
    private final Provider instanceManagerFactoryProvider;
    private final Provider pluginEnablerProvider;
    private final Provider pluginInstanceFactoryProvider;
    private final Provider pluginPrefsProvider;
    private final Provider preHandlerManagerProvider;
    private final Provider privilegedPluginsProvider;

    public PluginsModule_ProvidesPluginManagerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.contextProvider = provider;
        this.instanceManagerFactoryProvider = provider2;
        this.debugProvider = provider3;
        this.preHandlerManagerProvider = provider4;
        this.pluginEnablerProvider = provider5;
        this.pluginPrefsProvider = provider6;
        this.privilegedPluginsProvider = provider7;
        this.pluginInstanceFactoryProvider = provider8;
    }

    public static PluginsModule_ProvidesPluginManagerFactory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8) {
        return new PluginsModule_ProvidesPluginManagerFactory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5), Providers.asDaggerProvider(provider6), Providers.asDaggerProvider(provider7), Providers.asDaggerProvider(provider8));
    }

    public static PluginManager providesPluginManager(Context context, PluginActionManager.Factory factory, boolean z, UncaughtExceptionPreHandlerManager uncaughtExceptionPreHandlerManager, PluginEnabler pluginEnabler, PluginPrefs pluginPrefs, List<String> list, PluginInstance.Factory factory2) {
        PluginManager pluginManagerProvidesPluginManager = PluginsModule.providesPluginManager(context, factory, z, uncaughtExceptionPreHandlerManager, pluginEnabler, pluginPrefs, list, factory2);
        pluginManagerProvidesPluginManager.getClass();
        return pluginManagerProvidesPluginManager;
    }

    public static PluginsModule_ProvidesPluginManagerFactory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        return new PluginsModule_ProvidesPluginManagerFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    @Override // javax.inject.Provider
    public PluginManager get() {
        return providesPluginManager((Context) this.contextProvider.get(), (PluginActionManager.Factory) this.instanceManagerFactoryProvider.get(), ((Boolean) this.debugProvider.get()).booleanValue(), (UncaughtExceptionPreHandlerManager) this.preHandlerManagerProvider.get(), (PluginEnabler) this.pluginEnablerProvider.get(), (PluginPrefs) this.pluginPrefsProvider.get(), (List) this.privilegedPluginsProvider.get(), (PluginInstance.Factory) this.pluginInstanceFactoryProvider.get());
    }
}
