package com.android.systemui.plugins.aod;

import android.graphics.Rect;
import android.view.View;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface PluginTspEventCallback {
    boolean containsTouchableRect(int i, int i2);

    Rect getTouchableRect();

    int getTspEventAction();

    boolean isWakeUpAction();

    boolean onTspEvent(View view, PluginTspEvent pluginTspEvent);
}
