package android.os;

import android.icu.util.ULocale;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

/* loaded from: classes3.dex */
public final class LocaleList implements Parcelable {
    private static final int NUM_PSEUDO_LOCALES = 2;
    private static final String STRING_AR_XB = "ar-XB";
    private static final String STRING_EN_XA = "en-XA";
    private final Locale[] mList;
    private final String mStringRepresentation;
    private static final Locale[] sEmptyList = new Locale[0];
    private static final LocaleList sEmptyLocaleList = new LocaleList(new Locale[0]);
    public static final Parcelable.Creator<LocaleList> CREATOR = new Parcelable.Creator<LocaleList>() { // from class: android.os.LocaleList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LocaleList createFromParcel(Parcel parcel) {
            return LocaleList.forLanguageTags(parcel.readString8());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LocaleList[] newArray(int i) {
            return new LocaleList[i];
        }
    };
    private static final Locale LOCALE_EN_XA = new Locale("en", "XA");
    private static final Locale LOCALE_AR_XB = new Locale("ar", "XB");
    private static final Locale EN_LATN = Locale.forLanguageTag("en-Latn");
    private static final Object sLock = new Object();
    private static LocaleList sLastExplicitlySetLocaleList = null;
    private static LocaleList sDefaultLocaleList = null;
    private static LocaleList sDefaultAdjustedLocaleList = null;
    private static Locale sLastDefaultLocale = null;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Locale get(int i) {
        if (i < 0) {
            return null;
        }
        Locale[] localeArr = this.mList;
        if (i < localeArr.length) {
            return localeArr[i];
        }
        return null;
    }

    public boolean isEmpty() {
        return this.mList.length == 0;
    }

    public int size() {
        return this.mList.length;
    }

