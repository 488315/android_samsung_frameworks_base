package com.samsung.context.sdk.samsunganalytics.internal.policy;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.Tracker;
import com.samsung.context.sdk.samsunganalytics.internal.connection.API;
import com.samsung.context.sdk.samsunganalytics.internal.device.DeviceInfo;
import com.samsung.context.sdk.samsunganalytics.internal.util.AuthUtil;
import com.samsung.context.sdk.samsunganalytics.internal.util.ClientUtil;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import com.sec.android.diagmonagent.common.util.executor.SingleThreadExecutor;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PolicyUtils {
    public static int senderType = -1;

    public static String getSystemProperties(String str) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", String.class).invoke(null, str);
        } catch (Exception e) {
            StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("failed to get system properties : ", str, ", error : ");
            m.append(e.getMessage());
            Debug.logwingW(m.toString());
            return "";
        }
    }

    public static boolean isPolicyExpired(Context context) {
        SharedPreferences preferences = Preferences.getPreferences(context);
        if (Utils.compareDays(1, Long.valueOf(preferences.getLong("quota_reset_date", 0L)))) {
            preferences.edit().putLong("quota_reset_date", System.currentTimeMillis()).putInt("data_used", 0).putInt("wifi_used", 0).apply();
        }
        return Utils.compareDays(preferences.getInt("rint", 1), Long.valueOf(preferences.getLong("policy_received_date", 0L)));
    }

    public static void updatePolicy(Context context, Configuration configuration, SingleThreadExecutor singleThreadExecutor, DeviceInfo deviceInfo, Tracker.AnonymousClass7 anonymousClass7) {
        Debug.LogENG("Build policy client, trid: " + configuration.trackingId.substring(0, 7) + ", uv: " + configuration.version);
        SharedPreferences preferences = Preferences.getPreferences(context);
        API api = API.GET_POLICY;
        HashMap hashMap = new HashMap();
        hashMap.put("pkn", context.getPackageName());
        hashMap.put("dm", deviceInfo.deviceModel);
        if (!TextUtils.isEmpty(deviceInfo.mcc)) {
            hashMap.put("mcc", deviceInfo.mcc);
        }
        if (!TextUtils.isEmpty(deviceInfo.mnc)) {
            hashMap.put("mnc", deviceInfo.mnc);
        }
        hashMap.put("uv", configuration.version);
        hashMap.put("sv", "6.05.073");
        hashMap.put("tid", configuration.trackingId);
        String valueOf = String.valueOf(System.currentTimeMillis());
        hashMap.put("ts", valueOf);
        hashMap.put("hc", AuthUtil.sha256(configuration.trackingId + valueOf + ClientUtil.SALT));
        String systemProperties = getSystemProperties("ro.csc.sales_code");
        if (!TextUtils.isEmpty(systemProperties)) {
            hashMap.put("csc", systemProperties);
        }
        String systemProperties2 = getSystemProperties("ro.csc.countryiso_code");
        if (!TextUtils.isEmpty(systemProperties2)) {
            hashMap.put("cc", systemProperties2);
        }
        singleThreadExecutor.execute(new PolicyClient(api, hashMap, preferences, anonymousClass7));
    }

    public static void useQuota(Context context, int i, int i2) {
        SharedPreferences preferences = Preferences.getPreferences(context);
        if (i == 1) {
            preferences.edit().putInt("wifi_used", preferences.getInt("wifi_used", 0) + i2).apply();
        } else if (i == 0) {
            preferences.edit().putInt("data_used", context.getSharedPreferences("SamsungAnalyticsPrefs", 0).getInt("data_used", 0) + i2).apply();
        }
    }
}
