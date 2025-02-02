package com.samsung.context.sdk.samsunganalytics.internal.policy;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.Callback;
import com.samsung.context.sdk.samsunganalytics.internal.connection.API;
import com.samsung.context.sdk.samsunganalytics.internal.device.DeviceInfo;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/* loaded from: classes.dex */
public final class PolicyUtils {
    private static int senderType = -1;

    public static int getSenderType() {
        return senderType;
    }

    public static boolean isPolicyExpired(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SamsungAnalyticsPrefs", 0);
        if (Utils.compareDays(1, Long.valueOf(sharedPreferences.getLong("quota_reset_date", 0L)))) {
            sharedPreferences.edit().putLong("quota_reset_date", System.currentTimeMillis()).putInt("data_used", 0).putInt("wifi_used", 0).apply();
        }
        return Utils.compareDays(sharedPreferences.getInt("rint", 1), Long.valueOf(sharedPreferences.getLong("policy_received_date", 0L)));
    }

    public static GetPolicyClient makeGetPolicyClient(Context context, Configuration configuration, DeviceInfo deviceInfo, Callback callback) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SamsungAnalyticsPrefs", 0);
        API api = API.GET_POLICY;
        HashMap hashMap = new HashMap();
        hashMap.put("pkn", context.getPackageName());
        hashMap.put("dm", deviceInfo.getDeviceModel());
        if (!TextUtils.isEmpty(deviceInfo.getMcc())) {
            hashMap.put("mcc", deviceInfo.getMcc());
        }
        if (!TextUtils.isEmpty(deviceInfo.getMnc())) {
            hashMap.put("mnc", deviceInfo.getMnc());
        }
        hashMap.put("uv", configuration.getVersion());
        hashMap.put("sv", "6.05.015");
        hashMap.put("did", configuration.getDeviceId());
        hashMap.put("tid", configuration.getTrackingId());
        String format = SimpleDateFormat.getTimeInstance(2, Locale.US).format(new Date());
        hashMap.put("ts", format);
        hashMap.put("hc", Validation.sha256(configuration.getTrackingId() + format + "RSSAV1wsc2s314SAamk"));
        String str = null;
        try {
            str = (String) Class.forName("android.os.SystemProperties").getMethod("get", String.class).invoke(null, "ro.csc.sales_code");
        } catch (Exception unused) {
        }
        if (!TextUtils.isEmpty(str)) {
            hashMap.put("csc", str);
        }
        GetPolicyClient getPolicyClient = new GetPolicyClient(api, hashMap, sharedPreferences, callback);
        Debug.LogENG("trid: " + configuration.getTrackingId().substring(0, 7) + ", uv: " + configuration.getVersion());
        return getPolicyClient;
    }

    public static void setSenderType(Context context, Configuration configuration) {
        if (senderType == -1) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.sec.android.diagmonagent", 0);
                Debug.LogD("Validation", "dma pkg:" + packageInfo.versionCode);
                int i = packageInfo.versionCode;
                if (i < 540000000) {
                    configuration.getClass();
                    senderType = 1;
                } else if (i >= 600000000) {
                    senderType = 3;
                } else {
                    senderType = 2;
                }
            } catch (Exception e) {
                configuration.getClass();
                senderType = 1;
                Debug.LogD("DMA not found" + e.getMessage());
            }
        }
    }

    public static void useQuota(Context context, int i, int i2) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SamsungAnalyticsPrefs", 0);
        if (i == 1) {
            sharedPreferences.edit().putInt("wifi_used", sharedPreferences.getInt("wifi_used", 0) + i2).apply();
        } else if (i == 0) {
            sharedPreferences.edit().putInt("data_used", context.getSharedPreferences("SamsungAnalyticsPrefs", 0).getInt("data_used", 0) + i2).apply();
        }
    }
}
