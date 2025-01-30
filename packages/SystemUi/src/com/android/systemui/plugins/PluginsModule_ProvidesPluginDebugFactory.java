package com.android.systemui.plugins;

import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginsModule_ProvidesPluginDebugFactory implements Provider {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class InstanceHolder {
        private static final PluginsModule_ProvidesPluginDebugFactory INSTANCE = new PluginsModule_ProvidesPluginDebugFactory();

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
