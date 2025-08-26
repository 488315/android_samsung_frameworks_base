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
        String[] strArrSplit = str.split(Session.SESSION_SEPARATION_CHAR_CHILD, 3);
        if (strArrSplit.length >= 1 && "tl".equals(strArrSplit[0])) {
            strArrSplit[0] = "fil";
        }
        if (strArrSplit.length == 1) {
            return new Locale(strArrSplit[0]);
        }
        if (strArrSplit.length == 2) {
            return new Locale(strArrSplit[0], strArrSplit[1]);
        }
        if (strArrSplit.length == 3) {
            return new Locale(strArrSplit[0], strArrSplit[1], strArrSplit[2]);
        }
        return null;
    }
}
