package com.android.server.inputmethod;

import android.content.res.Resources;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;
import com.android.server.inputmethod.LocaleUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public abstract class SubtypeUtils {
    public static final String SUBTYPE_MODE_ANY = null;
    public static InputMethodInfo sCachedInputMethodInfo;
    public static ArrayList sCachedResult;
    public static LocaleList sCachedSystemLocales;
    public static final Object sCacheLock = new Object();
    public static final LocaleUtils.LocaleExtractor sSubtypeToLocale = new LocaleUtils.LocaleExtractor() { // from class: com.android.server.inputmethod.SubtypeUtils$$ExternalSyntheticLambda0
        @Override // com.android.server.inputmethod.LocaleUtils.LocaleExtractor
        public final Locale get(Object obj) {
            Locale lambda$static$0;
            lambda$static$0 = SubtypeUtils.lambda$static$0((InputMethodSubtype) obj);
            return lambda$static$0;
        }
    };

    public static boolean containsSubtypeOf(InputMethodInfo inputMethodInfo, Locale locale, boolean z, String str) {
        if (locale == null) {
            return false;
        }
        int subtypeCount = inputMethodInfo.getSubtypeCount();
        for (int i = 0; i < subtypeCount; i++) {
            InputMethodSubtype subtypeAt = inputMethodInfo.getSubtypeAt(i);
            if (z) {
                Locale localeObject = subtypeAt.getLocaleObject();
                if (localeObject == null) {
                    continue;
                } else if (TextUtils.equals(localeObject.getLanguage(), locale.getLanguage())) {
                    if (!TextUtils.equals(localeObject.getCountry(), locale.getCountry())) {
                        continue;
                    }
                    if (str != SUBTYPE_MODE_ANY || TextUtils.isEmpty(str) || str.equalsIgnoreCase(subtypeAt.getMode())) {
                        return true;
                    }
                } else {
                    continue;
                }
            } else {
                if (!TextUtils.equals(new Locale(LocaleUtils.getLanguageFromLocaleString(subtypeAt.getLocale())).getLanguage(), locale.getLanguage())) {
                    continue;
                }
                return str != SUBTYPE_MODE_ANY ? true : true;
            }
        }
        return false;
    }

    public static ArrayList getSubtypes(InputMethodInfo inputMethodInfo) {
        ArrayList arrayList = new ArrayList();
        int subtypeCount = inputMethodInfo.getSubtypeCount();
        for (int i = 0; i < subtypeCount; i++) {
            arrayList.add(inputMethodInfo.getSubtypeAt(i));
        }
        return arrayList;
    }

    public static boolean isValidSubtypeId(InputMethodInfo inputMethodInfo, int i) {
        return getSubtypeIdFromHashCode(inputMethodInfo, i) != -1;
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

    public static /* synthetic */ Locale lambda$static$0(InputMethodSubtype inputMethodSubtype) {
        if (inputMethodSubtype != null) {
            return inputMethodSubtype.getLocaleObject();
        }
        return null;
    }

    public static ArrayList getImplicitlyApplicableSubtypesLocked(Resources resources, InputMethodInfo inputMethodInfo) {
        LocaleList locales = resources.getConfiguration().getLocales();
        Object obj = sCacheLock;
        synchronized (obj) {
            if (locales.equals(sCachedSystemLocales) && sCachedInputMethodInfo == inputMethodInfo) {
                return new ArrayList(sCachedResult);
            }
            ArrayList implicitlyApplicableSubtypesLockedImpl = getImplicitlyApplicableSubtypesLockedImpl(resources, inputMethodInfo);
            synchronized (obj) {
                sCachedSystemLocales = locales;
                sCachedInputMethodInfo = inputMethodInfo;
                sCachedResult = new ArrayList(implicitlyApplicableSubtypesLockedImpl);
            }
            return implicitlyApplicableSubtypesLockedImpl;
        }
    }

    public static ArrayList getImplicitlyApplicableSubtypesLockedImpl(Resources resources, InputMethodInfo inputMethodInfo) {
        InputMethodSubtype findLastResortApplicableSubtypeLocked;
        boolean z;
        ArrayList subtypes = getSubtypes(inputMethodInfo);
        LocaleList locales = resources.getConfiguration().getLocales();
        String locale = locales.get(0).toString();
        if (TextUtils.isEmpty(locale)) {
            return new ArrayList();
        }
        int size = subtypes.size();
        ArrayMap arrayMap = new ArrayMap();
        for (int i = 0; i < size; i++) {
            InputMethodSubtype inputMethodSubtype = (InputMethodSubtype) subtypes.get(i);
            if (inputMethodSubtype.overridesImplicitlyEnabledSubtype()) {
                String mode = inputMethodSubtype.getMode();
                if (!arrayMap.containsKey(mode)) {
                    arrayMap.put(mode, inputMethodSubtype);
                }
            }
        }
        if (arrayMap.size() > 0) {
            return new ArrayList(arrayMap.values());
        }
        ArrayMap arrayMap2 = new ArrayMap();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < size; i2++) {
            InputMethodSubtype inputMethodSubtype2 = (InputMethodSubtype) subtypes.get(i2);
            String mode2 = inputMethodSubtype2.getMode();
            if ("keyboard".equals(mode2)) {
                arrayList.add(inputMethodSubtype2);
            } else {
                if (!arrayMap2.containsKey(mode2)) {
                    arrayMap2.put(mode2, new ArrayList());
                }
                ((ArrayList) arrayMap2.get(mode2)).add(inputMethodSubtype2);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        LocaleUtils.filterByLanguage(arrayList, sSubtypeToLocale, locales, arrayList2);
        if (!arrayList2.isEmpty()) {
            int size2 = arrayList2.size();
            int i3 = 0;
            while (true) {
                if (i3 >= size2) {
                    z = false;
                    break;
                }
                if (((InputMethodSubtype) arrayList2.get(i3)).isAsciiCapable()) {
                    z = true;
                    break;
                }
                i3++;
            }
            if (!z) {
                int size3 = arrayList.size();
                for (int i4 = 0; i4 < size3; i4++) {
                    InputMethodSubtype inputMethodSubtype3 = (InputMethodSubtype) arrayList.get(i4);
                    if ("keyboard".equals(inputMethodSubtype3.getMode()) && inputMethodSubtype3.containsExtraValueKey("EnabledWhenDefaultIsNotAsciiCapable")) {
                        arrayList2.add(inputMethodSubtype3);
                    }
                }
            }
        }
        if (arrayList2.isEmpty() && (findLastResortApplicableSubtypeLocked = findLastResortApplicableSubtypeLocked(resources, subtypes, "keyboard", locale, true)) != null) {
            arrayList2.add(findLastResortApplicableSubtypeLocked);
        }
        Iterator it = arrayMap2.values().iterator();
        while (it.hasNext()) {
            LocaleUtils.filterByLanguage((ArrayList) it.next(), sSubtypeToLocale, locales, arrayList2);
        }
        return arrayList2;
    }

    public static InputMethodSubtype findLastResortApplicableSubtypeLocked(Resources resources, List list, String str, String str2, boolean z) {
        InputMethodSubtype inputMethodSubtype = null;
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = resources.getConfiguration().locale.toString();
        }
        String languageFromLocaleString = LocaleUtils.getLanguageFromLocaleString(str2);
        int size = list.size();
        int i = 0;
        boolean z2 = false;
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
                if (!z2 && languageFromLocaleString.equals(languageFromLocaleString2)) {
                    z2 = true;
                    inputMethodSubtype2 = inputMethodSubtype3;
                }
            }
            i++;
        }
        return (inputMethodSubtype2 == null && z) ? inputMethodSubtype : inputMethodSubtype2;
    }
}
