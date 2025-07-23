package com.android.systemui;

import android.os.Build;
import android.os.Debug;
import android.os.SystemProperties;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.wifi.SemWifiApCust;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class CvRune extends Rune {
    public static final boolean HOTSPOT_CHECK_MHSDBG;
    public static final String HOTSPOT_CONFIG_OP_BRANDING;
    public static final boolean HOTSPOT_ENABLED_SPRINT_EXTENSION = SemCscFeature.getInstance().getBoolean("CscFeature_Common_EnableSprintExtension");

    static {
        HOTSPOT_CHECK_MHSDBG = "eng".equals(Build.TYPE) || Debug.semIsProductDev();
        SystemProperties.get("vendor.wifiap.simcheck.disable").equals("1");
        SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigOpBrandingForIndicatorIcon", "");
        SemWifiApCust.getInstance();
        HOTSPOT_CONFIG_OP_BRANDING = SemWifiApCust.mMHSCustomer;
        SystemProperties.getInt("ro.product.first_api_level", -1);
    }
}
