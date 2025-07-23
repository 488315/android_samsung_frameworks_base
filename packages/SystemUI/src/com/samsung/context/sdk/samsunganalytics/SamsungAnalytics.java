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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:35:0x005f, code lost:
    
        r1 = new com.samsung.context.sdk.samsunganalytics.SamsungAnalytics(r5, r6);
        com.samsung.context.sdk.samsunganalytics.SamsungAnalytics.instance = r1;
        com.samsung.context.sdk.samsunganalytics.SamsungAnalyticsHolder.diagnosticInstance = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0024, code lost:
    
        if (r1 != null) goto L40;
     */
    /* JADX WARN: Removed duplicated region for block: B:27:0x004c A[Catch: all -> 0x0051, TryCatch #0 {all -> 0x0051, blocks: (B:16:0x0029, B:18:0x002f, B:21:0x0034, B:27:0x004c, B:28:0x0053, B:30:0x0057, B:35:0x005f, B:36:0x0068), top: B:15:0x0029 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void setConfiguration(android.app.Application r5, com.samsung.context.sdk.samsunganalytics.Configuration r6) {
        /*
            java.lang.String r0 = "SamsungAnalytics setConfiguration"
            android.os.Trace.beginSection(r0)
            com.samsung.context.sdk.samsunganalytics.SamsungAnalytics r0 = com.samsung.context.sdk.samsunganalytics.SamsungAnalytics.instance
            if (r0 == 0) goto L26
            com.samsung.context.sdk.samsunganalytics.internal.Tracker r1 = r0.tracker
            if (r1 != 0) goto Le
            goto L26
        Le:
            if (r0 == 0) goto L69
            if (r1 != 0) goto L13
            goto L69
        L13:
            android.content.Context r0 = r5.getApplicationContext()
            com.samsung.context.sdk.samsunganalytics.SamsungAnalytics r1 = com.samsung.context.sdk.samsunganalytics.SamsungAnalytics.instance
            com.samsung.context.sdk.samsunganalytics.internal.Tracker r1 = r1.tracker
            com.samsung.context.sdk.samsunganalytics.Configuration r1 = r1.configuration
            boolean r0 = com.samsung.context.sdk.samsunganalytics.internal.util.Utils.isSendingAppCommonSupported(r0)
            if (r0 == 0) goto L24
            goto L69
        L24:
            if (r1 != 0) goto L69
        L26:
            java.lang.Class<com.samsung.context.sdk.samsunganalytics.SamsungAnalytics> r0 = com.samsung.context.sdk.samsunganalytics.SamsungAnalytics.class
            monitor-enter(r0)
            com.samsung.context.sdk.samsunganalytics.SamsungAnalytics r1 = com.samsung.context.sdk.samsunganalytics.SamsungAnalytics.instance     // Catch: java.lang.Throwable -> L51
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L49
            com.samsung.context.sdk.samsunganalytics.internal.Tracker r1 = r1.tracker     // Catch: java.lang.Throwable -> L51
            if (r1 != 0) goto L34
            goto L49
        L34:
            android.content.Context r1 = r5.getApplicationContext()     // Catch: java.lang.Throwable -> L51
            com.samsung.context.sdk.samsunganalytics.SamsungAnalytics r4 = com.samsung.context.sdk.samsunganalytics.SamsungAnalytics.instance     // Catch: java.lang.Throwable -> L51
            com.samsung.context.sdk.samsunganalytics.internal.Tracker r4 = r4.tracker     // Catch: java.lang.Throwable -> L51
            com.samsung.context.sdk.samsunganalytics.Configuration r4 = r4.configuration     // Catch: java.lang.Throwable -> L51
            boolean r1 = com.samsung.context.sdk.samsunganalytics.internal.util.Utils.isSendingAppCommonSupported(r1)     // Catch: java.lang.Throwable -> L51
            if (r1 == 0) goto L45
            goto L49
        L45:
            if (r4 != 0) goto L49
            r1 = r2
            goto L4a
        L49:
            r1 = r3
        L4a:
            if (r1 == 0) goto L53
            com.samsung.context.sdk.samsunganalytics.SamsungAnalytics r1 = com.samsung.context.sdk.samsunganalytics.SamsungAnalyticsHolder.diagnosticInstance     // Catch: java.lang.Throwable -> L51
            com.samsung.context.sdk.samsunganalytics.SamsungAnalytics.instance = r1     // Catch: java.lang.Throwable -> L51
            goto L53
        L51:
            r5 = move-exception
            goto L6d
        L53:
            com.samsung.context.sdk.samsunganalytics.SamsungAnalytics r1 = com.samsung.context.sdk.samsunganalytics.SamsungAnalytics.instance     // Catch: java.lang.Throwable -> L51
            if (r1 == 0) goto L5d
            com.samsung.context.sdk.samsunganalytics.internal.Tracker r1 = r1.tracker     // Catch: java.lang.Throwable -> L51
            if (r1 != 0) goto L5c
            goto L5d
        L5c:
            r2 = r3
        L5d:
            if (r2 == 0) goto L68
            com.samsung.context.sdk.samsunganalytics.SamsungAnalytics r1 = new com.samsung.context.sdk.samsunganalytics.SamsungAnalytics     // Catch: java.lang.Throwable -> L51
            r1.<init>(r5, r6)     // Catch: java.lang.Throwable -> L51
            com.samsung.context.sdk.samsunganalytics.SamsungAnalytics.instance = r1     // Catch: java.lang.Throwable -> L51
            com.samsung.context.sdk.samsunganalytics.SamsungAnalyticsHolder.diagnosticInstance = r1     // Catch: java.lang.Throwable -> L51
        L68:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L51
        L69:
            android.os.Trace.endSection()
            return
        L6d:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L51
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.context.sdk.samsunganalytics.SamsungAnalytics.setConfiguration(android.app.Application, com.samsung.context.sdk.samsunganalytics.Configuration):void");
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
                    HashMap hashMap;
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
                        Map map2 = map;
                        if (map2 == null || map2.isEmpty()) {
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
                            Map map3 = map;
                            PropertyLogRegister propertyLogRegister = new PropertyLogRegister(context);
                            map3.remove("t");
                            SharedPreferences sharedPreferences = propertyLogRegister.context.getSharedPreferences("SAProperties", 0);
                            for (Map.Entry entry : map3.entrySet()) {
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
                                    hashMap = new HashMap();
                                } else {
                                    Utils.Depth depth = Utils.Depth.TWO_DEPTH;
                                    HashMap hashMap2 = new HashMap();
                                    for (String str3 : str2.split(depth.getCollectionDLM())) {
                                        String[] split = str3.split(depth.getKeyValueDLM());
                                        if (split.length > 1) {
                                            hashMap2.put(split[0], split[1]);
                                        }
                                    }
                                    hashMap = hashMap2;
                                }
                                hashMap.put("guid", string);
                                map.put("cd", Utils.makeDelimiterString(Validation.checkSizeLimit(hashMap), Utils.Depth.TWO_DEPTH));
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
