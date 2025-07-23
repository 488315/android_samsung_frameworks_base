package com.samsung.android.wifitrackerlib;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class WifiIssueDetectorUtil {
    public final String mNameOfUid;
    public final SemWifiManager mSemWifiManager;

    public WifiIssueDetectorUtil(Context context) {
        context.getPackageName();
        this.mNameOfUid = context.getPackageManager().getNameForUid(context.getUserId());
        this.mSemWifiManager = (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
    }

    public final void reportConnectNetwork(WifiConfiguration wifiConfiguration) {
        String str;
        String[] strArr;
        String str2;
        if (wifiConfiguration != null) {
            int i = wifiConfiguration.networkId;
            String printableSsid = wifiConfiguration.getPrintableSsid();
            int i2 = 1;
            if (wifiConfiguration.allowedKeyManagement.get(1) || wifiConfiguration.allowedKeyManagement.get(8) ? (str = wifiConfiguration.preSharedKey) == null || str.length() <= 2 : !wifiConfiguration.allowedKeyManagement.get(0) || (strArr = wifiConfiguration.wepKeys) == null || (str2 = strArr[0]) == null || str2.length() <= 2) {
                i2 = 0;
            }
            boolean isPasspoint = wifiConfiguration.isPasspoint();
            SemWifiManager semWifiManager = this.mSemWifiManager;
            Bundle bundle = new Bundle();
            bundle.putInt("netid", i);
            bundle.putString("ssid", printableSsid);
            bundle.putString("apiName", "connect");
            bundle.putString("callUid", this.mNameOfUid);
            bundle.putInt("hasPassword", i2);
            bundle.putInt("isPasspoint", isPasspoint ? 1 : 0);
            semWifiManager.reportIssue(103, bundle);
        }
    }
}
