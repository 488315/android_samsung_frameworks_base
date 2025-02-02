package com.samsung.context.sdk.samsunganalytics.internal;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.UserAgreement;
import com.samsung.context.sdk.samsunganalytics.internal.connection.Directory;
import com.samsung.context.sdk.samsunganalytics.internal.connection.Domain;
import com.samsung.context.sdk.samsunganalytics.internal.device.DeviceInfo;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback;
import com.samsung.context.sdk.samsunganalytics.internal.executor.SingleThreadExecutor;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.sender.DMA.DMALogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.Sender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.Manager;
import com.samsung.context.sdk.samsunganalytics.internal.sender.buffering.database.DbManager;
import com.samsung.context.sdk.samsunganalytics.internal.setting.BuildClient;
import com.samsung.context.sdk.samsunganalytics.internal.terms.RegisterTask;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class Tracker {
    private Application application;
    private Configuration configuration;

    /* renamed from: com.samsung.context.sdk.samsunganalytics.internal.Tracker$1 */
    final class C03011 implements UserAgreement {
        final /* synthetic */ Context val$context;

        C03011(Context context) {
            this.val$context = context;
        }

        @Override // com.samsung.context.sdk.samsunganalytics.UserAgreement
        public final boolean isAgreement() {
            return Settings.System.getInt(this.val$context.getContentResolver(), "samsung_errorlog_agree", 0) == 1;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x01d7, code lost:
    
        if ((java.lang.Long.valueOf(java.lang.System.currentTimeMillis()).longValue() > (((long) 6) * 3600000) + r13.longValue()) != false) goto L58;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0221 A[LOOP:0: B:33:0x021b->B:35:0x0221, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x00df  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Tracker(Application application, Configuration configuration) {
        boolean z;
        String str;
        String sb;
        boolean z2;
        String string;
        this.application = application;
        this.configuration = configuration;
        Context applicationContext = application.getApplicationContext();
        String str2 = "";
        if (TextUtils.isEmpty(configuration.getDeviceId())) {
            SharedPreferences preferences = Preferences.getPreferences(this.application);
            String str3 = "deviceId";
            String string2 = preferences.getString("deviceId", "");
            int i = preferences.getInt("auidType", -1);
            if (TextUtils.isEmpty(string2) || string2.length() != 32 || i == -1) {
                z = false;
            } else {
                this.configuration.setAuidType(i);
                this.configuration.setDeviceId(string2);
                z = true;
            }
            if (!z && configuration.isEnableAutoDeviceId() && PolicyUtils.getSenderType() == 1) {
                SecureRandom secureRandom = new SecureRandom();
                byte[] bArr = new byte[16];
                StringBuilder sb2 = new StringBuilder(32);
                int i2 = 0;
                for (int i3 = 32; i2 < i3; i3 = 32) {
                    secureRandom.nextBytes(bArr);
                    try {
                        str = str3;
                    } catch (Exception e) {
                        e = e;
                        str = str3;
                    }
                    try {
                        sb2.append("0123456789abcdefghijklmjopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt((int) (Math.abs(new BigInteger(bArr).longValue()) % 62)));
                        i2++;
                        str3 = str;
                    } catch (Exception e2) {
                        e = e2;
                        Debug.LogException(Tracker.class, e);
                        sb = null;
                        Preferences.getPreferences(this.application.getApplicationContext()).edit().putString(str, sb).putInt("auidType", 1).apply();
                        this.configuration.setAuidType(1);
                        this.configuration.setDeviceId(sb);
                        if (PolicyUtils.getSenderType() == 0) {
                        }
                        this.configuration.setUserAgreement(new C03011(applicationContext));
                        if (((C03011) this.configuration.getUserAgreement()).isAgreement()) {
                        }
                        SingleThreadExecutor.getInstance().execute(new BuildClient(applicationContext, configuration));
                        final SharedPreferences sharedPreferences = this.application.getSharedPreferences("SATerms", 0);
                        while (r2.hasNext()) {
                        }
                        Debug.LogD("Tracker", "Tracker start:6.05.015 , senderType : " + PolicyUtils.getSenderType());
                    }
                }
                str = str3;
                sb = sb2.toString();
                Preferences.getPreferences(this.application.getApplicationContext()).edit().putString(str, sb).putInt("auidType", 1).apply();
                this.configuration.setAuidType(1);
                this.configuration.setDeviceId(sb);
                if (PolicyUtils.getSenderType() == 0) {
                    SharedPreferences preferences2 = Preferences.getPreferences(this.application);
                    Domain.DLS.setDomain(preferences2.getString("dom", ""));
                    Directory.DLS_DIR.setDirectory(preferences2.getString("uri", ""));
                    Directory.DLS_DIR_BAT.setDirectory(preferences2.getString("bat-uri", ""));
                    if (PolicyUtils.isPolicyExpired(this.application.getApplicationContext())) {
                        SingleThreadExecutor.getInstance().execute(PolicyUtils.makeGetPolicyClient(this.application, this.configuration, new DeviceInfo(this.application), new Callback<Void, Boolean>() { // from class: com.samsung.context.sdk.samsunganalytics.internal.Tracker.2
                            @Override // com.samsung.context.sdk.samsunganalytics.internal.Callback
                            public final void onResult(Object obj) {
                                if (((Boolean) obj).booleanValue()) {
                                    Tracker tracker = Tracker.this;
                                    tracker.configuration.getClass();
                                    Manager manager = Manager.getInstance(tracker.application.getApplicationContext(), tracker.configuration);
                                    Context applicationContext2 = tracker.application.getApplicationContext();
                                    manager.getClass();
                                    manager.enableDatabaseBuffering(new DbManager(applicationContext2));
                                }
                            }
                        }));
                    }
                }
                this.configuration.setUserAgreement(new C03011(applicationContext));
                if (((C03011) this.configuration.getUserAgreement()).isAgreement() && PolicyUtils.getSenderType() == 3) {
                    SharedPreferences preferences3 = Preferences.getPreferences(applicationContext);
                    try {
                        str2 = applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 0).versionName;
                    } catch (PackageManager.NameNotFoundException e3) {
                        Debug.LogException(Tracker.class, e3);
                    }
                    z2 = preferences3.getBoolean("sendCommonSuccess", false);
                    string = preferences3.getString("appVersion", "None");
                    Long valueOf = Long.valueOf(preferences3.getLong("sendCommonTime", 0L));
                    Debug.LogD("AppVersion = " + str2 + ", prefAppVerison = " + string + ", beforeSendCommonTime = " + valueOf + ", success = " + z2);
                    if (str2.equals(string) && (!z2 || !Utils.compareDays(7, valueOf))) {
                        if (!z2) {
                        }
                    }
                    Debug.LogD("send Common!!");
                    preferences3.edit().putString("appVersion", str2).putLong("sendCommonTime", System.currentTimeMillis()).apply();
                    ((DMALogSender) Sender.get(application, 3, configuration)).sendCommon();
                }
                SingleThreadExecutor.getInstance().execute(new BuildClient(applicationContext, configuration));
                final SharedPreferences sharedPreferences2 = this.application.getSharedPreferences("SATerms", 0);
                for (Map.Entry<String, ?> entry : sharedPreferences2.getAll().entrySet()) {
                    final String key = entry.getKey();
                    SingleThreadExecutor.getInstance().execute(new RegisterTask(this.configuration.getTrackingId(), key, ((Long) entry.getValue()).longValue(), new AsyncTaskCallback() { // from class: com.samsung.context.sdk.samsunganalytics.internal.Tracker.4
                        @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback
                        public final void onSuccess() {
                            sharedPreferences2.edit().remove(key).apply();
                        }

                        @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskCallback
                        public final void onFail(String str4, String str5, String str6) {
                        }
                    }));
                }
                Debug.LogD("Tracker", "Tracker start:6.05.015 , senderType : " + PolicyUtils.getSenderType());
            }
        } else {
            this.configuration.setAuidType(2);
        }
        if (PolicyUtils.getSenderType() == 0) {
        }
        this.configuration.setUserAgreement(new C03011(applicationContext));
        if (((C03011) this.configuration.getUserAgreement()).isAgreement()) {
            SharedPreferences preferences32 = Preferences.getPreferences(applicationContext);
            str2 = applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 0).versionName;
            z2 = preferences32.getBoolean("sendCommonSuccess", false);
            string = preferences32.getString("appVersion", "None");
            Long valueOf2 = Long.valueOf(preferences32.getLong("sendCommonTime", 0L));
            Debug.LogD("AppVersion = " + str2 + ", prefAppVerison = " + string + ", beforeSendCommonTime = " + valueOf2 + ", success = " + z2);
            if (str2.equals(string)) {
                if (!z2) {
                }
            }
            Debug.LogD("send Common!!");
            preferences32.edit().putString("appVersion", str2).putLong("sendCommonTime", System.currentTimeMillis()).apply();
            ((DMALogSender) Sender.get(application, 3, configuration)).sendCommon();
        }
        SingleThreadExecutor.getInstance().execute(new BuildClient(applicationContext, configuration));
        final SharedPreferences sharedPreferences22 = this.application.getSharedPreferences("SATerms", 0);
        while (r2.hasNext()) {
        }
        Debug.LogD("Tracker", "Tracker start:6.05.015 , senderType : " + PolicyUtils.getSenderType());
    }

    public final int sendLog(Map map) {
        boolean z;
        if (!((C03011) this.configuration.getUserAgreement()).isAgreement()) {
            Debug.LogD("user do not agree");
            return -2;
        }
        if (map != null) {
            HashMap hashMap = (HashMap) map;
            if (!hashMap.isEmpty()) {
                boolean z2 = false;
                if (PolicyUtils.getSenderType() < 2 && TextUtils.isEmpty(this.configuration.getDeviceId())) {
                    Debug.LogD("did is empty");
                    z = false;
                } else {
                    z = true;
                }
                if (!z) {
                    return -5;
                }
                if (((String) hashMap.get("t")).equals("pp")) {
                    if (Utils.compareDays(1, Long.valueOf(Preferences.getPreferences(this.application).getLong("property_sent_date", 0L)))) {
                        Preferences.getPreferences(this.application).edit().putLong("property_sent_date", System.currentTimeMillis()).apply();
                        z2 = true;
                    } else {
                        Debug.LogD("do not send property < 1day");
                    }
                    if (!z2) {
                        return -9;
                    }
                }
                return Sender.get(this.application, PolicyUtils.getSenderType(), this.configuration).send(map);
            }
        }
        Debug.LogD("Failure to send Logs : No data");
        return -3;
    }
}
