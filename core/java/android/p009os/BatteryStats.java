package android.p009os;

import android.app.Notification;
import android.app.backup.FullBackup;
import android.app.blob.XmlTags;
import android.app.job.JobParameters;
import android.app.slice.Slice;
import android.content.Context;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.PackageManager;
import android.content.res.Resources;
import android.hardware.gnss.GnssSignalType;
import android.hardware.p006tv.tuner.FrontendInnerFec;
import android.hardware.scontext.SContextConstants;
import android.hardware.usb.UsbManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.location.LocationManager;
import android.media.audio.common.AudioDeviceDescription;
import android.provider.Settings;
import android.security.Credentials;
import android.service.timezone.TimeZoneProviderService;
import android.telephony.CellSignalStrength;
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
import com.android.internal.C4337R;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.p029os.BatteryStatsHistoryIterator;
import com.android.internal.telephony.DctConstants;
import com.google.android.collect.Lists;
import com.samsung.android.biometrics.SemBiometricConstants;
import com.samsung.android.core.CoreSaConstant;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.ims.options.SemCapabilities;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
  static final String[] SCREEN_BRIGHTNESS_NAMES;
  static final String[] SCREEN_BRIGHTNESS_SHORT_NAMES;
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
  public static final long[] JOB_FRESHNESS_BUCKETS = {
    3600000, 7200000, 14400000, 28800000, Long.MAX_VALUE
  };

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

  protected abstract BatteryUsageStats getBatteryUsageStats(Context context, boolean z);

  public abstract BluetoothBatteryStats getBluetoothBatteryStats();

  public abstract ControllerActivityCounter getBluetoothControllerActivity();

  public abstract long getBluetoothEnergyConsumptionUC();

  public abstract long getBluetoothScanTime(long j, int i);

  public abstract long getCameraEnergyConsumptionUC();

  public abstract long getCameraOnTime(long j, int i);

  public abstract LevelStepTracker getChargeLevelStepTracker();

  public abstract long getCpuEnergyConsumptionUC();

  public abstract int getCpuFreqCount();

  public abstract long[] getCpuFreqs();

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

  public abstract int getDischargeAmountScreenOff();

  public abstract int getDischargeAmountScreenOffSinceCharge();

  public abstract int getDischargeAmountScreenOffSinceChargeCoulombCounter();

  public abstract int getDischargeAmountScreenOffSinceChargePermil();

  public abstract int getDischargeAmountScreenOn();

  public abstract int getDischargeAmountScreenOnSinceCharge();

  public abstract int getDischargeAmountScreenOnSinceChargeCoulombCounter();

  public abstract int getDischargeAmountScreenOnSinceChargePermil();

  public abstract int getDischargeAmountSubScreenOffSinceChargePermil();

  public abstract int getDischargeAmountSubScreenOnSinceChargePermil();

  public abstract int getDischargeCurrentLevel();

  public abstract LevelStepTracker getDischargeLevelStepTracker();

  public abstract int getDischargeStartLevel();

  public abstract int getDisplayCount();

  public abstract int getDisplayHighRefreshRateCount(int i, int i2);

  public abstract long getDisplayHighRefreshRateTime(int i, long j, int i2);

  public abstract Timer getDisplayHighRefreshRateTimer(int i);

  public abstract long getDisplayPowerDrainCount(int i);

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

  public abstract Map<String, ? extends LongCounter> getScreenWakeStats();

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

  public abstract long getTxSharingDischargeAmount(int i);

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

  public abstract boolean hasDisplayPowerReporting();

  public abstract boolean hasModemActivityReporting();

  public abstract boolean hasSpeakerOutPowerReporting();

  public abstract boolean hasWifiActivityReporting();

  public abstract boolean isJdmModel();

  public abstract boolean isProcessStateDataAvailable();

  public abstract BatteryStatsHistoryIterator iterateBatteryStatsHistory();

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
    int length = TelephonyManager.getAllNetworkTypes().length + 1;
    DATA_CONNECTION_EMERGENCY_SERVICE = length;
    int i = length + 1;
    DATA_CONNECTION_OTHER = i;
    String[] strArr4 = {
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
    DATA_CONNECTION_NAMES = strArr4;
    NUM_DATA_CONNECTION_TYPES = i + 1;
    RADIO_ACCESS_TECHNOLOGY_NAMES = new String[] {"Other", DctConstants.RAT_NAME_LTE, "NR"};
    String[] strArr5 = {
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
    WIFI_SUPPL_STATE_NAMES = strArr5;
    String[] strArr6 = {
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
    WIFI_SUPPL_STATE_SHORT_NAMES = strArr6;
    HISTORY_STATE_DESCRIPTIONS =
        new BitDescription[] {
          new BitDescription(Integer.MIN_VALUE, "running", "r"),
          new BitDescription(1073741824, "wake_lock", "w"),
          new BitDescription(8388608, Context.SENSOR_SERVICE, XmlTags.TAG_SESSION),
          new BitDescription(536870912, LocationManager.GPS_PROVIDER, "g"),
          new BitDescription(268435456, "wifi_full_lock", "Wl"),
          new BitDescription(134217728, "wifi_scan", "Ws"),
          new BitDescription(65536, "wifi_multicast", "Wm"),
          new BitDescription(67108864, "wifi_radio", "Wr"),
          new BitDescription(33554432, "mobile_radio", "Pr"),
          new BitDescription(2097152, "phone_scanning", "Psc"),
          new BitDescription(4194304, "audio", "a"),
          new BitDescription(1048576, "screen", GnssSignalType.CODE_TYPE_S),
          new BitDescription(524288, BatteryManager.EXTRA_PLUGGED, "BP"),
          new BitDescription(262144, "screen_doze", "Sd"),
          new BitDescription(
              HistoryItem.STATE_DATA_CONNECTION_MASK, 9, "data_conn", "Pcn", strArr4, strArr4),
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
              strArr2,
              strArr3)
        };
    HISTORY_STATE2_DESCRIPTIONS =
        new BitDescription[] {
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
          new BitDescription(4194304, "bluetooth", "b"),
          new BitDescription(
              112,
              4,
              "wifi_signal_strength",
              "Wss",
              new String[] {"0", "1", "2", "3", "4"},
              new String[] {"0", "1", "2", "3", "4"}),
          new BitDescription(15, 0, "wifi_suppl", "Wsp", strArr5, strArr6),
          new BitDescription(2097152, Context.CAMERA_SERVICE, Credentials.CERTIFICATE_USAGE_CA),
          new BitDescription(1048576, "ble_scan", "bles"),
          new BitDescription(524288, "cellular_high_tx_power", "Chtp"),
          new BitDescription(
              128,
              7,
              "gps_signal_quality",
              "Gss",
              new String[] {"poor", "good"},
              new String[] {"poor", "good"})
        };
    HISTORY_EVENT_NAMES =
        new String[] {
          SemCapabilities.FEATURE_TAG_NULL,
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
          "est_capacity"
        };
    HISTORY_EVENT_CHECKIN_NAMES =
        new String[] {
          "Enl", "Epr", "Efg", "Etp", "Esy", "Ewl", "Ejb", "Eur", "Euf", "Ecn", "Eac", "Epi", "Epu",
          "Eal", "Est", "Eai", "Eaa", "Etw", "Esw", "Ewa", "Elw", "Eec"
        };
    IntToString intToString =
        new IntToString() { // from class: android.os.BatteryStats$$ExternalSyntheticLambda0
          @Override // android.os.BatteryStats.IntToString
          public final String applyAsString(int i2) {
            return UserHandle.formatUid(i2);
          }
        };
    sUidToString = intToString;
    IntToString intToString2 =
        new IntToString() { // from class: android.os.BatteryStats$$ExternalSyntheticLambda1
          @Override // android.os.BatteryStats.IntToString
          public final String applyAsString(int i2) {
            return Integer.toString(i2);
          }
        };
    sIntToString = intToString2;
    HISTORY_EVENT_INT_FORMATTERS =
        new IntToString[] {
          intToString,
          intToString,
          intToString,
          intToString,
          intToString,
          intToString,
          intToString,
          intToString,
          intToString,
          intToString,
          intToString,
          intToString2,
          intToString,
          intToString,
          intToString,
          intToString,
          intToString,
          intToString,
          intToString,
          intToString,
          intToString,
          intToString2
        };
    WIFI_STATE_NAMES =
        new String[] {
          "off",
          "scanning",
          "no_net",
          "disconn",
          "sta",
          SemWifiP2pManager.TYPE_WIFI_P2P,
          "sta_p2p",
          "soft_ap"
        };
    STEP_LEVEL_MODES_OF_INTEREST = new int[] {7, 15, 11, 7, 7, 7, 7, 7, 15, 11};
    STEP_LEVEL_MODE_VALUES = new int[] {0, 4, 8, 1, 5, 2, 6, 3, 7, 11};
    STEP_LEVEL_MODE_LABELS =
        new String[] {
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
    String[] strArr7 = new String[19];
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
    static final String[] PROCESS_STATE_NAMES = {
      "Top", "Fg Service", "Foreground", "Background", "Top Sleeping", "Heavy Weight", "Cached"
    };
    public static final String[] UID_PROCESS_TYPES = {
      "T", "FS", "F", GnssSignalType.CODE_TYPE_B, "TS", "HW", GnssSignalType.CODE_TYPE_C
    };

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

    public abstract long getDisplayPowerDrain(int i);

    public abstract long getDisplayTime(int i);

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
      String[] strArr = {
        "other",
        "button",
        "touch",
        Context.ACCESSIBILITY_SERVICE,
        Context.ATTENTION_SERVICE,
        "faceDown",
        "deviceState"
      };
      USER_ACTIVITY_TYPES = strArr;
      NUM_USER_ACTIVITY_TYPES = strArr.length;
    }

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
      long[] jArr = new long[numSteps];
      this.mStepDurations = jArr;
      System.arraycopy(steps, 0, jArr, 0, numSteps);
    }

    public long getDurationAt(int index) {
      return this.mStepDurations[index] & BatteryStats.STEP_LEVEL_TIME_MASK;
    }

    public int getLevelAt(int index) {
      return (int) ((this.mStepDurations[index] & BatteryStats.STEP_LEVEL_LEVEL_MASK) >> 40);
    }

    public int getInitModeAt(int index) {
      return (int) ((this.mStepDurations[index] & BatteryStats.STEP_LEVEL_INITIAL_MODE_MASK) >> 48);
    }

    public int getModModeAt(int index) {
      return (int)
          ((this.mStepDurations[index] & BatteryStats.STEP_LEVEL_MODIFIED_MODE_MASK) >> 56);
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
          this.mStepDurations[index] = (BatteryStats.STEP_LEVEL_TIME_MASK & duration) | out2;
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

    public long computeTimeEstimate(long modesOfInterest, long modeValues, int[] outNumOfInterest) {
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
        if ((modMode & modesOfInterest) == 0 && (initMode & modesOfInterest) == modeValues) {
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

  public static final class EnergyConsumerDetails {
    public long[] chargeUC;
    public EnergyConsumer[] consumers;

    public static final class EnergyConsumer {
      public String name;
      public int ordinal;
      public int type;
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < this.consumers.length; i++) {
        if (this.chargeUC[i] != -1) {
          if (sb.length() != 0) {
            sb.append(' ');
          }
          sb.append(this.consumers[i].name);
          sb.append('=');
          sb.append(this.chargeUC[i]);
        }
      }
      return sb.toString();
    }
  }

  public static final class CpuUsageDetails {
    public String[] cpuBracketDescriptions;
    public long[] cpuUsageMs;
    public int uid;

    public String toString() {
      StringBuilder sb = new StringBuilder();
      UserHandle.formatUid(sb, this.uid);
      sb.append(": ");
      for (int bracket = 0; bracket < this.cpuUsageMs.length; bracket++) {
        if (bracket != 0) {
          sb.append(", ");
        }
        sb.append(this.cpuUsageMs[bracket]);
      }
      return sb.toString();
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
    public static final int STATE2_GPS_SIGNAL_QUALITY_MASK = 128;
    public static final int STATE2_GPS_SIGNAL_QUALITY_SHIFT = 7;
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
    public char batteryVoltage;
    public byte cmd;
    public CpuUsageDetails cpuUsageDetails;
    public short current;
    public long currentTime;
    public EnergyConsumerDetails energyConsumerDetails;
    public int eventCode;
    public HistoryTag eventTag;
    public byte highSpeakerVolume;
    public final HistoryTag localEventTag;
    public final HistoryTag localWakeReasonTag;
    public final HistoryTag localWakelockTag;
    public double modemRailChargeMah;
    public HistoryItem next;
    public int numReadInts;
    public byte otgOnline;
    public byte pa_temp;
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
      readFromParcel(src);
    }

    public void writeToParcel(Parcel dest, int flags) {
      dest.writeLong(this.time);
      int bat =
          (this.cmd & 255)
              | ((this.batteryLevel << 8) & 65280)
              | ((this.batteryStatus << 16) & 983040)
              | ((this.batteryHealth << 20) & SurfaceControl.NO_REMOTECONTROL)
              | ((this.batteryPlugType << SprAnimatorBase.INTERPOLATOR_TYPE_ELASTICEASEINOUT)
                  & 251658240)
              | (this.wakelockTag != null ? 268435456 : 0)
              | (this.wakeReasonTag != null ? 536870912 : 0)
              | (this.eventCode != 0 ? 1073741824 : 0);
      dest.writeInt(bat);
      int bat2 = (this.batteryTemperature & 65535) | ((this.batteryVoltage << 16) & (-65536));
      dest.writeInt(bat2);
      int bat3 =
          (this.current & 65535)
              | ((this.ap_temp << 16) & Spanned.SPAN_PRIORITY)
              | ((this.pa_temp << SprAnimatorBase.INTERPOLATOR_TYPE_ELASTICEASEINOUT)
                  & (-16777216));
      dest.writeInt(bat3);
      int bat4 =
          ((this.sub_batt_temp << 8) & 65280)
              | ((this.skin_temp << 16) & Spanned.SPAN_PRIORITY)
              | ((this.wifi_ap << SprAnimatorBase.INTERPOLATOR_TYPE_EXPOEASEIN) & 33554432)
              | ((this.otgOnline << SprAnimatorBase.INTERPOLATOR_TYPE_EXPOEASEOUT) & 67108864)
              | ((this.highSpeakerVolume << 27) & 134217728)
              | ((this.subScreenOn << SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN) & 268435456)
              | ((this.subScreenDoze << SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEOUT) & 536870912);
      dest.writeInt(bat4);
      int bat5 =
          (this.batterySecTxShareEvent & 16777215)
              | ((this.batterySecOnline << SprAnimatorBase.INTERPOLATOR_TYPE_ELASTICEASEINOUT)
                  & (-16777216));
      dest.writeInt(bat5);
      dest.writeInt(this.batterySecCurrentEvent);
      dest.writeInt(this.batterySecEvent);
      dest.writeInt(this.protectBatteryMode);
      dest.writeInt(this.batteryChargeUah);
      dest.writeDouble(this.modemRailChargeMah);
      dest.writeDouble(this.wifiRailChargeMah);
      dest.writeInt(this.states);
      dest.writeInt(this.states2);
      HistoryTag historyTag = this.wakelockTag;
      if (historyTag != null) {
        historyTag.writeToParcel(dest, flags);
      }
      HistoryTag historyTag2 = this.wakeReasonTag;
      if (historyTag2 != null) {
        historyTag2.writeToParcel(dest, flags);
      }
      int i = this.eventCode;
      if (i != 0) {
        dest.writeInt(i);
        this.eventTag.writeToParcel(dest, flags);
      }
      byte b = this.cmd;
      if (b == 5 || b == 7) {
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
      this.batteryVoltage = (char) ((bat2 >> 16) & 65535);
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
        HistoryTag historyTag = this.localWakelockTag;
        this.wakelockTag = historyTag;
        historyTag.readFromParcel(src);
      } else {
        this.wakelockTag = null;
      }
      if ((536870912 & bat) != 0) {
        HistoryTag historyTag2 = this.localWakeReasonTag;
        this.wakeReasonTag = historyTag2;
        historyTag2.readFromParcel(src);
      } else {
        this.wakeReasonTag = null;
      }
      if ((1073741824 & bat) != 0) {
        this.eventCode = src.readInt();
        HistoryTag historyTag3 = this.localEventTag;
        this.eventTag = historyTag3;
        historyTag3.readFromParcel(src);
      } else {
        this.eventCode = 0;
        this.eventTag = null;
      }
      byte b = this.cmd;
      if (b == 5 || b == 7) {
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
      this.batteryVoltage = (char) 0;
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
      this.energyConsumerDetails = null;
      this.cpuUsageDetails = null;
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
        HistoryTag historyTag = this.localWakelockTag;
        this.wakelockTag = historyTag;
        historyTag.setTo(o.wakelockTag);
      } else {
        this.wakelockTag = null;
      }
      if (o.wakeReasonTag != null) {
        HistoryTag historyTag2 = this.localWakeReasonTag;
        this.wakeReasonTag = historyTag2;
        historyTag2.setTo(o.wakeReasonTag);
      } else {
        this.wakeReasonTag = null;
      }
      this.eventCode = o.eventCode;
      if (o.eventTag != null) {
        HistoryTag historyTag3 = this.localEventTag;
        this.eventTag = historyTag3;
        historyTag3.setTo(o.eventTag);
      } else {
        this.eventTag = null;
      }
      this.tagsFirstOccurrence = o.tagsFirstOccurrence;
      this.currentTime = o.currentTime;
      this.energyConsumerDetails = o.energyConsumerDetails;
      this.cpuUsageDetails = o.cpuUsageDetails;
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
      HistoryTag historyTag = this.wakelockTag;
      HistoryTag historyTag2 = o.wakelockTag;
      if (historyTag != historyTag2
          && (historyTag == null || historyTag2 == null || !historyTag.equals(historyTag2))) {
        return false;
      }
      HistoryTag historyTag3 = this.wakeReasonTag;
      HistoryTag historyTag4 = o.wakeReasonTag;
      if (historyTag3 != historyTag4
          && (historyTag3 == null || historyTag4 == null || !historyTag3.equals(historyTag4))) {
        return false;
      }
      HistoryTag historyTag5 = this.eventTag;
      HistoryTag historyTag6 = o.eventTag;
      if (historyTag5 != historyTag6) {
        return (historyTag5 == null || historyTag6 == null || !historyTag5.equals(historyTag6))
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
        int mask, int shift, String name, String shortName, String[] values, String[] shortValues) {
      this.mask = mask;
      this.shift = shift;
      this.name = name;
      this.shortName = shortName;
      this.values = values;
      this.shortValues = shortValues;
    }
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

  private static final void dumpLineHeader(PrintWriter pw, int uid, String category, String type) {
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

  /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
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
      long scanTimeMs2 = totalControllerActivityTimeMs - ((idleTimeMs + rxTimeMs2) + totalTxTimeMs);
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
    if (!controllerName.equals(CELLULAR_CONTROLLER_NAME)) {
      i = which;
      obj = CELLULAR_CONTROLLER_NAME;
    } else {
      i = which;
      long sleepTimeMs = counter.getSleepTimeCounter().getCountLocked(i);
      obj = CELLULAR_CONTROLLER_NAME;
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
          .append(new DecimalFormat("#.##").format(monitoredRailChargeConsumedMaMs / 3600000.0d));
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
          long timeMs2 = getActiveTxRadioDurationMs(rat4, freqLvl3, strength2, rawRealtimeMs);
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
      Context context, PrintWriter printWriter, int i, int i2, boolean z) {
    String str;
    long j;
    int i3;
    AggregateBatteryConsumer aggregateBatteryConsumer;
    BatteryUsageStats batteryUsageStats;
    List<UidBatteryConsumer> list;
    StringBuilder sb;
    long j2;
    String str2;
    int i4;
    char c;
    int i5;
    String str3;
    long j3;
    long[] jArr;
    Timer timer;
    String str4;
    ProportionalAttributionCalculator proportionalAttributionCalculator;
    long j4;
    long j5;
    Integer num;
    Map<String, ? extends Timer> map;
    long j6;
    long[] jArr2;
    StringBuilder sb2;
    StringBuilder sb3;
    String str5;
    long j7;
    ArrayMap<String, ? extends Uid.Pkg.Serv> arrayMap;
    ArrayMap<String, ? extends Uid.Pkg> arrayMap2;
    StringBuilder sb4;
    String str6;
    ArrayMap<String, ? extends Uid.Proc> arrayMap3;
    long[] jArr3;
    long[] jArr4;
    int i6;
    long j8;
    ArrayMap<String, SparseIntArray> arrayMap4;
    long j9;
    long j10;
    ArrayMap<String, ? extends Uid.Wakelock> arrayMap5;
    long j11;
    long j12;
    Timer timer2;
    ArrayMap<String, ? extends Timer> arrayMap6;
    StringBuilder sb5;
    String str7;
    char c2;
    int i7;
    long j13;
    int i8 = i2;
    if (i != 0) {
      dumpLine(
          printWriter,
          0,
          STAT_NAMES[i],
          Notification.CATEGORY_ERROR,
          "ERROR: BatteryStats.dumpCheckin called for which type "
              + i
              + " but only STATS_SINCE_CHARGED is supported.");
      return;
    }
    long uptimeMillis = SystemClock.uptimeMillis() * 1000;
    long elapsedRealtime = SystemClock.elapsedRealtime();
    long j14 = elapsedRealtime * 1000;
    long batteryUptime = getBatteryUptime(uptimeMillis);
    long computeBatteryUptime = computeBatteryUptime(uptimeMillis, i);
    long computeBatteryRealtime = computeBatteryRealtime(j14, i);
    long computeBatteryScreenOffUptime = computeBatteryScreenOffUptime(uptimeMillis, i);
    long computeBatteryScreenOffRealtime = computeBatteryScreenOffRealtime(j14, i);
    long computeRealtime = computeRealtime(j14, i);
    long computeUptime = computeUptime(uptimeMillis, i);
    long screenOnTime = getScreenOnTime(j14, i);
    long screenDozeTime = getScreenDozeTime(j14, i);
    long interactiveTime = getInteractiveTime(j14, i);
    long powerSaveModeEnabledTime = getPowerSaveModeEnabledTime(j14, i);
    long deviceIdleModeTime = getDeviceIdleModeTime(1, j14, i);
    long deviceIdleModeTime2 = getDeviceIdleModeTime(2, j14, i);
    long deviceIdlingTime = getDeviceIdlingTime(1, j14, i);
    long deviceIdlingTime2 = getDeviceIdlingTime(2, j14, i);
    int numConnectivityChange = getNumConnectivityChange(i);
    long phoneOnTime = getPhoneOnTime(j14, i);
    long uahDischarge = getUahDischarge(i);
    long uahDischargeScreenOff = getUahDischargeScreenOff(i);
    long uahDischargeScreenDoze = getUahDischargeScreenDoze(i);
    long uahDischargeLightDoze = getUahDischargeLightDoze(i);
    long uahDischargeDeepDoze = getUahDischargeDeepDoze(i);
    StringBuilder sb6 = new StringBuilder(128);
    SparseArray<? extends Uid> uidStats = getUidStats();
    int size = uidStats.size();
    String str8 = STAT_NAMES[i];
    Object[] objArr = new Object[12];
    objArr[0] = i == 0 ? Integer.valueOf(getStartCount()) : "N/A";
    objArr[1] = Long.valueOf(computeBatteryRealtime / 1000);
    objArr[2] = Long.valueOf(computeBatteryUptime / 1000);
    long j15 = uptimeMillis;
    objArr[3] = Long.valueOf(computeRealtime / 1000);
    objArr[4] = Long.valueOf(computeUptime / 1000);
    objArr[5] = Long.valueOf(getStartClockTime());
    objArr[6] = Long.valueOf(computeBatteryScreenOffRealtime / 1000);
    objArr[7] = Long.valueOf(computeBatteryScreenOffUptime / 1000);
    objArr[8] = Integer.valueOf(getEstimatedBatteryCapacity());
    objArr[9] = Integer.valueOf(getMinLearnedBatteryCapacity());
    objArr[10] = Integer.valueOf(getMaxLearnedBatteryCapacity());
    objArr[11] = Long.valueOf(screenDozeTime / 1000);
    dumpLine(printWriter, 0, str8, BATTERY_DATA, objArr);
    int i9 = 0;
    long j16 = 0;
    long j17 = 0;
    while (i9 < size) {
      ArrayMap<String, ? extends Uid.Wakelock> wakelockStats =
          uidStats.valueAt(i9).getWakelockStats();
      int i10 = size;
      int size2 = wakelockStats.size() - 1;
      while (size2 >= 0) {
        SparseArray<? extends Uid> sparseArray = uidStats;
        Uid.Wakelock valueAt = wakelockStats.valueAt(size2);
        long j18 = elapsedRealtime;
        Timer wakeTime = valueAt.getWakeTime(1);
        if (wakeTime != null) {
          j17 += wakeTime.getTotalTimeLocked(j14, i);
        }
        Timer wakeTime2 = valueAt.getWakeTime(0);
        if (wakeTime2 != null) {
          j16 += wakeTime2.getTotalTimeLocked(j14, i);
        }
        size2--;
        uidStats = sparseArray;
        elapsedRealtime = j18;
      }
      i9++;
      size = i10;
    }
    int i11 = size;
    dumpLine(
        printWriter,
        0,
        str8,
        GLOBAL_NETWORK_DATA,
        Long.valueOf(getNetworkActivityBytes(0, i)),
        Long.valueOf(getNetworkActivityBytes(1, i)),
        Long.valueOf(getNetworkActivityBytes(2, i)),
        Long.valueOf(getNetworkActivityBytes(3, i)),
        Long.valueOf(getNetworkActivityPackets(0, i)),
        Long.valueOf(getNetworkActivityPackets(1, i)),
        Long.valueOf(getNetworkActivityPackets(2, i)),
        Long.valueOf(getNetworkActivityPackets(3, i)),
        Long.valueOf(getNetworkActivityBytes(4, i)),
        Long.valueOf(getNetworkActivityBytes(5, i)));
    long j19 = batteryUptime;
    SparseArray<? extends Uid> sparseArray2 = uidStats;
    StringBuilder sb7 = sb6;
    long j20 = elapsedRealtime;
    dumpControllerActivityLine(
        printWriter, 0, str8, GLOBAL_MODEM_CONTROLLER_DATA, getModemControllerActivity(), i);
    dumpLine(
        printWriter,
        0,
        str8,
        GLOBAL_WIFI_DATA,
        Long.valueOf(getWifiOnTime(j14, i) / 1000),
        Long.valueOf(getGlobalWifiRunningTime(j14, i) / 1000),
        0,
        0,
        0);
    Integer num2 = 0;
    long j21 = j14;
    dumpControllerActivityLine(
        printWriter, 0, str8, GLOBAL_WIFI_CONTROLLER_DATA, getWifiControllerActivity(), i);
    dumpControllerActivityLine(
        printWriter,
        0,
        str8,
        GLOBAL_BLUETOOTH_CONTROLLER_DATA,
        getBluetoothControllerActivity(),
        i);
    boolean z2 = true;
    dumpLine(
        printWriter,
        0,
        str8,
        MISC_DATA,
        Long.valueOf(screenOnTime / 1000),
        Long.valueOf(phoneOnTime / 1000),
        Long.valueOf(j17 / 1000),
        Long.valueOf(j16 / 1000),
        Long.valueOf(getMobileRadioActiveTime(j21, i) / 1000),
        Long.valueOf(getMobileRadioActiveAdjustedTime(i) / 1000),
        Long.valueOf(interactiveTime / 1000),
        Long.valueOf(powerSaveModeEnabledTime / 1000),
        Integer.valueOf(numConnectivityChange),
        Long.valueOf(deviceIdleModeTime2 / 1000),
        Integer.valueOf(getDeviceIdleModeCount(2, i)),
        Long.valueOf(deviceIdlingTime2 / 1000),
        Integer.valueOf(getDeviceIdlingCount(2, i)),
        Integer.valueOf(getMobileRadioActiveCount(i)),
        Long.valueOf(getMobileRadioActiveUnknownTime(i) / 1000),
        Long.valueOf(deviceIdleModeTime / 1000),
        Integer.valueOf(getDeviceIdleModeCount(1, i)),
        Long.valueOf(deviceIdlingTime / 1000),
        Integer.valueOf(getDeviceIdlingCount(1, i)),
        Long.valueOf(getLongestDeviceIdleModeTime(1)),
        Long.valueOf(getLongestDeviceIdleModeTime(2)));
    Object[] objArr2 = new Object[5];
    int i12 = 0;
    for (int i13 = 5; i12 < i13; i13 = 5) {
      objArr2[i12] = Long.valueOf(getScreenBrightnessTime(i12, j21, i) / 1000);
      i12++;
    }
    dumpLine(printWriter, 0, str8, "br", objArr2);
    Object[] objArr3 = new Object[CellSignalStrength.getNumSignalStrengthLevels()];
    for (int i14 = 0; i14 < CellSignalStrength.getNumSignalStrengthLevels(); i14++) {
      objArr3[i14] = Long.valueOf(getPhoneSignalStrengthTime(i14, j21, i) / 1000);
    }
    dumpLine(printWriter, 0, str8, SIGNAL_STRENGTH_TIME_DATA, objArr3);
    dumpLine(
        printWriter,
        0,
        str8,
        SIGNAL_SCANNING_TIME_DATA,
        Long.valueOf(getPhoneSignalScanningTime(j21, i) / 1000));
    for (int i15 = 0; i15 < CellSignalStrength.getNumSignalStrengthLevels(); i15++) {
      objArr3[i15] = Integer.valueOf(getPhoneSignalStrengthCount(i15, i));
    }
    dumpLine(printWriter, 0, str8, SIGNAL_STRENGTH_COUNT_DATA, objArr3);
    Object[] objArr4 = new Object[NUM_DATA_CONNECTION_TYPES];
    for (int i16 = 0; i16 < NUM_DATA_CONNECTION_TYPES; i16++) {
      objArr4[i16] = Long.valueOf(getPhoneDataConnectionTime(i16, j21, i) / 1000);
    }
    dumpLine(printWriter, 0, str8, DATA_CONNECTION_TIME_DATA, objArr4);
    for (int i17 = 0; i17 < NUM_DATA_CONNECTION_TYPES; i17++) {
      objArr4[i17] = Integer.valueOf(getPhoneDataConnectionCount(i17, i));
    }
    dumpLine(printWriter, 0, str8, DATA_CONNECTION_COUNT_DATA, objArr4);
    Object[] objArr5 = new Object[8];
    int i18 = 0;
    for (int i19 = 8; i18 < i19; i19 = 8) {
      objArr5[i18] = Long.valueOf(getWifiStateTime(i18, j21, i) / 1000);
      i18++;
    }
    dumpLine(printWriter, 0, str8, WIFI_STATE_TIME_DATA, objArr5);
    for (int i20 = 0; i20 < 8; i20++) {
      objArr5[i20] = Integer.valueOf(getWifiStateCount(i20, i));
    }
    dumpLine(printWriter, 0, str8, WIFI_STATE_COUNT_DATA, objArr5);
    char c3 = '\r';
    Object[] objArr6 = new Object[13];
    for (int i21 = 0; i21 < 13; i21++) {
      objArr6[i21] = Long.valueOf(getWifiSupplStateTime(i21, j21, i) / 1000);
    }
    dumpLine(printWriter, 0, str8, WIFI_SUPPL_STATE_TIME_DATA, objArr6);
    for (int i22 = 0; i22 < 13; i22++) {
      objArr6[i22] = Integer.valueOf(getWifiSupplStateCount(i22, i));
    }
    dumpLine(printWriter, 0, str8, WIFI_SUPPL_STATE_COUNT_DATA, objArr6);
    Object[] objArr7 = new Object[5];
    int i23 = 0;
    for (int i24 = 5; i23 < i24; i24 = 5) {
      objArr7[i23] = Long.valueOf(getWifiSignalStrengthTime(i23, j21, i) / 1000);
      i23++;
    }
    dumpLine(printWriter, 0, str8, WIFI_SIGNAL_STRENGTH_TIME_DATA, objArr7);
    for (int i25 = 0; i25 < 5; i25++) {
      objArr7[i25] = Integer.valueOf(getWifiSignalStrengthCount(i25, i));
    }
    dumpLine(printWriter, 0, str8, WIFI_SIGNAL_STRENGTH_COUNT_DATA, objArr7);
    dumpLine(
        printWriter,
        0,
        str8,
        WIFI_MULTICAST_TOTAL_DATA,
        Long.valueOf(getWifiMulticastWakelockTime(j21, i) / 1000),
        Integer.valueOf(getWifiMulticastWakelockCount(i)));
    dumpLine(
        printWriter,
        0,
        str8,
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
    String str9 = "\"";
    if (i8 < 0) {
      Map<String, ? extends Timer> kernelWakelockStats = getKernelWakelockStats();
      if (kernelWakelockStats.size() > 0) {
        for (Map.Entry<String, ? extends Timer> entry : kernelWakelockStats.entrySet()) {
          sb7.setLength(0);
          String str10 = str9;
          printWakeLockCheckin(sb7, entry.getValue(), j21, null, i, "");
          dumpLine(
              printWriter,
              0,
              str8,
              KERNEL_WAKELOCK_DATA,
              str10 + entry.getKey() + str10,
              sb7.toString());
          z2 = z2;
          str9 = str10;
          objArr7 = objArr7;
          j21 = j21;
          c3 = '\r';
        }
        str = str9;
        j13 = j21;
      } else {
        str = "\"";
        j13 = j21;
      }
      Map<String, ? extends Timer> wakeupReasonStats = getWakeupReasonStats();
      if (wakeupReasonStats.size() > 0) {
        for (Iterator<Map.Entry<String, ? extends Timer>> it =
                wakeupReasonStats.entrySet().iterator();
            it.hasNext();
            it = it) {
          Map.Entry<String, ? extends Timer> next = it.next();
          dumpLine(
              printWriter,
              0,
              str8,
              WAKEUP_REASON_DATA,
              str + next.getKey() + str,
              Long.valueOf((next.getValue().getTotalTimeLocked(j13, i) + 500) / 1000),
              Integer.valueOf(next.getValue().getCountLocked(i)));
          wakeupReasonStats = wakeupReasonStats;
        }
        j = j13;
      } else {
        j = j13;
      }
    } else {
      str = "\"";
      j = j21;
    }
    Map<String, ? extends Timer> rpmStats = getRpmStats();
    Map<String, ? extends Timer> screenOffRpmStats = getScreenOffRpmStats();
    if (rpmStats.size() > 0) {
      Iterator<Map.Entry<String, ? extends Timer>> it2 = rpmStats.entrySet().iterator();
      while (it2.hasNext()) {
        Map.Entry<String, ? extends Timer> next2 = it2.next();
        sb7.setLength(0);
        Timer value = next2.getValue();
        long totalTimeLocked = (value.getTotalTimeLocked(j, i) + 500) / 1000;
        int countLocked = value.getCountLocked(i);
        Iterator<Map.Entry<String, ? extends Timer>> it3 = it2;
        Timer timer3 = screenOffRpmStats.get(next2.getKey());
        if (timer3 != null) {
          long totalTimeLocked2 = (timer3.getTotalTimeLocked(j, i) + 500) / 1000;
        }
        if (timer3 != null) {
          timer3.getCountLocked(i);
        }
        dumpLine(
            printWriter,
            0,
            str8,
            RESOURCE_POWER_MANAGER_DATA,
            str + next2.getKey() + str,
            Long.valueOf(totalTimeLocked),
            Integer.valueOf(countLocked));
        it2 = it3;
      }
    }
    BatteryUsageStats batteryUsageStats2 = getBatteryUsageStats(context, true);
    dumpLine(
        printWriter,
        0,
        str8,
        POWER_USE_SUMMARY_DATA,
        formatCharge(batteryUsageStats2.getBatteryCapacity()),
        formatCharge(batteryUsageStats2.getConsumedPower()),
        formatCharge(batteryUsageStats2.getDischargedPowerRange().getLower().doubleValue()),
        formatCharge(batteryUsageStats2.getDischargedPowerRange().getUpper().doubleValue()));
    AggregateBatteryConsumer aggregateBatteryConsumer2 =
        batteryUsageStats2.getAggregateBatteryConsumer(0);
    int i26 = 0;
    while (i26 < 19) {
      String str11 = CHECKIN_POWER_COMPONENT_LABELS[i26];
      if (str11 == null) {
        str11 = "???";
      }
      dumpLine(
          printWriter,
          0,
          str8,
          POWER_USE_ITEM_DATA,
          str11,
          formatCharge(aggregateBatteryConsumer2.getConsumedPower(i26)),
          Integer.valueOf(shouldHidePowerComponent(i26) ? 1 : 0),
          "0",
          "0");
      i26++;
      j = j;
    }
    long j22 = j;
    ProportionalAttributionCalculator proportionalAttributionCalculator2 =
        new ProportionalAttributionCalculator(context, batteryUsageStats2);
    List<UidBatteryConsumer> uidBatteryConsumers = batteryUsageStats2.getUidBatteryConsumers();
    int i27 = 0;
    while (i27 < uidBatteryConsumers.size()) {
      UidBatteryConsumer uidBatteryConsumer = uidBatteryConsumers.get(i27);
      dumpLine(
          printWriter,
          uidBatteryConsumer.getUid(),
          str8,
          POWER_USE_ITEM_DATA,
          "uid",
          formatCharge(uidBatteryConsumer.getConsumedPower()),
          Integer.valueOf(
              proportionalAttributionCalculator2.isSystemBatteryConsumer(uidBatteryConsumer)
                  ? 1
                  : 0),
          formatCharge(uidBatteryConsumer.getConsumedPower(0)),
          formatCharge(
              proportionalAttributionCalculator2.getProportionalPowerMah(uidBatteryConsumer)));
      i27++;
      aggregateBatteryConsumer2 = aggregateBatteryConsumer2;
    }
    AggregateBatteryConsumer aggregateBatteryConsumer3 = aggregateBatteryConsumer2;
    long[] cpuFreqs = getCpuFreqs();
    if (cpuFreqs != null) {
      sb7.setLength(0);
      for (int i28 = 0; i28 < cpuFreqs.length; i28++) {
        if (i28 != 0) {
          sb7.append(',');
        }
        sb7.append(cpuFreqs[i28]);
      }
      dumpLine(printWriter, 0, str8, GLOBAL_CPU_FREQ_DATA, sb7.toString());
    }
    int i29 = 0;
    while (true) {
      int i30 = i11;
      if (i29 < i30) {
        SparseArray<? extends Uid> sparseArray3 = sparseArray2;
        int keyAt = sparseArray3.keyAt(i29);
        if (i8 >= 0 && keyAt != i8) {
          jArr2 = cpuFreqs;
          sparseArray2 = sparseArray3;
          i4 = i30;
          i3 = i29;
          batteryUsageStats = batteryUsageStats2;
          proportionalAttributionCalculator = proportionalAttributionCalculator2;
          map = screenOffRpmStats;
          str4 = str8;
          sb3 = sb7;
          str5 = str;
          aggregateBatteryConsumer = aggregateBatteryConsumer3;
          j2 = j15;
          num = num2;
          j7 = j19;
          j5 = j20;
          j6 = j22;
          list = uidBatteryConsumers;
        } else {
          Uid valueAt2 = sparseArray3.valueAt(i29);
          sparseArray2 = sparseArray3;
          long networkActivityBytes = valueAt2.getNetworkActivityBytes(0, i);
          long networkActivityBytes2 = valueAt2.getNetworkActivityBytes(1, i);
          long networkActivityBytes3 = valueAt2.getNetworkActivityBytes(2, i);
          long networkActivityBytes4 = valueAt2.getNetworkActivityBytes(3, i);
          long networkActivityPackets = valueAt2.getNetworkActivityPackets(0, i);
          long networkActivityPackets2 = valueAt2.getNetworkActivityPackets(1, i);
          long mobileRadioActiveTime = valueAt2.getMobileRadioActiveTime(i);
          int mobileRadioActiveCount = valueAt2.getMobileRadioActiveCount(i);
          long mobileRadioApWakeupCount = valueAt2.getMobileRadioApWakeupCount(i);
          long networkActivityPackets3 = valueAt2.getNetworkActivityPackets(2, i);
          ProportionalAttributionCalculator proportionalAttributionCalculator3 =
              proportionalAttributionCalculator2;
          long networkActivityPackets4 = valueAt2.getNetworkActivityPackets(3, i);
          long wifiRadioApWakeupCount = valueAt2.getWifiRadioApWakeupCount(i);
          long networkActivityBytes5 = valueAt2.getNetworkActivityBytes(4, i);
          long networkActivityBytes6 = valueAt2.getNetworkActivityBytes(5, i);
          long networkActivityBytes7 = valueAt2.getNetworkActivityBytes(6, i);
          long networkActivityBytes8 = valueAt2.getNetworkActivityBytes(7, i);
          long networkActivityBytes9 = valueAt2.getNetworkActivityBytes(8, i);
          long networkActivityBytes10 = valueAt2.getNetworkActivityBytes(9, i);
          long networkActivityPackets5 = valueAt2.getNetworkActivityPackets(6, i);
          long networkActivityPackets6 = valueAt2.getNetworkActivityPackets(7, i);
          long networkActivityPackets7 = valueAt2.getNetworkActivityPackets(8, i);
          long networkActivityPackets8 = valueAt2.getNetworkActivityPackets(9, i);
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
                str8,
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
          i3 = i29;
          aggregateBatteryConsumer = aggregateBatteryConsumer3;
          batteryUsageStats = batteryUsageStats2;
          Map<String, ? extends Timer> map2 = screenOffRpmStats;
          long j23 = j22;
          list = uidBatteryConsumers;
          dumpControllerActivityLine(
              printWriter,
              keyAt,
              str8,
              MODEM_CONTROLLER_DATA,
              valueAt2.getModemControllerActivity(),
              i);
          long fullWifiLockTime = valueAt2.getFullWifiLockTime(j23, i);
          long wifiScanTime = valueAt2.getWifiScanTime(j23, i);
          int wifiScanCount = valueAt2.getWifiScanCount(i);
          int wifiScanBackgroundCount = valueAt2.getWifiScanBackgroundCount(i);
          long wifiScanActualTime = (valueAt2.getWifiScanActualTime(j23) + 500) / 1000;
          long wifiScanBackgroundTime = (valueAt2.getWifiScanBackgroundTime(j23) + 500) / 1000;
          long wifiRunningTime = valueAt2.getWifiRunningTime(j23, i);
          if (fullWifiLockTime != 0
              || wifiScanTime != 0
              || wifiScanCount != 0
              || wifiScanBackgroundCount != 0
              || wifiScanActualTime != 0
              || wifiScanBackgroundTime != 0
              || wifiRunningTime != 0) {
            sb = sb7;
            j2 = j15;
            str2 = str;
            i4 = i30;
            c = '\n';
            i5 = keyAt;
            dumpLine(
                printWriter,
                i5,
                str8,
                WIFI_DATA,
                Long.valueOf(fullWifiLockTime),
                Long.valueOf(wifiScanTime),
                Long.valueOf(wifiRunningTime),
                Integer.valueOf(wifiScanCount),
                num2,
                num2,
                num2,
                Integer.valueOf(wifiScanBackgroundCount),
                Long.valueOf(wifiScanActualTime),
                Long.valueOf(wifiScanBackgroundTime));
          } else {
            sb = sb7;
            str2 = str;
            i4 = i30;
            j2 = j15;
            i5 = keyAt;
            c = '\n';
          }
          StringBuilder sb8 = sb;
          dumpControllerActivityLine(
              printWriter, i5, str8, WIFI_CONTROLLER_DATA, valueAt2.getWifiControllerActivity(), i);
          Timer bluetoothScanTimer = valueAt2.getBluetoothScanTimer();
          if (bluetoothScanTimer != null) {
            long totalTimeLocked3 = (bluetoothScanTimer.getTotalTimeLocked(j23, i) + 500) / 1000;
            if (totalTimeLocked3 != 0) {
              int countLocked2 = bluetoothScanTimer.getCountLocked(i);
              Timer bluetoothScanBackgroundTimer = valueAt2.getBluetoothScanBackgroundTimer();
              int countLocked3 =
                  bluetoothScanBackgroundTimer != null
                      ? bluetoothScanBackgroundTimer.getCountLocked(i)
                      : 0;
              String str12 = str8;
              j3 = j20;
              long totalDurationMsLocked = bluetoothScanTimer.getTotalDurationMsLocked(j3);
              long totalDurationMsLocked2 =
                  bluetoothScanBackgroundTimer != null
                      ? bluetoothScanBackgroundTimer.getTotalDurationMsLocked(j3)
                      : 0L;
              int countLocked4 =
                  valueAt2.getBluetoothScanResultCounter() != null
                      ? valueAt2.getBluetoothScanResultCounter().getCountLocked(i)
                      : 0;
              if (valueAt2.getBluetoothScanResultBgCounter() != null) {
                timer = bluetoothScanTimer;
                i7 = valueAt2.getBluetoothScanResultBgCounter().getCountLocked(i);
              } else {
                timer = bluetoothScanTimer;
                i7 = 0;
              }
              jArr = cpuFreqs;
              Timer bluetoothUnoptimizedScanTimer = valueAt2.getBluetoothUnoptimizedScanTimer();
              long totalDurationMsLocked3 =
                  bluetoothUnoptimizedScanTimer != null
                      ? bluetoothUnoptimizedScanTimer.getTotalDurationMsLocked(j3)
                      : 0L;
              long maxDurationMsLocked =
                  bluetoothUnoptimizedScanTimer != null
                      ? bluetoothUnoptimizedScanTimer.getMaxDurationMsLocked(j3)
                      : 0L;
              Timer bluetoothUnoptimizedScanBackgroundTimer =
                  valueAt2.getBluetoothUnoptimizedScanBackgroundTimer();
              str3 = str12;
              dumpLine(
                  printWriter,
                  i5,
                  str3,
                  BLUETOOTH_MISC_DATA,
                  Long.valueOf(totalTimeLocked3),
                  Integer.valueOf(countLocked2),
                  Integer.valueOf(countLocked3),
                  Long.valueOf(totalDurationMsLocked),
                  Long.valueOf(totalDurationMsLocked2),
                  Integer.valueOf(countLocked4),
                  Integer.valueOf(i7),
                  Long.valueOf(totalDurationMsLocked3),
                  Long.valueOf(
                      bluetoothUnoptimizedScanBackgroundTimer != null
                          ? bluetoothUnoptimizedScanBackgroundTimer.getTotalDurationMsLocked(j3)
                          : 0L),
                  Long.valueOf(maxDurationMsLocked),
                  Long.valueOf(
                      bluetoothUnoptimizedScanBackgroundTimer != null
                          ? bluetoothUnoptimizedScanBackgroundTimer.getMaxDurationMsLocked(j3)
                          : 0L));
            } else {
              str3 = str8;
              j3 = j20;
              jArr = cpuFreqs;
              timer = bluetoothScanTimer;
            }
          } else {
            str3 = str8;
            j3 = j20;
            jArr = cpuFreqs;
            timer = bluetoothScanTimer;
          }
          str4 = str3;
          dumpControllerActivityLine(
              printWriter,
              i5,
              str4,
              BLUETOOTH_CONTROLLER_DATA,
              valueAt2.getBluetoothControllerActivity(),
              i);
          if (valueAt2.hasUserActivity()) {
            Object[] objArr8 = new Object[Uid.NUM_USER_ACTIVITY_TYPES];
            boolean z3 = false;
            for (int i31 = 0; i31 < Uid.NUM_USER_ACTIVITY_TYPES; i31++) {
              int userActivityCount = valueAt2.getUserActivityCount(i31, i);
              objArr8[i31] = Integer.valueOf(userActivityCount);
              if (userActivityCount != 0) {
                z3 = true;
              }
            }
            if (z3) {
              dumpLine(printWriter, i5, str4, USER_ACTIVITY_DATA, objArr8);
            }
          }
          if (valueAt2.getAggregatedPartialWakelockTimer() != null) {
            Timer aggregatedPartialWakelockTimer = valueAt2.getAggregatedPartialWakelockTimer();
            long totalDurationMsLocked4 =
                aggregatedPartialWakelockTimer.getTotalDurationMsLocked(j3);
            Timer subTimer = aggregatedPartialWakelockTimer.getSubTimer();
            dumpLine(
                printWriter,
                i5,
                str4,
                AGGREGATED_WAKELOCK_DATA,
                Long.valueOf(totalDurationMsLocked4),
                Long.valueOf(subTimer != null ? subTimer.getTotalDurationMsLocked(j3) : 0L));
          }
          ArrayMap<String, ? extends Uid.Wakelock> wakelockStats2 = valueAt2.getWakelockStats();
          int size3 = wakelockStats2.size() - 1;
          while (size3 >= 0) {
            Uid.Wakelock valueAt3 = wakelockStats2.valueAt(size3);
            sb8.setLength(0);
            long j24 = j3;
            int i32 = size3;
            long j25 = j23;
            ArrayMap<String, ? extends Uid.Wakelock> arrayMap7 = wakelockStats2;
            ProportionalAttributionCalculator proportionalAttributionCalculator4 =
                proportionalAttributionCalculator3;
            String printWakeLockCheckin =
                printWakeLockCheckin(
                    sb8, valueAt3.getWakeTime(1), j23, FullBackup.FILES_TREE_TOKEN, i, "");
            Timer wakeTime3 = valueAt3.getWakeTime(0);
            printWakeLockCheckin(
                sb8,
                valueAt3.getWakeTime(2),
                j25,
                "w",
                i,
                printWakeLockCheckin(
                    sb8,
                    wakeTime3 != null ? wakeTime3.getSubTimer() : null,
                    j25,
                    "bp",
                    i,
                    printWakeLockCheckin(sb8, wakeTime3, j25, "p", i, printWakeLockCheckin)));
            if (sb8.length() > 0) {
              String keyAt2 = arrayMap7.keyAt(i32);
              if (keyAt2.indexOf(44) < 0) {
                c2 = '_';
              } else {
                c2 = '_';
                keyAt2 = keyAt2.replace(',', '_');
              }
              if (keyAt2.indexOf(10) >= 0) {
                keyAt2 = keyAt2.replace('\n', c2);
              }
              if (keyAt2.indexOf(13) >= 0) {
                keyAt2 = keyAt2.replace('\r', c2);
              }
              dumpLine(printWriter, i5, str4, "wl", keyAt2, sb8.toString());
            }
            size3 = i32 - 1;
            wakelockStats2 = arrayMap7;
            proportionalAttributionCalculator3 = proportionalAttributionCalculator4;
            j3 = j24;
            j23 = j25;
          }
          long j26 = j23;
          long j27 = j3;
          proportionalAttributionCalculator = proportionalAttributionCalculator3;
          ArrayMap<String, ? extends Uid.Wakelock> arrayMap8 = wakelockStats2;
          Timer multicastWakelockStats = valueAt2.getMulticastWakelockStats();
          if (multicastWakelockStats == null) {
            j4 = j26;
          } else {
            j4 = j26;
            long totalTimeLocked4 = multicastWakelockStats.getTotalTimeLocked(j4, i) / 1000;
            int countLocked5 = multicastWakelockStats.getCountLocked(i);
            if (totalTimeLocked4 > 0) {
              dumpLine(
                  printWriter,
                  i5,
                  str4,
                  WIFI_MULTICAST_DATA,
                  Long.valueOf(totalTimeLocked4),
                  Integer.valueOf(countLocked5));
            }
          }
          ArrayMap<String, ? extends Timer> syncStats = valueAt2.getSyncStats();
          int size4 = syncStats.size() - 1;
          while (size4 >= 0) {
            Timer valueAt4 = syncStats.valueAt(size4);
            long totalTimeLocked5 = (valueAt4.getTotalTimeLocked(j4, i) + 500) / 1000;
            int countLocked6 = valueAt4.getCountLocked(i);
            Timer subTimer2 = valueAt4.getSubTimer();
            if (subTimer2 != null) {
              arrayMap5 = arrayMap8;
              j11 = j27;
              j12 = subTimer2.getTotalDurationMsLocked(j11);
            } else {
              arrayMap5 = arrayMap8;
              j11 = j27;
              j12 = -1;
            }
            long j28 = j12;
            int countLocked7 = subTimer2 != null ? subTimer2.getCountLocked(i) : -1;
            if (totalTimeLocked5 != 0) {
              timer2 = multicastWakelockStats;
              str7 = str2;
              arrayMap6 = syncStats;
              sb5 = sb8;
              dumpLine(
                  printWriter,
                  i5,
                  str4,
                  SYNC_DATA,
                  str7 + syncStats.keyAt(size4) + str7,
                  Long.valueOf(totalTimeLocked5),
                  Integer.valueOf(countLocked6),
                  Long.valueOf(j28),
                  Integer.valueOf(countLocked7));
            } else {
              timer2 = multicastWakelockStats;
              arrayMap6 = syncStats;
              sb5 = sb8;
              str7 = str2;
            }
            size4--;
            j27 = j11;
            str2 = str7;
            arrayMap8 = arrayMap5;
            multicastWakelockStats = timer2;
            syncStats = arrayMap6;
            sb8 = sb5;
          }
          StringBuilder sb9 = sb8;
          long j29 = j27;
          String str13 = str2;
          ArrayMap<String, ? extends Timer> jobStats = valueAt2.getJobStats();
          int size5 = jobStats.size() - 1;
          while (size5 >= 0) {
            Timer valueAt5 = jobStats.valueAt(size5);
            long totalTimeLocked6 = (valueAt5.getTotalTimeLocked(j4, i) + 500) / 1000;
            int countLocked8 = valueAt5.getCountLocked(i);
            Timer subTimer3 = valueAt5.getSubTimer();
            long totalDurationMsLocked5 =
                subTimer3 != null ? subTimer3.getTotalDurationMsLocked(j29) : -1L;
            int countLocked9 = subTimer3 != null ? subTimer3.getCountLocked(i) : -1;
            if (totalTimeLocked6 != 0) {
              j10 = j4;
              dumpLine(
                  printWriter,
                  i5,
                  str4,
                  JOB_DATA,
                  str13 + jobStats.keyAt(size5) + str13,
                  Long.valueOf(totalTimeLocked6),
                  Integer.valueOf(countLocked8),
                  Long.valueOf(totalDurationMsLocked5),
                  Integer.valueOf(countLocked9));
            } else {
              j10 = j4;
            }
            size5--;
            j4 = j10;
          }
          long j30 = j4;
          int[] jobStopReasonCodes = JobParameters.getJobStopReasonCodes();
          Object[] objArr9 = new Object[jobStopReasonCodes.length + 1];
          ArrayMap<String, SparseIntArray> jobCompletionStats = valueAt2.getJobCompletionStats();
          int size6 = jobCompletionStats.size() - 1;
          while (size6 >= 0) {
            SparseIntArray valueAt6 = jobCompletionStats.valueAt(size6);
            if (valueAt6 == null) {
              arrayMap4 = jobCompletionStats;
              j9 = j29;
            } else {
              objArr9[0] = str13 + jobCompletionStats.keyAt(size6) + str13;
              int i33 = 0;
              while (i33 < jobStopReasonCodes.length) {
                objArr9[i33 + 1] = Integer.valueOf(valueAt6.get(jobStopReasonCodes[i33], 0));
                i33++;
                jobCompletionStats = jobCompletionStats;
                j29 = j29;
              }
              arrayMap4 = jobCompletionStats;
              j9 = j29;
              dumpLine(printWriter, i5, str4, JOB_COMPLETION_DATA, objArr9);
            }
            size6--;
            jobCompletionStats = arrayMap4;
            j29 = j9;
          }
          long j31 = j29;
          valueAt2.getDeferredJobsCheckinLineLocked(sb9, i);
          if (sb9.length() > 0) {
            dumpLine(printWriter, i5, str4, JOBS_DEFERRED_DATA, sb9.toString());
          }
          int i34 = i5;
          long j32 = j30;
          String str14 = str13;
          j5 = j31;
          num = num2;
          map = map2;
          dumpTimer(
              printWriter,
              i34,
              str4,
              FLASHLIGHT_DATA,
              valueAt2.getFlashlightTurnedOnTimer(),
              j32,
              i);
          dumpTimer(printWriter, i34, str4, CAMERA_DATA, valueAt2.getCameraTurnedOnTimer(), j32, i);
          dumpTimer(printWriter, i34, str4, "vid", valueAt2.getVideoTurnedOnTimer(), j32, i);
          dumpTimer(printWriter, i34, str4, AUDIO_DATA, valueAt2.getAudioTurnedOnTimer(), j32, i);
          SparseArray<? extends Uid.Sensor> sensorStats = valueAt2.getSensorStats();
          int size7 = sensorStats.size();
          int i35 = 0;
          while (i35 < size7) {
            Uid.Sensor valueAt7 = sensorStats.valueAt(i35);
            int keyAt3 = sensorStats.keyAt(i35);
            Timer sensorTime = valueAt7.getSensorTime();
            if (sensorTime != null) {
              i6 = size7;
              long j33 = j32;
              long totalTimeLocked7 = (sensorTime.getTotalTimeLocked(j33, i) + 500) / 1000;
              if (totalTimeLocked7 != 0) {
                int countLocked10 = sensorTime.getCountLocked(i);
                j8 = j33;
                Timer sensorBackgroundTime = valueAt7.getSensorBackgroundTime();
                dumpLine(
                    printWriter,
                    i5,
                    str4,
                    SENSOR_DATA,
                    Integer.valueOf(keyAt3),
                    Long.valueOf(totalTimeLocked7),
                    Integer.valueOf(countLocked10),
                    Integer.valueOf(
                        sensorBackgroundTime != null ? sensorBackgroundTime.getCountLocked(i) : 0),
                    Long.valueOf(sensorTime.getTotalDurationMsLocked(j5)),
                    Long.valueOf(
                        sensorBackgroundTime != null
                            ? sensorBackgroundTime.getTotalDurationMsLocked(j5)
                            : 0L));
              } else {
                j8 = j33;
              }
            } else {
              i6 = size7;
              j8 = j32;
            }
            i35++;
            size7 = i6;
            j32 = j8;
          }
          int i36 = i5;
          long j34 = j32;
          dumpTimer(printWriter, i36, str4, VIBRATOR_DATA, valueAt2.getVibratorOnTimer(), j34, i);
          dumpTimer(
              printWriter,
              i36,
              str4,
              FOREGROUND_ACTIVITY_DATA,
              valueAt2.getForegroundActivityTimer(),
              j34,
              i);
          dumpTimer(
              printWriter,
              i36,
              str4,
              FOREGROUND_SERVICE_DATA,
              valueAt2.getForegroundServiceTimer(),
              j34,
              i);
          Object[] objArr10 = new Object[7];
          long j35 = 0;
          int i37 = 0;
          for (int i38 = 7; i37 < i38; i38 = 7) {
            long j36 = j32;
            long processStateTime = valueAt2.getProcessStateTime(i37, j36, i);
            j35 += processStateTime;
            objArr10[i37] = Long.valueOf((processStateTime + 500) / 1000);
            i37++;
            j32 = j36;
          }
          long j37 = j32;
          if (j35 > 0) {
            dumpLine(printWriter, i5, str4, "st", objArr10);
          }
          long userCpuTimeUs = valueAt2.getUserCpuTimeUs(i);
          long systemCpuTimeUs = valueAt2.getSystemCpuTimeUs(i);
          if (userCpuTimeUs > 0 || systemCpuTimeUs > 0) {
            dumpLine(
                printWriter,
                i5,
                str4,
                CPU_DATA,
                Long.valueOf(userCpuTimeUs / 1000),
                Long.valueOf(systemCpuTimeUs / 1000),
                num);
          }
          if (jArr == null) {
            j6 = j37;
            jArr2 = jArr;
            sb2 = sb9;
          } else {
            long[] cpuFreqTimes = valueAt2.getCpuFreqTimes(i);
            if (cpuFreqTimes != null) {
              long[] jArr5 = jArr;
              if (cpuFreqTimes.length != jArr5.length) {
                j6 = j37;
                jArr3 = jArr5;
                sb2 = sb9;
              } else {
                sb2 = sb9;
                sb2.setLength(0);
                int i39 = 0;
                while (true) {
                  long j38 = j35;
                  if (i39 >= cpuFreqTimes.length) {
                    break;
                  }
                  if (i39 != 0) {
                    sb2.append(',');
                  }
                  sb2.append(cpuFreqTimes[i39]);
                  i39++;
                  j35 = j38;
                }
                long[] screenOffCpuFreqTimes = valueAt2.getScreenOffCpuFreqTimes(i);
                if (screenOffCpuFreqTimes != null) {
                  int i40 = 0;
                  while (i40 < screenOffCpuFreqTimes.length) {
                    sb2.append(',').append(screenOffCpuFreqTimes[i40]);
                    i40++;
                    jArr5 = jArr5;
                    j37 = j37;
                  }
                  j6 = j37;
                  jArr3 = jArr5;
                } else {
                  j6 = j37;
                  jArr3 = jArr5;
                  for (int i41 = 0; i41 < cpuFreqTimes.length; i41++) {
                    sb2.append(",0");
                  }
                }
                dumpLine(
                    printWriter,
                    i5,
                    str4,
                    CPU_TIMES_AT_FREQ_DATA,
                    "A",
                    Integer.valueOf(cpuFreqTimes.length),
                    sb2.toString());
              }
            } else {
              j6 = j37;
              jArr3 = jArr;
              sb2 = sb9;
            }
            long[] jArr6 = new long[getCpuFreqCount()];
            int i42 = 0;
            while (i42 < 7) {
              if (!valueAt2.getCpuFreqTimes(jArr6, i42)) {
                jArr4 = jArr3;
              } else {
                sb2.setLength(0);
                int i43 = 0;
                while (i43 < jArr6.length) {
                  if (i43 != 0) {
                    sb2.append(',');
                  }
                  sb2.append(jArr6[i43]);
                  i43++;
                  jArr3 = jArr3;
                }
                jArr4 = jArr3;
                if (valueAt2.getScreenOffCpuFreqTimes(jArr6, i42)) {
                  for (long j39 : jArr6) {
                    sb2.append(',').append(j39);
                  }
                } else {
                  for (int i44 = 0; i44 < jArr6.length; i44++) {
                    sb2.append(",0");
                  }
                }
                dumpLine(
                    printWriter,
                    i5,
                    str4,
                    CPU_TIMES_AT_FREQ_DATA,
                    Uid.UID_PROCESS_TYPES[i42],
                    Integer.valueOf(jArr6.length),
                    sb2.toString());
              }
              i42++;
              jArr3 = jArr4;
            }
            jArr2 = jArr3;
          }
          ArrayMap<String, ? extends Uid.Proc> processStats = valueAt2.getProcessStats();
          int size8 = processStats.size() - 1;
          while (size8 >= 0) {
            Uid.Proc valueAt8 = processStats.valueAt(size8);
            long userTime = valueAt8.getUserTime(i);
            long systemTime = valueAt8.getSystemTime(i);
            long foregroundTime = valueAt8.getForegroundTime(i);
            int starts = valueAt8.getStarts(i);
            int numCrashes = valueAt8.getNumCrashes(i);
            int numAnrs = valueAt8.getNumAnrs(i);
            if (userTime == 0
                && systemTime == 0
                && foregroundTime == 0
                && starts == 0
                && numAnrs == 0
                && numCrashes == 0) {
              arrayMap3 = processStats;
              sb4 = sb2;
              str6 = str14;
            } else {
              sb4 = sb2;
              str6 = str14;
              arrayMap3 = processStats;
              dumpLine(
                  printWriter,
                  i5,
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
            str14 = str6;
            sb2 = sb4;
            processStats = arrayMap3;
          }
          sb3 = sb2;
          String str15 = str14;
          ArrayMap<String, ? extends Uid.Pkg> packageStats = valueAt2.getPackageStats();
          int size9 = packageStats.size() - 1;
          while (size9 >= 0) {
            Uid.Pkg valueAt9 = packageStats.valueAt(size9);
            int i45 = 0;
            ArrayMap<String, ? extends Counter> wakeupAlarmStats = valueAt9.getWakeupAlarmStats();
            int size10 = wakeupAlarmStats.size() - 1;
            while (size10 >= 0) {
              int countLocked11 = wakeupAlarmStats.valueAt(size10).getCountLocked(i);
              i45 += countLocked11;
              dumpLine(
                  printWriter,
                  i5,
                  str4,
                  WAKEUP_ALARM_DATA,
                  wakeupAlarmStats.keyAt(size10).replace(',', '_'),
                  Integer.valueOf(countLocked11));
              size10--;
              wakeupAlarmStats = wakeupAlarmStats;
              str15 = str15;
              valueAt2 = valueAt2;
            }
            Uid uid = valueAt2;
            String str16 = str15;
            ArrayMap<String, ? extends Uid.Pkg.Serv> serviceStats = valueAt9.getServiceStats();
            int size11 = serviceStats.size() - 1;
            while (size11 >= 0) {
              Uid.Pkg.Serv valueAt10 = serviceStats.valueAt(size11);
              long j40 = j19;
              long startTime = valueAt10.getStartTime(j40, i);
              int starts2 = valueAt10.getStarts(i);
              int launches = valueAt10.getLaunches(i);
              if (startTime == 0 && starts2 == 0 && launches == 0) {
                arrayMap = serviceStats;
                arrayMap2 = packageStats;
              } else {
                arrayMap = serviceStats;
                arrayMap2 = packageStats;
                dumpLine(
                    printWriter,
                    i5,
                    str4,
                    APK_DATA,
                    Integer.valueOf(i45),
                    packageStats.keyAt(size9),
                    serviceStats.keyAt(size11),
                    Long.valueOf(startTime / 1000),
                    Integer.valueOf(starts2),
                    Integer.valueOf(launches));
              }
              size11--;
              j19 = j40;
              serviceStats = arrayMap;
              packageStats = arrayMap2;
            }
            size9--;
            str15 = str16;
            valueAt2 = uid;
          }
          str5 = str15;
          j7 = j19;
        }
        i29 = i3 + 1;
        j19 = j7;
        j20 = j5;
        num2 = num;
        screenOffRpmStats = map;
        proportionalAttributionCalculator2 = proportionalAttributionCalculator;
        i11 = i4;
        uidBatteryConsumers = list;
        aggregateBatteryConsumer3 = aggregateBatteryConsumer;
        batteryUsageStats2 = batteryUsageStats;
        j15 = j2;
        str = str5;
        j22 = j6;
        sb7 = sb3;
        i8 = i2;
        str8 = str4;
        cpuFreqs = jArr2;
      } else {
        return;
      }
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

  /* JADX WARN: Removed duplicated region for block: B:448:0x1ac1  */
  /* JADX WARN: Removed duplicated region for block: B:456:0x1b4e  */
  /* JADX WARN: Removed duplicated region for block: B:506:0x1ccc  */
  /* JADX WARN: Removed duplicated region for block: B:512:0x1d8c  */
  /* JADX WARN: Removed duplicated region for block: B:531:0x1dfc  */
  /* JADX WARN: Removed duplicated region for block: B:541:0x1f04  */
  /* JADX WARN: Removed duplicated region for block: B:560:0x1ffa  */
  /* JADX WARN: Removed duplicated region for block: B:565:0x2051  */
  /* JADX WARN: Removed duplicated region for block: B:587:0x212a  */
  /* JADX WARN: Removed duplicated region for block: B:609:0x21d8  */
  /* JADX WARN: Removed duplicated region for block: B:624:0x2244  */
  /* JADX WARN: Removed duplicated region for block: B:627:0x22dc  */
  /* JADX WARN: Removed duplicated region for block: B:667:0x2448  */
  /* JADX WARN: Removed duplicated region for block: B:670:0x247b  */
  /* JADX WARN: Removed duplicated region for block: B:687:0x24e8  */
  /* JADX WARN: Removed duplicated region for block: B:714:0x25aa  */
  /* JADX WARN: Removed duplicated region for block: B:722:0x2603  */
  /* JADX WARN: Removed duplicated region for block: B:730:0x2634  */
  /* JADX WARN: Removed duplicated region for block: B:739:0x2669  */
  /* JADX WARN: Removed duplicated region for block: B:762:0x26ee  */
  /* JADX WARN: Removed duplicated region for block: B:820:0x288b  */
  /* JADX WARN: Removed duplicated region for block: B:845:0x2993  */
  /* JADX WARN: Removed duplicated region for block: B:847:0x299b A[SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:848:0x265d  */
  /* JADX WARN: Removed duplicated region for block: B:849:0x262c  */
  /* JADX WARN: Removed duplicated region for block: B:851:0x24dc  */
  /* JADX WARN: Removed duplicated region for block: B:853:0x203d  */
  /* JADX WARN: Removed duplicated region for block: B:885:0x1feb  */
  /* JADX WARN: Removed duplicated region for block: B:886:0x1dd5  */
  /* JADX WARN: Removed duplicated region for block: B:902:0x1ce0  */
  /* JADX WARN: Removed duplicated region for block: B:921:0x1d71  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void dumpLocked(
      Context context, PrintWriter pw, String prefix, int which, int reqUid, boolean wifiOnly) {
    long subScreenDozeTime;
    long subScreenDozeTime2;
    long subScreenDozeTime3;
    long whichBatteryRealtime;
    String str;
    long dischargeScreenOnCount;
    long interactiveTime;
    long subScreenOnTime;
    String str2;
    long whichBatteryRealtime2;
    String str3;
    String str4;
    String str5;
    PrintWriter printWriter;
    String str6;
    String str7;
    String str8;
    long whichBatteryRealtime3;
    String str9;
    long powerSaveModeEnabledTime;
    long powerSaveModeEnabledTime2;
    String str10;
    String str11;
    long rawRealtime;
    long deviceIdlingTime;
    long deviceIdlingTime2;
    long phoneOnTime;
    int NU;
    ArrayList<TimerEntry> timers;
    long mact5GTimeMs;
    long mactTimeMs;
    String str12;
    long mactTimeMs2;
    int numDisplays;
    int multicastWakeLockCountTotal;
    String str13;
    String str14;
    long mobileRxTotalPackets;
    String str15;
    String str16;
    long wifiRxTotalPackets;
    String str17;
    String str18;
    BatteryUsageStats stats;
    String str19;
    String str20;
    long whichBatteryRealtime4;
    Comparator<TimerEntry> timerComparator;
    long whichBatteryRealtime5;
    String str21;
    String str22;
    String str23;
    long rawRealtime2;
    String str24;
    int i;
    String str25;
    int i2;
    int i3;
    StringBuilder sb;
    String str26;
    String str27;
    int i4;
    int numDisplays2;
    String str28;
    long whichBatteryRealtime6;
    String str29;
    String str30;
    String str31;
    long mobileTxTotalPackets;
    PrintWriter printWriter2;
    long wifiWakeup;
    PrintWriter printWriter3;
    long wifiRxPackets;
    BatteryStats batteryStats;
    StringBuilder sb2;
    long rawRealtime3;
    long rawRealtime4;
    long mobileRxBytes;
    Uid u;
    String str32;
    long mobileActiveTime;
    long uidMobileActiveTime;
    String str33;
    int i5;
    long wifiRxPackets2;
    long wifiRxBytes;
    long wifiTxBytes;
    long wifiRxPackets3;
    long wifiRxBytes2;
    long wifiTxPackets;
    long whichBatteryRealtime7;
    long wifiTxBytes2;
    long rawRealtime5;
    long wifiTxPackets2;
    long wifiScanActualTimeBg;
    int wifiScanCountBg;
    PrintWriter printWriter4;
    PrintWriter printWriter5;
    long whichBatteryRealtime8;
    long btRxBytes;
    long btTxBytes;
    Timer bleTimer;
    String str34;
    Timer bleTimer2;
    String str35;
    String str36;
    long rawRealtimeMs;
    long rawRealtimeMs2;
    PrintWriter printWriter6;
    int i6;
    Uid u2;
    String str37;
    int countWakelock;
    int iw;
    String str38;
    long totalFullWakelock;
    Uid u3;
    PrintWriter printWriter7;
    Timer mcTimer;
    String str39;
    long rawRealtime6;
    int isy;
    int ij;
    int ic;
    Uid u4;
    long mobileActiveTime2;
    String str40;
    SparseArray<? extends Uid> uidStats;
    int NU2;
    int iu;
    long[] cpuFreqs;
    int NSE;
    int ise;
    long rawRealtimeMs3;
    PrintWriter printWriter8;
    long displayTime;
    boolean uidActivity;
    String str41;
    long totalStateTime;
    int ips;
    long rawRealtime7;
    long userCpuTimeUs;
    long systemCpuTimeUs;
    long[] cpuFreqTimes;
    long[] screenOffCpuFreqTimes;
    int procState;
    int ipr;
    String str42;
    PrintWriter printWriter9;
    int ipkg;
    int i7;
    long batteryUptime;
    String str43;
    long batteryUptime2;
    int numExcessive;
    String str44;
    PrintWriter printWriter10;
    String str45;
    Uid.Proc ps;
    int numAnrs;
    String str46;
    long systemTime;
    int numExcessive2;
    int numDisplays3;
    long rawRealtime8;
    int numDisplays4;
    String str47;
    long totalStateTime2;
    long rawRealtime9;
    long time;
    String str48;
    boolean uidActivity2;
    String str49;
    long displayTime2;
    long rawRealtimeMs4;
    int NSE2;
    String str50;
    SparseArray<? extends Uid.Sensor> sensors;
    String str51;
    String str52;
    String str53;
    long rawRealtime10;
    long j;
    int bgCount;
    String str54;
    String str55;
    String str56;
    long rawRealtime11;
    long rawRealtime12;
    long rawRealtimeMs5;
    ArrayMap<String, ? extends Timer> syncs;
    String str57;
    Timer mcTimer2;
    int countWakelock2;
    long rawRealtimeMs6;
    long actualBgPartialWakelock;
    long j2;
    String str58;
    long j3;
    long j4;
    int resultCount;
    int resultCountBg;
    long j5;
    long j6;
    long j7;
    long j8;
    int countBg;
    int count;
    long unoptimizedScanTotalTimeBg;
    String str59;
    String str60;
    String str61;
    String str62;
    long rawRealtime13;
    long wifiTxTotalBytes;
    String str63;
    long wifiTxTotalPackets;
    long wifiTxTotalPackets2;
    String str64;
    int numCellularRxBins;
    String str65;
    String str66;
    long mobileTxTotalBytes;
    String str67;
    long mobileRxTotalBytes;
    long mobileRxTotalBytes2;
    String str68;
    int multicastWakeLockCountTotal2;
    String str69;
    String str70;
    long whichBatteryRealtime9;
    long whichBatteryRealtime10;
    String str71;
    String str72;
    String str73;
    String str74;
    String str75;
    String str76;
    String str77;
    String str78 = prefix;
    if (which != 0) {
      pw.println(
          "ERROR: BatteryStats.dump called for which type "
              + which
              + " but only STATS_SINCE_CHARGED is supported");
      return;
    }
    long rawUptime = SystemClock.uptimeMillis() * 1000;
    long rawRealtime14 = SystemClock.elapsedRealtime() * 1000;
    long rawRealtimeMs7 = (rawRealtime14 + 500) / 1000;
    long batteryUptime3 = getBatteryUptime(rawUptime);
    long whichBatteryUptime = computeBatteryUptime(rawUptime, which);
    long whichBatteryRealtime11 = computeBatteryRealtime(rawRealtime14, which);
    long totalRealtime = computeRealtime(rawRealtime14, which);
    long totalUptime = computeUptime(rawUptime, which);
    long whichBatteryScreenOffUptime = computeBatteryScreenOffUptime(rawUptime, which);
    long whichBatteryScreenOffRealtime = computeBatteryScreenOffRealtime(rawRealtime14, which);
    long batteryTimeRemaining = computeBatteryTimeRemaining(rawRealtime14);
    long chargeTimeRemaining = computeChargeTimeRemaining(rawRealtime14);
    long screenDozeTime = getScreenDozeTime(rawRealtime14, which);
    long subScreenDozeTime4 = getSubScreenDozeTime(rawRealtime14, which);
    StringBuilder sb3 = new StringBuilder(128);
    SparseArray<? extends Uid> uidStats2 = getUidStats();
    int NU3 = uidStats2.size();
    int estimatedBatteryCapacity = getEstimatedBatteryCapacity();
    SparseArray<? extends Uid> uidStats3 = uidStats2;
    int NU4 = NU3;
    if (estimatedBatteryCapacity <= 0) {
      subScreenDozeTime = subScreenDozeTime4;
    } else {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Estimated battery capacity: ");
      subScreenDozeTime = subScreenDozeTime4;
      sb3.append(formatCharge(estimatedBatteryCapacity));
      sb3.append(" mAh");
      pw.println(sb3.toString());
    }
    int lastLearnedBatteryCapacity = getLearnedBatteryCapacity();
    if (lastLearnedBatteryCapacity > 0) {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Last learned battery capacity: ");
      sb3.append(formatCharge(lastLearnedBatteryCapacity / 1000));
      sb3.append(" mAh");
      pw.println(sb3.toString());
    }
    int minLearnedBatteryCapacity = getMinLearnedBatteryCapacity();
    if (minLearnedBatteryCapacity > 0) {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Min learned battery capacity: ");
      sb3.append(formatCharge(minLearnedBatteryCapacity / 1000));
      sb3.append(" mAh");
      pw.println(sb3.toString());
    }
    int maxLearnedBatteryCapacity = getMaxLearnedBatteryCapacity();
    if (maxLearnedBatteryCapacity > 0) {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Max learned battery capacity: ");
      sb3.append(formatCharge(maxLearnedBatteryCapacity / 1000));
      sb3.append(" mAh");
      pw.println(sb3.toString());
    }
    sb3.setLength(0);
    sb3.append(str78);
    sb3.append("  Time on battery: ");
    formatTimeMs(sb3, whichBatteryRealtime11 / 1000);
    sb3.append(NavigationBarInflaterView.KEY_CODE_START);
    sb3.append(formatRatioLocked(whichBatteryRealtime11, totalRealtime));
    sb3.append(") realtime, ");
    formatTimeMs(sb3, whichBatteryUptime / 1000);
    sb3.append(NavigationBarInflaterView.KEY_CODE_START);
    sb3.append(formatRatioLocked(whichBatteryUptime, whichBatteryRealtime11));
    sb3.append(") uptime");
    pw.println(sb3.toString());
    sb3.setLength(0);
    sb3.append(str78);
    sb3.append("  Time on battery screen off: ");
    formatTimeMs(sb3, whichBatteryScreenOffRealtime / 1000);
    sb3.append(NavigationBarInflaterView.KEY_CODE_START);
    sb3.append(formatRatioLocked(whichBatteryScreenOffRealtime, whichBatteryRealtime11));
    sb3.append(") realtime, ");
    formatTimeMs(sb3, whichBatteryScreenOffUptime / 1000);
    sb3.append(NavigationBarInflaterView.KEY_CODE_START);
    sb3.append(formatRatioLocked(whichBatteryScreenOffUptime, whichBatteryRealtime11));
    sb3.append(") uptime");
    pw.println(sb3.toString());
    sb3.setLength(0);
    sb3.append(str78);
    sb3.append("  Time on battery screen doze: ");
    formatTimeMs(sb3, screenDozeTime / 1000);
    sb3.append(NavigationBarInflaterView.KEY_CODE_START);
    sb3.append(formatRatioLocked(screenDozeTime, whichBatteryRealtime11));
    sb3.append(NavigationBarInflaterView.KEY_CODE_END);
    pw.println(sb3.toString());
    if (subScreenDozeTime <= 0) {
      subScreenDozeTime2 = subScreenDozeTime;
    } else {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Time on battery sub screen doze: ");
      formatTimeMs(sb3, subScreenDozeTime / 1000);
      sb3.append(NavigationBarInflaterView.KEY_CODE_START);
      subScreenDozeTime2 = subScreenDozeTime;
      sb3.append(formatRatioLocked(subScreenDozeTime2, whichBatteryRealtime11));
      sb3.append(NavigationBarInflaterView.KEY_CODE_END);
      pw.println(sb3.toString());
    }
    sb3.setLength(0);
    sb3.append(str78);
    sb3.append("  Total run time: ");
    formatTimeMs(sb3, totalRealtime / 1000);
    sb3.append("realtime, ");
    formatTimeMs(sb3, totalUptime / 1000);
    sb3.append("uptime");
    pw.println(sb3.toString());
    if (batteryTimeRemaining >= 0) {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Battery time remaining: ");
      formatTimeMs(sb3, batteryTimeRemaining / 1000);
      pw.println(sb3.toString());
    }
    if (chargeTimeRemaining >= 0) {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Charge time remaining: ");
      formatTimeMs(sb3, chargeTimeRemaining / 1000);
      pw.println(sb3.toString());
    }
    long dischargeCount = getUahDischarge(which);
    if (dischargeCount < 0) {
      subScreenDozeTime3 = subScreenDozeTime2;
    } else {
      subScreenDozeTime3 = subScreenDozeTime2;
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Discharge: ");
      sb3.append(formatCharge(dischargeCount / 1000.0d));
      sb3.append(" mAh");
      pw.println(sb3.toString());
    }
    long dischargeScreenOffCount = getUahDischargeScreenOff(which);
    if (dischargeScreenOffCount >= 0) {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Screen off discharge: ");
      sb3.append(formatCharge(dischargeScreenOffCount / 1000.0d));
      sb3.append(" mAh");
      pw.println(sb3.toString());
    }
    long dischargeScreenDozeCount = getUahDischargeScreenDoze(which);
    if (dischargeScreenDozeCount < 0) {
      whichBatteryRealtime = whichBatteryRealtime11;
      str = NavigationBarInflaterView.KEY_CODE_END;
    } else {
      str = NavigationBarInflaterView.KEY_CODE_END;
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Screen doze discharge: ");
      whichBatteryRealtime = whichBatteryRealtime11;
      sb3.append(formatCharge(dischargeScreenDozeCount / 1000.0d));
      sb3.append(" mAh");
      pw.println(sb3.toString());
    }
    long dischargeScreenOnCount2 = dischargeCount - dischargeScreenOffCount;
    if (dischargeScreenOnCount2 >= 0) {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Screen on discharge: ");
      sb3.append(formatCharge(dischargeScreenOnCount2 / 1000.0d));
      sb3.append(" mAh");
      pw.println(sb3.toString());
    }
    long dischargeLightDozeCount = getUahDischargeLightDoze(which);
    if (dischargeLightDozeCount < 0) {
      dischargeScreenOnCount = dischargeScreenOnCount2;
    } else {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Device light doze discharge: ");
      dischargeScreenOnCount = dischargeScreenOnCount2;
      sb3.append(formatCharge(dischargeLightDozeCount / 1000.0d));
      sb3.append(" mAh");
      pw.println(sb3.toString());
    }
    long dischargeDeepDozeCount = getUahDischargeDeepDoze(which);
    if (dischargeDeepDozeCount >= 0) {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Device deep doze discharge: ");
      sb3.append(formatCharge(dischargeDeepDozeCount / 1000.0d));
      sb3.append(" mAh");
      pw.println(sb3.toString());
    }
    pw.print("  Start clock time: ");
    pw.println(DateFormat.format("yyyy-MM-dd-HH-mm-ss", getStartClockTime()).toString());
    long screenOnTime = getScreenOnTime(rawRealtime14, which);
    long subScreenOnTime2 = getSubScreenOnTime(rawRealtime14, which);
    long screenHighBrightnessTime = getScreenHighBrightnessTime(rawRealtime14, which);
    long subScreenHighBrightnessTime = getSubScreenHighBrightnessTime(rawRealtime14, which);
    long interactiveTime2 = getInteractiveTime(rawRealtime14, which);
    long powerSaveModeEnabledTime3 = getPowerSaveModeEnabledTime(rawRealtime14, which);
    long deviceIdleModeLightTime = getDeviceIdleModeTime(1, rawRealtime14, which);
    long deviceIdleModeFullTime = getDeviceIdleModeTime(2, rawRealtime14, which);
    long deviceLightIdlingTime = getDeviceIdlingTime(1, rawRealtime14, which);
    long deviceIdlingTime3 = getDeviceIdlingTime(2, rawRealtime14, which);
    long phoneOnTime2 = getPhoneOnTime(rawRealtime14, which);
    getGlobalWifiRunningTime(rawRealtime14, which);
    getWifiOnTime(rawRealtime14, which);
    sb3.setLength(0);
    sb3.append(str78);
    sb3.append("  Screen on: ");
    formatTimeMs(sb3, screenOnTime / 1000);
    sb3.append(NavigationBarInflaterView.KEY_CODE_START);
    long whichBatteryRealtime12 = whichBatteryRealtime;
    sb3.append(formatRatioLocked(screenOnTime, whichBatteryRealtime12));
    String str79 = ") ";
    sb3.append(") ");
    sb3.append(getScreenOnCount(which));
    sb3.append("x, Interactive: ");
    long screenOnTime2 = screenOnTime;
    formatTimeMs(sb3, interactiveTime2 / 1000);
    sb3.append(NavigationBarInflaterView.KEY_CODE_START);
    sb3.append(formatRatioLocked(interactiveTime2, whichBatteryRealtime12));
    String str80 = str;
    sb3.append(str80);
    pw.println(sb3.toString());
    if (subScreenOnTime2 <= 0) {
      interactiveTime = interactiveTime2;
      subScreenOnTime = subScreenOnTime2;
    } else {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Sub screen on: ");
      interactiveTime = interactiveTime2;
      formatTimeMs(sb3, subScreenOnTime2 / 1000);
      sb3.append(NavigationBarInflaterView.KEY_CODE_START);
      subScreenOnTime = subScreenOnTime2;
      sb3.append(formatRatioLocked(subScreenOnTime, whichBatteryRealtime12));
      sb3.append(") ");
      sb3.append(getSubScreenOnCount(which));
      sb3.append("x");
      pw.println(sb3.toString());
    }
    sb3.setLength(0);
    sb3.append(str78);
    sb3.append("  Screen brightnesses:");
    boolean didOne = false;
    int i8 = 0;
    while (true) {
      str2 = str79;
      whichBatteryRealtime2 = whichBatteryRealtime12;
      str3 = "\n    ";
      str4 = " ";
      if (i8 >= 5) {
        break;
      }
      long subScreenOnTime3 = subScreenOnTime;
      long time2 = getScreenBrightnessTime(i8, rawRealtime14, which);
      long autoTime = getScreenAutoBrightnessTime(i8, rawRealtime14, which);
      if (time2 != 0) {
        sb3.append("\n    ");
        sb3.append(str78);
        didOne = true;
        sb3.append(SCREEN_BRIGHTNESS_NAMES[i8]);
        sb3.append(" ");
        formatTimeMs(sb3, time2 / 1000);
        sb3.append(NavigationBarInflaterView.KEY_CODE_START);
        long screenOnTime3 = screenOnTime2;
        sb3.append(formatRatioLocked(time2, screenOnTime3));
        sb3.append(str80);
        if (autoTime <= 0) {
          screenOnTime2 = screenOnTime3;
        } else {
          sb3.append(" -- auto ");
          screenOnTime2 = screenOnTime3;
          formatTimeMs(sb3, autoTime / 1000);
        }
      }
      i8++;
      str79 = str2;
      whichBatteryRealtime12 = whichBatteryRealtime2;
      subScreenOnTime = subScreenOnTime3;
    }
    long mobileRxTotalBytes3 = subScreenOnTime;
    long screenOnTime4 = screenOnTime2;
    String str81 = " (no activity)";
    if (!didOne) {
      sb3.append(" (no activity)");
    }
    pw.println(sb3.toString());
    if (mobileRxTotalBytes3 <= 0) {
      str5 = " ";
      printWriter = pw;
      str6 = "\n    ";
    } else {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Sub screen brightnesses:");
      boolean didOne2 = false;
      int i9 = 0;
      while (i9 < 5) {
        boolean didOne3 = didOne2;
        String str82 = str81;
        long time3 = getSubScreenBrightnessTime(i9, rawRealtime14, which);
        long autoTime2 = getSubScreenAutoBrightnessTime(i9, rawRealtime14, which);
        if (time3 == 0) {
          str77 = str4;
          str75 = str82;
          didOne2 = didOne3;
          str76 = str3;
        } else {
          sb3.append(str3);
          sb3.append(str78);
          str75 = str82;
          sb3.append(SCREEN_BRIGHTNESS_NAMES[i9]);
          sb3.append(str4);
          str76 = str3;
          str77 = str4;
          formatTimeMs(sb3, time3 / 1000);
          sb3.append(NavigationBarInflaterView.KEY_CODE_START);
          sb3.append(formatRatioLocked(time3, screenOnTime4));
          sb3.append(str80);
          if (autoTime2 > 0) {
            sb3.append(" -- auto ");
            formatTimeMs(sb3, autoTime2 / 1000);
          }
          didOne2 = true;
        }
        i9++;
        str3 = str76;
        str4 = str77;
        str81 = str75;
      }
      String str83 = str81;
      str6 = str3;
      str5 = str4;
      if (didOne2) {
        str81 = str83;
      } else {
        str81 = str83;
        sb3.append(str81);
      }
      printWriter = pw;
      printWriter.println(sb3.toString());
    }
    sb3.setLength(0);
    sb3.append(str78);
    sb3.append("  Screen refresh rate:");
    boolean didOne4 = false;
    int i10 = 0;
    while (true) {
      long screenOnTime5 = screenOnTime4;
      if (i10 >= 4) {
        break;
      }
      long hrrTime = getDisplayHighRefreshRateTime(i10, rawRealtime14, which);
      if (hrrTime == 0) {
        str72 = str6;
        str73 = str5;
        str74 = str81;
      } else {
        str72 = str6;
        sb3.append(str72);
        sb3.append(str78);
        sb3.append(Settings.Secure.refreshRateModeToString(i10).replace("REFRESH_RATE_MODE_", ""));
        str73 = str5;
        sb3.append(str73);
        str74 = str81;
        formatTimeMs(sb3, hrrTime / 1000);
        didOne4 = true;
      }
      i10++;
      str5 = str73;
      str81 = str74;
      screenOnTime4 = screenOnTime5;
      str6 = str72;
    }
    String str84 = str6;
    String str85 = str5;
    String str86 = str81;
    if (!didOne4) {
      sb3.append(" (disabled)");
    }
    printWriter.println(sb3.toString());
    if (mobileRxTotalBytes3 <= 0) {
      str7 = str84;
    } else {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Sub screen refresh rate:");
      didOne4 = false;
      int i11 = 0;
      while (i11 < 4) {
        long hrrTime2 = getSubDisplayHighRefreshRateTime(i11, rawRealtime14, which);
        if (hrrTime2 == 0) {
          str71 = str84;
        } else {
          sb3.append(str84);
          sb3.append(str78);
          sb3.append(
              Settings.Secure.refreshRateModeToString(i11).replace("REFRESH_RATE_MODE_", ""));
          sb3.append(str85);
          str71 = str84;
          formatTimeMs(sb3, hrrTime2 / 1000);
          didOne4 = true;
        }
        i11++;
        str84 = str71;
      }
      str7 = str84;
      if (!didOne4) {
        sb3.append(" (disabled)");
      }
      printWriter.println(sb3.toString());
    }
    if (screenHighBrightnessTime > 0) {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Screen high brightness time: ");
      formatTimeMs(sb3, screenHighBrightnessTime / 1000);
      printWriter.println(sb3.toString());
    }
    if (subScreenHighBrightnessTime > 0) {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Sub screen high brightness time: ");
      formatTimeMs(sb3, subScreenHighBrightnessTime / 1000);
      printWriter.println(sb3.toString());
    }
    if (powerSaveModeEnabledTime3 == 0) {
      str8 = str85;
      whichBatteryRealtime3 = whichBatteryRealtime2;
      str9 = "";
      powerSaveModeEnabledTime = powerSaveModeEnabledTime3;
    } else {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Power save mode enabled: ");
      formatTimeMs(sb3, powerSaveModeEnabledTime3 / 1000);
      sb3.append(NavigationBarInflaterView.KEY_CODE_START);
      str8 = str85;
      whichBatteryRealtime3 = whichBatteryRealtime2;
      str9 = "";
      powerSaveModeEnabledTime = powerSaveModeEnabledTime3;
      sb3.append(formatRatioLocked(powerSaveModeEnabledTime, whichBatteryRealtime3));
      sb3.append(str80);
      printWriter.println(sb3.toString());
    }
    if (deviceLightIdlingTime != 0) {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Device light idling: ");
      powerSaveModeEnabledTime2 = powerSaveModeEnabledTime;
      formatTimeMs(sb3, deviceLightIdlingTime / 1000);
      sb3.append(NavigationBarInflaterView.KEY_CODE_START);
      sb3.append(formatRatioLocked(deviceLightIdlingTime, whichBatteryRealtime3));
      str11 = str2;
      sb3.append(str11);
      sb3.append(getDeviceIdlingCount(1, which));
      str10 = "x";
      sb3.append(str10);
      printWriter.println(sb3.toString());
    } else {
      powerSaveModeEnabledTime2 = powerSaveModeEnabledTime;
      str10 = "x";
      str11 = str2;
    }
    if (deviceIdleModeLightTime == 0) {
      rawRealtime = rawRealtime14;
    } else {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Idle mode light time: ");
      rawRealtime = rawRealtime14;
      formatTimeMs(sb3, deviceIdleModeLightTime / 1000);
      sb3.append(NavigationBarInflaterView.KEY_CODE_START);
      sb3.append(formatRatioLocked(deviceIdleModeLightTime, whichBatteryRealtime3));
      sb3.append(str11);
      sb3.append(getDeviceIdleModeCount(1, which));
      sb3.append(str10);
      sb3.append(" -- longest ");
      formatTimeMs(sb3, getLongestDeviceIdleModeTime(1));
      printWriter.println(sb3.toString());
    }
    if (deviceIdlingTime3 == 0) {
      deviceIdlingTime = deviceIdlingTime3;
    } else {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Device full idling: ");
      formatTimeMs(sb3, deviceIdlingTime3 / 1000);
      sb3.append(NavigationBarInflaterView.KEY_CODE_START);
      deviceIdlingTime = deviceIdlingTime3;
      sb3.append(formatRatioLocked(deviceIdlingTime, whichBatteryRealtime3));
      sb3.append(str11);
      sb3.append(getDeviceIdlingCount(2, which));
      sb3.append(str10);
      printWriter.println(sb3.toString());
    }
    if (deviceIdleModeFullTime == 0) {
      deviceIdlingTime2 = deviceIdlingTime;
    } else {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Idle mode full time: ");
      deviceIdlingTime2 = deviceIdlingTime;
      formatTimeMs(sb3, deviceIdleModeFullTime / 1000);
      sb3.append(NavigationBarInflaterView.KEY_CODE_START);
      sb3.append(formatRatioLocked(deviceIdleModeFullTime, whichBatteryRealtime3));
      sb3.append(str11);
      sb3.append(getDeviceIdleModeCount(2, which));
      sb3.append(str10);
      sb3.append(" -- longest ");
      formatTimeMs(sb3, getLongestDeviceIdleModeTime(2));
      printWriter.println(sb3.toString());
    }
    if (phoneOnTime2 == 0) {
      phoneOnTime = phoneOnTime2;
    } else {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Active phone call: ");
      formatTimeMs(sb3, phoneOnTime2 / 1000);
      sb3.append(NavigationBarInflaterView.KEY_CODE_START);
      phoneOnTime = phoneOnTime2;
      sb3.append(formatRatioLocked(phoneOnTime, whichBatteryRealtime3));
      sb3.append(str11);
      sb3.append(getPhoneOnCount(which));
      sb3.append(str10);
    }
    int connChanges = getNumConnectivityChange(which);
    if (connChanges != 0) {
      pw.print(prefix);
      printWriter.print("  Connectivity changes: ");
      printWriter.println(connChanges);
    }
    long fullWakeLockTimeTotalMicros = 0;
    long partialWakeLockTimeTotalMicros = 0;
    ArrayList<TimerEntry> timers2 = new ArrayList<>();
    int iu2 = 0;
    while (true) {
      NU = NU4;
      if (iu2 >= NU) {
        break;
      }
      NU4 = NU;
      SparseArray<? extends Uid> uidStats4 = uidStats3;
      Uid u5 = uidStats4.valueAt(iu2);
      ArrayMap<String, ? extends Uid.Wakelock> wakelocks = u5.getWakelockStats();
      String str87 = str10;
      int iw2 = wakelocks.size() - 1;
      while (iw2 >= 0) {
        int connChanges2 = connChanges;
        Uid.Wakelock wl = wakelocks.valueAt(iw2);
        String str88 = str80;
        String str89 = str86;
        Timer fullWakeTimer = wl.getWakeTime(1);
        if (fullWakeTimer == null) {
          whichBatteryRealtime9 = whichBatteryRealtime3;
          whichBatteryRealtime10 = rawRealtime;
        } else {
          whichBatteryRealtime9 = whichBatteryRealtime3;
          whichBatteryRealtime10 = rawRealtime;
          fullWakeLockTimeTotalMicros +=
              fullWakeTimer.getTotalTimeLocked(whichBatteryRealtime10, which);
        }
        Timer partialWakeTimer = wl.getWakeTime(0);
        if (partialWakeTimer != null) {
          long totalTimeMicros = partialWakeTimer.getTotalTimeLocked(whichBatteryRealtime10, which);
          if (totalTimeMicros > 0) {
            if (reqUid < 0) {
              timers2.add(
                  new TimerEntry(
                      wakelocks.keyAt(iw2), u5.getUid(), partialWakeTimer, totalTimeMicros));
            }
            partialWakeLockTimeTotalMicros += totalTimeMicros;
          }
        }
        iw2--;
        rawRealtime = whichBatteryRealtime10;
        str86 = str89;
        connChanges = connChanges2;
        str80 = str88;
        whichBatteryRealtime3 = whichBatteryRealtime9;
      }
      iu2++;
      uidStats3 = uidStats4;
      str10 = str87;
      whichBatteryRealtime3 = whichBatteryRealtime3;
    }
    long whichBatteryRealtime13 = whichBatteryRealtime3;
    String str90 = str10;
    int connChanges3 = connChanges;
    String str91 = str80;
    String str92 = str86;
    SparseArray<? extends Uid> uidStats5 = uidStats3;
    long whichBatteryRealtime14 = rawRealtime;
    long mobileRxTotalBytes4 = getNetworkActivityBytes(0, which);
    long mobileTxTotalBytes2 = getNetworkActivityBytes(1, which);
    long wifiRxTotalBytes = getNetworkActivityBytes(2, which);
    long wifiTxTotalBytes2 = getNetworkActivityBytes(3, which);
    long mobileRxTotalPackets2 = getNetworkActivityPackets(0, which);
    long mobileTxTotalPackets2 = getNetworkActivityPackets(1, which);
    long wifiRxTotalPackets2 = getNetworkActivityPackets(2, which);
    long wifiTxTotalPackets3 = getNetworkActivityPackets(3, which);
    long btRxTotalBytes = getNetworkActivityBytes(4, which);
    long btTxTotalBytes = getNetworkActivityBytes(5, which);
    long whichBatteryRealtime15 = getMobileActiveTime(whichBatteryRealtime14, which) / 1000;
    long mact5GTimeMs2 = getMobileActive5GTime(whichBatteryRealtime14, which) / 1000;
    if (fullWakeLockTimeTotalMicros != 0) {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Total full wakelock time: ");
      formatTimeMsNoSpace(sb3, (fullWakeLockTimeTotalMicros + 500) / 1000);
      printWriter.println(sb3.toString());
    }
    if (partialWakeLockTimeTotalMicros != 0) {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Total partial wakelock time: ");
      formatTimeMsNoSpace(sb3, (partialWakeLockTimeTotalMicros + 500) / 1000);
      printWriter.println(sb3.toString());
    }
    long multicastWakeLockTimeTotalMicros =
        getWifiMulticastWakelockTime(whichBatteryRealtime14, which);
    int multicastWakeLockCountTotal3 = getWifiMulticastWakelockCount(which);
    if (multicastWakeLockTimeTotalMicros != 0) {
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Total WiFi Multicast wakelock Count: ");
      sb3.append(multicastWakeLockCountTotal3);
      printWriter.println(sb3.toString());
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  Total WiFi Multicast wakelock time: ");
      formatTimeMsNoSpace(sb3, (multicastWakeLockTimeTotalMicros + 500) / 1000);
      printWriter.println(sb3.toString());
    }
    int numDisplays5 = getDisplayCount();
    if (numDisplays5 <= 1) {
      timers = timers2;
      mact5GTimeMs = mact5GTimeMs2;
      mactTimeMs = whichBatteryRealtime15;
      str12 = str9;
      mactTimeMs2 = whichBatteryRealtime13;
      numDisplays = numDisplays5;
      multicastWakeLockCountTotal = multicastWakeLockCountTotal3;
      str13 = str78;
      str14 = str91;
    } else {
      str12 = str9;
      printWriter.println(str12);
      pw.print(prefix);
      timers = timers2;
      sb3.setLength(0);
      sb3.append(str78);
      sb3.append("  MULTI-DISPLAY POWER SUMMARY START");
      printWriter.println(sb3.toString());
      int display = 0;
      while (display < numDisplays5) {
        int numDisplays6 = numDisplays5;
        sb3.setLength(0);
        sb3.append(str78);
        sb3.append("  Display ");
        sb3.append(display);
        sb3.append(" Statistics:");
        printWriter.println(sb3.toString());
        long mact5GTimeMs3 = mact5GTimeMs2;
        long displayScreenOnTime = getDisplayScreenOnTime(display, whichBatteryRealtime14);
        sb3.setLength(0);
        sb3.append(str78);
        sb3.append("    Screen on: ");
        long mactTimeMs3 = whichBatteryRealtime15;
        long mactTimeMs4 = displayScreenOnTime / 1000;
        formatTimeMs(sb3, mactTimeMs4);
        sb3.append(NavigationBarInflaterView.KEY_CODE_START);
        long whichBatteryRealtime16 = whichBatteryRealtime13;
        sb3.append(formatRatioLocked(displayScreenOnTime, whichBatteryRealtime16));
        sb3.append(str11);
        printWriter.println(sb3.toString());
        sb3.setLength(0);
        sb3.append("    Screen brightness levels:");
        boolean didOne5 = false;
        int bin = 0;
        while (true) {
          multicastWakeLockCountTotal2 = multicastWakeLockCountTotal3;
          if (bin >= 5) {
            break;
          }
          long whichBatteryRealtime17 = whichBatteryRealtime16;
          long timeUs = getDisplayScreenBrightnessTime(display, bin, whichBatteryRealtime14);
          if (timeUs == 0) {
            str70 = str91;
          } else {
            didOne5 = true;
            sb3.append("\n      ");
            sb3.append(str78);
            sb3.append(SCREEN_BRIGHTNESS_NAMES[bin]);
            sb3.append(str8);
            formatTimeMs(sb3, timeUs / 1000);
            sb3.append(NavigationBarInflaterView.KEY_CODE_START);
            sb3.append(formatRatioLocked(timeUs, displayScreenOnTime));
            str70 = str91;
            sb3.append(str70);
          }
          bin++;
          str91 = str70;
          multicastWakeLockCountTotal3 = multicastWakeLockCountTotal2;
          whichBatteryRealtime16 = whichBatteryRealtime17;
          str78 = prefix;
        }
        long whichBatteryRealtime18 = whichBatteryRealtime16;
        String str93 = str91;
        if (didOne5) {
          str69 = str92;
        } else {
          str69 = str92;
          sb3.append(str69);
        }
        printWriter.println(sb3.toString());
        long displayScreenDozeTimeUs = getDisplayScreenDozeTime(display, whichBatteryRealtime14);
        sb3.setLength(0);
        sb3.append(prefix);
        sb3.append("    Screen Doze: ");
        str92 = str69;
        formatTimeMs(sb3, displayScreenDozeTimeUs / 1000);
        sb3.append(NavigationBarInflaterView.KEY_CODE_START);
        sb3.append(formatRatioLocked(displayScreenDozeTimeUs, whichBatteryRealtime18));
        sb3.append(str11);
        printWriter.println(sb3.toString());
        display++;
        str91 = str93;
        str78 = prefix;
        numDisplays5 = numDisplays6;
        multicastWakeLockCountTotal3 = multicastWakeLockCountTotal2;
        mact5GTimeMs2 = mact5GTimeMs3;
        whichBatteryRealtime13 = whichBatteryRealtime18;
        whichBatteryRealtime15 = mactTimeMs3;
      }
      mact5GTimeMs = mact5GTimeMs2;
      mactTimeMs = whichBatteryRealtime15;
      numDisplays = numDisplays5;
      mactTimeMs2 = whichBatteryRealtime13;
      multicastWakeLockCountTotal = multicastWakeLockCountTotal3;
      str13 = str78;
      str14 = str91;
      pw.print(prefix);
      sb3.setLength(0);
      sb3.append(str13);
      sb3.append("  MULTI-DISPLAY POWER SUMMARY END");
      printWriter.println(sb3.toString());
    }
    printWriter.println(str12);
    pw.print(prefix);
    sb3.setLength(0);
    sb3.append(str13);
    sb3.append("  Mobile active info");
    sb3.append("\n     Mobile active time: ");
    formatTimeMs(sb3, mactTimeMs);
    sb3.append("\n     Mobile active 5G time: ");
    long mactTimeMs5 = mact5GTimeMs;
    formatTimeMs(sb3, mactTimeMs5);
    printWriter.println(sb3.toString());
    printWriter.println(str12);
    pw.print(prefix);
    sb3.setLength(0);
    sb3.append(str13);
    sb3.append("  CONNECTIVITY POWER SUMMARY START");
    printWriter.println(sb3.toString());
    pw.print(prefix);
    sb3.setLength(0);
    sb3.append(str13);
    sb3.append("  Logging duration for connectivity statistics: ");
    formatTimeMs(sb3, mactTimeMs2 / 1000);
    printWriter.println(sb3.toString());
    sb3.setLength(0);
    sb3.append(str13);
    sb3.append("  Cellular Statistics:");
    printWriter.println(sb3.toString());
    pw.print(prefix);
    sb3.setLength(0);
    sb3.append(str13);
    sb3.append("     Cellular kernel active time: ");
    long mobileActiveTime3 = getMobileRadioActiveTime(whichBatteryRealtime14, which);
    formatTimeMs(sb3, mobileActiveTime3 / 1000);
    sb3.append(NavigationBarInflaterView.KEY_CODE_START);
    sb3.append(formatRatioLocked(mobileActiveTime3, mactTimeMs2));
    sb3.append(str14);
    printWriter.println(sb3.toString());
    ControllerActivityCounter modemControllerActivity = getModemControllerActivity();
    String str94 = str14;
    long batteryUptime4 = batteryUptime3;
    String str95 = str92;
    SparseArray<? extends Uid> uidStats6 = uidStats5;
    String str96 = str12;
    long rawRealtimeMs8 = rawRealtimeMs7;
    String str97 = str8;
    String str98 = str11;
    long mobileActiveTime4 = mobileActiveTime3;
    int NU5 = NU;
    long deviceIdlingTime4 = deviceIdlingTime2;
    long interactiveTime3 = mactTimeMs;
    long whichBatteryRealtime19 = mactTimeMs2;
    long rawRealtime15 = whichBatteryRealtime14;
    String str99 = NavigationBarInflaterView.KEY_CODE_START;
    printControllerActivity(
        pw, sb3, prefix, CELLULAR_CONTROLLER_NAME, modemControllerActivity, which);
    printCellularPerRatBreakdown(pw, sb3, str13 + "     ", rawRealtimeMs8);
    printWriter.print("     Cellular data received: ");
    long whichBatteryRealtime20 = mobileRxTotalBytes4;
    printWriter.println(formatBytesLocked(whichBatteryRealtime20));
    printWriter.print("     Cellular data sent: ");
    long mobileTxTotalBytes3 = mobileTxTotalBytes2;
    printWriter.println(formatBytesLocked(mobileTxTotalBytes3));
    printWriter.print("     Cellular packets received: ");
    long mobileRxTotalPackets3 = mobileRxTotalPackets2;
    printWriter.println(mobileRxTotalPackets3);
    printWriter.print("     Cellular packets sent: ");
    printWriter.println(mobileTxTotalPackets2);
    sb3.setLength(0);
    long rawRealtime16 = mobileTxTotalPackets2;
    sb3.append(prefix);
    sb3.append("     Cellular Radio Access Technology:");
    boolean didOne6 = false;
    int i12 = 0;
    while (true) {
      mobileRxTotalPackets = mobileRxTotalPackets3;
      if (i12 >= NUM_DATA_CONNECTION_TYPES) {
        break;
      }
      long time4 = getPhoneDataConnectionTime(i12, rawRealtime15, which);
      if (time4 == 0) {
        mobileTxTotalBytes = mobileTxTotalBytes3;
        mobileRxTotalBytes = whichBatteryRealtime20;
        str67 = str99;
        str68 = str98;
        mobileRxTotalBytes2 = whichBatteryRealtime19;
      } else {
        mobileTxTotalBytes = mobileTxTotalBytes3;
        sb3.append("\n       ");
        sb3.append(prefix);
        didOne6 = true;
        String[] strArr = DATA_CONNECTION_NAMES;
        sb3.append(
            i12 < strArr.length
                ? strArr[i12]
                : TimeZoneProviderService.TEST_COMMAND_RESULT_ERROR_KEY);
        sb3.append(str97);
        formatTimeMs(sb3, time4 / 1000);
        str67 = str99;
        sb3.append(str67);
        mobileRxTotalBytes = whichBatteryRealtime20;
        mobileRxTotalBytes2 = whichBatteryRealtime19;
        sb3.append(formatRatioLocked(time4, mobileRxTotalBytes2));
        str68 = str98;
        sb3.append(str68);
      }
      i12++;
      str98 = str68;
      str99 = str67;
      whichBatteryRealtime19 = mobileRxTotalBytes2;
      mobileTxTotalBytes3 = mobileTxTotalBytes;
      mobileRxTotalPackets3 = mobileRxTotalPackets;
      whichBatteryRealtime20 = mobileRxTotalBytes;
    }
    long mobileRxTotalBytes5 = whichBatteryRealtime20;
    String str100 = str99;
    String str101 = str98;
    long mobileRxTotalBytes6 = whichBatteryRealtime19;
    if (didOne6) {
      str15 = str95;
    } else {
      str15 = str95;
      sb3.append(str15);
    }
    printWriter.println(sb3.toString());
    sb3.setLength(0);
    sb3.append(prefix);
    sb3.append("     Cellular Rx signal strength (RSRP):");
    String str102 = str15;
    String[] cellularRxSignalStrengthDescription = {
      "very poor (less than -128dBm): ",
      "poor (-128dBm to -118dBm): ",
      "moderate (-118dBm to -108dBm): ",
      "good (-108dBm to -98dBm): ",
      "great (greater than -98dBm): "
    };
    int numCellularRxBins2 =
        Math.min(
            CellSignalStrength.getNumSignalStrengthLevels(),
            cellularRxSignalStrengthDescription.length);
    int i13 = 0;
    boolean didOne7 = false;
    while (i13 < numCellularRxBins2) {
      String str103 = str101;
      long time5 = getPhoneSignalStrengthTime(i13, rawRealtime15, which);
      if (time5 == 0) {
        numCellularRxBins = numCellularRxBins2;
        str65 = str97;
        str66 = str103;
      } else {
        sb3.append("\n       ");
        sb3.append(prefix);
        didOne7 = true;
        sb3.append(cellularRxSignalStrengthDescription[i13]);
        sb3.append(str97);
        numCellularRxBins = numCellularRxBins2;
        str65 = str97;
        formatTimeMs(sb3, time5 / 1000);
        sb3.append(str100);
        sb3.append(formatRatioLocked(time5, mobileRxTotalBytes6));
        str66 = str103;
        sb3.append(str66);
      }
      i13++;
      str101 = str66;
      str97 = str65;
      numCellularRxBins2 = numCellularRxBins;
    }
    String str104 = str97;
    String str105 = str101;
    if (didOne7) {
      str16 = str102;
    } else {
      str16 = str102;
      sb3.append(str16);
    }
    pw.println(sb3.toString());
    pw.print(prefix);
    sb3.setLength(0);
    sb3.append(prefix);
    sb3.append("  Wifi Statistics:");
    pw.println(sb3.toString());
    pw.print(prefix);
    sb3.setLength(0);
    sb3.append(prefix);
    sb3.append("     Wifi kernel active time: ");
    long wifiActiveTime = getWifiActiveTime(rawRealtime15, which);
    String str106 = str105;
    String str107 = str16;
    formatTimeMs(sb3, wifiActiveTime / 1000);
    sb3.append(str100);
    sb3.append(formatRatioLocked(wifiActiveTime, mobileRxTotalBytes6));
    sb3.append(str94);
    pw.println(sb3.toString());
    String[] cellularRxSignalStrengthDescription2 = cellularRxSignalStrengthDescription;
    String str108 = str100;
    long whichBatteryRealtime21 = mobileRxTotalBytes6;
    printControllerActivity(
        pw, sb3, prefix, WIFI_CONTROLLER_NAME, getWifiControllerActivity(), which);
    pw.print("     Wifi data received: ");
    pw.println(formatBytesLocked(wifiRxTotalBytes));
    pw.print("     Wifi data sent: ");
    long wifiTxTotalBytes3 = wifiTxTotalBytes2;
    pw.println(formatBytesLocked(wifiTxTotalBytes3));
    pw.print("     Wifi packets received: ");
    long wifiRxTotalPackets3 = wifiRxTotalPackets2;
    pw.println(wifiRxTotalPackets3);
    pw.print("     Wifi packets sent: ");
    long whichBatteryRealtime22 = wifiTxTotalPackets3;
    pw.println(whichBatteryRealtime22);
    sb3.setLength(0);
    sb3.append(prefix);
    sb3.append("     Wifi states:");
    boolean didOne8 = false;
    int i14 = 0;
    while (true) {
      wifiRxTotalPackets = wifiRxTotalPackets3;
      if (i14 >= 8) {
        break;
      }
      long time6 = getWifiStateTime(i14, rawRealtime15, which);
      if (time6 == 0) {
        wifiTxTotalBytes = wifiTxTotalBytes3;
        wifiTxTotalPackets = whichBatteryRealtime22;
        str64 = str106;
        str63 = str104;
        wifiTxTotalPackets2 = whichBatteryRealtime21;
      } else {
        wifiTxTotalBytes = wifiTxTotalBytes3;
        sb3.append("\n       ");
        didOne8 = true;
        sb3.append(WIFI_STATE_NAMES[i14]);
        str63 = str104;
        sb3.append(str63);
        wifiTxTotalPackets = whichBatteryRealtime22;
        long wifiTxTotalPackets4 = time6 / 1000;
        formatTimeMs(sb3, wifiTxTotalPackets4);
        sb3.append(str108);
        wifiTxTotalPackets2 = whichBatteryRealtime21;
        sb3.append(formatRatioLocked(time6, wifiTxTotalPackets2));
        str64 = str106;
        sb3.append(str64);
      }
      i14++;
      str106 = str64;
      str104 = str63;
      whichBatteryRealtime21 = wifiTxTotalPackets2;
      wifiTxTotalBytes3 = wifiTxTotalBytes;
      wifiRxTotalPackets3 = wifiRxTotalPackets;
      whichBatteryRealtime22 = wifiTxTotalPackets;
    }
    long wifiTxTotalPackets5 = whichBatteryRealtime22;
    String str109 = str106;
    String str110 = str104;
    long wifiTxTotalPackets6 = whichBatteryRealtime21;
    if (didOne8) {
      str17 = str107;
    } else {
      str17 = str107;
      sb3.append(str17);
    }
    pw.println(sb3.toString());
    sb3.setLength(0);
    sb3.append(prefix);
    sb3.append("     Wifi supplicant states:");
    boolean didOne9 = false;
    int i15 = 0;
    while (i15 < 13) {
      long time7 = getWifiSupplStateTime(i15, rawRealtime15, which);
      if (time7 == 0) {
        rawRealtime13 = rawRealtime15;
      } else {
        sb3.append("\n       ");
        didOne9 = true;
        sb3.append(WIFI_SUPPL_STATE_NAMES[i15]);
        sb3.append(str110);
        rawRealtime13 = rawRealtime15;
        formatTimeMs(sb3, time7 / 1000);
        sb3.append(str108);
        sb3.append(formatRatioLocked(time7, wifiTxTotalPackets6));
        sb3.append(str109);
      }
      i15++;
      rawRealtime15 = rawRealtime13;
    }
    long rawRealtime17 = rawRealtime15;
    if (!didOne9) {
      sb3.append(str17);
    }
    pw.println(sb3.toString());
    sb3.setLength(0);
    String str111 = prefix;
    sb3.append(str111);
    sb3.append("     Wifi Rx signal strength (RSSI):");
    String[] wifiRxSignalStrengthDescription = {
      "very poor (less than -88.75dBm): ",
      "poor (-88.75 to -77.5dBm): ",
      "moderate (-77.5dBm to -66.25dBm): ",
      "good (-66.25dBm to -55dBm): ",
      "great (greater than -55dBm): "
    };
    int numWifiRxBins = Math.min(5, wifiRxSignalStrengthDescription.length);
    int i16 = 0;
    boolean didOne10 = false;
    while (i16 < numWifiRxBins) {
      int numWifiRxBins2 = numWifiRxBins;
      String str112 = str109;
      String[] wifiRxSignalStrengthDescription2 = wifiRxSignalStrengthDescription;
      long rawRealtime18 = rawRealtime17;
      long time8 = getWifiSignalStrengthTime(i16, rawRealtime18, which);
      if (time8 == 0) {
        str61 = str110;
        rawRealtime17 = rawRealtime18;
        str62 = str112;
        str60 = str7;
      } else {
        str60 = str7;
        sb3.append(str60);
        sb3.append(str111);
        didOne10 = true;
        str61 = str110;
        sb3.append("     ");
        sb3.append(wifiRxSignalStrengthDescription2[i16]);
        rawRealtime17 = rawRealtime18;
        formatTimeMs(sb3, time8 / 1000);
        sb3.append(str108);
        sb3.append(formatRatioLocked(time8, wifiTxTotalPackets6));
        str62 = str112;
        sb3.append(str62);
      }
      i16++;
      str7 = str60;
      str109 = str62;
      wifiRxSignalStrengthDescription = wifiRxSignalStrengthDescription2;
      numWifiRxBins = numWifiRxBins2;
      str110 = str61;
    }
    String str113 = str110;
    String[] wifiRxSignalStrengthDescription3 = wifiRxSignalStrengthDescription;
    String str114 = str7;
    String str115 = str109;
    if (!didOne10) {
      sb3.append(str17);
    }
    pw.println(sb3.toString());
    pw.print(prefix);
    sb3.setLength(0);
    sb3.append(str111);
    sb3.append("  GPS Statistics:");
    pw.println(sb3.toString());
    sb3.setLength(0);
    sb3.append(str111);
    sb3.append("     GPS signal quality (Top 4 Average CN0):");
    String[] gpsSignalQualityDescription = {
      "poor (less than 20 dBHz): ", "good (greater than 20 dBHz): "
    };
    int numGpsSignalQualityBins = Math.min(2, gpsSignalQualityDescription.length);
    int i17 = 0;
    while (i17 < numGpsSignalQualityBins) {
      String[] gpsSignalQualityDescription2 = gpsSignalQualityDescription;
      long time9 = getGpsSignalQualityTime(i17, rawRealtime17, which);
      sb3.append(str114);
      sb3.append(str111);
      sb3.append("  ");
      sb3.append(gpsSignalQualityDescription2[i17]);
      formatTimeMs(sb3, time9 / 1000);
      sb3.append(str108);
      sb3.append(formatRatioLocked(time9, wifiTxTotalPackets6));
      sb3.append(str115);
      i17++;
      gpsSignalQualityDescription = gpsSignalQualityDescription2;
      numGpsSignalQualityBins = numGpsSignalQualityBins;
      str114 = str114;
    }
    String str116 = str114;
    String[] wifiRxSignalStrengthDescription4 = gpsSignalQualityDescription;
    pw.println(sb3.toString());
    long gpsBatteryDrainMaMs = getGpsBatteryDrainMaMs();
    if (gpsBatteryDrainMaMs > 0) {
      pw.print(prefix);
      sb3.setLength(0);
      sb3.append(str111);
      sb3.append("     GPS Battery Drain: ");
      sb3.append(new DecimalFormat("#.##").format(gpsBatteryDrainMaMs / 3600000.0d));
      sb3.append("mAh");
      pw.println(sb3.toString());
    }
    long rawRealtime19 = rawRealtime17;
    long gpsRunningTimeScreenOn = getScreenOnGpsRunningTime(rawRealtime19, which);
    if (gpsRunningTimeScreenOn > 0) {
      sb3.setLength(0);
      pw.print(prefix);
      sb3.append("     GPS run time while screen on: ");
      formatTimeMs(sb3, gpsRunningTimeScreenOn / 1000);
      pw.println(sb3.toString());
    }
    pw.print(prefix);
    sb3.setLength(0);
    sb3.append(str111);
    sb3.append("  CONNECTIVITY POWER SUMMARY END");
    pw.println(sb3.toString());
    pw.println(str96);
    pw.print(prefix);
    pw.print("  Bluetooth total received: ");
    pw.print(formatBytesLocked(btRxTotalBytes));
    pw.print(", sent: ");
    long wifiTxTotalPackets7 = gpsBatteryDrainMaMs;
    pw.println(formatBytesLocked(btTxTotalBytes));
    long btTxTotalBytes2 = getBluetoothScanTime(rawRealtime19, which) / 1000;
    sb3.setLength(0);
    sb3.append(str111);
    sb3.append("  Bluetooth scan time: ");
    formatTimeMs(sb3, btTxTotalBytes2);
    pw.println(sb3.toString());
    long mactTimeMs6 = btRxTotalBytes;
    long totalTimeMillis = rawRealtime19;
    String str117 = str115;
    String str118 = str113;
    long bluetoothScanTimeMs = wifiTxTotalPackets5;
    printControllerActivity(pw, sb3, prefix, "Bluetooth", getBluetoothControllerActivity(), which);
    pw.println();
    pw.print(prefix);
    sb3.setLength(0);
    sb3.append(str111);
    sb3.append("  Speaker Statistics:");
    boolean didOne11 = false;
    int i18 = 0;
    while (true) {
      str18 = ": ";
      if (i18 >= 16) {
        break;
      }
      long time10 = getSpeakerMediaTime(i18, which);
      if (time10 != 0) {
        didOne11 = true;
        sb3.append(str116);
        sb3.append("level(media) " + i18);
        sb3.append(": ");
        formatTimeMs(sb3, time10);
      }
      i18++;
    }
    boolean didOne12 = didOne11;
    for (int i19 = 0; i19 < 16; i19++) {
      long time11 = getSpeakerCallTime(i19, which);
      if (time11 != 0) {
        sb3.append(str116);
        sb3.append("level(call) " + i19);
        sb3.append(": ");
        formatTimeMs(sb3, time11);
        didOne12 = true;
      }
    }
    if (!didOne12) {
      sb3.append(str17);
    }
    pw.println(sb3.toString());
    pw.println();
    pw.print(prefix);
    pw.println("  Device battery use since last full charge");
    pw.print(prefix);
    pw.print("    Amount discharged (lower bound): ");
    pw.println(getLowDischargeAmountSinceCharge());
    pw.print(prefix);
    pw.print("    Amount discharged (upper bound): ");
    pw.println(getHighDischargeAmountSinceCharge());
    pw.print(prefix);
    pw.print("    Amount discharged while screen on: ");
    pw.println(getDischargeAmountScreenOnSinceCharge());
    pw.print(prefix);
    pw.print("    Amount discharged permil while screen on: ");
    pw.println(getDischargeAmountScreenOnSinceChargePermil());
    if (mobileRxTotalBytes3 > 0) {
      pw.print(prefix);
      pw.print("    Amount discharged permil while sub screen on: ");
      pw.println(getDischargeAmountSubScreenOnSinceChargePermil());
    }
    pw.print(prefix);
    pw.print("    Amount discharged while screen off: ");
    pw.println(getDischargeAmountScreenOffSinceCharge());
    pw.print(prefix);
    pw.print("    Amount discharged permil while screen off: ");
    pw.println(getDischargeAmountScreenOffSinceChargePermil());
    pw.print(prefix);
    pw.print("    Amount discharged while screen doze: ");
    pw.println(getDischargeAmountScreenDozeSinceCharge());
    pw.println();
    BatteryUsageStats stats2 = getBatteryUsageStats(context, true);
    stats2.dump(pw, str111);
    List<UidMobileRadioStats> uidMobileRadioStats =
        getUidMobileRadioStats(stats2.getUidBatteryConsumers());
    if (uidMobileRadioStats.size() <= 0) {
      stats = stats2;
      str19 = str94;
      str20 = str90;
      whichBatteryRealtime4 = wifiTxTotalPackets6;
    } else {
      pw.print(prefix);
      pw.println("  Per-app mobile ms per packet:");
      long totalTime = 0;
      int i20 = 0;
      while (i20 < uidMobileRadioStats.size()) {
        UidMobileRadioStats mrs = uidMobileRadioStats.get(i20);
        sb3.setLength(0);
        sb3.append(str111);
        sb3.append("    Uid ");
        UserHandle.formatUid(sb3, mrs.uid);
        sb3.append(": ");
        sb3.append(formatValue(mrs.millisecondsPerPacket));
        sb3.append(" (");
        sb3.append(mrs.rxPackets + mrs.txPackets);
        sb3.append(" packets over ");
        formatTimeMsNoSpace(sb3, mrs.radioActiveMs);
        sb3.append(str117);
        sb3.append(mrs.radioActiveCount);
        sb3.append(str90);
        pw.println(sb3);
        totalTime += mrs.radioActiveMs;
        i20++;
        stats2 = stats2;
        uidMobileRadioStats = uidMobileRadioStats;
      }
      stats = stats2;
      str20 = str90;
      sb3.setLength(0);
      sb3.append(str111);
      sb3.append("    TOTAL TIME: ");
      formatTimeMs(sb3, totalTime);
      sb3.append(str108);
      whichBatteryRealtime4 = wifiTxTotalPackets6;
      sb3.append(formatRatioLocked(totalTime, whichBatteryRealtime4));
      str19 = str94;
      sb3.append(str19);
      pw.println(sb3);
      pw.println();
    }
    Comparator<TimerEntry> timerComparator2 =
        new Comparator<TimerEntry>() { // from class: android.os.BatteryStats.1
          @Override // java.util.Comparator
          public int compare(TimerEntry lhs, TimerEntry rhs) {
            long lhsTime = lhs.mTime;
            long rhsTime = rhs.mTime;
            if (lhsTime < rhsTime) {
              return 1;
            }
            if (lhsTime > rhsTime) {
              return -1;
            }
            return 0;
          }
        };
    if (reqUid >= 0) {
      timerComparator = timerComparator2;
      whichBatteryRealtime5 = whichBatteryRealtime4;
      str21 = str117;
      str22 = str108;
      str23 = str20;
      rawRealtime2 = totalTimeMillis;
      str24 = str118;
      i = which;
      str25 = ": ";
    } else {
      Map<String, ? extends Timer> kernelWakelocks = getKernelWakelockStats();
      if (kernelWakelocks.size() <= 0) {
        timerComparator = timerComparator2;
        whichBatteryRealtime5 = whichBatteryRealtime4;
        str21 = str117;
        str22 = str108;
        str23 = str20;
        rawRealtime2 = totalTimeMillis;
        i = which;
        str25 = ": ";
      } else {
        ArrayList<TimerEntry> ktimers = new ArrayList<>();
        Iterator<Map.Entry<String, ? extends Timer>> it = kernelWakelocks.entrySet().iterator();
        while (it.hasNext()) {
          Map.Entry<String, ? extends Timer> ent = it.next();
          Iterator<Map.Entry<String, ? extends Timer>> it2 = it;
          Timer timer = ent.getValue();
          long whichBatteryRealtime23 = whichBatteryRealtime4;
          BatteryUsageStats stats3 = stats;
          String str119 = str117;
          long rawRealtime20 = totalTimeMillis;
          long totalTimeMillis2 = computeWakeLock(timer, rawRealtime20, which);
          if (totalTimeMillis2 > 0) {
            ktimers.add(new TimerEntry(ent.getKey(), 0, timer, totalTimeMillis2));
          }
          totalTimeMillis = rawRealtime20;
          str117 = str119;
          it = it2;
          stats = stats3;
          whichBatteryRealtime4 = whichBatteryRealtime23;
        }
        whichBatteryRealtime5 = whichBatteryRealtime4;
        BatteryUsageStats stats4 = stats;
        str21 = str117;
        rawRealtime2 = totalTimeMillis;
        if (ktimers.size() <= 0) {
          timerComparator = timerComparator2;
          str22 = str108;
          str23 = str20;
          i = which;
          str25 = ": ";
        } else {
          Collections.sort(ktimers, timerComparator2);
          pw.print(prefix);
          pw.println("  All kernel wake locks:");
          int i21 = 0;
          while (i21 < ktimers.size()) {
            TimerEntry timer2 = ktimers.get(i21);
            sb3.setLength(0);
            sb3.append(str111);
            sb3.append("  Kernel Wake lock ");
            sb3.append(timer2.mName);
            Comparator<TimerEntry> timerComparator3 = timerComparator2;
            ArrayList<TimerEntry> ktimers2 = ktimers;
            int i22 = i21;
            String str120 = str20;
            BatteryUsageStats stats5 = stats4;
            String str121 = str108;
            String str122 = str18;
            String linePrefix = printWakeLock(sb3, timer2.mTimer, rawRealtime2, null, which, ": ");
            if (!linePrefix.equals(str122)) {
              sb3.append(" realtime");
              pw.println(sb3.toString());
            }
            i21 = i22 + 1;
            timerComparator2 = timerComparator3;
            str18 = str122;
            stats4 = stats5;
            ktimers = ktimers2;
            str20 = str120;
            str108 = str121;
          }
          timerComparator = timerComparator2;
          str22 = str108;
          str23 = str20;
          i = which;
          str25 = str18;
          pw.println();
        }
      }
      if (timers.size() <= 0) {
        str24 = str118;
      } else {
        ArrayList<TimerEntry> timers3 = timers;
        Collections.sort(timers3, timerComparator);
        pw.print(prefix);
        pw.println("  All partial wake locks:");
        int i23 = 0;
        while (i23 < timers3.size()) {
          TimerEntry timer3 = timers3.get(i23);
          sb3.setLength(0);
          sb3.append("  Wake lock ");
          UserHandle.formatUid(sb3, timer3.mId);
          String str123 = str118;
          sb3.append(str123);
          sb3.append(timer3.mName);
          printWakeLock(sb3, timer3.mTimer, rawRealtime2, null, which, ": ");
          sb3.append(" realtime");
          pw.println(sb3.toString());
          i23++;
          timers3 = timers3;
          str118 = str123;
        }
        str24 = str118;
        timers3.clear();
        pw.println();
      }
      Map<String, ? extends Timer> wakeupReasons = getWakeupReasonStats();
      if (wakeupReasons.size() > 0) {
        pw.print(prefix);
        pw.println("  All wakeup reasons:");
        ArrayList<TimerEntry> reasons = new ArrayList<>();
        for (Map.Entry<String, ? extends Timer> ent2 : wakeupReasons.entrySet()) {
          reasons.add(new TimerEntry(ent2.getKey(), 0, ent2.getValue(), r2.getCountLocked(i)));
        }
        Collections.sort(reasons, timerComparator);
        int i24 = 0;
        while (i24 < reasons.size()) {
          TimerEntry timer4 = reasons.get(i24);
          sb3.setLength(0);
          sb3.append(str111);
          sb3.append("  Wakeup reason ");
          sb3.append(timer4.mName);
          printWakeLock(sb3, timer4.mTimer, rawRealtime2, null, which, ": ");
          sb3.append(" realtime");
          pw.println(sb3.toString());
          i24++;
          reasons = reasons;
        }
        pw.println();
      }
    }
    Map<String, ? extends LongCounter> screenWakeStats = getScreenWakeStats();
    if (screenWakeStats.size() > 0) {
      pw.print(prefix);
      pw.println("  All screen wake reasons:");
      for (Map.Entry<String, ? extends LongCounter> ent3 : screenWakeStats.entrySet()) {
        LongCounter counter = ent3.getValue();
        sb3.setLength(0);
        sb3.append("  ");
        sb3.append(ent3.getKey());
        sb3.append(str25);
        sb3.append(counter.getCountLocked(i));
        sb3.append(" times");
        pw.println(sb3.toString());
      }
      pw.println();
    }
    LongSparseArray<? extends Timer> mMemoryStats = getKernelMemoryStats();
    if (mMemoryStats.size() <= 0) {
      i2 = 0;
    } else {
      pw.println("  Memory Stats");
      for (int i25 = 0; i25 < mMemoryStats.size(); i25++) {
        sb3.setLength(0);
        sb3.append("  Bandwidth ");
        sb3.append(mMemoryStats.keyAt(i25));
        sb3.append(" Time ");
        sb3.append(mMemoryStats.valueAt(i25).getTotalTimeLocked(rawRealtime2, i));
        pw.println(sb3.toString());
      }
      i2 = 0;
      pw.println();
    }
    Map<String, ? extends Timer> rpmStats = getRpmStats();
    if (rpmStats.size() <= 0) {
      i3 = i2;
      sb = sb3;
      str26 = str25;
      str27 = str19;
      i4 = i;
      numDisplays2 = numDisplays;
      str28 = str21;
      whichBatteryRealtime6 = whichBatteryRealtime5;
      str29 = str24;
      str30 = str22;
      str31 = str23;
      mobileTxTotalPackets = rawRealtime2;
    } else {
      pw.print(prefix);
      pw.println("  Resource Power Manager Stats");
      if (rpmStats.size() <= 0) {
        i3 = i2;
        sb = sb3;
        str26 = str25;
        str27 = str19;
        i4 = i;
        numDisplays2 = numDisplays;
        str28 = str21;
        whichBatteryRealtime6 = whichBatteryRealtime5;
        str29 = str24;
        str30 = str22;
        str31 = str23;
        mobileTxTotalPackets = rawRealtime2;
      } else {
        for (Map.Entry<String, ? extends Timer> ent4 : rpmStats.entrySet()) {
          String timerName = ent4.getKey();
          StringBuilder sb4 = sb3;
          long whichBatteryRealtime24 = whichBatteryRealtime5;
          long whichBatteryRealtime25 = mactTimeMs6;
          long btRxTotalBytes2 = interactiveTime3;
          long mactTimeMs7 = interactiveTime;
          long mobileTxTotalPackets3 = rawRealtime16;
          long mobileTxTotalPackets4 = rawRealtime2;
          printTimer(pw, sb4, ent4.getValue(), mobileTxTotalPackets4, which, prefix, timerName);
          str111 = str111;
          i2 = i2;
          str22 = str22;
          sb3 = sb4;
          i = i;
          mMemoryStats = mMemoryStats;
          timerComparator = timerComparator;
          rawRealtime2 = mobileTxTotalPackets4;
          rawRealtime16 = mobileTxTotalPackets3;
          wifiRxTotalPackets = wifiRxTotalPackets;
          str25 = str25;
          str19 = str19;
          numDisplays = numDisplays;
          mobileRxTotalBytes5 = mobileRxTotalBytes5;
          mobileRxTotalPackets = mobileRxTotalPackets;
          bluetoothScanTimeMs = bluetoothScanTimeMs;
          wifiTxTotalPackets7 = wifiTxTotalPackets7;
          mobileRxTotalBytes3 = mobileRxTotalBytes3;
          deviceIdleModeLightTime = deviceIdleModeLightTime;
          deviceIdleModeFullTime = deviceIdleModeFullTime;
          deviceLightIdlingTime = deviceLightIdlingTime;
          deviceIdlingTime4 = deviceIdlingTime4;
          interactiveTime = mactTimeMs7;
          interactiveTime3 = btRxTotalBytes2;
          mactTimeMs6 = whichBatteryRealtime25;
          whichBatteryRealtime5 = whichBatteryRealtime24;
          connChanges3 = connChanges3;
          wifiRxSignalStrengthDescription3 = wifiRxSignalStrengthDescription3;
          wifiRxSignalStrengthDescription4 = wifiRxSignalStrengthDescription4;
          multicastWakeLockCountTotal = multicastWakeLockCountTotal;
          cellularRxSignalStrengthDescription2 = cellularRxSignalStrengthDescription2;
        }
        i3 = i2;
        sb = sb3;
        str26 = str25;
        str27 = str19;
        i4 = i;
        numDisplays2 = numDisplays;
        str28 = str21;
        whichBatteryRealtime6 = whichBatteryRealtime5;
        str29 = str24;
        str30 = str22;
        str31 = str23;
        mobileTxTotalPackets = rawRealtime2;
      }
      pw.println();
    }
    long[] cpuFreqs2 = getCpuFreqs();
    if (cpuFreqs2 == null) {
      printWriter2 = pw;
    } else {
      sb.setLength(i3);
      sb.append("  CPU freqs:");
      for (long j9 : cpuFreqs2) {
        sb.append(' ').append(j9);
      }
      printWriter2 = pw;
      printWriter2.println(sb.toString());
      pw.println();
    }
    int iu3 = 0;
    while (true) {
      int NU6 = NU5;
      if (iu3 < NU6) {
        SparseArray<? extends Uid> uidStats7 = uidStats6;
        int uid = uidStats7.keyAt(iu3);
        if (reqUid >= 0 && uid != reqUid && uid != 1000) {
          str40 = str30;
          str36 = str31;
          NU2 = NU6;
          uidStats = uidStats7;
          iu = iu3;
          printWriter9 = printWriter2;
          rawRealtime7 = mobileTxTotalPackets;
          rawRealtimeMs3 = rawRealtimeMs8;
          batteryUptime = batteryUptime4;
          mobileActiveTime2 = mobileActiveTime4;
          whichBatteryRealtime8 = whichBatteryRealtime6;
          str43 = str26;
          str38 = str27;
          str42 = str29;
          str35 = str28;
          i7 = i4;
          cpuFreqs = cpuFreqs2;
          sb2 = sb;
        } else {
          Uid u6 = uidStats7.valueAt(iu3);
          pw.print(prefix);
          printWriter2.print("  ");
          UserHandle.formatUid(printWriter2, uid);
          printWriter2.println(":");
          boolean uidActivity3 = false;
          long mobileRxBytes2 = u6.getNetworkActivityBytes(i3, i4);
          long mobileTxBytes = u6.getNetworkActivityBytes(1, i4);
          long wifiRxBytes3 = u6.getNetworkActivityBytes(2, i4);
          long wifiTxBytes3 = u6.getNetworkActivityBytes(3, i4);
          long btRxBytes2 = u6.getNetworkActivityBytes(4, i4);
          long btTxBytes2 = u6.getNetworkActivityBytes(5, i4);
          long mobileRxPackets = u6.getNetworkActivityPackets(0, i4);
          String str124 = str30;
          StringBuilder sb5 = sb;
          long mobileTxPackets = u6.getNetworkActivityPackets(1, i4);
          int iu4 = iu3;
          long wifiRxPackets4 = u6.getNetworkActivityPackets(2, i4);
          long[] cpuFreqs3 = cpuFreqs2;
          long wifiTxPackets3 = u6.getNetworkActivityPackets(3, i4);
          long uidMobileActiveTime2 = u6.getMobileRadioActiveTime(i4);
          int uidMobileActiveCount = u6.getMobileRadioActiveCount(i4);
          long rawRealtime21 = mobileTxTotalPackets;
          long fullWifiLockOnTime = u6.getFullWifiLockTime(rawRealtime21, i4);
          long wifiScanTime = u6.getWifiScanTime(rawRealtime21, i4);
          int wifiScanCount = u6.getWifiScanCount(i4);
          int wifiScanCountBg2 = u6.getWifiScanBackgroundCount(i4);
          long wifiScanActualTime = u6.getWifiScanActualTime(rawRealtime21);
          long wifiScanActualTimeBg2 = u6.getWifiScanBackgroundTime(rawRealtime21);
          long uidWifiRunningTime = u6.getWifiRunningTime(rawRealtime21, i4);
          long mobileWakeup = u6.getMobileRadioApWakeupCount(i4);
          long wifiWakeup2 = u6.getWifiRadioApWakeupCount(i4);
          if (mobileRxBytes2 > 0
              || mobileTxBytes > 0
              || mobileRxPackets > 0
              || mobileTxPackets > 0) {
            pw.print(prefix);
            wifiWakeup = wifiWakeup2;
            printWriter3 = pw;
            wifiRxPackets = wifiRxPackets4;
            printWriter3.print("    Mobile network: ");
            batteryStats = this;
            printWriter3.print(batteryStats.formatBytesLocked(mobileRxBytes2));
            printWriter3.print(" received, ");
            printWriter3.print(batteryStats.formatBytesLocked(mobileTxBytes));
            printWriter3.print(" sent (packets ");
            printWriter3.print(mobileRxPackets);
            printWriter3.print(" received, ");
            printWriter3.print(mobileTxPackets);
            printWriter3.println(" sent)");
          } else {
            wifiWakeup = wifiWakeup2;
            wifiRxPackets = wifiRxPackets4;
            batteryStats = this;
            printWriter3 = pw;
          }
          if (uidMobileActiveTime2 <= 0 && uidMobileActiveCount <= 0) {
            uidMobileActiveTime = uidMobileActiveTime2;
            sb2 = sb5;
            rawRealtime4 = rawRealtime21;
            u = u6;
            rawRealtime3 = mobileRxBytes2;
            mobileRxBytes = mobileActiveTime4;
            str32 = str28;
            mobileActiveTime = mobileRxPackets;
          } else {
            sb2 = sb5;
            sb2.setLength(0);
            rawRealtime3 = mobileRxBytes2;
            sb2.append(prefix);
            sb2.append("    Mobile radio active: ");
            rawRealtime4 = rawRealtime21;
            formatTimeMs(sb2, uidMobileActiveTime2 / 1000);
            sb2.append(str124);
            mobileRxBytes = mobileActiveTime4;
            u = u6;
            sb2.append(batteryStats.formatRatioLocked(uidMobileActiveTime2, mobileRxBytes));
            str32 = str28;
            sb2.append(str32);
            sb2.append(uidMobileActiveCount);
            sb2.append(str31);
            long packets = mobileRxPackets + mobileTxPackets;
            long mobileTxPackets2 = packets == 0 ? 1L : packets;
            sb2.append(" @ ");
            mobileActiveTime = mobileRxPackets;
            uidMobileActiveTime = uidMobileActiveTime2;
            sb2.append(formatCharge((uidMobileActiveTime2 / 1000) / mobileTxPackets2));
            sb2.append(" mspp");
            printWriter3.println(sb2.toString());
          }
          if (mobileWakeup <= 0) {
            str33 = prefix;
            i5 = 0;
          } else {
            i5 = 0;
            sb2.setLength(0);
            str33 = prefix;
            sb2.append(str33);
            sb2.append("    Mobile radio AP wakeups: ");
            sb2.append(mobileWakeup);
            printWriter3.println(sb2.toString());
          }
          long wifiWakeup3 = wifiWakeup;
          String str125 = str33;
          PrintWriter printWriter11 = printWriter3;
          long mobileActiveTime5 = mobileRxBytes;
          int i26 = i5;
          String str126 = str124;
          String str127 = str31;
          printControllerActivityIfInteresting(
              pw,
              sb2,
              str33 + "  ",
              CELLULAR_CONTROLLER_NAME,
              u.getModemControllerActivity(),
              which);
          if (wifiRxBytes3 > 0 || wifiTxBytes3 > 0) {
            wifiRxPackets2 = wifiRxPackets;
          } else {
            wifiRxPackets2 = wifiRxPackets;
            if (wifiRxPackets2 <= 0 && wifiTxPackets3 <= 0) {
              wifiRxBytes = wifiRxBytes3;
              wifiTxBytes = wifiTxBytes3;
              wifiRxPackets3 = wifiTxPackets3;
              if (fullWifiLockOnTime != 0
                  && wifiScanTime == 0
                  && wifiScanCount == 0
                  && wifiScanCountBg2 == 0
                  && wifiScanActualTime == 0
                  && wifiScanActualTimeBg2 == 0
                  && uidWifiRunningTime == 0) {
                wifiRxBytes2 = wifiRxBytes;
                wifiTxPackets = wifiRxPackets3;
                printWriter4 = printWriter11;
                wifiScanCountBg = wifiScanCountBg2;
                wifiTxPackets2 = wifiScanActualTime;
                wifiScanActualTimeBg = wifiScanActualTimeBg2;
                long j10 = wifiTxBytes;
                wifiTxBytes2 = rawRealtime4;
                rawRealtime5 = whichBatteryRealtime6;
                whichBatteryRealtime7 = j10;
              } else {
                sb2.setLength(i26);
                sb2.append(str125);
                sb2.append("    Wifi Running: ");
                formatTimeMs(sb2, uidWifiRunningTime / 1000);
                sb2.append(str126);
                wifiRxBytes2 = wifiRxBytes;
                wifiTxPackets = wifiRxPackets3;
                long wifiTxPackets4 = whichBatteryRealtime6;
                sb2.append(formatRatioLocked(uidWifiRunningTime, wifiTxPackets4));
                sb2.append(")\n");
                sb2.append(str125);
                sb2.append("    Full Wifi Lock: ");
                whichBatteryRealtime7 = wifiTxBytes;
                formatTimeMs(sb2, fullWifiLockOnTime / 1000);
                sb2.append(str126);
                sb2.append(formatRatioLocked(fullWifiLockOnTime, wifiTxPackets4));
                sb2.append(")\n");
                sb2.append(str125);
                sb2.append("    Wifi Scan (blamed): ");
                formatTimeMs(sb2, wifiScanTime / 1000);
                sb2.append(str126);
                sb2.append(formatRatioLocked(wifiScanTime, wifiTxPackets4));
                sb2.append(str32);
                sb2.append(wifiScanCount);
                sb2.append("x\n");
                sb2.append(str125);
                sb2.append("    Wifi Scan (actual): ");
                formatTimeMs(sb2, wifiScanActualTime / 1000);
                sb2.append(str126);
                wifiTxBytes2 = rawRealtime4;
                rawRealtime5 = wifiTxPackets4;
                wifiTxPackets2 = wifiScanActualTime;
                sb2.append(
                    formatRatioLocked(wifiTxPackets2, computeBatteryRealtime(wifiTxBytes2, 0)));
                sb2.append(str32);
                sb2.append(wifiScanCount);
                sb2.append("x\n");
                sb2.append(str125);
                sb2.append("    Background Wifi Scan: ");
                formatTimeMs(sb2, wifiScanActualTimeBg2 / 1000);
                sb2.append(str126);
                wifiScanActualTimeBg = wifiScanActualTimeBg2;
                sb2.append(
                    formatRatioLocked(
                        wifiScanActualTimeBg, computeBatteryRealtime(wifiTxBytes2, 0)));
                sb2.append(str32);
                wifiScanCountBg = wifiScanCountBg2;
                sb2.append(wifiScanCountBg);
                sb2.append(str127);
                printWriter4 = pw;
                printWriter4.println(sb2.toString());
              }
              if (wifiWakeup3 > 0) {
                sb2.setLength(0);
                sb2.append(str125);
                sb2.append("    WiFi AP wakeups: ");
                sb2.append(wifiWakeup3);
                printWriter4.println(sb2.toString());
              }
              long rawRealtime22 = wifiTxBytes2;
              printWriter5 = printWriter4;
              whichBatteryRealtime8 = rawRealtime5;
              printControllerActivityIfInteresting(
                  pw,
                  sb2,
                  str125 + "  ",
                  WIFI_CONTROLLER_NAME,
                  u.getWifiControllerActivity(),
                  which);
              if (btRxBytes2 > 0 && btTxBytes2 <= 0) {
                btRxBytes = btRxBytes2;
                btTxBytes = btTxBytes2;
              } else {
                pw.print(prefix);
                printWriter5.print("    Bluetooth network: ");
                btRxBytes = btRxBytes2;
                printWriter5.print(formatBytesLocked(btRxBytes));
                printWriter5.print(" received, ");
                btTxBytes = btTxBytes2;
                printWriter5.print(formatBytesLocked(btTxBytes));
                printWriter5.println(" sent");
              }
              bleTimer = u.getBluetoothScanTimer();
              String str128 = "\n";
              if (bleTimer == null) {
                long btTxBytes3 = (bleTimer.getTotalTimeLocked(rawRealtime22, which) + 500) / 1000;
                if (btTxBytes3 == 0) {
                  str34 = " times)";
                  bleTimer2 = bleTimer;
                  str35 = str32;
                  str36 = str127;
                  rawRealtimeMs = rawRealtimeMs8;
                  rawRealtimeMs2 = rawRealtime22;
                  printWriter6 = printWriter5;
                } else {
                  int count2 = bleTimer.getCountLocked(which);
                  Timer bleTimerBg = u.getBluetoothScanBackgroundTimer();
                  int countBg2 = bleTimerBg != null ? bleTimerBg.getCountLocked(which) : 0;
                  str35 = str32;
                  Timer bleDutyTimer = u.getBluetoothDutyScanTimer();
                  if (bleDutyTimer != null) {
                    j3 = (bleDutyTimer.getTotalTimeLocked(rawRealtime22, which) + 500) / 1000;
                  } else {
                    j3 = 0;
                  }
                  long totalDutyTimeMs = j3;
                  str36 = str127;
                  long rawRealtimeMs9 = rawRealtimeMs8;
                  rawRealtimeMs2 = rawRealtime22;
                  long actualTimeMs = bleTimer.getTotalDurationMsLocked(rawRealtimeMs9);
                  if (bleTimerBg == null) {
                    j4 = 0;
                  } else {
                    j4 = bleTimerBg.getTotalDurationMsLocked(rawRealtimeMs9);
                  }
                  long actualTimeMsBg = j4;
                  int resultCount2 =
                      u.getBluetoothScanResultCounter() != null
                          ? u.getBluetoothScanResultCounter().getCountLocked(which)
                          : 0;
                  if (u.getBluetoothScanResultBgCounter() != null) {
                    resultCount = resultCount2;
                    resultCountBg = u.getBluetoothScanResultBgCounter().getCountLocked(which);
                  } else {
                    resultCount = resultCount2;
                    resultCountBg = 0;
                  }
                  Timer unoptimizedScanTimer = u.getBluetoothUnoptimizedScanTimer();
                  if (unoptimizedScanTimer == null) {
                    j5 = 0;
                  } else {
                    j5 = unoptimizedScanTimer.getTotalDurationMsLocked(rawRealtimeMs9);
                  }
                  long unoptimizedScanTotalTime = j5;
                  if (unoptimizedScanTimer == null) {
                    j6 = 0;
                  } else {
                    j6 = unoptimizedScanTimer.getMaxDurationMsLocked(rawRealtimeMs9);
                  }
                  long unoptimizedScanMaxTime = j6;
                  Timer unoptimizedScanTimerBg = u.getBluetoothUnoptimizedScanBackgroundTimer();
                  if (unoptimizedScanTimerBg == null) {
                    j7 = 0;
                  } else {
                    j7 = unoptimizedScanTimerBg.getTotalDurationMsLocked(rawRealtimeMs9);
                  }
                  long unoptimizedScanTotalTimeBg2 = j7;
                  if (unoptimizedScanTimerBg == null) {
                    j8 = 0;
                  } else {
                    j8 = unoptimizedScanTimerBg.getMaxDurationMsLocked(rawRealtimeMs9);
                  }
                  long unoptimizedScanMaxTimeBg = j8;
                  rawRealtimeMs = rawRealtimeMs9;
                  sb2.setLength(0);
                  sb2.append(str125);
                  sb2.append("    Bluetooth Duty Scan (total actual realtime with duty): ");
                  formatTimeMs(sb2, totalDutyTimeMs);
                  if (bleDutyTimer != null && bleDutyTimer.isRunningLocked()) {
                    sb2.append(" (currently running)");
                  }
                  sb2.append("\n");
                  if (actualTimeMs != btTxBytes3) {
                    sb2.append(str125);
                    sb2.append("    Bluetooth Scan (total blamed realtime): ");
                    formatTimeMs(sb2, btTxBytes3);
                    sb2.append(" (");
                    sb2.append(count2);
                    sb2.append(" times)");
                    if (bleTimer.isRunningLocked()) {
                      sb2.append(" (currently running)");
                    }
                    sb2.append("\n");
                  }
                  sb2.append(str125);
                  sb2.append("    Bluetooth Scan (total actual realtime): ");
                  formatTimeMs(sb2, actualTimeMs);
                  sb2.append(" (");
                  sb2.append(count2);
                  sb2.append(" times)");
                  if (bleTimer.isRunningLocked()) {
                    sb2.append(" (currently running)");
                  }
                  sb2.append("\n");
                  if (actualTimeMsBg <= 0) {
                    countBg = countBg2;
                    if (countBg <= 0) {
                      bleTimer2 = bleTimer;
                      sb2.append(str125);
                      sb2.append("    Bluetooth Scan Results: ");
                      sb2.append(resultCount);
                      str34 = " times)";
                      sb2.append(" (");
                      sb2.append(resultCountBg);
                      sb2.append(" in background)");
                      if (unoptimizedScanTotalTime > 0) {
                        count = count2;
                        unoptimizedScanTotalTimeBg = unoptimizedScanTotalTimeBg2;
                        if (unoptimizedScanTotalTimeBg <= 0) {
                          printWriter6 = pw;
                          printWriter6.println(sb2.toString());
                          uidActivity3 = true;
                        }
                      } else {
                        count = count2;
                        unoptimizedScanTotalTimeBg = unoptimizedScanTotalTimeBg2;
                      }
                      sb2.append("\n");
                      sb2.append(str125);
                      sb2.append("    Unoptimized Bluetooth Scan (realtime): ");
                      formatTimeMs(sb2, unoptimizedScanTotalTime);
                      sb2.append(" (max ");
                      formatTimeMs(sb2, unoptimizedScanMaxTime);
                      str59 = str27;
                      sb2.append(str59);
                      if (unoptimizedScanTimer != null && unoptimizedScanTimer.isRunningLocked()) {
                        sb2.append(" (currently running unoptimized)");
                      }
                      if (unoptimizedScanTimerBg != null || unoptimizedScanTotalTimeBg <= 0) {
                        str27 = str59;
                      } else {
                        sb2.append("\n");
                        sb2.append(str125);
                        sb2.append("    Unoptimized Bluetooth Scan (background realtime): ");
                        formatTimeMs(sb2, unoptimizedScanTotalTimeBg);
                        sb2.append(" (max ");
                        formatTimeMs(sb2, unoptimizedScanMaxTimeBg);
                        sb2.append(str59);
                        if (!unoptimizedScanTimerBg.isRunningLocked()) {
                          str27 = str59;
                        } else {
                          str27 = str59;
                          sb2.append(" (currently running unoptimized in background)");
                        }
                      }
                      printWriter6 = pw;
                      printWriter6.println(sb2.toString());
                      uidActivity3 = true;
                    }
                  } else {
                    countBg = countBg2;
                  }
                  sb2.append(str125);
                  bleTimer2 = bleTimer;
                  sb2.append("    Bluetooth Scan (background realtime): ");
                  formatTimeMs(sb2, actualTimeMsBg);
                  sb2.append(" (");
                  sb2.append(countBg);
                  sb2.append(" times)");
                  if (bleTimerBg != null && bleTimerBg.isRunningLocked()) {
                    sb2.append(" (currently running in background)");
                  }
                  sb2.append("\n");
                  sb2.append(str125);
                  sb2.append("    Bluetooth Scan Results: ");
                  sb2.append(resultCount);
                  str34 = " times)";
                  sb2.append(" (");
                  sb2.append(resultCountBg);
                  sb2.append(" in background)");
                  if (unoptimizedScanTotalTime > 0) {}
                  sb2.append("\n");
                  sb2.append(str125);
                  sb2.append("    Unoptimized Bluetooth Scan (realtime): ");
                  formatTimeMs(sb2, unoptimizedScanTotalTime);
                  sb2.append(" (max ");
                  formatTimeMs(sb2, unoptimizedScanMaxTime);
                  str59 = str27;
                  sb2.append(str59);
                  if (unoptimizedScanTimer != null) {
                    sb2.append(" (currently running unoptimized)");
                  }
                  if (unoptimizedScanTimerBg != null) {}
                  str27 = str59;
                  printWriter6 = pw;
                  printWriter6.println(sb2.toString());
                  uidActivity3 = true;
                }
              } else {
                str34 = " times)";
                bleTimer2 = bleTimer;
                str35 = str32;
                str36 = str127;
                rawRealtimeMs = rawRealtimeMs8;
                rawRealtimeMs2 = rawRealtime22;
                printWriter6 = printWriter5;
              }
              if (u.hasUserActivity()) {
                i6 = which;
                u2 = u;
                str37 = str29;
              } else {
                boolean hasData = false;
                int i27 = 0;
                while (i27 < Uid.NUM_USER_ACTIVITY_TYPES) {
                  Uid u7 = u;
                  int val = u7.getUserActivityCount(i27, which);
                  if (val == 0) {
                    str58 = str29;
                  } else {
                    if (!hasData) {
                      sb2.setLength(0);
                      sb2.append("    User activity: ");
                      hasData = true;
                    } else {
                      sb2.append(", ");
                    }
                    sb2.append(val);
                    str58 = str29;
                    sb2.append(str58);
                    sb2.append(Uid.USER_ACTIVITY_TYPES[i27]);
                  }
                  i27++;
                  u = u7;
                  str29 = str58;
                }
                i6 = which;
                u2 = u;
                str37 = str29;
                if (hasData) {
                  printWriter6.println(sb2.toString());
                }
              }
              ArrayMap<String, ? extends Uid.Wakelock> wakelocks2 = u2.getWakelockStats();
              countWakelock = 0;
              long totalWindowWakelock = 0;
              long totalDrawWakelock = 0;
              iw = wakelocks2.size() - 1;
              long totalFullWakelock2 = 0;
              long totalFullWakelock3 = 0;
              while (iw >= 0) {
                Uid.Wakelock wl2 = wakelocks2.valueAt(iw);
                String str129 = str128;
                sb2.setLength(0);
                sb2.append(str125);
                sb2.append("    Wake lock ");
                sb2.append(wakelocks2.keyAt(iw));
                String str130 = str27;
                ArrayMap<String, ? extends Uid.Wakelock> wakelocks3 = wakelocks2;
                String str131 = str34;
                String str132 = str37;
                long totalPartialWakelock = totalFullWakelock3;
                long totalPartialWakelock2 = rawRealtimeMs2;
                Timer bleTimer3 = bleTimer2;
                int iw3 = iw;
                long totalFullWakelock4 = totalFullWakelock2;
                String linePrefix2 =
                    printWakeLock(
                        sb2, wl2.getWakeTime(1), totalPartialWakelock2, "full", which, ": ");
                Timer pTimer = wl2.getWakeTime(0);
                String linePrefix3 =
                    printWakeLock(
                        sb2, pTimer, totalPartialWakelock2, Slice.HINT_PARTIAL, which, linePrefix2);
                long j11 = rawRealtimeMs2;
                printWakeLock(
                    sb2,
                    wl2.getWakeTime(18),
                    j11,
                    "draw",
                    which,
                    printWakeLock(
                        sb2,
                        wl2.getWakeTime(2),
                        j11,
                        Context.WINDOW_SERVICE,
                        which,
                        printWakeLock(
                            sb2,
                            pTimer != null ? pTimer.getSubTimer() : null,
                            j11,
                            "background partial",
                            which,
                            linePrefix3)));
                sb2.append(" realtime");
                printWriter6.println(sb2.toString());
                uidActivity3 = true;
                countWakelock++;
                long rawRealtime23 = rawRealtimeMs2;
                totalFullWakelock2 =
                    totalFullWakelock4 + computeWakeLock(wl2.getWakeTime(1), rawRealtime23, i6);
                long totalPartialWakelock3 =
                    computeWakeLock(wl2.getWakeTime(0), rawRealtime23, i6) + totalPartialWakelock;
                totalWindowWakelock += computeWakeLock(wl2.getWakeTime(2), rawRealtime23, i6);
                rawRealtimeMs2 = rawRealtime23;
                totalDrawWakelock += computeWakeLock(wl2.getWakeTime(18), rawRealtime23, i6);
                iw = iw3 - 1;
                printWriter6 = pw;
                wakelocks2 = wakelocks3;
                bleTimer2 = bleTimer3;
                str27 = str130;
                str37 = str132;
                totalFullWakelock3 = totalPartialWakelock3;
                str128 = str129;
                str34 = str131;
              }
              String str133 = str128;
              String str134 = str37;
              String str135 = str34;
              long totalWindowWakelock2 = totalWindowWakelock;
              str38 = str27;
              long totalPartialWakelock4 = totalFullWakelock3;
              long totalDrawWakelock2 = totalDrawWakelock;
              if (countWakelock > 1) {
                totalFullWakelock = totalFullWakelock2;
                u3 = u2;
                printWriter7 = pw;
              } else {
                if (u2.getAggregatedPartialWakelockTimer() == null) {
                  totalFullWakelock = totalFullWakelock2;
                  u3 = u2;
                  countWakelock2 = countWakelock;
                  rawRealtimeMs6 = 0;
                  actualBgPartialWakelock = 0;
                } else {
                  Timer aggTimer = u2.getAggregatedPartialWakelockTimer();
                  totalFullWakelock = totalFullWakelock2;
                  long rawRealtimeMs10 = rawRealtimeMs;
                  long actualTotalPartialWakelock =
                      aggTimer.getTotalDurationMsLocked(rawRealtimeMs10);
                  Timer bgAggTimer = aggTimer.getSubTimer();
                  if (bgAggTimer == null) {
                    j2 = 0;
                  } else {
                    j2 = bgAggTimer.getTotalDurationMsLocked(rawRealtimeMs10);
                  }
                  long actualBgPartialWakelock2 = j2;
                  rawRealtimeMs = rawRealtimeMs10;
                  u3 = u2;
                  countWakelock2 = countWakelock;
                  rawRealtimeMs6 = actualTotalPartialWakelock;
                  actualBgPartialWakelock = actualBgPartialWakelock2;
                }
                if (rawRealtimeMs6 == 0
                    && actualBgPartialWakelock == 0
                    && totalFullWakelock == 0
                    && totalPartialWakelock4 == 0
                    && totalWindowWakelock2 == 0) {
                  printWriter7 = pw;
                } else {
                  sb2.setLength(0);
                  sb2.append(str125);
                  sb2.append("    TOTAL wake: ");
                  boolean needComma = false;
                  if (totalFullWakelock != 0) {
                    formatTimeMs(sb2, totalFullWakelock);
                    sb2.append("full");
                    needComma = true;
                  }
                  if (totalPartialWakelock4 != 0) {
                    if (needComma) {
                      sb2.append(", ");
                    }
                    needComma = true;
                    formatTimeMs(sb2, totalPartialWakelock4);
                    sb2.append("blamed partial");
                  }
                  if (rawRealtimeMs6 != 0) {
                    if (needComma) {
                      sb2.append(", ");
                    }
                    needComma = true;
                    formatTimeMs(sb2, rawRealtimeMs6);
                    sb2.append("actual partial");
                  }
                  if (actualBgPartialWakelock != 0) {
                    if (needComma) {
                      sb2.append(", ");
                    }
                    needComma = true;
                    formatTimeMs(sb2, actualBgPartialWakelock);
                    sb2.append("actual background partial");
                  }
                  if (totalWindowWakelock2 != 0) {
                    if (needComma) {
                      sb2.append(", ");
                    }
                    needComma = true;
                    formatTimeMs(sb2, totalWindowWakelock2);
                    sb2.append(Context.WINDOW_SERVICE);
                  }
                  if (totalDrawWakelock2 != 0) {
                    if (needComma) {
                      sb2.append(",");
                    }
                    formatTimeMs(sb2, totalDrawWakelock2);
                    sb2.append("draw");
                  }
                  sb2.append(" realtime");
                  printWriter7 = pw;
                  printWriter7.println(sb2.toString());
                }
              }
              mcTimer = u3.getMulticastWakelockStats();
              if (mcTimer != null) {
                str39 = prefix;
                rawRealtime6 = rawRealtimeMs2;
              } else {
                rawRealtime6 = rawRealtimeMs2;
                long multicastWakeLockTimeMicros = mcTimer.getTotalTimeLocked(rawRealtime6, i6);
                int multicastWakeLockCount = mcTimer.getCountLocked(i6);
                if (multicastWakeLockTimeMicros <= 0) {
                  str39 = prefix;
                } else {
                  sb2.setLength(0);
                  str39 = prefix;
                  sb2.append(str39);
                  sb2.append("    WiFi Multicast Wakelock");
                  sb2.append(" count = ");
                  sb2.append(multicastWakeLockCount);
                  sb2.append(" time = ");
                  formatTimeMsNoSpace(sb2, (multicastWakeLockTimeMicros + 500) / 1000);
                  printWriter7.println(sb2.toString());
                }
              }
              ArrayMap<String, ? extends Timer> syncs2 = u3.getSyncStats();
              isy = syncs2.size() - 1;
              while (isy >= 0) {
                Timer timer5 = syncs2.valueAt(isy);
                Uid u8 = u3;
                long totalTime2 = (timer5.getTotalTimeLocked(rawRealtime6, i6) + 500) / 1000;
                long totalDrawWakelock3 = totalDrawWakelock2;
                int count3 = timer5.getCountLocked(i6);
                Timer bgTimer = timer5.getSubTimer();
                if (bgTimer != null) {
                  rawRealtime11 = rawRealtime6;
                  rawRealtime12 = rawRealtimeMs;
                  rawRealtimeMs5 = bgTimer.getTotalDurationMsLocked(rawRealtime12);
                } else {
                  rawRealtime11 = rawRealtime6;
                  rawRealtime12 = rawRealtimeMs;
                  rawRealtimeMs5 = -1;
                }
                long bgTime = rawRealtimeMs5;
                int bgCount2 = bgTimer != null ? bgTimer.getCountLocked(i6) : -1;
                sb2.setLength(0);
                sb2.append(str39);
                sb2.append("    Sync ");
                sb2.append(syncs2.keyAt(isy));
                String str136 = str26;
                sb2.append(str136);
                if (totalTime2 != 0) {
                  formatTimeMs(sb2, totalTime2);
                  syncs = syncs2;
                  sb2.append("realtime (");
                  sb2.append(count3);
                  str57 = str135;
                  sb2.append(str57);
                  mcTimer2 = mcTimer;
                  if (bgTime > 0) {
                    sb2.append(", ");
                    formatTimeMs(sb2, bgTime);
                    sb2.append("background (");
                    sb2.append(bgCount2);
                    sb2.append(str57);
                  }
                } else {
                  syncs = syncs2;
                  str57 = str135;
                  mcTimer2 = mcTimer;
                  sb2.append("(not used)");
                }
                printWriter7.println(sb2.toString());
                uidActivity3 = true;
                isy--;
                u3 = u8;
                rawRealtimeMs = rawRealtime12;
                mcTimer = mcTimer2;
                rawRealtime6 = rawRealtime11;
                str135 = str57;
                syncs2 = syncs;
                str26 = str136;
                totalDrawWakelock2 = totalDrawWakelock3;
              }
              Uid u9 = u3;
              long rawRealtime24 = rawRealtime6;
              String str137 = str26;
              long rawRealtime25 = rawRealtimeMs;
              String str138 = str135;
              ArrayMap<String, ? extends Timer> jobs = u9.getJobStats();
              ij = jobs.size() - 1;
              while (ij >= 0) {
                Timer timer6 = jobs.valueAt(ij);
                long rawRealtime26 = (timer6.getTotalTimeLocked(rawRealtime24, i6) + 500) / 1000;
                int count4 = timer6.getCountLocked(i6);
                Uid u10 = u9;
                Timer bgTimer2 = timer6.getSubTimer();
                long bgTime2 =
                    bgTimer2 != null ? bgTimer2.getTotalDurationMsLocked(rawRealtime25) : -1L;
                int bgCount3 = bgTimer2 != null ? bgTimer2.getCountLocked(i6) : -1;
                sb2.setLength(0);
                sb2.append(str39);
                sb2.append("    Job ");
                sb2.append(jobs.keyAt(ij));
                sb2.append(str137);
                if (rawRealtime26 != 0) {
                  formatTimeMs(sb2, rawRealtime26);
                  sb2.append("realtime (");
                  sb2.append(count4);
                  sb2.append(str138);
                  if (bgTime2 > 0) {
                    sb2.append(", ");
                    formatTimeMs(sb2, bgTime2);
                    sb2.append("background (");
                    sb2.append(bgCount3);
                    sb2.append(str138);
                    str56 = str138;
                  } else {
                    str56 = str138;
                  }
                } else {
                  str56 = str138;
                  sb2.append("(not used)");
                }
                printWriter7.println(sb2.toString());
                uidActivity3 = true;
                ij--;
                u9 = u10;
                str138 = str56;
              }
              String str139 = str138;
              Uid u11 = u9;
              ArrayMap<String, SparseIntArray> completions = u11.getJobCompletionStats();
              ic = completions.size() - 1;
              while (ic >= 0) {
                SparseIntArray types = completions.valueAt(ic);
                if (types == null) {
                  str54 = str126;
                  str55 = str134;
                } else {
                  pw.print(prefix);
                  printWriter7.print("    Job Completions ");
                  printWriter7.print(completions.keyAt(ic));
                  printWriter7.print(":");
                  for (int it3 = 0; it3 < types.size(); it3++) {
                    printWriter7.print(str134);
                    printWriter7.print(
                        JobParameters.getInternalReasonCodeDescription(types.keyAt(it3)));
                    printWriter7.print(str126);
                    printWriter7.print(types.valueAt(it3));
                    printWriter7.print("x)");
                  }
                  str54 = str126;
                  str55 = str134;
                  pw.println();
                }
                ic--;
                str134 = str55;
                str126 = str54;
              }
              String str140 = str126;
              String str141 = str134;
              u4 = u11;
              u4.getDeferredJobsLineLocked(sb2, i6);
              if (sb2.length() > 0) {
                printWriter7.print("    Jobs deferred on launch ");
                printWriter7.println(sb2.toString());
              }
              StringBuilder sb6 = sb2;
              String str142 = str141;
              mobileActiveTime2 = mobileActiveTime5;
              str40 = str140;
              int i28 = 0;
              long rawRealtime27 = rawRealtime24;
              long rawRealtimeMs11 = rawRealtime25;
              uidStats = uidStats7;
              NU2 = NU6;
              iu = iu4;
              String str143 = ", ";
              cpuFreqs = cpuFreqs3;
              boolean uidActivity4 =
                  uidActivity3
                      | printTimer(
                          pw,
                          sb6,
                          u4.getFlashlightTurnedOnTimer(),
                          rawRealtime27,
                          which,
                          prefix,
                          CoreSaConstant.VALUE_FLASHLIGHT)
                      | printTimer(
                          pw,
                          sb6,
                          u4.getCameraTurnedOnTimer(),
                          rawRealtime27,
                          which,
                          prefix,
                          CoreSaConstant.VALUE_CAMERA)
                      | printTimer(
                          pw,
                          sb6,
                          u4.getVideoTurnedOnTimer(),
                          rawRealtime27,
                          which,
                          prefix,
                          "Video")
                      | printTimer(
                          pw,
                          sb6,
                          u4.getAudioTurnedOnTimer(),
                          rawRealtime27,
                          which,
                          prefix,
                          "Audio");
              SparseArray<? extends Uid.Sensor> sensors2 = u4.getSensorStats();
              NSE = sensors2.size();
              ise = 0;
              while (ise < NSE) {
                Uid.Sensor se = sensors2.valueAt(ise);
                sensors2.keyAt(ise);
                sb2.setLength(i28);
                sb2.append(prefix);
                sb2.append("    Sensor ");
                int handle = se.getHandle();
                if (handle == -10000) {
                  sb2.append("GPS");
                } else if (handle == -10001) {
                  sb2.append("actualGPS");
                } else {
                  sb2.append(handle);
                }
                sb2.append(str137);
                Timer timer7 = se.getSensorTime();
                if (timer7 != null) {
                  long j12 = rawRealtime27;
                  str52 = str137;
                  str53 = str142;
                  rawRealtime10 = j12;
                  long totalTime3 = (timer7.getTotalTimeLocked(rawRealtime10, which) + 500) / 1000;
                  int count5 = timer7.getCountLocked(which);
                  NSE2 = NSE;
                  Timer bgTimer3 = se.getSensorBackgroundTime();
                  int bgCount4 = bgTimer3 != null ? bgTimer3.getCountLocked(which) : 0;
                  long actualTime = timer7.getTotalDurationMsLocked(rawRealtimeMs11);
                  if (bgTimer3 == null) {
                    j = 0;
                  } else {
                    j = bgTimer3.getTotalDurationMsLocked(rawRealtimeMs11);
                  }
                  long bgActualTime = j;
                  if (totalTime3 != 0) {
                    if (actualTime == totalTime3) {
                      rawRealtimeMs4 = rawRealtimeMs11;
                    } else {
                      formatTimeMs(sb2, totalTime3);
                      rawRealtimeMs4 = rawRealtimeMs11;
                      sb2.append("blamed realtime, ");
                    }
                    formatTimeMs(sb2, actualTime);
                    sb2.append("realtime (");
                    sb2.append(count5);
                    str50 = str139;
                    sb2.append(str50);
                    if (bgActualTime == 0) {
                      bgCount = bgCount4;
                      if (bgCount <= 0) {
                        sensors = sensors2;
                        str51 = str143;
                      }
                    } else {
                      bgCount = bgCount4;
                    }
                    sensors = sensors2;
                    str51 = str143;
                    sb2.append(str51);
                    formatTimeMs(sb2, bgActualTime);
                    sb2.append("background (");
                    sb2.append(bgCount);
                    sb2.append(str50);
                  } else {
                    rawRealtimeMs4 = rawRealtimeMs11;
                    str50 = str139;
                    sensors = sensors2;
                    str51 = str143;
                    sb2.append("(not used)");
                  }
                } else {
                  rawRealtimeMs4 = rawRealtimeMs11;
                  NSE2 = NSE;
                  str50 = str139;
                  sensors = sensors2;
                  str51 = str143;
                  long j13 = rawRealtime27;
                  str52 = str137;
                  str53 = str142;
                  rawRealtime10 = j13;
                  sb2.append("(not used)");
                }
                pw.println(sb2.toString());
                ise++;
                uidActivity4 = true;
                str143 = str51;
                NSE = NSE2;
                sensors2 = sensors;
                i28 = 0;
                str139 = str50;
                rawRealtimeMs11 = rawRealtimeMs4;
                long j14 = rawRealtime10;
                str137 = str52;
                str142 = str53;
                rawRealtime27 = j14;
              }
              rawRealtimeMs3 = rawRealtimeMs11;
              boolean uidActivity5 = uidActivity4;
              printWriter8 = pw;
              long j15 = rawRealtime27;
              String str144 = str137;
              String str145 = str142;
              long rawRealtime28 = j15;
              StringBuilder sb7 = sb2;
              String str146 = str143;
              boolean uidActivity6 =
                  uidActivity5
                      | printTimer(
                          pw,
                          sb7,
                          u4.getVibratorOnTimer(),
                          rawRealtime28,
                          which,
                          prefix,
                          "Vibrator")
                      | printTimer(
                          pw,
                          sb7,
                          u4.getForegroundActivityTimer(),
                          rawRealtime28,
                          which,
                          prefix,
                          "Foreground activities")
                      | printTimer(
                          pw,
                          sb7,
                          u4.getForegroundServiceTimer(),
                          rawRealtime28,
                          which,
                          prefix,
                          "Foreground services");
              int i29 = which;
              displayTime = u4.getDisplayTime(i29);
              if (displayTime > 0) {
                long displayPowerDrain = u4.getDisplayPowerDrain(i29);
                sb2.setLength(0);
                sb2.append(prefix);
                sb2.append("    ");
                sb2.append("Screen discharge: ");
                sb2.append(formatCharge(displayPowerDrain));
                sb2.append("uA for: ");
                formatTimeMs(sb2, displayTime);
                printWriter8.println(sb2.toString());
              }
              if (u4.hasSpeakerActivity()) {
                uidActivity = uidActivity6;
                str41 = str145;
              } else {
                sb2.setLength(0);
                sb2.append(prefix);
                sb2.append("    Total speaker time per level:");
                boolean hasData2 = false;
                int i30 = 0;
                while (i30 < 16) {
                  long time12 = u4.getSpeakerMediaTime(i30, i29) / 1000;
                  if (time12 == 0) {
                    uidActivity2 = uidActivity6;
                    displayTime2 = displayTime;
                    str49 = str145;
                  } else {
                    hasData2 = true;
                    uidActivity2 = uidActivity6;
                    str49 = str145;
                    displayTime2 = displayTime;
                    sb2.append(str49 + i30 + ":" + time12);
                  }
                  i30++;
                  str145 = str49;
                  uidActivity6 = uidActivity2;
                  displayTime = displayTime2;
                }
                uidActivity = uidActivity6;
                str41 = str145;
                if (hasData2) {
                  printWriter8.println(sb2.toString());
                }
              }
              totalStateTime = 0;
              ips = 0;
              while (ips < 7) {
                long time13 = u4.getProcessStateTime(ips, rawRealtime28, i29);
                if (time13 <= 0) {
                  rawRealtime8 = rawRealtime28;
                  numDisplays4 = numDisplays2;
                  str47 = str144;
                } else {
                  long totalStateTime3 = totalStateTime + time13;
                  sb2.setLength(0);
                  sb2.append(prefix);
                  sb2.append("    ");
                  sb2.append(Uid.PROCESS_STATE_NAMES[ips]);
                  sb2.append(" for: ");
                  formatTimeMs(sb2, (time13 + 500) / 1000);
                  printWriter8.println(sb2.toString());
                  if (ips == 0) {
                    totalStateTime2 = totalStateTime3;
                    numDisplays4 = numDisplays2;
                    if (numDisplays4 <= 1) {
                      rawRealtime8 = rawRealtime28;
                      str47 = str144;
                    } else {
                      int display2 = 0;
                      while (display2 < numDisplays4) {
                        long displayTopTime =
                            u4.getDisplayTopActivityTime(display2, rawRealtime28, i29);
                        if (displayTopTime <= 0) {
                          rawRealtime9 = rawRealtime28;
                          time = time13;
                          str48 = str144;
                        } else {
                          sb2.setLength(0);
                          sb2.append(prefix);
                          rawRealtime9 = rawRealtime28;
                          str48 = str144;
                          sb2.append("      Display #" + display2 + str48);
                          time = time13;
                          formatTimeMs(sb2, (displayTopTime + 500) / 1000);
                          printWriter8.println(sb2.toString());
                        }
                        display2++;
                        str144 = str48;
                        rawRealtime28 = rawRealtime9;
                        time13 = time;
                      }
                      rawRealtime8 = rawRealtime28;
                      str47 = str144;
                    }
                  } else {
                    rawRealtime8 = rawRealtime28;
                    totalStateTime2 = totalStateTime3;
                    numDisplays4 = numDisplays2;
                    str47 = str144;
                  }
                  uidActivity = true;
                  totalStateTime = totalStateTime2;
                }
                ips++;
                str144 = str47;
                numDisplays2 = numDisplays4;
                rawRealtime28 = rawRealtime8;
              }
              rawRealtime7 = rawRealtime28;
              int numDisplays7 = numDisplays2;
              String str147 = str144;
              if (totalStateTime > 0) {
                sb2.setLength(0);
                sb2.append(prefix);
                sb2.append("    Total running: ");
                formatTimeMs(sb2, (totalStateTime + 500) / 1000);
                printWriter8.println(sb2.toString());
              }
              userCpuTimeUs = u4.getUserCpuTimeUs(i29);
              systemCpuTimeUs = u4.getSystemCpuTimeUs(i29);
              if (userCpuTimeUs <= 0 || systemCpuTimeUs > 0) {
                sb2.setLength(0);
                sb2.append(prefix);
                sb2.append("    Total cpu time: u=");
                long totalStateTime4 = userCpuTimeUs / 1000;
                formatTimeMs(sb2, totalStateTime4);
                sb2.append("s=");
                formatTimeMs(sb2, systemCpuTimeUs / 1000);
                printWriter8.println(sb2.toString());
              }
              cpuFreqTimes = u4.getCpuFreqTimes(i29);
              if (cpuFreqTimes == null) {
                sb2.setLength(0);
                sb2.append("    Total cpu time per freq:");
                int i31 = 0;
                while (i31 < cpuFreqTimes.length) {
                  sb2.append(' ').append(cpuFreqTimes[i31]);
                  i31++;
                  userCpuTimeUs = userCpuTimeUs;
                }
                printWriter8.println(sb2.toString());
              }
              screenOffCpuFreqTimes = u4.getScreenOffCpuFreqTimes(i29);
              if (screenOffCpuFreqTimes == null) {
                sb2.setLength(0);
                sb2.append("    Total screen-off cpu time per freq:");
                int i32 = 0;
                while (i32 < screenOffCpuFreqTimes.length) {
                  sb2.append(' ').append(screenOffCpuFreqTimes[i32]);
                  i32++;
                  systemCpuTimeUs = systemCpuTimeUs;
                }
                printWriter8.println(sb2.toString());
              }
              long[] timesInFreqMs = new long[getCpuFreqCount()];
              procState = 0;
              while (procState < 7) {
                if (!u4.getCpuFreqTimes(timesInFreqMs, procState)) {
                  numDisplays3 = numDisplays7;
                } else {
                  sb2.setLength(0);
                  sb2.append("    Cpu times per freq at state ")
                      .append(Uid.PROCESS_STATE_NAMES[procState])
                      .append(ShortcutConstants.SERVICES_SEPARATOR);
                  int i33 = 0;
                  while (i33 < timesInFreqMs.length) {
                    sb2.append(str41).append(timesInFreqMs[i33]);
                    i33++;
                    numDisplays7 = numDisplays7;
                  }
                  numDisplays3 = numDisplays7;
                  printWriter8.println(sb2.toString());
                }
                if (u4.getScreenOffCpuFreqTimes(timesInFreqMs, procState)) {
                  sb2.setLength(0);
                  sb2.append("   Screen-off cpu times per freq at state ")
                      .append(Uid.PROCESS_STATE_NAMES[procState])
                      .append(ShortcutConstants.SERVICES_SEPARATOR);
                  for (long j16 : timesInFreqMs) {
                    sb2.append(str41).append(j16);
                  }
                  printWriter8.println(sb2.toString());
                }
                procState++;
                numDisplays7 = numDisplays3;
              }
              numDisplays2 = numDisplays7;
              ArrayMap<String, ? extends Uid.Proc> processStats = u4.getProcessStats();
              ipr = processStats.size() - 1;
              while (ipr >= 0) {
                Uid.Proc ps2 = processStats.valueAt(ipr);
                long userTime = ps2.getUserTime(i29);
                long[] screenOffCpuFreqTimes2 = screenOffCpuFreqTimes;
                long[] timesInFreqMs2 = timesInFreqMs;
                long systemTime2 = ps2.getSystemTime(i29);
                long[] cpuFreqTimes2 = cpuFreqTimes;
                String str148 = str147;
                long foregroundTime = ps2.getForegroundTime(i29);
                String str149 = str41;
                int starts = ps2.getStarts(i29);
                Uid u12 = u4;
                int numCrashes = ps2.getNumCrashes(i29);
                int numAnrs2 = ps2.getNumAnrs(i29);
                int numExcessive3 = i29 == 0 ? ps2.countExcessivePowers() : 0;
                if (userTime == 0 && systemTime2 == 0 && foregroundTime == 0 && starts == 0) {
                  numExcessive = numExcessive3;
                  if (numExcessive == 0 && numCrashes == 0 && numAnrs2 == 0) {
                    printWriter10 = pw;
                    str45 = str146;
                    str44 = str133;
                    ipr--;
                    i29 = which;
                    str146 = str45;
                    printWriter8 = printWriter10;
                    str133 = str44;
                    screenOffCpuFreqTimes = screenOffCpuFreqTimes2;
                    timesInFreqMs = timesInFreqMs2;
                    str147 = str148;
                    cpuFreqTimes = cpuFreqTimes2;
                    str41 = str149;
                    u4 = u12;
                  }
                } else {
                  numExcessive = numExcessive3;
                }
                Uid.Proc ps3 = ps2;
                sb2.setLength(0);
                sb2.append(prefix);
                sb2.append("    Proc ");
                sb2.append(processStats.keyAt(ipr));
                sb2.append(":\n");
                sb2.append(prefix);
                sb2.append("      CPU: ");
                formatTimeMs(sb2, userTime);
                sb2.append("usr + ");
                formatTimeMs(sb2, systemTime2);
                sb2.append("krn ; ");
                formatTimeMs(sb2, foregroundTime);
                sb2.append(FOREGROUND_ACTIVITY_DATA);
                if (starts == 0 && numCrashes == 0 && numAnrs2 == 0) {
                  str44 = str133;
                } else {
                  str44 = str133;
                  sb2.append(str44);
                  sb2.append(prefix);
                  sb2.append("      ");
                  boolean hasOne = false;
                  if (starts != 0) {
                    hasOne = true;
                    sb2.append(starts);
                    sb2.append(" starts");
                  }
                  if (numCrashes != 0) {
                    if (hasOne) {
                      sb2.append(str146);
                    }
                    hasOne = true;
                    sb2.append(numCrashes);
                    sb2.append(" crashes");
                  }
                  if (numAnrs2 != 0) {
                    if (hasOne) {
                      sb2.append(str146);
                    }
                    sb2.append(numAnrs2);
                    sb2.append(" anrs");
                  }
                }
                printWriter10 = pw;
                printWriter10.println(sb2.toString());
                int e = 0;
                while (e < numExcessive) {
                  int starts2 = starts;
                  Uid.Proc ps4 = ps3;
                  int numCrashes2 = numCrashes;
                  Uid.Proc.ExcessivePower ew = ps4.getExcessivePower(e);
                  if (ew == null) {
                    ps = ps4;
                    numAnrs = numAnrs2;
                    str46 = str146;
                    systemTime = systemTime2;
                    numExcessive2 = numExcessive;
                  } else {
                    pw.print(prefix);
                    ps = ps4;
                    printWriter10.print("      * Killed for ");
                    numAnrs = numAnrs2;
                    if (ew.type == 2) {
                      printWriter10.print(CPU_DATA);
                    } else {
                      printWriter10.print("unknown");
                    }
                    printWriter10.print(" use: ");
                    str46 = str146;
                    TimeUtils.formatDuration(ew.usedTime, printWriter10);
                    printWriter10.print(" over ");
                    TimeUtils.formatDuration(ew.overTime, printWriter10);
                    if (ew.overTime == 0) {
                      systemTime = systemTime2;
                      numExcessive2 = numExcessive;
                    } else {
                      printWriter10.print(" (");
                      systemTime = systemTime2;
                      numExcessive2 = numExcessive;
                      printWriter10.print((ew.usedTime * 100) / ew.overTime);
                      printWriter10.println("%)");
                    }
                  }
                  e++;
                  str146 = str46;
                  numExcessive = numExcessive2;
                  starts = starts2;
                  numCrashes = numCrashes2;
                  ps3 = ps;
                  numAnrs2 = numAnrs;
                  systemTime2 = systemTime;
                }
                str45 = str146;
                uidActivity = true;
                ipr--;
                i29 = which;
                str146 = str45;
                printWriter8 = printWriter10;
                str133 = str44;
                screenOffCpuFreqTimes = screenOffCpuFreqTimes2;
                timesInFreqMs = timesInFreqMs2;
                str147 = str148;
                cpuFreqTimes = cpuFreqTimes2;
                str41 = str149;
                u4 = u12;
              }
              str42 = str41;
              String str150 = str147;
              printWriter9 = printWriter8;
              ArrayMap<String, ? extends Uid.Pkg> packageStats = u4.getPackageStats();
              ipkg = packageStats.size() - 1;
              while (ipkg >= 0) {
                pw.print(prefix);
                printWriter9.print("    Apk ");
                printWriter9.print(packageStats.keyAt(ipkg));
                printWriter9.println(":");
                boolean apkActivity = false;
                Uid.Pkg ps5 = packageStats.valueAt(ipkg);
                ArrayMap<String, ? extends Counter> alarms = ps5.getWakeupAlarmStats();
                for (int iwa = alarms.size() - 1; iwa >= 0; iwa--) {
                  pw.print(prefix);
                  printWriter9.print("      Wakeup alarm ");
                  printWriter9.print(alarms.keyAt(iwa));
                  printWriter9.print(str150);
                  printWriter9.print(alarms.valueAt(iwa).getCountLocked(which));
                  printWriter9.println(" times");
                  apkActivity = true;
                }
                String str151 = str150;
                ArrayMap<String, ? extends Uid.Pkg.Serv> serviceStats = ps5.getServiceStats();
                int isvc = serviceStats.size() - 1;
                while (isvc >= 0) {
                  Uid.Pkg.Serv ss = serviceStats.valueAt(isvc);
                  Uid.Pkg ps6 = ps5;
                  ArrayMap<String, ? extends Counter> alarms2 = alarms;
                  long batteryUptime5 = batteryUptime4;
                  long startTime = ss.getStartTime(batteryUptime5, which);
                  int starts3 = ss.getStarts(which);
                  ArrayMap<String, ? extends Uid.Pkg> packageStats2 = packageStats;
                  int launches = ss.getLaunches(which);
                  if (startTime == 0 && starts3 == 0 && launches == 0) {
                    batteryUptime2 = batteryUptime5;
                  } else {
                    batteryUptime2 = batteryUptime5;
                    sb2.setLength(0);
                    sb2.append(prefix);
                    sb2.append("      Service ");
                    sb2.append(serviceStats.keyAt(isvc));
                    sb2.append(":\n");
                    sb2.append(prefix);
                    sb2.append("        Created for: ");
                    formatTimeMs(sb2, startTime / 1000);
                    sb2.append("uptime\n");
                    sb2.append(prefix);
                    sb2.append("        Starts: ");
                    sb2.append(starts3);
                    sb2.append(", launches: ");
                    sb2.append(launches);
                    printWriter9.println(sb2.toString());
                    apkActivity = true;
                  }
                  isvc--;
                  ps5 = ps6;
                  alarms = alarms2;
                  packageStats = packageStats2;
                  batteryUptime4 = batteryUptime2;
                }
                ArrayMap<String, ? extends Uid.Pkg> packageStats3 = packageStats;
                long batteryUptime6 = batteryUptime4;
                if (!apkActivity) {
                  pw.print(prefix);
                  printWriter9.println("      (nothing executed)");
                }
                uidActivity = true;
                ipkg--;
                packageStats = packageStats3;
                batteryUptime4 = batteryUptime6;
                str150 = str151;
              }
              i7 = which;
              batteryUptime = batteryUptime4;
              str43 = str150;
              if (uidActivity) {
                pw.print(prefix);
                printWriter9.println("    (nothing executed)");
              }
            }
          }
          pw.print(prefix);
          printWriter11.print("    Wi-Fi network: ");
          wifiRxBytes = wifiRxBytes3;
          printWriter11.print(formatBytesLocked(wifiRxBytes));
          printWriter11.print(" received, ");
          wifiTxBytes = wifiTxBytes3;
          printWriter11.print(formatBytesLocked(wifiTxBytes));
          printWriter11.print(" sent (packets ");
          printWriter11.print(wifiRxPackets2);
          printWriter11.print(" received, ");
          wifiRxPackets3 = wifiTxPackets3;
          printWriter11.print(wifiRxPackets3);
          printWriter11.println(" sent)");
          if (fullWifiLockOnTime != 0) {}
          sb2.setLength(i26);
          sb2.append(str125);
          sb2.append("    Wifi Running: ");
          formatTimeMs(sb2, uidWifiRunningTime / 1000);
          sb2.append(str126);
          wifiRxBytes2 = wifiRxBytes;
          wifiTxPackets = wifiRxPackets3;
          long wifiTxPackets42 = whichBatteryRealtime6;
          sb2.append(formatRatioLocked(uidWifiRunningTime, wifiTxPackets42));
          sb2.append(")\n");
          sb2.append(str125);
          sb2.append("    Full Wifi Lock: ");
          whichBatteryRealtime7 = wifiTxBytes;
          formatTimeMs(sb2, fullWifiLockOnTime / 1000);
          sb2.append(str126);
          sb2.append(formatRatioLocked(fullWifiLockOnTime, wifiTxPackets42));
          sb2.append(")\n");
          sb2.append(str125);
          sb2.append("    Wifi Scan (blamed): ");
          formatTimeMs(sb2, wifiScanTime / 1000);
          sb2.append(str126);
          sb2.append(formatRatioLocked(wifiScanTime, wifiTxPackets42));
          sb2.append(str32);
          sb2.append(wifiScanCount);
          sb2.append("x\n");
          sb2.append(str125);
          sb2.append("    Wifi Scan (actual): ");
          formatTimeMs(sb2, wifiScanActualTime / 1000);
          sb2.append(str126);
          wifiTxBytes2 = rawRealtime4;
          rawRealtime5 = wifiTxPackets42;
          wifiTxPackets2 = wifiScanActualTime;
          sb2.append(formatRatioLocked(wifiTxPackets2, computeBatteryRealtime(wifiTxBytes2, 0)));
          sb2.append(str32);
          sb2.append(wifiScanCount);
          sb2.append("x\n");
          sb2.append(str125);
          sb2.append("    Background Wifi Scan: ");
          formatTimeMs(sb2, wifiScanActualTimeBg2 / 1000);
          sb2.append(str126);
          wifiScanActualTimeBg = wifiScanActualTimeBg2;
          sb2.append(
              formatRatioLocked(wifiScanActualTimeBg, computeBatteryRealtime(wifiTxBytes2, 0)));
          sb2.append(str32);
          wifiScanCountBg = wifiScanCountBg2;
          sb2.append(wifiScanCountBg);
          sb2.append(str127);
          printWriter4 = pw;
          printWriter4.println(sb2.toString());
          if (wifiWakeup3 > 0) {}
          long rawRealtime222 = wifiTxBytes2;
          printWriter5 = printWriter4;
          whichBatteryRealtime8 = rawRealtime5;
          printControllerActivityIfInteresting(
              pw, sb2, str125 + "  ", WIFI_CONTROLLER_NAME, u.getWifiControllerActivity(), which);
          if (btRxBytes2 > 0) {}
          pw.print(prefix);
          printWriter5.print("    Bluetooth network: ");
          btRxBytes = btRxBytes2;
          printWriter5.print(formatBytesLocked(btRxBytes));
          printWriter5.print(" received, ");
          btTxBytes = btTxBytes2;
          printWriter5.print(formatBytesLocked(btTxBytes));
          printWriter5.println(" sent");
          bleTimer = u.getBluetoothScanTimer();
          String str1282 = "\n";
          if (bleTimer == null) {}
          if (u.hasUserActivity()) {}
          ArrayMap<String, ? extends Uid.Wakelock> wakelocks22 = u2.getWakelockStats();
          countWakelock = 0;
          long totalWindowWakelock3 = 0;
          long totalDrawWakelock4 = 0;
          iw = wakelocks22.size() - 1;
          long totalFullWakelock22 = 0;
          long totalFullWakelock32 = 0;
          while (iw >= 0) {}
          String str1332 = str1282;
          String str1342 = str37;
          String str1352 = str34;
          long totalWindowWakelock22 = totalWindowWakelock3;
          str38 = str27;
          long totalPartialWakelock42 = totalFullWakelock32;
          long totalDrawWakelock22 = totalDrawWakelock4;
          if (countWakelock > 1) {}
          mcTimer = u3.getMulticastWakelockStats();
          if (mcTimer != null) {}
          ArrayMap<String, ? extends Timer> syncs22 = u3.getSyncStats();
          isy = syncs22.size() - 1;
          while (isy >= 0) {}
          Uid u92 = u3;
          long rawRealtime242 = rawRealtime6;
          String str1372 = str26;
          long rawRealtime252 = rawRealtimeMs;
          String str1382 = str1352;
          ArrayMap<String, ? extends Timer> jobs2 = u92.getJobStats();
          ij = jobs2.size() - 1;
          while (ij >= 0) {}
          String str1392 = str1382;
          Uid u112 = u92;
          ArrayMap<String, SparseIntArray> completions2 = u112.getJobCompletionStats();
          ic = completions2.size() - 1;
          while (ic >= 0) {}
          String str1402 = str126;
          String str1412 = str1342;
          u4 = u112;
          u4.getDeferredJobsLineLocked(sb2, i6);
          if (sb2.length() > 0) {}
          StringBuilder sb62 = sb2;
          String str1422 = str1412;
          mobileActiveTime2 = mobileActiveTime5;
          str40 = str1402;
          int i282 = 0;
          long rawRealtime272 = rawRealtime242;
          long rawRealtimeMs112 = rawRealtime252;
          uidStats = uidStats7;
          NU2 = NU6;
          iu = iu4;
          String str1432 = ", ";
          cpuFreqs = cpuFreqs3;
          boolean uidActivity42 =
              uidActivity3
                  | printTimer(
                      pw,
                      sb62,
                      u4.getFlashlightTurnedOnTimer(),
                      rawRealtime272,
                      which,
                      prefix,
                      CoreSaConstant.VALUE_FLASHLIGHT)
                  | printTimer(
                      pw,
                      sb62,
                      u4.getCameraTurnedOnTimer(),
                      rawRealtime272,
                      which,
                      prefix,
                      CoreSaConstant.VALUE_CAMERA)
                  | printTimer(
                      pw, sb62, u4.getVideoTurnedOnTimer(), rawRealtime272, which, prefix, "Video")
                  | printTimer(
                      pw, sb62, u4.getAudioTurnedOnTimer(), rawRealtime272, which, prefix, "Audio");
          SparseArray<? extends Uid.Sensor> sensors22 = u4.getSensorStats();
          NSE = sensors22.size();
          ise = 0;
          while (ise < NSE) {}
          rawRealtimeMs3 = rawRealtimeMs112;
          boolean uidActivity52 = uidActivity42;
          printWriter8 = pw;
          long j152 = rawRealtime272;
          String str1442 = str1372;
          String str1452 = str1422;
          long rawRealtime282 = j152;
          StringBuilder sb72 = sb2;
          String str1462 = str1432;
          boolean uidActivity62 =
              uidActivity52
                  | printTimer(
                      pw, sb72, u4.getVibratorOnTimer(), rawRealtime282, which, prefix, "Vibrator")
                  | printTimer(
                      pw,
                      sb72,
                      u4.getForegroundActivityTimer(),
                      rawRealtime282,
                      which,
                      prefix,
                      "Foreground activities")
                  | printTimer(
                      pw,
                      sb72,
                      u4.getForegroundServiceTimer(),
                      rawRealtime282,
                      which,
                      prefix,
                      "Foreground services");
          int i292 = which;
          displayTime = u4.getDisplayTime(i292);
          if (displayTime > 0) {}
          if (u4.hasSpeakerActivity()) {}
          totalStateTime = 0;
          ips = 0;
          while (ips < 7) {}
          rawRealtime7 = rawRealtime282;
          int numDisplays72 = numDisplays2;
          String str1472 = str1442;
          if (totalStateTime > 0) {}
          userCpuTimeUs = u4.getUserCpuTimeUs(i292);
          systemCpuTimeUs = u4.getSystemCpuTimeUs(i292);
          if (userCpuTimeUs <= 0) {}
          sb2.setLength(0);
          sb2.append(prefix);
          sb2.append("    Total cpu time: u=");
          long totalStateTime42 = userCpuTimeUs / 1000;
          formatTimeMs(sb2, totalStateTime42);
          sb2.append("s=");
          formatTimeMs(sb2, systemCpuTimeUs / 1000);
          printWriter8.println(sb2.toString());
          cpuFreqTimes = u4.getCpuFreqTimes(i292);
          if (cpuFreqTimes == null) {}
          screenOffCpuFreqTimes = u4.getScreenOffCpuFreqTimes(i292);
          if (screenOffCpuFreqTimes == null) {}
          long[] timesInFreqMs3 = new long[getCpuFreqCount()];
          procState = 0;
          while (procState < 7) {}
          numDisplays2 = numDisplays72;
          ArrayMap<String, ? extends Uid.Proc> processStats2 = u4.getProcessStats();
          ipr = processStats2.size() - 1;
          while (ipr >= 0) {}
          str42 = str41;
          String str1502 = str1472;
          printWriter9 = printWriter8;
          ArrayMap<String, ? extends Uid.Pkg> packageStats4 = u4.getPackageStats();
          ipkg = packageStats4.size() - 1;
          while (ipkg >= 0) {}
          i7 = which;
          batteryUptime = batteryUptime4;
          str43 = str1502;
          if (uidActivity) {}
        }
        iu3 = iu + 1;
        printWriter2 = printWriter9;
        sb = sb2;
        str26 = str43;
        i4 = i7;
        cpuFreqs2 = cpuFreqs;
        NU5 = NU2;
        str30 = str40;
        str28 = str35;
        whichBatteryRealtime6 = whichBatteryRealtime8;
        uidStats6 = uidStats;
        str31 = str36;
        mobileActiveTime4 = mobileActiveTime2;
        str27 = str38;
        rawRealtimeMs8 = rawRealtimeMs3;
        mobileTxTotalPackets = rawRealtime7;
        str29 = str42;
        batteryUptime4 = batteryUptime;
        i3 = 0;
      } else {
        return;
      }
    }
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
          sb.append((bd.mask & newval) != 0 ? "+" : NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
          sb.append(longNames ? bd.name : bd.shortName);
          if (bd.mask == 1073741824 && wakelockTag != null) {
            didWake = true;
            sb.append("=");
            if (longNames) {
              UserHandle.formatUid(sb, wakelockTag.uid);
              sb.append(":\"");
              sb.append(wakelockTag.string);
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
      if (longNames) {
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

    private String printNextItem(HistoryItem rec, long baseTime, boolean checkin, boolean verbose) {
      StringBuilder item = new StringBuilder();
      if (rec.cpuUsageDetails != null
          && rec.cpuUsageDetails.cpuBracketDescriptions != null
          && checkin) {
        String[] descriptions = rec.cpuUsageDetails.cpuBracketDescriptions;
        for (int bracket = 0; bracket < descriptions.length; bracket++) {
          item.append(9);
          item.append(',');
          item.append(BatteryStats.HISTORY_DATA);
          item.append(",0,XB,");
          item.append(descriptions.length);
          item.append(',');
          item.append(bracket);
          item.append(',');
          item.append(descriptions[bracket]);
          item.append("\n");
        }
      }
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
      } else if (rec.cmd == 5 || rec.cmd == 7) {
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
          int i = this.oldStatus;
          switch (i) {
            case 1:
              item.append(checkin ? "?" : "unknown");
              break;
            case 2:
              item.append(checkin ? "c" : UsbManager.USB_FUNCTION_CHARGING);
              break;
            case 3:
              item.append(checkin ? "d" : "discharging");
              break;
            case 4:
              item.append(checkin ? "n" : "not-charging");
              break;
            case 5:
              item.append(checkin ? FullBackup.FILES_TREE_TOKEN : "full");
              break;
            default:
              item.append(i);
              break;
          }
        }
        if (this.oldHealth != rec.batteryHealth) {
          this.oldHealth = rec.batteryHealth;
          item.append(checkin ? ",Bh=" : " health=");
          int i2 = this.oldHealth;
          switch (i2) {
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
              item.append(checkin ? "d" : "dead");
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
              item.append(i2);
              break;
          }
        }
        if (this.oldPlug != rec.batteryPlugType) {
          this.oldPlug = rec.batteryPlugType;
          item.append(checkin ? ",Bp=" : " plug=");
          int i3 = this.oldPlug;
          switch (i3) {
            case 0:
              item.append(checkin ? "n" : "none");
              break;
            case 1:
              item.append(checkin ? "a" : "ac");
              break;
            case 2:
              item.append(checkin ? XmlTags.ATTR_UID : "usb");
              break;
            case 3:
            default:
              item.append(i3);
              break;
            case 4:
              item.append(checkin ? "w" : AudioDeviceDescription.CONNECTION_WIRELESS);
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
            item.append(String.format("0x%x", Integer.valueOf(this.oldSecTxShareEvent)));
            item.append(" online=");
            item.append(this.oldSecOnline);
            item.append(" current_event=");
            item.append(String.format("0x%x", Integer.valueOf(this.oldSecCurrentEvent)));
            item.append(" misc_event=");
            item.append(String.format("0x%x", Integer.valueOf(this.oldSecEvent)));
          }
        }
        if (!checkin && this.oldProtectBatteryMode != rec.protectBatteryMode) {
          int i4 = rec.protectBatteryMode;
          this.oldProtectBatteryMode = i4;
          if (i4 >= 0 && i4 < BatteryStats.PROTECT_BATTERY_MODE_TYPES.length) {
            item.append(" pbm=");
            item.append(BatteryStats.PROTECT_BATTERY_MODE_TYPES[this.oldProtectBatteryMode]);
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
            item.append(rec.wakeReasonTag.poolIdx);
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
              checkin ? BatteryStats.HISTORY_EVENT_CHECKIN_NAMES : BatteryStats.HISTORY_EVENT_NAMES;
          int idx = rec.eventCode & HistoryItem.EVENT_TYPE_MASK;
          if (idx >= 0 && idx < eventNames.length) {
            item.append(eventNames[idx]);
          } else {
            item.append(checkin ? "Ev" : "event");
            item.append(idx);
          }
          item.append("=");
          if (checkin) {
            item.append(rec.eventTag.poolIdx);
          } else {
            item.append(
                BatteryStats.HISTORY_EVENT_INT_FORMATTERS[idx].applyAsString(rec.eventTag.uid));
            item.append(":\"");
            item.append(rec.eventTag.string);
            item.append("\"");
          }
        }
        boolean firstExtension = true;
        if (rec.energyConsumerDetails != null) {
          firstExtension = false;
          if (!checkin) {
            item.append(" ext=energy:");
            item.append(rec.energyConsumerDetails);
          } else {
            item.append(",XE");
            for (int i5 = 0; i5 < rec.energyConsumerDetails.consumers.length; i5++) {
              if (rec.energyConsumerDetails.chargeUC[i5] != -1) {
                item.append(',');
                item.append(rec.energyConsumerDetails.consumers[i5].name);
                item.append('=');
                item.append(rec.energyConsumerDetails.chargeUC[i5]);
              }
            }
          }
        }
        if (rec.cpuUsageDetails != null) {
          if (!checkin) {
            if (!firstExtension) {
              item.append("\n                ");
            }
            String[] descriptions2 = rec.cpuUsageDetails.cpuBracketDescriptions;
            if (descriptions2 != null) {
              for (int bracket2 = 0; bracket2 < descriptions2.length; bracket2++) {
                item.append(" ext=cpu-bracket:");
                item.append(bracket2);
                item.append(":");
                item.append(descriptions2[bracket2]);
                item.append("\n                ");
              }
            }
            item.append(" ext=cpu:");
            item.append(rec.cpuUsageDetails);
          } else {
            if (!firstExtension) {
              item.append('\n');
              item.append(9);
              item.append(',');
              item.append(BatteryStats.HISTORY_DATA);
              item.append(",0");
            }
            item.append(",XC,");
            item.append(rec.cpuUsageDetails.uid);
            for (int i6 = 0; i6 < rec.cpuUsageDetails.cpuUsageMs.length; i6++) {
              item.append(',');
              item.append(rec.cpuUsageDetails.cpuUsageMs[i6]);
            }
          }
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

    private void printStepCpuUidCheckinDetails(StringBuilder sb, int uid, int utime, int stime) {
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

  /* JADX WARN: Removed duplicated region for block: B:100:? A[RETURN, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:41:0x009c A[Catch: all -> 0x0174, TryCatch #3 {all -> 0x0174, blocks: (B:36:0x0063, B:39:0x006f, B:41:0x009c, B:43:0x00a0, B:46:0x00a8, B:48:0x00b5, B:51:0x00c8, B:55:0x014d, B:56:0x00d5, B:57:0x00dd, B:59:0x00e3, B:60:0x00f4, B:62:0x00fa, B:66:0x011f, B:75:0x0153, B:76:0x015e, B:79:0x0166, B:103:0x0081, B:106:0x008b), top: B:35:0x0063 }] */
  /* JADX WARN: Removed duplicated region for block: B:90:0x0196  */
  /* JADX WARN: Removed duplicated region for block: B:93:0x019f  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private void dumpHistory(PrintWriter printWriter, int i, long j, boolean z) {
    long j2;
    boolean z2;
    boolean z3;
    int i2;
    HistoryTag historyTag;
    synchronized (this) {
      dumpHistoryTagPoolLocked(printWriter, z);
    }
    HistoryPrinter historyPrinter = new HistoryPrinter();
    BatteryStatsHistoryIterator iterateBatteryStatsHistory = iterateBatteryStatsHistory();
    HistoryEventTracker historyEventTracker = null;
    long j3 = -1;
    long j4 = -1;
    boolean z4 = false;
    while (true) {
      try {
        HistoryItem next = iterateBatteryStatsHistory.next();
        if (next == null) {
          break;
        }
        try {
          long j5 = next.time;
          if (j3 >= 0) {
            j2 = j3;
          } else {
            j2 = j5;
          }
          try {
            if (next.time >= j) {
              if (j >= 0 && !z4) {
                if (next.cmd != 5 && next.cmd != 7 && next.cmd != 4 && next.cmd != 8) {
                  if (next.currentTime == 0) {
                    z2 = z4;
                    z3 = false;
                  } else {
                    z2 = true;
                    try {
                      byte b = next.cmd;
                      next.cmd = (byte) 5;
                      historyPrinter.printNextItem(printWriter, next, j2, z, (i & 32) != 0);
                      next.cmd = b;
                      z3 = false;
                    } catch (Throwable th) {
                      th = th;
                      j4 = j5;
                      th.printStackTrace(printWriter);
                      Slog.wtf(TAG, "Corrupted battery history", th);
                      if (iterateBatteryStatsHistory != null) {}
                      if (j < 0) {}
                    }
                  }
                  if (historyEventTracker != null) {
                    if (next.cmd != 0) {
                      historyPrinter.printNextItem(
                          printWriter, next, j2, z, (i & 32) != 0 ? true : z3 ? 1 : 0);
                      next.cmd = z3 ? (byte) 1 : (byte) 0;
                    }
                    int i3 = next.eventCode;
                    HistoryTag historyTag2 = next.eventTag;
                    next.eventTag = new HistoryTag();
                    int i4 = 0;
                    while (i4 < 22) {
                      HashMap<String, SparseIntArray> stateForEvent =
                          historyEventTracker.getStateForEvent(i4);
                      if (stateForEvent == null) {
                        i2 = i4;
                        historyTag = historyTag2;
                      } else {
                        for (Map.Entry<String, SparseIntArray> entry : stateForEvent.entrySet()) {
                          SparseIntArray value = entry.getValue();
                          int i5 = 0;
                          while (i5 < value.size()) {
                            next.eventCode = i4;
                            next.eventTag.string = entry.getKey();
                            next.eventTag.uid = value.keyAt(i5);
                            next.eventTag.poolIdx = value.valueAt(i5);
                            historyPrinter.printNextItem(
                                printWriter, next, j2, z, (i & 32) != 0 ? true : z3);
                            next.wakeReasonTag = null;
                            next.wakelockTag = null;
                            i5++;
                            historyTag2 = historyTag2;
                            value = value;
                            i4 = i4;
                            z3 = false;
                          }
                          z3 = false;
                        }
                        i2 = i4;
                        historyTag = historyTag2;
                      }
                      i4 = i2 + 1;
                      historyTag2 = historyTag;
                      z3 = false;
                    }
                    next.eventCode = i3;
                    next.eventTag = historyTag2;
                    historyEventTracker = null;
                  }
                }
                z2 = true;
                z3 = false;
                historyPrinter.printNextItem(printWriter, next, j2, z, (i & 32) != 0);
                next.cmd = (byte) 0;
                if (historyEventTracker != null) {}
              } else {
                z2 = z4;
              }
              historyPrinter.printNextItem(printWriter, next, j2, z, (i & 32) != 0);
              z4 = z2;
            }
            j4 = j5;
            j3 = j2;
          } catch (Throwable th2) {
            th = th2;
            j4 = j5;
          }
        } catch (Throwable th3) {
          th = th3;
        }
      } finally {
      }
    }
    if (iterateBatteryStatsHistory != null) {
      iterateBatteryStatsHistory.close();
    }
    if (j < 0) {
      commitCurrentHistoryBatchLocked();
      printWriter.print(z ? "NEXT: " : "  NEXT: ");
      printWriter.println(1 + j4);
    }
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
    while (true) {
      if (i < STEP_LEVEL_MODES_OF_INTEREST.length) {
        int i2 = i;
        long estimatedTime = steps.computeTimeEstimate(r3[i], STEP_LEVEL_MODE_VALUES[i], tmpOutInt);
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
      } else {
        return;
      }
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

  public void dump(Context context, PrintWriter pw, int flags, int reqUid, long histStart) {
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
      dumpLocked(context, pw, flags, reqUid, filtering);
    }
  }

  private void dumpLocked(
      Context context, PrintWriter pw, int flags, int reqUid, boolean filtering) {
    ArrayList<PackageChange> pkgc;
    LevelStepTracker csteps;
    LevelStepTracker dsteps;
    int[] outInt;
    CharSequence charSequence;
    String str;
    String str2;
    boolean z;
    ArrayList<PackageChange> pkgc2;
    boolean z2;
    DailyItem dit;
    ArrayList<PackageChange> pkgc3;
    CharSequence charSequence2;
    boolean z3;
    String str3;
    SparseArray<? extends Uid> uidStats;
    int NU;
    long j;
    BatteryStats batteryStats = this;
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
    boolean z4 = false;
    if (!filtering || (flags & 2) != 0) {
      if (dumpDurationSteps(
          pw, "  ", "Discharge step durations:", getDischargeLevelStepTracker(), false)) {
        long timeRemaining =
            batteryStats.computeBatteryTimeRemaining(SystemClock.elapsedRealtime() * 1000);
        if (timeRemaining >= 0) {
          pw.print("  Estimated discharge time remaining: ");
          TimeUtils.formatDuration(timeRemaining / 1000, pw);
          pw.println();
        }
        LevelStepTracker steps = getDischargeLevelStepTracker();
        int i2 = 0;
        while (true) {
          if (i2 >= STEP_LEVEL_MODES_OF_INTEREST.length) {
            break;
          }
          dumpTimeEstimate(
              pw,
              "  Estimated ",
              STEP_LEVEL_MODE_LABELS[i2],
              " time: ",
              steps.computeTimeEstimate(r0[i2], STEP_LEVEL_MODE_VALUES[i2], null));
          i2++;
        }
        pw.println();
      }
      z4 = false;
      if (!dumpDurationSteps(
          pw, "  ", "Charge step durations:", getChargeLevelStepTracker(), false)) {
        batteryStats = this;
      } else {
        batteryStats = this;
        long timeRemaining2 =
            batteryStats.computeChargeTimeRemaining(SystemClock.elapsedRealtime() * 1000);
        if (timeRemaining2 >= 0) {
          pw.print("  Estimated charge time remaining: ");
          TimeUtils.formatDuration(timeRemaining2 / 1000, pw);
          pw.println();
        }
        pw.println();
      }
    }
    if (filtering && (flags & 4) == 0) {
      z2 = z4;
      str = "";
    } else {
      pw.println("Daily stats:");
      pw.print("  Current start time: ");
      pw.println(DateFormat.format("yyyy-MM-dd-HH-mm-ss", getCurrentDailyStartTime()).toString());
      pw.print("  Next min deadline: ");
      pw.println(DateFormat.format("yyyy-MM-dd-HH-mm-ss", getNextMinDailyDeadline()).toString());
      pw.print("  Next max deadline: ");
      pw.println(DateFormat.format("yyyy-MM-dd-HH-mm-ss", getNextMaxDailyDeadline()).toString());
      StringBuilder sb = new StringBuilder(64);
      int[] outInt2 = new int[1];
      LevelStepTracker dsteps2 = getDailyDischargeLevelStepTracker();
      LevelStepTracker csteps2 = getDailyChargeLevelStepTracker();
      ArrayList<PackageChange> pkgc4 = getDailyPackageChanges();
      if (dsteps2.mNumStepDurations <= 0 && csteps2.mNumStepDurations <= 0 && pkgc4 == null) {
        outInt = outInt2;
        charSequence = "yyyy-MM-dd-HH-mm-ss";
        str = "";
        str2 = "    ";
        z = z4;
        pkgc2 = pkgc4;
      } else {
        if ((flags & 4) != 0) {
          pkgc = pkgc4;
          csteps = csteps2;
          dsteps = dsteps2;
          outInt = outInt2;
          charSequence = "yyyy-MM-dd-HH-mm-ss";
          str = "";
          str2 = "    ";
          z = z4;
        } else if (!filtering) {
          pkgc = pkgc4;
          csteps = csteps2;
          dsteps = dsteps2;
          outInt = outInt2;
          charSequence = "yyyy-MM-dd-HH-mm-ss";
          str = "";
          str2 = "    ";
          z = z4;
        } else {
          pw.println("  Current daily steps:");
          str = "";
          str2 = "    ";
          dumpDailyLevelStepSummary(pw, "    ", "Discharge", dsteps2, sb, outInt2);
          outInt = outInt2;
          charSequence = "yyyy-MM-dd-HH-mm-ss";
          z = z4;
          dumpDailyLevelStepSummary(pw, "    ", "Charge", csteps2, sb, outInt);
          pkgc2 = pkgc4;
        }
        if (dumpDurationSteps(pw, str2, "  Current daily discharge step durations:", dsteps, z)) {
          dumpDailyLevelStepSummary(pw, "      ", "Discharge", dsteps, sb, outInt);
        }
        if (dumpDurationSteps(pw, str2, "  Current daily charge step durations:", csteps, z)) {
          dumpDailyLevelStepSummary(pw, "      ", "Charge", csteps, sb, outInt);
        }
        pkgc2 = pkgc;
        batteryStats.dumpDailyPackageChanges(pw, str2, pkgc2);
      }
      int curIndex = 0;
      while (true) {
        DailyItem dit2 = batteryStats.getDailyItemLocked(curIndex);
        if (dit2 == null) {
          break;
        }
        int curIndex2 = curIndex + 1;
        int curIndex3 = flags & 4;
        if (curIndex3 != 0) {
          pw.println();
        }
        pw.print("  Daily from ");
        pw.print(DateFormat.format(charSequence, dit2.mStartTime).toString());
        pw.print(" to ");
        pw.print(DateFormat.format(charSequence, dit2.mEndTime).toString());
        pw.println(":");
        if ((flags & 4) != 0) {
          dit = dit2;
          pkgc3 = pkgc2;
        } else if (filtering) {
          pkgc3 = pkgc2;
          int[] iArr = outInt;
          dumpDailyLevelStepSummary(pw, "    ", "Discharge", dit2.mDischargeSteps, sb, iArr);
          dumpDailyLevelStepSummary(pw, "    ", "Charge", dit2.mChargeSteps, sb, iArr);
          charSequence2 = charSequence;
          z3 = false;
          z = z3;
          curIndex = curIndex2;
          pkgc2 = pkgc3;
          charSequence = charSequence2;
        } else {
          dit = dit2;
          pkgc3 = pkgc2;
        }
        if (!dumpDurationSteps(
            pw, "      ", "    Discharge step durations:", dit.mDischargeSteps, false)) {
          charSequence2 = charSequence;
          str3 = "      ";
        } else {
          charSequence2 = charSequence;
          str3 = "      ";
          dumpDailyLevelStepSummary(pw, "        ", "Discharge", dit.mDischargeSteps, sb, outInt);
        }
        if (!dumpDurationSteps(pw, str3, "    Charge step durations:", dit.mChargeSteps, false)) {
          z3 = false;
        } else {
          z3 = false;
          dumpDailyLevelStepSummary(pw, "        ", "Charge", dit.mChargeSteps, sb, outInt);
        }
        batteryStats.dumpDailyPackageChanges(pw, str2, dit.mPackageChanges);
        z = z3;
        curIndex = curIndex2;
        pkgc2 = pkgc3;
        charSequence = charSequence2;
      }
      z2 = z;
      pw.println();
    }
    if (!filtering || (flags & 2) != 0) {
      pw.println("Statistics since last charge:");
      pw.println(
          "  System starts: "
              + getStartCount()
              + ", currently on battery: "
              + getIsOnBattery()
              + ", can read charging time: "
              + canReadTimeToFullNow()
              + (isJdmModel() ? " (JDM)" : str));
      dumpLocked(context, pw, "", 0, reqUid, (flags & 64) != 0 ? true : z2);
      pw.println();
    }
    if ((flags & 32) != 0) {
      batteryStats.printLatestBackupData(pw);
    }
  }

  public void dumpCheckin(
      Context context, PrintWriter pw, List<ApplicationInfo> apps, int flags, long histStart) {
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
      dumpCheckinLocked(context, pw, apps, flags);
    }
  }

  private void dumpCheckinLocked(
      Context context, PrintWriter pw, List<ApplicationInfo> apps, int flags) {
    if (apps != null) {
      SparseArray<Pair<ArrayList<String>, MutableBoolean>> uids = new SparseArray<>();
      for (int i = 0; i < apps.size(); i++) {
        ApplicationInfo ai = apps.get(i);
        Pair<ArrayList<String>, MutableBoolean> pkgs = uids.get(UserHandle.getAppId(ai.uid));
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
      dumpCheckinLocked(context, pw, 0, -1, (flags & 64) != 0);
    }
  }

  public void dumpProtoLocked(
      Context context, FileDescriptor fd, List<ApplicationInfo> apps, int flags, long histStart) {
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
      BatteryUsageStats stats = getBatteryUsageStats(context, false);
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
    long[] cpuFreqs;
    long rawRealtimeMs2;
    SparseArray<? extends Uid.Sensor> sensors;
    UidBatteryConsumer consumer;
    long[] timesInFreqMs;
    long[] timesInFreqScreenOffMs;
    int iu;
    ArrayList<String> pkgs;
    long[] cpuFreqs2;
    SparseArray<UidBatteryConsumer> uidToConsumer;
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
    SparseArray<UidBatteryConsumer> uidToConsumer2 = new SparseArray<>();
    List<UidBatteryConsumer> consumers = stats.getUidBatteryConsumers();
    int i2 = consumers.size() - 1;
    while (i2 >= 0) {
      UidBatteryConsumer bs = consumers.get(i2);
      uidToConsumer2.put(bs.getUid(), bs);
      i2--;
      consumers = consumers;
    }
    List<UidBatteryConsumer> consumers2 = consumers;
    SparseArray<? extends Uid> uidStats2 = getUidStats();
    int n = uidStats2.size();
    int iu2 = 0;
    while (iu2 < n) {
      int n2 = n;
      SparseArray<UidBatteryConsumer> uidToConsumer3 = uidToConsumer2;
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
        long bgTimeMs = bgTimer != null ? bgTimer.getTotalDurationMsLocked(rawRealtimeMs) : 0L;
        long awToken = proto.start(1146756268056L);
        proto.write(1112396529665L, totTimeMs);
        proto.write(1112396529666L, bgTimeMs);
        proto.end(awToken);
      }
      int iu5 = iu3;
      long rawRealtimeUs4 = rawRealtimeUs;
      List<UidBatteryConsumer> consumers3 = consumers2;
      SparseArray<UidBatteryConsumer> uidToConsumer4 = uidToConsumer3;
      dumpTimer(proto, 1146756268040L, u4.getAudioTurnedOnTimer(), rawRealtimeUs4, 0);
      dumpControllerActivityProto(proto, 1146756268035L, u4.getBluetoothControllerActivity(), 0);
      Timer bleTimer = u4.getBluetoothScanTimer();
      if (bleTimer != null) {
        long bmToken = proto.start(1146756268038L);
        dumpTimer(proto, 1146756268033L, bleTimer, rawRealtimeUs4, 0);
        dumpTimer(proto, 1146756268034L, u4.getBluetoothScanBackgroundTimer(), rawRealtimeUs4, 0);
        dumpTimer(proto, 1146756268035L, u4.getBluetoothUnoptimizedScanTimer(), rawRealtimeUs4, 0);
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
      long[] cpuFreqs3 = getCpuFreqs();
      if (cpuFreqs3 == null) {
        cpuFreqs = cpuFreqs3;
        rawRealtimeMs2 = rawRealtimeMs;
      } else {
        long[] cpuFreqTimeMs = u4.getCpuFreqTimes(0);
        if (cpuFreqTimeMs == null || cpuFreqTimeMs.length != cpuFreqs3.length) {
          cpuFreqs = cpuFreqs3;
          rawRealtimeMs2 = rawRealtimeMs;
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
            cpuFreqs3 = cpuFreqs3;
            cpuFreqTimeMs = cpuFreqTimeMs;
            rawRealtimeMs = rawRealtimeMs;
          }
          cpuFreqs = cpuFreqs3;
          rawRealtimeMs2 = rawRealtimeMs;
        }
      }
      long[] timesInFreqMs2 = new long[getCpuFreqCount()];
      long[] timesInFreqScreenOffMs2 = new long[getCpuFreqCount()];
      int procState = 0;
      while (procState < 7) {
        if (!u4.getCpuFreqTimes(timesInFreqMs2, procState)) {
          iu = iu5;
          pkgs = pkgs6;
          cpuFreqs2 = cpuFreqs;
          uidToConsumer = uidToConsumer4;
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
            pkgs6 = pkgs6;
            iu5 = iu6;
            uidToConsumer4 = uidToConsumer4;
            cpuFreqs = cpuFreqs;
          }
          iu = iu5;
          pkgs = pkgs6;
          cpuFreqs2 = cpuFreqs;
          uidToConsumer = uidToConsumer4;
          proto.end(procToken);
        }
        procState++;
        pkgs6 = pkgs;
        iu5 = iu;
        uidToConsumer4 = uidToConsumer;
        cpuFreqs = cpuFreqs2;
      }
      int iu7 = iu5;
      SparseArray<UidBatteryConsumer> uidToConsumer5 = uidToConsumer4;
      long j2 = 2246267895810L;
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
            long[] timesInFreqMs3 = timesInFreqMs2;
            long rToken = proto.start(j2);
            proto.write(1159641169921L, r);
            proto.write(1120986464258L, types.get(r, 0));
            proto.end(rToken);
            i3++;
            timesInFreqMs2 = timesInFreqMs3;
            timesInFreqScreenOffMs2 = timesInFreqScreenOffMs2;
            types = types;
            jcToken = jcToken;
            j2 = 2246267895810L;
          }
          timesInFreqMs = timesInFreqMs2;
          timesInFreqScreenOffMs = timesInFreqScreenOffMs2;
          proto.end(jcToken);
        } else {
          timesInFreqMs = timesInFreqMs2;
          timesInFreqScreenOffMs = timesInFreqScreenOffMs2;
        }
        ic3++;
        timesInFreqMs2 = timesInFreqMs;
        timesInFreqScreenOffMs2 = timesInFreqScreenOffMs;
        j2 = 2246267895810L;
      }
      long[] timesInFreqMs4 = timesInFreqMs2;
      long j3 = 1120986464258L;
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
        j3 = 1120986464258L;
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
      int uid4 = uid3;
      SparseArray<UidBatteryConsumer> uidToConsumer6 = uidToConsumer5;
      UidBatteryConsumer consumer2 = uidToConsumer6.get(uid4);
      if (consumer2 != null) {
        long bsToken = proto.start(1146756268050L);
        proto.write(1103806595073L, consumer2.getConsumedPower());
        proto.write(
            1133871366146L, proportionalAttributionCalculator.isSystemBatteryConsumer(consumer2));
        proto.write(1103806595075L, consumer2.getConsumedPower(0));
        proto.write(
            1103806595076L, proportionalAttributionCalculator.getProportionalPowerMah(consumer2));
        proto.end(bsToken);
      }
      ArrayMap<String, ? extends Uid.Proc> processStats = u4.getProcessStats();
      int ipr = processStats.size() - 1;
      while (ipr >= 0) {
        Uid.Proc ps = processStats.valueAt(ipr);
        long prToken = proto.start(2246267895827L);
        proto.write(1138166333441L, processStats.keyAt(ipr));
        proto.write(1112396529666L, ps.getUserTime(0));
        proto.write(1112396529667L, ps.getSystemTime(0));
        proto.write(1112396529668L, ps.getForegroundTime(0));
        proto.write(1120986464261L, ps.getStarts(0));
        proto.write(1120986464262L, ps.getNumAnrs(0));
        proto.write(1120986464263L, ps.getNumCrashes(0));
        proto.end(prToken);
        ipr--;
        processStats = processStats;
        uidToConsumer6 = uidToConsumer6;
        consumer2 = consumer2;
      }
      UidBatteryConsumer consumer3 = consumer2;
      SparseArray<UidBatteryConsumer> uidToConsumer7 = uidToConsumer6;
      SparseArray<? extends Uid.Sensor> sensors2 = u4.getSensorStats();
      int ise = 0;
      while (ise < sensors2.size()) {
        Uid.Sensor se = sensors2.valueAt(ise);
        Timer timer3 = se.getSensorTime();
        if (timer3 == null) {
          consumer = consumer3;
        } else {
          Timer bgTimer3 = se.getSensorBackgroundTime();
          int sensorNumber = sensors2.keyAt(ise);
          long seToken = proto.start(2246267895829L);
          proto.write(1120986464257L, sensorNumber);
          consumer = consumer3;
          dumpTimer(proto, 1146756268034L, timer3, rawRealtimeUs4, 0);
          dumpTimer(proto, 1146756268035L, bgTimer3, rawRealtimeUs4, 0);
          proto.end(seToken);
        }
        ise++;
        consumer3 = consumer;
      }
      int ips = 0;
      while (ips < 7) {
        long rawRealtimeUs5 = rawRealtimeUs4;
        long durMs = roundUsToMs(u4.getProcessStateTime(ips, rawRealtimeUs5, 0));
        if (durMs == 0) {
          sensors = sensors2;
        } else {
          long stToken = proto.start(2246267895828L);
          proto.write(1159641169921L, ips);
          sensors = sensors2;
          proto.write(1112396529666L, durMs);
          proto.end(stToken);
        }
        ips++;
        rawRealtimeUs4 = rawRealtimeUs5;
        sensors2 = sensors;
      }
      long rawRealtimeUs6 = rawRealtimeUs4;
      long j4 = 1112396529666L;
      ArrayMap<String, ? extends Timer> syncs = u4.getSyncStats();
      int isy = syncs.size() - 1;
      while (isy >= 0) {
        Timer timer4 = syncs.valueAt(isy);
        Timer bgTimer4 = timer4.getSubTimer();
        long syToken = proto.start(2246267895830L);
        proto.write(1138166333441L, syncs.keyAt(isy));
        dumpTimer(proto, 1146756268034L, timer4, rawRealtimeUs6, 0);
        dumpTimer(proto, 1146756268035L, bgTimer4, rawRealtimeUs6, 0);
        proto.end(syToken);
        isy--;
        j4 = 1112396529666L;
        syncs = syncs;
        timesInFreqMs4 = timesInFreqMs4;
        uid4 = uid4;
      }
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
      dumpTimer(proto, 1146756268045L, u4.getVibratorOnTimer(), rawRealtimeUs6, 0);
      dumpTimer(proto, 1146756268046L, u4.getVideoTurnedOnTimer(), rawRealtimeUs6, 0);
      ArrayMap<String, ? extends Uid.Wakelock> wakelocks = u4.getWakelockStats();
      int iw = wakelocks.size() - 1;
      while (iw >= 0) {
        Uid.Wakelock wl = wakelocks.valueAt(iw);
        long wToken = proto.start(2246267895833L);
        proto.write(1138166333441L, wakelocks.keyAt(iw));
        int iw2 = iw;
        ArrayMap<String, ? extends Uid.Wakelock> wakelocks2 = wakelocks;
        dumpTimer(proto, 1146756268034L, wl.getWakeTime(1), rawRealtimeUs6, 0);
        Timer pTimer = wl.getWakeTime(0);
        if (pTimer != null) {
          dumpTimer(proto, 1146756268035L, pTimer, rawRealtimeUs6, 0);
          dumpTimer(proto, 1146756268036L, pTimer.getSubTimer(), rawRealtimeUs6, 0);
        }
        dumpTimer(proto, 1146756268037L, wl.getWakeTime(2), rawRealtimeUs6, 0);
        proto.end(wToken);
        iw = iw2 - 1;
        wakelocks = wakelocks2;
      }
      dumpTimer(proto, 1146756268060L, u4.getMulticastWakelockStats(), rawRealtimeUs6, 0);
      int i5 = 1;
      int ipkg = packageStats5.size() - 1;
      while (ipkg >= 0) {
        ArrayMap<String, ? extends Uid.Pkg> packageStats6 = packageStats5;
        ArrayMap<String, ? extends Counter> alarms =
            packageStats6.valueAt(ipkg).getWakeupAlarmStats();
        for (int iwa = alarms.size() - i5; iwa >= 0; iwa--) {
          long waToken = proto.start(2246267895834L);
          proto.write(1138166333441L, alarms.keyAt(iwa));
          proto.write(1120986464258L, alarms.valueAt(iwa).getCountLocked(0));
          proto.end(waToken);
        }
        ipkg--;
        packageStats5 = packageStats6;
        i5 = 1;
      }
      dumpControllerActivityProto(proto, 1146756268037L, u4.getWifiControllerActivity(), 0);
      long wToken2 = proto.start(1146756268059L);
      proto.write(1112396529665L, roundUsToMs(u4.getFullWifiLockTime(rawRealtimeUs6, 0)));
      dumpTimer(proto, 1146756268035L, u4.getWifiScanTimer(), rawRealtimeUs6, 0);
      proto.write(1112396529666L, roundUsToMs(u4.getWifiRunningTime(rawRealtimeUs6, 0)));
      dumpTimer(proto, 1146756268036L, u4.getWifiScanBackgroundTimer(), rawRealtimeUs6, 0);
      proto.end(wToken2);
      proto.end(uTkn3);
      iu2 = iu7 + 1;
      protoOutputStream2 = proto;
      uidStats2 = uidStats4;
      aidToPackages2 = aidToPackages;
      n = n2;
      which = which2;
      batteryUptimeUs2 = batteryUptimeUs4;
      uidToConsumer2 = uidToConsumer7;
      consumers2 = consumers3;
      rawRealtimeUs = rawRealtimeUs6;
      rawUptimeUs = rawUptimeUs2;
      rawRealtimeMs4 = rawRealtimeMs2;
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:44:0x00ec A[Catch: all -> 0x01c3, TryCatch #1 {all -> 0x01c3, blocks: (B:39:0x00b9, B:42:0x00c5, B:44:0x00ec, B:46:0x00f0, B:49:0x00f8, B:51:0x0103, B:54:0x0116, B:58:0x0199, B:59:0x0123, B:60:0x012b, B:62:0x0131, B:63:0x0142, B:65:0x0148, B:69:0x016d, B:78:0x019f, B:79:0x01aa, B:82:0x01b2, B:101:0x00d4, B:104:0x00dd), top: B:38:0x00b9 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private void dumpProtoHistoryLocked(ProtoOutputStream protoOutputStream, int i, long j) {
    Throwable th;
    boolean z;
    boolean z2;
    int i2;
    HistoryTag historyTag;
    protoOutputStream.write(1120986464257L, 36);
    protoOutputStream.write(1112396529666L, getParcelVersion());
    protoOutputStream.write(1138166333443L, getStartPlatformVersion());
    protoOutputStream.write(1138166333444L, getEndPlatformVersion());
    for (int i3 = 0; i3 < getHistoryStringPoolSize(); i3++) {
      long start = protoOutputStream.start(2246267895813L);
      protoOutputStream.write(1120986464257L, i3);
      protoOutputStream.write(1120986464258L, getHistoryTagPoolUid(i3));
      protoOutputStream.write(1138166333443L, getHistoryTagPoolString(i3));
      protoOutputStream.end(start);
    }
    HistoryPrinter historyPrinter = new HistoryPrinter();
    BatteryStatsHistoryIterator iterateBatteryStatsHistory = iterateBatteryStatsHistory();
    HistoryEventTracker historyEventTracker = null;
    long j2 = -1;
    long j3 = -1;
    boolean z3 = false;
    while (true) {
      try {
        HistoryItem next = iterateBatteryStatsHistory.next();
        if (next == null) {
          break;
        }
        long j4 = next.time;
        long j5 = j2 < 0 ? j4 : j2;
        try {
          if (next.time >= j) {
            if (j < 0 || z3) {
              z = z3;
            } else {
              if (next.cmd != 5 && next.cmd != 7 && next.cmd != 4 && next.cmd != 8) {
                if (next.currentTime != 0) {
                  z = true;
                  try {
                    byte b = next.cmd;
                    next.cmd = (byte) 5;
                    historyPrinter.printNextItem(protoOutputStream, next, j5, (i & 32) != 0);
                    next.cmd = b;
                    z2 = false;
                  } catch (Throwable th2) {
                    th = th2;
                    if (iterateBatteryStatsHistory == null) {
                      throw th;
                    }
                    try {
                      iterateBatteryStatsHistory.close();
                      throw th;
                    } catch (Throwable th3) {
                      th.addSuppressed(th3);
                      throw th;
                    }
                  }
                } else {
                  z = z3;
                  z2 = false;
                }
                if (historyEventTracker != null) {
                  if (next.cmd != 0) {
                    historyPrinter.printNextItem(
                        protoOutputStream, next, j5, (i & 32) != 0 ? true : z2 ? 1 : 0);
                    next.cmd = z2 ? (byte) 1 : (byte) 0;
                  }
                  int i4 = next.eventCode;
                  HistoryTag historyTag2 = next.eventTag;
                  next.eventTag = new HistoryTag();
                  int i5 = 0;
                  while (i5 < 22) {
                    HashMap<String, SparseIntArray> stateForEvent =
                        historyEventTracker.getStateForEvent(i5);
                    if (stateForEvent == null) {
                      i2 = i5;
                      historyTag = historyTag2;
                    } else {
                      for (Map.Entry<String, SparseIntArray> entry : stateForEvent.entrySet()) {
                        SparseIntArray value = entry.getValue();
                        int i6 = 0;
                        while (i6 < value.size()) {
                          next.eventCode = i5;
                          next.eventTag.string = entry.getKey();
                          next.eventTag.uid = value.keyAt(i6);
                          next.eventTag.poolIdx = value.valueAt(i6);
                          historyPrinter.printNextItem(
                              protoOutputStream, next, j5, (i & 32) != 0 ? true : z2);
                          next.wakeReasonTag = null;
                          next.wakelockTag = null;
                          i6++;
                          historyTag2 = historyTag2;
                          i5 = i5;
                          value = value;
                          z2 = false;
                        }
                        z2 = false;
                      }
                      i2 = i5;
                      historyTag = historyTag2;
                    }
                    i5 = i2 + 1;
                    historyTag2 = historyTag;
                    z2 = false;
                  }
                  next.eventCode = i4;
                  next.eventTag = historyTag2;
                  historyEventTracker = null;
                }
              }
              z = true;
              z2 = false;
              historyPrinter.printNextItem(protoOutputStream, next, j5, (i & 32) != 0);
              next.cmd = (byte) 0;
              if (historyEventTracker != null) {}
            }
            historyPrinter.printNextItem(protoOutputStream, next, j5, (i & 32) != 0);
            j3 = j4;
            z3 = z;
            j2 = j5;
          } else {
            j3 = j4;
            j2 = j5;
          }
        } catch (Throwable th4) {
          th = th4;
        }
      } catch (Throwable th5) {
        th = th5;
      }
    }
    if (j >= 0) {
      commitCurrentHistoryBatchLocked();
      protoOutputStream.write(2237677961222L, "NEXT: " + (1 + j3));
    }
    if (iterateBatteryStatsHistory != null) {
      iterateBatteryStatsHistory.close();
    }
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
    while (true) {
      if (i2 >= NUM_DATA_CONNECTION_TYPES) {
        break;
      }
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
    dumpDurationSteps(proto, 2246267895814L, getDischargeLevelStepTracker());
    long[] cpuFreqs = getCpuFreqs();
    if (cpuFreqs != null) {
      for (long i3 : cpuFreqs) {
        proto.write(SystemProto.CPU_FREQUENCY, i3);
      }
    }
    dumpControllerActivityProto(proto, 1146756268041L, getBluetoothControllerActivity(), 0);
    dumpControllerActivityProto(proto, 1146756268042L, getModemControllerActivity(), 0);
    long gnToken = proto.start(1146756268044L);
    proto.write(1112396529665L, getNetworkActivityBytes(0, 0));
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
    proto.end(gwToken);
    Map<String, ? extends Timer> kernelWakelocks = getKernelWakelockStats();
    for (Map.Entry<String, ? extends Timer> ent : kernelWakelocks.entrySet()) {
      long kwToken = proto.start(2246267895822L);
      proto.write(1138166333441L, ent.getKey());
      dumpTimer(proto, 1146756268034L, ent.getValue(), rawRealtimeUs3, 0);
      proto.end(kwToken);
      kernelWakelocks = kernelWakelocks;
      gwToken = gwToken;
    }
    int i4 = 1;
    SparseArray<? extends Uid> uidStats = getUidStats();
    int iu = 0;
    long fullWakeLockTimeTotalUs = 0;
    long partialWakeLockTimeTotalUs = 0;
    while (iu < uidStats.size()) {
      Uid u = uidStats.valueAt(iu);
      ArrayMap<String, ? extends Uid.Wakelock> wakelocks = u.getWakelockStats();
      int iw = wakelocks.size() - i4;
      while (iw >= 0) {
        Uid.Wakelock wl = wakelocks.valueAt(iw);
        Timer fullWakeTimer = wl.getWakeTime(i4);
        if (fullWakeTimer == null) {
          i = 0;
        } else {
          i = 0;
          fullWakeLockTimeTotalUs += fullWakeTimer.getTotalTimeLocked(rawRealtimeUs3, 0);
        }
        Uid u2 = u;
        Timer partialWakeTimer = wl.getWakeTime(i);
        if (partialWakeTimer != null) {
          partialWakeLockTimeTotalUs += partialWakeTimer.getTotalTimeLocked(rawRealtimeUs3, i);
        }
        iw--;
        u = u2;
        i4 = 1;
      }
      iu++;
      i4 = 1;
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
    long mToken2 = multicastWakeLockTimeTotalUs / 1000;
    proto.write(1112396529665L, mToken2);
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
      long wmctToken2 = wmctToken;
      long puiToken = proto.start(2246267895825L);
      proto.write(1159641169921L, n);
      proto.write(1120986464258L, 0);
      proto.write(1103806595075L, deviceConsumer.getConsumedPower(powerComponent));
      proto.write(1133871366148L, shouldHidePowerComponent(powerComponent));
      proto.write(1103806595077L, 0);
      proto.write(1103806595078L, 0);
      proto.end(puiToken);
      powerComponent++;
      multicastWakeLockCountTotal = multicastWakeLockCountTotal;
      wmctToken = wmctToken2;
      rawUptimeUs = rawUptimeUs;
    }
    int multicastWakeLockCountTotal2 = multicastWakeLockCountTotal;
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
      dumpTimer(proto, 1146756268035L, screenOffRpmStats2.get(ent2.getKey()), rawRealtimeUs3, 0);
      proto.end(rpmToken);
      uidStats = uidStats;
      screenOffRpmStats = screenOffRpmStats2;
      multicastWakeLockCountTotal2 = multicastWakeLockCountTotal2;
    }
    for (int i5 = 0; i5 < 5; i5++) {
      long sbToken = proto.start(2246267895828L);
      proto.write(1159641169921L, i5);
      dumpTimer(proto, 1146756268034L, getScreenBrightnessTimer(i5), rawRealtimeUs3, 0);
      proto.end(sbToken);
    }
    dumpTimer(proto, 1146756268053L, getPhoneSignalScanningTimer(), rawRealtimeUs3, 0);
    for (int i6 = 0; i6 < CellSignalStrength.getNumSignalStrengthLevels(); i6++) {
      long pssToken = proto.start(2246267895824L);
      proto.write(1159641169921L, i6);
      dumpTimer(proto, 1146756268034L, getPhoneSignalStrengthTimer(i6), rawRealtimeUs3, 0);
      proto.end(pssToken);
    }
    Map<String, ? extends Timer> wakeupReasons = getWakeupReasonStats();
    for (Map.Entry<String, ? extends Timer> ent3 : wakeupReasons.entrySet()) {
      long wrToken = proto.start(2246267895830L);
      proto.write(1138166333441L, ent3.getKey());
      dumpTimer(proto, 1146756268034L, ent3.getValue(), rawRealtimeUs3, 0);
      proto.end(wrToken);
    }
    for (int i7 = 0; i7 < 5; i7++) {
      long wssToken = proto.start(2246267895832L);
      proto.write(1159641169921L, i7);
      dumpTimer(proto, 1146756268034L, getWifiSignalStrengthTimer(i7), rawRealtimeUs3, 0);
      proto.end(wssToken);
    }
    for (int i8 = 0; i8 < 8; i8++) {
      long wsToken = proto.start(2246267895833L);
      proto.write(1159641169921L, i8);
      dumpTimer(proto, 1146756268034L, getWifiStateTimer(i8), rawRealtimeUs3, 0);
      proto.end(wsToken);
    }
    for (int i9 = 0; i9 < 13; i9++) {
      long wssToken2 = proto.start(2246267895834L);
      proto.write(1159641169921L, i9);
      dumpTimer(proto, 1146756268034L, getWifiSupplStateTimer(i9), rawRealtimeUs3, 0);
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
          resources2.getStringArray(C4337R.array.config_batteryPackageTypeSystem);
      String[] servicePackageArray =
          resources2.getStringArray(C4337R.array.config_batteryPackageTypeService);
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

  /* JADX INFO: Access modifiers changed from: private */
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
                    uid, rxPackets, txPackets, radioActiveMs, radioActiveCount, msPerPacket));
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
                    ((BatteryStats.UidMobileRadioStats) obj2).millisecondsPerPacket,
                    ((BatteryStats.UidMobileRadioStats) obj).millisecondsPerPacket);
            return compare;
          }
        });
    return uidMobileRadioStats;
  }

  void printLatestBackupData(PrintWriter pw) {
    File[] childFileList;
    long latestTime = 0;
    File backupDir = new File("/data/log/batterystats/");
    if (backupDir.exists() && (childFileList = backupDir.listFiles()) != null) {
      for (File childFile : childFileList) {
        long time =
            Long.parseLong(
                childFile.getAbsolutePath().replace("/data/log/batterystats/newbatterystats", ""));
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
}
