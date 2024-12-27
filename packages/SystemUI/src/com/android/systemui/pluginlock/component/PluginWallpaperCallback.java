package com.android.systemui.pluginlock.component;

import android.app.SemWallpaperColors;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface PluginWallpaperCallback {
    void onDataCleared();

    void onReady();

    void onWallpaperHintUpdate(SemWallpaperColors semWallpaperColors);

    void onWallpaperUpdate(boolean z);
}
