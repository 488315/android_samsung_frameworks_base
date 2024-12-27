package com.android.systemui;

import android.os.SystemProperties;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class CvOperator {
    public static final String sISOCountry = SystemProperties.get("ro.csc.countryiso_code", "");

    public static int getHotspotStringID(int i) {
        String str = sISOCountry;
        return i == R.string.quick_settings_mobile_hotspot_label ? "JP".equals(str) ? R.string.quick_settings_mobile_hotspot_label_jpn : i : i == R.string.mobile_hotspot_detail_title ? "JP".equals(str) ? R.string.mobile_hotspot_detail_title_jpn : i : i == R.string.mobile_hotspot_dialog_nosim_warning_title ? "JP".equals(str) ? R.string.mobile_hotspot_dialog_nosim_warning_title_jpn : i : (i == R.string.mobile_hotspot_dialog_nosim_warning_message && "JP".equals(str)) ? R.string.mobile_hotspot_dialog_nosim_warning_message_jpn : i;
    }
}
