package com.samsung.systemui.splugins;

import android.content.ComponentName;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface SPluginEnabler {
    public static final int DISABLED_FROM_EXPLICIT_CRASH = 2;
    public static final int DISABLED_FROM_SYSTEM_CRASH = 3;
    public static final int DISABLED_INVALID_VERSION = 1;
    public static final int DISABLED_MANUALLY = 1;
    public static final int ENABLED = 0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public @interface DisableReason {
    }

    int getDisableReason(ComponentName componentName);

    boolean isEnabled(ComponentName componentName);

    void setDisabled(ComponentName componentName, int i);

    void setEnabled(ComponentName componentName);
}
