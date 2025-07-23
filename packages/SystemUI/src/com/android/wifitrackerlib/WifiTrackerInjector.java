package com.android.wifitrackerlib;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Log;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.samsung.android.wifi.SemWifiApCust;
import com.sec.ims.settings.ImsProfile;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class WifiTrackerInjector {
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public final DevicePolicyManager mDevicePolicyManager;
    public final int mInstantHotspotEnableProperty;
    public final boolean mIsDemoMode;
    public final UserManager mUserManager;
    public final WifiManager mWifiManager;
    public boolean mVerboseLoggingDisabledOverride = false;
    public final Set mNoAttributionAnnotationPackages = new ArraySet();

    public WifiTrackerInjector(Context context) {
        this.mInstantHotspotEnableProperty = -1;
        this.mContext = context;
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        this.mIsDemoMode = UserManager.isDeviceInDemoMode(context);
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        for (String str : context.getString(R.string.wifitrackerlib_no_attribution_annotation_packages).split(",")) {
            this.mNoAttributionAnnotationPackages.add(str);
        }
        this.mInstantHotspotEnableProperty = Settings.Secure.getInt(this.mContext.getContentResolver(), "vendor.wifiap.ih.enable", -1);
    }

    public final boolean isSharedConnectivityFeatureEnabled() {
        boolean z = !"CN".equalsIgnoreCase(SystemProperties.get("ro.csc.countryiso_code"));
        if (SemWifiApCust.DBG) {
            int i = this.mInstantHotspotEnableProperty;
            if (i >= 0) {
                z = i == 1;
            }
            StringBuilder m = RowView$$ExternalSyntheticOutline0.m("Instant Hotspot isDeviceConfigEnabled ", " rollout:", z);
            m.append(DeviceConfig.getBoolean(ImsProfile.PDN_WIFI, "shared_connectivity_enabled", false));
            Log.i("WifiTrackerInjector", m.toString());
        }
        return z;
    }
}
