package com.android.systemui.shared.plugins;

import android.content.ComponentName;

/* loaded from: classes3.dex */
public interface PluginEnabler {
    int getDisableReason(ComponentName componentName);

    boolean isEnabled(ComponentName componentName);

    void setDisabled(ComponentName componentName, int i);

    void setEnabled(ComponentName componentName);
}
