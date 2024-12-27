package com.android.internal.inputmethod;

import android.content.om.SamsungThemeConstants;
import android.telecom.Logging.Session;
import android.text.TextUtils;

import java.util.Locale;

public class SubtypeLocaleUtils {
    public static Locale constructLocaleFromString(String localeStr) {
        if (TextUtils.isEmpty(localeStr)) {
            return null;
        }
        String[] localeParams = localeStr.split(Session.SESSION_SEPARATION_CHAR_CHILD, 3);
        if (localeParams.length >= 1
                && SamsungThemeConstants.TAGALOG_LOCALE_CODE.equals(localeParams[0])) {
            localeParams[0] = SamsungThemeConstants.FILIPINO_LOCALE_CODE;
        }
        if (localeParams.length == 1) {
            return new Locale(localeParams[0]);
        }
        if (localeParams.length == 2) {
            return new Locale(localeParams[0], localeParams[1]);
        }
        if (localeParams.length == 3) {
            return new Locale(localeParams[0], localeParams[1], localeParams[2]);
        }
        return null;
    }
}
