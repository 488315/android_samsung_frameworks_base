package com.android.systemui.plugins.keyguardstatusview;

import android.graphics.Point;
import android.util.DisplayMetrics;

/* loaded from: classes2.dex */
public interface PluginDisplayLifeCycle {
    DisplayMetrics getDisplayMetrics();

    Point getRealSize();

    boolean isFolderOpened();
}
