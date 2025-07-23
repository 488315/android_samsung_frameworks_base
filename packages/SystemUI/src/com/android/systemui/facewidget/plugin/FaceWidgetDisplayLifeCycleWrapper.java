package com.android.systemui.facewidget.plugin;

import android.graphics.Point;
import android.util.DisplayMetrics;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.plugins.keyguardstatusview.PluginDisplayLifeCycle;
import com.android.systemui.util.LogUtil;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class FaceWidgetDisplayLifeCycleWrapper implements PluginDisplayLifeCycle {
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
        LogUtil.w("DisplayLifecycle", "getDisplayMetrics(%d) is null, return empty Point", 0);
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
