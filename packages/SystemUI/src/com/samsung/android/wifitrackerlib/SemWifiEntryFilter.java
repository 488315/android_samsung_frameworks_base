package com.samsung.android.wifitrackerlib;

import android.content.Context;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SemWifiEntryFilter {
    public static final int[] SETTING_DEVELOPER_RSSI = {-73, -78, -127};
    public static final int[] SETTING_DEVELOPER_RSSI_5G = {-70, -75, -127};
    public final boolean CSC_WIFI_SUPPORT_VZW_EAP_AKA;
    public final boolean DISPLAY_SSID_STATUS_BAR_INFO;
    public final Context mContext;
    public final boolean mIsDeveloperOptionOn;
    public int mWeakSignalRssi;
    public int mWeakSignalRssi5Ghz;

    public SemWifiEntryFilter(Context context) {
        int i = SemWifiUtils.$r8$clinit;
        String str = "";
        try {
            str = SystemProperties.get("persist.omc.sales_code");
            if (TextUtils.isEmpty(str)) {
                str = SystemProperties.get("ro.csc.sales_code");
                if (TextUtils.isEmpty(str)) {
                    str = SystemProperties.get("ril.sales_code");
                }
            }
        } catch (Exception unused) {
        }
        this.CSC_WIFI_SUPPORT_VZW_EAP_AKA = "VZW".equals(str);
        this.DISPLAY_SSID_STATUS_BAR_INFO = "SWC".equals(SystemProperties.get("ro.csc.sales_code"));
        this.mContext = context;
        this.mIsDeveloperOptionOn = SemWifiEntryFlags.isWifiDeveloperOptionOn(context);
        updateRssiFilter();
    }

    public final void updateRssiFilter() {
        if (this.mIsDeveloperOptionOn) {
            Context context = this.mContext;
            int i = SemWifiUtils.$r8$clinit;
            int i2 = Settings.Global.getInt(context.getContentResolver(), "sec_wifi_developer_rssi_level", 1);
            this.mWeakSignalRssi = SETTING_DEVELOPER_RSSI[i2];
            this.mWeakSignalRssi5Ghz = SETTING_DEVELOPER_RSSI_5G[i2];
        } else {
            this.mWeakSignalRssi = -78;
            this.mWeakSignalRssi5Ghz = -75;
        }
        StringBuilder sb = new StringBuilder("mWeakSignalRssi: ");
        sb.append(this.mWeakSignalRssi);
        sb.append(", mWeakSignalRssi5Ghz: ");
        RecyclerView$$ExternalSyntheticOutline0.m(this.mWeakSignalRssi5Ghz, "SemWifiEntryFilter", sb);
    }
}
