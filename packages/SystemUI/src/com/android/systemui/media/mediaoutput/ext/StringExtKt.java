package com.android.systemui.media.mediaoutput.ext;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public abstract class StringExtKt {
    public static String maskedLogText$default(String str) {
        String strM;
        if (str != null) {
            String str2 = (!StringsKt__StringsKt.contains(str, ":", false) || str.length() <= 5) ? null : str;
            if (str2 != null && (strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m("XX:XX:", str2.substring(str2.length() - 5, str2.length()))) != null) {
                return strM;
            }
        }
        return str;
    }
}
