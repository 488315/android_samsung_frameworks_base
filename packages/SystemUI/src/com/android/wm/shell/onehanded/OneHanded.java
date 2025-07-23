package com.android.wm.shell.onehanded;

import android.os.SystemProperties;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface OneHanded {
    public static final boolean sIsSupportOneHandedMode = SystemProperties.getBoolean("ro.support_one_handed_mode", false);
}
