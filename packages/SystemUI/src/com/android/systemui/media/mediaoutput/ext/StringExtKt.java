package com.android.systemui.media.mediaoutput.ext;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class StringExtKt {
    public static String maskedLogText$default(String str) {
        String m;
        if (str != null) {
            String str2 = (!StringsKt__StringsKt.contains(str, ":", false) || str.length() <= 5) ? null : str;
            if (str2 != null && (m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m("XX:XX:", str2.substring(str2.length() - 5, str2.length()))) != null) {
                return m;
            }
        }
        return str;
    }
}
