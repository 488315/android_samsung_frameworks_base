package com.samsung.android.wifitrackerlib;

import android.content.Context;
import android.os.SystemProperties;
import android.provider.Settings;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SemWifiEntryFilter {
    public static final int[] SETTING_DEVELOPER_RSSI = {-73, -78, -127};
    public static final int[] SETTING_DEVELOPER_RSSI_5G = {-70, -75, -127};
    public final boolean CSC_WIFI_SUPPORT_VZW_EAP_AKA = "VZW".equals(SemWifiUtils.readSalesCode());
    public final boolean DISPLAY_SSID_STATUS_BAR_INFO = "SWC".equals(SystemProperties.get("ro.csc.sales_code"));
    public final Context mContext;
    public final boolean mIsDeveloperOptionOn;
    public int mWeakSignalRssi;
    public int mWeakSignalRssi5Ghz;

    public SemWifiEntryFilter(Context context) {
        this.mContext = context;
        this.mIsDeveloperOptionOn = SemWifiEntryFlags.isWifiDeveloperOptionOn(context);
        updateRssiFilter();
    }

    public final void updateRssiFilter() {
        if (this.mIsDeveloperOptionOn) {
            int i = SemWifiUtils.$r8$clinit;
            int i2 = Settings.Global.getInt(this.mContext.getContentResolver(), "sec_wifi_developer_rssi_level", 1);
            this.mWeakSignalRssi = SETTING_DEVELOPER_RSSI[i2];
            this.mWeakSignalRssi5Ghz = SETTING_DEVELOPER_RSSI_5G[i2];
        } else {
            this.mWeakSignalRssi = -78;
            this.mWeakSignalRssi5Ghz = -75;
        }
        StringBuilder sb = new StringBuilder("mWeakSignalRssi: ");
        sb.append(this.mWeakSignalRssi);
        sb.append(", mWeakSignalRssi5Ghz: ");
        RecyclerView$$ExternalSyntheticOutline0.m46m(sb, this.mWeakSignalRssi5Ghz, "SemWifiEntryFilter");
    }
}
