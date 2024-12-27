package com.samsung.context.sdk.samsunganalytics.internal.device;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;

import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public final class DeviceInfo {
    public final String androidVersion;
    public final String appVersionName;
    public final String deviceModel;
    public final String firmwareVersion;
    public final String language;
    public final String mcc;
    public final String mnc;
    public final String timeZoneOffset;

    public DeviceInfo(Context context) {
        String simOperator;
        this.language = "";
        this.androidVersion = "";
        this.deviceModel = "";
        this.appVersionName = "";
        this.mcc = "";
        this.mnc = "";
        this.timeZoneOffset = "";
        this.firmwareVersion = "";
        Locale locale = context.getResources().getConfiguration().locale;
        locale.getDisplayCountry();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager != null
                && (simOperator = telephonyManager.getSimOperator()) != null
                && simOperator.length() >= 3) {
            this.mcc = simOperator.substring(0, 3);
            this.mnc = simOperator.substring(3);
        }
        this.language = locale.getLanguage();
        this.androidVersion = Build.VERSION.RELEASE;
        String str = Build.BRAND;
        this.deviceModel = Build.MODEL;
        this.firmwareVersion = Build.VERSION.INCREMENTAL;
        this.timeZoneOffset =
                String.valueOf(
                        TimeUnit.MILLISECONDS.toMinutes(TimeZone.getDefault().getRawOffset()));
        try {
            this.appVersionName =
                    context.getPackageManager()
                            .getPackageInfo(context.getPackageName(), 0)
                            .versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Debug.LogException(DeviceInfo.class, e);
        }
    }
}
