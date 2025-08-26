package com.samsung.context.sdk.samsunganalytics.internal.util;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Trace;
import android.text.TextUtils;
import androidx.appcompat.app.AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.context.sdk.samsunganalytics.AnalyticsException;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.policy.Validation;
import com.samsung.context.sdk.samsunganalytics.internal.property.PropertyLogSender;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.sender.Sender;
import com.samsung.context.sdk.samsunganalytics.internal.setting.SettingLogReader;
import com.samsung.context.sdk.samsunganalytics.internal.setting.SettingLogSender;
import com.sec.android.diagmonagent.common.util.CommonUtils;
import com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient;
import com.sec.android.diagmonagent.common.util.executor.SingleThreadExecutor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class Utils {
    public static AnonymousClass1 br;

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
        HashMap map = new HashMap();
        map.put("av", CommonUtils.getPackageVersion(context));
        map.put("uv", configuration.version);
        map.put("v", "6.05.073");
        Depth depth = Depth.ONE_DEPTH;
        contentValues.put("appCommon_data", makeDelimiterString(map, depth));
        if (TextUtils.isEmpty(null)) {
            return;
        }
        HashMap map2 = new HashMap();
        map2.put("auid", null);
        map2.put("at", String.valueOf(configuration.auidType));
        contentValues.put("appCommon_did", makeDelimiterString(map2, depth));
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
                        Utils.sendSettings(context3, configuration);
                        Utils.sendProperties(context2, configuration);
                    }
                });
            }
        };
        context.registerReceiver(br, AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0.m("android.intent.action.ACTION_POWER_CONNECTED"));
    }

    public static void sendProperties(Context context, Configuration configuration) {
        int iSend;
        Uri uriInsert;
        Trace.beginSection("PropertyLogSender sendLog");
        PropertyLogSender propertyLogSender = new PropertyLogSender(context, configuration);
        Configuration configuration2 = propertyLogSender.config;
        boolean zIsAgreement = configuration2.userAgreement.isAgreement();
        if (710000000 > CommonUtils.getDMAVersion(propertyLogSender.context) && !zIsAgreement) {
            Debug.LogD("user do not agree Property");
        } else {
            Map<String, ?> all = propertyLogSender.context.getSharedPreferences("SAProperties", 0).getAll();
            if (all == null || all.isEmpty()) {
                Debug.LogD("PropertyLogBuildClient", "No Property log");
            } else {
                if (configuration2.isAlwaysRunningApp) {
                    registerReceiver(propertyLogSender.context, configuration2);
                }
                String strMakeDelimiterString = makeDelimiterString(Validation.checkSizeLimit(all), Depth.TWO_DEPTH);
                String strSha256 = AuthUtil.sha256(strMakeDelimiterString);
                Context context2 = propertyLogSender.context;
                String string = Preferences.getPreferences(context2).getString("property_data", "");
                long j = context2.getSharedPreferences("SamsungAnalyticsPrefs", 0).getLong("property_sent_date", 0L);
                if (!string.equals(strSha256) || compareDays(1, Long.valueOf(j))) {
                    context2.getSharedPreferences("SamsungAnalyticsPrefs", 0).edit().putString("property_data", strSha256).apply();
                    context2.getSharedPreferences("SamsungAnalyticsPrefs", 0).edit().putLong("property_sent_date", System.currentTimeMillis()).apply();
                    Debug.LogI("update property, send it");
                    Debug.LogI("Send Property Log");
                    HashMap map = new HashMap();
                    String strValueOf = String.valueOf(System.currentTimeMillis());
                    map.put("ts", strValueOf);
                    map.put("t", "pp");
                    map.put("cp", strMakeDelimiterString);
                    int i = PolicyUtils.senderType;
                    if (i >= 3) {
                        map.put("v", "6.05.073");
                        map.put("tz", String.valueOf(getTimeZoneOffset()));
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("tcType", (Integer) 0);
                        contentValues.put("tid", configuration2.trackingId);
                        contentValues.put("logType", LogType.UIX.getAbbrev());
                        contentValues.put("timeStamp", strValueOf);
                        contentValues.put("agree", Integer.valueOf(zIsAgreement ? 1 : 0));
                        contentValues.put(PhoneRestrictionPolicy.BODY, makeDelimiterString(map, Depth.ONE_DEPTH));
                        if (!isSendingAppCommonSupported(propertyLogSender.context)) {
                            addAppCommonData(propertyLogSender.context, contentValues, configuration2);
                        }
                        if (!isSendingAppCommonSupported(propertyLogSender.context)) {
                            contentValues.put("networkType", (Integer) (-1));
                        }
                        try {
                            uriInsert = propertyLogSender.context.getContentResolver().insert(Uri.parse("content://com.sec.android.log.diagmonagent.sa/log"), contentValues);
                        } catch (IllegalArgumentException e) {
                            Debug.logwingE("failed to send properties" + e.getMessage());
                            uriInsert = null;
                        }
                        if (uriInsert == null) {
                            Debug.LogD("Property send fail");
                        } else {
                            iSend = Integer.parseInt(uriInsert.getLastPathSegment());
                        }
                    } else {
                        iSend = Sender.get(propertyLogSender.context, i, configuration2).send(map);
                    }
                    Debug.LogI("Send Property Log Result = " + iSend);
                } else {
                    Debug.LogD("do not send property < 1day");
                }
            }
        }
        Trace.endSection();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:97:0x02d0  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x02e6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void sendSettings(Context context, Configuration configuration) {
        ArrayList arrayList;
        int i;
        int i2;
        String strValueOf;
        boolean z;
        int i3;
        Uri uriInsert;
        int i4;
        Trace.beginSection("SettingLogSender sendLog");
        SettingLogSender settingLogSender = new SettingLogSender(context, configuration);
        Configuration configuration2 = settingLogSender.config;
        boolean zIsAgreement = configuration2.userAgreement.isAgreement();
        if (710000000 > CommonUtils.getDMAVersion(settingLogSender.context) && !zIsAgreement) {
            Debug.LogD("user do not agree setting");
        } else if (compareDays(1, Long.valueOf(Preferences.getPreferences(settingLogSender.context).getLong("status_sent_date", 0L)))) {
            SettingLogReader settingLogReader = new SettingLogReader(settingLogSender.context);
            int i5 = 0;
            if (settingLogReader.appPrefNames.isEmpty()) {
                i = 0;
                arrayList = null;
            } else {
                arrayList = new ArrayList();
                String strM = "";
                for (String str : settingLogReader.appPrefNames) {
                    SharedPreferences sharedPreferences = settingLogReader.context.getSharedPreferences(str, i5);
                    Set<String> stringSet = Preferences.getPreferences(settingLogReader.context).getStringSet(str, new HashSet());
                    for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
                        if (stringSet.contains(entry.getKey())) {
                            Class<?> cls = entry.getValue().getClass();
                            i2 = i5;
                            if (!cls.equals(Integer.class) && !cls.equals(Float.class) && !cls.equals(Long.class) && !cls.equals(String.class) && !cls.equals(Boolean.class)) {
                                strValueOf = "";
                                for (String str2 : (Set) entry.getValue()) {
                                    if (!TextUtils.isEmpty(strValueOf)) {
                                        StringBuilder sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strValueOf);
                                        sbM.append(settingLogReader.threeDepthCollectionDelimiter);
                                        strValueOf = sbM.toString();
                                    }
                                    strValueOf = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strValueOf, str2);
                                    if (strValueOf.length() >= 1024) {
                                        break;
                                    }
                                }
                            } else {
                                strValueOf = String.valueOf(entry.getValue());
                            }
                            String strM2 = TransitionKt$$ExternalSyntheticOutline0.m(PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(Validation.checkSizeLimit(100, entry.getKey())), settingLogReader.twoDepthKeyValueDelimiter, Validation.checkSizeLimit(1024, strValueOf));
                            if (!TextUtils.isEmpty(strM)) {
                                if (strM2.length() + strM.length() > 512) {
                                    arrayList.add(strM);
                                    strM = "";
                                } else {
                                    StringBuilder sbM2 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM);
                                    sbM2.append(settingLogReader.twoDepthCollectionDelimiter);
                                    strM = sbM2.toString();
                                }
                            }
                            strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM, strM2);
                        } else {
                            i2 = i5;
                        }
                        i5 = i2;
                    }
                }
                i = i5;
                if (!strM.isEmpty()) {
                    arrayList.add(strM);
                }
            }
            if (arrayList == null || arrayList.isEmpty()) {
                Debug.LogD("Setting Sender", "No status log");
            } else {
                if (configuration2.isAlwaysRunningApp) {
                    registerReceiver(settingLogSender.context, configuration2);
                }
                Debug.LogD("Send Setting Log");
                int i6 = PolicyUtils.senderType;
                if (i6 == 3) {
                    String strValueOf2 = String.valueOf(System.currentTimeMillis());
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("tcType", Integer.valueOf(i));
                    contentValues.put("tid", configuration2.trackingId);
                    contentValues.put("logType", LogType.UIX.getAbbrev());
                    contentValues.put("timeStamp", strValueOf2);
                    contentValues.put("agree", Integer.valueOf(configuration2.userAgreement.isAgreement() ? 1 : 0));
                    if (!isSendingAppCommonSupported(settingLogSender.context)) {
                        addAppCommonData(settingLogSender.context, contentValues, configuration2);
                    }
                    if (!isSendingAppCommonSupported(settingLogSender.context)) {
                        contentValues.put("networkType", (Integer) (-1));
                    }
                    HashMap map = new HashMap();
                    map.put("ts", strValueOf2);
                    map.put("t", "st");
                    map.put("v", "6.05.073");
                    map.put("tz", String.valueOf(getTimeZoneOffset()));
                    Uri uri = Uri.parse("content://com.sec.android.log.diagmonagent.sa/log");
                    int size = arrayList.size();
                    int i7 = i;
                    while (i7 < size) {
                        int i8 = i7 + 1;
                        map.put("sti", (String) arrayList.get(i7));
                        contentValues.put(PhoneRestrictionPolicy.BODY, makeDelimiterString(map, Depth.ONE_DEPTH));
                        try {
                            uriInsert = settingLogSender.context.getContentResolver().insert(uri, contentValues);
                        } catch (IllegalArgumentException e) {
                            Debug.logwingW("failed to send setting log" + e.getMessage());
                            uriInsert = null;
                        }
                        if (uriInsert == null || ((i4 = Integer.parseInt(uriInsert.getLastPathSegment())) != 0 && i4 != 2)) {
                            i3 = i;
                            break;
                        }
                        i7 = i8;
                    }
                    i3 = 1;
                } else if (i6 == 2 || i6 == 0) {
                    String strValueOf3 = String.valueOf(System.currentTimeMillis());
                    HashMap map2 = new HashMap();
                    map2.put("ts", strValueOf3);
                    map2.put("t", "st");
                    int size2 = arrayList.size();
                    int i9 = i;
                    while (i9 < size2) {
                        Object obj = arrayList.get(i9);
                        i9++;
                        map2.put("sti", (String) obj);
                        if (Sender.get(settingLogSender.context, PolicyUtils.senderType, configuration2).send(map2) != 0) {
                            i3 = i;
                            break;
                        }
                    }
                    i3 = 1;
                } else {
                    Debug.logwingW("Sender type is invalid : " + i6);
                    z = i;
                    if (z == 0) {
                        Preferences.getPreferences(settingLogSender.context).edit().putLong("status_sent_date", System.currentTimeMillis()).apply();
                    } else {
                        Preferences.getPreferences(settingLogSender.context).edit().putLong("status_sent_date", 0L).apply();
                    }
                    Debug.LogD("Send Setting Log Result = " + z);
                }
                z = i3;
                if (z == 0) {
                }
                Debug.LogD("Send Setting Log Result = " + z);
            }
        } else {
            Debug.LogD("do not send setting < 1day");
        }
        Trace.endSection();
    }

    public static void throwException(String str) {
        if (!Build.TYPE.equals("user")) {
            throw new AnalyticsException(str);
        }
        Debug.LogE(str);
    }
}
