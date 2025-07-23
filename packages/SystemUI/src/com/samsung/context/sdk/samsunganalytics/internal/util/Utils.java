package com.samsung.context.sdk.samsunganalytics.internal.util;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Trace;
import android.text.TextUtils;
import androidx.appcompat.app.AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.context.sdk.samsunganalytics.AnalyticsException;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.policy.Validation;
import com.samsung.context.sdk.samsunganalytics.internal.property.PropertyLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.sender.Sender;
import com.sec.android.diagmonagent.common.util.CommonUtils;
import com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient;
import com.sec.android.diagmonagent.common.util.executor.SingleThreadExecutor;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Utils {
    public static AnonymousClass1 br;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum Depth {
        ONE_DEPTH("\u0002", "\u0003"),
        TWO_DEPTH("\u0004", "\u0005"),
        THREE_DEPTH("\u0006", "\u0007");

        private final String collDlm;
        private final String keyValueDlm;

        Depth(String str, String str2) {
            this.collDlm = str;
            this.keyValueDlm = str2;
        }

        public final String getCollectionDLM() {
            return this.collDlm;
        }

        public final String getKeyValueDLM() {
            return this.keyValueDlm;
        }
    }

    public static void addAppCommonData(Context context, ContentValues contentValues, Configuration configuration) {
        HashMap hashMap = new HashMap();
        hashMap.put("av", CommonUtils.getPackageVersion(context));
        hashMap.put("uv", configuration.version);
        hashMap.put("v", "6.05.073");
        Depth depth = Depth.ONE_DEPTH;
        contentValues.put("appCommon_data", makeDelimiterString(hashMap, depth));
        if (TextUtils.isEmpty(null)) {
            return;
        }
        HashMap hashMap2 = new HashMap();
        hashMap2.put("auid", null);
        hashMap2.put("at", String.valueOf(configuration.auidType));
        contentValues.put("appCommon_did", makeDelimiterString(hashMap2, depth));
    }

    public static boolean compareDays(int i, Long l) {
        return System.currentTimeMillis() > (((long) i) * 86400000) + l.longValue();
    }

    public static long getTimeZoneOffset() {
        return TimeUnit.MILLISECONDS.toMinutes(TimeZone.getDefault().getRawOffset() + android.icu.util.TimeZone.getDefault().getDSTSavings());
    }

    public static boolean isSendingAppCommonSupported(Context context) {
        return 712601000 > CommonUtils.getDMAVersion(context);
    }

    public static String makeDelimiterString(Map map, Depth depth) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append(depth.getCollectionDLM());
            }
            sb.append((String) entry.getKey());
            sb.append(depth.getKeyValueDLM());
            sb.append((String) entry.getValue());
        }
        return sb.toString();
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.context.sdk.samsunganalytics.internal.util.Utils$1] */
    public static void registerReceiver(Context context, final Configuration configuration) {
        Debug.LogENG("register BR");
        if (br != null) {
            Debug.LogENG("BR is already registered");
            return;
        }
        br = new BroadcastReceiver() { // from class: com.samsung.context.sdk.samsunganalytics.internal.util.Utils.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(final Context context2, Intent intent) {
                StringBuilder sb = new StringBuilder("receive BR ");
                sb.append(intent != null ? intent.getAction() : "null");
                Debug.LogENG(sb.toString());
                if (intent == null || !"android.intent.action.ACTION_POWER_CONNECTED".equals(intent.getAction())) {
                    return;
                }
                SingleThreadExecutor.getInstance().execute(new AsyncTaskClient() { // from class: com.samsung.context.sdk.samsunganalytics.internal.util.Utils.1.1
                    @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
                    public final int onFinish() {
                        return 0;
                    }

                    @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
                    public final void run() {
                        Context context3 = context2;
                        AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                        Utils.sendSettings(context3, Configuration.this);
                        Utils.sendProperties(context2, Configuration.this);
                    }
                });
            }
        };
        context.registerReceiver(br, AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0.m("android.intent.action.ACTION_POWER_CONNECTED"));
    }

    public static void sendProperties(Context context, Configuration configuration) {
        int send;
        Uri uri;
        Trace.beginSection("PropertyLogSender sendLog");
        PropertyLogSender propertyLogSender = new PropertyLogSender(context, configuration);
        Configuration configuration2 = propertyLogSender.config;
        boolean isAgreement = configuration2.userAgreement.isAgreement();
        if (710000000 > CommonUtils.getDMAVersion(propertyLogSender.context) && !isAgreement) {
            Debug.LogD("user do not agree Property");
        } else {
            Map<String, ?> all = propertyLogSender.context.getSharedPreferences("SAProperties", 0).getAll();
            if (all == null || all.isEmpty()) {
                Debug.LogD("PropertyLogBuildClient", "No Property log");
            } else {
                if (configuration2.isAlwaysRunningApp) {
                    registerReceiver(propertyLogSender.context, configuration2);
                }
                String makeDelimiterString = makeDelimiterString(Validation.checkSizeLimit(all), Depth.TWO_DEPTH);
                String sha256 = AuthUtil.sha256(makeDelimiterString);
                Context context2 = propertyLogSender.context;
                String string = Preferences.getPreferences(context2).getString("property_data", "");
                long j = context2.getSharedPreferences("SamsungAnalyticsPrefs", 0).getLong("property_sent_date", 0L);
                if (!string.equals(sha256) || compareDays(1, Long.valueOf(j))) {
                    context2.getSharedPreferences("SamsungAnalyticsPrefs", 0).edit().putString("property_data", sha256).apply();
                    context2.getSharedPreferences("SamsungAnalyticsPrefs", 0).edit().putLong("property_sent_date", System.currentTimeMillis()).apply();
                    Debug.LogI("update property, send it");
                    Debug.LogI("Send Property Log");
                    HashMap hashMap = new HashMap();
                    String valueOf = String.valueOf(System.currentTimeMillis());
                    hashMap.put("ts", valueOf);
                    hashMap.put("t", "pp");
                    hashMap.put("cp", makeDelimiterString);
                    int i = PolicyUtils.senderType;
                    if (i >= 3) {
                        hashMap.put("v", "6.05.073");
                        hashMap.put("tz", String.valueOf(getTimeZoneOffset()));
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("tcType", (Integer) 0);
                        contentValues.put("tid", configuration2.trackingId);
                        contentValues.put("logType", LogType.UIX.getAbbrev());
                        contentValues.put("timeStamp", valueOf);
                        contentValues.put("agree", Integer.valueOf(isAgreement ? 1 : 0));
                        contentValues.put(PhoneRestrictionPolicy.BODY, makeDelimiterString(hashMap, Depth.ONE_DEPTH));
                        if (!isSendingAppCommonSupported(propertyLogSender.context)) {
                            addAppCommonData(propertyLogSender.context, contentValues, configuration2);
                        }
                        if (!isSendingAppCommonSupported(propertyLogSender.context)) {
                            contentValues.put("networkType", (Integer) (-1));
                        }
                        try {
                            uri = propertyLogSender.context.getContentResolver().insert(Uri.parse("content://com.sec.android.log.diagmonagent.sa/log"), contentValues);
                        } catch (IllegalArgumentException e) {
                            Debug.logwingE("failed to send properties" + e.getMessage());
                            uri = null;
                        }
                        if (uri == null) {
                            Debug.LogD("Property send fail");
                        } else {
                            send = Integer.parseInt(uri.getLastPathSegment());
                        }
                    } else {
                        send = Sender.get(propertyLogSender.context, i, configuration2).send(hashMap);
                    }
                    Debug.LogI("Send Property Log Result = " + send);
                } else {
                    Debug.LogD("do not send property < 1day");
                }
            }
        }
        Trace.endSection();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:41:0x02d0  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x02e6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void sendSettings(android.content.Context r18, com.samsung.context.sdk.samsunganalytics.Configuration r19) {
        /*
            Method dump skipped, instructions count: 790
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.context.sdk.samsunganalytics.internal.util.Utils.sendSettings(android.content.Context, com.samsung.context.sdk.samsunganalytics.Configuration):void");
    }

    public static void throwException(String str) {
        if (!Build.TYPE.equals("user")) {
            throw new AnalyticsException(str);
        }
        Debug.LogE(str);
    }
}
