package com.android.server.wm;

import android.os.LocaleList;
import java.util.Locale;

/* loaded from: classes3.dex */
public abstract class LocaleOverlayHelper {
    public static LocaleList combineLocalesIfOverlayExists(LocaleList localeList, LocaleList localeList2) {
        return (localeList == null || localeList.isEmpty()) ? localeList : combineLocales(localeList, localeList2);
    }

    public static LocaleList combineLocales(LocaleList localeList, LocaleList localeList2) {
        Locale[] localeArr = new Locale[localeList.size() + localeList2.size()];
        for (int i = 0; i < localeList.size(); i++) {
            localeArr[i] = localeList.get(i);
        }
        for (int i2 = 0; i2 < localeList2.size(); i2++) {
            localeArr[localeList.size() + i2] = localeList2.get(i2);
        }
        return new LocaleList(localeArr);
    }
}
