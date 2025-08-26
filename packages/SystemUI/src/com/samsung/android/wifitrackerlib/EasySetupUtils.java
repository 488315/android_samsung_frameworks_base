package com.samsung.android.wifitrackerlib;

import android.content.Context;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

/* loaded from: classes4.dex */
public class EasySetupUtils {
    public final SemWifiManager mSemWifiManager;

    public EasySetupUtils(Context context) {
        this.mSemWifiManager = (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
    }
}
