package com.samsung.context.sdk.samsunganalytics;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Trace;
import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.Tracker;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.policy.Validation;
import com.samsung.context.sdk.samsunganalytics.internal.property.PropertyLogRegister;
import com.samsung.context.sdk.samsunganalytics.internal.sender.Sender;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import com.sec.android.diagmonagent.common.util.CommonUtils;
import com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient;
import com.sec.android.diagmonagent.common.util.executor.SingleThreadExecutor;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class SamsungAnalytics {
    public static SamsungAnalytics instance;
    public final Tracker tracker;

    private SamsungAnalytics(Application application, Configuration configuration) {
        this.tracker = null;
        if (application == null) {
            Utils.throwException("context cannot be null");
            return;
        }
        if (configuration == null) {
            Utils.throwException("Configuration cannot be null");
            return;
        }
        if (TextUtils.isEmpty(configuration.trackingId)) {
            Utils.throwException("TrackingId is empty, set TrackingId");
            return;
        }
        if (TextUtils.isEmpty(null) && !configuration.enableAutoDeviceId) {
            Utils.throwException("Device Id is empty, set Device Id or enable auto device id");
            return;
        }
        if (!TextUtils.isEmpty(null)) {
            Utils.throwException("This mode is not allowed to set device Id");
        } else if (TextUtils.isEmpty(configuration.version)) {
            Utils.throwException("you should set the UI version");
        } else {
            this.tracker = new Tracker(application, configuration);
        }
    }

    public static SamsungAnalytics getInstance() {
        if (instance == null) {
            Utils.throwException("call after setConfiguration() method");
            if (Build.TYPE.equals("user")) {
                synchronized (SamsungAnalytics.class) {
                    try {
                        if (instance == null) {
                            instance = new SamsungAnalytics(null, null);
                        }
                    } finally {
                    }
                }
            }
        }
        return instance;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004c A[Catch: all -> 0x0051, TryCatch #0 {all -> 0x0051, blocks: (B:16:0x0029, B:18:0x002f, B:21:0x0034, B:28:0x004c, B:31:0x0053, B:33:0x0057, B:38:0x005f, B:39:0x0068), top: B:44:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x005f A[Catch: all -> 0x0051, TryCatch #0 {all -> 0x0051, blocks: (B:16:0x0029, B:18:0x002f, B:21:0x0034, B:28:0x004c, B:31:0x0053, B:33:0x0057, B:38:0x005f, B:39:0x0068), top: B:44:0x0029 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void setConfiguration(Application application, Configuration configuration) {
        boolean z;
        SamsungAnalytics samsungAnalytics;
        Tracker tracker;
        Trace.beginSection("SamsungAnalytics setConfiguration");
        SamsungAnalytics samsungAnalytics2 = instance;
        if (samsungAnalytics2 == null || (tracker = samsungAnalytics2.tracker) == null) {
            synchronized (SamsungAnalytics.class) {
                try {
                    SamsungAnalytics samsungAnalytics3 = instance;
                    boolean z2 = true;
                    if (samsungAnalytics3 == null || samsungAnalytics3.tracker == null) {
                        z = false;
                        if (z) {
                            instance = SamsungAnalyticsHolder.diagnosticInstance;
                        }
                        samsungAnalytics = instance;
                        if (samsungAnalytics != null && samsungAnalytics.tracker != null) {
                            z2 = false;
                        }
                        if (z2) {
                            SamsungAnalytics samsungAnalytics4 = new SamsungAnalytics(application, configuration);
                            instance = samsungAnalytics4;
                            SamsungAnalyticsHolder.diagnosticInstance = samsungAnalytics4;
                        }
                    } else {
                        Context applicationContext = application.getApplicationContext();
                        Configuration configuration2 = instance.tracker.configuration;
                        if (!Utils.isSendingAppCommonSupported(applicationContext) && configuration2 == null) {
                            z = true;
                        }
                        if (z) {
                        }
                        samsungAnalytics = instance;
                        if (samsungAnalytics != null) {
                            z2 = false;
                        }
                        if (z2) {
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } else if (samsungAnalytics2 != null && tracker != null) {
            Context applicationContext2 = application.getApplicationContext();
            Configuration configuration3 = instance.tracker.configuration;
            if (!Utils.isSendingAppCommonSupported(applicationContext2) && configuration3 == null) {
            }
        }
        Trace.endSection();
    }

    public final void sendLog(final Map map) {
        Debug.LogD("sendLog");
        try {
            final Tracker tracker = this.tracker;
            tracker.getClass();
            Trace.beginAsyncSection("Tracker SendLog SingleThreadExecutor", 1468411569);
            SingleThreadExecutor.getInstance().execute(new AsyncTaskClient() { // from class: com.samsung.context.sdk.samsunganalytics.internal.Tracker.3
                @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
                public final int onFinish() {
                    return 0;
                }

                @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
                public final void run() {
                    String str;
                    HashMap map2;
                    Tracker tracker2 = Tracker.this;
                    if (Tracker.access$100(tracker2)) {
                        int dMAVersion = CommonUtils.getDMAVersion(tracker2.mContext);
                        Configuration configuration = tracker2.configuration;
                        if (710000000 > dMAVersion) {
                            if (!configuration.userAgreement.isAgreement()) {
                                Debug.LogD("user do not agree");
                                return;
                            } else {
                                map.remove("pd");
                                map.remove("ps");
                            }
                        }
                        Map map3 = map;
                        if (map3 == null || map3.isEmpty()) {
                            Debug.LogD("Failure to send Logs : No data");
                            return;
                        }
                        if (PolicyUtils.senderType < 2) {
                            configuration.getClass();
                            if (TextUtils.isEmpty(null)) {
                                Debug.LogD("did is empty");
                                return;
                            }
                        }
                        if ("pp".equals(map.get("t"))) {
                            Context context = tracker2.mContext;
                            Map map4 = map;
                            PropertyLogRegister propertyLogRegister = new PropertyLogRegister(context);
                            map4.remove("t");
                            SharedPreferences sharedPreferences = propertyLogRegister.context.getSharedPreferences("SAProperties", 0);
                            for (Map.Entry entry : map4.entrySet()) {
                                if (TextUtils.isEmpty((CharSequence) entry.getValue())) {
                                    sharedPreferences.edit().remove((String) entry.getKey()).apply();
                                } else {
                                    sharedPreferences.edit().putString((String) entry.getKey(), (String) entry.getValue()).apply();
                                }
                            }
                            Utils.sendProperties(tracker2.mContext, configuration);
                            return;
                        }
                        if ("ev".equals(map.get("t")) && (str = (String) map.get("et")) != null && (str.equals(String.valueOf(10)) || str.equals(String.valueOf(11)))) {
                            String string = tracker2.mContext.getSharedPreferences("SAProperties", 0).getString("guid", "");
                            if (!TextUtils.isEmpty(string)) {
                                String str2 = (String) map.get("cd");
                                if (TextUtils.isEmpty(str2)) {
                                    map2 = new HashMap();
                                } else {
                                    Utils.Depth depth = Utils.Depth.TWO_DEPTH;
                                    HashMap map5 = new HashMap();
                                    for (String str3 : str2.split(depth.getCollectionDLM())) {
                                        String[] strArrSplit = str3.split(depth.getKeyValueDLM());
                                        if (strArrSplit.length > 1) {
                                            map5.put(strArrSplit[0], strArrSplit[1]);
                                        }
                                    }
                                    map2 = map5;
                                }
                                map2.put("guid", string);
                                map.put("cd", Utils.makeDelimiterString(Validation.checkSizeLimit(map2), Utils.Depth.TWO_DEPTH));
                            }
                        }
                        Sender.get(tracker2.application, PolicyUtils.senderType, configuration).send(map);
                    }
                }
            });
            Trace.endAsyncSection("Tracker SendLog SingleThreadExecutor", 1468411569);
        } catch (NullPointerException unused) {
        }
    }
}
