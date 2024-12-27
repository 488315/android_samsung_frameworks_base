package com.samsung.telephony.sysprop;

import android.os.SystemProperties;

import com.android.internal.telephony.TelephonyProperties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;

public final class SemTelephonyProps {
    private SemTelephonyProps() {}

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static Boolean tryParseBoolean(String str) {
        char c;
        if (str == null) {
            return null;
        }
        String lowerCase = str.toLowerCase(Locale.US);
        switch (lowerCase.hashCode()) {
            case 48:
                if (lowerCase.equals("0")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 49:
                if (lowerCase.equals("1")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3569038:
                if (lowerCase.equals("true")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 97196323:
                if (lowerCase.equals("false")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Integer tryParseInteger(String str) {
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Integer tryParseUInt(String str) {
        try {
            return Integer.valueOf(Integer.parseUnsignedInt(str));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Long tryParseLong(String str) {
        try {
            return Long.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Long tryParseULong(String str) {
        try {
            return Long.valueOf(Long.parseUnsignedLong(str));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Double tryParseDouble(String str) {
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException e) {
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
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static <T> List<T> tryParseList(Function<String, T> elementParser, String str) {
        if ("".equals(str)) {
            return new ArrayList();
        }
        List<T> ret = new ArrayList<>();
        int p = 0;
        while (true) {
            StringBuilder sb = new StringBuilder();
            while (p < str.length() && str.charAt(p) != ',') {
                if (str.charAt(p) == '\\') {
                    p++;
                }
                if (p == str.length()) {
                    break;
                }
                sb.append(str.charAt(p));
                p++;
            }
            ret.add(elementParser.apply(sb.toString()));
            if (p == str.length()) {
                return ret;
            }
            p++;
        }
    }

    private static <T extends Enum<T>> List<T> tryParseEnumList(Class<T> enumType, String str) {
        if ("".equals(str)) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (String element : str.split(",")) {
            arrayList.add(tryParseEnum(enumType, element));
        }
        return arrayList;
    }

    private static String escape(String str) {
        return str.replaceAll("([\\\\,])", "\\\\$1");
    }

    private static <T> String formatList(List<T> list) {
        StringJoiner joiner = new StringJoiner(",");
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            T element = it.next();
            joiner.add(element == null ? "" : escape(element.toString()));
        }
        return joiner.toString();
    }

    private static String formatUIntList(List<Integer> list) {
        StringJoiner joiner = new StringJoiner(",");
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            Integer element = it.next();
            joiner.add(element == null ? "" : escape(Integer.toUnsignedString(element.intValue())));
        }
        return joiner.toString();
    }

    private static String formatULongList(List<Long> list) {
        StringJoiner joiner = new StringJoiner(",");
        Iterator<Long> it = list.iterator();
        while (it.hasNext()) {
            Long element = it.next();
            joiner.add(element == null ? "" : escape(Long.toUnsignedString(element.longValue())));
        }
        return joiner.toString();
    }

    private static <T extends Enum<T>> String formatEnumList(
            List<T> list, Function<T, String> elementFormatter) {
        StringJoiner joiner = new StringJoiner(",");
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            T element = it.next();
            joiner.add(element == null ? "" : elementFormatter.apply(element));
        }
        return joiner.toString();
    }

    public static List<Integer> volte_911call() {
        String value = SystemProperties.get("ril.volte.911call");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda4
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        Integer tryParseInteger;
                        tryParseInteger = SemTelephonyProps.tryParseInteger((String) obj);
                        return tryParseInteger;
                    }
                },
                value);
    }

    public static void volte_911call(List<Integer> value) {
        SystemProperties.set("ril.volte.911call", value == null ? "" : formatList(value));
    }

    public static Optional<String> test_emer_num() {
        String value = SystemProperties.get("persist.radio.test_emer_num");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void test_emer_num(String value) {
        SystemProperties.set("persist.radio.test_emer_num", value == null ? "" : value.toString());
    }

    public static Optional<Boolean> telephony_default_networkmode_automatic() {
        String value = SystemProperties.get("ro.ril.telephony.default_networkmode_automatic");
        return Optional.ofNullable(tryParseBoolean(value));
    }

    public static void telephony_default_networkmode_automatic(Boolean value) {
        SystemProperties.set(
                "ro.ril.telephony.default_networkmode_automatic",
                value == null ? "" : value.toString());
    }

    public static Optional<String> ril_cdma_home_operator_alpha() {
        String value = SystemProperties.get("ro.ril.cdma.home.operator.alpha");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void ril_cdma_home_operator_alpha(String value) {
        SystemProperties.set(
                "ro.ril.cdma.home.operator.alpha", value == null ? "" : value.toString());
    }

    public static Optional<String> ril_cdma_home_operator_numeric() {
        String value = SystemProperties.get("ro.ril.cdma.home.operator.numeric");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void ril_cdma_home_operator_numeric(String value) {
        SystemProperties.set(
                "ro.ril.cdma.home.operator.numeric", value == null ? "" : value.toString());
    }

    public static Optional<String> ril_home_operator_carrierid() {
        String value = SystemProperties.get("ro.ril.home.operator.carrierid");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void ril_home_operator_carrierid(String value) {
        SystemProperties.set(
                "ro.ril.home.operator.carrierid", value == null ? "" : value.toString());
    }

    public static List<Integer> lte_voice_support() {
        String value = SystemProperties.get("ril.ims.ltevoicesupport");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        Integer tryParseInteger;
                        tryParseInteger = SemTelephonyProps.tryParseInteger((String) obj);
                        return tryParseInteger;
                    }
                },
                value);
    }

    public static void lte_voice_support(List<Integer> value) {
        SystemProperties.set("ril.ims.ltevoicesupport", value == null ? "" : formatList(value));
    }

    public static List<String> ss_error_code() {
        String value = SystemProperties.get("ril.ss.errorcode");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        String tryParseString;
                        tryParseString = SemTelephonyProps.tryParseString((String) obj);
                        return tryParseString;
                    }
                },
                value);
    }

    public static void ss_error_code(List<String> value) {
        SystemProperties.set("ril.ss.errorcode", value == null ? "" : formatList(value));
    }

    public static Optional<String> carrier() {
        String value = SystemProperties.get("ro.carrier");
        return Optional.ofNullable(tryParseString(value));
    }

    public static Optional<String> ril_preconfig() {
        String value = SystemProperties.get("persist.ril.preconfig");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void ril_preconfig(String value) {
        SystemProperties.set("persist.ril.preconfig", value == null ? "" : value.toString());
    }

    public static Optional<String> support_dual_rat() {
        String value = SystemProperties.get("persist.radio.support.dualrat");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void support_dual_rat(String value) {
        SystemProperties.set(
                "persist.radio.support.dualrat", value == null ? "" : value.toString());
    }

    public static List<String> limited_lte_reject() {
        String value = SystemProperties.get("ril.data.limited_lte_reject");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda5
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        String tryParseString;
                        tryParseString = SemTelephonyProps.tryParseString((String) obj);
                        return tryParseString;
                    }
                },
                value);
    }

    public static void limited_lte_reject(List<String> value) {
        SystemProperties.set("ril.data.limited_lte_reject", value == null ? "" : formatList(value));
    }

    public static List<Boolean> sim_mobility() {
        String value = SystemProperties.get("ril.sim.mobility");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        Boolean tryParseBoolean;
                        tryParseBoolean = SemTelephonyProps.tryParseBoolean((String) obj);
                        return tryParseBoolean;
                    }
                },
                value);
    }

    public static void sim_mobility(List<Boolean> value) {
        SystemProperties.set("ril.sim.mobility", value == null ? "" : formatList(value));
    }

    public static List<Integer> latest_modeltype() {
        String value = SystemProperties.get("persist.radio.latest-modeltype");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda22
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        Integer tryParseInteger;
                        tryParseInteger = SemTelephonyProps.tryParseInteger((String) obj);
                        return tryParseInteger;
                    }
                },
                value);
    }

    public static void latest_modeltype(List<Integer> value) {
        SystemProperties.set(
                "persist.radio.latest-modeltype", value == null ? "" : formatList(value));
    }

    public static List<String> network_reject_cause() {
        String value = SystemProperties.get("ril.skt.network_regist");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda3
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        String tryParseString;
                        tryParseString = SemTelephonyProps.tryParseString((String) obj);
                        return tryParseString;
                    }
                },
                value);
    }

    public static void network_reject_cause(List<String> value) {
        SystemProperties.set("ril.skt.network_regist", value == null ? "" : formatList(value));
    }

    public static List<String> network_reg_status() {
        String value = SystemProperties.get("ril.skt.network_regist_status");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda18
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        String tryParseString;
                        tryParseString = SemTelephonyProps.tryParseString((String) obj);
                        return tryParseString;
                    }
                },
                value);
    }

    public static void network_reg_status(List<String> value) {
        SystemProperties.set(
                "ril.skt.network_regist_status", value == null ? "" : formatList(value));
    }

    public static List<String> current_plmn() {
        String value = SystemProperties.get("ril.currentplmn");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda19
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        String tryParseString;
                        tryParseString = SemTelephonyProps.tryParseString((String) obj);
                        return tryParseString;
                    }
                },
                value);
    }

    public static void current_plmn(List<String> value) {
        SystemProperties.set("ril.currentplmn", value == null ? "" : formatList(value));
    }

    public static List<String> reject_rat() {
        String value = SystemProperties.get("ril.reject.rat");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda25
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        String tryParseString;
                        tryParseString = SemTelephonyProps.tryParseString((String) obj);
                        return tryParseString;
                    }
                },
                value);
    }

    public static void reject_rat(List<String> value) {
        SystemProperties.set("ril.reject.rat", value == null ? "" : formatList(value));
    }

    public static List<String> rejected_plmn() {
        String value = SystemProperties.get("ril.rejectedPlmn");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda20
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        String tryParseString;
                        tryParseString = SemTelephonyProps.tryParseString((String) obj);
                        return tryParseString;
                    }
                },
                value);
    }

    public static void rejected_plmn(List<String> value) {
        SystemProperties.set("ril.rejectedPlmn", value == null ? "" : formatList(value));
    }

    public static Optional<String> ipc_log() {
        String value = SystemProperties.get("persist.radio.ipclog");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void ipc_log(String value) {
        SystemProperties.set("persist.radio.ipclog", value == null ? "" : value.toString());
    }

    public static Optional<Integer> lte_vrte_ltd() {
        String value = SystemProperties.get("persist.radio.lte_vrte_ltd");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void lte_vrte_ltd(Integer value) {
        SystemProperties.set("persist.radio.lte_vrte_ltd", value == null ? "" : value.toString());
    }

    public static Optional<Integer> max_ims_instance() {
        String value = SystemProperties.get("persist.radio.max_ims_instance");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void max_ims_instance(Integer value) {
        SystemProperties.set(
                "persist.radio.max_ims_instance", value == null ? "" : value.toString());
    }

    public static Optional<Boolean> override_ps_e911() {
        String value = SystemProperties.get("persist.radio.override_pse911");
        return Optional.ofNullable(tryParseBoolean(value));
    }

    public static void override_ps_e911(Boolean value) {
        SystemProperties.set(
                "persist.radio.override_pse911", value == null ? "" : value.toString());
    }

    public static Optional<Boolean> override_ps_voice() {
        String value = SystemProperties.get("persist.radio.override_psvoice");
        return Optional.ofNullable(tryParseBoolean(value));
    }

    public static void override_ps_voice(Boolean value) {
        SystemProperties.set(
                "persist.radio.override_psvoice", value == null ? "" : value.toString());
    }

    public static Optional<Integer> report_r_state() {
        String value = SystemProperties.get("persist.radio.report_rstate");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void report_r_state(Integer value) {
        SystemProperties.set("persist.radio.report_rstate", value == null ? "" : value.toString());
    }

    public static Optional<Integer> sib16_support() {
        String value = SystemProperties.get("persist.radio.sib16_support");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void sib16_support(Integer value) {
        SystemProperties.set("persist.radio.sib16_support", value == null ? "" : value.toString());
    }

    public static Optional<Integer> silent_reset() {
        String value = SystemProperties.get("persist.radio.silent-reset");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void silent_reset(Integer value) {
        SystemProperties.set("persist.radio.silent-reset", value == null ? "" : value.toString());
    }

    public static Optional<String> sys_locale() {
        String value = SystemProperties.get("persist.sys.locale");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void sys_locale(String value) {
        SystemProperties.set("persist.sys.locale", value == null ? "" : value.toString());
    }

    public static Optional<String> omc_etc_path() {
        String value = SystemProperties.get("persist.sys.omc_etcpath");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void omc_etc_path(String value) {
        SystemProperties.set("persist.sys.omc_etcpath", value == null ? "" : value.toString());
    }

    public static Optional<String> act_date() {
        String value = SystemProperties.get("ril.actdate");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void act_date(String value) {
        SystemProperties.set("ril.actdate", value == null ? "" : value.toString());
    }

    public static Optional<Long> backoff_state() {
        String value = SystemProperties.get("ril.backoffstate");
        return Optional.ofNullable(tryParseLong(value));
    }

    public static void backoff_state(Long value) {
        SystemProperties.set("ril.backoffstate", value == null ? "" : value.toString());
    }

    public static Optional<Integer> bravo() {
        String value = SystemProperties.get("ril.BRAVO");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void bravo(Integer value) {
        SystemProperties.set("ril.BRAVO", value == null ? "" : value.toString());
    }

    public static Optional<Integer> sierra() {
        String value = SystemProperties.get("ril.SIERRA");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void sierra(Integer value) {
        SystemProperties.set("ril.SIERRA", value == null ? "" : value.toString());
    }

    public static Optional<Integer> november() {
        String value = SystemProperties.get("ril.NOVEMBER");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void november(Integer value) {
        SystemProperties.set("ril.NOVEMBER", value == null ? "" : value.toString());
    }

    public static Optional<Integer> ril_char() {
        String value = SystemProperties.get("ril.CHAR");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void ril_char(Integer value) {
        SystemProperties.set("ril.CHAR", value == null ? "" : value.toString());
    }

    public static Optional<Integer> lima() {
        String value = SystemProperties.get("ril.LIMA");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void lima(Integer value) {
        SystemProperties.set("ril.LIMA", value == null ? "" : value.toString());
    }

    public static Optional<Integer> read_done() {
        String value = SystemProperties.get("ril.read.done");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void read_done(Integer value) {
        SystemProperties.set("ril.read.done", value == null ? "" : value.toString());
    }

    public static Optional<Integer> is_cdma() {
        String value = SystemProperties.get("ril.iscdma");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void is_cdma(Integer value) {
        SystemProperties.set("ril.iscdma", value == null ? "" : value.toString());
    }

    public static List<Integer> call_end_cause_param() {
        String value = SystemProperties.get("ril.call_end_cause.param");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda7
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        Integer tryParseInteger;
                        tryParseInteger = SemTelephonyProps.tryParseInteger((String) obj);
                        return tryParseInteger;
                    }
                },
                value);
    }

    public static void call_end_cause_param(List<Integer> value) {
        SystemProperties.set("ril.call_end_cause.param", value == null ? "" : formatList(value));
    }

    public static Optional<String> in_ecm_mode() {
        String value = SystemProperties.get("ril.vendor.inecmmode");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void in_ecm_mode(String value) {
        SystemProperties.set("ril.vendor.inecmmode", value == null ? "" : value.toString());
    }

    public static Optional<Integer> cs_svc() {
        String value = SystemProperties.get("ril.cs_svc");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void cs_svc(Integer value) {
        SystemProperties.set("ril.cs_svc", value == null ? "" : value.toString());
    }

    public static List<String> debug_cdma_support_type() {
        String value = SystemProperties.get("ril.debug.cdmasupporttype");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda21
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        String tryParseString;
                        tryParseString = SemTelephonyProps.tryParseString((String) obj);
                        return tryParseString;
                    }
                },
                value);
    }

    public static void debug_cdma_support_type(List<String> value) {
        SystemProperties.set("ril.debug.cdmasupporttype", value == null ? "" : formatList(value));
    }

    public static Optional<Integer> device_off_res() {
        String value = SystemProperties.get("ril.deviceOffRes");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void device_off_res(Integer value) {
        SystemProperties.set("ril.deviceOffRes", value == null ? "" : value.toString());
    }

    public static Optional<String> dump_time() {
        String value = SystemProperties.get("ril.dumptime");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void dump_time(String value) {
        SystemProperties.set("ril.dumptime", value == null ? "" : value.toString());
    }

    public static Optional<Integer> enabled_5g_rf() {
        String value = SystemProperties.get("ril.enabled_5g_rf");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void enabled_5g_rf(Integer value) {
        SystemProperties.set("ril.enabled_5g_rf", value == null ? "" : value.toString());
    }

    public static List<Integer> get_band() {
        String value = SystemProperties.get("ril.get_band");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda9
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        Integer tryParseInteger;
                        tryParseInteger = SemTelephonyProps.tryParseInteger((String) obj);
                        return tryParseInteger;
                    }
                },
                value);
    }

    public static void get_band(List<Integer> value) {
        SystemProperties.set("ril.get_band", value == null ? "" : formatList(value));
    }

    public static List<Integer> get_ca_comb() {
        String value = SystemProperties.get("ril.get_ca_comb");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda23
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        Integer tryParseInteger;
                        tryParseInteger = SemTelephonyProps.tryParseInteger((String) obj);
                        return tryParseInteger;
                    }
                },
                value);
    }

    public static void get_ca_comb(List<Integer> value) {
        SystemProperties.set("ril.get_ca_comb", value == null ? "" : formatList(value));
    }

    public static Optional<String> loopback_call_flag() {
        String value = SystemProperties.get("ril.LoopbackCallFlag");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void loopback_call_flag(String value) {
        SystemProperties.set("ril.LoopbackCallFlag", value == null ? "" : value.toString());
    }

    public static Optional<Integer> lte_voice_status() {
        String value = SystemProperties.get("ril.lte.voice.status");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void lte_voice_status(Integer value) {
        SystemProperties.set("ril.lte.voice.status", value == null ? "" : value.toString());
    }

    public static List<Integer> lte_band() {
        String value = SystemProperties.get("ril.lteband");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda8
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        Integer tryParseInteger;
                        tryParseInteger = SemTelephonyProps.tryParseInteger((String) obj);
                        return tryParseInteger;
                    }
                },
                value);
    }

    public static void lte_band(List<Integer> value) {
        SystemProperties.set("ril.lteband", value == null ? "" : formatList(value));
    }

    public static List<String> lte_network_type() {
        String value = SystemProperties.get("ril.ltenetworktype");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda24
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        String tryParseString;
                        tryParseString = SemTelephonyProps.tryParseString((String) obj);
                        return tryParseString;
                    }
                },
                value);
    }

    public static void lte_network_type(List<String> value) {
        SystemProperties.set("ril.ltenetworktype", value == null ? "" : formatList(value));
    }

    public static List<String> lte_scell_bands() {
        String value = SystemProperties.get("ril.ltescellbands");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda16
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        String tryParseString;
                        tryParseString = SemTelephonyProps.tryParseString((String) obj);
                        return tryParseString;
                    }
                },
                value);
    }

    public static void lte_scell_bands(List<String> value) {
        SystemProperties.set("ril.ltescellbands", value == null ? "" : formatList(value));
    }

    public static Optional<Integer> main_stack() {
        String value = SystemProperties.get("ril.MainStack");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void main_stack(Integer value) {
        SystemProperties.set("ril.MainStack", value == null ? "" : value.toString());
    }

    public static Optional<String> network_manual_set_rat() {
        String value = SystemProperties.get("ril.network_manual_set.rat");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void network_manual_set_rat(String value) {
        SystemProperties.set("ril.network_manual_set.rat", value == null ? "" : value.toString());
    }

    public static List<String> nr_network_type() {
        String value = SystemProperties.get("ril.nrnetworktype");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda15
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        String tryParseString;
                        tryParseString = SemTelephonyProps.tryParseString((String) obj);
                        return tryParseString;
                    }
                },
                value);
    }

    public static void nr_network_type(List<String> value) {
        SystemProperties.set("ril.nrnetworktype", value == null ? "" : formatList(value));
    }

    public static Optional<String> phone1_mapped_md() {
        String value = SystemProperties.get("ril.phone1.mapped.md");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void phone1_mapped_md(String value) {
        SystemProperties.set("ril.phone1.mapped.md", value == null ? "" : value.toString());
    }

    public static Optional<Integer> preconfig_reset() {
        String value = SystemProperties.get("persist.radio.preconfig_reset");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void preconfig_reset(Integer value) {
        SystemProperties.set(
                "persist.radio.preconfig_reset", value == null ? "" : value.toString());
    }

    public static Optional<Integer> radio_state() {
        String value = SystemProperties.get("ril.radiostate");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void radio_state(Integer value) {
        SystemProperties.set("ril.radiostate", value == null ? "" : value.toString());
    }

    public static Optional<Long> sib16_last_abs_time() {
        String value = SystemProperties.get("ril.sib16.last.absTime");
        return Optional.ofNullable(tryParseLong(value));
    }

    public static void sib16_last_abs_time(Long value) {
        SystemProperties.set("ril.sib16.last.absTime", value == null ? "" : value.toString());
    }

    public static Optional<Integer> sib16_last_dst() {
        String value = SystemProperties.get("ril.sib16.last.dst");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void sib16_last_dst(Integer value) {
        SystemProperties.set("ril.sib16.last.dst", value == null ? "" : value.toString());
    }

    public static Optional<Long> sib16_last_elapsed_time() {
        String value = SystemProperties.get("ril.sib16.last.elapsedtime");
        return Optional.ofNullable(tryParseLong(value));
    }

    public static void sib16_last_elapsed_time(Long value) {
        SystemProperties.set("ril.sib16.last.elapsedtime", value == null ? "" : value.toString());
    }

    public static Optional<Integer> sib16_last_timezone() {
        String value = SystemProperties.get("ril.sib16.last.timezone");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void sib16_last_timezone(Integer value) {
        SystemProperties.set("ril.sib16.last.timezone", value == null ? "" : value.toString());
    }

    public static List<String> signal_param() {
        String value = SystemProperties.get("ril.signal.param");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda10
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        String tryParseString;
                        tryParseString = SemTelephonyProps.tryParseString((String) obj);
                        return tryParseString;
                    }
                },
                value);
    }

    public static void signal_param(List<String> value) {
        SystemProperties.set("ril.signal.param", value == null ? "" : formatList(value));
    }

    public static Optional<Integer> support_incremental_scan() {
        String value = SystemProperties.get("ril.support.incrementalscan");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void support_incremental_scan(Integer value) {
        SystemProperties.set("ril.support.incrementalscan", value == null ? "" : value.toString());
    }

    public static Optional<Integer> support_nr_mode_from_cp() {
        String value = SystemProperties.get("persist.ril.supportNrModefromCp");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void support_nr_mode_from_cp(Integer value) {
        SystemProperties.set(
                "persist.ril.supportNrModefromCp", value == null ? "" : value.toString());
    }

    public static Optional<Integer> support_sa() {
        String value = SystemProperties.get("ril.supportSA");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void support_sa(Integer value) {
        SystemProperties.set("ril.supportSA", value == null ? "" : value.toString());
    }

    public static Optional<Integer> twwan_911_timer() {
        String value = SystemProperties.get("ril.twwan911Timer");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void twwan_911_timer(Integer value) {
        SystemProperties.set("ril.twwan911Timer", value == null ? "" : value.toString());
    }

    public static Optional<Integer> ussd_not_done() {
        String value = SystemProperties.get("ril.ussd.notdone");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void ussd_not_done(Integer value) {
        SystemProperties.set("ril.ussd.notdone", value == null ? "" : value.toString());
    }

    public static Optional<String> build_type() {
        String value = SystemProperties.get("ro.build.type");
        return Optional.ofNullable(tryParseString(value));
    }

    public static Optional<String> telephony_default_network_wrong() {
        String value = SystemProperties.get("ro.boot.telephony.default_network_wrong");
        return Optional.ofNullable(tryParseString(value));
    }

    public static Optional<String> def_network_after_check_tdscdma() {
        String value = SystemProperties.get("ro.ril.def_network_after_check_tdscdma");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void def_network_after_check_tdscdma(String value) {
        SystemProperties.set(
                "ro.ril.def_network_after_check_tdscdma", value == null ? "" : value.toString());
    }

    public static Optional<String> support_cdma() {
        String value = SystemProperties.get("ro.ril.support_cdma");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void support_cdma(String value) {
        SystemProperties.set("ro.ril.support_cdma", value == null ? "" : value.toString());
    }

    public static Optional<String> svdo() {
        String value = SystemProperties.get("ro.ril.svdo");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void svdo(String value) {
        SystemProperties.set("ro.ril.svdo", value == null ? "" : value.toString());
    }

    public static Optional<String> svlte1x() {
        String value = SystemProperties.get("ro.ril.svlte1x");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void svlte1x(String value) {
        SystemProperties.set("ro.ril.svlte1x", value == null ? "" : value.toString());
    }

    public static Optional<String> shutdown_requested() {
        String value = SystemProperties.get("sys.shutdown.requested");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void shutdown_requested(String value) {
        SystemProperties.set("sys.shutdown.requested", value == null ? "" : value.toString());
    }

    public static List<Integer> ril_init_done() {
        String value = SystemProperties.get("ril.init_done");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda6
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        Integer tryParseInteger;
                        tryParseInteger = SemTelephonyProps.tryParseInteger((String) obj);
                        return tryParseInteger;
                    }
                },
                value);
    }

    public static void ril_init_done(List<Integer> value) {
        SystemProperties.set("ril.init_done", value == null ? "" : formatList(value));
    }

    public static List<String> band_list() {
        String value = SystemProperties.get("ril.bandList");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda26
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        String tryParseString;
                        tryParseString = SemTelephonyProps.tryParseString((String) obj);
                        return tryParseString;
                    }
                },
                value);
    }

    public static void band_list(List<String> value) {
        SystemProperties.set("ril.bandList", value == null ? "" : formatList(value));
    }

    public static Optional<String> test_plmn() {
        String value = SystemProperties.get("ril.test.plmn");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void test_plmn(String value) {
        SystemProperties.set("ril.test.plmn", value == null ? "" : value.toString());
    }

    public static Optional<Long> nitz_time() {
        String value = SystemProperties.get("gsm.nitz.time");
        return Optional.ofNullable(tryParseLong(value));
    }

    public static void nitz_time(Long value) {
        SystemProperties.set("gsm.nitz.time", value == null ? "" : value.toString());
    }

    public static Optional<Long> nitz_time_elapsed_time() {
        String value = SystemProperties.get("gsm.nitz.time-elapsedtime");
        return Optional.ofNullable(tryParseLong(value));
    }

    public static void nitz_time_elapsed_time(Long value) {
        SystemProperties.set("gsm.nitz.time-elapsedtime", value == null ? "" : value.toString());
    }

    public static List<Integer> current_active_phone() {
        String value = SystemProperties.get(TelephonyProperties.CURRENT_ACTIVE_PHONE);
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda17
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        Integer tryParseInteger;
                        tryParseInteger = SemTelephonyProps.tryParseInteger((String) obj);
                        return tryParseInteger;
                    }
                },
                value);
    }

    public static void current_active_phone(List<Integer> value) {
        SystemProperties.set(
                TelephonyProperties.CURRENT_ACTIVE_PHONE, value == null ? "" : formatList(value));
    }

    public static List<String> operator_alpha() {
        String value = SystemProperties.get(TelephonyProperties.PROPERTY_OPERATOR_ALPHA);
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda13
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        String tryParseString;
                        tryParseString = SemTelephonyProps.tryParseString((String) obj);
                        return tryParseString;
                    }
                },
                value);
    }

    public static void operator_alpha(List<String> value) {
        SystemProperties.set(
                TelephonyProperties.PROPERTY_OPERATOR_ALPHA,
                value == null ? "" : formatList(value));
    }

    public static List<String> operator_numeric() {
        String value = SystemProperties.get(TelephonyProperties.PROPERTY_OPERATOR_NUMERIC);
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda12
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        String tryParseString;
                        tryParseString = SemTelephonyProps.tryParseString((String) obj);
                        return tryParseString;
                    }
                },
                value);
    }

    public static void operator_numeric(List<String> value) {
        SystemProperties.set(
                TelephonyProperties.PROPERTY_OPERATOR_NUMERIC,
                value == null ? "" : formatList(value));
    }

    public static List<String> msim_submode() {
        String value = SystemProperties.get("ril.msim.submode");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda11
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        String tryParseString;
                        tryParseString = SemTelephonyProps.tryParseString((String) obj);
                        return tryParseString;
                    }
                },
                value);
    }

    public static void msim_submode(List<String> value) {
        SystemProperties.set("ril.msim.submode", value == null ? "" : formatList(value));
    }

    public static Optional<String> multi_sim_config() {
        String value = SystemProperties.get(TelephonyProperties.PROPERTY_MULTI_SIM_CONFIG);
        return Optional.ofNullable(tryParseString(value));
    }

    public static void multi_sim_config(String value) {
        SystemProperties.set(
                TelephonyProperties.PROPERTY_MULTI_SIM_CONFIG,
                value == null ? "" : value.toString());
    }

    public static Optional<String> cdma_home_operator_alpha() {
        String value = SystemProperties.get("ro.cdma.home.operator.alpha");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void cdma_home_operator_alpha(String value) {
        SystemProperties.set("ro.cdma.home.operator.alpha", value == null ? "" : value.toString());
    }

    public static Optional<String> sys_timezone() {
        String value = SystemProperties.get("persist.sys.timezone");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void sys_timezone(String value) {
        SystemProperties.set("persist.sys.timezone", value == null ? "" : value.toString());
    }

    public static List<Integer> operator_default_network() {
        String value = SystemProperties.get("persist.radio.def_network");
        return tryParseList(
                new Function() { // from class:
                                 // com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda14
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        Integer tryParseInteger;
                        tryParseInteger = SemTelephonyProps.tryParseInteger((String) obj);
                        return tryParseInteger;
                    }
                },
                value);
    }

    public static void operator_default_network(List<Integer> value) {
        SystemProperties.set("persist.radio.def_network", value == null ? "" : formatList(value));
    }

    public static Optional<String> multisim_standby_active() {
        String value = SystemProperties.get("ril.multisim.standby_active");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void multisim_standby_active(String value) {
        SystemProperties.set("ril.multisim.standby_active", value == null ? "" : value.toString());
    }

    public static Optional<Integer> support_satellite() {
        String value = SystemProperties.get("persist.radio.support.satellite");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void support_satellite(Integer value) {
        SystemProperties.set(
                "persist.radio.support.satellite", value == null ? "" : value.toString());
    }

    public static Optional<Integer> tiantong_backoff_state() {
        String value = SystemProperties.get("ril.tiantong.backoffstate");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void tiantong_backoff_state(Integer value) {
        SystemProperties.set("ril.tiantong.backoffstate", value == null ? "" : value.toString());
    }

    public static Optional<Integer> tiantong_phone_id() {
        String value = SystemProperties.get("ril.tiantong.phone.id");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void tiantong_phone_id(Integer value) {
        SystemProperties.set("ril.tiantong.phone.id", value == null ? "" : value.toString());
    }

    public static Optional<Integer> support_expansion_emc() {
        String value = SystemProperties.get("ril.support.expansionemc");
        return Optional.ofNullable(tryParseInteger(value));
    }

    public static void support_expansion_emc(Integer value) {
        SystemProperties.set("ril.support.expansionemc", value == null ? "" : value.toString());
    }

    public static Optional<String> satellite_sweep_frequency() {
        String value = SystemProperties.get("persist.radio.sat.sweepfreq");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void satellite_sweep_frequency(String value) {
        SystemProperties.set("persist.radio.sat.sweepfreq", value == null ? "" : value.toString());
    }

    public static Optional<String> tiantong_modem_state() {
        String value = SystemProperties.get("ril.tiantong.modem.state");
        return Optional.ofNullable(tryParseString(value));
    }

    public static void tiantong_modem_state(String value) {
        SystemProperties.set("ril.tiantong.modem.state", value == null ? "" : value.toString());
    }
}
