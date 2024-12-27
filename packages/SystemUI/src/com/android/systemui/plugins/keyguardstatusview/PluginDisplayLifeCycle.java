package com.android.systemui.plugins.keyguardstatusview;

import android.graphics.Point;
import android.util.DisplayMetrics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface PluginDisplayLifeCycle {
    DisplayMetrics getDisplayMetrics();

    Point getRealSize();

    boolean isFolderOpened();
}
