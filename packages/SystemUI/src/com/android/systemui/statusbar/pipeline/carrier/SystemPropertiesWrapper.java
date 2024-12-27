package com.android.systemui.statusbar.pipeline.carrier;

import android.os.SemSystemProperties;
import android.os.SystemProperties;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SystemPropertiesWrapper {
    public final String salesCode = SystemProperties.get("ro.csc.sales_code", "");
    public final boolean singleSKU = SemSystemProperties.getBoolean("mdc.singlesku", false);
    public final boolean unified = SemSystemProperties.getBoolean("mdc.unified", false);

    public SystemPropertiesWrapper() {
        SystemProperties.get("persist.ril.config.dualims", "");
    }
}
