package android.sysprop;

import android.os.SystemProperties;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final class NfcProperties {
    private NfcProperties() {
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

    /* JADX INFO: Access modifiers changed from: private */
    public static Integer tryParseInteger(String str) {
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

    public static Optional<Boolean> debug_enabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("persist.nfc.debug_enabled")));
    }

    public static void debug_enabled(Boolean bool) {
        SystemProperties.set("persist.nfc.debug_enabled", bool == null ? "" : bool.toString());
    }

    public enum snoop_log_mode_values {
        FULL("full"),
        FILTERED("filtered");

        private final String propValue;

        snoop_log_mode_values(String str) {
            this.propValue = str;
        }

        public String getPropValue() {
            return this.propValue;
        }
    }

    public static Optional<snoop_log_mode_values> snoop_log_mode() {
        return Optional.ofNullable((snoop_log_mode_values) tryParseEnum(snoop_log_mode_values.class, SystemProperties.get("persist.nfc.snoop_log_mode")));
    }

    public static void snoop_log_mode(snoop_log_mode_values snoop_log_mode_valuesVar) {
        SystemProperties.set("persist.nfc.snoop_log_mode", snoop_log_mode_valuesVar == null ? "" : snoop_log_mode_valuesVar.getPropValue());
    }

    public static Optional<Boolean> vendor_debug_enabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("persist.nfc.vendor_debug_enabled")));
    }

    public static void vendor_debug_enabled(Boolean bool) {
        SystemProperties.set("persist.nfc.vendor_debug_enabled", bool == null ? "" : bool.toString());
    }

    public static Optional<Boolean> skipNdefRead() {
        String str = SystemProperties.get("nfc.dta.skip_ndef_read");
        if ("".equals(str)) {
            Log.v("NfcProperties", "prop nfc.dta.skip_ndef_read doesn't exist; fallback to legacy prop nfc.dta.skipNdefRead");
            str = SystemProperties.get("nfc.dta.skipNdefRead");
        }
        return Optional.ofNullable(tryParseBoolean(str));
    }

    public static Optional<Boolean> initialized() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("nfc.initialized")));
    }

    public static void initialized(Boolean bool) {
        SystemProperties.set("nfc.initialized", bool == null ? "" : bool.toString());
    }

    public static List<Integer> info_antpos_X() {
        return tryParseList(new Function() { // from class: android.sysprop.NfcProperties$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return NfcProperties.tryParseInteger((String) obj);
            }
        }, SystemProperties.get("ro.nfc.info.antpos.X"));
    }

    public static List<Integer> info_antpos_Y() {
        return tryParseList(new Function() { // from class: android.sysprop.NfcProperties$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return NfcProperties.tryParseInteger((String) obj);
            }
        }, SystemProperties.get("ro.nfc.info.antpos.Y"));
    }

    public static Optional<Integer> info_antpos_device_width() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ro.nfc.info.antpos.device_width")));
    }

    public static Optional<Integer> info_antpos_device_height() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ro.nfc.info.antpos.device_height")));
    }

    public static Optional<Boolean> info_antpos_device_foldable() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("ro.nfc.info.antpos.device_foldable")));
    }

    public static Optional<Boolean> observe_mode_supported() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("ro.nfc.observe_mode_supported")));
    }

    public static Optional<Boolean> get_caps_supported() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("ro.nfc.get_caps_supported")));
    }
}
