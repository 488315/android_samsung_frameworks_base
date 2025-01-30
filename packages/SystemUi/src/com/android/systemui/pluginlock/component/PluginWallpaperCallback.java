package com.android.systemui.pluginlock.component;

import android.app.SemWallpaperColors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface PluginWallpaperCallback {
    void onDataCleared();

    void onReady();

    void onWallpaperHintUpdate(SemWallpaperColors semWallpaperColors);

    void onWallpaperUpdate(boolean z);
}
