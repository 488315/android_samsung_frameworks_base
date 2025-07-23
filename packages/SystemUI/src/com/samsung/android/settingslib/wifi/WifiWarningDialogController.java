package com.samsung.android.settingslib.wifi;

import android.content.Context;
import android.net.wifi.WifiManager;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class WifiWarningDialogController {
    public final Context mContext;
    public final SemWifiManager mSemWifiManager;
    public final WifiManager mWifiManager;

    public WifiWarningDialogController(Context context) {
        this.mContext = context;
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        this.mSemWifiManager = (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
    }
}
