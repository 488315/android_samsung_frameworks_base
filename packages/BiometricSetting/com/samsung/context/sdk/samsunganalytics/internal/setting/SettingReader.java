package com.samsung.context.sdk.samsunganalytics.internal.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import com.samsung.context.sdk.samsunganalytics.internal.util.Delimiter;
import com.samsung.context.sdk.samsunganalytics.internal.util.Preferences;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class SettingReader {
    private final String THREE_DEPTH_ENTITY_DELIMETER;
    private final String TWO_DEPTH_DELIMETER;
    private final String TWO_DEPTH_ENTITY_DELIMETER;
    private Set<String> appPrefNames;
    private Context context;

    public SettingReader(Context context) {
        this.context = context;
        this.appPrefNames = context.getSharedPreferences("SamsungAnalyticsPrefs", 0).getStringSet("AppPrefs", new HashSet());
        Delimiter.Depth depth = Delimiter.Depth.TWO_DEPTH;
        this.TWO_DEPTH_DELIMETER = depth.getKeyValueDLM();
        this.TWO_DEPTH_ENTITY_DELIMETER = depth.getCollectionDLM();
        this.THREE_DEPTH_ENTITY_DELIMETER = Delimiter.Depth.THREE_DEPTH.getCollectionDLM();
    }

    public final List<String> read() {
        String str;
        ArrayList arrayList = null;
        if (!this.appPrefNames.isEmpty()) {
            ArrayList arrayList2 = new ArrayList();
            String str2 = "";
            for (String str3 : this.appPrefNames) {
                SharedPreferences sharedPreferences = this.context.getSharedPreferences(str3, 0);
                Set<String> stringSet = Preferences.getPreferences(this.context).getStringSet(str3, new HashSet());
                for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
                    if (stringSet.contains(entry.getKey())) {
                        Class<?> cls = entry.getValue().getClass();
                        boolean equals = cls.equals(Integer.class);
                        String str4 = this.TWO_DEPTH_DELIMETER;
                        if (equals || cls.equals(Float.class) || cls.equals(Long.class) || cls.equals(String.class) || cls.equals(Boolean.class)) {
                            str = "" + entry.getKey() + str4 + entry.getValue();
                        } else {
                            Set<String> set = (Set) entry.getValue();
                            String str5 = "" + entry.getKey() + str4;
                            String str6 = null;
                            for (String str7 : set) {
                                if (!TextUtils.isEmpty(str6)) {
                                    StringBuilder m2m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m2m(str6);
                                    m2m.append(this.THREE_DEPTH_ENTITY_DELIMETER);
                                    str6 = m2m.toString();
                                }
                                str6 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m1m(str6, str7);
                            }
                            str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m1m(str5, str6);
                        }
                        if (str.length() + str2.length() > 512) {
                            arrayList2.add(str2);
                            str2 = "";
                        } else if (!TextUtils.isEmpty(str2)) {
                            StringBuilder m2m2 = ArrayLinkedVariables$$ExternalSyntheticOutline0.m2m(str2);
                            m2m2.append(this.TWO_DEPTH_ENTITY_DELIMETER);
                            str2 = m2m2.toString();
                        }
                        str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m1m(str2, str);
                    }
                }
            }
            if (str2.length() != 0) {
                arrayList2.add(str2);
            }
            arrayList = arrayList2;
        }
        Map<String, ?> all = this.context.getSharedPreferences("SASettingPref", 0).getAll();
        if (all != null && !all.isEmpty()) {
            arrayList.add(Delimiter.makeDelimiterString(all, Delimiter.Depth.TWO_DEPTH));
        }
        return arrayList;
    }
}
