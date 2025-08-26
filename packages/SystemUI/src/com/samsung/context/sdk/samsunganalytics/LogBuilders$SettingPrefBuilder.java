package com.samsung.context.sdk.samsunganalytics;

import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public class LogBuilders$SettingPrefBuilder {
    public final Map map = new HashMap();

    public final void addKey(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            Utils.throwException("Failure to build logs [setting preference] : Setting key cannot be null.");
        }
        if (!((HashMap) this.map).containsKey(str) && !TextUtils.isEmpty(str)) {
            ((HashMap) this.map).put(str, new HashSet());
        } else if (TextUtils.isEmpty(str)) {
            Utils.throwException("Failure to build logs [setting preference] : Preference name cannot be null.");
        }
        ((Set) ((HashMap) this.map).get(str)).add(str2);
    }
}
