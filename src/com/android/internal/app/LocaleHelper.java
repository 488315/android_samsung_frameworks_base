package com.android.internal.app;

import android.icu.text.CaseMap;
import android.icu.text.ListFormatter;
import android.icu.text.NumberingSystem;
import android.icu.util.ULocale;
import android.os.LocaleList;
import com.android.internal.app.LocaleStore;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/* loaded from: classes5.dex */
public class LocaleHelper {
    public static String toSentenceCase(String str, Locale locale) {
        return CaseMap.toTitle().wholeString().noLowercase().apply(locale, null, str);
    }

    public static String normalizeForSearch(String str, Locale locale) {
        return str.toUpperCase();
    }

    private static boolean shouldUseDialectName(Locale locale) {
        String language = locale.getLanguage();
        return "fa".equals(language) || "ro".equals(language) || "zh".equals(language) || "my".equals(language) || "ZG".equals(locale.getCountry());
    }

    public static String getDisplayName(Locale locale, Locale locale2, boolean z) {
        String displayName;
        ULocale forLocale = ULocale.forLocale(locale2);
        if (shouldUseDialectName(locale)) {
            displayName = ULocale.getDisplayNameWithDialect(locale.toLanguageTag(), forLocale);
        } else {
            displayName = ULocale.getDisplayName(locale.toLanguageTag(), forLocale);
        }
        return z ? toSentenceCase(displayName, locale2) : displayName;
    }

    public static String getDisplayName(Locale locale, boolean z) {
        return getDisplayName(locale, Locale.getDefault(), z);
    }

    public static String getDisplayCountry(Locale locale, Locale locale2) {
        String languageTag = locale.toLanguageTag();
        ULocale forLocale = ULocale.forLocale(locale2);
        String displayCountry = ULocale.getDisplayCountry(languageTag, forLocale);
        return locale.getUnicodeLocaleType("nu") != null ? String.format("%s (%s)", displayCountry, ULocale.getDisplayKeywordValue(languageTag, "numbers", forLocale)) : displayCountry;
    }

    public static String getDisplayCountry(Locale locale) {
        return ULocale.getDisplayCountry(locale.toLanguageTag(), ULocale.getDefault());
    }

    public static String getDisplayLocaleList(LocaleList localeList, Locale locale, int i) {
        int size;
        int i2;
        if (locale == null) {
            locale = Locale.getDefault();
        }
        boolean z = localeList.size() > i;
        if (z) {
            size = i + 1;
            i2 = i;
        } else {
            size = localeList.size();
            i2 = size;
        }
        String[] strArr = new String[size];
        for (int i3 = 0; i3 < i2; i3++) {
            strArr[i3] = getDisplayName(localeList.get(i3), locale, false);
        }
        if (z) {
            strArr[i] = "&";
        }
        return ListFormatter.getInstance(locale).format(strArr);
    }

    public static String getDisplayNumberingSystemKeyValue(Locale locale, Locale locale2) {
        return new ULocale.Builder().setUnicodeLocaleKeyword("nu", NumberingSystem.getInstance(locale).getName()).build().getDisplayKeywordValue("numbers", ULocale.forLocale(locale2));
    }

    public static Locale addLikelySubtags(Locale locale) {
        return ULocale.addLikelySubtags(ULocale.forLocale(locale)).toLocale();
    }

    public static final class LocaleInfoComparator implements Comparator<LocaleStore.LocaleInfo> {
        private static final String PREFIX_ARABIC = "ال";
        private final Collator mCollator;
        private final boolean mCountryMode;
        private final boolean mUseSecSuggestion;

        public LocaleInfoComparator(Locale locale, boolean z) {
            this(locale, z, false);
        }

        public LocaleInfoComparator(Locale locale, boolean z, boolean z2) {
            this.mCollator = Collator.getInstance(locale);
            this.mCountryMode = z;
            this.mUseSecSuggestion = z2;
        }

        private String removePrefixForCompare(Locale locale, String str) {
            return ("ar".equals(locale.getLanguage()) && str.startsWith(PREFIX_ARABIC)) ? str.substring(2) : str;
        }

        @Override // java.util.Comparator
        public int compare(LocaleStore.LocaleInfo localeInfo, LocaleStore.LocaleInfo localeInfo2) {
            return semCompare(localeInfo, localeInfo2);
        }

        private int semCompare(LocaleStore.LocaleInfo localeInfo, LocaleStore.LocaleInfo localeInfo2) {
            if (localeInfo.isAppCurrentLocale() || localeInfo2.isAppCurrentLocale()) {
                return localeInfo.isAppCurrentLocale() ? -1 : 1;
            }
            if (localeInfo.isSystemLocale() || localeInfo2.isSystemLocale()) {
                return localeInfo.isSystemLocale() ? -1 : 1;
            }
            if (localeInfo.isSecXmlSuggested() != localeInfo2.isSecXmlSuggested()) {
                return localeInfo.isSecXmlSuggested() ? -1 : 1;
            }
            if (localeInfo.isSuggested() != localeInfo2.isSuggested()) {
                return localeInfo.isSuggested() ? -1 : 1;
            }
            if (localeInfo.isPriorityLocale() != localeInfo2.isPriorityLocale()) {
                return localeInfo.isPriorityLocale() ? -1 : 1;
            }
            if (!this.mUseSecSuggestion || localeInfo.isSecSuggested() == localeInfo2.isSecSuggested()) {
                return this.mCollator.compare(removePrefixForCompare(localeInfo.getLocale(), localeInfo.getLabel(this.mCountryMode)), removePrefixForCompare(localeInfo2.getLocale(), localeInfo2.getLabel(this.mCountryMode)));
            }
            return localeInfo.isSecSuggested() ? -1 : 1;
        }
    }
}
