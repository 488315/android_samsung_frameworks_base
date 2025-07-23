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
public final class CarProperties {
    private CarProperties() {
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

    public static Optional<Integer> boot_user_override_id() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("android.car.systemuser.bootuseroverrideid")));
    }

    public static void boot_user_override_id(Integer num) {
        SystemProperties.set("android.car.systemuser.bootuseroverrideid", num == null ? "" : num.toString());
    }

    public static Optional<String> trusted_device_device_name_prefix() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ro.android.car.trusteddevice.device_name_prefix")));
    }

    public static Optional<Integer> number_pre_created_users() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("android.car.number_pre_created_users")));
    }

    public static void number_pre_created_users(Integer num) {
        SystemProperties.set("android.car.number_pre_created_users", num == null ? "" : num.toString());
    }

    public static Optional<Integer> number_pre_created_guests() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("android.car.number_pre_created_guests")));
    }

    public static void number_pre_created_guests(Integer num) {
        SystemProperties.set("android.car.number_pre_created_guests", num == null ? "" : num.toString());
    }

    public static Optional<Boolean> user_hal_enabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("android.car.user_hal_enabled")));
    }

    public static void user_hal_enabled(Boolean bool) {
        SystemProperties.set("android.car.user_hal_enabled", bool == null ? "" : bool.toString());
    }

    public static Optional<Integer> user_hal_timeout() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("android.car.user_hal_timeout")));
    }

    public static void user_hal_timeout(Integer num) {
        SystemProperties.set("android.car.user_hal_timeout", num == null ? "" : num.toString());
    }

    public static Optional<Integer> device_policy_manager_timeout() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("android.car.device_policy_manager_timeout")));
    }

    public static void device_policy_manager_timeout(Integer num) {
        SystemProperties.set("android.car.device_policy_manager_timeout", num == null ? "" : num.toString());
    }
}
