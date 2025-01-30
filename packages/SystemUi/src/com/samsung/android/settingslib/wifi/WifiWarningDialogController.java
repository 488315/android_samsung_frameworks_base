package com.samsung.android.settingslib.wifi;

import android.content.Context;
import android.net.wifi.WifiManager;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class WifiWarningDialogController {
    public final Context mContext;
    public final SemWifiManager mSemWifiManager;
    public final WifiManager mWifiManager;

    public WifiWarningDialogController(Context context) {
        this.mContext = context;
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        this.mSemWifiManager = (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
    }
}
