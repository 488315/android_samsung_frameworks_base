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
public final class HdmiProperties {
    private HdmiProperties() {
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

    @Deprecated
    public static List<Integer> device_type() {
        return tryParseList(new Function() { // from class: android.sysprop.HdmiProperties$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return HdmiProperties.tryParseInteger((String) obj);
            }
        }, SystemProperties.get("ro.hdmi.device_type"));
    }

    public enum cec_device_types_values {
        TV("tv"),
        RECORDING_DEVICE("recording_device"),
        RESERVED("reserved"),
        TUNER("tuner"),
        PLAYBACK_DEVICE("playback_device"),
        AUDIO_SYSTEM("audio_system"),
        PURE_CEC_SWITCH("pure_cec_switch"),
        VIDEO_PROCESSOR("video_processor");

        private final String propValue;

        cec_device_types_values(String str) {
            this.propValue = str;
        }

        public String getPropValue() {
            return this.propValue;
        }
    }

    public static List<cec_device_types_values> cec_device_types() {
        return tryParseEnumList(cec_device_types_values.class, SystemProperties.get("ro.hdmi.cec_device_types"));
    }

    public static Optional<String> arc_port() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ro.hdmi.property_sytem_audio_device_arc_port")));
    }

    public static Optional<Boolean> forward_volume_keys_when_system_audio_mode_off() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("ro.hdmi.cec_audio_device_forward_volume_keys_system_audio_mode_off")));
    }

    public static Optional<Boolean> is_switch() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("ro.hdmi.property_is_device_hdmi_cec_switch")));
    }

    public enum playback_device_action_on_routing_control_values {
        NONE("none"),
        WAKE_UP_ONLY("wake_up_only"),
        WAKE_UP_AND_SEND_ACTIVE_SOURCE("wake_up_and_send_active_source");

        private final String propValue;

        playback_device_action_on_routing_control_values(String str) {
            this.propValue = str;
        }

        public String getPropValue() {
            return this.propValue;
        }
    }

    public static Optional<playback_device_action_on_routing_control_values> playback_device_action_on_routing_control() {
        return Optional.ofNullable((playback_device_action_on_routing_control_values) tryParseEnum(playback_device_action_on_routing_control_values.class, SystemProperties.get("ro.hdmi.cec.source.playback_device_action_on_routing_control")));
    }
}
