package com.android.systemui.plugins.aod;

import android.graphics.Rect;
import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface PluginTspEventCallback {
    boolean containsTouchableRect(int i, int i2);

    Rect getTouchableRect();

    int getTspEventAction();

    boolean isWakeUpAction();

    boolean onTspEvent(View view, PluginTspEvent pluginTspEvent);
}
