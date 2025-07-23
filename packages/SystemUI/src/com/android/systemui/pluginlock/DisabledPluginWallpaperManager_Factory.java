package com.android.systemui.pluginlock;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DisabledPluginWallpaperManager_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
