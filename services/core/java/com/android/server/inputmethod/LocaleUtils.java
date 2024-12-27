package com.android.server.inputmethod;

import android.icu.util.ULocale;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.inputmethod.InputMethodSubtype;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class LocaleUtils {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ScoreEntry implements Comparable {
        public int mIndex;
        public final byte[] mScore;

        public ScoreEntry(int i, byte[] bArr) {
            this.mIndex = -1;
            this.mScore = new byte[bArr.length];
            int i2 = 0;
            while (true) {
                byte[] bArr2 = this.mScore;
                if (i2 >= bArr2.length) {
                    this.mIndex = i;
                    return;
                } else {
                    bArr2[i2] = bArr[i2];
                    i2++;
                }
            }
        }

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            byte[] bArr = this.mScore;
            byte[] bArr2 = ((ScoreEntry) obj).mScore;
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i2 >= bArr.length) {
                    break;
                }
                byte b = bArr[i2];
                byte b2 = bArr2[i2];
                if (b > b2) {
                    i = 1;
                    break;
                }
                if (b < b2) {
                    i = -1;
                    break;
                }
                i2++;
            }
            return i * (-1);
        }
    }

    public static void filterByLanguage(List list, LocaleList localeList, ArrayList arrayList) {
        byte b;
        byte b2;
        if (localeList.isEmpty()) {
            return;
        }
        int size = localeList.size();
        ArrayMap arrayMap = new ArrayMap();
        byte[] bArr = new byte[size];
        ULocale[] uLocaleArr = new ULocale[size];
        int size2 = list.size();
        byte b3 = 0;
        int i = 0;
        while (i < size2) {
            InputMethodSubtype inputMethodSubtype = (InputMethodSubtype) list.get(i);
            Locale localeObject = inputMethodSubtype != null ? inputMethodSubtype.getLocaleObject() : null;
            if (localeObject != null) {
                int i2 = b3;
                boolean z = true;
                while (i2 < size) {
                    Locale locale = localeList.get(i2);
                    if (TextUtils.equals(localeObject.getLanguage(), locale.getLanguage())) {
                        if (uLocaleArr[i2] == null) {
                            uLocaleArr[i2] = ULocale.addLikelySubtags(ULocale.forLocale(locale));
                        }
                        ULocale uLocale = uLocaleArr[i2];
                        ULocale forLocale = ULocale.forLocale(localeObject);
                        byte b4 = 4;
                        if (!uLocale.equals(forLocale)) {
                            ULocale addLikelySubtags = ULocale.addLikelySubtags(forLocale);
                            String script = uLocale.getScript();
                            if (script.isEmpty() || !script.equals(addLikelySubtags.getScript())) {
                                b4 = 1;
                            } else {
                                String country = uLocale.getCountry();
                                if (country.isEmpty() || !country.equals(addLikelySubtags.getCountry())) {
                                    b4 = 2;
                                } else {
                                    String script2 = forLocale.getScript();
                                    String country2 = forLocale.getCountry();
                                    if ((!script2.isEmpty() && !script2.equals(addLikelySubtags.getScript())) || (!country2.isEmpty() && !country2.equals(addLikelySubtags.getCountry()))) {
                                        b4 = 3;
                                    }
                                }
                            }
                        }
                        bArr[i2] = b4;
                        if (z && b4 != 0) {
                            z = false;
                        }
                    } else {
                        bArr[i2] = b3;
                    }
                    i2++;
                    b3 = 0;
                }
                if (!z) {
                    String language = localeObject.getLanguage();
                    ScoreEntry scoreEntry = (ScoreEntry) arrayMap.get(language);
                    if (scoreEntry == null) {
                        arrayMap.put(language, new ScoreEntry(i, bArr));
                    } else {
                        byte[] bArr2 = scoreEntry.mScore;
                        int i3 = 0;
                        while (true) {
                            if (i3 < bArr2.length && (b = bArr2[i3]) <= (b2 = bArr[i3])) {
                                if (b < b2) {
                                    int i4 = 0;
                                    while (true) {
                                        byte[] bArr3 = scoreEntry.mScore;
                                        if (i4 >= bArr3.length) {
                                            break;
                                        }
                                        bArr3[i4] = bArr[i4];
                                        i4++;
                                    }
                                    scoreEntry.mIndex = i;
                                } else {
                                    i3++;
                                }
                            }
                        }
                    }
                }
            }
            i++;
            b3 = 0;
        }
        int size3 = arrayMap.size();
        ScoreEntry[] scoreEntryArr = new ScoreEntry[size3];
        for (int i5 = 0; i5 < size3; i5++) {
            scoreEntryArr[i5] = (ScoreEntry) arrayMap.valueAt(i5);
        }
        Arrays.sort(scoreEntryArr);
        for (int i6 = 0; i6 < size3; i6++) {
            arrayList.add(list.get(scoreEntryArr[i6].mIndex));
        }
    }

    public static String getLanguageFromLocaleString(String str) {
        int indexOf = str.indexOf(95);
        return indexOf < 0 ? str : str.substring(0, indexOf);
    }
}
