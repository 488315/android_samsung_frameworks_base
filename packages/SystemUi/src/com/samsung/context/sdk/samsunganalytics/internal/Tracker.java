package com.samsung.context.sdk.samsunganalytics.internal;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.text.TextUtils;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.connection.Directory;
import com.samsung.context.sdk.samsunganalytics.internal.connection.Domain;
import com.samsung.context.sdk.samsunganalytics.internal.device.DeviceInfo;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback;
import com.samsung.context.sdk.samsunganalytics.internal.executor.SingleThreadExecutor;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.policy.Validation;
import com.samsung.context.sdk.samsunganalytics.internal.property.PropertyLogBuildClient;
import com.samsung.context.sdk.samsunganalytics.internal.property.PropertyRegisterClient;
import com.samsung.context.sdk.samsunganalytics.internal.sender.DMA.DMALogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.Sender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.Manager;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.database.DbManager;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.queue.QueueManager;
import com.samsung.context.sdk.samsunganalytics.internal.setting.SettingLogBuildClient;
import com.samsung.context.sdk.samsunganalytics.internal.terms.RegisterTask;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Tracker {
    public final Application application;
    public final Configuration configuration;
    public final Context mContext;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.samsung.context.sdk.samsunganalytics.internal.Tracker$1 */
    public final class C47841 {
        public C47841() {
        }

        public final boolean isAgreement() {
            return Settings.System.getInt(Tracker.this.mContext.getContentResolver(), "samsung_errorlog_agree", 0) == 1;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x012e, code lost:
    
        if ((r11 > (((long) 6) * 3600000) + r13) != false) goto L44;
     */
    /* JADX WARN: Type inference failed for: r8v12, types: [com.samsung.context.sdk.samsunganalytics.internal.Tracker$2] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Tracker(Application application, Configuration configuration) {
        String str;
        this.application = application;
        this.configuration = configuration;
        Context applicationContext = application.getApplicationContext();
        this.mContext = applicationContext;
        String str2 = "";
        if (TextUtils.isEmpty(configuration.deviceId)) {
            SharedPreferences sharedPreferences = application.getSharedPreferences("SamsungAnalyticsPrefs", 0);
            String string = sharedPreferences.getString("deviceId", "");
            int i = sharedPreferences.getInt("auidType", -1);
            if (!TextUtils.isEmpty(string) && string.length() == 32 && i != -1) {
                configuration.auidType = i;
                configuration.deviceId = string;
            }
        } else {
            configuration.auidType = 2;
        }
        if (PolicyUtils.senderType == 0) {
            SharedPreferences sharedPreferences2 = application.getSharedPreferences("SamsungAnalyticsPrefs", 0);
            Domain.DLS.setDomain(sharedPreferences2.getString("dom", ""));
            Directory.DLS_DIR.setDirectory(sharedPreferences2.getString("uri", ""));
            Directory.DLS_DIR_BAT.setDirectory(sharedPreferences2.getString("bat-uri", ""));
            if (PolicyUtils.isPolicyExpired(applicationContext)) {
                SingleThreadExecutor.getInstance().execute(PolicyUtils.makeGetPolicyClient(application, configuration, new DeviceInfo(application), new Callback() { // from class: com.samsung.context.sdk.samsunganalytics.internal.Tracker.2
                    @Override // com.samsung.context.sdk.samsunganalytics.internal.Callback
                    public final void onResult(Object obj) {
                        if (((Boolean) obj).booleanValue()) {
                            Tracker tracker = Tracker.this;
                            tracker.configuration.getClass();
                            Context context = tracker.mContext;
                            Manager manager = Manager.getInstance(context, tracker.configuration);
                            manager.getClass();
                            DbManager dbManager = new DbManager(context);
                            manager.useDatabase = true;
                            manager.dbManager = dbManager;
                            QueueManager queueManager = manager.queueManager;
                            if (queueManager.logQueue.isEmpty()) {
                                return;
                            }
                            LinkedBlockingQueue linkedBlockingQueue = queueManager.logQueue;
                            Iterator it = linkedBlockingQueue.iterator();
                            while (it.hasNext()) {
                                manager.dbManager.insert((SimpleLog) it.next());
                            }
                            linkedBlockingQueue.clear();
                        }
                    }
                }));
            }
        }
        configuration.userAgreement = new C47841();
        if ((Utils.isDMADataProvideVersion(application.getApplicationContext()) || configuration.userAgreement.isAgreement()) && PolicyUtils.senderType == 3) {
            SharedPreferences preferences = Preferences.getPreferences(applicationContext);
            try {
                str2 = applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                Debug.LogException(Tracker.class, e);
            }
            str2 = str2 == null ? "None" : str2;
            boolean z = preferences.getBoolean("sendCommonSuccess", false);
            String string2 = preferences.getString("appVersion", "None");
            Long valueOf = Long.valueOf(preferences.getLong("sendCommonTime", 0L));
            StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("AppVersion = ", str2, ", prefAppVerison = ", string2, ", beforeSendCommonTime = ");
            m87m.append(valueOf);
            m87m.append(", success = ");
            m87m.append(z);
            Debug.LogD(m87m.toString());
            if (!str2.equals(string2) || (z && Utils.compareDays(7, valueOf))) {
                str = str2;
            } else if (!z) {
                long longValue = Long.valueOf(System.currentTimeMillis()).longValue();
                long longValue2 = valueOf.longValue();
                str = str2;
            }
            Debug.LogD("send Common!!");
            preferences.edit().putString("appVersion", str).putLong("sendCommonTime", System.currentTimeMillis()).apply();
            ((DMALogSender) Sender.get(application, 3, configuration)).sendCommon();
        }
        SingleThreadExecutor.getInstance().execute(new SettingLogBuildClient(this.mContext, configuration));
        final SharedPreferences sharedPreferences3 = this.application.getSharedPreferences("SATerms", 0);
        for (Map.Entry<String, ?> entry : sharedPreferences3.getAll().entrySet()) {
            final String key = entry.getKey();
            SingleThreadExecutor.getInstance().execute(new RegisterTask(this.configuration.trackingId, key, ((Long) entry.getValue()).longValue(), new AsyncTaskCallback(this) { // from class: com.samsung.context.sdk.samsunganalytics.internal.Tracker.4
                @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback
                public final void onSuccess() {
                    sharedPreferences3.edit().remove(key).apply();
                }

                @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback
                public final void onFail(String str3, String str4, String str5) {
                }
            }));
        }
        Debug.LogD("Tracker", "Tracker start:6.05.033 , senderType : " + PolicyUtils.senderType);
    }

    public final int sendLog(Map map) {
        boolean z;
        String str;
        HashMap hashMap;
        Application application = this.application;
        boolean isDMADataProvideVersion = Utils.isDMADataProvideVersion(application.getApplicationContext());
        Configuration configuration = this.configuration;
        if (!isDMADataProvideVersion) {
            if (!configuration.userAgreement.isAgreement()) {
                Debug.LogD("user do not agree");
                return -2;
            }
            HashMap hashMap2 = (HashMap) map;
            if (hashMap2.containsKey("pd")) {
                hashMap2.remove("pd");
            }
            if (hashMap2.containsKey("ps")) {
                hashMap2.remove("ps");
            }
        }
        if (map != null) {
            HashMap hashMap3 = (HashMap) map;
            if (!hashMap3.isEmpty()) {
                if (PolicyUtils.senderType < 2 && TextUtils.isEmpty(configuration.deviceId)) {
                    Debug.LogD("did is empty");
                    z = false;
                } else {
                    z = true;
                }
                if (!z) {
                    return -5;
                }
                boolean equals = "pp".equals(hashMap3.get("t"));
                Context context = this.mContext;
                if (equals) {
                    SingleThreadExecutor.getInstance().execute(new PropertyRegisterClient(context, map));
                    SingleThreadExecutor.getInstance().execute(new PropertyLogBuildClient(context, configuration));
                    return 0;
                }
                if ("ev".equals(hashMap3.get("t")) && (str = (String) hashMap3.get("et")) != null && (str.equals(String.valueOf(10)) || str.equals(String.valueOf(11)))) {
                    String string = context.getSharedPreferences("SAProperties", 0).getString("guid", "");
                    if (!TextUtils.isEmpty(string)) {
                        String str2 = (String) hashMap3.get("cd");
                        if (TextUtils.isEmpty(str2)) {
                            hashMap = new HashMap();
                        } else {
                            Utils.Depth depth = Utils.Depth.TWO_DEPTH;
                            HashMap hashMap4 = new HashMap();
                            for (String str3 : str2.split(depth.getCollectionDLM())) {
                                String[] split = str3.split(depth.getKeyValueDLM());
                                if (split.length > 1) {
                                    hashMap4.put(split[0], split[1]);
                                }
                            }
                            hashMap = hashMap4;
                        }
                        hashMap.put("guid", string);
                        hashMap3.put("cd", Utils.makeDelimiterString(Validation.checkSizeLimit(hashMap), Utils.Depth.TWO_DEPTH));
                    }
                }
                return Sender.get(application, PolicyUtils.senderType, configuration).send(map);
            }
        }
        Debug.LogD("Failure to send Logs : No data");
        return -3;
    }
}
