package com.android.systemui.pluginlock;

import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class DisabledPluginWallpaperManager_Factory implements Provider {

    final class InstanceHolder {
        static final DisabledPluginWallpaperManager_Factory INSTANCE = new DisabledPluginWallpaperManager_Factory();

        private InstanceHolder() {
        }
    }

    public static DisabledPluginWallpaperManager_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static DisabledPluginWallpaperManager newInstance() {
        return new DisabledPluginWallpaperManager();
    }

    @Override // javax.inject.Provider
    public DisabledPluginWallpaperManager get() {
        return newInstance();
    }
}
