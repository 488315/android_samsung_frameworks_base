package com.android.systemui.plugins.aod;

import android.graphics.Rect;
import android.view.View;

public interface PluginTspEventCallback {
    boolean containsTouchableRect(int i, int i2);

    Rect getTouchableRect();

    int getTspEventAction();

    boolean isWakeUpAction();

    boolean onTspEvent(View view, PluginTspEvent pluginTspEvent);
}
