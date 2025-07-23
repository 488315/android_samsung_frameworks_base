package com.android.systemui.pluginlock.component;

import android.app.SemWallpaperColors;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface PluginWallpaperCallback {
    void onDataCleared();

    void onReady();

    void onWallpaperHintUpdate(SemWallpaperColors semWallpaperColors);

    void onWallpaperUpdate(boolean z);
}
