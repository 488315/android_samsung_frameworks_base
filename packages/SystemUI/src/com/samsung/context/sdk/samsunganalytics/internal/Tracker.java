package com.samsung.context.sdk.samsunganalytics.internal;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Trace;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.connection.Directory;
import com.samsung.context.sdk.samsunganalytics.internal.connection.Domain;
import com.samsung.context.sdk.samsunganalytics.internal.device.DeviceInfo;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.policy.Validation;
import com.samsung.context.sdk.samsunganalytics.internal.sender.DMA.DMALogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.Sender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.Manager;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.database.DbManager;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.queue.QueueManager;
import com.samsung.context.sdk.samsunganalytics.internal.terms.RegisterLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.terms.RegisterTask;
import com.samsung.context.sdk.samsunganalytics.internal.terms.RegisterType;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.DeleteApiCallChecker;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import com.sec.android.diagmonagent.common.util.CommonUtils;
import com.sec.android.diagmonagent.common.util.executor.AsyncTaskCallback;
import com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient;
import com.sec.android.diagmonagent.common.util.executor.SingleThreadExecutor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public class Tracker {
    public final Application application;
    public final Configuration configuration;
    public final DeleteApiCallChecker deleteApiCallChecker;
    public final Context mContext;
    public int mStatus = 0;

    /* renamed from: com.samsung.context.sdk.samsunganalytics.internal.Tracker$1, reason: invalid class name */
    public class AnonymousClass1 {
        public AnonymousClass1() {
        }

        public final boolean isAgreement() {
            return Settings.System.getInt(Tracker.this.mContext.getContentResolver(), "samsung_errorlog_agree", 0) == 1;
        }
    }

    public Tracker(final Application application, final Configuration configuration) {
        Trace.beginSection("Tracker Constructor");
        this.application = application;
        this.configuration = configuration;
        Context applicationContext = application.getApplicationContext();
        this.mContext = applicationContext;
        this.deleteApiCallChecker = new DeleteApiCallChecker(applicationContext);
        configuration.getClass();
        configuration.userAgreement = new AnonymousClass1();
        Trace.beginAsyncSection("Tracker Constructor SingleThreadExecutor", -757204973);
        SingleThreadExecutor.getInstance().execute(new AsyncTaskClient() { // from class: com.samsung.context.sdk.samsunganalytics.internal.Tracker.2
            @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
            public final int onFinish() {
                return 0;
            }

            /* JADX WARN: Removed duplicated region for block: B:17:0x0086 A[LOOP:0: B:15:0x007e->B:17:0x0086, LOOP_END] */
            /* JADX WARN: Removed duplicated region for block: B:20:0x00c8  */
            /* JADX WARN: Removed duplicated region for block: B:31:0x00c2 A[EDGE_INSN: B:31:0x00c2->B:18:0x00c2 BREAK  A[LOOP:0: B:15:0x007e->B:17:0x0086], SYNTHETIC] */
            @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void run() {
                final RegisterLogSender registerLogSender;
                Iterator<Map.Entry<String, ?>> it;
                boolean zHasNext;
                Configuration configuration2;
                RegisterType registerType;
                RegisterType registerType2;
                Tracker tracker = Tracker.this;
                if (Tracker.access$100(tracker)) {
                    Context context = tracker.mContext;
                    String packageVersion = CommonUtils.getPackageVersion(context);
                    String string = Preferences.getPreferences(context).getString("appVersionForInit", "");
                    boolean zIsEmpty = TextUtils.isEmpty(string);
                    Configuration configuration3 = configuration;
                    if (!zIsEmpty) {
                        if (!string.equals(packageVersion) && !TextUtils.isEmpty(packageVersion)) {
                            context.getSharedPreferences("SamsungAnalyticsPrefs", 0).edit().putString("appVersionForInit", packageVersion).apply();
                        }
                        Application application2 = application;
                        RegisterType registerType3 = RegisterType.SEND_PREVIOUS_REGISTRATION_INFO;
                        Trace.beginSection("RegisterLogSender sendLog");
                        registerLogSender = new RegisterLogSender(application2, configuration3, registerType3);
                        it = registerLogSender.application.getSharedPreferences("SATerms", 0).getAll().entrySet().iterator();
                        while (true) {
                            zHasNext = it.hasNext();
                            configuration2 = registerLogSender.config;
                            if (zHasNext) {
                                break;
                            }
                            Map.Entry<String, ?> next = it.next();
                            final String key = next.getKey();
                            final long jLongValue = ((Long) next.getValue()).longValue();
                            Debug.LogD("Send previous agreement, timestamp : " + jLongValue);
                            SingleThreadExecutor.getInstance().execute(new RegisterTask(configuration2.trackingId, key, jLongValue, new AsyncTaskCallback() { // from class: com.samsung.context.sdk.samsunganalytics.internal.terms.RegisterLogSender.1
                                public final /* synthetic */ String val$deviceId;
                                public final /* synthetic */ long val$timestamp;

                                public AnonymousClass1(final String key2, final long jLongValue2) {
                                    str = key2;
                                    j = jLongValue2;
                                }

                                @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskCallback
                                public final void onFail(String str, String str2, String str3) {
                                    RegisterLogSender.this.application.getSharedPreferences("SATerms", 0).edit().putLong(str, j).apply();
                                    sendRegistrationResult(false);
                                }

                                @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskCallback
                                public final void onSuccess() {
                                    RegisterLogSender.this.application.getSharedPreferences("SATerms", 0).edit().remove(str).apply();
                                    sendRegistrationResult(true);
                                }

                                public final void sendRegistrationResult(boolean z) {
                                    RegisterLogSender registerLogSender2 = RegisterLogSender.this;
                                    if (910701000 <= CommonUtils.getDMAVersion(registerLogSender2.application.getApplicationContext())) {
                                        Uri uri = Uri.parse("content://com.sec.android.log.diagmonagent.sa/registrationHistory");
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put("tid", registerLogSender2.config.trackingId);
                                        contentValues.put("eventTimestamp", Long.valueOf(j));
                                        contentValues.put("sendTimestamp", Long.valueOf(System.currentTimeMillis()));
                                        contentValues.put("apiType", (Integer) 11);
                                        contentValues.put("result", Boolean.valueOf(z));
                                        try {
                                            registerLogSender2.application.getApplicationContext().getContentResolver().insert(uri, contentValues);
                                        } catch (Exception e) {
                                            Debug.logwingW("Send registration result failed : " + e.getMessage());
                                        }
                                    }
                                }
                            }));
                        }
                        registerType = RegisterType.SEND_PREVIOUS_REGISTRATION_INFO;
                        registerType2 = registerLogSender.registerType;
                        if (registerType2 != registerType) {
                            if (PolicyUtils.senderType >= 2) {
                                Debug.LogD("Send broadcast for " + registerType2.getAction() + ", tid : " + configuration2.trackingId);
                                Application application3 = registerLogSender.application;
                                Intent intent = new Intent();
                                intent.setPackage("com.sec.android.diagmonagent");
                                intent.setAction(registerType2.getAction());
                                intent.putExtra("tid", configuration2.trackingId);
                                intent.putExtra("agree", false);
                                if (registerType2 == RegisterType.DELETE_SENSITIVE_APP_DATA) {
                                    ArrayList<Integer> arrayList = new ArrayList<>();
                                    arrayList.add(10);
                                    arrayList.add(11);
                                    intent.putIntegerArrayListExtra("event_type", arrayList);
                                }
                                application3.sendBroadcast(intent);
                                if (PolicyUtils.senderType == 2) {
                                    ((DMALogSender) Sender.get(registerLogSender.application, 2, configuration2)).isReset = true;
                                }
                            }
                            final long jCurrentTimeMillis = System.currentTimeMillis();
                            Debug.LogD("Send agreement, timestamp : " + jCurrentTimeMillis);
                            SingleThreadExecutor singleThreadExecutor = SingleThreadExecutor.getInstance();
                            String str = configuration2.trackingId;
                            final String str2 = registerLogSender.deviceId;
                            singleThreadExecutor.execute(new RegisterTask(str, str2, jCurrentTimeMillis, new AsyncTaskCallback() { // from class: com.samsung.context.sdk.samsunganalytics.internal.terms.RegisterLogSender.1
                                public final /* synthetic */ String val$deviceId;
                                public final /* synthetic */ long val$timestamp;

                                public AnonymousClass1(final String str22, final long jCurrentTimeMillis2) {
                                    str = str22;
                                    j = jCurrentTimeMillis2;
                                }

                                @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskCallback
                                public final void onFail(String str3, String str22, String str32) {
                                    RegisterLogSender.this.application.getSharedPreferences("SATerms", 0).edit().putLong(str, j).apply();
                                    sendRegistrationResult(false);
                                }

                                @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskCallback
                                public final void onSuccess() {
                                    RegisterLogSender.this.application.getSharedPreferences("SATerms", 0).edit().remove(str).apply();
                                    sendRegistrationResult(true);
                                }

                                public final void sendRegistrationResult(boolean z) {
                                    RegisterLogSender registerLogSender2 = RegisterLogSender.this;
                                    if (910701000 <= CommonUtils.getDMAVersion(registerLogSender2.application.getApplicationContext())) {
                                        Uri uri = Uri.parse("content://com.sec.android.log.diagmonagent.sa/registrationHistory");
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put("tid", registerLogSender2.config.trackingId);
                                        contentValues.put("eventTimestamp", Long.valueOf(j));
                                        contentValues.put("sendTimestamp", Long.valueOf(System.currentTimeMillis()));
                                        contentValues.put("apiType", (Integer) 11);
                                        contentValues.put("result", Boolean.valueOf(z));
                                        try {
                                            registerLogSender2.application.getApplicationContext().getContentResolver().insert(uri, contentValues);
                                        } catch (Exception e) {
                                            Debug.logwingW("Send registration result failed : " + e.getMessage());
                                        }
                                    }
                                }
                            }));
                        }
                        Trace.endSection();
                    }
                    context.getSharedPreferences("SamsungAnalyticsPrefs", 0).edit().putString("appVersionForInit", packageVersion).apply();
                    Utils.sendSettings(tracker.mContext, configuration3);
                    Utils.sendProperties(tracker.mContext, configuration3);
                    Application application22 = application;
                    RegisterType registerType32 = RegisterType.SEND_PREVIOUS_REGISTRATION_INFO;
                    Trace.beginSection("RegisterLogSender sendLog");
                    registerLogSender = new RegisterLogSender(application22, configuration3, registerType32);
                    it = registerLogSender.application.getSharedPreferences("SATerms", 0).getAll().entrySet().iterator();
                    while (true) {
                        zHasNext = it.hasNext();
                        configuration2 = registerLogSender.config;
                        if (zHasNext) {
                        }
                        Map.Entry<String, ?> next2 = it.next();
                        final String key2 = next2.getKey();
                        final long jLongValue2 = ((Long) next2.getValue()).longValue();
                        Debug.LogD("Send previous agreement, timestamp : " + jLongValue2);
                        SingleThreadExecutor.getInstance().execute(new RegisterTask(configuration2.trackingId, key2, jLongValue2, new AsyncTaskCallback() { // from class: com.samsung.context.sdk.samsunganalytics.internal.terms.RegisterLogSender.1
                            public final /* synthetic */ String val$deviceId;
                            public final /* synthetic */ long val$timestamp;

                            public AnonymousClass1(final String key22, final long jLongValue22) {
                                str = key22;
                                j = jLongValue22;
                            }

                            @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskCallback
                            public final void onFail(String str3, String str22, String str32) {
                                RegisterLogSender.this.application.getSharedPreferences("SATerms", 0).edit().putLong(str, j).apply();
                                sendRegistrationResult(false);
                            }

                            @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskCallback
                            public final void onSuccess() {
                                RegisterLogSender.this.application.getSharedPreferences("SATerms", 0).edit().remove(str).apply();
                                sendRegistrationResult(true);
                            }

                            public final void sendRegistrationResult(boolean z) {
                                RegisterLogSender registerLogSender2 = RegisterLogSender.this;
                                if (910701000 <= CommonUtils.getDMAVersion(registerLogSender2.application.getApplicationContext())) {
                                    Uri uri = Uri.parse("content://com.sec.android.log.diagmonagent.sa/registrationHistory");
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("tid", registerLogSender2.config.trackingId);
                                    contentValues.put("eventTimestamp", Long.valueOf(j));
                                    contentValues.put("sendTimestamp", Long.valueOf(System.currentTimeMillis()));
                                    contentValues.put("apiType", (Integer) 11);
                                    contentValues.put("result", Boolean.valueOf(z));
                                    try {
                                        registerLogSender2.application.getApplicationContext().getContentResolver().insert(uri, contentValues);
                                    } catch (Exception e) {
                                        Debug.logwingW("Send registration result failed : " + e.getMessage());
                                    }
                                }
                            }
                        }));
                    }
                    registerType = RegisterType.SEND_PREVIOUS_REGISTRATION_INFO;
                    registerType2 = registerLogSender.registerType;
                    if (registerType2 != registerType) {
                    }
                    Trace.endSection();
                }
            }
        });
        Trace.endAsyncSection("Tracker Constructor SingleThreadExecutor", -757204973);
        Debug.LogI("Tracker start:6.05.073");
        Trace.endSection();
    }

    public static boolean access$100(Tracker tracker) {
        synchronized (tracker) {
            boolean z = false;
            if (-1 == tracker.mStatus) {
                Debug.LogD("Tracker is not initialized, status : " + tracker.mStatus);
                return false;
            }
            if (1 == tracker.init() && tracker.deleteApiCallChecker.isNotOverLimit()) {
                z = true;
            }
            return z;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0163  */
    /* JADX WARN: Type inference failed for: r9v6, types: [com.samsung.context.sdk.samsunganalytics.internal.Tracker$7] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int init() throws ClassNotFoundException {
        boolean zBooleanValue;
        boolean z;
        String[] strArr;
        boolean z2;
        Cursor cursorQuery;
        if (this.mStatus == 0) {
            Configuration configuration = this.configuration;
            configuration.getClass();
            Context context = this.mContext;
            if (PolicyUtils.senderType == -1) {
                int dMAVersion = CommonUtils.getDMAVersion(context);
                if (dMAVersion >= 540000000) {
                    PolicyUtils.senderType = dMAVersion >= 600000000 ? 3 : 2;
                } else {
                    PolicyUtils.senderType = -1;
                }
            }
            if (PolicyUtils.senderType == 0) {
                SharedPreferences preferences = Preferences.getPreferences(this.application);
                Domain.DLS.setDomain(preferences.getString("dom", ""));
                Directory.DLS_DIR.setDirectory(preferences.getString("uri", ""));
                Directory.DLS_DIR_BAT.setDirectory(preferences.getString("bat-uri", ""));
                if (PolicyUtils.isPolicyExpired(this.mContext)) {
                    PolicyUtils.updatePolicy(this.application, configuration, SingleThreadExecutor.getInstance(), DeviceInfo.getDeviceInfo(this.mContext), new Callback() { // from class: com.samsung.context.sdk.samsunganalytics.internal.Tracker.7
                        @Override // com.samsung.context.sdk.samsunganalytics.internal.Callback
                        public final void onResult(Object obj) {
                            if (((Boolean) obj).booleanValue()) {
                                Tracker tracker = Tracker.this;
                                tracker.configuration.getClass();
                                Manager manager = Manager.getInstance(tracker.mContext, tracker.configuration);
                                Context context2 = tracker.mContext;
                                manager.getClass();
                                DbManager dbManager = new DbManager(context2);
                                manager.useDatabase = true;
                                manager.dbManager = dbManager;
                                QueueManager queueManager = manager.queueManager;
                                if (queueManager.logQueue.isEmpty()) {
                                    return;
                                }
                                Iterator it = queueManager.logQueue.iterator();
                                while (it.hasNext()) {
                                    manager.dbManager.insert((SimpleLog) it.next());
                                }
                                queueManager.logQueue.clear();
                            }
                        }
                    });
                }
            }
            UserManager userManager = (UserManager) this.mContext.getSystemService("user");
            if (userManager != null && !userManager.isUserUnlocked()) {
                Debug.LogD("current user is locked");
                this.mStatus = 0;
                return 0;
            }
            Context context2 = this.mContext;
            SharedPreferences preferences2 = Preferences.getPreferences(context2);
            int i = preferences2.getInt("enable_device", 0);
            if (i == 0) {
                try {
                    Class<?> cls = Class.forName("com.samsung.android.feature.SemFloatingFeature");
                    zBooleanValue = ((Boolean) cls.getMethod("getBoolean", String.class).invoke(cls.getMethod("getInstance", null).invoke(null, null), "SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE")).booleanValue();
                } catch (Exception e) {
                    try {
                        cursorQuery = context2.getContentResolver().query(Uri.parse("content://com.sec.android.log.diagmonagent.sa/check/diagnostic"), null, null, null);
                    } catch (Exception unused) {
                        z2 = false;
                    }
                    if (cursorQuery != null) {
                        cursorQuery.moveToNext();
                        z2 = 1 == cursorQuery.getInt(0);
                        try {
                            cursorQuery.close();
                        } catch (Exception unused2) {
                            Debug.LogD("DMA is not supported");
                            Debug.LogException(Validation.class, e);
                            zBooleanValue = z2;
                            if (zBooleanValue) {
                            }
                            if (zBooleanValue) {
                            }
                        }
                        zBooleanValue = z2;
                    } else {
                        zBooleanValue = false;
                    }
                }
                if (zBooleanValue) {
                    Debug.LogD("feature is not supported");
                    preferences2.edit().putInt("enable_device", 2).apply();
                } else {
                    Debug.LogD("cf feature is supported");
                    preferences2.edit().putInt("enable_device", 1).apply();
                }
            } else {
                zBooleanValue = i == 1;
            }
            if (zBooleanValue) {
                Debug.LogD("Device is not enabled for logging");
                this.mStatus = -1;
                return -1;
            }
            int i2 = PolicyUtils.senderType;
            if (-1 == i2) {
                Debug.LogD("SenderType is None");
                this.mStatus = -1;
                return -1;
            }
            if (i2 == 2) {
                PackageInfo servicePkgInfo = CommonUtils.getServicePkgInfo(this.mContext);
                if (servicePkgInfo == null || (strArr = servicePkgInfo.requestedPermissions) == null) {
                    z = false;
                    if (!z) {
                        Utils.throwException("SamsungAnalytics2 need to define 'com.sec.spp.permission.TOKEN_XXXX' permission in AndroidManifest");
                        this.mStatus = -1;
                        return -1;
                    }
                } else {
                    for (String str : strArr) {
                        if (str.startsWith("com.sec.spp.permission.TOKEN")) {
                            z = true;
                            break;
                        }
                    }
                    z = false;
                    if (!z) {
                    }
                }
            }
            if (Utils.isSendingAppCommonSupported(this.mContext)) {
                if (((710000000 <= CommonUtils.getDMAVersion(this.mContext)) || configuration.userAgreement.isAgreement()) && PolicyUtils.senderType == 3) {
                    SharedPreferences preferences3 = Preferences.getPreferences(this.mContext);
                    String packageVersion = CommonUtils.getPackageVersion(this.mContext);
                    if (TextUtils.isEmpty(packageVersion)) {
                        packageVersion = "None";
                    }
                    boolean z3 = preferences3.getBoolean("sendCommonSuccess", false);
                    String string = preferences3.getString("appVersion", "None");
                    long j = preferences3.getLong("sendCommonTime", 0L);
                    Long lValueOf = Long.valueOf(j);
                    StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("AppVersion = ", packageVersion, ", prefAppVersion = ", string, ", beforeSendCommonTime = ");
                    sbM.append(lValueOf);
                    sbM.append(", success = ");
                    sbM.append(z3);
                    Debug.LogD(sbM.toString());
                    if (!packageVersion.equals(string) || ((z3 && Utils.compareDays(7, lValueOf)) || (!z3 && System.currentTimeMillis() > (6 * 3600000) + j))) {
                        Debug.LogD("send app common");
                        preferences3.edit().putString("appVersion", packageVersion).putLong("sendCommonTime", System.currentTimeMillis()).apply();
                        ((DMALogSender) Sender.get(this.application, 3, configuration)).sendCommon();
                    }
                }
            }
        }
        this.mStatus = 1;
        return 1;
    }
}
