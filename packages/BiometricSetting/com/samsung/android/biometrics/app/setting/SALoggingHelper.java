package com.samsung.android.biometrics.app.setting;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.context.sdk.samsunganalytics.AnalyticsException;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.LogBuilders$EventBuilder;
import com.samsung.context.sdk.samsunganalytics.SamsungAnalytics;
import java.util.HashMap;

/* loaded from: classes.dex */
public final class SALoggingHelper {
    private static String getUiVersion(Context context) {
        int i;
        boolean z = context != null && (context.getPackageManager().hasSystemFeature("com.samsung.feature.samsung_experience_mobile") || context.getPackageManager().hasSystemFeature("com.samsung.feature.samsung_experience_mobile_lite"));
        Log.secD("BSS_SALoggingHelper", "isSemAvailable: " + z);
        if (!z || (i = Build.VERSION.SEM_PLATFORM_INT) < 80100) {
            return "";
        }
        return String.valueOf(i / 10000) + "." + String.valueOf((i % 10000) / 100);
    }

    public static void insertEventLogging() {
        insertSALog(String.valueOf(8234), String.valueOf(8235), null, -9999L);
    }

    public static void insertFlowLogging(int i) {
        insertSALog(String.valueOf(i), null, null, -9999L);
    }

    private static void insertSALog(String str, String str2, String str3, long j) {
        if (Utils.DEBUG) {
            StringBuilder sb = new StringBuilder("insertLog screen:");
            sb.append(str);
            sb.append(str2 != null ? " event:".concat(str2) : " flow logging");
            String str4 = "";
            sb.append(str3 != null ? " detail:".concat(str3) : "");
            if (j != -9999) {
                str4 = " value:" + String.valueOf(j);
            }
            sb.append(str4);
            Log.d("BSS_SALoggingHelper", sb.toString());
        }
        HashMap hashMap = new HashMap();
        hashMap.put("det", str3);
        try {
            if (str2 == null) {
                LogBuilders$EventBuilder logBuilders$EventBuilder = new LogBuilders$EventBuilder(1);
                if (TextUtils.isEmpty(str)) {
                    com.samsung.context.sdk.samsunganalytics.internal.util.Utils.throwException("Failure to build logs [PropertyBuilder] : Key cannot be null.");
                } else {
                    logBuilders$EventBuilder.set("pn", str);
                }
                SamsungAnalytics.getInstance().sendLog(logBuilders$EventBuilder.build());
                return;
            }
            LogBuilders$EventBuilder logBuilders$EventBuilder2 = new LogBuilders$EventBuilder(0);
            if (TextUtils.isEmpty(str)) {
                com.samsung.context.sdk.samsunganalytics.internal.util.Utils.throwException("Failure to build logs [PropertyBuilder] : Key cannot be null.");
            } else {
                logBuilders$EventBuilder2.set("pn", str);
            }
            if (TextUtils.isEmpty(str2)) {
                com.samsung.context.sdk.samsunganalytics.internal.util.Utils.throwException("Failure to build Log : Event name cannot be null");
            }
            logBuilders$EventBuilder2.set("en", str2);
            if (str3 != null) {
                logBuilders$EventBuilder2.setDimension(hashMap);
            }
            if (j != -9999) {
                logBuilders$EventBuilder2.set("ev", String.valueOf(j));
            }
            SamsungAnalytics.getInstance().sendLog(logBuilders$EventBuilder2.build());
        } catch (AnalyticsException unused) {
            Log.e("BSS_SALoggingHelper", "Exception occurred while SA logging");
        }
    }

    public static void setConfiguration(Application application) {
        try {
            Configuration configuration = new Configuration();
            configuration.setTrackingId();
            configuration.setVersion(getUiVersion(application.getApplicationContext()));
            configuration.enableAutoDeviceId();
            SamsungAnalytics.setConfiguration(application, configuration);
        } catch (AnalyticsException unused) {
            Log.e("BSS_SALoggingHelper", "Exception occurred while SA logging setConfiguration");
        }
    }

    public static void insertEventLogging(int i, int i2) {
        insertSALog(String.valueOf(8234), String.valueOf(i), String.valueOf(i2), -9999L);
    }

    public static void insertEventLogging(int i, long j) {
        insertSALog(String.valueOf(8255), String.valueOf(8256), String.valueOf(i), j);
    }
}
