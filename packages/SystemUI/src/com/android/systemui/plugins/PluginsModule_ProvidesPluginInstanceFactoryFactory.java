package com.android.systemui.plugins;

import com.android.systemui.shared.plugins.PluginInstance;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.List;

/* loaded from: classes2.dex */
public final class PluginsModule_ProvidesPluginInstanceFactoryFactory implements Provider {
    private final Provider isDebugProvider;
    private final Provider privilegedPluginsProvider;

    public PluginsModule_ProvidesPluginInstanceFactoryFactory(Provider provider, Provider provider2) {
        this.privilegedPluginsProvider = provider;
        this.isDebugProvider = provider2;
    }

    public static PluginsModule_ProvidesPluginInstanceFactoryFactory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new PluginsModule_ProvidesPluginInstanceFactoryFactory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static PluginInstance.Factory providesPluginInstanceFactory(List<String> list, boolean z) {
        PluginInstance.Factory factoryProvidesPluginInstanceFactory = PluginsModule.providesPluginInstanceFactory(list, z);
        factoryProvidesPluginInstanceFactory.getClass();
        return factoryProvidesPluginInstanceFactory;
    }

    public static PluginsModule_ProvidesPluginInstanceFactoryFactory create(Provider provider, Provider provider2) {
        return new PluginsModule_ProvidesPluginInstanceFactoryFactory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public PluginInstance.Factory get() {
        return providesPluginInstanceFactory((List) this.privilegedPluginsProvider.get(), ((Boolean) this.isDebugProvider.get()).booleanValue());
    }
}
