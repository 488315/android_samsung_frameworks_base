package android.os;

import android.app.ActivityManager;
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
import android.os.BatteryStats;
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
import com.android.internal.accessibility.common.ShortcutConstants;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.Flags;
import com.android.internal.os.BatteryStatsHistoryIterator;
import com.android.internal.os.CpuScalingPolicies;
import com.android.internal.os.PowerStats;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.telephony.DctConstants;
import com.android.net.module.util.NetworkCapabilitiesUtils;
import com.google.android.collect.Lists;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
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
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/* loaded from: classes3.dex */
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
    private static final String[] CHECKIN_POWER_COMPONENT_LABELS;
    static final int CHECKIN_VERSION = 36;
    private static final String CPU_DATA = "cpu";
    private static final String CPU_TIMES_AT_FREQ_DATA = "ctf";
    private static final String DATA_CONNECTION_COUNT_DATA = "dcc";
    public static final int DATA_CONNECTION_EMERGENCY_SERVICE;
    static final String[] DATA_CONNECTION_NAMES;
    public static final int DATA_CONNECTION_OTHER;
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
    public static final int DUMP_DEBUG_PERF_BASELINE = 128;
    public static final int DUMP_DEVICE_WIFI_ONLY = 64;
    public static final int DUMP_HISTORY_ONLY = 8;
    public static final int DUMP_INCLUDE_HISTORY = 16;
    public static final int DUMP_VERBOSE = 32;
    public static final long DURATION_UNAVAILABLE = -1;
    private static final String FLASHLIGHT_DATA = "fla";
    public static final int FLASHLIGHT_TURNED_ON = 16;
    public static final int FOREGROUND_ACTIVITY = 10;
    private static final String FOREGROUND_ACTIVITY_DATA = "fg";
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
    public static final String[] HISTORY_EVENT_CHECKIN_NAMES;
    public static final IntToString[] HISTORY_EVENT_INT_FORMATTERS;
    public static final String[] HISTORY_EVENT_NAMES;
    public static final BitDescription[] HISTORY_STATE2_DESCRIPTIONS;
    public static final BitDescription[] HISTORY_STATE_DESCRIPTIONS;
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
    public static final int NUM_ALL_NETWORK_TYPES;
    public static final int NUM_DATA_CONNECTION_TYPES;
    public static final int NUM_HIGH_REFRESH_RATE_BINS = 4;
    public static final int NUM_NETWORK_ACTIVITY_TYPES = 10;
    public static final int NUM_PROTECT_BATTERY_MODE_TYPES;
    public static final int NUM_SCREEN_BRIGHTNESS_BINS = 5;
    public static final int NUM_WIFI_SIGNAL_STRENGTH_BINS = 5;
    public static final long POWER_DATA_UNAVAILABLE = -1;
    private static final String POWER_USE_ITEM_DATA = "pwi";
    private static final String POWER_USE_SUMMARY_DATA = "pws";
    private static final String PROCESS_DATA = "pr";
    public static final int PROCESS_STATE = 12;
    private static final String[] PROTECT_BATTERY_MODE_TYPES;
    public static final int RADIO_ACCESS_TECHNOLOGY_COUNT = 3;
    public static final int RADIO_ACCESS_TECHNOLOGY_LTE = 1;
    public static final String[] RADIO_ACCESS_TECHNOLOGY_NAMES;
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
    public static final String[] SCREEN_BRIGHTNESS_NAMES;
    static final String[] SCREEN_BRIGHTNESS_SHORT_NAMES;
    protected static final boolean SCREEN_OFF_RPM_STATS_ENABLED = false;
    public static final int SENSOR = 3;
    private static final String SENSOR_DATA = "sr";
    public static final String SERVICE_NAME = "batterystats";
    private static final String SIGNAL_SCANNING_TIME_DATA = "sst";
    private static final String SIGNAL_STRENGTH_COUNT_DATA = "sgc";
    private static final String SIGNAL_STRENGTH_TIME_DATA = "sgt";
    private static final String STATE_TIME_DATA = "st";

    @Deprecated
    public static final int STATS_CURRENT = 1;
    public static final int STATS_SINCE_CHARGED = 0;

    @Deprecated
    public static final int STATS_SINCE_UNPLUGGED = 2;
    public static final long STEP_LEVEL_INITIAL_MODE_MASK = 71776119061217280L;
    public static final int STEP_LEVEL_INITIAL_MODE_SHIFT = 48;
    public static final long STEP_LEVEL_LEVEL_MASK = 280375465082880L;
    public static final int STEP_LEVEL_LEVEL_SHIFT = 40;
    public static final int[] STEP_LEVEL_MODES_OF_INTEREST;
    public static final int STEP_LEVEL_MODE_DEVICE_IDLE = 8;
    public static final String[] STEP_LEVEL_MODE_LABELS;
    public static final int STEP_LEVEL_MODE_POWER_SAVE = 4;
    public static final int STEP_LEVEL_MODE_SCREEN_STATE = 3;
    public static final int[] STEP_LEVEL_MODE_VALUES;
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
    static final String[] WIFI_STATE_NAMES;
    private static final String WIFI_STATE_TIME_DATA = "wst";
    private static final String WIFI_SUPPL_STATE_COUNT_DATA = "wssc";
    static final String[] WIFI_SUPPL_STATE_NAMES;
    static final String[] WIFI_SUPPL_STATE_SHORT_NAMES;
    private static final String WIFI_SUPPL_STATE_TIME_DATA = "wsst";
    private static final IntToString sIntToString;
    private static final IntToString sUidToString;
    private final StringBuilder mFormatBuilder;
    private final Formatter mFormatter;
    private static final String[] STAT_NAMES = {XmlTags.TAG_LEASEE, "c", XmlTags.ATTR_UID};
    public static final long[] JOB_FRESHNESS_BUCKETS = {3600000, 7200000, 14400000, 28800000, Long.MAX_VALUE};

    public interface BatteryStatsDumpHelper {
        BatteryUsageStats getBatteryUsageStats(BatteryStats batteryStats, boolean z);
    }

    public static abstract class ControllerActivityCounter {
        public abstract LongCounter getIdleTimeCounter();

        public abstract LongCounter getMonitoredRailChargeConsumedMaMs();

        public abstract LongCounter getPowerCounter();

        public abstract LongCounter getRxTimeCounter();

        public abstract LongCounter getScanTimeCounter();

        public abstract LongCounter getSleepTimeCounter();

        public abstract LongCounter[] getTxTimeCounters();
    }

    public static abstract class Counter {
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

    public static abstract class LongCounter {
        public abstract long getCountForProcessState(int i);

        public abstract long getCountLocked(int i);

        public abstract void logState(Printer printer, String str);
    }

    public static abstract class LongCounterArray {
        public abstract long[] getCountsLocked(int i);

        public abstract void logState(Printer printer, String str);
    }

    public static abstract class ModemActivityCounter {
        public abstract LongCounter getIdleTimeCounter();

        public abstract ModemTxRxCounter getLcModemActivityInfo();

        public abstract ModemTxRxCounter getNrModemActivityInfo();

        public abstract LongCounter getSleepTimeCounter();
    }

    public static abstract class ModemTxRxCounter {
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
    public @interface RadioAccessTechnology {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StatName {
    }

    public static abstract class Timer {
        public abstract int getCountLocked(int i);

        public long getCurrentDurationMsLocked(long j) {
            return -1L;
        }

        public long getMaxDurationMsLocked(long j) {
            return -1L;
        }

        public Timer getSubTimer() {
            return null;
        }

        public abstract long getTimeSinceMarkLocked(long j);

        public long getTotalDurationMsLocked(long j) {
            return -1L;
        }

        public abstract long getTotalTimeLocked(long j, int i);

        public boolean isRunningLocked() {
            return false;
        }

        public abstract void logState(Printer printer, String str);
    }

    protected static int getCellSignalStrengthLevelCount$ravenwood() {
        return 5;
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

    protected static boolean isLowRamDevice$ravenwood() {
        return false;
    }

    public static int mapToInternalProcessState(int i) {
        if (i == 20) {
            return 7;
        }
        if (i == 2) {
            return 0;
        }
        if (i == 3) {
            return 3;
        }
        if (i == 4 || i == 5) {
            return 1;
        }
        if (i <= 6) {
            return 2;
        }
        if (i <= 11) {
            return 3;
        }
        if (i <= 12) {
            return 4;
        }
        return i <= 13 ? 5 : 6;
    }

    public static int mapUidProcessStateToBatteryConsumerProcessState(int i) {
        if (i != 0) {
            if (i == 1) {
                return 3;
            }
            if (i != 2) {
                if (i == 3 || i == 4) {
                    return 2;
                }
                return i != 6 ? 0 : 4;
            }
        }
        return 1;
    }

    private boolean shouldHidePowerComponent(int i) {
        return i == 16 || i == 8 || i == 0 || i == 15;
    }

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

    public abstract long getCoverDisplayHighRefreshRateTime(long j, int i);

    public abstract Timer getCoverDisplayRefreshRateTimer(int i);

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

    public abstract int getDischargeAmountSilentOnScreenOffSinceChargePermil();

    public abstract int getDischargeAmountSilentOnScreenOnSinceChargePermil();

    public abstract int getDischargeAmountSubScreenDozeSinceChargePermil();

    public abstract int getDischargeAmountSubScreenOnSinceChargePermil();

    public abstract int getDischargeCurrentLevel();

    public abstract LevelStepTracker getDischargeLevelStepTracker();

    public abstract int getDischargeStartLevel();

    public abstract int getDisplayCount();

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

    public abstract long[][] getKernelCpuSpeedTimes();

    public abstract LongSparseArray<? extends Timer> getKernelMemoryStats();

    public abstract Map<String, ? extends Timer> getKernelWakelockStats();

    public abstract int getLearnedBatteryCapacity();

    public abstract long getLongestDeviceIdleModeTime(int i);

    public abstract int getLowDischargeAmountSinceCharge();

    public abstract long getMainDisplayHighRefreshRateTime(long j, int i);

    public abstract Timer getMainDisplayRefreshRateTimer(int i);

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

    public abstract long[][] getScreenOffKernelCpuSpeedTimes();

    public abstract Map<String, ? extends Timer> getScreenOffRpmStats();

    public abstract int getScreenOnCount(int i);

    public abstract long getScreenOnEnergyConsumptionUC();

    public abstract long getScreenOnGpsRunningTime(long j, int i);

    public abstract long getScreenOnTime(long j, int i);

    public abstract Map<String, ? extends Counter> getScreenWakeStats();

    public abstract long getSilentLogOnScreenOffTime(long j, int i);

    public abstract long getSilentLogOnScreenOnTime(long j, int i);

    public abstract long getSpeakerCallTime(int i, int i2);

    public abstract long getSpeakerMediaTime(int i, int i2);

    public abstract long getStartClockTime();

    public abstract int getStartCount();

    public abstract String getStartPlatformVersion();

    public abstract long getStatsStartRealtime();

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

    public void prepareForDumpLocked() {
    }

    public abstract void updateTxPowerSharing();

    public BatteryStats() {
        StringBuilder sb = new StringBuilder(32);
        this.mFormatBuilder = sb;
        this.mFormatter = new Formatter(sb);
    }

    static {
        String[] strArr = {"off", "max", "longTerm", "basic", "adaptive"};
        PROTECT_BATTERY_MODE_TYPES = strArr;
        NUM_PROTECT_BATTERY_MODE_TYPES = strArr.length;
        String[] strArr2 = {"dark", "dim", "medium", "light", "bright"};
        SCREEN_BRIGHTNESS_NAMES = strArr2;
        String[] strArr3 = {"0", "1", "2", "3", "4"};
        SCREEN_BRIGHTNESS_SHORT_NAMES = strArr3;
        String[] strArr4 = {"oos", "gprs", Context.SEM_EDGE_SERVICE, "umts", "cdma", "evdo_0", "evdo_A", "1xrtt", "hsdpa", "hsupa", "hspa", "iden", "evdo_b", "lte", "ehrpd", "hspap", "gsm", "td_scdma", "iwlan", "lte_ca", "nr", "emngcy", "other"};
        DATA_CONNECTION_NAMES = strArr4;
        int allNetworkTypesCount = getAllNetworkTypesCount();
        NUM_ALL_NETWORK_TYPES = allNetworkTypesCount;
        DATA_CONNECTION_EMERGENCY_SERVICE = allNetworkTypesCount + 1;
        DATA_CONNECTION_OTHER = allNetworkTypesCount + 2;
        NUM_DATA_CONNECTION_TYPES = allNetworkTypesCount + 3;
        RADIO_ACCESS_TECHNOLOGY_NAMES = new String[]{"Other", DctConstants.RAT_NAME_LTE, "NR"};
        String[] strArr5 = {"invalid", "disconn", "disabled", "inactive", "scanning", "authenticating", "associating", "associated", "4-way-handshake", "group-handshake", "completed", "dormant", "uninit"};
        WIFI_SUPPL_STATE_NAMES = strArr5;
        String[] strArr6 = {"inv", "dsc", "dis", "inact", "scan", Context.AUTH_SERVICE, "ascing", "asced", "4-way", "group", "compl", "dorm", "uninit"};
        WIFI_SUPPL_STATE_SHORT_NAMES = strArr6;
        HISTORY_STATE_DESCRIPTIONS = new BitDescription[]{new BitDescription(Integer.MIN_VALUE, "running", "r"), new BitDescription(1073741824, "wake_lock", "w"), new BitDescription(8388608, Context.SENSOR_SERVICE, XmlTags.TAG_SESSION), new BitDescription(536870912, "gps", "g"), new BitDescription(268435456, "wifi_full_lock", "Wl"), new BitDescription(134217728, "wifi_scan", "Ws"), new BitDescription(65536, "wifi_multicast", "Wm"), new BitDescription(67108864, "wifi_radio", "Wr"), new BitDescription(33554432, "mobile_radio", "Pr"), new BitDescription(2097152, "phone_scanning", "Psc"), new BitDescription(4194304, "audio", FullBackup.APK_TREE_TOKEN), new BitDescription(1048576, "screen", GnssSignalType.CODE_TYPE_S), new BitDescription(524288, BatteryManager.EXTRA_PLUGGED, "BP"), new BitDescription(262144, "screen_doze", "Sd"), new BitDescription(HistoryItem.STATE_DATA_CONNECTION_MASK, 9, "data_conn", "Pcn", strArr4, strArr4), new BitDescription(448, 6, "phone_state", "Pst", new String[]{"in", "out", "emergency", "off"}, new String[]{"in", "out", "em", "off"}), new BitDescription(56, 3, "phone_signal_strength", "Pss", new String[]{"none", "poor", "moderate", "good", "great"}, new String[]{"0", "1", "2", "3", "4"}), new BitDescription(7, 0, "brightness", "Sb", strArr2, strArr3)};
        HISTORY_STATE2_DESCRIPTIONS = new BitDescription[]{new BitDescription(Integer.MIN_VALUE, "power_save", "ps"), new BitDescription(1073741824, "video", "v"), new BitDescription(536870912, "wifi_running", "Ww"), new BitDescription(268435456, "wifi", GnssSignalType.CODE_TYPE_W), new BitDescription(134217728, "flashlight", "fl"), new BitDescription(100663296, 25, "device_idle", "di", new String[]{"off", "light", "full", "???"}, new String[]{"off", "light", "full", "???"}), new BitDescription(16777216, UsbManager.USB_FUNCTION_CHARGING, "ch"), new BitDescription(262144, "usb_data", "Ud"), new BitDescription(8388608, "phone_in_call", "Pcl"), new BitDescription(4194304, "bluetooth", XmlTags.TAG_BLOB), new BitDescription(112, 4, "wifi_signal_strength", "Wss", new String[]{"0", "1", "2", "3", "4"}, new String[]{"0", "1", "2", "3", "4"}), new BitDescription(15, 0, "wifi_suppl", "Wsp", strArr5, strArr6), new BitDescription(2097152, Context.CAMERA_SERVICE, Credentials.CERTIFICATE_USAGE_CA), new BitDescription(1048576, "ble_scan", "bles"), new BitDescription(524288, "cellular_high_tx_power", "Chtp"), new BitDescription(384, 7, "gps_signal_quality", "Gss", new String[]{"poor", "good", "none"}, new String[]{"poor", "good", "none"}), new BitDescription(1536, 9, "nr_state", "nrs", new String[]{"none", NetworkPolicyManager.FIREWALL_CHAIN_NAME_RESTRICTED, "not_restricted", "connected"}, new String[]{"0", "1", "2", "3"})};
        HISTORY_EVENT_NAMES = new String[]{PerfettoProtoLogImpl.NULL_STRING, "proc", FOREGROUND_ACTIVITY_DATA, GenerateXML.TOP, "sync", "wake_lock_in", "job", "user", "userfg", "conn", "active", "pkginst", "pkgunin", "alarm", Context.STATS_MANAGER, "pkginactive", "pkgactive", "tmpwhitelist", "screenwake", "wakeupap", "longwake", "state", "display_state_changed"};
        HISTORY_EVENT_CHECKIN_NAMES = new String[]{"Enl", "Epr", "Efg", "Etp", "Esy", "Ewl", "Ejb", "Eur", "Euf", "Ecn", "Eac", "Epi", "Epu", "Eal", "Est", "Eai", "Eaa", "Etw", "Esw", "Ewa", "Elw", "Esc", "Eds"};
        IntToString intToString = new IntToString() { // from class: android.os.BatteryStats$$ExternalSyntheticLambda0
            @Override // android.os.BatteryStats.IntToString
            public final String applyAsString(int i) {
                return UserHandle.formatUid(i);
            }
        };
        sUidToString = intToString;
        IntToString intToString2 = new IntToString() { // from class: android.os.BatteryStats$$ExternalSyntheticLambda1
            @Override // android.os.BatteryStats.IntToString
            public final String applyAsString(int i) {
                return Integer.toString(i);
            }
        };
        sIntToString = intToString2;
        HISTORY_EVENT_INT_FORMATTERS = new IntToString[]{intToString, intToString, intToString, intToString, intToString, intToString, intToString, intToString, intToString, intToString, intToString, intToString2, intToString, intToString, intToString, intToString, intToString, intToString, intToString, intToString, intToString, intToString2, intToString};
        WIFI_STATE_NAMES = new String[]{"off", "scanning", "no_net", "disconn", "sta", SemWifiP2pManager.TYPE_WIFI_P2P, "sta_p2p", "soft_ap"};
        STEP_LEVEL_MODES_OF_INTEREST = new int[]{7, 15, 11, 7, 7, 7, 7, 7, 15, 11};
        STEP_LEVEL_MODE_VALUES = new int[]{0, 4, 8, 1, 5, 2, 6, 3, 7, 11};
        STEP_LEVEL_MODE_LABELS = new String[]{"screen off", "screen off power save", "screen off device idle", "screen on", "screen on power save", "screen doze", "screen doze power save", "screen doze-suspend", "screen doze-suspend power save", "screen doze-suspend device idle"};
        String[] strArr7 = new String[20];
        CHECKIN_POWER_COMPONENT_LABELS = strArr7;
        strArr7[0] = "scrn";
        strArr7[1] = CPU_DATA;
        strArr7[2] = "blue";
        strArr7[3] = Context.CAMERA_SERVICE;
        strArr7[4] = "audio";
        strArr7[5] = "video";
        strArr7[6] = "flashlight";
        strArr7[8] = "cell";
        strArr7[9] = "sensors";
        strArr7[10] = "gnss";
        strArr7[11] = "wifi";
        strArr7[13] = "memory";
        strArr7[14] = "phone";
        strArr7[15] = "ambi";
        strArr7[16] = "idle";
        DISPLAY_TRANSPORT_PRIORITIES = new int[]{4, 0, 5, 2, 1, 3, 8};
    }

    public static abstract class Uid {
        public static final int NUM_PROCESS_STATE = 7;
        public static final int NUM_USER_ACTIVITY_TYPES;
        public static final int NUM_WIFI_BATCHED_SCAN_BINS = 5;
        public static final int PROCESS_STATE_BACKGROUND = 3;
        public static final int PROCESS_STATE_CACHED = 6;
        public static final int PROCESS_STATE_FOREGROUND = 2;
        public static final int PROCESS_STATE_FOREGROUND_SERVICE = 1;
        public static final int PROCESS_STATE_HEAVY_WEIGHT = 5;
        public static final int PROCESS_STATE_NONEXISTENT = 7;
        public static final int PROCESS_STATE_TOP = 0;
        public static final int PROCESS_STATE_TOP_SLEEPING = 4;
        static final String[] USER_ACTIVITY_TYPES;
        static final String[] PROCESS_STATE_NAMES = {"Top", "Fg Service", "Foreground", "Background", "Top Sleeping", "Heavy Weight", "Cached"};
        public static final String[] UID_PROCESS_TYPES = {"T", "FS", "F", GnssSignalType.CODE_TYPE_B, "TS", "HW", GnssSignalType.CODE_TYPE_C};

        public static abstract class Pkg {

            public static abstract class Serv {
                public abstract int getLaunches(int i);

                public abstract long getStartTime(long j, int i);

                public abstract int getStarts(int i);
            }

            public abstract ArrayMap<String, ? extends Serv> getServiceStats();

            public abstract ArrayMap<String, ? extends Counter> getWakeupAlarmStats();
        }

        public static abstract class Proc {

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

        public static abstract class Sensor {
            public static final int GPS = -10000;
            public static final int actualGPS = -10001;

            public abstract int getHandle();

            public abstract Timer getSensorBackgroundTime();

            public abstract Timer getSensorTime();
        }

        public static abstract class Wakelock {
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

        static {
            String[] strArr = {"other", "button", "touch", Context.ACCESSIBILITY_SERVICE, Context.ATTENTION_SERVICE, "faceDown", "deviceState"};
            USER_ACTIVITY_TYPES = strArr;
            NUM_USER_ACTIVITY_TYPES = strArr.length;
        }

        public class Pid {
            public int mWakeNesting;
            public long mWakeStartMs;
            public long mWakeSumMs;

            public Pid(Uid uid) {
            }
        }
    }

    public static final class LevelStepTracker {
        public long mLastStepTime = -1;
        public int mNumStepDurations;
        public final long[] mStepDurations;

        public LevelStepTracker(int i) {
            this.mStepDurations = new long[i];
        }

        public LevelStepTracker(int i, long[] jArr) {
            this.mNumStepDurations = i;
            long[] jArr2 = new long[i];
            this.mStepDurations = jArr2;
            System.arraycopy(jArr, 0, jArr2, 0, i);
        }

        public long getDurationAt(int i) {
            return this.mStepDurations[i] & BatteryStats.STEP_LEVEL_TIME_MASK;
        }

        public int getLevelAt(int i) {
            return (int) ((this.mStepDurations[i] & BatteryStats.STEP_LEVEL_LEVEL_MASK) >> 40);
        }

        public int getInitModeAt(int i) {
            return (int) ((this.mStepDurations[i] & BatteryStats.STEP_LEVEL_INITIAL_MODE_MASK) >> 48);
        }

        public int getModModeAt(int i) {
            return (int) ((this.mStepDurations[i] & BatteryStats.STEP_LEVEL_MODIFIED_MODE_MASK) >> 56);
        }

        private void appendHex(long j, int i, StringBuilder sb) {
            boolean z = false;
            while (i >= 0) {
                int i2 = (int) ((j >> i) & 15);
                i -= 4;
                if (z || i2 != 0) {
                    if (i2 >= 0 && i2 <= 9) {
                        sb.append((char) (i2 + 48));
                    } else {
                        sb.append((char) (i2 + 87));
                    }
                    z = true;
                }
            }
        }

        public void encodeEntryAt(int i, StringBuilder sb) {
            long j = this.mStepDurations[i];
            long j2 = BatteryStats.STEP_LEVEL_TIME_MASK & j;
            int i2 = (int) ((BatteryStats.STEP_LEVEL_LEVEL_MASK & j) >> 40);
            int i3 = (int) ((BatteryStats.STEP_LEVEL_INITIAL_MODE_MASK & j) >> 48);
            int i4 = (int) ((j & BatteryStats.STEP_LEVEL_MODIFIED_MODE_MASK) >> 56);
            int i5 = (i3 & 3) + 1;
            if (i5 == 1) {
                sb.append('f');
            } else if (i5 == 2) {
                sb.append('o');
            } else if (i5 == 3) {
                sb.append(DateFormat.DATE);
            } else if (i5 == 4) {
                sb.append(DateFormat.TIME_ZONE);
            }
            if ((i3 & 4) != 0) {
                sb.append('p');
            }
            if ((i3 & 8) != 0) {
                sb.append('i');
            }
            int i6 = (i4 & 3) + 1;
            if (i6 == 1) {
                sb.append('F');
            } else if (i6 == 2) {
                sb.append('O');
            } else if (i6 == 3) {
                sb.append('D');
            } else if (i6 == 4) {
                sb.append('Z');
            }
            if ((i4 & 4) != 0) {
                sb.append('P');
            }
            if ((i4 & 8) != 0) {
                sb.append('I');
            }
            sb.append('-');
            appendHex(i2, 4, sb);
            sb.append('-');
            appendHex(j2, 36, sb);
        }

        public void decodeEntryAt(int i, String str) {
            int i2;
            char charAt;
            int i3;
            char charAt2;
            long j;
            int length = str.length();
            int i4 = 0;
            long j2 = 0;
            while (i4 < length && (charAt2 = str.charAt(i4)) != '-') {
                i4++;
                if (charAt2 == 'D') {
                    j = 144115188075855872L;
                } else if (charAt2 != 'F') {
                    if (charAt2 == 'I') {
                        j = 576460752303423488L;
                    } else if (charAt2 == 'Z') {
                        j = 216172782113783808L;
                    } else if (charAt2 == 'd') {
                        j = 562949953421312L;
                    } else if (charAt2 != 'f') {
                        if (charAt2 == 'i') {
                            j = FrontendInnerFec.FEC_135_180;
                        } else if (charAt2 == 'z') {
                            j = 844424930131968L;
                        } else if (charAt2 == 'O') {
                            j = 72057594037927936L;
                        } else if (charAt2 == 'P') {
                            j = 288230376151711744L;
                        } else if (charAt2 == 'o') {
                            j = 281474976710656L;
                        } else if (charAt2 == 'p') {
                            j = FrontendInnerFec.FEC_132_180;
                        }
                    }
                }
                j2 |= j;
            }
            int i5 = i4 + 1;
            long j3 = 0;
            while (i5 < length && (charAt = str.charAt(i5)) != '-') {
                i5++;
                j3 <<= 4;
                if (charAt >= '0' && charAt <= '9') {
                    i3 = charAt - '0';
                } else if (charAt >= 'a' && charAt <= 'f') {
                    i3 = charAt - 'W';
                } else if (charAt >= 'A' && charAt <= 'F') {
                    i3 = charAt - '7';
                }
                j3 += i3;
            }
            int i6 = i5 + 1;
            long j4 = j2 | ((j3 << 40) & BatteryStats.STEP_LEVEL_LEVEL_MASK);
            long j5 = 0;
            while (i6 < length) {
                char charAt3 = str.charAt(i6);
                if (charAt3 == '-') {
                    break;
                }
                i6++;
                j5 <<= 4;
                if (charAt3 >= '0' && charAt3 <= '9') {
                    i2 = charAt3 - '0';
                } else if (charAt3 >= 'a' && charAt3 <= 'f') {
                    i2 = charAt3 - 'W';
                } else if (charAt3 >= 'A' && charAt3 <= 'F') {
                    i2 = charAt3 - '7';
                }
                j5 += i2;
            }
            this.mStepDurations[i] = (j5 & BatteryStats.STEP_LEVEL_TIME_MASK) | j4;
        }

        public void init() {
            this.mLastStepTime = -1L;
            this.mNumStepDurations = 0;
        }

        public void clearTime() {
            this.mLastStepTime = -1L;
        }

        public long computeTimePerLevel() {
            long[] jArr = this.mStepDurations;
            int i = this.mNumStepDurations;
            if (i <= 0) {
                return -1L;
            }
            long j = 0;
            for (int i2 = 0; i2 < i; i2++) {
                j += jArr[i2] & BatteryStats.STEP_LEVEL_TIME_MASK;
            }
            return j / i;
        }

        public long computeTimeEstimate(long j, long j2, int[] iArr) {
            long[] jArr = this.mStepDurations;
            int i = this.mNumStepDurations;
            if (i <= 0) {
                return -1L;
            }
            long j3 = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < i; i3++) {
                long j4 = jArr[i3];
                long j5 = (BatteryStats.STEP_LEVEL_INITIAL_MODE_MASK & j4) >> 48;
                if ((((BatteryStats.STEP_LEVEL_MODIFIED_MODE_MASK & j4) >> 56) & j) == 0 && (j5 & j) == j2) {
                    i2++;
                    j3 += j4 & BatteryStats.STEP_LEVEL_TIME_MASK;
                }
            }
            if (i2 <= 0) {
                return -1L;
            }
            if (iArr != null) {
                iArr[0] = i2;
            }
            return (j3 / i2) * 100;
        }

        public void addLevelSteps(int i, long j, long j2) {
            int i2 = this.mNumStepDurations;
            long j3 = this.mLastStepTime;
            if (j3 >= 0 && i > 0) {
                long[] jArr = this.mStepDurations;
                long j4 = j2 - j3;
                for (int i3 = 0; i3 < i; i3++) {
                    System.arraycopy(jArr, 0, jArr, 1, jArr.length - 1);
                    long j5 = j4 / (i - i3);
                    j4 -= j5;
                    if (j5 > BatteryStats.STEP_LEVEL_TIME_MASK) {
                        j5 = 1099511627775L;
                    }
                    jArr[0] = j5 | j;
                }
                i2 += i;
                if (i2 > jArr.length) {
                    i2 = jArr.length;
                }
            }
            this.mNumStepDurations = i2;
            this.mLastStepTime = j2;
        }

        public void readFromParcel(Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt > this.mStepDurations.length) {
                throw new ParcelFormatException("more step durations than available: " + readInt);
            }
            this.mNumStepDurations = readInt;
            for (int i = 0; i < readInt; i++) {
                this.mStepDurations[i] = parcel.readLong();
            }
        }

        public void writeToParcel(Parcel parcel) {
            int i = this.mNumStepDurations;
            parcel.writeInt(i);
            for (int i2 = 0; i2 < i; i2++) {
                parcel.writeLong(this.mStepDurations[i2]);
            }
        }
    }

    public static final class HistoryTag {
        public static final int HISTORY_TAG_POOL_OVERFLOW = -1;
        public int poolIdx;
        public String string;
        public int uid;

        public void setTo(HistoryTag historyTag) {
            this.string = historyTag.string;
            this.uid = historyTag.uid;
            this.poolIdx = historyTag.poolIdx;
        }

        public void setTo(String str, int i) {
            this.string = str;
            this.uid = i;
            this.poolIdx = -1;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.string);
            parcel.writeInt(this.uid);
        }

        public void readFromParcel(Parcel parcel) {
            this.string = parcel.readString();
            this.uid = parcel.readInt();
            this.poolIdx = -1;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            HistoryTag historyTag = (HistoryTag) obj;
            return this.uid == historyTag.uid && this.string.equals(historyTag.string);
        }

        public int hashCode() {
            return (this.string.hashCode() * 31) + this.uid;
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

        public void writeToParcel(Parcel parcel) {
            parcel.writeInt(this.userTime);
            parcel.writeInt(this.systemTime);
            parcel.writeInt(this.appCpuUid1);
            parcel.writeInt(this.appCpuUTime1);
            parcel.writeInt(this.appCpuSTime1);
            parcel.writeInt(this.appCpuUid2);
            parcel.writeInt(this.appCpuUTime2);
            parcel.writeInt(this.appCpuSTime2);
            parcel.writeInt(this.appCpuUid3);
            parcel.writeInt(this.appCpuUTime3);
            parcel.writeInt(this.appCpuSTime3);
            parcel.writeInt(this.statUserTime);
            parcel.writeInt(this.statSystemTime);
            parcel.writeInt(this.statIOWaitTime);
            parcel.writeInt(this.statIrqTime);
            parcel.writeInt(this.statSoftIrqTime);
            parcel.writeInt(this.statIdlTime);
            parcel.writeString8(this.statSubsystemPowerState);
        }

        public void readFromParcel(Parcel parcel) {
            this.userTime = parcel.readInt();
            this.systemTime = parcel.readInt();
            this.appCpuUid1 = parcel.readInt();
            this.appCpuUTime1 = parcel.readInt();
            this.appCpuSTime1 = parcel.readInt();
            this.appCpuUid2 = parcel.readInt();
            this.appCpuUTime2 = parcel.readInt();
            this.appCpuSTime2 = parcel.readInt();
            this.appCpuUid3 = parcel.readInt();
            this.appCpuUTime3 = parcel.readInt();
            this.appCpuSTime3 = parcel.readInt();
            this.statUserTime = parcel.readInt();
            this.statSystemTime = parcel.readInt();
            this.statIOWaitTime = parcel.readInt();
            this.statIrqTime = parcel.readInt();
            this.statSoftIrqTime = parcel.readInt();
            this.statIdlTime = parcel.readInt();
            this.statSubsystemPowerState = parcel.readString8();
        }

        public boolean isEmpty() {
            return this.userTime == 0 && this.systemTime == 0 && this.appCpuUid1 == -1 && this.appCpuUid2 == -1 && this.appCpuUid3 == -1 && this.statSystemTime == 0 && this.statIOWaitTime == 0 && this.statIrqTime == 0 && this.statSoftIrqTime == 0 && this.statIdlTime == 0 && this.statSubsystemPowerState == null;
        }
    }

    public static final class ProcessStateChange {
        private static final int LARGE_UID_FLAG = Integer.MIN_VALUE;
        private static final int PROC_STATE_MASK = 2130706432;
        private static final int PROC_STATE_SHIFT = Integer.numberOfTrailingZeros(PROC_STATE_MASK);
        private static final int SMALL_UID_MASK = 16777215;
        public int processState;
        public int uid;

        public void writeToParcel(Parcel parcel) {
            int i = this.processState << PROC_STATE_SHIFT;
            int i2 = this.uid;
            if (((-16777216) & i2) == 0) {
                parcel.writeInt(i | i2);
            } else {
                parcel.writeInt(i | Integer.MIN_VALUE);
                parcel.writeInt(this.uid);
            }
        }

        public void readFromParcel(Parcel parcel) {
            int readInt = parcel.readInt();
            int i = (PROC_STATE_MASK & readInt) >>> PROC_STATE_SHIFT;
            this.processState = i;
            if (i >= 5) {
                Slog.e(BatteryStats.TAG, "Unrecognized proc state in battery history: " + this.processState);
                this.processState = 0;
            }
            if ((Integer.MIN_VALUE & readInt) == 0) {
                this.uid = (-2130706433) & readInt;
            } else {
                this.uid = parcel.readInt();
            }
        }

        public String formatForBatteryHistory() {
            return UserHandle.formatUid(this.uid) + ": " + BatteryConsumer.processStateToString(this.processState);
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
        public static final int EVENT_COUNT = 23;
        public static final int EVENT_DISPLAY_STATE_CHANGED = 22;
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
        public static final int GNSS_SIGNAL_QUALITY_NONE = 2;
        public static final int IMPORTANT_FOR_POWER_STATS_STATES = 1623195648;
        public static final int IMPORTANT_FOR_POWER_STATS_STATES2 = 1210057088;
        public static final int MOST_INTERESTING_STATES = 1835008;
        public static final int MOST_INTERESTING_STATES2 = -1749024768;
        public static final int SEC_TEMPERATURE2_SUB_SCREEN_DOZE_FLAG = 536870912;
        public static final int SEC_TEMPERATURE2_SUB_SCREEN_ON_FLAG = 268435456;
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

        public HistoryItem(Parcel parcel) {
            this.cmd = (byte) -1;
            this.localWakelockTag = new HistoryTag();
            this.localWakeReasonTag = new HistoryTag();
            this.localEventTag = new HistoryTag();
            this.localProcessStateChange = new ProcessStateChange();
            readFromParcel(parcel);
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(this.time);
            parcel.writeInt((this.cmd & 255) | ((this.batteryLevel << 8) & 65280) | ((this.batteryStatus << 16) & 983040) | ((this.batteryHealth << 20) & SurfaceControl.NO_REMOTECONTROL) | ((this.batteryPlugType << 24) & 251658240) | (this.wakelockTag != null ? 268435456 : 0) | (this.wakeReasonTag != null ? 536870912 : 0) | (this.eventCode != 0 ? 1073741824 : 0));
            parcel.writeInt((this.batteryTemperature & 65535) | ((this.batteryVoltage << 16) & (-65536)));
            parcel.writeInt((this.current & 65535) | ((this.ap_temp << 16) & Spanned.SPAN_PRIORITY) | ((this.pa_temp << 24) & (-16777216)));
            parcel.writeInt(((this.sub_batt_temp << 8) & 65280) | ((this.skin_temp << 16) & Spanned.SPAN_PRIORITY) | ((this.wifi_ap << 25) & 33554432) | ((this.otgOnline << 26) & 67108864) | ((this.highSpeakerVolume << 27) & 134217728) | ((this.subScreenOn << SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN) & 268435456) | ((this.subScreenDoze << SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEOUT) & 536870912));
            parcel.writeInt((this.batterySecTxShareEvent & 16777215) | ((this.batterySecOnline << 24) & (-16777216)));
            parcel.writeInt(this.batterySecCurrentEvent);
            parcel.writeInt(this.batterySecEvent);
            parcel.writeInt(this.protectBatteryMode);
            parcel.writeInt(this.batteryChargeUah);
            parcel.writeDouble(this.modemRailChargeMah);
            parcel.writeDouble(this.wifiRailChargeMah);
            parcel.writeInt(this.states);
            parcel.writeInt(this.states2);
            HistoryTag historyTag = this.wakelockTag;
            if (historyTag != null) {
                historyTag.writeToParcel(parcel, i);
            }
            HistoryTag historyTag2 = this.wakeReasonTag;
            if (historyTag2 != null) {
                historyTag2.writeToParcel(parcel, i);
            }
            int i2 = this.eventCode;
            if (i2 != 0) {
                parcel.writeInt(i2);
                this.eventTag.writeToParcel(parcel, i);
            }
            byte b = this.cmd;
            if (b == 5 || b == 7) {
                parcel.writeLong(this.currentTime);
            }
        }

        public void readFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            this.time = parcel.readLong();
            int readInt = parcel.readInt();
            this.cmd = (byte) (readInt & 255);
            this.batteryLevel = (byte) ((readInt >> 8) & 255);
            this.batteryStatus = (byte) ((readInt >> 16) & 15);
            this.batteryHealth = (byte) ((readInt >> 20) & 15);
            this.batteryPlugType = (byte) ((readInt >> 24) & 15);
            int readInt2 = parcel.readInt();
            this.batteryTemperature = (short) (readInt2 & 65535);
            this.batteryVoltage = (short) ((readInt2 >> 16) & 65535);
            int readInt3 = parcel.readInt();
            this.current = (short) (65535 & readInt3);
            this.ap_temp = (byte) ((readInt3 >> 16) & 255);
            this.pa_temp = (byte) ((readInt3 >> 24) & 255);
            int readInt4 = parcel.readInt();
            this.sub_batt_temp = (byte) ((readInt4 >> 8) & 255);
            this.skin_temp = (byte) ((readInt4 >> 16) & 255);
            this.wifi_ap = (byte) ((readInt4 >> 25) & 1);
            this.otgOnline = (byte) ((readInt4 >> 26) & 1);
            this.highSpeakerVolume = (byte) ((readInt4 >> 27) & 1);
            this.subScreenOn = (byte) ((readInt4 >> 28) & 1);
            this.subScreenDoze = (byte) ((readInt4 >> 29) & 1);
            int readInt5 = parcel.readInt();
            this.batterySecTxShareEvent = 16777215 & readInt5;
            this.batterySecOnline = (byte) ((readInt5 >> 24) & 255);
            this.batterySecCurrentEvent = parcel.readInt();
            this.batterySecEvent = parcel.readInt();
            this.protectBatteryMode = parcel.readInt();
            this.batteryChargeUah = parcel.readInt();
            this.modemRailChargeMah = parcel.readDouble();
            this.wifiRailChargeMah = parcel.readDouble();
            this.states = parcel.readInt();
            this.states2 = parcel.readInt();
            if ((268435456 & readInt) != 0) {
                HistoryTag historyTag = this.localWakelockTag;
                this.wakelockTag = historyTag;
                historyTag.readFromParcel(parcel);
            } else {
                this.wakelockTag = null;
            }
            if ((536870912 & readInt) != 0) {
                HistoryTag historyTag2 = this.localWakeReasonTag;
                this.wakeReasonTag = historyTag2;
                historyTag2.readFromParcel(parcel);
            } else {
                this.wakeReasonTag = null;
            }
            if ((readInt & 1073741824) != 0) {
                this.eventCode = parcel.readInt();
                HistoryTag historyTag3 = this.localEventTag;
                this.eventTag = historyTag3;
                historyTag3.readFromParcel(parcel);
            } else {
                this.eventCode = 0;
                this.eventTag = null;
            }
            byte b = this.cmd;
            if (b == 5 || b == 7) {
                this.currentTime = parcel.readLong();
            } else {
                this.currentTime = 0L;
            }
            this.numReadInts += (parcel.dataPosition() - dataPosition) / 4;
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
            this.states2 = 256;
            this.wakelockTag = null;
            this.wakeReasonTag = null;
            this.eventCode = 0;
            this.eventTag = null;
            this.tagsFirstOccurrence = false;
            this.powerStats = null;
            this.processStateChange = null;
            this.stepDetails = null;
        }

        public void setTo(HistoryItem historyItem) {
            this.time = historyItem.time;
            this.cmd = historyItem.cmd;
            setToCommon(historyItem);
        }

        public void setTo(long j, byte b, HistoryItem historyItem) {
            this.time = j;
            this.cmd = b;
            setToCommon(historyItem);
        }

        private void setToCommon(HistoryItem historyItem) {
            this.batteryLevel = historyItem.batteryLevel;
            this.batteryStatus = historyItem.batteryStatus;
            this.batteryHealth = historyItem.batteryHealth;
            this.batteryPlugType = historyItem.batteryPlugType;
            this.batteryTemperature = historyItem.batteryTemperature;
            this.batteryVoltage = historyItem.batteryVoltage;
            this.current = historyItem.current;
            this.ap_temp = historyItem.ap_temp;
            this.pa_temp = historyItem.pa_temp;
            this.sub_batt_temp = historyItem.sub_batt_temp;
            this.skin_temp = historyItem.skin_temp;
            this.wifi_ap = historyItem.wifi_ap;
            this.otgOnline = historyItem.otgOnline;
            this.highSpeakerVolume = historyItem.highSpeakerVolume;
            this.subScreenOn = historyItem.subScreenOn;
            this.subScreenDoze = historyItem.subScreenDoze;
            this.batterySecTxShareEvent = historyItem.batterySecTxShareEvent;
            this.batterySecOnline = historyItem.batterySecOnline;
            this.batterySecCurrentEvent = historyItem.batterySecCurrentEvent;
            this.batterySecEvent = historyItem.batterySecEvent;
            this.protectBatteryMode = historyItem.protectBatteryMode;
            this.batteryChargeUah = historyItem.batteryChargeUah;
            this.modemRailChargeMah = historyItem.modemRailChargeMah;
            this.wifiRailChargeMah = historyItem.wifiRailChargeMah;
            this.states = historyItem.states;
            this.states2 = historyItem.states2;
            if (historyItem.wakelockTag != null) {
                HistoryTag historyTag = this.localWakelockTag;
                this.wakelockTag = historyTag;
                historyTag.setTo(historyItem.wakelockTag);
            } else {
                this.wakelockTag = null;
            }
            if (historyItem.wakeReasonTag != null) {
                HistoryTag historyTag2 = this.localWakeReasonTag;
                this.wakeReasonTag = historyTag2;
                historyTag2.setTo(historyItem.wakeReasonTag);
            } else {
                this.wakeReasonTag = null;
            }
            this.eventCode = historyItem.eventCode;
            if (historyItem.eventTag != null) {
                HistoryTag historyTag3 = this.localEventTag;
                this.eventTag = historyTag3;
                historyTag3.setTo(historyItem.eventTag);
            } else {
                this.eventTag = null;
            }
            this.tagsFirstOccurrence = historyItem.tagsFirstOccurrence;
            this.currentTime = historyItem.currentTime;
            this.powerStats = historyItem.powerStats;
            this.processStateChange = historyItem.processStateChange;
            this.stepDetails = historyItem.stepDetails;
        }

        public boolean sameNonEvent(HistoryItem historyItem) {
            return this.batteryLevel == historyItem.batteryLevel && this.batteryStatus == historyItem.batteryStatus && this.batteryHealth == historyItem.batteryHealth && this.batteryPlugType == historyItem.batteryPlugType && this.batteryTemperature == historyItem.batteryTemperature && this.batteryVoltage == historyItem.batteryVoltage && this.current == historyItem.current && this.ap_temp == historyItem.ap_temp && this.pa_temp == historyItem.pa_temp && this.sub_batt_temp == historyItem.sub_batt_temp && this.skin_temp == historyItem.skin_temp && this.wifi_ap == historyItem.wifi_ap && this.otgOnline == historyItem.otgOnline && this.highSpeakerVolume == historyItem.highSpeakerVolume && this.subScreenOn == historyItem.subScreenOn && this.subScreenDoze == historyItem.subScreenDoze && this.batterySecTxShareEvent == historyItem.batterySecTxShareEvent && this.batterySecOnline == historyItem.batterySecOnline && this.batterySecCurrentEvent == historyItem.batterySecCurrentEvent && this.batterySecEvent == historyItem.batterySecEvent && this.protectBatteryMode == historyItem.protectBatteryMode && this.batteryChargeUah == historyItem.batteryChargeUah && this.modemRailChargeMah == historyItem.modemRailChargeMah && this.wifiRailChargeMah == historyItem.wifiRailChargeMah && this.states == historyItem.states && this.states2 == historyItem.states2 && this.currentTime == historyItem.currentTime;
        }

        public boolean same(HistoryItem historyItem) {
            if (!sameNonEvent(historyItem) || this.eventCode != historyItem.eventCode) {
                return false;
            }
            HistoryTag historyTag = this.wakelockTag;
            HistoryTag historyTag2 = historyItem.wakelockTag;
            if (historyTag != historyTag2 && (historyTag == null || historyTag2 == null || !historyTag.equals(historyTag2))) {
                return false;
            }
            HistoryTag historyTag3 = this.wakeReasonTag;
            HistoryTag historyTag4 = historyItem.wakeReasonTag;
            if (historyTag3 != historyTag4 && (historyTag3 == null || historyTag4 == null || !historyTag3.equals(historyTag4))) {
                return false;
            }
            HistoryTag historyTag5 = this.eventTag;
            HistoryTag historyTag6 = historyItem.eventTag;
            if (historyTag5 != historyTag6) {
                return (historyTag5 == null || historyTag6 == null || !historyTag5.equals(historyTag6)) ? false : true;
            }
            return true;
        }
    }

    public static final class HistoryEventTracker {
        private final HashMap<String, SparseIntArray>[] mActiveEvents = new HashMap[23];

        public boolean updateState(int i, String str, int i2, int i3) {
            SparseIntArray sparseIntArray;
            int indexOfKey;
            if ((32768 & i) == 0) {
                if ((i & 16384) == 0) {
                    return true;
                }
                HashMap<String, SparseIntArray> hashMap = this.mActiveEvents[i & HistoryItem.EVENT_TYPE_MASK];
                if (hashMap == null || (sparseIntArray = hashMap.get(str)) == null || (indexOfKey = sparseIntArray.indexOfKey(i2)) < 0) {
                    return false;
                }
                sparseIntArray.removeAt(indexOfKey);
                if (sparseIntArray.size() > 0) {
                    return true;
                }
                hashMap.remove(str);
                return true;
            }
            int i4 = i & HistoryItem.EVENT_TYPE_MASK;
            HashMap<String, SparseIntArray> hashMap2 = this.mActiveEvents[i4];
            if (hashMap2 == null) {
                hashMap2 = new HashMap<>();
                this.mActiveEvents[i4] = hashMap2;
            }
            SparseIntArray sparseIntArray2 = hashMap2.get(str);
            if (sparseIntArray2 == null) {
                sparseIntArray2 = new SparseIntArray();
                hashMap2.put(str, sparseIntArray2);
            }
            if (sparseIntArray2.indexOfKey(i2) >= 0) {
                return false;
            }
            sparseIntArray2.put(i2, i3);
            return true;
        }

        public void removeEvents(int i) {
            this.mActiveEvents[i & HistoryItem.EVENT_TYPE_MASK] = null;
        }

        public HashMap<String, SparseIntArray> getStateForEvent(int i) {
            return this.mActiveEvents[i];
        }
    }

    public static final class BitDescription {
        public final int mask;
        public final String name;
        public final int shift;
        public final String shortName;
        public final String[] shortValues;
        public final String[] values;

        public BitDescription(int i, String str, String str2) {
            this.mask = i;
            this.shift = -1;
            this.name = str;
            this.shortName = str2;
            this.values = null;
            this.shortValues = null;
        }

        public BitDescription(int i, int i2, String str, String str2, String[] strArr, String[] strArr2) {
            this.mask = i;
            this.shift = i2;
            this.name = str;
            this.shortName = str2;
            this.values = strArr;
            this.shortValues = strArr2;
        }
    }

    public static int getAllNetworkTypesCount() {
        int length = TelephonyManager.getAllNetworkTypes().length;
        String[] strArr = DATA_CONNECTION_NAMES;
        int i = length + 3;
        if (strArr.length == i) {
            return length;
        }
        throw new IllegalStateException("DATA_CONNECTION_NAMES length does not match network type count. Expected: " + i + ", actual:" + strArr.length);
    }

    public static int getAllNetworkTypesCount$ravenwood() {
        return DATA_CONNECTION_NAMES.length - 3;
    }

    public int getBatteryCapacity() {
        int learnedBatteryCapacity = getLearnedBatteryCapacity();
        if (learnedBatteryCapacity > 0) {
            return learnedBatteryCapacity / 1000;
        }
        int minLearnedBatteryCapacity = getMinLearnedBatteryCapacity();
        if (minLearnedBatteryCapacity > 0) {
            return minLearnedBatteryCapacity / 1000;
        }
        return getEstimatedBatteryCapacity();
    }

    private static final void formatTimeRaw(StringBuilder sb, long j) {
        long j2 = j / 86400;
        if (j2 != 0) {
            sb.append(j2);
            sb.append("d ");
        }
        long j3 = j2 * 86400;
        long j4 = (j - j3) / 3600;
        if (j4 != 0 || j3 != 0) {
            sb.append(j4);
            sb.append("h ");
        }
        long j5 = j3 + (j4 * 3600);
        long j6 = (j - j5) / 60;
        if (j6 != 0 || j5 != 0) {
            sb.append(j6);
            sb.append("m ");
        }
        long j7 = j5 + (j6 * 60);
        if (j == 0 && j7 == 0) {
            return;
        }
        sb.append(j - j7);
        sb.append("s ");
    }

    public static final void formatTimeMs(StringBuilder sb, long j) {
        long j2 = j / 1000;
        formatTimeRaw(sb, j2);
        sb.append(j - (j2 * 1000));
        sb.append("ms ");
    }

    public static final void formatTimeMsNoSpace(StringBuilder sb, long j) {
        long j2 = j / 1000;
        formatTimeRaw(sb, j2);
        sb.append(j - (j2 * 1000));
        sb.append("ms");
    }

    public final String formatRatioLocked(long j, long j2) {
        if (j2 == 0) {
            return "--%";
        }
        this.mFormatBuilder.setLength(0);
        this.mFormatter.format("%.1f%%", Float.valueOf((j / j2) * 100.0f));
        return this.mFormatBuilder.toString();
    }

    final String formatBytesLocked(long j) {
        this.mFormatBuilder.setLength(0);
        if (j < 1024) {
            return j + GnssSignalType.CODE_TYPE_B;
        }
        if (j < 1048576) {
            this.mFormatter.format("%.2fKB", Double.valueOf(j / 1024.0d));
            return this.mFormatBuilder.toString();
        }
        if (j < 1073741824) {
            this.mFormatter.format("%.2fMB", Double.valueOf(j / 1048576.0d));
            return this.mFormatBuilder.toString();
        }
        this.mFormatter.format("%.2fGB", Double.valueOf(j / 1.073741824E9d));
        return this.mFormatBuilder.toString();
    }

    public static String formatCharge(double d) {
        return formatValue(d);
    }

    private static String formatValue(double d) {
        String str;
        if (d == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            return "0";
        }
        if (d < 1.0E-5d) {
            str = "%.8f";
        } else if (d < 1.0E-4d) {
            str = "%.7f";
        } else if (d < 0.001d) {
            str = "%.6f";
        } else if (d < 0.01d) {
            str = "%.5f";
        } else if (d < 0.1d) {
            str = "%.4f";
        } else if (d < 1.0d) {
            str = "%.3f";
        } else if (d < 10.0d) {
            str = "%.2f";
        } else if (d < 100.0d) {
            str = "%.1f";
        } else {
            str = "%.0f";
        }
        return String.format(Locale.ENGLISH, str, Double.valueOf(d));
    }

    private static long roundUsToMs(long j) {
        return (j + 500) / 1000;
    }

    private static long computeWakeLock(Timer timer, long j, int i) {
        if (timer != null) {
            return (timer.getTotalTimeLocked(j, i) + 500) / 1000;
        }
        return 0L;
    }

    private static final String printWakeLock(StringBuilder sb, Timer timer, long j, String str, int i, String str2) {
        if (timer != null) {
            long computeWakeLock = computeWakeLock(timer, j, i);
            int countLocked = timer.getCountLocked(i);
            if (computeWakeLock != 0) {
                sb.append(str2);
                formatTimeMs(sb, computeWakeLock);
                if (str != null) {
                    sb.append(str);
                    sb.append(' ');
                }
                sb.append('(');
                sb.append(countLocked);
                sb.append(" times)");
                long j2 = j / 1000;
                long maxDurationMsLocked = timer.getMaxDurationMsLocked(j2);
                if (maxDurationMsLocked >= 0) {
                    sb.append(" max=");
                    sb.append(maxDurationMsLocked);
                }
                long totalDurationMsLocked = timer.getTotalDurationMsLocked(j2);
                if (totalDurationMsLocked > computeWakeLock) {
                    sb.append(" actual=");
                    sb.append(totalDurationMsLocked);
                }
                if (timer.isRunningLocked()) {
                    long currentDurationMsLocked = timer.getCurrentDurationMsLocked(j2);
                    if (currentDurationMsLocked >= 0) {
                        sb.append(" (running for ");
                        sb.append(currentDurationMsLocked);
                        sb.append("ms)");
                        return ", ";
                    }
                    sb.append(" (running)");
                    return ", ";
                }
                return ", ";
            }
        }
        return str2;
    }

    private static final boolean printTimer(PrintWriter printWriter, StringBuilder sb, Timer timer, long j, int i, String str, String str2) {
        if (timer != null) {
            long totalTimeLocked = (timer.getTotalTimeLocked(j, i) + 500) / 1000;
            int countLocked = timer.getCountLocked(i);
            if (totalTimeLocked != 0) {
                sb.setLength(0);
                sb.append(str);
                sb.append("    ");
                sb.append(str2);
                sb.append(": ");
                formatTimeMs(sb, totalTimeLocked);
                sb.append("realtime (");
                sb.append(countLocked);
                sb.append(" times)");
                long j2 = j / 1000;
                long maxDurationMsLocked = timer.getMaxDurationMsLocked(j2);
                if (maxDurationMsLocked >= 0) {
                    sb.append(" max=");
                    sb.append(maxDurationMsLocked);
                }
                if (timer.isRunningLocked()) {
                    long currentDurationMsLocked = timer.getCurrentDurationMsLocked(j2);
                    if (currentDurationMsLocked >= 0) {
                        sb.append(" (running for ");
                        sb.append(currentDurationMsLocked);
                        sb.append("ms)");
                    } else {
                        sb.append(" (running)");
                    }
                }
                printWriter.println(sb.toString());
                return true;
            }
        }
        return false;
    }

    private static final String printWakeLockCheckin(StringBuilder sb, Timer timer, long j, String str, int i, String str2) {
        long j2;
        int i2;
        long j3;
        long j4;
        long j5;
        String str3;
        if (timer != null) {
            j2 = timer.getTotalTimeLocked(j, i);
            i2 = timer.getCountLocked(i);
            long j6 = j / 1000;
            j4 = timer.getCurrentDurationMsLocked(j6);
            j5 = timer.getMaxDurationMsLocked(j6);
            j3 = timer.getTotalDurationMsLocked(j6);
        } else {
            j2 = 0;
            i2 = 0;
            j3 = 0;
            j4 = 0;
            j5 = 0;
        }
        sb.append(str2);
        sb.append((j2 + 500) / 1000);
        sb.append(',');
        if (str != null) {
            str3 = str + ",";
        } else {
            str3 = "";
        }
        sb.append(str3);
        sb.append(i2);
        sb.append(',');
        sb.append(j4);
        sb.append(',');
        sb.append(j5);
        if (str != null) {
            sb.append(',');
            sb.append(j3);
        }
        return ",";
    }

    private static final void dumpLineHeader(PrintWriter printWriter, int i, String str, String str2) {
        printWriter.print(9);
        printWriter.print(',');
        printWriter.print(i);
        printWriter.print(',');
        printWriter.print(str);
        printWriter.print(',');
        printWriter.print(str2);
    }

    private static final void dumpLine(PrintWriter printWriter, int i, String str, String str2, Object... objArr) {
        dumpLineHeader(printWriter, i, str, str2);
        for (Object obj : objArr) {
            printWriter.print(',');
            printWriter.print(obj);
        }
        printWriter.println();
    }

    private static final void dumpTimer(PrintWriter printWriter, int i, String str, String str2, Timer timer, long j, int i2) {
        if (timer != null) {
            long roundUsToMs = roundUsToMs(timer.getTotalTimeLocked(j, i2));
            int countLocked = timer.getCountLocked(i2);
            if (roundUsToMs == 0 && countLocked == 0) {
                return;
            }
            dumpLine(printWriter, i, str, str2, Long.valueOf(roundUsToMs), Integer.valueOf(countLocked));
        }
    }

    private static void dumpTimer(ProtoOutputStream protoOutputStream, long j, Timer timer, long j2, int i) {
        if (timer == null) {
            return;
        }
        long roundUsToMs = roundUsToMs(timer.getTotalTimeLocked(j2, i));
        int countLocked = timer.getCountLocked(i);
        long j3 = j2 / 1000;
        long maxDurationMsLocked = timer.getMaxDurationMsLocked(j3);
        long currentDurationMsLocked = timer.getCurrentDurationMsLocked(j3);
        long totalDurationMsLocked = timer.getTotalDurationMsLocked(j3);
        if (roundUsToMs == 0 && countLocked == 0 && maxDurationMsLocked == -1 && currentDurationMsLocked == -1 && totalDurationMsLocked == -1) {
            return;
        }
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1112396529665L, roundUsToMs);
        protoOutputStream.write(1112396529666L, countLocked);
        if (maxDurationMsLocked != -1) {
            protoOutputStream.write(1112396529667L, maxDurationMsLocked);
        }
        if (currentDurationMsLocked != -1) {
            protoOutputStream.write(1112396529668L, currentDurationMsLocked);
        }
        if (totalDurationMsLocked != -1) {
            protoOutputStream.write(1112396529669L, totalDurationMsLocked);
        }
        protoOutputStream.end(start);
    }

    private static boolean controllerActivityHasData(ControllerActivityCounter controllerActivityCounter, int i) {
        if (controllerActivityCounter == null) {
            return false;
        }
        if (controllerActivityCounter.getIdleTimeCounter().getCountLocked(i) != 0 || controllerActivityCounter.getRxTimeCounter().getCountLocked(i) != 0 || controllerActivityCounter.getPowerCounter().getCountLocked(i) != 0 || controllerActivityCounter.getMonitoredRailChargeConsumedMaMs().getCountLocked(i) != 0) {
            return true;
        }
        for (LongCounter longCounter : controllerActivityCounter.getTxTimeCounters()) {
            if (longCounter.getCountLocked(i) != 0) {
                return true;
            }
        }
        return false;
    }

    private static final void dumpControllerActivityLine(PrintWriter printWriter, int i, String str, String str2, ControllerActivityCounter controllerActivityCounter, int i2) {
        if (controllerActivityHasData(controllerActivityCounter, i2)) {
            dumpLineHeader(printWriter, i, str, str2);
            printWriter.print(",");
            printWriter.print(controllerActivityCounter.getIdleTimeCounter().getCountLocked(i2));
            printWriter.print(",");
            printWriter.print(controllerActivityCounter.getRxTimeCounter().getCountLocked(i2));
            printWriter.print(",");
            printWriter.print(controllerActivityCounter.getPowerCounter().getCountLocked(i2) / 3600000.0d);
            printWriter.print(",");
            printWriter.print(controllerActivityCounter.getMonitoredRailChargeConsumedMaMs().getCountLocked(i2) / 3600000.0d);
            LongCounter[] txTimeCounters = controllerActivityCounter.getTxTimeCounters();
            for (LongCounter longCounter : txTimeCounters) {
                printWriter.print(",");
                printWriter.print(longCounter.getCountLocked(i2));
            }
            printWriter.println();
        }
    }

    private static void dumpControllerActivityProto(ProtoOutputStream protoOutputStream, long j, ControllerActivityCounter controllerActivityCounter, int i) {
        if (controllerActivityHasData(controllerActivityCounter, i)) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1112396529665L, controllerActivityCounter.getIdleTimeCounter().getCountLocked(i));
            protoOutputStream.write(1112396529666L, controllerActivityCounter.getRxTimeCounter().getCountLocked(i));
            protoOutputStream.write(1112396529667L, controllerActivityCounter.getPowerCounter().getCountLocked(i) / 3600000.0d);
            protoOutputStream.write(1103806595077L, controllerActivityCounter.getMonitoredRailChargeConsumedMaMs().getCountLocked(i) / 3600000.0d);
            LongCounter[] txTimeCounters = controllerActivityCounter.getTxTimeCounters();
            for (int i2 = 0; i2 < txTimeCounters.length; i2++) {
                LongCounter longCounter = txTimeCounters[i2];
                long start2 = protoOutputStream.start(2246267895812L);
                protoOutputStream.write(1120986464257L, i2);
                protoOutputStream.write(1112396529666L, longCounter.getCountLocked(i));
                protoOutputStream.end(start2);
            }
            protoOutputStream.end(start);
        }
    }

    private final void printControllerActivityIfInteresting(PrintWriter printWriter, StringBuilder sb, String str, String str2, ControllerActivityCounter controllerActivityCounter, int i) {
        if (controllerActivityHasData(controllerActivityCounter, i)) {
            printControllerActivity(printWriter, sb, str, str2, controllerActivityCounter, i);
        }
    }

    private final void printControllerActivity(PrintWriter printWriter, StringBuilder sb, String str, String str2, ControllerActivityCounter controllerActivityCounter, int i) {
        long j;
        Object obj;
        String[] strArr;
        long countLocked = controllerActivityCounter.getIdleTimeCounter().getCountLocked(i);
        long countLocked2 = controllerActivityCounter.getRxTimeCounter().getCountLocked(i);
        long countLocked3 = controllerActivityCounter.getPowerCounter().getCountLocked(i);
        long countLocked4 = controllerActivityCounter.getMonitoredRailChargeConsumedMaMs().getCountLocked(i);
        long computeBatteryRealtime = computeBatteryRealtime(SystemClock.elapsedRealtime() * 1000, i) / 1000;
        LongCounter[] txTimeCounters = controllerActivityCounter.getTxTimeCounters();
        long j2 = 0;
        int i2 = 0;
        for (int length = txTimeCounters.length; i2 < length; length = length) {
            j2 += txTimeCounters[i2].getCountLocked(i);
            i2++;
        }
        if (str2.equals(WIFI_CONTROLLER_NAME)) {
            j = countLocked2;
            long countLocked5 = controllerActivityCounter.getScanTimeCounter().getCountLocked(i);
            sb.setLength(0);
            sb.append(str);
            sb.append("     ");
            sb.append(str2);
            sb.append(" Scan time:  ");
            formatTimeMs(sb, countLocked5);
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(formatRatioLocked(countLocked5, computeBatteryRealtime));
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
            printWriter.println(sb.toString());
            long j3 = computeBatteryRealtime - ((countLocked + j) + j2);
            sb.setLength(0);
            sb.append(str);
            sb.append("     ");
            sb.append(str2);
            sb.append(" Sleep time:  ");
            formatTimeMs(sb, j3);
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(formatRatioLocked(j3, computeBatteryRealtime));
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
            printWriter.println(sb.toString());
        } else {
            j = countLocked2;
        }
        if (!str2.equals("Cellular")) {
            obj = "Cellular";
        } else {
            long countLocked6 = controllerActivityCounter.getSleepTimeCounter().getCountLocked(i);
            obj = "Cellular";
            sb.setLength(0);
            sb.append(str);
            sb.append("     ");
            sb.append(str2);
            sb.append(" Sleep time:  ");
            formatTimeMs(sb, countLocked6);
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(formatRatioLocked(countLocked6, computeBatteryRealtime));
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
            printWriter.println(sb.toString());
        }
        sb.setLength(0);
        sb.append(str);
        sb.append("     ");
        sb.append(str2);
        sb.append(" Idle time:   ");
        formatTimeMs(sb, countLocked);
        sb.append(NavigationBarInflaterView.KEY_CODE_START);
        sb.append(formatRatioLocked(countLocked, computeBatteryRealtime));
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        printWriter.println(sb.toString());
        sb.setLength(0);
        sb.append(str);
        sb.append("     ");
        sb.append(str2);
        sb.append(" Rx time:     ");
        long j4 = j;
        formatTimeMs(sb, j4);
        sb.append(NavigationBarInflaterView.KEY_CODE_START);
        sb.append(formatRatioLocked(j4, computeBatteryRealtime));
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        printWriter.println(sb.toString());
        sb.setLength(0);
        sb.append(str);
        sb.append("     ");
        sb.append(str2);
        sb.append(" Tx time:     ");
        str2.hashCode();
        if (str2.equals(obj)) {
            strArr = new String[]{"   less than 0dBm: ", "   0dBm to 8dBm: ", "   8dBm to 15dBm: ", "   15dBm to 20dBm: ", "   above 20dBm: "};
        } else {
            strArr = new String[]{"[0]", "[1]", "[2]", "[3]", "[4]"};
        }
        int min = Math.min(controllerActivityCounter.getTxTimeCounters().length, strArr.length);
        if (min > 1) {
            printWriter.println(sb.toString());
            int i3 = 0;
            while (i3 < min) {
                String[] strArr2 = strArr;
                long countLocked7 = controllerActivityCounter.getTxTimeCounters()[i3].getCountLocked(i);
                sb.setLength(0);
                sb.append(str);
                sb.append("    ");
                sb.append(strArr2[i3]);
                sb.append(" ");
                formatTimeMs(sb, countLocked7);
                sb.append(NavigationBarInflaterView.KEY_CODE_START);
                sb.append(formatRatioLocked(countLocked7, computeBatteryRealtime));
                sb.append(NavigationBarInflaterView.KEY_CODE_END);
                printWriter.println(sb.toString());
                i3++;
                strArr = strArr2;
                min = min;
            }
        } else {
            long countLocked8 = controllerActivityCounter.getTxTimeCounters()[0].getCountLocked(i);
            formatTimeMs(sb, countLocked8);
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(formatRatioLocked(countLocked8, computeBatteryRealtime));
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
            printWriter.println(sb.toString());
        }
        if (countLocked3 > 0) {
            sb.setLength(0);
            sb.append(str);
            sb.append("     ");
            sb.append(str2);
            sb.append(" Battery drain: ");
            sb.append(formatCharge(countLocked3 / 3600000.0d));
            sb.append("mAh");
            printWriter.println(sb.toString());
        }
        if (countLocked4 > 0) {
            sb.setLength(0);
            sb.append(str);
            sb.append("     ");
            sb.append(str2);
            sb.append(" Monitored rail energy drain: ");
            sb.append(new DecimalFormat("#.##").format(countLocked4 / 3600000.0d));
            sb.append(" mAh");
            printWriter.println(sb.toString());
        }
    }

    private void printCellularPerRatBreakdown(PrintWriter printWriter, StringBuilder sb, String str, long j) {
        int i;
        String[] strArr = {"    Unknown frequency:\n", "    Low frequency (less than 1GHz):\n", "    Middle frequency (1GHz to 3GHz):\n", "    High frequency (3GHz to 6GHz):\n", "    Mmwave frequency (greater than 6GHz):\n"};
        String[] strArr2 = {"        unknown:  ", "        poor:     ", "        moderate: ", "        good:     ", "        great:    "};
        int i2 = 0;
        long mobileRadioActiveTime = getMobileRadioActiveTime(j * 1000, 0) / 1000;
        sb.setLength(0);
        sb.append(str);
        sb.append("Active Cellular Radio Access Technology Breakdown:");
        printWriter.println(sb);
        int numSignalStrengthLevels = CellSignalStrength.getNumSignalStrengthLevels();
        int i3 = 2;
        boolean z = false;
        int i4 = 2;
        while (i4 >= 0) {
            sb.setLength(i2);
            sb.append(str);
            sb.append("  ");
            sb.append(RADIO_ACCESS_TECHNOLOGY_NAMES[i4]);
            sb.append(":\n");
            sb.append(str);
            boolean z2 = z;
            int i5 = (i4 == i3 ? 5 : 1) - 1;
            while (i5 >= 0) {
                int length = sb.length();
                if (i4 == i3) {
                    sb.append(strArr[i5]);
                } else {
                    sb.append("    All frequencies:\n");
                }
                sb.append(str);
                sb.append("      Signal Strength Time:\n");
                int i6 = length;
                int i7 = i2;
                int i8 = i7;
                while (i7 < numSignalStrengthLevels) {
                    int i9 = i6;
                    String[] strArr3 = strArr;
                    String[] strArr4 = strArr2;
                    long activeRadioDurationMs = getActiveRadioDurationMs(i4, i5, i7, j);
                    if (activeRadioDurationMs > 0) {
                        sb.append(str);
                        sb.append(strArr4[i7]);
                        formatTimeMs(sb, activeRadioDurationMs);
                        sb.append(NavigationBarInflaterView.KEY_CODE_START);
                        sb.append(formatRatioLocked(activeRadioDurationMs, mobileRadioActiveTime));
                        sb.append(")\n");
                        i8 = 1;
                    }
                    i7++;
                    i6 = i9;
                    strArr = strArr3;
                    strArr2 = strArr4;
                }
                int i10 = i6;
                String[] strArr5 = strArr;
                String[] strArr6 = strArr2;
                sb.append(str);
                sb.append("      Tx Time:\n");
                int i11 = 0;
                while (i11 < numSignalStrengthLevels) {
                    long activeTxRadioDurationMs = getActiveTxRadioDurationMs(i4, i5, i11, j);
                    if (activeTxRadioDurationMs <= 0) {
                        i = i11;
                    } else {
                        sb.append(str);
                        i = i11;
                        sb.append(strArr6[i]);
                        formatTimeMs(sb, activeTxRadioDurationMs);
                        sb.append(NavigationBarInflaterView.KEY_CODE_START);
                        sb.append(formatRatioLocked(activeTxRadioDurationMs, mobileRadioActiveTime));
                        sb.append(")\n");
                        i8 = 1;
                    }
                    i11 = i + 1;
                }
                sb.append(str);
                sb.append("      Rx Time: ");
                long activeRxRadioDurationMs = getActiveRxRadioDurationMs(i4, i5, j);
                formatTimeMs(sb, activeRxRadioDurationMs);
                sb.append(NavigationBarInflaterView.KEY_CODE_START);
                sb.append(formatRatioLocked(activeRxRadioDurationMs, mobileRadioActiveTime));
                sb.append(")\n");
                if (i8 != 0) {
                    printWriter.print(sb);
                    sb.setLength(0);
                    sb.append(str);
                    z2 = true;
                } else {
                    sb.setLength(i10);
                }
                i5--;
                strArr = strArr5;
                strArr2 = strArr6;
                i2 = 0;
                i3 = 2;
            }
            i4--;
            z = z2;
            i2 = 0;
            i3 = 2;
        }
        if (z) {
            return;
        }
        sb.setLength(0);
        sb.append(str);
        sb.append("  (no activity)");
        printWriter.println(sb);
    }

    /* JADX WARN: Removed duplicated region for block: B:227:0x09c5  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x09ef  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x0a21  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x0aed  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x0b1b  */
    /* JADX WARN: Removed duplicated region for block: B:293:0x0bac  */
    /* JADX WARN: Removed duplicated region for block: B:310:0x0c26  */
    /* JADX WARN: Removed duplicated region for block: B:325:0x0c79  */
    /* JADX WARN: Removed duplicated region for block: B:328:0x0cd2  */
    /* JADX WARN: Removed duplicated region for block: B:348:0x0d6e A[LOOP:28: B:347:0x0d6c->B:348:0x0d6e, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:352:0x0d86  */
    /* JADX WARN: Removed duplicated region for block: B:359:0x0db9  */
    /* JADX WARN: Removed duplicated region for block: B:411:0x0e9f  */
    /* JADX WARN: Removed duplicated region for block: B:428:0x0f29  */
    /* JADX WARN: Removed duplicated region for block: B:452:0x0e8d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dumpCheckinLocked(android.content.Context r115, java.io.PrintWriter r116, int r117, int r118, boolean r119, android.os.BatteryStats.BatteryStatsDumpHelper r120) {
        /*
            Method dump skipped, instructions count: 4082
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.BatteryStats.dumpCheckinLocked(android.content.Context, java.io.PrintWriter, int, int, boolean, android.os.BatteryStats$BatteryStatsDumpHelper):void");
    }

    static final class TimerEntry {
        final int mId;
        final String mName;
        final long mTime;
        final Timer mTimer;

        TimerEntry(String str, int i, Timer timer, long j) {
            this.mName = str;
            this.mId = i;
            this.mTimer = timer;
            this.mTime = j;
        }
    }

    private void printmAh(PrintWriter printWriter, double d) {
        printWriter.print(formatCharge(d));
    }

    private void printmAh(StringBuilder sb, double d) {
        sb.append(formatCharge(d));
    }

    /* JADX WARN: Removed duplicated region for block: B:348:0x12f8  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x136f  */
    /* JADX WARN: Removed duplicated region for block: B:549:0x1bb2  */
    /* JADX WARN: Removed duplicated region for block: B:568:0x1c1b  */
    /* JADX WARN: Removed duplicated region for block: B:578:0x1cfd  */
    /* JADX WARN: Removed duplicated region for block: B:596:0x1dcc  */
    /* JADX WARN: Removed duplicated region for block: B:601:0x1e0d  */
    /* JADX WARN: Removed duplicated region for block: B:622:0x1eb5  */
    /* JADX WARN: Removed duplicated region for block: B:643:0x1f50  */
    /* JADX WARN: Removed duplicated region for block: B:658:0x1fbc  */
    /* JADX WARN: Removed duplicated region for block: B:661:0x201a  */
    /* JADX WARN: Removed duplicated region for block: B:697:0x212f  */
    /* JADX WARN: Removed duplicated region for block: B:714:0x2181  */
    /* JADX WARN: Removed duplicated region for block: B:738:0x2212  */
    /* JADX WARN: Removed duplicated region for block: B:745:0x2265  */
    /* JADX WARN: Removed duplicated region for block: B:752:0x22b4  */
    /* JADX WARN: Removed duplicated region for block: B:764:0x22f3  */
    /* JADX WARN: Removed duplicated region for block: B:794:0x23a4  */
    /* JADX WARN: Removed duplicated region for block: B:849:0x250a  */
    /* JADX WARN: Removed duplicated region for block: B:873:0x2602  */
    /* JADX WARN: Removed duplicated region for block: B:875:0x260a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:881:0x22a6  */
    /* JADX WARN: Removed duplicated region for block: B:883:0x2179  */
    /* JADX WARN: Removed duplicated region for block: B:915:0x1dc3  */
    /* JADX WARN: Removed duplicated region for block: B:916:0x1bf7  */
    /* JADX WARN: Removed duplicated region for block: B:957:0x1360  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dumpLocked(android.content.Context r88, java.io.PrintWriter r89, java.lang.String r90, int r91, int r92, boolean r93, android.os.BatteryStats.BatteryStatsDumpHelper r94) {
        /*
            Method dump skipped, instructions count: 9770
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.BatteryStats.dumpLocked(android.content.Context, java.io.PrintWriter, java.lang.String, int, int, boolean, android.os.BatteryStats$BatteryStatsDumpHelper):void");
    }

    static void printBitDescriptions(StringBuilder sb, int i, int i2, HistoryTag historyTag, BitDescription[] bitDescriptionArr, boolean z) {
        int i3 = i ^ i2;
        if (i3 == 0) {
            return;
        }
        boolean z2 = false;
        for (BitDescription bitDescription : bitDescriptionArr) {
            if ((bitDescription.mask & i3) != 0) {
                sb.append(z ? " " : ",");
                if (bitDescription.shift < 0) {
                    sb.append((bitDescription.mask & i2) != 0 ? "+" : NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                    sb.append(z ? bitDescription.name : bitDescription.shortName);
                    if (bitDescription.mask == 1073741824 && historyTag != null) {
                        sb.append("=");
                        if (z || historyTag.poolIdx == -1) {
                            UserHandle.formatUid(sb, historyTag.uid);
                            sb.append(":\"");
                            if (historyTag.string != null) {
                                sb.append(historyTag.string.replace("\"", "\"\""));
                            }
                            sb.append("\"");
                        } else {
                            sb.append(historyTag.poolIdx);
                        }
                        z2 = true;
                    }
                } else {
                    sb.append(z ? bitDescription.name : bitDescription.shortName);
                    sb.append("=");
                    int i4 = (bitDescription.mask & i2) >> bitDescription.shift;
                    if (bitDescription.values != null && i4 >= 0 && i4 < bitDescription.values.length) {
                        sb.append(z ? bitDescription.values[i4] : bitDescription.shortValues[i4]);
                    } else {
                        sb.append(i4);
                    }
                }
            }
        }
        if (z2 || historyTag == null) {
            return;
        }
        sb.append(z ? " wake_lock=" : ",w=");
        if (z || historyTag.poolIdx == -1) {
            UserHandle.formatUid(sb, historyTag.uid);
            sb.append(":\"");
            sb.append(historyTag.string);
            sb.append("\"");
            return;
        }
        sb.append(historyTag.poolIdx);
    }

    public static class HistoryPrinter {
        private static final int FORMAT_LEGACY = 1;
        private static final int FORMAT_VERSION = 2;
        long lastTime;
        private final SimpleDateFormat mCurrentTimeEventTimeFormat;
        private final Date mDate;
        private final int mFormatVersion;
        private final SimpleDateFormat mHistoryItemTimestampFormat;
        private final HistoryLogTimeFormatter mHistoryLogTimeFormatter;
        private final boolean mPerformanceBaseline;
        private final StringBuilder mStringBuilder;
        int oldAp_temp;
        int oldChargeMAh;
        int oldCurrent;
        int oldHealth;
        int oldHighSpeakerVolume;
        int oldLevel;
        double oldModemRailChargeMah;
        int oldOtgOnline;
        int oldPa_temp;
        int oldPlug;
        int oldProtectBatteryMode;
        int oldSecCurrentEvent;
        int oldSecEvent;
        int oldSecOnline;
        int oldSecTxShareEvent;
        int oldSkin_temp;
        int oldState;
        int oldState2;
        int oldStatus;
        int oldSubScreenDoze;
        int oldSubScreenOn;
        int oldSub_batt_temp;
        int oldTemp;
        int oldVolt;
        double oldWifiRailChargeMah;
        int oldWifi_ap;

        public HistoryPrinter() {
            this(0);
        }

        public HistoryPrinter(int i) {
            this(TimeZone.getDefault(), i);
        }

        public HistoryPrinter(TimeZone timeZone, int i) {
            this(Flags.extendedBatteryHistoryContinuousCollectionEnabled() ? 2 : 1, timeZone, i);
        }

        private HistoryPrinter(int i, TimeZone timeZone, int i2) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US);
            this.mCurrentTimeEventTimeFormat = simpleDateFormat;
            this.mDate = new Date();
            this.mStringBuilder = new StringBuilder();
            this.oldState = 0;
            this.oldState2 = 0;
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
            this.lastTime = -1L;
            this.mFormatVersion = i;
            boolean z = (i2 & 128) != 0;
            this.mPerformanceBaseline = z;
            if (z) {
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US);
                this.mHistoryItemTimestampFormat = simpleDateFormat2;
                simpleDateFormat2.getCalendar().setTimeZone(timeZone);
                this.mHistoryLogTimeFormatter = null;
            } else {
                this.mHistoryItemTimestampFormat = null;
                this.mHistoryLogTimeFormatter = new HistoryLogTimeFormatter(timeZone);
            }
            simpleDateFormat.getCalendar().setTimeZone(timeZone);
        }

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

        public void printNextItem(PrintWriter printWriter, HistoryItem historyItem, long j, boolean z, boolean z2) {
            printWriter.print(printNextItem(historyItem, j, z, z2));
        }

        public void printNextItem(ProtoOutputStream protoOutputStream, HistoryItem historyItem, long j, boolean z) {
            String[] split = printNextItem(historyItem, j, true, z).split(ShaderAssembler.NEWLINE);
            for (String str : split) {
                protoOutputStream.write(2237677961222L, str);
            }
        }

        private String printNextItem(HistoryItem historyItem, long j, boolean z, boolean z2) {
            String[] strArr;
            boolean z3;
            boolean z4;
            StringBuilder sb = this.mStringBuilder;
            sb.setLength(0);
            boolean z5 = true;
            if (!z) {
                sb.append("  ");
                if (this.mFormatVersion == 1) {
                    TimeUtils.formatDuration(historyItem.time - j, sb, 19);
                    sb.append(" (");
                    sb.append(historyItem.numReadInts);
                    sb.append(") ");
                } else if (this.mPerformanceBaseline) {
                    this.mDate.setTime(historyItem.currentTime);
                    sb.append(this.mHistoryItemTimestampFormat.format(this.mDate));
                    sb.append(' ');
                } else {
                    this.mHistoryLogTimeFormatter.append(sb, historyItem.currentTime);
                    sb.append(' ');
                }
            } else {
                sb.append(9);
                sb.append(',');
                sb.append(BatteryStats.HISTORY_DATA);
                sb.append(',');
                if (this.lastTime < 0) {
                    sb.append(historyItem.time - j);
                } else {
                    sb.append(historyItem.time - this.lastTime);
                }
                this.lastTime = historyItem.time;
            }
            if (historyItem.cmd == 4) {
                if (z) {
                    sb.append(":");
                }
                sb.append("START\n");
                reset();
            } else {
                if (historyItem.cmd == 5 || historyItem.cmd == 7) {
                    if (z) {
                        sb.append(":");
                    }
                    if (historyItem.cmd == 7) {
                        sb.append("RESET:");
                        reset();
                    }
                    sb.append("TIME:");
                    if (z) {
                        sb.append(historyItem.currentTime);
                        sb.append(ShaderAssembler.NEWLINE);
                    } else {
                        sb.append(" ");
                        this.mDate.setTime(historyItem.currentTime);
                        sb.append(this.mCurrentTimeEventTimeFormat.format(this.mDate));
                        sb.append(ShaderAssembler.NEWLINE);
                    }
                } else if (historyItem.cmd == 8) {
                    if (z) {
                        sb.append(":");
                    }
                    sb.append("SHUTDOWN\n");
                } else if (historyItem.cmd == 6) {
                    if (z) {
                        sb.append(":");
                    }
                    sb.append("*OVERFLOW*\n");
                } else {
                    if (!z) {
                        if (historyItem.batteryLevel < 10) {
                            sb.append("00");
                        } else if (historyItem.batteryLevel < 100) {
                            sb.append("0");
                        }
                        sb.append(historyItem.batteryLevel);
                        if (z2) {
                            sb.append(" ");
                            if (historyItem.states >= 0) {
                                if (historyItem.states < 16) {
                                    sb.append("0000000");
                                } else if (historyItem.states < 256) {
                                    sb.append("000000");
                                } else if (historyItem.states < 4096) {
                                    sb.append("00000");
                                } else if (historyItem.states < 65536) {
                                    sb.append("0000");
                                } else if (historyItem.states < 1048576) {
                                    sb.append("000");
                                } else if (historyItem.states < 16777216) {
                                    sb.append("00");
                                } else if (historyItem.states < 268435456) {
                                    sb.append("0");
                                }
                            }
                            sb.append(Integer.toHexString(historyItem.states));
                        }
                    } else if (this.oldLevel != historyItem.batteryLevel) {
                        this.oldLevel = historyItem.batteryLevel;
                        sb.append(",Bl=");
                        sb.append(historyItem.batteryLevel);
                    }
                    if (this.oldStatus != historyItem.batteryStatus) {
                        this.oldStatus = historyItem.batteryStatus;
                        sb.append(z ? ",Bs=" : " status=");
                        int i = this.oldStatus;
                        if (i == 1) {
                            sb.append(z ? "?" : "unknown");
                        } else if (i == 2) {
                            sb.append(z ? "c" : UsbManager.USB_FUNCTION_CHARGING);
                        } else if (i == 3) {
                            sb.append(z ? XmlTags.ATTR_DESCRIPTION : "discharging");
                        } else if (i == 4) {
                            sb.append(z ? "n" : "not-charging");
                        } else if (i == 5) {
                            sb.append(z ? FullBackup.FILES_TREE_TOKEN : "full");
                        } else {
                            sb.append(i);
                        }
                    }
                    if (this.oldHealth != historyItem.batteryHealth) {
                        this.oldHealth = historyItem.batteryHealth;
                        sb.append(z ? ",Bh=" : " health=");
                        int i2 = this.oldHealth;
                        switch (i2) {
                            case 1:
                                sb.append(z ? "?" : "unknown");
                                break;
                            case 2:
                                sb.append(z ? "g" : "good");
                                break;
                            case 3:
                                sb.append(z ? BatteryStats.HISTORY_DATA : "overheat");
                                break;
                            case 4:
                                sb.append(z ? XmlTags.ATTR_DESCRIPTION : "dead");
                                break;
                            case 5:
                                sb.append(z ? "v" : "over-voltage");
                                break;
                            case 6:
                                sb.append(z ? FullBackup.FILES_TREE_TOKEN : "failure");
                                break;
                            case 7:
                                sb.append(z ? "c" : "cold");
                                break;
                            case 8:
                                sb.append(z ? XmlTags.TAG_LEASEE : "over-limit");
                                break;
                            case 9:
                                sb.append(z ? XmlTags.ATTR_UID : "under-voltage");
                                break;
                            default:
                                sb.append(i2);
                                break;
                        }
                    }
                    if (this.oldPlug != historyItem.batteryPlugType) {
                        this.oldPlug = historyItem.batteryPlugType;
                        sb.append(z ? ",Bp=" : " plug=");
                        int i3 = this.oldPlug;
                        if (i3 == 0) {
                            sb.append(z ? "n" : "none");
                        } else if (i3 == 1) {
                            sb.append(z ? FullBackup.APK_TREE_TOKEN : "ac");
                        } else if (i3 == 2) {
                            sb.append(z ? XmlTags.ATTR_UID : "usb");
                        } else if (i3 == 4) {
                            sb.append(z ? "w" : AudioDeviceDescription.CONNECTION_WIRELESS);
                        } else {
                            sb.append(i3);
                        }
                    }
                    if (this.oldTemp != historyItem.batteryTemperature) {
                        this.oldTemp = historyItem.batteryTemperature;
                        sb.append(z ? ",Bt=" : " temp=");
                        sb.append(this.oldTemp);
                    }
                    if (this.oldVolt != historyItem.batteryVoltage) {
                        this.oldVolt = historyItem.batteryVoltage;
                        sb.append(z ? ",Bv=" : " volt=");
                        sb.append(this.oldVolt);
                    }
                    if (!z) {
                        boolean z6 = historyItem.ap_temp != Byte.MIN_VALUE;
                        boolean z7 = historyItem.pa_temp != Byte.MIN_VALUE;
                        boolean z8 = historyItem.skin_temp != Byte.MIN_VALUE;
                        boolean z9 = historyItem.sub_batt_temp != Byte.MIN_VALUE;
                        if (this.oldCurrent != historyItem.current) {
                            this.oldCurrent = historyItem.current;
                            z4 = true;
                        } else {
                            z4 = false;
                        }
                        if (this.oldAp_temp != historyItem.ap_temp) {
                            this.oldAp_temp = historyItem.ap_temp;
                            z4 = true;
                        }
                        if (this.oldPa_temp != historyItem.pa_temp) {
                            this.oldPa_temp = historyItem.pa_temp;
                            z4 = true;
                        }
                        if (this.oldSkin_temp != historyItem.skin_temp) {
                            this.oldSkin_temp = historyItem.skin_temp;
                            z4 = true;
                        }
                        if (this.oldSub_batt_temp != historyItem.sub_batt_temp) {
                            this.oldSub_batt_temp = historyItem.sub_batt_temp;
                            z4 = true;
                        }
                        if (z4) {
                            sb.append(" current=");
                            sb.append(this.oldCurrent);
                            if (z6) {
                                sb.append(" ap_temp=");
                                sb.append(this.oldAp_temp);
                            }
                            if (z7) {
                                sb.append(" pa_temp=");
                                sb.append(this.oldPa_temp);
                            }
                            if (z8) {
                                sb.append(" skin_temp=");
                                sb.append(this.oldSkin_temp);
                            }
                            if (z9) {
                                sb.append(" sub_batt_temp=");
                                sb.append(this.oldSub_batt_temp);
                            }
                        }
                    }
                    if (!z && this.oldWifi_ap != historyItem.wifi_ap) {
                        byte b = historyItem.wifi_ap;
                        this.oldWifi_ap = b;
                        if (b == 1) {
                            sb.append(" +");
                        } else {
                            sb.append(" -");
                        }
                        sb.append("wifi_ap");
                    }
                    if (!z && this.oldOtgOnline != historyItem.otgOnline) {
                        byte b2 = historyItem.otgOnline;
                        this.oldOtgOnline = b2;
                        if (b2 == 1) {
                            sb.append(" +");
                        } else {
                            sb.append(" -");
                        }
                        sb.append("otg");
                    }
                    if (!z && this.oldHighSpeakerVolume != historyItem.highSpeakerVolume) {
                        byte b3 = historyItem.highSpeakerVolume;
                        this.oldHighSpeakerVolume = b3;
                        if (b3 == 1) {
                            sb.append(" +");
                        } else {
                            sb.append(" -");
                        }
                        sb.append("high_speaker_volume");
                    }
                    if (!z && this.oldSubScreenOn != historyItem.subScreenOn) {
                        byte b4 = historyItem.subScreenOn;
                        this.oldSubScreenOn = b4;
                        if (b4 == 1) {
                            sb.append(" +");
                        } else {
                            sb.append(" -");
                        }
                        sb.append("sub_screen");
                    }
                    if (!z && this.oldSubScreenDoze != historyItem.subScreenDoze) {
                        byte b5 = historyItem.subScreenDoze;
                        this.oldSubScreenDoze = b5;
                        if (b5 == 1) {
                            sb.append(" +");
                        } else {
                            sb.append(" -");
                        }
                        sb.append("sub_screen_doze");
                    }
                    if (!z) {
                        if (this.oldSecTxShareEvent != historyItem.batterySecTxShareEvent) {
                            this.oldSecTxShareEvent = historyItem.batterySecTxShareEvent;
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        if (this.oldSecOnline != historyItem.batterySecOnline) {
                            this.oldSecOnline = historyItem.batterySecOnline;
                            z3 = true;
                        }
                        if (this.oldSecCurrentEvent != historyItem.batterySecCurrentEvent) {
                            this.oldSecCurrentEvent = historyItem.batterySecCurrentEvent;
                            z3 = true;
                        }
                        if (this.oldSecEvent != historyItem.batterySecEvent) {
                            this.oldSecEvent = historyItem.batterySecEvent;
                        } else {
                            z5 = z3;
                        }
                        if (z5) {
                            sb.append(" txshare_event=");
                            sb.append(String.format("0x%x", Integer.valueOf(this.oldSecTxShareEvent)));
                            sb.append(" online=");
                            sb.append(this.oldSecOnline);
                            sb.append(" current_event=");
                            sb.append(String.format("0x%x", Integer.valueOf(this.oldSecCurrentEvent)));
                            sb.append(" misc_event=");
                            sb.append(String.format("0x%x", Integer.valueOf(this.oldSecEvent)));
                        }
                    }
                    if (!z && this.oldProtectBatteryMode != historyItem.protectBatteryMode) {
                        int i4 = historyItem.protectBatteryMode;
                        this.oldProtectBatteryMode = i4;
                        if (i4 >= 0 && i4 < BatteryStats.PROTECT_BATTERY_MODE_TYPES.length) {
                            sb.append(" pbm=");
                            sb.append(BatteryStats.PROTECT_BATTERY_MODE_TYPES[this.oldProtectBatteryMode]);
                        }
                    }
                    int i5 = historyItem.batteryChargeUah / 1000;
                    if (this.oldChargeMAh != i5) {
                        this.oldChargeMAh = i5;
                        sb.append(z ? ",Bcc=" : " charge=");
                        sb.append(this.oldChargeMAh);
                    }
                    if (this.oldModemRailChargeMah != historyItem.modemRailChargeMah) {
                        this.oldModemRailChargeMah = historyItem.modemRailChargeMah;
                        sb.append(z ? ",Mrc=" : " modemRailChargemAh=");
                        sb.append(new DecimalFormat("#.##").format(this.oldModemRailChargeMah));
                    }
                    if (this.oldWifiRailChargeMah != historyItem.wifiRailChargeMah) {
                        this.oldWifiRailChargeMah = historyItem.wifiRailChargeMah;
                        sb.append(z ? ",Wrc=" : " wifiRailChargemAh=");
                        sb.append(new DecimalFormat("#.##").format(this.oldWifiRailChargeMah));
                    }
                    BatteryStats.printBitDescriptions(sb, this.oldState, historyItem.states, historyItem.wakelockTag, BatteryStats.HISTORY_STATE_DESCRIPTIONS, !z);
                    BatteryStats.printBitDescriptions(sb, this.oldState2, historyItem.states2, null, BatteryStats.HISTORY_STATE2_DESCRIPTIONS, !z);
                    if (historyItem.wakeReasonTag != null) {
                        if (z) {
                            sb.append(",wr=");
                            if (historyItem.wakeReasonTag.poolIdx == -1) {
                                sb.append(BatteryStats.sUidToString.applyAsString(historyItem.wakeReasonTag.uid));
                                sb.append(":\"");
                                sb.append(historyItem.wakeReasonTag.string.replace("\"", "\"\""));
                                sb.append("\"");
                            } else {
                                sb.append(historyItem.wakeReasonTag.poolIdx);
                            }
                        } else {
                            sb.append(" wake_reason=");
                            sb.append(historyItem.wakeReasonTag.uid);
                            sb.append(":\"");
                            sb.append(historyItem.wakeReasonTag.string);
                            sb.append("\"");
                        }
                    }
                    if (historyItem.eventCode != 0) {
                        sb.append(z ? "," : " ");
                        if ((historyItem.eventCode & 32768) != 0) {
                            sb.append("+");
                        } else if ((historyItem.eventCode & 16384) != 0) {
                            sb.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                        }
                        if (z) {
                            strArr = BatteryStats.HISTORY_EVENT_CHECKIN_NAMES;
                        } else {
                            strArr = BatteryStats.HISTORY_EVENT_NAMES;
                        }
                        int i6 = historyItem.eventCode & HistoryItem.EVENT_TYPE_MASK;
                        if (i6 >= 0 && i6 < strArr.length) {
                            sb.append(strArr[i6]);
                        } else {
                            sb.append(z ? "Ev" : "event");
                            sb.append(i6);
                        }
                        sb.append("=");
                        if (z) {
                            if (historyItem.eventTag.poolIdx == -1) {
                                sb.append(BatteryStats.HISTORY_EVENT_INT_FORMATTERS[i6].applyAsString(historyItem.eventTag.uid));
                                sb.append(":\"");
                                sb.append(historyItem.eventTag.string.replace("\"", "\"\""));
                                sb.append("\"");
                            } else {
                                sb.append(historyItem.eventTag.poolIdx);
                            }
                        } else {
                            sb.append(BatteryStats.HISTORY_EVENT_INT_FORMATTERS[i6].applyAsString(historyItem.eventTag.uid));
                            sb.append(":\"");
                            sb.append(historyItem.eventTag.string);
                            sb.append("\"");
                        }
                    }
                    if (historyItem.powerStats != null && z2 && !z) {
                        sb.append("\n                 Stats: ");
                        sb.append(historyItem.powerStats.formatForBatteryHistory("\n                    "));
                    }
                    if (historyItem.processStateChange != null && z2 && !z) {
                        sb.append(" procstate: ");
                        sb.append(historyItem.processStateChange.formatForBatteryHistory());
                    }
                    sb.append(ShaderAssembler.NEWLINE);
                    if (historyItem.stepDetails != null) {
                        if (!z) {
                            sb.append("                 Details: cpu=");
                            sb.append(historyItem.stepDetails.userTime);
                            sb.append("u+");
                            sb.append(historyItem.stepDetails.systemTime);
                            sb.append(XmlTags.TAG_SESSION);
                            if (historyItem.stepDetails.appCpuUid1 >= 0) {
                                sb.append(" (");
                                printStepCpuUidDetails(sb, historyItem.stepDetails.appCpuUid1, historyItem.stepDetails.appCpuUTime1, historyItem.stepDetails.appCpuSTime1);
                                if (historyItem.stepDetails.appCpuUid2 >= 0) {
                                    sb.append(", ");
                                    printStepCpuUidDetails(sb, historyItem.stepDetails.appCpuUid2, historyItem.stepDetails.appCpuUTime2, historyItem.stepDetails.appCpuSTime2);
                                }
                                if (historyItem.stepDetails.appCpuUid3 >= 0) {
                                    sb.append(", ");
                                    printStepCpuUidDetails(sb, historyItem.stepDetails.appCpuUid3, historyItem.stepDetails.appCpuUTime3, historyItem.stepDetails.appCpuSTime3);
                                }
                                sb.append(')');
                            }
                            sb.append(ShaderAssembler.NEWLINE);
                            sb.append("                          /proc/stat=");
                            sb.append(historyItem.stepDetails.statUserTime);
                            sb.append(" usr, ");
                            sb.append(historyItem.stepDetails.statSystemTime);
                            sb.append(" sys, ");
                            sb.append(historyItem.stepDetails.statIOWaitTime);
                            sb.append(" io, ");
                            sb.append(historyItem.stepDetails.statIrqTime);
                            sb.append(" irq, ");
                            sb.append(historyItem.stepDetails.statSoftIrqTime);
                            sb.append(" sirq, ");
                            sb.append(historyItem.stepDetails.statIdlTime);
                            sb.append(" idle");
                            int i7 = historyItem.stepDetails.statUserTime + historyItem.stepDetails.statSystemTime + historyItem.stepDetails.statIOWaitTime + historyItem.stepDetails.statIrqTime + historyItem.stepDetails.statSoftIrqTime;
                            int i8 = historyItem.stepDetails.statIdlTime + i7;
                            if (i8 > 0) {
                                sb.append(" (");
                                sb.append(String.format("%.1f%%", Float.valueOf((i7 / i8) * 100.0f)));
                                sb.append(" of ");
                                StringBuilder sb2 = new StringBuilder(64);
                                BatteryStats.formatTimeMsNoSpace(sb2, i8 * 10);
                                sb.append((CharSequence) sb2);
                                sb.append(NavigationBarInflaterView.KEY_CODE_END);
                            }
                            sb.append(", SubsystemPowerState ");
                            sb.append(historyItem.stepDetails.statSubsystemPowerState != null ? historyItem.stepDetails.statSubsystemPowerState : "Empty");
                            sb.append(ShaderAssembler.NEWLINE);
                        } else {
                            sb.append(9);
                            sb.append(',');
                            sb.append(BatteryStats.HISTORY_DATA);
                            sb.append(",0,Dcpu=");
                            sb.append(historyItem.stepDetails.userTime);
                            sb.append(":");
                            sb.append(historyItem.stepDetails.systemTime);
                            if (historyItem.stepDetails.appCpuUid1 >= 0) {
                                printStepCpuUidCheckinDetails(sb, historyItem.stepDetails.appCpuUid1, historyItem.stepDetails.appCpuUTime1, historyItem.stepDetails.appCpuSTime1);
                                if (historyItem.stepDetails.appCpuUid2 >= 0) {
                                    printStepCpuUidCheckinDetails(sb, historyItem.stepDetails.appCpuUid2, historyItem.stepDetails.appCpuUTime2, historyItem.stepDetails.appCpuSTime2);
                                }
                                if (historyItem.stepDetails.appCpuUid3 >= 0) {
                                    printStepCpuUidCheckinDetails(sb, historyItem.stepDetails.appCpuUid3, historyItem.stepDetails.appCpuUTime3, historyItem.stepDetails.appCpuSTime3);
                                }
                            }
                            sb.append(ShaderAssembler.NEWLINE);
                            sb.append(9);
                            sb.append(',');
                            sb.append(BatteryStats.HISTORY_DATA);
                            sb.append(",0,Dpst=");
                            sb.append(historyItem.stepDetails.statUserTime);
                            sb.append(',');
                            sb.append(historyItem.stepDetails.statSystemTime);
                            sb.append(',');
                            sb.append(historyItem.stepDetails.statIOWaitTime);
                            sb.append(',');
                            sb.append(historyItem.stepDetails.statIrqTime);
                            sb.append(',');
                            sb.append(historyItem.stepDetails.statSoftIrqTime);
                            sb.append(',');
                            sb.append(historyItem.stepDetails.statIdlTime);
                            sb.append(',');
                            if (historyItem.stepDetails.statSubsystemPowerState != null) {
                                sb.append(historyItem.stepDetails.statSubsystemPowerState);
                            }
                            sb.append(ShaderAssembler.NEWLINE);
                        }
                    }
                    this.oldState = historyItem.states;
                    this.oldState2 = historyItem.states2;
                    if ((historyItem.states2 & 524288) != 0) {
                        historyItem.states2 &= -524289;
                    }
                }
            }
            return sb.toString();
        }

        private void printStepCpuUidDetails(StringBuilder sb, int i, int i2, int i3) {
            UserHandle.formatUid(sb, i);
            sb.append("=");
            sb.append(i2);
            sb.append("u+");
            sb.append(i3);
            sb.append(XmlTags.TAG_SESSION);
        }

        private void printStepCpuUidCheckinDetails(StringBuilder sb, int i, int i2, int i3) {
            sb.append('/');
            sb.append(i);
            sb.append(":");
            sb.append(i2);
            sb.append(":");
            sb.append(i3);
        }

        private static class HistoryLogTimeFormatter {
            private static final long HOUR_MILLIS = 3600000;
            private static final long MINUTE_MILLIS = 60000;
            private long mCachedHour;
            private String mCachedHourFormatted;
            private final Date mDate;
            private final SimpleDateFormat mDateFormat;
            private final long mTimeZoneOffset;

            private HistoryLogTimeFormatter(TimeZone timeZone) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:", Locale.US);
                this.mDateFormat = simpleDateFormat;
                this.mDate = new Date();
                this.mTimeZoneOffset = timeZone.getRawOffset();
                simpleDateFormat.getCalendar().setTimeZone(timeZone);
            }

            void append(StringBuilder sb, long j) {
                long j2 = this.mTimeZoneOffset + j;
                long j3 = j2 / 3600000;
                if (j3 != this.mCachedHour) {
                    this.mDate.setTime(j);
                    this.mCachedHourFormatted = this.mDateFormat.format(this.mDate);
                    this.mCachedHour = j3;
                }
                sb.append(this.mCachedHourFormatted);
                long j4 = j2 % 3600000;
                long j5 = j4 / 60000;
                if (j5 < 10) {
                    sb.append('0');
                }
                sb.append(j5);
                sb.append(ShortcutConstants.SERVICES_SEPARATOR);
                long j6 = j4 % 60000;
                long j7 = j6 / 1000;
                if (j7 < 10) {
                    sb.append('0');
                }
                sb.append(j7);
                sb.append('.');
                long j8 = j6 % 1000;
                if (j8 < 100) {
                    sb.append('0');
                    if (j8 < 10) {
                        sb.append('0');
                    }
                }
                sb.append(j8);
            }
        }
    }

    private void printSizeValue(PrintWriter printWriter, long j) {
        String str;
        float f = j;
        if (f < 10240.0f) {
            str = "";
        } else {
            f /= 1024.0f;
            str = "KB";
        }
        if (f >= 10240.0f) {
            f /= 1024.0f;
            str = "MB";
        }
        if (f >= 10240.0f) {
            f /= 1024.0f;
            str = "GB";
        }
        if (f >= 10240.0f) {
            f /= 1024.0f;
            str = "TB";
        }
        if (f >= 10240.0f) {
            f /= 1024.0f;
            str = "PB";
        }
        printWriter.print((int) f);
        printWriter.print(str);
    }

    private static boolean dumpTimeEstimate(PrintWriter printWriter, String str, String str2, String str3, long j) {
        if (j < 0) {
            return false;
        }
        printWriter.print(str);
        printWriter.print(str2);
        printWriter.print(str3);
        StringBuilder sb = new StringBuilder(64);
        formatTimeMs(sb, j);
        printWriter.print(sb);
        printWriter.println();
        return true;
    }

    private static boolean dumpDurationSteps(PrintWriter printWriter, String str, String str2, LevelStepTracker levelStepTracker, boolean z) {
        int i;
        int i2;
        int i3;
        String str3;
        int i4 = 0;
        if (levelStepTracker == null || (i = levelStepTracker.mNumStepDurations) <= 0) {
            return false;
        }
        if (!z) {
            printWriter.println(str2);
        }
        String[] strArr = new String[5];
        int i5 = 0;
        while (true) {
            int i6 = 1;
            if (i5 >= i) {
                return true;
            }
            long durationAt = levelStepTracker.getDurationAt(i5);
            int levelAt = levelStepTracker.getLevelAt(i5);
            long initModeAt = levelStepTracker.getInitModeAt(i5);
            long modModeAt = levelStepTracker.getModModeAt(i5);
            int i7 = i4;
            if (z) {
                strArr[i7] = Long.toString(durationAt);
                strArr[1] = Integer.toString(levelAt);
                if ((modModeAt & 3) == 0) {
                    i2 = i;
                    int i8 = ((int) (initModeAt & 3)) + 1;
                    if (i8 == 1) {
                        strArr[2] = "s-";
                    } else if (i8 == 2) {
                        strArr[2] = "s+";
                    } else if (i8 == 3) {
                        strArr[2] = "sd";
                    } else if (i8 == 4) {
                        strArr[2] = "sds";
                    } else {
                        strArr[2] = "?";
                    }
                } else {
                    i2 = i;
                    strArr[2] = "";
                }
                if ((modModeAt & 4) == 0) {
                    strArr[3] = (initModeAt & 4) != 0 ? "p+" : "p-";
                } else {
                    strArr[3] = "";
                }
                if ((modModeAt & 8) == 0) {
                    strArr[4] = (initModeAt & 8) != 0 ? "i+" : "i-";
                } else {
                    strArr[4] = "";
                }
                dumpLine(printWriter, i7, "i", str2, strArr);
            } else {
                i2 = i;
                printWriter.print(str);
                printWriter.print("#");
                printWriter.print(i5);
                printWriter.print(": ");
                TimeUtils.formatDuration(durationAt, printWriter);
                printWriter.print(" to ");
                printWriter.print(levelAt);
                String str4 = " (";
                if ((modModeAt & 3) == 0) {
                    printWriter.print(" (");
                    int i9 = ((int) (initModeAt & 3)) + 1;
                    if (i9 == 1) {
                        printWriter.print("screen-off");
                    } else if (i9 == 2) {
                        printWriter.print("screen-on");
                    } else if (i9 == 3) {
                        printWriter.print("screen-doze");
                    } else if (i9 == 4) {
                        printWriter.print("screen-doze-suspend");
                    } else {
                        printWriter.print("screen-?");
                    }
                    i3 = 1;
                } else {
                    i3 = i7;
                }
                if ((modModeAt & 4) == 0) {
                    if (i3 == 0) {
                        str3 = " (";
                    } else {
                        str3 = ", ";
                    }
                    printWriter.print(str3);
                    printWriter.print((initModeAt & 4) != 0 ? "power-save-on" : "power-save-off");
                    i3 = 1;
                }
                if ((modModeAt & 8) == 0) {
                    if (i3 != 0) {
                        str4 = ", ";
                    }
                    printWriter.print(str4);
                    printWriter.print((initModeAt & 8) != 0 ? "device-idle-on" : "device-idle-off");
                } else {
                    i6 = i3;
                }
                if (i6 != 0) {
                    printWriter.print(NavigationBarInflaterView.KEY_CODE_END);
                }
                printWriter.println();
            }
            i5++;
            i4 = i7;
            i = i2;
        }
    }

    private static void dumpDurationSteps(ProtoOutputStream protoOutputStream, long j, LevelStepTracker levelStepTracker) {
        int i;
        int i2;
        if (levelStepTracker == null) {
            return;
        }
        int i3 = levelStepTracker.mNumStepDurations;
        int i4 = 0;
        while (i4 < i3) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1112396529665L, levelStepTracker.getDurationAt(i4));
            protoOutputStream.write(1120986464258L, levelStepTracker.getLevelAt(i4));
            long initModeAt = levelStepTracker.getInitModeAt(i4);
            long modModeAt = levelStepTracker.getModModeAt(i4);
            int i5 = 3;
            int i6 = 1;
            if ((modModeAt & 3) == 0) {
                int i7 = ((int) (initModeAt & 3)) + 1;
                if (i7 == 1) {
                    i2 = 2;
                } else if (i7 == 2) {
                    i = i4;
                    i2 = 1;
                } else if (i7 != 3) {
                    i2 = 4;
                    if (i7 != 4) {
                        i2 = 5;
                    }
                } else {
                    i = i4;
                    i2 = 3;
                }
                i = i4;
            } else {
                i = i4;
                i2 = 0;
            }
            protoOutputStream.write(1159641169923L, i2);
            if ((modModeAt & 4) != 0) {
                i6 = 0;
            } else if ((4 & initModeAt) == 0) {
                i6 = 2;
            }
            protoOutputStream.write(1159641169924L, i6);
            if ((modModeAt & 8) != 0) {
                i5 = 0;
            } else if ((8 & initModeAt) != 0) {
                i5 = 2;
            }
            protoOutputStream.write(1159641169925L, i5);
            protoOutputStream.end(start);
            i4 = i + 1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x01c8  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:65:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0102 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void dumpHistory(java.io.PrintWriter r24, int r25, long r26, boolean r28) {
        /*
            Method dump skipped, instructions count: 591
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.BatteryStats.dumpHistory(java.io.PrintWriter, int, long, boolean):void");
    }

    private void dumpHistoryTagPoolLocked(PrintWriter printWriter, boolean z) {
        if (z) {
            for (int i = 0; i < getHistoryStringPoolSize(); i++) {
                printWriter.print(9);
                printWriter.print(',');
                printWriter.print(HISTORY_STRING_POOL);
                printWriter.print(',');
                printWriter.print(i);
                printWriter.print(",");
                printWriter.print(getHistoryTagPoolUid(i));
                printWriter.print(",\"");
                String historyTagPoolString = getHistoryTagPoolString(i);
                if (historyTagPoolString != null) {
                    printWriter.print(historyTagPoolString.replace("\\", "\\\\").replace("\"", "\\\""));
                }
                printWriter.print("\"");
                printWriter.println();
            }
        }
    }

    private void dumpDailyLevelStepSummary(PrintWriter printWriter, String str, String str2, LevelStepTracker levelStepTracker, StringBuilder sb, int[] iArr) {
        if (levelStepTracker == null) {
            return;
        }
        long computeTimeEstimate = levelStepTracker.computeTimeEstimate(0L, 0L, iArr);
        int i = 0;
        if (computeTimeEstimate >= 0) {
            printWriter.print(str);
            printWriter.print(str2);
            printWriter.print(" total time: ");
            sb.setLength(0);
            formatTimeMs(sb, computeTimeEstimate);
            printWriter.print(sb);
            printWriter.print(" (from ");
            printWriter.print(iArr[0]);
            printWriter.println(" steps)");
        }
        int i2 = 0;
        while (true) {
            if (i2 >= STEP_LEVEL_MODES_OF_INTEREST.length) {
                return;
            }
            int i3 = i;
            long computeTimeEstimate2 = levelStepTracker.computeTimeEstimate(r4[i2], STEP_LEVEL_MODE_VALUES[i2], iArr);
            if (computeTimeEstimate2 > 0) {
                printWriter.print(str);
                printWriter.print(str2);
                printWriter.print(" ");
                printWriter.print(STEP_LEVEL_MODE_LABELS[i2]);
                printWriter.print(" time: ");
                sb.setLength(i3);
                formatTimeMs(sb, computeTimeEstimate2);
                printWriter.print(sb);
                printWriter.print(" (from ");
                printWriter.print(iArr[i3]);
                printWriter.println(" steps)");
            }
            i2++;
            i = i3;
        }
    }

    private void dumpDailyPackageChanges(PrintWriter printWriter, String str, ArrayList<PackageChange> arrayList) {
        if (arrayList == null) {
            return;
        }
        printWriter.print(str);
        printWriter.println("Package changes:");
        for (int i = 0; i < arrayList.size(); i++) {
            PackageChange packageChange = arrayList.get(i);
            if (packageChange.mUpdate) {
                printWriter.print(str);
                printWriter.print("  Update ");
                printWriter.print(packageChange.mPackageName);
                printWriter.print(" vers=");
                printWriter.println(packageChange.mVersionCode);
            } else {
                printWriter.print(str);
                printWriter.print("  Uninstall ");
                printWriter.println(packageChange.mPackageName);
            }
        }
    }

    public void dump(Context context, PrintWriter printWriter, int i, int i2, long j, BatteryStatsDumpHelper batteryStatsDumpHelper) {
        synchronized (this) {
            prepareForDumpLocked();
        }
        boolean z = (i & 14) != 0;
        if ((i & 8) != 0 || !z) {
            dumpHistory(printWriter, i, j, false);
            printWriter.println();
        }
        if (z && (i & 6) == 0) {
            return;
        }
        synchronized (this) {
            dumpLocked(context, printWriter, i, i2, z, batteryStatsDumpHelper);
        }
    }

    private void dumpLocked(Context context, PrintWriter printWriter, int i, int i2, boolean z, BatteryStatsDumpHelper batteryStatsDumpHelper) {
        long j;
        BatteryStats batteryStats = this;
        PrintWriter printWriter2 = printWriter;
        if (z) {
            j = 0;
        } else {
            SparseArray<? extends Uid> uidStats = batteryStats.getUidStats();
            int size = uidStats.size();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            boolean z2 = false;
            for (int i3 = 0; i3 < size; i3++) {
                SparseArray<? extends Uid.Pid> pidStats = uidStats.valueAt(i3).getPidStats();
                if (pidStats != null) {
                    for (int i4 = 0; i4 < pidStats.size(); i4++) {
                        Uid.Pid valueAt = pidStats.valueAt(i4);
                        if (!z2) {
                            printWriter2.println("Per-PID Stats:");
                            z2 = true;
                        }
                        long j2 = valueAt.mWakeSumMs + (valueAt.mWakeNesting > 0 ? elapsedRealtime - valueAt.mWakeStartMs : 0L);
                        printWriter2.print("  PID ");
                        printWriter2.print(pidStats.keyAt(i4));
                        printWriter2.print(" wake time: ");
                        TimeUtils.formatDuration(j2, printWriter2);
                        printWriter2.println("");
                    }
                }
            }
            j = 0;
            if (z2) {
                printWriter2.println();
            }
        }
        if (!z || (i & 2) != 0) {
            if (dumpDurationSteps(printWriter2, "  ", "Discharge step durations:", batteryStats.getDischargeLevelStepTracker(), false)) {
                long computeBatteryTimeRemaining = batteryStats.computeBatteryTimeRemaining(SystemClock.elapsedRealtime() * 1000);
                if (computeBatteryTimeRemaining >= j) {
                    printWriter2.print("  Estimated discharge time remaining: ");
                    TimeUtils.formatDuration(computeBatteryTimeRemaining / 1000, printWriter2);
                    printWriter2.println();
                }
                LevelStepTracker dischargeLevelStepTracker = batteryStats.getDischargeLevelStepTracker();
                int i5 = 0;
                while (true) {
                    if (i5 >= STEP_LEVEL_MODES_OF_INTEREST.length) {
                        break;
                    }
                    dumpTimeEstimate(printWriter2, "  Estimated ", STEP_LEVEL_MODE_LABELS[i5], " time: ", dischargeLevelStepTracker.computeTimeEstimate(r2[i5], STEP_LEVEL_MODE_VALUES[i5], null));
                    i5++;
                }
                printWriter2.println();
            }
            if (dumpDurationSteps(printWriter2, "  ", "Charge step durations:", batteryStats.getChargeLevelStepTracker(), false)) {
                long computeChargeTimeRemaining = batteryStats.computeChargeTimeRemaining(SystemClock.elapsedRealtime() * 1000);
                if (computeChargeTimeRemaining >= j) {
                    printWriter2.print("  Estimated charge time remaining: ");
                    TimeUtils.formatDuration(computeChargeTimeRemaining / 1000, printWriter2);
                    printWriter2.println();
                }
                printWriter2.println();
            }
        }
        if (!z || (i & 4) != 0) {
            printWriter2.println("Daily stats:");
            printWriter2.print("  Current start time: ");
            printWriter2.println(DateFormat.format("yyyy-MM-dd-HH-mm-ss", batteryStats.getCurrentDailyStartTime()).toString());
            printWriter2.print("  Next min deadline: ");
            printWriter2.println(DateFormat.format("yyyy-MM-dd-HH-mm-ss", batteryStats.getNextMinDailyDeadline()).toString());
            printWriter2.print("  Next max deadline: ");
            printWriter2.println(DateFormat.format("yyyy-MM-dd-HH-mm-ss", batteryStats.getNextMaxDailyDeadline()).toString());
            StringBuilder sb = new StringBuilder(64);
            int[] iArr = new int[1];
            LevelStepTracker dailyDischargeLevelStepTracker = batteryStats.getDailyDischargeLevelStepTracker();
            LevelStepTracker dailyChargeLevelStepTracker = batteryStats.getDailyChargeLevelStepTracker();
            ArrayList<PackageChange> dailyPackageChanges = batteryStats.getDailyPackageChanges();
            if (dailyDischargeLevelStepTracker.mNumStepDurations > 0 || dailyChargeLevelStepTracker.mNumStepDurations > 0 || dailyPackageChanges != null) {
                if ((i & 4) != 0 || !z) {
                    if (dumpDurationSteps(printWriter2, "    ", "  Current daily discharge step durations:", dailyDischargeLevelStepTracker, false)) {
                        dumpDailyLevelStepSummary(printWriter2, "      ", "Discharge", dailyDischargeLevelStepTracker, sb, iArr);
                    }
                    if (dumpDurationSteps(printWriter2, "    ", "  Current daily charge step durations:", dailyChargeLevelStepTracker, false)) {
                        batteryStats = this;
                        batteryStats.dumpDailyLevelStepSummary(printWriter2, "      ", "Charge", dailyChargeLevelStepTracker, sb, iArr);
                    } else {
                        batteryStats = this;
                    }
                    batteryStats.dumpDailyPackageChanges(printWriter2, "    ", dailyPackageChanges);
                } else {
                    printWriter2.println("  Current daily steps:");
                    batteryStats.dumpDailyLevelStepSummary(printWriter2, "    ", "Discharge", dailyDischargeLevelStepTracker, sb, iArr);
                    batteryStats = this;
                    printWriter2 = printWriter;
                    batteryStats.dumpDailyLevelStepSummary(printWriter2, "    ", "Charge", dailyChargeLevelStepTracker, sb, iArr);
                }
            }
            int i6 = 0;
            while (true) {
                DailyItem dailyItemLocked = batteryStats.getDailyItemLocked(i6);
                if (dailyItemLocked == null) {
                    break;
                }
                int i7 = i6 + 1;
                int i8 = i & 4;
                if (i8 != 0) {
                    printWriter2.println();
                }
                printWriter2.print("  Daily from ");
                printWriter2.print(DateFormat.format("yyyy-MM-dd-HH-mm-ss", dailyItemLocked.mStartTime).toString());
                printWriter2.print(" to ");
                printWriter2.print(DateFormat.format("yyyy-MM-dd-HH-mm-ss", dailyItemLocked.mEndTime).toString());
                printWriter2.println(":");
                if (i8 != 0 || !z) {
                    if (dumpDurationSteps(printWriter2, "      ", "    Discharge step durations:", dailyItemLocked.mDischargeSteps, false)) {
                        dumpDailyLevelStepSummary(printWriter2, "        ", "Discharge", dailyItemLocked.mDischargeSteps, sb, iArr);
                    }
                    if (dumpDurationSteps(printWriter2, "      ", "    Charge step durations:", dailyItemLocked.mChargeSteps, false)) {
                        batteryStats = this;
                        batteryStats.dumpDailyLevelStepSummary(printWriter2, "        ", "Charge", dailyItemLocked.mChargeSteps, sb, iArr);
                    } else {
                        batteryStats = this;
                    }
                    batteryStats.dumpDailyPackageChanges(printWriter2, "    ", dailyItemLocked.mPackageChanges);
                } else {
                    batteryStats.dumpDailyLevelStepSummary(printWriter2, "    ", "Discharge", dailyItemLocked.mDischargeSteps, sb, iArr);
                    batteryStats = this;
                    printWriter2 = printWriter;
                    batteryStats.dumpDailyLevelStepSummary(printWriter2, "    ", "Charge", dailyItemLocked.mChargeSteps, sb, iArr);
                }
                i6 = i7;
            }
            printWriter2.println();
        }
        if (!z || (i & 2) != 0) {
            printWriter2.println("Feature status:");
            printWriter2.println("  Can read 'charging remaining time': " + batteryStats.canReadTimeToFullNow());
            printWriter2.println("  Can trust power_profile: " + batteryStats.canTrustSecPowerProfile());
            printWriter2.println();
        }
        if (!z || (i & 2) != 0) {
            printWriter2.println("Statistics since last charge:");
            printWriter2.println("  System starts: " + batteryStats.getStartCount() + ", currently on battery: " + batteryStats.getIsOnBattery());
            PrintWriter printWriter3 = printWriter2;
            batteryStats.dumpLocked(context, printWriter3, "", 0, i2, (i & 64) != 0, batteryStatsDumpHelper);
            printWriter2 = printWriter3;
            printWriter2.println();
        }
        if ((i & 32) != 0) {
            batteryStats.printLatestBackupData(printWriter2);
        }
    }

    public void dumpCheckin(Context context, PrintWriter printWriter, List<ApplicationInfo> list, int i, long j, BatteryStatsDumpHelper batteryStatsDumpHelper) {
        synchronized (this) {
            prepareForDumpLocked();
            dumpLine(printWriter, 0, "i", VERSION_DATA, 36, Integer.valueOf(getParcelVersion()), getStartPlatformVersion(), getEndPlatformVersion());
        }
        if ((i & 24) != 0) {
            dumpHistory(printWriter, i, j, true);
        }
        if ((i & 8) != 0) {
            return;
        }
        synchronized (this) {
            dumpCheckinLocked(context, printWriter, list, i, batteryStatsDumpHelper);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void dumpCheckinLocked(Context context, PrintWriter printWriter, List<ApplicationInfo> list, int i, BatteryStatsDumpHelper batteryStatsDumpHelper) {
        if (list != null) {
            SparseArray sparseArray = new SparseArray();
            for (int i2 = 0; i2 < list.size(); i2++) {
                ApplicationInfo applicationInfo = list.get(i2);
                Pair pair = (Pair) sparseArray.get(UserHandle.getAppId(applicationInfo.uid));
                if (pair == null) {
                    pair = new Pair(new ArrayList(), new MutableBoolean(false));
                    sparseArray.put(UserHandle.getAppId(applicationInfo.uid), pair);
                }
                ((ArrayList) pair.first).add(applicationInfo.packageName);
            }
            SparseArray<? extends Uid> uidStats = getUidStats();
            int size = uidStats.size();
            for (int i3 = 0; i3 < size; i3++) {
                int appId = UserHandle.getAppId(uidStats.keyAt(i3));
                Pair pair2 = (Pair) sparseArray.get(appId);
                if (pair2 != null && !((MutableBoolean) pair2.second).value) {
                    ((MutableBoolean) pair2.second).value = true;
                    for (int i4 = 0; i4 < ((ArrayList) pair2.first).size(); i4++) {
                        dumpLine(printWriter, 0, "i", "uid", Integer.toString(appId), (String) ((ArrayList) pair2.first).get(i4));
                    }
                }
            }
        }
        if ((i & 4) == 0) {
            dumpDurationSteps(printWriter, "", DISCHARGE_STEP_DATA, getDischargeLevelStepTracker(), true);
            String[] strArr = new String[1];
            long computeBatteryTimeRemaining = computeBatteryTimeRemaining(SystemClock.elapsedRealtime() * 1000);
            if (computeBatteryTimeRemaining >= 0) {
                strArr[0] = Long.toString(computeBatteryTimeRemaining);
                dumpLine(printWriter, 0, "i", DISCHARGE_TIME_REMAIN_DATA, strArr);
            }
            dumpDurationSteps(printWriter, "", CHARGE_STEP_DATA, getChargeLevelStepTracker(), true);
            long computeChargeTimeRemaining = computeChargeTimeRemaining(SystemClock.elapsedRealtime() * 1000);
            if (computeChargeTimeRemaining >= 0) {
                strArr[0] = Long.toString(computeChargeTimeRemaining);
                dumpLine(printWriter, 0, "i", CHARGE_TIME_REMAIN_DATA, strArr);
            }
            dumpCheckinLocked(context, printWriter, 0, -1, (i & 64) != 0, batteryStatsDumpHelper);
        }
    }

    public void dumpProtoLocked(Context context, FileDescriptor fileDescriptor, List<ApplicationInfo> list, int i, long j, BatteryStatsDumpHelper batteryStatsDumpHelper) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
        prepareForDumpLocked();
        if ((i & 24) != 0) {
            dumpProtoHistoryLocked(protoOutputStream, i, j);
            protoOutputStream.flush();
            return;
        }
        long start = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1120986464257L, 36);
        protoOutputStream.write(1112396529666L, getParcelVersion());
        protoOutputStream.write(1138166333443L, getStartPlatformVersion());
        protoOutputStream.write(1138166333444L, getEndPlatformVersion());
        if ((i & 4) == 0) {
            BatteryUsageStats batteryUsageStats = batteryStatsDumpHelper.getBatteryUsageStats(this, false);
            dumpProtoAppsLocked(protoOutputStream, batteryUsageStats, list, new ProportionalAttributionCalculator(context, batteryUsageStats));
            dumpProtoSystemLocked(protoOutputStream, batteryUsageStats);
        }
        protoOutputStream.end(start);
        protoOutputStream.flush();
    }

    private void dumpProtoAppsLocked(ProtoOutputStream protoOutputStream, BatteryUsageStats batteryUsageStats, List<ApplicationInfo> list, ProportionalAttributionCalculator proportionalAttributionCalculator) {
        long j;
        long j2;
        long j3;
        SparseArray sparseArray;
        long j4;
        ArrayMap<String, SparseIntArray> arrayMap;
        int i;
        long j5;
        long j6;
        int i2;
        long[] jArr;
        long[] jArr2;
        int i3;
        long[] cpuFreqTimes;
        int i4;
        int i5;
        long j7;
        SparseArray sparseArray2;
        long j8;
        ArrayList arrayList;
        long j9;
        long j10;
        int i6;
        ArrayMap<String, ? extends Uid.Pkg> arrayMap2;
        ArrayList arrayList2;
        long j11;
        ArrayMap<String, ? extends Uid.Pkg> arrayMap3;
        int i7;
        int i8;
        ProtoOutputStream protoOutputStream2 = protoOutputStream;
        long uptimeMillis = SystemClock.uptimeMillis() * 1000;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j12 = 1000 * elapsedRealtime;
        long batteryUptime = getBatteryUptime(uptimeMillis);
        SparseArray sparseArray3 = new SparseArray();
        if (list != null) {
            for (int i9 = 0; i9 < list.size(); i9++) {
                ApplicationInfo applicationInfo = list.get(i9);
                int appId = UserHandle.getAppId(applicationInfo.uid);
                ArrayList arrayList3 = (ArrayList) sparseArray3.get(appId);
                if (arrayList3 == null) {
                    arrayList3 = new ArrayList();
                    sparseArray3.put(appId, arrayList3);
                }
                arrayList3.add(applicationInfo.packageName);
            }
        }
        SparseArray sparseArray4 = new SparseArray();
        List<UidBatteryConsumer> uidBatteryConsumers = batteryUsageStats.getUidBatteryConsumers();
        boolean z = true;
        int size = uidBatteryConsumers.size() - 1;
        while (size >= 0) {
            UidBatteryConsumer uidBatteryConsumer = uidBatteryConsumers.get(size);
            sparseArray4.put(uidBatteryConsumer.getUid(), uidBatteryConsumer);
            size--;
            z = z;
        }
        SparseArray<? extends Uid> uidStats = getUidStats();
        int size2 = uidStats.size();
        int i10 = 0;
        while (i10 < size2) {
            SparseArray sparseArray5 = sparseArray4;
            long start = protoOutputStream2.start(2246267895813L);
            Uid valueAt = uidStats.valueAt(i10);
            int i11 = size2;
            int keyAt = uidStats.keyAt(i10);
            protoOutputStream2.write(1120986464257L, keyAt);
            ArrayList arrayList4 = (ArrayList) sparseArray3.get(UserHandle.getAppId(keyAt));
            if (arrayList4 == null) {
                arrayList4 = new ArrayList();
            }
            ArrayMap<String, ? extends Uid.Pkg> packageStats = valueAt.getPackageStats();
            SparseArray<? extends Uid> sparseArray6 = uidStats;
            int size3 = packageStats.size() - 1;
            int i12 = i10;
            while (size3 >= 0) {
                String keyAt2 = packageStats.keyAt(size3);
                ArrayMap<String, ? extends Uid.Pkg.Serv> serviceStats = packageStats.valueAt(size3).getServiceStats();
                if (serviceStats.size() == 0) {
                    j7 = j12;
                    j9 = elapsedRealtime;
                    j10 = batteryUptime;
                    sparseArray2 = sparseArray3;
                    j8 = start;
                    arrayList = arrayList4;
                    arrayMap2 = packageStats;
                    i6 = size3;
                } else {
                    j7 = j12;
                    sparseArray2 = sparseArray3;
                    j8 = start;
                    long start2 = protoOutputStream2.start(2246267895810L);
                    protoOutputStream2.write(1138166333441L, keyAt2);
                    arrayList4.remove(keyAt2);
                    int size4 = serviceStats.size() - 1;
                    while (size4 >= 0) {
                        Uid.Pkg.Serv valueAt2 = serviceStats.valueAt(size4);
                        long j13 = batteryUptime;
                        long roundUsToMs = roundUsToMs(valueAt2.getStartTime(batteryUptime, 0));
                        int starts = valueAt2.getStarts(0);
                        int launches = valueAt2.getLaunches(0);
                        if (roundUsToMs == 0 && starts == 0 && launches == 0) {
                            i8 = size4;
                            arrayList2 = arrayList4;
                            j11 = elapsedRealtime;
                            i7 = size3;
                            arrayMap3 = packageStats;
                        } else {
                            arrayList2 = arrayList4;
                            j11 = elapsedRealtime;
                            arrayMap3 = packageStats;
                            long start3 = protoOutputStream2.start(2246267895810L);
                            i7 = size3;
                            i8 = size4;
                            protoOutputStream2.write(1138166333441L, serviceStats.keyAt(size4));
                            protoOutputStream2.write(1112396529666L, roundUsToMs);
                            protoOutputStream2.write(1120986464259L, starts);
                            protoOutputStream2.write(1120986464260L, launches);
                            protoOutputStream2.end(start3);
                        }
                        size4 = i8 - 1;
                        size3 = i7;
                        packageStats = arrayMap3;
                        arrayList4 = arrayList2;
                        batteryUptime = j13;
                        elapsedRealtime = j11;
                    }
                    arrayList = arrayList4;
                    j9 = elapsedRealtime;
                    j10 = batteryUptime;
                    i6 = size3;
                    arrayMap2 = packageStats;
                    protoOutputStream2.end(start2);
                }
                size3 = i6 - 1;
                packageStats = arrayMap2;
                arrayList4 = arrayList;
                sparseArray3 = sparseArray2;
                j12 = j7;
                start = j8;
                batteryUptime = j10;
                elapsedRealtime = j9;
            }
            long j14 = j12;
            long j15 = elapsedRealtime;
            long j16 = batteryUptime;
            SparseArray sparseArray7 = sparseArray3;
            long j17 = start;
            ArrayMap<String, ? extends Uid.Pkg> arrayMap4 = packageStats;
            Iterator it = arrayList4.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                long start4 = protoOutputStream2.start(2246267895810L);
                protoOutputStream2.write(1138166333441L, str);
                protoOutputStream2.end(start4);
            }
            if (valueAt.getAggregatedPartialWakelockTimer() != null) {
                Timer aggregatedPartialWakelockTimer = valueAt.getAggregatedPartialWakelockTimer();
                j = j15;
                long totalDurationMsLocked = aggregatedPartialWakelockTimer.getTotalDurationMsLocked(j);
                Timer subTimer = aggregatedPartialWakelockTimer.getSubTimer();
                long totalDurationMsLocked2 = subTimer != null ? subTimer.getTotalDurationMsLocked(j) : 0L;
                long start5 = protoOutputStream2.start(1146756268056L);
                protoOutputStream2.write(1112396529665L, totalDurationMsLocked);
                j2 = 1112396529666L;
                protoOutputStream2.write(1112396529666L, totalDurationMsLocked2);
                protoOutputStream2.end(start5);
            } else {
                j = j15;
                j2 = 1112396529666L;
            }
            long j18 = j14;
            dumpTimer(protoOutputStream2, 1146756268040L, valueAt.getAudioTurnedOnTimer(), j18, 0);
            dumpControllerActivityProto(protoOutputStream2, 1146756268035L, valueAt.getBluetoothControllerActivity(), 0);
            Timer bluetoothScanTimer = valueAt.getBluetoothScanTimer();
            if (bluetoothScanTimer != null) {
                long start6 = protoOutputStream2.start(1146756268038L);
                dumpTimer(protoOutputStream2, 1146756268033L, bluetoothScanTimer, j18, 0);
                protoOutputStream2 = protoOutputStream;
                dumpTimer(protoOutputStream2, 1146756268034L, valueAt.getBluetoothScanBackgroundTimer(), j18, 0);
                dumpTimer(protoOutputStream2, 1146756268035L, valueAt.getBluetoothUnoptimizedScanTimer(), j18, 0);
                dumpTimer(protoOutputStream2, 1146756268036L, valueAt.getBluetoothUnoptimizedScanBackgroundTimer(), j18, 0);
                if (valueAt.getBluetoothScanResultCounter() != null) {
                    Counter bluetoothScanResultCounter = valueAt.getBluetoothScanResultCounter();
                    i4 = 0;
                    i5 = bluetoothScanResultCounter.getCountLocked(0);
                } else {
                    i4 = 0;
                    i5 = 0;
                }
                protoOutputStream2.write(1120986464261L, i5);
                j3 = 1120986464262L;
                protoOutputStream2.write(1120986464262L, valueAt.getBluetoothScanResultBgCounter() != null ? valueAt.getBluetoothScanResultBgCounter().getCountLocked(i4) : 0);
                protoOutputStream2.end(start6);
            } else {
                j3 = 1120986464262L;
            }
            dumpTimer(protoOutputStream2, 1146756268041L, valueAt.getCameraTurnedOnTimer(), j18, 0);
            long start7 = protoOutputStream2.start(1146756268039L);
            protoOutputStream2.write(1112396529665L, roundUsToMs(valueAt.getUserCpuTimeUs(0)));
            protoOutputStream2.write(1112396529666L, roundUsToMs(valueAt.getSystemCpuTimeUs(0)));
            CpuScalingPolicies cpuScalingPolicies = getCpuScalingPolicies();
            if (cpuScalingPolicies != null && (cpuFreqTimes = valueAt.getCpuFreqTimes(0)) != null && cpuFreqTimes.length == cpuScalingPolicies.getScalingStepCount()) {
                long[] screenOffCpuFreqTimes = valueAt.getScreenOffCpuFreqTimes(0);
                if (screenOffCpuFreqTimes == null) {
                    screenOffCpuFreqTimes = new long[cpuFreqTimes.length];
                }
                int i13 = 0;
                while (i13 < cpuFreqTimes.length) {
                    long start8 = protoOutputStream2.start(2246267895811L);
                    int i14 = i13 + 1;
                    long[] jArr3 = screenOffCpuFreqTimes;
                    protoOutputStream2.write(1120986464257L, i14);
                    protoOutputStream2.write(1112396529666L, cpuFreqTimes[i13]);
                    protoOutputStream2.write(1112396529667L, jArr3[i13]);
                    protoOutputStream2.end(start8);
                    screenOffCpuFreqTimes = jArr3;
                    i13 = i14;
                    j18 = j18;
                }
            }
            long j19 = j18;
            int scalingStepCount = getCpuScalingPolicies().getScalingStepCount();
            long[] jArr4 = new long[scalingStepCount];
            long[] jArr5 = new long[scalingStepCount];
            int i15 = 0;
            while (i15 < 7) {
                if (valueAt.getCpuFreqTimes(jArr4, i15)) {
                    if (!valueAt.getScreenOffCpuFreqTimes(jArr5, i15)) {
                        Arrays.fill(jArr5, 0L);
                    }
                    long start9 = protoOutputStream2.start(2246267895812L);
                    protoOutputStream2.write(1159641169921L, i15);
                    int i16 = 0;
                    while (i16 < scalingStepCount) {
                        int i17 = scalingStepCount;
                        long[] jArr6 = jArr4;
                        int i18 = i16;
                        long start10 = protoOutputStream2.start(2246267895810L);
                        int i19 = i18 + 1;
                        long[] jArr7 = jArr5;
                        protoOutputStream2.write(1120986464257L, i19);
                        protoOutputStream2.write(1112396529666L, jArr6[i18]);
                        protoOutputStream2.write(1112396529667L, jArr7[i18]);
                        protoOutputStream2.end(start10);
                        scalingStepCount = i17;
                        jArr4 = jArr6;
                        i15 = i15;
                        i16 = i19;
                        jArr5 = jArr7;
                    }
                    i2 = scalingStepCount;
                    jArr = jArr4;
                    jArr2 = jArr5;
                    i3 = i15;
                    protoOutputStream2.end(start9);
                } else {
                    i2 = scalingStepCount;
                    jArr = jArr4;
                    jArr2 = jArr5;
                    i3 = i15;
                }
                i15 = i3 + 1;
                scalingStepCount = i2;
                jArr4 = jArr;
                jArr5 = jArr2;
            }
            protoOutputStream2.end(start7);
            long j20 = j19;
            dumpTimer(protoOutputStream2, 1146756268042L, valueAt.getFlashlightTurnedOnTimer(), j20, 0);
            ProtoOutputStream protoOutputStream3 = protoOutputStream;
            dumpTimer(protoOutputStream3, 1146756268043L, valueAt.getForegroundActivityTimer(), j20, 0);
            dumpTimer(protoOutputStream3, 1146756268044L, valueAt.getForegroundServiceTimer(), j20, 0);
            ArrayMap<String, SparseIntArray> jobCompletionStats = valueAt.getJobCompletionStats();
            int i20 = 0;
            while (i20 < jobCompletionStats.size()) {
                SparseIntArray valueAt3 = jobCompletionStats.valueAt(i20);
                if (valueAt3 != null) {
                    long start11 = protoOutputStream3.start(2246267895824L);
                    arrayMap = jobCompletionStats;
                    i = i20;
                    protoOutputStream3.write(1138166333441L, jobCompletionStats.keyAt(i20));
                    int[] jobStopReasonCodes = JobParameters.getJobStopReasonCodes();
                    int length = jobStopReasonCodes.length;
                    int i21 = 0;
                    while (i21 < length) {
                        int i22 = length;
                        int i23 = jobStopReasonCodes[i21];
                        long j21 = j;
                        long start12 = protoOutputStream3.start(2246267895810L);
                        protoOutputStream3.write(1159641169921L, i23);
                        protoOutputStream3.write(1120986464258L, valueAt3.get(i23, 0));
                        protoOutputStream3.end(start12);
                        i21++;
                        length = i22;
                        j = j21;
                        j20 = j20;
                    }
                    j5 = j20;
                    j6 = j;
                    protoOutputStream3.end(start11);
                } else {
                    arrayMap = jobCompletionStats;
                    i = i20;
                    j5 = j20;
                    j6 = j;
                }
                i20 = i + 1;
                jobCompletionStats = arrayMap;
                j = j6;
                j20 = j5;
            }
            long j22 = j20;
            long j23 = j;
            ArrayMap<String, ? extends Timer> jobStats = valueAt.getJobStats();
            for (int size5 = jobStats.size() - 1; size5 >= 0; size5--) {
                Timer valueAt4 = jobStats.valueAt(size5);
                Timer subTimer2 = valueAt4.getSubTimer();
                long start13 = protoOutputStream3.start(2246267895823L);
                protoOutputStream3.write(1138166333441L, jobStats.keyAt(size5));
                dumpTimer(protoOutputStream3, 1146756268034L, valueAt4, j22, 0);
                protoOutputStream3 = protoOutputStream;
                dumpTimer(protoOutputStream3, 1146756268035L, subTimer2, j22, 0);
                protoOutputStream3.end(start13);
            }
            dumpControllerActivityProto(protoOutputStream3, 1146756268036L, valueAt.getModemControllerActivity(), 0);
            long start14 = protoOutputStream3.start(1146756268049L);
            protoOutputStream3.write(1112396529665L, valueAt.getNetworkActivityBytes(0, 0));
            protoOutputStream3.write(1112396529666L, valueAt.getNetworkActivityBytes(1, 0));
            protoOutputStream3.write(1112396529667L, valueAt.getNetworkActivityBytes(2, 0));
            protoOutputStream3.write(1112396529668L, valueAt.getNetworkActivityBytes(3, 0));
            protoOutputStream3.write(1112396529669L, valueAt.getNetworkActivityBytes(4, 0));
            protoOutputStream3.write(1112396529670L, valueAt.getNetworkActivityBytes(5, 0));
            protoOutputStream3.write(1112396529671L, valueAt.getNetworkActivityPackets(0, 0));
            protoOutputStream3.write(1112396529672L, valueAt.getNetworkActivityPackets(1, 0));
            protoOutputStream3.write(1112396529673L, valueAt.getNetworkActivityPackets(2, 0));
            protoOutputStream3.write(1112396529674L, valueAt.getNetworkActivityPackets(3, 0));
            protoOutputStream3.write(1112396529675L, roundUsToMs(valueAt.getMobileRadioActiveTime(0)));
            protoOutputStream3.write(1120986464268L, valueAt.getMobileRadioActiveCount(0));
            protoOutputStream3.write(1120986464269L, valueAt.getMobileRadioApWakeupCount(0));
            protoOutputStream3.write(1120986464270L, valueAt.getWifiRadioApWakeupCount(0));
            protoOutputStream3.write(1112396529679L, valueAt.getNetworkActivityBytes(6, 0));
            int i24 = 1;
            protoOutputStream3.write(1112396529680L, valueAt.getNetworkActivityBytes(7, 0));
            protoOutputStream3.write(1112396529681L, valueAt.getNetworkActivityBytes(8, 0));
            protoOutputStream3.write(1112396529682L, valueAt.getNetworkActivityBytes(9, 0));
            protoOutputStream3.write(1112396529683L, valueAt.getNetworkActivityPackets(6, 0));
            protoOutputStream3.write(1112396529684L, valueAt.getNetworkActivityPackets(7, 0));
            protoOutputStream3.write(1112396529685L, valueAt.getNetworkActivityPackets(8, 0));
            protoOutputStream3.write(1112396529686L, valueAt.getNetworkActivityPackets(9, 0));
            protoOutputStream3.end(start14);
            SparseArray sparseArray8 = sparseArray5;
            UidBatteryConsumer uidBatteryConsumer2 = (UidBatteryConsumer) sparseArray8.get(keyAt);
            if (uidBatteryConsumer2 != null) {
                long start15 = protoOutputStream3.start(1146756268050L);
                protoOutputStream3.write(1103806595073L, uidBatteryConsumer2.getConsumedPower());
                protoOutputStream3.write(1133871366146L, proportionalAttributionCalculator.isSystemBatteryConsumer(uidBatteryConsumer2));
                protoOutputStream3.write(1103806595075L, uidBatteryConsumer2.getConsumedPower(0));
                protoOutputStream3.write(1103806595076L, proportionalAttributionCalculator.getProportionalPowerMah(uidBatteryConsumer2));
                protoOutputStream3.end(start15);
            }
            ArrayMap<String, ? extends Uid.Proc> processStats = valueAt.getProcessStats();
            for (int size6 = processStats.size() - 1; size6 >= 0; size6--) {
                Uid.Proc valueAt5 = processStats.valueAt(size6);
                long start16 = protoOutputStream3.start(2246267895827L);
                protoOutputStream3.write(1138166333441L, processStats.keyAt(size6));
                protoOutputStream3.write(1112396529666L, valueAt5.getUserTime(0));
                protoOutputStream3.write(1112396529667L, valueAt5.getSystemTime(0));
                protoOutputStream3.write(1112396529668L, valueAt5.getForegroundTime(0));
                protoOutputStream3.write(1120986464261L, valueAt5.getStarts(0));
                protoOutputStream3.write(1120986464262L, valueAt5.getNumAnrs(0));
                protoOutputStream3.write(1120986464263L, valueAt5.getNumCrashes(0));
                protoOutputStream3.end(start16);
            }
            SparseArray<? extends Uid.Sensor> sensorStats = valueAt.getSensorStats();
            int i25 = 0;
            while (i25 < sensorStats.size()) {
                Uid.Sensor valueAt6 = sensorStats.valueAt(i25);
                Timer sensorTime = valueAt6.getSensorTime();
                if (sensorTime == null) {
                    sparseArray = sparseArray8;
                    j4 = j22;
                } else {
                    Timer sensorBackgroundTime = valueAt6.getSensorBackgroundTime();
                    int keyAt3 = sensorStats.keyAt(i25);
                    long start17 = protoOutputStream3.start(2246267895829L);
                    protoOutputStream3.write(1120986464257L, keyAt3);
                    sparseArray = sparseArray8;
                    j4 = j22;
                    dumpTimer(protoOutputStream3, 1146756268034L, sensorTime, j4, 0);
                    protoOutputStream3 = protoOutputStream;
                    dumpTimer(protoOutputStream3, 1146756268035L, sensorBackgroundTime, j4, 0);
                    protoOutputStream3.end(start17);
                }
                i25++;
                j22 = j4;
                sparseArray8 = sparseArray;
            }
            SparseArray sparseArray9 = sparseArray8;
            j12 = j22;
            for (int i26 = 0; i26 < 7; i26++) {
                long roundUsToMs2 = roundUsToMs(valueAt.getProcessStateTime(i26, j12, 0));
                if (roundUsToMs2 != 0) {
                    long start18 = protoOutputStream3.start(2246267895828L);
                    protoOutputStream3.write(1159641169921L, i26);
                    protoOutputStream3.write(1112396529666L, roundUsToMs2);
                    protoOutputStream3.end(start18);
                }
            }
            ArrayMap<String, ? extends Timer> syncStats = valueAt.getSyncStats();
            for (int size7 = syncStats.size() - 1; size7 >= 0; size7--) {
                Timer valueAt7 = syncStats.valueAt(size7);
                Timer subTimer3 = valueAt7.getSubTimer();
                long start19 = protoOutputStream3.start(2246267895830L);
                protoOutputStream3.write(1138166333441L, syncStats.keyAt(size7));
                dumpTimer(protoOutputStream3, 1146756268034L, valueAt7, j12, 0);
                protoOutputStream3 = protoOutputStream;
                dumpTimer(protoOutputStream3, 1146756268035L, subTimer3, j12, 0);
                protoOutputStream3.end(start19);
            }
            if (valueAt.hasUserActivity()) {
                for (int i27 = 0; i27 < Uid.NUM_USER_ACTIVITY_TYPES; i27++) {
                    int userActivityCount = valueAt.getUserActivityCount(i27, 0);
                    if (userActivityCount != 0) {
                        long start20 = protoOutputStream3.start(2246267895831L);
                        protoOutputStream3.write(1159641169921L, i27);
                        protoOutputStream3.write(1120986464258L, userActivityCount);
                        protoOutputStream3.end(start20);
                    }
                }
            }
            dumpTimer(protoOutputStream3, 1146756268045L, valueAt.getVibratorOnTimer(), j12, 0);
            protoOutputStream2 = protoOutputStream;
            dumpTimer(protoOutputStream2, 1146756268046L, valueAt.getVideoTurnedOnTimer(), j12, 0);
            ArrayMap<String, ? extends Uid.Wakelock> wakelockStats = valueAt.getWakelockStats();
            int size8 = wakelockStats.size() - 1;
            while (size8 >= 0) {
                Uid.Wakelock valueAt8 = wakelockStats.valueAt(size8);
                long start21 = protoOutputStream2.start(2246267895833L);
                protoOutputStream2.write(1138166333441L, wakelockStats.keyAt(size8));
                dumpTimer(protoOutputStream2, 1146756268034L, valueAt8.getWakeTime(i24), j12, 0);
                Timer wakeTime = valueAt8.getWakeTime(0);
                if (wakeTime != null) {
                    dumpTimer(protoOutputStream, 1146756268035L, wakeTime, j12, 0);
                    dumpTimer(protoOutputStream, 1146756268036L, wakeTime.getSubTimer(), j12, 0);
                }
                protoOutputStream2 = protoOutputStream;
                dumpTimer(protoOutputStream2, 1146756268037L, valueAt8.getWakeTime(2), j12, 0);
                protoOutputStream2.end(start21);
                size8--;
                i24 = 1;
            }
            dumpTimer(protoOutputStream2, 1146756268060L, valueAt.getMulticastWakelockStats(), j12, 0);
            for (int size9 = arrayMap4.size() - 1; size9 >= 0; size9--) {
                ArrayMap<String, ? extends Counter> wakeupAlarmStats = arrayMap4.valueAt(size9).getWakeupAlarmStats();
                for (int size10 = wakeupAlarmStats.size() - 1; size10 >= 0; size10--) {
                    long start22 = protoOutputStream2.start(2246267895834L);
                    protoOutputStream2.write(1138166333441L, wakeupAlarmStats.keyAt(size10));
                    protoOutputStream2.write(1120986464258L, wakeupAlarmStats.valueAt(size10).getCountLocked(0));
                    protoOutputStream2.end(start22);
                }
            }
            dumpControllerActivityProto(protoOutputStream2, 1146756268037L, valueAt.getWifiControllerActivity(), 0);
            long start23 = protoOutputStream2.start(1146756268059L);
            protoOutputStream2.write(1112396529665L, roundUsToMs(valueAt.getFullWifiLockTime(j12, 0)));
            dumpTimer(protoOutputStream2, 1146756268035L, valueAt.getWifiScanTimer(), j12, 0);
            protoOutputStream2.write(1112396529666L, roundUsToMs(valueAt.getWifiRunningTime(j12, 0)));
            dumpTimer(protoOutputStream2, 1146756268036L, valueAt.getWifiScanBackgroundTimer(), j12, 0);
            protoOutputStream2.end(start23);
            protoOutputStream2.end(j17);
            i10 = i12 + 1;
            sparseArray4 = sparseArray9;
            size2 = i11;
            uidStats = sparseArray6;
            sparseArray3 = sparseArray7;
            batteryUptime = j16;
            elapsedRealtime = j23;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00d5 A[Catch: all -> 0x01bd, TryCatch #1 {all -> 0x01bd, blocks: (B:8:0x0070, B:10:0x0076, B:13:0x007d, B:18:0x008b, B:20:0x0090, B:22:0x0095, B:24:0x009a, B:27:0x00a1, B:29:0x00a7, B:32:0x00b4, B:35:0x00d5, B:37:0x00d9, B:40:0x00e0, B:41:0x00ea, B:44:0x00fa, B:47:0x0165, B:48:0x0103, B:49:0x010b, B:51:0x0111, B:52:0x011f, B:54:0x0125, B:58:0x014e, B:65:0x016c, B:66:0x0179, B:69:0x0180, B:78:0x00c0, B:81:0x00ca, B:89:0x0198), top: B:7:0x0070 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0173  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void dumpProtoHistoryLocked(android.util.proto.ProtoOutputStream r24, int r25, long r26) {
        /*
            Method dump skipped, instructions count: 458
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.BatteryStats.dumpProtoHistoryLocked(android.util.proto.ProtoOutputStream, int, long):void");
    }

    private void dumpProtoSystemLocked(ProtoOutputStream protoOutputStream, BatteryUsageStats batteryUsageStats) {
        boolean z;
        int i;
        ProtoOutputStream protoOutputStream2 = protoOutputStream;
        long start = protoOutputStream2.start(1146756268038L);
        long uptimeMillis = SystemClock.uptimeMillis() * 1000;
        long elapsedRealtime = SystemClock.elapsedRealtime() * 1000;
        long start2 = protoOutputStream2.start(1146756268033L);
        protoOutputStream2.write(1112396529665L, getStartClockTime());
        protoOutputStream2.write(1112396529666L, getStartCount());
        int i2 = 0;
        protoOutputStream2.write(1112396529667L, computeRealtime(elapsedRealtime, 0) / 1000);
        protoOutputStream2.write(1112396529668L, computeUptime(uptimeMillis, 0) / 1000);
        protoOutputStream2.write(1112396529669L, computeBatteryRealtime(elapsedRealtime, 0) / 1000);
        protoOutputStream2.write(1112396529670L, computeBatteryUptime(uptimeMillis, 0) / 1000);
        protoOutputStream2.write(1112396529671L, computeBatteryScreenOffRealtime(elapsedRealtime, 0) / 1000);
        protoOutputStream2.write(1112396529672L, computeBatteryScreenOffUptime(uptimeMillis, 0) / 1000);
        protoOutputStream2.write(1112396529673L, getScreenDozeTime(elapsedRealtime, 0) / 1000);
        protoOutputStream2.write(1112396529674L, getEstimatedBatteryCapacity());
        protoOutputStream2.write(1112396529675L, getMinLearnedBatteryCapacity());
        protoOutputStream2.write(1112396529676L, getMaxLearnedBatteryCapacity());
        protoOutputStream2.end(start2);
        long start3 = protoOutputStream2.start(1146756268034L);
        protoOutputStream2.write(1120986464257L, getLowDischargeAmountSinceCharge());
        protoOutputStream2.write(1120986464258L, getHighDischargeAmountSinceCharge());
        protoOutputStream2.write(1120986464259L, getDischargeAmountScreenOnSinceCharge());
        protoOutputStream2.write(1120986464260L, getDischargeAmountScreenOffSinceCharge());
        protoOutputStream2.write(1120986464261L, getDischargeAmountScreenDozeSinceCharge());
        protoOutputStream2.write(1112396529670L, getUahDischarge(0) / 1000);
        protoOutputStream2.write(1112396529671L, getUahDischargeScreenOff(0) / 1000);
        protoOutputStream2.write(1112396529672L, getUahDischargeScreenDoze(0) / 1000);
        protoOutputStream2.write(1112396529673L, getUahDischargeLightDoze(0) / 1000);
        protoOutputStream2.write(1112396529674L, getUahDischargeDeepDoze(0) / 1000);
        protoOutputStream2.end(start3);
        long computeChargeTimeRemaining = computeChargeTimeRemaining(elapsedRealtime);
        if (computeChargeTimeRemaining >= 0) {
            protoOutputStream2.write(1112396529667L, computeChargeTimeRemaining / 1000);
        } else {
            long computeBatteryTimeRemaining = computeBatteryTimeRemaining(elapsedRealtime);
            if (computeBatteryTimeRemaining >= 0) {
                protoOutputStream2.write(1112396529668L, computeBatteryTimeRemaining / 1000);
            } else {
                protoOutputStream2.write(1112396529668L, -1);
            }
        }
        dumpDurationSteps(protoOutputStream2, 2246267895813L, getChargeLevelStepTracker());
        int i3 = 0;
        while (true) {
            if (i3 >= NUM_DATA_CONNECTION_TYPES) {
                break;
            }
            z = i3 != 0 ? i2 : true;
            int i4 = (i3 == DATA_CONNECTION_OTHER || i3 == DATA_CONNECTION_EMERGENCY_SERVICE) ? i2 : i3;
            long j = elapsedRealtime;
            long start4 = protoOutputStream2.start(2246267895816L);
            if (z) {
                protoOutputStream2.write(1133871366146L, z);
            } else {
                protoOutputStream2.write(1159641169921L, i4);
            }
            elapsedRealtime = j;
            dumpTimer(protoOutputStream2, 1146756268035L, getPhoneDataConnectionTimer(i3), elapsedRealtime, 0);
            protoOutputStream2.end(start4);
            i3++;
            i2 = 0;
        }
        int i5 = i2;
        dumpDurationSteps(protoOutputStream2, 2246267895814L, getDischargeLevelStepTracker());
        CpuScalingPolicies cpuScalingPolicies = getCpuScalingPolicies();
        if (cpuScalingPolicies != null) {
            int[] policies = cpuScalingPolicies.getPolicies();
            int length = policies.length;
            for (int i6 = i5; i6 < length; i6++) {
                int[] frequencies = cpuScalingPolicies.getFrequencies(policies[i6]);
                int length2 = frequencies.length;
                for (int i7 = i5; i7 < length2; i7++) {
                    protoOutputStream2.write(SystemProto.CPU_FREQUENCY, frequencies[i7]);
                }
            }
        }
        dumpControllerActivityProto(protoOutputStream2, 1146756268041L, getBluetoothControllerActivity(), i5);
        dumpControllerActivityProto(protoOutputStream2, 1146756268042L, getModemControllerActivity(), i5);
        long start5 = protoOutputStream2.start(1146756268044L);
        protoOutputStream2.write(1112396529665L, getNetworkActivityBytes(i5, i5));
        protoOutputStream2.write(1112396529666L, getNetworkActivityBytes(1, i5));
        protoOutputStream2.write(1112396529669L, getNetworkActivityPackets(i5, i5));
        protoOutputStream2.write(1112396529670L, getNetworkActivityPackets(1, i5));
        int i8 = 2;
        protoOutputStream2.write(1112396529667L, getNetworkActivityBytes(2, i5));
        protoOutputStream2.write(1112396529668L, getNetworkActivityBytes(3, i5));
        protoOutputStream2.write(1112396529671L, getNetworkActivityPackets(2, i5));
        protoOutputStream2.write(1112396529672L, getNetworkActivityPackets(3, i5));
        protoOutputStream2.write(1112396529673L, getNetworkActivityBytes(4, i5));
        protoOutputStream2.write(1112396529674L, getNetworkActivityBytes(5, i5));
        protoOutputStream2.end(start5);
        dumpControllerActivityProto(protoOutputStream2, 1146756268043L, getWifiControllerActivity(), i5);
        long start6 = protoOutputStream2.start(1146756268045L);
        protoOutputStream2.write(1112396529665L, getWifiOnTime(elapsedRealtime, i5) / 1000);
        protoOutputStream2.write(1112396529666L, getGlobalWifiRunningTime(elapsedRealtime, i5) / 1000);
        protoOutputStream2.end(start6);
        for (Map.Entry<String, ? extends Timer> entry : getKernelWakelockStats().entrySet()) {
            long start7 = protoOutputStream2.start(2246267895822L);
            protoOutputStream2.write(1138166333441L, entry.getKey());
            dumpTimer(protoOutputStream2, 1146756268034L, entry.getValue(), elapsedRealtime, 0);
            protoOutputStream2.end(start7);
            z = z;
        }
        int i9 = z;
        SparseArray<? extends Uid> uidStats = getUidStats();
        long j2 = 0;
        long j3 = 0;
        for (int i10 = i5; i10 < uidStats.size(); i10++) {
            ArrayMap<String, ? extends Uid.Wakelock> wakelockStats = uidStats.valueAt(i10).getWakelockStats();
            for (int size = wakelockStats.size() - i9; size >= 0; size--) {
                Uid.Wakelock valueAt = wakelockStats.valueAt(size);
                Timer wakeTime = valueAt.getWakeTime(i9);
                if (wakeTime != null) {
                    j2 += wakeTime.getTotalTimeLocked(elapsedRealtime, i5);
                }
                Timer wakeTime2 = valueAt.getWakeTime(i5);
                if (wakeTime2 != null) {
                    j3 += wakeTime2.getTotalTimeLocked(elapsedRealtime, i5);
                }
            }
        }
        long start8 = protoOutputStream2.start(1146756268047L);
        protoOutputStream2.write(1112396529665L, getScreenOnTime(elapsedRealtime, i5) / 1000);
        protoOutputStream2.write(1112396529666L, getPhoneOnTime(elapsedRealtime, i5) / 1000);
        protoOutputStream2.write(1112396529667L, j2 / 1000);
        protoOutputStream2.write(1112396529668L, j3 / 1000);
        protoOutputStream2.write(1112396529669L, getMobileRadioActiveTime(elapsedRealtime, i5) / 1000);
        protoOutputStream2.write(1112396529670L, getMobileRadioActiveAdjustedTime(i5) / 1000);
        protoOutputStream2.write(1120986464263L, getMobileRadioActiveCount(i5));
        protoOutputStream2.write(1120986464264L, getMobileRadioActiveUnknownTime(i5) / 1000);
        protoOutputStream2.write(1112396529673L, getInteractiveTime(elapsedRealtime, i5) / 1000);
        protoOutputStream2.write(1112396529674L, getPowerSaveModeEnabledTime(elapsedRealtime, i5) / 1000);
        protoOutputStream2.write(1120986464267L, getNumConnectivityChange(i5));
        protoOutputStream2.write(1112396529676L, getDeviceIdleModeTime(2, elapsedRealtime, i5) / 1000);
        protoOutputStream2.write(1120986464269L, getDeviceIdleModeCount(2, i5));
        protoOutputStream2.write(1112396529678L, getDeviceIdlingTime(2, elapsedRealtime, i5) / 1000);
        protoOutputStream2.write(1120986464271L, getDeviceIdlingCount(2, i5));
        protoOutputStream2.write(1112396529680L, getLongestDeviceIdleModeTime(2));
        protoOutputStream2.write(1112396529681L, getDeviceIdleModeTime(i9, elapsedRealtime, i5) / 1000);
        protoOutputStream2.write(1120986464274L, getDeviceIdleModeCount(i9, i5));
        protoOutputStream2.write(1112396529683L, getDeviceIdlingTime(i9, elapsedRealtime, i5) / 1000);
        protoOutputStream2.write(1120986464276L, getDeviceIdlingCount(i9, i5));
        protoOutputStream2.write(1112396529685L, getLongestDeviceIdleModeTime(i9));
        protoOutputStream2.end(start8);
        long wifiMulticastWakelockTime = getWifiMulticastWakelockTime(elapsedRealtime, i5);
        int wifiMulticastWakelockCount = getWifiMulticastWakelockCount(i5);
        long start9 = protoOutputStream2.start(1146756268055L);
        protoOutputStream2.write(1112396529665L, wifiMulticastWakelockTime / 1000);
        protoOutputStream2.write(1120986464258L, wifiMulticastWakelockCount);
        protoOutputStream2.end(start9);
        AggregateBatteryConsumer aggregateBatteryConsumer = batteryUsageStats.getAggregateBatteryConsumer(i5);
        int i11 = i5;
        while (i11 < 20) {
            if (i11 == 0) {
                i = 7;
            } else if (i11 == 6) {
                i = 6;
            } else if (i11 == 8) {
                i = i8;
            } else if (i11 == 11) {
                i = 4;
            } else if (i11 == i8) {
                i = 5;
            } else if (i11 != 3) {
                switch (i11) {
                    case 13:
                        i = 12;
                        break;
                    case 14:
                        i = 3;
                        break;
                    case 15:
                        i = 13;
                        break;
                    case 16:
                        i = i9;
                        break;
                    default:
                        i = i5;
                        break;
                }
            } else {
                i = 11;
            }
            long start10 = protoOutputStream2.start(2246267895825L);
            protoOutputStream2.write(1159641169921L, i);
            protoOutputStream2.write(1120986464258L, i5);
            protoOutputStream2.write(1103806595075L, aggregateBatteryConsumer.getConsumedPower(i11));
            protoOutputStream2.write(1133871366148L, shouldHidePowerComponent(i11));
            protoOutputStream2.write(1103806595077L, i5);
            protoOutputStream2.write(1103806595078L, i5);
            protoOutputStream2.end(start10);
            i11++;
            i9 = 1;
            i8 = 2;
        }
        long start11 = protoOutputStream2.start(1146756268050L);
        protoOutputStream2.write(1103806595073L, batteryUsageStats.getBatteryCapacity());
        protoOutputStream2.write(1103806595074L, batteryUsageStats.getConsumedPower());
        protoOutputStream2.write(1103806595075L, batteryUsageStats.getDischargedPowerRange().getLower().doubleValue());
        protoOutputStream2.write(1103806595076L, batteryUsageStats.getDischargedPowerRange().getUpper().doubleValue());
        protoOutputStream2.end(start11);
        Map<String, ? extends Timer> rpmStats = getRpmStats();
        Map<String, ? extends Timer> screenOffRpmStats = getScreenOffRpmStats();
        for (Map.Entry<String, ? extends Timer> entry2 : rpmStats.entrySet()) {
            long start12 = protoOutputStream2.start(2246267895827L);
            protoOutputStream2.write(1138166333441L, entry2.getKey());
            dumpTimer(protoOutputStream2, 1146756268034L, entry2.getValue(), elapsedRealtime, 0);
            protoOutputStream2 = protoOutputStream;
            dumpTimer(protoOutputStream2, 1146756268035L, screenOffRpmStats.get(entry2.getKey()), elapsedRealtime, 0);
            protoOutputStream2.end(start12);
        }
        for (int i12 = i5; i12 < 5; i12++) {
            long start13 = protoOutputStream2.start(2246267895828L);
            protoOutputStream2.write(1159641169921L, i12);
            dumpTimer(protoOutputStream2, 1146756268034L, getScreenBrightnessTimer(i12), elapsedRealtime, 0);
            protoOutputStream2.end(start13);
        }
        dumpTimer(protoOutputStream2, 1146756268053L, getPhoneSignalScanningTimer(), elapsedRealtime, 0);
        for (int i13 = i5; i13 < CellSignalStrength.getNumSignalStrengthLevels(); i13++) {
            long start14 = protoOutputStream2.start(2246267895824L);
            protoOutputStream2.write(1159641169921L, i13);
            dumpTimer(protoOutputStream2, 1146756268034L, getPhoneSignalStrengthTimer(i13), elapsedRealtime, 0);
            protoOutputStream2.end(start14);
        }
        for (Map.Entry<String, ? extends Timer> entry3 : getWakeupReasonStats().entrySet()) {
            long start15 = protoOutputStream2.start(2246267895830L);
            protoOutputStream2.write(1138166333441L, entry3.getKey());
            dumpTimer(protoOutputStream2, 1146756268034L, entry3.getValue(), elapsedRealtime, 0);
            protoOutputStream2.end(start15);
        }
        for (int i14 = i5; i14 < 5; i14++) {
            long start16 = protoOutputStream2.start(2246267895832L);
            protoOutputStream2.write(1159641169921L, i14);
            dumpTimer(protoOutputStream2, 1146756268034L, getWifiSignalStrengthTimer(i14), elapsedRealtime, 0);
            protoOutputStream2.end(start16);
        }
        for (int i15 = i5; i15 < 8; i15++) {
            long start17 = protoOutputStream2.start(2246267895833L);
            protoOutputStream2.write(1159641169921L, i15);
            dumpTimer(protoOutputStream2, 1146756268034L, getWifiStateTimer(i15), elapsedRealtime, 0);
            protoOutputStream2.end(start17);
        }
        while (i5 < 13) {
            long start18 = protoOutputStream2.start(2246267895834L);
            protoOutputStream2.write(1159641169921L, i5);
            dumpTimer(protoOutputStream2, 1146756268034L, getWifiSupplStateTimer(i5), elapsedRealtime, 0);
            protoOutputStream2.end(start18);
            i5++;
        }
        protoOutputStream2.end(start);
    }

    public static boolean checkWifiOnly(Context context) {
        if (((TelephonyManager) context.getSystemService(TelephonyManager.class)) == null) {
            return false;
        }
        return !r1.isDataCapable();
    }

    private static class ProportionalAttributionCalculator {
        private static final double SYSTEM_BATTERY_CONSUMER = -1.0d;
        private final PackageManager mPackageManager;
        private final SparseDoubleArray mProportionalPowerMah;
        private final HashSet<String> mSystemAndServicePackages;

        ProportionalAttributionCalculator(Context context, BatteryUsageStats batteryUsageStats) {
            this.mPackageManager = context.getPackageManager();
            Resources resources = context.getResources();
            String[] stringArray = resources.getStringArray(R.array.config_batteryPackageTypeSystem);
            String[] stringArray2 = resources.getStringArray(R.array.config_batteryPackageTypeService);
            this.mSystemAndServicePackages = new HashSet<>(stringArray.length + stringArray2.length);
            for (String str : stringArray) {
                this.mSystemAndServicePackages.add(str);
            }
            for (String str2 : stringArray2) {
                this.mSystemAndServicePackages.add(str2);
            }
            List<UidBatteryConsumer> uidBatteryConsumers = batteryUsageStats.getUidBatteryConsumers();
            this.mProportionalPowerMah = new SparseDoubleArray(uidBatteryConsumers.size());
            double d = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            for (int size = uidBatteryConsumers.size() - 1; size >= 0; size--) {
                UidBatteryConsumer uidBatteryConsumer = uidBatteryConsumers.get(size);
                int uid = uidBatteryConsumer.getUid();
                if (isSystemUid(uid)) {
                    this.mProportionalPowerMah.put(uid, -1.0d);
                    d += uidBatteryConsumer.getConsumedPower();
                }
            }
            double consumedPower = batteryUsageStats.getConsumedPower() - d;
            if (Math.abs(consumedPower) > 0.001d) {
                for (int size2 = uidBatteryConsumers.size() - 1; size2 >= 0; size2--) {
                    UidBatteryConsumer uidBatteryConsumer2 = uidBatteryConsumers.get(size2);
                    int uid2 = uidBatteryConsumer2.getUid();
                    if (this.mProportionalPowerMah.get(uid2) != -1.0d) {
                        double consumedPower2 = uidBatteryConsumer2.getConsumedPower();
                        this.mProportionalPowerMah.put(uid2, consumedPower2 + ((d * consumedPower2) / consumedPower));
                    }
                }
            }
        }

        boolean isSystemBatteryConsumer(UidBatteryConsumer uidBatteryConsumer) {
            return this.mProportionalPowerMah.get(uidBatteryConsumer.getUid()) < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        }

        double getProportionalPowerMah(UidBatteryConsumer uidBatteryConsumer) {
            double d = this.mProportionalPowerMah.get(uidBatteryConsumer.getUid());
            return d >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? d : SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        }

        private boolean isSystemUid(int i) {
            if (i >= 0 && i < 10000) {
                return true;
            }
            String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
            if (packagesForUid == null) {
                return false;
            }
            for (String str : packagesForUid) {
                if (this.mSystemAndServicePackages.contains(str)) {
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class UidMobileRadioStats {
        public final double millisecondsPerPacket;
        public final int radioActiveCount;
        public final long radioActiveMs;
        public final long rxPackets;
        public final long txPackets;
        public final int uid;

        private UidMobileRadioStats(int i, long j, long j2, long j3, int i2, double d) {
            this.uid = i;
            this.txPackets = j2;
            this.rxPackets = j;
            this.radioActiveMs = j3;
            this.radioActiveCount = i2;
            this.millisecondsPerPacket = d;
        }
    }

    private List<UidMobileRadioStats> getUidMobileRadioStats(List<UidBatteryConsumer> list) {
        int uid;
        Uid uid2;
        int i;
        SparseArray<? extends Uid> uidStats = getUidStats();
        ArrayList newArrayList = Lists.newArrayList();
        int i2 = 0;
        int i3 = 0;
        while (i3 < list.size()) {
            UidBatteryConsumer uidBatteryConsumer = list.get(i3);
            if (uidBatteryConsumer.getConsumedPower(8) != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && (uid2 = uidStats.get((uid = uidBatteryConsumer.getUid()))) != null) {
                long networkActivityPackets = uid2.getNetworkActivityPackets(i2, i2);
                long networkActivityPackets2 = uid2.getNetworkActivityPackets(1, i2);
                if (networkActivityPackets != 0 || networkActivityPackets2 != 0) {
                    long mobileRadioActiveTime = uid2.getMobileRadioActiveTime(i2) / 1000;
                    int mobileRadioActiveCount = uid2.getMobileRadioActiveCount(i2);
                    i = i3;
                    double d = mobileRadioActiveTime / (networkActivityPackets + networkActivityPackets2);
                    if (d != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                        newArrayList.add(new UidMobileRadioStats(uid, networkActivityPackets, networkActivityPackets2, mobileRadioActiveTime, mobileRadioActiveCount, d));
                    }
                    i3 = i + 1;
                    i2 = 0;
                }
            }
            i = i3;
            i3 = i + 1;
            i2 = 0;
        }
        newArrayList.sort(new Comparator() { // from class: android.os.BatteryStats$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int compare;
                compare = Double.compare(((BatteryStats.UidMobileRadioStats) obj2).millisecondsPerPacket, ((BatteryStats.UidMobileRadioStats) obj).millisecondsPerPacket);
                return compare;
            }
        });
        return newArrayList;
    }

    protected static boolean isLowRamDevice() {
        return ActivityManager.isLowRamDeviceStatic();
    }

    protected static int getCellSignalStrengthLevelCount() {
        return CellSignalStrength.getNumSignalStrengthLevels();
    }

    protected static int getModemTxPowerLevelCount() {
        return ModemActivityInfo.getNumTxPowerLevels();
    }

    protected static int getDisplayTransport(int[] iArr) {
        return NetworkCapabilitiesUtils.getDisplayTransport(iArr);
    }

    protected static int getDisplayTransport$ravenwood(int[] iArr) {
        for (int i : DISPLAY_TRANSPORT_PRIORITIES) {
            for (int i2 : iArr) {
                if (i2 == i) {
                    return i;
                }
            }
        }
        return iArr[0];
    }

    void printLatestBackupData(PrintWriter printWriter) {
        File[] listFiles;
        File file = new File("/data/log/batterystats/");
        if (!file.exists() || (listFiles = file.listFiles()) == null) {
            return;
        }
        long j = 0;
        for (File file2 : listFiles) {
            long parseLong = Long.parseLong(file2.getAbsolutePath().replace("/data/log/batterystats/newbatterystats", ""));
            if (parseLong > j) {
                j = parseLong;
            }
        }
        if (j <= 0) {
            return;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream("/data/log/batterystats/newbatterystats" + j);
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                try {
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    try {
                        printWriter.println("\nLatest newbatterystats:");
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine != null) {
                                printWriter.println(readLine);
                            } else {
                                printWriter.println();
                                bufferedReader.close();
                                inputStreamReader.close();
                                fileInputStream.close();
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

    private String getScaledCpuFreqTimes(CpuScalingPolicies cpuScalingPolicies, long[] jArr) {
        String str = "";
        int i = 0;
        for (int i2 : cpuScalingPolicies.getPolicies()) {
            str = str + "\n      ";
            for (int i3 = 0; i3 < cpuScalingPolicies.getFrequencies(i2).length; i3++) {
                str = str + " " + jArr[i + i3];
            }
            i += cpuScalingPolicies.getFrequencies(i2).length;
        }
        return str;
    }
}
