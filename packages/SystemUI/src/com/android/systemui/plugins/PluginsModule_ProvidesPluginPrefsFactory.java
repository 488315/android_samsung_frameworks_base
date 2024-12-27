package com.android.systemui.plugins;

import android.content.Context;
import com.android.systemui.shared.plugins.PluginPrefs;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PluginsModule_ProvidesPluginPrefsFactory implements Provider {
    private final javax.inject.Provider contextProvider;

    public PluginsModule_ProvidesPluginPrefsFactory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static PluginsModule_ProvidesPluginPrefsFactory create(javax.inject.Provider provider) {
        return new PluginsModule_ProvidesPluginPrefsFactory(provider);
    }

    public static PluginPrefs providesPluginPrefs(Context context) {
        PluginPrefs providesPluginPrefs = PluginsModule.providesPluginPrefs(context);
        Preconditions.checkNotNullFromProvides(providesPluginPrefs);
        return providesPluginPrefs;
    }

    @Override // javax.inject.Provider
    public PluginPrefs get() {
        return providesPluginPrefs((Context) this.contextProvider.get());
    }
}
