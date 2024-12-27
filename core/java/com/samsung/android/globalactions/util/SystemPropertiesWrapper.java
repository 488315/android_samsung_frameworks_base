package com.samsung.android.globalactions.util;

import android.content.Context;
import android.os.Build;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.security.keystore.KeyProperties;
import com.android.internal.R;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.util.List;

/* loaded from: classes6.dex */
public class SystemPropertiesWrapper {
    public static final String KEY_SYS_SHUTDOWN = "persist.sys.shutdown";
    private static final String TAG = "SystemPropertiesWrapper";
    private final Context mContext;
    private final LogWrapper mLogWrapper;

    public SystemPropertiesWrapper(Context context, LogWrapper logWrapper) {
        this.mContext = context;
        this.mLogWrapper = logWrapper;
    }

    public void set(String key, String value) {
        SystemProperties.set(key, value);
    }

    public String get(String value) {
        return SystemProperties.get(value);
    }

    public boolean isBuildTypeENG() {
        return "eng".equals(Build.TYPE);
    }

    public String getBugReportStatus() {
        return this.mContext.getString(R.string.bugreport_status, Build.VERSION.RELEASE, Build.ID);
    }

    public boolean isDomesticOtaMode() {
        return "true".equals(SystemProperties.get("ril.domesticOtaStart"));
    }

    public boolean isDataModeSupportedSalesCode() {
        return List.of("GLB", "XTC", "SMA", "XTE").contains(SystemProperties.get("ro.csc.sales_code", KeyProperties.DIGEST_NONE).trim().toUpperCase());
    }

    public boolean isForceRestartMessageSupportedSalesCode() {
        return List.of("CHC", "CHM", "CHN", "CBK", "CTC", "CHU", "BNZ").contains(SystemProperties.get("ro.csc.sales_code", KeyProperties.DIGEST_NONE).trim().toUpperCase());
    }

    public boolean isBrazilianCountryISO() {
        return "BR".equalsIgnoreCase(SemSystemProperties.getCountryIso());
    }

    public boolean isTabletDevice() {
        String deviceType = SystemProperties.get("ro.build.characteristics");
        return deviceType != null && deviceType.contains(BnRConstants.DEVICETYPE_TABLET);
    }
}
