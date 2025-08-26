package com.samsung.android.core;

import android.app.ActivityTaskManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.FactoryTest;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.os.BackgroundThread;
import com.samsung.android.rune.CoreRune;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class CoreSaLogger {
    private static final String ADVANCED_TRACKING_ID = "408-399-975257";
    private static final String BASIC_TRACKING_ID = "4F4-399-995755";
    public static final String DETAIL_KEY = "det";
    private static final String DEX_TRACKING_ID = "403-399-565756";
    private static final String DIMENSION_KEY = "dimension";
    private static final String DIMENSION_VALUE_KEY1 = "d_key1";
    private static final String EXTRA_KEY = "extra";
    private static final String FEATURE_KEY = "feature";
    public static final String MODE_KEY = "mode";
    private static final int NULL_VALUE = -1;
    private static final String PACKAGE_NAME_KEY = "pkg_name";
    private static final String PAGE_ID_KEY = "page_id";
    private static final String SA_ACTION = "com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY";
    private static final String SA_PACKAGE = "com.sec.android.diagmonagent";
    private static final String SCREEN_ID_KEY = "screen_id";
    private static final String SETTING_KEY = "setting";
    private static final String SYSTEMUI_TRACKING_ID = "472-399-5110257";
    private static final String TAG = "CoreSaLogger";
    private static final String TRACKING_ID_KEY = "tracking_id";
    private static final String TYPE_KEY = "type";
    private static final String TYPE_VALUE_EV = "ev";
    private static final String TYPE_VALUE_ST = "st";
    private static final String VALUE_KEY = "value";
    private static final boolean IS_FACTORY_BINARY = FactoryTest.isFactoryBinary();
    private static final HashMap<String, String> sSettingMapForBasic = new HashMap<>();
    private static final HashMap<String, String> sSettingMapForAdvanced = new HashMap<>();
    private static final HashMap<String, String> sSettingMapForDex = new HashMap<>();
    private static final HashMap<String, String> sCustomDimensionForMode = new HashMap<>();

    private static void sendLogToServer(String str, String str2, String str3, String str4, String str5, long j, HashMap<String, String> map) {
        if (IS_FACTORY_BINARY) {
            Log.d(TAG, "Does't send Logging, It's FactoryBinary");
            return;
        }
        try {
            ActivityTaskManager.getService().sendSaLoggingBroadcast(str, str2, str3, str4, str5, j, map);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to sendSaLoggingBroadcast", e);
        }
    }

    public static void sendSaLoggingBroadcast(final Context context, final String str, final String str2, final String str3, final String str4, final String str5, final long j, final HashMap<String, String> map, final String str6) {
        BackgroundThread.getHandler().post(new Runnable() { // from class: com.samsung.android.core.CoreSaLogger$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                CoreSaLogger.lambda$sendSaLoggingBroadcast$0(str4, str5, j, map, str6, str2, str, str3, context);
            }
        });
    }

    static /* synthetic */ void lambda$sendSaLoggingBroadcast$0(String str, String str2, long j, HashMap map, String str3, String str4, String str5, String str6, Context context) {
        Bundle bundle = new Bundle();
        bundle.putString("tracking_id", str4);
        if (!TextUtils.isEmpty(str5)) {
            bundle.putString(PAGE_ID_KEY, str5);
        }
        if (!TextUtils.isEmpty(str6)) {
            bundle.putString(SCREEN_ID_KEY, str6);
        }
        bundle.putString("feature", str);
        if (str2 != null) {
            bundle.putString("extra", str2);
        }
        if (j != -1) {
            bundle.putLong("value", j);
        }
        if (map != null) {
            bundle.putSerializable("dimension", map);
        }
        if (ADVANCED_TRACKING_ID.equals(str4) && !"None".equals(str3)) {
            HashMap<String, String> map2 = sCustomDimensionForMode;
            map2.put("mode", str3);
            bundle.putSerializable("dimension", map2);
        }
        bundle.putString("type", "ev");
        bundle.putString("pkg_name", "com.samsung.android.appcore");
        if (CoreSaConstant.SPLIT_EVENT_APP_PAIR_ID.equals(str) || CoreSaConstant.SPLIT_EVENT_DISMISS_APP_ID.equals(str)) {
            HashMap map3 = new HashMap();
            map3.put("MULTI_WIN_APP_RECOMMEND", new String[]{"extra"});
            bundle.putSerializable("personalizedData", map3);
        } else if (CoreSaConstant.FREEFORM_EVENT_OPEN_ID.equals(str) || CoreSaConstant.DEX_FREEFORM_EVENT_OPEN_ID.equals(str)) {
            HashMap map4 = new HashMap();
            map4.put(DIMENSION_VALUE_KEY1, String.valueOf(j));
            bundle.putSerializable("dimension", map4);
        }
        context.sendBroadcastAsUser(new Intent("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY").setPackage("com.sec.android.diagmonagent").putExtras(bundle).addFlags(67108864), UserHandle.CURRENT_OR_SELF);
        if (CoreRune.MW_SA_RUNESTONE_LOGGING) {
            RunestoneLogger.interpretSaToRunestone(context, str, str2);
        }
    }

    private static void sendSettingLogToServer(String str, String str2, String str3) {
        if (IS_FACTORY_BINARY) {
            Log.d(TAG, "Does't send Logging, It's FactoryBinary");
            return;
        }
        try {
            ActivityTaskManager.getService().sendSaLoggingBroadcastForSetting(str, str2, str3);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to sendSaLoggingBroadcastForSetting", e);
        }
    }

    public static void sendSaLoggingBroadcastForSetting(final Context context, final String str, final String str2, final String str3) {
        if (str == null || str2 == null) {
            Log.d(TAG, "Null trackingId or settingId");
        } else {
            BackgroundThread.getHandler().post(new Runnable() { // from class: com.samsung.android.core.CoreSaLogger$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    CoreSaLogger.lambda$sendSaLoggingBroadcastForSetting$1(str, str2, str3, context);
                }
            });
        }
    }

    static /* synthetic */ void lambda$sendSaLoggingBroadcastForSetting$1(String str, String str2, String str3, Context context) {
        HashMap<String, String> mapPutToSettingMap = putToSettingMap(str, str2, str3);
        if (mapPutToSettingMap == null) {
            Log.w(TAG, "Null setting");
        } else {
            sendSaLoggingBroadcastForSetting(context, str, mapPutToSettingMap, false);
        }
    }

    public static void sendSaLoggingBroadcastForBasicSetting(final Context context, final HashMap<String, String> map) {
        BackgroundThread.getHandler().post(new Runnable() { // from class: com.samsung.android.core.CoreSaLogger$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CoreSaLogger.sendSaLoggingBroadcastForSetting(context, CoreSaLogger.BASIC_TRACKING_ID, (HashMap<String, String>) map, true);
            }
        });
    }

    public static void sendSaLoggingBroadcastForAdvancedSetting(final Context context, final HashMap<String, String> map) {
        BackgroundThread.getHandler().post(new Runnable() { // from class: com.samsung.android.core.CoreSaLogger$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                CoreSaLogger.sendSaLoggingBroadcastForSetting(context, CoreSaLogger.ADVANCED_TRACKING_ID, (HashMap<String, String>) map, true);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void sendSaLoggingBroadcastForSetting(Context context, String str, HashMap<String, String> map, boolean z) {
        HashMap<String, String> settingMap;
        if (z && (settingMap = getSettingMap(str)) != null) {
            settingMap.putAll(map);
            map = settingMap;
        }
        Bundle bundle = new Bundle();
        bundle.putString("tracking_id", str);
        bundle.putString("pkg_name", "com.samsung.android.appcore");
        bundle.putString("type", "st");
        bundle.putSerializable(SETTING_KEY, map);
        context.sendBroadcastAsUser(new Intent("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY").setPackage("com.sec.android.diagmonagent").putExtras(bundle).addFlags(67108864), UserHandle.CURRENT_OR_SELF);
    }

    private static HashMap<String, String> putToSettingMap(String str, String str2, String str3) {
        HashMap<String, String> settingMap = getSettingMap(str);
        if (settingMap != null) {
            settingMap.put(str2, str3);
        }
        return settingMap;
    }

    private static HashMap<String, String> getSettingMap(String str) {
        str.hashCode();
        switch (str) {
            case "403-399-565756":
                return sSettingMapForDex;
            case "408-399-975257":
                return sSettingMapForAdvanced;
            case "4F4-399-995755":
                return sSettingMapForBasic;
            default:
                Log.d(TAG, "Invalid tracking id");
                return null;
        }
    }

    public static void logForBasic(String str) {
        sendLogToServer(BASIC_TRACKING_ID, null, null, str, null, -1L, null);
    }

    public static void logForBasic(String str, String str2) {
        sendLogToServer(BASIC_TRACKING_ID, null, null, str, str2, -1L, null);
    }

    public static void logForBasic(String str, int i) {
        sendLogToServer(BASIC_TRACKING_ID, null, null, str, null, i, null);
    }

    public static void logForBasic(String str, HashMap<String, String> map) {
        sendLogToServer(BASIC_TRACKING_ID, null, null, str, null, -1L, map);
    }

    public static void logSettingStatusForBasic(String str, String str2) {
        sendSettingLogToServer(BASIC_TRACKING_ID, str, str2);
    }

    public static void logForAdvanced(String str) {
        sendLogToServer(ADVANCED_TRACKING_ID, null, null, str, null, -1L, null);
    }

    public static void logForAdvanced(String str, String str2) {
        sendLogToServer(ADVANCED_TRACKING_ID, null, null, str, str2, -1L, null);
    }

    public static void logForAdvanced(String str, String str2, int i) {
        sendLogToServer(ADVANCED_TRACKING_ID, null, null, str, str2, i, null);
    }

    public static void logForAdvanced(String str, String str2, HashMap<String, String> map) {
        sendLogToServer(ADVANCED_TRACKING_ID, null, null, str, str2, -1L, map);
    }

    public static void logSettingStatusForAdvanced(String str, String str2) {
        sendSettingLogToServer(ADVANCED_TRACKING_ID, str, str2);
    }

    public static void logForDexMW(String str, String str2) {
        sendLogToServer(ADVANCED_TRACKING_ID, null, null, str, str2, -1L, null);
    }

    public static void logForDexMW(String str, String str2, int i) {
        sendLogToServer(ADVANCED_TRACKING_ID, null, null, str, str2, i, null);
    }

    public static void logForSystemUI(String str, HashMap<String, String> map) {
        sendLogToServer(SYSTEMUI_TRACKING_ID, null, null, str, null, -1L, map);
    }

    public static void logForDexWithScreenId(String str, String str2) {
        sendLogToServer(ADVANCED_TRACKING_ID, null, str, str2, null, -1L, null);
    }

    public static void logForDexWithScreenId(String str, String str2, String str3) {
        sendLogToServer(ADVANCED_TRACKING_ID, null, str, str2, str3, -1L, null);
    }

    public static void logForDexWithScreenId(String str, String str2, long j) {
        sendLogToServer(ADVANCED_TRACKING_ID, null, str, str2, null, j, null);
    }

    public static void logForDexWithScreenId(String str, String str2, String str3, int i) {
        sendLogToServer(ADVANCED_TRACKING_ID, null, str, str2, str3, i, null);
    }

    public static void logForDexWithScreenId(String str, String str2, HashMap<String, String> map) {
        sendLogToServer(ADVANCED_TRACKING_ID, null, str, str2, null, -1L, map);
    }

    public static void logForMWWithPageId(String str, String str2) {
        sendLogToServer(ADVANCED_TRACKING_ID, str, null, str2, null, -1L, null);
    }

    public static void logForMWWithPageId(String str, String str2, long j) {
        sendLogToServer(ADVANCED_TRACKING_ID, str, null, str2, null, j, null);
    }

    public static void logForMWWithPageId(String str, String str2, HashMap<String, String> map) {
        sendLogToServer(ADVANCED_TRACKING_ID, str, null, str2, null, -1L, map);
    }
}
