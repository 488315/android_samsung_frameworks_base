package com.samsung.android.lib.galaxyfinder.search.util;

import com.samsung.android.util.SemLog;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SearchLog {
    public static void d(String str, String str2) {
        try {
            SemLog.d("SamSearch_".concat(str), str2);
        } catch (Exception unused) {
        }
    }
}
