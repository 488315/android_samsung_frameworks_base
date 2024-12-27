package com.android.server.wm;

import android.os.LocaleList;

import java.util.Locale;

public abstract class LocaleOverlayHelper {
    public static LocaleList combineLocalesIfOverlayExists(
            LocaleList localeList, LocaleList localeList2) {
        if (localeList == null || localeList.isEmpty()) {
            return localeList;
        }
        Locale[] localeArr = new Locale[localeList2.size() + localeList.size()];
        for (int i = 0; i < localeList.size(); i++) {
            localeArr[i] = localeList.get(i);
        }
        for (int i2 = 0; i2 < localeList2.size(); i2++) {
            localeArr[localeList.size() + i2] = localeList2.get(i2);
        }
        return new LocaleList(localeArr);
    }
}
