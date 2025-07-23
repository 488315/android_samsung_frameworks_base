package com.android.systemui.plugins;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PluginsModule_ProvidesPluginDebugFactory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final PluginsModule_ProvidesPluginDebugFactory INSTANCE = new PluginsModule_ProvidesPluginDebugFactory();

        private InstanceHolder() {
        }
    }

    public static PluginsModule_ProvidesPluginDebugFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static boolean providesPluginDebug() {
        return PluginsModule.providesPluginDebug();
    }

    @Override // javax.inject.Provider
    public Boolean get() {
        return Boolean.valueOf(providesPluginDebug());
    }
}
