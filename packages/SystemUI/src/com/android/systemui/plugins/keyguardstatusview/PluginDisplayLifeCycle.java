package com.android.systemui.plugins.keyguardstatusview;

import android.graphics.Point;
import android.util.DisplayMetrics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface PluginDisplayLifeCycle {
    DisplayMetrics getDisplayMetrics();

    Point getRealSize();

    boolean isFolderOpened();
}
