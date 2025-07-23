package com.android.internal.inputmethod;

import android.telecom.Logging.Session;
import android.text.TextUtils;
import java.util.Locale;

/* loaded from: classes5.dex */
public class SubtypeLocaleUtils {
    public static Locale constructLocaleFromString(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] split = str.split(Session.SESSION_SEPARATION_CHAR_CHILD, 3);
        if (split.length >= 1 && "tl".equals(split[0])) {
            split[0] = "fil";
        }
        if (split.length == 1) {
            return new Locale(split[0]);
        }
        if (split.length == 2) {
            return new Locale(split[0], split[1]);
        }
        if (split.length == 3) {
            return new Locale(split[0], split[1], split[2]);
        }
        return null;
    }
}
