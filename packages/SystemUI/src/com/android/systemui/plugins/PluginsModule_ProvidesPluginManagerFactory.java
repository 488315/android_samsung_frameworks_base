package com.android.systemui.plugins;

import android.content.Context;
import com.android.systemui.shared.plugins.PluginActionManager;
import com.android.systemui.shared.plugins.PluginEnabler;
import com.android.systemui.shared.plugins.PluginInstance;
import com.android.systemui.shared.plugins.PluginPrefs;
import com.android.systemui.shared.system.UncaughtExceptionPreHandlerManager;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PluginsModule_ProvidesPluginManagerFactory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider debugProvider;
    private final javax.inject.Provider instanceManagerFactoryProvider;
    private final javax.inject.Provider pluginEnablerProvider;
    private final javax.inject.Provider pluginInstanceFactoryProvider;
    private final javax.inject.Provider pluginPrefsProvider;
    private final javax.inject.Provider preHandlerManagerProvider;
    private final javax.inject.Provider privilegedPluginsProvider;

    public PluginsModule_ProvidesPluginManagerFactory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8) {
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
        return new PluginsModule_ProvidesPluginManagerFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static PluginManager providesPluginManager(Context context, PluginActionManager.Factory factory, boolean z, UncaughtExceptionPreHandlerManager uncaughtExceptionPreHandlerManager, PluginEnabler pluginEnabler, PluginPrefs pluginPrefs, List<String> list, PluginInstance.Factory factory2) {
        PluginManager providesPluginManager = PluginsModule.providesPluginManager(context, factory, z, uncaughtExceptionPreHandlerManager, pluginEnabler, pluginPrefs, list, factory2);
        Preconditions.checkNotNullFromProvides(providesPluginManager);
        return providesPluginManager;
    }

    @Override // javax.inject.Provider
    public PluginManager get() {
        return providesPluginManager((Context) this.contextProvider.get(), (PluginActionManager.Factory) this.instanceManagerFactoryProvider.get(), ((Boolean) this.debugProvider.get()).booleanValue(), (UncaughtExceptionPreHandlerManager) this.preHandlerManagerProvider.get(), (PluginEnabler) this.pluginEnablerProvider.get(), (PluginPrefs) this.pluginPrefsProvider.get(), (List) this.privilegedPluginsProvider.get(), (PluginInstance.Factory) this.pluginInstanceFactoryProvider.get());
    }
}
