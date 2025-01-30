package com.android.systemui;

import android.os.SystemProperties;
import com.samsung.android.feature.SemCscFeature;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CvOperator {
    public static final String sISOCountry = SystemProperties.get("ro.csc.countryiso_code", "");

    static {
        String string = SemCscFeature.getInstance().getString("CscFeature_Wifi_ConfigOpBrandingForMobileAp", "ALL");
        "VZW".equals(string);
        "SPRINT".equals(string);
    }

    public static int getHotspotStringID(int i) {
        String str = sISOCountry;
        return i == R.string.quick_settings_mobile_hotspot_label ? "JP".equals(str) ? R.string.quick_settings_mobile_hotspot_label_jpn : i : i == R.string.mobile_hotspot_detail_title ? "JP".equals(str) ? R.string.mobile_hotspot_detail_title_jpn : i : i == R.string.mobile_hotspot_dialog_nosim_warning_title ? "JP".equals(str) ? R.string.mobile_hotspot_dialog_nosim_warning_title_jpn : i : (i == R.string.mobile_hotspot_dialog_nosim_warning_message && "JP".equals(str)) ? R.string.mobile_hotspot_dialog_nosim_warning_message_jpn : i;
    }
}
