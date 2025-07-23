package com.android.systemui.statusbar.pipeline.carrier;

import android.os.SemSystemProperties;
import android.os.SystemProperties;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SystemPropertiesWrapper {
    public final String salesCode = SystemProperties.get("ro.csc.sales_code", "");
    public final boolean singleSKU = SemSystemProperties.getBoolean("mdc.singlesku", false);
    public final boolean unified = SemSystemProperties.getBoolean("mdc.unified", false);

    public SystemPropertiesWrapper() {
        SystemProperties.get("persist.ril.config.dualims", "");
    }
}
