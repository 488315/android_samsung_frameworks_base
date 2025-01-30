package com.samsung.context.sdk.samsunganalytics.internal.setting;

import android.content.Context;
import android.content.SharedPreferences;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SettingRegisterClient implements AsyncTaskClient {
    public final Context context;
    public final Map map;

    public SettingRegisterClient(Context context, Map<String, Set<String>> map) {
        this.context = context;
        this.map = map;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final int onFinish() {
        return 0;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final void run() {
        SharedPreferences preferences = Preferences.getPreferences(this.context);
        Iterator<String> it = preferences.getStringSet("AppPrefs", new HashSet()).iterator();
        while (it.hasNext()) {
            preferences.edit().remove(it.next()).apply();
        }
        preferences.edit().remove("AppPrefs").apply();
        HashSet hashSet = new HashSet();
        for (Map.Entry entry : this.map.entrySet()) {
            String str = (String) entry.getKey();
            hashSet.add(str);
            preferences.edit().putStringSet(str, (Set) entry.getValue()).apply();
        }
        preferences.edit().putStringSet("AppPrefs", hashSet).apply();
    }
}
