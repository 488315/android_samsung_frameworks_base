package com.android.systemui.plugins;

import com.android.systemui.shared.plugins.PluginInstance;
import dagger.internal.Preconditions;
import java.util.List;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginsModule_ProvidesPluginInstanceFactoryFactory implements Provider {
    private final Provider isDebugProvider;
    private final Provider privilegedPluginsProvider;

    public PluginsModule_ProvidesPluginInstanceFactoryFactory(Provider provider, Provider provider2) {
        this.privilegedPluginsProvider = provider;
        this.isDebugProvider = provider2;
    }

    public static PluginsModule_ProvidesPluginInstanceFactoryFactory create(Provider provider, Provider provider2) {
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
