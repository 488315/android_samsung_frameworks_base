package com.samsung.android.knox.analytics.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.UserHandle;
import android.sec.enterprise.proxy.EnterpriseProxyConstants;

/* loaded from: classes6.dex */
public class UploaderBroadcaster {
    private static final String ACTIVATE_KNOX_ANALYTICS_DEVICE_STATUS = "com.samsung.android.knox.intent.action.ACTIVATE_KNOX_ANALYTICS_DEVICE_STATUS";
    private static final String BROADCAST_DB_SIZE_WARNING_TO_UPLOADER_ACTION = "com.samsung.android.knox.analytics.intent.action.DB_SIZE_WARNING_USAGE_INTERNAL";
    private static final String BROADCAST_STATUS_TO_UPLOADER_ACTION = "com.samsung.android.knox.analytics.intent.action.STATUS_INTERNAL";
    private static final String BROADCAST_STATUS_TO_UPLOADER_EXTRA_KEY = "com.samsung.android.knox.analytics.intent.extra.STATUS_INTERNAL";
    private static final String BROADCAST_STATUS_TO_UPLOADER_OFF_FORCEFUL_VALUE = "OFF_FORCEFUL";
    private static final String BROADCAST_STATUS_TO_UPLOADER_OFF_VALUE = "OFF";
    private static final String BROADCAST_STATUS_TO_UPLOADER_ON_VALUE = "ON";
    private static final String DEACTIVATE_KNOX_ANALYTICS_DEVICE_STATUS = "com.samsung.android.knox.intent.action.DEACTIVATE_KNOX_ANALYTICS_DEVICE_STATUS";
    private static final String TAG = "[KnoxAnalytics] " + UploaderBroadcaster.class.getSimpleName();
    public static final String UPLOADER_COMPONENT = "com.samsung.android.knox.analytics.uploader.service.ServiceReceiver";
    public static final String UPLOADER_PACKAGENAME = "com.samsung.android.knox.analytics.uploader";

    public static void broadcastAnalyticsStatus(Context context, boolean isAnalyticsStarted, boolean isForceShutdown) {
        Log.d(TAG, "broadcastAnalyticsStatus(" + String.valueOf(isAnalyticsStarted) + "," + String.valueOf(isForceShutdown) + NavigationBarInflaterView.KEY_CODE_END);
        Intent intent = new Intent(BROADCAST_STATUS_TO_UPLOADER_ACTION);
        intent.setComponent(new ComponentName(UPLOADER_PACKAGENAME, UPLOADER_COMPONENT)).putExtra(BROADCAST_STATUS_TO_UPLOADER_EXTRA_KEY, getStatusValue(isAnalyticsStarted, isForceShutdown));
        context.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
    }

    public static void broadcastKAStatusToMDM(Context context, boolean start, boolean isNewActivation, boolean isForceful) {
        Intent intent;
        Log.d(TAG, "broadcastKAServiceStatusToMDM( start:" + String.valueOf(start) + " , isNewActivation:" + String.valueOf(isNewActivation) + " , isForceful:" + String.valueOf(isForceful) + NavigationBarInflaterView.KEY_CODE_END);
        if (start) {
            intent = new Intent(ACTIVATE_KNOX_ANALYTICS_DEVICE_STATUS);
            intent.setPackage(EnterpriseProxyConstants.ENTERPRISE_AUTH_DIALOG_PACKAGE).putExtra("isNewActivation", isNewActivation);
        } else {
            intent = new Intent(DEACTIVATE_KNOX_ANALYTICS_DEVICE_STATUS);
            intent.setPackage(EnterpriseProxyConstants.ENTERPRISE_AUTH_DIALOG_PACKAGE).putExtra("isForceful", isForceful);
        }
        context.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
    }

    private static String getStatusValue(boolean isAnalyticsStarted, boolean isForceShutdown) {
        if (isAnalyticsStarted) {
            return BROADCAST_STATUS_TO_UPLOADER_ON_VALUE;
        }
        if (isForceShutdown) {
            return BROADCAST_STATUS_TO_UPLOADER_OFF_FORCEFUL_VALUE;
        }
        return "OFF";
    }

    public static void broadcastDbSizeWarning(Context context) {
        Log.d(TAG, "broadcastDbSizeWarning()");
        Intent intent = new Intent(BROADCAST_DB_SIZE_WARNING_TO_UPLOADER_ACTION);
        intent.setComponent(new ComponentName(UPLOADER_PACKAGENAME, UPLOADER_COMPONENT));
        context.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
    }
}
