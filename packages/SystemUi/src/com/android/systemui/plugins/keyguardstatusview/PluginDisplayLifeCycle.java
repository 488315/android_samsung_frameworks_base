package com.android.systemui.plugins.keyguardstatusview;

import android.graphics.Point;
import android.util.DisplayMetrics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface PluginDisplayLifeCycle {
    DisplayMetrics getDisplayMetrics();

    Point getRealSize();

    boolean isFolderOpened();
}
