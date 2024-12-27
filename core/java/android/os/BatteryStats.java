package android.os;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.backup.FullBackup;
import android.app.blob.XmlTags;
import android.app.job.JobParameters;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.gnss.GnssSignalType;
import android.hardware.scontext.SContextConstants;
import android.hardware.tv.tuner.FrontendInnerFec;
import android.hardware.usb.UsbManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.audio.common.AudioDeviceDescription;
import android.net.NetworkPolicyManager;
import android.security.Credentials;
import android.telephony.CellSignalStrength;
import android.telephony.ModemActivityInfo;
import android.telephony.TelephonyManager;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.LongSparseArray;
import android.util.MutableBoolean;
import android.util.Pair;
import android.util.Printer;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseDoubleArray;
import android.util.SparseIntArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import android.view.SurfaceControl;

import com.android.internal.R;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.os.BatteryStatsHistoryIterator;
import com.android.internal.os.CpuScalingPolicies;
import com.android.internal.os.PowerStats;
import com.android.internal.telephony.DctConstants;
import com.android.net.module.util.NetworkCapabilitiesUtils;

import com.google.android.collect.Lists;
import com.samsung.android.biometrics.SemBiometricConstants;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.wallpaperbackup.GenerateXML;
import com.samsung.android.wifi.p2p.SemWifiP2pManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class BatteryStats {
    private static final String AGGREGATED_WAKELOCK_DATA = "awl";
    public static final int AGGREGATED_WAKE_TYPE_PARTIAL = 20;
    private static final String APK_DATA = "apk";
    private static final String AUDIO_DATA = "aud";
    public static final int AUDIO_TURNED_ON = 15;
    private static final String BATTERY_DATA = "bt";
    private static final String BATTERY_DISCHARGE_DATA = "dc";
    private static final int BATTERY_HEALTH_OVERHEATLIMIT = 8;
    private static final int BATTERY_HEALTH_UNDER_VOLTAGE = 9;
    private static final String BATTERY_LEVEL_DATA = "lv";
    private static final int BATTERY_STATS_CHECKIN_VERSION = 9;
    private static final String BLUETOOTH_CONTROLLER_DATA = "ble";
    public static final int BLUETOOTH_DUTY_SCAN_ON = 24;
    private static final String BLUETOOTH_MISC_DATA = "blem";
    public static final int BLUETOOTH_SCAN_ON = 19;
    public static final int BLUETOOTH_UNOPTIMIZED_SCAN_ON = 21;
    private static final long BYTES_PER_GB = 1073741824;
    private static final long BYTES_PER_KB = 1024;
    private static final long BYTES_PER_MB = 1048576;
    private static final String CAMERA_DATA = "cam";
    public static final int CAMERA_TURNED_ON = 17;
    private static final String CELLULAR_CONTROLLER_NAME = "Cellular";
    private static final String CHARGE_STEP_DATA = "csd";
    private static final String CHARGE_TIME_REMAIN_DATA = "ctr";
    static final int CHECKIN_VERSION = 36;
    private static final String CPU_DATA = "cpu";
    private static final String CPU_TIMES_AT_FREQ_DATA = "ctf";
    private static final String DATA_CONNECTION_COUNT_DATA = "dcc";
    public static final int DATA_CONNECTION_OUT_OF_SERVICE = 0;
    private static final String DATA_CONNECTION_TIME_DATA = "dct";
    public static final int DEVICE_IDLE_MODE_DEEP = 2;
    public static final int DEVICE_IDLE_MODE_LIGHT = 1;
    public static final int DEVICE_IDLE_MODE_OFF = 0;
    private static final String DISCHARGE_STEP_DATA = "dsd";
    private static final String DISCHARGE_TIME_REMAIN_DATA = "dtr";
    private static final int[] DISPLAY_TRANSPORT_PRIORITIES;
    public static final int DUMP_CHARGED_ONLY = 2;
    public static final int DUMP_DAILY_ONLY = 4;
    public static final int DUMP_DEVICE_WIFI_ONLY = 64;
    public static final int DUMP_HISTORY_ONLY = 8;
    public static final int DUMP_INCLUDE_HISTORY = 16;
    public static final int DUMP_VERBOSE = 32;
    public static final long DURATION_UNAVAILABLE = -1;
    private static final String FLASHLIGHT_DATA = "fla";
    public static final int FLASHLIGHT_TURNED_ON = 16;
    public static final int FOREGROUND_ACTIVITY = 10;
    public static final int FOREGROUND_SERVICE = 22;
    private static final String FOREGROUND_SERVICE_DATA = "fgs";
    public static final int FULL_WIFI_LOCK = 5;
    private static final String GLOBAL_BLUETOOTH_CONTROLLER_DATA = "gble";
    private static final String GLOBAL_CPU_FREQ_DATA = "gcf";
    private static final String GLOBAL_MODEM_CONTROLLER_DATA = "gmcd";
    private static final String GLOBAL_NETWORK_DATA = "gn";
    private static final String GLOBAL_WIFI_CONTROLLER_DATA = "gwfcd";
    private static final String GLOBAL_WIFI_DATA = "gwfl";
    private static final String HISTORY_DATA = "h";
    private static final String HISTORY_STRING_POOL = "hsp";
    public static final int JOB = 14;
    private static final String JOBS_DEFERRED_DATA = "jbd";
    private static final String JOB_COMPLETION_DATA = "jbc";
    private static final String JOB_DATA = "jb";
    private static final String KERNEL_WAKELOCK_DATA = "kwl";
    private static final boolean LOCAL_LOGV = false;
    public static final int MAX_TRACKED_SCREEN_STATE = 4;
    public static final double MILLISECONDS_IN_HOUR = 3600000.0d;
    private static final String MISC_DATA = "m";
    private static final String MODEM_CONTROLLER_DATA = "mcd";
    public static final int MODEM_TX_POWER_LEVELS = 5;
    public static final int NETWORK_BT_RX_DATA = 4;
    public static final int NETWORK_BT_TX_DATA = 5;
    private static final String NETWORK_DATA = "nt";
    public static final int NETWORK_MOBILE_BG_RX_DATA = 6;
    public static final int NETWORK_MOBILE_BG_TX_DATA = 7;
    public static final int NETWORK_MOBILE_RX_DATA = 0;
    public static final int NETWORK_MOBILE_TX_DATA = 1;
    public static final int NETWORK_WIFI_BG_RX_DATA = 8;
    public static final int NETWORK_WIFI_BG_TX_DATA = 9;
    public static final int NETWORK_WIFI_RX_DATA = 2;
    public static final int NETWORK_WIFI_TX_DATA = 3;
    public static final int NUM_HIGH_REFRESH_RATE_BINS = 4;
    public static final int NUM_NETWORK_ACTIVITY_TYPES = 10;
    public static final int NUM_SCREEN_BRIGHTNESS_BINS = 5;
    public static final int NUM_WIFI_SIGNAL_STRENGTH_BINS = 5;
    public static final long POWER_DATA_UNAVAILABLE = -1;
    private static final String POWER_USE_ITEM_DATA = "pwi";
    private static final String POWER_USE_SUMMARY_DATA = "pws";
    private static final String PROCESS_DATA = "pr";
    public static final int PROCESS_STATE = 12;
    public static final int RADIO_ACCESS_TECHNOLOGY_COUNT = 3;
    public static final int RADIO_ACCESS_TECHNOLOGY_LTE = 1;
    public static final int RADIO_ACCESS_TECHNOLOGY_NR = 2;
    public static final int RADIO_ACCESS_TECHNOLOGY_OTHER = 0;
    private static final String RESOURCE_POWER_MANAGER_DATA = "rpm";
    public static final String RESULT_RECEIVER_CONTROLLER_KEY = "controller_activity";
    public static final int SCREEN_BRIGHTNESS_BRIGHT = 4;
    public static final int SCREEN_BRIGHTNESS_DARK = 0;
    private static final String SCREEN_BRIGHTNESS_DATA = "br";
    public static final int SCREEN_BRIGHTNESS_DIM = 1;
    public static final int SCREEN_BRIGHTNESS_LIGHT = 3;
    public static final int SCREEN_BRIGHTNESS_MEDIUM = 2;
    protected static final boolean SCREEN_OFF_RPM_STATS_ENABLED = false;
    public static final int SENSOR = 3;
    private static final String SENSOR_DATA = "sr";
    public static final String SERVICE_NAME = "batterystats";
    private static final String SIGNAL_SCANNING_TIME_DATA = "sst";
    private static final String SIGNAL_STRENGTH_COUNT_DATA = "sgc";
    private static final String SIGNAL_STRENGTH_TIME_DATA = "sgt";
    private static final String STATE_TIME_DATA = "st";

    @Deprecated public static final int STATS_CURRENT = 1;
    public static final int STATS_SINCE_CHARGED = 0;

    @Deprecated public static final int STATS_SINCE_UNPLUGGED = 2;
    public static final long STEP_LEVEL_INITIAL_MODE_MASK = 71776119061217280L;
    public static final int STEP_LEVEL_INITIAL_MODE_SHIFT = 48;
    public static final long STEP_LEVEL_LEVEL_MASK = 280375465082880L;
    public static final int STEP_LEVEL_LEVEL_SHIFT = 40;
    public static final int STEP_LEVEL_MODE_DEVICE_IDLE = 8;
    public static final int STEP_LEVEL_MODE_POWER_SAVE = 4;
    public static final int STEP_LEVEL_MODE_SCREEN_STATE = 3;
    public static final long STEP_LEVEL_MODIFIED_MODE_MASK = -72057594037927936L;
    public static final int STEP_LEVEL_MODIFIED_MODE_SHIFT = 56;
    public static final long STEP_LEVEL_TIME_MASK = 1099511627775L;
    public static final int SYNC = 13;
    private static final String SYNC_DATA = "sy";
    private static final String TAG = "BatteryStats";
    private static final String UID_DATA = "uid";
    public static final String UID_TIMES_TYPE_ALL = "A";
    private static final String USER_ACTIVITY_DATA = "ua";
    private static final String VERSION_DATA = "vers";
    private static final String VIBRATOR_DATA = "vib";
    public static final int VIBRATOR_ON = 9;
    private static final String VIDEO_DATA = "vid";
    public static final int VIDEO_TURNED_ON = 8;
    private static final String WAKELOCK_DATA = "wl";
    private static final String WAKEUP_ALARM_DATA = "wua";
    private static final String WAKEUP_REASON_DATA = "wr";
    public static final int WAKE_TYPE_DRAW = 18;
    public static final int WAKE_TYPE_FULL = 1;
    public static final int WAKE_TYPE_PARTIAL = 0;
    public static final int WAKE_TYPE_WINDOW = 2;
    public static final int WIFI_AGGREGATE_MULTICAST_ENABLED = 23;
    public static final int WIFI_BATCHED_SCAN = 11;
    private static final String WIFI_CONTROLLER_DATA = "wfcd";
    private static final String WIFI_CONTROLLER_NAME = "WiFi";
    private static final String WIFI_DATA = "wfl";
    private static final String WIFI_MULTICAST_DATA = "wmc";
    public static final int WIFI_MULTICAST_ENABLED = 7;
    private static final String WIFI_MULTICAST_TOTAL_DATA = "wmct";
    public static final int WIFI_RUNNING = 4;
    public static final int WIFI_SCAN = 6;
    private static final String WIFI_SIGNAL_STRENGTH_COUNT_DATA = "wsgc";
    private static final String WIFI_SIGNAL_STRENGTH_TIME_DATA = "wsgt";
    private static final String WIFI_STATE_COUNT_DATA = "wsc";
    private static final String WIFI_STATE_TIME_DATA = "wst";
    private static final String WIFI_SUPPL_STATE_COUNT_DATA = "wssc";
    private static final String WIFI_SUPPL_STATE_TIME_DATA = "wsst";
    private final StringBuilder mFormatBuilder = new StringBuilder(32);
    private final Formatter mFormatter = new Formatter(this.mFormatBuilder);
    private static final String[] STAT_NAMES = {XmlTags.TAG_LEASEE, "c", XmlTags.ATTR_UID};
    public static final long[] JOB_FRESHNESS_BUCKETS = {
        3600000, 7200000, 14400000, 28800000, Long.MAX_VALUE
    };
    private static final String[] PROTECT_BATTERY_MODE_TYPES = {
        "off", "max", "longTerm", "basic", "adaptive"
    };
    public static final int NUM_PROTECT_BATTERY_MODE_TYPES = PROTECT_BATTERY_MODE_TYPES.length;
    static final String[] SCREEN_BRIGHTNESS_NAMES = {"dark", "dim", "medium", "light", "bright"};
    static final String[] SCREEN_BRIGHTNESS_SHORT_NAMES = {"0", "1", "2", "3", "4"};
    static final String[] DATA_CONNECTION_NAMES = {
        "oos",
        "gprs",
        Context.SEM_EDGE_SERVICE,
        "umts",
        "cdma",
        "evdo_0",
        "evdo_A",
        "1xrtt",
        "hsdpa",
        "hsupa",
        "hspa",
        "iden",
        "evdo_b",
        "lte",
        "ehrpd",
        "hspap",
        "gsm",
        "td_scdma",
        "iwlan",
        "lte_ca",
        "nr",
        "emngcy",
        "other"
    };
    public static final int NUM_ALL_NETWORK_TYPES = getAllNetworkTypesCount();
    public static final int DATA_CONNECTION_EMERGENCY_SERVICE = NUM_ALL_NETWORK_TYPES + 1;
    public static final int DATA_CONNECTION_OTHER = NUM_ALL_NETWORK_TYPES + 2;
    public static final int NUM_DATA_CONNECTION_TYPES = NUM_ALL_NETWORK_TYPES + 3;
    public static final String[] RADIO_ACCESS_TECHNOLOGY_NAMES = {
        "Other", DctConstants.RAT_NAME_LTE, "NR"
    };
    static final String[] WIFI_SUPPL_STATE_NAMES = {
        "invalid",
        "disconn",
        "disabled",
        "inactive",
        "scanning",
        "authenticating",
        "associating",
        "associated",
        "4-way-handshake",
        "group-handshake",
        "completed",
        "dormant",
        "uninit"
    };
    static final String[] WIFI_SUPPL_STATE_SHORT_NAMES = {
        "inv",
        "dsc",
        "dis",
        "inact",
        "scan",
        Context.AUTH_SERVICE,
        "ascing",
        "asced",
        "4-way",
        "group",
        "compl",
        "dorm",
        "uninit"
    };
    public static final BitDescription[] HISTORY_STATE_DESCRIPTIONS = {
        new BitDescription(Integer.MIN_VALUE, "running", "r"),
        new BitDescription(1073741824, "wake_lock", "w"),
        new BitDescription(8388608, Context.SENSOR_SERVICE, XmlTags.TAG_SESSION),
        new BitDescription(536870912, "gps", "g"),
        new BitDescription(268435456, "wifi_full_lock", "Wl"),
        new BitDescription(134217728, "wifi_scan", "Ws"),
        new BitDescription(65536, "wifi_multicast", "Wm"),
        new BitDescription(67108864, "wifi_radio", "Wr"),
        new BitDescription(33554432, "mobile_radio", "Pr"),
        new BitDescription(2097152, "phone_scanning", "Psc"),
        new BitDescription(4194304, "audio", FullBackup.APK_TREE_TOKEN),
        new BitDescription(1048576, "screen", GnssSignalType.CODE_TYPE_S),
        new BitDescription(524288, BatteryManager.EXTRA_PLUGGED, "BP"),
        new BitDescription(262144, "screen_doze", "Sd"),
        new BitDescription(
                HistoryItem.STATE_DATA_CONNECTION_MASK,
                9,
                "data_conn",
                "Pcn",
                DATA_CONNECTION_NAMES,
                DATA_CONNECTION_NAMES),
        new BitDescription(
                448,
                6,
                "phone_state",
                "Pst",
                new String[] {"in", "out", "emergency", "off"},
                new String[] {"in", "out", "em", "off"}),
        new BitDescription(
                56,
                3,
                "phone_signal_strength",
                "Pss",
                new String[] {"none", "poor", "moderate", "good", "great"},
                new String[] {"0", "1", "2", "3", "4"}),
        new BitDescription(
                7,
                0,
                SemBiometricConstants.KEY_INDISPLAY_SENSOR_OPTICAL_BRIGHTNESS,
                "Sb",
                SCREEN_BRIGHTNESS_NAMES,
                SCREEN_BRIGHTNESS_SHORT_NAMES)
    };
    public static final BitDescription[] HISTORY_STATE2_DESCRIPTIONS = {
        new BitDescription(Integer.MIN_VALUE, "power_save", "ps"),
        new BitDescription(1073741824, "video", "v"),
        new BitDescription(536870912, "wifi_running", "Ww"),
        new BitDescription(268435456, "wifi", GnssSignalType.CODE_TYPE_W),
        new BitDescription(134217728, "flashlight", "fl"),
        new BitDescription(
                100663296,
                25,
                "device_idle",
                "di",
                new String[] {"off", "light", "full", "???"},
                new String[] {"off", "light", "full", "???"}),
        new BitDescription(16777216, UsbManager.USB_FUNCTION_CHARGING, "ch"),
        new BitDescription(262144, "usb_data", "Ud"),
        new BitDescription(8388608, "phone_in_call", "Pcl"),
        new BitDescription(4194304, "bluetooth", XmlTags.TAG_BLOB),
        new BitDescription(
                112,
                4,
                "wifi_signal_strength",
                "Wss",
                new String[] {"0", "1", "2", "3", "4"},
                new String[] {"0", "1", "2", "3", "4"}),
        new BitDescription(
                15, 0, "wifi_suppl", "Wsp", WIFI_SUPPL_STATE_NAMES, WIFI_SUPPL_STATE_SHORT_NAMES),
        new BitDescription(2097152, Context.CAMERA_SERVICE, Credentials.CERTIFICATE_USAGE_CA),
        new BitDescription(1048576, "ble_scan", "bles"),
        new BitDescription(524288, "cellular_high_tx_power", "Chtp"),
        new BitDescription(
                384,
                7,
                "gps_signal_quality",
                "Gss",
                new String[] {"poor", "good", "none"},
                new String[] {"poor", "good", "none"}),
        new BitDescription(
                1536,
                9,
                "nr_state",
                "nrs",
                new String[] {
                    "none",
                    NetworkPolicyManager.FIREWALL_CHAIN_NAME_RESTRICTED,
                    "not_restricted",
                    "connected"
                },
                new String[] {"0", "1", "2", "3"})
    };
    private static final String FOREGROUND_ACTIVITY_DATA = "fg";
    public static final String[] HISTORY_EVENT_NAMES = {
        "null",
        "proc",
        FOREGROUND_ACTIVITY_DATA,
        GenerateXML.TOP,
        "sync",
        "wake_lock_in",
        "job",
        "user",
        "userfg",
        "conn",
        "active",
        "pkginst",
        "pkgunin",
        "alarm",
        Context.STATS_MANAGER,
        "pkginactive",
        "pkgactive",
        "tmpwhitelist",
        "screenwake",
        "wakeupap",
        "longwake",
        "est_capacity",
        "state"
    };
    public static final String[] HISTORY_EVENT_CHECKIN_NAMES = {
        "Enl", "Epr", "Efg", "Etp", "Esy", "Ewl", "Ejb", "Eur", "Euf", "Ecn", "Eac", "Epi", "Epu",
        "Eal", "Est", "Eai", "Eaa", "Etw", "Esw", "Ewa", "Elw", "Eec", "Esc"
    };
    private static final IntToString sUidToString =
            new IntToString() { // from class: android.os.BatteryStats$$ExternalSyntheticLambda0
                @Override // android.os.BatteryStats.IntToString
                public final String applyAsString(int i) {
                    return UserHandle.formatUid(i);
                }
            };
    private static final IntToString sIntToString =
            new IntToString() { // from class: android.os.BatteryStats$$ExternalSyntheticLambda1
                @Override // android.os.BatteryStats.IntToString
                public final String applyAsString(int i) {
                    return Integer.toString(i);
                }
            };
    public static final IntToString[] HISTORY_EVENT_INT_FORMATTERS = {
        sUidToString,
        sUidToString,
        sUidToString,
        sUidToString,
        sUidToString,
        sUidToString,
        sUidToString,
        sUidToString,
        sUidToString,
        sUidToString,
        sUidToString,
        sIntToString,
        sUidToString,
        sUidToString,
        sUidToString,
        sUidToString,
        sUidToString,
        sUidToString,
        sUidToString,
        sUidToString,
        sUidToString,
        sIntToString,
        sUidToString
    };
    static final String[] WIFI_STATE_NAMES = {
        "off",
        "scanning",
        "no_net",
        "disconn",
        "sta",
        SemWifiP2pManager.TYPE_WIFI_P2P,
        "sta_p2p",
        "soft_ap"
    };
    public static final int[] STEP_LEVEL_MODES_OF_INTEREST = {7, 15, 11, 7, 7, 7, 7, 7, 15, 11};
    public static final int[] STEP_LEVEL_MODE_VALUES = {0, 4, 8, 1, 5, 2, 6, 3, 7, 11};
    public static final String[] STEP_LEVEL_MODE_LABELS = {
        "screen off",
        "screen off power save",
        "screen off device idle",
        "screen on",
        "screen on power save",
        "screen doze",
        "screen doze power save",
        "screen doze-suspend",
        "screen doze-suspend power save",
        "screen doze-suspend device idle"
    };
    private static final String[] CHECKIN_POWER_COMPONENT_LABELS = new String[19];

    public interface BatteryStatsDumpHelper {
        BatteryUsageStats getBatteryUsageStats(BatteryStats batteryStats, boolean z);
    }

    public abstract static class ControllerActivityCounter {
        public abstract LongCounter getIdleTimeCounter();

        public abstract LongCounter getMonitoredRailChargeConsumedMaMs();

        public abstract LongCounter getPowerCounter();

        public abstract LongCounter getRxTimeCounter();

        public abstract LongCounter getScanTimeCounter();

        public abstract LongCounter getSleepTimeCounter();

        public abstract LongCounter[] getTxTimeCounters();
    }

    public abstract static class Counter {
        public abstract int getCountLocked(int i);

        public abstract void logState(Printer printer, String str);
    }

    public static final class DailyItem {
        public LevelStepTracker mChargeSteps;
        public LevelStepTracker mDischargeSteps;
        public long mEndTime;
        public ArrayList<PackageChange> mPackageChanges;
        public long mStartTime;
    }

    @FunctionalInterface
    public interface IntToString {
        String applyAsString(int i);
    }

    public abstract static class LongCounter {
        public abstract long getCountForProcessState(int i);

        public abstract long getCountLocked(int i);

        public abstract void logState(Printer printer, String str);
    }

    public abstract static class LongCounterArray {
        public abstract long[] getCountsLocked(int i);

        public abstract void logState(Printer printer, String str);
    }

    public abstract static class ModemActivityCounter {
        public abstract LongCounter getIdleTimeCounter();

        public abstract ModemTxRxCounter getLcModemActivityInfo();

        public abstract ModemTxRxCounter getNrModemActivityInfo();

        public abstract LongCounter getSleepTimeCounter();
    }

    public abstract static class ModemTxRxCounter {
        public abstract LongCounter getRxByteCounter();

        public abstract LongCounter getRxTimeCounter();

        public abstract LongCounter getTxByteCounter();

        public abstract LongCounter[] getTxTimeCounters();
    }

    public static final class PackageChange {
        public String mPackageName;
        public boolean mUpdate;
        public long mVersionCode;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RadioAccessTechnology {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface StatName {}

    public abstract boolean canReadTimeToFullNow();

    public abstract boolean canTrustSecPowerProfile();

    public abstract void commitCurrentHistoryBatchLocked();

    public abstract long computeBatteryRealtime(long j, int i);

    public abstract long computeBatteryScreenOffRealtime(long j, int i);

    public abstract long computeBatteryScreenOffUptime(long j, int i);

    public abstract long computeBatteryTimeRemaining(long j);

    public abstract long computeBatteryUptime(long j, int i);

    public abstract long computeChargeTimeRemaining(long j);

    public abstract long computeRealtime(long j, int i);

    public abstract long computeUptime(long j, int i);

    public abstract long getActiveRadioDurationMs(int i, int i2, int i3, long j);

    public abstract long getActiveRxRadioDurationMs(int i, int i2, long j);

    public abstract long getActiveTxRadioDurationMs(int i, int i2, int i3, long j);

    public abstract long getBatteryRealtime(long j);

    public abstract long getBatteryUptime(long j);

    public abstract BluetoothBatteryStats getBluetoothBatteryStats();

    public abstract ControllerActivityCounter getBluetoothControllerActivity();

    public abstract long getBluetoothEnergyConsumptionUC();

    public abstract long getBluetoothScanTime(long j, int i);

    public abstract long getCameraEnergyConsumptionUC();

    public abstract long getCameraOnTime(long j, int i);

    public abstract LevelStepTracker getChargeLevelStepTracker();

    public abstract long getCpuEnergyConsumptionUC();

    public abstract CpuScalingPolicies getCpuScalingPolicies();

    public abstract long getCurrentDailyStartTime();

    public abstract long[] getCustomEnergyConsumerBatteryConsumptionUC();

    public abstract String[] getCustomEnergyConsumerNames();

    public abstract LevelStepTracker getDailyChargeLevelStepTracker();

    public abstract LevelStepTracker getDailyDischargeLevelStepTracker();

    public abstract DailyItem getDailyItemLocked(int i);

    public abstract ArrayList<PackageChange> getDailyPackageChanges();

    public abstract int getDeviceIdleModeCount(int i, int i2);

    public abstract long getDeviceIdleModeTime(int i, long j, int i2);

    public abstract int getDeviceIdlingCount(int i, int i2);

    public abstract long getDeviceIdlingTime(int i, long j, int i2);

    public abstract int getDischargeAmount(int i);

    public abstract int getDischargeAmountScreenDoze();

    public abstract int getDischargeAmountScreenDozeSinceCharge();

    public abstract int getDischargeAmountScreenDozeSinceChargePermil();

    public abstract int getDischargeAmountScreenOff();

    public abstract int getDischargeAmountScreenOffSinceCharge();

    public abstract int getDischargeAmountScreenOffSinceChargeCoulombCounter();

    public abstract int getDischargeAmountScreenOffSinceChargePermil();

    public abstract int getDischargeAmountScreenOn();

    public abstract int getDischargeAmountScreenOnSinceCharge();

    public abstract int getDischargeAmountScreenOnSinceChargeCoulombCounter();

    public abstract int getDischargeAmountScreenOnSinceChargePermil();

    public abstract int getDischargeAmountSubScreenDozeSinceChargePermil();

    public abstract int getDischargeAmountSubScreenOnSinceChargePermil();

    public abstract int getDischargeCurrentLevel();

    public abstract LevelStepTracker getDischargeLevelStepTracker();

    public abstract int getDischargeStartLevel();

    public abstract int getDisplayCount();

    public abstract long getDisplayHighRefreshRateTime(int i, long j, int i2);

    public abstract Timer getDisplayHighRefreshRateTimer(int i);

    public abstract long getDisplayScreenBrightnessTime(int i, int i2, long j);

    public abstract long getDisplayScreenDozeTime(int i, long j);

    public abstract long getDisplayScreenOnTime(int i, long j);

    public abstract String getEndPlatformVersion();

    public abstract int getEstimatedBatteryCapacity();

    public abstract long getFlashlightOnCount(int i);

    public abstract long getFlashlightOnTime(long j, int i);

    public abstract long getGlobalWifiRunningTime(long j, int i);

    public abstract long getGnssEnergyConsumptionUC();

    public abstract long getGpsBatteryDrainMaMs();

    public abstract long getGpsSignalQualityTime(int i, long j, int i2);

    public abstract int getHighDischargeAmountSinceCharge();

    public abstract int getHistoryStringPoolBytes();

    public abstract int getHistoryStringPoolSize();

    public abstract String getHistoryTagPoolString(int i);

    public abstract int getHistoryTagPoolUid(int i);

    public abstract int getHistoryTotalSize();

    public abstract int getHistoryUsedSize();

    public abstract long getInteractiveTime(long j, int i);

    public abstract boolean getIsOnBattery();

    public abstract LongSparseArray<? extends Timer> getKernelMemoryStats();

    public abstract Map<String, ? extends Timer> getKernelWakelockStats();

    public abstract int getLearnedBatteryCapacity();

    public abstract long getLongestDeviceIdleModeTime(int i);

    public abstract int getLowDischargeAmountSinceCharge();

    public abstract int getMaxLearnedBatteryCapacity();

    public abstract int getMinLearnedBatteryCapacity();

    public abstract long getMobileActive5GTime(long j, int i);

    public abstract long getMobileActiveTime(long j, int i);

    public abstract long getMobileRadioActiveAdjustedTime(int i);

    public abstract int getMobileRadioActiveCount(int i);

    public abstract long getMobileRadioActiveTime(long j, int i);

    public abstract int getMobileRadioActiveUnknownCount(int i);

    public abstract long getMobileRadioActiveUnknownTime(int i);

    public abstract long getMobileRadioEnergyConsumptionUC();

    public abstract ControllerActivityCounter getModemControllerActivity();

    public abstract long getNetworkActivityBytes(int i, int i2);

    public abstract long getNetworkActivityPackets(int i, int i2);

    public abstract ModemActivityCounter getNetworkModemControllerActivity();

    public abstract long getNextMaxDailyDeadline();

    public abstract long getNextMinDailyDeadline();

    public abstract long getNrNsaTime(long j);

    public abstract int getNumConnectivityChange(int i);

    public abstract int getParcelVersion();

    public abstract int getPhoneDataConnectionCount(int i, int i2);

    public abstract long getPhoneDataConnectionTime(int i, long j, int i2);

    public abstract Timer getPhoneDataConnectionTimer(int i);

    public abstract long getPhoneEnergyConsumptionUC();

    public abstract int getPhoneOnCount(int i);

    public abstract long getPhoneOnTime(long j, int i);

    public abstract long getPhoneSignalScanningTime(long j, int i);

    public abstract Timer getPhoneSignalScanningTimer();

    public abstract int getPhoneSignalStrengthCount(int i, int i2);

    public abstract long getPhoneSignalStrengthTime(int i, long j, int i2);

    protected abstract Timer getPhoneSignalStrengthTimer(int i);

    public abstract int getPowerSaveModeEnabledCount(int i);

    public abstract long getPowerSaveModeEnabledTime(long j, int i);

    public abstract Map<String, ? extends Timer> getRpmStats();

    public abstract long getScreenAutoBrightnessTime(int i, long j, int i2);

    public abstract long getScreenBrightnessTime(int i, long j, int i2);

    public abstract Timer getScreenBrightnessTimer(int i);

    public abstract int getScreenDozeCount(int i);

    public abstract long getScreenDozeEnergyConsumptionUC();

    public abstract long getScreenDozeTime(long j, int i);

    public abstract long getScreenHighBrightnessTime(long j, int i);

    public abstract Map<String, ? extends Timer> getScreenOffRpmStats();

    public abstract int getScreenOnCount(int i);

    public abstract long getScreenOnEnergyConsumptionUC();

    public abstract long getScreenOnGpsRunningTime(long j, int i);

    public abstract long getScreenOnTime(long j, int i);

    public abstract Map<String, ? extends Counter> getScreenWakeStats();

    public abstract long getSpeakerCallTime(int i, int i2);

    public abstract long getSpeakerMediaTime(int i, int i2);

    public abstract long getStartClockTime();

    public abstract int getStartCount();

    public abstract String getStartPlatformVersion();

    public abstract long getStatsStartRealtime();

    public abstract long getSubDisplayHighRefreshRateTime(int i, long j, int i2);

    public abstract long getSubScreenAutoBrightnessTime(int i, long j, int i2);

    public abstract long getSubScreenBrightnessTime(int i, long j, int i2);

    public abstract Timer getSubScreenBrightnessTimer(int i);

    public abstract int getSubScreenDozeCount(int i);

    public abstract long getSubScreenDozeTime(long j, int i);

    public abstract long getSubScreenHighBrightnessTime(long j, int i);

    public abstract int getSubScreenOnCount(int i);

    public abstract long getSubScreenOnTime(long j, int i);

    public abstract long[] getSystemServiceTimeAtCpuSpeeds();

    public abstract long getTxPowerSharingTime(long j, int i);

    public abstract long getTxSharingDischargeAmount();

    public abstract long getUahDischarge(int i);

    public abstract long getUahDischargeDeepDoze(int i);

    public abstract long getUahDischargeLightDoze(int i);

    public abstract long getUahDischargeScreenDoze(int i);

    public abstract long getUahDischargeScreenOff(int i);

    public abstract SparseArray<? extends Uid> getUidStats();

    public abstract WakeLockStats getWakeLockStats();

    public abstract Map<String, ? extends Timer> getWakeupReasonStats();

    public abstract long getWifiActiveTime(long j, int i);

    public abstract ControllerActivityCounter getWifiControllerActivity();

    public abstract long getWifiEnergyConsumptionUC();

    public abstract int getWifiMulticastWakelockCount(int i);

    public abstract long getWifiMulticastWakelockTime(long j, int i);

    public abstract long getWifiOnTime(long j, int i);

    public abstract int getWifiSignalStrengthCount(int i, int i2);

    public abstract long getWifiSignalStrengthTime(int i, long j, int i2);

    public abstract Timer getWifiSignalStrengthTimer(int i);

    public abstract int getWifiStateCount(int i, int i2);

    public abstract long getWifiStateTime(int i, long j, int i2);

    public abstract Timer getWifiStateTimer(int i);

    public abstract int getWifiSupplStateCount(int i, int i2);

    public abstract long getWifiSupplStateTime(int i, long j, int i2);

    public abstract Timer getWifiSupplStateTimer(int i);

    public abstract boolean hasBluetoothActivityReporting();

    public abstract boolean hasModemActivityReporting();

    public abstract boolean hasSpeakerOutPowerReporting();

    public abstract boolean hasWifiActivityReporting();

    public abstract boolean isProcessStateDataAvailable();

    public abstract BatteryStatsHistoryIterator iterateBatteryStatsHistory(long j, long j2);

    public abstract void updateTxPowerSharing();

    static {
        CHECKIN_POWER_COMPONENT_LABELS[0] = "scrn";
        CHECKIN_POWER_COMPONENT_LABELS[1] = CPU_DATA;
        CHECKIN_POWER_COMPONENT_LABELS[2] = "blue";
        CHECKIN_POWER_COMPONENT_LABELS[3] = Context.CAMERA_SERVICE;
        CHECKIN_POWER_COMPONENT_LABELS[4] = "audio";
        CHECKIN_POWER_COMPONENT_LABELS[5] = "video";
        CHECKIN_POWER_COMPONENT_LABELS[6] = "flashlight";
        CHECKIN_POWER_COMPONENT_LABELS[8] = "cell";
        CHECKIN_POWER_COMPONENT_LABELS[9] = "sensors";
        CHECKIN_POWER_COMPONENT_LABELS[10] = "gnss";
        CHECKIN_POWER_COMPONENT_LABELS[11] = "wifi";
        CHECKIN_POWER_COMPONENT_LABELS[13] = "memory";
        CHECKIN_POWER_COMPONENT_LABELS[14] = "phone";
        CHECKIN_POWER_COMPONENT_LABELS[15] = "ambi";
        CHECKIN_POWER_COMPONENT_LABELS[16] = "idle";
        DISPLAY_TRANSPORT_PRIORITIES = new int[] {4, 0, 5, 2, 1, 3, 8};
    }

    public abstract static class Timer {
        public abstract int getCountLocked(int i);

        public abstract long getTimeSinceMarkLocked(long j);

        public abstract long getTotalTimeLocked(long j, int i);

        public abstract void logState(Printer printer, String str);

        public long getMaxDurationMsLocked(long elapsedRealtimeMs) {
            return -1L;
        }

        public long getCurrentDurationMsLocked(long elapsedRealtimeMs) {
            return -1L;
        }

        public long getTotalDurationMsLocked(long elapsedRealtimeMs) {
            return -1L;
        }

        public Timer getSubTimer() {
            return null;
        }

        public boolean isRunningLocked() {
            return false;
        }
    }

    public static int mapToInternalProcessState(int procState) {
        if (procState == 20) {
            return 7;
        }
        if (procState == 2) {
            return 0;
        }
        if (procState == 3) {
            return 3;
        }
        if (procState == 4 || procState == 5) {
            return 1;
        }
        if (procState <= 6) {
            return 2;
        }
        if (procState <= 11) {
            return 3;
        }
        if (procState <= 12) {
            return 4;
        }
        if (procState <= 13) {
            return 5;
        }
        return 6;
    }

    public static int mapUidProcessStateToBatteryConsumerProcessState(int processState) {
        switch (processState) {
            case 0:
            case 2:
                return 1;
            case 1:
                return 3;
            case 3:
            case 4:
                return 2;
            case 5:
            default:
                return 0;
            case 6:
                return 4;
        }
    }

    public abstract static class Uid {
        public static final int NUM_PROCESS_STATE = 7;
        public static final int NUM_WIFI_BATCHED_SCAN_BINS = 5;
        public static final int PROCESS_STATE_BACKGROUND = 3;
        public static final int PROCESS_STATE_CACHED = 6;
        public static final int PROCESS_STATE_FOREGROUND = 2;
        public static final int PROCESS_STATE_FOREGROUND_SERVICE = 1;
        public static final int PROCESS_STATE_HEAVY_WEIGHT = 5;
        public static final int PROCESS_STATE_NONEXISTENT = 7;
        public static final int PROCESS_STATE_TOP = 0;
        public static final int PROCESS_STATE_TOP_SLEEPING = 4;
        static final String[] PROCESS_STATE_NAMES = {
            "Top",
            "Fg Service",
            "Foreground",
            "Background",
            "Top Sleeping",
            "Heavy Weight",
            "Cached"
        };
        public static final String[] UID_PROCESS_TYPES = {
            "T", "FS", "F", GnssSignalType.CODE_TYPE_B, "TS", "HW", GnssSignalType.CODE_TYPE_C
        };
        static final String[] USER_ACTIVITY_TYPES = {
            "other",
            "button",
            "touch",
            Context.ACCESSIBILITY_SERVICE,
            Context.ATTENTION_SERVICE,
            "faceDown",
            "deviceState"
        };
        public static final int NUM_USER_ACTIVITY_TYPES = USER_ACTIVITY_TYPES.length;

        public abstract static class Pkg {

            public abstract static class Serv {
                public abstract int getLaunches(int i);

                public abstract long getStartTime(long j, int i);

                public abstract int getStarts(int i);
            }

            public abstract ArrayMap<String, ? extends Serv> getServiceStats();

            public abstract ArrayMap<String, ? extends Counter> getWakeupAlarmStats();
        }

        public abstract static class Proc {

            public static class ExcessivePower {
                public static final int TYPE_CPU = 2;
                public static final int TYPE_WAKE = 1;
                public long overTime;
                public int type;
                public long usedTime;
            }

            public abstract int countExcessivePowers();

            public abstract ExcessivePower getExcessivePower(int i);

            public abstract long getForegroundTime(int i);

            public abstract int getNumAnrs(int i);

            public abstract int getNumCrashes(int i);

            public abstract int getStarts(int i);

            public abstract long getSystemTime(int i);

            public abstract long getUserTime(int i);

            public abstract boolean isActive();
        }

        public abstract static class Sensor {
            public static final int GPS = -10000;
            public static final int actualGPS = -10001;

            public abstract int getHandle();

            public abstract Timer getSensorBackgroundTime();

            public abstract Timer getSensorTime();
        }

        public abstract static class Wakelock {
            public abstract Timer getWakeTime(int i);
        }

        public abstract Timer getAggregatedPartialWakelockTimer();

        public abstract Timer getAudioTurnedOnTimer();

        public abstract ControllerActivityCounter getBluetoothControllerActivity();

        public abstract Timer getBluetoothDutyScanTimer();

        public abstract long getBluetoothEnergyConsumptionUC();

        public abstract long getBluetoothEnergyConsumptionUC(int i);

        public abstract Timer getBluetoothScanBackgroundTimer();

        public abstract Counter getBluetoothScanResultBgCounter();

        public abstract Counter getBluetoothScanResultCounter();

        public abstract Timer getBluetoothScanTimer();

        public abstract Timer getBluetoothUnoptimizedScanBackgroundTimer();

        public abstract Timer getBluetoothUnoptimizedScanTimer();

        public abstract long getCameraEnergyConsumptionUC();

        public abstract Timer getCameraTurnedOnTimer();

        public abstract long getCpuActiveTime();

        public abstract long getCpuActiveTime(int i);

        public abstract long[] getCpuClusterTimes();

        public abstract long getCpuEnergyConsumptionUC();

        public abstract long getCpuEnergyConsumptionUC(int i);

        public abstract boolean getCpuFreqTimes(long[] jArr, int i);

        public abstract long[] getCpuFreqTimes(int i);

        public abstract long[] getCustomEnergyConsumerBatteryConsumptionUC();

        public abstract void getDeferredJobsCheckinLineLocked(StringBuilder sb, int i);

        public abstract void getDeferredJobsLineLocked(StringBuilder sb, int i);

        public abstract long getDisplayTopActivityTime(int i, long j, int i2);

        public abstract Timer getFlashlightTurnedOnTimer();

        public abstract Timer getForegroundActivityTimer();

        public abstract Timer getForegroundServiceTimer();

        public abstract long getFullWifiLockTime(long j, int i);

        public abstract long getGnssEnergyConsumptionUC();

        public abstract ArrayMap<String, SparseIntArray> getJobCompletionStats();

        public abstract ArrayMap<String, ? extends Timer> getJobStats();

        public abstract int getMobileRadioActiveCount(int i);

        public abstract long getMobileRadioActiveTime(int i);

        public abstract long getMobileRadioActiveTimeInProcessState(int i);

        public abstract long getMobileRadioApWakeupCount(int i);

        public abstract long getMobileRadioEnergyConsumptionUC();

        public abstract long getMobileRadioEnergyConsumptionUC(int i);

        public abstract ControllerActivityCounter getModemControllerActivity();

        public abstract Timer getMulticastWakelockStats();

        public abstract long getNetworkActivityBytes(int i, int i2);

        public abstract long getNetworkActivityPackets(int i, int i2);

        public abstract ArrayMap<String, ? extends Pkg> getPackageStats();

        public abstract SparseArray<? extends Pid> getPidStats();

        public abstract long getProcessStateTime(int i, long j, int i2);

        public abstract Timer getProcessStateTimer(int i);

        public abstract ArrayMap<String, ? extends Proc> getProcessStats();

        public abstract double getProportionalSystemServiceUsage();

        public abstract boolean getScreenOffCpuFreqTimes(long[] jArr, int i);

        public abstract long[] getScreenOffCpuFreqTimes(int i);

        public abstract long getScreenOnEnergyConsumptionUC();

        public abstract SparseArray<? extends Sensor> getSensorStats();

        public abstract long getSpeakerMediaTime(int i, int i2);

        public abstract ArrayMap<String, ? extends Timer> getSyncStats();

        public abstract long getSystemCpuTimeUs(int i);

        @Deprecated
        public abstract long getTimeAtCpuSpeed(int i, int i2, int i3);

        public abstract int getUid();

        public abstract int getUserActivityCount(int i, int i2);

        public abstract long getUserCpuTimeUs(int i);

        public abstract Timer getVibratorOnTimer();

        public abstract Timer getVideoTurnedOnTimer();

        public abstract ArrayMap<String, ? extends Wakelock> getWakelockStats();

        public abstract int getWifiBatchedScanCount(int i, int i2);

        public abstract long getWifiBatchedScanTime(int i, long j, int i2);

        public abstract ControllerActivityCounter getWifiControllerActivity();

        public abstract long getWifiEnergyConsumptionUC();

        public abstract long getWifiEnergyConsumptionUC(int i);

        public abstract long getWifiMulticastTime(long j, int i);

        public abstract long getWifiRadioApWakeupCount(int i);

        public abstract long getWifiRunningTime(long j, int i);

        public abstract long getWifiScanActualTime(long j);

        public abstract int getWifiScanBackgroundCount(int i);

        public abstract long getWifiScanBackgroundTime(long j);

        public abstract Timer getWifiScanBackgroundTimer();

        public abstract int getWifiScanCount(int i);

        public abstract long getWifiScanTime(long j, int i);

        public abstract Timer getWifiScanTimer();

        public abstract boolean hasNetworkActivity();

        public abstract boolean hasSpeakerActivity();

        public abstract boolean hasUserActivity();

        public abstract void noteActivityPausedLocked(long j);

        public abstract void noteActivityResumedLocked(long j);

        public abstract void noteFullWifiLockAcquiredLocked(long j);

        public abstract void noteFullWifiLockReleasedLocked(long j);

        public abstract void noteUserActivityLocked(int i);

        public abstract void noteWifiBatchedScanStartedLocked(int i, long j);

        public abstract void noteWifiBatchedScanStoppedLocked(long j);

        public abstract void noteWifiMulticastDisabledLocked(long j);

        public abstract void noteWifiMulticastEnabledLocked(long j);

        public abstract void noteWifiRunningLocked(long j);

        public abstract void noteWifiScanStartedLocked(long j);

        public abstract void noteWifiScanStoppedLocked(long j);

        public abstract void noteWifiStoppedLocked(long j);

        public class Pid {
            public int mWakeNesting;
            public long mWakeStartMs;
            public long mWakeSumMs;

            public Pid() {}
        }
    }

    public static final class LevelStepTracker {
        public long mLastStepTime = -1;
        public int mNumStepDurations;
        public final long[] mStepDurations;

        public LevelStepTracker(int maxLevelSteps) {
            this.mStepDurations = new long[maxLevelSteps];
        }

        public LevelStepTracker(int numSteps, long[] steps) {
            this.mNumStepDurations = numSteps;
            this.mStepDurations = new long[numSteps];
            System.arraycopy(steps, 0, this.mStepDurations, 0, numSteps);
        }

        public long getDurationAt(int index) {
            return this.mStepDurations[index] & BatteryStats.STEP_LEVEL_TIME_MASK;
        }

        public int getLevelAt(int index) {
            return (int) ((this.mStepDurations[index] & BatteryStats.STEP_LEVEL_LEVEL_MASK) >> 40);
        }

        public int getInitModeAt(int index) {
            return (int)
                    ((this.mStepDurations[index] & BatteryStats.STEP_LEVEL_INITIAL_MODE_MASK)
                            >> 48);
        }

        public int getModModeAt(int index) {
            return (int)
                    ((this.mStepDurations[index] & BatteryStats.STEP_LEVEL_MODIFIED_MODE_MASK)
                            >> 56);
        }

        private void appendHex(long val, int topOffset, StringBuilder out) {
            boolean hasData = false;
            while (topOffset >= 0) {
                int digit = (int) ((val >> topOffset) & 15);
                topOffset -= 4;
                if (hasData || digit != 0) {
                    hasData = true;
                    if (digit >= 0 && digit <= 9) {
                        out.append((char) (digit + 48));
                    } else {
                        out.append((char) ((digit + 97) - 10));
                    }
                }
            }
        }

        public void encodeEntryAt(int index, StringBuilder out) {
            long item = this.mStepDurations[index];
            long duration = BatteryStats.STEP_LEVEL_TIME_MASK & item;
            int level = (int) ((BatteryStats.STEP_LEVEL_LEVEL_MASK & item) >> 40);
            int initMode = (int) ((BatteryStats.STEP_LEVEL_INITIAL_MODE_MASK & item) >> 48);
            int modMode = (int) ((BatteryStats.STEP_LEVEL_MODIFIED_MODE_MASK & item) >> 56);
            switch ((initMode & 3) + 1) {
                case 1:
                    out.append('f');
                    break;
                case 2:
                    out.append('o');
                    break;
                case 3:
                    out.append(DateFormat.DATE);
                    break;
                case 4:
                    out.append(DateFormat.TIME_ZONE);
                    break;
            }
            if ((initMode & 4) != 0) {
                out.append('p');
            }
            if ((initMode & 8) != 0) {
                out.append('i');
            }
            switch ((modMode & 3) + 1) {
                case 1:
                    out.append('F');
                    break;
                case 2:
                    out.append('O');
                    break;
                case 3:
                    out.append('D');
                    break;
                case 4:
                    out.append('Z');
                    break;
            }
            if ((modMode & 4) != 0) {
                out.append('P');
            }
            if ((modMode & 8) != 0) {
                out.append('I');
            }
            out.append('-');
            appendHex(level, 4, out);
            out.append('-');
            appendHex(duration, 36, out);
        }

        public void decodeEntryAt(int index, String value) {
            char c;
            char c2;
            char c3;
            char c4;
            char c5;
            char c6;
            int N = value.length();
            int i = 0;
            long out = 0;
            while (true) {
                c = '-';
                if (i < N && (c6 = value.charAt(i)) != '-') {
                    i++;
                    switch (c6) {
                        case 'D':
                            out |= 144115188075855872L;
                            break;
                        case 'F':
                            out |= 0;
                            break;
                        case 'I':
                            out |= 576460752303423488L;
                            break;
                        case 'O':
                            out |= 72057594037927936L;
                            break;
                        case 'P':
                            out |= 288230376151711744L;
                            break;
                        case 'Z':
                            out |= 216172782113783808L;
                            break;
                        case 'd':
                            out |= 562949953421312L;
                            break;
                        case 'f':
                            out |= 0;
                            break;
                        case 'i':
                            out |= FrontendInnerFec.FEC_135_180;
                            break;
                        case 'o':
                            out |= 281474976710656L;
                            break;
                        case 'p':
                            out |= FrontendInnerFec.FEC_132_180;
                            break;
                        case 'z':
                            out |= 844424930131968L;
                            break;
                    }
                }
            }
            int i2 = i + 1;
            long level = 0;
            while (true) {
                c2 = '9';
                c3 = 4;
                if (i2 < N && (c5 = value.charAt(i2)) != '-') {
                    i2++;
                    level <<= 4;
                    if (c5 >= '0' && c5 <= '9') {
                        level += c5 - '0';
                    } else if (c5 >= 'a' && c5 <= 'f') {
                        level += (c5 - 'a') + 10;
                    } else if (c5 >= 'A' && c5 <= 'F') {
                        level += (c5 - 'A') + 10;
                    }
                }
            }
            int i3 = i2 + 1;
            long out2 = out | ((level << 40) & BatteryStats.STEP_LEVEL_LEVEL_MASK);
            long duration = 0;
            while (i3 < N) {
                char c7 = value.charAt(i3);
                if (c7 != c) {
                    i3++;
                    duration <<= c3;
                    if (c7 >= '0' && c7 <= c2) {
                        duration += c7 - '0';
                        c = '-';
                        c2 = '9';
                        c3 = 4;
                    } else if (c7 >= 'a' && c7 <= 'f') {
                        duration += (c7 - 'a') + 10;
                        c = '-';
                        c2 = '9';
                        c3 = 4;
                    } else {
                        if (c7 >= 'A') {
                            c4 = 'F';
                            if (c7 <= 'F') {
                                duration += (c7 - 'A') + 10;
                                c = '-';
                                c2 = '9';
                                c3 = 4;
                            }
                        } else {
                            c4 = 'F';
                        }
                        c = '-';
                        c2 = '9';
                        c3 = 4;
                    }
                } else {
                    this.mStepDurations[index] =
                            (BatteryStats.STEP_LEVEL_TIME_MASK & duration) | out2;
                }
            }
            this.mStepDurations[index] = (BatteryStats.STEP_LEVEL_TIME_MASK & duration) | out2;
        }

        public void init() {
            this.mLastStepTime = -1L;
            this.mNumStepDurations = 0;
        }

        public void clearTime() {
            this.mLastStepTime = -1L;
        }

        public long computeTimePerLevel() {
            long[] steps = this.mStepDurations;
            int numSteps = this.mNumStepDurations;
            if (numSteps <= 0) {
                return -1L;
            }
            long total = 0;
            for (int i = 0; i < numSteps; i++) {
                total += steps[i] & BatteryStats.STEP_LEVEL_TIME_MASK;
            }
            return total / numSteps;
        }

        public long computeTimeEstimate(
                long modesOfInterest, long modeValues, int[] outNumOfInterest) {
            long[] steps = this.mStepDurations;
            int count = this.mNumStepDurations;
            if (count <= 0) {
                return -1L;
            }
            long total = 0;
            int numOfInterest = 0;
            for (int i = 0; i < count; i++) {
                long initMode = (steps[i] & BatteryStats.STEP_LEVEL_INITIAL_MODE_MASK) >> 48;
                long modMode = (steps[i] & BatteryStats.STEP_LEVEL_MODIFIED_MODE_MASK) >> 56;
                if ((modMode & modesOfInterest) == 0
                        && (initMode & modesOfInterest) == modeValues) {
                    numOfInterest++;
                    total += steps[i] & BatteryStats.STEP_LEVEL_TIME_MASK;
                }
            }
            if (numOfInterest <= 0) {
                return -1L;
            }
            if (outNumOfInterest != null) {
                outNumOfInterest[0] = numOfInterest;
            }
            return (total / numOfInterest) * 100;
        }

        public void addLevelSteps(int numStepLevels, long modeBits, long elapsedRealtime) {
            int stepCount = this.mNumStepDurations;
            long lastStepTime = this.mLastStepTime;
            if (lastStepTime >= 0 && numStepLevels > 0) {
                long[] steps = this.mStepDurations;
                long duration = elapsedRealtime - lastStepTime;
                for (int i = 0; i < numStepLevels; i++) {
                    System.arraycopy(steps, 0, steps, 1, steps.length - 1);
                    long thisDuration = duration / (numStepLevels - i);
                    duration -= thisDuration;
                    if (thisDuration > BatteryStats.STEP_LEVEL_TIME_MASK) {
                        thisDuration = BatteryStats.STEP_LEVEL_TIME_MASK;
                    }
                    steps[0] = thisDuration | modeBits;
                }
                stepCount += numStepLevels;
                if (stepCount > steps.length) {
                    stepCount = steps.length;
                }
            }
            this.mNumStepDurations = stepCount;
            this.mLastStepTime = elapsedRealtime;
        }

        public void readFromParcel(Parcel in) {
            int N = in.readInt();
            if (N > this.mStepDurations.length) {
                throw new ParcelFormatException("more step durations than available: " + N);
            }
            this.mNumStepDurations = N;
            for (int i = 0; i < N; i++) {
                this.mStepDurations[i] = in.readLong();
            }
        }

        public void writeToParcel(Parcel out) {
            int N = this.mNumStepDurations;
            out.writeInt(N);
            for (int i = 0; i < N; i++) {
                out.writeLong(this.mStepDurations[i]);
            }
        }
    }

    public static final class HistoryTag {
        public static final int HISTORY_TAG_POOL_OVERFLOW = -1;
        public int poolIdx;
        public String string;
        public int uid;

        public void setTo(HistoryTag o) {
            this.string = o.string;
            this.uid = o.uid;
            this.poolIdx = o.poolIdx;
        }

        public void setTo(String _string, int _uid) {
            this.string = _string;
            this.uid = _uid;
            this.poolIdx = -1;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.string);
            dest.writeInt(this.uid);
        }

        public void readFromParcel(Parcel src) {
            this.string = src.readString();
            this.uid = src.readInt();
            this.poolIdx = -1;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            HistoryTag that = (HistoryTag) o;
            if (this.uid == that.uid && this.string.equals(that.string)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            int result = this.string.hashCode();
            return (result * 31) + this.uid;
        }
    }

    public static final class HistoryStepDetails {
        public int appCpuSTime1;
        public int appCpuSTime2;
        public int appCpuSTime3;
        public int appCpuUTime1;
        public int appCpuUTime2;
        public int appCpuUTime3;
        public int appCpuUid1;
        public int appCpuUid2;
        public int appCpuUid3;
        public int statIOWaitTime;
        public int statIdlTime;
        public int statIrqTime;
        public int statSoftIrqTime;
        public String statSubsystemPowerState;
        public int statSystemTime;
        public int statUserTime;
        public int systemTime;
        public int userTime;

        public HistoryStepDetails() {
            clear();
        }

        public void clear() {
            this.systemTime = 0;
            this.userTime = 0;
            this.appCpuUid3 = -1;
            this.appCpuUid2 = -1;
            this.appCpuUid1 = -1;
            this.appCpuSTime3 = 0;
            this.appCpuUTime3 = 0;
            this.appCpuSTime2 = 0;
            this.appCpuUTime2 = 0;
            this.appCpuSTime1 = 0;
            this.appCpuUTime1 = 0;
        }

        public void writeToParcel(Parcel out) {
            out.writeInt(this.userTime);
            out.writeInt(this.systemTime);
            out.writeInt(this.appCpuUid1);
            out.writeInt(this.appCpuUTime1);
            out.writeInt(this.appCpuSTime1);
            out.writeInt(this.appCpuUid2);
            out.writeInt(this.appCpuUTime2);
            out.writeInt(this.appCpuSTime2);
            out.writeInt(this.appCpuUid3);
            out.writeInt(this.appCpuUTime3);
            out.writeInt(this.appCpuSTime3);
            out.writeInt(this.statUserTime);
            out.writeInt(this.statSystemTime);
            out.writeInt(this.statIOWaitTime);
            out.writeInt(this.statIrqTime);
            out.writeInt(this.statSoftIrqTime);
            out.writeInt(this.statIdlTime);
            out.writeString(this.statSubsystemPowerState);
        }

        public void readFromParcel(Parcel in) {
            this.userTime = in.readInt();
            this.systemTime = in.readInt();
            this.appCpuUid1 = in.readInt();
            this.appCpuUTime1 = in.readInt();
            this.appCpuSTime1 = in.readInt();
            this.appCpuUid2 = in.readInt();
            this.appCpuUTime2 = in.readInt();
            this.appCpuSTime2 = in.readInt();
            this.appCpuUid3 = in.readInt();
            this.appCpuUTime3 = in.readInt();
            this.appCpuSTime3 = in.readInt();
            this.statUserTime = in.readInt();
            this.statSystemTime = in.readInt();
            this.statIOWaitTime = in.readInt();
            this.statIrqTime = in.readInt();
            this.statSoftIrqTime = in.readInt();
            this.statIdlTime = in.readInt();
            this.statSubsystemPowerState = in.readString();
        }
    }

    public static final class ProcessStateChange {
        private static final int LARGE_UID_FLAG = Integer.MIN_VALUE;
        private static final int PROC_STATE_MASK = 2130706432;
        private static final int PROC_STATE_SHIFT = Integer.numberOfTrailingZeros(PROC_STATE_MASK);
        private static final int SMALL_UID_MASK = 16777215;
        public int processState;
        public int uid;

        public void writeToParcel(Parcel out) {
            int bits = this.processState << PROC_STATE_SHIFT;
            if ((this.uid & (-16777216)) == 0) {
                out.writeInt(bits | this.uid);
            } else {
                out.writeInt(bits | Integer.MIN_VALUE);
                out.writeInt(this.uid);
            }
        }

        public void readFromParcel(Parcel in) {
            int bits = in.readInt();
            this.processState = (PROC_STATE_MASK & bits) >>> PROC_STATE_SHIFT;
            if (this.processState >= 5) {
                Slog.e(
                        BatteryStats.TAG,
                        "Unrecognized proc state in battery history: " + this.processState);
                this.processState = 0;
            }
            if ((Integer.MIN_VALUE & bits) == 0) {
                this.uid = (-2130706433) & bits;
            } else {
                this.uid = in.readInt();
            }
        }

        public String formatForBatteryHistory() {
            return UserHandle.formatUid(this.uid)
                    + ": "
                    + BatteryConsumer.processStateToString(this.processState);
        }
    }

    public static final class HistoryItem {
        public static final byte CMD_CURRENT_TIME = 5;
        public static final byte CMD_NULL = -1;
        public static final byte CMD_OVERFLOW = 6;
        public static final byte CMD_RESET = 7;
        public static final byte CMD_SHUTDOWN = 8;
        public static final byte CMD_START = 4;
        public static final byte CMD_UPDATE = 0;
        public static final int EVENT_ACTIVE = 10;
        public static final int EVENT_ALARM = 13;
        public static final int EVENT_ALARM_FINISH = 16397;
        public static final int EVENT_ALARM_START = 32781;
        public static final int EVENT_COLLECT_EXTERNAL_STATS = 14;
        public static final int EVENT_CONNECTIVITY_CHANGED = 9;
        public static final int EVENT_COUNT = 22;
        public static final int EVENT_FLAG_FINISH = 16384;
        public static final int EVENT_FLAG_START = 32768;
        public static final int EVENT_FOREGROUND = 2;
        public static final int EVENT_FOREGROUND_FINISH = 16386;
        public static final int EVENT_FOREGROUND_START = 32770;
        public static final int EVENT_JOB = 6;
        public static final int EVENT_JOB_FINISH = 16390;
        public static final int EVENT_JOB_START = 32774;
        public static final int EVENT_LONG_WAKE_LOCK = 20;
        public static final int EVENT_LONG_WAKE_LOCK_FINISH = 16404;
        public static final int EVENT_LONG_WAKE_LOCK_START = 32788;
        public static final int EVENT_NONE = 0;
        public static final int EVENT_PACKAGE_ACTIVE = 16;
        public static final int EVENT_PACKAGE_INACTIVE = 15;
        public static final int EVENT_PACKAGE_INSTALLED = 11;
        public static final int EVENT_PACKAGE_UNINSTALLED = 12;
        public static final int EVENT_PROC = 1;
        public static final int EVENT_PROC_FINISH = 16385;
        public static final int EVENT_PROC_START = 32769;
        public static final int EVENT_SCREEN_WAKE_UP = 18;
        public static final int EVENT_STATE_CHANGE = 21;
        public static final int EVENT_SYNC = 4;
        public static final int EVENT_SYNC_FINISH = 16388;
        public static final int EVENT_SYNC_START = 32772;
        public static final int EVENT_TEMP_WHITELIST = 17;
        public static final int EVENT_TEMP_WHITELIST_FINISH = 16401;
        public static final int EVENT_TEMP_WHITELIST_START = 32785;
        public static final int EVENT_TOP = 3;
        public static final int EVENT_TOP_FINISH = 16387;
        public static final int EVENT_TOP_START = 32771;
        public static final int EVENT_TYPE_MASK = -49153;
        public static final int EVENT_USER_FOREGROUND = 8;
        public static final int EVENT_USER_FOREGROUND_FINISH = 16392;
        public static final int EVENT_USER_FOREGROUND_START = 32776;
        public static final int EVENT_USER_RUNNING = 7;
        public static final int EVENT_USER_RUNNING_FINISH = 16391;
        public static final int EVENT_USER_RUNNING_START = 32775;
        public static final int EVENT_WAKEUP_AP = 19;
        public static final int EVENT_WAKE_LOCK = 5;
        public static final int EVENT_WAKE_LOCK_FINISH = 16389;
        public static final int EVENT_WAKE_LOCK_START = 32773;
        public static final int IMPORTANT_FOR_POWER_STATS_STATES = 549453824;
        public static final int IMPORTANT_FOR_POWER_STATS_STATES2 = 1210057088;
        public static final int MOST_INTERESTING_STATES = 1835008;
        public static final int MOST_INTERESTING_STATES2 = -1749024768;
        public static final int SETTLE_TO_ZERO_STATES = -1900544;
        public static final int SETTLE_TO_ZERO_STATES2 = 1748959232;
        public static final int STATE2_BLUETOOTH_ON_FLAG = 4194304;
        public static final int STATE2_BLUETOOTH_SCAN_FLAG = 1048576;
        public static final int STATE2_CAMERA_FLAG = 2097152;
        public static final int STATE2_CELLULAR_HIGH_TX_POWER_FLAG = 524288;
        public static final int STATE2_CHARGING_FLAG = 16777216;
        public static final int STATE2_DEVICE_IDLE_MASK = 100663296;
        public static final int STATE2_DEVICE_IDLE_SHIFT = 25;
        public static final int STATE2_EXTENSIONS_FLAG = 131072;
        public static final int STATE2_FLASHLIGHT_FLAG = 134217728;
        public static final int STATE2_GPS_SIGNAL_QUALITY_MASK = 384;
        public static final int STATE2_GPS_SIGNAL_QUALITY_SHIFT = 7;
        public static final int STATE2_NR_STATE_MASK = 1536;
        public static final int STATE2_NR_STATE_SHIFT = 9;
        public static final int STATE2_PHONE_IN_CALL_FLAG = 8388608;
        public static final int STATE2_POWER_SAVE_FLAG = Integer.MIN_VALUE;
        public static final int STATE2_USB_DATA_LINK_FLAG = 262144;
        public static final int STATE2_VIDEO_ON_FLAG = 1073741824;
        public static final int STATE2_WIFI_ON_FLAG = 268435456;
        public static final int STATE2_WIFI_RUNNING_FLAG = 536870912;
        public static final int STATE2_WIFI_SIGNAL_STRENGTH_MASK = 112;
        public static final int STATE2_WIFI_SIGNAL_STRENGTH_SHIFT = 4;
        public static final int STATE2_WIFI_SUPPL_STATE_MASK = 15;
        public static final int STATE2_WIFI_SUPPL_STATE_SHIFT = 0;
        public static final int STATE_AUDIO_ON_FLAG = 4194304;
        public static final int STATE_BATTERY_PLUGGED_FLAG = 524288;
        public static final int STATE_BRIGHTNESS_MASK = 7;
        public static final int STATE_BRIGHTNESS_SHIFT = 0;
        public static final int STATE_CPU_RUNNING_FLAG = Integer.MIN_VALUE;
        public static final int STATE_DATA_CONNECTION_MASK = 15872;
        public static final int STATE_DATA_CONNECTION_SHIFT = 9;
        public static final int STATE_GPS_ON_FLAG = 536870912;
        public static final int STATE_MOBILE_RADIO_ACTIVE_FLAG = 33554432;
        public static final int STATE_PHONE_SCANNING_FLAG = 2097152;
        public static final int STATE_PHONE_SIGNAL_STRENGTH_MASK = 56;
        public static final int STATE_PHONE_SIGNAL_STRENGTH_SHIFT = 3;
        public static final int STATE_PHONE_STATE_MASK = 448;
        public static final int STATE_PHONE_STATE_SHIFT = 6;
        private static final int STATE_RESERVED_0 = 16777216;
        public static final int STATE_SCREEN_DOZE_FLAG = 262144;
        public static final int STATE_SCREEN_ON_FLAG = 1048576;
        public static final int STATE_SENSOR_ON_FLAG = 8388608;
        public static final int STATE_WAKE_LOCK_FLAG = 1073741824;
        public static final int STATE_WIFI_FULL_LOCK_FLAG = 268435456;
        public static final int STATE_WIFI_MULTICAST_ON_FLAG = 65536;
        public static final int STATE_WIFI_RADIO_ACTIVE_FLAG = 67108864;
        public static final int STATE_WIFI_SCAN_FLAG = 134217728;
        public byte ap_temp;
        public int batteryChargeUah;
        public byte batteryHealth;
        public byte batteryLevel;
        public byte batteryPlugType;
        public int batterySecCurrentEvent;
        public int batterySecEvent;
        public byte batterySecOnline;
        public int batterySecTxShareEvent;
        public byte batteryStatus;
        public short batteryTemperature;
        public short batteryVoltage;
        public byte cmd;
        public short current;
        public long currentTime;
        public int eventCode;
        public HistoryTag eventTag;
        public byte highSpeakerVolume;
        public final HistoryTag localEventTag;
        public final ProcessStateChange localProcessStateChange;
        public final HistoryTag localWakeReasonTag;
        public final HistoryTag localWakelockTag;
        public double modemRailChargeMah;
        public HistoryItem next;
        public int numReadInts;
        public byte otgOnline;
        public byte pa_temp;
        public PowerStats powerStats;
        public ProcessStateChange processStateChange;
        public int protectBatteryMode;
        public byte skin_temp;
        public int states;
        public int states2;
        public HistoryStepDetails stepDetails;
        public byte subScreenDoze;
        public byte subScreenOn;
        public byte sub_batt_temp;
        public boolean tagsFirstOccurrence;
        public long time;
        public HistoryTag wakeReasonTag;
        public HistoryTag wakelockTag;
        public double wifiRailChargeMah;
        public byte wifi_ap;

        public boolean isDeltaData() {
            return this.cmd == 0;
        }

        public HistoryItem() {
            this.cmd = (byte) -1;
            this.localWakelockTag = new HistoryTag();
            this.localWakeReasonTag = new HistoryTag();
            this.localEventTag = new HistoryTag();
            this.localProcessStateChange = new ProcessStateChange();
            this.ap_temp = Byte.MIN_VALUE;
            this.pa_temp = Byte.MIN_VALUE;
            this.skin_temp = Byte.MIN_VALUE;
            this.sub_batt_temp = Byte.MIN_VALUE;
            this.protectBatteryMode = -999;
        }

        public HistoryItem(Parcel src) {
            this.cmd = (byte) -1;
            this.localWakelockTag = new HistoryTag();
            this.localWakeReasonTag = new HistoryTag();
            this.localEventTag = new HistoryTag();
            this.localProcessStateChange = new ProcessStateChange();
            readFromParcel(src);
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.time);
            int bat =
                    (this.cmd & 255)
                            | ((this.batteryLevel << 8) & 65280)
                            | ((this.batteryStatus << 16) & 983040)
                            | ((this.batteryHealth << 20) & SurfaceControl.NO_REMOTECONTROL)
                            | ((this.batteryPlugType << 24) & 251658240)
                            | (this.wakelockTag != null ? 268435456 : 0)
                            | (this.wakeReasonTag != null ? 536870912 : 0)
                            | (this.eventCode != 0 ? 1073741824 : 0);
            dest.writeInt(bat);
            int bat2 = (this.batteryTemperature & 65535) | ((this.batteryVoltage << 16) & (-65536));
            dest.writeInt(bat2);
            int bat3 =
                    (this.current & 65535)
                            | ((this.ap_temp << 16) & Spanned.SPAN_PRIORITY)
                            | ((this.pa_temp << 24) & (-16777216));
            dest.writeInt(bat3);
            int bat4 =
                    ((this.sub_batt_temp << 8) & 65280)
                            | ((this.skin_temp << 16) & Spanned.SPAN_PRIORITY)
                            | ((this.wifi_ap << 25) & 33554432)
                            | ((this.otgOnline << 26) & 67108864)
                            | ((this.highSpeakerVolume << 27) & 134217728)
                            | ((this.subScreenOn << SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN)
                                    & 268435456)
                            | ((this.subScreenDoze << SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEOUT)
                                    & 536870912);
            dest.writeInt(bat4);
            int bat5 =
                    (this.batterySecTxShareEvent & 16777215)
                            | ((this.batterySecOnline << 24) & (-16777216));
            dest.writeInt(bat5);
            dest.writeInt(this.batterySecCurrentEvent);
            dest.writeInt(this.batterySecEvent);
            dest.writeInt(this.protectBatteryMode);
            dest.writeInt(this.batteryChargeUah);
            dest.writeDouble(this.modemRailChargeMah);
            dest.writeDouble(this.wifiRailChargeMah);
            dest.writeInt(this.states);
            dest.writeInt(this.states2);
            if (this.wakelockTag != null) {
                this.wakelockTag.writeToParcel(dest, flags);
            }
            if (this.wakeReasonTag != null) {
                this.wakeReasonTag.writeToParcel(dest, flags);
            }
            if (this.eventCode != 0) {
                dest.writeInt(this.eventCode);
                this.eventTag.writeToParcel(dest, flags);
            }
            if (this.cmd == 5 || this.cmd == 7) {
                dest.writeLong(this.currentTime);
            }
        }

        public void readFromParcel(Parcel src) {
            int start = src.dataPosition();
            this.time = src.readLong();
            int bat = src.readInt();
            this.cmd = (byte) (bat & 255);
            this.batteryLevel = (byte) ((bat >> 8) & 255);
            this.batteryStatus = (byte) ((bat >> 16) & 15);
            this.batteryHealth = (byte) ((bat >> 20) & 15);
            this.batteryPlugType = (byte) ((bat >> 24) & 15);
            int bat2 = src.readInt();
            this.batteryTemperature = (short) (bat2 & 65535);
            this.batteryVoltage = (short) ((bat2 >> 16) & 65535);
            int bat3 = src.readInt();
            this.current = (short) (65535 & bat3);
            this.ap_temp = (byte) ((bat3 >> 16) & 255);
            this.pa_temp = (byte) ((bat3 >> 24) & 255);
            int bat4 = src.readInt();
            this.sub_batt_temp = (byte) ((bat4 >> 8) & 255);
            this.skin_temp = (byte) ((bat4 >> 16) & 255);
            this.wifi_ap = (byte) ((bat4 >> 25) & 1);
            this.otgOnline = (byte) ((bat4 >> 26) & 1);
            this.highSpeakerVolume = (byte) ((bat4 >> 27) & 1);
            this.subScreenOn = (byte) ((bat4 >> 28) & 1);
            this.subScreenDoze = (byte) ((bat4 >> 29) & 1);
            int bat5 = src.readInt();
            this.batterySecTxShareEvent = 16777215 & bat5;
            this.batterySecOnline = (byte) ((bat5 >> 24) & 255);
            this.batterySecCurrentEvent = src.readInt();
            this.batterySecEvent = src.readInt();
            this.protectBatteryMode = src.readInt();
            this.batteryChargeUah = src.readInt();
            this.modemRailChargeMah = src.readDouble();
            this.wifiRailChargeMah = src.readDouble();
            this.states = src.readInt();
            this.states2 = src.readInt();
            if ((268435456 & bat) != 0) {
                this.wakelockTag = this.localWakelockTag;
                this.wakelockTag.readFromParcel(src);
            } else {
                this.wakelockTag = null;
            }
            if ((536870912 & bat) != 0) {
                this.wakeReasonTag = this.localWakeReasonTag;
                this.wakeReasonTag.readFromParcel(src);
            } else {
                this.wakeReasonTag = null;
            }
            if ((1073741824 & bat) != 0) {
                this.eventCode = src.readInt();
                this.eventTag = this.localEventTag;
                this.eventTag.readFromParcel(src);
            } else {
                this.eventCode = 0;
                this.eventTag = null;
            }
            if (this.cmd == 5 || this.cmd == 7) {
                this.currentTime = src.readLong();
            } else {
                this.currentTime = 0L;
            }
            this.numReadInts += (src.dataPosition() - start) / 4;
        }

        public void clear() {
            this.time = 0L;
            this.cmd = (byte) -1;
            this.batteryLevel = (byte) 0;
            this.batteryStatus = (byte) 0;
            this.batteryHealth = (byte) 0;
            this.batteryPlugType = (byte) 0;
            this.batteryTemperature = (short) 0;
            this.batteryVoltage = (short) 0;
            this.current = (short) 0;
            this.ap_temp = Byte.MIN_VALUE;
            this.pa_temp = Byte.MIN_VALUE;
            this.sub_batt_temp = Byte.MIN_VALUE;
            this.skin_temp = Byte.MIN_VALUE;
            this.wifi_ap = (byte) 0;
            this.otgOnline = (byte) 0;
            this.highSpeakerVolume = (byte) 0;
            this.subScreenOn = (byte) 0;
            this.subScreenDoze = (byte) 0;
            this.batterySecTxShareEvent = 0;
            this.batterySecOnline = (byte) 0;
            this.batterySecCurrentEvent = 0;
            this.batterySecEvent = 0;
            this.protectBatteryMode = -999;
            this.batteryChargeUah = 0;
            this.modemRailChargeMah = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            this.wifiRailChargeMah = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            this.states = 0;
            this.states2 = 0;
            this.wakelockTag = null;
            this.wakeReasonTag = null;
            this.eventCode = 0;
            this.eventTag = null;
            this.tagsFirstOccurrence = false;
            this.powerStats = null;
            this.processStateChange = null;
        }

        public void setTo(HistoryItem o) {
            this.time = o.time;
            this.cmd = o.cmd;
            setToCommon(o);
        }

        public void setTo(long time, byte cmd, HistoryItem o) {
            this.time = time;
            this.cmd = cmd;
            setToCommon(o);
        }

        private void setToCommon(HistoryItem o) {
            this.batteryLevel = o.batteryLevel;
            this.batteryStatus = o.batteryStatus;
            this.batteryHealth = o.batteryHealth;
            this.batteryPlugType = o.batteryPlugType;
            this.batteryTemperature = o.batteryTemperature;
            this.batteryVoltage = o.batteryVoltage;
            this.current = o.current;
            this.ap_temp = o.ap_temp;
            this.pa_temp = o.pa_temp;
            this.sub_batt_temp = o.sub_batt_temp;
            this.skin_temp = o.skin_temp;
            this.wifi_ap = o.wifi_ap;
            this.otgOnline = o.otgOnline;
            this.highSpeakerVolume = o.highSpeakerVolume;
            this.subScreenOn = o.subScreenOn;
            this.subScreenDoze = o.subScreenDoze;
            this.batterySecTxShareEvent = o.batterySecTxShareEvent;
            this.batterySecOnline = o.batterySecOnline;
            this.batterySecCurrentEvent = o.batterySecCurrentEvent;
            this.batterySecEvent = o.batterySecEvent;
            this.protectBatteryMode = o.protectBatteryMode;
            this.batteryChargeUah = o.batteryChargeUah;
            this.modemRailChargeMah = o.modemRailChargeMah;
            this.wifiRailChargeMah = o.wifiRailChargeMah;
            this.states = o.states;
            this.states2 = o.states2;
            if (o.wakelockTag != null) {
                this.wakelockTag = this.localWakelockTag;
                this.wakelockTag.setTo(o.wakelockTag);
            } else {
                this.wakelockTag = null;
            }
            if (o.wakeReasonTag != null) {
                this.wakeReasonTag = this.localWakeReasonTag;
                this.wakeReasonTag.setTo(o.wakeReasonTag);
            } else {
                this.wakeReasonTag = null;
            }
            this.eventCode = o.eventCode;
            if (o.eventTag != null) {
                this.eventTag = this.localEventTag;
                this.eventTag.setTo(o.eventTag);
            } else {
                this.eventTag = null;
            }
            this.tagsFirstOccurrence = o.tagsFirstOccurrence;
            this.currentTime = o.currentTime;
            this.powerStats = o.powerStats;
            this.processStateChange = o.processStateChange;
        }

        public boolean sameNonEvent(HistoryItem o) {
            return this.batteryLevel == o.batteryLevel
                    && this.batteryStatus == o.batteryStatus
                    && this.batteryHealth == o.batteryHealth
                    && this.batteryPlugType == o.batteryPlugType
                    && this.batteryTemperature == o.batteryTemperature
                    && this.batteryVoltage == o.batteryVoltage
                    && this.current == o.current
                    && this.ap_temp == o.ap_temp
                    && this.pa_temp == o.pa_temp
                    && this.sub_batt_temp == o.sub_batt_temp
                    && this.skin_temp == o.skin_temp
                    && this.wifi_ap == o.wifi_ap
                    && this.otgOnline == o.otgOnline
                    && this.highSpeakerVolume == o.highSpeakerVolume
                    && this.subScreenOn == o.subScreenOn
                    && this.subScreenDoze == o.subScreenDoze
                    && this.batterySecTxShareEvent == o.batterySecTxShareEvent
                    && this.batterySecOnline == o.batterySecOnline
                    && this.batterySecCurrentEvent == o.batterySecCurrentEvent
                    && this.batterySecEvent == o.batterySecEvent
                    && this.protectBatteryMode == o.protectBatteryMode
                    && this.batteryChargeUah == o.batteryChargeUah
                    && this.modemRailChargeMah == o.modemRailChargeMah
                    && this.wifiRailChargeMah == o.wifiRailChargeMah
                    && this.states == o.states
                    && this.states2 == o.states2
                    && this.currentTime == o.currentTime;
        }

        public boolean same(HistoryItem o) {
            if (!sameNonEvent(o) || this.eventCode != o.eventCode) {
                return false;
            }
            if (this.wakelockTag != o.wakelockTag
                    && (this.wakelockTag == null
                            || o.wakelockTag == null
                            || !this.wakelockTag.equals(o.wakelockTag))) {
                return false;
            }
            if (this.wakeReasonTag != o.wakeReasonTag
                    && (this.wakeReasonTag == null
                            || o.wakeReasonTag == null
                            || !this.wakeReasonTag.equals(o.wakeReasonTag))) {
                return false;
            }
            if (this.eventTag != o.eventTag) {
                return (this.eventTag == null
                                || o.eventTag == null
                                || !this.eventTag.equals(o.eventTag))
                        ? false
                        : true;
            }
            return true;
        }
    }

    public static final class HistoryEventTracker {
        private final HashMap<String, SparseIntArray>[] mActiveEvents = new HashMap[22];

        public boolean updateState(int code, String name, int uid, int poolIdx) {
            SparseIntArray uids;
            int idx;
            if ((32768 & code) != 0) {
                int idx2 = code & HistoryItem.EVENT_TYPE_MASK;
                HashMap<String, SparseIntArray> active = this.mActiveEvents[idx2];
                if (active == null) {
                    active = new HashMap<>();
                    this.mActiveEvents[idx2] = active;
                }
                SparseIntArray uids2 = active.get(name);
                if (uids2 == null) {
                    uids2 = new SparseIntArray();
                    active.put(name, uids2);
                }
                if (uids2.indexOfKey(uid) >= 0) {
                    return false;
                }
                uids2.put(uid, poolIdx);
                return true;
            }
            if ((code & 16384) != 0) {
                HashMap<String, SparseIntArray> active2 =
                        this.mActiveEvents[code & HistoryItem.EVENT_TYPE_MASK];
                if (active2 == null
                        || (uids = active2.get(name)) == null
                        || (idx = uids.indexOfKey(uid)) < 0) {
                    return false;
                }
                uids.removeAt(idx);
                if (uids.size() <= 0) {
                    active2.remove(name);
                    return true;
                }
                return true;
            }
            return true;
        }

        public void removeEvents(int code) {
            int idx = (-49153) & code;
            this.mActiveEvents[idx] = null;
        }

        public HashMap<String, SparseIntArray> getStateForEvent(int code) {
            return this.mActiveEvents[code];
        }
    }

    public static final class BitDescription {
        public final int mask;
        public final String name;
        public final int shift;
        public final String shortName;
        public final String[] shortValues;
        public final String[] values;

        public BitDescription(int mask, String name, String shortName) {
            this.mask = mask;
            this.shift = -1;
            this.name = name;
            this.shortName = shortName;
            this.values = null;
            this.shortValues = null;
        }

        public BitDescription(
                int mask,
                int shift,
                String name,
                String shortName,
                String[] values,
                String[] shortValues) {
            this.mask = mask;
            this.shift = shift;
            this.name = name;
            this.shortName = shortName;
            this.values = values;
            this.shortValues = shortValues;
        }
    }

    public static int getAllNetworkTypesCount() {
        int count = TelephonyManager.getAllNetworkTypes().length;
        if (DATA_CONNECTION_NAMES.length != count + 3) {
            throw new IllegalStateException(
                    "DATA_CONNECTION_NAMES length does not match network type count. Expected: "
                            + (count + 3)
                            + ", actual:"
                            + DATA_CONNECTION_NAMES.length);
        }
        return count;
    }

    public static int getAllNetworkTypesCount$ravenwood() {
        return DATA_CONNECTION_NAMES.length - 3;
    }

    private static final void formatTimeRaw(StringBuilder out, long seconds) {
        long days = seconds / 86400;
        if (days != 0) {
            out.append(days);
            out.append("d ");
        }
        long used = days * 60 * 60 * 24;
        long hours = (seconds - used) / 3600;
        if (hours != 0 || used != 0) {
            out.append(hours);
            out.append("h ");
        }
        long used2 = used + (hours * 60 * 60);
        long mins = (seconds - used2) / 60;
        if (mins != 0 || used2 != 0) {
            out.append(mins);
            out.append("m ");
        }
        long used3 = used2 + (60 * mins);
        if (seconds != 0 || used3 != 0) {
            out.append(seconds - used3);
            out.append("s ");
        }
    }

    public static final void formatTimeMs(StringBuilder sb, long time) {
        long sec = time / 1000;
        formatTimeRaw(sb, sec);
        sb.append(time - (1000 * sec));
        sb.append("ms ");
    }

    public static final void formatTimeMsNoSpace(StringBuilder sb, long time) {
        long sec = time / 1000;
        formatTimeRaw(sb, sec);
        sb.append(time - (1000 * sec));
        sb.append("ms");
    }

    public final String formatRatioLocked(long num, long den) {
        if (den == 0) {
            return "--%";
        }
        float perc = (num / den) * 100.0f;
        this.mFormatBuilder.setLength(0);
        this.mFormatter.format("%.1f%%", Float.valueOf(perc));
        return this.mFormatBuilder.toString();
    }

    final String formatBytesLocked(long bytes) {
        this.mFormatBuilder.setLength(0);
        if (bytes < 1024) {
            return bytes + GnssSignalType.CODE_TYPE_B;
        }
        if (bytes < 1048576) {
            this.mFormatter.format("%.2fKB", Double.valueOf(bytes / 1024.0d));
            return this.mFormatBuilder.toString();
        }
        if (bytes < 1073741824) {
            this.mFormatter.format("%.2fMB", Double.valueOf(bytes / 1048576.0d));
            return this.mFormatBuilder.toString();
        }
        this.mFormatter.format("%.2fGB", Double.valueOf(bytes / 1.073741824E9d));
        return this.mFormatBuilder.toString();
    }

    public static String formatCharge(double power) {
        return formatValue(power);
    }

    private static String formatValue(double value) {
        String format;
        if (value == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            return "0";
        }
        if (value < 1.0E-5d) {
            format = "%.8f";
        } else if (value < 1.0E-4d) {
            format = "%.7f";
        } else if (value < 0.001d) {
            format = "%.6f";
        } else if (value < 0.01d) {
            format = "%.5f";
        } else if (value < 0.1d) {
            format = "%.4f";
        } else if (value < 1.0d) {
            format = "%.3f";
        } else if (value < 10.0d) {
            format = "%.2f";
        } else if (value < 100.0d) {
            format = "%.1f";
        } else {
            format = "%.0f";
        }
        return String.format(Locale.ENGLISH, format, Double.valueOf(value));
    }

    private static long roundUsToMs(long timeUs) {
        return (500 + timeUs) / 1000;
    }

    private static long computeWakeLock(Timer timer, long elapsedRealtimeUs, int which) {
        if (timer != null) {
            long totalTimeMicros = timer.getTotalTimeLocked(elapsedRealtimeUs, which);
            long totalTimeMillis = (500 + totalTimeMicros) / 1000;
            return totalTimeMillis;
        }
        return 0L;
    }

    private static final String printWakeLock(
            StringBuilder sb,
            Timer timer,
            long elapsedRealtimeUs,
            String name,
            int which,
            String linePrefix) {
        if (timer != null) {
            long totalTimeMillis = computeWakeLock(timer, elapsedRealtimeUs, which);
            int count = timer.getCountLocked(which);
            if (totalTimeMillis != 0) {
                sb.append(linePrefix);
                formatTimeMs(sb, totalTimeMillis);
                if (name != null) {
                    sb.append(name);
                    sb.append(' ');
                }
                sb.append('(');
                sb.append(count);
                sb.append(" times)");
                long maxDurationMs = timer.getMaxDurationMsLocked(elapsedRealtimeUs / 1000);
                if (maxDurationMs >= 0) {
                    sb.append(" max=");
                    sb.append(maxDurationMs);
                }
                long totalDurMs = timer.getTotalDurationMsLocked(elapsedRealtimeUs / 1000);
                if (totalDurMs > totalTimeMillis) {
                    sb.append(" actual=");
                    sb.append(totalDurMs);
                }
                if (timer.isRunningLocked()) {
                    long currentMs = timer.getCurrentDurationMsLocked(elapsedRealtimeUs / 1000);
                    if (currentMs >= 0) {
                        sb.append(" (running for ");
                        sb.append(currentMs);
                        sb.append("ms)");
                        return ", ";
                    }
                    sb.append(" (running)");
                    return ", ";
                }
                return ", ";
            }
        }
        return linePrefix;
    }

    private static final boolean printTimer(
            PrintWriter pw,
            StringBuilder sb,
            Timer timer,
            long rawRealtimeUs,
            int which,
            String prefix,
            String type) {
        if (timer != null) {
            long totalTimeMs = (timer.getTotalTimeLocked(rawRealtimeUs, which) + 500) / 1000;
            int count = timer.getCountLocked(which);
            if (totalTimeMs != 0) {
                sb.setLength(0);
                sb.append(prefix);
                sb.append("    ");
                sb.append(type);
                sb.append(": ");
                formatTimeMs(sb, totalTimeMs);
                sb.append("realtime (");
                sb.append(count);
                sb.append(" times)");
                long maxDurationMs = timer.getMaxDurationMsLocked(rawRealtimeUs / 1000);
                if (maxDurationMs >= 0) {
                    sb.append(" max=");
                    sb.append(maxDurationMs);
                }
                if (timer.isRunningLocked()) {
                    long currentMs = timer.getCurrentDurationMsLocked(rawRealtimeUs / 1000);
                    if (currentMs >= 0) {
                        sb.append(" (running for ");
                        sb.append(currentMs);
                        sb.append("ms)");
                    } else {
                        sb.append(" (running)");
                    }
                }
                pw.println(sb.toString());
                return true;
            }
        }
        return false;
    }

    private static final String printWakeLockCheckin(
            StringBuilder sb,
            Timer timer,
            long elapsedRealtimeUs,
            String name,
            int which,
            String linePrefix) {
        long totalTimeMicros = 0;
        int count = 0;
        long max = 0;
        long current = 0;
        long totalDuration = 0;
        if (timer != null) {
            long totalTimeMicros2 = timer.getTotalTimeLocked(elapsedRealtimeUs, which);
            count = timer.getCountLocked(which);
            current = timer.getCurrentDurationMsLocked(elapsedRealtimeUs / 1000);
            max = timer.getMaxDurationMsLocked(elapsedRealtimeUs / 1000);
            totalDuration = timer.getTotalDurationMsLocked(elapsedRealtimeUs / 1000);
            totalTimeMicros = totalTimeMicros2;
        }
        sb.append(linePrefix);
        sb.append((totalTimeMicros + 500) / 1000);
        sb.append(',');
        sb.append(name != null ? name + "," : "");
        sb.append(count);
        sb.append(',');
        sb.append(current);
        sb.append(',');
        sb.append(max);
        if (name != null) {
            sb.append(',');
            sb.append(totalDuration);
        }
        return ",";
    }

    private static final void dumpLineHeader(
            PrintWriter pw, int uid, String category, String type) {
        pw.print(9);
        pw.print(',');
        pw.print(uid);
        pw.print(',');
        pw.print(category);
        pw.print(',');
        pw.print(type);
    }

    private static final void dumpLine(
            PrintWriter pw, int uid, String category, String type, Object... args) {
        dumpLineHeader(pw, uid, category, type);
        for (Object arg : args) {
            pw.print(',');
            pw.print(arg);
        }
        pw.println();
    }

    private static final void dumpTimer(
            PrintWriter pw,
            int uid,
            String category,
            String type,
            Timer timer,
            long rawRealtime,
            int which) {
        if (timer != null) {
            long totalTime = roundUsToMs(timer.getTotalTimeLocked(rawRealtime, which));
            int count = timer.getCountLocked(which);
            if (totalTime != 0 || count != 0) {
                dumpLine(pw, uid, category, type, Long.valueOf(totalTime), Integer.valueOf(count));
            }
        }
    }

    private static void dumpTimer(
            ProtoOutputStream proto, long fieldId, Timer timer, long rawRealtimeUs, int which) {
        if (timer == null) {
            return;
        }
        long timeMs = roundUsToMs(timer.getTotalTimeLocked(rawRealtimeUs, which));
        int count = timer.getCountLocked(which);
        long maxDurationMs = timer.getMaxDurationMsLocked(rawRealtimeUs / 1000);
        long curDurationMs = timer.getCurrentDurationMsLocked(rawRealtimeUs / 1000);
        long totalDurationMs = timer.getTotalDurationMsLocked(rawRealtimeUs / 1000);
        if (timeMs != 0
                || count != 0
                || maxDurationMs != -1
                || curDurationMs != -1
                || totalDurationMs != -1) {
            long token = proto.start(fieldId);
            proto.write(1112396529665L, timeMs);
            proto.write(1112396529666L, count);
            if (maxDurationMs != -1) {
                proto.write(1112396529667L, maxDurationMs);
            }
            if (curDurationMs != -1) {
                proto.write(1112396529668L, curDurationMs);
            }
            if (totalDurationMs != -1) {
                proto.write(1112396529669L, totalDurationMs);
            }
            proto.end(token);
        }
    }

    private static boolean controllerActivityHasData(ControllerActivityCounter counter, int which) {
        if (counter == null) {
            return false;
        }
        if (counter.getIdleTimeCounter().getCountLocked(which) != 0
                || counter.getRxTimeCounter().getCountLocked(which) != 0
                || counter.getPowerCounter().getCountLocked(which) != 0
                || counter.getMonitoredRailChargeConsumedMaMs().getCountLocked(which) != 0) {
            return true;
        }
        for (LongCounter c : counter.getTxTimeCounters()) {
            if (c.getCountLocked(which) != 0) {
                return true;
            }
        }
        return false;
    }

    private static final void dumpControllerActivityLine(
            PrintWriter pw,
            int uid,
            String category,
            String type,
            ControllerActivityCounter counter,
            int which) {
        if (!controllerActivityHasData(counter, which)) {
            return;
        }
        dumpLineHeader(pw, uid, category, type);
        pw.print(",");
        pw.print(counter.getIdleTimeCounter().getCountLocked(which));
        pw.print(",");
        pw.print(counter.getRxTimeCounter().getCountLocked(which));
        pw.print(",");
        pw.print(counter.getPowerCounter().getCountLocked(which) / 3600000.0d);
        pw.print(",");
        pw.print(counter.getMonitoredRailChargeConsumedMaMs().getCountLocked(which) / 3600000.0d);
        for (LongCounter c : counter.getTxTimeCounters()) {
            pw.print(",");
            pw.print(c.getCountLocked(which));
        }
        pw.println();
    }

    private static void dumpControllerActivityProto(
            ProtoOutputStream proto, long fieldId, ControllerActivityCounter counter, int which) {
        if (!controllerActivityHasData(counter, which)) {
            return;
        }
        long cToken = proto.start(fieldId);
        proto.write(1112396529665L, counter.getIdleTimeCounter().getCountLocked(which));
        proto.write(1112396529666L, counter.getRxTimeCounter().getCountLocked(which));
        proto.write(1112396529667L, counter.getPowerCounter().getCountLocked(which) / 3600000.0d);
        proto.write(
                1103806595077L,
                counter.getMonitoredRailChargeConsumedMaMs().getCountLocked(which) / 3600000.0d);
        LongCounter[] txCounters = counter.getTxTimeCounters();
        for (int i = 0; i < txCounters.length; i++) {
            LongCounter c = txCounters[i];
            long tToken = proto.start(2246267895812L);
            proto.write(1120986464257L, i);
            proto.write(1112396529666L, c.getCountLocked(which));
            proto.end(tToken);
        }
        proto.end(cToken);
    }

    private final void printControllerActivityIfInteresting(
            PrintWriter pw,
            StringBuilder sb,
            String prefix,
            String controllerName,
            ControllerActivityCounter counter,
            int which) {
        if (controllerActivityHasData(counter, which)) {
            printControllerActivity(pw, sb, prefix, controllerName, counter, which);
        }
    }

    private final void printControllerActivity(
            PrintWriter pw,
            StringBuilder sb,
            String prefix,
            String controllerName,
            ControllerActivityCounter counter,
            int which) {
        long rxTimeMs;
        String str;
        int i;
        Object obj;
        char c;
        String[] powerLevel;
        String[] powerLevel2;
        long powerDrainMaMs;
        long idleTimeMs = counter.getIdleTimeCounter().getCountLocked(which);
        long rxTimeMs2 = counter.getRxTimeCounter().getCountLocked(which);
        long powerDrainMaMs2 = counter.getPowerCounter().getCountLocked(which);
        long monitoredRailChargeConsumedMaMs =
                counter.getMonitoredRailChargeConsumedMaMs().getCountLocked(which);
        long totalControllerActivityTimeMs =
                computeBatteryRealtime(SystemClock.elapsedRealtime() * 1000, which) / 1000;
        long totalTxTimeMs = 0;
        LongCounter[] txTimeCounters = counter.getTxTimeCounters();
        int length = txTimeCounters.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = length;
            LongCounter txState = txTimeCounters[i2];
            totalTxTimeMs += txState.getCountLocked(which);
            i2++;
            length = i3;
        }
        if (!controllerName.equals(WIFI_CONTROLLER_NAME)) {
            rxTimeMs = rxTimeMs2;
            str = " Sleep time:  ";
        } else {
            long scanTimeMs = counter.getScanTimeCounter().getCountLocked(which);
            sb.setLength(0);
            sb.append(prefix);
            sb.append("     ");
            sb.append(controllerName);
            sb.append(" Scan time:  ");
            formatTimeMs(sb, scanTimeMs);
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(formatRatioLocked(scanTimeMs, totalControllerActivityTimeMs));
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
            pw.println(sb.toString());
            long scanTimeMs2 =
                    totalControllerActivityTimeMs - ((idleTimeMs + rxTimeMs2) + totalTxTimeMs);
            sb.setLength(0);
            sb.append(prefix);
            sb.append("     ");
            sb.append(controllerName);
            str = " Sleep time:  ";
            sb.append(str);
            formatTimeMs(sb, scanTimeMs2);
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            rxTimeMs = rxTimeMs2;
            sb.append(formatRatioLocked(scanTimeMs2, totalControllerActivityTimeMs));
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
            pw.println(sb.toString());
        }
        if (!controllerName.equals("Cellular")) {
            i = which;
            obj = "Cellular";
        } else {
            i = which;
            long sleepTimeMs = counter.getSleepTimeCounter().getCountLocked(i);
            obj = "Cellular";
            sb.setLength(0);
            sb.append(prefix);
            sb.append("     ");
            sb.append(controllerName);
            sb.append(str);
            formatTimeMs(sb, sleepTimeMs);
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(formatRatioLocked(sleepTimeMs, totalControllerActivityTimeMs));
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
            pw.println(sb.toString());
        }
        sb.setLength(0);
        sb.append(prefix);
        sb.append("     ");
        sb.append(controllerName);
        sb.append(" Idle time:   ");
        formatTimeMs(sb, idleTimeMs);
        sb.append(NavigationBarInflaterView.KEY_CODE_START);
        sb.append(formatRatioLocked(idleTimeMs, totalControllerActivityTimeMs));
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        pw.println(sb.toString());
        sb.setLength(0);
        sb.append(prefix);
        sb.append("     ");
        sb.append(controllerName);
        sb.append(" Rx time:     ");
        long rxTimeMs3 = rxTimeMs;
        formatTimeMs(sb, rxTimeMs3);
        sb.append(NavigationBarInflaterView.KEY_CODE_START);
        sb.append(formatRatioLocked(rxTimeMs3, totalControllerActivityTimeMs));
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        pw.println(sb.toString());
        sb.setLength(0);
        sb.append(prefix);
        sb.append("     ");
        sb.append(controllerName);
        sb.append(" Tx time:     ");
        switch (controllerName.hashCode()) {
            case -851952246:
                if (controllerName.equals(obj)) {
                    c = 0;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                powerLevel =
                        new String[] {
                            "   less than 0dBm: ",
                            "   0dBm to 8dBm: ",
                            "   8dBm to 15dBm: ",
                            "   15dBm to 20dBm: ",
                            "   above 20dBm: "
                        };
                break;
            default:
                powerLevel = new String[] {"[0]", "[1]", "[2]", "[3]", "[4]"};
                break;
        }
        int numTxLvls = Math.min(counter.getTxTimeCounters().length, powerLevel.length);
        if (numTxLvls > 1) {
            pw.println(sb.toString());
            for (int lvl = 0; lvl < numTxLvls; lvl++) {
                long txLvlTimeMs = counter.getTxTimeCounters()[lvl].getCountLocked(i);
                sb.setLength(0);
                sb.append(prefix);
                sb.append("    ");
                sb.append(powerLevel[lvl]);
                sb.append(" ");
                formatTimeMs(sb, txLvlTimeMs);
                sb.append(NavigationBarInflaterView.KEY_CODE_START);
                sb.append(formatRatioLocked(txLvlTimeMs, totalControllerActivityTimeMs));
                sb.append(NavigationBarInflaterView.KEY_CODE_END);
                pw.println(sb.toString());
            }
        } else {
            long txLvlTimeMs2 = counter.getTxTimeCounters()[0].getCountLocked(i);
            formatTimeMs(sb, txLvlTimeMs2);
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(formatRatioLocked(txLvlTimeMs2, totalControllerActivityTimeMs));
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
            pw.println(sb.toString());
        }
        if (powerDrainMaMs2 <= 0) {
            powerLevel2 = powerLevel;
            powerDrainMaMs = powerDrainMaMs2;
        } else {
            sb.setLength(0);
            sb.append(prefix);
            sb.append("     ");
            sb.append(controllerName);
            powerLevel2 = powerLevel;
            powerDrainMaMs = powerDrainMaMs2;
            sb.append(" Battery drain: ").append(formatCharge(powerDrainMaMs / 3600000.0d));
            sb.append("mAh");
            pw.println(sb.toString());
        }
        if (monitoredRailChargeConsumedMaMs > 0) {
            sb.setLength(0);
            sb.append(prefix);
            sb.append("     ");
            sb.append(controllerName);
            sb.append(" Monitored rail energy drain: ")
                    .append(
                            new DecimalFormat("#.##")
                                    .format(monitoredRailChargeConsumedMaMs / 3600000.0d));
            sb.append(" mAh");
            pw.println(sb.toString());
        }
    }

    private void printCellularPerRatBreakdown(
            PrintWriter pw, StringBuilder sb, String prefix, long rawRealtimeMs) {
        String[] nrFrequencyRangeDescription;
        String signalStrengthHeader;
        long j = rawRealtimeMs;
        String allFrequenciesHeader = "    All frequencies:\n";
        String[] nrFrequencyRangeDescription2 = {
            "    Unknown frequency:\n",
            "    Low frequency (less than 1GHz):\n",
            "    Middle frequency (1GHz to 3GHz):\n",
            "    High frequency (3GHz to 6GHz):\n",
            "    Mmwave frequency (greater than 6GHz):\n"
        };
        String signalStrengthHeader2 = "      Signal Strength Time:\n";
        String txHeader = "      Tx Time:\n";
        String rxHeader = "      Rx Time: ";
        String[] signalStrengthDescription = {
            "        unknown:  ",
            "        poor:     ",
            "        moderate: ",
            "        good:     ",
            "        great:    "
        };
        int rat = 0;
        long totalActiveTimesMs = getMobileRadioActiveTime(j * 1000, 0) / 1000;
        sb.setLength(0);
        sb.append(prefix);
        sb.append("Active Cellular Radio Access Technology Breakdown:");
        pw.println(sb);
        boolean hasData = false;
        int numSignalStrength = CellSignalStrength.getNumSignalStrengthLevels();
        int rat2 = 2;
        while (rat2 >= 0) {
            sb.setLength(rat);
            sb.append(prefix);
            sb.append("  ");
            sb.append(RADIO_ACCESS_TECHNOLOGY_NAMES[rat2]);
            sb.append(":\n");
            sb.append(prefix);
            int numFreqLvl = rat2 == 2 ? nrFrequencyRangeDescription2.length : 1;
            int freqLvl = numFreqLvl - 1;
            boolean hasData2 = hasData;
            while (freqLvl >= 0) {
                int freqDescriptionStart = sb.length();
                boolean hasFreqData = false;
                String allFrequenciesHeader2 = allFrequenciesHeader;
                if (rat2 == 2) {
                    sb.append(nrFrequencyRangeDescription2[freqLvl]);
                } else {
                    sb.append("    All frequencies:\n");
                }
                sb.append(prefix);
                sb.append("      Signal Strength Time:\n");
                int strength = 0;
                while (true) {
                    nrFrequencyRangeDescription = nrFrequencyRangeDescription2;
                    signalStrengthHeader = signalStrengthHeader2;
                    if (strength >= numSignalStrength) {
                        break;
                    }
                    String txHeader2 = txHeader;
                    int freqDescriptionStart2 = freqDescriptionStart;
                    int rat3 = rat2;
                    String rxHeader2 = rxHeader;
                    long totalActiveTimesMs2 = totalActiveTimesMs;
                    int freqLvl2 = freqLvl;
                    int numSignalStrength2 = numSignalStrength;
                    long timeMs = getActiveRadioDurationMs(rat2, freqLvl, strength, rawRealtimeMs);
                    if (timeMs > 0) {
                        sb.append(prefix);
                        sb.append(signalStrengthDescription[strength]);
                        formatTimeMs(sb, timeMs);
                        sb.append(NavigationBarInflaterView.KEY_CODE_START);
                        sb.append(formatRatioLocked(timeMs, totalActiveTimesMs2));
                        sb.append(")\n");
                        hasFreqData = true;
                    }
                    strength++;
                    numSignalStrength = numSignalStrength2;
                    freqLvl = freqLvl2;
                    totalActiveTimesMs = totalActiveTimesMs2;
                    nrFrequencyRangeDescription2 = nrFrequencyRangeDescription;
                    signalStrengthHeader2 = signalStrengthHeader;
                    txHeader = txHeader2;
                    rat2 = rat3;
                    rxHeader = rxHeader2;
                    freqDescriptionStart = freqDescriptionStart2;
                }
                int freqDescriptionStart3 = freqDescriptionStart;
                int rat4 = rat2;
                int freqLvl3 = freqLvl;
                int numSignalStrength3 = numSignalStrength;
                String txHeader3 = txHeader;
                String rxHeader3 = rxHeader;
                long totalActiveTimesMs3 = totalActiveTimesMs;
                sb.append(prefix);
                sb.append("      Tx Time:\n");
                for (int strength2 = 0; strength2 < numSignalStrength3; strength2++) {
                    long timeMs2 =
                            getActiveTxRadioDurationMs(rat4, freqLvl3, strength2, rawRealtimeMs);
                    if (timeMs2 > 0) {
                        sb.append(prefix);
                        sb.append(signalStrengthDescription[strength2]);
                        formatTimeMs(sb, timeMs2);
                        sb.append(NavigationBarInflaterView.KEY_CODE_START);
                        sb.append(formatRatioLocked(timeMs2, totalActiveTimesMs3));
                        sb.append(")\n");
                        hasFreqData = true;
                    }
                }
                sb.append(prefix);
                sb.append("      Rx Time: ");
                long rxTimeMs = getActiveRxRadioDurationMs(rat4, freqLvl3, rawRealtimeMs);
                formatTimeMs(sb, rxTimeMs);
                sb.append(NavigationBarInflaterView.KEY_CODE_START);
                sb.append(formatRatioLocked(rxTimeMs, totalActiveTimesMs3));
                sb.append(")\n");
                if (hasFreqData) {
                    hasData2 = true;
                    pw.print(sb);
                    sb.setLength(0);
                    sb.append(prefix);
                } else {
                    sb.setLength(freqDescriptionStart3);
                }
                int freqDescriptionStart4 = freqLvl3 - 1;
                j = rawRealtimeMs;
                numSignalStrength = numSignalStrength3;
                rat2 = rat4;
                totalActiveTimesMs = totalActiveTimesMs3;
                allFrequenciesHeader = allFrequenciesHeader2;
                nrFrequencyRangeDescription2 = nrFrequencyRangeDescription;
                signalStrengthHeader2 = signalStrengthHeader;
                txHeader = txHeader3;
                rxHeader = rxHeader3;
                freqLvl = freqDescriptionStart4;
            }
            int freqLvl4 = rat2;
            int i = freqLvl4 - 1;
            j = j;
            rat = 0;
            rat2 = i;
            numSignalStrength = numSignalStrength;
            totalActiveTimesMs = totalActiveTimesMs;
            hasData = hasData2;
            txHeader = txHeader;
            rxHeader = rxHeader;
        }
        int numSignalStrength4 = rat;
        if (!hasData) {
            sb.setLength(numSignalStrength4);
            sb.append(prefix);
            sb.append("  (no activity)");
            pw.println(sb);
        }
    }

    public final void dumpCheckinLocked(
            Context context,
            PrintWriter printWriter,
            int i,
            int i2,
            boolean z,
            BatteryStatsDumpHelper batteryStatsDumpHelper) {
        Integer num;
        SparseArray<? extends Uid> sparseArray;
        String str;
        String str2;
        long j;
        long j2;
        int i3;
        AggregateBatteryConsumer aggregateBatteryConsumer;
        List<UidBatteryConsumer> list;
        CpuScalingPolicies cpuScalingPolicies;
        StringBuilder sb;
        long j3;
        int i4;
        byte b;
        String str3;
        long j4;
        SparseArray<? extends Uid> sparseArray2;
        Map<String, ? extends Timer> map;
        long j5;
        long j6;
        String str4;
        Object[] objArr;
        long j7;
        StringBuilder sb2;
        String str5;
        Integer num2;
        long j8;
        ArrayMap<String, ? extends Uid.Pkg> arrayMap;
        StringBuilder sb3;
        String str6;
        ArrayMap<String, ? extends Uid.Proc> arrayMap2;
        Object[] objArr2;
        Object[] objArr3;
        int i5;
        long j9;
        long j10;
        String str7;
        ArrayMap<String, SparseIntArray> arrayMap3;
        ArrayMap<String, ? extends Timer> arrayMap4;
        StringBuilder sb4;
        long j11;
        long j12;
        Timer timer;
        ArrayMap<String, ? extends Uid.Wakelock> arrayMap5;
        String str8;
        ArrayMap<String, ? extends Timer> arrayMap6;
        char c;
        int i6;
        long j13;
        BatteryStats batteryStats = this;
        int i7 = i;
        Integer num3 = 0;
        if (i7 != 0) {
            dumpLine(
                    printWriter,
                    0,
                    STAT_NAMES[i7],
                    Notification.CATEGORY_ERROR,
                    "ERROR: BatteryStats.dumpCheckin called for which type "
                            + i7
                            + " but only STATS_SINCE_CHARGED is supported.");
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis() * 1000;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j14 = elapsedRealtime * 1000;
        long batteryUptime = batteryStats.getBatteryUptime(uptimeMillis);
        long computeBatteryUptime = batteryStats.computeBatteryUptime(uptimeMillis, i7);
        long computeBatteryRealtime = batteryStats.computeBatteryRealtime(j14, i7);
        long computeBatteryScreenOffUptime =
                batteryStats.computeBatteryScreenOffUptime(uptimeMillis, i7);
        long computeBatteryScreenOffRealtime =
                batteryStats.computeBatteryScreenOffRealtime(j14, i7);
        long computeRealtime = batteryStats.computeRealtime(j14, i7);
        long computeUptime = batteryStats.computeUptime(uptimeMillis, i7);
        long screenOnTime = batteryStats.getScreenOnTime(j14, i7);
        long screenDozeTime = batteryStats.getScreenDozeTime(j14, i7);
        long interactiveTime = batteryStats.getInteractiveTime(j14, i7);
        long powerSaveModeEnabledTime = batteryStats.getPowerSaveModeEnabledTime(j14, i7);
        long deviceIdleModeTime = batteryStats.getDeviceIdleModeTime(1, j14, i7);
        long deviceIdleModeTime2 = batteryStats.getDeviceIdleModeTime(2, j14, i7);
        long deviceIdlingTime = batteryStats.getDeviceIdlingTime(1, j14, i7);
        long deviceIdlingTime2 = batteryStats.getDeviceIdlingTime(2, j14, i7);
        int numConnectivityChange = batteryStats.getNumConnectivityChange(i7);
        long phoneOnTime = batteryStats.getPhoneOnTime(j14, i7);
        long uahDischarge = batteryStats.getUahDischarge(i7);
        long uahDischargeScreenOff = batteryStats.getUahDischargeScreenOff(i7);
        long uahDischargeScreenDoze = batteryStats.getUahDischargeScreenDoze(i7);
        long uahDischargeLightDoze = batteryStats.getUahDischargeLightDoze(i7);
        long uahDischargeDeepDoze = batteryStats.getUahDischargeDeepDoze(i7);
        StringBuilder sb5 = new StringBuilder(128);
        SparseArray<? extends Uid> uidStats = getUidStats();
        int size = uidStats.size();
        String str9 = STAT_NAMES[i7];
        dumpLine(
                printWriter,
                0,
                str9,
                BATTERY_DATA,
                i7 == 0 ? Integer.valueOf(getStartCount()) : "N/A",
                Long.valueOf(computeBatteryRealtime / 1000),
                Long.valueOf(computeBatteryUptime / 1000),
                Long.valueOf(computeRealtime / 1000),
                Long.valueOf(computeUptime / 1000),
                Long.valueOf(getStartClockTime()),
                Long.valueOf(computeBatteryScreenOffRealtime / 1000),
                Long.valueOf(computeBatteryScreenOffUptime / 1000),
                Integer.valueOf(getEstimatedBatteryCapacity()),
                Integer.valueOf(getMinLearnedBatteryCapacity()),
                Integer.valueOf(getMaxLearnedBatteryCapacity()),
                Long.valueOf(screenDozeTime / 1000));
        int i8 = 0;
        long j15 = 0;
        long j16 = 0;
        while (i8 < size) {
            Uid valueAt = uidStats.valueAt(i8);
            ArrayMap<String, ? extends Uid.Wakelock> wakelockStats = valueAt.getWakelockStats();
            SparseArray<? extends Uid> sparseArray3 = uidStats;
            int size2 = wakelockStats.size() - 1;
            while (size2 >= 0) {
                int i9 = size;
                Uid.Wakelock valueAt2 = wakelockStats.valueAt(size2);
                Uid uid = valueAt;
                ArrayMap<String, ? extends Uid.Wakelock> arrayMap7 = wakelockStats;
                Timer wakeTime = valueAt2.getWakeTime(1);
                if (wakeTime != null) {
                    j16 += wakeTime.getTotalTimeLocked(j14, i7);
                }
                Timer wakeTime2 = valueAt2.getWakeTime(0);
                if (wakeTime2 != null) {
                    j15 += wakeTime2.getTotalTimeLocked(j14, i7);
                }
                size2--;
                valueAt = uid;
                size = i9;
                wakelockStats = arrayMap7;
            }
            i8++;
            uidStats = sparseArray3;
        }
        dumpLine(
                printWriter,
                0,
                str9,
                GLOBAL_NETWORK_DATA,
                Long.valueOf(batteryStats.getNetworkActivityBytes(0, i7)),
                Long.valueOf(batteryStats.getNetworkActivityBytes(1, i7)),
                Long.valueOf(batteryStats.getNetworkActivityBytes(2, i7)),
                Long.valueOf(batteryStats.getNetworkActivityBytes(3, i7)),
                Long.valueOf(batteryStats.getNetworkActivityPackets(0, i7)),
                Long.valueOf(batteryStats.getNetworkActivityPackets(1, i7)),
                Long.valueOf(batteryStats.getNetworkActivityPackets(2, i7)),
                Long.valueOf(batteryStats.getNetworkActivityPackets(3, i7)),
                Long.valueOf(batteryStats.getNetworkActivityBytes(4, i7)),
                Long.valueOf(batteryStats.getNetworkActivityBytes(5, i7)));
        SparseArray<? extends Uid> sparseArray4 = uidStats;
        long j17 = batteryUptime;
        int i10 = size;
        long j18 = elapsedRealtime;
        dumpControllerActivityLine(
                printWriter,
                0,
                str9,
                GLOBAL_MODEM_CONTROLLER_DATA,
                getModemControllerActivity(),
                i);
        dumpLine(
                printWriter,
                0,
                str9,
                GLOBAL_WIFI_DATA,
                Long.valueOf(batteryStats.getWifiOnTime(j14, i7) / 1000),
                Long.valueOf(batteryStats.getGlobalWifiRunningTime(j14, i7) / 1000),
                num3,
                num3,
                num3);
        dumpControllerActivityLine(
                printWriter, 0, str9, GLOBAL_WIFI_CONTROLLER_DATA, getWifiControllerActivity(), i);
        dumpControllerActivityLine(
                printWriter,
                0,
                str9,
                GLOBAL_BLUETOOTH_CONTROLLER_DATA,
                getBluetoothControllerActivity(),
                i);
        long j19 = j14;
        String str10 = str9;
        dumpLine(
                printWriter,
                0,
                str10,
                MISC_DATA,
                Long.valueOf(screenOnTime / 1000),
                Long.valueOf(phoneOnTime / 1000),
                Long.valueOf(j16 / 1000),
                Long.valueOf(j15 / 1000),
                Long.valueOf(batteryStats.getMobileRadioActiveTime(j19, i7) / 1000),
                Long.valueOf(batteryStats.getMobileRadioActiveAdjustedTime(i7) / 1000),
                Long.valueOf(interactiveTime / 1000),
                Long.valueOf(powerSaveModeEnabledTime / 1000),
                Integer.valueOf(numConnectivityChange),
                Long.valueOf(deviceIdleModeTime2 / 1000),
                Integer.valueOf(batteryStats.getDeviceIdleModeCount(2, i7)),
                Long.valueOf(deviceIdlingTime2 / 1000),
                Integer.valueOf(batteryStats.getDeviceIdlingCount(2, i7)),
                Integer.valueOf(batteryStats.getMobileRadioActiveCount(i7)),
                Long.valueOf(batteryStats.getMobileRadioActiveUnknownTime(i7) / 1000),
                Long.valueOf(deviceIdleModeTime / 1000),
                Integer.valueOf(batteryStats.getDeviceIdleModeCount(1, i7)),
                Long.valueOf(deviceIdlingTime / 1000),
                Integer.valueOf(batteryStats.getDeviceIdlingCount(1, i7)),
                Long.valueOf(batteryStats.getLongestDeviceIdleModeTime(1)),
                Long.valueOf(batteryStats.getLongestDeviceIdleModeTime(2)));
        Object[] objArr4 = new Object[5];
        for (int i11 = 0; i11 < 5; i11++) {
            objArr4[i11] = Long.valueOf(batteryStats.getScreenBrightnessTime(i11, j19, i7) / 1000);
        }
        dumpLine(printWriter, 0, str10, "br", objArr4);
        Object[] objArr5 = new Object[CellSignalStrength.getNumSignalStrengthLevels()];
        for (int i12 = 0; i12 < CellSignalStrength.getNumSignalStrengthLevels(); i12++) {
            objArr5[i12] =
                    Long.valueOf(batteryStats.getPhoneSignalStrengthTime(i12, j19, i7) / 1000);
        }
        dumpLine(printWriter, 0, str10, SIGNAL_STRENGTH_TIME_DATA, objArr5);
        dumpLine(
                printWriter,
                0,
                str10,
                SIGNAL_SCANNING_TIME_DATA,
                Long.valueOf(batteryStats.getPhoneSignalScanningTime(j19, i7) / 1000));
        for (int i13 = 0; i13 < CellSignalStrength.getNumSignalStrengthLevels(); i13++) {
            objArr5[i13] = Integer.valueOf(batteryStats.getPhoneSignalStrengthCount(i13, i7));
        }
        dumpLine(printWriter, 0, str10, SIGNAL_STRENGTH_COUNT_DATA, objArr5);
        Object[] objArr6 = new Object[NUM_DATA_CONNECTION_TYPES];
        for (int i14 = 0; i14 < NUM_DATA_CONNECTION_TYPES; i14++) {
            objArr6[i14] =
                    Long.valueOf(batteryStats.getPhoneDataConnectionTime(i14, j19, i7) / 1000);
        }
        dumpLine(printWriter, 0, str10, DATA_CONNECTION_TIME_DATA, objArr6);
        for (int i15 = 0; i15 < NUM_DATA_CONNECTION_TYPES; i15++) {
            objArr6[i15] = Integer.valueOf(batteryStats.getPhoneDataConnectionCount(i15, i7));
        }
        dumpLine(printWriter, 0, str10, DATA_CONNECTION_COUNT_DATA, objArr6);
        Object[] objArr7 = new Object[8];
        for (int i16 = 0; i16 < 8; i16++) {
            objArr7[i16] = Long.valueOf(batteryStats.getWifiStateTime(i16, j19, i7) / 1000);
        }
        dumpLine(printWriter, 0, str10, WIFI_STATE_TIME_DATA, objArr7);
        for (int i17 = 0; i17 < 8; i17++) {
            objArr7[i17] = Integer.valueOf(batteryStats.getWifiStateCount(i17, i7));
        }
        dumpLine(printWriter, 0, str10, WIFI_STATE_COUNT_DATA, objArr7);
        Object[] objArr8 = new Object[13];
        for (int i18 = 0; i18 < 13; i18++) {
            objArr8[i18] = Long.valueOf(batteryStats.getWifiSupplStateTime(i18, j19, i7) / 1000);
        }
        dumpLine(printWriter, 0, str10, WIFI_SUPPL_STATE_TIME_DATA, objArr8);
        for (int i19 = 0; i19 < 13; i19++) {
            objArr8[i19] = Integer.valueOf(batteryStats.getWifiSupplStateCount(i19, i7));
        }
        dumpLine(printWriter, 0, str10, WIFI_SUPPL_STATE_COUNT_DATA, objArr8);
        Object[] objArr9 = new Object[5];
        for (int i20 = 0; i20 < 5; i20++) {
            objArr9[i20] =
                    Long.valueOf(batteryStats.getWifiSignalStrengthTime(i20, j19, i7) / 1000);
        }
        dumpLine(printWriter, 0, str10, WIFI_SIGNAL_STRENGTH_TIME_DATA, objArr9);
        for (int i21 = 0; i21 < 5; i21++) {
            objArr9[i21] = Integer.valueOf(batteryStats.getWifiSignalStrengthCount(i21, i7));
        }
        dumpLine(printWriter, 0, str10, WIFI_SIGNAL_STRENGTH_COUNT_DATA, objArr9);
        dumpLine(
                printWriter,
                0,
                str10,
                WIFI_MULTICAST_TOTAL_DATA,
                Long.valueOf(batteryStats.getWifiMulticastWakelockTime(j19, i7) / 1000),
                Integer.valueOf(batteryStats.getWifiMulticastWakelockCount(i7)));
        dumpLine(
                printWriter,
                0,
                str10,
                BATTERY_DISCHARGE_DATA,
                Integer.valueOf(getLowDischargeAmountSinceCharge()),
                Integer.valueOf(getHighDischargeAmountSinceCharge()),
                Integer.valueOf(getDischargeAmountScreenOnSinceCharge()),
                Integer.valueOf(getDischargeAmountScreenOffSinceCharge()),
                Long.valueOf(uahDischarge / 1000),
                Long.valueOf(uahDischargeScreenOff / 1000),
                Integer.valueOf(getDischargeAmountScreenDozeSinceCharge()),
                Long.valueOf(uahDischargeScreenDoze / 1000),
                Long.valueOf(uahDischargeLightDoze / 1000),
                Long.valueOf(uahDischargeDeepDoze / 1000));
        String str11 = "\"";
        if (i2 >= 0) {
            num = num3;
            sparseArray = sparseArray4;
            str = str10;
            str2 = "\"";
            j = j19;
        } else {
            Map<String, ? extends Timer> kernelWakelockStats = getKernelWakelockStats();
            if (kernelWakelockStats.size() <= 0) {
                num = num3;
                sparseArray = sparseArray4;
                str = str10;
                str2 = "\"";
                j = j19;
            } else {
                for (Map.Entry<String, ? extends Timer> entry : kernelWakelockStats.entrySet()) {
                    sb5.setLength(0);
                    Integer num4 = num3;
                    Object[] objArr10 = objArr9;
                    String str12 = str10;
                    String str13 = str11;
                    printWakeLockCheckin(sb5, entry.getValue(), j19, null, i, "");
                    dumpLine(
                            printWriter,
                            0,
                            str12,
                            KERNEL_WAKELOCK_DATA,
                            str13 + entry.getKey() + str13,
                            sb5.toString());
                    str10 = str12;
                    j19 = j19;
                    str11 = str13;
                    objArr9 = objArr10;
                    num3 = num4;
                    sparseArray4 = sparseArray4;
                }
                num = num3;
                sparseArray = sparseArray4;
                str = str10;
                str2 = str11;
                j = j19;
            }
            Map<String, ? extends Timer> wakeupReasonStats = getWakeupReasonStats();
            if (wakeupReasonStats.size() > 0) {
                Iterator<Map.Entry<String, ? extends Timer>> it =
                        wakeupReasonStats.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, ? extends Timer> next = it.next();
                    dumpLine(
                            printWriter,
                            0,
                            str,
                            WAKEUP_REASON_DATA,
                            str2 + next.getKey() + str2,
                            Long.valueOf((next.getValue().getTotalTimeLocked(j, i7) + 500) / 1000),
                            Integer.valueOf(next.getValue().getCountLocked(i7)));
                    it = it;
                    wakeupReasonStats = wakeupReasonStats;
                }
            }
        }
        Map<String, ? extends Timer> rpmStats = getRpmStats();
        Map<String, ? extends Timer> screenOffRpmStats = getScreenOffRpmStats();
        if (rpmStats.size() > 0) {
            Iterator<Map.Entry<String, ? extends Timer>> it2 = rpmStats.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry<String, ? extends Timer> next2 = it2.next();
                sb5.setLength(0);
                Timer value = next2.getValue();
                long totalTimeLocked = (value.getTotalTimeLocked(j, i7) + 500) / 1000;
                int countLocked = value.getCountLocked(i7);
                Iterator<Map.Entry<String, ? extends Timer>> it3 = it2;
                Timer timer2 = screenOffRpmStats.get(next2.getKey());
                if (timer2 != null) {
                    long totalTimeLocked2 = (timer2.getTotalTimeLocked(j, i7) + 500) / 1000;
                }
                if (timer2 != null) {
                    timer2.getCountLocked(i7);
                }
                dumpLine(
                        printWriter,
                        0,
                        str,
                        RESOURCE_POWER_MANAGER_DATA,
                        str2 + next2.getKey() + str2,
                        Long.valueOf(totalTimeLocked),
                        Integer.valueOf(countLocked));
                it2 = it3;
            }
        }
        BatteryUsageStats batteryUsageStats =
                batteryStatsDumpHelper.getBatteryUsageStats(batteryStats, true);
        dumpLine(
                printWriter,
                0,
                str,
                POWER_USE_SUMMARY_DATA,
                formatCharge(batteryUsageStats.getBatteryCapacity()),
                formatCharge(batteryUsageStats.getConsumedPower()),
                formatCharge(batteryUsageStats.getDischargedPowerRange().getLower().doubleValue()),
                formatCharge(batteryUsageStats.getDischargedPowerRange().getUpper().doubleValue()));
        AggregateBatteryConsumer aggregateBatteryConsumer2 =
                batteryUsageStats.getAggregateBatteryConsumer(0);
        int i22 = 0;
        while (i22 < 19) {
            String str14 = CHECKIN_POWER_COMPONENT_LABELS[i22];
            if (str14 == null) {
                str14 = "???";
            }
            dumpLine(
                    printWriter,
                    0,
                    str,
                    POWER_USE_ITEM_DATA,
                    str14,
                    formatCharge(aggregateBatteryConsumer2.getConsumedPower(i22)),
                    Integer.valueOf(batteryStats.shouldHidePowerComponent(i22) ? 1 : 0),
                    "0",
                    "0");
            i22++;
            batteryStats = this;
            aggregateBatteryConsumer2 = aggregateBatteryConsumer2;
        }
        AggregateBatteryConsumer aggregateBatteryConsumer3 = aggregateBatteryConsumer2;
        ProportionalAttributionCalculator proportionalAttributionCalculator =
                new ProportionalAttributionCalculator(context, batteryUsageStats);
        List<UidBatteryConsumer> uidBatteryConsumers = batteryUsageStats.getUidBatteryConsumers();
        int i23 = 0;
        while (i23 < uidBatteryConsumers.size()) {
            UidBatteryConsumer uidBatteryConsumer = uidBatteryConsumers.get(i23);
            dumpLine(
                    printWriter,
                    uidBatteryConsumer.getUid(),
                    str,
                    POWER_USE_ITEM_DATA,
                    "uid",
                    formatCharge(uidBatteryConsumer.getConsumedPower()),
                    Integer.valueOf(
                            proportionalAttributionCalculator.isSystemBatteryConsumer(
                                            uidBatteryConsumer)
                                    ? 1
                                    : 0),
                    formatCharge(uidBatteryConsumer.getConsumedPower(0)),
                    formatCharge(
                            proportionalAttributionCalculator.getProportionalPowerMah(
                                    uidBatteryConsumer)));
            i23++;
            uidBatteryConsumers = uidBatteryConsumers;
            batteryUsageStats = batteryUsageStats;
            screenOffRpmStats = screenOffRpmStats;
            proportionalAttributionCalculator = proportionalAttributionCalculator;
        }
        List<UidBatteryConsumer> list2 = uidBatteryConsumers;
        Map<String, ? extends Timer> map2 = screenOffRpmStats;
        CpuScalingPolicies cpuScalingPolicies2 = getCpuScalingPolicies();
        if (cpuScalingPolicies2 == null) {
            j2 = j;
        } else {
            sb5.setLength(0);
            int[] policies = cpuScalingPolicies2.getPolicies();
            int length = policies.length;
            int i24 = 0;
            while (i24 < length) {
                int[] frequencies = cpuScalingPolicies2.getFrequencies(policies[i24]);
                int length2 = frequencies.length;
                int i25 = 0;
                while (i25 < length2) {
                    int[] iArr = policies;
                    int i26 = frequencies[i25];
                    if (sb5.length() != 0) {
                        j13 = j;
                        sb5.append(',');
                    } else {
                        j13 = j;
                    }
                    sb5.append(i26);
                    i25++;
                    policies = iArr;
                    j = j13;
                }
                i24++;
                j = j;
            }
            j2 = j;
            dumpLine(printWriter, 0, str, GLOBAL_CPU_FREQ_DATA, sb5.toString());
        }
        int i27 = 0;
        while (i27 < i10) {
            SparseArray<? extends Uid> sparseArray5 = sparseArray;
            int keyAt = sparseArray5.keyAt(i27);
            long j20 = j2;
            if (i2 >= 0 && keyAt != i2) {
                cpuScalingPolicies = cpuScalingPolicies2;
                j7 = j20;
                i3 = i27;
                sb2 = sb5;
                i4 = i10;
                str5 = str2;
                aggregateBatteryConsumer = aggregateBatteryConsumer3;
                list = list2;
                j8 = j17;
                j6 = j18;
                num2 = num;
                map = map2;
                sparseArray2 = sparseArray5;
                str4 = str;
            } else {
                Uid valueAt3 = sparseArray5.valueAt(i27);
                long networkActivityBytes = valueAt3.getNetworkActivityBytes(0, i7);
                long networkActivityBytes2 = valueAt3.getNetworkActivityBytes(1, i7);
                long networkActivityBytes3 = valueAt3.getNetworkActivityBytes(2, i7);
                long networkActivityBytes4 = valueAt3.getNetworkActivityBytes(3, i7);
                long networkActivityPackets = valueAt3.getNetworkActivityPackets(0, i7);
                long networkActivityPackets2 = valueAt3.getNetworkActivityPackets(1, i7);
                long mobileRadioActiveTime = valueAt3.getMobileRadioActiveTime(i7);
                int mobileRadioActiveCount = valueAt3.getMobileRadioActiveCount(i7);
                long mobileRadioApWakeupCount = valueAt3.getMobileRadioApWakeupCount(i7);
                long networkActivityPackets3 = valueAt3.getNetworkActivityPackets(2, i7);
                long networkActivityPackets4 = valueAt3.getNetworkActivityPackets(3, i7);
                long wifiRadioApWakeupCount = valueAt3.getWifiRadioApWakeupCount(i7);
                long networkActivityBytes5 = valueAt3.getNetworkActivityBytes(4, i7);
                long networkActivityBytes6 = valueAt3.getNetworkActivityBytes(5, i7);
                long networkActivityBytes7 = valueAt3.getNetworkActivityBytes(6, i7);
                long networkActivityBytes8 = valueAt3.getNetworkActivityBytes(7, i7);
                long networkActivityBytes9 = valueAt3.getNetworkActivityBytes(8, i7);
                long networkActivityBytes10 = valueAt3.getNetworkActivityBytes(9, i7);
                long networkActivityPackets5 = valueAt3.getNetworkActivityPackets(6, i7);
                long networkActivityPackets6 = valueAt3.getNetworkActivityPackets(7, i7);
                long networkActivityPackets7 = valueAt3.getNetworkActivityPackets(8, i7);
                long networkActivityPackets8 = valueAt3.getNetworkActivityPackets(9, i7);
                if (networkActivityBytes > 0
                        || networkActivityBytes2 > 0
                        || networkActivityBytes3 > 0
                        || networkActivityBytes4 > 0
                        || networkActivityPackets > 0
                        || networkActivityPackets2 > 0
                        || networkActivityPackets3 > 0
                        || networkActivityPackets4 > 0
                        || mobileRadioActiveTime > 0
                        || mobileRadioActiveCount > 0
                        || networkActivityBytes5 > 0
                        || networkActivityBytes6 > 0
                        || mobileRadioApWakeupCount > 0
                        || wifiRadioApWakeupCount > 0
                        || networkActivityBytes7 > 0
                        || networkActivityBytes8 > 0
                        || networkActivityBytes9 > 0
                        || networkActivityBytes10 > 0
                        || networkActivityPackets5 > 0
                        || networkActivityPackets6 > 0
                        || networkActivityPackets7 > 0
                        || networkActivityPackets8 > 0) {
                    dumpLine(
                            printWriter,
                            keyAt,
                            str,
                            NETWORK_DATA,
                            Long.valueOf(networkActivityBytes),
                            Long.valueOf(networkActivityBytes2),
                            Long.valueOf(networkActivityBytes3),
                            Long.valueOf(networkActivityBytes4),
                            Long.valueOf(networkActivityPackets),
                            Long.valueOf(networkActivityPackets2),
                            Long.valueOf(networkActivityPackets3),
                            Long.valueOf(networkActivityPackets4),
                            Long.valueOf(mobileRadioActiveTime),
                            Integer.valueOf(mobileRadioActiveCount),
                            Long.valueOf(networkActivityBytes5),
                            Long.valueOf(networkActivityBytes6),
                            Long.valueOf(mobileRadioApWakeupCount),
                            Long.valueOf(wifiRadioApWakeupCount),
                            Long.valueOf(networkActivityBytes7),
                            Long.valueOf(networkActivityBytes8),
                            Long.valueOf(networkActivityBytes9),
                            Long.valueOf(networkActivityBytes10),
                            Long.valueOf(networkActivityPackets5),
                            Long.valueOf(networkActivityPackets6),
                            Long.valueOf(networkActivityPackets7),
                            Long.valueOf(networkActivityPackets8));
                }
                i3 = i27;
                Uid uid2 = valueAt3;
                int i28 = i10;
                String str15 = str2;
                aggregateBatteryConsumer = aggregateBatteryConsumer3;
                list = list2;
                dumpControllerActivityLine(
                        printWriter,
                        keyAt,
                        str,
                        MODEM_CONTROLLER_DATA,
                        valueAt3.getModemControllerActivity(),
                        i);
                long fullWifiLockTime = uid2.getFullWifiLockTime(j20, i7);
                long wifiScanTime = uid2.getWifiScanTime(j20, i7);
                int wifiScanCount = uid2.getWifiScanCount(i7);
                int wifiScanBackgroundCount = uid2.getWifiScanBackgroundCount(i7);
                long wifiScanActualTime = (uid2.getWifiScanActualTime(j20) + 500) / 1000;
                long wifiScanBackgroundTime = (uid2.getWifiScanBackgroundTime(j20) + 500) / 1000;
                long wifiRunningTime = uid2.getWifiRunningTime(j20, i7);
                if (fullWifiLockTime == 0
                        && wifiScanTime == 0
                        && wifiScanCount == 0
                        && wifiScanBackgroundCount == 0
                        && wifiScanActualTime == 0
                        && wifiScanBackgroundTime == 0
                        && wifiRunningTime == 0) {
                    cpuScalingPolicies = cpuScalingPolicies2;
                    sb = sb5;
                    j3 = j20;
                    b = 2;
                    i4 = i28;
                    str3 = str15;
                } else {
                    cpuScalingPolicies = cpuScalingPolicies2;
                    sb = sb5;
                    j3 = j20;
                    i4 = i28;
                    b = 2;
                    str3 = str15;
                    dumpLine(
                            printWriter,
                            keyAt,
                            str,
                            WIFI_DATA,
                            Long.valueOf(fullWifiLockTime),
                            Long.valueOf(wifiScanTime),
                            Long.valueOf(wifiRunningTime),
                            Integer.valueOf(wifiScanCount),
                            num,
                            num,
                            num,
                            Integer.valueOf(wifiScanBackgroundCount),
                            Long.valueOf(wifiScanActualTime),
                            Long.valueOf(wifiScanBackgroundTime));
                }
                String str16 = str3;
                StringBuilder sb6 = sb;
                dumpControllerActivityLine(
                        printWriter,
                        keyAt,
                        str,
                        WIFI_CONTROLLER_DATA,
                        uid2.getWifiControllerActivity(),
                        i);
                Timer bluetoothScanTimer = uid2.getBluetoothScanTimer();
                if (bluetoothScanTimer == null) {
                    j4 = j18;
                } else {
                    long j21 = j3;
                    long totalTimeLocked3 =
                            (bluetoothScanTimer.getTotalTimeLocked(j21, i7) + 500) / 1000;
                    if (totalTimeLocked3 != 0) {
                        int countLocked2 = bluetoothScanTimer.getCountLocked(i7);
                        Timer bluetoothScanBackgroundTimer = uid2.getBluetoothScanBackgroundTimer();
                        int countLocked3 =
                                bluetoothScanBackgroundTimer != null
                                        ? bluetoothScanBackgroundTimer.getCountLocked(i7)
                                        : 0;
                        long j22 = j18;
                        long totalDurationMsLocked =
                                bluetoothScanTimer.getTotalDurationMsLocked(j22);
                        long totalDurationMsLocked2 =
                                bluetoothScanBackgroundTimer != null
                                        ? bluetoothScanBackgroundTimer.getTotalDurationMsLocked(j22)
                                        : 0L;
                        int countLocked4 =
                                uid2.getBluetoothScanResultCounter() != null
                                        ? uid2.getBluetoothScanResultCounter().getCountLocked(i7)
                                        : 0;
                        if (uid2.getBluetoothScanResultBgCounter() != null) {
                            j3 = j21;
                            i6 = uid2.getBluetoothScanResultBgCounter().getCountLocked(i7);
                        } else {
                            j3 = j21;
                            i6 = 0;
                        }
                        Timer bluetoothUnoptimizedScanTimer =
                                uid2.getBluetoothUnoptimizedScanTimer();
                        long totalDurationMsLocked3 =
                                bluetoothUnoptimizedScanTimer != null
                                        ? bluetoothUnoptimizedScanTimer.getTotalDurationMsLocked(
                                                j22)
                                        : 0L;
                        long maxDurationMsLocked =
                                bluetoothUnoptimizedScanTimer != null
                                        ? bluetoothUnoptimizedScanTimer.getMaxDurationMsLocked(j22)
                                        : 0L;
                        Timer bluetoothUnoptimizedScanBackgroundTimer =
                                uid2.getBluetoothUnoptimizedScanBackgroundTimer();
                        j4 = j22;
                        dumpLine(
                                printWriter,
                                keyAt,
                                str,
                                BLUETOOTH_MISC_DATA,
                                Long.valueOf(totalTimeLocked3),
                                Integer.valueOf(countLocked2),
                                Integer.valueOf(countLocked3),
                                Long.valueOf(totalDurationMsLocked),
                                Long.valueOf(totalDurationMsLocked2),
                                Integer.valueOf(countLocked4),
                                Integer.valueOf(i6),
                                Long.valueOf(totalDurationMsLocked3),
                                Long.valueOf(
                                        bluetoothUnoptimizedScanBackgroundTimer != null
                                                ? bluetoothUnoptimizedScanBackgroundTimer
                                                        .getTotalDurationMsLocked(j22)
                                                : 0L),
                                Long.valueOf(maxDurationMsLocked),
                                Long.valueOf(
                                        bluetoothUnoptimizedScanBackgroundTimer != null
                                                ? bluetoothUnoptimizedScanBackgroundTimer
                                                        .getMaxDurationMsLocked(j22)
                                                : 0L));
                    } else {
                        j3 = j21;
                        j4 = j18;
                    }
                }
                String str17 = str16;
                long j23 = j4;
                long j24 = j3;
                dumpControllerActivityLine(
                        printWriter,
                        keyAt,
                        str,
                        BLUETOOTH_CONTROLLER_DATA,
                        uid2.getBluetoothControllerActivity(),
                        i);
                if (uid2.hasUserActivity()) {
                    Object[] objArr11 = new Object[Uid.NUM_USER_ACTIVITY_TYPES];
                    boolean z2 = false;
                    for (int i29 = 0; i29 < Uid.NUM_USER_ACTIVITY_TYPES; i29++) {
                        int userActivityCount = uid2.getUserActivityCount(i29, i7);
                        objArr11[i29] = Integer.valueOf(userActivityCount);
                        if (userActivityCount != 0) {
                            z2 = true;
                        }
                    }
                    if (z2) {
                        dumpLine(printWriter, keyAt, str, USER_ACTIVITY_DATA, objArr11);
                    }
                }
                if (uid2.getAggregatedPartialWakelockTimer() != null) {
                    Timer aggregatedPartialWakelockTimer = uid2.getAggregatedPartialWakelockTimer();
                    long totalDurationMsLocked4 =
                            aggregatedPartialWakelockTimer.getTotalDurationMsLocked(j23);
                    Timer subTimer = aggregatedPartialWakelockTimer.getSubTimer();
                    dumpLine(
                            printWriter,
                            keyAt,
                            str,
                            AGGREGATED_WAKELOCK_DATA,
                            Long.valueOf(totalDurationMsLocked4),
                            Long.valueOf(
                                    subTimer != null
                                            ? subTimer.getTotalDurationMsLocked(j23)
                                            : 0L));
                }
                ArrayMap<String, ? extends Uid.Wakelock> wakelockStats2 = uid2.getWakelockStats();
                int size3 = wakelockStats2.size() - 1;
                while (size3 >= 0) {
                    Uid.Wakelock valueAt4 = wakelockStats2.valueAt(size3);
                    sb6.setLength(0);
                    String str18 = str17;
                    long j25 = j23;
                    int i30 = size3;
                    ArrayMap<String, ? extends Uid.Wakelock> arrayMap8 = wakelockStats2;
                    SparseArray<? extends Uid> sparseArray6 = sparseArray5;
                    Map<String, ? extends Timer> map3 = map2;
                    String printWakeLockCheckin =
                            printWakeLockCheckin(
                                    sb6,
                                    valueAt4.getWakeTime(1),
                                    j24,
                                    FullBackup.FILES_TREE_TOKEN,
                                    i,
                                    "");
                    Timer wakeTime3 = valueAt4.getWakeTime(0);
                    printWakeLockCheckin(
                            sb6,
                            valueAt4.getWakeTime(2),
                            j24,
                            "w",
                            i,
                            printWakeLockCheckin(
                                    sb6,
                                    wakeTime3 != null ? wakeTime3.getSubTimer() : null,
                                    j24,
                                    "bp",
                                    i,
                                    printWakeLockCheckin(
                                            sb6, wakeTime3, j24, "p", i, printWakeLockCheckin)));
                    if (sb6.length() > 0) {
                        String keyAt2 = arrayMap8.keyAt(i30);
                        if (keyAt2.indexOf(44) < 0) {
                            c = '_';
                        } else {
                            c = '_';
                            keyAt2 = keyAt2.replace(',', '_');
                        }
                        if (keyAt2.indexOf(10) >= 0) {
                            keyAt2 = keyAt2.replace('\n', c);
                        }
                        if (keyAt2.indexOf(13) >= 0) {
                            keyAt2 = keyAt2.replace('\r', c);
                        }
                        dumpLine(printWriter, keyAt, str, "wl", keyAt2, sb6.toString());
                    }
                    size3 = i30 - 1;
                    wakelockStats2 = arrayMap8;
                    str17 = str18;
                    sparseArray5 = sparseArray6;
                    map2 = map3;
                    j23 = j25;
                }
                String str19 = str17;
                sparseArray2 = sparseArray5;
                long j26 = j23;
                map = map2;
                ArrayMap<String, ? extends Uid.Wakelock> arrayMap9 = wakelockStats2;
                Timer multicastWakelockStats = uid2.getMulticastWakelockStats();
                if (multicastWakelockStats != null) {
                    j5 = j24;
                    i7 = i;
                    long totalTimeLocked4 =
                            multicastWakelockStats.getTotalTimeLocked(j5, i7) / 1000;
                    int countLocked5 = multicastWakelockStats.getCountLocked(i7);
                    if (totalTimeLocked4 > 0) {
                        dumpLine(
                                printWriter,
                                keyAt,
                                str,
                                WIFI_MULTICAST_DATA,
                                Long.valueOf(totalTimeLocked4),
                                Integer.valueOf(countLocked5));
                    }
                } else {
                    j5 = j24;
                    i7 = i;
                }
                ArrayMap<String, ? extends Timer> syncStats = uid2.getSyncStats();
                int size4 = syncStats.size() - 1;
                while (size4 >= 0) {
                    Timer valueAt5 = syncStats.valueAt(size4);
                    long totalTimeLocked5 = (valueAt5.getTotalTimeLocked(j5, i7) + 500) / 1000;
                    int countLocked6 = valueAt5.getCountLocked(i7);
                    Timer subTimer2 = valueAt5.getSubTimer();
                    if (subTimer2 != null) {
                        sb4 = sb6;
                        j11 = j26;
                        j12 = subTimer2.getTotalDurationMsLocked(j11);
                    } else {
                        sb4 = sb6;
                        j11 = j26;
                        j12 = -1;
                    }
                    int countLocked7 = subTimer2 != null ? subTimer2.getCountLocked(i7) : -1;
                    if (totalTimeLocked5 == 0) {
                        timer = multicastWakelockStats;
                        arrayMap5 = arrayMap9;
                        str8 = str19;
                        arrayMap6 = syncStats;
                    } else {
                        timer = multicastWakelockStats;
                        arrayMap5 = arrayMap9;
                        str8 = str19;
                        arrayMap6 = syncStats;
                        dumpLine(
                                printWriter,
                                keyAt,
                                str,
                                SYNC_DATA,
                                str8 + syncStats.keyAt(size4) + str8,
                                Long.valueOf(totalTimeLocked5),
                                Integer.valueOf(countLocked6),
                                Long.valueOf(j12),
                                Integer.valueOf(countLocked7));
                    }
                    size4--;
                    j26 = j11;
                    syncStats = arrayMap6;
                    sb6 = sb4;
                    multicastWakelockStats = timer;
                    str19 = str8;
                    arrayMap9 = arrayMap5;
                }
                StringBuilder sb7 = sb6;
                String str20 = str19;
                long j27 = j26;
                ArrayMap<String, ? extends Timer> jobStats = uid2.getJobStats();
                int size5 = jobStats.size() - 1;
                while (size5 >= 0) {
                    Timer valueAt6 = jobStats.valueAt(size5);
                    long totalTimeLocked6 = (valueAt6.getTotalTimeLocked(j5, i7) + 500) / 1000;
                    int countLocked8 = valueAt6.getCountLocked(i7);
                    long j28 = j5;
                    Timer subTimer3 = valueAt6.getSubTimer();
                    long totalDurationMsLocked5 =
                            subTimer3 != null ? subTimer3.getTotalDurationMsLocked(j27) : -1L;
                    int countLocked9 = subTimer3 != null ? subTimer3.getCountLocked(i7) : -1;
                    if (totalTimeLocked6 == 0) {
                        arrayMap4 = jobStats;
                    } else {
                        arrayMap4 = jobStats;
                        dumpLine(
                                printWriter,
                                keyAt,
                                str,
                                JOB_DATA,
                                str20 + jobStats.keyAt(size5) + str20,
                                Long.valueOf(totalTimeLocked6),
                                Integer.valueOf(countLocked8),
                                Long.valueOf(totalDurationMsLocked5),
                                Integer.valueOf(countLocked9));
                    }
                    size5--;
                    j5 = j28;
                    jobStats = arrayMap4;
                }
                long j29 = j5;
                int[] jobStopReasonCodes = JobParameters.getJobStopReasonCodes();
                Object[] objArr12 = new Object[jobStopReasonCodes.length + 1];
                ArrayMap<String, SparseIntArray> jobCompletionStats = uid2.getJobCompletionStats();
                int size6 = jobCompletionStats.size() - 1;
                while (size6 >= 0) {
                    SparseIntArray valueAt7 = jobCompletionStats.valueAt(size6);
                    if (valueAt7 == null) {
                        arrayMap3 = jobCompletionStats;
                    } else {
                        int i31 = 0;
                        objArr12[0] = str20 + jobCompletionStats.keyAt(size6) + str20;
                        int i32 = 0;
                        while (i32 < jobStopReasonCodes.length) {
                            objArr12[i32 + 1] =
                                    Integer.valueOf(valueAt7.get(jobStopReasonCodes[i32], i31));
                            i32++;
                            jobCompletionStats = jobCompletionStats;
                            i31 = 0;
                        }
                        arrayMap3 = jobCompletionStats;
                        dumpLine(printWriter, keyAt, str, JOB_COMPLETION_DATA, objArr12);
                    }
                    size6--;
                    jobCompletionStats = arrayMap3;
                }
                uid2.getDeferredJobsCheckinLineLocked(sb7, i7);
                if (sb7.length() > 0) {
                    dumpLine(printWriter, keyAt, str, JOBS_DEFERRED_DATA, sb7.toString());
                }
                long j30 = j29;
                StringBuilder sb8 = sb7;
                String str21 = str;
                Integer num5 = num;
                String str22 = str20;
                Integer num6 = num5;
                dumpTimer(
                        printWriter,
                        keyAt,
                        str,
                        FLASHLIGHT_DATA,
                        uid2.getFlashlightTurnedOnTimer(),
                        j30,
                        i);
                dumpTimer(
                        printWriter,
                        keyAt,
                        str21,
                        CAMERA_DATA,
                        uid2.getCameraTurnedOnTimer(),
                        j30,
                        i);
                dumpTimer(printWriter, keyAt, str21, "vid", uid2.getVideoTurnedOnTimer(), j30, i);
                dumpTimer(
                        printWriter,
                        keyAt,
                        str21,
                        AUDIO_DATA,
                        uid2.getAudioTurnedOnTimer(),
                        j30,
                        i);
                SparseArray<? extends Uid.Sensor> sensorStats = uid2.getSensorStats();
                int size7 = sensorStats.size();
                int i33 = 0;
                while (i33 < size7) {
                    Uid.Sensor valueAt8 = sensorStats.valueAt(i33);
                    int keyAt3 = sensorStats.keyAt(i33);
                    Timer sensorTime = valueAt8.getSensorTime();
                    if (sensorTime != null) {
                        i5 = size7;
                        long j31 = j30;
                        long totalTimeLocked7 =
                                (sensorTime.getTotalTimeLocked(j31, i7) + 500) / 1000;
                        if (totalTimeLocked7 != 0) {
                            int countLocked10 = sensorTime.getCountLocked(i7);
                            j10 = j31;
                            Timer sensorBackgroundTime = valueAt8.getSensorBackgroundTime();
                            int countLocked11 =
                                    sensorBackgroundTime != null
                                            ? sensorBackgroundTime.getCountLocked(i7)
                                            : 0;
                            long totalDurationMsLocked6 = sensorTime.getTotalDurationMsLocked(j27);
                            long totalDurationMsLocked7 =
                                    sensorBackgroundTime != null
                                            ? sensorBackgroundTime.getTotalDurationMsLocked(j27)
                                            : 0L;
                            j9 = j27;
                            str7 = str21;
                            dumpLine(
                                    printWriter,
                                    keyAt,
                                    str7,
                                    SENSOR_DATA,
                                    Integer.valueOf(keyAt3),
                                    Long.valueOf(totalTimeLocked7),
                                    Integer.valueOf(countLocked10),
                                    Integer.valueOf(countLocked11),
                                    Long.valueOf(totalDurationMsLocked6),
                                    Long.valueOf(totalDurationMsLocked7));
                        } else {
                            j10 = j31;
                            j9 = j27;
                            str7 = str21;
                        }
                    } else {
                        i5 = size7;
                        j9 = j27;
                        j10 = j30;
                        str7 = str21;
                    }
                    i33++;
                    str21 = str7;
                    size7 = i5;
                    j30 = j10;
                    j27 = j9;
                }
                j6 = j27;
                str4 = str21;
                long j32 = j30;
                dumpTimer(
                        printWriter, keyAt, str4, VIBRATOR_DATA, uid2.getVibratorOnTimer(), j32, i);
                dumpTimer(
                        printWriter,
                        keyAt,
                        str4,
                        FOREGROUND_ACTIVITY_DATA,
                        uid2.getForegroundActivityTimer(),
                        j32,
                        i);
                dumpTimer(
                        printWriter,
                        keyAt,
                        str4,
                        FOREGROUND_SERVICE_DATA,
                        uid2.getForegroundServiceTimer(),
                        j32,
                        i);
                Object[] objArr13 = new Object[7];
                long j33 = 0;
                int i34 = 0;
                for (int i35 = 7; i34 < i35; i35 = 7) {
                    long j34 = j30;
                    long processStateTime = uid2.getProcessStateTime(i34, j34, i7);
                    j33 += processStateTime;
                    objArr13[i34] = Long.valueOf((processStateTime + 500) / 1000);
                    i34++;
                    j30 = j34;
                }
                long j35 = j30;
                if (j33 > 0) {
                    dumpLine(printWriter, keyAt, str4, "st", objArr13);
                }
                long userCpuTimeUs = uid2.getUserCpuTimeUs(i7);
                long systemCpuTimeUs = uid2.getSystemCpuTimeUs(i7);
                if (userCpuTimeUs > 0 || systemCpuTimeUs > 0) {
                    dumpLine(
                            printWriter,
                            keyAt,
                            str4,
                            CPU_DATA,
                            Long.valueOf(userCpuTimeUs / 1000),
                            Long.valueOf(systemCpuTimeUs / 1000),
                            num6);
                }
                if (cpuScalingPolicies == null) {
                    objArr = objArr13;
                    j7 = j35;
                } else {
                    long[] cpuFreqTimes = uid2.getCpuFreqTimes(i7);
                    if (cpuFreqTimes == null) {
                        objArr2 = objArr13;
                        j7 = j35;
                    } else if (cpuFreqTimes.length != cpuScalingPolicies.getScalingStepCount()) {
                        objArr2 = objArr13;
                        j7 = j35;
                    } else {
                        sb8.setLength(0);
                        int i36 = 0;
                        while (i36 < cpuFreqTimes.length) {
                            if (i36 != 0) {
                                sb8.append(',');
                            }
                            sb8.append(cpuFreqTimes[i36]);
                            i36++;
                            objArr13 = objArr13;
                            j33 = j33;
                        }
                        objArr2 = objArr13;
                        long[] screenOffCpuFreqTimes = uid2.getScreenOffCpuFreqTimes(i7);
                        if (screenOffCpuFreqTimes != null) {
                            int i37 = 0;
                            while (i37 < screenOffCpuFreqTimes.length) {
                                sb8.append(',').append(screenOffCpuFreqTimes[i37]);
                                i37++;
                                j35 = j35;
                            }
                            j7 = j35;
                        } else {
                            j7 = j35;
                            for (int i38 = 0; i38 < cpuFreqTimes.length; i38++) {
                                sb8.append(",0");
                            }
                        }
                        dumpLine(
                                printWriter,
                                keyAt,
                                str4,
                                CPU_TIMES_AT_FREQ_DATA,
                                "A",
                                Integer.valueOf(cpuFreqTimes.length),
                                sb8.toString());
                    }
                    long[] jArr = new long[getCpuScalingPolicies().getScalingStepCount()];
                    int i39 = 0;
                    while (i39 < 7) {
                        if (!uid2.getCpuFreqTimes(jArr, i39)) {
                            objArr3 = objArr2;
                        } else {
                            sb8.setLength(0);
                            for (int i40 = 0; i40 < jArr.length; i40++) {
                                if (i40 != 0) {
                                    sb8.append(',');
                                }
                                sb8.append(jArr[i40]);
                            }
                            if (uid2.getScreenOffCpuFreqTimes(jArr, i39)) {
                                int i41 = 0;
                                while (i41 < jArr.length) {
                                    sb8.append(',').append(jArr[i41]);
                                    i41++;
                                    objArr2 = objArr2;
                                }
                                objArr3 = objArr2;
                            } else {
                                objArr3 = objArr2;
                                for (int i42 = 0; i42 < jArr.length; i42++) {
                                    sb8.append(",0");
                                }
                            }
                            dumpLine(
                                    printWriter,
                                    keyAt,
                                    str4,
                                    CPU_TIMES_AT_FREQ_DATA,
                                    Uid.UID_PROCESS_TYPES[i39],
                                    Integer.valueOf(jArr.length),
                                    sb8.toString());
                        }
                        i39++;
                        objArr2 = objArr3;
                    }
                    objArr = objArr2;
                }
                ArrayMap<String, ? extends Uid.Proc> processStats = uid2.getProcessStats();
                int size8 = processStats.size() - 1;
                while (size8 >= 0) {
                    Uid.Proc valueAt9 = processStats.valueAt(size8);
                    long userTime = valueAt9.getUserTime(i7);
                    long systemTime = valueAt9.getSystemTime(i7);
                    long foregroundTime = valueAt9.getForegroundTime(i7);
                    int starts = valueAt9.getStarts(i7);
                    int numCrashes = valueAt9.getNumCrashes(i7);
                    int numAnrs = valueAt9.getNumAnrs(i7);
                    if (userTime == 0
                            && systemTime == 0
                            && foregroundTime == 0
                            && starts == 0
                            && numAnrs == 0
                            && numCrashes == 0) {
                        sb3 = sb8;
                        arrayMap2 = processStats;
                        str6 = str22;
                    } else {
                        sb3 = sb8;
                        str6 = str22;
                        arrayMap2 = processStats;
                        dumpLine(
                                printWriter,
                                keyAt,
                                str4,
                                PROCESS_DATA,
                                str6 + processStats.keyAt(size8) + str6,
                                Long.valueOf(userTime),
                                Long.valueOf(systemTime),
                                Long.valueOf(foregroundTime),
                                Integer.valueOf(starts),
                                Integer.valueOf(numAnrs),
                                Integer.valueOf(numCrashes));
                    }
                    size8--;
                    str22 = str6;
                    sb8 = sb3;
                    processStats = arrayMap2;
                }
                sb2 = sb8;
                String str23 = str22;
                ArrayMap<String, ? extends Uid.Pkg> packageStats = uid2.getPackageStats();
                int size9 = packageStats.size() - 1;
                while (size9 >= 0) {
                    Uid.Pkg valueAt10 = packageStats.valueAt(size9);
                    int i43 = 0;
                    ArrayMap<String, ? extends Counter> wakeupAlarmStats =
                            valueAt10.getWakeupAlarmStats();
                    int size10 = wakeupAlarmStats.size() - 1;
                    while (size10 >= 0) {
                        int countLocked12 = wakeupAlarmStats.valueAt(size10).getCountLocked(i7);
                        i43 += countLocked12;
                        dumpLine(
                                printWriter,
                                keyAt,
                                str4,
                                WAKEUP_ALARM_DATA,
                                wakeupAlarmStats.keyAt(size10).replace(',', '_'),
                                Integer.valueOf(countLocked12));
                        size10--;
                        wakeupAlarmStats = wakeupAlarmStats;
                        objArr = objArr;
                        str23 = str23;
                    }
                    String str24 = str23;
                    Object[] objArr14 = objArr;
                    ArrayMap<String, ? extends Uid.Pkg.Serv> serviceStats =
                            valueAt10.getServiceStats();
                    int size11 = serviceStats.size() - 1;
                    while (size11 >= 0) {
                        Uid.Pkg.Serv valueAt11 = serviceStats.valueAt(size11);
                        Uid uid3 = uid2;
                        Integer num7 = num6;
                        long j36 = j17;
                        long startTime = valueAt11.getStartTime(j36, i7);
                        int starts2 = valueAt11.getStarts(i7);
                        int launches = valueAt11.getLaunches(i7);
                        if (startTime == 0 && starts2 == 0 && launches == 0) {
                            arrayMap = packageStats;
                        } else {
                            arrayMap = packageStats;
                            dumpLine(
                                    printWriter,
                                    keyAt,
                                    str4,
                                    APK_DATA,
                                    Integer.valueOf(i43),
                                    packageStats.keyAt(size9),
                                    serviceStats.keyAt(size11),
                                    Long.valueOf(startTime / 1000),
                                    Integer.valueOf(starts2),
                                    Integer.valueOf(launches));
                        }
                        size11--;
                        j17 = j36;
                        num6 = num7;
                        packageStats = arrayMap;
                        uid2 = uid3;
                    }
                    size9--;
                    num6 = num6;
                    objArr = objArr14;
                    str23 = str24;
                    uid2 = uid2;
                }
                str5 = str23;
                num2 = num6;
                j8 = j17;
            }
            j17 = j8;
            str = str4;
            num = num2;
            map2 = map;
            str2 = str5;
            aggregateBatteryConsumer3 = aggregateBatteryConsumer;
            list2 = list;
            i10 = i4;
            j2 = j7;
            sb5 = sb2;
            i27 = i3 + 1;
            sparseArray = sparseArray2;
            cpuScalingPolicies2 = cpuScalingPolicies;
            j18 = j6;
        }
    }

    static final class TimerEntry {
        final int mId;
        final String mName;
        final long mTime;
        final Timer mTimer;

        TimerEntry(String name, int id, Timer timer, long time) {
            this.mName = name;
            this.mId = id;
            this.mTimer = timer;
            this.mTime = time;
        }
    }

    private void printmAh(PrintWriter printer, double power) {
        printer.print(formatCharge(power));
    }

    private void printmAh(StringBuilder sb, double power) {
        sb.append(formatCharge(power));
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dumpLocked(
            android.content.Context r243,
            java.io.PrintWriter r244,
            java.lang.String r245,
            int r246,
            int r247,
            boolean r248,
            android.os.BatteryStats.BatteryStatsDumpHelper r249) {
        /*
            Method dump skipped, instructions count: 10967
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.os.BatteryStats.dumpLocked(android.content.Context,"
                    + " java.io.PrintWriter, java.lang.String, int, int, boolean,"
                    + " android.os.BatteryStats$BatteryStatsDumpHelper):void");
    }

    static void printBitDescriptions(
            StringBuilder sb,
            int oldval,
            int newval,
            HistoryTag wakelockTag,
            BitDescription[] descriptions,
            boolean longNames) {
        int diff = oldval ^ newval;
        if (diff == 0) {
            return;
        }
        boolean didWake = false;
        for (BitDescription bd : descriptions) {
            if ((bd.mask & diff) != 0) {
                sb.append(longNames ? " " : ",");
                if (bd.shift < 0) {
                    sb.append(
                            (bd.mask & newval) != 0 ? "+" : NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                    sb.append(longNames ? bd.name : bd.shortName);
                    if (bd.mask == 1073741824 && wakelockTag != null) {
                        didWake = true;
                        sb.append("=");
                        if (longNames || wakelockTag.poolIdx == -1) {
                            UserHandle.formatUid(sb, wakelockTag.uid);
                            sb.append(":\"");
                            sb.append(wakelockTag.string.replace("\"", "\"\""));
                            sb.append("\"");
                        } else {
                            sb.append(wakelockTag.poolIdx);
                        }
                    }
                } else {
                    sb.append(longNames ? bd.name : bd.shortName);
                    sb.append("=");
                    int val = (bd.mask & newval) >> bd.shift;
                    if (bd.values != null && val >= 0 && val < bd.values.length) {
                        sb.append(longNames ? bd.values[val] : bd.shortValues[val]);
                    } else {
                        sb.append(val);
                    }
                }
            }
        }
        if (!didWake && wakelockTag != null) {
            sb.append(longNames ? " wake_lock=" : ",w=");
            if (longNames || wakelockTag.poolIdx == -1) {
                UserHandle.formatUid(sb, wakelockTag.uid);
                sb.append(":\"");
                sb.append(wakelockTag.string);
                sb.append("\"");
                return;
            }
            sb.append(wakelockTag.poolIdx);
        }
    }

    public void prepareForDumpLocked() {}

    public static class HistoryPrinter {
        int oldState = 0;
        int oldState2 = 0;
        int oldLevel = -1;
        int oldStatus = -1;
        int oldHealth = -1;
        int oldPlug = -1;
        int oldTemp = -1;
        int oldVolt = -1;
        int oldCurrent = -1;
        int oldAp_temp = -1;
        int oldPa_temp = -1;
        int oldSub_batt_temp = -1;
        int oldSkin_temp = -1;
        int oldWifi_ap = -1;
        int oldOtgOnline = -1;
        int oldHighSpeakerVolume = -1;
        int oldSubScreenOn = -1;
        int oldSubScreenDoze = -1;
        int oldSecTxShareEvent = -1;
        int oldSecOnline = -1;
        int oldSecCurrentEvent = -1;
        int oldSecEvent = -1;
        int oldProtectBatteryMode = -1;
        int oldChargeMAh = -1;
        double oldModemRailChargeMah = -1.0d;
        double oldWifiRailChargeMah = -1.0d;
        long lastTime = -1;

        void reset() {
            this.oldState2 = 0;
            this.oldState = 0;
            this.oldLevel = -1;
            this.oldStatus = -1;
            this.oldHealth = -1;
            this.oldPlug = -1;
            this.oldTemp = -1;
            this.oldVolt = -1;
            this.oldCurrent = -1;
            this.oldAp_temp = -1;
            this.oldPa_temp = -1;
            this.oldSub_batt_temp = -1;
            this.oldSkin_temp = -1;
            this.oldWifi_ap = -1;
            this.oldOtgOnline = -1;
            this.oldHighSpeakerVolume = -1;
            this.oldSubScreenOn = -1;
            this.oldSubScreenDoze = -1;
            this.oldSecTxShareEvent = -1;
            this.oldSecOnline = -1;
            this.oldSecCurrentEvent = -1;
            this.oldSecEvent = -1;
            this.oldProtectBatteryMode = -1;
            this.oldChargeMAh = -1;
            this.oldModemRailChargeMah = -1.0d;
            this.oldWifiRailChargeMah = -1.0d;
        }

        public void printNextItem(
                PrintWriter pw, HistoryItem rec, long baseTime, boolean checkin, boolean verbose) {
            pw.print(printNextItem(rec, baseTime, checkin, verbose));
        }

        public void printNextItem(
                ProtoOutputStream proto, HistoryItem rec, long baseTime, boolean verbose) {
            String item = printNextItem(rec, baseTime, true, verbose);
            for (String line : item.split("\n")) {
                proto.write(2237677961222L, line);
            }
        }

        private String printNextItem(
                HistoryItem rec, long baseTime, boolean checkin, boolean verbose) {
            StringBuilder item = new StringBuilder();
            if (!checkin) {
                item.append("  ");
                TimeUtils.formatDuration(rec.time - baseTime, item, 19);
                item.append(" (");
                item.append(rec.numReadInts);
                item.append(") ");
            } else {
                item.append(9);
                item.append(',');
                item.append(BatteryStats.HISTORY_DATA);
                item.append(',');
                if (this.lastTime >= 0) {
                    item.append(rec.time - this.lastTime);
                } else {
                    item.append(rec.time - baseTime);
                }
                this.lastTime = rec.time;
            }
            if (rec.cmd == 4) {
                if (checkin) {
                    item.append(":");
                }
                item.append("START\n");
                reset();
            } else {
                if (rec.cmd == 5 || rec.cmd == 7) {
                    if (checkin) {
                        item.append(":");
                    }
                    if (rec.cmd == 7) {
                        item.append("RESET:");
                        reset();
                    }
                    item.append("TIME:");
                    if (checkin) {
                        item.append(rec.currentTime);
                        item.append("\n");
                    } else {
                        item.append(" ");
                        item.append(
                                new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.ENGLISH)
                                        .format(Long.valueOf(rec.currentTime)));
                        item.append("\n");
                    }
                } else if (rec.cmd == 8) {
                    if (checkin) {
                        item.append(":");
                    }
                    item.append("SHUTDOWN\n");
                } else if (rec.cmd == 6) {
                    if (checkin) {
                        item.append(":");
                    }
                    item.append("*OVERFLOW*\n");
                } else {
                    if (!checkin) {
                        if (rec.batteryLevel < 10) {
                            item.append("00");
                        } else if (rec.batteryLevel < 100) {
                            item.append("0");
                        }
                        item.append(rec.batteryLevel);
                        if (verbose) {
                            item.append(" ");
                            if (rec.states >= 0) {
                                if (rec.states < 16) {
                                    item.append("0000000");
                                } else if (rec.states < 256) {
                                    item.append("000000");
                                } else if (rec.states < 4096) {
                                    item.append("00000");
                                } else if (rec.states < 65536) {
                                    item.append("0000");
                                } else if (rec.states < 1048576) {
                                    item.append("000");
                                } else if (rec.states < 16777216) {
                                    item.append("00");
                                } else if (rec.states < 268435456) {
                                    item.append("0");
                                }
                            }
                            item.append(Integer.toHexString(rec.states));
                        }
                    } else if (this.oldLevel != rec.batteryLevel) {
                        this.oldLevel = rec.batteryLevel;
                        item.append(",Bl=");
                        item.append(rec.batteryLevel);
                    }
                    if (this.oldStatus != rec.batteryStatus) {
                        this.oldStatus = rec.batteryStatus;
                        item.append(checkin ? ",Bs=" : " status=");
                        switch (this.oldStatus) {
                            case 1:
                                item.append(checkin ? "?" : "unknown");
                                break;
                            case 2:
                                item.append(checkin ? "c" : UsbManager.USB_FUNCTION_CHARGING);
                                break;
                            case 3:
                                item.append(checkin ? XmlTags.ATTR_DESCRIPTION : "discharging");
                                break;
                            case 4:
                                item.append(checkin ? "n" : "not-charging");
                                break;
                            case 5:
                                item.append(checkin ? FullBackup.FILES_TREE_TOKEN : "full");
                                break;
                            default:
                                item.append(this.oldStatus);
                                break;
                        }
                    }
                    if (this.oldHealth != rec.batteryHealth) {
                        this.oldHealth = rec.batteryHealth;
                        item.append(checkin ? ",Bh=" : " health=");
                        switch (this.oldHealth) {
                            case 1:
                                item.append(checkin ? "?" : "unknown");
                                break;
                            case 2:
                                item.append(checkin ? "g" : "good");
                                break;
                            case 3:
                                item.append(checkin ? BatteryStats.HISTORY_DATA : "overheat");
                                break;
                            case 4:
                                item.append(checkin ? XmlTags.ATTR_DESCRIPTION : "dead");
                                break;
                            case 5:
                                item.append(checkin ? "v" : "over-voltage");
                                break;
                            case 6:
                                item.append(checkin ? FullBackup.FILES_TREE_TOKEN : "failure");
                                break;
                            case 7:
                                item.append(checkin ? "c" : "cold");
                                break;
                            case 8:
                                item.append(checkin ? XmlTags.TAG_LEASEE : "over-limit");
                                break;
                            case 9:
                                item.append(checkin ? XmlTags.ATTR_UID : "under-voltage");
                                break;
                            default:
                                item.append(this.oldHealth);
                                break;
                        }
                    }
                    if (this.oldPlug != rec.batteryPlugType) {
                        this.oldPlug = rec.batteryPlugType;
                        item.append(checkin ? ",Bp=" : " plug=");
                        switch (this.oldPlug) {
                            case 0:
                                item.append(checkin ? "n" : "none");
                                break;
                            case 1:
                                item.append(checkin ? FullBackup.APK_TREE_TOKEN : "ac");
                                break;
                            case 2:
                                item.append(checkin ? XmlTags.ATTR_UID : "usb");
                                break;
                            case 3:
                            default:
                                item.append(this.oldPlug);
                                break;
                            case 4:
                                item.append(
                                        checkin ? "w" : AudioDeviceDescription.CONNECTION_WIRELESS);
                                break;
                        }
                    }
                    if (this.oldTemp != rec.batteryTemperature) {
                        this.oldTemp = rec.batteryTemperature;
                        item.append(checkin ? ",Bt=" : " temp=");
                        item.append(this.oldTemp);
                    }
                    if (this.oldVolt != rec.batteryVoltage) {
                        this.oldVolt = rec.batteryVoltage;
                        item.append(checkin ? ",Bv=" : " volt=");
                        item.append(this.oldVolt);
                    }
                    if (!checkin) {
                        boolean mChanged = false;
                        boolean isApTempValid = rec.ap_temp != Byte.MIN_VALUE;
                        boolean isPaTempValid = rec.pa_temp != Byte.MIN_VALUE;
                        boolean isSkinTempValid = rec.skin_temp != Byte.MIN_VALUE;
                        boolean isSubBattTempValid = rec.sub_batt_temp != Byte.MIN_VALUE;
                        if (this.oldCurrent != rec.current) {
                            this.oldCurrent = rec.current;
                            mChanged = true;
                        }
                        if (this.oldAp_temp != rec.ap_temp) {
                            this.oldAp_temp = rec.ap_temp;
                            mChanged = true;
                        }
                        if (this.oldPa_temp != rec.pa_temp) {
                            this.oldPa_temp = rec.pa_temp;
                            mChanged = true;
                        }
                        if (this.oldSkin_temp != rec.skin_temp) {
                            this.oldSkin_temp = rec.skin_temp;
                            mChanged = true;
                        }
                        if (this.oldSub_batt_temp != rec.sub_batt_temp) {
                            this.oldSub_batt_temp = rec.sub_batt_temp;
                            mChanged = true;
                        }
                        if (mChanged) {
                            item.append(" current=");
                            item.append(this.oldCurrent);
                            if (isApTempValid) {
                                item.append(" ap_temp=");
                                item.append(this.oldAp_temp);
                            }
                            if (isPaTempValid) {
                                item.append(" pa_temp=");
                                item.append(this.oldPa_temp);
                            }
                            if (isSkinTempValid) {
                                item.append(" skin_temp=");
                                item.append(this.oldSkin_temp);
                            }
                            if (isSubBattTempValid) {
                                item.append(" sub_batt_temp=");
                                item.append(this.oldSub_batt_temp);
                            }
                        }
                    }
                    if (!checkin) {
                        boolean mChanged2 = false;
                        if (this.oldWifi_ap != rec.wifi_ap) {
                            this.oldWifi_ap = rec.wifi_ap;
                            mChanged2 = true;
                        }
                        if (mChanged2) {
                            if (this.oldWifi_ap == 1) {
                                item.append(" +");
                            } else {
                                item.append(" -");
                            }
                            item.append("wifi_ap");
                        }
                    }
                    if (!checkin) {
                        boolean mChanged3 = false;
                        if (this.oldOtgOnline != rec.otgOnline) {
                            this.oldOtgOnline = rec.otgOnline;
                            mChanged3 = true;
                        }
                        if (mChanged3) {
                            if (this.oldOtgOnline == 1) {
                                item.append(" +");
                            } else {
                                item.append(" -");
                            }
                            item.append("otg");
                        }
                    }
                    if (!checkin) {
                        boolean mChanged4 = false;
                        if (this.oldHighSpeakerVolume != rec.highSpeakerVolume) {
                            this.oldHighSpeakerVolume = rec.highSpeakerVolume;
                            mChanged4 = true;
                        }
                        if (mChanged4) {
                            if (this.oldHighSpeakerVolume == 1) {
                                item.append(" +");
                            } else {
                                item.append(" -");
                            }
                            item.append("high_speaker_volume");
                        }
                    }
                    if (!checkin) {
                        boolean mChanged5 = false;
                        if (this.oldSubScreenOn != rec.subScreenOn) {
                            this.oldSubScreenOn = rec.subScreenOn;
                            mChanged5 = true;
                        }
                        if (mChanged5) {
                            if (this.oldSubScreenOn == 1) {
                                item.append(" +");
                            } else {
                                item.append(" -");
                            }
                            item.append("sub_screen");
                        }
                    }
                    if (!checkin) {
                        boolean mChanged6 = false;
                        if (this.oldSubScreenDoze != rec.subScreenDoze) {
                            this.oldSubScreenDoze = rec.subScreenDoze;
                            mChanged6 = true;
                        }
                        if (mChanged6) {
                            if (this.oldSubScreenDoze == 1) {
                                item.append(" +");
                            } else {
                                item.append(" -");
                            }
                            item.append("sub_screen_doze");
                        }
                    }
                    if (!checkin) {
                        boolean mChanged7 = false;
                        if (this.oldSecTxShareEvent != rec.batterySecTxShareEvent) {
                            this.oldSecTxShareEvent = rec.batterySecTxShareEvent;
                            mChanged7 = true;
                        }
                        if (this.oldSecOnline != rec.batterySecOnline) {
                            this.oldSecOnline = rec.batterySecOnline;
                            mChanged7 = true;
                        }
                        if (this.oldSecCurrentEvent != rec.batterySecCurrentEvent) {
                            this.oldSecCurrentEvent = rec.batterySecCurrentEvent;
                            mChanged7 = true;
                        }
                        if (this.oldSecEvent != rec.batterySecEvent) {
                            this.oldSecEvent = rec.batterySecEvent;
                            mChanged7 = true;
                        }
                        if (mChanged7) {
                            item.append(" txshare_event=");
                            item.append(
                                    String.format(
                                            "0x%x", Integer.valueOf(this.oldSecTxShareEvent)));
                            item.append(" online=");
                            item.append(this.oldSecOnline);
                            item.append(" current_event=");
                            item.append(
                                    String.format(
                                            "0x%x", Integer.valueOf(this.oldSecCurrentEvent)));
                            item.append(" misc_event=");
                            item.append(String.format("0x%x", Integer.valueOf(this.oldSecEvent)));
                        }
                    }
                    if (!checkin && this.oldProtectBatteryMode != rec.protectBatteryMode) {
                        this.oldProtectBatteryMode = rec.protectBatteryMode;
                        if (this.oldProtectBatteryMode >= 0
                                && this.oldProtectBatteryMode
                                        < BatteryStats.PROTECT_BATTERY_MODE_TYPES.length) {
                            item.append(" pbm=");
                            item.append(
                                    BatteryStats.PROTECT_BATTERY_MODE_TYPES[
                                            this.oldProtectBatteryMode]);
                        }
                    }
                    int chargeMAh = rec.batteryChargeUah / 1000;
                    if (this.oldChargeMAh != chargeMAh) {
                        this.oldChargeMAh = chargeMAh;
                        item.append(checkin ? ",Bcc=" : " charge=");
                        item.append(this.oldChargeMAh);
                    }
                    if (this.oldModemRailChargeMah != rec.modemRailChargeMah) {
                        this.oldModemRailChargeMah = rec.modemRailChargeMah;
                        item.append(checkin ? ",Mrc=" : " modemRailChargemAh=");
                        item.append(new DecimalFormat("#.##").format(this.oldModemRailChargeMah));
                    }
                    if (this.oldWifiRailChargeMah != rec.wifiRailChargeMah) {
                        this.oldWifiRailChargeMah = rec.wifiRailChargeMah;
                        item.append(checkin ? ",Wrc=" : " wifiRailChargemAh=");
                        item.append(new DecimalFormat("#.##").format(this.oldWifiRailChargeMah));
                    }
                    BatteryStats.printBitDescriptions(
                            item,
                            this.oldState,
                            rec.states,
                            rec.wakelockTag,
                            BatteryStats.HISTORY_STATE_DESCRIPTIONS,
                            !checkin);
                    BatteryStats.printBitDescriptions(
                            item,
                            this.oldState2,
                            rec.states2,
                            null,
                            BatteryStats.HISTORY_STATE2_DESCRIPTIONS,
                            !checkin);
                    if (rec.wakeReasonTag != null) {
                        if (checkin) {
                            item.append(",wr=");
                            if (rec.wakeReasonTag.poolIdx == -1) {
                                item.append(
                                        BatteryStats.sUidToString.applyAsString(
                                                rec.wakeReasonTag.uid));
                                item.append(":\"");
                                item.append(rec.wakeReasonTag.string.replace("\"", "\"\""));
                                item.append("\"");
                            } else {
                                item.append(rec.wakeReasonTag.poolIdx);
                            }
                        } else {
                            item.append(" wake_reason=");
                            item.append(rec.wakeReasonTag.uid);
                            item.append(":\"");
                            item.append(rec.wakeReasonTag.string);
                            item.append("\"");
                        }
                    }
                    if (rec.eventCode != 0) {
                        item.append(checkin ? "," : " ");
                        if ((rec.eventCode & 32768) != 0) {
                            item.append("+");
                        } else if ((rec.eventCode & 16384) != 0) {
                            item.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                        }
                        String[] eventNames =
                                checkin
                                        ? BatteryStats.HISTORY_EVENT_CHECKIN_NAMES
                                        : BatteryStats.HISTORY_EVENT_NAMES;
                        int idx = rec.eventCode & HistoryItem.EVENT_TYPE_MASK;
                        if (idx >= 0 && idx < eventNames.length) {
                            item.append(eventNames[idx]);
                        } else {
                            item.append(checkin ? "Ev" : "event");
                            item.append(idx);
                        }
                        item.append("=");
                        if (checkin) {
                            if (rec.eventTag.poolIdx == -1) {
                                item.append(
                                        BatteryStats.HISTORY_EVENT_INT_FORMATTERS[idx]
                                                .applyAsString(rec.eventTag.uid));
                                item.append(":\"");
                                item.append(rec.eventTag.string.replace("\"", "\"\""));
                                item.append("\"");
                            } else {
                                item.append(rec.eventTag.poolIdx);
                            }
                        } else {
                            item.append(
                                    BatteryStats.HISTORY_EVENT_INT_FORMATTERS[idx].applyAsString(
                                            rec.eventTag.uid));
                            item.append(":\"");
                            item.append(rec.eventTag.string);
                            item.append("\"");
                        }
                    }
                    if (rec.powerStats != null && verbose && !checkin) {
                        item.append("\n                 Stats: ");
                        item.append(
                                rec.powerStats.formatForBatteryHistory("\n                    "));
                    }
                    if (rec.processStateChange != null && verbose && !checkin) {
                        item.append(" procstate: ");
                        item.append(rec.processStateChange.formatForBatteryHistory());
                    }
                    item.append("\n");
                    if (rec.stepDetails != null) {
                        if (!checkin) {
                            item.append("                 Details: cpu=");
                            item.append(rec.stepDetails.userTime);
                            item.append("u+");
                            item.append(rec.stepDetails.systemTime);
                            item.append(XmlTags.TAG_SESSION);
                            if (rec.stepDetails.appCpuUid1 >= 0) {
                                item.append(" (");
                                printStepCpuUidDetails(
                                        item,
                                        rec.stepDetails.appCpuUid1,
                                        rec.stepDetails.appCpuUTime1,
                                        rec.stepDetails.appCpuSTime1);
                                if (rec.stepDetails.appCpuUid2 >= 0) {
                                    item.append(", ");
                                    printStepCpuUidDetails(
                                            item,
                                            rec.stepDetails.appCpuUid2,
                                            rec.stepDetails.appCpuUTime2,
                                            rec.stepDetails.appCpuSTime2);
                                }
                                if (rec.stepDetails.appCpuUid3 >= 0) {
                                    item.append(", ");
                                    printStepCpuUidDetails(
                                            item,
                                            rec.stepDetails.appCpuUid3,
                                            rec.stepDetails.appCpuUTime3,
                                            rec.stepDetails.appCpuSTime3);
                                }
                                item.append(')');
                            }
                            item.append("\n");
                            item.append("                          /proc/stat=");
                            item.append(rec.stepDetails.statUserTime);
                            item.append(" usr, ");
                            item.append(rec.stepDetails.statSystemTime);
                            item.append(" sys, ");
                            item.append(rec.stepDetails.statIOWaitTime);
                            item.append(" io, ");
                            item.append(rec.stepDetails.statIrqTime);
                            item.append(" irq, ");
                            item.append(rec.stepDetails.statSoftIrqTime);
                            item.append(" sirq, ");
                            item.append(rec.stepDetails.statIdlTime);
                            item.append(" idle");
                            int totalRun =
                                    rec.stepDetails.statUserTime
                                            + rec.stepDetails.statSystemTime
                                            + rec.stepDetails.statIOWaitTime
                                            + rec.stepDetails.statIrqTime
                                            + rec.stepDetails.statSoftIrqTime;
                            int total = rec.stepDetails.statIdlTime + totalRun;
                            if (total > 0) {
                                item.append(" (");
                                float perc = (totalRun / total) * 100.0f;
                                item.append(String.format("%.1f%%", Float.valueOf(perc)));
                                item.append(" of ");
                                StringBuilder sb = new StringBuilder(64);
                                BatteryStats.formatTimeMsNoSpace(sb, total * 10);
                                item.append((CharSequence) sb);
                                item.append(NavigationBarInflaterView.KEY_CODE_END);
                            }
                            item.append(", SubsystemPowerState ");
                            item.append(rec.stepDetails.statSubsystemPowerState);
                            item.append("\n");
                        } else {
                            item.append(9);
                            item.append(',');
                            item.append(BatteryStats.HISTORY_DATA);
                            item.append(",0,Dcpu=");
                            item.append(rec.stepDetails.userTime);
                            item.append(":");
                            item.append(rec.stepDetails.systemTime);
                            if (rec.stepDetails.appCpuUid1 >= 0) {
                                printStepCpuUidCheckinDetails(
                                        item,
                                        rec.stepDetails.appCpuUid1,
                                        rec.stepDetails.appCpuUTime1,
                                        rec.stepDetails.appCpuSTime1);
                                if (rec.stepDetails.appCpuUid2 >= 0) {
                                    printStepCpuUidCheckinDetails(
                                            item,
                                            rec.stepDetails.appCpuUid2,
                                            rec.stepDetails.appCpuUTime2,
                                            rec.stepDetails.appCpuSTime2);
                                }
                                if (rec.stepDetails.appCpuUid3 >= 0) {
                                    printStepCpuUidCheckinDetails(
                                            item,
                                            rec.stepDetails.appCpuUid3,
                                            rec.stepDetails.appCpuUTime3,
                                            rec.stepDetails.appCpuSTime3);
                                }
                            }
                            item.append("\n");
                            item.append(9);
                            item.append(',');
                            item.append(BatteryStats.HISTORY_DATA);
                            item.append(",0,Dpst=");
                            item.append(rec.stepDetails.statUserTime);
                            item.append(',');
                            item.append(rec.stepDetails.statSystemTime);
                            item.append(',');
                            item.append(rec.stepDetails.statIOWaitTime);
                            item.append(',');
                            item.append(rec.stepDetails.statIrqTime);
                            item.append(',');
                            item.append(rec.stepDetails.statSoftIrqTime);
                            item.append(',');
                            item.append(rec.stepDetails.statIdlTime);
                            item.append(',');
                            if (rec.stepDetails.statSubsystemPowerState != null) {
                                item.append(rec.stepDetails.statSubsystemPowerState);
                            }
                            item.append("\n");
                        }
                    }
                    this.oldState = rec.states;
                    this.oldState2 = rec.states2;
                    if ((rec.states2 & 524288) != 0) {
                        rec.states2 &= -524289;
                    }
                }
            }
            return item.toString();
        }

        private void printStepCpuUidDetails(StringBuilder sb, int uid, int utime, int stime) {
            UserHandle.formatUid(sb, uid);
            sb.append("=");
            sb.append(utime);
            sb.append("u+");
            sb.append(stime);
            sb.append(XmlTags.TAG_SESSION);
        }

        private void printStepCpuUidCheckinDetails(
                StringBuilder sb, int uid, int utime, int stime) {
            sb.append('/');
            sb.append(uid);
            sb.append(":");
            sb.append(utime);
            sb.append(":");
            sb.append(stime);
        }
    }

    private void printSizeValue(PrintWriter pw, long size) {
        float result = size;
        String suffix = "";
        if (result >= 10240.0f) {
            suffix = "KB";
            result /= 1024.0f;
        }
        if (result >= 10240.0f) {
            suffix = "MB";
            result /= 1024.0f;
        }
        if (result >= 10240.0f) {
            suffix = "GB";
            result /= 1024.0f;
        }
        if (result >= 10240.0f) {
            suffix = "TB";
            result /= 1024.0f;
        }
        if (result >= 10240.0f) {
            suffix = "PB";
            result /= 1024.0f;
        }
        pw.print((int) result);
        pw.print(suffix);
    }

    private static boolean dumpTimeEstimate(
            PrintWriter pw, String label1, String label2, String label3, long estimatedTime) {
        if (estimatedTime < 0) {
            return false;
        }
        pw.print(label1);
        pw.print(label2);
        pw.print(label3);
        StringBuilder sb = new StringBuilder(64);
        formatTimeMs(sb, estimatedTime);
        pw.print(sb);
        pw.println();
        return true;
    }

    private static boolean dumpDurationSteps(
            PrintWriter pw, String prefix, String header, LevelStepTracker steps, boolean checkin) {
        int count;
        int count2;
        String str = header;
        LevelStepTracker levelStepTracker = steps;
        char c = 0;
        if (levelStepTracker == null || (count = levelStepTracker.mNumStepDurations) <= 0) {
            return false;
        }
        if (!checkin) {
            pw.println(str);
        }
        String[] lineArgs = new String[5];
        int i = 0;
        while (i < count) {
            long duration = levelStepTracker.getDurationAt(i);
            int level = levelStepTracker.getLevelAt(i);
            long initMode = levelStepTracker.getInitModeAt(i);
            long modMode = levelStepTracker.getModModeAt(i);
            if (checkin) {
                lineArgs[c] = Long.toString(duration);
                lineArgs[1] = Integer.toString(level);
                if ((modMode & 3) == 0) {
                    count2 = count;
                    switch (((int) (initMode & 3)) + 1) {
                        case 1:
                            lineArgs[2] = "s-";
                            break;
                        case 2:
                            lineArgs[2] = "s+";
                            break;
                        case 3:
                            lineArgs[2] = "sd";
                            break;
                        case 4:
                            lineArgs[2] = "sds";
                            break;
                        default:
                            lineArgs[2] = "?";
                            break;
                    }
                } else {
                    count2 = count;
                    lineArgs[2] = "";
                }
                if ((modMode & 4) == 0) {
                    lineArgs[3] = (initMode & 4) != 0 ? "p+" : "p-";
                } else {
                    lineArgs[3] = "";
                }
                if ((modMode & 8) == 0) {
                    lineArgs[4] = (8 & initMode) != 0 ? "i+" : "i-";
                } else {
                    lineArgs[4] = "";
                }
                dumpLine(pw, 0, "i", str, lineArgs);
            } else {
                count2 = count;
                pw.print(prefix);
                pw.print("#");
                pw.print(i);
                pw.print(": ");
                TimeUtils.formatDuration(duration, pw);
                pw.print(" to ");
                pw.print(level);
                boolean haveModes = false;
                if ((modMode & 3) == 0) {
                    pw.print(" (");
                    switch (((int) (initMode & 3)) + 1) {
                        case 1:
                            pw.print("screen-off");
                            break;
                        case 2:
                            pw.print("screen-on");
                            break;
                        case 3:
                            pw.print("screen-doze");
                            break;
                        case 4:
                            pw.print("screen-doze-suspend");
                            break;
                        default:
                            pw.print("screen-?");
                            break;
                    }
                    haveModes = true;
                }
                if ((modMode & 4) == 0) {
                    pw.print(haveModes ? ", " : " (");
                    pw.print((initMode & 4) != 0 ? "power-save-on" : "power-save-off");
                    haveModes = true;
                }
                if ((modMode & 8) == 0) {
                    pw.print(haveModes ? ", " : " (");
                    pw.print((initMode & 8) != 0 ? "device-idle-on" : "device-idle-off");
                    haveModes = true;
                }
                if (haveModes) {
                    pw.print(NavigationBarInflaterView.KEY_CODE_END);
                }
                pw.println();
            }
            i++;
            str = header;
            levelStepTracker = steps;
            count = count2;
            c = 0;
        }
        return true;
    }

    private static void dumpDurationSteps(
            ProtoOutputStream proto, long fieldId, LevelStepTracker steps) {
        if (steps == null) {
            return;
        }
        int count = steps.mNumStepDurations;
        for (int i = 0; i < count; i++) {
            long token = proto.start(fieldId);
            proto.write(1112396529665L, steps.getDurationAt(i));
            proto.write(1120986464258L, steps.getLevelAt(i));
            long initMode = steps.getInitModeAt(i);
            long modMode = steps.getModModeAt(i);
            int ds = 0;
            if ((modMode & 3) == 0) {
                switch (((int) (3 & initMode)) + 1) {
                    case 1:
                        ds = 2;
                        break;
                    case 2:
                        ds = 1;
                        break;
                    case 3:
                        ds = 3;
                        break;
                    case 4:
                        ds = 4;
                        break;
                    default:
                        ds = 5;
                        break;
                }
            }
            proto.write(1159641169923L, ds);
            int psm = 0;
            if ((modMode & 4) == 0) {
                psm = (4 & initMode) == 0 ? 2 : 1;
            }
            proto.write(1159641169924L, psm);
            int im = 0;
            if ((modMode & 8) == 0) {
                im = (8 & initMode) == 0 ? 3 : 2;
            }
            proto.write(1159641169925L, im);
            proto.end(token);
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void dumpHistory(java.io.PrintWriter r32, int r33, long r34, boolean r36) {
        /*
            Method dump skipped, instructions count: 520
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.os.BatteryStats.dumpHistory(java.io.PrintWriter,"
                    + " int, long, boolean):void");
    }

    private void dumpHistoryTagPoolLocked(PrintWriter pw, boolean checkin) {
        if (checkin) {
            for (int i = 0; i < getHistoryStringPoolSize(); i++) {
                pw.print(9);
                pw.print(',');
                pw.print(HISTORY_STRING_POOL);
                pw.print(',');
                pw.print(i);
                pw.print(",");
                pw.print(getHistoryTagPoolUid(i));
                pw.print(",\"");
                String str = getHistoryTagPoolString(i);
                if (str != null) {
                    pw.print(str.replace("\\", "\\\\").replace("\"", "\\\""));
                }
                pw.print("\"");
                pw.println();
            }
            return;
        }
        long historyTotalSize = getHistoryTotalSize();
        long historyUsedSize = getHistoryUsedSize();
        pw.print("Battery History (");
        pw.print((100 * historyUsedSize) / historyTotalSize);
        pw.print("% used, ");
        printSizeValue(pw, historyUsedSize);
        pw.print(" used of ");
        printSizeValue(pw, historyTotalSize);
        pw.print(", ");
        pw.print(getHistoryStringPoolSize());
        pw.print(" strings using ");
        printSizeValue(pw, getHistoryStringPoolBytes());
        pw.println("):");
    }

    private void dumpDailyLevelStepSummary(
            PrintWriter pw,
            String prefix,
            String label,
            LevelStepTracker steps,
            StringBuilder tmpSb,
            int[] tmpOutInt) {
        if (steps == null) {
            return;
        }
        long timeRemaining = steps.computeTimeEstimate(0L, 0L, tmpOutInt);
        if (timeRemaining >= 0) {
            pw.print(prefix);
            pw.print(label);
            pw.print(" total time: ");
            tmpSb.setLength(0);
            formatTimeMs(tmpSb, timeRemaining);
            pw.print(tmpSb);
            pw.print(" (from ");
            pw.print(tmpOutInt[0]);
            pw.println(" steps)");
        }
        int i = 0;
        while (i < STEP_LEVEL_MODES_OF_INTEREST.length) {
            int i2 = i;
            long estimatedTime =
                    steps.computeTimeEstimate(
                            STEP_LEVEL_MODES_OF_INTEREST[i], STEP_LEVEL_MODE_VALUES[i], tmpOutInt);
            if (estimatedTime > 0) {
                pw.print(prefix);
                pw.print(label);
                pw.print(" ");
                pw.print(STEP_LEVEL_MODE_LABELS[i2]);
                pw.print(" time: ");
                tmpSb.setLength(0);
                formatTimeMs(tmpSb, estimatedTime);
                pw.print(tmpSb);
                pw.print(" (from ");
                pw.print(tmpOutInt[0]);
                pw.println(" steps)");
            }
            i = i2 + 1;
        }
    }

    private void dumpDailyPackageChanges(
            PrintWriter pw, String prefix, ArrayList<PackageChange> changes) {
        if (changes == null) {
            return;
        }
        pw.print(prefix);
        pw.println("Package changes:");
        for (int i = 0; i < changes.size(); i++) {
            PackageChange pc = changes.get(i);
            if (pc.mUpdate) {
                pw.print(prefix);
                pw.print("  Update ");
                pw.print(pc.mPackageName);
                pw.print(" vers=");
                pw.println(pc.mVersionCode);
            } else {
                pw.print(prefix);
                pw.print("  Uninstall ");
                pw.println(pc.mPackageName);
            }
        }
    }

    public void dump(
            Context context,
            PrintWriter pw,
            int flags,
            int reqUid,
            long histStart,
            BatteryStatsDumpHelper dumpHelper) {
        synchronized (this) {
            prepareForDumpLocked();
        }
        boolean filtering = (flags & 14) != 0;
        if ((flags & 8) != 0 || !filtering) {
            dumpHistory(pw, flags, histStart, false);
            pw.println();
        }
        if (filtering && (flags & 6) == 0) {
            return;
        }
        synchronized (this) {
            dumpLocked(context, pw, flags, reqUid, filtering, dumpHelper);
        }
    }

    private void dumpLocked(
            Context context,
            PrintWriter pw,
            int flags,
            int reqUid,
            boolean filtering,
            BatteryStatsDumpHelper dumpHelper) {
        String str;
        ArrayList<PackageChange> pkgc;
        LevelStepTracker csteps;
        LevelStepTracker dsteps;
        int[] outInt;
        CharSequence charSequence;
        boolean z;
        boolean z2;
        DailyItem dit;
        LevelStepTracker dsteps2;
        boolean z3;
        String str2;
        SparseArray<? extends Uid> uidStats;
        int NU;
        long j;
        if (!filtering) {
            SparseArray<? extends Uid> uidStats2 = getUidStats();
            int NU2 = uidStats2.size();
            boolean didPid = false;
            long nowRealtime = SystemClock.elapsedRealtime();
            int i = 0;
            while (i < NU2) {
                Uid uid = uidStats2.valueAt(i);
                SparseArray<? extends Uid.Pid> pids = uid.getPidStats();
                if (pids != null) {
                    int j2 = 0;
                    while (j2 < pids.size()) {
                        Uid.Pid pid = pids.valueAt(j2);
                        if (!didPid) {
                            pw.println("Per-PID Stats:");
                            didPid = true;
                        }
                        long j3 = pid.mWakeSumMs;
                        if (pid.mWakeNesting > 0) {
                            uidStats = uidStats2;
                            NU = NU2;
                            j = nowRealtime - pid.mWakeStartMs;
                        } else {
                            uidStats = uidStats2;
                            NU = NU2;
                            j = 0;
                        }
                        long time = j3 + j;
                        pw.print("  PID ");
                        pw.print(pids.keyAt(j2));
                        pw.print(" wake time: ");
                        TimeUtils.formatDuration(time, pw);
                        pw.println("");
                        j2++;
                        uidStats2 = uidStats;
                        NU2 = NU;
                    }
                }
                i++;
                uidStats2 = uidStats2;
                NU2 = NU2;
            }
            if (didPid) {
                pw.println();
            }
        }
        if (!filtering || (flags & 2) != 0) {
            if (dumpDurationSteps(
                    pw, "  ", "Discharge step durations:", getDischargeLevelStepTracker(), false)) {
                long timeRemaining =
                        computeBatteryTimeRemaining(SystemClock.elapsedRealtime() * 1000);
                if (timeRemaining >= 0) {
                    pw.print("  Estimated discharge time remaining: ");
                    TimeUtils.formatDuration(timeRemaining / 1000, pw);
                    pw.println();
                }
                LevelStepTracker steps = getDischargeLevelStepTracker();
                for (int i2 = 0; i2 < STEP_LEVEL_MODES_OF_INTEREST.length; i2++) {
                    dumpTimeEstimate(
                            pw,
                            "  Estimated ",
                            STEP_LEVEL_MODE_LABELS[i2],
                            " time: ",
                            steps.computeTimeEstimate(
                                    STEP_LEVEL_MODES_OF_INTEREST[i2],
                                    STEP_LEVEL_MODE_VALUES[i2],
                                    null));
                }
                pw.println();
            }
            if (dumpDurationSteps(
                    pw, "  ", "Charge step durations:", getChargeLevelStepTracker(), false)) {
                long timeRemaining2 =
                        computeChargeTimeRemaining(SystemClock.elapsedRealtime() * 1000);
                if (timeRemaining2 >= 0) {
                    pw.print("  Estimated charge time remaining: ");
                    TimeUtils.formatDuration(timeRemaining2 / 1000, pw);
                    pw.println();
                }
                pw.println();
            }
        }
        if (filtering && (flags & 4) == 0) {
            z2 = false;
        } else {
            pw.println("Daily stats:");
            pw.print("  Current start time: ");
            pw.println(
                    DateFormat.format("yyyy-MM-dd-HH-mm-ss", getCurrentDailyStartTime())
                            .toString());
            pw.print("  Next min deadline: ");
            pw.println(
                    DateFormat.format("yyyy-MM-dd-HH-mm-ss", getNextMinDailyDeadline()).toString());
            pw.print("  Next max deadline: ");
            pw.println(
                    DateFormat.format("yyyy-MM-dd-HH-mm-ss", getNextMaxDailyDeadline()).toString());
            StringBuilder sb = new StringBuilder(64);
            int[] outInt2 = new int[1];
            LevelStepTracker dsteps3 = getDailyDischargeLevelStepTracker();
            LevelStepTracker csteps2 = getDailyChargeLevelStepTracker();
            ArrayList<PackageChange> pkgc2 = getDailyPackageChanges();
            if (dsteps3.mNumStepDurations <= 0 && csteps2.mNumStepDurations <= 0 && pkgc2 == null) {
                str = "    ";
                dsteps = dsteps3;
                outInt = outInt2;
                charSequence = "yyyy-MM-dd-HH-mm-ss";
                z = false;
            } else {
                if ((flags & 4) != 0) {
                    str = "    ";
                    pkgc = pkgc2;
                    csteps = csteps2;
                    dsteps = dsteps3;
                    outInt = outInt2;
                    charSequence = "yyyy-MM-dd-HH-mm-ss";
                    z = false;
                } else if (!filtering) {
                    str = "    ";
                    pkgc = pkgc2;
                    csteps = csteps2;
                    dsteps = dsteps3;
                    outInt = outInt2;
                    charSequence = "yyyy-MM-dd-HH-mm-ss";
                    z = false;
                } else {
                    pw.println("  Current daily steps:");
                    str = "    ";
                    dumpDailyLevelStepSummary(pw, "    ", "Discharge", dsteps3, sb, outInt2);
                    dsteps = dsteps3;
                    outInt = outInt2;
                    charSequence = "yyyy-MM-dd-HH-mm-ss";
                    z = false;
                    dumpDailyLevelStepSummary(pw, "    ", "Charge", csteps2, sb, outInt);
                }
                if (dumpDurationSteps(
                        pw, str, "  Current daily discharge step durations:", dsteps, z)) {
                    dumpDailyLevelStepSummary(pw, "      ", "Discharge", dsteps, sb, outInt);
                }
                if (dumpDurationSteps(
                        pw, str, "  Current daily charge step durations:", csteps, z)) {
                    dumpDailyLevelStepSummary(pw, "      ", "Charge", csteps, sb, outInt);
                }
                dumpDailyPackageChanges(pw, str, pkgc);
            }
            int curIndex = 0;
            while (true) {
                DailyItem dit2 = getDailyItemLocked(curIndex);
                if (dit2 == null) {
                    break;
                }
                int curIndex2 = curIndex + 1;
                int curIndex3 = flags & 4;
                if (curIndex3 != 0) {
                    pw.println();
                }
                pw.print("  Daily from ");
                CharSequence charSequence2 = charSequence;
                pw.print(DateFormat.format(charSequence2, dit2.mStartTime).toString());
                pw.print(" to ");
                pw.print(DateFormat.format(charSequence2, dit2.mEndTime).toString());
                pw.println(":");
                if ((flags & 4) != 0) {
                    charSequence = charSequence2;
                    dit = dit2;
                } else if (filtering) {
                    charSequence = charSequence2;
                    int[] iArr = outInt;
                    dumpDailyLevelStepSummary(
                            pw, "    ", "Discharge", dit2.mDischargeSteps, sb, iArr);
                    dumpDailyLevelStepSummary(pw, "    ", "Charge", dit2.mChargeSteps, sb, iArr);
                    dsteps2 = dsteps;
                    z3 = false;
                    z = z3;
                    curIndex = curIndex2;
                    dsteps = dsteps2;
                } else {
                    charSequence = charSequence2;
                    dit = dit2;
                }
                if (!dumpDurationSteps(
                        pw,
                        "      ",
                        "    Discharge step durations:",
                        dit.mDischargeSteps,
                        false)) {
                    dsteps2 = dsteps;
                    str2 = "      ";
                } else {
                    dsteps2 = dsteps;
                    str2 = "      ";
                    dumpDailyLevelStepSummary(
                            pw, "        ", "Discharge", dit.mDischargeSteps, sb, outInt);
                }
                if (!dumpDurationSteps(
                        pw, str2, "    Charge step durations:", dit.mChargeSteps, false)) {
                    z3 = false;
                } else {
                    z3 = false;
                    dumpDailyLevelStepSummary(
                            pw, "        ", "Charge", dit.mChargeSteps, sb, outInt);
                }
                dumpDailyPackageChanges(pw, str, dit.mPackageChanges);
                z = z3;
                curIndex = curIndex2;
                dsteps = dsteps2;
            }
            z2 = z;
            pw.println();
        }
        if (!filtering || (flags & 2) != 0) {
            pw.println("Feature status:");
            pw.println("  Can read 'charging remaining time': " + canReadTimeToFullNow());
            pw.println("  Can trust power_profile: " + canTrustSecPowerProfile());
            pw.println();
        }
        if (!filtering || (flags & 2) != 0) {
            pw.println("Statistics since last charge:");
            pw.println(
                    "  System starts: "
                            + getStartCount()
                            + ", currently on battery: "
                            + getIsOnBattery());
            dumpLocked(context, pw, "", 0, reqUid, (flags & 64) != 0 ? true : z2, dumpHelper);
            pw.println();
        }
        if ((flags & 32) != 0) {
            printLatestBackupData(pw);
        }
    }

    public void dumpCheckin(
            Context context,
            PrintWriter pw,
            List<ApplicationInfo> apps,
            int flags,
            long histStart,
            BatteryStatsDumpHelper dumpHelper) {
        synchronized (this) {
            prepareForDumpLocked();
            dumpLine(
                    pw,
                    0,
                    "i",
                    VERSION_DATA,
                    36,
                    Integer.valueOf(getParcelVersion()),
                    getStartPlatformVersion(),
                    getEndPlatformVersion());
        }
        if ((flags & 24) != 0) {
            dumpHistory(pw, flags, histStart, true);
        }
        if ((flags & 8) != 0) {
            return;
        }
        synchronized (this) {
            dumpCheckinLocked(context, pw, apps, flags, dumpHelper);
        }
    }

    private void dumpCheckinLocked(
            Context context,
            PrintWriter pw,
            List<ApplicationInfo> apps,
            int flags,
            BatteryStatsDumpHelper dumpHelper) {
        if (apps != null) {
            SparseArray<Pair<ArrayList<String>, MutableBoolean>> uids = new SparseArray<>();
            for (int i = 0; i < apps.size(); i++) {
                ApplicationInfo ai = apps.get(i);
                Pair<ArrayList<String>, MutableBoolean> pkgs =
                        uids.get(UserHandle.getAppId(ai.uid));
                if (pkgs == null) {
                    pkgs = new Pair<>(new ArrayList(), new MutableBoolean(false));
                    uids.put(UserHandle.getAppId(ai.uid), pkgs);
                }
                pkgs.first.add(ai.packageName);
            }
            SparseArray<? extends Uid> uidStats = getUidStats();
            int NU = uidStats.size();
            String[] lineArgs = new String[2];
            for (int i2 = 0; i2 < NU; i2++) {
                int uid = UserHandle.getAppId(uidStats.keyAt(i2));
                Pair<ArrayList<String>, MutableBoolean> pkgs2 = uids.get(uid);
                if (pkgs2 != null && !pkgs2.second.value) {
                    pkgs2.second.value = true;
                    for (int j = 0; j < pkgs2.first.size(); j++) {
                        lineArgs[0] = Integer.toString(uid);
                        lineArgs[1] = pkgs2.first.get(j);
                        dumpLine(pw, 0, "i", "uid", lineArgs);
                    }
                }
            }
        }
        if ((flags & 4) == 0) {
            dumpDurationSteps(pw, "", DISCHARGE_STEP_DATA, getDischargeLevelStepTracker(), true);
            String[] lineArgs2 = new String[1];
            long timeRemaining = computeBatteryTimeRemaining(SystemClock.elapsedRealtime() * 1000);
            if (timeRemaining >= 0) {
                lineArgs2[0] = Long.toString(timeRemaining);
                dumpLine(pw, 0, "i", DISCHARGE_TIME_REMAIN_DATA, lineArgs2);
            }
            dumpDurationSteps(pw, "", CHARGE_STEP_DATA, getChargeLevelStepTracker(), true);
            long timeRemaining2 = computeChargeTimeRemaining(SystemClock.elapsedRealtime() * 1000);
            if (timeRemaining2 >= 0) {
                lineArgs2[0] = Long.toString(timeRemaining2);
                dumpLine(pw, 0, "i", CHARGE_TIME_REMAIN_DATA, lineArgs2);
            }
            dumpCheckinLocked(context, pw, 0, -1, (flags & 64) != 0, dumpHelper);
        }
    }

    public void dumpProtoLocked(
            Context context,
            FileDescriptor fd,
            List<ApplicationInfo> apps,
            int flags,
            long histStart,
            BatteryStatsDumpHelper dumpHelper) {
        ProtoOutputStream proto = new ProtoOutputStream(fd);
        prepareForDumpLocked();
        if ((flags & 24) != 0) {
            dumpProtoHistoryLocked(proto, flags, histStart);
            proto.flush();
            return;
        }
        long bToken = proto.start(1146756268033L);
        proto.write(1120986464257L, 36);
        proto.write(1112396529666L, getParcelVersion());
        proto.write(1138166333443L, getStartPlatformVersion());
        proto.write(1138166333444L, getEndPlatformVersion());
        if ((flags & 4) == 0) {
            BatteryUsageStats stats = dumpHelper.getBatteryUsageStats(this, false);
            ProportionalAttributionCalculator proportionalAttributionCalculator =
                    new ProportionalAttributionCalculator(context, stats);
            dumpProtoAppsLocked(proto, stats, apps, proportionalAttributionCalculator);
            dumpProtoSystemLocked(proto, stats);
        }
        proto.end(bToken);
        proto.flush();
    }

    private void dumpProtoAppsLocked(
            ProtoOutputStream proto,
            BatteryUsageStats stats,
            List<ApplicationInfo> apps,
            ProportionalAttributionCalculator proportionalAttributionCalculator) {
        long rawRealtimeUs;
        SparseArray<ArrayList<String>> aidToPackages;
        long rawRealtimeMs;
        long j;
        long rawRealtimeMs2;
        SparseArray<UidBatteryConsumer> uidToConsumer;
        long j2;
        ProportionalAttributionCalculator proportionalAttributionCalculator2;
        UidBatteryConsumer consumer;
        long[] timesInFreqScreenOffMs;
        ArrayMap<String, ? extends Uid.Proc> processStats;
        int iu;
        ArrayList<String> pkgs;
        long[] timesInFreqMs;
        SparseArray<UidBatteryConsumer> uidToConsumer2;
        int stepCount;
        ProtoOutputStream protoOutputStream;
        long uTkn;
        int uid;
        long rawRealtimeMs3;
        long batteryUptimeUs;
        SparseArray<? extends Uid> uidStats;
        Uid u;
        ArrayMap<String, ? extends Uid.Pkg> packageStats;
        ArrayList<String> pkgs2;
        Uid u2;
        ArrayMap<String, ? extends Uid.Pkg> packageStats2;
        ArrayList<String> pkgs3;
        ProtoOutputStream protoOutputStream2 = proto;
        int which = 0;
        long rawUptimeUs = SystemClock.uptimeMillis() * 1000;
        long rawRealtimeMs4 = SystemClock.elapsedRealtime();
        long rawRealtimeUs2 = rawRealtimeMs4 * 1000;
        long batteryUptimeUs2 = getBatteryUptime(rawUptimeUs);
        SparseArray<ArrayList<String>> aidToPackages2 = new SparseArray<>();
        if (apps == null) {
            rawRealtimeUs = rawRealtimeUs2;
        } else {
            int i = 0;
            while (i < apps.size()) {
                ApplicationInfo ai = apps.get(i);
                long rawRealtimeUs3 = rawRealtimeUs2;
                int aid = UserHandle.getAppId(ai.uid);
                ArrayList<String> pkgs4 = aidToPackages2.get(aid);
                if (pkgs4 == null) {
                    pkgs4 = new ArrayList<>();
                    aidToPackages2.put(aid, pkgs4);
                }
                pkgs4.add(ai.packageName);
                i++;
                rawRealtimeUs2 = rawRealtimeUs3;
            }
            rawRealtimeUs = rawRealtimeUs2;
        }
        SparseArray<UidBatteryConsumer> uidToConsumer3 = new SparseArray<>();
        List<UidBatteryConsumer> consumers = stats.getUidBatteryConsumers();
        int i2 = consumers.size() - 1;
        while (i2 >= 0) {
            UidBatteryConsumer bs = consumers.get(i2);
            uidToConsumer3.put(bs.getUid(), bs);
            i2--;
            consumers = consumers;
        }
        List<UidBatteryConsumer> consumers2 = consumers;
        SparseArray<? extends Uid> uidStats2 = getUidStats();
        int n = uidStats2.size();
        int iu2 = 0;
        while (iu2 < n) {
            int n2 = n;
            SparseArray<UidBatteryConsumer> uidToConsumer4 = uidToConsumer3;
            long uTkn2 = protoOutputStream2.start(2246267895813L);
            Uid u3 = uidStats2.valueAt(iu2);
            int which2 = which;
            int uid2 = uidStats2.keyAt(iu2);
            long rawUptimeUs2 = rawUptimeUs;
            protoOutputStream2.write(1120986464257L, uid2);
            ArrayList<String> pkgs5 = aidToPackages2.get(UserHandle.getAppId(uid2));
            if (pkgs5 == null) {
                pkgs5 = new ArrayList<>();
            }
            ArrayMap<String, ? extends Uid.Pkg> packageStats3 = u3.getPackageStats();
            int iu3 = iu2;
            int iu4 = packageStats3.size() - 1;
            while (true) {
                aidToPackages = aidToPackages2;
                if (iu4 < 0) {
                    break;
                }
                String pkg = packageStats3.keyAt(iu4);
                ArrayMap<String, ? extends Uid.Pkg.Serv> serviceStats =
                        packageStats3.valueAt(iu4).getServiceStats();
                if (serviceStats.size() == 0) {
                    protoOutputStream = proto;
                    batteryUptimeUs = batteryUptimeUs2;
                    uTkn = uTkn2;
                    uidStats = uidStats2;
                    u = u3;
                    uid = uid2;
                    pkgs2 = pkgs5;
                    packageStats = packageStats3;
                    rawRealtimeMs3 = rawRealtimeMs4;
                } else {
                    protoOutputStream = proto;
                    uTkn = uTkn2;
                    uid = uid2;
                    ArrayMap<String, ? extends Uid.Pkg> packageStats4 = packageStats3;
                    rawRealtimeMs3 = rawRealtimeMs4;
                    long pToken = protoOutputStream.start(2246267895810L);
                    protoOutputStream.write(1138166333441L, pkg);
                    pkgs5.remove(pkg);
                    int isvc = serviceStats.size() - 1;
                    while (isvc >= 0) {
                        Uid.Pkg.Serv ss = serviceStats.valueAt(isvc);
                        String pkg2 = pkg;
                        SparseArray<? extends Uid> uidStats3 = uidStats2;
                        long startTimeMs = roundUsToMs(ss.getStartTime(batteryUptimeUs2, 0));
                        long batteryUptimeUs3 = batteryUptimeUs2;
                        int starts = ss.getStarts(0);
                        int launches = ss.getLaunches(0);
                        if (startTimeMs == 0 && starts == 0 && launches == 0) {
                            u2 = u3;
                            packageStats2 = packageStats4;
                            pkgs3 = pkgs5;
                        } else {
                            u2 = u3;
                            long sToken = protoOutputStream.start(2246267895810L);
                            packageStats2 = packageStats4;
                            pkgs3 = pkgs5;
                            protoOutputStream.write(1138166333441L, serviceStats.keyAt(isvc));
                            protoOutputStream.write(1112396529666L, startTimeMs);
                            protoOutputStream.write(1120986464259L, starts);
                            protoOutputStream.write(1120986464260L, launches);
                            protoOutputStream.end(sToken);
                        }
                        isvc--;
                        pkgs5 = pkgs3;
                        pkg = pkg2;
                        uidStats2 = uidStats3;
                        batteryUptimeUs2 = batteryUptimeUs3;
                        u3 = u2;
                        packageStats4 = packageStats2;
                    }
                    batteryUptimeUs = batteryUptimeUs2;
                    uidStats = uidStats2;
                    u = u3;
                    packageStats = packageStats4;
                    pkgs2 = pkgs5;
                    protoOutputStream.end(pToken);
                }
                iu4--;
                pkgs5 = pkgs2;
                aidToPackages2 = aidToPackages;
                uid2 = uid;
                uidStats2 = uidStats;
                uTkn2 = uTkn;
                rawRealtimeMs4 = rawRealtimeMs3;
                batteryUptimeUs2 = batteryUptimeUs;
                u3 = u;
                packageStats3 = packageStats;
            }
            long batteryUptimeUs4 = batteryUptimeUs2;
            long uTkn3 = uTkn2;
            SparseArray<? extends Uid> uidStats4 = uidStats2;
            Uid u4 = u3;
            int uid3 = uid2;
            ArrayList<String> pkgs6 = pkgs5;
            ArrayMap<String, ? extends Uid.Pkg> packageStats5 = packageStats3;
            long rawRealtimeMs5 = rawRealtimeMs4;
            Iterator<String> it = pkgs6.iterator();
            while (it.hasNext()) {
                String p = it.next();
                long pToken2 = proto.start(2246267895810L);
                proto.write(1138166333441L, p);
                proto.end(pToken2);
            }
            if (u4.getAggregatedPartialWakelockTimer() == null) {
                rawRealtimeMs = rawRealtimeMs5;
            } else {
                Timer timer = u4.getAggregatedPartialWakelockTimer();
                rawRealtimeMs = rawRealtimeMs5;
                long totTimeMs = timer.getTotalDurationMsLocked(rawRealtimeMs);
                Timer bgTimer = timer.getSubTimer();
                long bgTimeMs =
                        bgTimer != null ? bgTimer.getTotalDurationMsLocked(rawRealtimeMs) : 0L;
                long awToken = proto.start(1146756268056L);
                proto.write(1112396529665L, totTimeMs);
                proto.write(1112396529666L, bgTimeMs);
                proto.end(awToken);
            }
            int iu5 = iu3;
            long rawRealtimeUs4 = rawRealtimeUs;
            List<UidBatteryConsumer> consumers3 = consumers2;
            SparseArray<UidBatteryConsumer> uidToConsumer5 = uidToConsumer4;
            dumpTimer(proto, 1146756268040L, u4.getAudioTurnedOnTimer(), rawRealtimeUs4, 0);
            dumpControllerActivityProto(
                    proto, 1146756268035L, u4.getBluetoothControllerActivity(), 0);
            Timer bleTimer = u4.getBluetoothScanTimer();
            if (bleTimer != null) {
                long bmToken = proto.start(1146756268038L);
                dumpTimer(proto, 1146756268033L, bleTimer, rawRealtimeUs4, 0);
                dumpTimer(
                        proto,
                        1146756268034L,
                        u4.getBluetoothScanBackgroundTimer(),
                        rawRealtimeUs4,
                        0);
                dumpTimer(
                        proto,
                        1146756268035L,
                        u4.getBluetoothUnoptimizedScanTimer(),
                        rawRealtimeUs4,
                        0);
                dumpTimer(
                        proto,
                        1146756268036L,
                        u4.getBluetoothUnoptimizedScanBackgroundTimer(),
                        rawRealtimeUs4,
                        0);
                j = 1120986464261L;
                proto.write(
                        1120986464261L,
                        u4.getBluetoothScanResultCounter() != null
                                ? u4.getBluetoothScanResultCounter().getCountLocked(0)
                                : 0);
                proto.write(
                        1120986464262L,
                        u4.getBluetoothScanResultBgCounter() != null
                                ? u4.getBluetoothScanResultBgCounter().getCountLocked(0)
                                : 0);
                proto.end(bmToken);
            } else {
                j = 1120986464261L;
            }
            dumpTimer(proto, 1146756268041L, u4.getCameraTurnedOnTimer(), rawRealtimeUs4, 0);
            long cpuToken = proto.start(1146756268039L);
            proto.write(1112396529665L, roundUsToMs(u4.getUserCpuTimeUs(0)));
            proto.write(1112396529666L, roundUsToMs(u4.getSystemCpuTimeUs(0)));
            CpuScalingPolicies scalingPolicies = getCpuScalingPolicies();
            if (scalingPolicies == null) {
                rawRealtimeMs2 = rawRealtimeMs;
                uidToConsumer = uidToConsumer5;
            } else {
                long[] cpuFreqTimeMs = u4.getCpuFreqTimes(0);
                if (cpuFreqTimeMs == null) {
                    rawRealtimeMs2 = rawRealtimeMs;
                    uidToConsumer = uidToConsumer5;
                } else if (cpuFreqTimeMs.length != scalingPolicies.getScalingStepCount()) {
                    rawRealtimeMs2 = rawRealtimeMs;
                    uidToConsumer = uidToConsumer5;
                } else {
                    long[] screenOffCpuFreqTimeMs = u4.getScreenOffCpuFreqTimes(0);
                    if (screenOffCpuFreqTimeMs == null) {
                        screenOffCpuFreqTimeMs = new long[cpuFreqTimeMs.length];
                    }
                    int ic = 0;
                    while (ic < cpuFreqTimeMs.length) {
                        long cToken = proto.start(2246267895811L);
                        proto.write(1120986464257L, ic + 1);
                        proto.write(1112396529666L, cpuFreqTimeMs[ic]);
                        proto.write(1112396529667L, screenOffCpuFreqTimeMs[ic]);
                        proto.end(cToken);
                        ic++;
                        uidToConsumer5 = uidToConsumer5;
                        rawRealtimeMs = rawRealtimeMs;
                    }
                    rawRealtimeMs2 = rawRealtimeMs;
                    uidToConsumer = uidToConsumer5;
                }
            }
            int stepCount2 = getCpuScalingPolicies().getScalingStepCount();
            long[] timesInFreqMs2 = new long[stepCount2];
            long[] timesInFreqScreenOffMs2 = new long[stepCount2];
            int procState = 0;
            while (true) {
                j2 = 1159641169921L;
                if (procState >= 7) {
                    break;
                }
                if (!u4.getCpuFreqTimes(timesInFreqMs2, procState)) {
                    iu = iu5;
                    pkgs = pkgs6;
                    timesInFreqMs = timesInFreqMs2;
                    uidToConsumer2 = uidToConsumer;
                    stepCount = stepCount2;
                } else {
                    if (!u4.getScreenOffCpuFreqTimes(timesInFreqScreenOffMs2, procState)) {
                        Arrays.fill(timesInFreqScreenOffMs2, 0L);
                    }
                    long procToken = proto.start(2246267895812L);
                    proto.write(1159641169921L, procState);
                    int ic2 = 0;
                    while (ic2 < timesInFreqMs2.length) {
                        int iu6 = iu5;
                        long cToken2 = proto.start(2246267895810L);
                        proto.write(1120986464257L, ic2 + 1);
                        proto.write(1112396529666L, timesInFreqMs2[ic2]);
                        proto.write(1112396529667L, timesInFreqScreenOffMs2[ic2]);
                        proto.end(cToken2);
                        ic2++;
                        stepCount2 = stepCount2;
                        pkgs6 = pkgs6;
                        iu5 = iu6;
                        uidToConsumer = uidToConsumer;
                        timesInFreqMs2 = timesInFreqMs2;
                    }
                    iu = iu5;
                    pkgs = pkgs6;
                    timesInFreqMs = timesInFreqMs2;
                    uidToConsumer2 = uidToConsumer;
                    stepCount = stepCount2;
                    proto.end(procToken);
                }
                procState++;
                stepCount2 = stepCount;
                pkgs6 = pkgs;
                iu5 = iu;
                uidToConsumer = uidToConsumer2;
                timesInFreqMs2 = timesInFreqMs;
            }
            int iu7 = iu5;
            SparseArray<UidBatteryConsumer> uidToConsumer6 = uidToConsumer;
            long j3 = 2246267895810L;
            proto.end(cpuToken);
            dumpTimer(proto, 1146756268042L, u4.getFlashlightTurnedOnTimer(), rawRealtimeUs4, 0);
            dumpTimer(proto, 1146756268043L, u4.getForegroundActivityTimer(), rawRealtimeUs4, 0);
            dumpTimer(proto, 1146756268044L, u4.getForegroundServiceTimer(), rawRealtimeUs4, 0);
            ArrayMap<String, SparseIntArray> completions = u4.getJobCompletionStats();
            int ic3 = 0;
            while (ic3 < completions.size()) {
                SparseIntArray types = completions.valueAt(ic3);
                if (types != null) {
                    long jcToken = proto.start(2246267895824L);
                    proto.write(1138166333441L, completions.keyAt(ic3));
                    int[] jobStopReasonCodes = JobParameters.getJobStopReasonCodes();
                    int length = jobStopReasonCodes.length;
                    int i3 = 0;
                    while (i3 < length) {
                        int r = jobStopReasonCodes[i3];
                        int[] iArr = jobStopReasonCodes;
                        long rToken = proto.start(j3);
                        proto.write(j2, r);
                        proto.write(1120986464258L, types.get(r, 0));
                        proto.end(rToken);
                        i3++;
                        jobStopReasonCodes = iArr;
                        length = length;
                        j3 = 2246267895810L;
                        j2 = 1159641169921L;
                    }
                    proto.end(jcToken);
                }
                ic3++;
                j3 = 2246267895810L;
                j2 = 1159641169921L;
            }
            ArrayMap<String, ? extends Timer> jobs = u4.getJobStats();
            int ij = jobs.size() - 1;
            while (ij >= 0) {
                Timer timer2 = jobs.valueAt(ij);
                Timer bgTimer2 = timer2.getSubTimer();
                long jToken = proto.start(2246267895823L);
                proto.write(1138166333441L, jobs.keyAt(ij));
                dumpTimer(proto, 1146756268034L, timer2, rawRealtimeUs4, 0);
                dumpTimer(proto, 1146756268035L, bgTimer2, rawRealtimeUs4, 0);
                proto.end(jToken);
                ij--;
                completions = completions;
            }
            dumpControllerActivityProto(proto, 1146756268036L, u4.getModemControllerActivity(), 0);
            long nToken = proto.start(1146756268049L);
            proto.write(1112396529665L, u4.getNetworkActivityBytes(0, 0));
            proto.write(1112396529666L, u4.getNetworkActivityBytes(1, 0));
            proto.write(1112396529667L, u4.getNetworkActivityBytes(2, 0));
            proto.write(1112396529668L, u4.getNetworkActivityBytes(3, 0));
            proto.write(1112396529669L, u4.getNetworkActivityBytes(4, 0));
            proto.write(1112396529670L, u4.getNetworkActivityBytes(5, 0));
            proto.write(1112396529671L, u4.getNetworkActivityPackets(0, 0));
            proto.write(1112396529672L, u4.getNetworkActivityPackets(1, 0));
            proto.write(1112396529673L, u4.getNetworkActivityPackets(2, 0));
            proto.write(1112396529674L, u4.getNetworkActivityPackets(3, 0));
            proto.write(1112396529675L, roundUsToMs(u4.getMobileRadioActiveTime(0)));
            proto.write(1120986464268L, u4.getMobileRadioActiveCount(0));
            proto.write(1120986464269L, u4.getMobileRadioApWakeupCount(0));
            proto.write(1120986464270L, u4.getWifiRadioApWakeupCount(0));
            proto.write(1112396529679L, u4.getNetworkActivityBytes(6, 0));
            proto.write(1112396529680L, u4.getNetworkActivityBytes(7, 0));
            proto.write(1112396529681L, u4.getNetworkActivityBytes(8, 0));
            proto.write(1112396529682L, u4.getNetworkActivityBytes(9, 0));
            proto.write(1112396529683L, u4.getNetworkActivityPackets(6, 0));
            proto.write(1112396529684L, u4.getNetworkActivityPackets(7, 0));
            proto.write(1112396529685L, u4.getNetworkActivityPackets(8, 0));
            proto.write(1112396529686L, u4.getNetworkActivityPackets(9, 0));
            proto.end(nToken);
            SparseArray<UidBatteryConsumer> uidToConsumer7 = uidToConsumer6;
            UidBatteryConsumer consumer2 = uidToConsumer7.get(uid3);
            if (consumer2 == null) {
                proportionalAttributionCalculator2 = proportionalAttributionCalculator;
            } else {
                long bsToken = proto.start(1146756268050L);
                proto.write(1103806595073L, consumer2.getConsumedPower());
                proportionalAttributionCalculator2 = proportionalAttributionCalculator;
                proto.write(
                        1133871366146L,
                        proportionalAttributionCalculator2.isSystemBatteryConsumer(consumer2));
                proto.write(1103806595075L, consumer2.getConsumedPower(0));
                proto.write(
                        1103806595076L,
                        proportionalAttributionCalculator2.getProportionalPowerMah(consumer2));
                proto.end(bsToken);
            }
            ArrayMap<String, ? extends Uid.Proc> processStats2 = u4.getProcessStats();
            int ipr = processStats2.size() - 1;
            while (ipr >= 0) {
                Uid.Proc ps = processStats2.valueAt(ipr);
                long prToken = proto.start(2246267895827L);
                proto.write(1138166333441L, processStats2.keyAt(ipr));
                proto.write(1112396529666L, ps.getUserTime(0));
                proto.write(1112396529667L, ps.getSystemTime(0));
                proto.write(1112396529668L, ps.getForegroundTime(0));
                proto.write(1120986464261L, ps.getStarts(0));
                proto.write(1120986464262L, ps.getNumAnrs(0));
                proto.write(1120986464263L, ps.getNumCrashes(0));
                proto.end(prToken);
                ipr--;
                uidToConsumer7 = uidToConsumer7;
                processStats2 = processStats2;
                nToken = nToken;
            }
            ArrayMap<String, ? extends Uid.Proc> processStats3 = processStats2;
            SparseArray<UidBatteryConsumer> uidToConsumer8 = uidToConsumer7;
            SparseArray<? extends Uid.Sensor> sensors = u4.getSensorStats();
            int ise = 0;
            while (ise < sensors.size()) {
                Uid.Sensor se = sensors.valueAt(ise);
                Timer timer3 = se.getSensorTime();
                if (timer3 == null) {
                    consumer = consumer2;
                    timesInFreqScreenOffMs = timesInFreqScreenOffMs2;
                    processStats = processStats3;
                } else {
                    Timer bgTimer3 = se.getSensorBackgroundTime();
                    int sensorNumber = sensors.keyAt(ise);
                    long seToken = proto.start(2246267895829L);
                    proto.write(1120986464257L, sensorNumber);
                    consumer = consumer2;
                    timesInFreqScreenOffMs = timesInFreqScreenOffMs2;
                    processStats = processStats3;
                    dumpTimer(proto, 1146756268034L, timer3, rawRealtimeUs4, 0);
                    dumpTimer(proto, 1146756268035L, bgTimer3, rawRealtimeUs4, 0);
                    proto.end(seToken);
                }
                ise++;
                consumer2 = consumer;
                processStats3 = processStats;
                timesInFreqScreenOffMs2 = timesInFreqScreenOffMs;
            }
            for (int ips = 0; ips < 7; ips++) {
                long rawRealtimeUs5 = rawRealtimeUs4;
                long durMs = roundUsToMs(u4.getProcessStateTime(ips, rawRealtimeUs5, 0));
                if (durMs == 0) {
                    rawRealtimeUs4 = rawRealtimeUs5;
                } else {
                    long stToken = proto.start(2246267895828L);
                    proto.write(1159641169921L, ips);
                    rawRealtimeUs4 = rawRealtimeUs5;
                    proto.write(1112396529666L, durMs);
                    proto.end(stToken);
                }
            }
            ArrayMap<String, ? extends Timer> syncs = u4.getSyncStats();
            for (int isy = syncs.size() - 1; isy >= 0; isy--) {
                Timer timer4 = syncs.valueAt(isy);
                Timer bgTimer4 = timer4.getSubTimer();
                long syToken = proto.start(2246267895830L);
                proto.write(1138166333441L, syncs.keyAt(isy));
                long rawRealtimeUs6 = rawRealtimeUs4;
                dumpTimer(proto, 1146756268034L, timer4, rawRealtimeUs6, 0);
                dumpTimer(proto, 1146756268035L, bgTimer4, rawRealtimeUs6, 0);
                proto.end(syToken);
            }
            long rawRealtimeUs7 = rawRealtimeUs4;
            if (u4.hasUserActivity()) {
                for (int i4 = 0; i4 < Uid.NUM_USER_ACTIVITY_TYPES; i4++) {
                    int val = u4.getUserActivityCount(i4, 0);
                    if (val != 0) {
                        long uaToken = proto.start(2246267895831L);
                        proto.write(1159641169921L, i4);
                        proto.write(1120986464258L, val);
                        proto.end(uaToken);
                    }
                }
            }
            dumpTimer(proto, 1146756268045L, u4.getVibratorOnTimer(), rawRealtimeUs7, 0);
            dumpTimer(proto, 1146756268046L, u4.getVideoTurnedOnTimer(), rawRealtimeUs7, 0);
            ArrayMap<String, ? extends Uid.Wakelock> wakelocks = u4.getWakelockStats();
            int iw = wakelocks.size() - 1;
            while (iw >= 0) {
                Uid.Wakelock wl = wakelocks.valueAt(iw);
                long wToken = proto.start(2246267895833L);
                proto.write(1138166333441L, wakelocks.keyAt(iw));
                int iw2 = iw;
                ArrayMap<String, ? extends Uid.Wakelock> wakelocks2 = wakelocks;
                dumpTimer(proto, 1146756268034L, wl.getWakeTime(1), rawRealtimeUs7, 0);
                Timer pTimer = wl.getWakeTime(0);
                if (pTimer != null) {
                    dumpTimer(proto, 1146756268035L, pTimer, rawRealtimeUs7, 0);
                    dumpTimer(proto, 1146756268036L, pTimer.getSubTimer(), rawRealtimeUs7, 0);
                }
                dumpTimer(proto, 1146756268037L, wl.getWakeTime(2), rawRealtimeUs7, 0);
                proto.end(wToken);
                iw = iw2 - 1;
                wakelocks = wakelocks2;
            }
            dumpTimer(proto, 1146756268060L, u4.getMulticastWakelockStats(), rawRealtimeUs7, 0);
            int i5 = 1;
            int ipkg = packageStats5.size() - 1;
            while (ipkg >= 0) {
                ArrayMap<String, ? extends Uid.Pkg> packageStats6 = packageStats5;
                ArrayMap<String, ? extends Counter> alarms =
                        packageStats6.valueAt(ipkg).getWakeupAlarmStats();
                int iwa = alarms.size() - i5;
                while (iwa >= 0) {
                    long waToken = proto.start(2246267895834L);
                    proto.write(1138166333441L, alarms.keyAt(iwa));
                    proto.write(1120986464258L, alarms.valueAt(iwa).getCountLocked(0));
                    proto.end(waToken);
                    iwa--;
                    packageStats6 = packageStats6;
                }
                packageStats5 = packageStats6;
                ipkg--;
                i5 = 1;
            }
            dumpControllerActivityProto(proto, 1146756268037L, u4.getWifiControllerActivity(), 0);
            long wToken2 = proto.start(1146756268059L);
            proto.write(1112396529665L, roundUsToMs(u4.getFullWifiLockTime(rawRealtimeUs7, 0)));
            dumpTimer(proto, 1146756268035L, u4.getWifiScanTimer(), rawRealtimeUs7, 0);
            proto.write(1112396529666L, roundUsToMs(u4.getWifiRunningTime(rawRealtimeUs7, 0)));
            dumpTimer(proto, 1146756268036L, u4.getWifiScanBackgroundTimer(), rawRealtimeUs7, 0);
            proto.end(wToken2);
            proto.end(uTkn3);
            iu2 = iu7 + 1;
            protoOutputStream2 = proto;
            uidStats2 = uidStats4;
            aidToPackages2 = aidToPackages;
            n = n2;
            which = which2;
            rawUptimeUs = rawUptimeUs2;
            rawRealtimeMs4 = rawRealtimeMs2;
            batteryUptimeUs2 = batteryUptimeUs4;
            uidToConsumer3 = uidToConsumer8;
            consumers2 = consumers3;
            rawRealtimeUs = rawRealtimeUs7;
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void dumpProtoHistoryLocked(
            android.util.proto.ProtoOutputStream r28, int r29, long r30) {
        /*
            Method dump skipped, instructions count: 541
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.os.BatteryStats.dumpProtoHistoryLocked(android.util.proto.ProtoOutputStream,"
                    + " int, long):void");
    }

    private void dumpProtoSystemLocked(ProtoOutputStream proto, BatteryUsageStats stats) {
        long timeRemainingUs;
        int i;
        long pdcToken;
        long sToken = proto.start(1146756268038L);
        long rawUptimeUs = SystemClock.uptimeMillis() * 1000;
        long rawRealtimeMs = SystemClock.elapsedRealtime();
        long rawRealtimeUs = rawRealtimeMs * 1000;
        long bToken = proto.start(1146756268033L);
        proto.write(1112396529665L, getStartClockTime());
        proto.write(1112396529666L, getStartCount());
        proto.write(1112396529667L, computeRealtime(rawRealtimeUs, 0) / 1000);
        proto.write(1112396529668L, computeUptime(rawUptimeUs, 0) / 1000);
        proto.write(1112396529669L, computeBatteryRealtime(rawRealtimeUs, 0) / 1000);
        proto.write(1112396529670L, computeBatteryUptime(rawUptimeUs, 0) / 1000);
        proto.write(1112396529671L, computeBatteryScreenOffRealtime(rawRealtimeUs, 0) / 1000);
        proto.write(1112396529672L, computeBatteryScreenOffUptime(rawUptimeUs, 0) / 1000);
        proto.write(1112396529673L, getScreenDozeTime(rawRealtimeUs, 0) / 1000);
        proto.write(1112396529674L, getEstimatedBatteryCapacity());
        proto.write(1112396529675L, getMinLearnedBatteryCapacity());
        proto.write(1112396529676L, getMaxLearnedBatteryCapacity());
        proto.end(bToken);
        long bdToken = proto.start(1146756268034L);
        proto.write(1120986464257L, getLowDischargeAmountSinceCharge());
        proto.write(1120986464258L, getHighDischargeAmountSinceCharge());
        proto.write(1120986464259L, getDischargeAmountScreenOnSinceCharge());
        proto.write(1120986464260L, getDischargeAmountScreenOffSinceCharge());
        proto.write(1120986464261L, getDischargeAmountScreenDozeSinceCharge());
        proto.write(1112396529670L, getUahDischarge(0) / 1000);
        proto.write(1112396529671L, getUahDischargeScreenOff(0) / 1000);
        proto.write(1112396529672L, getUahDischargeScreenDoze(0) / 1000);
        proto.write(1112396529673L, getUahDischargeLightDoze(0) / 1000);
        proto.write(1112396529674L, getUahDischargeDeepDoze(0) / 1000);
        proto.end(bdToken);
        long timeRemainingUs2 = computeChargeTimeRemaining(rawRealtimeUs);
        if (timeRemainingUs2 >= 0) {
            proto.write(1112396529667L, timeRemainingUs2 / 1000);
            timeRemainingUs = timeRemainingUs2;
        } else {
            long timeRemainingUs3 = computeBatteryTimeRemaining(rawRealtimeUs);
            if (timeRemainingUs3 >= 0) {
                proto.write(1112396529668L, timeRemainingUs3 / 1000);
            } else {
                proto.write(1112396529668L, -1);
            }
            timeRemainingUs = timeRemainingUs3;
        }
        dumpDurationSteps(proto, 2246267895813L, getChargeLevelStepTracker());
        int i2 = 0;
        while (i2 < NUM_DATA_CONNECTION_TYPES) {
            boolean isNone = i2 == 0;
            int telephonyNetworkType = i2;
            int telephonyNetworkType2 =
                    (i2 == DATA_CONNECTION_OTHER || i2 == DATA_CONNECTION_EMERGENCY_SERVICE)
                            ? 0
                            : telephonyNetworkType;
            long rawRealtimeUs2 = rawRealtimeUs;
            long pdcToken2 = proto.start(2246267895816L);
            if (isNone) {
                pdcToken = pdcToken2;
                proto.write(1133871366146L, isNone);
            } else {
                pdcToken = pdcToken2;
                proto.write(1159641169921L, telephonyNetworkType2);
            }
            rawRealtimeUs = rawRealtimeUs2;
            dumpTimer(proto, 1146756268035L, getPhoneDataConnectionTimer(i2), rawRealtimeUs, 0);
            proto.end(pdcToken);
            i2++;
            timeRemainingUs = timeRemainingUs;
        }
        long rawRealtimeUs3 = rawRealtimeUs;
        int i3 = 0;
        dumpDurationSteps(proto, 2246267895814L, getDischargeLevelStepTracker());
        CpuScalingPolicies scalingPolicies = getCpuScalingPolicies();
        if (scalingPolicies != null) {
            int[] policies = scalingPolicies.getPolicies();
            int length = policies.length;
            int i4 = 0;
            while (i4 < length) {
                int policy = policies[i4];
                int[] frequencies = scalingPolicies.getFrequencies(policy);
                int length2 = frequencies.length;
                int i5 = i3;
                while (i5 < length2) {
                    int frequency = frequencies[i5];
                    proto.write(SystemProto.CPU_FREQUENCY, frequency);
                    i5++;
                    policies = policies;
                    length = length;
                }
                i4++;
                i3 = 0;
            }
        }
        dumpControllerActivityProto(proto, 1146756268041L, getBluetoothControllerActivity(), 0);
        dumpControllerActivityProto(proto, 1146756268042L, getModemControllerActivity(), 0);
        long gnToken = proto.start(1146756268044L);
        proto.write(1112396529665L, getNetworkActivityBytes(0, 0));
        int i6 = 1;
        proto.write(1112396529666L, getNetworkActivityBytes(1, 0));
        proto.write(1112396529669L, getNetworkActivityPackets(0, 0));
        proto.write(1112396529670L, getNetworkActivityPackets(1, 0));
        proto.write(1112396529667L, getNetworkActivityBytes(2, 0));
        proto.write(1112396529668L, getNetworkActivityBytes(3, 0));
        proto.write(1112396529671L, getNetworkActivityPackets(2, 0));
        proto.write(1112396529672L, getNetworkActivityPackets(3, 0));
        proto.write(1112396529673L, getNetworkActivityBytes(4, 0));
        proto.write(1112396529674L, getNetworkActivityBytes(5, 0));
        proto.end(gnToken);
        dumpControllerActivityProto(proto, 1146756268043L, getWifiControllerActivity(), 0);
        long gwToken = proto.start(1146756268045L);
        proto.write(1112396529665L, getWifiOnTime(rawRealtimeUs3, 0) / 1000);
        proto.write(1112396529666L, getGlobalWifiRunningTime(rawRealtimeUs3, 0) / 1000);
        long gwToken2 = gwToken;
        proto.end(gwToken2);
        Map<String, ? extends Timer> kernelWakelocks = getKernelWakelockStats();
        for (Map.Entry<String, ? extends Timer> ent : kernelWakelocks.entrySet()) {
            long kwToken = proto.start(2246267895822L);
            proto.write(1138166333441L, ent.getKey());
            dumpTimer(proto, 1146756268034L, ent.getValue(), rawRealtimeUs3, 0);
            proto.end(kwToken);
            i6 = i6;
            gwToken2 = gwToken2;
            bdToken = bdToken;
        }
        int i7 = i6;
        SparseArray<? extends Uid> uidStats = getUidStats();
        int iu = 0;
        long fullWakeLockTimeTotalUs = 0;
        long partialWakeLockTimeTotalUs = 0;
        while (iu < uidStats.size()) {
            Uid u = uidStats.valueAt(iu);
            ArrayMap<String, ? extends Uid.Wakelock> wakelocks = u.getWakelockStats();
            int iw = wakelocks.size() - i7;
            while (iw >= 0) {
                Uid.Wakelock wl = wakelocks.valueAt(iw);
                Timer fullWakeTimer = wl.getWakeTime(i7);
                if (fullWakeTimer == null) {
                    i = 0;
                } else {
                    i = 0;
                    fullWakeLockTimeTotalUs += fullWakeTimer.getTotalTimeLocked(rawRealtimeUs3, 0);
                }
                Timer partialWakeTimer = wl.getWakeTime(i);
                if (partialWakeTimer != null) {
                    partialWakeLockTimeTotalUs +=
                            partialWakeTimer.getTotalTimeLocked(rawRealtimeUs3, i);
                }
                iw--;
                i7 = 1;
            }
            iu++;
            i7 = 1;
        }
        long mToken = proto.start(1146756268047L);
        proto.write(1112396529665L, getScreenOnTime(rawRealtimeUs3, 0) / 1000);
        proto.write(1112396529666L, getPhoneOnTime(rawRealtimeUs3, 0) / 1000);
        proto.write(1112396529667L, fullWakeLockTimeTotalUs / 1000);
        proto.write(1112396529668L, partialWakeLockTimeTotalUs / 1000);
        proto.write(1112396529669L, getMobileRadioActiveTime(rawRealtimeUs3, 0) / 1000);
        proto.write(1112396529670L, getMobileRadioActiveAdjustedTime(0) / 1000);
        proto.write(1120986464263L, getMobileRadioActiveCount(0));
        proto.write(1120986464264L, getMobileRadioActiveUnknownTime(0) / 1000);
        proto.write(1112396529673L, getInteractiveTime(rawRealtimeUs3, 0) / 1000);
        proto.write(1112396529674L, getPowerSaveModeEnabledTime(rawRealtimeUs3, 0) / 1000);
        proto.write(1120986464267L, getNumConnectivityChange(0));
        proto.write(1112396529676L, getDeviceIdleModeTime(2, rawRealtimeUs3, 0) / 1000);
        proto.write(1120986464269L, getDeviceIdleModeCount(2, 0));
        proto.write(1112396529678L, getDeviceIdlingTime(2, rawRealtimeUs3, 0) / 1000);
        proto.write(1120986464271L, getDeviceIdlingCount(2, 0));
        proto.write(1112396529680L, getLongestDeviceIdleModeTime(2));
        proto.write(1112396529681L, getDeviceIdleModeTime(1, rawRealtimeUs3, 0) / 1000);
        proto.write(1120986464274L, getDeviceIdleModeCount(1, 0));
        proto.write(1112396529683L, getDeviceIdlingTime(1, rawRealtimeUs3, 0) / 1000);
        proto.write(1120986464276L, getDeviceIdlingCount(1, 0));
        proto.write(1112396529685L, getLongestDeviceIdleModeTime(1));
        proto.end(mToken);
        long multicastWakeLockTimeTotalUs = getWifiMulticastWakelockTime(rawRealtimeUs3, 0);
        int multicastWakeLockCountTotal = getWifiMulticastWakelockCount(0);
        long wmctToken = proto.start(1146756268055L);
        proto.write(1112396529665L, multicastWakeLockTimeTotalUs / 1000);
        proto.write(1120986464258L, multicastWakeLockCountTotal);
        proto.end(wmctToken);
        BatteryConsumer deviceConsumer = stats.getAggregateBatteryConsumer(0);
        int powerComponent = 0;
        while (powerComponent < 19) {
            int n = 0;
            switch (powerComponent) {
                case 0:
                    n = 7;
                    break;
                case 2:
                    n = 5;
                    break;
                case 3:
                    n = 11;
                    break;
                case 6:
                    n = 6;
                    break;
                case 8:
                    n = 2;
                    break;
                case 11:
                    n = 4;
                    break;
                case 13:
                    n = 12;
                    break;
                case 14:
                    n = 3;
                    break;
                case 15:
                    n = 13;
                    break;
                case 16:
                    n = 1;
                    break;
            }
            long puiToken = proto.start(2246267895825L);
            proto.write(1159641169921L, n);
            proto.write(1120986464258L, 0);
            proto.write(1103806595075L, deviceConsumer.getConsumedPower(powerComponent));
            proto.write(1133871366148L, shouldHidePowerComponent(powerComponent));
            proto.write(1103806595077L, 0);
            proto.write(1103806595078L, 0);
            proto.end(puiToken);
            powerComponent++;
            wmctToken = wmctToken;
            deviceConsumer = deviceConsumer;
        }
        long pusToken = proto.start(1146756268050L);
        proto.write(1103806595073L, stats.getBatteryCapacity());
        proto.write(1103806595074L, stats.getConsumedPower());
        proto.write(1103806595075L, stats.getDischargedPowerRange().getLower().doubleValue());
        proto.write(1103806595076L, stats.getDischargedPowerRange().getUpper().doubleValue());
        proto.end(pusToken);
        Map<String, ? extends Timer> rpmStats = getRpmStats();
        Map<String, ? extends Timer> screenOffRpmStats = getScreenOffRpmStats();
        for (Map.Entry<String, ? extends Timer> ent2 : rpmStats.entrySet()) {
            long rpmToken = proto.start(2246267895827L);
            proto.write(1138166333441L, ent2.getKey());
            Map<String, ? extends Timer> screenOffRpmStats2 = screenOffRpmStats;
            dumpTimer(proto, 1146756268034L, ent2.getValue(), rawRealtimeUs3, 0);
            dumpTimer(
                    proto,
                    1146756268035L,
                    screenOffRpmStats2.get(ent2.getKey()),
                    rawRealtimeUs3,
                    0);
            proto.end(rpmToken);
            multicastWakeLockCountTotal = multicastWakeLockCountTotal;
            screenOffRpmStats = screenOffRpmStats2;
        }
        for (int i8 = 0; i8 < 5; i8++) {
            long sbToken = proto.start(2246267895828L);
            proto.write(1159641169921L, i8);
            dumpTimer(proto, 1146756268034L, getScreenBrightnessTimer(i8), rawRealtimeUs3, 0);
            proto.end(sbToken);
        }
        dumpTimer(proto, 1146756268053L, getPhoneSignalScanningTimer(), rawRealtimeUs3, 0);
        for (int i9 = 0; i9 < CellSignalStrength.getNumSignalStrengthLevels(); i9++) {
            long pssToken = proto.start(2246267895824L);
            proto.write(1159641169921L, i9);
            dumpTimer(proto, 1146756268034L, getPhoneSignalStrengthTimer(i9), rawRealtimeUs3, 0);
            proto.end(pssToken);
        }
        Map<String, ? extends Timer> wakeupReasons = getWakeupReasonStats();
        for (Map.Entry<String, ? extends Timer> ent3 : wakeupReasons.entrySet()) {
            long wrToken = proto.start(2246267895830L);
            proto.write(1138166333441L, ent3.getKey());
            dumpTimer(proto, 1146756268034L, ent3.getValue(), rawRealtimeUs3, 0);
            proto.end(wrToken);
        }
        for (int i10 = 0; i10 < 5; i10++) {
            long wssToken = proto.start(2246267895832L);
            proto.write(1159641169921L, i10);
            dumpTimer(proto, 1146756268034L, getWifiSignalStrengthTimer(i10), rawRealtimeUs3, 0);
            proto.end(wssToken);
        }
        for (int i11 = 0; i11 < 8; i11++) {
            long wsToken = proto.start(2246267895833L);
            proto.write(1159641169921L, i11);
            dumpTimer(proto, 1146756268034L, getWifiStateTimer(i11), rawRealtimeUs3, 0);
            proto.end(wsToken);
        }
        for (int i12 = 0; i12 < 13; i12++) {
            long wssToken2 = proto.start(2246267895834L);
            proto.write(1159641169921L, i12);
            dumpTimer(proto, 1146756268034L, getWifiSupplStateTimer(i12), rawRealtimeUs3, 0);
            proto.end(wssToken2);
        }
        proto.end(sToken);
    }

    public static boolean checkWifiOnly(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TelephonyManager.class);
        if (tm == null) {
            return false;
        }
        return !tm.isDataCapable();
    }

    private boolean shouldHidePowerComponent(int powerComponent) {
        return powerComponent == 16
                || powerComponent == 8
                || powerComponent == 0
                || powerComponent == 15;
    }

    private static class ProportionalAttributionCalculator {
        private static final double SYSTEM_BATTERY_CONSUMER = -1.0d;
        private final PackageManager mPackageManager;
        private final SparseDoubleArray mProportionalPowerMah;
        private final HashSet<String> mSystemAndServicePackages;

        ProportionalAttributionCalculator(Context context, BatteryUsageStats stats) {
            double d;
            Resources resources;
            ProportionalAttributionCalculator proportionalAttributionCalculator = this;
            proportionalAttributionCalculator.mPackageManager = context.getPackageManager();
            Resources resources2 = context.getResources();
            String[] systemPackageArray =
                    resources2.getStringArray(R.array.config_batteryPackageTypeSystem);
            String[] servicePackageArray =
                    resources2.getStringArray(R.array.config_batteryPackageTypeService);
            proportionalAttributionCalculator.mSystemAndServicePackages =
                    new HashSet<>(systemPackageArray.length + servicePackageArray.length);
            for (String packageName : systemPackageArray) {
                proportionalAttributionCalculator.mSystemAndServicePackages.add(packageName);
            }
            for (String packageName2 : servicePackageArray) {
                proportionalAttributionCalculator.mSystemAndServicePackages.add(packageName2);
            }
            List<UidBatteryConsumer> uidBatteryConsumers = stats.getUidBatteryConsumers();
            proportionalAttributionCalculator.mProportionalPowerMah =
                    new SparseDoubleArray(uidBatteryConsumers.size());
            double systemPowerMah = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            int i = uidBatteryConsumers.size();
            while (true) {
                i--;
                d = -1.0d;
                if (i < 0) {
                    break;
                }
                UidBatteryConsumer consumer = uidBatteryConsumers.get(i);
                int uid = consumer.getUid();
                if (proportionalAttributionCalculator.isSystemUid(uid)) {
                    proportionalAttributionCalculator.mProportionalPowerMah.put(uid, -1.0d);
                    systemPowerMah += consumer.getConsumedPower();
                }
            }
            double totalRemainingPower = stats.getConsumedPower() - systemPowerMah;
            if (Math.abs(totalRemainingPower) > 0.001d) {
                int i2 = uidBatteryConsumers.size() - 1;
                while (i2 >= 0) {
                    UidBatteryConsumer consumer2 = uidBatteryConsumers.get(i2);
                    int uid2 = consumer2.getUid();
                    if (proportionalAttributionCalculator.mProportionalPowerMah.get(uid2) == d) {
                        resources = resources2;
                    } else {
                        double power = consumer2.getConsumedPower();
                        resources = resources2;
                        proportionalAttributionCalculator.mProportionalPowerMah.put(
                                uid2, power + ((systemPowerMah * power) / totalRemainingPower));
                    }
                    i2--;
                    proportionalAttributionCalculator = this;
                    resources2 = resources;
                    d = -1.0d;
                }
            }
        }

        boolean isSystemBatteryConsumer(UidBatteryConsumer consumer) {
            return this.mProportionalPowerMah.get(consumer.getUid())
                    < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        }

        double getProportionalPowerMah(UidBatteryConsumer consumer) {
            double powerMah = this.mProportionalPowerMah.get(consumer.getUid());
            return powerMah >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN
                    ? powerMah
                    : SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        }

        private boolean isSystemUid(int uid) {
            if (uid >= 0 && uid < 10000) {
                return true;
            }
            String[] packages = this.mPackageManager.getPackagesForUid(uid);
            if (packages == null) {
                return false;
            }
            for (String packageName : packages) {
                if (this.mSystemAndServicePackages.contains(packageName)) {
                    return true;
                }
            }
            return false;
        }
    }

    static class UidMobileRadioStats {
        public final double millisecondsPerPacket;
        public final int radioActiveCount;
        public final long radioActiveMs;
        public final long rxPackets;
        public final long txPackets;
        public final int uid;

        private UidMobileRadioStats(
                int uid,
                long rxPackets,
                long txPackets,
                long radioActiveMs,
                int radioActiveCount,
                double millisecondsPerPacket) {
            this.uid = uid;
            this.txPackets = txPackets;
            this.rxPackets = rxPackets;
            this.radioActiveMs = radioActiveMs;
            this.radioActiveCount = radioActiveCount;
            this.millisecondsPerPacket = millisecondsPerPacket;
        }
    }

    private List<UidMobileRadioStats> getUidMobileRadioStats(
            List<UidBatteryConsumer> uidBatteryConsumers) {
        SparseArray<? extends Uid> uidStats = getUidStats();
        List<UidMobileRadioStats> uidMobileRadioStats = Lists.newArrayList();
        for (int i = 0; i < uidBatteryConsumers.size(); i++) {
            UidBatteryConsumer consumer = uidBatteryConsumers.get(i);
            if (consumer.getConsumedPower(8) != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                int uid = consumer.getUid();
                Uid u = uidStats.get(uid);
                long rxPackets = u.getNetworkActivityPackets(0, 0);
                long txPackets = u.getNetworkActivityPackets(1, 0);
                if (rxPackets != 0 || txPackets != 0) {
                    long radioActiveMs = u.getMobileRadioActiveTime(0) / 1000;
                    int radioActiveCount = u.getMobileRadioActiveCount(0);
                    double msPerPacket = radioActiveMs / (rxPackets + txPackets);
                    if (msPerPacket != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                        uidMobileRadioStats.add(
                                new UidMobileRadioStats(
                                        uid,
                                        rxPackets,
                                        txPackets,
                                        radioActiveMs,
                                        radioActiveCount,
                                        msPerPacket));
                    }
                }
            }
        }
        uidMobileRadioStats.sort(
                new Comparator() { // from class: android.os.BatteryStats$$ExternalSyntheticLambda2
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        int compare;
                        compare =
                                Double.compare(
                                        ((BatteryStats.UidMobileRadioStats) obj2)
                                                .millisecondsPerPacket,
                                        ((BatteryStats.UidMobileRadioStats) obj)
                                                .millisecondsPerPacket);
                        return compare;
                    }
                });
        return uidMobileRadioStats;
    }

    protected static boolean isLowRamDevice() {
        return ActivityManager.isLowRamDeviceStatic();
    }

    protected static boolean isLowRamDevice$ravenwood() {
        return false;
    }

    protected static int getCellSignalStrengthLevelCount() {
        return CellSignalStrength.getNumSignalStrengthLevels();
    }

    protected static int getCellSignalStrengthLevelCount$ravenwood() {
        return 5;
    }

    protected static int getModemTxPowerLevelCount() {
        return ModemActivityInfo.getNumTxPowerLevels();
    }

    protected static int getModemTxPowerLevelCount$ravenwood() {
        return 5;
    }

    protected static boolean isKernelStatsAvailable() {
        return true;
    }

    protected static boolean isKernelStatsAvailable$ravenwood() {
        return false;
    }

    protected static int getDisplayTransport(int[] transports) {
        return NetworkCapabilitiesUtils.getDisplayTransport(transports);
    }

    protected static int getDisplayTransport$ravenwood(int[] transports) {
        for (int transport : DISPLAY_TRANSPORT_PRIORITIES) {
            for (int t : transports) {
                if (t == transport) {
                    return transport;
                }
            }
        }
        return transports[0];
    }

    void printLatestBackupData(PrintWriter pw) {
        File[] childFileList;
        long latestTime = 0;
        File backupDir = new File("/data/log/batterystats/");
        if (backupDir.exists() && (childFileList = backupDir.listFiles()) != null) {
            for (File childFile : childFileList) {
                long time =
                        Long.parseLong(
                                childFile
                                        .getAbsolutePath()
                                        .replace("/data/log/batterystats/newbatterystats", ""));
                if (time > latestTime) {
                    latestTime = time;
                }
            }
            if (latestTime <= 0) {
                return;
            }
            try {
                FileInputStream fis =
                        new FileInputStream("/data/log/batterystats/newbatterystats" + latestTime);
                try {
                    InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                    try {
                        BufferedReader br = new BufferedReader(isr);
                        try {
                            pw.println("\nLatest newbatterystats:");
                            while (true) {
                                String log = br.readLine();
                                if (log != null) {
                                    pw.println(log);
                                } else {
                                    pw.println();
                                    br.close();
                                    isr.close();
                                    fis.close();
                                    return;
                                }
                            }
                        } finally {
                        }
                    } finally {
                    }
                } finally {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getScaledCpuFreqTimes(CpuScalingPolicies policies, long[] freqTable) {
        String result = "";
        int step = 0;
        for (int policy : policies.getPolicies()) {
            result = result + "\n      ";
            for (int i = 0; i < policies.getFrequencies(policy).length; i++) {
                result = result + " " + freqTable[step + i];
            }
            step += policies.getFrequencies(policy).length;
        }
        return result;
    }
}
