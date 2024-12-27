package com.android.server.locales;

import java.util.Locale;

public final class AppLocaleChangedAtomRecord {
    public final int mCallingUid;
    public String mNewLocales;
    public String mPrevLocales;
    public int mTargetUid = -1;
    public int mStatus = 0;
    public int mCaller = 0;

    public AppLocaleChangedAtomRecord(int i) {
        this.mNewLocales = "default-";
        this.mPrevLocales = "default-";
        this.mCallingUid = i;
        Locale locale = Locale.getDefault();
        if (locale != null) {
            this.mNewLocales = "default-" + locale.toLanguageTag();
            this.mPrevLocales = "default-" + locale.toLanguageTag();
        }
    }

    public static String convertEmptyLocales(String str) {
        Locale locale;
        if (!"".equals(str) || (locale = Locale.getDefault()) == null) {
            return str;
        }
        return "default-" + locale.toLanguageTag();
    }
}
