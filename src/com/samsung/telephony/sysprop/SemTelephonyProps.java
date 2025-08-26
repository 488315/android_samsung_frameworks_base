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

/* loaded from: classes6.dex */
public final class SemTelephonyProps {
    private SemTelephonyProps() {
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

    public static List<Integer> volte_911call() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseInteger((String) obj);
            }
        }, SystemProperties.get("ril.volte.911call"));
    }

    public static void volte_911call(List<Integer> list) {
        SystemProperties.set("ril.volte.911call", list == null ? "" : formatList(list));
    }

    public static Optional<String> test_emer_num() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("persist.radio.test_emer_num")));
    }

    public static void test_emer_num(String str) {
        SystemProperties.set("persist.radio.test_emer_num", str == null ? "" : str.toString());
    }

    public static Optional<Boolean> telephony_default_networkmode_automatic() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("ro.ril.telephony.default_networkmode_automatic")));
    }

    public static void telephony_default_networkmode_automatic(Boolean bool) {
        SystemProperties.set("ro.ril.telephony.default_networkmode_automatic", bool == null ? "" : bool.toString());
    }

    public static List<Integer> lte_voice_support() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseInteger((String) obj);
            }
        }, SystemProperties.get("ril.ims.ltevoicesupport"));
    }

    public static void lte_voice_support(List<Integer> list) {
        SystemProperties.set("ril.ims.ltevoicesupport", list == null ? "" : formatList(list));
    }

    public static List<String> ss_error_code() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseString((String) obj);
            }
        }, SystemProperties.get("ril.ss.errorcode"));
    }

    public static void ss_error_code(List<String> list) {
        SystemProperties.set("ril.ss.errorcode", list == null ? "" : formatList(list));
    }

    public static Optional<String> carrier() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ro.carrier")));
    }

    public static Optional<String> ril_preconfig() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("persist.ril.preconfig")));
    }

    public static void ril_preconfig(String str) {
        SystemProperties.set("persist.ril.preconfig", str == null ? "" : str.toString());
    }

    public static Optional<String> support_dual_rat() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("persist.radio.support.dualrat")));
    }

    public static void support_dual_rat(String str) {
        SystemProperties.set("persist.radio.support.dualrat", str == null ? "" : str.toString());
    }

    public static List<String> limited_lte_reject() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseString((String) obj);
            }
        }, SystemProperties.get("ril.data.limited_lte_reject"));
    }

    public static void limited_lte_reject(List<String> list) {
        SystemProperties.set("ril.data.limited_lte_reject", list == null ? "" : formatList(list));
    }

    public static List<Boolean> sim_mobility() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseBoolean((String) obj);
            }
        }, SystemProperties.get("ril.sim.mobility"));
    }

    public static void sim_mobility(List<Boolean> list) {
        SystemProperties.set("ril.sim.mobility", list == null ? "" : formatList(list));
    }

    public static List<Integer> latest_modeltype() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda22
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseInteger((String) obj);
            }
        }, SystemProperties.get("persist.radio.latest-modeltype"));
    }

    public static void latest_modeltype(List<Integer> list) {
        SystemProperties.set("persist.radio.latest-modeltype", list == null ? "" : formatList(list));
    }

    public static List<String> network_reject_cause() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseString((String) obj);
            }
        }, SystemProperties.get("ril.skt.network_regist"));
    }

    public static void network_reject_cause(List<String> list) {
        SystemProperties.set("ril.skt.network_regist", list == null ? "" : formatList(list));
    }

    public static List<String> network_reg_status() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda18
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseString((String) obj);
            }
        }, SystemProperties.get("ril.skt.network_regist_status"));
    }

    public static void network_reg_status(List<String> list) {
        SystemProperties.set("ril.skt.network_regist_status", list == null ? "" : formatList(list));
    }

    public static List<String> current_plmn() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda19
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseString((String) obj);
            }
        }, SystemProperties.get("ril.currentplmn"));
    }

    public static void current_plmn(List<String> list) {
        SystemProperties.set("ril.currentplmn", list == null ? "" : formatList(list));
    }

    public static List<String> reject_rat() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda25
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseString((String) obj);
            }
        }, SystemProperties.get("ril.reject.rat"));
    }

    public static void reject_rat(List<String> list) {
        SystemProperties.set("ril.reject.rat", list == null ? "" : formatList(list));
    }

    public static List<String> rejected_plmn() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda20
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseString((String) obj);
            }
        }, SystemProperties.get("ril.rejectedPlmn"));
    }

    public static void rejected_plmn(List<String> list) {
        SystemProperties.set("ril.rejectedPlmn", list == null ? "" : formatList(list));
    }

    public static Optional<String> ipc_log() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("persist.radio.ipclog")));
    }

    public static void ipc_log(String str) {
        SystemProperties.set("persist.radio.ipclog", str == null ? "" : str.toString());
    }

    public static Optional<Integer> lte_vrte_ltd() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("persist.radio.lte_vrte_ltd")));
    }

    public static void lte_vrte_ltd(Integer num) {
        SystemProperties.set("persist.radio.lte_vrte_ltd", num == null ? "" : num.toString());
    }

    public static Optional<Integer> max_ims_instance() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("persist.radio.max_ims_instance")));
    }

    public static void max_ims_instance(Integer num) {
        SystemProperties.set("persist.radio.max_ims_instance", num == null ? "" : num.toString());
    }

    public static Optional<Boolean> override_ps_e911() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("persist.radio.override_pse911")));
    }

    public static void override_ps_e911(Boolean bool) {
        SystemProperties.set("persist.radio.override_pse911", bool == null ? "" : bool.toString());
    }

    public static Optional<Boolean> override_ps_voice() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("persist.radio.override_psvoice")));
    }

    public static void override_ps_voice(Boolean bool) {
        SystemProperties.set("persist.radio.override_psvoice", bool == null ? "" : bool.toString());
    }

    public static Optional<Integer> report_r_state() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("persist.radio.report_rstate")));
    }

    public static void report_r_state(Integer num) {
        SystemProperties.set("persist.radio.report_rstate", num == null ? "" : num.toString());
    }

    public static Optional<Integer> sib16_support() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("persist.radio.sib16_support")));
    }

    public static void sib16_support(Integer num) {
        SystemProperties.set("persist.radio.sib16_support", num == null ? "" : num.toString());
    }

    public static Optional<Integer> silent_reset() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("persist.radio.silent-reset")));
    }

    public static void silent_reset(Integer num) {
        SystemProperties.set("persist.radio.silent-reset", num == null ? "" : num.toString());
    }

    public static Optional<String> sys_locale() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("persist.sys.locale")));
    }

    public static void sys_locale(String str) {
        SystemProperties.set("persist.sys.locale", str == null ? "" : str.toString());
    }

    public static Optional<String> omc_etc_path() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("persist.sys.omc_etcpath")));
    }

    public static void omc_etc_path(String str) {
        SystemProperties.set("persist.sys.omc_etcpath", str == null ? "" : str.toString());
    }

    public static Optional<String> act_date() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ril.actdate")));
    }

    public static void act_date(String str) {
        SystemProperties.set("ril.actdate", str == null ? "" : str.toString());
    }

    public static Optional<Long> backoff_state() {
        return Optional.ofNullable(tryParseLong(SystemProperties.get("ril.backoffstate")));
    }

    public static void backoff_state(Long l) {
        SystemProperties.set("ril.backoffstate", l == null ? "" : l.toString());
    }

    public static Optional<Integer> bravo() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.BRAVO")));
    }

    public static void bravo(Integer num) {
        SystemProperties.set("ril.BRAVO", num == null ? "" : num.toString());
    }

    public static Optional<Integer> sierra() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.SIERRA")));
    }

    public static void sierra(Integer num) {
        SystemProperties.set("ril.SIERRA", num == null ? "" : num.toString());
    }

    public static Optional<Integer> november() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.NOVEMBER")));
    }

    public static void november(Integer num) {
        SystemProperties.set("ril.NOVEMBER", num == null ? "" : num.toString());
    }

    public static Optional<Integer> ril_char() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.CHAR")));
    }

    public static void ril_char(Integer num) {
        SystemProperties.set("ril.CHAR", num == null ? "" : num.toString());
    }

    public static Optional<Integer> lima() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.LIMA")));
    }

    public static void lima(Integer num) {
        SystemProperties.set("ril.LIMA", num == null ? "" : num.toString());
    }

    public static Optional<Integer> read_done() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.read.done")));
    }

    public static void read_done(Integer num) {
        SystemProperties.set("ril.read.done", num == null ? "" : num.toString());
    }

    public static Optional<Integer> is_cdma() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.iscdma")));
    }

    public static void is_cdma(Integer num) {
        SystemProperties.set("ril.iscdma", num == null ? "" : num.toString());
    }

    public static List<Integer> call_end_cause_param() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseInteger((String) obj);
            }
        }, SystemProperties.get("ril.call_end_cause.param"));
    }

    public static void call_end_cause_param(List<Integer> list) {
        SystemProperties.set("ril.call_end_cause.param", list == null ? "" : formatList(list));
    }

    public static Optional<String> in_ecm_mode() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ril.vendor.inecmmode")));
    }

    public static void in_ecm_mode(String str) {
        SystemProperties.set("ril.vendor.inecmmode", str == null ? "" : str.toString());
    }

    public static Optional<Integer> cs_svc() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.cs_svc")));
    }

    public static void cs_svc(Integer num) {
        SystemProperties.set("ril.cs_svc", num == null ? "" : num.toString());
    }

    public static List<String> debug_cdma_support_type() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda21
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseString((String) obj);
            }
        }, SystemProperties.get("ril.debug.cdmasupporttype"));
    }

    public static void debug_cdma_support_type(List<String> list) {
        SystemProperties.set("ril.debug.cdmasupporttype", list == null ? "" : formatList(list));
    }

    public static Optional<Integer> device_off_res() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.deviceOffRes")));
    }

    public static void device_off_res(Integer num) {
        SystemProperties.set("ril.deviceOffRes", num == null ? "" : num.toString());
    }

    public static Optional<String> dump_time() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ril.dumptime")));
    }

    public static void dump_time(String str) {
        SystemProperties.set("ril.dumptime", str == null ? "" : str.toString());
    }

    public static Optional<Integer> enabled_5g_rf() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.enabled_5g_rf")));
    }

    public static void enabled_5g_rf(Integer num) {
        SystemProperties.set("ril.enabled_5g_rf", num == null ? "" : num.toString());
    }

    public static List<Integer> get_band() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseInteger((String) obj);
            }
        }, SystemProperties.get("ril.get_band"));
    }

    public static void get_band(List<Integer> list) {
        SystemProperties.set("ril.get_band", list == null ? "" : formatList(list));
    }

    public static List<Integer> get_ca_comb() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda23
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseInteger((String) obj);
            }
        }, SystemProperties.get("ril.get_ca_comb"));
    }

    public static void get_ca_comb(List<Integer> list) {
        SystemProperties.set("ril.get_ca_comb", list == null ? "" : formatList(list));
    }

    public static Optional<String> loopback_call_flag() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ril.LoopbackCallFlag")));
    }

    public static void loopback_call_flag(String str) {
        SystemProperties.set("ril.LoopbackCallFlag", str == null ? "" : str.toString());
    }

    public static Optional<Integer> lte_voice_status() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.lte.voice.status")));
    }

    public static void lte_voice_status(Integer num) {
        SystemProperties.set("ril.lte.voice.status", num == null ? "" : num.toString());
    }

    public static List<Integer> lte_band() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseInteger((String) obj);
            }
        }, SystemProperties.get("ril.lteband"));
    }

    public static void lte_band(List<Integer> list) {
        SystemProperties.set("ril.lteband", list == null ? "" : formatList(list));
    }

    public static List<String> lte_network_type() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda24
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseString((String) obj);
            }
        }, SystemProperties.get("ril.ltenetworktype"));
    }

    public static void lte_network_type(List<String> list) {
        SystemProperties.set("ril.ltenetworktype", list == null ? "" : formatList(list));
    }

    public static List<String> lte_scell_bands() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda16
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseString((String) obj);
            }
        }, SystemProperties.get("ril.ltescellbands"));
    }

    public static void lte_scell_bands(List<String> list) {
        SystemProperties.set("ril.ltescellbands", list == null ? "" : formatList(list));
    }

    public static Optional<Integer> main_stack() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.MainStack")));
    }

    public static void main_stack(Integer num) {
        SystemProperties.set("ril.MainStack", num == null ? "" : num.toString());
    }

    public static Optional<String> network_manual_set_rat() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ril.network_manual_set.rat")));
    }

    public static void network_manual_set_rat(String str) {
        SystemProperties.set("ril.network_manual_set.rat", str == null ? "" : str.toString());
    }

    public static List<String> nr_network_type() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda15
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseString((String) obj);
            }
        }, SystemProperties.get("ril.nrnetworktype"));
    }

    public static void nr_network_type(List<String> list) {
        SystemProperties.set("ril.nrnetworktype", list == null ? "" : formatList(list));
    }

    public static Optional<String> phone1_mapped_md() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ril.phone1.mapped.md")));
    }

    public static void phone1_mapped_md(String str) {
        SystemProperties.set("ril.phone1.mapped.md", str == null ? "" : str.toString());
    }

    public static Optional<Integer> preconfig_reset() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("persist.radio.preconfig_reset")));
    }

    public static void preconfig_reset(Integer num) {
        SystemProperties.set("persist.radio.preconfig_reset", num == null ? "" : num.toString());
    }

    public static Optional<Integer> radio_state() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.radiostate")));
    }

    public static void radio_state(Integer num) {
        SystemProperties.set("ril.radiostate", num == null ? "" : num.toString());
    }

    public static Optional<Long> sib16_last_abs_time() {
        return Optional.ofNullable(tryParseLong(SystemProperties.get("ril.sib16.last.absTime")));
    }

    public static void sib16_last_abs_time(Long l) {
        SystemProperties.set("ril.sib16.last.absTime", l == null ? "" : l.toString());
    }

    public static Optional<Integer> sib16_last_dst() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.sib16.last.dst")));
    }

    public static void sib16_last_dst(Integer num) {
        SystemProperties.set("ril.sib16.last.dst", num == null ? "" : num.toString());
    }

    public static Optional<Long> sib16_last_elapsed_time() {
        return Optional.ofNullable(tryParseLong(SystemProperties.get("ril.sib16.last.elapsedtime")));
    }

    public static void sib16_last_elapsed_time(Long l) {
        SystemProperties.set("ril.sib16.last.elapsedtime", l == null ? "" : l.toString());
    }

    public static Optional<Integer> sib16_last_timezone() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.sib16.last.timezone")));
    }

    public static void sib16_last_timezone(Integer num) {
        SystemProperties.set("ril.sib16.last.timezone", num == null ? "" : num.toString());
    }

    public static List<String> signal_param() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseString((String) obj);
            }
        }, SystemProperties.get("ril.signal.param"));
    }

    public static void signal_param(List<String> list) {
        SystemProperties.set("ril.signal.param", list == null ? "" : formatList(list));
    }

    public static Optional<Integer> support_incremental_scan() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.support.incrementalscan")));
    }

    public static void support_incremental_scan(Integer num) {
        SystemProperties.set("ril.support.incrementalscan", num == null ? "" : num.toString());
    }

    public static Optional<Integer> support_nr_mode_from_cp() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("persist.ril.supportNrModefromCp")));
    }

    public static void support_nr_mode_from_cp(Integer num) {
        SystemProperties.set("persist.ril.supportNrModefromCp", num == null ? "" : num.toString());
    }

    public static Optional<Integer> support_sa() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.supportSA")));
    }

    public static void support_sa(Integer num) {
        SystemProperties.set("ril.supportSA", num == null ? "" : num.toString());
    }

    public static Optional<Integer> twwan_911_timer() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.twwan911Timer")));
    }

    public static void twwan_911_timer(Integer num) {
        SystemProperties.set("ril.twwan911Timer", num == null ? "" : num.toString());
    }

    public static Optional<Integer> ussd_not_done() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.ussd.notdone")));
    }

    public static void ussd_not_done(Integer num) {
        SystemProperties.set("ril.ussd.notdone", num == null ? "" : num.toString());
    }

    public static Optional<String> build_type() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ro.build.type")));
    }

    public static Optional<String> telephony_default_network_wrong() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ro.boot.telephony.default_network_wrong")));
    }

    public static Optional<String> def_network_after_check_tdscdma() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ro.ril.def_network_after_check_tdscdma")));
    }

    public static void def_network_after_check_tdscdma(String str) {
        SystemProperties.set("ro.ril.def_network_after_check_tdscdma", str == null ? "" : str.toString());
    }

    public static Optional<String> support_cdma() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ro.ril.support_cdma")));
    }

    public static void support_cdma(String str) {
        SystemProperties.set("ro.ril.support_cdma", str == null ? "" : str.toString());
    }

    public static Optional<String> svdo() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ro.ril.svdo")));
    }

    public static void svdo(String str) {
        SystemProperties.set("ro.ril.svdo", str == null ? "" : str.toString());
    }

    public static Optional<String> svlte1x() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ro.ril.svlte1x")));
    }

    public static void svlte1x(String str) {
        SystemProperties.set("ro.ril.svlte1x", str == null ? "" : str.toString());
    }

    public static Optional<String> shutdown_requested() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("sys.shutdown.requested")));
    }

    public static void shutdown_requested(String str) {
        SystemProperties.set("sys.shutdown.requested", str == null ? "" : str.toString());
    }

    public static List<Integer> ril_init_done() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseInteger((String) obj);
            }
        }, SystemProperties.get("ril.init_done"));
    }

    public static void ril_init_done(List<Integer> list) {
        SystemProperties.set("ril.init_done", list == null ? "" : formatList(list));
    }

    public static List<String> band_list() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda26
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseString((String) obj);
            }
        }, SystemProperties.get("ril.bandList"));
    }

    public static void band_list(List<String> list) {
        SystemProperties.set("ril.bandList", list == null ? "" : formatList(list));
    }

    public static Optional<String> test_plmn() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ril.test.plmn")));
    }

    public static void test_plmn(String str) {
        SystemProperties.set("ril.test.plmn", str == null ? "" : str.toString());
    }

    public static Optional<Long> nitz_time() {
        return Optional.ofNullable(tryParseLong(SystemProperties.get("gsm.nitz.time")));
    }

    public static void nitz_time(Long l) {
        SystemProperties.set("gsm.nitz.time", l == null ? "" : l.toString());
    }

    public static Optional<Long> nitz_time_elapsed_time() {
        return Optional.ofNullable(tryParseLong(SystemProperties.get("gsm.nitz.time-elapsedtime")));
    }

    public static void nitz_time_elapsed_time(Long l) {
        SystemProperties.set("gsm.nitz.time-elapsedtime", l == null ? "" : l.toString());
    }

    public static List<Integer> current_active_phone() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda17
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseInteger((String) obj);
            }
        }, SystemProperties.get(TelephonyProperties.CURRENT_ACTIVE_PHONE));
    }

    public static void current_active_phone(List<Integer> list) {
        SystemProperties.set(TelephonyProperties.CURRENT_ACTIVE_PHONE, list == null ? "" : formatList(list));
    }

    public static List<String> operator_alpha() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda13
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseString((String) obj);
            }
        }, SystemProperties.get(TelephonyProperties.PROPERTY_OPERATOR_ALPHA));
    }

    public static void operator_alpha(List<String> list) {
        SystemProperties.set(TelephonyProperties.PROPERTY_OPERATOR_ALPHA, list == null ? "" : formatList(list));
    }

    public static List<String> operator_numeric() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseString((String) obj);
            }
        }, SystemProperties.get(TelephonyProperties.PROPERTY_OPERATOR_NUMERIC));
    }

    public static void operator_numeric(List<String> list) {
        SystemProperties.set(TelephonyProperties.PROPERTY_OPERATOR_NUMERIC, list == null ? "" : formatList(list));
    }

    public static List<String> msim_submode() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseString((String) obj);
            }
        }, SystemProperties.get("ril.msim.submode"));
    }

    public static void msim_submode(List<String> list) {
        SystemProperties.set("ril.msim.submode", list == null ? "" : formatList(list));
    }

    public static Optional<String> multi_sim_config() {
        return Optional.ofNullable(tryParseString(SystemProperties.get(TelephonyProperties.PROPERTY_MULTI_SIM_CONFIG)));
    }

    public static void multi_sim_config(String str) {
        SystemProperties.set(TelephonyProperties.PROPERTY_MULTI_SIM_CONFIG, str == null ? "" : str.toString());
    }

    public static Optional<String> cdma_home_operator_alpha() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ro.cdma.home.operator.alpha")));
    }

    public static void cdma_home_operator_alpha(String str) {
        SystemProperties.set("ro.cdma.home.operator.alpha", str == null ? "" : str.toString());
    }

    public static Optional<String> sys_timezone() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("persist.sys.timezone")));
    }

    public static void sys_timezone(String str) {
        SystemProperties.set("persist.sys.timezone", str == null ? "" : str.toString());
    }

    public static List<Integer> operator_default_network() {
        return tryParseList(new Function() { // from class: com.samsung.telephony.sysprop.SemTelephonyProps$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SemTelephonyProps.tryParseInteger((String) obj);
            }
        }, SystemProperties.get("persist.radio.def_network"));
    }

    public static void operator_default_network(List<Integer> list) {
        SystemProperties.set("persist.radio.def_network", list == null ? "" : formatList(list));
    }

    public static Optional<String> multisim_standby_active() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ril.multisim.standby_active")));
    }

    public static void multisim_standby_active(String str) {
        SystemProperties.set("ril.multisim.standby_active", str == null ? "" : str.toString());
    }

    public static Optional<Integer> support_satellite() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("persist.radio.support.satellite")));
    }

    public static void support_satellite(Integer num) {
        SystemProperties.set("persist.radio.support.satellite", num == null ? "" : num.toString());
    }

    public static Optional<Integer> tiantong_backoff_state() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.tiantong.backoffstate")));
    }

    public static void tiantong_backoff_state(Integer num) {
        SystemProperties.set("ril.tiantong.backoffstate", num == null ? "" : num.toString());
    }

    public static Optional<Integer> tiantong_phone_id() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.tiantong.phone.id")));
    }

    public static void tiantong_phone_id(Integer num) {
        SystemProperties.set("ril.tiantong.phone.id", num == null ? "" : num.toString());
    }

    public static Optional<Integer> support_expansion_emc() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("ril.support.expansionemc")));
    }

    public static void support_expansion_emc(Integer num) {
        SystemProperties.set("ril.support.expansionemc", num == null ? "" : num.toString());
    }

    public static Optional<String> satellite_sweep_frequency() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("persist.radio.sat.sweepfreq")));
    }

    public static void satellite_sweep_frequency(String str) {
        SystemProperties.set("persist.radio.sat.sweepfreq", str == null ? "" : str.toString());
    }

    public static Optional<String> tiantong_modem_state() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("ril.tiantong.modem.state")));
    }

    public static void tiantong_modem_state(String str) {
        SystemProperties.set("ril.tiantong.modem.state", str == null ? "" : str.toString());
    }
}
