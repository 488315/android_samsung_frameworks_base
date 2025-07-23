package com.samsung.android.app;

import android.content.Context;
import com.android.internal.app.LocalePicker;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes6.dex */
public class SemLocalePicker {
    private SemLocalePicker() {
    }

    public static class LocaleInfo {
        String label;
        Locale locale;

        private LocaleInfo(LocalePicker.LocaleInfo localeInfo) {
            if (localeInfo != null) {
                this.label = localeInfo.getLabel();
                this.locale = localeInfo.getLocale();
            }
        }

        public String getLabel() {
            return this.label;
        }

        public Locale getLocale() {
            return this.locale;
        }
    }

    public static List<LocaleInfo> getAllLocales(Context context) {
        List<LocalePicker.LocaleInfo> allAssetLocales = LocalePicker.getAllAssetLocales(context, false);
        ArrayList arrayList = new ArrayList();
        if (allAssetLocales != null && allAssetLocales.size() != 0) {
            for (LocalePicker.LocaleInfo localeInfo : allAssetLocales) {
                if (localeInfo != null) {
                    arrayList.add(new LocaleInfo(localeInfo));
                }
            }
        }
        return arrayList;
    }

    public static void updateLocale(Locale locale) {
        if (locale == null) {
            return;
        }
        LocalePicker.updateLocale(locale);
    }
}
