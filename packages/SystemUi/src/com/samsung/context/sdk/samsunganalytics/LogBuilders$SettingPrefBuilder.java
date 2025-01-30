package com.samsung.context.sdk.samsunganalytics;

import android.text.TextUtils;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LogBuilders$SettingPrefBuilder {
    public final Map map = new HashMap();

    public final void addKey(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            Utils.throwException("Failure to build logs [setting preference] : Setting key cannot be null.");
        }
        HashMap hashMap = (HashMap) this.map;
        if (!hashMap.containsKey(str) && !TextUtils.isEmpty(str)) {
            hashMap.put(str, new HashSet());
        } else if (TextUtils.isEmpty(str)) {
            Utils.throwException("Failure to build logs [setting preference] : Preference name cannot be null.");
        }
        ((Set) hashMap.get(str)).add(str2);
    }
}
