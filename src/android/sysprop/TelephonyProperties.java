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
public final class TelephonyProperties {
    private TelephonyProperties() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Boolean tryParseBoolean(String str) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public static String tryParseString(String str) {
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

    public static Optional<Boolean> airplane_mode_on() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("persist.radio.airplane_mode_on")));
    }

    public static void airplane_mode_on(Boolean bool) {
        String str;
        if (bool == null) {
            str = "";
        } else {
            str = bool.booleanValue() ? "1" : "0";
        }
        SystemProperties.set("persist.radio.airplane_mode_on", str);
    }

    public static List<String> baseband_version() {
        return tryParseList(new Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String tryParseString;
                tryParseString = TelephonyProperties.tryParseString((String) obj);
                return tryParseString;
            }
        }, SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_BASEBAND_VERSION));
    }

    public static void baseband_version(List<String> list) {
        SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_BASEBAND_VERSION, list == null ? "" : formatList(list));
    }

    public static Optional<String> ril_impl() {
        return Optional.ofNullable(tryParseString(SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_RIL_IMPL)));
    }

    public static List<String> operator_alpha() {
        return tryParseList(new Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String tryParseString;
                tryParseString = TelephonyProperties.tryParseString((String) obj);
                return tryParseString;
            }
        }, SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_ALPHA));
    }

    public static void operator_alpha(List<String> list) {
        SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_ALPHA, list == null ? "" : formatList(list));
    }

    public static List<String> operator_numeric() {
        return tryParseList(new Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String tryParseString;
                tryParseString = TelephonyProperties.tryParseString((String) obj);
                return tryParseString;
            }
        }, SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_NUMERIC));
    }

    public static void operator_numeric(List<String> list) {
        SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_NUMERIC, list == null ? "" : formatList(list));
    }

    public static Optional<Boolean> operator_is_manual() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_ISMANUAL)));
    }

    public static List<Boolean> operator_is_roaming() {
        return tryParseList(new Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Boolean tryParseBoolean;
                tryParseBoolean = TelephonyProperties.tryParseBoolean((String) obj);
                return tryParseBoolean;
            }
        }, SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_ISROAMING));
    }

    public static void operator_is_roaming(List<Boolean> list) {
        SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_ISROAMING, list == null ? "" : formatList(list));
    }

    public static List<String> operator_iso_country() {
        return tryParseList(new Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String tryParseString;
                tryParseString = TelephonyProperties.tryParseString((String) obj);
                return tryParseString;
            }
        }, SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_ISO_COUNTRY));
    }

    public static void operator_iso_country(List<String> list) {
        SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_ISO_COUNTRY, list == null ? "" : formatList(list));
    }

    public static Optional<String> lte_on_cdma_product_type() {
        return Optional.ofNullable(tryParseString(SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_LTE_ON_CDMA_PRODUCT_TYPE)));
    }

    public static Optional<Integer> lte_on_cdma_device() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_LTE_ON_CDMA_DEVICE)));
    }

    public static List<Integer> current_active_phone() {
        return tryParseList(new Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Integer tryParseInteger;
                tryParseInteger = TelephonyProperties.tryParseInteger((String) obj);
                return tryParseInteger;
            }
        }, SystemProperties.get(com.android.internal.telephony.TelephonyProperties.CURRENT_ACTIVE_PHONE));
    }

    public static void current_active_phone(List<Integer> list) {
        SystemProperties.set(com.android.internal.telephony.TelephonyProperties.CURRENT_ACTIVE_PHONE, list == null ? "" : formatList(list));
    }

    public static List<String> sim_state() {
        return tryParseList(new Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String tryParseString;
                tryParseString = TelephonyProperties.tryParseString((String) obj);
                return tryParseString;
            }
        }, SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_SIM_STATE));
    }

    public static void sim_state(List<String> list) {
        SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_SIM_STATE, list == null ? "" : formatList(list));
    }

    public static List<String> icc_operator_numeric() {
        return tryParseList(new Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String tryParseString;
                tryParseString = TelephonyProperties.tryParseString((String) obj);
                return tryParseString;
            }
        }, SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_ICC_OPERATOR_NUMERIC));
    }

    public static void icc_operator_numeric(List<String> list) {
        SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_ICC_OPERATOR_NUMERIC, list == null ? "" : formatList(list));
    }

    public static List<String> icc_operator_alpha() {
        return tryParseList(new Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String tryParseString;
                tryParseString = TelephonyProperties.tryParseString((String) obj);
                return tryParseString;
            }
        }, SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_ICC_OPERATOR_ALPHA));
    }

    public static void icc_operator_alpha(List<String> list) {
        SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_ICC_OPERATOR_ALPHA, list == null ? "" : formatList(list));
    }

    public static List<String> icc_operator_iso_country() {
        return tryParseList(new Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String tryParseString;
                tryParseString = TelephonyProperties.tryParseString((String) obj);
                return tryParseString;
            }
        }, SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_ICC_OPERATOR_ISO_COUNTRY));
    }

    public static void icc_operator_iso_country(List<String> list) {
        SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_ICC_OPERATOR_ISO_COUNTRY, list == null ? "" : formatList(list));
    }

    public static List<String> data_network_type() {
        return tryParseList(new Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String tryParseString;
                tryParseString = TelephonyProperties.tryParseString((String) obj);
                return tryParseString;
            }
        }, SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_DATA_NETWORK_TYPE));
    }

    public static void data_network_type(List<String> list) {
        SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_DATA_NETWORK_TYPE, list == null ? "" : formatList(list));
    }

    public static Optional<Boolean> in_ecm_mode() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_INECM_MODE)));
    }

    public static void in_ecm_mode(Boolean bool) {
        SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_INECM_MODE, bool == null ? "" : bool.toString());
    }

    public static Optional<Long> ecm_exit_timer() {
        return Optional.ofNullable(tryParseLong(SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_ECM_EXIT_TIMER)));
    }

    public static Optional<String> operator_idp_string() {
        return Optional.ofNullable(tryParseString(SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_IDP_STRING)));
    }

    public static void operator_idp_string(String str) {
        SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_OPERATOR_IDP_STRING, str == null ? "" : str.toString());
    }

    public static List<String> otasp_num_schema() {
        return tryParseList(new Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String tryParseString;
                tryParseString = TelephonyProperties.tryParseString((String) obj);
                return tryParseString;
            }
        }, SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_OTASP_NUM_SCHEMA));
    }

    public static Optional<Boolean> disable_call() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_DISABLE_CALL)));
    }

    public static Optional<Boolean> ril_sends_multiple_call_ring() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_RIL_SENDS_MULTIPLE_CALL_RING)));
    }

    public static Optional<Integer> call_ring_delay() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_CALL_RING_DELAY)));
    }

    public static Optional<Integer> cdma_msg_id() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_CDMA_MSG_ID)));
    }

    public static void cdma_msg_id(Integer num) {
        SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_CDMA_MSG_ID, num == null ? "" : num.toString());
    }

    public static Optional<Integer> wake_lock_timeout() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_WAKE_LOCK_TIMEOUT)));
    }

    public static Optional<Boolean> reset_on_radio_tech_change() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_RESET_ON_RADIO_TECH_CHANGE)));
    }

    public static List<Boolean> sms_receive() {
        return tryParseList(new Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Boolean tryParseBoolean;
                tryParseBoolean = TelephonyProperties.tryParseBoolean((String) obj);
                return tryParseBoolean;
            }
        }, SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_SMS_RECEIVE));
    }

    public static List<Boolean> sms_send() {
        return tryParseList(new Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Boolean tryParseBoolean;
                tryParseBoolean = TelephonyProperties.tryParseBoolean((String) obj);
                return tryParseBoolean;
            }
        }, SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_SMS_SEND));
    }

    public static Optional<Boolean> test_csim() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_TEST_CSIM)));
    }

    public static Optional<Boolean> ignore_nitz() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_IGNORE_NITZ)));
    }

    public static Optional<String> multi_sim_config() {
        return Optional.ofNullable(tryParseString(SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_MULTI_SIM_CONFIG)));
    }

    public static void multi_sim_config(String str) {
        SystemProperties.set(com.android.internal.telephony.TelephonyProperties.PROPERTY_MULTI_SIM_CONFIG, str == null ? "" : str.toString());
    }

    public static Optional<Boolean> reboot_on_modem_change() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_REBOOT_REQUIRED_ON_MODEM_CHANGE)));
    }

    public static Optional<Integer> videocall_audio_output() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("persist.radio.call.audio.output")));
    }

    public static Optional<Boolean> enable_esim_ui_by_default() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("esim.enable_esim_system_ui_by_default")));
    }

    public static List<Integer> default_network() {
        return tryParseList(new Function() { // from class: android.sysprop.TelephonyProperties$$ExternalSyntheticLambda13
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Integer tryParseInteger;
                tryParseInteger = TelephonyProperties.tryParseInteger((String) obj);
                return tryParseInteger;
            }
        }, SystemProperties.get("ro.telephony.default_network"));
    }

    public static Optional<Boolean> data_roaming() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("ro.com.android.dataroaming")));
    }

    public static Optional<Boolean> mobile_data() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("ro.com.android.mobiledata")));
    }

    public static Optional<Integer> wps_info() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("wifidirect.wps")));
    }

    public static Optional<Integer> max_active_modems() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get(com.android.internal.telephony.TelephonyProperties.PROPERTY_MAX_ACTIVE_MODEMS)));
    }

    public static Optional<Integer> sim_slots_count() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ro.telephony.sim_slots.count")));
    }

    public static Optional<Integer> multi_sim_voice_capability() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.multisim.voice_capability")));
    }

    public static void multi_sim_voice_capability(Integer num) {
        SystemProperties.set("ril.multisim.voice_capability", num == null ? "" : num.toString());
    }

    public static Optional<Boolean> in_scbm() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("ril.inscbm")));
    }

    public static void in_scbm(Boolean bool) {
        SystemProperties.set("ril.inscbm", bool == null ? "" : bool.toString());
    }

    public static Optional<Boolean> dsds_transition_supported() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("ril.multisim.dsds_transition_supported")));
    }

    public static void dsds_transition_supported(Boolean bool) {
        SystemProperties.set("ril.multisim.dsds_transition_supported", bool == null ? "" : bool.toString());
    }
}
