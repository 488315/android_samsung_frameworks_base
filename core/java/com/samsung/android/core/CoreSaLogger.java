package com.samsung.android.core;

import android.app.ActivityTaskManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.FactoryTest;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;

import com.android.internal.os.BackgroundThread;

import java.util.HashMap;

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
    private static final String SA_ACTION =
            "com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY";
    private static final String SA_PACKAGE = "com.sec.android.diagmonagent";
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

    private static void sendLogToServer(
            String trackingId,
            String eventId,
            String eventDetail,
            long value,
            HashMap<String, String> customDimension) {
        if (IS_FACTORY_BINARY) {
            Log.d(TAG, "Does't send Logging, It's FactoryBinary");
            return;
        }
        try {
            ActivityTaskManager.getService()
                    .sendSaLoggingBroadcast(
                            trackingId, eventId, eventDetail, value, customDimension);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to sendSaLoggingBroadcast", e);
        }
    }

    public static void sendSaLoggingBroadcast(
            final Context context,
            final String trackingId,
            final String eventId,
            final String detail,
            final long value,
            final HashMap<String, String> customDimension,
            final String mode) {
        BackgroundThread.getHandler()
                .post(
                        new Runnable() { // from class:
                                         // com.samsung.android.core.CoreSaLogger$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                CoreSaLogger.lambda$sendSaLoggingBroadcast$0(
                                        eventId,
                                        detail,
                                        value,
                                        customDimension,
                                        mode,
                                        trackingId,
                                        context);
                            }
                        });
    }

    static /* synthetic */ void lambda$sendSaLoggingBroadcast$0(
            String eventId,
            String detail,
            long value,
            HashMap customDimension,
            String mode,
            String trackingId,
            Context context) {
        Bundle bundle = new Bundle();
        bundle.putString("tracking_id", trackingId);
        bundle.putString("feature", eventId);
        if (detail != null) {
            bundle.putString("extra", detail);
        }
        if (value != -1) {
            bundle.putLong("value", value);
        }
        if (customDimension != null) {
            bundle.putSerializable("dimension", customDimension);
        }
        if (ADVANCED_TRACKING_ID.equals(trackingId) && !"None".equals(mode)) {
            sCustomDimensionForMode.put("mode", mode);
            bundle.putSerializable("dimension", sCustomDimensionForMode);
        }
        bundle.putString("type", "ev");
        bundle.putString("pkg_name", "com.samsung.android.appcore");
        if (CoreSaConstant.SPLIT_EVENT_APP_PAIR_ID.equals(eventId)
                || CoreSaConstant.SPLIT_EVENT_DISMISS_APP_ID.equals(eventId)) {
            HashMap<String, String[]> personalizedData = new HashMap<>();
            personalizedData.put("MULTI_WIN_APP_RECOMMEND", new String[] {"extra"});
            bundle.putSerializable("personalizedData", personalizedData);
        } else if (CoreSaConstant.FREEFORM_EVENT_OPEN_ID.equals(eventId)
                || CoreSaConstant.DEX_FREEFORM_EVENT_OPEN_ID.equals(eventId)) {
            HashMap<String, String> dimension = new HashMap<>();
            dimension.put(DIMENSION_VALUE_KEY1, String.valueOf(value));
            bundle.putSerializable("dimension", dimension);
        }
        context.sendBroadcastAsUser(
                new Intent("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY")
                        .setPackage("com.sec.android.diagmonagent")
                        .putExtras(bundle)
                        .addFlags(67108864),
                UserHandle.CURRENT_OR_SELF);
    }

    private static void sendSettingLogToServer(String trackingId, String settingId, String value) {
        if (IS_FACTORY_BINARY) {
            Log.d(TAG, "Does't send Logging, It's FactoryBinary");
            return;
        }
        try {
            ActivityTaskManager.getService()
                    .sendSaLoggingBroadcastForSetting(trackingId, settingId, value);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to sendSaLoggingBroadcastForSetting", e);
        }
    }

    public static void sendSaLoggingBroadcastForSetting(
            final Context context,
            final String trackingId,
            final String settingId,
            final String value) {
        if (trackingId == null || settingId == null) {
            Log.d(TAG, "Null trackingId or settingId");
        } else {
            BackgroundThread.getHandler()
                    .post(
                            new Runnable() { // from class:
                                             // com.samsung.android.core.CoreSaLogger$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CoreSaLogger.lambda$sendSaLoggingBroadcastForSetting$1(
                                            trackingId, settingId, value, context);
                                }
                            });
        }
    }

    static /* synthetic */ void lambda$sendSaLoggingBroadcastForSetting$1(
            String trackingId, String settingId, String value, Context context) {
        HashMap<String, String> setting = putToSettingMap(trackingId, settingId, value);
        if (setting == null) {
            Log.w(TAG, "Null setting");
        } else {
            sendSaLoggingBroadcastForSetting(context, trackingId, setting);
        }
    }

    public static void sendSaLoggingBroadcastForBasicSetting(
            final Context context, final HashMap<String, String> setting) {
        BackgroundThread.getHandler()
                .post(
                        new Runnable() { // from class:
                                         // com.samsung.android.core.CoreSaLogger$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                CoreSaLogger.lambda$sendSaLoggingBroadcastForBasicSetting$2(
                                        setting, context);
                            }
                        });
    }

    static /* synthetic */ void lambda$sendSaLoggingBroadcastForBasicSetting$2(
            HashMap setting, Context context) {
        HashMap<String, String> settingMapForBasic = getSettingMap(BASIC_TRACKING_ID);
        if (settingMapForBasic != null) {
            settingMapForBasic.putAll(setting);
        }
        sendSaLoggingBroadcastForSetting(context, BASIC_TRACKING_ID, setting);
    }

    private static void sendSaLoggingBroadcastForSetting(
            Context context, String trackingId, HashMap<String, String> setting) {
        Bundle bundle = new Bundle();
        bundle.putString("tracking_id", trackingId);
        bundle.putString("pkg_name", "com.samsung.android.appcore");
        bundle.putString("type", "st");
        bundle.putSerializable(SETTING_KEY, setting);
        context.sendBroadcastAsUser(
                new Intent("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY")
                        .setPackage("com.sec.android.diagmonagent")
                        .putExtras(bundle)
                        .addFlags(67108864),
                UserHandle.CURRENT_OR_SELF);
    }

    private static HashMap<String, String> putToSettingMap(
            String trackingId, String settingId, String value) {
        HashMap<String, String> setting = getSettingMap(trackingId);
        if (setting != null) {
            setting.put(settingId, value);
        }
        return setting;
    }

    private static HashMap<String, String> getSettingMap(String trackingId) {
        char c;
        switch (trackingId.hashCode()) {
            case -953029196:
                if (trackingId.equals(DEX_TRACKING_ID)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -192180280:
                if (trackingId.equals(ADVANCED_TRACKING_ID)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1428409127:
                if (trackingId.equals(BASIC_TRACKING_ID)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                HashMap<String, String> setting = sSettingMapForBasic;
                return setting;
            case 1:
                HashMap<String, String> setting2 = sSettingMapForAdvanced;
                return setting2;
            case 2:
                HashMap<String, String> setting3 = sSettingMapForDex;
                return setting3;
            default:
                Log.d(TAG, "Invalid tracking id");
                return null;
        }
    }

    public static void logForBasic(String eventId) {
        sendLogToServer(BASIC_TRACKING_ID, eventId, null, -1L, null);
    }

    public static void logForBasic(String eventId, String detail) {
        sendLogToServer(BASIC_TRACKING_ID, eventId, detail, -1L, null);
    }

    public static void logForBasic(String eventId, int value) {
        sendLogToServer(BASIC_TRACKING_ID, eventId, null, value, null);
    }

    public static void logForBasic(String eventId, HashMap<String, String> customDimension) {
        sendLogToServer(BASIC_TRACKING_ID, eventId, null, -1L, customDimension);
    }

    public static void logSettingStatusForBasic(String settingId, String value) {
        sendSettingLogToServer(BASIC_TRACKING_ID, settingId, value);
    }

    public static void logForAdvanced(String eventId) {
        sendLogToServer(ADVANCED_TRACKING_ID, eventId, null, -1L, null);
    }

    public static void logForAdvanced(String eventId, String detail) {
        sendLogToServer(ADVANCED_TRACKING_ID, eventId, detail, -1L, null);
    }

    public static void logForAdvanced(String eventId, String detail, int value) {
        sendLogToServer(ADVANCED_TRACKING_ID, eventId, detail, value, null);
    }

    public static void logForAdvanced(String eventId, String detail, HashMap<String, String> map) {
        sendLogToServer(ADVANCED_TRACKING_ID, eventId, detail, -1L, map);
    }

    public static void logSettingStatusForAdvanced(String settingId, String value) {
        sendSettingLogToServer(ADVANCED_TRACKING_ID, settingId, value);
    }

    public static void logForDexMW(String eventId, String detail) {
        sendLogToServer(DEX_TRACKING_ID, eventId, detail, -1L, null);
    }

    public static void logForDexMW(String eventId, String detail, int value) {
        sendLogToServer(DEX_TRACKING_ID, eventId, detail, value, null);
    }

    public static void logForSystemUI(String eventId, HashMap<String, String> customDimension) {
        sendLogToServer(SYSTEMUI_TRACKING_ID, eventId, null, -1L, customDimension);
    }
}
