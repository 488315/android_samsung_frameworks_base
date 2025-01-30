package com.android.systemui.facewidget.plugin;

import android.graphics.Point;
import android.util.DisplayMetrics;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.plugins.keyguardstatusview.PluginDisplayLifeCycle;
import com.android.systemui.util.LogUtil;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FaceWidgetDisplayLifeCycleWrapper implements PluginDisplayLifeCycle {
    public final DisplayLifecycle mDisplayLifecycle;

    public FaceWidgetDisplayLifeCycleWrapper(DisplayLifecycle displayLifecycle) {
        this.mDisplayLifecycle = displayLifecycle;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginDisplayLifeCycle
    public final DisplayMetrics getDisplayMetrics() {
        DisplayLifecycle displayLifecycle = this.mDisplayLifecycle;
        if (displayLifecycle.getDisplay(0) == null) {
            displayLifecycle.addDisplay(0);
        }
        DisplayMetrics displayMetrics = (DisplayMetrics) displayLifecycle.mDisplayMetricsHash.get(0);
        if (displayMetrics != null) {
            return displayMetrics;
        }
        LogUtil.m226w("DisplayLifecycle", "getDisplayMetrics(%d) is null, return empty Point", 0);
        return new DisplayMetrics();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginDisplayLifeCycle
    public final Point getRealSize() {
        return this.mDisplayLifecycle.getRealSize();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginDisplayLifeCycle
    public final boolean isFolderOpened() {
        return this.mDisplayLifecycle.mIsFolderOpened;
    }
}
