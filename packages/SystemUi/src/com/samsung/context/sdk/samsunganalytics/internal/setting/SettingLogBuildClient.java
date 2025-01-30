package com.samsung.context.sdk.samsunganalytics.internal.setting;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.policy.PolicyUtils;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.sender.Sender;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SettingLogBuildClient implements AsyncTaskClient {
    public final Configuration config;
    public final Context context;
    public List settings;

    public SettingLogBuildClient(Context context, Configuration configuration) {
        this.context = context;
        this.config = configuration;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final int onFinish() {
        boolean z;
        Uri uri;
        Configuration configuration = this.config;
        boolean isAgreement = configuration.userAgreement.isAgreement();
        Context context = this.context;
        if (!Utils.isDMADataProvideVersion(context) && !isAgreement) {
            Debug.LogD("user do not agree setting");
            return 0;
        }
        List list = this.settings;
        if (list == null || list.isEmpty()) {
            Debug.LogD("Setting Sender", "No status log");
            return 0;
        }
        if (configuration.isAlwaysRunningApp) {
            Utils.registerReceiver(context, configuration);
        }
        if (!Utils.compareDays(7, Long.valueOf(Preferences.getPreferences(context).getLong("status_sent_date", 0L)))) {
            Debug.LogD("do not send setting < 7days");
            return 0;
        }
        Debug.LogD("send setting Logs");
        String valueOf = String.valueOf(System.currentTimeMillis());
        HashMap hashMap = new HashMap();
        hashMap.put("ts", valueOf);
        hashMap.put("t", "st");
        if (PolicyUtils.senderType >= 3) {
            Uri parse = Uri.parse("content://com.sec.android.log.diagmonagent.sa/log");
            hashMap.put("v", "6.05.033");
            hashMap.put("tz", String.valueOf(TimeUnit.MILLISECONDS.toMinutes(TimeZone.getDefault().getRawOffset())));
            ContentValues contentValues = new ContentValues();
            configuration.getClass();
            contentValues.put("tcType", (Integer) 0);
            contentValues.put("tid", configuration.trackingId);
            contentValues.put("logType", LogType.UIX.getAbbrev());
            contentValues.put("timeStamp", valueOf);
            contentValues.put("agree", Integer.valueOf(isAgreement ? 1 : 0));
            Iterator it = this.settings.iterator();
            z = false;
            while (it.hasNext()) {
                hashMap.put("sti", (String) it.next());
                contentValues.put(PhoneRestrictionPolicy.BODY, Utils.makeDelimiterString(hashMap, Utils.Depth.ONE_DEPTH));
                try {
                    uri = context.getContentResolver().insert(parse, contentValues);
                } catch (IllegalArgumentException unused) {
                    uri = null;
                }
                if (uri != null) {
                    int parseInt = Integer.parseInt(uri.getLastPathSegment());
                    Debug.LogD("Send SettingLog Result = " + parseInt);
                    if (parseInt == 0) {
                        z = true;
                    }
                }
            }
        } else {
            Iterator it2 = this.settings.iterator();
            boolean z2 = false;
            while (it2.hasNext()) {
                hashMap.put("sti", (String) it2.next());
                if (Sender.get(context, PolicyUtils.senderType, configuration).send(hashMap) == 0) {
                    Debug.LogD("Setting Sender", "Send success");
                    z2 = true;
                } else {
                    Debug.LogD("Setting Sender", "Send fail");
                }
            }
            z = z2;
        }
        if (z) {
            Preferences.getPreferences(context).edit().putLong("status_sent_date", System.currentTimeMillis()).apply();
        } else {
            Preferences.getPreferences(context).edit().putLong("status_sent_date", 0L).apply();
        }
        Debug.LogD("Save Setting Result = " + z);
        return 0;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final void run() {
        String str;
        SettingReader settingReader = new SettingReader(this.context);
        Set<String> set = settingReader.appPrefNames;
        ArrayList arrayList = null;
        if (!set.isEmpty()) {
            ArrayList arrayList2 = new ArrayList();
            String str2 = "";
            for (String str3 : set) {
                Context context = settingReader.context;
                SharedPreferences sharedPreferences = context.getSharedPreferences(str3, 0);
                Set<String> stringSet = Preferences.getPreferences(context).getStringSet(str3, new HashSet());
                for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
                    if (stringSet.contains(entry.getKey())) {
                        Class<?> cls = entry.getValue().getClass();
                        boolean equals = cls.equals(Integer.class);
                        String str4 = settingReader.TWO_DEPTH_DELIMETER;
                        if (equals || cls.equals(Float.class) || cls.equals(Long.class) || cls.equals(String.class) || cls.equals(Boolean.class)) {
                            str = "" + entry.getKey() + str4 + entry.getValue();
                        } else {
                            Set<String> set2 = (Set) entry.getValue();
                            String m16m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder(""), entry.getKey(), str4);
                            String str5 = null;
                            for (String str6 : set2) {
                                if (!TextUtils.isEmpty(str5)) {
                                    StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str5);
                                    m18m.append(settingReader.THREE_DEPTH_ENTITY_DELIMETER);
                                    str5 = m18m.toString();
                                }
                                str5 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str5, str6);
                            }
                            str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m16m, str5);
                        }
                        if (str.length() + str2.length() > 512) {
                            arrayList2.add(str2);
                            str2 = "";
                        } else if (!TextUtils.isEmpty(str2)) {
                            StringBuilder m18m2 = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str2);
                            m18m2.append(settingReader.TWO_DEPTH_ENTITY_DELIMETER);
                            str2 = m18m2.toString();
                        }
                        str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str2, str);
                    }
                }
            }
            if (str2.length() != 0) {
                arrayList2.add(str2);
            }
            arrayList = arrayList2;
        }
        this.settings = arrayList;
    }
}
