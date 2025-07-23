package com.android.systemui.plugins.aod;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface PluginAODParameter {
    boolean getBoolean(int i, boolean z);

    int getInt(int i, int i2);

    int[] getSensorToBrightness();

    String getString(int i, String str);
}
