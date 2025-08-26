package android.os;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.backup.FullBackup;
import android.app.blob.XmlTags;
import android.app.job.JobParameters;
import android.app.slice.Slice;
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
import android.provider.Settings;
import android.security.Credentials;
import android.service.timezone.TimeZoneProviderService;
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
import com.samsung.android.core.CoreSaConstant;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.wallpaperbackup.GenerateXML;
import com.samsung.android.wifi.p2p.SemWifiP2pManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
            char cCharAt;
            int i3;
            char cCharAt2;
            long j;
            int length = str.length();
            int i4 = 0;
            long j2 = 0;
            while (i4 < length && (cCharAt2 = str.charAt(i4)) != '-') {
                i4++;
                if (cCharAt2 == 'D') {
                    j = 144115188075855872L;
                } else if (cCharAt2 != 'F') {
                    if (cCharAt2 == 'I') {
                        j = 576460752303423488L;
                    } else if (cCharAt2 == 'Z') {
                        j = 216172782113783808L;
                    } else if (cCharAt2 == 'd') {
                        j = 562949953421312L;
                    } else if (cCharAt2 != 'f') {
                        if (cCharAt2 == 'i') {
                            j = FrontendInnerFec.FEC_135_180;
                        } else if (cCharAt2 == 'z') {
                            j = 844424930131968L;
                        } else if (cCharAt2 == 'O') {
                            j = 72057594037927936L;
                        } else if (cCharAt2 == 'P') {
                            j = 288230376151711744L;
                        } else if (cCharAt2 == 'o') {
                            j = 281474976710656L;
                        } else if (cCharAt2 == 'p') {
                            j = FrontendInnerFec.FEC_132_180;
                        }
                    }
                }
                j2 |= j;
            }
            int i5 = i4 + 1;
            long j3 = 0;
            while (i5 < length && (cCharAt = str.charAt(i5)) != '-') {
                i5++;
                j3 <<= 4;
                if (cCharAt >= '0' && cCharAt <= '9') {
                    i3 = cCharAt - '0';
                } else if (cCharAt >= 'a' && cCharAt <= 'f') {
                    i3 = cCharAt - 'W';
                } else if (cCharAt >= 'A' && cCharAt <= 'F') {
                    i3 = cCharAt - '7';
                }
                j3 += i3;
            }
            int i6 = i5 + 1;
            long j4 = j2 | ((j3 << 40) & BatteryStats.STEP_LEVEL_LEVEL_MASK);
            long j5 = 0;
            while (i6 < length) {
                char cCharAt3 = str.charAt(i6);
                if (cCharAt3 == '-') {
                    break;
                }
                i6++;
                j5 <<= 4;
                if (cCharAt3 >= '0' && cCharAt3 <= '9') {
                    i2 = cCharAt3 - '0';
                } else if (cCharAt3 >= 'a' && cCharAt3 <= 'f') {
                    i2 = cCharAt3 - 'W';
                } else if (cCharAt3 >= 'A' && cCharAt3 <= 'F') {
                    i2 = cCharAt3 - '7';
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
            int length = this.mNumStepDurations;
            long j3 = this.mLastStepTime;
            if (j3 >= 0 && i > 0) {
                long[] jArr = this.mStepDurations;
                long j4 = j2 - j3;
                for (int i2 = 0; i2 < i; i2++) {
                    System.arraycopy(jArr, 0, jArr, 1, jArr.length - 1);
                    long j5 = j4 / (i - i2);
                    j4 -= j5;
                    if (j5 > BatteryStats.STEP_LEVEL_TIME_MASK) {
                        j5 = 1099511627775L;
                    }
                    jArr[0] = j5 | j;
                }
                length += i;
                if (length > jArr.length) {
                    length = jArr.length;
                }
            }
            this.mNumStepDurations = length;
            this.mLastStepTime = j2;
        }

        public void readFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            if (i > this.mStepDurations.length) {
                throw new ParcelFormatException("more step durations than available: " + i);
            }
            this.mNumStepDurations = i;
            for (int i2 = 0; i2 < i; i2++) {
                this.mStepDurations[i2] = parcel.readLong();
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
            int i = parcel.readInt();
            int i2 = (PROC_STATE_MASK & i) >>> PROC_STATE_SHIFT;
            this.processState = i2;
            if (i2 >= 5) {
                Slog.e(BatteryStats.TAG, "Unrecognized proc state in battery history: " + this.processState);
                this.processState = 0;
            }
            if ((Integer.MIN_VALUE & i) == 0) {
                this.uid = (-2130706433) & i;
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
            int iDataPosition = parcel.dataPosition();
            this.time = parcel.readLong();
            int i = parcel.readInt();
            this.cmd = (byte) (i & 255);
            this.batteryLevel = (byte) ((i >> 8) & 255);
            this.batteryStatus = (byte) ((i >> 16) & 15);
            this.batteryHealth = (byte) ((i >> 20) & 15);
            this.batteryPlugType = (byte) ((i >> 24) & 15);
            int i2 = parcel.readInt();
            this.batteryTemperature = (short) (i2 & 65535);
            this.batteryVoltage = (short) ((i2 >> 16) & 65535);
            int i3 = parcel.readInt();
            this.current = (short) (65535 & i3);
            this.ap_temp = (byte) ((i3 >> 16) & 255);
            this.pa_temp = (byte) ((i3 >> 24) & 255);
            int i4 = parcel.readInt();
            this.sub_batt_temp = (byte) ((i4 >> 8) & 255);
            this.skin_temp = (byte) ((i4 >> 16) & 255);
            this.wifi_ap = (byte) ((i4 >> 25) & 1);
            this.otgOnline = (byte) ((i4 >> 26) & 1);
            this.highSpeakerVolume = (byte) ((i4 >> 27) & 1);
            this.subScreenOn = (byte) ((i4 >> 28) & 1);
            this.subScreenDoze = (byte) ((i4 >> 29) & 1);
            int i5 = parcel.readInt();
            this.batterySecTxShareEvent = 16777215 & i5;
            this.batterySecOnline = (byte) ((i5 >> 24) & 255);
            this.batterySecCurrentEvent = parcel.readInt();
            this.batterySecEvent = parcel.readInt();
            this.protectBatteryMode = parcel.readInt();
            this.batteryChargeUah = parcel.readInt();
            this.modemRailChargeMah = parcel.readDouble();
            this.wifiRailChargeMah = parcel.readDouble();
            this.states = parcel.readInt();
            this.states2 = parcel.readInt();
            if ((268435456 & i) != 0) {
                HistoryTag historyTag = this.localWakelockTag;
                this.wakelockTag = historyTag;
                historyTag.readFromParcel(parcel);
            } else {
                this.wakelockTag = null;
            }
            if ((536870912 & i) != 0) {
                HistoryTag historyTag2 = this.localWakeReasonTag;
                this.wakeReasonTag = historyTag2;
                historyTag2.readFromParcel(parcel);
            } else {
                this.wakeReasonTag = null;
            }
            if ((i & 1073741824) != 0) {
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
            this.numReadInts += (parcel.dataPosition() - iDataPosition) / 4;
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
            int iIndexOfKey;
            if ((32768 & i) == 0) {
                if ((i & 16384) == 0) {
                    return true;
                }
                HashMap<String, SparseIntArray> map = this.mActiveEvents[i & HistoryItem.EVENT_TYPE_MASK];
                if (map == null || (sparseIntArray = map.get(str)) == null || (iIndexOfKey = sparseIntArray.indexOfKey(i2)) < 0) {
                    return false;
                }
                sparseIntArray.removeAt(iIndexOfKey);
                if (sparseIntArray.size() > 0) {
                    return true;
                }
                map.remove(str);
                return true;
            }
            int i4 = i & HistoryItem.EVENT_TYPE_MASK;
            HashMap<String, SparseIntArray> map2 = this.mActiveEvents[i4];
            if (map2 == null) {
                map2 = new HashMap<>();
                this.mActiveEvents[i4] = map2;
            }
            SparseIntArray sparseIntArray2 = map2.get(str);
            if (sparseIntArray2 == null) {
                sparseIntArray2 = new SparseIntArray();
                map2.put(str, sparseIntArray2);
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
            long jComputeWakeLock = computeWakeLock(timer, j, i);
            int countLocked = timer.getCountLocked(i);
            if (jComputeWakeLock != 0) {
                sb.append(str2);
                formatTimeMs(sb, jComputeWakeLock);
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
                if (totalDurationMsLocked > jComputeWakeLock) {
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
        long totalTimeLocked;
        int countLocked;
        long totalDurationMsLocked;
        long currentDurationMsLocked;
        long maxDurationMsLocked;
        String str3;
        if (timer != null) {
            totalTimeLocked = timer.getTotalTimeLocked(j, i);
            countLocked = timer.getCountLocked(i);
            long j2 = j / 1000;
            currentDurationMsLocked = timer.getCurrentDurationMsLocked(j2);
            maxDurationMsLocked = timer.getMaxDurationMsLocked(j2);
            totalDurationMsLocked = timer.getTotalDurationMsLocked(j2);
        } else {
            totalTimeLocked = 0;
            countLocked = 0;
            totalDurationMsLocked = 0;
            currentDurationMsLocked = 0;
            maxDurationMsLocked = 0;
        }
        sb.append(str2);
        sb.append((totalTimeLocked + 500) / 1000);
        sb.append(',');
        if (str != null) {
            str3 = str + ",";
        } else {
            str3 = "";
        }
        sb.append(str3);
        sb.append(countLocked);
        sb.append(',');
        sb.append(currentDurationMsLocked);
        sb.append(',');
        sb.append(maxDurationMsLocked);
        if (str != null) {
            sb.append(',');
            sb.append(totalDurationMsLocked);
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
            long jRoundUsToMs = roundUsToMs(timer.getTotalTimeLocked(j, i2));
            int countLocked = timer.getCountLocked(i2);
            if (jRoundUsToMs == 0 && countLocked == 0) {
                return;
            }
            dumpLine(printWriter, i, str, str2, Long.valueOf(jRoundUsToMs), Integer.valueOf(countLocked));
        }
    }

    private static void dumpTimer(ProtoOutputStream protoOutputStream, long j, Timer timer, long j2, int i) {
        if (timer == null) {
            return;
        }
        long jRoundUsToMs = roundUsToMs(timer.getTotalTimeLocked(j2, i));
        int countLocked = timer.getCountLocked(i);
        long j3 = j2 / 1000;
        long maxDurationMsLocked = timer.getMaxDurationMsLocked(j3);
        long currentDurationMsLocked = timer.getCurrentDurationMsLocked(j3);
        long totalDurationMsLocked = timer.getTotalDurationMsLocked(j3);
        if (jRoundUsToMs == 0 && countLocked == 0 && maxDurationMsLocked == -1 && currentDurationMsLocked == -1 && totalDurationMsLocked == -1) {
            return;
        }
        long jStart = protoOutputStream.start(j);
        protoOutputStream.write(1112396529665L, jRoundUsToMs);
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
        protoOutputStream.end(jStart);
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
            long jStart = protoOutputStream.start(j);
            protoOutputStream.write(1112396529665L, controllerActivityCounter.getIdleTimeCounter().getCountLocked(i));
            protoOutputStream.write(1112396529666L, controllerActivityCounter.getRxTimeCounter().getCountLocked(i));
            protoOutputStream.write(1112396529667L, controllerActivityCounter.getPowerCounter().getCountLocked(i) / 3600000.0d);
            protoOutputStream.write(1103806595077L, controllerActivityCounter.getMonitoredRailChargeConsumedMaMs().getCountLocked(i) / 3600000.0d);
            LongCounter[] txTimeCounters = controllerActivityCounter.getTxTimeCounters();
            for (int i2 = 0; i2 < txTimeCounters.length; i2++) {
                LongCounter longCounter = txTimeCounters[i2];
                long jStart2 = protoOutputStream.start(2246267895812L);
                protoOutputStream.write(1120986464257L, i2);
                protoOutputStream.write(1112396529666L, longCounter.getCountLocked(i));
                protoOutputStream.end(jStart2);
            }
            protoOutputStream.end(jStart);
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
        long jComputeBatteryRealtime = computeBatteryRealtime(SystemClock.elapsedRealtime() * 1000, i) / 1000;
        LongCounter[] txTimeCounters = controllerActivityCounter.getTxTimeCounters();
        long countLocked5 = 0;
        int i2 = 0;
        for (int length = txTimeCounters.length; i2 < length; length = length) {
            countLocked5 += txTimeCounters[i2].getCountLocked(i);
            i2++;
        }
        if (str2.equals(WIFI_CONTROLLER_NAME)) {
            j = countLocked2;
            long countLocked6 = controllerActivityCounter.getScanTimeCounter().getCountLocked(i);
            sb.setLength(0);
            sb.append(str);
            sb.append("     ");
            sb.append(str2);
            sb.append(" Scan time:  ");
            formatTimeMs(sb, countLocked6);
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(formatRatioLocked(countLocked6, jComputeBatteryRealtime));
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
            printWriter.println(sb.toString());
            long j2 = jComputeBatteryRealtime - ((countLocked + j) + countLocked5);
            sb.setLength(0);
            sb.append(str);
            sb.append("     ");
            sb.append(str2);
            sb.append(" Sleep time:  ");
            formatTimeMs(sb, j2);
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(formatRatioLocked(j2, jComputeBatteryRealtime));
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
            printWriter.println(sb.toString());
        } else {
            j = countLocked2;
        }
        if (!str2.equals("Cellular")) {
            obj = "Cellular";
        } else {
            long countLocked7 = controllerActivityCounter.getSleepTimeCounter().getCountLocked(i);
            obj = "Cellular";
            sb.setLength(0);
            sb.append(str);
            sb.append("     ");
            sb.append(str2);
            sb.append(" Sleep time:  ");
            formatTimeMs(sb, countLocked7);
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(formatRatioLocked(countLocked7, jComputeBatteryRealtime));
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
        sb.append(formatRatioLocked(countLocked, jComputeBatteryRealtime));
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        printWriter.println(sb.toString());
        sb.setLength(0);
        sb.append(str);
        sb.append("     ");
        sb.append(str2);
        sb.append(" Rx time:     ");
        long j3 = j;
        formatTimeMs(sb, j3);
        sb.append(NavigationBarInflaterView.KEY_CODE_START);
        sb.append(formatRatioLocked(j3, jComputeBatteryRealtime));
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
        int iMin = Math.min(controllerActivityCounter.getTxTimeCounters().length, strArr.length);
        if (iMin > 1) {
            printWriter.println(sb.toString());
            int i3 = 0;
            while (i3 < iMin) {
                String[] strArr2 = strArr;
                long countLocked8 = controllerActivityCounter.getTxTimeCounters()[i3].getCountLocked(i);
                sb.setLength(0);
                sb.append(str);
                sb.append("    ");
                sb.append(strArr2[i3]);
                sb.append(" ");
                formatTimeMs(sb, countLocked8);
                sb.append(NavigationBarInflaterView.KEY_CODE_START);
                sb.append(formatRatioLocked(countLocked8, jComputeBatteryRealtime));
                sb.append(NavigationBarInflaterView.KEY_CODE_END);
                printWriter.println(sb.toString());
                i3++;
                strArr = strArr2;
                iMin = iMin;
            }
        } else {
            long countLocked9 = controllerActivityCounter.getTxTimeCounters()[0].getCountLocked(i);
            formatTimeMs(sb, countLocked9);
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(formatRatioLocked(countLocked9, jComputeBatteryRealtime));
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

    /* JADX WARN: Removed duplicated region for block: B:210:0x09b2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dumpCheckinLocked(Context context, PrintWriter printWriter, int i, int i2, boolean z, BatteryStatsDumpHelper batteryStatsDumpHelper) {
        long j;
        PrintWriter printWriter2;
        StringBuilder sb;
        String str;
        long j2;
        CpuScalingPolicies cpuScalingPolicies;
        long j3;
        int i3;
        int i4;
        int i5;
        String str2;
        char c;
        int i6;
        String str3;
        StringBuilder sb2;
        String str4;
        long j4;
        PrintWriter printWriter3;
        Integer num;
        long j5;
        Integer num2;
        ArrayMap<String, ? extends Uid.Pkg> arrayMap;
        Uid uid;
        ArrayMap<String, ? extends Uid.Proc> arrayMap2;
        Integer num3;
        long j6;
        ArrayMap<String, ? extends Timer> arrayMap3;
        long j7;
        long totalDurationMsLocked;
        ArrayMap<String, ? extends Timer> arrayMap4;
        int i7;
        String str5;
        BatteryStats batteryStats = this;
        int i8 = i2;
        Integer num4 = 0;
        if (i != 0) {
            dumpLine(printWriter, 0, STAT_NAMES[i], Notification.CATEGORY_ERROR, "ERROR: BatteryStats.dumpCheckin called for which type " + i + " but only STATS_SINCE_CHARGED is supported.");
            return;
        }
        long jUptimeMillis = SystemClock.uptimeMillis() * 1000;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long j8 = jElapsedRealtime * 1000;
        long batteryUptime = batteryStats.getBatteryUptime(jUptimeMillis);
        long jComputeBatteryUptime = batteryStats.computeBatteryUptime(jUptimeMillis, i);
        long jComputeBatteryRealtime = batteryStats.computeBatteryRealtime(j8, i);
        long jComputeBatteryScreenOffUptime = batteryStats.computeBatteryScreenOffUptime(jUptimeMillis, i);
        long jComputeBatteryScreenOffRealtime = batteryStats.computeBatteryScreenOffRealtime(j8, i);
        long jComputeRealtime = batteryStats.computeRealtime(j8, i);
        long jComputeUptime = batteryStats.computeUptime(jUptimeMillis, i);
        long screenOnTime = batteryStats.getScreenOnTime(j8, i);
        long screenDozeTime = batteryStats.getScreenDozeTime(j8, i);
        long interactiveTime = batteryStats.getInteractiveTime(j8, i);
        long powerSaveModeEnabledTime = batteryStats.getPowerSaveModeEnabledTime(j8, i);
        long deviceIdleModeTime = batteryStats.getDeviceIdleModeTime(1, j8, i);
        long deviceIdleModeTime2 = batteryStats.getDeviceIdleModeTime(2, j8, i);
        long deviceIdlingTime = batteryStats.getDeviceIdlingTime(1, j8, i);
        long deviceIdlingTime2 = batteryStats.getDeviceIdlingTime(2, j8, i);
        int numConnectivityChange = batteryStats.getNumConnectivityChange(i);
        long phoneOnTime = batteryStats.getPhoneOnTime(j8, i);
        long uahDischarge = batteryStats.getUahDischarge(i);
        long uahDischargeScreenOff = batteryStats.getUahDischargeScreenOff(i);
        long uahDischargeScreenDoze = batteryStats.getUahDischargeScreenDoze(i);
        long uahDischargeLightDoze = batteryStats.getUahDischargeLightDoze(i);
        long uahDischargeDeepDoze = batteryStats.getUahDischargeDeepDoze(i);
        int i9 = 1;
        StringBuilder sb3 = new StringBuilder(128);
        SparseArray<? extends Uid> uidStats = batteryStats.getUidStats();
        int size = uidStats.size();
        String str6 = STAT_NAMES[i];
        Object[] objArr = {i == 0 ? Integer.valueOf(batteryStats.getStartCount()) : "N/A", Long.valueOf(jComputeBatteryRealtime / 1000), Long.valueOf(jComputeBatteryUptime / 1000), Long.valueOf(jComputeRealtime / 1000), Long.valueOf(jComputeUptime / 1000), Long.valueOf(batteryStats.getStartClockTime()), Long.valueOf(jComputeBatteryScreenOffRealtime / 1000), Long.valueOf(jComputeBatteryScreenOffUptime / 1000), Integer.valueOf(batteryStats.getEstimatedBatteryCapacity()), Integer.valueOf(batteryStats.getMinLearnedBatteryCapacity()), Integer.valueOf(batteryStats.getMaxLearnedBatteryCapacity()), Long.valueOf(screenDozeTime / 1000)};
        long j9 = jElapsedRealtime;
        dumpLine(printWriter, 0, str6, BATTERY_DATA, objArr);
        long totalTimeLocked = 0;
        long totalTimeLocked2 = 0;
        int i10 = 0;
        while (i10 < size) {
            ArrayMap<String, ? extends Uid.Wakelock> wakelockStats = uidStats.valueAt(i10).getWakelockStats();
            SparseArray<? extends Uid> sparseArray = uidStats;
            int size2 = wakelockStats.size() - 1;
            while (size2 >= 0) {
                int i11 = size2;
                Uid.Wakelock wakelockValueAt = wakelockStats.valueAt(size2);
                int i12 = i10;
                ArrayMap<String, ? extends Uid.Wakelock> arrayMap5 = wakelockStats;
                Timer wakeTime = wakelockValueAt.getWakeTime(i9);
                if (wakeTime != null) {
                    totalTimeLocked += wakeTime.getTotalTimeLocked(j8, i);
                }
                Timer wakeTime2 = wakelockValueAt.getWakeTime(0);
                if (wakeTime2 != null) {
                    totalTimeLocked2 += wakeTime2.getTotalTimeLocked(j8, i);
                }
                size2 = i11 - 1;
                i10 = i12;
                wakelockStats = arrayMap5;
                i9 = 1;
            }
            i10++;
            uidStats = sparseArray;
            i9 = 1;
        }
        dumpLine(printWriter, 0, str6, GLOBAL_NETWORK_DATA, Long.valueOf(batteryStats.getNetworkActivityBytes(0, i)), Long.valueOf(batteryStats.getNetworkActivityBytes(1, i)), Long.valueOf(batteryStats.getNetworkActivityBytes(2, i)), Long.valueOf(batteryStats.getNetworkActivityBytes(3, i)), Long.valueOf(batteryStats.getNetworkActivityPackets(0, i)), Long.valueOf(batteryStats.getNetworkActivityPackets(1, i)), Long.valueOf(batteryStats.getNetworkActivityPackets(2, i)), Long.valueOf(batteryStats.getNetworkActivityPackets(3, i)), Long.valueOf(batteryStats.getNetworkActivityBytes(4, i)), Long.valueOf(batteryStats.getNetworkActivityBytes(5, i)));
        SparseArray<? extends Uid> sparseArray2 = uidStats;
        StringBuilder sb4 = sb3;
        dumpControllerActivityLine(printWriter, 0, str6, GLOBAL_MODEM_CONTROLLER_DATA, batteryStats.getModemControllerActivity(), i);
        dumpLine(printWriter, 0, str6, GLOBAL_WIFI_DATA, Long.valueOf(batteryStats.getWifiOnTime(j8, i) / 1000), Long.valueOf(batteryStats.getGlobalWifiRunningTime(j8, i) / 1000), num4, num4, num4);
        dumpControllerActivityLine(printWriter, 0, str6, GLOBAL_WIFI_CONTROLLER_DATA, batteryStats.getWifiControllerActivity(), i);
        PrintWriter printWriter4 = printWriter;
        int i13 = i;
        dumpControllerActivityLine(printWriter4, 0, str6, GLOBAL_BLUETOOTH_CONTROLLER_DATA, batteryStats.getBluetoothControllerActivity(), i13);
        String str7 = str6;
        dumpLine(printWriter4, 0, str7, MISC_DATA, Long.valueOf(screenOnTime / 1000), Long.valueOf(phoneOnTime / 1000), Long.valueOf(totalTimeLocked / 1000), Long.valueOf(totalTimeLocked2 / 1000), Long.valueOf(batteryStats.getMobileRadioActiveTime(j8, i13) / 1000), Long.valueOf(batteryStats.getMobileRadioActiveAdjustedTime(i13) / 1000), Long.valueOf(interactiveTime / 1000), Long.valueOf(powerSaveModeEnabledTime / 1000), Integer.valueOf(numConnectivityChange), Long.valueOf(deviceIdleModeTime2 / 1000), Integer.valueOf(batteryStats.getDeviceIdleModeCount(2, i13)), Long.valueOf(deviceIdlingTime2 / 1000), Integer.valueOf(batteryStats.getDeviceIdlingCount(2, i13)), Integer.valueOf(batteryStats.getMobileRadioActiveCount(i13)), Long.valueOf(batteryStats.getMobileRadioActiveUnknownTime(i13) / 1000), Long.valueOf(deviceIdleModeTime / 1000), Integer.valueOf(batteryStats.getDeviceIdleModeCount(1, i13)), Long.valueOf(deviceIdlingTime / 1000), Integer.valueOf(batteryStats.getDeviceIdlingCount(1, i13)), Long.valueOf(batteryStats.getLongestDeviceIdleModeTime(1)), Long.valueOf(batteryStats.getLongestDeviceIdleModeTime(2)));
        Object[] objArr2 = new Object[5];
        for (int i14 = 0; i14 < 5; i14++) {
            objArr2[i14] = Long.valueOf(batteryStats.getScreenBrightnessTime(i14, j8, i13) / 1000);
        }
        dumpLine(printWriter4, 0, str7, "br", objArr2);
        Object[] objArr3 = new Object[CellSignalStrength.getNumSignalStrengthLevels()];
        for (int i15 = 0; i15 < CellSignalStrength.getNumSignalStrengthLevels(); i15++) {
            objArr3[i15] = Long.valueOf(batteryStats.getPhoneSignalStrengthTime(i15, j8, i13) / 1000);
        }
        dumpLine(printWriter4, 0, str7, SIGNAL_STRENGTH_TIME_DATA, objArr3);
        dumpLine(printWriter4, 0, str7, SIGNAL_SCANNING_TIME_DATA, Long.valueOf(batteryStats.getPhoneSignalScanningTime(j8, i13) / 1000));
        for (int i16 = 0; i16 < CellSignalStrength.getNumSignalStrengthLevels(); i16++) {
            objArr3[i16] = Integer.valueOf(batteryStats.getPhoneSignalStrengthCount(i16, i13));
        }
        dumpLine(printWriter4, 0, str7, SIGNAL_STRENGTH_COUNT_DATA, objArr3);
        Object[] objArr4 = new Object[NUM_DATA_CONNECTION_TYPES];
        for (int i17 = 0; i17 < NUM_DATA_CONNECTION_TYPES; i17++) {
            objArr4[i17] = Long.valueOf(batteryStats.getPhoneDataConnectionTime(i17, j8, i13) / 1000);
        }
        dumpLine(printWriter4, 0, str7, DATA_CONNECTION_TIME_DATA, objArr4);
        for (int i18 = 0; i18 < NUM_DATA_CONNECTION_TYPES; i18++) {
            objArr4[i18] = Integer.valueOf(batteryStats.getPhoneDataConnectionCount(i18, i13));
        }
        dumpLine(printWriter4, 0, str7, DATA_CONNECTION_COUNT_DATA, objArr4);
        Object[] objArr5 = new Object[8];
        for (int i19 = 0; i19 < 8; i19++) {
            objArr5[i19] = Long.valueOf(batteryStats.getWifiStateTime(i19, j8, i13) / 1000);
        }
        dumpLine(printWriter4, 0, str7, WIFI_STATE_TIME_DATA, objArr5);
        for (int i20 = 0; i20 < 8; i20++) {
            objArr5[i20] = Integer.valueOf(batteryStats.getWifiStateCount(i20, i13));
        }
        dumpLine(printWriter4, 0, str7, WIFI_STATE_COUNT_DATA, objArr5);
        Object[] objArr6 = new Object[13];
        for (int i21 = 0; i21 < 13; i21++) {
            objArr6[i21] = Long.valueOf(batteryStats.getWifiSupplStateTime(i21, j8, i13) / 1000);
        }
        dumpLine(printWriter4, 0, str7, WIFI_SUPPL_STATE_TIME_DATA, objArr6);
        for (int i22 = 0; i22 < 13; i22++) {
            objArr6[i22] = Integer.valueOf(batteryStats.getWifiSupplStateCount(i22, i13));
        }
        dumpLine(printWriter4, 0, str7, WIFI_SUPPL_STATE_COUNT_DATA, objArr6);
        char c2 = 5;
        Object[] objArr7 = new Object[5];
        for (int i23 = 0; i23 < 5; i23++) {
            objArr7[i23] = Long.valueOf(batteryStats.getWifiSignalStrengthTime(i23, j8, i13) / 1000);
        }
        dumpLine(printWriter4, 0, str7, WIFI_SIGNAL_STRENGTH_TIME_DATA, objArr7);
        for (int i24 = 0; i24 < 5; i24++) {
            objArr7[i24] = Integer.valueOf(batteryStats.getWifiSignalStrengthCount(i24, i13));
        }
        dumpLine(printWriter4, 0, str7, WIFI_SIGNAL_STRENGTH_COUNT_DATA, objArr7);
        dumpLine(printWriter4, 0, str7, WIFI_MULTICAST_TOTAL_DATA, Long.valueOf(batteryStats.getWifiMulticastWakelockTime(j8, i13) / 1000), Integer.valueOf(batteryStats.getWifiMulticastWakelockCount(i13)));
        dumpLine(printWriter4, 0, str7, BATTERY_DISCHARGE_DATA, Integer.valueOf(batteryStats.getLowDischargeAmountSinceCharge()), Integer.valueOf(batteryStats.getHighDischargeAmountSinceCharge()), Integer.valueOf(batteryStats.getDischargeAmountScreenOnSinceCharge()), Integer.valueOf(batteryStats.getDischargeAmountScreenOffSinceCharge()), Long.valueOf(uahDischarge / 1000), Long.valueOf(uahDischargeScreenOff / 1000), Integer.valueOf(batteryStats.getDischargeAmountScreenDozeSinceCharge()), Long.valueOf(uahDischargeScreenDoze / 1000), Long.valueOf(uahDischargeLightDoze / 1000), Long.valueOf(uahDischargeDeepDoze / 1000));
        String str8 = "\"";
        if (i8 < 0) {
            Map<String, ? extends Timer> kernelWakelockStats = batteryStats.getKernelWakelockStats();
            if (kernelWakelockStats.size() > 0) {
                for (Map.Entry<String, ? extends Timer> entry : kernelWakelockStats.entrySet()) {
                    sb4.setLength(0);
                    long j10 = j8;
                    String str9 = str7;
                    PrintWriter printWriter5 = printWriter4;
                    StringBuilder sb5 = sb4;
                    printWakeLockCheckin(sb5, entry.getValue(), j10, null, i13, "");
                    dumpLine(printWriter5, 0, str9, KERNEL_WAKELOCK_DATA, "\"" + entry.getKey() + "\"", sb5.toString());
                    c2 = c2;
                    batteryUptime = batteryUptime;
                    sb4 = sb5;
                    printWriter4 = printWriter5;
                    j8 = j10;
                    str7 = str9;
                }
            }
            j = batteryUptime;
            long j11 = j8;
            printWriter2 = printWriter4;
            sb = sb4;
            str = str7;
            j2 = j11;
            Map<String, ? extends Timer> wakeupReasonStats = batteryStats.getWakeupReasonStats();
            if (wakeupReasonStats.size() > 0) {
                for (Map.Entry<String, ? extends Timer> entry2 : wakeupReasonStats.entrySet()) {
                    dumpLine(printWriter2, 0, str, WAKEUP_REASON_DATA, "\"" + entry2.getKey() + "\"", Long.valueOf((entry2.getValue().getTotalTimeLocked(j2, i13) + 500) / 1000), Integer.valueOf(entry2.getValue().getCountLocked(i13)));
                }
            }
        } else {
            j = batteryUptime;
            printWriter2 = printWriter4;
            sb = sb4;
            str = str7;
            j2 = j8;
        }
        Map<String, ? extends Timer> rpmStats = batteryStats.getRpmStats();
        Map<String, ? extends Timer> screenOffRpmStats = batteryStats.getScreenOffRpmStats();
        if (rpmStats.size() > 0) {
            for (Map.Entry<String, ? extends Timer> entry3 : rpmStats.entrySet()) {
                sb.setLength(0);
                Timer value = entry3.getValue();
                long totalTimeLocked3 = (value.getTotalTimeLocked(j2, i13) + 500) / 1000;
                int countLocked = value.getCountLocked(i13);
                Timer timer = screenOffRpmStats.get(entry3.getKey());
                if (timer != null) {
                    long totalTimeLocked4 = (timer.getTotalTimeLocked(j2, i13) + 500) / 1000;
                }
                if (timer != null) {
                    timer.getCountLocked(i13);
                }
                dumpLine(printWriter2, 0, str, RESOURCE_POWER_MANAGER_DATA, "\"" + entry3.getKey() + "\"", Long.valueOf(totalTimeLocked3), Integer.valueOf(countLocked));
            }
        }
        BatteryUsageStats batteryUsageStats = batteryStatsDumpHelper.getBatteryUsageStats(batteryStats, true);
        dumpLine(printWriter2, 0, str, POWER_USE_SUMMARY_DATA, formatCharge(batteryUsageStats.getBatteryCapacity()), formatCharge(batteryUsageStats.getConsumedPower()), formatCharge(((Double) batteryUsageStats.getDischargedPowerRange().getLower()).doubleValue()), formatCharge(((Double) batteryUsageStats.getDischargedPowerRange().getUpper()).doubleValue()));
        AggregateBatteryConsumer aggregateBatteryConsumer = batteryUsageStats.getAggregateBatteryConsumer(0);
        int i25 = 0;
        while (i25 < 20) {
            String str10 = CHECKIN_POWER_COMPONENT_LABELS[i25];
            if (str10 == null) {
                str10 = "???";
            }
            dumpLine(printWriter2, 0, str, POWER_USE_ITEM_DATA, str10, formatCharge(aggregateBatteryConsumer.getConsumedPower(i25)), Integer.valueOf(batteryStats.shouldHidePowerComponent(i25) ? 1 : 0), "0", "0");
            i25++;
            batteryStats = this;
            j2 = j2;
        }
        long j12 = j2;
        ProportionalAttributionCalculator proportionalAttributionCalculator = new ProportionalAttributionCalculator(context, batteryUsageStats);
        List<UidBatteryConsumer> uidBatteryConsumers = batteryUsageStats.getUidBatteryConsumers();
        int i26 = 0;
        while (i26 < uidBatteryConsumers.size()) {
            UidBatteryConsumer uidBatteryConsumer = uidBatteryConsumers.get(i26);
            dumpLine(printWriter2, uidBatteryConsumer.getUid(), str, POWER_USE_ITEM_DATA, "uid", formatCharge(uidBatteryConsumer.getConsumedPower()), Integer.valueOf(proportionalAttributionCalculator.isSystemBatteryConsumer(uidBatteryConsumer) ? 1 : 0), formatCharge(uidBatteryConsumer.getConsumedPower(0)), formatCharge(proportionalAttributionCalculator.getProportionalPowerMah(uidBatteryConsumer)));
            i26++;
            uidBatteryConsumers = uidBatteryConsumers;
            proportionalAttributionCalculator = proportionalAttributionCalculator;
        }
        CpuScalingPolicies cpuScalingPolicies2 = getCpuScalingPolicies();
        if (cpuScalingPolicies2 != null) {
            sb.setLength(0);
            int[] policies = cpuScalingPolicies2.getPolicies();
            int length = policies.length;
            int i27 = 0;
            while (i27 < length) {
                int[] frequencies = cpuScalingPolicies2.getFrequencies(policies[i27]);
                int length2 = frequencies.length;
                int i28 = 0;
                while (i28 < length2) {
                    int i29 = frequencies[i28];
                    CpuScalingPolicies cpuScalingPolicies3 = cpuScalingPolicies2;
                    if (sb.length() != 0) {
                        sb.append(',');
                    }
                    sb.append(i29);
                    i28++;
                    cpuScalingPolicies2 = cpuScalingPolicies3;
                }
                i27++;
                cpuScalingPolicies2 = cpuScalingPolicies2;
            }
            cpuScalingPolicies = cpuScalingPolicies2;
            dumpLine(printWriter2, 0, str, GLOBAL_CPU_FREQ_DATA, sb.toString());
        } else {
            cpuScalingPolicies = cpuScalingPolicies2;
        }
        int i30 = 0;
        while (i30 < size) {
            SparseArray<? extends Uid> sparseArray3 = sparseArray2;
            int iKeyAt = sparseArray3.keyAt(i30);
            if (i8 < 0 || iKeyAt == i8) {
                Uid uidValueAt = sparseArray3.valueAt(i30);
                long networkActivityBytes = uidValueAt.getNetworkActivityBytes(0, i13);
                long networkActivityBytes2 = uidValueAt.getNetworkActivityBytes(1, i13);
                long networkActivityBytes3 = uidValueAt.getNetworkActivityBytes(2, i13);
                long networkActivityBytes4 = uidValueAt.getNetworkActivityBytes(3, i13);
                long networkActivityPackets = uidValueAt.getNetworkActivityPackets(0, i13);
                long networkActivityPackets2 = uidValueAt.getNetworkActivityPackets(1, i13);
                long mobileRadioActiveTime = uidValueAt.getMobileRadioActiveTime(i13);
                int mobileRadioActiveCount = uidValueAt.getMobileRadioActiveCount(i13);
                long mobileRadioApWakeupCount = uidValueAt.getMobileRadioApWakeupCount(i13);
                long networkActivityPackets3 = uidValueAt.getNetworkActivityPackets(2, i13);
                long networkActivityPackets4 = uidValueAt.getNetworkActivityPackets(3, i13);
                long wifiRadioApWakeupCount = uidValueAt.getWifiRadioApWakeupCount(i13);
                long networkActivityBytes5 = uidValueAt.getNetworkActivityBytes(4, i13);
                long networkActivityBytes6 = uidValueAt.getNetworkActivityBytes(5, i13);
                long networkActivityBytes7 = uidValueAt.getNetworkActivityBytes(6, i13);
                long networkActivityBytes8 = uidValueAt.getNetworkActivityBytes(7, i13);
                long networkActivityBytes9 = uidValueAt.getNetworkActivityBytes(8, i13);
                long networkActivityBytes10 = uidValueAt.getNetworkActivityBytes(9, i13);
                long networkActivityPackets5 = uidValueAt.getNetworkActivityPackets(6, i13);
                long networkActivityPackets6 = uidValueAt.getNetworkActivityPackets(7, i13);
                long networkActivityPackets7 = uidValueAt.getNetworkActivityPackets(8, i13);
                long networkActivityPackets8 = uidValueAt.getNetworkActivityPackets(9, i13);
                if (networkActivityBytes > 0 || networkActivityBytes2 > 0 || networkActivityBytes3 > 0 || networkActivityBytes4 > 0 || networkActivityPackets > 0 || networkActivityPackets2 > 0 || networkActivityPackets3 > 0 || networkActivityPackets4 > 0 || mobileRadioActiveTime > 0 || mobileRadioActiveCount > 0 || networkActivityBytes5 > 0 || networkActivityBytes6 > 0 || mobileRadioApWakeupCount > 0 || wifiRadioApWakeupCount > 0 || networkActivityBytes7 > 0 || networkActivityBytes8 > 0 || networkActivityBytes9 > 0 || networkActivityBytes10 > 0 || networkActivityPackets5 > 0 || networkActivityPackets6 > 0 || networkActivityPackets7 > 0 || networkActivityPackets8 > 0) {
                    dumpLine(printWriter2, iKeyAt, str, NETWORK_DATA, Long.valueOf(networkActivityBytes), Long.valueOf(networkActivityBytes2), Long.valueOf(networkActivityBytes3), Long.valueOf(networkActivityBytes4), Long.valueOf(networkActivityPackets), Long.valueOf(networkActivityPackets2), Long.valueOf(networkActivityPackets3), Long.valueOf(networkActivityPackets4), Long.valueOf(mobileRadioActiveTime), Integer.valueOf(mobileRadioActiveCount), Long.valueOf(networkActivityBytes5), Long.valueOf(networkActivityBytes6), Long.valueOf(mobileRadioApWakeupCount), Long.valueOf(wifiRadioApWakeupCount), Long.valueOf(networkActivityBytes7), Long.valueOf(networkActivityBytes8), Long.valueOf(networkActivityBytes9), Long.valueOf(networkActivityBytes10), Long.valueOf(networkActivityPackets5), Long.valueOf(networkActivityPackets6), Long.valueOf(networkActivityPackets7), Long.valueOf(networkActivityPackets8));
                }
                PrintWriter printWriter6 = printWriter2;
                Uid uid2 = uidValueAt;
                int i31 = size;
                String str11 = str;
                long j13 = j12;
                dumpControllerActivityLine(printWriter6, iKeyAt, str11, MODEM_CONTROLLER_DATA, uidValueAt.getModemControllerActivity(), i13);
                long fullWifiLockTime = uid2.getFullWifiLockTime(j13, i13);
                long wifiScanTime = uid2.getWifiScanTime(j13, i13);
                int wifiScanCount = uid2.getWifiScanCount(i13);
                int wifiScanBackgroundCount = uid2.getWifiScanBackgroundCount(i13);
                long wifiScanActualTime = (uid2.getWifiScanActualTime(j13) + 500) / 1000;
                long wifiScanBackgroundTime = (uid2.getWifiScanBackgroundTime(j13) + 500) / 1000;
                long wifiRunningTime = uid2.getWifiRunningTime(j13, i13);
                if (fullWifiLockTime == 0 && wifiScanTime == 0 && wifiScanCount == 0 && wifiScanBackgroundCount == 0 && wifiScanActualTime == 0 && wifiScanBackgroundTime == 0 && wifiRunningTime == 0) {
                    i4 = i31;
                    str2 = str8;
                    i3 = i30;
                    sparseArray2 = sparseArray3;
                    j3 = j9;
                    i5 = 1;
                    c = 2;
                } else {
                    j3 = j9;
                    i3 = i30;
                    i4 = i31;
                    sparseArray2 = sparseArray3;
                    i5 = 1;
                    str2 = str8;
                    c = 2;
                    dumpLine(printWriter6, iKeyAt, str11, WIFI_DATA, Long.valueOf(fullWifiLockTime), Long.valueOf(wifiScanTime), Long.valueOf(wifiRunningTime), Integer.valueOf(wifiScanCount), num4, num4, num4, Integer.valueOf(wifiScanBackgroundCount), Long.valueOf(wifiScanActualTime), Long.valueOf(wifiScanBackgroundTime));
                }
                long j14 = j;
                long j15 = j3;
                dumpControllerActivityLine(printWriter6, iKeyAt, str11, WIFI_CONTROLLER_DATA, uid2.getWifiControllerActivity(), i13);
                Timer bluetoothScanTimer = uid2.getBluetoothScanTimer();
                if (bluetoothScanTimer != null) {
                    long totalTimeLocked5 = (bluetoothScanTimer.getTotalTimeLocked(j13, i13) + 500) / 1000;
                    if (totalTimeLocked5 != 0) {
                        int countLocked2 = bluetoothScanTimer.getCountLocked(i13);
                        i6 = i5;
                        Timer bluetoothScanBackgroundTimer = uid2.getBluetoothScanBackgroundTimer();
                        int countLocked3 = bluetoothScanBackgroundTimer != null ? bluetoothScanBackgroundTimer.getCountLocked(i13) : 0;
                        long totalDurationMsLocked2 = bluetoothScanTimer.getTotalDurationMsLocked(j15);
                        long totalDurationMsLocked3 = bluetoothScanBackgroundTimer != null ? bluetoothScanBackgroundTimer.getTotalDurationMsLocked(j15) : 0L;
                        int countLocked4 = uid2.getBluetoothScanResultCounter() != null ? uid2.getBluetoothScanResultCounter().getCountLocked(i13) : 0;
                        int countLocked5 = uid2.getBluetoothScanResultBgCounter() != null ? uid2.getBluetoothScanResultBgCounter().getCountLocked(i13) : 0;
                        int i32 = countLocked4;
                        Timer bluetoothUnoptimizedScanTimer = uid2.getBluetoothUnoptimizedScanTimer();
                        long totalDurationMsLocked4 = bluetoothUnoptimizedScanTimer != null ? bluetoothUnoptimizedScanTimer.getTotalDurationMsLocked(j15) : 0L;
                        long maxDurationMsLocked = bluetoothUnoptimizedScanTimer != null ? bluetoothUnoptimizedScanTimer.getMaxDurationMsLocked(j15) : 0L;
                        Timer bluetoothUnoptimizedScanBackgroundTimer = uid2.getBluetoothUnoptimizedScanBackgroundTimer();
                        dumpLine(printWriter6, iKeyAt, str11, BLUETOOTH_MISC_DATA, Long.valueOf(totalTimeLocked5), Integer.valueOf(countLocked2), Integer.valueOf(countLocked3), Long.valueOf(totalDurationMsLocked2), Long.valueOf(totalDurationMsLocked3), Integer.valueOf(i32), Integer.valueOf(countLocked5), Long.valueOf(totalDurationMsLocked4), Long.valueOf(bluetoothUnoptimizedScanBackgroundTimer != null ? bluetoothUnoptimizedScanBackgroundTimer.getTotalDurationMsLocked(j15) : 0L), Long.valueOf(maxDurationMsLocked), Long.valueOf(bluetoothUnoptimizedScanBackgroundTimer != null ? bluetoothUnoptimizedScanBackgroundTimer.getMaxDurationMsLocked(j15) : 0L));
                    } else {
                        i6 = i5;
                    }
                    dumpControllerActivityLine(printWriter6, iKeyAt, str11, BLUETOOTH_CONTROLLER_DATA, uid2.getBluetoothControllerActivity(), i13);
                    String str12 = str11;
                    if (uid2.hasUserActivity()) {
                        Object[] objArr8 = new Object[Uid.NUM_USER_ACTIVITY_TYPES];
                        int i33 = 0;
                        for (int i34 = 0; i34 < Uid.NUM_USER_ACTIVITY_TYPES; i34++) {
                            int userActivityCount = uid2.getUserActivityCount(i34, i13);
                            objArr8[i34] = Integer.valueOf(userActivityCount);
                            if (userActivityCount != 0) {
                                i33 = i6;
                            }
                        }
                        if (i33 != 0) {
                            dumpLine(printWriter6, iKeyAt, str12, USER_ACTIVITY_DATA, objArr8);
                        }
                    }
                    if (uid2.getAggregatedPartialWakelockTimer() != null) {
                        Timer aggregatedPartialWakelockTimer = uid2.getAggregatedPartialWakelockTimer();
                        long totalDurationMsLocked5 = aggregatedPartialWakelockTimer.getTotalDurationMsLocked(j15);
                        Timer subTimer = aggregatedPartialWakelockTimer.getSubTimer();
                        dumpLine(printWriter6, iKeyAt, str12, AGGREGATED_WAKELOCK_DATA, Long.valueOf(totalDurationMsLocked5), Long.valueOf(subTimer != null ? subTimer.getTotalDurationMsLocked(j15) : 0L));
                    }
                    ArrayMap<String, ? extends Uid.Wakelock> wakelockStats2 = uid2.getWakelockStats();
                    int size3 = wakelockStats2.size() - 1;
                    while (size3 >= 0) {
                        long j16 = j13;
                        Uid.Wakelock wakelockValueAt2 = wakelockStats2.valueAt(size3);
                        sb.setLength(0);
                        int i35 = iKeyAt;
                        StringBuilder sb6 = sb;
                        long j17 = j15;
                        long j18 = j14;
                        Integer num5 = num4;
                        String str13 = str12;
                        ArrayMap<String, ? extends Uid.Wakelock> arrayMap6 = wakelockStats2;
                        Uid uid3 = uid2;
                        int i36 = size3;
                        String strPrintWakeLockCheckin = printWakeLockCheckin(sb6, wakelockValueAt2.getWakeTime(i6), j16, FullBackup.FILES_TREE_TOKEN, i13, "");
                        Timer wakeTime3 = wakelockValueAt2.getWakeTime(0);
                        i13 = i;
                        printWakeLockCheckin(sb6, wakelockValueAt2.getWakeTime(2), j16, "w", i13, printWakeLockCheckin(sb6, wakeTime3 != null ? wakeTime3.getSubTimer() : null, j16, "bp", i13, printWakeLockCheckin(sb6, wakeTime3, j16, "p", i, strPrintWakeLockCheckin)));
                        if (sb6.length() > 0) {
                            String strKeyAt = arrayMap6.keyAt(i36);
                            if (strKeyAt.indexOf(44) >= 0) {
                                strKeyAt = strKeyAt.replace(',', '_');
                            }
                            if (strKeyAt.indexOf(10) >= 0) {
                                strKeyAt = strKeyAt.replace('\n', '_');
                            }
                            if (strKeyAt.indexOf(13) >= 0) {
                                strKeyAt = strKeyAt.replace('\r', '_');
                            }
                            dumpLine(printWriter6, i35, str13, "wl", strKeyAt, sb6.toString());
                        }
                        int i37 = i36 - 1;
                        j13 = j16;
                        sb = sb6;
                        iKeyAt = i35;
                        wakelockStats2 = arrayMap6;
                        num4 = num5;
                        uid2 = uid3;
                        j15 = j17;
                        i6 = 1;
                        size3 = i37;
                        str12 = str13;
                        j14 = j18;
                    }
                    Uid uid4 = uid2;
                    long j19 = j13;
                    long j20 = j15;
                    j = j14;
                    Integer num6 = num4;
                    str3 = str12;
                    int i38 = iKeyAt;
                    sb2 = sb;
                    Timer multicastWakelockStats = uid4.getMulticastWakelockStats();
                    if (multicastWakelockStats != null) {
                        long totalTimeLocked6 = multicastWakelockStats.getTotalTimeLocked(j19, i13) / 1000;
                        int countLocked6 = multicastWakelockStats.getCountLocked(i13);
                        if (totalTimeLocked6 > 0) {
                            dumpLine(printWriter6, i38, str3, WIFI_MULTICAST_DATA, Long.valueOf(totalTimeLocked6), Integer.valueOf(countLocked6));
                        }
                    }
                    ArrayMap<String, ? extends Timer> syncStats = uid4.getSyncStats();
                    int size4 = syncStats.size() - 1;
                    while (size4 >= 0) {
                        Timer timerValueAt = syncStats.valueAt(size4);
                        long totalTimeLocked7 = (timerValueAt.getTotalTimeLocked(j19, i13) + 500) / 1000;
                        int countLocked7 = timerValueAt.getCountLocked(i13);
                        Timer subTimer2 = timerValueAt.getSubTimer();
                        if (subTimer2 != null) {
                            j7 = j20;
                            totalDurationMsLocked = subTimer2.getTotalDurationMsLocked(j7);
                        } else {
                            j7 = j20;
                            totalDurationMsLocked = -1;
                        }
                        int countLocked8 = subTimer2 != null ? subTimer2.getCountLocked(i13) : -1;
                        if (totalTimeLocked7 != 0) {
                            str5 = str2;
                            StringBuilder sb7 = new StringBuilder(str5);
                            arrayMap4 = syncStats;
                            sb7.append(syncStats.keyAt(size4));
                            sb7.append(str5);
                            String string = sb7.toString();
                            Long lValueOf = Long.valueOf(totalTimeLocked7);
                            i7 = size4;
                            dumpLine(printWriter6, i38, str3, SYNC_DATA, string, lValueOf, Integer.valueOf(countLocked7), Long.valueOf(totalDurationMsLocked), Integer.valueOf(countLocked8));
                        } else {
                            arrayMap4 = syncStats;
                            i7 = size4;
                            str5 = str2;
                        }
                        size4 = i7 - 1;
                        str2 = str5;
                        syncStats = arrayMap4;
                        j20 = j7;
                    }
                    str4 = str2;
                    j4 = j20;
                    ArrayMap<String, ? extends Timer> jobStats = uid4.getJobStats();
                    int size5 = jobStats.size() - 1;
                    while (size5 >= 0) {
                        Timer timerValueAt2 = jobStats.valueAt(size5);
                        long totalTimeLocked8 = (timerValueAt2.getTotalTimeLocked(j19, i13) + 500) / 1000;
                        int countLocked9 = timerValueAt2.getCountLocked(i13);
                        Timer subTimer3 = timerValueAt2.getSubTimer();
                        long totalDurationMsLocked6 = subTimer3 != null ? subTimer3.getTotalDurationMsLocked(j4) : -1L;
                        int countLocked10 = subTimer3 != null ? subTimer3.getCountLocked(i13) : -1;
                        if (totalTimeLocked8 != 0) {
                            arrayMap3 = jobStats;
                            dumpLine(printWriter6, i38, str3, JOB_DATA, str4 + jobStats.keyAt(size5) + str4, Long.valueOf(totalTimeLocked8), Integer.valueOf(countLocked9), Long.valueOf(totalDurationMsLocked6), Integer.valueOf(countLocked10));
                        } else {
                            arrayMap3 = jobStats;
                        }
                        size5--;
                        jobStats = arrayMap3;
                    }
                    int[] jobStopReasonCodes = JobParameters.getJobStopReasonCodes();
                    Object[] objArr9 = new Object[jobStopReasonCodes.length + 1];
                    ArrayMap<String, SparseIntArray> jobCompletionStats = uid4.getJobCompletionStats();
                    int size6 = jobCompletionStats.size() - 1;
                    while (size6 >= 0) {
                        SparseIntArray sparseIntArrayValueAt = jobCompletionStats.valueAt(size6);
                        if (sparseIntArrayValueAt != null) {
                            StringBuilder sb8 = new StringBuilder(str4);
                            j6 = j19;
                            sb8.append(jobCompletionStats.keyAt(size6));
                            sb8.append(str4);
                            int i39 = 0;
                            objArr9[0] = sb8.toString();
                            int i40 = 0;
                            while (i40 < jobStopReasonCodes.length) {
                                int i41 = i40 + 1;
                                objArr9[i41] = Integer.valueOf(sparseIntArrayValueAt.get(jobStopReasonCodes[i40], i39));
                                i40 = i41;
                                i39 = 0;
                            }
                            dumpLine(printWriter6, i38, str3, JOB_COMPLETION_DATA, objArr9);
                        } else {
                            j6 = j19;
                        }
                        size6--;
                        j19 = j6;
                    }
                    long j21 = j19;
                    uid4.getDeferredJobsCheckinLineLocked(sb2, i13);
                    if (sb2.length() > 0) {
                        dumpLine(printWriter6, i38, str3, JOBS_DEFERRED_DATA, sb2.toString());
                    }
                    Uid uid5 = uid4;
                    dumpTimer(printWriter6, i38, str3, FLASHLIGHT_DATA, uid4.getFlashlightTurnedOnTimer(), j21, i13);
                    dumpTimer(printWriter, i38, str3, CAMERA_DATA, uid5.getCameraTurnedOnTimer(), j21, i);
                    dumpTimer(printWriter, i38, str3, "vid", uid5.getVideoTurnedOnTimer(), j21, i);
                    dumpTimer(printWriter, i38, str3, AUDIO_DATA, uid5.getAudioTurnedOnTimer(), j21, i);
                    SparseArray<? extends Uid.Sensor> sensorStats = uid5.getSensorStats();
                    int size7 = sensorStats.size();
                    int i42 = 0;
                    while (i42 < size7) {
                        Uid.Sensor sensorValueAt = sensorStats.valueAt(i42);
                        int iKeyAt2 = sensorStats.keyAt(i42);
                        SparseArray<? extends Uid.Sensor> sparseArray4 = sensorStats;
                        Timer sensorTime = sensorValueAt.getSensorTime();
                        if (sensorTime != null) {
                            long totalTimeLocked9 = (sensorTime.getTotalTimeLocked(j21, i) + 500) / 1000;
                            if (totalTimeLocked9 != 0) {
                                int countLocked11 = sensorTime.getCountLocked(i);
                                Timer sensorBackgroundTime = sensorValueAt.getSensorBackgroundTime();
                                dumpLine(printWriter, i38, str3, SENSOR_DATA, Integer.valueOf(iKeyAt2), Long.valueOf(totalTimeLocked9), Integer.valueOf(countLocked11), Integer.valueOf(sensorBackgroundTime != null ? sensorBackgroundTime.getCountLocked(i) : 0), Long.valueOf(sensorTime.getTotalDurationMsLocked(j4)), Long.valueOf(sensorBackgroundTime != null ? sensorBackgroundTime.getTotalDurationMsLocked(j4) : 0L));
                            }
                        }
                        i42++;
                        sensorStats = sparseArray4;
                    }
                    dumpTimer(printWriter, i38, str3, VIBRATOR_DATA, uid5.getVibratorOnTimer(), j21, i);
                    dumpTimer(printWriter, i38, str3, FOREGROUND_ACTIVITY_DATA, uid5.getForegroundActivityTimer(), j21, i);
                    printWriter3 = printWriter;
                    dumpTimer(printWriter3, i38, str3, FOREGROUND_SERVICE_DATA, uid5.getForegroundServiceTimer(), j21, i);
                    long j22 = j21;
                    i13 = i;
                    Object[] objArr10 = new Object[7];
                    long j23 = 0;
                    int i43 = 0;
                    for (int i44 = 7; i43 < i44; i44 = 7) {
                        long processStateTime = uid5.getProcessStateTime(i43, j22, i13);
                        j23 += processStateTime;
                        objArr10[i43] = Long.valueOf((processStateTime + 500) / 1000);
                        i43++;
                    }
                    if (j23 > 0) {
                        dumpLine(printWriter3, i38, str3, "st", objArr10);
                    }
                    long userCpuTimeUs = uid5.getUserCpuTimeUs(i13);
                    long systemCpuTimeUs = uid5.getSystemCpuTimeUs(i13);
                    if (userCpuTimeUs > 0 || systemCpuTimeUs > 0) {
                        num = num6;
                        dumpLine(printWriter3, i38, str3, CPU_DATA, Long.valueOf(userCpuTimeUs / 1000), Long.valueOf(systemCpuTimeUs / 1000), num);
                    } else {
                        num = num6;
                    }
                    if (cpuScalingPolicies != null) {
                        long[] cpuFreqTimes = uid5.getCpuFreqTimes(i13);
                        if (cpuFreqTimes == null || cpuFreqTimes.length != cpuScalingPolicies.getScalingStepCount()) {
                            j5 = j22;
                        } else {
                            sb2.setLength(0);
                            int i45 = 0;
                            while (i45 < cpuFreqTimes.length) {
                                if (i45 != 0) {
                                    sb2.append(',');
                                }
                                sb2.append(cpuFreqTimes[i45]);
                                i45++;
                                j22 = j22;
                            }
                            j5 = j22;
                            long[] screenOffCpuFreqTimes = uid5.getScreenOffCpuFreqTimes(i13);
                            if (screenOffCpuFreqTimes != null) {
                                for (long j24 : screenOffCpuFreqTimes) {
                                    sb2.append(',');
                                    sb2.append(j24);
                                }
                            } else {
                                for (int i46 = 0; i46 < cpuFreqTimes.length; i46++) {
                                    sb2.append(",0");
                                }
                            }
                            dumpLine(printWriter3, i38, str3, CPU_TIMES_AT_FREQ_DATA, "A", Integer.valueOf(cpuFreqTimes.length), sb2.toString());
                        }
                        int scalingStepCount = getCpuScalingPolicies().getScalingStepCount();
                        long[] jArr = new long[scalingStepCount];
                        int i47 = 0;
                        while (i47 < 7) {
                            if (uid5.getCpuFreqTimes(jArr, i47)) {
                                sb2.setLength(0);
                                int i48 = 0;
                                while (i48 < scalingStepCount) {
                                    if (i48 != 0) {
                                        sb2.append(',');
                                    }
                                    sb2.append(jArr[i48]);
                                    i48++;
                                    num = num;
                                }
                                num3 = num;
                                if (uid5.getScreenOffCpuFreqTimes(jArr, i47)) {
                                    for (int i49 = 0; i49 < scalingStepCount; i49++) {
                                        sb2.append(',');
                                        sb2.append(jArr[i49]);
                                    }
                                } else {
                                    for (int i50 = 0; i50 < scalingStepCount; i50++) {
                                        sb2.append(",0");
                                    }
                                }
                                dumpLine(printWriter3, i38, str3, CPU_TIMES_AT_FREQ_DATA, Uid.UID_PROCESS_TYPES[i47], Integer.valueOf(scalingStepCount), sb2.toString());
                            } else {
                                num3 = num;
                            }
                            i47++;
                            num = num3;
                        }
                    } else {
                        j5 = j22;
                    }
                    num2 = num;
                    ArrayMap<String, ? extends Uid.Proc> processStats = uid5.getProcessStats();
                    int size8 = processStats.size() - 1;
                    while (size8 >= 0) {
                        Uid.Proc procValueAt = processStats.valueAt(size8);
                        long userTime = procValueAt.getUserTime(i13);
                        long systemTime = procValueAt.getSystemTime(i13);
                        long foregroundTime = procValueAt.getForegroundTime(i13);
                        int starts = procValueAt.getStarts(i13);
                        int numCrashes = procValueAt.getNumCrashes(i13);
                        int numAnrs = procValueAt.getNumAnrs(i13);
                        if (userTime == 0 && systemTime == 0 && foregroundTime == 0 && starts == 0 && numAnrs == 0 && numCrashes == 0) {
                            uid = uid5;
                            arrayMap2 = processStats;
                        } else {
                            uid = uid5;
                            StringBuilder sb9 = new StringBuilder(str4);
                            arrayMap2 = processStats;
                            sb9.append(processStats.keyAt(size8));
                            sb9.append(str4);
                            dumpLine(printWriter3, i38, str3, PROCESS_DATA, sb9.toString(), Long.valueOf(userTime), Long.valueOf(systemTime), Long.valueOf(foregroundTime), Integer.valueOf(starts), Integer.valueOf(numAnrs), Integer.valueOf(numCrashes));
                        }
                        size8--;
                        uid5 = uid;
                        processStats = arrayMap2;
                    }
                    ArrayMap<String, ? extends Uid.Pkg> packageStats = uid5.getPackageStats();
                    for (int size9 = packageStats.size() - 1; size9 >= 0; size9--) {
                        Uid.Pkg pkgValueAt = packageStats.valueAt(size9);
                        ArrayMap<String, ? extends Counter> wakeupAlarmStats = pkgValueAt.getWakeupAlarmStats();
                        int size10 = wakeupAlarmStats.size() - 1;
                        int i51 = 0;
                        while (size10 >= 0) {
                            int countLocked12 = wakeupAlarmStats.valueAt(size10).getCountLocked(i13);
                            i51 += countLocked12;
                            dumpLine(printWriter3, i38, str3, WAKEUP_ALARM_DATA, wakeupAlarmStats.keyAt(size10).replace(',', '_'), Integer.valueOf(countLocked12));
                            size10--;
                            pkgValueAt = pkgValueAt;
                            wakeupAlarmStats = wakeupAlarmStats;
                        }
                        ArrayMap<String, ? extends Uid.Pkg.Serv> serviceStats = pkgValueAt.getServiceStats();
                        int size11 = serviceStats.size() - 1;
                        while (size11 >= 0) {
                            Uid.Pkg.Serv servValueAt = serviceStats.valueAt(size11);
                            long j25 = j;
                            long startTime = servValueAt.getStartTime(j25, i13);
                            int starts2 = servValueAt.getStarts(i13);
                            int launches = servValueAt.getLaunches(i13);
                            if (startTime == 0 && starts2 == 0 && launches == 0) {
                                arrayMap = packageStats;
                            } else {
                                Object[] objArr11 = {Integer.valueOf(i51), packageStats.keyAt(size9), serviceStats.keyAt(size11), Long.valueOf(startTime / 1000), Integer.valueOf(starts2), Integer.valueOf(launches)};
                                arrayMap = packageStats;
                                dumpLine(printWriter3, i38, str3, APK_DATA, objArr11);
                            }
                            size11--;
                            j = j25;
                            packageStats = arrayMap;
                        }
                    }
                }
            } else {
                printWriter3 = printWriter2;
                i4 = size;
                str4 = str8;
                i3 = i30;
                sparseArray2 = sparseArray3;
                j4 = j9;
                j5 = j12;
                str3 = str;
                num2 = num4;
                sb2 = sb;
            }
            printWriter2 = printWriter3;
            j = j;
            sb = sb2;
            str = str3;
            num4 = num2;
            j12 = j5;
            i30 = i3 + 1;
            j9 = j4;
            size = i4;
            str8 = str4;
            i8 = i2;
        }
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

    /* JADX WARN: Removed duplicated region for block: B:314:0x12f8  */
    /* JADX WARN: Removed duplicated region for block: B:319:0x1360  */
    /* JADX WARN: Removed duplicated region for block: B:322:0x136f  */
    /* JADX WARN: Removed duplicated region for block: B:542:0x1b9c  */
    /* JADX WARN: Removed duplicated region for block: B:837:0x24d1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dumpLocked(Context context, PrintWriter printWriter, String str, int i, int i2, boolean z, BatteryStatsDumpHelper batteryStatsDumpHelper) {
        long j;
        long j2;
        long j3;
        long j4;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        PrintWriter printWriter2;
        long j5;
        String str7;
        String str8;
        String str9;
        String str10;
        String str11;
        long j6;
        String str12;
        long j7;
        String str13;
        String str14;
        String str15;
        String str16;
        int i3;
        long j8;
        StringBuilder sb;
        ArrayList arrayList;
        long j9;
        long j10;
        int i4;
        String str17;
        String str18;
        String str19;
        String str20;
        String str21;
        long j11;
        int i5;
        int i6;
        String str22;
        String str23;
        String str24;
        String str25;
        String str26;
        String str27;
        String str28;
        long j12;
        String str29;
        StringBuilder sb2;
        long j13;
        String str30;
        PrintWriter printWriter3;
        long j14;
        String str31;
        CpuScalingPolicies cpuScalingPolicies;
        long j15;
        int i7;
        int i8;
        int i9;
        long j16;
        String str32;
        int i10;
        long j17;
        String str33;
        String str34;
        long j18;
        int i11;
        String str35;
        String str36;
        long j19;
        PrintWriter printWriter4;
        String str37;
        String str38;
        String str39;
        boolean z2;
        Uid uid;
        String str40;
        boolean z3;
        String str41;
        String str42;
        long j20;
        BatteryStats batteryStats;
        boolean z4;
        int i12;
        CpuScalingPolicies cpuScalingPolicies2;
        int i13;
        PrintWriter printWriter5;
        int i14;
        int i15;
        long j21;
        String str43;
        String str44;
        boolean z5;
        PrintWriter printWriter6;
        String str45;
        int i16;
        String str46;
        int i17;
        long[] jArr;
        int i18;
        long j22;
        long j23;
        String str47;
        long j24;
        SparseArray<? extends Uid.Sensor> sparseArray;
        long j25;
        String str48;
        int i19;
        long totalDurationMsLocked;
        String str49;
        String str50;
        long j26;
        long j27;
        long totalDurationMsLocked2;
        long j28;
        long totalDurationMsLocked3;
        String str51;
        long j29;
        long j30;
        boolean z6;
        String str52;
        int countLocked;
        Timer timer;
        long totalTimeLocked;
        Timer timer2;
        int countLocked2;
        Timer timer3;
        int countLocked3;
        long maxDurationMsLocked;
        int i20;
        String str53;
        StringBuilder sb3;
        Map<String, ? extends Timer> wakeupReasonStats;
        long j31;
        long j32;
        String str54;
        String[] strArr;
        int i21;
        int i22;
        boolean z7;
        int i23;
        int i24;
        int i25;
        long j33;
        String str55;
        long j34;
        String str56;
        int i26;
        String str57;
        String str58;
        String str59;
        String str60;
        String str61;
        String str62;
        long j35;
        String str63;
        long j36;
        int i27;
        String str64;
        if (i != 0) {
            printWriter.println("ERROR: BatteryStats.dump called for which type " + i + " but only STATS_SINCE_CHARGED is supported");
            return;
        }
        long jUptimeMillis = SystemClock.uptimeMillis() * 1000;
        long jElapsedRealtime = SystemClock.elapsedRealtime() * 1000;
        long j37 = (jElapsedRealtime + 500) / 1000;
        long batteryUptime = getBatteryUptime(jUptimeMillis);
        long jComputeBatteryUptime = computeBatteryUptime(jUptimeMillis, i);
        long j38 = batteryUptime;
        long jComputeBatteryRealtime = computeBatteryRealtime(jElapsedRealtime, i);
        long jComputeRealtime = computeRealtime(jElapsedRealtime, i);
        long jComputeUptime = computeUptime(jUptimeMillis, i);
        long jComputeBatteryScreenOffUptime = computeBatteryScreenOffUptime(jUptimeMillis, i);
        long jComputeBatteryScreenOffRealtime = computeBatteryScreenOffRealtime(jElapsedRealtime, i);
        long jComputeBatteryTimeRemaining = computeBatteryTimeRemaining(jElapsedRealtime);
        long jComputeChargeTimeRemaining = computeChargeTimeRemaining(jElapsedRealtime);
        long screenDozeTime = getScreenDozeTime(jElapsedRealtime, i);
        long subScreenDozeTime = getSubScreenDozeTime(jElapsedRealtime, i);
        StringBuilder sb4 = new StringBuilder(128);
        SparseArray<? extends Uid> uidStats = getUidStats();
        int size = uidStats.size();
        int estimatedBatteryCapacity = getEstimatedBatteryCapacity();
        if (estimatedBatteryCapacity > 0) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Estimated battery capacity: ");
            j = subScreenDozeTime;
            sb4.append(formatCharge(estimatedBatteryCapacity));
            sb4.append(" mAh");
            printWriter.println(sb4.toString());
        } else {
            j = subScreenDozeTime;
        }
        if (getLearnedBatteryCapacity() > 0) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Last learned battery capacity: ");
            sb4.append(formatCharge(r2 / 1000));
            sb4.append(" mAh");
            printWriter.println(sb4.toString());
        }
        if (getMinLearnedBatteryCapacity() > 0) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Min learned battery capacity: ");
            sb4.append(formatCharge(r2 / 1000));
            sb4.append(" mAh");
            printWriter.println(sb4.toString());
        }
        int i28 = 0;
        if (getMaxLearnedBatteryCapacity() > 0) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Max learned battery capacity: ");
            sb4.append(formatCharge(r2 / 1000));
            sb4.append(" mAh");
            printWriter.println(sb4.toString());
            i28 = 0;
        }
        sb4.setLength(i28);
        sb4.append(str);
        sb4.append("  Time on battery: ");
        long j39 = jComputeBatteryRealtime / 1000;
        formatTimeMs(sb4, j39);
        sb4.append(NavigationBarInflaterView.KEY_CODE_START);
        sb4.append(formatRatioLocked(jComputeBatteryRealtime, jComputeRealtime));
        sb4.append(") realtime, ");
        formatTimeMs(sb4, jComputeBatteryUptime / 1000);
        sb4.append(NavigationBarInflaterView.KEY_CODE_START);
        sb4.append(formatRatioLocked(jComputeBatteryUptime, jComputeBatteryRealtime));
        sb4.append(") uptime");
        printWriter.println(sb4.toString());
        sb4.setLength(0);
        sb4.append(str);
        sb4.append("  Time on battery screen off: ");
        formatTimeMs(sb4, jComputeBatteryScreenOffRealtime / 1000);
        sb4.append(NavigationBarInflaterView.KEY_CODE_START);
        sb4.append(formatRatioLocked(jComputeBatteryScreenOffRealtime, jComputeBatteryRealtime));
        sb4.append(") realtime, ");
        formatTimeMs(sb4, jComputeBatteryScreenOffUptime / 1000);
        sb4.append(NavigationBarInflaterView.KEY_CODE_START);
        sb4.append(formatRatioLocked(jComputeBatteryScreenOffUptime, jComputeBatteryRealtime));
        sb4.append(") uptime");
        printWriter.println(sb4.toString());
        sb4.setLength(0);
        sb4.append(str);
        sb4.append("  Time on battery screen doze: ");
        formatTimeMs(sb4, screenDozeTime / 1000);
        sb4.append(NavigationBarInflaterView.KEY_CODE_START);
        sb4.append(formatRatioLocked(screenDozeTime, jComputeBatteryRealtime));
        sb4.append(NavigationBarInflaterView.KEY_CODE_END);
        printWriter.println(sb4.toString());
        if (j > 0) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Time on battery sub screen doze: ");
            j2 = 0;
            formatTimeMs(sb4, j / 1000);
            sb4.append(NavigationBarInflaterView.KEY_CODE_START);
            sb4.append(formatRatioLocked(j, jComputeBatteryRealtime));
            sb4.append(NavigationBarInflaterView.KEY_CODE_END);
            printWriter.println(sb4.toString());
        } else {
            j2 = 0;
        }
        sb4.setLength(0);
        sb4.append(str);
        sb4.append("  Total run time: ");
        formatTimeMs(sb4, jComputeRealtime / 1000);
        sb4.append("realtime, ");
        formatTimeMs(sb4, jComputeUptime / 1000);
        sb4.append("uptime");
        printWriter.println(sb4.toString());
        if (jComputeBatteryTimeRemaining >= j2) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Battery time remaining: ");
            formatTimeMs(sb4, jComputeBatteryTimeRemaining / 1000);
            printWriter.println(sb4.toString());
        }
        if (jComputeChargeTimeRemaining >= j2) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Charge time remaining: ");
            formatTimeMs(sb4, jComputeChargeTimeRemaining / 1000);
            printWriter.println(sb4.toString());
        }
        long uahDischarge = getUahDischarge(i);
        if (uahDischarge >= j2) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Discharge: ");
            j3 = j39;
            sb4.append(formatCharge(uahDischarge / 1000.0d));
            sb4.append(" mAh");
            printWriter.println(sb4.toString());
        } else {
            j3 = j39;
        }
        long uahDischargeScreenOff = getUahDischargeScreenOff(i);
        if (uahDischargeScreenOff >= j2) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Screen off discharge: ");
            j4 = uahDischarge;
            sb4.append(formatCharge(uahDischargeScreenOff / 1000.0d));
            sb4.append(" mAh");
            printWriter.println(sb4.toString());
        } else {
            j4 = uahDischarge;
        }
        long uahDischargeScreenDoze = getUahDischargeScreenDoze(i);
        if (uahDischargeScreenDoze >= j2) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Screen doze discharge: ");
            sb4.append(formatCharge(uahDischargeScreenDoze / 1000.0d));
            sb4.append(" mAh");
            printWriter.println(sb4.toString());
        }
        long j40 = j4 - uahDischargeScreenOff;
        if (j40 >= j2) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Screen on discharge: ");
            sb4.append(formatCharge(j40 / 1000.0d));
            sb4.append(" mAh");
            printWriter.println(sb4.toString());
        }
        long uahDischargeLightDoze = getUahDischargeLightDoze(i);
        if (uahDischargeLightDoze >= j2) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Device light doze discharge: ");
            sb4.append(formatCharge(uahDischargeLightDoze / 1000.0d));
            sb4.append(" mAh");
            printWriter.println(sb4.toString());
        }
        long uahDischargeDeepDoze = getUahDischargeDeepDoze(i);
        if (uahDischargeDeepDoze >= j2) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Device deep doze discharge: ");
            sb4.append(formatCharge(uahDischargeDeepDoze / 1000.0d));
            sb4.append(" mAh");
            printWriter.println(sb4.toString());
        }
        printWriter.print("  Start clock time: ");
        printWriter.println(DateFormat.format("yyyy-MM-dd-HH-mm-ss", getStartClockTime()).toString());
        long j41 = jElapsedRealtime;
        long screenOnTime = getScreenOnTime(j41, i);
        long subScreenOnTime = getSubScreenOnTime(j41, i);
        long screenHighBrightnessTime = getScreenHighBrightnessTime(j41, i);
        long subScreenHighBrightnessTime = getSubScreenHighBrightnessTime(j41, i);
        long interactiveTime = getInteractiveTime(j41, i);
        long powerSaveModeEnabledTime = getPowerSaveModeEnabledTime(j41, i);
        long deviceIdleModeTime = getDeviceIdleModeTime(1, j41, i);
        long deviceIdleModeTime2 = getDeviceIdleModeTime(2, j41, i);
        long deviceIdlingTime = getDeviceIdlingTime(1, j41, i);
        long deviceIdlingTime2 = getDeviceIdlingTime(2, j41, i);
        long phoneOnTime = getPhoneOnTime(j41, i);
        getGlobalWifiRunningTime(j41, i);
        getWifiOnTime(j41, i);
        sb4.setLength(0);
        sb4.append(str);
        sb4.append("  Screen on: ");
        formatTimeMs(sb4, screenOnTime / 1000);
        String str65 = NavigationBarInflaterView.KEY_CODE_START;
        sb4.append(str65);
        sb4.append(formatRatioLocked(screenOnTime, jComputeBatteryRealtime));
        String str66 = ") ";
        sb4.append(") ");
        sb4.append(getScreenOnCount(i));
        sb4.append("x, Interactive: ");
        formatTimeMs(sb4, interactiveTime / 1000);
        sb4.append(str65);
        sb4.append(formatRatioLocked(interactiveTime, jComputeBatteryRealtime));
        sb4.append(NavigationBarInflaterView.KEY_CODE_END);
        printWriter.println(sb4.toString());
        String str67 = "x";
        if (subScreenOnTime <= j2) {
            str2 = NavigationBarInflaterView.KEY_CODE_END;
        } else {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Sub screen on: ");
            str2 = NavigationBarInflaterView.KEY_CODE_END;
            formatTimeMs(sb4, subScreenOnTime / 1000);
            sb4.append(str65);
            sb4.append(formatRatioLocked(subScreenOnTime, jComputeBatteryRealtime));
            sb4.append(") ");
            sb4.append(getSubScreenOnCount(i));
            sb4.append("x");
            printWriter.println(sb4.toString());
        }
        sb4.setLength(0);
        sb4.append(str);
        sb4.append("  Screen brightnesses:");
        int i29 = 0;
        boolean z8 = false;
        while (true) {
            str3 = "\n    ";
            str4 = str67;
            str5 = str66;
            str6 = " ";
            if (i29 >= 5) {
                break;
            }
            long j42 = jComputeBatteryRealtime;
            long screenBrightnessTime = getScreenBrightnessTime(i29, j41, i);
            long screenAutoBrightnessTime = getScreenAutoBrightnessTime(i29, j41, i);
            if (screenBrightnessTime == j2) {
                i27 = i29;
                str64 = str2;
            } else {
                sb4.append("\n    ");
                sb4.append(str);
                sb4.append(SCREEN_BRIGHTNESS_NAMES[i29]);
                sb4.append(" ");
                i27 = i29;
                formatTimeMs(sb4, screenBrightnessTime / 1000);
                sb4.append(str65);
                sb4.append(formatRatioLocked(screenBrightnessTime, screenOnTime));
                str64 = str2;
                sb4.append(str64);
                if (screenAutoBrightnessTime > j2) {
                    sb4.append(" -- auto ");
                    formatTimeMs(sb4, screenAutoBrightnessTime / 1000);
                }
                z8 = true;
            }
            i29 = i27 + 1;
            str2 = str64;
            str67 = str4;
            str66 = str5;
            jComputeBatteryRealtime = j42;
        }
        long j43 = jComputeBatteryRealtime;
        String str68 = str2;
        String str69 = " (no activity)";
        if (!z8) {
            sb4.append(" (no activity)");
        }
        printWriter.println(sb4.toString());
        if (subScreenOnTime > j2) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Sub screen brightnesses:");
            int i30 = 0;
            boolean z9 = false;
            while (i30 < 5) {
                long j44 = screenOnTime;
                long subScreenBrightnessTime = getSubScreenBrightnessTime(i30, j41, i);
                long subScreenAutoBrightnessTime = getSubScreenAutoBrightnessTime(i30, j41, i);
                if (subScreenBrightnessTime == j2) {
                    j35 = j41;
                    str63 = str6;
                    j36 = j44;
                } else {
                    sb4.append("\n    ");
                    sb4.append(str);
                    sb4.append(SCREEN_BRIGHTNESS_NAMES[i30]);
                    sb4.append(str6);
                    j35 = j41;
                    str63 = str6;
                    formatTimeMs(sb4, subScreenBrightnessTime / 1000);
                    sb4.append(str65);
                    j36 = j44;
                    sb4.append(formatRatioLocked(subScreenBrightnessTime, j36));
                    sb4.append(str68);
                    if (subScreenAutoBrightnessTime > j2) {
                        sb4.append(" -- auto ");
                        formatTimeMs(sb4, subScreenAutoBrightnessTime / 1000);
                    }
                    z9 = true;
                }
                i30++;
                screenOnTime = j36;
                str6 = str63;
                j41 = j35;
            }
            j5 = j41;
            str7 = str6;
            if (!z9) {
                sb4.append(" (no activity)");
            }
            printWriter2 = printWriter;
            printWriter2.println(sb4.toString());
        } else {
            printWriter2 = printWriter;
            j5 = j41;
            str7 = " ";
        }
        sb4.setLength(0);
        sb4.append(str);
        sb4.append("  Screen refresh rate:");
        int i31 = 0;
        boolean z10 = false;
        while (i31 < 4) {
            String str70 = str7;
            long j45 = j5;
            long totalTimeLocked2 = getMainDisplayRefreshRateTimer(i31).getTotalTimeLocked(j45, i);
            if (totalTimeLocked2 == j2) {
                str62 = str65;
                str61 = str70;
            } else {
                sb4.append("\n    ");
                sb4.append(str);
                sb4.append(Settings.Secure.refreshRateModeToString(i31).replace("REFRESH_RATE_MODE_", ""));
                str61 = str70;
                sb4.append(str61);
                str62 = str65;
                formatTimeMs(sb4, totalTimeLocked2 / 1000);
                z10 = true;
            }
            i31++;
            j5 = j45;
            str65 = str62;
            str7 = str61;
        }
        String str71 = str65;
        String str72 = str7;
        long j46 = j5;
        if (!z10) {
            sb4.append(" (disabled)");
        }
        printWriter2.println(sb4.toString());
        if (subScreenOnTime <= j2) {
            str8 = " (no activity)";
            str9 = "\n    ";
        } else {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Sub screen refresh rate:");
            int i32 = 0;
            boolean z11 = false;
            while (i32 < 4) {
                long totalTimeLocked3 = getCoverDisplayRefreshRateTimer(i32).getTotalTimeLocked(j46, i);
                if (totalTimeLocked3 == j2) {
                    str59 = str69;
                    str60 = str3;
                } else {
                    sb4.append(str3);
                    sb4.append(str);
                    sb4.append(Settings.Secure.refreshRateModeToString(i32).replace("REFRESH_RATE_MODE_", ""));
                    sb4.append(str72);
                    str59 = str69;
                    str60 = str3;
                    formatTimeMs(sb4, totalTimeLocked3 / 1000);
                    z11 = true;
                }
                i32++;
                str3 = str60;
                str69 = str59;
            }
            str8 = str69;
            str9 = str3;
            if (!z11) {
                sb4.append(" (disabled)");
            }
            printWriter2.println(sb4.toString());
        }
        if (screenHighBrightnessTime > j2) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Screen high brightness time: ");
            formatTimeMs(sb4, screenHighBrightnessTime / 1000);
            printWriter2.println(sb4.toString());
        }
        if (subScreenHighBrightnessTime > j2) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Sub screen high brightness time: ");
            formatTimeMs(sb4, subScreenHighBrightnessTime / 1000);
            printWriter2.println(sb4.toString());
        }
        if (powerSaveModeEnabledTime == j2) {
            str10 = str72;
            str11 = str9;
            j6 = j46;
            str12 = str71;
            j7 = j43;
            str13 = "";
        } else {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Power save mode enabled: ");
            formatTimeMs(sb4, powerSaveModeEnabledTime / 1000);
            str12 = str71;
            sb4.append(str12);
            str13 = "";
            j6 = j46;
            str10 = str72;
            str11 = str9;
            j7 = j43;
            sb4.append(formatRatioLocked(powerSaveModeEnabledTime, j7));
            sb4.append(str68);
            printWriter2.println(sb4.toString());
        }
        if (deviceIdlingTime != j2) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Device light idling: ");
            formatTimeMs(sb4, deviceIdlingTime / 1000);
            sb4.append(str12);
            sb4.append(formatRatioLocked(deviceIdlingTime, j7));
            str15 = str5;
            sb4.append(str15);
            sb4.append(getDeviceIdlingCount(1, i));
            str14 = str4;
            sb4.append(str14);
            printWriter2.println(sb4.toString());
        } else {
            str14 = str4;
            str15 = str5;
        }
        if (deviceIdleModeTime != j2) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Idle mode light time: ");
            str16 = str13;
            formatTimeMs(sb4, deviceIdleModeTime / 1000);
            sb4.append(str12);
            sb4.append(formatRatioLocked(deviceIdleModeTime, j7));
            sb4.append(str15);
            sb4.append(getDeviceIdleModeCount(1, i));
            sb4.append(str14);
            sb4.append(" -- longest ");
            formatTimeMs(sb4, getLongestDeviceIdleModeTime(1));
            printWriter2.println(sb4.toString());
        } else {
            str16 = str13;
        }
        if (deviceIdlingTime2 != j2) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Device full idling: ");
            formatTimeMs(sb4, deviceIdlingTime2 / 1000);
            sb4.append(str12);
            sb4.append(formatRatioLocked(deviceIdlingTime2, j7));
            sb4.append(str15);
            sb4.append(getDeviceIdlingCount(2, i));
            sb4.append(str14);
            printWriter2.println(sb4.toString());
        }
        if (deviceIdleModeTime2 != j2) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Idle mode full time: ");
            formatTimeMs(sb4, deviceIdleModeTime2 / 1000);
            sb4.append(str12);
            sb4.append(formatRatioLocked(deviceIdleModeTime2, j7));
            sb4.append(str15);
            sb4.append(getDeviceIdleModeCount(2, i));
            sb4.append(str14);
            sb4.append(" -- longest ");
            formatTimeMs(sb4, getLongestDeviceIdleModeTime(2));
            printWriter2.println(sb4.toString());
        }
        if (phoneOnTime != j2) {
            sb4.setLength(0);
            sb4.append(str);
            sb4.append("  Active phone call: ");
            formatTimeMs(sb4, phoneOnTime / 1000);
            sb4.append(str12);
            sb4.append(formatRatioLocked(phoneOnTime, j7));
            sb4.append(str15);
            sb4.append(getPhoneOnCount(i));
            sb4.append(str14);
            printWriter2.println(sb4.toString());
        }
        int numConnectivityChange = getNumConnectivityChange(i);
        if (numConnectivityChange != 0) {
            printWriter.print(str);
            printWriter2.print("  Connectivity changes: ");
            printWriter2.println(numConnectivityChange);
        }
        ArrayList arrayList2 = new ArrayList();
        long totalTimeLocked4 = j2;
        long j47 = totalTimeLocked4;
        int i33 = 0;
        while (true) {
            i3 = size;
            if (i33 >= i3) {
                break;
            }
            size = i3;
            SparseArray<? extends Uid> sparseArray2 = uidStats;
            Uid uidValueAt = sparseArray2.valueAt(i33);
            String str73 = str16;
            ArrayMap<String, ? extends Uid.Wakelock> wakelockStats = uidValueAt.getWakelockStats();
            int i34 = i33;
            int size2 = wakelockStats.size() - 1;
            while (size2 >= 0) {
                String str74 = str11;
                Uid.Wakelock wakelockValueAt = wakelockStats.valueAt(size2);
                String str75 = str14;
                SparseArray<? extends Uid> sparseArray3 = sparseArray2;
                Timer wakeTime = wakelockValueAt.getWakeTime(1);
                String str76 = str68;
                long j48 = j7;
                long j49 = j6;
                if (wakeTime != null) {
                    totalTimeLocked4 += wakeTime.getTotalTimeLocked(j49, i);
                }
                Timer wakeTime2 = wakelockValueAt.getWakeTime(0);
                if (wakeTime2 != null) {
                    long totalTimeLocked5 = wakeTime2.getTotalTimeLocked(j49, i);
                    if (totalTimeLocked5 > j2) {
                        if (i2 < 0) {
                            arrayList2.add(new TimerEntry(wakelockStats.keyAt(size2), uidValueAt.getUid(), wakeTime2, totalTimeLocked5));
                        }
                        j47 += totalTimeLocked5;
                    }
                }
                size2--;
                j6 = j49;
                str68 = str76;
                sparseArray2 = sparseArray3;
                str11 = str74;
                str14 = str75;
                j7 = j48;
            }
            i33 = i34 + 1;
            str16 = str73;
            str68 = str68;
            uidStats = sparseArray2;
            j7 = j7;
        }
        SparseArray<? extends Uid> sparseArray4 = uidStats;
        String str77 = str16;
        String str78 = str11;
        long j50 = j7;
        String str79 = str14;
        String str80 = str68;
        long j51 = j6;
        long networkActivityBytes = getNetworkActivityBytes(0, i);
        String str81 = str12;
        long networkActivityBytes2 = getNetworkActivityBytes(1, i);
        long networkActivityBytes3 = getNetworkActivityBytes(2, i);
        long networkActivityBytes4 = getNetworkActivityBytes(3, i);
        long networkActivityPackets = getNetworkActivityPackets(0, i);
        long networkActivityPackets2 = getNetworkActivityPackets(1, i);
        long networkActivityPackets3 = getNetworkActivityPackets(2, i);
        long networkActivityPackets4 = getNetworkActivityPackets(3, i);
        long networkActivityBytes5 = getNetworkActivityBytes(4, i);
        long networkActivityBytes6 = getNetworkActivityBytes(5, i);
        long mobileActiveTime = getMobileActiveTime(j51, i) / 1000;
        long mobileActive5GTime = getMobileActive5GTime(j51, i) / 1000;
        long silentLogOnScreenOnTime = getSilentLogOnScreenOnTime(j51, i) / 1000;
        long silentLogOnScreenOffTime = getSilentLogOnScreenOffTime(j51, i) / 1000;
        if (totalTimeLocked4 != j2) {
            sb = sb4;
            arrayList = arrayList2;
            sb.setLength(0);
            sb.append(str);
            sb.append("  Total full wakelock time: ");
            j8 = silentLogOnScreenOffTime;
            formatTimeMsNoSpace(sb, (totalTimeLocked4 + 500) / 1000);
            printWriter2.println(sb.toString());
        } else {
            j8 = silentLogOnScreenOffTime;
            sb = sb4;
            arrayList = arrayList2;
        }
        if (j47 != j2) {
            sb.setLength(0);
            sb.append(str);
            sb.append("  Total partial wakelock time: ");
            formatTimeMsNoSpace(sb, (j47 + 500) / 1000);
            printWriter2.println(sb.toString());
        }
        long wifiMulticastWakelockTime = getWifiMulticastWakelockTime(j51, i);
        int wifiMulticastWakelockCount = getWifiMulticastWakelockCount(i);
        if (wifiMulticastWakelockTime != j2) {
            sb.setLength(0);
            sb.append(str);
            sb.append("  Total WiFi Multicast wakelock Count: ");
            sb.append(wifiMulticastWakelockCount);
            printWriter2.println(sb.toString());
            sb.setLength(0);
            sb.append(str);
            sb.append("  Total WiFi Multicast wakelock time: ");
            formatTimeMsNoSpace(sb, (wifiMulticastWakelockTime + 500) / 1000);
            printWriter2.println(sb.toString());
        }
        int displayCount = getDisplayCount();
        if (displayCount > 1) {
            printWriter2.println(str77);
            printWriter.print(str);
            sb.setLength(0);
            sb.append(str);
            sb.append("  MULTI-DISPLAY POWER SUMMARY START");
            printWriter2.println(sb.toString());
            int i35 = 0;
            while (i35 < displayCount) {
                String str82 = str80;
                sb.setLength(0);
                sb.append(str);
                sb.append("  Display ");
                sb.append(i35);
                sb.append(" Statistics:");
                printWriter2.println(sb.toString());
                long j52 = mobileActive5GTime;
                long displayScreenOnTime = getDisplayScreenOnTime(i35, j51);
                sb.setLength(0);
                sb.append(str);
                sb.append("    Screen on: ");
                long j53 = mobileActiveTime;
                formatTimeMs(sb, displayScreenOnTime / 1000);
                String str83 = str81;
                sb.append(str83);
                int i36 = displayCount;
                long j54 = j50;
                sb.append(formatRatioLocked(displayScreenOnTime, j54));
                sb.append(str15);
                printWriter2.println(sb.toString());
                sb.setLength(0);
                sb.append("    Screen brightness levels:");
                int i37 = 0;
                boolean z12 = false;
                while (i37 < 5) {
                    long j55 = j54;
                    long displayScreenBrightnessTime = getDisplayScreenBrightnessTime(i35, i37, j51);
                    if (displayScreenBrightnessTime == j2) {
                        i26 = i37;
                        str56 = str10;
                        str58 = str82;
                        str57 = str15;
                    } else {
                        sb.append("\n      ");
                        sb.append(str);
                        sb.append(SCREEN_BRIGHTNESS_NAMES[i37]);
                        str56 = str10;
                        sb.append(str56);
                        i26 = i37;
                        str57 = str15;
                        formatTimeMs(sb, displayScreenBrightnessTime / 1000);
                        sb.append(str83);
                        sb.append(formatRatioLocked(displayScreenBrightnessTime, displayScreenOnTime));
                        str58 = str82;
                        sb.append(str58);
                        z12 = true;
                    }
                    str82 = str58;
                    str15 = str57;
                    i37 = i26 + 1;
                    str10 = str56;
                    j54 = j55;
                }
                j50 = j54;
                String str84 = str10;
                String str85 = str82;
                String str86 = str15;
                String str87 = str8;
                if (!z12) {
                    sb.append(str87);
                }
                printWriter2.println(sb.toString());
                long displayScreenDozeTime = getDisplayScreenDozeTime(i35, j51);
                sb.setLength(0);
                sb.append(str);
                sb.append("    Screen Doze: ");
                formatTimeMs(sb, displayScreenDozeTime / 1000);
                sb.append(str83);
                sb.append(formatRatioLocked(displayScreenDozeTime, j50));
                sb.append(str86);
                printWriter2.println(sb.toString());
                str10 = str84;
                str8 = str87;
                str81 = str83;
                mobileActive5GTime = j52;
                str15 = str86;
                i35++;
                str80 = str85;
                mobileActiveTime = j53;
                displayCount = i36;
            }
            j9 = mobileActiveTime;
            j10 = mobileActive5GTime;
            i4 = displayCount;
            str17 = str15;
            str18 = str80;
            str19 = str10;
            str20 = str8;
            str21 = str81;
            j11 = j50;
            printWriter.print(str);
            i5 = 0;
            sb.setLength(0);
            sb.append(str);
            sb.append("  MULTI-DISPLAY POWER SUMMARY END");
            printWriter2.println(sb.toString());
        } else {
            j9 = mobileActiveTime;
            j10 = mobileActive5GTime;
            i4 = displayCount;
            str17 = str15;
            str18 = str80;
            str19 = str10;
            str20 = str8;
            str21 = str81;
            j11 = j50;
            i5 = 0;
        }
        printWriter2.println(str77);
        printWriter.print(str);
        sb.setLength(i5);
        sb.append(str);
        sb.append("  Mobile info");
        sb.append("\n     Mobile active time: ");
        String str88 = str17;
        formatTimeMs(sb, j9);
        sb.append("\n     Mobile active 5G time: ");
        formatTimeMs(sb, j10);
        sb.append("\n     Silent log on screen on time: ");
        formatTimeMs(sb, silentLogOnScreenOnTime);
        sb.append("\n     Silent log on screen off time: ");
        String str89 = str20;
        formatTimeMs(sb, j8);
        printWriter2.println(sb.toString());
        printWriter2.println(str77);
        printWriter.print(str);
        sb.setLength(0);
        sb.append(str);
        sb.append("  CONNECTIVITY POWER SUMMARY START");
        printWriter2.println(sb.toString());
        printWriter.print(str);
        sb.setLength(0);
        sb.append(str);
        sb.append("  Logging duration for connectivity statistics: ");
        formatTimeMs(sb, j3);
        printWriter2.println(sb.toString());
        sb.setLength(0);
        sb.append(str);
        sb.append("  Cellular Statistics:");
        printWriter2.println(sb.toString());
        printWriter.print(str);
        sb.setLength(0);
        sb.append(str);
        sb.append("     Cellular kernel active time: ");
        long mobileRadioActiveTime = getMobileRadioActiveTime(j51, i);
        formatTimeMs(sb, mobileRadioActiveTime / 1000);
        sb.append(str21);
        sb.append(formatRatioLocked(mobileRadioActiveTime, j11));
        sb.append(str18);
        printWriter2.println(sb.toString());
        String str90 = str19;
        PrintWriter printWriter7 = printWriter2;
        StringBuilder sb5 = sb;
        String str91 = str88;
        printControllerActivity(printWriter7, sb5, str, "Cellular", getModemControllerActivity(), i);
        int i38 = i;
        printCellularPerRatBreakdown(printWriter, sb5, str + "     ", j37);
        printWriter.print("     Cellular data received: ");
        printWriter.println(formatBytesLocked(networkActivityBytes));
        printWriter.print("     Cellular data sent: ");
        printWriter.println(formatBytesLocked(networkActivityBytes2));
        printWriter.print("     Cellular packets received: ");
        printWriter.println(networkActivityPackets);
        printWriter.print("     Cellular packets sent: ");
        printWriter.println(networkActivityPackets2);
        sb5.setLength(0);
        sb5.append(str);
        sb5.append("     Cellular Radio Access Technology:");
        int i39 = 0;
        boolean z13 = false;
        while (i39 < NUM_DATA_CONNECTION_TYPES) {
            boolean z14 = z13;
            long phoneDataConnectionTime = getPhoneDataConnectionTime(i39, j51, i38);
            if (phoneDataConnectionTime == j2) {
                j34 = j51;
                str55 = str18;
                z13 = z14;
            } else {
                str55 = str18;
                sb5.append("\n       ");
                sb5.append(str);
                String[] strArr2 = DATA_CONNECTION_NAMES;
                sb5.append(i39 < strArr2.length ? strArr2[i39] : TimeZoneProviderService.TEST_COMMAND_RESULT_ERROR_KEY);
                sb5.append(str90);
                j34 = j51;
                formatTimeMs(sb5, phoneDataConnectionTime / 1000);
                sb5.append(str21);
                sb5.append(formatRatioLocked(phoneDataConnectionTime, j11));
                sb5.append(str91);
                if (i39 == 13) {
                    long nrNsaTime = getNrNsaTime(j34);
                    if (nrNsaTime != j2) {
                        sb5.append("\n         ");
                        sb5.append(str);
                        sb5.append("nr_nsa");
                        sb5.append(str90);
                        j34 = j34;
                        formatTimeMs(sb5, nrNsaTime / 1000);
                        sb5.append(str21);
                        sb5.append(formatRatioLocked(nrNsaTime, j11));
                        sb5.append(str91);
                    } else {
                        j34 = j34;
                    }
                }
                z13 = true;
            }
            i39++;
            i38 = i;
            j51 = j34;
            str18 = str55;
        }
        long j56 = j51;
        String str92 = str18;
        String str93 = str89;
        if (!z13) {
            sb5.append(str93);
        }
        printWriter.println(sb5.toString());
        sb5.setLength(0);
        sb5.append(str);
        sb5.append("     Cellular Rx signal strength (RSRP):");
        String[] strArr3 = {"very poor (less than -128dBm): ", "poor (-128dBm to -118dBm): ", "moderate (-118dBm to -108dBm): ", "good (-108dBm to -98dBm): ", "great (greater than -98dBm): "};
        int iMin = Math.min(CellSignalStrength.getNumSignalStrengthLevels(), 5);
        int i40 = 0;
        boolean z15 = false;
        while (i40 < iMin) {
            String[] strArr4 = strArr3;
            int i41 = iMin;
            String str94 = str93;
            boolean z16 = z15;
            long j57 = j56;
            long phoneSignalStrengthTime = getPhoneSignalStrengthTime(i40, j57, i);
            if (phoneSignalStrengthTime == j2) {
                j33 = j57;
                i25 = i40;
                z15 = z16;
            } else {
                i25 = i40;
                sb5.append("\n       ");
                sb5.append(str);
                sb5.append(strArr4[i25]);
                sb5.append(str90);
                j33 = j57;
                formatTimeMs(sb5, phoneSignalStrengthTime / 1000);
                sb5.append(str21);
                sb5.append(formatRatioLocked(phoneSignalStrengthTime, j11));
                sb5.append(str91);
                z15 = true;
            }
            i40 = i25 + 1;
            strArr3 = strArr4;
            iMin = i41;
            str93 = str94;
            j56 = j33;
        }
        long j58 = j56;
        if (!z15) {
            sb5.append(str93);
        }
        printWriter.println(sb5.toString());
        printWriter.print(str);
        sb5.setLength(0);
        sb5.append(str);
        sb5.append("  Wifi Statistics:");
        printWriter.println(sb5.toString());
        printWriter.print(str);
        sb5.setLength(0);
        sb5.append(str);
        sb5.append("     Wifi kernel active time: ");
        long wifiActiveTime = getWifiActiveTime(j58, i);
        formatTimeMs(sb5, wifiActiveTime / 1000);
        sb5.append(str21);
        sb5.append(formatRatioLocked(wifiActiveTime, j11));
        sb5.append(str92);
        printWriter.println(sb5.toString());
        long j59 = j37;
        long j60 = j58;
        printControllerActivity(printWriter, sb5, str, WIFI_CONTROLLER_NAME, getWifiControllerActivity(), i);
        printWriter.print("     Wifi data received: ");
        printWriter.println(formatBytesLocked(networkActivityBytes3));
        printWriter.print("     Wifi data sent: ");
        printWriter.println(formatBytesLocked(networkActivityBytes4));
        printWriter.print("     Wifi packets received: ");
        printWriter.println(networkActivityPackets3);
        printWriter.print("     Wifi packets sent: ");
        printWriter.println(networkActivityPackets4);
        sb5.setLength(0);
        sb5.append(str);
        sb5.append("     Wifi states:");
        int i42 = 0;
        boolean z17 = false;
        while (i42 < 8) {
            String str95 = str91;
            long wifiStateTime = getWifiStateTime(i42, j60, i);
            if (wifiStateTime == j2) {
                i24 = i42;
                str91 = str95;
            } else {
                sb5.append("\n       ");
                sb5.append(WIFI_STATE_NAMES[i42]);
                sb5.append(str90);
                i24 = i42;
                formatTimeMs(sb5, wifiStateTime / 1000);
                sb5.append(str21);
                sb5.append(formatRatioLocked(wifiStateTime, j11));
                str91 = str95;
                sb5.append(str91);
                z17 = true;
            }
            i42 = i24 + 1;
        }
        if (!z17) {
            sb5.append(str93);
        }
        printWriter.println(sb5.toString());
        sb5.setLength(0);
        sb5.append(str);
        sb5.append("     Wifi supplicant states:");
        int i43 = 0;
        boolean z18 = false;
        while (i43 < 13) {
            String str96 = str91;
            long wifiSupplStateTime = getWifiSupplStateTime(i43, j60, i);
            if (wifiSupplStateTime == j2) {
                i23 = i43;
                str91 = str96;
            } else {
                sb5.append("\n       ");
                sb5.append(WIFI_SUPPL_STATE_NAMES[i43]);
                sb5.append(str90);
                i23 = i43;
                formatTimeMs(sb5, wifiSupplStateTime / 1000);
                sb5.append(str21);
                sb5.append(formatRatioLocked(wifiSupplStateTime, j11));
                str91 = str96;
                sb5.append(str91);
                z18 = true;
            }
            i43 = i23 + 1;
        }
        if (!z18) {
            sb5.append(str93);
        }
        printWriter.println(sb5.toString());
        sb5.setLength(0);
        sb5.append(str);
        sb5.append("     Wifi Rx signal strength (RSSI):");
        String[] strArr5 = {"very poor (less than -88.75dBm): ", "poor (-88.75 to -77.5dBm): ", "moderate (-77.5dBm to -66.25dBm): ", "good (-66.25dBm to -55dBm): ", "great (greater than -55dBm): "};
        int iMin2 = Math.min(5, 5);
        int i44 = 0;
        boolean z19 = false;
        while (i44 < iMin2) {
            String str97 = str91;
            String str98 = str93;
            long wifiSignalStrengthTime = getWifiSignalStrengthTime(i44, j60, i);
            if (wifiSignalStrengthTime == j2) {
                strArr = strArr5;
                i21 = i44;
                i22 = iMin2;
                z7 = z19;
                str91 = str97;
                str54 = str78;
            } else {
                str54 = str78;
                sb5.append(str54);
                sb5.append(str);
                strArr = strArr5;
                sb5.append("     ");
                sb5.append(strArr[i44]);
                i21 = i44;
                i22 = iMin2;
                formatTimeMs(sb5, wifiSignalStrengthTime / 1000);
                sb5.append(str21);
                sb5.append(formatRatioLocked(wifiSignalStrengthTime, j11));
                str91 = str97;
                sb5.append(str91);
                z7 = true;
            }
            str78 = str54;
            str93 = str98;
            z19 = z7;
            i44 = i21 + 1;
            iMin2 = i22;
            strArr5 = strArr;
        }
        String str99 = str93;
        String str100 = str78;
        if (!z19) {
            sb5.append(str99);
        }
        printWriter.println(sb5.toString());
        printWriter.print(str);
        sb5.setLength(0);
        sb5.append(str);
        sb5.append("  GPS Statistics:");
        printWriter.println(sb5.toString());
        sb5.setLength(0);
        sb5.append(str);
        sb5.append("     GPS signal quality (Top 4 Average CN0):");
        String[] strArr6 = {"poor (less than 20 dBHz): ", "good (greater than 20 dBHz): "};
        int iMin3 = Math.min(2, 2);
        int i45 = 0;
        while (i45 < iMin3) {
            long j61 = j11;
            int i46 = iMin3;
            long gpsSignalQualityTime = getGpsSignalQualityTime(i45, j60, i);
            sb5.append(str100);
            sb5.append(str);
            String[] strArr7 = strArr6;
            sb5.append("  ");
            sb5.append(strArr7[i45]);
            formatTimeMs(sb5, gpsSignalQualityTime / 1000);
            sb5.append(str21);
            sb5.append(formatRatioLocked(gpsSignalQualityTime, j61));
            sb5.append(str91);
            i45++;
            iMin3 = i46;
            j11 = j61;
            strArr6 = strArr7;
            str99 = str99;
        }
        String str101 = str99;
        long j62 = j11;
        printWriter.println(sb5.toString());
        long gpsBatteryDrainMaMs = getGpsBatteryDrainMaMs();
        if (gpsBatteryDrainMaMs > j2) {
            printWriter.print(str);
            sb5.setLength(0);
            sb5.append(str);
            sb5.append("     GPS Battery Drain: ");
            sb5.append(new DecimalFormat("#.##").format(gpsBatteryDrainMaMs / 3600000.0d));
            sb5.append("mAh");
            printWriter.println(sb5.toString());
        }
        long screenOnGpsRunningTime = getScreenOnGpsRunningTime(j60, i);
        if (screenOnGpsRunningTime > j2) {
            i6 = 0;
            sb5.setLength(0);
            printWriter.print(str);
            sb5.append("     GPS run time while screen on: ");
            formatTimeMs(sb5, screenOnGpsRunningTime / 1000);
            printWriter.println(sb5.toString());
        } else {
            i6 = 0;
        }
        printWriter.print(str);
        sb5.setLength(i6);
        sb5.append(str);
        sb5.append("  CONNECTIVITY POWER SUMMARY END");
        printWriter.println(sb5.toString());
        printWriter.println(str77);
        printWriter.print(str);
        printWriter.print("  Bluetooth total received: ");
        printWriter.print(formatBytesLocked(networkActivityBytes5));
        printWriter.print(", sent: ");
        printWriter.println(formatBytesLocked(networkActivityBytes6));
        long bluetoothScanTime = getBluetoothScanTime(j60, i) / 1000;
        sb5.setLength(0);
        sb5.append(str);
        sb5.append("  Bluetooth scan time: ");
        formatTimeMs(sb5, bluetoothScanTime);
        printWriter.println(sb5.toString());
        printControllerActivity(printWriter, sb5, str, "Bluetooth", getBluetoothControllerActivity(), i);
        BatteryStats batteryStats2 = this;
        PrintWriter printWriter8 = printWriter;
        StringBuilder sb6 = sb5;
        String str102 = str;
        int i47 = i;
        printWriter8.println();
        printWriter.print(str);
        sb6.setLength(0);
        sb6.append(str102);
        sb6.append("  Speaker Statistics:");
        int i48 = 0;
        boolean z20 = false;
        while (true) {
            str22 = ": ";
            if (i48 >= 16) {
                break;
            }
            boolean z21 = z20;
            long speakerMediaTime = batteryStats2.getSpeakerMediaTime(i48, i47);
            if (speakerMediaTime == j2) {
                j32 = j60;
                z20 = z21;
            } else {
                sb6.append(str100);
                j32 = j60;
                sb6.append("level(media) " + i48);
                sb6.append(": ");
                formatTimeMs(sb6, speakerMediaTime);
                z20 = true;
            }
            i48++;
            j60 = j32;
        }
        boolean z22 = z20;
        long j63 = j60;
        for (int i49 = 0; i49 < 16; i49++) {
            long speakerCallTime = batteryStats2.getSpeakerCallTime(i49, i47);
            if (speakerCallTime != j2) {
                sb6.append(str100);
                sb6.append("level(call) " + i49);
                sb6.append(": ");
                formatTimeMs(sb6, speakerCallTime);
                z22 = true;
            }
        }
        if (!z22) {
            sb6.append(str101);
        }
        printWriter8.println(sb6.toString());
        printWriter8.println();
        printWriter.print(str);
        printWriter8.println("  Device battery use since last full charge");
        printWriter.print(str);
        printWriter8.print("    Amount discharged (lower bound): ");
        printWriter8.println(batteryStats2.getLowDischargeAmountSinceCharge());
        printWriter.print(str);
        printWriter8.print("    Amount discharged (upper bound): ");
        printWriter8.println(batteryStats2.getHighDischargeAmountSinceCharge());
        printWriter.print(str);
        printWriter8.print("    Amount discharged while screen on: ");
        printWriter8.println(batteryStats2.getDischargeAmountScreenOnSinceCharge());
        printWriter.print(str);
        printWriter8.print("    Amount discharged while screen off: ");
        printWriter8.println(batteryStats2.getDischargeAmountScreenOffSinceCharge());
        printWriter.print(str);
        printWriter8.print("    Amount discharged while screen doze: ");
        printWriter8.println(batteryStats2.getDischargeAmountScreenDozeSinceCharge());
        printWriter.print(str);
        printWriter8.print("    Amount discharged permil while screen on: ");
        printWriter8.println(batteryStats2.getDischargeAmountScreenOnSinceChargePermil());
        printWriter.print(str);
        printWriter8.print("    Amount discharged permil while screen off: ");
        printWriter8.println(batteryStats2.getDischargeAmountScreenOffSinceChargePermil());
        printWriter.print(str);
        printWriter8.print("    Amount discharged permil while screen doze: ");
        printWriter8.println(batteryStats2.getDischargeAmountScreenDozeSinceChargePermil());
        if (subScreenOnTime > j2) {
            printWriter.print(str);
            printWriter8.print("    Amount discharged permil while sub screen on: ");
            printWriter8.println(batteryStats2.getDischargeAmountSubScreenOnSinceChargePermil());
            printWriter.print(str);
            printWriter8.print("    Amount discharged permil while sub screen doze: ");
            printWriter8.println(batteryStats2.getDischargeAmountSubScreenDozeSinceChargePermil());
        }
        if (silentLogOnScreenOnTime + j8 > j2) {
            printWriter.print(str);
            printWriter8.print("    Amount discharged permil while screen on with silent log on: ");
            printWriter8.println(batteryStats2.getDischargeAmountSilentOnScreenOnSinceChargePermil());
            printWriter.print(str);
            printWriter8.print("    Amount discharged permil while screen off with silent log on: ");
            printWriter8.println(batteryStats2.getDischargeAmountSilentOnScreenOffSinceChargePermil());
        }
        printWriter8.println();
        BatteryUsageStats batteryUsageStats = batteryStatsDumpHelper.getBatteryUsageStats(batteryStats2, true);
        batteryUsageStats.dump(printWriter8, str102);
        List<UidMobileRadioStats> uidMobileRadioStats = batteryStats2.getUidMobileRadioStats(batteryUsageStats.getUidBatteryConsumers());
        if (uidMobileRadioStats.size() > 0) {
            printWriter.print(str);
            printWriter8.println("  Per-app mobile ms per packet:");
            long j64 = j2;
            int i50 = 0;
            while (i50 < uidMobileRadioStats.size()) {
                UidMobileRadioStats uidMobileRadioStats2 = uidMobileRadioStats.get(i50);
                sb6.setLength(0);
                sb6.append(str102);
                sb6.append("    Uid ");
                UserHandle.formatUid(sb6, uidMobileRadioStats2.uid);
                sb6.append(": ");
                sb6.append(formatValue(uidMobileRadioStats2.millisecondsPerPacket));
                sb6.append(" (");
                sb6.append(uidMobileRadioStats2.rxPackets + uidMobileRadioStats2.txPackets);
                sb6.append(" packets over ");
                formatTimeMsNoSpace(sb6, uidMobileRadioStats2.radioActiveMs);
                sb6.append(str91);
                sb6.append(uidMobileRadioStats2.radioActiveCount);
                sb6.append(str79);
                printWriter8.println(sb6);
                j64 += uidMobileRadioStats2.radioActiveMs;
                i50++;
                uidMobileRadioStats = uidMobileRadioStats;
            }
            str24 = str79;
            sb6.setLength(0);
            sb6.append(str102);
            sb6.append("    TOTAL TIME: ");
            formatTimeMs(sb6, j64);
            sb6.append(str21);
            sb6.append(batteryStats2.formatRatioLocked(j64, j62));
            str23 = str92;
            sb6.append(str23);
            printWriter8.println(sb6);
            printWriter8.println();
        } else {
            str23 = str92;
            str24 = str79;
        }
        Comparator<TimerEntry> comparator = new Comparator<TimerEntry>(batteryStats2) { // from class: android.os.BatteryStats.1
            @Override // java.util.Comparator
            public int compare(TimerEntry timerEntry, TimerEntry timerEntry2) {
                long j65 = timerEntry.mTime;
                long j66 = timerEntry2.mTime;
                if (j65 < j66) {
                    return 1;
                }
                return j65 > j66 ? -1 : 0;
            }
        };
        if (i2 >= 0) {
            str25 = str24;
            str26 = str91;
            str27 = str23;
            str28 = str90;
            j12 = j63;
            str29 = ": ";
        } else {
            Map<String, ? extends Timer> kernelWakelockStats = batteryStats2.getKernelWakelockStats();
            if (kernelWakelockStats.size() > 0) {
                ArrayList arrayList3 = new ArrayList();
                Iterator<Map.Entry<String, ? extends Timer>> it = kernelWakelockStats.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, ? extends Timer> next = it.next();
                    String str103 = str24;
                    Timer value = next.getValue();
                    Iterator<Map.Entry<String, ? extends Timer>> it2 = it;
                    long j65 = j63;
                    long jComputeWakeLock = computeWakeLock(value, j65, i47);
                    if (jComputeWakeLock > j2) {
                        arrayList3.add(new TimerEntry(next.getKey(), 0, value, jComputeWakeLock));
                    }
                    j63 = j65;
                    str24 = str103;
                    it = it2;
                }
                String str104 = str24;
                j12 = j63;
                if (arrayList3.size() > 0) {
                    Collections.sort(arrayList3, comparator);
                    printWriter.print(str);
                    printWriter8.println("  All kernel wake locks:");
                    int i51 = 0;
                    while (true) {
                        j31 = j12;
                        if (i51 >= arrayList3.size()) {
                            break;
                        }
                        TimerEntry timerEntry = (TimerEntry) arrayList3.get(i51);
                        sb6.setLength(0);
                        sb6.append(str102);
                        sb6.append("  Kernel Wake lock ");
                        sb6.append(timerEntry.mName);
                        String str105 = str22;
                        String str106 = str91;
                        String str107 = str23;
                        ArrayList arrayList4 = arrayList3;
                        String str108 = str104;
                        String str109 = str102;
                        StringBuilder sb7 = sb6;
                        int i52 = i51;
                        String strPrintWakeLock = printWakeLock(sb7, timerEntry.mTimer, j31, null, i47, ": ");
                        j12 = j31;
                        if (!strPrintWakeLock.equals(str105)) {
                            sb7.append(" realtime");
                            printWriter8.println(sb7.toString());
                        }
                        i51 = i52 + 1;
                        i47 = i;
                        sb6 = sb7;
                        str102 = str109;
                        str22 = str105;
                        str104 = str108;
                        str91 = str106;
                        str23 = str107;
                        arrayList3 = arrayList4;
                    }
                    str26 = str91;
                    str27 = str23;
                    str25 = str104;
                    j12 = j31;
                    str53 = str102;
                    sb3 = sb6;
                    str29 = str22;
                    printWriter8.println();
                    if (arrayList.size() <= 0) {
                        ArrayList arrayList5 = arrayList;
                        Collections.sort(arrayList5, comparator);
                        printWriter.print(str);
                        printWriter8.println("  All partial wake locks:");
                        int i53 = 0;
                        while (i53 < arrayList5.size()) {
                            TimerEntry timerEntry2 = (TimerEntry) arrayList5.get(i53);
                            sb3.setLength(0);
                            sb3.append("  Wake lock ");
                            UserHandle.formatUid(sb3, timerEntry2.mId);
                            sb3.append(str90);
                            ArrayList arrayList6 = arrayList5;
                            sb3.append(timerEntry2.mName);
                            long j66 = j12;
                            printWakeLock(sb3, timerEntry2.mTimer, j66, null, i, ": ");
                            StringBuilder sb8 = sb3;
                            j12 = j66;
                            sb8.append(" realtime");
                            printWriter8.println(sb8.toString());
                            i53++;
                            sb3 = sb8;
                            arrayList5 = arrayList6;
                        }
                        i47 = i;
                        ArrayList arrayList7 = arrayList5;
                        str28 = str90;
                        sb6 = sb3;
                        arrayList7.clear();
                        printWriter8.println();
                    } else {
                        i47 = i;
                        sb6 = sb3;
                        str28 = str90;
                    }
                    wakeupReasonStats = batteryStats2.getWakeupReasonStats();
                    if (wakeupReasonStats.size() > 0) {
                        printWriter.print(str);
                        printWriter8.println("  All wakeup reasons:");
                        ArrayList arrayList8 = new ArrayList();
                        Iterator<Map.Entry<String, ? extends Timer>> it3 = wakeupReasonStats.entrySet().iterator();
                        while (it3.hasNext()) {
                            Map.Entry<String, ? extends Timer> next2 = it3.next();
                            Iterator<Map.Entry<String, ? extends Timer>> it4 = it3;
                            arrayList8.add(new TimerEntry(next2.getKey(), 0, next2.getValue(), r0.getCountLocked(i47)));
                            it3 = it4;
                            j12 = j12;
                        }
                        long j67 = j12;
                        Collections.sort(arrayList8, comparator);
                        int i54 = 0;
                        while (i54 < arrayList8.size()) {
                            TimerEntry timerEntry3 = (TimerEntry) arrayList8.get(i54);
                            sb6.setLength(0);
                            sb6.append(str53);
                            sb6.append("  Wakeup reason ");
                            sb6.append(timerEntry3.mName);
                            StringBuilder sb9 = sb6;
                            ArrayList arrayList9 = arrayList8;
                            long j68 = j67;
                            printWakeLock(sb9, timerEntry3.mTimer, j68, null, i47, ": ");
                            sb6 = sb9;
                            sb6.append(" realtime");
                            printWriter8.println(sb6.toString());
                            i54++;
                            j67 = j68;
                            arrayList8 = arrayList9;
                        }
                        j12 = j67;
                        printWriter8.println();
                    }
                } else {
                    str26 = str91;
                    str27 = str23;
                    str25 = str104;
                }
            } else {
                str25 = str24;
                str26 = str91;
                str27 = str23;
                j12 = j63;
            }
            str53 = str102;
            sb3 = sb6;
            str29 = ": ";
            if (arrayList.size() <= 0) {
            }
            wakeupReasonStats = batteryStats2.getWakeupReasonStats();
            if (wakeupReasonStats.size() > 0) {
            }
        }
        Map<String, ? extends Counter> screenWakeStats = batteryStats2.getScreenWakeStats();
        if (screenWakeStats.size() > 0) {
            printWriter.print(str);
            printWriter8.println("  All screen wake reasons:");
            for (Map.Entry<String, ? extends Counter> entry : screenWakeStats.entrySet()) {
                Counter value2 = entry.getValue();
                sb6.setLength(0);
                sb6.append("  ");
                sb6.append(entry.getKey());
                sb6.append(str29);
                sb6.append(value2.getCountLocked(i47));
                sb6.append(" times");
                printWriter8.println(sb6.toString());
            }
            printWriter8.println();
        }
        LongSparseArray<? extends Timer> kernelMemoryStats = batteryStats2.getKernelMemoryStats();
        if (kernelMemoryStats.size() > 0) {
            printWriter8.println("  Memory Stats");
            for (int i55 = 0; i55 < kernelMemoryStats.size(); i55++) {
                sb6.setLength(0);
                sb6.append("  Bandwidth ");
                sb6.append(kernelMemoryStats.keyAt(i55));
                sb6.append(" Time ");
                sb6.append(kernelMemoryStats.valueAt(i55).getTotalTimeLocked(j12, i47));
                printWriter8.println(sb6.toString());
            }
            printWriter8.println();
        }
        Map<String, ? extends Timer> rpmStats = batteryStats2.getRpmStats();
        if (rpmStats.size() > 0) {
            printWriter.print(str);
            printWriter8.println("  Resource Power Manager Stats");
            if (rpmStats.size() > 0) {
                Iterator<Map.Entry<String, ? extends Timer>> it5 = rpmStats.entrySet().iterator();
                while (it5.hasNext()) {
                    Map.Entry<String, ? extends Timer> next3 = it5.next();
                    String key = next3.getKey();
                    Timer value3 = next3.getValue();
                    PrintWriter printWriter9 = printWriter8;
                    printTimer(printWriter9, sb6, value3, j12, i47, str, key);
                    j12 = j12;
                    str29 = str29;
                    it5 = it5;
                    printWriter8 = printWriter9;
                    sb6 = sb6;
                }
            }
            sb2 = sb6;
            j13 = j12;
            str30 = str29;
            printWriter3 = printWriter8;
            j14 = mobileRadioActiveTime;
            str31 = str26;
            printWriter3.println();
        } else {
            sb2 = sb6;
            j13 = j12;
            str30 = str29;
            printWriter3 = printWriter8;
            j14 = mobileRadioActiveTime;
            str31 = str26;
        }
        CpuScalingPolicies cpuScalingPolicies3 = batteryStats2.getCpuScalingPolicies();
        String str110 = ShaderAssembler.NEWLINE;
        if (cpuScalingPolicies3 != null) {
            j15 = j62;
            sb2.setLength(0);
            sb2.append("  CPU scaling: ");
            int[] policies = cpuScalingPolicies3.getPolicies();
            int length = policies.length;
            int i56 = 0;
            int i57 = 0;
            while (i56 < length) {
                int i58 = i56;
                int i59 = policies[i58];
                sb2.append(ShaderAssembler.NEWLINE);
                int i60 = length;
                sb2.append("    policy");
                sb2.append(i59);
                sb2.append(ShortcutConstants.SERVICES_SEPARATOR);
                int[] frequencies = cpuScalingPolicies3.getFrequencies(i59);
                int length2 = frequencies.length;
                CpuScalingPolicies cpuScalingPolicies4 = cpuScalingPolicies3;
                int i61 = 0;
                while (i61 < length2) {
                    int i62 = i61;
                    int i63 = frequencies[i62];
                    sb2.append(' ');
                    sb2.append(i63);
                    i57++;
                    i61 = i62 + 1;
                    frequencies = frequencies;
                }
                i56 = i58 + 1;
                length = i60;
                cpuScalingPolicies3 = cpuScalingPolicies4;
            }
            cpuScalingPolicies = cpuScalingPolicies3;
            printWriter3.println(sb2);
            printWriter3.println();
            i7 = i57;
        } else {
            cpuScalingPolicies = cpuScalingPolicies3;
            j15 = j62;
            i7 = 0;
        }
        long[][] kernelCpuSpeedTimes = batteryStats2.getKernelCpuSpeedTimes();
        if (kernelCpuSpeedTimes == null || kernelCpuSpeedTimes.length <= 0) {
            i8 = i7;
        } else {
            sb2.setLength(0);
            sb2.append("  CPU times (time_in_state): ");
            i8 = i7;
            int i64 = 0;
            while (i64 < kernelCpuSpeedTimes.length) {
                int i65 = cpuScalingPolicies != null ? cpuScalingPolicies.getPolicies()[Math.min(i64, cpuScalingPolicies.getPolicies().length - 1)] : i64;
                sb2.append(ShaderAssembler.NEWLINE);
                long[][] jArr2 = kernelCpuSpeedTimes;
                sb2.append("    policy");
                sb2.append(i65);
                sb2.append(ShortcutConstants.SERVICES_SEPARATOR);
                int i66 = 0;
                while (i66 < jArr2[i64].length) {
                    sb2.append(' ');
                    sb2.append(jArr2[i64][i66]);
                    i66++;
                    i64 = i64;
                }
                i64++;
                kernelCpuSpeedTimes = jArr2;
            }
            printWriter3.println(sb2);
            printWriter3.println();
        }
        long[][] screenOffKernelCpuSpeedTimes = batteryStats2.getScreenOffKernelCpuSpeedTimes();
        if (screenOffKernelCpuSpeedTimes != null && screenOffKernelCpuSpeedTimes.length > 0) {
            sb2.setLength(0);
            sb2.append("  Screen off CPU times (time_in_state): ");
            int i67 = 0;
            while (i67 < screenOffKernelCpuSpeedTimes.length) {
                if (cpuScalingPolicies != null) {
                    Math.min(i67, cpuScalingPolicies.getPolicies().length - 1);
                    i20 = cpuScalingPolicies.getPolicies()[i67];
                } else {
                    i20 = i67;
                }
                sb2.append(ShaderAssembler.NEWLINE);
                long[][] jArr3 = screenOffKernelCpuSpeedTimes;
                sb2.append("    policy");
                sb2.append(i20);
                sb2.append(ShortcutConstants.SERVICES_SEPARATOR);
                int i68 = 0;
                while (i68 < jArr3[i67].length) {
                    sb2.append(' ');
                    sb2.append(jArr3[i67][i68]);
                    i68++;
                    i67 = i67;
                }
                i67++;
                screenOffKernelCpuSpeedTimes = jArr3;
            }
            printWriter3.println(sb2);
            printWriter3.println();
        }
        int i69 = i3;
        int i70 = 0;
        while (i70 < i69) {
            String str111 = str110;
            SparseArray<? extends Uid> sparseArray5 = sparseArray4;
            int iKeyAt = sparseArray5.keyAt(i70);
            int i71 = i69;
            if (i2 < 0 || iKeyAt == i2 || iKeyAt == 1000) {
                Uid uidValueAt2 = sparseArray5.valueAt(i70);
                printWriter.print(str);
                sparseArray4 = sparseArray5;
                printWriter3.print("  ");
                UserHandle.formatUid(printWriter3, iKeyAt);
                printWriter3.println(":");
                String str112 = str31;
                i9 = i70;
                long networkActivityBytes7 = uidValueAt2.getNetworkActivityBytes(0, i47);
                long j69 = j14;
                long networkActivityBytes8 = uidValueAt2.getNetworkActivityBytes(1, i47);
                long networkActivityBytes9 = uidValueAt2.getNetworkActivityBytes(2, i47);
                long networkActivityBytes10 = uidValueAt2.getNetworkActivityBytes(3, i47);
                long networkActivityBytes11 = uidValueAt2.getNetworkActivityBytes(4, i47);
                long networkActivityBytes12 = uidValueAt2.getNetworkActivityBytes(5, i47);
                long networkActivityPackets5 = uidValueAt2.getNetworkActivityPackets(0, i47);
                String str113 = str25;
                long networkActivityPackets6 = uidValueAt2.getNetworkActivityPackets(1, i47);
                long networkActivityPackets7 = uidValueAt2.getNetworkActivityPackets(2, i47);
                long networkActivityPackets8 = uidValueAt2.getNetworkActivityPackets(3, i47);
                long mobileRadioActiveTime2 = uidValueAt2.getMobileRadioActiveTime(i47);
                int mobileRadioActiveCount = uidValueAt2.getMobileRadioActiveCount(i47);
                long fullWifiLockTime = uidValueAt2.getFullWifiLockTime(j13, i47);
                long wifiScanTime = uidValueAt2.getWifiScanTime(j13, i47);
                int wifiScanCount = uidValueAt2.getWifiScanCount(i47);
                int wifiScanBackgroundCount = uidValueAt2.getWifiScanBackgroundCount(i47);
                long wifiScanActualTime = uidValueAt2.getWifiScanActualTime(j13);
                long wifiScanBackgroundTime = uidValueAt2.getWifiScanBackgroundTime(j13);
                long wifiRunningTime = uidValueAt2.getWifiRunningTime(j13, i47);
                long j70 = j13;
                long mobileRadioApWakeupCount = uidValueAt2.getMobileRadioApWakeupCount(i47);
                long wifiRadioApWakeupCount = uidValueAt2.getWifiRadioApWakeupCount(i47);
                if (networkActivityBytes7 > j2 || networkActivityBytes8 > j2 || networkActivityPackets5 > j2 || networkActivityPackets6 > j2) {
                    printWriter.print(str);
                    printWriter3.print("    Mobile network: ");
                    printWriter3.print(batteryStats2.formatBytesLocked(networkActivityBytes7));
                    printWriter3.print(" received, ");
                    printWriter3.print(batteryStats2.formatBytesLocked(networkActivityBytes8));
                    printWriter3.print(" sent (packets ");
                    printWriter3.print(networkActivityPackets5);
                    printWriter3.print(" received, ");
                    j16 = networkActivityPackets6;
                    printWriter3.print(j16);
                    printWriter3.println(" sent)");
                } else {
                    j16 = networkActivityPackets6;
                }
                if (mobileRadioActiveTime2 > j2 || mobileRadioActiveCount > 0) {
                    sb2.setLength(0);
                    str32 = str;
                    sb2.append(str32);
                    i10 = wifiScanCount;
                    sb2.append("    Mobile radio active: ");
                    long j71 = mobileRadioActiveTime2 / 1000;
                    formatTimeMs(sb2, j71);
                    sb2.append(str21);
                    j17 = wifiRadioApWakeupCount;
                    sb2.append(batteryStats2.formatRatioLocked(mobileRadioActiveTime2, j69));
                    str33 = str112;
                    sb2.append(str33);
                    sb2.append(mobileRadioActiveCount);
                    str34 = str113;
                    sb2.append(str34);
                    long j72 = networkActivityPackets5 + j16;
                    if (j72 == j2) {
                        j72 = 1;
                    }
                    j18 = j69;
                    sb2.append(" @ ");
                    sb2.append(formatCharge(j71 / j72));
                    sb2.append(" mspp");
                    printWriter3.println(sb2.toString());
                } else {
                    str32 = str;
                    i10 = wifiScanCount;
                    str33 = str112;
                    j18 = j69;
                    j17 = wifiRadioApWakeupCount;
                    str34 = str113;
                }
                if (mobileRadioApWakeupCount > j2) {
                    sb2.setLength(0);
                    sb2.append(str32);
                    sb2.append("    Mobile radio AP wakeups: ");
                    sb2.append(mobileRadioApWakeupCount);
                    printWriter3.println(sb2.toString());
                }
                StringBuilder sb10 = sb2;
                Uid uid2 = uidValueAt2;
                int i72 = i10;
                printControllerActivityIfInteresting(printWriter3, sb10, str32 + "  ", "Cellular", uidValueAt2.getModemControllerActivity(), i);
                if (networkActivityBytes9 > j2 || networkActivityBytes10 > j2 || networkActivityPackets7 > j2 || networkActivityPackets8 > j2) {
                    printWriter.print(str);
                    printWriter3.print("    Wi-Fi network: ");
                    printWriter3.print(formatBytesLocked(networkActivityBytes9));
                    printWriter3.print(" received, ");
                    printWriter3.print(formatBytesLocked(networkActivityBytes10));
                    printWriter3.print(" sent (packets ");
                    printWriter3.print(networkActivityPackets7);
                    printWriter3.print(" received, ");
                    printWriter3.print(networkActivityPackets8);
                    printWriter3.println(" sent)");
                }
                if (fullWifiLockTime == j2 && wifiScanTime == j2 && i72 == 0 && wifiScanBackgroundCount == 0 && wifiScanActualTime == j2 && wifiScanBackgroundTime == j2 && wifiRunningTime == j2) {
                    i11 = 0;
                } else {
                    sb10.setLength(0);
                    sb10.append(str);
                    sb10.append("    Wifi Running: ");
                    formatTimeMs(sb10, wifiRunningTime / 1000);
                    sb10.append(str21);
                    long j73 = j15;
                    sb10.append(formatRatioLocked(wifiRunningTime, j73));
                    sb10.append(")\n");
                    sb10.append(str);
                    sb10.append("    Full Wifi Lock: ");
                    formatTimeMs(sb10, fullWifiLockTime / 1000);
                    sb10.append(str21);
                    sb10.append(formatRatioLocked(fullWifiLockTime, j73));
                    sb10.append(")\n");
                    sb10.append(str);
                    sb10.append("    Wifi Scan (blamed): ");
                    formatTimeMs(sb10, wifiScanTime / 1000);
                    sb10.append(str21);
                    sb10.append(formatRatioLocked(wifiScanTime, j73));
                    sb10.append(str33);
                    sb10.append(i72);
                    sb10.append("x\n");
                    sb10.append(str);
                    sb10.append("    Wifi Scan (actual): ");
                    formatTimeMs(sb10, wifiScanActualTime / 1000);
                    sb10.append(str21);
                    sb10.append(formatRatioLocked(wifiScanActualTime, computeBatteryRealtime(j70, 0)));
                    sb10.append(str33);
                    sb10.append(i72);
                    sb10.append("x\n");
                    sb10.append(str);
                    sb10.append("    Background Wifi Scan: ");
                    formatTimeMs(sb10, wifiScanBackgroundTime / 1000);
                    sb10.append(str21);
                    i11 = 0;
                    sb10.append(formatRatioLocked(wifiScanBackgroundTime, computeBatteryRealtime(j70, 0)));
                    sb10.append(str33);
                    sb10.append(wifiScanBackgroundCount);
                    sb10.append(str34);
                    printWriter3.println(sb10.toString());
                }
                if (j17 > j2) {
                    sb10.setLength(i11);
                    sb10.append(str);
                    sb10.append("    WiFi AP wakeups: ");
                    sb10.append(j17);
                    printWriter3.println(sb10.toString());
                }
                printControllerActivityIfInteresting(printWriter3, sb10, str + "  ", WIFI_CONTROLLER_NAME, uid2.getWifiControllerActivity(), i);
                PrintWriter printWriter10 = printWriter3;
                StringBuilder sb11 = sb10;
                int i73 = i;
                if (networkActivityBytes11 > j2 || networkActivityBytes12 > j2) {
                    printWriter.print(str);
                    printWriter10.print("    Bluetooth network: ");
                    printWriter10.print(formatBytesLocked(networkActivityBytes11));
                    printWriter10.print(" received, ");
                    printWriter10.print(formatBytesLocked(networkActivityBytes12));
                    printWriter10.println(" sent");
                }
                Timer bluetoothScanTimer = uid2.getBluetoothScanTimer();
                if (bluetoothScanTimer != null) {
                    long totalTimeLocked6 = (bluetoothScanTimer.getTotalTimeLocked(j70, i73) + 500) / 1000;
                    if (totalTimeLocked6 != j2) {
                        int countLocked4 = bluetoothScanTimer.getCountLocked(i73);
                        Timer bluetoothScanBackgroundTimer = uid2.getBluetoothScanBackgroundTimer();
                        if (bluetoothScanBackgroundTimer != null) {
                            str35 = str33;
                            countLocked = bluetoothScanBackgroundTimer.getCountLocked(i73);
                        } else {
                            str35 = str33;
                            countLocked = 0;
                        }
                        str36 = str34;
                        Timer bluetoothDutyScanTimer = uid2.getBluetoothDutyScanTimer();
                        if (bluetoothDutyScanTimer != null) {
                            timer = bluetoothDutyScanTimer;
                            j19 = j70;
                            totalTimeLocked = (bluetoothDutyScanTimer.getTotalTimeLocked(j70, i73) + 500) / 1000;
                        } else {
                            timer = bluetoothDutyScanTimer;
                            j19 = j70;
                            totalTimeLocked = j2;
                        }
                        int i74 = countLocked;
                        long j74 = j59;
                        long totalDurationMsLocked4 = bluetoothScanTimer.getTotalDurationMsLocked(j74);
                        long totalDurationMsLocked5 = bluetoothScanBackgroundTimer != null ? bluetoothScanBackgroundTimer.getTotalDurationMsLocked(j74) : j2;
                        if (uid2.getBluetoothScanResultCounter() != null) {
                            timer2 = bluetoothScanTimer;
                            countLocked2 = uid2.getBluetoothScanResultCounter().getCountLocked(i73);
                        } else {
                            timer2 = bluetoothScanTimer;
                            countLocked2 = 0;
                        }
                        if (uid2.getBluetoothScanResultBgCounter() != null) {
                            timer3 = bluetoothScanBackgroundTimer;
                            countLocked3 = uid2.getBluetoothScanResultBgCounter().getCountLocked(i73);
                        } else {
                            timer3 = bluetoothScanBackgroundTimer;
                            countLocked3 = 0;
                        }
                        str37 = str21;
                        Timer bluetoothUnoptimizedScanTimer = uid2.getBluetoothUnoptimizedScanTimer();
                        long totalDurationMsLocked6 = bluetoothUnoptimizedScanTimer != null ? bluetoothUnoptimizedScanTimer.getTotalDurationMsLocked(j74) : j2;
                        long maxDurationMsLocked2 = bluetoothUnoptimizedScanTimer != null ? bluetoothUnoptimizedScanTimer.getMaxDurationMsLocked(j74) : j2;
                        Timer bluetoothUnoptimizedScanBackgroundTimer = uid2.getBluetoothUnoptimizedScanBackgroundTimer();
                        long totalDurationMsLocked7 = bluetoothUnoptimizedScanBackgroundTimer != null ? bluetoothUnoptimizedScanBackgroundTimer.getTotalDurationMsLocked(j74) : j2;
                        if (bluetoothUnoptimizedScanBackgroundTimer != null) {
                            j59 = j74;
                            maxDurationMsLocked = bluetoothUnoptimizedScanBackgroundTimer.getMaxDurationMsLocked(j74);
                        } else {
                            j59 = j74;
                            maxDurationMsLocked = j2;
                        }
                        sb11.setLength(0);
                        sb11.append(str);
                        sb11.append("    Bluetooth Duty Scan (total actual realtime with duty): ");
                        formatTimeMs(sb11, totalTimeLocked);
                        if (timer != null && timer.isRunningLocked()) {
                            sb11.append(" (currently running)");
                        }
                        str39 = str111;
                        sb11.append(str39);
                        if (totalDurationMsLocked4 != totalTimeLocked6) {
                            sb11.append(str);
                            sb11.append("    Bluetooth Scan (total blamed realtime): ");
                            formatTimeMs(sb11, totalTimeLocked6);
                            sb11.append(" (");
                            sb11.append(countLocked4);
                            sb11.append(" times)");
                            if (timer2.isRunningLocked()) {
                                sb11.append(" (currently running)");
                            }
                            sb11.append(str39);
                        }
                        sb11.append(str);
                        sb11.append("    Bluetooth Scan (total actual realtime): ");
                        formatTimeMs(sb11, totalDurationMsLocked4);
                        sb11.append(" (");
                        sb11.append(countLocked4);
                        sb11.append(" times)");
                        if (timer2.isRunningLocked()) {
                            sb11.append(" (currently running)");
                        }
                        sb11.append(str39);
                        long j75 = totalDurationMsLocked5;
                        if (j75 > j2 || i74 > 0) {
                            sb11.append(str);
                            sb11.append("    Bluetooth Scan (background realtime): ");
                            formatTimeMs(sb11, j75);
                            sb11.append(" (");
                            sb11.append(i74);
                            sb11.append(" times)");
                            if (timer3 != null && timer3.isRunningLocked()) {
                                sb11.append(" (currently running in background)");
                            }
                            sb11.append(str39);
                        }
                        sb11.append(str);
                        sb11.append("    Bluetooth Scan Results: ");
                        sb11.append(countLocked2);
                        sb11.append(" (");
                        sb11.append(countLocked3);
                        sb11.append(" in background)");
                        long j76 = totalDurationMsLocked6;
                        long j77 = totalDurationMsLocked7;
                        if (j76 > j2 || j77 > j2) {
                            sb11.append(str39);
                            sb11.append(str);
                            sb11.append("    Unoptimized Bluetooth Scan (realtime): ");
                            formatTimeMs(sb11, j76);
                            sb11.append(" (max ");
                            formatTimeMs(sb11, maxDurationMsLocked2);
                            str38 = str27;
                            sb11.append(str38);
                            if (bluetoothUnoptimizedScanTimer != null && bluetoothUnoptimizedScanTimer.isRunningLocked()) {
                                sb11.append(" (currently running unoptimized)");
                            }
                            if (bluetoothUnoptimizedScanBackgroundTimer != null && j77 > j2) {
                                sb11.append(str39);
                                sb11.append(str);
                                sb11.append("    Unoptimized Bluetooth Scan (background realtime): ");
                                formatTimeMs(sb11, j77);
                                sb11.append(" (max ");
                                formatTimeMs(sb11, maxDurationMsLocked);
                                sb11.append(str38);
                                if (bluetoothUnoptimizedScanBackgroundTimer.isRunningLocked()) {
                                    sb11.append(" (currently running unoptimized in background)");
                                }
                            }
                        } else {
                            str38 = str27;
                        }
                        printWriter4 = printWriter;
                        printWriter4.println(sb11.toString());
                        z2 = true;
                    } else {
                        str35 = str33;
                        str36 = str34;
                        j19 = j70;
                        printWriter4 = printWriter10;
                        str37 = str21;
                        str38 = str27;
                        str39 = str111;
                        z2 = false;
                    }
                    if (uid2.hasUserActivity()) {
                        int i75 = 0;
                        boolean z23 = false;
                        while (i75 < Uid.NUM_USER_ACTIVITY_TYPES) {
                            Uid uid3 = uid2;
                            int userActivityCount = uid3.getUserActivityCount(i75, i73);
                            if (userActivityCount != 0) {
                                if (!z23) {
                                    sb11.setLength(0);
                                    sb11.append("    User activity: ");
                                    z23 = true;
                                } else {
                                    sb11.append(", ");
                                }
                                sb11.append(userActivityCount);
                                str52 = str28;
                                sb11.append(str52);
                                sb11.append(Uid.USER_ACTIVITY_TYPES[i75]);
                            } else {
                                str52 = str28;
                            }
                            i75++;
                            uid2 = uid3;
                            str28 = str52;
                        }
                        uid = uid2;
                        str40 = str28;
                        if (z23) {
                            printWriter4.println(sb11.toString());
                        }
                    } else {
                        uid = uid2;
                        str40 = str28;
                    }
                    ArrayMap<String, ? extends Uid.Wakelock> wakelockStats2 = uid.getWakelockStats();
                    boolean z24 = z2;
                    int size3 = wakelockStats2.size() - 1;
                    str111 = str39;
                    str27 = str38;
                    String str114 = str40;
                    String str115 = " times)";
                    long jComputeWakeLock2 = j2;
                    long jComputeWakeLock3 = jComputeWakeLock2;
                    long jComputeWakeLock4 = jComputeWakeLock3;
                    long jComputeWakeLock5 = jComputeWakeLock4;
                    int i76 = 0;
                    while (size3 >= 0) {
                        Uid.Wakelock wakelockValueAt2 = wakelockStats2.valueAt(size3);
                        sb11.setLength(0);
                        sb11.append(str);
                        sb11.append("    Wake lock ");
                        sb11.append(wakelockStats2.keyAt(size3));
                        int i77 = size3;
                        StringBuilder sb12 = sb11;
                        long j78 = jComputeWakeLock3;
                        ArrayMap<String, ? extends Uid.Wakelock> arrayMap = wakelockStats2;
                        int i78 = i76;
                        Uid uid4 = uid;
                        long j79 = j19;
                        String strPrintWakeLock2 = printWakeLock(sb12, wakelockValueAt2.getWakeTime(1), j79, "full", i73, ": ");
                        Timer wakeTime3 = wakelockValueAt2.getWakeTime(0);
                        i73 = i;
                        printWakeLock(sb12, wakelockValueAt2.getWakeTime(18), j79, "draw", i73, printWakeLock(sb12, wakelockValueAt2.getWakeTime(2), j79, Context.WINDOW_SERVICE, i, printWakeLock(sb12, wakeTime3 != null ? wakeTime3.getSubTimer() : null, j79, "background partial", i, printWakeLock(sb12, wakeTime3, j79, Slice.HINT_PARTIAL, i, strPrintWakeLock2))));
                        sb11 = sb12;
                        sb11.append(" realtime");
                        printWriter4.println(sb11.toString());
                        i76 = i78 + 1;
                        jComputeWakeLock2 += computeWakeLock(wakelockValueAt2.getWakeTime(1), j79, i73);
                        jComputeWakeLock4 += computeWakeLock(wakelockValueAt2.getWakeTime(0), j79, i73);
                        jComputeWakeLock5 += computeWakeLock(wakelockValueAt2.getWakeTime(2), j79, i73);
                        size3 = i77 - 1;
                        wakelockStats2 = arrayMap;
                        uid = uid4;
                        j19 = j79;
                        jComputeWakeLock3 = j78 + computeWakeLock(wakelockValueAt2.getWakeTime(18), j79, i73);
                        z24 = true;
                    }
                    long j80 = jComputeWakeLock3;
                    long j81 = j19;
                    Uid uid5 = uid;
                    if (i76 > 1) {
                        if (uid5.getAggregatedPartialWakelockTimer() != null) {
                            Timer aggregatedPartialWakelockTimer = uid5.getAggregatedPartialWakelockTimer();
                            boolean z25 = z24;
                            long j82 = j59;
                            long totalDurationMsLocked8 = aggregatedPartialWakelockTimer.getTotalDurationMsLocked(j82);
                            Timer subTimer = aggregatedPartialWakelockTimer.getSubTimer();
                            long totalDurationMsLocked9 = subTimer != null ? subTimer.getTotalDurationMsLocked(j82) : j2;
                            j59 = j82;
                            z3 = z25;
                            j29 = totalDurationMsLocked8;
                            j30 = totalDurationMsLocked9;
                        } else {
                            z3 = z24;
                            j29 = j2;
                            j30 = j29;
                        }
                        if (j29 != j2 || j30 != j2 || jComputeWakeLock2 != j2 || jComputeWakeLock4 != j2 || jComputeWakeLock5 != j2) {
                            sb11.setLength(0);
                            sb11.append(str);
                            sb11.append("    TOTAL wake: ");
                            if (jComputeWakeLock2 != j2) {
                                formatTimeMs(sb11, jComputeWakeLock2);
                                sb11.append("full");
                                z6 = true;
                            } else {
                                z6 = false;
                            }
                            if (jComputeWakeLock4 != j2) {
                                if (z6) {
                                    sb11.append(", ");
                                }
                                formatTimeMs(sb11, jComputeWakeLock4);
                                sb11.append("blamed partial");
                                z6 = true;
                            }
                            if (j29 != j2) {
                                if (z6) {
                                    sb11.append(", ");
                                }
                                formatTimeMs(sb11, j29);
                                sb11.append("actual partial");
                                z6 = true;
                            }
                            if (j30 != j2) {
                                if (z6) {
                                    sb11.append(", ");
                                }
                                formatTimeMs(sb11, j30);
                                sb11.append("actual background partial");
                                z6 = true;
                            }
                            if (jComputeWakeLock5 != j2) {
                                if (z6) {
                                    sb11.append(", ");
                                }
                                formatTimeMs(sb11, jComputeWakeLock5);
                                sb11.append(Context.WINDOW_SERVICE);
                                z6 = true;
                            }
                            if (j80 != j2) {
                                if (z6) {
                                    sb11.append(",");
                                }
                                formatTimeMs(sb11, j80);
                                sb11.append("draw");
                            }
                            sb11.append(" realtime");
                            printWriter4.println(sb11.toString());
                        }
                    } else {
                        z3 = z24;
                    }
                    Timer multicastWakelockStats = uid5.getMulticastWakelockStats();
                    int i79 = i;
                    if (multicastWakelockStats != null) {
                        long totalTimeLocked7 = multicastWakelockStats.getTotalTimeLocked(j81, i79);
                        int countLocked5 = multicastWakelockStats.getCountLocked(i79);
                        if (totalTimeLocked7 > j2) {
                            sb11.setLength(0);
                            sb11.append(str);
                            sb11.append("    WiFi Multicast Wakelock");
                            sb11.append(" count = ");
                            sb11.append(countLocked5);
                            sb11.append(" time = ");
                            formatTimeMsNoSpace(sb11, (totalTimeLocked7 + 500) / 1000);
                            printWriter4.println(sb11.toString());
                        }
                    }
                    ArrayMap<String, ? extends Timer> syncStats = uid5.getSyncStats();
                    int size4 = syncStats.size() - 1;
                    while (size4 >= 0) {
                        Timer timerValueAt = syncStats.valueAt(size4);
                        long totalTimeLocked8 = (timerValueAt.getTotalTimeLocked(j81, i79) + 500) / 1000;
                        int countLocked6 = timerValueAt.getCountLocked(i79);
                        Timer subTimer2 = timerValueAt.getSubTimer();
                        if (subTimer2 != null) {
                            j28 = j59;
                            totalDurationMsLocked3 = subTimer2.getTotalDurationMsLocked(j28);
                        } else {
                            j28 = j59;
                            totalDurationMsLocked3 = -1;
                        }
                        long j83 = totalDurationMsLocked3;
                        int countLocked7 = subTimer2 != null ? subTimer2.getCountLocked(i) : -1;
                        long j84 = j28;
                        sb11.setLength(0);
                        sb11.append(str);
                        sb11.append("    Sync ");
                        sb11.append(syncStats.keyAt(size4));
                        String str116 = str30;
                        sb11.append(str116);
                        if (totalTimeLocked8 != j2) {
                            formatTimeMs(sb11, totalTimeLocked8);
                            sb11.append("realtime (");
                            sb11.append(countLocked6);
                            str51 = str115;
                            sb11.append(str51);
                            if (j83 > j2) {
                                sb11.append(", ");
                                formatTimeMs(sb11, j83);
                                sb11.append("background (");
                                sb11.append(countLocked7);
                                sb11.append(str51);
                            }
                        } else {
                            str51 = str115;
                            sb11.append("(not used)");
                        }
                        printWriter4 = printWriter;
                        printWriter4.println(sb11.toString());
                        size4--;
                        str30 = str116;
                        str115 = str51;
                        i79 = i;
                        j59 = j84;
                        z3 = true;
                    }
                    String str117 = str115;
                    int i80 = i79;
                    String str118 = str30;
                    long j85 = j59;
                    ArrayMap<String, ? extends Timer> jobStats = uid5.getJobStats();
                    int size5 = jobStats.size() - 1;
                    boolean z26 = z3;
                    while (size5 >= 0) {
                        Timer timerValueAt2 = jobStats.valueAt(size5);
                        long totalTimeLocked9 = (timerValueAt2.getTotalTimeLocked(j81, i80) + 500) / 1000;
                        int countLocked8 = timerValueAt2.getCountLocked(i80);
                        Timer subTimer3 = timerValueAt2.getSubTimer();
                        if (subTimer3 != null) {
                            j26 = j81;
                            j27 = j85;
                            totalDurationMsLocked2 = subTimer3.getTotalDurationMsLocked(j27);
                        } else {
                            j26 = j81;
                            j27 = j85;
                            totalDurationMsLocked2 = -1;
                        }
                        long j86 = j27;
                        long j87 = totalDurationMsLocked2;
                        int countLocked9 = subTimer3 != null ? subTimer3.getCountLocked(i80) : -1;
                        sb11.setLength(0);
                        sb11.append(str);
                        sb11.append("    Job ");
                        sb11.append(jobStats.keyAt(size5));
                        sb11.append(str118);
                        if (totalTimeLocked9 != j2) {
                            formatTimeMs(sb11, totalTimeLocked9);
                            sb11.append("realtime (");
                            sb11.append(countLocked8);
                            sb11.append(str117);
                            if (j87 > j2) {
                                sb11.append(", ");
                                formatTimeMs(sb11, j87);
                                sb11.append("background (");
                                sb11.append(countLocked9);
                                sb11.append(str117);
                            }
                        } else {
                            sb11.append("(not used)");
                        }
                        printWriter4.println(sb11.toString());
                        size5--;
                        j81 = j26;
                        j85 = j86;
                        z26 = true;
                    }
                    long j88 = j81;
                    long j89 = j85;
                    ArrayMap<String, SparseIntArray> jobCompletionStats = uid5.getJobCompletionStats();
                    int size6 = jobCompletionStats.size() - 1;
                    while (size6 >= 0) {
                        SparseIntArray sparseIntArrayValueAt = jobCompletionStats.valueAt(size6);
                        if (sparseIntArrayValueAt != null) {
                            printWriter.print(str);
                            printWriter4.print("    Job Completions ");
                            printWriter4.print(jobCompletionStats.keyAt(size6));
                            printWriter4.print(":");
                            for (int i81 = 0; i81 < sparseIntArrayValueAt.size(); i81++) {
                                printWriter4.print(str114);
                                printWriter4.print(JobParameters.getInternalReasonCodeDescription(sparseIntArrayValueAt.keyAt(i81)));
                                printWriter4.print(str37);
                                printWriter4.print(sparseIntArrayValueAt.valueAt(i81));
                                printWriter4.print("x)");
                            }
                            str49 = str37;
                            str50 = str114;
                            printWriter4.println();
                        } else {
                            str49 = str37;
                            str50 = str114;
                        }
                        size6--;
                        str37 = str49;
                        str114 = str50;
                    }
                    Uid uid6 = uid5;
                    String str119 = str37;
                    String str120 = str114;
                    uid6.getDeferredJobsLineLocked(sb11, i80);
                    if (sb11.length() > 0) {
                        printWriter4.print("    Jobs deferred on launch ");
                        printWriter4.println(sb11.toString());
                    }
                    String str121 = str;
                    boolean z27 = z26;
                    str41 = str119;
                    PrintWriter printWriter11 = printWriter4;
                    long j90 = j89;
                    String str122 = str118;
                    long j91 = j88;
                    int i82 = i;
                    StringBuilder sb13 = sb11;
                    boolean zPrintTimer = printTimer(printWriter, sb11, uid6.getAudioTurnedOnTimer(), j91, i82, str121, "Audio") | z27 | printTimer(printWriter11, sb11, uid6.getFlashlightTurnedOnTimer(), j91, i80, str121, CoreSaConstant.VALUE_FLASHLIGHT) | printTimer(printWriter, sb11, uid6.getCameraTurnedOnTimer(), j91, i82, str121, CoreSaConstant.VALUE_CAMERA) | printTimer(printWriter, sb11, uid6.getVideoTurnedOnTimer(), j91, i82, str121, "Video");
                    SparseArray<? extends Uid.Sensor> sensorStats = uid6.getSensorStats();
                    int size7 = sensorStats.size();
                    boolean z28 = zPrintTimer;
                    int i83 = 0;
                    while (i83 < size7) {
                        Uid.Sensor sensorValueAt = sensorStats.valueAt(i83);
                        sensorStats.keyAt(i83);
                        int i84 = i83;
                        sb13.setLength(0);
                        sb13.append(str121);
                        sb13.append("    Sensor ");
                        int handle = sensorValueAt.getHandle();
                        if (handle == -10000) {
                            sb13.append("GPS");
                        } else if (handle == -10001) {
                            sb13.append("actualGPS");
                        } else {
                            sb13.append(handle);
                        }
                        sb13.append(str122);
                        Timer sensorTime = sensorValueAt.getSensorTime();
                        if (sensorTime != null) {
                            j24 = j91;
                            long totalTimeLocked10 = (sensorTime.getTotalTimeLocked(j91, i82) + 500) / 1000;
                            int countLocked10 = sensorTime.getCountLocked(i82);
                            sparseArray = sensorStats;
                            Timer sensorBackgroundTime = sensorValueAt.getSensorBackgroundTime();
                            int countLocked11 = sensorBackgroundTime != null ? sensorBackgroundTime.getCountLocked(i82) : 0;
                            str48 = str120;
                            i19 = size7;
                            long totalDurationMsLocked10 = sensorTime.getTotalDurationMsLocked(j90);
                            if (sensorBackgroundTime != null) {
                                j25 = j90;
                                totalDurationMsLocked = sensorBackgroundTime.getTotalDurationMsLocked(j90);
                            } else {
                                j25 = j90;
                                totalDurationMsLocked = j2;
                            }
                            if (totalTimeLocked10 != j2) {
                                if (totalDurationMsLocked10 != totalTimeLocked10) {
                                    formatTimeMs(sb13, totalTimeLocked10);
                                    sb13.append("blamed realtime, ");
                                }
                                formatTimeMs(sb13, totalDurationMsLocked10);
                                sb13.append("realtime (");
                                sb13.append(countLocked10);
                                sb13.append(str117);
                                if (totalDurationMsLocked != j2 || countLocked11 > 0) {
                                    sb13.append(", ");
                                    formatTimeMs(sb13, totalDurationMsLocked);
                                    sb13.append("background (");
                                    sb13.append(countLocked11);
                                    sb13.append(str117);
                                }
                            } else {
                                sb13.append("(not used)");
                            }
                        } else {
                            j24 = j91;
                            sparseArray = sensorStats;
                            j25 = j90;
                            str48 = str120;
                            i19 = size7;
                            sb13.append("(not used)");
                        }
                        printWriter.println(sb13.toString());
                        i83 = i84 + 1;
                        str121 = str;
                        i82 = i;
                        sensorStats = sparseArray;
                        size7 = i19;
                        j91 = j24;
                        j90 = j25;
                        str120 = str48;
                        z28 = true;
                    }
                    j59 = j90;
                    String str123 = str120;
                    int i85 = i;
                    long j92 = j91;
                    PrintWriter printWriter12 = printWriter;
                    sb2 = sb13;
                    boolean zPrintTimer2 = printTimer(printWriter, sb13, uid6.getForegroundServiceTimer(), j92, i85, str, "Foreground services") | z28 | printTimer(printWriter, sb13, uid6.getVibratorOnTimer(), j92, i85, str, "Vibrator") | printTimer(printWriter, sb13, uid6.getForegroundActivityTimer(), j92, i85, str, "Foreground activities");
                    if (uid6.hasSpeakerActivity()) {
                        sb2.setLength(0);
                        sb2.append(str);
                        sb2.append("    Total speaker time per level:");
                        int i86 = 0;
                        boolean z29 = false;
                        while (i86 < 16) {
                            long speakerMediaTime2 = uid6.getSpeakerMediaTime(i86, i85) / 1000;
                            if (speakerMediaTime2 == j2) {
                                str47 = str123;
                            } else {
                                str47 = str123;
                                sb2.append(str47 + i86 + ":" + speakerMediaTime2);
                                z29 = true;
                            }
                            i86++;
                            str123 = str47;
                        }
                        str42 = str123;
                        if (z29) {
                            printWriter12.println(sb2.toString());
                        }
                    } else {
                        str42 = str123;
                    }
                    long j93 = j2;
                    int i87 = 0;
                    while (i87 < 7) {
                        long processStateTime = uid6.getProcessStateTime(i87, j92, i85);
                        if (processStateTime > j2) {
                            long j94 = j93 + processStateTime;
                            sb2.setLength(0);
                            sb2.append(str);
                            sb2.append("    ");
                            sb2.append(Uid.PROCESS_STATE_NAMES[i87]);
                            sb2.append(" for: ");
                            formatTimeMs(sb2, (processStateTime + 500) / 1000);
                            printWriter12.println(sb2.toString());
                            i18 = i4;
                            if (i87 == 0 && i18 > 1) {
                                int i88 = 0;
                                while (i88 < i18) {
                                    long displayTopActivityTime = uid6.getDisplayTopActivityTime(i88, j92, i85);
                                    if (displayTopActivityTime > j2) {
                                        sb2.setLength(0);
                                        sb2.append(str);
                                        j23 = j92;
                                        sb2.append("      Display #" + i88 + str122);
                                        formatTimeMs(sb2, (displayTopActivityTime + 500) / 1000);
                                        printWriter12.println(sb2.toString());
                                    } else {
                                        j23 = j92;
                                    }
                                    i88++;
                                    j92 = j23;
                                }
                            }
                            j22 = j92;
                            j93 = j94;
                            zPrintTimer2 = true;
                        } else {
                            i18 = i4;
                            j22 = j92;
                        }
                        i87++;
                        i4 = i18;
                        j92 = j22;
                    }
                    int i89 = i4;
                    j20 = j92;
                    if (j93 > j2) {
                        sb2.setLength(0);
                        sb2.append(str);
                        sb2.append("    Total running: ");
                        formatTimeMs(sb2, (j93 + 500) / 1000);
                        printWriter12.println(sb2.toString());
                    }
                    long userCpuTimeUs = uid6.getUserCpuTimeUs(i85);
                    long systemCpuTimeUs = uid6.getSystemCpuTimeUs(i85);
                    if (userCpuTimeUs > j2 || systemCpuTimeUs > j2) {
                        sb2.setLength(0);
                        sb2.append(str);
                        sb2.append("    Total cpu time: u=");
                        formatTimeMs(sb2, userCpuTimeUs / 1000);
                        sb2.append("s=");
                        formatTimeMs(sb2, systemCpuTimeUs / 1000);
                        printWriter12.println(sb2.toString());
                    }
                    long[] cpuFreqTimes = uid6.getCpuFreqTimes(i85);
                    if (cpuFreqTimes != null) {
                        sb2.setLength(0);
                        sb2.append("    Total cpu time per freq:");
                        i12 = i8;
                        if (i12 == cpuFreqTimes.length) {
                            batteryStats = this;
                            cpuScalingPolicies2 = cpuScalingPolicies;
                            sb2.append(batteryStats.getScaledCpuFreqTimes(cpuScalingPolicies2, cpuFreqTimes));
                        } else {
                            batteryStats = this;
                            cpuScalingPolicies2 = cpuScalingPolicies;
                            int i90 = 0;
                            while (i90 < cpuFreqTimes.length) {
                                sb2.append(' ');
                                long[] jArr4 = cpuFreqTimes;
                                sb2.append(jArr4[i90]);
                                i90++;
                                zPrintTimer2 = zPrintTimer2;
                                cpuFreqTimes = jArr4;
                            }
                        }
                        z4 = zPrintTimer2;
                        printWriter12.println(sb2.toString());
                    } else {
                        batteryStats = this;
                        z4 = zPrintTimer2;
                        i12 = i8;
                        cpuScalingPolicies2 = cpuScalingPolicies;
                    }
                    long[] screenOffCpuFreqTimes = uid6.getScreenOffCpuFreqTimes(i85);
                    if (screenOffCpuFreqTimes != null) {
                        sb2.setLength(0);
                        sb2.append("    Total screen-off cpu time per freq:");
                        if (i12 == screenOffCpuFreqTimes.length) {
                            sb2.append(batteryStats.getScaledCpuFreqTimes(cpuScalingPolicies2, screenOffCpuFreqTimes));
                        } else {
                            int i91 = 0;
                            while (i91 < screenOffCpuFreqTimes.length) {
                                sb2.append(' ');
                                long[] jArr5 = screenOffCpuFreqTimes;
                                int i92 = i91;
                                sb2.append(jArr5[i92]);
                                i91 = i92 + 1;
                                screenOffCpuFreqTimes = jArr5;
                            }
                        }
                        printWriter12.println(sb2.toString());
                    }
                    int scalingStepCount = batteryStats.getCpuScalingPolicies().getScalingStepCount();
                    long[] jArr6 = new long[scalingStepCount];
                    int i93 = 0;
                    while (i93 < 7) {
                        if (uid6.getCpuFreqTimes(jArr6, i93)) {
                            sb2.setLength(0);
                            sb2.append("    Cpu times per freq at state ");
                            sb2.append(Uid.PROCESS_STATE_NAMES[i93]);
                            sb2.append(ShortcutConstants.SERVICES_SEPARATOR);
                            if (i12 == scalingStepCount) {
                                sb2.append(batteryStats.getScaledCpuFreqTimes(cpuScalingPolicies2, jArr6));
                            } else {
                                int i94 = 0;
                                while (i94 < scalingStepCount) {
                                    sb2.append(str42);
                                    sb2.append(jArr6[i94]);
                                    i94++;
                                    str122 = str122;
                                    i89 = i89;
                                }
                            }
                            i16 = i89;
                            str46 = str122;
                            printWriter12.println(sb2.toString());
                        } else {
                            i16 = i89;
                            str46 = str122;
                        }
                        if (uid6.getScreenOffCpuFreqTimes(jArr6, i93)) {
                            sb2.setLength(0);
                            sb2.append("    Screen-off cpu times per freq at state ");
                            sb2.append(Uid.PROCESS_STATE_NAMES[i93]);
                            sb2.append(ShortcutConstants.SERVICES_SEPARATOR);
                            if (i12 == scalingStepCount) {
                                sb2.append(batteryStats.getScaledCpuFreqTimes(cpuScalingPolicies2, jArr6));
                            } else {
                                int i95 = 0;
                                while (i95 < scalingStepCount) {
                                    sb2.append(str42);
                                    long[] jArr7 = jArr6;
                                    sb2.append(jArr7[i95]);
                                    i95++;
                                    scalingStepCount = scalingStepCount;
                                    jArr6 = jArr7;
                                }
                            }
                            i17 = scalingStepCount;
                            jArr = jArr6;
                            printWriter12.println(sb2.toString());
                        } else {
                            i17 = scalingStepCount;
                            jArr = jArr6;
                        }
                        i93++;
                        scalingStepCount = i17;
                        jArr6 = jArr;
                        str122 = str46;
                        i89 = i16;
                    }
                    i13 = i89;
                    String str124 = str122;
                    ArrayMap<String, ? extends Uid.Proc> processStats = uid6.getProcessStats();
                    int size8 = processStats.size() - 1;
                    boolean z30 = z4;
                    while (size8 >= 0) {
                        Uid.Proc procValueAt = processStats.valueAt(size8);
                        boolean z31 = z30;
                        long userTime = procValueAt.getUserTime(i85);
                        Uid uid7 = uid6;
                        long systemTime = procValueAt.getSystemTime(i85);
                        String str125 = str42;
                        long foregroundTime = procValueAt.getForegroundTime(i85);
                        CpuScalingPolicies cpuScalingPolicies5 = cpuScalingPolicies2;
                        int starts = procValueAt.getStarts(i85);
                        int i96 = i12;
                        int numCrashes = procValueAt.getNumCrashes(i85);
                        int numAnrs = procValueAt.getNumAnrs(i85);
                        int iCountExcessivePowers = i85 == 0 ? procValueAt.countExcessivePowers() : 0;
                        if (userTime == j2 && systemTime == j2 && foregroundTime == j2 && starts == 0 && iCountExcessivePowers == 0 && numCrashes == 0 && numAnrs == 0) {
                            z30 = z31;
                            printWriter6 = printWriter;
                        } else {
                            Uid.Proc proc = procValueAt;
                            sb2.setLength(0);
                            sb2.append(str);
                            sb2.append("    Proc ");
                            sb2.append(processStats.keyAt(size8));
                            sb2.append(":\n");
                            sb2.append(str);
                            sb2.append("      CPU: ");
                            formatTimeMs(sb2, userTime);
                            sb2.append("usr + ");
                            formatTimeMs(sb2, systemTime);
                            sb2.append("krn ; ");
                            formatTimeMs(sb2, foregroundTime);
                            sb2.append(FOREGROUND_ACTIVITY_DATA);
                            if (starts == 0 && numCrashes == 0 && numAnrs == 0) {
                                str44 = str111;
                            } else {
                                str44 = str111;
                                sb2.append(str44);
                                sb2.append(str);
                                sb2.append("      ");
                                if (starts != 0) {
                                    sb2.append(starts);
                                    sb2.append(" starts");
                                    z5 = true;
                                } else {
                                    z5 = false;
                                }
                                if (numCrashes != 0) {
                                    if (z5) {
                                        sb2.append(", ");
                                    }
                                    sb2.append(numCrashes);
                                    sb2.append(" crashes");
                                    z5 = true;
                                }
                                if (numAnrs != 0) {
                                    if (z5) {
                                        sb2.append(", ");
                                    }
                                    sb2.append(numAnrs);
                                    sb2.append(" anrs");
                                }
                            }
                            printWriter6 = printWriter;
                            printWriter6.println(sb2.toString());
                            int i97 = 0;
                            while (i97 < iCountExcessivePowers) {
                                Uid.Proc proc2 = proc;
                                Uid.Proc.ExcessivePower excessivePower = proc2.getExcessivePower(i97);
                                if (excessivePower != null) {
                                    printWriter.print(str);
                                    printWriter6.print("      * Killed for ");
                                    if (excessivePower.type == 2) {
                                        printWriter6.print(CPU_DATA);
                                    } else {
                                        printWriter6.print("unknown");
                                    }
                                    printWriter6.print(" use: ");
                                    TimeUtils.formatDuration(excessivePower.usedTime, printWriter6);
                                    printWriter6.print(" over ");
                                    TimeUtils.formatDuration(excessivePower.overTime, printWriter6);
                                    if (excessivePower.overTime != j2) {
                                        printWriter6.print(" (");
                                        str45 = str44;
                                        printWriter6.print((excessivePower.usedTime * 100) / excessivePower.overTime);
                                        printWriter6.println("%)");
                                    } else {
                                        str45 = str44;
                                    }
                                }
                                i97++;
                                str44 = str45;
                                proc = proc2;
                            }
                            str111 = str44;
                            z30 = true;
                        }
                        size8--;
                        i85 = i;
                        printWriter12 = printWriter6;
                        cpuScalingPolicies2 = cpuScalingPolicies5;
                        i12 = i96;
                        uid6 = uid7;
                        str42 = str125;
                    }
                    printWriter5 = printWriter12;
                    cpuScalingPolicies = cpuScalingPolicies2;
                    i14 = i12;
                    str28 = str42;
                    ArrayMap<String, ? extends Uid.Pkg> packageStats = uid6.getPackageStats();
                    int size9 = packageStats.size() - 1;
                    boolean z32 = z30;
                    while (size9 >= 0) {
                        printWriter.print(str);
                        printWriter5.print("    Apk ");
                        printWriter5.print(packageStats.keyAt(size9));
                        printWriter5.println(":");
                        Uid.Pkg pkgValueAt = packageStats.valueAt(size9);
                        ArrayMap<String, ? extends Counter> wakeupAlarmStats = pkgValueAt.getWakeupAlarmStats();
                        int size10 = wakeupAlarmStats.size() - 1;
                        boolean z33 = false;
                        while (size10 >= 0) {
                            printWriter.print(str);
                            printWriter5.print("      Wakeup alarm ");
                            printWriter5.print(wakeupAlarmStats.keyAt(size10));
                            printWriter5.print(str124);
                            printWriter5.print(wakeupAlarmStats.valueAt(size10).getCountLocked(i));
                            printWriter5.println(" times");
                            size10--;
                            z33 = true;
                        }
                        String str126 = str124;
                        ArrayMap<String, ? extends Uid.Pkg.Serv> serviceStats = pkgValueAt.getServiceStats();
                        int size11 = serviceStats.size() - 1;
                        while (size11 >= 0) {
                            Uid.Pkg.Serv servValueAt = serviceStats.valueAt(size11);
                            long j95 = j38;
                            long startTime = servValueAt.getStartTime(j95, i);
                            int starts2 = servValueAt.getStarts(i);
                            int launches = servValueAt.getLaunches(i);
                            if (startTime != j2 || starts2 != 0 || launches != 0) {
                                sb2.setLength(0);
                                sb2.append(str);
                                sb2.append("      Service ");
                                sb2.append(serviceStats.keyAt(size11));
                                sb2.append(":\n");
                                sb2.append(str);
                                sb2.append("        Created for: ");
                                formatTimeMs(sb2, startTime / 1000);
                                sb2.append("uptime\n");
                                sb2.append(str);
                                sb2.append("        Starts: ");
                                sb2.append(starts2);
                                sb2.append(", launches: ");
                                sb2.append(launches);
                                printWriter5 = printWriter;
                                printWriter5.println(sb2.toString());
                                z33 = true;
                            }
                            size11--;
                            j38 = j95;
                        }
                        long j96 = j38;
                        if (!z33) {
                            printWriter.print(str);
                            printWriter5.println("      (nothing executed)");
                        }
                        size9--;
                        str124 = str126;
                        j38 = j96;
                        z32 = true;
                    }
                    i15 = i;
                    j21 = j38;
                    str43 = str124;
                    if (!z32) {
                        printWriter.print(str);
                        printWriter5.println("    (nothing executed)");
                    }
                }
            } else {
                j20 = j13;
                sparseArray4 = sparseArray5;
                j18 = j14;
                str36 = str25;
                str35 = str31;
                i9 = i70;
                str41 = str21;
                j21 = j38;
                str43 = str30;
                i14 = i8;
                i13 = i4;
                printWriter5 = printWriter3;
                i15 = i47;
            }
            batteryStats2 = this;
            str110 = str111;
            str30 = str43;
            i47 = i15;
            j38 = j21;
            i4 = i13;
            i69 = i71;
            str25 = str36;
            i8 = i14;
            str21 = str41;
            j14 = j18;
            j13 = j20;
            i70 = i9 + 1;
            printWriter3 = printWriter5;
            str31 = str35;
        }
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
            String[] strArrSplit = printNextItem(historyItem, j, true, z).split(ShaderAssembler.NEWLINE);
            for (String str : strArrSplit) {
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
            long jStart = protoOutputStream.start(j);
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
            protoOutputStream.end(jStart);
            i4 = i + 1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:124:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0102 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:176:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01c8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void dumpHistory(PrintWriter printWriter, int i, long j, boolean z) throws Throwable {
        BatteryStatsHistoryIterator batteryStatsHistoryIterator;
        long j2;
        long j3;
        Object obj;
        HistoryEventTracker historyEventTracker;
        boolean z2;
        HistoryPrinter historyPrinter;
        int i2;
        PrintWriter printWriter2 = printWriter;
        int i3 = i;
        boolean z3 = z;
        HistoryPrinter historyPrinter2 = new HistoryPrinter(i3);
        synchronized (this) {
            if (!z3) {
                long historyTotalSize = getHistoryTotalSize();
                long historyUsedSize = getHistoryUsedSize();
                printWriter2.print("Battery History");
                if (historyPrinter2.mFormatVersion != 1) {
                    printWriter2.print(" [Format: " + historyPrinter2.mFormatVersion + NavigationBarInflaterView.SIZE_MOD_END);
                }
                printWriter2.print(" (");
                printWriter2.print((100 * historyUsedSize) / historyTotalSize);
                printWriter2.print("% used, ");
                printSizeValue(printWriter2, historyUsedSize);
                printWriter2.print(" used of ");
                printSizeValue(printWriter2, historyTotalSize);
                printWriter2.print(", ");
                printWriter2.print(getHistoryStringPoolSize());
                printWriter2.print(" strings using ");
                printSizeValue(printWriter2, getHistoryStringPoolBytes());
                printWriter2.println("):");
            } else {
                dumpHistoryTagPoolLocked(printWriter2, z3);
            }
        }
        long j4 = 0;
        long j5 = -1;
        BatteryStatsHistoryIterator batteryStatsHistoryIteratorIterateBatteryStatsHistory = iterateBatteryStatsHistory(0L, -1L);
        HistoryPrinter historyPrinter3 = historyPrinter2;
        long j6 = -1;
        boolean z4 = false;
        HistoryEventTracker historyEventTracker2 = null;
        while (true) {
            try {
                HistoryItem next = batteryStatsHistoryIteratorIterateBatteryStatsHistory.next();
                if (next == null) {
                    break;
                }
                j2 = j4;
                try {
                    long j7 = next.time;
                    if (j5 < j2) {
                        j5 = j7;
                    }
                    try {
                        if (next.time >= j) {
                            if (j < j2 || z4) {
                                j3 = j7;
                                batteryStatsHistoryIterator = batteryStatsHistoryIteratorIterateBatteryStatsHistory;
                                obj = null;
                                historyEventTracker = historyEventTracker2;
                                z2 = z4;
                            } else {
                                try {
                                    if (next.cmd != 5) {
                                        try {
                                            if (next.cmd != 7 && next.cmd != 4 && next.cmd != 8) {
                                                batteryStatsHistoryIterator = batteryStatsHistoryIteratorIterateBatteryStatsHistory;
                                                try {
                                                    if (next.currentTime != j2) {
                                                        byte b = next.cmd;
                                                        next.cmd = (byte) 5;
                                                        historyEventTracker = historyEventTracker2;
                                                        HistoryPrinter historyPrinter4 = historyPrinter3;
                                                        historyPrinter4.printNextItem(printWriter2, next, j5, z3, (i3 & 32) != 0);
                                                        next.cmd = b;
                                                        historyPrinter = historyPrinter4;
                                                    } else {
                                                        historyEventTracker = historyEventTracker2;
                                                        z2 = z4;
                                                        historyPrinter = historyPrinter3;
                                                        if (historyEventTracker != null) {
                                                            try {
                                                                if (next.cmd != 0) {
                                                                    historyPrinter.printNextItem(printWriter, next, j5, z, (i3 & 32) != 0);
                                                                    i2 = 0;
                                                                    next.cmd = (byte) 0;
                                                                } else {
                                                                    i2 = 0;
                                                                }
                                                                int i4 = next.eventCode;
                                                                HistoryTag historyTag = next.eventTag;
                                                                next.eventTag = new HistoryTag();
                                                                int i5 = i2;
                                                                while (i5 < 23) {
                                                                    HashMap<String, SparseIntArray> stateForEvent = historyEventTracker.getStateForEvent(i5);
                                                                    if (stateForEvent != null) {
                                                                        for (Map.Entry<String, SparseIntArray> entry : stateForEvent.entrySet()) {
                                                                            SparseIntArray value = entry.getValue();
                                                                            HistoryPrinter historyPrinter5 = historyPrinter;
                                                                            int i6 = 0;
                                                                            while (i6 < value.size()) {
                                                                                next.eventCode = i5;
                                                                                int i7 = i4;
                                                                                next.eventTag.string = entry.getKey();
                                                                                next.eventTag.uid = value.keyAt(i6);
                                                                                next.eventTag.poolIdx = value.valueAt(i6);
                                                                                int i8 = i6;
                                                                                long j8 = j7;
                                                                                int i9 = i5;
                                                                                HistoryPrinter historyPrinter6 = historyPrinter5;
                                                                                HistoryTag historyTag2 = historyTag;
                                                                                historyPrinter6.printNextItem(printWriter, next, j5, z, (i & 32) != 0);
                                                                                historyPrinter5 = historyPrinter6;
                                                                                next.wakeReasonTag = null;
                                                                                next.wakelockTag = null;
                                                                                i4 = i7;
                                                                                i6 = i8 + 1;
                                                                                historyTag = historyTag2;
                                                                                i5 = i9;
                                                                                j7 = j8;
                                                                            }
                                                                            historyPrinter = historyPrinter5;
                                                                        }
                                                                    }
                                                                    j3 = j7;
                                                                    try {
                                                                        i5++;
                                                                        i4 = i4;
                                                                        historyTag = historyTag;
                                                                        historyPrinter = historyPrinter;
                                                                        j7 = j3;
                                                                    } catch (Throwable th) {
                                                                        th = th;
                                                                        printWriter2 = printWriter;
                                                                        j6 = j3;
                                                                        try {
                                                                            th.printStackTrace(printWriter2);
                                                                            Slog.wtf(TAG, "Corrupted battery history", th);
                                                                            if (batteryStatsHistoryIterator != null) {
                                                                            }
                                                                            if (j >= j2) {
                                                                            }
                                                                        } catch (Throwable th2) {
                                                                            th = th2;
                                                                            Throwable th3 = th;
                                                                            if (batteryStatsHistoryIterator == null) {
                                                                                throw th3;
                                                                            }
                                                                            try {
                                                                                batteryStatsHistoryIterator.close();
                                                                                throw th3;
                                                                            } catch (Throwable th4) {
                                                                                th3.addSuppressed(th4);
                                                                                throw th3;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                historyPrinter3 = historyPrinter;
                                                                int i10 = i4;
                                                                j3 = j7;
                                                                obj = null;
                                                                next.eventCode = i10;
                                                                next.eventTag = historyTag;
                                                                historyEventTracker = null;
                                                            } catch (Throwable th5) {
                                                                th = th5;
                                                                j3 = j7;
                                                            }
                                                        } else {
                                                            historyPrinter3 = historyPrinter;
                                                            j3 = j7;
                                                            obj = null;
                                                        }
                                                    }
                                                } catch (Throwable th6) {
                                                    th = th6;
                                                    printWriter2 = printWriter;
                                                    j6 = j7;
                                                    th.printStackTrace(printWriter2);
                                                    Slog.wtf(TAG, "Corrupted battery history", th);
                                                    if (batteryStatsHistoryIterator != null) {
                                                    }
                                                    if (j >= j2) {
                                                    }
                                                }
                                            }
                                            z2 = true;
                                            if (historyEventTracker != null) {
                                            }
                                        } catch (Throwable th7) {
                                            th = th7;
                                            batteryStatsHistoryIterator = batteryStatsHistoryIteratorIterateBatteryStatsHistory;
                                        }
                                    }
                                    batteryStatsHistoryIterator = batteryStatsHistoryIteratorIterateBatteryStatsHistory;
                                    historyEventTracker = historyEventTracker2;
                                    historyPrinter = historyPrinter3;
                                    historyPrinter.printNextItem(printWriter, next, j5, z, (i3 & 32) != 0);
                                    next.cmd = (byte) 0;
                                    z2 = true;
                                    if (historyEventTracker != null) {
                                    }
                                } catch (Throwable th8) {
                                    th = th8;
                                    j3 = j7;
                                    batteryStatsHistoryIterator = batteryStatsHistoryIteratorIterateBatteryStatsHistory;
                                }
                            }
                            HistoryPrinter historyPrinter7 = historyPrinter3;
                            printWriter2 = printWriter;
                            try {
                                historyPrinter7.printNextItem(printWriter2, next, j5, z, (i & 32) != 0);
                                historyPrinter3 = historyPrinter7;
                                z4 = z2;
                                historyEventTracker2 = historyEventTracker;
                            } catch (Throwable th9) {
                                th = th9;
                                j6 = j3;
                                th.printStackTrace(printWriter2);
                                Slog.wtf(TAG, "Corrupted battery history", th);
                                if (batteryStatsHistoryIterator != null) {
                                }
                                if (j >= j2) {
                                }
                            }
                        } else {
                            j3 = j7;
                            batteryStatsHistoryIterator = batteryStatsHistoryIteratorIterateBatteryStatsHistory;
                        }
                        i3 = i;
                        z3 = z;
                        batteryStatsHistoryIteratorIterateBatteryStatsHistory = batteryStatsHistoryIterator;
                        j4 = j2;
                        j6 = j3;
                    } catch (Throwable th10) {
                        th = th10;
                        j3 = j7;
                        batteryStatsHistoryIterator = batteryStatsHistoryIteratorIterateBatteryStatsHistory;
                    }
                } catch (Throwable th11) {
                    th = th11;
                    batteryStatsHistoryIterator = batteryStatsHistoryIteratorIterateBatteryStatsHistory;
                }
                if (batteryStatsHistoryIterator != null) {
                    batteryStatsHistoryIterator.close();
                }
                if (j >= j2) {
                    commitCurrentHistoryBatchLocked();
                    printWriter2.print(z ? "NEXT: " : "  NEXT: ");
                    printWriter2.println(1 + j6);
                    return;
                }
                return;
            } catch (Throwable th12) {
                th = th12;
                batteryStatsHistoryIterator = batteryStatsHistoryIteratorIterateBatteryStatsHistory;
            }
        }
        j2 = j4;
        batteryStatsHistoryIterator = batteryStatsHistoryIteratorIterateBatteryStatsHistory;
        if (batteryStatsHistoryIterator != null) {
        }
        if (j >= j2) {
        }
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
        long jComputeTimeEstimate = levelStepTracker.computeTimeEstimate(0L, 0L, iArr);
        int i = 0;
        if (jComputeTimeEstimate >= 0) {
            printWriter.print(str);
            printWriter.print(str2);
            printWriter.print(" total time: ");
            sb.setLength(0);
            formatTimeMs(sb, jComputeTimeEstimate);
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
            long jComputeTimeEstimate2 = levelStepTracker.computeTimeEstimate(r4[i2], STEP_LEVEL_MODE_VALUES[i2], iArr);
            if (jComputeTimeEstimate2 > 0) {
                printWriter.print(str);
                printWriter.print(str2);
                printWriter.print(" ");
                printWriter.print(STEP_LEVEL_MODE_LABELS[i2]);
                printWriter.print(" time: ");
                sb.setLength(i3);
                formatTimeMs(sb, jComputeTimeEstimate2);
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

    public void dump(Context context, PrintWriter printWriter, int i, int i2, long j, BatteryStatsDumpHelper batteryStatsDumpHelper) throws Throwable {
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

    private void dumpLocked(Context context, PrintWriter printWriter, int i, int i2, boolean z, BatteryStatsDumpHelper batteryStatsDumpHelper) throws NumberFormatException, IOException {
        long j;
        BatteryStats batteryStats = this;
        PrintWriter printWriter2 = printWriter;
        if (z) {
            j = 0;
        } else {
            SparseArray<? extends Uid> uidStats = batteryStats.getUidStats();
            int size = uidStats.size();
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            boolean z2 = false;
            for (int i3 = 0; i3 < size; i3++) {
                SparseArray<? extends Uid.Pid> pidStats = uidStats.valueAt(i3).getPidStats();
                if (pidStats != null) {
                    for (int i4 = 0; i4 < pidStats.size(); i4++) {
                        Uid.Pid pidValueAt = pidStats.valueAt(i4);
                        if (!z2) {
                            printWriter2.println("Per-PID Stats:");
                            z2 = true;
                        }
                        long j2 = pidValueAt.mWakeSumMs + (pidValueAt.mWakeNesting > 0 ? jElapsedRealtime - pidValueAt.mWakeStartMs : 0L);
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
                long jComputeBatteryTimeRemaining = batteryStats.computeBatteryTimeRemaining(SystemClock.elapsedRealtime() * 1000);
                if (jComputeBatteryTimeRemaining >= j) {
                    printWriter2.print("  Estimated discharge time remaining: ");
                    TimeUtils.formatDuration(jComputeBatteryTimeRemaining / 1000, printWriter2);
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
                long jComputeChargeTimeRemaining = batteryStats.computeChargeTimeRemaining(SystemClock.elapsedRealtime() * 1000);
                if (jComputeChargeTimeRemaining >= j) {
                    printWriter2.print("  Estimated charge time remaining: ");
                    TimeUtils.formatDuration(jComputeChargeTimeRemaining / 1000, printWriter2);
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

    public void dumpCheckin(Context context, PrintWriter printWriter, List<ApplicationInfo> list, int i, long j, BatteryStatsDumpHelper batteryStatsDumpHelper) throws Throwable {
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
            long jComputeBatteryTimeRemaining = computeBatteryTimeRemaining(SystemClock.elapsedRealtime() * 1000);
            if (jComputeBatteryTimeRemaining >= 0) {
                strArr[0] = Long.toString(jComputeBatteryTimeRemaining);
                dumpLine(printWriter, 0, "i", DISCHARGE_TIME_REMAIN_DATA, strArr);
            }
            dumpDurationSteps(printWriter, "", CHARGE_STEP_DATA, getChargeLevelStepTracker(), true);
            long jComputeChargeTimeRemaining = computeChargeTimeRemaining(SystemClock.elapsedRealtime() * 1000);
            if (jComputeChargeTimeRemaining >= 0) {
                strArr[0] = Long.toString(jComputeChargeTimeRemaining);
                dumpLine(printWriter, 0, "i", CHARGE_TIME_REMAIN_DATA, strArr);
            }
            dumpCheckinLocked(context, printWriter, 0, -1, (i & 64) != 0, batteryStatsDumpHelper);
        }
    }

    public void dumpProtoLocked(Context context, FileDescriptor fileDescriptor, List<ApplicationInfo> list, int i, long j, BatteryStatsDumpHelper batteryStatsDumpHelper) throws IOException {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
        prepareForDumpLocked();
        if ((i & 24) != 0) {
            dumpProtoHistoryLocked(protoOutputStream, i, j);
            protoOutputStream.flush();
            return;
        }
        long jStart = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1120986464257L, 36);
        protoOutputStream.write(1112396529666L, getParcelVersion());
        protoOutputStream.write(1138166333443L, getStartPlatformVersion());
        protoOutputStream.write(1138166333444L, getEndPlatformVersion());
        if ((i & 4) == 0) {
            BatteryUsageStats batteryUsageStats = batteryStatsDumpHelper.getBatteryUsageStats(this, false);
            dumpProtoAppsLocked(protoOutputStream, batteryUsageStats, list, new ProportionalAttributionCalculator(context, batteryUsageStats));
            dumpProtoSystemLocked(protoOutputStream, batteryUsageStats);
        }
        protoOutputStream.end(jStart);
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
        int countLocked;
        long j7;
        SparseArray sparseArray2;
        long j8;
        ArrayList arrayList;
        long j9;
        long j10;
        int i5;
        ArrayMap<String, ? extends Uid.Pkg> arrayMap2;
        ArrayList arrayList2;
        long j11;
        ArrayMap<String, ? extends Uid.Pkg> arrayMap3;
        int i6;
        int i7;
        ProtoOutputStream protoOutputStream2 = protoOutputStream;
        long jUptimeMillis = SystemClock.uptimeMillis() * 1000;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long j12 = 1000 * jElapsedRealtime;
        long batteryUptime = getBatteryUptime(jUptimeMillis);
        SparseArray sparseArray3 = new SparseArray();
        if (list != null) {
            for (int i8 = 0; i8 < list.size(); i8++) {
                ApplicationInfo applicationInfo = list.get(i8);
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
        int i9 = 0;
        while (i9 < size2) {
            SparseArray sparseArray5 = sparseArray4;
            long jStart = protoOutputStream2.start(2246267895813L);
            Uid uidValueAt = uidStats.valueAt(i9);
            int i10 = size2;
            int iKeyAt = uidStats.keyAt(i9);
            protoOutputStream2.write(1120986464257L, iKeyAt);
            ArrayList arrayList4 = (ArrayList) sparseArray3.get(UserHandle.getAppId(iKeyAt));
            if (arrayList4 == null) {
                arrayList4 = new ArrayList();
            }
            ArrayMap<String, ? extends Uid.Pkg> packageStats = uidValueAt.getPackageStats();
            SparseArray<? extends Uid> sparseArray6 = uidStats;
            int size3 = packageStats.size() - 1;
            int i11 = i9;
            while (size3 >= 0) {
                String strKeyAt = packageStats.keyAt(size3);
                ArrayMap<String, ? extends Uid.Pkg.Serv> serviceStats = packageStats.valueAt(size3).getServiceStats();
                if (serviceStats.size() == 0) {
                    j7 = j12;
                    j9 = jElapsedRealtime;
                    j10 = batteryUptime;
                    sparseArray2 = sparseArray3;
                    j8 = jStart;
                    arrayList = arrayList4;
                    arrayMap2 = packageStats;
                    i5 = size3;
                } else {
                    j7 = j12;
                    sparseArray2 = sparseArray3;
                    j8 = jStart;
                    long jStart2 = protoOutputStream2.start(2246267895810L);
                    protoOutputStream2.write(1138166333441L, strKeyAt);
                    arrayList4.remove(strKeyAt);
                    int size4 = serviceStats.size() - 1;
                    while (size4 >= 0) {
                        Uid.Pkg.Serv servValueAt = serviceStats.valueAt(size4);
                        long j13 = batteryUptime;
                        long jRoundUsToMs = roundUsToMs(servValueAt.getStartTime(batteryUptime, 0));
                        int starts = servValueAt.getStarts(0);
                        int launches = servValueAt.getLaunches(0);
                        if (jRoundUsToMs == 0 && starts == 0 && launches == 0) {
                            i7 = size4;
                            arrayList2 = arrayList4;
                            j11 = jElapsedRealtime;
                            i6 = size3;
                            arrayMap3 = packageStats;
                        } else {
                            arrayList2 = arrayList4;
                            j11 = jElapsedRealtime;
                            arrayMap3 = packageStats;
                            long jStart3 = protoOutputStream2.start(2246267895810L);
                            i6 = size3;
                            i7 = size4;
                            protoOutputStream2.write(1138166333441L, serviceStats.keyAt(size4));
                            protoOutputStream2.write(1112396529666L, jRoundUsToMs);
                            protoOutputStream2.write(1120986464259L, starts);
                            protoOutputStream2.write(1120986464260L, launches);
                            protoOutputStream2.end(jStart3);
                        }
                        size4 = i7 - 1;
                        size3 = i6;
                        packageStats = arrayMap3;
                        arrayList4 = arrayList2;
                        batteryUptime = j13;
                        jElapsedRealtime = j11;
                    }
                    arrayList = arrayList4;
                    j9 = jElapsedRealtime;
                    j10 = batteryUptime;
                    i5 = size3;
                    arrayMap2 = packageStats;
                    protoOutputStream2.end(jStart2);
                }
                size3 = i5 - 1;
                packageStats = arrayMap2;
                arrayList4 = arrayList;
                sparseArray3 = sparseArray2;
                j12 = j7;
                jStart = j8;
                batteryUptime = j10;
                jElapsedRealtime = j9;
            }
            long j14 = j12;
            long j15 = jElapsedRealtime;
            long j16 = batteryUptime;
            SparseArray sparseArray7 = sparseArray3;
            long j17 = jStart;
            ArrayMap<String, ? extends Uid.Pkg> arrayMap4 = packageStats;
            Iterator it = arrayList4.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                long jStart4 = protoOutputStream2.start(2246267895810L);
                protoOutputStream2.write(1138166333441L, str);
                protoOutputStream2.end(jStart4);
            }
            if (uidValueAt.getAggregatedPartialWakelockTimer() != null) {
                Timer aggregatedPartialWakelockTimer = uidValueAt.getAggregatedPartialWakelockTimer();
                j = j15;
                long totalDurationMsLocked = aggregatedPartialWakelockTimer.getTotalDurationMsLocked(j);
                Timer subTimer = aggregatedPartialWakelockTimer.getSubTimer();
                long totalDurationMsLocked2 = subTimer != null ? subTimer.getTotalDurationMsLocked(j) : 0L;
                long jStart5 = protoOutputStream2.start(1146756268056L);
                protoOutputStream2.write(1112396529665L, totalDurationMsLocked);
                j2 = 1112396529666L;
                protoOutputStream2.write(1112396529666L, totalDurationMsLocked2);
                protoOutputStream2.end(jStart5);
            } else {
                j = j15;
                j2 = 1112396529666L;
            }
            long j18 = j14;
            dumpTimer(protoOutputStream2, 1146756268040L, uidValueAt.getAudioTurnedOnTimer(), j18, 0);
            dumpControllerActivityProto(protoOutputStream2, 1146756268035L, uidValueAt.getBluetoothControllerActivity(), 0);
            Timer bluetoothScanTimer = uidValueAt.getBluetoothScanTimer();
            if (bluetoothScanTimer != null) {
                long jStart6 = protoOutputStream2.start(1146756268038L);
                dumpTimer(protoOutputStream2, 1146756268033L, bluetoothScanTimer, j18, 0);
                protoOutputStream2 = protoOutputStream;
                dumpTimer(protoOutputStream2, 1146756268034L, uidValueAt.getBluetoothScanBackgroundTimer(), j18, 0);
                dumpTimer(protoOutputStream2, 1146756268035L, uidValueAt.getBluetoothUnoptimizedScanTimer(), j18, 0);
                dumpTimer(protoOutputStream2, 1146756268036L, uidValueAt.getBluetoothUnoptimizedScanBackgroundTimer(), j18, 0);
                if (uidValueAt.getBluetoothScanResultCounter() != null) {
                    Counter bluetoothScanResultCounter = uidValueAt.getBluetoothScanResultCounter();
                    i4 = 0;
                    countLocked = bluetoothScanResultCounter.getCountLocked(0);
                } else {
                    i4 = 0;
                    countLocked = 0;
                }
                protoOutputStream2.write(1120986464261L, countLocked);
                j3 = 1120986464262L;
                protoOutputStream2.write(1120986464262L, uidValueAt.getBluetoothScanResultBgCounter() != null ? uidValueAt.getBluetoothScanResultBgCounter().getCountLocked(i4) : 0);
                protoOutputStream2.end(jStart6);
            } else {
                j3 = 1120986464262L;
            }
            dumpTimer(protoOutputStream2, 1146756268041L, uidValueAt.getCameraTurnedOnTimer(), j18, 0);
            long jStart7 = protoOutputStream2.start(1146756268039L);
            protoOutputStream2.write(1112396529665L, roundUsToMs(uidValueAt.getUserCpuTimeUs(0)));
            protoOutputStream2.write(1112396529666L, roundUsToMs(uidValueAt.getSystemCpuTimeUs(0)));
            CpuScalingPolicies cpuScalingPolicies = getCpuScalingPolicies();
            if (cpuScalingPolicies != null && (cpuFreqTimes = uidValueAt.getCpuFreqTimes(0)) != null && cpuFreqTimes.length == cpuScalingPolicies.getScalingStepCount()) {
                long[] screenOffCpuFreqTimes = uidValueAt.getScreenOffCpuFreqTimes(0);
                if (screenOffCpuFreqTimes == null) {
                    screenOffCpuFreqTimes = new long[cpuFreqTimes.length];
                }
                int i12 = 0;
                while (i12 < cpuFreqTimes.length) {
                    long jStart8 = protoOutputStream2.start(2246267895811L);
                    int i13 = i12 + 1;
                    long[] jArr3 = screenOffCpuFreqTimes;
                    protoOutputStream2.write(1120986464257L, i13);
                    protoOutputStream2.write(1112396529666L, cpuFreqTimes[i12]);
                    protoOutputStream2.write(1112396529667L, jArr3[i12]);
                    protoOutputStream2.end(jStart8);
                    screenOffCpuFreqTimes = jArr3;
                    i12 = i13;
                    j18 = j18;
                }
            }
            long j19 = j18;
            int scalingStepCount = getCpuScalingPolicies().getScalingStepCount();
            long[] jArr4 = new long[scalingStepCount];
            long[] jArr5 = new long[scalingStepCount];
            int i14 = 0;
            while (i14 < 7) {
                if (uidValueAt.getCpuFreqTimes(jArr4, i14)) {
                    if (!uidValueAt.getScreenOffCpuFreqTimes(jArr5, i14)) {
                        Arrays.fill(jArr5, 0L);
                    }
                    long jStart9 = protoOutputStream2.start(2246267895812L);
                    protoOutputStream2.write(1159641169921L, i14);
                    int i15 = 0;
                    while (i15 < scalingStepCount) {
                        int i16 = scalingStepCount;
                        long[] jArr6 = jArr4;
                        int i17 = i15;
                        long jStart10 = protoOutputStream2.start(2246267895810L);
                        int i18 = i17 + 1;
                        long[] jArr7 = jArr5;
                        protoOutputStream2.write(1120986464257L, i18);
                        protoOutputStream2.write(1112396529666L, jArr6[i17]);
                        protoOutputStream2.write(1112396529667L, jArr7[i17]);
                        protoOutputStream2.end(jStart10);
                        scalingStepCount = i16;
                        jArr4 = jArr6;
                        i14 = i14;
                        i15 = i18;
                        jArr5 = jArr7;
                    }
                    i2 = scalingStepCount;
                    jArr = jArr4;
                    jArr2 = jArr5;
                    i3 = i14;
                    protoOutputStream2.end(jStart9);
                } else {
                    i2 = scalingStepCount;
                    jArr = jArr4;
                    jArr2 = jArr5;
                    i3 = i14;
                }
                i14 = i3 + 1;
                scalingStepCount = i2;
                jArr4 = jArr;
                jArr5 = jArr2;
            }
            protoOutputStream2.end(jStart7);
            long j20 = j19;
            dumpTimer(protoOutputStream2, 1146756268042L, uidValueAt.getFlashlightTurnedOnTimer(), j20, 0);
            ProtoOutputStream protoOutputStream3 = protoOutputStream;
            dumpTimer(protoOutputStream3, 1146756268043L, uidValueAt.getForegroundActivityTimer(), j20, 0);
            dumpTimer(protoOutputStream3, 1146756268044L, uidValueAt.getForegroundServiceTimer(), j20, 0);
            ArrayMap<String, SparseIntArray> jobCompletionStats = uidValueAt.getJobCompletionStats();
            int i19 = 0;
            while (i19 < jobCompletionStats.size()) {
                SparseIntArray sparseIntArrayValueAt = jobCompletionStats.valueAt(i19);
                if (sparseIntArrayValueAt != null) {
                    long jStart11 = protoOutputStream3.start(2246267895824L);
                    arrayMap = jobCompletionStats;
                    i = i19;
                    protoOutputStream3.write(1138166333441L, jobCompletionStats.keyAt(i19));
                    int[] jobStopReasonCodes = JobParameters.getJobStopReasonCodes();
                    int length = jobStopReasonCodes.length;
                    int i20 = 0;
                    while (i20 < length) {
                        int i21 = length;
                        int i22 = jobStopReasonCodes[i20];
                        long j21 = j;
                        long jStart12 = protoOutputStream3.start(2246267895810L);
                        protoOutputStream3.write(1159641169921L, i22);
                        protoOutputStream3.write(1120986464258L, sparseIntArrayValueAt.get(i22, 0));
                        protoOutputStream3.end(jStart12);
                        i20++;
                        length = i21;
                        j = j21;
                        j20 = j20;
                    }
                    j5 = j20;
                    j6 = j;
                    protoOutputStream3.end(jStart11);
                } else {
                    arrayMap = jobCompletionStats;
                    i = i19;
                    j5 = j20;
                    j6 = j;
                }
                i19 = i + 1;
                jobCompletionStats = arrayMap;
                j = j6;
                j20 = j5;
            }
            long j22 = j20;
            long j23 = j;
            ArrayMap<String, ? extends Timer> jobStats = uidValueAt.getJobStats();
            for (int size5 = jobStats.size() - 1; size5 >= 0; size5--) {
                Timer timerValueAt = jobStats.valueAt(size5);
                Timer subTimer2 = timerValueAt.getSubTimer();
                long jStart13 = protoOutputStream3.start(2246267895823L);
                protoOutputStream3.write(1138166333441L, jobStats.keyAt(size5));
                dumpTimer(protoOutputStream3, 1146756268034L, timerValueAt, j22, 0);
                protoOutputStream3 = protoOutputStream;
                dumpTimer(protoOutputStream3, 1146756268035L, subTimer2, j22, 0);
                protoOutputStream3.end(jStart13);
            }
            dumpControllerActivityProto(protoOutputStream3, 1146756268036L, uidValueAt.getModemControllerActivity(), 0);
            long jStart14 = protoOutputStream3.start(1146756268049L);
            protoOutputStream3.write(1112396529665L, uidValueAt.getNetworkActivityBytes(0, 0));
            protoOutputStream3.write(1112396529666L, uidValueAt.getNetworkActivityBytes(1, 0));
            protoOutputStream3.write(1112396529667L, uidValueAt.getNetworkActivityBytes(2, 0));
            protoOutputStream3.write(1112396529668L, uidValueAt.getNetworkActivityBytes(3, 0));
            protoOutputStream3.write(1112396529669L, uidValueAt.getNetworkActivityBytes(4, 0));
            protoOutputStream3.write(1112396529670L, uidValueAt.getNetworkActivityBytes(5, 0));
            protoOutputStream3.write(1112396529671L, uidValueAt.getNetworkActivityPackets(0, 0));
            protoOutputStream3.write(1112396529672L, uidValueAt.getNetworkActivityPackets(1, 0));
            protoOutputStream3.write(1112396529673L, uidValueAt.getNetworkActivityPackets(2, 0));
            protoOutputStream3.write(1112396529674L, uidValueAt.getNetworkActivityPackets(3, 0));
            protoOutputStream3.write(1112396529675L, roundUsToMs(uidValueAt.getMobileRadioActiveTime(0)));
            protoOutputStream3.write(1120986464268L, uidValueAt.getMobileRadioActiveCount(0));
            protoOutputStream3.write(1120986464269L, uidValueAt.getMobileRadioApWakeupCount(0));
            protoOutputStream3.write(1120986464270L, uidValueAt.getWifiRadioApWakeupCount(0));
            protoOutputStream3.write(1112396529679L, uidValueAt.getNetworkActivityBytes(6, 0));
            int i23 = 1;
            protoOutputStream3.write(1112396529680L, uidValueAt.getNetworkActivityBytes(7, 0));
            protoOutputStream3.write(1112396529681L, uidValueAt.getNetworkActivityBytes(8, 0));
            protoOutputStream3.write(1112396529682L, uidValueAt.getNetworkActivityBytes(9, 0));
            protoOutputStream3.write(1112396529683L, uidValueAt.getNetworkActivityPackets(6, 0));
            protoOutputStream3.write(1112396529684L, uidValueAt.getNetworkActivityPackets(7, 0));
            protoOutputStream3.write(1112396529685L, uidValueAt.getNetworkActivityPackets(8, 0));
            protoOutputStream3.write(1112396529686L, uidValueAt.getNetworkActivityPackets(9, 0));
            protoOutputStream3.end(jStart14);
            SparseArray sparseArray8 = sparseArray5;
            UidBatteryConsumer uidBatteryConsumer2 = (UidBatteryConsumer) sparseArray8.get(iKeyAt);
            if (uidBatteryConsumer2 != null) {
                long jStart15 = protoOutputStream3.start(1146756268050L);
                protoOutputStream3.write(1103806595073L, uidBatteryConsumer2.getConsumedPower());
                protoOutputStream3.write(1133871366146L, proportionalAttributionCalculator.isSystemBatteryConsumer(uidBatteryConsumer2));
                protoOutputStream3.write(1103806595075L, uidBatteryConsumer2.getConsumedPower(0));
                protoOutputStream3.write(1103806595076L, proportionalAttributionCalculator.getProportionalPowerMah(uidBatteryConsumer2));
                protoOutputStream3.end(jStart15);
            }
            ArrayMap<String, ? extends Uid.Proc> processStats = uidValueAt.getProcessStats();
            for (int size6 = processStats.size() - 1; size6 >= 0; size6--) {
                Uid.Proc procValueAt = processStats.valueAt(size6);
                long jStart16 = protoOutputStream3.start(2246267895827L);
                protoOutputStream3.write(1138166333441L, processStats.keyAt(size6));
                protoOutputStream3.write(1112396529666L, procValueAt.getUserTime(0));
                protoOutputStream3.write(1112396529667L, procValueAt.getSystemTime(0));
                protoOutputStream3.write(1112396529668L, procValueAt.getForegroundTime(0));
                protoOutputStream3.write(1120986464261L, procValueAt.getStarts(0));
                protoOutputStream3.write(1120986464262L, procValueAt.getNumAnrs(0));
                protoOutputStream3.write(1120986464263L, procValueAt.getNumCrashes(0));
                protoOutputStream3.end(jStart16);
            }
            SparseArray<? extends Uid.Sensor> sensorStats = uidValueAt.getSensorStats();
            int i24 = 0;
            while (i24 < sensorStats.size()) {
                Uid.Sensor sensorValueAt = sensorStats.valueAt(i24);
                Timer sensorTime = sensorValueAt.getSensorTime();
                if (sensorTime == null) {
                    sparseArray = sparseArray8;
                    j4 = j22;
                } else {
                    Timer sensorBackgroundTime = sensorValueAt.getSensorBackgroundTime();
                    int iKeyAt2 = sensorStats.keyAt(i24);
                    long jStart17 = protoOutputStream3.start(2246267895829L);
                    protoOutputStream3.write(1120986464257L, iKeyAt2);
                    sparseArray = sparseArray8;
                    j4 = j22;
                    dumpTimer(protoOutputStream3, 1146756268034L, sensorTime, j4, 0);
                    protoOutputStream3 = protoOutputStream;
                    dumpTimer(protoOutputStream3, 1146756268035L, sensorBackgroundTime, j4, 0);
                    protoOutputStream3.end(jStart17);
                }
                i24++;
                j22 = j4;
                sparseArray8 = sparseArray;
            }
            SparseArray sparseArray9 = sparseArray8;
            j12 = j22;
            for (int i25 = 0; i25 < 7; i25++) {
                long jRoundUsToMs2 = roundUsToMs(uidValueAt.getProcessStateTime(i25, j12, 0));
                if (jRoundUsToMs2 != 0) {
                    long jStart18 = protoOutputStream3.start(2246267895828L);
                    protoOutputStream3.write(1159641169921L, i25);
                    protoOutputStream3.write(1112396529666L, jRoundUsToMs2);
                    protoOutputStream3.end(jStart18);
                }
            }
            ArrayMap<String, ? extends Timer> syncStats = uidValueAt.getSyncStats();
            for (int size7 = syncStats.size() - 1; size7 >= 0; size7--) {
                Timer timerValueAt2 = syncStats.valueAt(size7);
                Timer subTimer3 = timerValueAt2.getSubTimer();
                long jStart19 = protoOutputStream3.start(2246267895830L);
                protoOutputStream3.write(1138166333441L, syncStats.keyAt(size7));
                dumpTimer(protoOutputStream3, 1146756268034L, timerValueAt2, j12, 0);
                protoOutputStream3 = protoOutputStream;
                dumpTimer(protoOutputStream3, 1146756268035L, subTimer3, j12, 0);
                protoOutputStream3.end(jStart19);
            }
            if (uidValueAt.hasUserActivity()) {
                for (int i26 = 0; i26 < Uid.NUM_USER_ACTIVITY_TYPES; i26++) {
                    int userActivityCount = uidValueAt.getUserActivityCount(i26, 0);
                    if (userActivityCount != 0) {
                        long jStart20 = protoOutputStream3.start(2246267895831L);
                        protoOutputStream3.write(1159641169921L, i26);
                        protoOutputStream3.write(1120986464258L, userActivityCount);
                        protoOutputStream3.end(jStart20);
                    }
                }
            }
            dumpTimer(protoOutputStream3, 1146756268045L, uidValueAt.getVibratorOnTimer(), j12, 0);
            protoOutputStream2 = protoOutputStream;
            dumpTimer(protoOutputStream2, 1146756268046L, uidValueAt.getVideoTurnedOnTimer(), j12, 0);
            ArrayMap<String, ? extends Uid.Wakelock> wakelockStats = uidValueAt.getWakelockStats();
            int size8 = wakelockStats.size() - 1;
            while (size8 >= 0) {
                Uid.Wakelock wakelockValueAt = wakelockStats.valueAt(size8);
                long jStart21 = protoOutputStream2.start(2246267895833L);
                protoOutputStream2.write(1138166333441L, wakelockStats.keyAt(size8));
                dumpTimer(protoOutputStream2, 1146756268034L, wakelockValueAt.getWakeTime(i23), j12, 0);
                Timer wakeTime = wakelockValueAt.getWakeTime(0);
                if (wakeTime != null) {
                    dumpTimer(protoOutputStream, 1146756268035L, wakeTime, j12, 0);
                    dumpTimer(protoOutputStream, 1146756268036L, wakeTime.getSubTimer(), j12, 0);
                }
                protoOutputStream2 = protoOutputStream;
                dumpTimer(protoOutputStream2, 1146756268037L, wakelockValueAt.getWakeTime(2), j12, 0);
                protoOutputStream2.end(jStart21);
                size8--;
                i23 = 1;
            }
            dumpTimer(protoOutputStream2, 1146756268060L, uidValueAt.getMulticastWakelockStats(), j12, 0);
            for (int size9 = arrayMap4.size() - 1; size9 >= 0; size9--) {
                ArrayMap<String, ? extends Counter> wakeupAlarmStats = arrayMap4.valueAt(size9).getWakeupAlarmStats();
                for (int size10 = wakeupAlarmStats.size() - 1; size10 >= 0; size10--) {
                    long jStart22 = protoOutputStream2.start(2246267895834L);
                    protoOutputStream2.write(1138166333441L, wakeupAlarmStats.keyAt(size10));
                    protoOutputStream2.write(1120986464258L, wakeupAlarmStats.valueAt(size10).getCountLocked(0));
                    protoOutputStream2.end(jStart22);
                }
            }
            dumpControllerActivityProto(protoOutputStream2, 1146756268037L, uidValueAt.getWifiControllerActivity(), 0);
            long jStart23 = protoOutputStream2.start(1146756268059L);
            protoOutputStream2.write(1112396529665L, roundUsToMs(uidValueAt.getFullWifiLockTime(j12, 0)));
            dumpTimer(protoOutputStream2, 1146756268035L, uidValueAt.getWifiScanTimer(), j12, 0);
            protoOutputStream2.write(1112396529666L, roundUsToMs(uidValueAt.getWifiRunningTime(j12, 0)));
            dumpTimer(protoOutputStream2, 1146756268036L, uidValueAt.getWifiScanBackgroundTimer(), j12, 0);
            protoOutputStream2.end(jStart23);
            protoOutputStream2.end(j17);
            i9 = i11 + 1;
            sparseArray4 = sparseArray9;
            size2 = i10;
            uidStats = sparseArray6;
            sparseArray3 = sparseArray7;
            batteryUptime = j16;
            jElapsedRealtime = j23;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x00d5 A[Catch: all -> 0x01bd, TryCatch #1 {all -> 0x01bd, blocks: (B:7:0x0070, B:9:0x0076, B:12:0x007d, B:17:0x008b, B:19:0x0090, B:21:0x0095, B:23:0x009a, B:26:0x00a1, B:28:0x00a7, B:32:0x00b4, B:41:0x00d5, B:43:0x00d9, B:47:0x00e0, B:49:0x00ea, B:52:0x00fa, B:67:0x0165, B:55:0x0103, B:56:0x010b, B:58:0x0111, B:59:0x011f, B:61:0x0125, B:65:0x014e, B:68:0x016c, B:71:0x0179, B:75:0x0180, B:34:0x00c0, B:38:0x00ca, B:80:0x0198), top: B:94:0x0070 }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0173  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void dumpProtoHistoryLocked(ProtoOutputStream protoOutputStream, int i, long j) {
        long j2;
        long j3;
        Object obj;
        HistoryItem historyItem;
        int i2;
        boolean z;
        ProtoOutputStream protoOutputStream2 = protoOutputStream;
        protoOutputStream2.write(1120986464257L, 36);
        protoOutputStream2.write(1112396529666L, getParcelVersion());
        protoOutputStream2.write(1138166333443L, getStartPlatformVersion());
        protoOutputStream2.write(1138166333444L, getEndPlatformVersion());
        for (int i3 = 0; i3 < getHistoryStringPoolSize(); i3++) {
            long jStart = protoOutputStream2.start(2246267895813L);
            protoOutputStream2.write(1120986464257L, i3);
            protoOutputStream2.write(1120986464258L, getHistoryTagPoolUid(i3));
            protoOutputStream2.write(1138166333443L, getHistoryTagPoolString(i3));
            protoOutputStream2.end(jStart);
        }
        HistoryPrinter historyPrinter = new HistoryPrinter(i);
        long j4 = 0;
        long j5 = -1;
        BatteryStatsHistoryIterator batteryStatsHistoryIteratorIterateBatteryStatsHistory = iterateBatteryStatsHistory(0L, -1L);
        long j6 = -1;
        boolean z2 = false;
        HistoryEventTracker historyEventTracker = null;
        while (true) {
            try {
                HistoryItem next = batteryStatsHistoryIteratorIterateBatteryStatsHistory.next();
                if (next == null) {
                    break;
                }
                long j7 = next.time;
                if (j5 < j4) {
                    j5 = j7;
                }
                long j8 = j4;
                if (next.time >= j) {
                    if (j < j8 || z2) {
                        j2 = j7;
                        obj = null;
                        j3 = j5;
                        historyItem = next;
                    } else {
                        if (next.cmd == 5 || next.cmd == 7 || next.cmd == 4 || next.cmd == 8) {
                            j2 = j7;
                            j3 = j5;
                            historyItem = next;
                            historyPrinter.printNextItem(protoOutputStream, historyItem, j3, (i & 32) != 0);
                            historyItem.cmd = (byte) 0;
                        } else if (next.currentTime != j8) {
                            byte b = next.cmd;
                            next.cmd = (byte) 5;
                            if ((i & 32) != 0) {
                                j2 = j7;
                                z = true;
                            } else {
                                j2 = j7;
                                z = false;
                            }
                            j3 = j5;
                            historyItem = next;
                            historyPrinter.printNextItem(protoOutputStream2, historyItem, j3, z);
                            historyItem.cmd = b;
                        } else {
                            j2 = j7;
                            j3 = j5;
                            historyItem = next;
                            if (historyEventTracker == null) {
                                if (historyItem.cmd != 0) {
                                    historyPrinter.printNextItem(protoOutputStream, historyItem, j3, (i & 32) != 0);
                                    i2 = 0;
                                    historyItem.cmd = (byte) 0;
                                } else {
                                    i2 = 0;
                                }
                                int i4 = historyItem.eventCode;
                                HistoryTag historyTag = historyItem.eventTag;
                                historyItem.eventTag = new HistoryTag();
                                int i5 = i2;
                                while (i5 < 23) {
                                    HashMap<String, SparseIntArray> stateForEvent = historyEventTracker.getStateForEvent(i5);
                                    if (stateForEvent != null) {
                                        for (Map.Entry<String, SparseIntArray> entry : stateForEvent.entrySet()) {
                                            SparseIntArray value = entry.getValue();
                                            while (i2 < value.size()) {
                                                historyItem.eventCode = i5;
                                                HistoryPrinter historyPrinter2 = historyPrinter;
                                                historyItem.eventTag.string = entry.getKey();
                                                historyItem.eventTag.uid = value.keyAt(i2);
                                                historyItem.eventTag.poolIdx = value.valueAt(i2);
                                                SparseIntArray sparseIntArray = value;
                                                historyPrinter = historyPrinter2;
                                                historyPrinter.printNextItem(protoOutputStream, historyItem, j3, (i & 32) != 0);
                                                historyItem.wakeReasonTag = null;
                                                historyItem.wakelockTag = null;
                                                i2++;
                                                i5 = i5;
                                                value = sparseIntArray;
                                            }
                                            i2 = 0;
                                        }
                                    }
                                    i2 = 0;
                                    i5++;
                                }
                                obj = null;
                                historyItem.eventCode = i4;
                                historyItem.eventTag = historyTag;
                                historyEventTracker = null;
                            } else {
                                obj = null;
                            }
                        }
                        z2 = true;
                        if (historyEventTracker == null) {
                        }
                    }
                    protoOutputStream2 = protoOutputStream;
                    historyPrinter.printNextItem(protoOutputStream2, historyItem, j3, (i & 32) != 0);
                } else {
                    j2 = j7;
                    j3 = j5;
                }
                j5 = j3;
                j6 = j2;
                j4 = j8;
            } finally {
            }
        }
        if (j >= j4) {
            commitCurrentHistoryBatchLocked();
            protoOutputStream2.write(2237677961222L, "NEXT: " + (j6 + 1));
        }
        if (batteryStatsHistoryIteratorIterateBatteryStatsHistory != null) {
            batteryStatsHistoryIteratorIterateBatteryStatsHistory.close();
        }
    }

    private void dumpProtoSystemLocked(ProtoOutputStream protoOutputStream, BatteryUsageStats batteryUsageStats) {
        boolean z;
        int i;
        ProtoOutputStream protoOutputStream2 = protoOutputStream;
        long jStart = protoOutputStream2.start(1146756268038L);
        long jUptimeMillis = SystemClock.uptimeMillis() * 1000;
        long jElapsedRealtime = SystemClock.elapsedRealtime() * 1000;
        long jStart2 = protoOutputStream2.start(1146756268033L);
        protoOutputStream2.write(1112396529665L, getStartClockTime());
        protoOutputStream2.write(1112396529666L, getStartCount());
        int i2 = 0;
        protoOutputStream2.write(1112396529667L, computeRealtime(jElapsedRealtime, 0) / 1000);
        protoOutputStream2.write(1112396529668L, computeUptime(jUptimeMillis, 0) / 1000);
        protoOutputStream2.write(1112396529669L, computeBatteryRealtime(jElapsedRealtime, 0) / 1000);
        protoOutputStream2.write(1112396529670L, computeBatteryUptime(jUptimeMillis, 0) / 1000);
        protoOutputStream2.write(1112396529671L, computeBatteryScreenOffRealtime(jElapsedRealtime, 0) / 1000);
        protoOutputStream2.write(1112396529672L, computeBatteryScreenOffUptime(jUptimeMillis, 0) / 1000);
        protoOutputStream2.write(1112396529673L, getScreenDozeTime(jElapsedRealtime, 0) / 1000);
        protoOutputStream2.write(1112396529674L, getEstimatedBatteryCapacity());
        protoOutputStream2.write(1112396529675L, getMinLearnedBatteryCapacity());
        protoOutputStream2.write(1112396529676L, getMaxLearnedBatteryCapacity());
        protoOutputStream2.end(jStart2);
        long jStart3 = protoOutputStream2.start(1146756268034L);
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
        protoOutputStream2.end(jStart3);
        long jComputeChargeTimeRemaining = computeChargeTimeRemaining(jElapsedRealtime);
        if (jComputeChargeTimeRemaining >= 0) {
            protoOutputStream2.write(1112396529667L, jComputeChargeTimeRemaining / 1000);
        } else {
            long jComputeBatteryTimeRemaining = computeBatteryTimeRemaining(jElapsedRealtime);
            if (jComputeBatteryTimeRemaining >= 0) {
                protoOutputStream2.write(1112396529668L, jComputeBatteryTimeRemaining / 1000);
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
            long j = jElapsedRealtime;
            long jStart4 = protoOutputStream2.start(2246267895816L);
            if (z) {
                protoOutputStream2.write(1133871366146L, z);
            } else {
                protoOutputStream2.write(1159641169921L, i4);
            }
            jElapsedRealtime = j;
            dumpTimer(protoOutputStream2, 1146756268035L, getPhoneDataConnectionTimer(i3), jElapsedRealtime, 0);
            protoOutputStream2.end(jStart4);
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
        long jStart5 = protoOutputStream2.start(1146756268044L);
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
        protoOutputStream2.end(jStart5);
        dumpControllerActivityProto(protoOutputStream2, 1146756268043L, getWifiControllerActivity(), i5);
        long jStart6 = protoOutputStream2.start(1146756268045L);
        protoOutputStream2.write(1112396529665L, getWifiOnTime(jElapsedRealtime, i5) / 1000);
        protoOutputStream2.write(1112396529666L, getGlobalWifiRunningTime(jElapsedRealtime, i5) / 1000);
        protoOutputStream2.end(jStart6);
        for (Map.Entry<String, ? extends Timer> entry : getKernelWakelockStats().entrySet()) {
            long jStart7 = protoOutputStream2.start(2246267895822L);
            protoOutputStream2.write(1138166333441L, entry.getKey());
            dumpTimer(protoOutputStream2, 1146756268034L, entry.getValue(), jElapsedRealtime, 0);
            protoOutputStream2.end(jStart7);
            z = z;
        }
        int i9 = z;
        SparseArray<? extends Uid> uidStats = getUidStats();
        long totalTimeLocked = 0;
        long totalTimeLocked2 = 0;
        for (int i10 = i5; i10 < uidStats.size(); i10++) {
            ArrayMap<String, ? extends Uid.Wakelock> wakelockStats = uidStats.valueAt(i10).getWakelockStats();
            for (int size = wakelockStats.size() - i9; size >= 0; size--) {
                Uid.Wakelock wakelockValueAt = wakelockStats.valueAt(size);
                Timer wakeTime = wakelockValueAt.getWakeTime(i9);
                if (wakeTime != null) {
                    totalTimeLocked += wakeTime.getTotalTimeLocked(jElapsedRealtime, i5);
                }
                Timer wakeTime2 = wakelockValueAt.getWakeTime(i5);
                if (wakeTime2 != null) {
                    totalTimeLocked2 += wakeTime2.getTotalTimeLocked(jElapsedRealtime, i5);
                }
            }
        }
        long jStart8 = protoOutputStream2.start(1146756268047L);
        protoOutputStream2.write(1112396529665L, getScreenOnTime(jElapsedRealtime, i5) / 1000);
        protoOutputStream2.write(1112396529666L, getPhoneOnTime(jElapsedRealtime, i5) / 1000);
        protoOutputStream2.write(1112396529667L, totalTimeLocked / 1000);
        protoOutputStream2.write(1112396529668L, totalTimeLocked2 / 1000);
        protoOutputStream2.write(1112396529669L, getMobileRadioActiveTime(jElapsedRealtime, i5) / 1000);
        protoOutputStream2.write(1112396529670L, getMobileRadioActiveAdjustedTime(i5) / 1000);
        protoOutputStream2.write(1120986464263L, getMobileRadioActiveCount(i5));
        protoOutputStream2.write(1120986464264L, getMobileRadioActiveUnknownTime(i5) / 1000);
        protoOutputStream2.write(1112396529673L, getInteractiveTime(jElapsedRealtime, i5) / 1000);
        protoOutputStream2.write(1112396529674L, getPowerSaveModeEnabledTime(jElapsedRealtime, i5) / 1000);
        protoOutputStream2.write(1120986464267L, getNumConnectivityChange(i5));
        protoOutputStream2.write(1112396529676L, getDeviceIdleModeTime(2, jElapsedRealtime, i5) / 1000);
        protoOutputStream2.write(1120986464269L, getDeviceIdleModeCount(2, i5));
        protoOutputStream2.write(1112396529678L, getDeviceIdlingTime(2, jElapsedRealtime, i5) / 1000);
        protoOutputStream2.write(1120986464271L, getDeviceIdlingCount(2, i5));
        protoOutputStream2.write(1112396529680L, getLongestDeviceIdleModeTime(2));
        protoOutputStream2.write(1112396529681L, getDeviceIdleModeTime(i9, jElapsedRealtime, i5) / 1000);
        protoOutputStream2.write(1120986464274L, getDeviceIdleModeCount(i9, i5));
        protoOutputStream2.write(1112396529683L, getDeviceIdlingTime(i9, jElapsedRealtime, i5) / 1000);
        protoOutputStream2.write(1120986464276L, getDeviceIdlingCount(i9, i5));
        protoOutputStream2.write(1112396529685L, getLongestDeviceIdleModeTime(i9));
        protoOutputStream2.end(jStart8);
        long wifiMulticastWakelockTime = getWifiMulticastWakelockTime(jElapsedRealtime, i5);
        int wifiMulticastWakelockCount = getWifiMulticastWakelockCount(i5);
        long jStart9 = protoOutputStream2.start(1146756268055L);
        protoOutputStream2.write(1112396529665L, wifiMulticastWakelockTime / 1000);
        protoOutputStream2.write(1120986464258L, wifiMulticastWakelockCount);
        protoOutputStream2.end(jStart9);
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
            long jStart10 = protoOutputStream2.start(2246267895825L);
            protoOutputStream2.write(1159641169921L, i);
            protoOutputStream2.write(1120986464258L, i5);
            protoOutputStream2.write(1103806595075L, aggregateBatteryConsumer.getConsumedPower(i11));
            protoOutputStream2.write(1133871366148L, shouldHidePowerComponent(i11));
            protoOutputStream2.write(1103806595077L, i5);
            protoOutputStream2.write(1103806595078L, i5);
            protoOutputStream2.end(jStart10);
            i11++;
            i9 = 1;
            i8 = 2;
        }
        long jStart11 = protoOutputStream2.start(1146756268050L);
        protoOutputStream2.write(1103806595073L, batteryUsageStats.getBatteryCapacity());
        protoOutputStream2.write(1103806595074L, batteryUsageStats.getConsumedPower());
        protoOutputStream2.write(1103806595075L, ((Double) batteryUsageStats.getDischargedPowerRange().getLower()).doubleValue());
        protoOutputStream2.write(1103806595076L, ((Double) batteryUsageStats.getDischargedPowerRange().getUpper()).doubleValue());
        protoOutputStream2.end(jStart11);
        Map<String, ? extends Timer> rpmStats = getRpmStats();
        Map<String, ? extends Timer> screenOffRpmStats = getScreenOffRpmStats();
        for (Map.Entry<String, ? extends Timer> entry2 : rpmStats.entrySet()) {
            long jStart12 = protoOutputStream2.start(2246267895827L);
            protoOutputStream2.write(1138166333441L, entry2.getKey());
            dumpTimer(protoOutputStream2, 1146756268034L, entry2.getValue(), jElapsedRealtime, 0);
            protoOutputStream2 = protoOutputStream;
            dumpTimer(protoOutputStream2, 1146756268035L, screenOffRpmStats.get(entry2.getKey()), jElapsedRealtime, 0);
            protoOutputStream2.end(jStart12);
        }
        for (int i12 = i5; i12 < 5; i12++) {
            long jStart13 = protoOutputStream2.start(2246267895828L);
            protoOutputStream2.write(1159641169921L, i12);
            dumpTimer(protoOutputStream2, 1146756268034L, getScreenBrightnessTimer(i12), jElapsedRealtime, 0);
            protoOutputStream2.end(jStart13);
        }
        dumpTimer(protoOutputStream2, 1146756268053L, getPhoneSignalScanningTimer(), jElapsedRealtime, 0);
        for (int i13 = i5; i13 < CellSignalStrength.getNumSignalStrengthLevels(); i13++) {
            long jStart14 = protoOutputStream2.start(2246267895824L);
            protoOutputStream2.write(1159641169921L, i13);
            dumpTimer(protoOutputStream2, 1146756268034L, getPhoneSignalStrengthTimer(i13), jElapsedRealtime, 0);
            protoOutputStream2.end(jStart14);
        }
        for (Map.Entry<String, ? extends Timer> entry3 : getWakeupReasonStats().entrySet()) {
            long jStart15 = protoOutputStream2.start(2246267895830L);
            protoOutputStream2.write(1138166333441L, entry3.getKey());
            dumpTimer(protoOutputStream2, 1146756268034L, entry3.getValue(), jElapsedRealtime, 0);
            protoOutputStream2.end(jStart15);
        }
        for (int i14 = i5; i14 < 5; i14++) {
            long jStart16 = protoOutputStream2.start(2246267895832L);
            protoOutputStream2.write(1159641169921L, i14);
            dumpTimer(protoOutputStream2, 1146756268034L, getWifiSignalStrengthTimer(i14), jElapsedRealtime, 0);
            protoOutputStream2.end(jStart16);
        }
        for (int i15 = i5; i15 < 8; i15++) {
            long jStart17 = protoOutputStream2.start(2246267895833L);
            protoOutputStream2.write(1159641169921L, i15);
            dumpTimer(protoOutputStream2, 1146756268034L, getWifiStateTimer(i15), jElapsedRealtime, 0);
            protoOutputStream2.end(jStart17);
        }
        while (i5 < 13) {
            long jStart18 = protoOutputStream2.start(2246267895834L);
            protoOutputStream2.write(1159641169921L, i5);
            dumpTimer(protoOutputStream2, 1146756268034L, getWifiSupplStateTimer(i5), jElapsedRealtime, 0);
            protoOutputStream2.end(jStart18);
            i5++;
        }
        protoOutputStream2.end(jStart);
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

        ProportionalAttributionCalculator(Context context, BatteryUsageStats batteryUsageStats) throws Resources.NotFoundException {
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
            double consumedPower = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            for (int size = uidBatteryConsumers.size() - 1; size >= 0; size--) {
                UidBatteryConsumer uidBatteryConsumer = uidBatteryConsumers.get(size);
                int uid = uidBatteryConsumer.getUid();
                if (isSystemUid(uid)) {
                    this.mProportionalPowerMah.put(uid, -1.0d);
                    consumedPower += uidBatteryConsumer.getConsumedPower();
                }
            }
            double consumedPower2 = batteryUsageStats.getConsumedPower() - consumedPower;
            if (Math.abs(consumedPower2) > 0.001d) {
                for (int size2 = uidBatteryConsumers.size() - 1; size2 >= 0; size2--) {
                    UidBatteryConsumer uidBatteryConsumer2 = uidBatteryConsumers.get(size2);
                    int uid2 = uidBatteryConsumer2.getUid();
                    if (this.mProportionalPowerMah.get(uid2) != -1.0d) {
                        double consumedPower3 = uidBatteryConsumer2.getConsumedPower();
                        this.mProportionalPowerMah.put(uid2, consumedPower3 + ((consumedPower * consumedPower3) / consumedPower2));
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

    /* JADX WARN: Removed duplicated region for block: B:7:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private List<UidMobileRadioStats> getUidMobileRadioStats(List<UidBatteryConsumer> list) {
        int uid;
        Uid uid2;
        int i;
        SparseArray<? extends Uid> uidStats = getUidStats();
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        int i2 = 0;
        int i3 = 0;
        while (i3 < list.size()) {
            UidBatteryConsumer uidBatteryConsumer = list.get(i3);
            if (uidBatteryConsumer.getConsumedPower(8) != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && (uid2 = uidStats.get((uid = uidBatteryConsumer.getUid()))) != null) {
                long networkActivityPackets = uid2.getNetworkActivityPackets(i2, i2);
                long networkActivityPackets2 = uid2.getNetworkActivityPackets(1, i2);
                if (networkActivityPackets == 0 && networkActivityPackets2 == 0) {
                    i = i3;
                } else {
                    long mobileRadioActiveTime = uid2.getMobileRadioActiveTime(i2) / 1000;
                    int mobileRadioActiveCount = uid2.getMobileRadioActiveCount(i2);
                    i = i3;
                    double d = mobileRadioActiveTime / (networkActivityPackets + networkActivityPackets2);
                    if (d != SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                        arrayListNewArrayList.add(new UidMobileRadioStats(uid, networkActivityPackets, networkActivityPackets2, mobileRadioActiveTime, mobileRadioActiveCount, d));
                    }
                }
            }
            i3 = i + 1;
            i2 = 0;
        }
        arrayListNewArrayList.sort(new Comparator() { // from class: android.os.BatteryStats$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Double.compare(((BatteryStats.UidMobileRadioStats) obj2).millisecondsPerPacket, ((BatteryStats.UidMobileRadioStats) obj).millisecondsPerPacket);
            }
        });
        return arrayListNewArrayList;
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

    void printLatestBackupData(PrintWriter printWriter) throws NumberFormatException, IOException {
        File[] fileArrListFiles;
        File file = new File("/data/log/batterystats/");
        if (!file.exists() || (fileArrListFiles = file.listFiles()) == null) {
            return;
        }
        long j = 0;
        for (File file2 : fileArrListFiles) {
            long j2 = Long.parseLong(file2.getAbsolutePath().replace("/data/log/batterystats/newbatterystats", ""));
            if (j2 > j) {
                j = j2;
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
                            String line = bufferedReader.readLine();
                            if (line != null) {
                                printWriter.println(line);
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
        int length = 0;
        for (int i : cpuScalingPolicies.getPolicies()) {
            str = str + "\n      ";
            for (int i2 = 0; i2 < cpuScalingPolicies.getFrequencies(i).length; i2++) {
                str = str + " " + jArr[length + i2];
            }
            length += cpuScalingPolicies.getFrequencies(i).length;
        }
        return str;
    }
}
