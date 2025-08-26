package com.android.wm.shell.onehanded;

import android.os.SystemProperties;

/* loaded from: classes3.dex */
public interface OneHanded {
    public static final boolean sIsSupportOneHandedMode = SystemProperties.getBoolean("ro.support_one_handed_mode", false);
}
