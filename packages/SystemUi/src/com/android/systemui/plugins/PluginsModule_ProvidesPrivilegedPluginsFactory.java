package com.android.systemui.plugins;

import android.content.Context;
import dagger.internal.Preconditions;
import java.util.List;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginsModule_ProvidesPrivilegedPluginsFactory implements Provider {
    private final Provider contextProvider;

    public PluginsModule_ProvidesPrivilegedPluginsFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static PluginsModule_ProvidesPrivilegedPluginsFactory create(Provider provider) {
        return new PluginsModule_ProvidesPrivilegedPluginsFactory(provider);
    }

    public static List<String> providesPrivilegedPlugins(Context context) {
        List<String> providesPrivilegedPlugins = PluginsModule.providesPrivilegedPlugins(context);
        Preconditions.checkNotNullFromProvides(providesPrivilegedPlugins);
        return providesPrivilegedPlugins;
    }

    @Override // javax.inject.Provider
    public List<String> get() {
        return providesPrivilegedPlugins((Context) this.contextProvider.get());
    }
}
