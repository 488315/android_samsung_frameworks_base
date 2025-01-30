package com.android.systemui.plugins;

import android.content.Context;
import com.android.systemui.shared.plugins.PluginPrefs;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginsModule_ProvidesPluginPrefsFactory implements Provider {
    private final Provider contextProvider;

    public PluginsModule_ProvidesPluginPrefsFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static PluginsModule_ProvidesPluginPrefsFactory create(Provider provider) {
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
