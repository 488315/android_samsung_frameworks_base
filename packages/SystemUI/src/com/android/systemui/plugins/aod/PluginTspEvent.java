package com.android.systemui.plugins.aod;

import java.util.Locale;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class PluginTspEvent {
    public final int action;
    public final int x;
    public final int y;

    public PluginTspEvent(int i, int i2, int i3) {
        this.action = i;
        this.x = i2;
        this.y = i3;
    }

    public String toString() {
        return String.format(Locale.getDefault(), "[PluginTspEvent : action = %s, x = %d, y = %d]", Integer.valueOf(this.action), Integer.valueOf(this.x), Integer.valueOf(this.y));
    }
}
