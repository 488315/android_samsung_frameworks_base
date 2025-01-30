package com.samsung.context.sdk.samsunganalytics;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.UserManager;
import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.SamsungAnalytics;
import com.samsung.context.sdk.samsunganalytics.internal.Tracker;
import com.samsung.context.sdk.samsunganalytics.internal.executor.SingleThreadExecutor;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.policy.Validation;
import com.samsung.context.sdk.samsunganalytics.internal.setting.SettingLogBuildClient;
import com.samsung.context.sdk.samsunganalytics.internal.setting.SettingRegisterClient;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SamsungAnalytics {
    public static SamsungAnalytics instance;
    public final Tracker tracker;

    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:6:0x00e7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private SamsungAnalytics(final Application application, final Configuration configuration) {
        boolean z;
        boolean z2;
        this.tracker = null;
        boolean z3 = false;
        if (application == null) {
            Utils.throwException("context cannot be null");
        } else if (configuration == null) {
            Utils.throwException("Configuration cannot be null");
        } else if (TextUtils.isEmpty(configuration.trackingId)) {
            Utils.throwException("TrackingId is empty, set TrackingId");
        } else if (!TextUtils.isEmpty(configuration.deviceId) || configuration.enableAutoDeviceId) {
            if (PolicyUtils.senderType == -1) {
                int dMAversion = Utils.getDMAversion(application);
                if (dMAversion < 540000000) {
                    PolicyUtils.senderType = -1;
                } else if (dMAversion >= 600000000) {
                    PolicyUtils.senderType = 3;
                } else {
                    PolicyUtils.senderType = 2;
                }
            }
            int i = PolicyUtils.senderType;
            if (-1 == i) {
                Debug.LogD("SenderType is None");
            } else {
                if (i == 2) {
                    try {
                        String[] strArr = application.getPackageManager().getPackageInfo(application.getPackageName(), 4096).requestedPermissions;
                        if (strArr != null) {
                            for (String str : strArr) {
                                if (str.startsWith("com.sec.spp.permission.TOKEN")) {
                                    z2 = true;
                                    break;
                                }
                            }
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        Debug.LogException(Validation.class, e);
                    }
                    z2 = false;
                    if (!z2) {
                        Utils.throwException("SamsungAnalytics2 need to define 'com.sec.spp.permission.TOKEN_XXXX' permission in AndroidManifest");
                    }
                }
                if (!TextUtils.isEmpty(configuration.deviceId)) {
                    Utils.throwException("This mode is not allowed to set device Id");
                } else if (TextUtils.isEmpty(configuration.version)) {
                    Utils.throwException("you should set the UI version");
                } else {
                    UserManager userManager = (UserManager) application.getSystemService("user");
                    if (userManager == null || userManager.isUserUnlocked()) {
                        z = true;
                        if (z) {
                            return;
                        }
                        configuration.getClass();
                        SharedPreferences sharedPreferences = application.getSharedPreferences("SamsungAnalyticsPrefs", 0);
                        int i2 = sharedPreferences.getInt("enable_device", 0);
                        if (i2 == 0) {
                            try {
                                Class<?> cls = Class.forName("com.samsung.android.feature.SemFloatingFeature");
                                z3 = ((Boolean) cls.getMethod("getBoolean", String.class).invoke(cls.getMethod("getInstance", null).invoke(null, new Object[0]), "SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE")).booleanValue();
                            } catch (Exception e2) {
                                try {
                                    Cursor query = application.getContentResolver().query(Uri.parse("content://com.sec.android.log.diagmonagent.sa/check/diagnostic"), null, null, null);
                                    if (query != null) {
                                        try {
                                            query.moveToNext();
                                            if (1 == query.getInt(0)) {
                                                z3 = true;
                                            }
                                        } finally {
                                            query.close();
                                        }
                                    }
                                    if (query != null) {
                                    }
                                } catch (Exception unused) {
                                    Debug.LogD("DMA is not supported");
                                    Debug.LogException(Validation.class, e2);
                                }
                            }
                            if (z3) {
                                Debug.LogD("cf feature is supported");
                                sharedPreferences.edit().putInt("enable_device", 1).apply();
                            } else {
                                Debug.LogD("feature is not supported");
                                sharedPreferences.edit().putInt("enable_device", 2).apply();
                            }
                        } else if (i2 == 1) {
                            z3 = true;
                        }
                        if (z3) {
                            this.tracker = new Tracker(application, configuration);
                            return;
                        }
                        return;
                    }
                    Debug.LogE("The user has not unlocked the device.");
                    BroadcastReceiver c47891 = new BroadcastReceiver() { // from class: com.samsung.context.sdk.samsunganalytics.internal.policy.Validation.1
                        public final /* synthetic */ Configuration val$configuration;
                        public final /* synthetic */ Application val$context;

                        public C47891(final Application application2, final Configuration configuration2) {
                            r1 = application2;
                            r2 = configuration2;
                        }

                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            Debug.LogD("receive " + intent.getAction());
                            SamsungAnalytics.getInstanceAndConfig(r1, r2);
                        }
                    };
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
                    intentFilter.addAction("android.intent.action.USER_UNLOCKED");
                    application2.registerReceiver(c47891, intentFilter);
                }
            }
        } else {
            Utils.throwException("Device Id is empty, set Device Id or enable auto device id");
        }
        z = false;
        if (z) {
        }
    }

    public static SamsungAnalytics getInstance() {
        if (instance == null) {
            Utils.throwException("call after setConfiguration() method");
            if (!Build.TYPE.equals("eng")) {
                return getInstanceAndConfig(null, null);
            }
        }
        return instance;
    }

    public static SamsungAnalytics getInstanceAndConfig(Application application, Configuration configuration) {
        SamsungAnalytics samsungAnalytics = instance;
        if (samsungAnalytics == null || samsungAnalytics.tracker == null) {
            synchronized (SamsungAnalytics.class) {
                instance = new SamsungAnalytics(application, configuration);
            }
        }
        return instance;
    }

    public final void registerSettingPref(Map map) {
        try {
            Tracker tracker = this.tracker;
            tracker.getClass();
            SingleThreadExecutor singleThreadExecutor = SingleThreadExecutor.getInstance();
            Context context = tracker.mContext;
            singleThreadExecutor.execute(new SettingRegisterClient(context, map));
            SingleThreadExecutor.getInstance().execute(new SettingLogBuildClient(context, tracker.configuration));
        } catch (NullPointerException e) {
            Debug.LogException(SamsungAnalytics.class, e);
        }
    }

    public final void sendLog(Map map) {
        try {
            this.tracker.sendLog(map);
        } catch (NullPointerException unused) {
        }
    }
}
