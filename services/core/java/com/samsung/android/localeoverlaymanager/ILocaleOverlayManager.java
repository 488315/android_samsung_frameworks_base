package com.samsung.android.localeoverlaymanager;

import android.os.LocaleList;

/* loaded from: classes2.dex */
public interface ILocaleOverlayManager {
    void applyLocales(LocaleList localeList, int i, OverlayChangeObserver overlayChangeObserver);

    boolean applyLocalesForPackage(String str, int i, int i2, OverlayChangeObserver overlayChangeObserver);

    void applyPerAppLocale(LocaleList localeList, String str, int i);

    void checkSanityOfOverlays(int i);

    void cleanUpOverlays();

    void initializeOverlays(boolean z);

    void initializeOverlaysForSafeMode();
}
