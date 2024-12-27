package com.android.systemui.plugins;

import com.android.systemui.shared.plugins.PluginInstance;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PluginsModule_ProvidesPluginInstanceFactoryFactory implements Provider {
    private final javax.inject.Provider isDebugProvider;
    private final javax.inject.Provider privilegedPluginsProvider;

    public PluginsModule_ProvidesPluginInstanceFactoryFactory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.privilegedPluginsProvider = provider;
        this.isDebugProvider = provider2;
    }

    public static PluginsModule_ProvidesPluginInstanceFactoryFactory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new PluginsModule_ProvidesPluginInstanceFactoryFactory(provider, provider2);
    }

    public static PluginInstance.Factory providesPluginInstanceFactory(List<String> list, boolean z) {
        PluginInstance.Factory providesPluginInstanceFactory = PluginsModule.providesPluginInstanceFactory(list, z);
        Preconditions.checkNotNullFromProvides(providesPluginInstanceFactory);
        return providesPluginInstanceFactory;
    }

    @Override // javax.inject.Provider
    public PluginInstance.Factory get() {
        return providesPluginInstanceFactory((List) this.privilegedPluginsProvider.get(), ((Boolean) this.isDebugProvider.get()).booleanValue());
    }
}
