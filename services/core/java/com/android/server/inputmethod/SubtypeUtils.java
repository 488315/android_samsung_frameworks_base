package com.android.server.inputmethod;

import android.os.LocaleList;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;

import com.android.server.VcnManagementService$$ExternalSyntheticLambda10;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public abstract class SubtypeUtils {
    public static final Object sCacheLock = new Object();
    public static InputMethodInfo sCachedInputMethodInfo;
    public static ArrayList sCachedResult;
    public static LocaleList sCachedSystemLocales;
    public static final VcnManagementService$$ExternalSyntheticLambda10 sSubtypeToLocale = null;

    public static boolean containsSubtypeOf(
            InputMethodInfo inputMethodInfo, Locale locale, boolean z, String str) {
        if (locale == null) {
            return false;
        }
        int subtypeCount = inputMethodInfo.getSubtypeCount();
        for (int i = 0; i < subtypeCount; i++) {
            InputMethodSubtype subtypeAt = inputMethodInfo.getSubtypeAt(i);
            if (!z) {
                if (!TextUtils.equals(
                        new Locale(LocaleUtils.getLanguageFromLocaleString(subtypeAt.getLocale()))
                                .getLanguage(),
                        locale.getLanguage())) {
                    continue;
                }
                return !TextUtils.isEmpty(str) ? true : true;
            }
            Locale localeObject = subtypeAt.getLocaleObject();
            if (localeObject == null) {
                continue;
            } else if (TextUtils.equals(localeObject.getLanguage(), locale.getLanguage())) {
                if (!TextUtils.equals(localeObject.getCountry(), locale.getCountry())) {
                    continue;
                }
                if (!TextUtils.isEmpty(str) || str.equalsIgnoreCase(subtypeAt.getMode())) {}
            } else {
                continue;
            }
        }
        return false;
    }

    public static InputMethodSubtype findLastResortApplicableSubtype(
            String str, String str2, List list) {
        InputMethodSubtype inputMethodSubtype = null;
        if (list.isEmpty()) {
            return null;
        }
        String languageFromLocaleString = LocaleUtils.getLanguageFromLocaleString(str2);
        int size = list.size();
        int i = 0;
        boolean z = false;
        InputMethodSubtype inputMethodSubtype2 = null;
        while (true) {
            if (i >= size) {
                break;
            }
            InputMethodSubtype inputMethodSubtype3 = (InputMethodSubtype) list.get(i);
            String locale = inputMethodSubtype3.getLocale();
            String languageFromLocaleString2 = LocaleUtils.getLanguageFromLocaleString(locale);
            if (str == null || ((InputMethodSubtype) list.get(i)).getMode().equalsIgnoreCase(str)) {
                if (inputMethodSubtype == null) {
                    inputMethodSubtype = inputMethodSubtype3;
                }
                if (str2.equals(locale)) {
                    inputMethodSubtype2 = inputMethodSubtype3;
                    break;
                }
                if (!z && languageFromLocaleString.equals(languageFromLocaleString2)) {
                    z = true;
                    inputMethodSubtype2 = inputMethodSubtype3;
                }
            }
            i++;
        }
        return inputMethodSubtype2 == null ? inputMethodSubtype : inputMethodSubtype2;
    }

    public static ArrayList getImplicitlyApplicableSubtypes(
            LocaleList localeList, InputMethodInfo inputMethodInfo) {
        ArrayList arrayList;
        InputMethodSubtype findLastResortApplicableSubtype;
        synchronized (sCacheLock) {
            try {
                if (localeList.equals(sCachedSystemLocales)
                        && sCachedInputMethodInfo == inputMethodInfo) {
                    return new ArrayList(sCachedResult);
                }
                ArrayList arrayList2 = new ArrayList();
                int subtypeCount = inputMethodInfo.getSubtypeCount();
                for (int i = 0; i < subtypeCount; i++) {
                    arrayList2.add(inputMethodInfo.getSubtypeAt(i));
                }
                String locale = localeList.get(0).toString();
                if (TextUtils.isEmpty(locale)) {
                    arrayList = new ArrayList();
                } else {
                    int size = arrayList2.size();
                    ArrayMap arrayMap = new ArrayMap();
                    for (int i2 = 0; i2 < size; i2++) {
                        InputMethodSubtype inputMethodSubtype =
                                (InputMethodSubtype) arrayList2.get(i2);
                        if (inputMethodSubtype.overridesImplicitlyEnabledSubtype()) {
                            String mode = inputMethodSubtype.getMode();
                            if (!arrayMap.containsKey(mode)) {
                                arrayMap.put(mode, inputMethodSubtype);
                            }
                        }
                    }
                    if (arrayMap.size() > 0) {
                        arrayList = new ArrayList(arrayMap.values());
                    } else {
                        ArrayMap arrayMap2 = new ArrayMap();
                        ArrayList arrayList3 = new ArrayList();
                        for (int i3 = 0; i3 < size; i3++) {
                            InputMethodSubtype inputMethodSubtype2 =
                                    (InputMethodSubtype) arrayList2.get(i3);
                            String mode2 = inputMethodSubtype2.getMode();
                            if ("keyboard".equals(mode2)) {
                                arrayList3.add(inputMethodSubtype2);
                            } else {
                                if (!arrayMap2.containsKey(mode2)) {
                                    arrayMap2.put(mode2, new ArrayList());
                                }
                                ((ArrayList) arrayMap2.get(mode2)).add(inputMethodSubtype2);
                            }
                        }
                        ArrayList arrayList4 = new ArrayList();
                        LocaleUtils.filterByLanguage(arrayList3, localeList, arrayList4);
                        if (!arrayList4.isEmpty()) {
                            int size2 = arrayList4.size();
                            int i4 = 0;
                            while (true) {
                                if (i4 >= size2) {
                                    int size3 = arrayList3.size();
                                    for (int i5 = 0; i5 < size3; i5++) {
                                        InputMethodSubtype inputMethodSubtype3 =
                                                (InputMethodSubtype) arrayList3.get(i5);
                                        if ("keyboard".equals(inputMethodSubtype3.getMode())
                                                && inputMethodSubtype3.containsExtraValueKey(
                                                        "EnabledWhenDefaultIsNotAsciiCapable")) {
                                            arrayList4.add(inputMethodSubtype3);
                                        }
                                    }
                                } else {
                                    if (((InputMethodSubtype) arrayList4.get(i4))
                                            .isAsciiCapable()) {
                                        break;
                                    }
                                    i4++;
                                }
                            }
                        }
                        if (arrayList4.isEmpty()
                                && (findLastResortApplicableSubtype =
                                                findLastResortApplicableSubtype(
                                                        "keyboard", locale, arrayList2))
                                        != null) {
                            arrayList4.add(findLastResortApplicableSubtype);
                        }
                        Iterator it = arrayMap2.values().iterator();
                        while (it.hasNext()) {
                            LocaleUtils.filterByLanguage(
                                    (ArrayList) it.next(), localeList, arrayList4);
                        }
                        arrayList = arrayList4;
                    }
                }
                synchronized (sCacheLock) {
                    sCachedSystemLocales = localeList;
                    sCachedInputMethodInfo = inputMethodInfo;
                    sCachedResult = new ArrayList(arrayList);
                }
                return arrayList;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static int getSubtypeIdFromHashCode(InputMethodInfo inputMethodInfo, int i) {
        if (inputMethodInfo == null) {
            return -1;
        }
        int subtypeCount = inputMethodInfo.getSubtypeCount();
        for (int i2 = 0; i2 < subtypeCount; i2++) {
            if (i == inputMethodInfo.getSubtypeAt(i2).hashCode()) {
                return i2;
            }
        }
        return -1;
    }
}
