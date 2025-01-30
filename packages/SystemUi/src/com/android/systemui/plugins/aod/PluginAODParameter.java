package com.android.systemui.plugins.aod;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface PluginAODParameter {
    boolean getBoolean(int i, boolean z);

    int getInt(int i, int i2);

    int[] getSensorToBrightness();

    String getString(int i, String str);
}
