package com.android.systemui.statusbar.pipeline.carrier;

import android.os.SemSystemProperties;
import android.os.SystemProperties;

public final class SystemPropertiesWrapper {
    public final String salesCode = SystemProperties.get("ro.csc.sales_code", "");
    public final boolean singleSKU = SemSystemProperties.getBoolean("mdc.singlesku", false);
    public final boolean unified = SemSystemProperties.getBoolean("mdc.unified", false);

    public SystemPropertiesWrapper() {
        SystemProperties.get("persist.ril.config.dualims", "");
    }
}
