package com.samsung.android.lib.galaxyfinder.search.util;

import com.samsung.android.util.SemLog;

/* loaded from: classes4.dex */
public class SearchLog {
    public static void d(String str, String str2) {
        try {
            SemLog.d("SamSearch_".concat(str), str2);
        } catch (Exception unused) {
        }
    }
}
