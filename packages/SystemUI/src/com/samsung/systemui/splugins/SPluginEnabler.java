package com.samsung.systemui.splugins;

import android.content.ComponentName;

/* loaded from: classes4.dex */
public interface SPluginEnabler {
    public static final int DISABLED_FROM_EXPLICIT_CRASH = 2;
    public static final int DISABLED_FROM_SYSTEM_CRASH = 3;
    public static final int DISABLED_INVALID_VERSION = 1;
    public static final int DISABLED_MANUALLY = 1;
    public static final int ENABLED = 0;

    public @interface DisableReason {
    }

    int getDisableReason(ComponentName componentName);

    boolean isEnabled(ComponentName componentName);

    void setDisabled(ComponentName componentName, int i);

    void setEnabled(ComponentName componentName);
}
