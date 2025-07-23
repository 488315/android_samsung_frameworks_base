package android.sysprop;

import android.os.SystemProperties;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final class OtaProperties {
    private OtaProperties() {
    }

    private static Boolean tryParseBoolean(String str) {
        if (str == null) {
            return null;
        }
        String lowerCase = str.toLowerCase(Locale.US);
        lowerCase.hashCode();
        switch (lowerCase) {
        }
        return null;
    }

    private static Integer tryParseInteger(String str) {
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private static Integer tryParseUInt(String str) {
        try {
            return Integer.valueOf(Integer.parseUnsignedInt(str));
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private static Long tryParseLong(String str) {
        try {
            return Long.valueOf(str);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private static Long tryParseULong(String str) {
        try {
            return Long.valueOf(Long.parseUnsignedLong(str));
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private static Double tryParseDouble(String str) {
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private static String tryParseString(String str) {
        if ("".equals(str)) {
            return null;
        }
        return str;
    }

    private static <T extends Enum<T>> T tryParseEnum(Class<T> cls, String str) {
        try {
            return (T) Enum.valueOf(cls, str.toUpperCase(Locale.US));
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    private static <T> List<T> tryParseList(Function<String, T> function, String str) {
        if ("".equals(str)) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            StringBuilder sb = new StringBuilder();
            while (i < str.length() && str.charAt(i) != ',') {
                if (str.charAt(i) == '\\') {
                    i++;
                }
                if (i == str.length()) {
                    break;
                }
                sb.append(str.charAt(i));
                i++;
            }
            arrayList.add(function.apply(sb.toString()));
            if (i == str.length()) {
                return arrayList;
            }
            i++;
        }
    }

    private static <T extends Enum<T>> List<T> tryParseEnumList(Class<T> cls, String str) {
        if ("".equals(str)) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (String str2 : str.split(",")) {
            arrayList.add(tryParseEnum(cls, str2));
        }
        return arrayList;
    }

    private static String escape(String str) {
        return str.replaceAll("([\\\\,])", "\\\\$1");
    }

    private static <T> String formatList(List<T> list) {
        StringJoiner stringJoiner = new StringJoiner(",");
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            T next = it.next();
            stringJoiner.add(next == null ? "" : escape(next.toString()));
        }
        return stringJoiner.toString();
    }

    private static String formatUIntList(List<Integer> list) {
        StringJoiner stringJoiner = new StringJoiner(",");
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            Integer next = it.next();
            stringJoiner.add(next == null ? "" : escape(Integer.toUnsignedString(next.intValue())));
        }
        return stringJoiner.toString();
    }

    private static String formatULongList(List<Long> list) {
        StringJoiner stringJoiner = new StringJoiner(",");
        Iterator<Long> it = list.iterator();
        while (it.hasNext()) {
            Long next = it.next();
            stringJoiner.add(next == null ? "" : escape(Long.toUnsignedString(next.longValue())));
        }
        return stringJoiner.toString();
    }

    private static <T extends Enum<T>> String formatEnumList(List<T> list, Function<T, String> function) {
        StringJoiner stringJoiner = new StringJoiner(",");
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            T next = it.next();
            stringJoiner.add(next == null ? "" : function.apply(next));
        }
        return stringJoiner.toString();
    }

    public static Optional<Boolean> warm_reset() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("ota.warm_reset")));
    }

    public static void warm_reset(Boolean bool) {
        String str;
        if (bool == null) {
            str = "";
        } else {
            str = bool.booleanValue() ? "1" : "0";
        }
        SystemProperties.set("ota.warm_reset", str);
    }

    public static Optional<String> ab_ota_partitions() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ro.product.ab_ota_partitions")));
    }

    public static Optional<Boolean> virtual_ab_enabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("ro.virtual_ab.enabled")));
    }

    public static Optional<Boolean> virtual_ab_retrofit() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("ro.virtual_ab.retrofit")));
    }

    public static Optional<String> other_vbmeta_digest() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ota.other.vbmeta_digest")));
    }

    public static void other_vbmeta_digest(String str) {
        SystemProperties.set("ota.other.vbmeta_digest", str == null ? "" : str.toString());
    }
}
