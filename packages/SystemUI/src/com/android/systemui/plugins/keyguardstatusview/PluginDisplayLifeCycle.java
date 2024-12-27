package com.android.systemui.plugins.keyguardstatusview;

import android.graphics.Point;
import android.util.DisplayMetrics;

public interface PluginDisplayLifeCycle {
    DisplayMetrics getDisplayMetrics();

    Point getRealSize();

    boolean isFolderOpened();
}
