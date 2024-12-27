package com.android.systemui.media.mediaoutput.ext;

import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
