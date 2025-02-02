package androidx.core.os;

import android.os.LocaleList;
import java.util.Locale;

/* loaded from: classes.dex */
public final class LocaleListCompat {
    private static final LocaleListCompat sEmptyLocaleList = wrap(new LocaleList(new Locale[0]));
    private final LocaleListInterface mImpl;

    static class Api21Impl {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new Locale[]{new Locale("en", "XA"), new Locale("ar", "XB")};
        }
    }

    private LocaleListCompat(LocaleListPlatformWrapper localeListPlatformWrapper) {
        this.mImpl = localeListPlatformWrapper;
    }

    public static LocaleListCompat forLanguageTags(String str) {
        if (str == null || str.isEmpty()) {
            return sEmptyLocaleList;
        }
        String[] split = str.split(",", -1);
        int length = split.length;
        Locale[] localeArr = new Locale[length];
        for (int i = 0; i < length; i++) {
            String str2 = split[i];
            int i2 = Api21Impl.$r8$clinit;
            localeArr[i] = Locale.forLanguageTag(str2);
        }
        return wrap(new LocaleList(localeArr));
    }

    public static LocaleListCompat getEmptyLocaleList() {
        return sEmptyLocaleList;
    }

    public static LocaleListCompat wrap(LocaleList localeList) {
        return new LocaleListCompat(new LocaleListPlatformWrapper(localeList));
    }

    public final boolean equals(Object obj) {
        if (obj instanceof LocaleListCompat) {
            if (this.mImpl.equals(((LocaleListCompat) obj).mImpl)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.mImpl.hashCode();
    }

    public final boolean isEmpty() {
        return this.mImpl.isEmpty();
    }

    public final String toString() {
        return this.mImpl.toString();
    }
}
