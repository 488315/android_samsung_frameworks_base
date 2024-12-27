package com.samsung.context.sdk.samsunganalytics.internal.setting;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.sender.LogType;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.Delimiter;
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

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class BuildClient implements AsyncTaskClient {
    public Configuration config;
    public Context context;
    public Uri logUri;
    public List settings;

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final int onFinish() {
        Uri uri;
        Configuration configuration = this.config;
        if (!(Settings.System.getInt(
                        ((Context) configuration.userAgreement.val$context).getContentResolver(),
                        "samsung_errorlog_agree",
                        0)
                == 1)) {
            Debug.LogD("user do not agree setting");
            return 0;
        }
        List list = this.settings;
        if (list == null || list.isEmpty()) {
            Debug.LogD("Setting Sender", "No status log");
            return 0;
        }
        if (!Utils.compareDays(
                7,
                Long.valueOf(
                        Preferences.getPreferences(this.context)
                                .getLong("status_sent_date", 0L)))) {
            Debug.LogD("do not send setting < 7days");
            return 0;
        }
        Debug.LogD("send setting");
        HashMap hashMap = new HashMap();
        String valueOf = String.valueOf(System.currentTimeMillis());
        hashMap.put("ts", valueOf);
        hashMap.put("t", "st");
        hashMap.put("v", "6.05.015");
        hashMap.put(
                "tz",
                String.valueOf(
                        TimeUnit.MILLISECONDS.toMinutes(TimeZone.getDefault().getRawOffset())));
        Iterator it = this.settings.iterator();
        boolean z = false;
        while (it.hasNext()) {
            hashMap.put("sti", (String) it.next());
            ContentValues contentValues = new ContentValues();
            contentValues.put("tcType", (Integer) 0);
            contentValues.put("tid", configuration.trackingId);
            contentValues.put("logType", LogType.UIX.getAbbrev());
            contentValues.put("timeStamp", valueOf);
            contentValues.put(
                    "body", Delimiter.makeDelimiterString(hashMap, Delimiter.Depth.ONE_DEPTH));
            try {
                uri = this.context.getContentResolver().insert(this.logUri, contentValues);
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
        if (z) {
            Preferences.getPreferences(this.context)
                    .edit()
                    .putLong("status_sent_date", System.currentTimeMillis())
                    .apply();
        } else {
            Preferences.getPreferences(this.context).edit().putLong("status_sent_date", 0L).apply();
        }
        Debug.LogD("Save Setting Result = " + z);
        return 0;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final void run() {
        ArrayList arrayList;
        String str;
        Context context = this.context;
        Set<String> stringSet =
                Preferences.getPreferences(context).getStringSet("AppPrefs", new HashSet());
        Delimiter.Depth depth = Delimiter.Depth.TWO_DEPTH;
        String keyValueDLM = depth.getKeyValueDLM();
        String collectionDLM = depth.getCollectionDLM();
        String collectionDLM2 = Delimiter.Depth.THREE_DEPTH.getCollectionDLM();
        int i = 0;
        if (stringSet.isEmpty()) {
            arrayList = null;
        } else {
            ArrayList arrayList2 = new ArrayList();
            String str2 = "";
            for (String str3 : stringSet) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(str3, i);
                Set<String> stringSet2 =
                        context.getSharedPreferences("SamsungAnalyticsPrefs", i)
                                .getStringSet(str3, new HashSet());
                for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
                    if (stringSet2.contains(entry.getKey())) {
                        Class<?> cls = entry.getValue().getClass();
                        if (cls.equals(Integer.class)
                                || cls.equals(Float.class)
                                || cls.equals(Long.class)
                                || cls.equals(String.class)
                                || cls.equals(Boolean.class)) {
                            str = "" + entry.getKey() + keyValueDLM + entry.getValue();
                        } else {
                            Set<String> set = (Set) entry.getValue();
                            String str4 = "" + entry.getKey() + keyValueDLM;
                            String str5 = null;
                            for (String str6 : set) {
                                if (!TextUtils.isEmpty(str5)) {
                                    str5 =
                                            AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                                    str5, collectionDLM2);
                                }
                                str5 =
                                        AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                                str5, str6);
                            }
                            str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str4, str5);
                        }
                        if (str.length() + str2.length() > 512) {
                            arrayList2.add(str2);
                            str2 = "";
                        } else if (!TextUtils.isEmpty(str2)) {
                            str2 =
                                    AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                            str2, collectionDLM);
                        }
                        str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, str);
                    }
                    i = 0;
                }
            }
            if (str2.length() != 0) {
                arrayList2.add(str2);
            }
            arrayList = arrayList2;
        }
        Map<String, ?> all = context.getSharedPreferences("SASettingPref", 0).getAll();
        if (all != null && !all.isEmpty()) {
            arrayList.add(Delimiter.makeDelimiterString(all, depth));
        }
        this.settings = arrayList;
    }
}
