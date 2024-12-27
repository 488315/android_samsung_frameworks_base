package com.android.systemui.plugins.aod;

public interface PluginAODParameter {
    boolean getBoolean(int i, boolean z);

    int getInt(int i, int i2);

    int[] getSensorToBrightness();

    String getString(int i, String str);
}
