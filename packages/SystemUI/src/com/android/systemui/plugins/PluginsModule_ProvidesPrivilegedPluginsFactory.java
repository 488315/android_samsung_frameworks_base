package com.android.systemui.plugins;

import android.content.Context;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PluginsModule_ProvidesPrivilegedPluginsFactory implements Provider {
    private final Provider contextProvider;

    public PluginsModule_ProvidesPrivilegedPluginsFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static PluginsModule_ProvidesPrivilegedPluginsFactory create(javax.inject.Provider provider) {
        return new PluginsModule_ProvidesPrivilegedPluginsFactory(Providers.asDaggerProvider(provider));
    }

    public static List<String> providesPrivilegedPlugins(Context context) {
        List<String> providesPrivilegedPlugins = PluginsModule.providesPrivilegedPlugins(context);
        providesPrivilegedPlugins.getClass();
        return providesPrivilegedPlugins;
    }

    public static PluginsModule_ProvidesPrivilegedPluginsFactory create(Provider provider) {
        return new PluginsModule_ProvidesPrivilegedPluginsFactory(provider);
    }

    @Override // javax.inject.Provider
    public List<String> get() {
        return providesPrivilegedPlugins((Context) this.contextProvider.get());
    }
}
