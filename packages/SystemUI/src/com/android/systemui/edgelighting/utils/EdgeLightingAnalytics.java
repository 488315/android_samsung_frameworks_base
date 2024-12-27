package com.android.systemui.edgelighting.utils;

import android.app.Application;
import android.content.Context;
import android.os.Debug;
import android.util.Slog;
import com.android.systemui.R;
import com.android.systemui.util.DeviceState;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.LogBuilders$EventBuilder;
import com.samsung.context.sdk.samsunganalytics.LogBuilders$ScreenViewBuilder;
import com.samsung.context.sdk.samsunganalytics.SamsungAnalytics;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class EdgeLightingAnalytics {
    public static boolean sConfigured = false;
    public static Context sContext = null;
    public static String sCurrentScreenID = "";
    public static final Map sIDMap = new HashMap();

    public static void initEdgeLightingAnalyticsStates(Application application) {
        Slog.d("EdgeLightingAnalytics", "initEdgeLightingAnalyticsStates");
        Configuration configuration = new Configuration();
        configuration.trackingId = "472-399-5110257";
        configuration.version = "unspecified";
        configuration.enableAutoDeviceId = true;
        configuration.isAlwaysRunningApp = true;
        SamsungAnalytics.setConfiguration(application, configuration);
        sContext = application.getApplicationContext();
        sConfigured = true;
        Arrays.asList(EdgeLightingAnalytics.class.getFields()).stream().filter(new EdgeLightingAnalytics$$ExternalSyntheticLambda0()).forEach(new EdgeLightingAnalytics$$ExternalSyntheticLambda1());
        Context context = sContext;
        if (context == null) {
            Slog.d("EdgeLightingAnalytics", "warning, setConfiguration() needed for tile_ids loading.");
            return;
        }
        String[] stringArray = context.getResources().getStringArray(R.array.tile_ids);
        String str = stringArray[0];
        for (int i = 0; i < stringArray.length; i++) {
            if (i % 3 == 0) {
                str = stringArray[i];
            } else {
                ((HashMap) sIDMap).put(stringArray[i], str);
            }
        }
    }

    public static void sendEventLog(String str, String str2) {
        Context context = sContext;
        if (context != null) {
            DeviceState.isSubDisplay(context);
        }
        if (!sConfigured) {
            Slog.d("EdgeLightingAnalytics", "SA is NOT configured");
        }
        if (sConfigured) {
            StringBuilder sb = new StringBuilder("sendEventLog ");
            StringBuilder sb2 = new StringBuilder();
            HashMap hashMap = (HashMap) sIDMap;
            sb2.append(hashMap.containsKey(str) ? (String) hashMap.get(str) : "");
            sb2.append(", ");
            sb2.append(hashMap.containsKey(str2) ? (String) hashMap.get(str2) : "");
            sb.append(sb2.toString());
            Slog.d("EdgeLightingAnalytics", sb.toString());
            try {
                SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
                LogBuilders$EventBuilder logBuilders$EventBuilder = new LogBuilders$EventBuilder();
                logBuilders$EventBuilder.setScreenView(str);
                logBuilders$EventBuilder.setEventName(str2);
                samsungAnalytics.sendLog(logBuilders$EventBuilder.build());
            } catch (Exception e) {
                Slog.d("EdgeLightingAnalytics", "sendEventLog : " + e.getMessage() + ", " + Debug.getCallers(3));
            }
            if (sCurrentScreenID.equals(str)) {
                return;
            }
            sCurrentScreenID = str;
        }
    }

    public static void sendScreenViewLog(String str) {
        Context context = sContext;
        if (context != null) {
            DeviceState.isSubDisplay(context);
        }
        if (!sConfigured) {
            Slog.d("EdgeLightingAnalytics", "SA is NOT configured");
        }
        if (sConfigured && !sCurrentScreenID.equals(str)) {
            StringBuilder sb = new StringBuilder("sendScreenViewLog ");
            HashMap hashMap = (HashMap) sIDMap;
            sb.append(hashMap.containsKey(str) ? (String) hashMap.get(str) : "");
            Slog.d("EdgeLightingAnalytics", sb.toString());
            try {
                SamsungAnalytics samsungAnalytics = SamsungAnalytics.getInstance();
                LogBuilders$ScreenViewBuilder logBuilders$ScreenViewBuilder = new LogBuilders$ScreenViewBuilder();
                logBuilders$ScreenViewBuilder.setScreenView$1(str);
                samsungAnalytics.sendLog(logBuilders$ScreenViewBuilder.build());
            } catch (Exception e) {
                Slog.d("EdgeLightingAnalytics", "sendScreenViewLog : " + e.getMessage() + ", " + Debug.getCallers(3));
            }
            if (sCurrentScreenID.equals(str)) {
                return;
            }
            sCurrentScreenID = str;
        }
    }
}
