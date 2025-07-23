package com.android.systemui.plugins.aod;

import android.graphics.Rect;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface PluginTspEventCallback {
    boolean containsTouchableRect(int i, int i2);

    Rect getTouchableRect();

    int getTspEventAction();

    boolean isWakeUpAction();

    boolean onTspEvent(View view, PluginTspEvent pluginTspEvent);
}
