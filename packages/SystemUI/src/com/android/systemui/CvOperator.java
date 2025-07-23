package com.android.systemui;

import android.os.SystemProperties;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class CvOperator {
    public static final String sISOCountry = SystemProperties.get("ro.csc.countryiso_code", "");

    public static int getHotspotStringID(int i) {
        String str = sISOCountry;
        return i == R.string.quick_settings_mobile_hotspot_label ? "JP".equals(str) ? R.string.quick_settings_mobile_hotspot_label_jpn : i : i == R.string.mobile_hotspot_detail_title ? "JP".equals(str) ? R.string.mobile_hotspot_detail_title_jpn : i : i == R.string.mobile_hotspot_dialog_nosim_warning_title ? "JP".equals(str) ? R.string.mobile_hotspot_dialog_nosim_warning_title_jpn : i : (i == R.string.mobile_hotspot_dialog_nosim_warning_message && "JP".equals(str)) ? R.string.mobile_hotspot_dialog_nosim_warning_message_jpn : i;
    }
}
