package com.android.systemui.plugins;

import android.content.Context;
import com.android.systemui.shared.plugins.PluginPrefs;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        PluginPrefs providesPluginPrefs = PluginsModule.providesPluginPrefs(context);
        providesPluginPrefs.getClass();
        return providesPluginPrefs;
    }

    public static PluginsModule_ProvidesPluginPrefsFactory create(Provider provider) {
        return new PluginsModule_ProvidesPluginPrefsFactory(provider);
    }

    @Override // javax.inject.Provider
    public PluginPrefs get() {
        return providesPluginPrefs((Context) this.contextProvider.get());
    }
}
