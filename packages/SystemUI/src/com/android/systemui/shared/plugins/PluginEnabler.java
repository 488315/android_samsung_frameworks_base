package com.android.systemui.shared.plugins;

import android.content.ComponentName;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface PluginEnabler {
    int getDisableReason(ComponentName componentName);

    boolean isEnabled(ComponentName componentName);

    void setDisabled(ComponentName componentName, int i);

    void setEnabled(ComponentName componentName);
}
