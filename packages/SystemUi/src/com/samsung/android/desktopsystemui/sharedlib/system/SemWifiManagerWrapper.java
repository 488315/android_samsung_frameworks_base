package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.AppGlobals;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SemWifiManagerWrapper {
    private static final SemWifiManagerWrapper sInstance = new SemWifiManagerWrapper();
    private static final SemWifiManager mSemWifiManager = (SemWifiManager) AppGlobals.getInitialApplication().getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);

    private SemWifiManagerWrapper() {
    }

    public static SemWifiManagerWrapper getInstance() {
        return sInstance;
    }

    public boolean isDualAPConfiguration() {
        return mSemWifiManager.getSoftApConfiguration().getBand() != 0;
    }

    public boolean isWifiSharingLiteSupported() {
        return mSemWifiManager.isWifiSharingLiteSupported();
    }

    public boolean isWifiSharingSupported() {
        return mSemWifiManager.isWifiSharingSupported();
    }
}
