package android.sysprop;

import android.os.SystemProperties;
import android.provider.Downloads;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final class BluetoothProperties {
    private BluetoothProperties() {
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

    /* JADX INFO: Access modifiers changed from: private */
    public static Integer tryParseUInt(String str) {
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

    public enum snoop_default_mode_values {
        EMPTY("empty"),
        DISABLED("disabled"),
        FILTERED("filtered"),
        FULL("full");

        private final String propValue;

        snoop_default_mode_values(String str) {
            this.propValue = str;
        }

        public String getPropValue() {
            return this.propValue;
        }
    }

    public static Optional<snoop_default_mode_values> snoop_default_mode() {
        return Optional.ofNullable((snoop_default_mode_values) tryParseEnum(snoop_default_mode_values.class, SystemProperties.get("persist.bluetooth.btsnoopdefaultmode")));
    }

    public static void snoop_default_mode(snoop_default_mode_values snoop_default_mode_valuesVar) {
        SystemProperties.set("persist.bluetooth.btsnoopdefaultmode", snoop_default_mode_valuesVar == null ? "" : snoop_default_mode_valuesVar.getPropValue());
    }

    public enum snoop_log_mode_values {
        EMPTY("empty"),
        DISABLED("disabled"),
        FILTERED("filtered"),
        FULL("full");

        private final String propValue;

        snoop_log_mode_values(String str) {
            this.propValue = str;
        }

        public String getPropValue() {
            return this.propValue;
        }
    }

    public static Optional<snoop_log_mode_values> snoop_log_mode() {
        return Optional.ofNullable((snoop_log_mode_values) tryParseEnum(snoop_log_mode_values.class, SystemProperties.get("persist.bluetooth.btsnooplogmode")));
    }

    public static void snoop_log_mode(snoop_log_mode_values snoop_log_mode_valuesVar) {
        SystemProperties.set("persist.bluetooth.btsnooplogmode", snoop_log_mode_valuesVar == null ? "" : snoop_log_mode_valuesVar.getPropValue());
    }

    public static Optional<Boolean> snoop_log_filter_snoop_headers_enabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("persist.bluetooth.snooplogfilter.headers.enabled")));
    }

    public static void snoop_log_filter_snoop_headers_enabled(Boolean bool) {
        SystemProperties.set("persist.bluetooth.snooplogfilter.headers.enabled", bool == null ? "" : bool.toString());
    }

    public static Optional<Boolean> snoop_log_filter_profile_a2dp_enabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("persist.bluetooth.snooplogfilter.profiles.a2dp.enabled")));
    }

    public static void snoop_log_filter_profile_a2dp_enabled(Boolean bool) {
        SystemProperties.set("persist.bluetooth.snooplogfilter.profiles.a2dp.enabled", bool == null ? "" : bool.toString());
    }

    public enum snoop_log_filter_profile_map_values {
        EMPTY("empty"),
        DISABLED("disabled"),
        FULLFILTER("fullfilter"),
        HEADER(Downloads.Impl.RequestHeaders.COLUMN_HEADER),
        MAGIC("magic");

        private final String propValue;

        snoop_log_filter_profile_map_values(String str) {
            this.propValue = str;
        }

        public String getPropValue() {
            return this.propValue;
        }
    }

    public static Optional<snoop_log_filter_profile_map_values> snoop_log_filter_profile_map() {
        return Optional.ofNullable((snoop_log_filter_profile_map_values) tryParseEnum(snoop_log_filter_profile_map_values.class, SystemProperties.get("persist.bluetooth.snooplogfilter.profiles.map")));
    }

    public static void snoop_log_filter_profile_map(snoop_log_filter_profile_map_values snoop_log_filter_profile_map_valuesVar) {
        SystemProperties.set("persist.bluetooth.snooplogfilter.profiles.map", snoop_log_filter_profile_map_valuesVar == null ? "" : snoop_log_filter_profile_map_valuesVar.getPropValue());
    }

    public enum snoop_log_filter_profile_pbap_values {
        EMPTY("empty"),
        DISABLED("disabled"),
        FULLFILTER("fullfilter"),
        HEADER(Downloads.Impl.RequestHeaders.COLUMN_HEADER),
        MAGIC("magic");

        private final String propValue;

        snoop_log_filter_profile_pbap_values(String str) {
            this.propValue = str;
        }

        public String getPropValue() {
            return this.propValue;
        }
    }

    public static Optional<snoop_log_filter_profile_pbap_values> snoop_log_filter_profile_pbap() {
        return Optional.ofNullable((snoop_log_filter_profile_pbap_values) tryParseEnum(snoop_log_filter_profile_pbap_values.class, SystemProperties.get("persist.bluetooth.snooplogfilter.profiles.pbap")));
    }

    public static void snoop_log_filter_profile_pbap(snoop_log_filter_profile_pbap_values snoop_log_filter_profile_pbap_valuesVar) {
        SystemProperties.set("persist.bluetooth.snooplogfilter.profiles.pbap", snoop_log_filter_profile_pbap_valuesVar == null ? "" : snoop_log_filter_profile_pbap_valuesVar.getPropValue());
    }

    public static Optional<Boolean> snoop_log_filter_profile_rfcomm_enabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("persist.bluetooth.snooplogfilter.profiles.rfcomm.enabled")));
    }

    public static void snoop_log_filter_profile_rfcomm_enabled(Boolean bool) {
        SystemProperties.set("persist.bluetooth.snooplogfilter.profiles.rfcomm.enabled", bool == null ? "" : bool.toString());
    }

    public static Optional<Boolean> factory_reset() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("persist.bluetooth.factoryreset")));
    }

    public static void factory_reset(Boolean bool) {
        SystemProperties.set("persist.bluetooth.factoryreset", bool == null ? "" : bool.toString());
    }

    public static List<String> le_audio_allow_list() {
        return tryParseList(new Function() { // from class: android.sysprop.BluetoothProperties$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String tryParseString;
                tryParseString = BluetoothProperties.tryParseString((String) obj);
                return tryParseString;
            }
        }, SystemProperties.get("persist.bluetooth.leaudio.allow_list"));
    }

    public static void le_audio_allow_list(List<String> list) {
        SystemProperties.set("persist.bluetooth.leaudio.allow_list", list == null ? "" : formatList(list));
    }

    public static Optional<String> le_audio_dynamic_switcher_mode() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("persist.bluetooth.leaudio_dynamic_switcher.mode")));
    }

    public static void le_audio_dynamic_switcher_mode(String str) {
        SystemProperties.set("persist.bluetooth.leaudio_dynamic_switcher.mode", str == null ? "" : str.toString());
    }

    public static Optional<Boolean> isGapLePrivacyEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.core.gap.le.privacy.enabled")));
    }

    public static Optional<Integer> getGapLeConnMinLimit() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("bluetooth.core.gap.le.conn.min.limit")));
    }

    public static Optional<Boolean> isGapLeConnOnlyInit1mPhyEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.core.gap.le.conn.only_init_1m_phy.enabled")));
    }

    public static Optional<Boolean> isLeAudioInbandRingtoneSupported() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.core.le_audio.inband_ringtone.supported")));
    }

    public static Optional<Boolean> isLeAudioCodecExtensionAidlEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.core.le_audio.codec_extension_aidl.enabled")));
    }

    public static Optional<String> getDefaultDeviceName() {
        return Optional.ofNullable(tryParseString(SystemProperties.get("bluetooth.device.default_name")));
    }

    public static List<Integer> getClassOfDevice() {
        return tryParseList(new Function() { // from class: android.sysprop.BluetoothProperties$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Integer tryParseUInt;
                tryParseUInt = BluetoothProperties.tryParseUInt((String) obj);
                return tryParseUInt;
            }
        }, SystemProperties.get("bluetooth.device.class_of_device"));
    }

    public static Optional<Integer> getDefaultOutputOnlyAudioProfile() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("bluetooth.device.default_output_only_audio_profile")));
    }

    public static Optional<Integer> getDefaultDuplexAudioProfile() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("bluetooth.device.default_duplex_audio_profile")));
    }

    public static Optional<Integer> getHardwareOperatingVoltageMv() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("bluetooth.hardware.power.operating_voltage_mv")));
    }

    public static Optional<Integer> getHardwareIdleCurrentMa() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("bluetooth.hardware.power.idle_cur_ma")));
    }

    public static Optional<Integer> getHardwareTxCurrentMa() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("bluetooth.hardware.power.tx_cur_ma")));
    }

    public static Optional<Integer> getHardwareRxCurrentMa() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("bluetooth.hardware.power.rx_cur_ma")));
    }

    public static Optional<Boolean> isSupportPersistedStateEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.framework.support_persisted_state")));
    }

    public static Optional<Boolean> isAdapterAddressValidationEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.framework.adapter_address_validation")));
    }

    public static Optional<Boolean> isProfileA2dpSinkEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.a2dp.sink.enabled")));
    }

    public static Optional<Boolean> isProfileA2dpSourceEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.a2dp.source.enabled")));
    }

    public static Optional<Boolean> isProfileAshaCentralEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.asha.central.enabled")));
    }

    public static Optional<Boolean> isProfileAvrcpControllerEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.avrcp.controller.enabled")));
    }

    public static Optional<Boolean> isProfileAvrcpTargetEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.avrcp.target.enabled")));
    }

    public static Optional<Boolean> isProfileBapBroadcastAssistEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.bap.broadcast.assist.enabled")));
    }

    public static Optional<Boolean> isProfileBapBroadcastSourceEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.bap.broadcast.source.enabled")));
    }

    public static Optional<Boolean> isProfileBapUnicastClientEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.bap.unicast.client.enabled")));
    }

    public static Optional<Boolean> isProfileBasClientEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.bas.client.enabled")));
    }

    public static Optional<Boolean> isProfileBassClientEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.bass.client.enabled")));
    }

    public static Optional<Boolean> isProfileCsipSetCoordinatorEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.csip.set_coordinator.enabled")));
    }

    public static Optional<Boolean> isProfileGattEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.gatt.enabled")));
    }

    public static Optional<Boolean> isProfileGmapEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.gmap.enabled")));
    }

    public static Optional<Boolean> isProfileHapClientEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.hap.client.enabled")));
    }

    public static Optional<Boolean> isProfileHfpAgEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.hfp.ag.enabled")));
    }

    public static Optional<Boolean> isProfileHfpHfEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.hfp.hf.enabled")));
    }

    public static Optional<Boolean> isHfpSoftwareDatapathEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.hfp.software_datapath.enabled")));
    }

    public static Optional<Boolean> isProfileHidDeviceEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.hid.device.enabled")));
    }

    public static Optional<Boolean> isProfileHidHostEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.hid.host.enabled")));
    }

    public static Optional<Boolean> isProfileMapClientEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.map.client.enabled")));
    }

    public static Optional<Boolean> isProfileMapServerEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.map.server.enabled")));
    }

    public static Optional<Boolean> isProfileMcpServerEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.mcp.server.enabled")));
    }

    public static Optional<Boolean> isProfileOppEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.opp.enabled")));
    }

    public static Optional<Boolean> isProfilePanNapEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.pan.nap.enabled")));
    }

    public static Optional<Boolean> isProfilePanPanuEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.pan.panu.enabled")));
    }

    public static Optional<Boolean> isProfilePbapClientEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.pbap.client.enabled")));
    }

    public static Optional<Boolean> isProfilePbapServerEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.pbap.server.enabled")));
    }

    public static Optional<Boolean> isProfilePbapSimEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.pbap.sim.enabled")));
    }

    public static Optional<Boolean> isProfileSapServerEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.sap.server.enabled")));
    }

    public static Optional<Boolean> isProfileCcpServerEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.ccp.server.enabled")));
    }

    public static Optional<Boolean> isProfileVcpControllerEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.profile.vcp.controller.enabled")));
    }

    public static Optional<Integer> getLinkSupervisionTimeout() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.acl.link_supervision_timeout")));
    }

    public static Optional<Integer> getClassicPageScanType() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.classic.page_scan_type")));
    }

    public static Optional<Integer> getClassicPageScanInterval() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.classic.page_scan_interval")));
    }

    public static Optional<Integer> getClassicPageScanWindow() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.classic.page_scan_window")));
    }

    public static Optional<Integer> getClassicInquiryScanType() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.classic.inq_scan_type")));
    }

    public static Optional<Integer> getClassicInquiryScanInterval() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.classic.inq_scan_interval")));
    }

    public static Optional<Integer> getClassicInquiryScanWindow() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.classic.inq_scan_window")));
    }

    public static Optional<Integer> getClassicPageTimeout() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.classic.page_timeout")));
    }

    public static List<Integer> getClassicSniffMaxIntervals() {
        return tryParseList(new Function() { // from class: android.sysprop.BluetoothProperties$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Integer tryParseUInt;
                tryParseUInt = BluetoothProperties.tryParseUInt((String) obj);
                return tryParseUInt;
            }
        }, SystemProperties.get("bluetooth.core.classic.sniff_max_intervals"));
    }

    public static List<Integer> getClassicSniffMinIntervals() {
        return tryParseList(new Function() { // from class: android.sysprop.BluetoothProperties$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Integer tryParseUInt;
                tryParseUInt = BluetoothProperties.tryParseUInt((String) obj);
                return tryParseUInt;
            }
        }, SystemProperties.get("bluetooth.core.classic.sniff_min_intervals"));
    }

    public static List<Integer> getClassicSniffAttempts() {
        return tryParseList(new Function() { // from class: android.sysprop.BluetoothProperties$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Integer tryParseUInt;
                tryParseUInt = BluetoothProperties.tryParseUInt((String) obj);
                return tryParseUInt;
            }
        }, SystemProperties.get("bluetooth.core.classic.sniff_attempts"));
    }

    public static List<Integer> getClassicSniffTimeouts() {
        return tryParseList(new Function() { // from class: android.sysprop.BluetoothProperties$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Integer tryParseUInt;
                tryParseUInt = BluetoothProperties.tryParseUInt((String) obj);
                return tryParseUInt;
            }
        }, SystemProperties.get("bluetooth.core.classic.sniff_timeouts"));
    }

    public static Optional<Integer> getLeMinConnectionInterval() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.le.min_connection_interval")));
    }

    public static Optional<Integer> getLeMaxConnectionInterval() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.le.max_connection_interval")));
    }

    public static Optional<Integer> getLeConnectionLatency() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.le.connection_latency")));
    }

    public static Optional<Integer> getLeConnectionSupervisionTimeout() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.le.connection_supervision_timeout")));
    }

    public static Optional<Integer> getLeMinConnectionIntervalRelaxed() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.le.min_connection_interval_relaxed")));
    }

    public static Optional<Integer> getLeMaxConnectionIntervalRelaxed() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.le.max_connection_interval_relaxed")));
    }

    public static Optional<Integer> getLeMinConnectionIntervalAggressive() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.le.min_connection_interval_aggressive")));
    }

    public static Optional<Integer> getLeMaxConnectionIntervalAggressive() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.le.max_connection_interval_aggressive")));
    }

    public static Optional<Integer> getLeAggressiveConnectionThreshold() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.le.aggressive_connection_threshold")));
    }

    public static Optional<Integer> getLeDirectConnectionTimeout() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.le.direct_connection_timeout")));
    }

    public static Optional<Integer> getLeConnectionScanIntervalFast() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.le.connection_scan_interval_fast")));
    }

    public static Optional<Integer> getLeConnectionScanWindowFast() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.le.connection_scan_window_fast")));
    }

    public static Optional<Integer> getLeConnectionScanWindow2mFast() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.le.connection_scan_window_2m_fast")));
    }

    public static Optional<Integer> getLeConnectionScanWindowCodedFast() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.le.connection_scan_window_coded_fast")));
    }

    public static Optional<Integer> getLeConnectionScanIntervalSlow() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.le.connection_scan_interval_slow")));
    }

    public static Optional<Integer> getLeConnectionScanWindowSlow() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.le.connection_scan_window_slow")));
    }

    public static Optional<Integer> getLeInquiryScanInterval() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.le.inquiry_scan_interval")));
    }

    public static Optional<Integer> getLeInquiryScanWindow() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.le.inquiry_scan_window")));
    }

    public static Optional<Boolean> getLeVendorCapabilitiesEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.core.le.vendor_capabilities.enabled")));
    }

    public static Optional<Integer> getLeMaxNumberOfConcurrentConnections() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.core.le.max_number_of_concurrent_connections")));
    }

    public static List<String> dsa_transport_preference() {
        return tryParseList(new Function() { // from class: android.sysprop.BluetoothProperties$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String tryParseString;
                tryParseString = BluetoothProperties.tryParseString((String) obj);
                return tryParseString;
            }
        }, SystemProperties.get("bluetooth.core.le.dsa_transport_preference"));
    }

    public static Optional<Boolean> getDisableEnchancedConnection() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.sco.disable_enhanced_connection")));
    }

    public static Optional<Boolean> isScoManagedByAudioEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.sco.managed_by_audio")));
    }

    public static Optional<Boolean> enable_sniff_offload() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("persist.bluetooth.sniff_offload.enabled")));
    }

    public static Optional<Boolean> isMsftHciExtEnabled() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("bluetooth.core.le.use_msft_hci_ext")));
    }

    public static Optional<Integer> getMsftVendorOpcode() {
        return Optional.ofNullable(tryParseUInt(SystemProperties.get("bluetooth.hci.msft_vendor_opcode")));
    }
}
