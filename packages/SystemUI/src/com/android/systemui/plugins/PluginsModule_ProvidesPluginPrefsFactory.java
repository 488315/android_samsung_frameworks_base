package com.android.systemui.plugins;

import android.content.Context;
import com.android.systemui.shared.plugins.PluginPrefs;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes2.dex */
public final class PluginsModule_ProvidesPluginPrefsFactory implements Provider {
    private final Provider contextProvider;

    public PluginsModule_ProvidesPluginPrefsFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static PluginsModule_ProvidesPluginPrefsFactory create(javax.inject.Provider provider) {
        return new PluginsModule_ProvidesPluginPrefsFactory(Providers.asDaggerProvider(provider));
    }

    public static PluginPrefs providesPluginPrefs(Context context) {
        PluginPrefs pluginPrefsProvidesPluginPrefs = PluginsModule.providesPluginPrefs(context);
        pluginPrefsProvidesPluginPrefs.getClass();
        return pluginPrefsProvidesPluginPrefs;
    }

    public static PluginsModule_ProvidesPluginPrefsFactory create(Provider provider) {
        return new PluginsModule_ProvidesPluginPrefsFactory(provider);
    }

    @Override // javax.inject.Provider
    public PluginPrefs get() {
        return providesPluginPrefs((Context) this.contextProvider.get());
    }
}
