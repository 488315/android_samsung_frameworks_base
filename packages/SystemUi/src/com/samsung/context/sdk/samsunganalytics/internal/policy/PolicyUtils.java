package com.samsung.context.sdk.samsunganalytics.internal.policy;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.Tracker;
import com.samsung.context.sdk.samsunganalytics.internal.connection.API;
import com.samsung.context.sdk.samsunganalytics.internal.device.DeviceInfo;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class PolicyUtils {
    public static int senderType = -1;

    public static boolean isPolicyExpired(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SamsungAnalyticsPrefs", 0);
        if (Utils.compareDays(1, Long.valueOf(sharedPreferences.getLong("quota_reset_date", 0L)))) {
            sharedPreferences.edit().putLong("quota_reset_date", System.currentTimeMillis()).putInt("data_used", 0).putInt("wifi_used", 0).apply();
        }
        return Utils.compareDays(sharedPreferences.getInt("rint", 1), Long.valueOf(sharedPreferences.getLong("policy_received_date", 0L)));
    }

    public static GetPolicyClient makeGetPolicyClient(Context context, Configuration configuration, DeviceInfo deviceInfo, Tracker.C47852 c47852) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SamsungAnalyticsPrefs", 0);
        API api = API.GET_POLICY;
        HashMap hashMap = new HashMap();
        hashMap.put("pkn", context.getPackageName());
        hashMap.put("dm", deviceInfo.deviceModel);
        String str = deviceInfo.mcc;
        if (!TextUtils.isEmpty(str)) {
            hashMap.put("mcc", str);
        }
        String str2 = deviceInfo.mnc;
        if (!TextUtils.isEmpty(str2)) {
            hashMap.put("mnc", str2);
        }
        hashMap.put("uv", configuration.version);
        hashMap.put("sv", "6.05.033");
        hashMap.put("did", configuration.deviceId);
        hashMap.put("tid", configuration.trackingId);
        String format = SimpleDateFormat.getTimeInstance(2, Locale.US).format(new Date());
        hashMap.put("ts", format);
        hashMap.put("hc", Validation.sha256(configuration.trackingId + format + "RSSAV1wsc2s314SAamk"));
        String str3 = null;
        try {
            str3 = (String) Class.forName("android.os.SystemProperties").getMethod("get", String.class).invoke(null, "ro.csc.sales_code");
        } catch (Exception unused) {
        }
        if (!TextUtils.isEmpty(str3)) {
            hashMap.put("csc", str3);
        }
        GetPolicyClient getPolicyClient = new GetPolicyClient(api, hashMap, sharedPreferences, c47852);
        Debug.LogENG("trid: " + configuration.trackingId.substring(0, 7) + ", uv: " + configuration.version);
        return getPolicyClient;
    }

    public static void useQuota(int i, Context context, int i2) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SamsungAnalyticsPrefs", 0);
        if (i == 1) {
            sharedPreferences.edit().putInt("wifi_used", sharedPreferences.getInt("wifi_used", 0) + i2).apply();
        } else if (i == 0) {
            sharedPreferences.edit().putInt("data_used", context.getSharedPreferences("SamsungAnalyticsPrefs", 0).getInt("data_used", 0) + i2).apply();
        }
    }
}
