package com.samsung.context.sdk.samsunganalytics.internal.device;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class DeviceInfo {
    public static volatile DeviceInfo INSTANCE;
    public final String androidVersion = Build.VERSION.RELEASE;
    public final String deviceModel = Build.MODEL;
    public final String firmwareVersion = Build.VERSION.INCREMENTAL;
    public final String language;
    public final String mcc;
    public final String mnc;

    private DeviceInfo(Context context) {
        String simOperator;
        this.mcc = "";
        this.mnc = "";
        this.language = context.getResources().getConfiguration().locale.getLanguage();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null || telephonyManager.getSimState() != 5 || (simOperator = telephonyManager.getSimOperator()) == null || simOperator.length() < 3) {
            return;
        }
        this.mcc = simOperator.substring(0, 3);
        this.mnc = simOperator.substring(3);
    }

    public static DeviceInfo getDeviceInfo(Context context) {
        synchronized (DeviceInfo.class) {
            try {
                if (INSTANCE == null) {
                    INSTANCE = new DeviceInfo(context);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return INSTANCE;
    }
}
