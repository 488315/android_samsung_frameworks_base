package com.samsung.context.sdk.samsunganalytics;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import com.samsung.context.sdk.samsunganalytics.internal.Tracker;
import com.samsung.context.sdk.samsunganalytics.internal.policy.Validation;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.util.Map;

/* loaded from: classes.dex */
public final class SamsungAnalytics {
    private static SamsungAnalytics instance;
    private Tracker tracker;

    private SamsungAnalytics(Application application, Configuration configuration) {
        this.tracker = null;
        if (Validation.isValidConfig(application, configuration)) {
            configuration.getClass();
            boolean z = false;
            SharedPreferences sharedPreferences = application.getSharedPreferences("SamsungAnalyticsPrefs", 0);
            int i = sharedPreferences.getInt("enable_device", 0);
            if (i == 0) {
                try {
                    Class<?> cls = Class.forName("com.samsung.android.feature.SemFloatingFeature");
                    z = ((Boolean) cls.getMethod("getBoolean", String.class).invoke(cls.getMethod("getInstance", null).invoke(null, new Object[0]), "SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE")).booleanValue();
                } catch (Exception e) {
                    try {
                        Cursor query = application.getContentResolver().query(Uri.parse("content://com.sec.android.log.diagmonagent.sa/check/diagnostic"), null, null, null);
                        if (query != null) {
                            try {
                                query.moveToNext();
                                if (1 == query.getInt(0)) {
                                    z = true;
                                }
                            } finally {
                                query.close();
                            }
                        }
                        if (query != null) {
                        }
                    } catch (Exception unused) {
                        Debug.LogD("DMA is not supported");
                        Debug.LogException(Validation.class, e);
                    }
                }
                if (z) {
                    Debug.LogD("cf feature is supported");
                    sharedPreferences.edit().putInt("enable_device", 1).apply();
                } else {
                    Debug.LogD("feature is not supported");
                    sharedPreferences.edit().putInt("enable_device", 2).apply();
                }
            } else if (i == 1) {
                z = true;
            }
            if (z) {
                this.tracker = new Tracker(application, configuration);
            }
        }
    }

    public static SamsungAnalytics getInstance() {
        if (instance == null) {
            Utils.throwException("call after setConfiguration() method");
            if (!Build.TYPE.equals("eng")) {
                SamsungAnalytics samsungAnalytics = instance;
                if (samsungAnalytics == null || samsungAnalytics.tracker == null) {
                    synchronized (SamsungAnalytics.class) {
                        instance = new SamsungAnalytics(null, null);
                    }
                }
                return instance;
            }
        }
        return instance;
    }

    public static void setConfiguration(Application application, Configuration configuration) {
        SamsungAnalytics samsungAnalytics = instance;
        if (samsungAnalytics == null || samsungAnalytics.tracker == null) {
            synchronized (SamsungAnalytics.class) {
                instance = new SamsungAnalytics(application, configuration);
            }
        }
    }

    public final void sendLog(Map map) {
        try {
            this.tracker.sendLog(map);
        } catch (NullPointerException unused) {
        }
    }
}
