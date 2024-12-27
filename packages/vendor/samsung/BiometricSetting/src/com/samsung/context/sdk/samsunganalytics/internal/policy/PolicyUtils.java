package com.samsung.context.sdk.samsunganalytics.internal.policy;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.Tracker;
import com.samsung.context.sdk.samsunganalytics.internal.connection.API;
import com.samsung.context.sdk.samsunganalytics.internal.device.DeviceInfo;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public abstract class PolicyUtils {
    public static int senderType = -1;

    public static boolean isPolicyExpired(Context context) {
        SharedPreferences preferences = Preferences.getPreferences(context);
        if (Utils.compareDays(1, Long.valueOf(preferences.getLong("quota_reset_date", 0L)))) {
            preferences
                    .edit()
                    .putLong("quota_reset_date", System.currentTimeMillis())
                    .putInt("data_used", 0)
                    .putInt("wifi_used", 0)
                    .apply();
        }
        return Utils.compareDays(
                preferences.getInt("rint", 1),
                Long.valueOf(preferences.getLong("policy_received_date", 0L)));
    }

    public static GetPolicyClient makeGetPolicyClient(
            Context context,
            Configuration configuration,
            DeviceInfo deviceInfo,
            Tracker.AnonymousClass1 anonymousClass1) {
        String str;
        SharedPreferences preferences = Preferences.getPreferences(context);
        API api = API.GET_POLICY;
        HashMap hashMap = new HashMap();
        hashMap.put("pkn", context.getPackageName());
        hashMap.put("dm", deviceInfo.deviceModel);
        String str2 = deviceInfo.mcc;
        if (!TextUtils.isEmpty(str2)) {
            hashMap.put("mcc", str2);
        }
        String str3 = deviceInfo.mnc;
        if (!TextUtils.isEmpty(str3)) {
            hashMap.put("mnc", str3);
        }
        hashMap.put("uv", configuration.version);
        hashMap.put("sv", "6.05.015");
        hashMap.put("did", configuration.deviceId);
        hashMap.put("tid", configuration.trackingId);
        String format = SimpleDateFormat.getTimeInstance(2, Locale.US).format(new Date());
        hashMap.put("ts", format);
        hashMap.put(
                "hc", Validation.sha256(configuration.trackingId + format + "RSSAV1wsc2s314SAamk"));
        try {
            str =
                    (String)
                            Class.forName("android.os.SystemProperties")
                                    .getMethod("get", String.class)
                                    .invoke(null, "ro.csc.sales_code");
        } catch (Exception unused) {
            str = null;
        }
        if (!TextUtils.isEmpty(str)) {
            hashMap.put("csc", str);
        }
        GetPolicyClient getPolicyClient = new GetPolicyClient();
        getPolicyClient.conn = null;
        getPolicyClient.api = api;
        getPolicyClient.qParams = hashMap;
        getPolicyClient.pref = preferences;
        getPolicyClient.callback = anonymousClass1;
        Debug.LogENG(
                "trid: "
                        + configuration.trackingId.substring(0, 7)
                        + ", uv: "
                        + configuration.version);
        return getPolicyClient;
    }

    public static void useQuota(Context context, int i, int i2) {
        SharedPreferences preferences = Preferences.getPreferences(context);
        if (i == 1) {
            preferences.edit().putInt("wifi_used", preferences.getInt("wifi_used", 0) + i2).apply();
        } else if (i == 0) {
            preferences
                    .edit()
                    .putInt(
                            "data_used",
                            context.getSharedPreferences("SamsungAnalyticsPrefs", 0)
                                            .getInt("data_used", 0)
                                    + i2)
                    .apply();
        }
    }
}
