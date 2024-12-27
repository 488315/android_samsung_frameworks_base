package com.android.systemui.media.mediaoutput.ext;

import kotlin.text.StringsKt__StringsKt;

public abstract class StringExtKt {
    public static String maskedLogText$default(String str) {
        String concat;
        if (str == null) {
            return str;
        }
        String str2 = (!StringsKt__StringsKt.contains(str, ":", false) || str.length() <= 5) ? null : str;
        return (str2 == null || (concat = "XX:XX:".concat(str2.substring(str2.length() - 5, str2.length()))) == null) ? str : concat;
    }
}
