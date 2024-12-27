package com.android.systemui.plugins.aod;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface PluginAODParameter {
    boolean getBoolean(int i, boolean z);

    int getInt(int i, int i2);

    int[] getSensorToBrightness();

    String getString(int i, String str);
}