    public int indexOf(Locale locale) {
        int i = 0;
        while (true) {
            Locale[] localeArr = this.mList;
            if (i >= localeArr.length) {
                return -1;
            }
            if (localeArr[i].equals(locale)) {
                return i;
            }
            i++;
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LocaleList)) {
            return false;
        }
        Locale[] localeArr = ((LocaleList) obj).mList;
        if (this.mList.length != localeArr.length) {
            return false;
        }
        int i = 0;
        while (true) {
            Locale[] localeArr2 = this.mList;
            if (i >= localeArr2.length) {
                return true;
            }
            if (!localeArr2[i].equals(localeArr[i])) {
                return false;
            }
            i++;
        }
    }

    public int hashCode() {
        int iHashCode = 1;
        int i = 0;
        while (true) {
            Locale[] localeArr = this.mList;
            if (i >= localeArr.length) {
                return iHashCode;
            }
            iHashCode = (iHashCode * 31) + localeArr[i].hashCode();
            i++;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
        int i = 0;
        while (true) {
            Locale[] localeArr = this.mList;
            if (i < localeArr.length) {
                sb.append(localeArr[i]);
                if (i < this.mList.length - 1) {
                    sb.append(',');
                }
                i++;
            } else {
                sb.append(NavigationBarInflaterView.SIZE_MOD_END);
                return sb.toString();
            }
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mStringRepresentation);
    }

    public String toLanguageTags() {
        return this.mStringRepresentation;
    }

    public Locale[] getIntersection(LocaleList localeList) {
        ArrayList arrayList = new ArrayList();
        for (Locale locale : this.mList) {
            Locale[] localeArr = localeList.mList;
            int length = localeArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (matchesLanguageAndScript(localeArr[i], locale)) {
                    arrayList.add(locale);
                    break;
                }
                i++;
            }
        }
        return (Locale[]) arrayList.toArray(new Locale[0]);
    }

    public LocaleList(Locale... localeArr) {
        if (localeArr.length == 0) {
            this.mList = sEmptyList;
            this.mStringRepresentation = "";
            return;
        }
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < localeArr.length; i++) {
            Locale locale = localeArr[i];
            if (locale == null) {
                throw new NullPointerException("list[" + i + "] is null");
            }
            if (!hashSet.contains(locale)) {
                Locale locale2 = (Locale) locale.clone();
                arrayList.add(locale2);
                sb.append(locale2.toLanguageTag());
                if (i < localeArr.length - 1) {
                    sb.append(',');
                }
                hashSet.add(locale2);
            }
        }
        this.mList = (Locale[]) arrayList.toArray(new Locale[arrayList.size()]);
        this.mStringRepresentation = sb.toString();
    }

    public LocaleList(Locale locale, LocaleList localeList) {
        if (locale == null) {
            throw new NullPointerException("topLocale is null");
        }
        int length = localeList == null ? 0 : localeList.mList.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                i = -1;
                break;
            } else if (locale.equals(localeList.mList[i])) {
                break;
            } else {
                i++;
            }
        }
        int i2 = (i == -1 ? 1 : 0) + length;
        Locale[] localeArr = new Locale[i2];
        localeArr[0] = (Locale) locale.clone();
        if (i == -1) {
            int i3 = 0;
            while (i3 < length) {
                int i4 = i3 + 1;
                localeArr[i4] = (Locale) localeList.mList[i3].clone();
                i3 = i4;
            }
        } else {
            int i5 = 0;
            while (i5 < i) {
                int i6 = i5 + 1;
                localeArr[i6] = (Locale) localeList.mList[i5].clone();
                i5 = i6;
            }
            for (int i7 = i + 1; i7 < length; i7++) {
                localeArr[i7] = (Locale) localeList.mList[i7].clone();
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i8 = 0; i8 < i2; i8++) {
            sb.append(localeArr[i8].toLanguageTag());
            if (i8 < i2 - 1) {
                sb.append(',');
            }
        }
        this.mList = localeArr;
        this.mStringRepresentation = sb.toString();
    }

    public static LocaleList getEmptyLocaleList() {
        return sEmptyLocaleList;
    }

    public static LocaleList forLanguageTags(String str) {
        if (str == null || str.equals("")) {
            return getEmptyLocaleList();
        }
        String[] strArrSplit = str.split(",");
        int length = strArrSplit.length;
        Locale[] localeArr = new Locale[length];
        for (int i = 0; i < length; i++) {
            localeArr[i] = Locale.forLanguageTag(strArrSplit[i]);
        }
        return new LocaleList(localeArr);
    }

    private static String getLikelyScript(Locale locale) {
        String script = locale.getScript();
        return !script.isEmpty() ? script : ULocale.addLikelySubtags(ULocale.forLocale(locale)).getScript();
    }

    private static boolean isPseudoLocale(String str) {
        return STRING_EN_XA.equals(str) || STRING_AR_XB.equals(str);
    }

    public static boolean isPseudoLocale(Locale locale) {
        return LOCALE_EN_XA.equals(locale) || LOCALE_AR_XB.equals(locale);
    }

    public static boolean isPseudoLocale(ULocale uLocale) {
        return isPseudoLocale(uLocale != null ? uLocale.toLocale() : null);
    }

    public static boolean matchesLanguageAndScript(Locale locale, Locale locale2) {
        if (locale.equals(locale2)) {
            return true;
        }
        if (!locale.getLanguage().equals(locale2.getLanguage()) || isPseudoLocale(locale) || isPseudoLocale(locale2)) {
            return false;
        }
        String likelyScript = getLikelyScript(locale);
        if (likelyScript.isEmpty()) {
            String country = locale.getCountry();
            return country.isEmpty() || country.equals(locale2.getCountry());
        }
        return likelyScript.equals(getLikelyScript(locale2));
    }

    private int findFirstMatchIndex(Locale locale) {
        int i = 0;
        while (true) {
            Locale[] localeArr = this.mList;
            if (i >= localeArr.length) {
                return Integer.MAX_VALUE;
            }
            if (matchesLanguageAndScript(locale, localeArr[i])) {
                return i;
            }
            i++;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x001e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int computeFirstMatchIndex(Collection<String> collection, boolean z) {
        int iFindFirstMatchIndex;
        Locale[] localeArr = this.mList;
        if (localeArr.length == 1) {
            return 0;
        }
        if (localeArr.length == 0) {
            return -1;
        }
        if (z) {
            iFindFirstMatchIndex = findFirstMatchIndex(EN_LATN);
            if (iFindFirstMatchIndex == 0) {
                return 0;
            }
            if (iFindFirstMatchIndex >= Integer.MAX_VALUE) {
            }
        } else {
            iFindFirstMatchIndex = Integer.MAX_VALUE;
        }
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            int iFindFirstMatchIndex2 = findFirstMatchIndex(Locale.forLanguageTag(it.next()));
            if (iFindFirstMatchIndex2 == 0) {
                return 0;
            }
            if (iFindFirstMatchIndex2 < iFindFirstMatchIndex) {
                iFindFirstMatchIndex = iFindFirstMatchIndex2;
            }
        }
        if (iFindFirstMatchIndex == Integer.MAX_VALUE) {
            return 0;
        }
        return iFindFirstMatchIndex;
    }

    private Locale computeFirstMatch(Collection<String> collection, boolean z) {
        int iComputeFirstMatchIndex = computeFirstMatchIndex(collection, z);
        if (iComputeFirstMatchIndex == -1) {
            return null;
        }
        return this.mList[iComputeFirstMatchIndex];
    }

    public Locale getFirstMatch(String[] strArr) {
        return computeFirstMatch(Arrays.asList(strArr), false);
    }

    public int getFirstMatchIndex(String[] strArr) {
        return computeFirstMatchIndex(Arrays.asList(strArr), false);
    }

    public Locale getFirstMatchWithEnglishSupported(String[] strArr) {
        return computeFirstMatch(Arrays.asList(strArr), true);
    }

    public int getFirstMatchIndexWithEnglishSupported(Collection<String> collection) {
        return computeFirstMatchIndex(collection, true);
    }

    public int getFirstMatchIndexWithEnglishSupported(String[] strArr) {
        return getFirstMatchIndexWithEnglishSupported(Arrays.asList(strArr));
    }

    public static boolean isPseudoLocalesOnly(String[] strArr) {
        if (strArr == null) {
            return true;
        }
        if (strArr.length > 3) {
            return false;
        }
        for (String str : strArr) {
            if (!str.isEmpty() && !isPseudoLocale(str)) {
                return false;
            }
        }
        return true;
    }

    public static LocaleList getDefault() {
        Locale locale = Locale.getDefault();
        synchronized (sLock) {
            if (!locale.equals(sLastDefaultLocale)) {
                sLastDefaultLocale = locale;
                LocaleList localeList = sDefaultLocaleList;
                if (localeList != null && locale.equals(localeList.get(0))) {
                    return sDefaultLocaleList;
                }
                LocaleList localeList2 = new LocaleList(locale, sLastExplicitlySetLocaleList);
                sDefaultLocaleList = localeList2;
                sDefaultAdjustedLocaleList = localeList2;
            }
            return sDefaultLocaleList;
        }
    }

    public static LocaleList getAdjustedDefault() {
        LocaleList localeList;
        getDefault();
        synchronized (sLock) {
            localeList = sDefaultAdjustedLocaleList;
        }
        return localeList;
    }

    public static void setDefault(LocaleList localeList) {
        setDefault(localeList, 0);
    }

    public static void setDefault(LocaleList localeList, int i) {
        if (localeList == null) {
            throw new NullPointerException("locales is null");
        }
        if (localeList.isEmpty()) {
            throw new IllegalArgumentException("locales is empty");
        }
        synchronized (sLock) {
            Locale locale = localeList.get(i);
            sLastDefaultLocale = locale;
            Locale.setDefault(locale);
            sLastExplicitlySetLocaleList = localeList;
            sDefaultLocaleList = localeList;
            if (i == 0) {
                sDefaultAdjustedLocaleList = localeList;
            } else {
                sDefaultAdjustedLocaleList = new LocaleList(sLastDefaultLocale, localeList);
            }
        }
    }
}
