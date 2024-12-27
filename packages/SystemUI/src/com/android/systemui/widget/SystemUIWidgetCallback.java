package com.android.systemui.widget;

import android.app.SemWallpaperColors;

public interface SystemUIWidgetCallback {
    default void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
    }
}
