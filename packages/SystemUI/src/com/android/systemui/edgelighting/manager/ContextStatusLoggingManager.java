package com.android.systemui.edgelighting.manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Slog;
import com.android.systemui.edgelighting.Feature;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;
import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ContextStatusLoggingManager {
    public static ContextStatusLoggingManager mInstance;
    public final String TAG = "ContextStatusLoggingManager";
    public long mLastUpdateTime = -1;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class StatusLoggingTask extends AsyncTask {
        public final Context mTaskContext;

        public StatusLoggingTask(Context context) {
            this.mTaskContext = context;
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            try {
                sendEdgeLightingSettingsLogging(this.mTaskContext);
                ContextStatusLoggingManager.m1937$$Nest$msendEdgeLightingStatusLogging(ContextStatusLoggingManager.this, this.mTaskContext);
                return null;
            } catch (Exception e) {
                Slog.e(ContextStatusLoggingManager.this.TAG, "ContextStatusLoggingManager.doInBackground() : " + e.toString());
                e.printStackTrace();
                return null;
            }
        }

        public final void sendEdgeLightingSettingsLogging(Context context) {
            ContentValues makeLoggingContentValue;
            ContentValues makeLoggingContentValue2;
            boolean z = Feature.FEATURE_SUPPORT_EDGE_LIGHTING;
            if (z) {
                if (EdgeLightingSettingUtils.isEdgeLightingEnabled(context.getContentResolver())) {
                    ContextStatusLoggingManager.this.getClass();
                    makeLoggingContentValue2 = ContextStatusLoggingManager.makeLoggingContentValue("EL01", null, "1000");
                } else {
                    ContextStatusLoggingManager.this.getClass();
                    makeLoggingContentValue2 = ContextStatusLoggingManager.makeLoggingContentValue("EL01", null, "0");
                }
                ContextStatusLoggingManager.this.sendStatusContextLogging(context, makeLoggingContentValue2);
            }
            if (z) {
                boolean isEdgeLightingEnabled = EdgeLightingSettingUtils.isEdgeLightingEnabled(context.getContentResolver());
                int intForUser = Settings.System.getIntForUser(context.getContentResolver(), "edge_lighting_show_condition", !Feature.FEATURE_SUPPORT_AOD ? 1 : 0, -2);
                if (!isEdgeLightingEnabled) {
                    ContextStatusLoggingManager.this.getClass();
                    makeLoggingContentValue = ContextStatusLoggingManager.makeLoggingContentValue("EL02", "Off", null);
                } else if (intForUser == 0) {
                    ContextStatusLoggingManager.this.getClass();
                    makeLoggingContentValue = ContextStatusLoggingManager.makeLoggingContentValue("EL02", "Always", null);
                } else if (intForUser == 1) {
                    ContextStatusLoggingManager.this.getClass();
                    makeLoggingContentValue = ContextStatusLoggingManager.makeLoggingContentValue("EL02", "When screen is on", null);
                } else {
                    if (intForUser != 2) {
                        return;
                    }
                    ContextStatusLoggingManager.this.getClass();
                    makeLoggingContentValue = ContextStatusLoggingManager.makeLoggingContentValue("EL02", "When screen is off", null);
                }
                ContextStatusLoggingManager.this.sendStatusContextLogging(context, makeLoggingContentValue);
            }
        }
    }

    /* renamed from: -$$Nest$msendEdgeLightingStatusLogging, reason: not valid java name */
    public static void m1937$$Nest$msendEdgeLightingStatusLogging(ContextStatusLoggingManager contextStatusLoggingManager, Context context) {
        contextStatusLoggingManager.getClass();
        ContentValues makeLoggingContentValue = makeLoggingContentValue("EL13", Integer.toString(Settings.System.getIntForUser(context.getContentResolver(), "edge_lighting_color_type", 1, -2) + 1), null);
        ContentValues makeLoggingContentValue2 = makeLoggingContentValue("EL14", Integer.toString(Settings.System.getIntForUser(context.getContentResolver(), "edge_lighting_transparency", 0, -2)), null);
        ContentValues makeLoggingContentValue3 = makeLoggingContentValue("EL15", Integer.toString(Settings.System.getIntForUser(context.getContentResolver(), "edge_lighting_thickness", 0, -2) + 1), null);
        ContentValues makeLoggingContentValue4 = makeLoggingContentValue("EL20", Integer.toString(EdgeLightingSettingUtils.getEdgeLightingBasicColorIndex(context.getContentResolver())), null);
        ContentValues makeLoggingContentValue5 = makeLoggingContentValue("EL21", Integer.toString(EdgeLightingStyleManager.getInstance().getPreloadIndex(EdgeLightingStyleManager.getInstance().getEdgeLightingStyleType(context.getContentResolver())) + 1), null);
        ContentValues makeLoggingContentValue6 = makeLoggingContentValue("EL22", EdgeLightingStyleManager.getInstance().getEdgeLightingStyleType(context.getContentResolver()), null);
        contextStatusLoggingManager.sendStatusContextLogging(context, makeLoggingContentValue);
        contextStatusLoggingManager.sendStatusContextLogging(context, makeLoggingContentValue2);
        contextStatusLoggingManager.sendStatusContextLogging(context, makeLoggingContentValue3);
        contextStatusLoggingManager.sendStatusContextLogging(context, makeLoggingContentValue4);
        contextStatusLoggingManager.sendStatusContextLogging(context, makeLoggingContentValue5);
        contextStatusLoggingManager.sendStatusContextLogging(context, makeLoggingContentValue6);
    }

    private ContextStatusLoggingManager() {
    }

    public static ContextStatusLoggingManager getInstance() {
        if (mInstance == null) {
            mInstance = new ContextStatusLoggingManager();
        }
        return mInstance;
    }

    public static ContentValues makeLoggingContentValue(String str, String str2, String str3) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", "com.samsung.android.app.cocktailbarservice");
        contentValues.put("feature", str);
        if (str2 != null) {
            contentValues.put("extra", str2);
        }
        if (str3 != null) {
            contentValues.put("value", str3);
        }
        return contentValues;
    }

    public final void sendStatusContextLogging(Context context, ContentValues contentValues) {
        boolean z = Feature.FEATURE_CONTEXTSERVICE_ENABLE_SURVEY;
        String str = this.TAG;
        if (!z) {
            SemLog.i(str, "sendContextServiceLog -  servey mode feature not enabled");
            return;
        }
        StringBuffer stringBuffer = new StringBuffer("sendStatusContextLogging: ");
        stringBuffer.append(contentValues);
        SemLog.i(str, stringBuffer.toString());
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.providers.context.log.action.REPORT_APP_STATUS_SURVEY");
        intent.putExtra("data", contentValues);
        intent.setPackage("com.samsung.android.providers.context");
        context.sendBroadcast(intent);
    }

    public final void updateStatusLoggingItem(Context context) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mLastUpdateTime > 259200000) {
            Slog.d(this.TAG, "updateStatusLoggingItem: on " + currentTimeMillis);
            this.mLastUpdateTime = currentTimeMillis;
            new StatusLoggingTask(context).execute(null, null, null);
        }
    }
}
