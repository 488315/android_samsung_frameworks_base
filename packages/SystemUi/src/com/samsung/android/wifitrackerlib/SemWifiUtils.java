package com.samsung.android.wifitrackerlib;

import android.os.SystemProperties;
import android.text.TextUtils;
import com.samsung.android.feature.SemCscFeature;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SemWifiUtils {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        SemCscFeature.getInstance().getBoolean("CscFeature_Wifi_EnableAutoWifi");
    }

    public static int calculateSignalLevel(int i) {
        if (i <= -89) {
            return 0;
        }
        if (i > -89 && i <= -83) {
            return 1;
        }
        if (i <= -83 || i > -75) {
            return (i <= -75 || i > -64) ? 4 : 3;
        }
        return 2;
    }

    public static String readSalesCode() {
        try {
            String str = SystemProperties.get("persist.omc.sales_code");
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            String str2 = SystemProperties.get("ro.csc.sales_code");
            return TextUtils.isEmpty(str2) ? SystemProperties.get("ril.sales_code") : str2;
        } catch (Exception unused) {
            return "";
        }
    }
}
