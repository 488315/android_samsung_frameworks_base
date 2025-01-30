package com.android.systemui.statusbar.pipeline.carrier;

import android.os.SemSystemProperties;
import android.os.SystemProperties;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SystemPropertiesWrapper {
    public final String salesCode = SystemProperties.get("ro.csc.sales_code", "");
    public final boolean singleSKU = SemSystemProperties.getBoolean("mdc.singlesku", false);
    public final boolean unified = SemSystemProperties.getBoolean("mdc.unified", false);

    public SystemPropertiesWrapper() {
        SystemProperties.get("persist.ril.config.dualims", "");
        SystemProperties.getBoolean("ril.cdma.inecmmode", false);
        SystemProperties.getBoolean("ro.ril.svlte1x", false);
        SystemProperties.getBoolean("ro.ril.svdo", false);
    }
}
