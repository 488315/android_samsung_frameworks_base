package com.android.systemui.pluginlock.component;

import android.app.SemWallpaperColors;

public interface PluginWallpaperCallback {
    void onDataCleared();

    void onReady();

    void onWallpaperHintUpdate(SemWallpaperColors semWallpaperColors);

    void onWallpaperUpdate(boolean z);
}
