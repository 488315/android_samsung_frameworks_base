package com.android.systemui.plugins;

import android.content.Context;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PluginsModule_ProvidesPrivilegedPluginsFactory implements Provider {
    private final javax.inject.Provider contextProvider;

    public PluginsModule_ProvidesPrivilegedPluginsFactory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static PluginsModule_ProvidesPrivilegedPluginsFactory create(javax.inject.Provider provider) {
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
