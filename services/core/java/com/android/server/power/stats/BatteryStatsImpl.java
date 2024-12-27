package com.android.server.power.stats;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.usage.NetworkStatsManager;
import android.bluetooth.BluetoothActivityEnergyInfo;
import android.bluetooth.BluetoothManager;
import android.bluetooth.UidTraffic;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hardware.power.stats.StateResidency;
import android.hardware.power.stats.StateResidencyResult;
import android.net.NetworkStats;
import android.net.Uri;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.net.wifi.WifiManager;
import android.os.BatteryConsumer;
import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.BluetoothBatteryStats;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBatteryPropertiesRegistrar;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SemModemActivityInfo;
import android.os.SemSimpleNetworkStats;
import android.os.ServiceManager;
import android.os.SpeakerOutEnergyInfo;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.WakeLockStats;
import android.os.WorkSource;
import android.os.connectivity.CellularBatteryStats;
import android.os.connectivity.GpsBatteryStats;
import android.os.connectivity.WifiActivityEnergyInfo;
import android.os.connectivity.WifiBatteryStats;
import android.provider.Settings;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthNr;
import android.telephony.ModemActivityInfo;
import android.telephony.SignalStrength;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.IndentingPrintWriter;
import android.util.IntArray;
import android.util.KeyValueListParser;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.LongSparseLongArray;
import android.util.MutableInt;
import android.util.Patterns;
import android.util.Printer;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseDoubleArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.TimeUtils;
import android.util.Xml;
import android.view.Display;
import com.android.internal.app.IBatteryStatsCallback;
import com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.Flags;
import com.android.internal.logging.EventLogTags;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.BatteryStatsHistory;
import com.android.internal.os.BatteryStatsHistoryIterator;
import com.android.internal.os.BinderCallsStats;
import com.android.internal.os.BinderTransactionNameResolver;
import com.android.internal.os.Clock;
import com.android.internal.os.CpuScalingPolicies;
import com.android.internal.os.KernelCpuSpeedReader;
import com.android.internal.os.KernelCpuUidTimeReader;
import com.android.internal.os.KernelMemoryBandwidthStats;
import com.android.internal.os.KernelSingleProcessCpuThreadReader;
import com.android.internal.os.KernelSingleUidTimeReader;
import com.android.internal.os.LongArrayMultiStateCounter;
import com.android.internal.os.LongMultiStateCounter;
import com.android.internal.os.MonotonicClock;
import com.android.internal.os.PowerProfile;
import com.android.internal.os.PowerStats;
import com.android.internal.os.RailStats;
import com.android.internal.os.RpmStats;
import com.android.internal.power.EnergyConsumerStats;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerProcLock;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.BatteryStatsService;
import com.android.server.am.PendingIntentController$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.clipboard.ClipboardService;
import com.android.server.pm.UserManagerInternal;
import com.android.server.power.stats.BatteryStatsImpl;
import com.android.server.power.stats.BluetoothPowerStatsCollector;
import com.android.server.power.stats.KernelWakelockStats;
import com.android.server.power.stats.PowerStatsCollector;
import com.android.server.power.stats.PowerStatsUidResolver;
import com.android.server.power.stats.SystemServerCpuThreadReader;
import com.android.server.power.stats.WifiPowerStatsCollector;
import com.android.server.powerstats.PowerStatsService;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import libcore.util.EmptyArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BatteryStatsImpl extends BatteryStats {
    public static final int CELL_SIGNAL_STRENGTH_LEVEL_COUNT;
    public static final int MAX_WAKELOCKS_PER_UID;
    protected static final long MOBILE_RADIO_POWER_STATE_UPDATE_FREQ_MS = 600000;
    public static final int MODEM_TX_POWER_LEVEL_COUNT;
    public static final int[] SUPPORTED_PER_PROCESS_STATE_STANDARD_ENERGY_BUCKETS;
    public static final int VERSION;
    public static final int WAKE_LOCK_WEIGHT = 50;
    public static final AnonymousClass1 ZERO_LONG_COUNTER;
    public static final BatteryStats.LongCounter[] ZERO_LONG_COUNTER_ARRAY;
    public final BatteryStats.HistoryEventTracker mActiveEvents;
    public int mActiveRat;
    public AlarmManager mAlarmManager;
    public int mAp_temp;
    public int mAudioOnNesting;
    public StopwatchTimer mAudioOnTimer;
    public final ArrayList mAudioTurnedOnTimers;
    public boolean mAutoBrightnessMode;
    public int mBatteryCC;
    public int mBatteryChargeUah;
    public int mBatteryHealth;
    public int mBatteryLevel;
    public int mBatteryPlugType;
    public boolean mBatteryPluggedIn;
    public long mBatteryPluggedInRealTimeMs;
    public int mBatteryRaw;
    public int mBatterySecCurrentEvent;
    public int mBatterySecEvent;
    public int mBatterySecOnline;
    public int mBatterySecTxShareEvent;
    public final RemoteCallbackList mBatteryStatsCallbacks;
    protected final BatteryStatsConfig mBatteryStatsConfig;
    public int mBatteryStatus;
    public int mBatteryTemperature;
    public long mBatteryTimeToFullSeconds;
    public BatteryUsageStatsProvider mBatteryUsageStatsProvider;
    public int mBatteryVoltageMv;
    public LongSamplingCounterArray mBinderThreadCpuTimesUs;
    public ControllerActivityCounterImpl mBluetoothActivity;
    public BluetoothPowerCalculator mBluetoothPowerCalculator;
    public final BluetoothPowerStatsCollector mBluetoothPowerStatsCollector;
    public int mBluetoothScanNesting;
    public final ArrayList mBluetoothScanOnTimers;
    public StopwatchTimer mBluetoothScanTimer;
    public BatteryCallback mCallback;
    public int mCameraOnNesting;
    public StopwatchTimer mCameraOnTimer;
    public final CameraPowerStatsCollector mCameraPowerStatsCollector;
    public final ArrayList mCameraTurnedOnTimers;
    public final BatteryStats.LevelStepTracker mChargeStepTracker;
    public boolean mCharging;
    public final AtomicFile mCheckinFile;
    public final Clock mClock;
    protected final Constants mConstants;
    public final int[] mCpuPowerBracketMap;
    public CpuPowerCalculator mCpuPowerCalculator;
    public final CpuPowerStatsCollector mCpuPowerStatsCollector;
    protected CpuScalingPolicies mCpuScalingPolicies;
    public final long mCpuTimeReadsTrackingStartTimeMs;
    protected KernelCpuUidTimeReader.KernelCpuUidActiveTimeReader mCpuUidActiveTimeReader;
    protected KernelCpuUidTimeReader.KernelCpuUidClusterTimeReader mCpuUidClusterTimeReader;
    protected KernelCpuUidTimeReader.KernelCpuUidFreqTimeReader mCpuUidFreqTimeReader;
    protected KernelCpuUidTimeReader.KernelCpuUidUserSysTimeReader mCpuUidUserSysTimeReader;
    public int mCurStepMode;
    public int mCurrent;
    public final CustomEnergyConsumerPowerStatsCollector mCustomEnergyConsumerPowerStatsCollector;
    public CustomTelephonyCallback mCustomTelephonyCallback;
    public final BatteryStats.LevelStepTracker mDailyChargeStepTracker;
    public final BatteryStats.LevelStepTracker mDailyDischargeStepTracker;
    public final AtomicFile mDailyFile;
    public final ArrayList mDailyItems;
    public ArrayList mDailyPackageChanges;
    public long mDailyStartTimeMs;
    public final AnonymousClass3 mDeferSetCharging;
    public int mDeviceIdleMode;
    public StopwatchTimer mDeviceIdleModeFullTimer;
    public StopwatchTimer mDeviceIdleModeLightTimer;
    public boolean mDeviceIdling;
    public StopwatchTimer mDeviceIdlingTimer;
    public boolean mDeviceLightIdling;
    public StopwatchTimer mDeviceLightIdlingTimer;
    public int mDischargeAmountScreenDoze;
    public int mDischargeAmountScreenDozeSinceCharge;
    public int mDischargeAmountScreenDozeSinceChargePermil;
    public int mDischargeAmountScreenOff;
    public int mDischargeAmountScreenOffSinceCharge;
    public int mDischargeAmountScreenOffSinceChargeCoulombCounter;
    public int mDischargeAmountScreenOffSinceChargePermil;
    public int mDischargeAmountScreenOn;
    public int mDischargeAmountScreenOnSinceCharge;
    public int mDischargeAmountScreenOnSinceChargeCoulombCounter;
    public int mDischargeAmountScreenOnSinceChargePermil;
    public int mDischargeAmountSubScreenDozeSinceChargePermil;
    public int mDischargeAmountSubScreenOnSinceChargePermil;
    public LongSamplingCounter mDischargeCounter;
    public int mDischargeCurrentLevel;
    public LongSamplingCounter mDischargeDeepDozeCounter;
    public LongSamplingCounter mDischargeLightDozeCounter;
    public int mDischargePlugLevel;
    public LongSamplingCounter mDischargeScreenDozeCounter;
    public int mDischargeScreenDozeUnplugLevel;
    public int mDischargeScreenDozeUnplugLevelPermil;
    public LongSamplingCounter mDischargeScreenOffCounter;
    public int mDischargeScreenOffUnplugLevel;
    public int mDischargeScreenOffUnplugLevelCoulombCounter;
    public int mDischargeScreenOffUnplugLevelPermil;
    public int mDischargeScreenOnUnplugLevel;
    public int mDischargeScreenOnUnplugLevelCoulombCounter;
    public int mDischargeScreenOnUnplugLevelPermil;
    public final BatteryStats.LevelStepTracker mDischargeStepTracker;
    public int mDischargeSubScreenDozeUnplugLevelPermil;
    public int mDischargeSubScreenOnUnplugLevelPermil;
    public int mDischargeUnplugLevel;
    public int mDisplayId;
    public int mDisplayMismatchWtfCount;
    public final ArrayList mDrawTimers;
    public String mEndPlatformVersion;
    public final EnergyStatsRetriever mEnergyConsumerRetriever;
    protected EnergyConsumerStats.Config mEnergyConsumerStatsConfig;
    public int mEstimatedBatteryCapacityMah;
    public BatteryExternalStatsWorker mExternalSync;
    public boolean mFeatureComputeChargeTimeModel;
    public int mFlashlightOnNesting;
    public StopwatchTimer mFlashlightOnTimer;
    public final ArrayList mFlashlightTurnedOnTimers;
    public final FrameworkStatsLogger mFrameworkStatsLogger;
    public final ArrayList mFullTimers;
    public final ArrayList mFullWifiLockTimers;
    protected EnergyConsumerStats mGlobalEnergyConsumerStats;
    public boolean mGlobalWifiRunning;
    public StopwatchTimer mGlobalWifiRunningTimer;
    public final GnssPowerStatsCollector mGnssPowerStatsCollector;
    public int mGpsNesting;
    public int mGpsSignalQualityBin;
    public final StopwatchTimer[] mGpsSignalQualityTimer;
    public final MyHandler mHandler;
    public boolean mHasBluetoothReporting;
    public boolean mHasModemReporting;
    public boolean mHasSpeakerOutReporting;
    public boolean mHasWifiReporting;
    public boolean mHaveBatteryLevel;
    public int mHighDischargeAmountSinceCharge;
    public int mHighRefreshRateBin;
    public final StopwatchTimer[] mHighRefreshRateTimer;
    public final BatteryStatsHistory mHistory;
    public int mHotspotState;
    public boolean mIgnoreNextExternalStats;
    public int mInitStepMode;
    public boolean mInteractive;
    public StopwatchTimer mInteractiveTimer;
    public boolean mIsBuildTypeUser;
    public final boolean mIsCoulombCounterReported;
    public final boolean mIsRawSocReported;
    public boolean mIsServiceStateNr;
    public boolean mIsSubScreen;
    public boolean mIsSubScreenDoze;
    public boolean mIsSubScreenOn;
    protected KernelCpuSpeedReader[] mKernelCpuSpeedReaders;
    public final KernelMemoryBandwidthStats mKernelMemoryBandwidthStats;
    public final LongSparseArray mKernelMemoryStats;
    protected KernelSingleUidTimeReader mKernelSingleUidTimeReader;
    protected KernelWakelockReader mKernelWakelockReader;
    public final HashMap mKernelWakelockStats;
    public SpeakerOutEnergyInfo mLastAudioOutEnergyInfo;
    public long mLastBatteryCCUpdateTime;
    public long mLastBatteryRawUpdateTime;
    public final BluetoothActivityInfoCache mLastBluetoothActivityInfo;
    public long mLastCallbackTime;
    public int mLastChargeStepLevel;
    public int mLastDischargeStepLevel;
    public long mLastIdleTimeStartMs;
    public int mLastLearnedBatteryCapacityUah;
    public ModemActivityInfo mLastModemActivityInfo;
    public NetworkStats mLastModemNetworkStats;
    protected ArrayList mLastPartialTimers;
    public long mLastRpmStatsUpdateTimeMs;
    public long mLastWakeupElapsedTimeMs;
    public String mLastWakeupReason;
    public long mLastWakeupUptimeMs;
    public NetworkStats mLastWifiNetworkStats;
    public long mLastWriteTimeMs;
    public final BatteryStatsImpl$$ExternalSyntheticLambda2 mLongPlugInAlarmHandler;
    public long mLongestFullIdleTimeMs;
    public long mLongestLightIdleTimeMs;
    public int mLowDischargeAmountSinceCharge;
    public int mMaxChargeStepLevel;
    public int mMaxLearnedBatteryCapacityUah;
    public int mMinDischargeStepLevel;
    public int mMinLearnedBatteryCapacityUah;
    public StopwatchTimer mMobileActive5GTimer;
    public StopwatchTimer mMobileActiveTimer;
    public LongSamplingCounter mMobileRadioActiveAdjustedTime;
    public StopwatchTimer mMobileRadioActivePerAppTimer;
    public long mMobileRadioActiveStartTimeMs;
    public StopwatchTimer mMobileRadioActiveTimer;
    public LongSamplingCounter mMobileRadioActiveUnknownCount;
    public LongSamplingCounter mMobileRadioActiveUnknownTime;
    public MobileRadioPowerCalculator mMobileRadioPowerCalculator;
    public int mMobileRadioPowerState;
    public final MobileRadioPowerStatsCollector mMobileRadioPowerStatsCollector;
    public int mModStepMode;
    public ControllerActivityCounterImpl mModemActivity;
    public String[] mModemIfaces;
    public final Object mModemNetworkLock;
    public final MonotonicClock mMonotonicClock;
    public long mMonotonicEndTime;
    public long mMonotonicStartTime;
    public final LongSamplingCounter[] mNetworkByteActivityCounters;
    public ModemActivityCounterImpl mNetworkModemActivity;
    public final LongSamplingCounter[] mNetworkPacketActivityCounters;
    public final HashMap mNetworkStatsDeltaMap;
    public long mNextMaxDailyDeadlineMs;
    public long mNextMinDailyDeadlineMs;
    public boolean mNoAutoReset;
    public StopwatchTimer mNrNsaTimer;
    public int mNrState;
    public int mNumAllUidCpuTimeReads;
    public int mNumConnectivityChange;
    public long mNumSingleUidCpuTimeReads;
    public int mNumUidsRemoved;
    public boolean mOnBattery;
    protected boolean mOnBatteryInternal;
    public final TimeBase mOnBatteryScreenOffTimeBase;
    public final TimeBase mOnBatteryTimeBase;
    public int mOtgOnline;
    public int mPa_temp;
    protected ArrayList mPartialTimers;
    protected Queue mPendingRemovedUids;
    public boolean mPendingReportCharging;
    public final DisplayBatteryStats[] mPerDisplayBatteryStats;
    public boolean mPerProcStateCpuTimesAvailable;
    public final RadioAccessTechnologyBatteryStats[] mPerRatBatteryStats;
    public int mPhoneDataConnectionType;
    public final StopwatchTimer[] mPhoneDataConnectionsTimer;
    public boolean mPhoneOn;
    public StopwatchTimer mPhoneOnTimer;
    public int mPhoneServiceState;
    public int mPhoneServiceStateRaw;
    public StopwatchTimer mPhoneSignalScanningTimer;
    public int mPhoneSignalStrengthBin;
    public int mPhoneSignalStrengthBinRaw;
    public final StopwatchTimer[] mPhoneSignalStrengthsTimer;
    public int mPhoneSimStateRaw;
    public final PlatformIdleStateCallback mPlatformIdleStateCallback;
    protected PowerProfile mPowerProfile;
    public boolean mPowerSaveModeEnabled;
    public StopwatchTimer mPowerSaveModeEnabledTimer;
    public final SparseBooleanArray mPowerStatsCollectorEnabled;
    public final PowerStatsCollectorInjector mPowerStatsCollectorInjector;
    public PowerStatsStore mPowerStatsStore;
    protected final PowerStatsUidResolver mPowerStatsUidResolver;
    public boolean mPretendScreenOff;
    public int mProtectBatteryMode;
    public long mRealtimeStartUs;
    public long mRealtimeUs;
    public boolean mRecordAllHistory;
    public final HashMap mRpmStats;
    public boolean mSaveBatteryUsageStatsOnReset;
    public final StopwatchTimer[] mScreenAutoBrightnessTimer;
    public int mScreenBrightnessBin;
    public final StopwatchTimer[] mScreenBrightnessTimer;
    public StopwatchTimer mScreenDozeTimer;
    public boolean mScreenHighBrightness;
    public StopwatchTimer mScreenHighBrightnessTimer;
    public final HashMap mScreenOffRpmStats;
    public StopwatchTimer mScreenOnGpsTimer;
    public StopwatchTimer mScreenOnTimer;
    protected int mScreenState;
    public final HashMap mScreenWakeStats;
    public int mSensorNesting;
    public final SparseArray mSensorTimers;
    public boolean mShuttingDown;
    public int mSkin_temp;
    public final LongSamplingCounter[] mSpeakerCallTimeCounters;
    public final LongSamplingCounter[] mSpeakerMediaTimeCounters;
    public long mStartClockTimeMs;
    public int mStartCount;
    public String mStartPlatformVersion;
    public final AtomicFile mStatsFile;
    public final HistoryStepDetailsCalculatorImpl mStepDetailsCalculator;
    public final StopwatchTimer[] mSubHighRefreshRateTimer;
    public final StopwatchTimer[] mSubScreenAutoBrightnessTimer;
    public final StopwatchTimer[] mSubScreenBrightnessTimer;
    public StopwatchTimer mSubScreenDozeTimer;
    public StopwatchTimer mSubScreenHighBrightnessTimer;
    public StopwatchTimer mSubScreenOnTimer;
    public int mSubScreenPolicy;
    public int mSubScreenState;
    public final int mSub_batt_temp;
    public File mSystemDir;
    public boolean mSystemReady;
    protected SystemServerCpuThreadReader mSystemServerCpuThreadReader;
    public boolean mSystemServicesReady;
    public TelephonyManager mTelephonyManager;
    public long mTempTotalCpuSystemTimeUs;
    public long mTempTotalCpuUserTimeUs;
    public LongArrayMultiStateCounter.LongArrayContainer mTmpCpuTimeInFreq;
    public final RailStats mTmpRailStats;
    public RpmStats mTmpRpmStats;
    public final KernelWakelockStats mTmpWakelockStats;
    public final ConcurrentHashMap mTopAppStats;
    public long mTxPowerSharingDischargeMah;
    public boolean mTxPowerSharingOn;
    public StopwatchTimer mTxPowerSharingTimer;
    public final SparseArray mUidStats;
    public long mUptimeStartUs;
    public long mUptimeUs;
    public int mUsbDataState;
    protected UserInfoProvider mUserInfoProvider;
    public int mVideoOnNesting;
    public StopwatchTimer mVideoOnTimer;
    public final ArrayList mVideoTurnedOnTimers;
    public long[][] mWakeLockAllocationsUs;
    public boolean mWakeLockImportant;
    public int mWakeLockNesting;
    public final HashMap mWakeupReasonStats;
    public StopwatchTimer mWifiActiveTimer;
    public ControllerActivityCounterImpl mWifiActivity;
    public final SparseArray mWifiBatchedScanTimers;
    public int mWifiFullLockNesting;
    public String[] mWifiIfaces;
    public int mWifiMulticastNesting;
    public final ArrayList mWifiMulticastTimers;
    public StopwatchTimer mWifiMulticastWakelockTimer;
    public final Object mWifiNetworkLock;
    public boolean mWifiOn;
    public StopwatchTimer mWifiOnTimer;
    public WifiPowerCalculator mWifiPowerCalculator;
    public final WifiPowerStatsCollector mWifiPowerStatsCollector;
    public int mWifiRadioPowerState;
    public final ArrayList mWifiRunningTimers;
    public int mWifiScanNesting;
    public final ArrayList mWifiScanTimers;
    public int mWifiSignalStrengthBin;
    public final StopwatchTimer[] mWifiSignalStrengthsTimer;
    public int mWifiState;
    public final StopwatchTimer[] mWifiStateTimer;
    public final AnonymousClass2 mWifiStatsRetriever;
    public int mWifiSupplState;
    public final StopwatchTimer[] mWifiSupplStateTimer;
    public final ArrayList mWindowTimers;
    public final BatteryStatsImpl$$ExternalSyntheticLambda3 mWriteAsyncRunnable;
    public final ReentrantLock mWriteLock;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.power.stats.BatteryStatsImpl$1, reason: invalid class name */
    public final class AnonymousClass1 extends BatteryStats.LongCounter {
        public final long getCountForProcessState(int i) {
            return 0L;
        }

        public final long getCountLocked(int i) {
            return 0L;
        }

        public final void logState(Printer printer, String str) {
            printer.println(str + "mCount=0");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.power.stats.BatteryStatsImpl$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.power.stats.BatteryStatsImpl$4, reason: invalid class name */
    public final class AnonymousClass4 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ BatteryStatsImpl this$0;

        public /* synthetic */ AnonymousClass4(BatteryStatsImpl batteryStatsImpl, int i) {
            this.$r8$classId = i;
            this.this$0 = batteryStatsImpl;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    boolean booleanExtra = intent.getBooleanExtra("connected", false);
                    synchronized (this.this$0) {
                        BatteryStatsImpl batteryStatsImpl = this.this$0;
                        batteryStatsImpl.noteUsbConnectionStateLocked(batteryStatsImpl.mClock.elapsedRealtime(), this.this$0.mClock.uptimeMillis(), booleanExtra);
                    }
                    return;
                default:
                    final int intExtra = intent.getIntExtra("wifi_state", 14);
                    this.this$0.mHandler.post(new Runnable() { // from class: com.android.server.power.stats.BatteryStatsImpl$5$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            BatteryStatsImpl.AnonymousClass4 anonymousClass4 = BatteryStatsImpl.AnonymousClass4.this;
                            int i = intExtra;
                            synchronized (anonymousClass4.this$0) {
                                BatteryStatsImpl batteryStatsImpl2 = anonymousClass4.this$0;
                                long elapsedRealtime = batteryStatsImpl2.mClock.elapsedRealtime();
                                long uptimeMillis = anonymousClass4.this$0.mClock.uptimeMillis();
                                boolean z = i == 13;
                                if (batteryStatsImpl2.mHotspotState != i) {
                                    batteryStatsImpl2.mHotspotState = i;
                                    batteryStatsImpl2.mHistory.setWifiApState(z);
                                    batteryStatsImpl2.mHistory.writeHistoryItem(elapsedRealtime, uptimeMillis);
                                }
                            }
                        }
                    });
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.power.stats.BatteryStatsImpl$7, reason: invalid class name */
    public final class AnonymousClass7 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ BatteryStatsImpl this$0;
        public final /* synthetic */ long val$initialTimeMs;
        public final /* synthetic */ Object val$memStream;

        public /* synthetic */ AnonymousClass7(BatteryStatsImpl batteryStatsImpl, Object obj, long j, int i) {
            this.$r8$classId = i;
            this.this$0 = batteryStatsImpl;
            this.val$memStream = obj;
            this.val$initialTimeMs = j;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r10v10, types: [android.os.Parcel] */
        /* JADX WARN: Type inference failed for: r10v13 */
        /* JADX WARN: Type inference failed for: r10v14 */
        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this.this$0.mCheckinFile) {
                        long uptimeMillis = SystemClock.uptimeMillis();
                        FileOutputStream fileOutputStream = null;
                        try {
                            fileOutputStream = this.this$0.mDailyFile.startWrite();
                            ((ByteArrayOutputStream) this.val$memStream).writeTo(fileOutputStream);
                            fileOutputStream.flush();
                            this.this$0.mDailyFile.finishWrite(fileOutputStream);
                            FrameworkStatsLogger frameworkStatsLogger = this.this$0.mFrameworkStatsLogger;
                            long uptimeMillis2 = (this.val$initialTimeMs + SystemClock.uptimeMillis()) - uptimeMillis;
                            frameworkStatsLogger.getClass();
                            EventLogTags.writeCommitSysConfigFile("batterystats-daily", uptimeMillis2);
                        } catch (IOException e) {
                            Slog.w("BatteryStats", "Error writing battery daily items", e);
                            this.this$0.mDailyFile.failWrite(fileOutputStream);
                        }
                    }
                    return;
                default:
                    synchronized (this.this$0.mCheckinFile) {
                        long uptimeMillis3 = SystemClock.uptimeMillis();
                        FileOutputStream fileOutputStream2 = null;
                        try {
                            try {
                                fileOutputStream2 = this.this$0.mCheckinFile.startWrite();
                                fileOutputStream2.write(((Parcel) this.val$memStream).marshall());
                                fileOutputStream2.flush();
                                this.this$0.mCheckinFile.finishWrite(fileOutputStream2);
                                FrameworkStatsLogger frameworkStatsLogger2 = this.this$0.mFrameworkStatsLogger;
                                long uptimeMillis4 = (this.val$initialTimeMs + SystemClock.uptimeMillis()) - uptimeMillis3;
                                frameworkStatsLogger2.getClass();
                                EventLogTags.writeCommitSysConfigFile("batterystats-checkin", uptimeMillis4);
                                this = (Parcel) this.val$memStream;
                            } catch (IOException e2) {
                                Slog.w("BatteryStats", "Error writing checkin battery statistics", e2);
                                this.this$0.mCheckinFile.failWrite(fileOutputStream2);
                                this = (Parcel) this.val$memStream;
                            }
                            this.recycle();
                        } catch (Throwable th) {
                            ((Parcel) this.val$memStream).recycle();
                            throw th;
                        }
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BatchTimer extends Timer {
        public boolean mInDischarge;
        public long mLastAddedDurationUs;
        public long mLastAddedTimeUs;

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public final int computeCurrentCountLocked() {
            return this.mCount;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public final long computeRunTimeLocked(long j, long j2) {
            long j3 = this.mLastAddedTimeUs > 0 ? this.mLastAddedDurationUs - j2 : 0L;
            if (j3 <= 0) {
                return this.mTotalTimeUs;
            }
            this.mTotalTimeUs = j3;
            return j3;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public final void logState(Printer printer, String str) {
            super.logState(printer, str);
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "mLastAddedTime=");
            m.append(this.mLastAddedTimeUs);
            m.append(" mLastAddedDuration=");
            m.append(this.mLastAddedDurationUs);
            printer.println(m.toString());
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void onTimeStarted(long j, long j2) {
            recomputeLastDuration(j, false);
            this.mInDischarge = true;
            if (this.mLastAddedTimeUs == j) {
                this.mTotalTimeUs += this.mLastAddedDurationUs;
            }
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void onTimeStopped(long j, long j2, long j3) {
            recomputeLastDuration(j, false);
            this.mInDischarge = false;
            this.mTotalTimeUs = computeRunTimeLocked(j3, j);
            this.mCount = computeCurrentCountLocked();
        }

        public final void recomputeLastDuration(long j, boolean z) {
            long j2 = this.mLastAddedTimeUs > 0 ? this.mLastAddedDurationUs - j : 0L;
            if (j2 > 0) {
                if (this.mInDischarge) {
                    this.mTotalTimeUs -= j2;
                }
                if (z) {
                    this.mLastAddedTimeUs = 0L;
                } else {
                    this.mLastAddedTimeUs = j;
                    this.mLastAddedDurationUs -= j2;
                }
            }
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final boolean reset(long j, boolean z) {
            recomputeLastDuration(j, true);
            boolean z2 = this.mLastAddedTimeUs == j;
            super.reset(j, false);
            return !z2;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface BatteryCallback {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BatteryStatsConfig {
        public final Long mDefaultPowerStatsThrottlePeriod;
        public final int mFlags;
        public final Map mPowerStatsThrottlePeriods;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Builder {
            public static final long DEFAULT_POWER_STATS_THROTTLE_PERIOD = TimeUnit.HOURS.toMillis(1);
            public static final long DEFAULT_POWER_STATS_THROTTLE_PERIOD_CPU = TimeUnit.MINUTES.toMillis(1);
            public long mDefaultPowerStatsThrottlePeriod = DEFAULT_POWER_STATS_THROTTLE_PERIOD;
            public final Map mPowerStatsThrottlePeriods;
            public boolean mResetOnUnplugAfterSignificantCharge;
            public boolean mResetOnUnplugHighBatteryLevel;

            public Builder() {
                HashMap hashMap = new HashMap();
                this.mPowerStatsThrottlePeriods = hashMap;
                this.mResetOnUnplugHighBatteryLevel = true;
                this.mResetOnUnplugAfterSignificantCharge = true;
                hashMap.put(BatteryConsumer.powerComponentIdToString(1), Long.valueOf(DEFAULT_POWER_STATS_THROTTLE_PERIOD_CPU));
            }
        }

        public BatteryStatsConfig() {
            this.mFlags = 0;
            this.mDefaultPowerStatsThrottlePeriod = 0L;
            this.mPowerStatsThrottlePeriods = Map.of();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public BatteryStatsConfig(Builder builder) {
            boolean z = builder.mResetOnUnplugHighBatteryLevel;
            this.mFlags = builder.mResetOnUnplugAfterSignificantCharge ? (z ? 1 : 0) | 2 : z;
            this.mDefaultPowerStatsThrottlePeriod = Long.valueOf(builder.mDefaultPowerStatsThrottlePeriod);
            this.mPowerStatsThrottlePeriods = builder.mPowerStatsThrottlePeriods;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class BinderCallStats {
        public Class binderClass;
        public long callCount;
        public String methodName;
        public long recordedCallCount;
        public long recordedCpuTimeMicros;
        public int transactionCode;

        public void ensureMethodName(BinderTransactionNameResolver binderTransactionNameResolver) {
            if (this.methodName == null) {
                this.methodName = binderTransactionNameResolver.getMethodName(this.binderClass, this.transactionCode);
            }
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof BinderCallStats)) {
                return false;
            }
            BinderCallStats binderCallStats = (BinderCallStats) obj;
            return this.binderClass.equals(binderCallStats.binderClass) && this.transactionCode == binderCallStats.transactionCode;
        }

        public final int hashCode() {
            return (this.binderClass.hashCode() * 31) + this.transactionCode;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("BinderCallStats{");
            sb.append(this.binderClass);
            sb.append(" transaction=");
            sb.append(this.transactionCode);
            sb.append(" callCount=");
            sb.append(this.callCount);
            sb.append(" recordedCallCount=");
            sb.append(this.recordedCallCount);
            sb.append(" recorderCpuTimeMicros=");
            return AudioConfig$$ExternalSyntheticOutline0.m(sb, this.recordedCpuTimeMicros, "}");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BluetoothActivityInfoCache {
        public long energy;
        public long idleTimeMs;
        public long rxTimeMs;
        public long txTimeMs;
        public SparseLongArray uidRxBytes;
        public SparseLongArray uidTxBytes;

        public final void set(BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo) {
            this.idleTimeMs = bluetoothActivityEnergyInfo.getControllerIdleTimeMillis();
            this.rxTimeMs = bluetoothActivityEnergyInfo.getControllerRxTimeMillis();
            this.txTimeMs = bluetoothActivityEnergyInfo.getControllerTxTimeMillis();
            this.energy = bluetoothActivityEnergyInfo.getControllerEnergyUsed();
            if (bluetoothActivityEnergyInfo.getUidTraffic().isEmpty()) {
                return;
            }
            for (UidTraffic uidTraffic : bluetoothActivityEnergyInfo.getUidTraffic()) {
                this.uidRxBytes.put(uidTraffic.getUid(), uidTraffic.getRxBytes());
                this.uidTxBytes.put(uidTraffic.getUid(), uidTraffic.getTxBytes());
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BluetoothStatsRetrieverImpl {
        public final BluetoothManager mBluetoothManager;

        public BluetoothStatsRetrieverImpl(BluetoothManager bluetoothManager) {
            this.mBluetoothManager = bluetoothManager;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Constants extends ContentObserver {
        public int BATTERY_CHARGED_DELAY_MS;
        public int BATTERY_CHARGING_ENFORCE_LEVEL;
        public long BATTERY_LEVEL_COLLECTION_DELAY_MS;
        public long EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS;
        public long KERNEL_UID_READERS_THROTTLE_TIME;
        public int MAX_HISTORY_BUFFER;
        public int MAX_HISTORY_FILES;
        public int PER_UID_MODEM_MODEL;
        public boolean PHONE_ON_EXTERNAL_STATS_COLLECTION;
        public long PROC_STATE_CHANGE_COLLECTION_DELAY_MS;
        public int RESET_WHILE_PLUGGED_IN_MINIMUM_DURATION_HOURS;
        public boolean TRACK_CPU_ACTIVE_CLUSTER_TIME;
        public long UID_REMOVE_DELAY_MS;
        public final KeyValueListParser mParser;
        public ContentResolver mResolver;

        public Constants(MyHandler myHandler) {
            super(myHandler);
            this.TRACK_CPU_ACTIVE_CLUSTER_TIME = true;
            this.UID_REMOVE_DELAY_MS = 300000L;
            this.EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS = 600000L;
            this.BATTERY_LEVEL_COLLECTION_DELAY_MS = 300000L;
            this.PROC_STATE_CHANGE_COLLECTION_DELAY_MS = 60000L;
            this.BATTERY_CHARGED_DELAY_MS = 900000;
            this.BATTERY_CHARGING_ENFORCE_LEVEL = 90;
            this.PER_UID_MODEM_MODEL = 2;
            this.PHONE_ON_EXTERNAL_STATS_COLLECTION = true;
            this.RESET_WHILE_PLUGGED_IN_MINIMUM_DURATION_HOURS = 47;
            this.mParser = new KeyValueListParser(',');
            if (BatteryStats.isLowRamDevice()) {
                this.MAX_HISTORY_FILES = 64;
                this.MAX_HISTORY_BUFFER = EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT;
            } else {
                this.MAX_HISTORY_FILES = 32;
                this.MAX_HISTORY_BUFFER = 131072;
            }
        }

        public void onChange() {
            BatteryStatsImpl.this.mHistory.setMaxHistoryFiles(this.MAX_HISTORY_FILES);
            BatteryStatsImpl.this.mHistory.setMaxHistoryBufferSize(this.MAX_HISTORY_BUFFER);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (uri.equals(Settings.Global.getUriFor("battery_charging_state_update_delay"))) {
                synchronized (BatteryStatsImpl.this) {
                    updateBatteryChargedDelayMsLocked();
                }
                return;
            }
            if (uri.equals(Settings.Global.getUriFor("battery_charging_state_enforce_level"))) {
                synchronized (BatteryStatsImpl.this) {
                    updateBatteryChargingEnforceLevelLocked();
                }
                return;
            }
            if (uri.equals(Settings.System.getUriFor("screen_brightness_mode"))) {
                synchronized (BatteryStatsImpl.this) {
                    try {
                        BatteryStatsImpl.this.noteScreenAutoBrightnessLocked(Settings.System.getInt(this.mResolver, "screen_brightness_mode"));
                    } catch (Settings.SettingNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
            if (uri.equals(Settings.Secure.getUriFor("refresh_rate_mode"))) {
                synchronized (BatteryStatsImpl.this) {
                    updateDisplayHighRefreshRateLocked();
                }
            } else {
                if (!uri.equals(Settings.Global.getUriFor("protect_battery"))) {
                    updateConstants();
                    return;
                }
                synchronized (BatteryStatsImpl.this) {
                    Slog.i("BatteryStatsImpl", "Observer");
                    updateProtectBatteryModeLocked();
                }
            }
        }

        public final void updateBatteryChargedDelayMsLocked() {
            int i = Settings.Global.getInt(this.mResolver, "battery_charging_state_update_delay", -1);
            if (i < 0) {
                i = this.mParser.getInt("battery_charged_delay_ms", 900000);
            }
            this.BATTERY_CHARGED_DELAY_MS = i;
            BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
            if (batteryStatsImpl.mHandler.hasCallbacks(batteryStatsImpl.mDeferSetCharging)) {
                BatteryStatsImpl batteryStatsImpl2 = BatteryStatsImpl.this;
                batteryStatsImpl2.mHandler.removeCallbacks(batteryStatsImpl2.mDeferSetCharging);
                BatteryStatsImpl batteryStatsImpl3 = BatteryStatsImpl.this;
                batteryStatsImpl3.mHandler.postDelayed(batteryStatsImpl3.mDeferSetCharging, this.BATTERY_CHARGED_DELAY_MS);
            }
        }

        public final void updateBatteryChargingEnforceLevelLocked() {
            int i = this.BATTERY_CHARGING_ENFORCE_LEVEL;
            int i2 = Settings.Global.getInt(this.mResolver, "battery_charging_state_enforce_level", -1);
            if (i2 < 0) {
                i2 = this.mParser.getInt("battery_charging_enforce_level", 90);
            }
            this.BATTERY_CHARGING_ENFORCE_LEVEL = i2;
            BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
            int i3 = batteryStatsImpl.mLastChargeStepLevel;
            if (i2 > i3 || i3 >= i) {
                return;
            }
            batteryStatsImpl.setChargingLocked(true);
        }

        public final void updateConstants() {
            synchronized (BatteryStatsImpl.this) {
                try {
                    this.mParser.setString(Settings.Global.getString(this.mResolver, "battery_stats_constants"));
                } catch (IllegalArgumentException e) {
                    Slog.e("BatteryStatsImpl", "Bad batterystats settings", e);
                }
                this.TRACK_CPU_ACTIVE_CLUSTER_TIME = this.mParser.getBoolean("track_cpu_active_cluster_time", true);
                updateKernelUidReadersThrottleTime(this.KERNEL_UID_READERS_THROTTLE_TIME, this.mParser.getLong("kernel_uid_readers_throttle_time", 1000L));
                this.UID_REMOVE_DELAY_MS = this.mParser.getLong("uid_remove_delay_ms", 300000L);
                BatteryStatsImpl.this.clearPendingRemovedUidsLocked();
                this.EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS = this.mParser.getLong("external_stats_collection_rate_limit_ms", 600000L);
                this.BATTERY_LEVEL_COLLECTION_DELAY_MS = this.mParser.getLong("battery_level_collection_delay_ms", 300000L);
                this.PROC_STATE_CHANGE_COLLECTION_DELAY_MS = this.mParser.getLong("procstate_change_collection_delay_ms", 60000L);
                int i = 64;
                this.MAX_HISTORY_FILES = this.mParser.getInt("max_history_files", BatteryStats.isLowRamDevice() ? 64 : 32);
                KeyValueListParser keyValueListParser = this.mParser;
                if (!BatteryStats.isLowRamDevice()) {
                    i = 128;
                }
                this.MAX_HISTORY_BUFFER = keyValueListParser.getInt("max_history_buffer_kb", i) * 1024;
                String string = this.mParser.getString("per_uid_modem_power_model", "");
                string.getClass();
                int i2 = 2;
                if (!string.equals("modem_activity_info_rx_tx")) {
                    if (string.equals("mobile_radio_active_time")) {
                        i2 = 1;
                    } else {
                        Slog.w("BatteryStatsImpl", "Unexpected per uid modem model name (" + string + ")");
                    }
                }
                this.PER_UID_MODEM_MODEL = i2;
                this.PHONE_ON_EXTERNAL_STATS_COLLECTION = this.mParser.getBoolean("phone_on_external_stats_collection", true);
                this.RESET_WHILE_PLUGGED_IN_MINIMUM_DURATION_HOURS = this.mParser.getInt("reset_while_plugged_in_minimum_duration_hours", 47);
                updateBatteryChargedDelayMsLocked();
                updateBatteryChargingEnforceLevelLocked();
                onChange();
            }
        }

        public final void updateDisplayHighRefreshRateLocked() {
            try {
                int i = Settings.Secure.getInt(this.mResolver, "refresh_rate_mode");
                BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
                batteryStatsImpl.noteSubDisplayHighRefreshRateLocked(batteryStatsImpl.mSubScreenState, i);
                BatteryStatsImpl.this.noteDisplayHighRefreshRateLocked(i);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
        }

        public final void updateKernelUidReadersThrottleTime(long j, long j2) {
            this.KERNEL_UID_READERS_THROTTLE_TIME = j2;
            if (j != j2) {
                BatteryStatsImpl.this.mCpuUidUserSysTimeReader.setThrottle(j2);
                BatteryStatsImpl.this.mCpuUidFreqTimeReader.setThrottle(this.KERNEL_UID_READERS_THROTTLE_TIME);
                BatteryStatsImpl.this.mCpuUidActiveTimeReader.setThrottle(this.KERNEL_UID_READERS_THROTTLE_TIME);
                BatteryStatsImpl.this.mCpuUidClusterTimeReader.setThrottle(this.KERNEL_UID_READERS_THROTTLE_TIME);
            }
        }

        public final void updateProtectBatteryModeLocked() {
            int i = Settings.Global.getInt(this.mResolver, "protect_battery", 0);
            if (i < 0 || i >= BatteryStats.NUM_PROTECT_BATTERY_MODE_TYPES) {
                Slog.w("BatteryStatsImpl", BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "Unknown protect battery type ", " was specified."), new Throwable());
                return;
            }
            BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
            if (batteryStatsImpl.mProtectBatteryMode != i) {
                batteryStatsImpl.mProtectBatteryMode = i;
                batteryStatsImpl.mHistory.setProtectBatteryState(i);
                BatteryStatsImpl batteryStatsImpl2 = BatteryStatsImpl.this;
                batteryStatsImpl2.mHistory.writeHistoryItem(batteryStatsImpl2.mClock.elapsedRealtime(), BatteryStatsImpl.this.mClock.uptimeMillis());
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ControllerActivityCounterImpl extends BatteryStats.ControllerActivityCounter implements Parcelable {
        public final Clock mClock;
        public TimeMultiStateCounter mIdleTimeMillis;
        public final LongSamplingCounter mMonitoredRailChargeConsumedMaMs;
        public final int mNumTxStates;
        public final LongSamplingCounter mPowerDrainMaMs;
        public int mProcessState;
        public TimeMultiStateCounter mRxTimeMillis;
        public final LongSamplingCounter mScanTimeMillis;
        public final LongSamplingCounter mSleepTimeMillis;
        public final TimeBase mTimeBase;
        public TimeMultiStateCounter[] mTxTimeMillis;

        /* renamed from: -$$Nest$mgetOrCreateRxTimeCounter, reason: not valid java name */
        public static TimeMultiStateCounter m838$$Nest$mgetOrCreateRxTimeCounter(ControllerActivityCounterImpl controllerActivityCounterImpl) {
            if (controllerActivityCounterImpl.mRxTimeMillis == null) {
                controllerActivityCounterImpl.mRxTimeMillis = controllerActivityCounterImpl.createTimeMultiStateCounter();
            }
            return controllerActivityCounterImpl.mRxTimeMillis;
        }

        /* renamed from: -$$Nest$mgetOrCreateTxTimeCounters, reason: not valid java name */
        public static TimeMultiStateCounter[] m839$$Nest$mgetOrCreateTxTimeCounters(ControllerActivityCounterImpl controllerActivityCounterImpl) {
            if (controllerActivityCounterImpl.mTxTimeMillis == null) {
                controllerActivityCounterImpl.mTxTimeMillis = new TimeMultiStateCounter[controllerActivityCounterImpl.mNumTxStates];
                for (int i = 0; i < controllerActivityCounterImpl.mNumTxStates; i++) {
                    controllerActivityCounterImpl.mTxTimeMillis[i] = controllerActivityCounterImpl.createTimeMultiStateCounter();
                }
            }
            return controllerActivityCounterImpl.mTxTimeMillis;
        }

        /* renamed from: -$$Nest$msetState, reason: not valid java name */
        public static void m840$$Nest$msetState(ControllerActivityCounterImpl controllerActivityCounterImpl, int i, long j) {
            controllerActivityCounterImpl.mProcessState = i;
            TimeMultiStateCounter timeMultiStateCounter = controllerActivityCounterImpl.mIdleTimeMillis;
            if (timeMultiStateCounter != null) {
                TimeMultiStateCounter.m842$$Nest$msetState(timeMultiStateCounter, i, j);
            }
            TimeMultiStateCounter timeMultiStateCounter2 = controllerActivityCounterImpl.mRxTimeMillis;
            if (timeMultiStateCounter2 != null) {
                TimeMultiStateCounter.m842$$Nest$msetState(timeMultiStateCounter2, i, j);
            }
            if (controllerActivityCounterImpl.mTxTimeMillis == null) {
                return;
            }
            int i2 = 0;
            while (true) {
                TimeMultiStateCounter[] timeMultiStateCounterArr = controllerActivityCounterImpl.mTxTimeMillis;
                if (i2 >= timeMultiStateCounterArr.length) {
                    return;
                }
                TimeMultiStateCounter.m842$$Nest$msetState(timeMultiStateCounterArr[i2], i, j);
                i2++;
            }
        }

        public ControllerActivityCounterImpl(int i, Clock clock, TimeBase timeBase) {
            this.mClock = clock;
            this.mTimeBase = timeBase;
            this.mNumTxStates = i;
            this.mScanTimeMillis = new LongSamplingCounter(timeBase);
            this.mSleepTimeMillis = new LongSamplingCounter(timeBase);
            this.mPowerDrainMaMs = new LongSamplingCounter(timeBase);
            this.mMonitoredRailChargeConsumedMaMs = new LongSamplingCounter(timeBase);
        }

        public static void writeTimeMultiStateCounter(Parcel parcel, TimeMultiStateCounter timeMultiStateCounter) {
            if (timeMultiStateCounter == null) {
                parcel.writeBoolean(false);
            } else {
                parcel.writeBoolean(true);
                timeMultiStateCounter.mCounter.writeToParcel(parcel, 0);
            }
        }

        public final TimeMultiStateCounter createTimeMultiStateCounter() {
            long elapsedRealtime = this.mClock.elapsedRealtime();
            TimeMultiStateCounter timeMultiStateCounter = new TimeMultiStateCounter(this.mTimeBase, 5, elapsedRealtime);
            timeMultiStateCounter.mCounter.setState(BatteryStats.mapUidProcessStateToBatteryConsumerProcessState(this.mProcessState), elapsedRealtime);
            timeMultiStateCounter.mCounter.updateValue(0L, elapsedRealtime);
            return timeMultiStateCounter;
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        public final BatteryStats.LongCounter getIdleTimeCounter() {
            TimeMultiStateCounter timeMultiStateCounter = this.mIdleTimeMillis;
            return timeMultiStateCounter == null ? BatteryStatsImpl.ZERO_LONG_COUNTER : timeMultiStateCounter;
        }

        public final BatteryStats.LongCounter getMonitoredRailChargeConsumedMaMs() {
            return this.mMonitoredRailChargeConsumedMaMs;
        }

        public final BatteryStats.LongCounter getPowerCounter() {
            return this.mPowerDrainMaMs;
        }

        public final BatteryStats.LongCounter getRxTimeCounter() {
            TimeMultiStateCounter timeMultiStateCounter = this.mRxTimeMillis;
            return timeMultiStateCounter == null ? BatteryStatsImpl.ZERO_LONG_COUNTER : timeMultiStateCounter;
        }

        public final BatteryStats.LongCounter getScanTimeCounter() {
            return this.mScanTimeMillis;
        }

        public final BatteryStats.LongCounter getSleepTimeCounter() {
            return this.mSleepTimeMillis;
        }

        public final BatteryStats.LongCounter[] getTxTimeCounters() {
            TimeMultiStateCounter[] timeMultiStateCounterArr = this.mTxTimeMillis;
            return timeMultiStateCounterArr == null ? BatteryStatsImpl.ZERO_LONG_COUNTER_ARRAY : timeMultiStateCounterArr;
        }

        public final void readSummaryFromParcel(Parcel parcel) {
            TimeMultiStateCounter[] timeMultiStateCounterArr = null;
            this.mIdleTimeMillis = parcel.readBoolean() ? TimeMultiStateCounter.m843$$Nest$smreadFromParcel(parcel, this.mTimeBase, this.mClock.elapsedRealtime()) : null;
            LongSamplingCounter longSamplingCounter = this.mScanTimeMillis;
            longSamplingCounter.getClass();
            longSamplingCounter.mCount = parcel.readLong();
            LongSamplingCounter longSamplingCounter2 = this.mSleepTimeMillis;
            longSamplingCounter2.getClass();
            longSamplingCounter2.mCount = parcel.readLong();
            this.mRxTimeMillis = parcel.readBoolean() ? TimeMultiStateCounter.m843$$Nest$smreadFromParcel(parcel, this.mTimeBase, this.mClock.elapsedRealtime()) : null;
            TimeBase timeBase = this.mTimeBase;
            int i = this.mNumTxStates;
            if (parcel.readBoolean()) {
                int readInt = parcel.readInt();
                boolean z = readInt == i;
                TimeMultiStateCounter[] timeMultiStateCounterArr2 = new TimeMultiStateCounter[readInt];
                for (int i2 = 0; i2 < readInt; i2++) {
                    TimeMultiStateCounter m843$$Nest$smreadFromParcel = TimeMultiStateCounter.m843$$Nest$smreadFromParcel(parcel, timeBase, this.mClock.elapsedRealtime());
                    if (m843$$Nest$smreadFromParcel != null) {
                        timeMultiStateCounterArr2[i2] = m843$$Nest$smreadFromParcel;
                    } else {
                        z = false;
                    }
                }
                if (z) {
                    timeMultiStateCounterArr = timeMultiStateCounterArr2;
                }
            }
            this.mTxTimeMillis = timeMultiStateCounterArr;
            LongSamplingCounter longSamplingCounter3 = this.mPowerDrainMaMs;
            longSamplingCounter3.getClass();
            longSamplingCounter3.mCount = parcel.readLong();
            LongSamplingCounter longSamplingCounter4 = this.mMonitoredRailChargeConsumedMaMs;
            longSamplingCounter4.getClass();
            longSamplingCounter4.mCount = parcel.readLong();
        }

        public final void reset(long j) {
            BatteryStatsImpl.resetIfNotNull(this.mIdleTimeMillis, j);
            this.mScanTimeMillis.reset(j, false);
            this.mSleepTimeMillis.reset(j, false);
            BatteryStatsImpl.resetIfNotNull(this.mRxTimeMillis, j);
            BatteryStatsImpl.resetIfNotNull(this.mTxTimeMillis, j);
            this.mPowerDrainMaMs.reset(j, false);
            this.mMonitoredRailChargeConsumedMaMs.reset(j, false);
        }

        public final void writeSummaryToParcel(Parcel parcel) {
            writeTimeMultiStateCounter(parcel, this.mIdleTimeMillis);
            parcel.writeLong(this.mScanTimeMillis.mCount);
            parcel.writeLong(this.mSleepTimeMillis.mCount);
            writeTimeMultiStateCounter(parcel, this.mRxTimeMillis);
            TimeMultiStateCounter[] timeMultiStateCounterArr = this.mTxTimeMillis;
            if (timeMultiStateCounterArr != null) {
                parcel.writeBoolean(true);
                parcel.writeInt(timeMultiStateCounterArr.length);
                for (TimeMultiStateCounter timeMultiStateCounter : timeMultiStateCounterArr) {
                    timeMultiStateCounter.mCounter.writeToParcel(parcel, 0);
                }
            } else {
                parcel.writeBoolean(false);
            }
            parcel.writeLong(this.mPowerDrainMaMs.mCount);
            parcel.writeLong(this.mMonitoredRailChargeConsumedMaMs.mCount);
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            writeTimeMultiStateCounter(parcel, this.mIdleTimeMillis);
            parcel.writeLong(this.mScanTimeMillis.mCount);
            parcel.writeLong(this.mSleepTimeMillis.mCount);
            writeTimeMultiStateCounter(parcel, this.mRxTimeMillis);
            TimeMultiStateCounter[] timeMultiStateCounterArr = this.mTxTimeMillis;
            if (timeMultiStateCounterArr != null) {
                parcel.writeBoolean(true);
                parcel.writeInt(timeMultiStateCounterArr.length);
                for (TimeMultiStateCounter timeMultiStateCounter : timeMultiStateCounterArr) {
                    timeMultiStateCounter.mCounter.writeToParcel(parcel, 0);
                }
            } else {
                parcel.writeBoolean(false);
            }
            parcel.writeLong(this.mPowerDrainMaMs.mCount);
            parcel.writeLong(this.mMonitoredRailChargeConsumedMaMs.mCount);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Counter extends BatteryStats.Counter implements TimeBaseObs {
        public final AtomicInteger mCount = new AtomicInteger();
        public final TimeBase mTimeBase;

        public Counter(TimeBase timeBase) {
            this.mTimeBase = timeBase;
            timeBase.add(this);
        }

        public final void addAtomic(int i) {
            if (this.mTimeBase.mRunning) {
                this.mCount.addAndGet(i);
            }
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void detach() {
            this.mTimeBase.remove(this);
        }

        public final int getCountLocked(int i) {
            return this.mCount.get();
        }

        public final void logState(Printer printer, String str) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "mCount=");
            m.append(this.mCount.get());
            printer.println(m.toString());
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void onTimeStarted(long j, long j2) {
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void onTimeStopped(long j, long j2, long j3) {
        }

        public void readSummaryFromParcelLocked(Parcel parcel) {
            this.mCount.set(parcel.readInt());
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final boolean reset(long j, boolean z) {
            this.mCount.set(0);
            return true;
        }

        public void stepAtomic() {
            if (this.mTimeBase.mRunning) {
                this.mCount.incrementAndGet();
            }
        }

        public void writeSummaryFromParcelLocked(Parcel parcel) {
            parcel.writeInt(this.mCount.get());
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class CpuDeltaPowerAccumulator {
        public final CpuPowerCalculator mCalculator;
        public final double[] totalClusterChargesMah;
        public Uid mCachedUid = null;
        public double[] mUidClusterCache = null;
        public final ArrayMap perUidCpuClusterChargesMah = new ArrayMap();

        public CpuDeltaPowerAccumulator(CpuPowerCalculator cpuPowerCalculator, int i) {
            this.mCalculator = cpuPowerCalculator;
            this.totalClusterChargesMah = new double[i];
        }

        public final void addCpuClusterSpeedDurationsMs(Uid uid, int i, int i2, long j) {
            double[] orCreateUidCpuClusterCharges = getOrCreateUidCpuClusterCharges(uid);
            double d = this.mCalculator.mPerCpuFreqPowerEstimatorsByCluster[i][i2].mAveragePowerMahPerMs * j;
            orCreateUidCpuClusterCharges[i] = orCreateUidCpuClusterCharges[i] + d;
            double[] dArr = this.totalClusterChargesMah;
            dArr[i] = dArr[i] + d;
        }

        public final double[] getOrCreateUidCpuClusterCharges(Uid uid) {
            if (uid == this.mCachedUid) {
                return this.mUidClusterCache;
            }
            double[] dArr = (double[]) this.perUidCpuClusterChargesMah.get(uid);
            if (dArr == null) {
                dArr = new double[this.totalClusterChargesMah.length];
                this.perUidCpuClusterChargesMah.put(uid, dArr);
            }
            this.mCachedUid = uid;
            this.mUidClusterCache = dArr;
            return dArr;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CustomTelephonyCallback extends TelephonyCallback implements TelephonyCallback.ServiceStateListener {
        public CustomTelephonyCallback() {
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x002c A[Catch: all -> 0x0047, TryCatch #0 {all -> 0x0047, blocks: (B:3:0x0001, B:10:0x002c, B:11:0x003a, B:14:0x0040, B:15:0x0045, B:18:0x0049, B:19:0x004e, B:24:0x0035, B:25:0x0038), top: B:2:0x0001 }] */
        /* JADX WARN: Removed duplicated region for block: B:21:0x002f  */
        @Override // android.telephony.TelephonyCallback.ServiceStateListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onServiceStateChanged(android.telephony.ServiceState r10) {
            /*
                r9 = this;
                monitor-enter(r9)
                com.android.server.power.stats.BatteryStatsImpl r0 = com.android.server.power.stats.BatteryStatsImpl.this     // Catch: java.lang.Throwable -> L47
                com.android.internal.os.Clock r0 = r0.mClock     // Catch: java.lang.Throwable -> L47
                long r0 = r0.elapsedRealtime()     // Catch: java.lang.Throwable -> L47
                com.android.server.power.stats.BatteryStatsImpl r2 = com.android.server.power.stats.BatteryStatsImpl.this     // Catch: java.lang.Throwable -> L47
                com.android.internal.os.Clock r2 = r2.mClock     // Catch: java.lang.Throwable -> L47
                r2.uptimeMillis()     // Catch: java.lang.Throwable -> L47
                int r2 = r10.getDataNetworkType()     // Catch: java.lang.Throwable -> L47
                int r10 = r10.getNrState()     // Catch: java.lang.Throwable -> L47
                com.android.server.power.stats.BatteryStatsImpl r3 = com.android.server.power.stats.BatteryStatsImpl.this     // Catch: java.lang.Throwable -> L47
                int r4 = r3.mMobileRadioPowerState     // Catch: java.lang.Throwable -> L47
                r5 = 2
                r6 = 1
                r7 = 0
                r8 = 3
                if (r4 == r5) goto L27
                if (r4 != r8) goto L25
                goto L27
            L25:
                r4 = r7
                goto L28
            L27:
                r4 = r6
            L28:
                r5 = 20
                if (r2 != r5) goto L2f
                r3.mIsServiceStateNr = r6     // Catch: java.lang.Throwable -> L47
                goto L3a
            L2f:
                r5 = 13
                if (r2 != r5) goto L38
                if (r10 != r8) goto L38
                r3.mIsServiceStateNr = r6     // Catch: java.lang.Throwable -> L47
                goto L3a
            L38:
                r3.mIsServiceStateNr = r7     // Catch: java.lang.Throwable -> L47
            L3a:
                boolean r10 = r3.mIsServiceStateNr     // Catch: java.lang.Throwable -> L47
                if (r10 == 0) goto L49
                if (r4 == 0) goto L49
                com.android.server.power.stats.BatteryStatsImpl$StopwatchTimer r10 = r3.mMobileActive5GTimer     // Catch: java.lang.Throwable -> L47
                r10.startRunningLocked(r0)     // Catch: java.lang.Throwable -> L47
                monitor-exit(r9)     // Catch: java.lang.Throwable -> L47
                return
            L47:
                r10 = move-exception
                goto L50
            L49:
                com.android.server.power.stats.BatteryStatsImpl$StopwatchTimer r10 = r3.mMobileActive5GTimer     // Catch: java.lang.Throwable -> L47
                r10.stopAllRunningLocked(r0)     // Catch: java.lang.Throwable -> L47
                monitor-exit(r9)     // Catch: java.lang.Throwable -> L47
                return
            L50:
                monitor-exit(r9)     // Catch: java.lang.Throwable -> L47
                throw r10
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.stats.BatteryStatsImpl.CustomTelephonyCallback.onServiceStateChanged(android.telephony.ServiceState):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DisplayBatteryStats {
        public int screenBrightnessBin;
        public StopwatchTimer[] screenBrightnessTimers;
        public StopwatchTimer screenDozeTimer;
        public StopwatchTimer screenOnTimer;
        public int screenState;
        public int screenStateAtLastEnergyMeasurement;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DualTimer extends DurationTimer {
        public final DurationTimer mSubTimer;

        public DualTimer(Clock clock, Uid uid, int i, ArrayList arrayList, TimeBase timeBase, TimeBase timeBase2) {
            super(clock, uid, i, arrayList, timeBase);
            this.mSubTimer = new DurationTimer(clock, uid, i, null, timeBase2);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer, com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void detach() {
            this.mSubTimer.detach();
            super.detach();
        }

        public final BatteryStats.Timer getSubTimer() {
            return this.mSubTimer;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.DurationTimer, com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer, com.android.server.power.stats.BatteryStatsImpl.Timer
        public final void readSummaryFromParcelLocked(Parcel parcel) {
            super.readSummaryFromParcelLocked(parcel);
            this.mSubTimer.readSummaryFromParcelLocked(parcel);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.DurationTimer, com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer, com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final boolean reset(long j, boolean z) {
            return !((!super.reset(j, z)) | (!this.mSubTimer.reset(j, false)));
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.DurationTimer, com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer
        public final void startRunningLocked(long j) {
            super.startRunningLocked(j);
            this.mSubTimer.startRunningLocked(j);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer
        public final void stopAllRunningLocked(long j) {
            super.stopAllRunningLocked(j);
            this.mSubTimer.stopAllRunningLocked(j);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.DurationTimer, com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer
        public final void stopRunningLocked(long j) {
            super.stopRunningLocked(j);
            this.mSubTimer.stopRunningLocked(j);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.DurationTimer, com.android.server.power.stats.BatteryStatsImpl.Timer
        public final void writeSummaryFromParcelLocked(Parcel parcel, long j) {
            super.writeSummaryFromParcelLocked(parcel, j);
            this.mSubTimer.writeSummaryFromParcelLocked(parcel, j);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class DurationTimer extends StopwatchTimer {
        public long mCurrentDurationMs;
        public long mMaxDurationMs;
        public long mStartTimeMs;
        public long mTotalDurationMs;

        public DurationTimer(Clock clock, Uid uid, int i, ArrayList arrayList, TimeBase timeBase) {
            super(clock, uid, i, arrayList, timeBase);
            this.mStartTimeMs = -1L;
        }

        public final long getCurrentDurationMsLocked(long j) {
            long j2 = this.mCurrentDurationMs;
            if (this.mNesting <= 0) {
                return j2;
            }
            TimeBase timeBase = this.mTimeBase;
            return timeBase.mRunning ? j2 + ((timeBase.getRealtime(j * 1000) / 1000) - this.mStartTimeMs) : j2;
        }

        public final long getMaxDurationMsLocked(long j) {
            if (this.mNesting > 0) {
                long currentDurationMsLocked = getCurrentDurationMsLocked(j);
                if (currentDurationMsLocked > this.mMaxDurationMs) {
                    return currentDurationMsLocked;
                }
            }
            return this.mMaxDurationMs;
        }

        public final long getTotalDurationMsLocked(long j) {
            return getCurrentDurationMsLocked(j) + this.mTotalDurationMs;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void onTimeStarted(long j, long j2) {
            if (this.mNesting > 0) {
                this.mStartTimeMs = j2 / 1000;
            }
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void onTimeStopped(long j, long j2, long j3) {
            super.onTimeStopped(j, j2, j3);
            if (this.mNesting > 0) {
                this.mCurrentDurationMs = ((j3 / 1000) - this.mStartTimeMs) + this.mCurrentDurationMs;
            }
            this.mStartTimeMs = -1L;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer, com.android.server.power.stats.BatteryStatsImpl.Timer
        public void readSummaryFromParcelLocked(Parcel parcel) {
            super.readSummaryFromParcelLocked(parcel);
            this.mMaxDurationMs = parcel.readLong();
            this.mTotalDurationMs = parcel.readLong();
            this.mStartTimeMs = -1L;
            this.mCurrentDurationMs = 0L;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer, com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public boolean reset(long j, boolean z) {
            boolean reset = super.reset(j, z);
            this.mMaxDurationMs = 0L;
            this.mTotalDurationMs = 0L;
            this.mCurrentDurationMs = 0L;
            if (this.mNesting > 0) {
                this.mStartTimeMs = this.mTimeBase.getRealtime(j) / 1000;
            } else {
                this.mStartTimeMs = -1L;
            }
            return reset;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer
        public void startRunningLocked(long j) {
            super.startRunningLocked(j);
            if (this.mNesting == 1) {
                TimeBase timeBase = this.mTimeBase;
                if (timeBase.mRunning) {
                    this.mStartTimeMs = timeBase.getRealtime(j * 1000) / 1000;
                }
            }
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer
        public void stopRunningLocked(long j) {
            if (this.mNesting == 1) {
                long currentDurationMsLocked = getCurrentDurationMsLocked(j);
                this.mTotalDurationMs += currentDurationMsLocked;
                if (currentDurationMsLocked > this.mMaxDurationMs) {
                    this.mMaxDurationMs = currentDurationMsLocked;
                }
                this.mStartTimeMs = -1L;
                this.mCurrentDurationMs = 0L;
            }
            super.stopRunningLocked(j);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public void writeSummaryFromParcelLocked(Parcel parcel, long j) {
            super.writeSummaryFromParcelLocked(parcel, j);
            long j2 = j / 1000;
            parcel.writeLong(getMaxDurationMsLocked(j2));
            parcel.writeLong(getTotalDurationMsLocked(j2));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DutyTimer extends Timer {
        public long mAcquireTime;
        public int mDutyCycle;
        public int mNesting;
        public long mUpdateTime;

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public final int computeCurrentCountLocked() {
            return this.mCount;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public final long computeRunTimeLocked(long j, long j2) {
            return this.mTotalTimeUs + (this.mNesting > 0 ? ((j - this.mUpdateTime) * this.mDutyCycle) / 100 : 0L);
        }

        public final boolean isRunningLocked() {
            return this.mNesting > 0;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public final void logState(Printer printer, String str) {
            super.logState(printer, str);
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "mNesting=");
            m.append(this.mNesting);
            m.append(" mUpdateTime=");
            m.append(this.mUpdateTime);
            m.append(" mAcquireTime=");
            m.append(this.mAcquireTime);
            m.append(" mDutyCycle=");
            m.append(this.mDutyCycle);
            m.append(" mNesting=");
            m.append(this.mNesting);
            printer.println(m.toString());
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void onTimeStopped(long j, long j2, long j3) {
            if (this.mNesting > 0) {
                this.mTotalTimeUs = computeRunTimeLocked(j3, j);
                this.mCount = computeCurrentCountLocked();
                this.mUpdateTime = j3;
            }
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public final void readSummaryFromParcelLocked(Parcel parcel) {
            super.readSummaryFromParcelLocked(parcel);
            this.mDutyCycle = parcel.readInt();
            this.mNesting = 0;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final boolean reset(long j, boolean z) {
            boolean z2 = this.mNesting <= 0;
            super.reset(j, false);
            if (this.mNesting > 0) {
                this.mUpdateTime = this.mTimeBase.getRealtime(j);
            }
            this.mAcquireTime = -1L;
            return z2;
        }

        public final void stopRunningLocked(long j) {
            int i = this.mNesting;
            if (i == 0) {
                return;
            }
            int i2 = i - 1;
            this.mNesting = i2;
            if (i2 == 0) {
                long j2 = j * 1000;
                long realtime = this.mTimeBase.getRealtime(j2);
                this.mNesting = 1;
                long computeRunTimeLocked = computeRunTimeLocked(realtime, j2);
                this.mTotalTimeUs = computeRunTimeLocked;
                this.mNesting = 0;
                long j3 = this.mAcquireTime;
                if (j3 < 0 || computeRunTimeLocked != j3) {
                    return;
                }
                this.mCount--;
            }
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public final void writeSummaryFromParcelLocked(Parcel parcel, long j) {
            super.writeSummaryFromParcelLocked(parcel, j);
            parcel.writeInt(this.mDutyCycle);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface EnergyStatsRetriever {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FrameworkStatsLogger {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HistoryStepDetailsCalculatorImpl implements BatteryStatsHistory.HistoryStepDetailsCalculator {
        public long mCurStepCpuSystemTimeMs;
        public long mCurStepCpuUserTimeMs;
        public long mCurStepStatIOWaitTimeMs;
        public long mCurStepStatIdleTimeMs;
        public long mCurStepStatIrqTimeMs;
        public long mCurStepStatSoftIrqTimeMs;
        public long mCurStepStatSystemTimeMs;
        public long mCurStepStatUserTimeMs;
        public final BatteryStats.HistoryStepDetails mDetails = new BatteryStats.HistoryStepDetails();
        public boolean mHasHistoryStepDetails;
        public long mLastStepCpuSystemTimeMs;
        public long mLastStepCpuUserTimeMs;
        public long mLastStepStatIOWaitTimeMs;
        public long mLastStepStatIdleTimeMs;
        public long mLastStepStatIrqTimeMs;
        public long mLastStepStatSoftIrqTimeMs;
        public long mLastStepStatSystemTimeMs;
        public long mLastStepStatUserTimeMs;
        public boolean mUpdateRequested;

        public HistoryStepDetailsCalculatorImpl() {
        }

        public final void clear() {
            this.mHasHistoryStepDetails = false;
            this.mCurStepCpuUserTimeMs = 0L;
            this.mLastStepCpuUserTimeMs = 0L;
            this.mCurStepCpuSystemTimeMs = 0L;
            this.mLastStepCpuSystemTimeMs = 0L;
            this.mCurStepStatUserTimeMs = 0L;
            this.mLastStepStatUserTimeMs = 0L;
            this.mCurStepStatSystemTimeMs = 0L;
            this.mLastStepStatSystemTimeMs = 0L;
            this.mCurStepStatIOWaitTimeMs = 0L;
            this.mLastStepStatIOWaitTimeMs = 0L;
            this.mCurStepStatIrqTimeMs = 0L;
            this.mLastStepStatIrqTimeMs = 0L;
            this.mCurStepStatSoftIrqTimeMs = 0L;
            this.mLastStepStatSoftIrqTimeMs = 0L;
            this.mCurStepStatIdleTimeMs = 0L;
            this.mLastStepStatIdleTimeMs = 0L;
        }

        public final BatteryStats.HistoryStepDetails getHistoryStepDetails() {
            String str;
            int i = 0;
            if (!this.mUpdateRequested) {
                this.mUpdateRequested = true;
                BatteryStatsImpl.this.mExternalSync.scheduleCpuSyncDueToWakelockChange(0L);
                PlatformIdleStateCallback platformIdleStateCallback = BatteryStatsImpl.this.mPlatformIdleStateCallback;
                if (platformIdleStateCallback != null) {
                    BatteryStats.HistoryStepDetails historyStepDetails = this.mDetails;
                    BatteryStatsService batteryStatsService = (BatteryStatsService) platformIdleStateCallback;
                    synchronized (batteryStatsService.mPowerStatsLock) {
                        if (batteryStatsService.mPowerStatsInternal != null && !((HashMap) batteryStatsService.mEntityNames).isEmpty() && !((HashMap) batteryStatsService.mStateNames).isEmpty()) {
                            try {
                                StateResidencyResult[] stateResidencyResultArr = (StateResidencyResult[]) batteryStatsService.mPowerStatsInternal.getStateResidencyAsync(new int[0]).get(2000L, TimeUnit.MILLISECONDS);
                                if (stateResidencyResultArr == null || stateResidencyResultArr.length == 0) {
                                    str = "Empty";
                                } else {
                                    StringBuilder sb = new StringBuilder("SubsystemPowerState");
                                    int i2 = 32768;
                                    int i3 = 0;
                                    while (true) {
                                        if (i3 >= stateResidencyResultArr.length) {
                                            break;
                                        }
                                        StateResidencyResult stateResidencyResult = stateResidencyResultArr[i3];
                                        StringBuilder sb2 = new StringBuilder();
                                        sb2.append(" subsystem_" + i3);
                                        sb2.append(" name=" + ((String) ((HashMap) batteryStatsService.mEntityNames).get(Integer.valueOf(stateResidencyResult.id))));
                                        int i4 = 0;
                                        while (true) {
                                            StateResidency[] stateResidencyArr = stateResidencyResult.stateResidencyData;
                                            if (i4 >= stateResidencyArr.length) {
                                                break;
                                            }
                                            StateResidency stateResidency = stateResidencyArr[i4];
                                            sb2.append(" state_" + i4);
                                            sb2.append(" name=" + ((String) ((Map) ((HashMap) batteryStatsService.mStateNames).get(Integer.valueOf(stateResidencyResult.id))).get(Integer.valueOf(stateResidency.id))));
                                            sb2.append(" time=" + stateResidency.totalTimeInStateMs);
                                            sb2.append(" count=" + stateResidency.totalStateEntryCount);
                                            sb2.append(" last entry=" + stateResidency.lastEntryTimestampMs);
                                            i4++;
                                        }
                                        if (sb2.length() > i2) {
                                            Slog.e("BatteryStatsService", "getSubsystemLowPowerStats: buffer not enough");
                                            break;
                                        }
                                        i2 -= sb2.length();
                                        sb.append((CharSequence) sb2);
                                        i3++;
                                    }
                                    str = sb.toString();
                                }
                            } catch (Exception e) {
                                Slog.e("BatteryStatsService", "Failed to getStateResidencyAsync", e);
                                str = "Empty";
                            }
                        }
                        str = "Empty";
                    }
                    historyStepDetails.statSubsystemPowerState = str;
                }
            }
            if (!this.mHasHistoryStepDetails) {
                int size = BatteryStatsImpl.this.mUidStats.size();
                while (i < size) {
                    ((Uid) BatteryStatsImpl.this.mUidStats.valueAt(i)).getClass();
                    i++;
                }
                this.mLastStepCpuUserTimeMs = this.mCurStepCpuUserTimeMs;
                this.mLastStepCpuSystemTimeMs = this.mCurStepCpuSystemTimeMs;
                this.mLastStepStatUserTimeMs = this.mCurStepStatUserTimeMs;
                this.mLastStepStatSystemTimeMs = this.mCurStepStatSystemTimeMs;
                this.mLastStepStatIOWaitTimeMs = this.mCurStepStatIOWaitTimeMs;
                this.mLastStepStatIrqTimeMs = this.mCurStepStatIrqTimeMs;
                this.mLastStepStatSoftIrqTimeMs = this.mCurStepStatSoftIrqTimeMs;
                this.mLastStepStatIdleTimeMs = this.mCurStepStatIdleTimeMs;
                return null;
            }
            BatteryStats.HistoryStepDetails historyStepDetails2 = this.mDetails;
            historyStepDetails2.userTime = (int) (this.mCurStepCpuUserTimeMs - this.mLastStepCpuUserTimeMs);
            historyStepDetails2.systemTime = (int) (this.mCurStepCpuSystemTimeMs - this.mLastStepCpuSystemTimeMs);
            historyStepDetails2.statUserTime = (int) (this.mCurStepStatUserTimeMs - this.mLastStepStatUserTimeMs);
            historyStepDetails2.statSystemTime = (int) (this.mCurStepStatSystemTimeMs - this.mLastStepStatSystemTimeMs);
            historyStepDetails2.statIOWaitTime = (int) (this.mCurStepStatIOWaitTimeMs - this.mLastStepStatIOWaitTimeMs);
            historyStepDetails2.statIrqTime = (int) (this.mCurStepStatIrqTimeMs - this.mLastStepStatIrqTimeMs);
            historyStepDetails2.statSoftIrqTime = (int) (this.mCurStepStatSoftIrqTimeMs - this.mLastStepStatSoftIrqTimeMs);
            historyStepDetails2.statIdlTime = (int) (this.mCurStepStatIdleTimeMs - this.mLastStepStatIdleTimeMs);
            historyStepDetails2.appCpuUid3 = -1;
            historyStepDetails2.appCpuUid2 = -1;
            historyStepDetails2.appCpuUid1 = -1;
            historyStepDetails2.appCpuUTime3 = 0;
            historyStepDetails2.appCpuUTime2 = 0;
            historyStepDetails2.appCpuUTime1 = 0;
            historyStepDetails2.appCpuSTime3 = 0;
            historyStepDetails2.appCpuSTime2 = 0;
            historyStepDetails2.appCpuSTime1 = 0;
            int size2 = BatteryStatsImpl.this.mUidStats.size();
            while (i < size2) {
                Uid uid = (Uid) BatteryStatsImpl.this.mUidStats.valueAt(i);
                uid.getClass();
                int i5 = (int) 0;
                int i6 = i5 + i5;
                BatteryStats.HistoryStepDetails historyStepDetails3 = this.mDetails;
                if (i6 > historyStepDetails3.appCpuUTime3 + historyStepDetails3.appCpuSTime3) {
                    int i7 = historyStepDetails3.appCpuUTime2;
                    int i8 = historyStepDetails3.appCpuSTime2;
                    if (i6 <= i7 + i8) {
                        historyStepDetails3.appCpuUid3 = uid.mUid;
                        historyStepDetails3.appCpuUTime3 = i5;
                        historyStepDetails3.appCpuSTime3 = i5;
                    } else {
                        historyStepDetails3.appCpuUid3 = historyStepDetails3.appCpuUid2;
                        historyStepDetails3.appCpuUTime3 = i7;
                        historyStepDetails3.appCpuSTime3 = i8;
                        int i9 = historyStepDetails3.appCpuUTime1;
                        int i10 = historyStepDetails3.appCpuSTime1;
                        if (i6 <= i9 + i10) {
                            historyStepDetails3.appCpuUid2 = uid.mUid;
                            historyStepDetails3.appCpuUTime2 = i5;
                            historyStepDetails3.appCpuSTime2 = i5;
                        } else {
                            historyStepDetails3.appCpuUid2 = historyStepDetails3.appCpuUid1;
                            historyStepDetails3.appCpuUTime2 = i9;
                            historyStepDetails3.appCpuSTime2 = i10;
                            historyStepDetails3.appCpuUid1 = uid.mUid;
                            historyStepDetails3.appCpuUTime1 = i5;
                            historyStepDetails3.appCpuSTime1 = i5;
                        }
                    }
                }
                i++;
            }
            this.mLastStepCpuUserTimeMs = this.mCurStepCpuUserTimeMs;
            this.mLastStepCpuSystemTimeMs = this.mCurStepCpuSystemTimeMs;
            this.mLastStepStatUserTimeMs = this.mCurStepStatUserTimeMs;
            this.mLastStepStatSystemTimeMs = this.mCurStepStatSystemTimeMs;
            this.mLastStepStatIOWaitTimeMs = this.mCurStepStatIOWaitTimeMs;
            this.mLastStepStatIrqTimeMs = this.mCurStepStatIrqTimeMs;
            this.mLastStepStatSoftIrqTimeMs = this.mCurStepStatSoftIrqTimeMs;
            this.mLastStepStatIdleTimeMs = this.mCurStepStatIdleTimeMs;
            return this.mDetails;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class LongSamplingCounter extends BatteryStats.LongCounter implements TimeBaseObs {
        public long mCount;
        public final TimeBase mTimeBase;

        public LongSamplingCounter(TimeBase timeBase) {
            this.mTimeBase = timeBase;
            timeBase.add(this);
        }

        public final void addCountLocked(long j) {
            addCountLocked(j, this.mTimeBase.mRunning);
        }

        public final void addCountLocked(long j, boolean z) {
            if (z) {
                this.mCount += j;
            }
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void detach() {
            this.mTimeBase.remove(this);
        }

        public final long getCountForProcessState(int i) {
            if (i == 0) {
                return this.mCount;
            }
            return 0L;
        }

        public final long getCountLocked(int i) {
            return this.mCount;
        }

        public final void logState(Printer printer, String str) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "mCount=");
            m.append(this.mCount);
            printer.println(m.toString());
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void onTimeStarted(long j, long j2) {
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void onTimeStopped(long j, long j2, long j3) {
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final boolean reset(long j, boolean z) {
            this.mCount = 0L;
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class LongSamplingCounterArray extends BatteryStats.LongCounterArray implements TimeBaseObs {
        public static final /* synthetic */ int $r8$clinit = 0;
        public long[] mCounts;
        public final TimeBase mTimeBase;

        public LongSamplingCounterArray(TimeBase timeBase) {
            this.mTimeBase = timeBase;
            timeBase.add(this);
        }

        public static LongSamplingCounterArray readSummaryFromParcelLocked(Parcel parcel, TimeBase timeBase) {
            if (parcel.readInt() == 0) {
                return null;
            }
            LongSamplingCounterArray longSamplingCounterArray = new LongSamplingCounterArray(timeBase);
            longSamplingCounterArray.mCounts = parcel.createLongArray();
            return longSamplingCounterArray;
        }

        public final void addCountLocked(long[] jArr, boolean z) {
            if (jArr != null && z) {
                if (this.mCounts == null) {
                    this.mCounts = new long[jArr.length];
                }
                for (int i = 0; i < jArr.length; i++) {
                    long[] jArr2 = this.mCounts;
                    jArr2[i] = jArr2[i] + jArr[i];
                }
            }
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void detach() {
            this.mTimeBase.remove(this);
        }

        public final long[] getCountsLocked(int i) {
            long[] jArr = this.mCounts;
            if (jArr == null) {
                return null;
            }
            return Arrays.copyOf(jArr, jArr.length);
        }

        public final void logState(Printer printer, String str) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "mCounts=");
            m.append(Arrays.toString(this.mCounts));
            printer.println(m.toString());
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void onTimeStarted(long j, long j2) {
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void onTimeStopped(long j, long j2, long j3) {
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final boolean reset(long j, boolean z) {
            long[] jArr = this.mCounts;
            if (jArr == null) {
                return true;
            }
            Arrays.fill(jArr, 0L);
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ModemActivityCounterImpl extends BatteryStats.ModemActivityCounter implements Parcelable {
        public final LongSamplingCounter mIdleTimeMillis;
        public final ModemTxRxCounterImpl mLcModemActivityInfo;
        public final ModemTxRxCounterImpl mNrModemActivityInfo;
        public final LongSamplingCounter mSleepTimeMillis;

        public ModemActivityCounterImpl(TimeBase timeBase) {
            this.mSleepTimeMillis = new LongSamplingCounter(timeBase);
            this.mIdleTimeMillis = new LongSamplingCounter(timeBase);
            this.mNrModemActivityInfo = new ModemTxRxCounterImpl(timeBase);
            this.mLcModemActivityInfo = new ModemTxRxCounterImpl(timeBase);
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        public final BatteryStats.LongCounter getIdleTimeCounter() {
            return this.mIdleTimeMillis;
        }

        public final BatteryStats.ModemTxRxCounter getLcModemActivityInfo() {
            return this.mLcModemActivityInfo;
        }

        public final BatteryStats.ModemTxRxCounter getNrModemActivityInfo() {
            return this.mNrModemActivityInfo;
        }

        public final BatteryStats.LongCounter getSleepTimeCounter() {
            return this.mSleepTimeMillis;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(this.mSleepTimeMillis.mCount);
            parcel.writeLong(this.mIdleTimeMillis.mCount);
            this.mNrModemActivityInfo.writeToParcel(parcel);
            this.mLcModemActivityInfo.writeToParcel(parcel);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ModemTxRxCounterImpl extends BatteryStats.ModemTxRxCounter {
        public final LongSamplingCounter mRxByte;
        public final LongSamplingCounter mRxTimeMillis;
        public final LongSamplingCounter mTxByte;
        public final LongSamplingCounter[] mTxTimeMillis = new LongSamplingCounter[5];

        public ModemTxRxCounterImpl(TimeBase timeBase) {
            for (int i = 0; i < 5; i++) {
                this.mTxTimeMillis[i] = new LongSamplingCounter(timeBase);
            }
            this.mRxTimeMillis = new LongSamplingCounter(timeBase);
            this.mTxByte = new LongSamplingCounter(timeBase);
            this.mRxByte = new LongSamplingCounter(timeBase);
        }

        public final BatteryStats.LongCounter getRxByteCounter() {
            return this.mRxByte;
        }

        public final BatteryStats.LongCounter getRxTimeCounter() {
            return this.mRxTimeMillis;
        }

        public final BatteryStats.LongCounter getTxByteCounter() {
            return this.mTxByte;
        }

        public final BatteryStats.LongCounter[] getTxTimeCounters() {
            return this.mTxTimeMillis;
        }

        public final void readSummaryFromParcel(Parcel parcel) {
            int readInt = parcel.readInt();
            LongSamplingCounter[] longSamplingCounterArr = this.mTxTimeMillis;
            if (readInt != longSamplingCounterArr.length) {
                throw new ParcelFormatException("inconsistent tx state lengths");
            }
            for (LongSamplingCounter longSamplingCounter : longSamplingCounterArr) {
                longSamplingCounter.getClass();
                longSamplingCounter.mCount = parcel.readLong();
            }
            LongSamplingCounter longSamplingCounter2 = this.mRxTimeMillis;
            longSamplingCounter2.getClass();
            longSamplingCounter2.mCount = parcel.readLong();
            LongSamplingCounter longSamplingCounter3 = this.mTxByte;
            longSamplingCounter3.getClass();
            longSamplingCounter3.mCount = parcel.readLong();
            LongSamplingCounter longSamplingCounter4 = this.mRxByte;
            longSamplingCounter4.getClass();
            longSamplingCounter4.mCount = parcel.readLong();
        }

        public final void reset() {
            for (LongSamplingCounter longSamplingCounter : this.mTxTimeMillis) {
                longSamplingCounter.reset();
            }
            this.mRxTimeMillis.reset();
            this.mTxByte.reset();
            this.mRxByte.reset();
        }

        public final void writeSummaryToParcel(Parcel parcel) {
            parcel.writeInt(this.mTxTimeMillis.length);
            for (LongSamplingCounter longSamplingCounter : this.mTxTimeMillis) {
                parcel.writeLong(longSamplingCounter.mCount);
            }
            parcel.writeLong(this.mRxTimeMillis.mCount);
            parcel.writeLong(this.mTxByte.mCount);
            parcel.writeLong(this.mRxByte.mCount);
        }

        public final void writeToParcel(Parcel parcel) {
            parcel.writeInt(this.mTxTimeMillis.length);
            for (LongSamplingCounter longSamplingCounter : this.mTxTimeMillis) {
                parcel.writeLong(longSamplingCounter.mCount);
            }
            parcel.writeLong(this.mRxTimeMillis.mCount);
            parcel.writeLong(this.mTxByte.mCount);
            parcel.writeLong(this.mRxByte.mCount);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            String str;
            BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
            BatteryCallback batteryCallback = batteryStatsImpl.mCallback;
            int i = message.what;
            if (i == 1) {
                if (batteryCallback != null) {
                    ((ActivityManagerService) batteryCallback).updateCpuStatsNow();
                    return;
                }
                return;
            }
            if (i == 2) {
                if (batteryCallback != null) {
                    boolean z = message.arg1 != 0;
                    ActivityManagerService activityManagerService = (ActivityManagerService) batteryCallback;
                    activityManagerService.updateCpuStatsNow();
                    ActivityManagerProcLock activityManagerProcLock = activityManagerService.mProcLock;
                    ActivityManagerService.boostPriorityForProcLockedSection();
                    synchronized (activityManagerProcLock) {
                        try {
                            activityManagerService.mOnBattery = z;
                        } catch (Throwable th) {
                            ActivityManagerService.resetPriorityAfterProcLockedSection();
                            throw th;
                        }
                    }
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                    return;
                }
                return;
            }
            if (i != 3) {
                if (i == 4 && batteryCallback != null && batteryStatsImpl.mSystemServicesReady) {
                    ((ActivityManagerService) batteryCallback).batterySendBroadcast(BatteryService$$ExternalSyntheticOutline0.m(67108864, "com.samsung.server.BatteryStatsService.action.BATTERYSTATS_RESET"));
                    return;
                }
                return;
            }
            if (batteryCallback != null) {
                synchronized (batteryStatsImpl) {
                    try {
                        str = BatteryStatsImpl.this.mCharging ? "android.os.action.CHARGING" : "android.os.action.DISCHARGING";
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }
                ((ActivityManagerService) batteryCallback).batterySendBroadcast(BatteryService$$ExternalSyntheticOutline0.m(67108864, str));
                BatteryStatsImpl batteryStatsImpl2 = BatteryStatsImpl.this;
                if (batteryStatsImpl2.mSystemServicesReady) {
                    return;
                }
                batteryStatsImpl2.mPendingReportCharging = true;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NetworkStatsDelta {
        public long mRxBytes;
        public long mRxPackets;
        public int mSet;
        public long mTxBytes;
        public long mTxPackets;
        public int mUid;

        public final String toString() {
            return "NetworkStatsDelta{mUid=" + this.mUid + ", mSet=" + this.mSet + ", mRxBytes=" + this.mRxBytes + ", mRxPackets=" + this.mRxPackets + ", mTxBytes=" + this.mTxBytes + ", mTxPackets=" + this.mTxPackets + '}';
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface PlatformIdleStateCallback {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PowerStatsCollectorInjector {
        public BluetoothStatsRetrieverImpl mBluetoothStatsRetriever;
        public PowerStatsCollector.ConsumedEnergyRetrieverImpl mConsumedEnergyRetriever;
        public NetworkStatsManager mNetworkStatsManager;
        public PackageManager mPackageManager;
        public TelephonyManager mTelephonyManager;
        public WifiManager mWifiManager;

        public PowerStatsCollectorInjector() {
        }

        public final long getPowerStatsCollectionThrottlePeriod(String str) {
            BatteryStatsConfig batteryStatsConfig = BatteryStatsImpl.this.mBatteryStatsConfig;
            return ((Long) batteryStatsConfig.mPowerStatsThrottlePeriods.getOrDefault(str, batteryStatsConfig.mDefaultPowerStatsThrottlePeriod)).longValue();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RadioAccessTechnologyBatteryStats {
        public final StopwatchTimer[][] perStateTimers;
        public boolean mActive = false;
        public int mFrequencyRange = 0;
        public int mSignalStrength = 0;
        public LongSamplingCounter[][] mPerStateTxDurationMs = null;
        public LongSamplingCounter[] mPerFrequencyRxDurationMs = null;

        public RadioAccessTechnologyBatteryStats(int i, Clock clock, TimeBase timeBase) {
            this.perStateTimers = (StopwatchTimer[][]) Array.newInstance((Class<?>) StopwatchTimer.class, i, 5);
            for (int i2 = 0; i2 < i; i2++) {
                for (int i3 = 0; i3 < 5; i3++) {
                    this.perStateTimers[i2][i3] = new StopwatchTimer(clock, null, -1, null, timeBase);
                }
            }
        }

        public final LongSamplingCounter getRxDurationCounter(int i, boolean z) {
            LongSamplingCounter[] longSamplingCounterArr = this.mPerFrequencyRxDurationMs;
            StopwatchTimer[][] stopwatchTimerArr = this.perStateTimers;
            if (longSamplingCounterArr == null) {
                if (!z) {
                    return null;
                }
                int length = stopwatchTimerArr.length;
                TimeBase timeBase = stopwatchTimerArr[0][0].mTimeBase;
                this.mPerFrequencyRxDurationMs = new LongSamplingCounter[length];
                for (int i2 = 0; i2 < length; i2++) {
                    this.mPerFrequencyRxDurationMs[i2] = new LongSamplingCounter(timeBase);
                }
            }
            if (i >= 0 && i < stopwatchTimerArr.length) {
                return this.mPerFrequencyRxDurationMs[i];
            }
            BrailleDisplayConnection$$ExternalSyntheticOutline0.m(i, "Unexpected frequency range (", ") requested in getRxDurationCounter", "BatteryStatsImpl");
            return null;
        }

        public final LongSamplingCounter getTxDurationCounter(int i, int i2, boolean z) {
            LongSamplingCounter[][] longSamplingCounterArr = this.mPerStateTxDurationMs;
            StopwatchTimer[][] stopwatchTimerArr = this.perStateTimers;
            if (longSamplingCounterArr == null) {
                if (!z) {
                    return null;
                }
                int length = stopwatchTimerArr.length;
                StopwatchTimer[] stopwatchTimerArr2 = stopwatchTimerArr[0];
                int length2 = stopwatchTimerArr2.length;
                TimeBase timeBase = stopwatchTimerArr2[0].mTimeBase;
                this.mPerStateTxDurationMs = (LongSamplingCounter[][]) Array.newInstance((Class<?>) LongSamplingCounter.class, length, length2);
                for (int i3 = 0; i3 < length; i3++) {
                    for (int i4 = 0; i4 < length2; i4++) {
                        this.mPerStateTxDurationMs[i3][i4] = new LongSamplingCounter(timeBase);
                    }
                }
            }
            if (i < 0 || i >= stopwatchTimerArr.length) {
                BrailleDisplayConnection$$ExternalSyntheticOutline0.m(i, "Unexpected frequency range (", ") requested in getTxDurationCounter", "BatteryStatsImpl");
                return null;
            }
            if (i2 >= 0 && i2 < stopwatchTimerArr[0].length) {
                return this.mPerStateTxDurationMs[i][i2];
            }
            BrailleDisplayConnection$$ExternalSyntheticOutline0.m(i2, "Unexpected signal strength (", ") requested in getTxDurationCounter", "BatteryStatsImpl");
            return null;
        }

        public final void noteActive(long j, boolean z) {
            if (this.mActive == z) {
                return;
            }
            this.mActive = z;
            StopwatchTimer[][] stopwatchTimerArr = this.perStateTimers;
            if (z) {
                stopwatchTimerArr[this.mFrequencyRange][this.mSignalStrength].startRunningLocked(j);
            } else {
                stopwatchTimerArr[this.mFrequencyRange][this.mSignalStrength].stopRunningLocked(j);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RxTxConsumption {
        public final double rxConsumptionMah;
        public final double txConsumptionMah;
        public final double txToTotalRatio;

        public RxTxConsumption(double d, long j, double d2, long j2) {
            this.rxConsumptionMah = d;
            this.txConsumptionMah = d2;
            long j3 = j + j2;
            if (j3 == 0) {
                this.txToTotalRatio = 0.0d;
            } else {
                this.txToTotalRatio = j2 / j3;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SamplingTimer extends Timer {
        public int mBaseReportedCount;
        public long mBaseReportedTotalTimeUs;
        public int mCurrentReportedCount;
        public long mCurrentReportedTotalTimeUs;
        public boolean mTimeBaseRunning;
        public boolean mTrackingReportedValues;
        public int mUpdateVersion;

        public SamplingTimer(Clock clock, TimeBase timeBase) {
            super(clock, timeBase);
            this.mTrackingReportedValues = false;
            this.mTimeBaseRunning = timeBase.mRunning;
        }

        public SamplingTimer(Clock clock, TimeBase timeBase, Parcel parcel) {
            super(clock, timeBase, parcel);
            this.mCurrentReportedCount = parcel.readInt();
            this.mBaseReportedCount = parcel.readInt();
            this.mCurrentReportedTotalTimeUs = parcel.readLong();
            this.mBaseReportedTotalTimeUs = parcel.readLong();
            this.mTrackingReportedValues = parcel.readInt() == 1;
            this.mTimeBaseRunning = timeBase.mRunning;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public final int computeCurrentCountLocked() {
            return this.mCount + ((this.mTimeBaseRunning && this.mTrackingReportedValues) ? this.mCurrentReportedCount - this.mBaseReportedCount : 0);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public final long computeRunTimeLocked(long j, long j2) {
            return this.mTotalTimeUs + ((this.mTimeBaseRunning && this.mTrackingReportedValues) ? this.mCurrentReportedTotalTimeUs - this.mBaseReportedTotalTimeUs : 0L);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public final void logState(Printer printer, String str) {
            super.logState(printer, str);
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "mCurrentReportedCount=");
            m.append(this.mCurrentReportedCount);
            m.append(" mBaseReportedCount=");
            m.append(this.mBaseReportedCount);
            m.append(" mCurrentReportedTotalTime=");
            m.append(this.mCurrentReportedTotalTimeUs);
            m.append(" mBaseReportedTotalTimeUs=");
            m.append(this.mBaseReportedTotalTimeUs);
            printer.println(m.toString());
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void onTimeStarted(long j, long j2) {
            if (this.mTrackingReportedValues) {
                this.mBaseReportedTotalTimeUs = this.mCurrentReportedTotalTimeUs;
                this.mBaseReportedCount = this.mCurrentReportedCount;
            }
            this.mTimeBaseRunning = true;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void onTimeStopped(long j, long j2, long j3) {
            this.mTotalTimeUs = computeRunTimeLocked(j3, j);
            this.mCount = computeCurrentCountLocked();
            this.mTimeBaseRunning = false;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final boolean reset(long j, boolean z) {
            super.reset(j, false);
            this.mTrackingReportedValues = false;
            this.mBaseReportedTotalTimeUs = 0L;
            this.mBaseReportedCount = 0;
            return true;
        }

        public final void update(long j, long j2, int i, long j3) {
            if (this.mTimeBaseRunning && !this.mTrackingReportedValues) {
                this.mBaseReportedTotalTimeUs = j - j2;
                this.mBaseReportedCount = j2 == 0 ? i : i - 1;
            }
            this.mTrackingReportedValues = true;
            if (j < this.mCurrentReportedTotalTimeUs || i < this.mCurrentReportedCount) {
                this.mTotalTimeUs = computeRunTimeLocked(0L, j3);
                this.mCount = computeCurrentCountLocked();
                this.mCurrentReportedTotalTimeUs = 0L;
                this.mBaseReportedTotalTimeUs = 0L;
                this.mCurrentReportedCount = 0;
                this.mBaseReportedCount = 0;
                this.mTrackingReportedValues = false;
            }
            this.mCurrentReportedTotalTimeUs = j;
            this.mCurrentReportedCount = i;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class StopwatchTimer extends Timer {
        public long mAcquireTimeUs;
        public boolean mInList;
        public int mNesting;
        public long mTimeoutUs;
        public final ArrayList mTimerPool;
        public final Uid mUid;
        public long mUpdateTimeUs;

        public StopwatchTimer(Clock clock, Uid uid, int i, ArrayList arrayList, TimeBase timeBase) {
            super(clock, timeBase);
            this.mAcquireTimeUs = -1L;
            this.mUid = uid;
            this.mTimerPool = arrayList;
        }

        public static void refreshTimersLocked(long j, ArrayList arrayList, StopwatchTimer stopwatchTimer) {
            int size = arrayList.size();
            for (int i = size - 1; i >= 0; i--) {
                StopwatchTimer stopwatchTimer2 = (StopwatchTimer) arrayList.get(i);
                long j2 = j - stopwatchTimer2.mUpdateTimeUs;
                if (j2 > 0) {
                    stopwatchTimer2.mTotalTimeUs += j2 / size;
                }
                stopwatchTimer2.mUpdateTimeUs = j;
            }
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public final int computeCurrentCountLocked() {
            return this.mCount;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public final long computeRunTimeLocked(long j, long j2) {
            long j3 = this.mTimeoutUs;
            long j4 = 0;
            if (j3 > 0) {
                long j5 = this.mUpdateTimeUs;
                if (j > j5 + j3) {
                    j = j5 + j3;
                }
            }
            long j6 = this.mTotalTimeUs;
            if (this.mNesting > 0) {
                long j7 = j - this.mUpdateTimeUs;
                ArrayList arrayList = this.mTimerPool;
                j4 = j7 / ((arrayList == null || arrayList.size() <= 0) ? 1 : this.mTimerPool.size());
            }
            return j6 + j4;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void detach() {
            super.detach();
            ArrayList arrayList = this.mTimerPool;
            if (arrayList != null) {
                arrayList.remove(this);
            }
        }

        public final boolean isRunningLocked() {
            return this.mNesting > 0;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public void logState(Printer printer, String str) {
            super.logState(printer, str);
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "mNesting=");
            m.append(this.mNesting);
            m.append(" mUpdateTime=");
            m.append(this.mUpdateTimeUs);
            m.append(" mAcquireTime=");
            m.append(this.mAcquireTimeUs);
            printer.println(m.toString());
        }

        public void onTimeStopped(long j, long j2, long j3) {
            if (this.mNesting > 0) {
                this.mTotalTimeUs = computeRunTimeLocked(j3, j);
                this.mCount = computeCurrentCountLocked();
                this.mUpdateTimeUs = j3;
            }
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public void readSummaryFromParcelLocked(Parcel parcel) {
            super.readSummaryFromParcelLocked(parcel);
            this.mNesting = 0;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public boolean reset(long j, boolean z) {
            boolean z2 = false;
            boolean z3 = this.mNesting <= 0;
            if (z3 && z) {
                z2 = true;
            }
            super.reset(j, z2);
            if (this.mNesting > 0) {
                this.mUpdateTimeUs = this.mTimeBase.getRealtime(j);
            }
            this.mAcquireTimeUs = -1L;
            return z3;
        }

        public final void setMark(long j) {
            long realtime = this.mTimeBase.getRealtime(j * 1000);
            if (this.mNesting > 0) {
                ArrayList arrayList = this.mTimerPool;
                if (arrayList != null) {
                    refreshTimersLocked(realtime, arrayList, this);
                } else {
                    this.mTotalTimeUs = (realtime - this.mUpdateTimeUs) + this.mTotalTimeUs;
                    this.mUpdateTimeUs = realtime;
                }
            }
            this.mTimeBeforeMarkUs = this.mTotalTimeUs;
        }

        public void startRunningLocked(long j) {
            int i = this.mNesting;
            this.mNesting = i + 1;
            if (i == 0) {
                long realtime = this.mTimeBase.getRealtime(j * 1000);
                this.mUpdateTimeUs = realtime;
                ArrayList arrayList = this.mTimerPool;
                if (arrayList != null) {
                    refreshTimersLocked(realtime, arrayList, null);
                    this.mTimerPool.add(this);
                }
                if (!this.mTimeBase.mRunning) {
                    this.mAcquireTimeUs = -1L;
                } else {
                    this.mCount++;
                    this.mAcquireTimeUs = this.mTotalTimeUs;
                }
            }
        }

        public void stopAllRunningLocked(long j) {
            if (this.mNesting > 0) {
                this.mNesting = 1;
                stopRunningLocked(j);
            }
        }

        public void stopRunningLocked(long j) {
            int i = this.mNesting;
            if (i == 0) {
                return;
            }
            int i2 = i - 1;
            this.mNesting = i2;
            if (i2 == 0) {
                long j2 = j * 1000;
                long realtime = this.mTimeBase.getRealtime(j2);
                ArrayList arrayList = this.mTimerPool;
                if (arrayList != null) {
                    refreshTimersLocked(realtime, arrayList, null);
                    this.mTimerPool.remove(this);
                } else {
                    this.mNesting = 1;
                    this.mTotalTimeUs = computeRunTimeLocked(realtime, j2);
                    this.mNesting = 0;
                }
                long j3 = this.mAcquireTimeUs;
                if (j3 < 0 || this.mTotalTimeUs != j3) {
                    return;
                }
                this.mCount--;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TimeBase {
        public final Collection mObservers;
        public long mPastRealtimeUs;
        public long mPastUptimeUs;
        public long mRealtimeStartUs;
        public long mRealtimeUs;
        public boolean mRunning;
        public long mUptimeStartUs;
        public long mUptimeUs;

        public TimeBase(boolean z) {
            this.mObservers = z ? new HashSet() : new ArrayList();
        }

        public final void add(TimeBaseObs timeBaseObs) {
            this.mObservers.add(timeBaseObs);
        }

        public final long computeRealtime(long j) {
            return getRealtime(j) + this.mRealtimeUs;
        }

        public final long getRealtime(long j) {
            long j2 = this.mPastRealtimeUs;
            return this.mRunning ? j2 + (j - this.mRealtimeStartUs) : j2;
        }

        public final long getUptime(long j) {
            long j2 = this.mPastUptimeUs;
            return this.mRunning ? j2 + (j - this.mUptimeStartUs) : j2;
        }

        public final void init(long j, long j2) {
            this.mRealtimeUs = 0L;
            this.mUptimeUs = 0L;
            this.mPastUptimeUs = 0L;
            this.mPastRealtimeUs = 0L;
            this.mUptimeStartUs = j;
            this.mRealtimeStartUs = j2;
        }

        public final void remove(TimeBaseObs timeBaseObs) {
            this.mObservers.remove(timeBaseObs);
        }

        public final boolean setRunning(long j, long j2, boolean z) {
            if (this.mRunning == z) {
                return false;
            }
            this.mRunning = z;
            if (z) {
                this.mUptimeStartUs = j;
                this.mRealtimeStartUs = j2;
                long realtime = getRealtime(j2);
                Iterator it = this.mObservers.iterator();
                while (it.hasNext()) {
                    ((TimeBaseObs) it.next()).onTimeStarted(j2, realtime);
                }
                return true;
            }
            this.mPastUptimeUs = (j - this.mUptimeStartUs) + this.mPastUptimeUs;
            this.mPastRealtimeUs = (j2 - this.mRealtimeStartUs) + this.mPastRealtimeUs;
            long uptime = getUptime(j);
            long realtime2 = getRealtime(j2);
            Iterator it2 = this.mObservers.iterator();
            while (it2.hasNext()) {
                ((TimeBaseObs) it2.next()).onTimeStopped(j2, uptime, realtime2);
            }
            return true;
        }

        public final void writeSummaryToParcel(Parcel parcel, long j, long j2) {
            parcel.writeLong(getUptime(j) + this.mUptimeUs);
            parcel.writeLong(computeRealtime(j2));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface TimeBaseObs {
        void detach();

        void onTimeStarted(long j, long j2);

        void onTimeStopped(long j, long j2, long j3);

        default void reset() {
            reset(SystemClock.elapsedRealtime() * 1000, false);
        }

        boolean reset(long j, boolean z);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TimeInFreqMultiStateCounter implements TimeBaseObs {
        public final LongArrayMultiStateCounter mCounter;
        public final TimeBase mTimeBase;

        public TimeInFreqMultiStateCounter(TimeBase timeBase, int i, long j) {
            this(timeBase, new LongArrayMultiStateCounter(8, i), j);
        }

        public TimeInFreqMultiStateCounter(TimeBase timeBase, LongArrayMultiStateCounter longArrayMultiStateCounter, long j) {
            this.mTimeBase = timeBase;
            this.mCounter = longArrayMultiStateCounter;
            longArrayMultiStateCounter.setEnabled(timeBase.mRunning, j);
            timeBase.add(this);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void detach() {
            this.mTimeBase.remove(this);
        }

        public final boolean getCountsLocked(int i, long[] jArr) {
            if (jArr.length != this.mCounter.getArrayLength()) {
                return false;
            }
            this.mCounter.getCounts(jArr, i);
            for (int length = jArr.length - 1; length >= 0; length--) {
                if (jArr[length] != 0) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void onTimeStarted(long j, long j2) {
            this.mCounter.setEnabled(true, j / 1000);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void onTimeStopped(long j, long j2, long j3) {
            this.mCounter.setEnabled(false, j / 1000);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final boolean reset(long j, boolean z) {
            this.mCounter.reset();
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TimeMultiStateCounter extends BatteryStats.LongCounter implements TimeBaseObs {
        public final LongMultiStateCounter mCounter;
        public final TimeBase mTimeBase;

        /* renamed from: -$$Nest$mincrement, reason: not valid java name */
        public static void m841$$Nest$mincrement(TimeMultiStateCounter timeMultiStateCounter, long j, long j2) {
            timeMultiStateCounter.mCounter.incrementValue(j, j2);
        }

        /* renamed from: -$$Nest$msetState, reason: not valid java name */
        public static void m842$$Nest$msetState(TimeMultiStateCounter timeMultiStateCounter, int i, long j) {
            timeMultiStateCounter.mCounter.setState(i, j);
        }

        /* renamed from: -$$Nest$smreadFromParcel, reason: not valid java name */
        public static TimeMultiStateCounter m843$$Nest$smreadFromParcel(Parcel parcel, TimeBase timeBase, long j) {
            LongMultiStateCounter longMultiStateCounter = (LongMultiStateCounter) LongMultiStateCounter.CREATOR.createFromParcel(parcel);
            if (longMultiStateCounter.getStateCount() != 5) {
                return null;
            }
            return new TimeMultiStateCounter(timeBase, longMultiStateCounter, j);
        }

        public TimeMultiStateCounter(TimeBase timeBase, int i, long j) {
            this(timeBase, new LongMultiStateCounter(i), j);
        }

        public TimeMultiStateCounter(TimeBase timeBase, LongMultiStateCounter longMultiStateCounter, long j) {
            this.mTimeBase = timeBase;
            this.mCounter = longMultiStateCounter;
            longMultiStateCounter.setEnabled(timeBase.mRunning, j);
            timeBase.add(this);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void detach() {
            this.mTimeBase.remove(this);
        }

        public final long getCountForProcessState(int i) {
            return this.mCounter.getCount(i);
        }

        public final long getCountLocked(int i) {
            return this.mCounter.getTotalCount();
        }

        public final void logState(Printer printer, String str) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "mCounter=");
            m.append(this.mCounter);
            printer.println(m.toString());
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void onTimeStarted(long j, long j2) {
            this.mCounter.setEnabled(true, j / 1000);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void onTimeStopped(long j, long j2, long j3) {
            this.mCounter.setEnabled(false, j / 1000);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final boolean reset(long j, boolean z) {
            this.mCounter.reset();
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Timer extends BatteryStats.Timer implements TimeBaseObs {
        public final Clock mClock;
        public int mCount;
        public final TimeBase mTimeBase;
        public long mTimeBeforeMarkUs;
        public long mTotalTimeUs;

        public Timer(Clock clock, TimeBase timeBase) {
            this.mClock = clock;
            this.mTimeBase = timeBase;
            timeBase.add(this);
        }

        public Timer(Clock clock, TimeBase timeBase, Parcel parcel) {
            this.mClock = clock;
            this.mTimeBase = timeBase;
            this.mCount = parcel.readInt();
            this.mTotalTimeUs = parcel.readLong();
            this.mTimeBeforeMarkUs = parcel.readLong();
            timeBase.add(this);
        }

        public abstract int computeCurrentCountLocked();

        public abstract long computeRunTimeLocked(long j, long j2);

        public void detach() {
            this.mTimeBase.remove(this);
        }

        public final int getCountLocked(int i) {
            return computeCurrentCountLocked();
        }

        public final long getTimeSinceMarkLocked(long j) {
            return computeRunTimeLocked(this.mTimeBase.getRealtime(j), j) - this.mTimeBeforeMarkUs;
        }

        public final long getTotalTimeLocked(long j, int i) {
            return computeRunTimeLocked(this.mTimeBase.getRealtime(j), j);
        }

        public void logState(Printer printer, String str) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "mCount=");
            m.append(this.mCount);
            printer.println(m.toString());
            printer.println(str + "mTotalTime=" + this.mTotalTimeUs);
        }

        public void onTimeStarted(long j, long j2) {
        }

        public void readSummaryFromParcelLocked(Parcel parcel) {
            this.mTotalTimeUs = parcel.readLong();
            this.mCount = parcel.readInt();
            this.mTimeBeforeMarkUs = this.mTotalTimeUs;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public final void reset() {
            reset(this.mClock.elapsedRealtime() * 1000, false);
        }

        public boolean reset(long j, boolean z) {
            this.mTimeBeforeMarkUs = 0L;
            this.mTotalTimeUs = 0L;
            this.mCount = 0;
            if (!z) {
                return true;
            }
            detach();
            return true;
        }

        public void writeSummaryFromParcelLocked(Parcel parcel, long j) {
            parcel.writeLong(computeRunTimeLocked(this.mTimeBase.getRealtime(j), j));
            parcel.writeInt(computeCurrentCountLocked());
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Uid extends BatteryStats.Uid {
        public static final BinderCallStats sTempBinderCallStats = new BinderCallStats();
        public DualTimer mAggregatedPartialWakelockTimer;
        public StopwatchTimer mAudioTurnedOnTimer;
        public long mBinderCallCount;
        public ControllerActivityCounterImpl mBluetoothControllerActivity;
        public DutyTimer mBluetoothDutyScanTimer;
        public Counter mBluetoothScanResultBgCounter;
        public Counter mBluetoothScanResultCounter;
        public DualTimer mBluetoothScanTimer;
        public DualTimer mBluetoothUnoptimizedScanTimer;
        public final BatteryStatsImpl mBsi;
        public StopwatchTimer mCameraTurnedOnTimer;
        public SparseArray mChildUids;
        public TimeMultiStateCounter mCpuActiveTimeMs;
        public LongSamplingCounter[][] mCpuClusterSpeedTimesUs;
        public final LongSamplingCounterArray mCpuClusterTimesMs;
        public LongSamplingCounterArray mCpuFreqTimeMs;
        public StopwatchTimer mFlashlightTurnedOnTimer;
        public StopwatchTimer mForegroundActivityTimer;
        public StopwatchTimer mForegroundServiceTimer;
        public boolean mFullWifiLockOut;
        public StopwatchTimer mFullWifiLockTimer;
        public final AnonymousClass1 mJobStats;
        public final Counter mJobsDeferredCount;
        public final Counter mJobsDeferredEventCount;
        public final Counter[] mJobsFreshnessBuckets;
        public final LongSamplingCounter mJobsFreshnessTimeMs;
        public LongSamplingCounter mMobileRadioActiveCount;
        public TimeMultiStateCounter mMobileRadioActiveTime;
        public LongSamplingCounter mMobileRadioApWakeupCount;
        public ControllerActivityCounterImpl mModemControllerActivity;
        public LongSamplingCounter[] mNetworkByteActivityCounters;
        public LongSamplingCounter[] mNetworkPacketActivityCounters;
        public final TimeBase mOnBatteryBackgroundTimeBase;
        public final TimeBase mOnBatteryScreenOffBackgroundTimeBase;
        public StopwatchTimer[] mPerDisplayTopActivityTimer;
        public TimeInFreqMultiStateCounter mProcStateScreenOffTimeMs;
        public TimeInFreqMultiStateCounter mProcStateTimeMs;
        public final StopwatchTimer[] mProcessStateTimer;
        public double mProportionalSystemServiceUsage;
        public LongSamplingCounterArray mScreenOffCpuFreqTimeMs;
        public LongSamplingCounter[] mSpeakerMediaTimeCounters;
        public final AnonymousClass1 mSyncStats;
        public final LongSamplingCounter mSystemCpuTime;
        public long mSystemServiceTimeUs;
        public final int mUid;
        public EnergyConsumerStats mUidEnergyConsumerStats;
        public Counter[] mUserActivityCounters;
        public final LongSamplingCounter mUserCpuTime;
        public BatchTimer mVibratorOnTimer;
        public StopwatchTimer mVideoTurnedOnTimer;
        public final AnonymousClass1 mWakelockStats;
        public final StopwatchTimer[] mWifiBatchedScanTimer;
        public ControllerActivityCounterImpl mWifiControllerActivity;
        public StopwatchTimer mWifiMulticastTimer;
        public int mWifiMulticastWakelockCount;
        public LongSamplingCounter mWifiRadioApWakeupCount;
        public boolean mWifiRunning;
        public StopwatchTimer mWifiRunningTimer;
        public boolean mWifiScanStarted;
        public DualTimer mWifiScanTimer;
        public int mWifiBatchedScanBinStarted = -1;
        public int mProcessState = 7;
        public boolean mInForegroundService = false;
        public final ArrayMap mJobCompletions = new ArrayMap();
        public final SparseArray mSensorStats = new SparseArray();
        public final ArrayMap mProcessStats = new ArrayMap();
        public final ArrayMap mPackageStats = new ArrayMap();
        public final SparseArray mPids = new SparseArray();
        public final ArraySet mBinderCallStats = new ArraySet();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.power.stats.BatteryStatsImpl$Uid$1, reason: invalid class name */
        public final class AnonymousClass1 {
            public final /* synthetic */ int $r8$classId;
            public ArrayMap mActiveOverflow;
            public Object mCurOverflow;
            public long mLastCleanupTimeMs;
            public long mLastOverflowFinishTimeMs;
            public long mLastOverflowTimeMs;
            public final ArrayMap mMap;
            public final int mUid;
            public final /* synthetic */ Uid this$0;

            public AnonymousClass1(int i) {
                this.mMap = new ArrayMap();
                this.mUid = i;
            }

            /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(Uid uid, BatteryStatsImpl batteryStatsImpl, int i, int i2) {
                this(i);
                this.$r8$classId = i2;
                switch (i2) {
                    case 1:
                        this.this$0 = uid;
                        Objects.requireNonNull(batteryStatsImpl);
                        this(i);
                        break;
                    case 2:
                        this.this$0 = uid;
                        Objects.requireNonNull(batteryStatsImpl);
                        this(i);
                        break;
                    default:
                        this.this$0 = uid;
                        Objects.requireNonNull(batteryStatsImpl);
                        break;
                }
            }

            public final void add(String str, Object obj) {
                if (str == null) {
                    str = "";
                }
                this.mMap.put(str, obj);
                if ("*overflow*".equals(str)) {
                    this.mCurOverflow = obj;
                }
            }

            public final void cleanup(long j) {
                this.mLastCleanupTimeMs = j;
                ArrayMap arrayMap = this.mActiveOverflow;
                if (arrayMap != null && arrayMap.size() == 0) {
                    this.mActiveOverflow = null;
                }
                if (this.mActiveOverflow == null) {
                    if (this.mMap.containsKey("*overflow*")) {
                        Slog.wtf("BatteryStatsImpl", "Cleaning up with no active overflow, but have overflow entry " + this.mMap.get("*overflow*"));
                        this.mMap.remove("*overflow*");
                    }
                    this.mCurOverflow = null;
                    return;
                }
                if (this.mCurOverflow == null || !this.mMap.containsKey("*overflow*")) {
                    Slog.wtf("BatteryStatsImpl", "Cleaning up with active overflow, but no overflow entry: cur=" + this.mCurOverflow + " map=" + this.mMap.get("*overflow*"));
                }
            }

            public final Object instantiateObject() {
                switch (this.$r8$classId) {
                    case 0:
                        BatteryStatsImpl batteryStatsImpl = this.this$0.mBsi;
                        return new Wakelock();
                    case 1:
                        Uid uid = this.this$0;
                        BatteryStatsImpl batteryStatsImpl2 = uid.mBsi;
                        return new DualTimer(batteryStatsImpl2.mClock, uid, 13, null, batteryStatsImpl2.mOnBatteryTimeBase, uid.mOnBatteryBackgroundTimeBase);
                    default:
                        Uid uid2 = this.this$0;
                        BatteryStatsImpl batteryStatsImpl3 = uid2.mBsi;
                        return new DualTimer(batteryStatsImpl3.mClock, uid2, 14, null, batteryStatsImpl3.mOnBatteryTimeBase, uid2.mOnBatteryBackgroundTimeBase);
                }
            }

            public final Object startObject(long j, String str) {
                MutableInt mutableInt;
                if (str == null) {
                    str = "";
                }
                Object obj = this.mMap.get(str);
                if (obj != null) {
                    return obj;
                }
                ArrayMap arrayMap = this.mActiveOverflow;
                if (arrayMap != null && (mutableInt = (MutableInt) arrayMap.get(str)) != null) {
                    Object obj2 = this.mCurOverflow;
                    if (obj2 == null) {
                        Slog.wtf("BatteryStatsImpl", "Have active overflow " + str + " but null overflow");
                        obj2 = instantiateObject();
                        this.mCurOverflow = obj2;
                        this.mMap.put("*overflow*", obj2);
                    }
                    mutableInt.value++;
                    return obj2;
                }
                if (this.mMap.size() < BatteryStatsImpl.MAX_WAKELOCKS_PER_UID) {
                    Object instantiateObject = instantiateObject();
                    this.mMap.put(str, instantiateObject);
                    return instantiateObject;
                }
                Object obj3 = this.mCurOverflow;
                if (obj3 == null) {
                    obj3 = instantiateObject();
                    this.mCurOverflow = obj3;
                    this.mMap.put("*overflow*", obj3);
                }
                if (this.mActiveOverflow == null) {
                    this.mActiveOverflow = new ArrayMap();
                }
                this.mActiveOverflow.put(str, new MutableInt(1));
                this.mLastOverflowTimeMs = j;
                return obj3;
            }

            public final Object stopObject(long j, String str) {
                MutableInt mutableInt;
                Object obj;
                if (str == null) {
                    str = "";
                }
                Object obj2 = this.mMap.get(str);
                if (obj2 != null) {
                    return obj2;
                }
                ArrayMap arrayMap = this.mActiveOverflow;
                if (arrayMap != null && (mutableInt = (MutableInt) arrayMap.get(str)) != null && (obj = this.mCurOverflow) != null) {
                    int i = mutableInt.value - 1;
                    mutableInt.value = i;
                    if (i <= 0) {
                        this.mActiveOverflow.remove(str);
                        this.mLastOverflowFinishTimeMs = j;
                    }
                    return obj;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to find object for ");
                sb.append(str);
                sb.append(" in uid ");
                sb.append(this.mUid);
                sb.append(" mapsize=");
                sb.append(this.mMap.size());
                sb.append(" activeoverflow=");
                sb.append(this.mActiveOverflow);
                sb.append(" curoverflow=");
                sb.append(this.mCurOverflow);
                if (this.mLastOverflowTimeMs != 0) {
                    sb.append(" lastOverflowTime=");
                    TimeUtils.formatDuration(this.mLastOverflowTimeMs - j, sb);
                }
                if (this.mLastOverflowFinishTimeMs != 0) {
                    sb.append(" lastOverflowFinishTime=");
                    TimeUtils.formatDuration(this.mLastOverflowFinishTimeMs - j, sb);
                }
                if (this.mLastCleanupTimeMs != 0) {
                    sb.append(" lastCleanupTime=");
                    TimeUtils.formatDuration(this.mLastCleanupTimeMs - j, sb);
                }
                Slog.wtf("BatteryStatsImpl", sb.toString());
                return null;
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class ChildUid {
            public final TimeMultiStateCounter cpuActiveCounter;
            public final LongArrayMultiStateCounter cpuTimeInFreqCounter;

            public ChildUid(Uid uid) {
                long elapsedRealtime = uid.mBsi.mClock.elapsedRealtime();
                TimeMultiStateCounter timeMultiStateCounter = new TimeMultiStateCounter(uid.mBsi.mOnBatteryTimeBase, 1, elapsedRealtime);
                this.cpuActiveCounter = timeMultiStateCounter;
                timeMultiStateCounter.mCounter.setState(0, elapsedRealtime);
                if (!uid.mBsi.mCpuUidFreqTimeReader.isFastCpuTimesReader()) {
                    this.cpuTimeInFreqCounter = null;
                    return;
                }
                int scalingStepCount = uid.mBsi.mCpuScalingPolicies.getScalingStepCount();
                LongArrayMultiStateCounter longArrayMultiStateCounter = new LongArrayMultiStateCounter(1, scalingStepCount);
                this.cpuTimeInFreqCounter = longArrayMultiStateCounter;
                longArrayMultiStateCounter.updateValues(new LongArrayMultiStateCounter.LongArrayContainer(scalingStepCount), elapsedRealtime);
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Pkg extends BatteryStats.Uid.Pkg implements TimeBaseObs {
            public final BatteryStatsImpl mBsi;
            public final ArrayMap mWakeupAlarms = new ArrayMap();
            public final ArrayMap mServiceStats = new ArrayMap();

            /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
            public final class Serv extends BatteryStats.Uid.Pkg.Serv implements TimeBaseObs {
                public BatteryStatsImpl mBsi;
                public boolean mLaunched;
                public long mLaunchedSinceMs;
                public int mLaunches;
                public boolean mRunning;
                public long mRunningSinceMs;
                public long mStartTimeMs;
                public int mStarts;

                @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
                public final void detach() {
                    this.mBsi.mOnBatteryTimeBase.remove(this);
                }

                public final int getLaunches(int i) {
                    return this.mLaunches;
                }

                public final long getStartTime(long j, int i) {
                    return !this.mRunning ? this.mStartTimeMs : (this.mStartTimeMs + j) - this.mRunningSinceMs;
                }

                public final int getStarts(int i) {
                    return this.mStarts;
                }

                @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
                public final void onTimeStarted(long j, long j2) {
                }

                @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
                public final void onTimeStopped(long j, long j2, long j3) {
                }

                @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
                public final boolean reset(long j, boolean z) {
                    return true;
                }
            }

            public Pkg(BatteryStatsImpl batteryStatsImpl) {
                this.mBsi = batteryStatsImpl;
                batteryStatsImpl.mOnBatteryScreenOffTimeBase.add(this);
            }

            @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
            public final void detach() {
                this.mBsi.mOnBatteryScreenOffTimeBase.remove(this);
                for (int size = this.mWakeupAlarms.size() - 1; size >= 0; size--) {
                    BatteryStatsImpl.detachIfNotNull((Counter) this.mWakeupAlarms.valueAt(size));
                }
                for (int size2 = this.mServiceStats.size() - 1; size2 >= 0; size2--) {
                    BatteryStatsImpl.detachIfNotNull((Serv) this.mServiceStats.valueAt(size2));
                }
            }

            public final ArrayMap getServiceStats() {
                return this.mServiceStats;
            }

            public final ArrayMap getWakeupAlarmStats() {
                return this.mWakeupAlarms;
            }

            public final void noteWakeupAlarmLocked(String str) {
                Counter counter = (Counter) this.mWakeupAlarms.get(str);
                if (counter == null) {
                    counter = new Counter(this.mBsi.mOnBatteryScreenOffTimeBase);
                    this.mWakeupAlarms.put(str, counter);
                }
                counter.stepAtomic();
            }

            @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
            public final void onTimeStarted(long j, long j2) {
            }

            @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
            public final void onTimeStopped(long j, long j2, long j3) {
            }

            @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
            public final boolean reset(long j, boolean z) {
                return true;
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Proc extends BatteryStats.Uid.Proc implements TimeBaseObs {
            public boolean mActive;
            public BatteryStatsImpl mBsi;
            public ArrayList mExcessivePower;
            public long mForegroundTimeMs;
            public int mNumAnrs;
            public int mNumCrashes;
            public int mStarts;
            public long mSystemTimeMs;
            public long mUserTimeMs;

            public final int countExcessivePowers() {
                ArrayList arrayList = this.mExcessivePower;
                if (arrayList != null) {
                    return arrayList.size();
                }
                return 0;
            }

            @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
            public final void detach() {
                this.mActive = false;
                this.mBsi.mOnBatteryTimeBase.remove(this);
            }

            public final BatteryStats.Uid.Proc.ExcessivePower getExcessivePower(int i) {
                ArrayList arrayList = this.mExcessivePower;
                if (arrayList != null) {
                    return (BatteryStats.Uid.Proc.ExcessivePower) arrayList.get(i);
                }
                return null;
            }

            public final long getForegroundTime(int i) {
                return this.mForegroundTimeMs;
            }

            public final int getNumAnrs(int i) {
                return this.mNumAnrs;
            }

            public final int getNumCrashes(int i) {
                return this.mNumCrashes;
            }

            public final int getStarts(int i) {
                return this.mStarts;
            }

            public final long getSystemTime(int i) {
                return this.mSystemTimeMs;
            }

            public final long getUserTime(int i) {
                return this.mUserTimeMs;
            }

            public final boolean isActive() {
                return this.mActive;
            }

            @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
            public final void onTimeStarted(long j, long j2) {
            }

            @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
            public final void onTimeStopped(long j, long j2, long j3) {
            }

            public final void readExcessivePowerFromParcelLocked(Parcel parcel) {
                int readInt = parcel.readInt();
                if (readInt == 0) {
                    this.mExcessivePower = null;
                    return;
                }
                if (readInt > 10000) {
                    throw new ParcelFormatException(VibrationParam$1$$ExternalSyntheticOutline0.m(readInt, "File corrupt: too many excessive power entries "));
                }
                this.mExcessivePower = new ArrayList();
                for (int i = 0; i < readInt; i++) {
                    BatteryStats.Uid.Proc.ExcessivePower excessivePower = new BatteryStats.Uid.Proc.ExcessivePower();
                    excessivePower.type = parcel.readInt();
                    excessivePower.overTime = parcel.readLong();
                    excessivePower.usedTime = parcel.readLong();
                    this.mExcessivePower.add(excessivePower);
                }
            }

            @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
            public final boolean reset(long j, boolean z) {
                return true;
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Sensor extends BatteryStats.Uid.Sensor {
            public final int mHandle;
            public DualTimer mTimer;

            public Sensor(int i) {
                this.mHandle = i;
            }

            public final int getHandle() {
                return this.mHandle;
            }

            public final BatteryStats.Timer getSensorBackgroundTime() {
                DualTimer dualTimer = this.mTimer;
                if (dualTimer == null) {
                    return null;
                }
                return dualTimer.mSubTimer;
            }

            public final BatteryStats.Timer getSensorTime() {
                return this.mTimer;
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Wakelock extends BatteryStats.Uid.Wakelock {
            public StopwatchTimer mTimerDraw;
            public StopwatchTimer mTimerFull;
            public DualTimer mTimerPartial;
            public StopwatchTimer mTimerWindow;

            public final BatteryStats.Timer getWakeTime(int i) {
                if (i == 0) {
                    return this.mTimerPartial;
                }
                if (i == 1) {
                    return this.mTimerFull;
                }
                if (i == 2) {
                    return this.mTimerWindow;
                }
                if (i == 18) {
                    return this.mTimerDraw;
                }
                throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "type = "));
            }
        }

        /* renamed from: -$$Nest$mmarkGnssTimeUs, reason: not valid java name */
        public static long m845$$Nest$mmarkGnssTimeUs(Uid uid, long j) {
            DualTimer dualTimer;
            Sensor sensor = (Sensor) uid.mSensorStats.get(-10000);
            if (sensor == null || (dualTimer = sensor.mTimer) == null) {
                return 0L;
            }
            long timeSinceMarkLocked = dualTimer.getTimeSinceMarkLocked(1000 * j);
            dualTimer.setMark(j);
            return timeSinceMarkLocked;
        }

        /* renamed from: -$$Nest$mmarkProcessForegroundTimeUs, reason: not valid java name */
        public static long m846$$Nest$mmarkProcessForegroundTimeUs(Uid uid, long j, boolean z) {
            long j2;
            StopwatchTimer stopwatchTimer = uid.mForegroundActivityTimer;
            if (stopwatchTimer != null) {
                j2 = z ? stopwatchTimer.getTimeSinceMarkLocked(j * 1000) : 0L;
                stopwatchTimer.setMark(j);
            } else {
                j2 = 0;
            }
            StopwatchTimer stopwatchTimer2 = uid.mProcessStateTimer[0];
            if (stopwatchTimer2 != null) {
                r3 = z ? stopwatchTimer2.getTimeSinceMarkLocked(1000 * j) : 0L;
                stopwatchTimer2.setMark(j);
            }
            return r3 < j2 ? r3 : j2;
        }

        public Uid(BatteryStatsImpl batteryStatsImpl, int i, long j, long j2) {
            this.mBsi = batteryStatsImpl;
            this.mUid = i;
            TimeBase timeBase = new TimeBase(false);
            this.mOnBatteryBackgroundTimeBase = timeBase;
            long j3 = j2 * 1000;
            long j4 = 1000 * j;
            timeBase.init(j3, j4);
            TimeBase timeBase2 = new TimeBase(false);
            this.mOnBatteryScreenOffBackgroundTimeBase = timeBase2;
            timeBase2.init(j3, j4);
            this.mUserCpuTime = new LongSamplingCounter(batteryStatsImpl.mOnBatteryTimeBase);
            this.mSystemCpuTime = new LongSamplingCounter(batteryStatsImpl.mOnBatteryTimeBase);
            this.mCpuClusterTimesMs = new LongSamplingCounterArray(batteryStatsImpl.mOnBatteryTimeBase);
            this.mWakelockStats = new AnonymousClass1(this, batteryStatsImpl, i, 0);
            this.mSyncStats = new AnonymousClass1(this, batteryStatsImpl, i, 1);
            this.mJobStats = new AnonymousClass1(this, batteryStatsImpl, i, 2);
            this.mWifiRunningTimer = new StopwatchTimer(batteryStatsImpl.mClock, this, 4, batteryStatsImpl.mWifiRunningTimers, batteryStatsImpl.mOnBatteryTimeBase);
            this.mFullWifiLockTimer = new StopwatchTimer(batteryStatsImpl.mClock, this, 5, batteryStatsImpl.mFullWifiLockTimers, batteryStatsImpl.mOnBatteryTimeBase);
            this.mWifiScanTimer = new DualTimer(batteryStatsImpl.mClock, this, 6, batteryStatsImpl.mWifiScanTimers, batteryStatsImpl.mOnBatteryTimeBase, timeBase);
            this.mWifiBatchedScanTimer = new StopwatchTimer[5];
            this.mWifiMulticastTimer = new StopwatchTimer(batteryStatsImpl.mClock, this, 7, batteryStatsImpl.mWifiMulticastTimers, batteryStatsImpl.mOnBatteryTimeBase);
            this.mProcessStateTimer = new StopwatchTimer[7];
            this.mPerDisplayTopActivityTimer = new StopwatchTimer[batteryStatsImpl.mPerDisplayBatteryStats.length];
            this.mJobsDeferredEventCount = new Counter(batteryStatsImpl.mOnBatteryTimeBase);
            this.mJobsDeferredCount = new Counter(batteryStatsImpl.mOnBatteryTimeBase);
            this.mJobsFreshnessTimeMs = new LongSamplingCounter(batteryStatsImpl.mOnBatteryTimeBase);
            this.mJobsFreshnessBuckets = new Counter[BatteryStats.JOB_FRESHNESS_BUCKETS.length];
        }

        public static long[] nullIfAllZeros(LongSamplingCounterArray longSamplingCounterArray, int i) {
            long[] countsLocked;
            if (longSamplingCounterArray == null || (countsLocked = longSamplingCounterArray.getCountsLocked(i)) == null) {
                return null;
            }
            for (int length = countsLocked.length - 1; length >= 0; length--) {
                if (countsLocked[length] != 0) {
                    return countsLocked;
                }
            }
            return null;
        }

        public final DutyTimer createBluetoothDutyScanTimerLocked() {
            if (this.mBluetoothDutyScanTimer == null) {
                BatteryStatsImpl batteryStatsImpl = this.mBsi;
                DutyTimer dutyTimer = new DutyTimer(batteryStatsImpl.mClock, batteryStatsImpl.mOnBatteryTimeBase);
                dutyTimer.mAcquireTime = -1L;
                dutyTimer.mDutyCycle = 100;
                this.mBluetoothDutyScanTimer = dutyTimer;
            }
            return this.mBluetoothDutyScanTimer;
        }

        public final void detachFromTimeBase() {
            BatteryStatsImpl.detachIfNotNull(this.mWifiRunningTimer);
            BatteryStatsImpl.detachIfNotNull(this.mFullWifiLockTimer);
            BatteryStatsImpl.detachIfNotNull(this.mWifiScanTimer);
            BatteryStatsImpl.detachIfNotNull(this.mWifiBatchedScanTimer);
            BatteryStatsImpl.detachIfNotNull(this.mWifiMulticastTimer);
            BatteryStatsImpl.detachIfNotNull(this.mAudioTurnedOnTimer);
            BatteryStatsImpl.detachIfNotNull(this.mVideoTurnedOnTimer);
            BatteryStatsImpl.detachIfNotNull(this.mFlashlightTurnedOnTimer);
            BatteryStatsImpl.detachIfNotNull(this.mCameraTurnedOnTimer);
            BatteryStatsImpl.detachIfNotNull(this.mForegroundActivityTimer);
            BatteryStatsImpl.detachIfNotNull(this.mForegroundServiceTimer);
            BatteryStatsImpl.detachIfNotNull(this.mAggregatedPartialWakelockTimer);
            BatteryStatsImpl.detachIfNotNull(this.mBluetoothDutyScanTimer);
            BatteryStatsImpl.detachIfNotNull(this.mSpeakerMediaTimeCounters);
            BatteryStatsImpl.detachIfNotNull(this.mPerDisplayTopActivityTimer);
            BatteryStatsImpl.detachIfNotNull(this.mBluetoothScanTimer);
            BatteryStatsImpl.detachIfNotNull(this.mBluetoothUnoptimizedScanTimer);
            BatteryStatsImpl.detachIfNotNull(this.mBluetoothScanResultCounter);
            BatteryStatsImpl.detachIfNotNull(this.mBluetoothScanResultBgCounter);
            BatteryStatsImpl.detachIfNotNull(this.mProcessStateTimer);
            BatteryStatsImpl.detachIfNotNull(this.mVibratorOnTimer);
            BatteryStatsImpl.detachIfNotNull(this.mUserActivityCounters);
            BatteryStatsImpl.detachIfNotNull(this.mNetworkByteActivityCounters);
            BatteryStatsImpl.detachIfNotNull(this.mNetworkPacketActivityCounters);
            BatteryStatsImpl.detachIfNotNull(this.mMobileRadioActiveTime);
            BatteryStatsImpl.detachIfNotNull(this.mMobileRadioActiveCount);
            BatteryStatsImpl.detachIfNotNull(this.mMobileRadioApWakeupCount);
            BatteryStatsImpl.detachIfNotNull(this.mWifiRadioApWakeupCount);
            BatteryStatsImpl.m837$$Nest$smdetachIfNotNull(this.mWifiControllerActivity);
            BatteryStatsImpl.m837$$Nest$smdetachIfNotNull(this.mBluetoothControllerActivity);
            BatteryStatsImpl.m837$$Nest$smdetachIfNotNull(this.mModemControllerActivity);
            this.mPids.clear();
            BatteryStatsImpl.detachIfNotNull(this.mUserCpuTime);
            BatteryStatsImpl.detachIfNotNull(this.mSystemCpuTime);
            BatteryStatsImpl.detachIfNotNull(this.mCpuClusterSpeedTimesUs);
            BatteryStatsImpl.detachIfNotNull(this.mCpuActiveTimeMs);
            BatteryStatsImpl.detachIfNotNull(this.mCpuFreqTimeMs);
            BatteryStatsImpl.detachIfNotNull(this.mScreenOffCpuFreqTimeMs);
            BatteryStatsImpl.detachIfNotNull(this.mCpuClusterTimesMs);
            BatteryStatsImpl.detachIfNotNull(this.mProcStateTimeMs);
            BatteryStatsImpl.detachIfNotNull(this.mProcStateScreenOffTimeMs);
            ArrayMap arrayMap = this.mWakelockStats.mMap;
            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                Wakelock wakelock = (Wakelock) arrayMap.valueAt(size);
                BatteryStatsImpl.detachIfNotNull(wakelock.mTimerPartial);
                BatteryStatsImpl.detachIfNotNull(wakelock.mTimerFull);
                BatteryStatsImpl.detachIfNotNull(wakelock.mTimerWindow);
                BatteryStatsImpl.detachIfNotNull(wakelock.mTimerDraw);
            }
            ArrayMap arrayMap2 = this.mSyncStats.mMap;
            for (int size2 = arrayMap2.size() - 1; size2 >= 0; size2--) {
                BatteryStatsImpl.detachIfNotNull((DualTimer) arrayMap2.valueAt(size2));
            }
            ArrayMap arrayMap3 = this.mJobStats.mMap;
            for (int size3 = arrayMap3.size() - 1; size3 >= 0; size3--) {
                BatteryStatsImpl.detachIfNotNull((DualTimer) arrayMap3.valueAt(size3));
            }
            BatteryStatsImpl.detachIfNotNull(this.mJobsDeferredEventCount);
            BatteryStatsImpl.detachIfNotNull(this.mJobsDeferredCount);
            BatteryStatsImpl.detachIfNotNull(this.mJobsFreshnessTimeMs);
            BatteryStatsImpl.detachIfNotNull(this.mJobsFreshnessBuckets);
            for (int size4 = this.mSensorStats.size() - 1; size4 >= 0; size4--) {
                BatteryStatsImpl.detachIfNotNull(((Sensor) this.mSensorStats.valueAt(size4)).mTimer);
            }
            for (int size5 = this.mProcessStats.size() - 1; size5 >= 0; size5--) {
                ((Proc) this.mProcessStats.valueAt(size5)).detach();
            }
            this.mProcessStats.clear();
            for (int size6 = this.mPackageStats.size() - 1; size6 >= 0; size6--) {
                ((Pkg) this.mPackageStats.valueAt(size6)).detach();
            }
            this.mPackageStats.clear();
        }

        public final void ensureMultiStateCounters(long j) {
            if (this.mBsi.mPowerStatsCollectorEnabled.get(1)) {
                throw new IllegalStateException("Multi-state counters used in streamlined mode");
            }
            if (this.mProcStateTimeMs == null) {
                BatteryStatsImpl batteryStatsImpl = this.mBsi;
                this.mProcStateTimeMs = new TimeInFreqMultiStateCounter(batteryStatsImpl.mOnBatteryTimeBase, batteryStatsImpl.mCpuScalingPolicies.getScalingStepCount(), j);
            }
            if (this.mProcStateScreenOffTimeMs == null) {
                BatteryStatsImpl batteryStatsImpl2 = this.mBsi;
                this.mProcStateScreenOffTimeMs = new TimeInFreqMultiStateCounter(batteryStatsImpl2.mOnBatteryScreenOffTimeBase, batteryStatsImpl2.mCpuScalingPolicies.getScalingStepCount(), j);
            }
        }

        public final void ensureNetworkActivityLocked() {
            if (this.mNetworkByteActivityCounters != null) {
                return;
            }
            this.mNetworkByteActivityCounters = new LongSamplingCounter[10];
            this.mNetworkPacketActivityCounters = new LongSamplingCounter[10];
            for (int i = 0; i < 10; i++) {
                this.mNetworkByteActivityCounters[i] = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
                this.mNetworkPacketActivityCounters[i] = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            }
            this.mMobileRadioActiveCount = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
        }

        public final BatteryStats.Timer getAggregatedPartialWakelockTimer() {
            return this.mAggregatedPartialWakelockTimer;
        }

        public final BatteryStats.Timer getAudioTurnedOnTimer() {
            return this.mAudioTurnedOnTimer;
        }

        public ArraySet getBinderCallStats() {
            return this.mBinderCallStats;
        }

        public final BatteryStats.ControllerActivityCounter getBluetoothControllerActivity() {
            return this.mBluetoothControllerActivity;
        }

        public final BatteryStats.Timer getBluetoothDutyScanTimer() {
            return this.mBluetoothDutyScanTimer;
        }

        public final long getBluetoothEnergyConsumptionUC() {
            return getEnergyConsumptionUC(5);
        }

        public final long getBluetoothEnergyConsumptionUC(int i) {
            return getEnergyConsumptionUC(5, i);
        }

        public final BatteryStats.Timer getBluetoothScanBackgroundTimer() {
            DualTimer dualTimer = this.mBluetoothScanTimer;
            if (dualTimer == null) {
                return null;
            }
            return dualTimer.mSubTimer;
        }

        public final BatteryStats.Counter getBluetoothScanResultBgCounter() {
            return this.mBluetoothScanResultBgCounter;
        }

        public final BatteryStats.Counter getBluetoothScanResultCounter() {
            return this.mBluetoothScanResultCounter;
        }

        public final BatteryStats.Timer getBluetoothScanTimer() {
            return this.mBluetoothScanTimer;
        }

        public final BatteryStats.Timer getBluetoothUnoptimizedScanBackgroundTimer() {
            DualTimer dualTimer = this.mBluetoothUnoptimizedScanTimer;
            if (dualTimer == null) {
                return null;
            }
            return dualTimer.mSubTimer;
        }

        public final BatteryStats.Timer getBluetoothUnoptimizedScanTimer() {
            return this.mBluetoothUnoptimizedScanTimer;
        }

        public final long getCameraEnergyConsumptionUC() {
            return getEnergyConsumptionUC(8);
        }

        public final BatteryStats.Timer getCameraTurnedOnTimer() {
            return this.mCameraTurnedOnTimer;
        }

        public final long getCpuActiveTime() {
            long j = 0;
            if (this.mCpuActiveTimeMs == null) {
                return 0L;
            }
            for (int i = 0; i < 5; i++) {
                j += this.mCpuActiveTimeMs.mCounter.getCount(i);
            }
            return j;
        }

        public final long getCpuActiveTime(int i) {
            TimeMultiStateCounter timeMultiStateCounter = this.mCpuActiveTimeMs;
            if (timeMultiStateCounter == null || i < 0 || i >= 5) {
                return 0L;
            }
            return timeMultiStateCounter.mCounter.getCount(i);
        }

        public final TimeMultiStateCounter getCpuActiveTimeCounter() {
            if (this.mCpuActiveTimeMs == null) {
                long elapsedRealtime = this.mBsi.mClock.elapsedRealtime();
                TimeMultiStateCounter timeMultiStateCounter = new TimeMultiStateCounter(this.mBsi.mOnBatteryTimeBase, 5, elapsedRealtime);
                this.mCpuActiveTimeMs = timeMultiStateCounter;
                timeMultiStateCounter.mCounter.setState(BatteryStats.mapUidProcessStateToBatteryConsumerProcessState(this.mProcessState), elapsedRealtime);
            }
            return this.mCpuActiveTimeMs;
        }

        public final long[] getCpuClusterTimes() {
            return nullIfAllZeros(this.mCpuClusterTimesMs, 0);
        }

        public final long getCpuEnergyConsumptionUC() {
            return getEnergyConsumptionUC(3);
        }

        public final long getCpuEnergyConsumptionUC(int i) {
            return getEnergyConsumptionUC(3, i);
        }

        public final boolean getCpuFreqTimes(long[] jArr, int i) {
            TimeInFreqMultiStateCounter timeInFreqMultiStateCounter;
            if (i < 0 || i >= 7 || (timeInFreqMultiStateCounter = this.mProcStateTimeMs) == null) {
                return false;
            }
            if (this.mBsi.mPerProcStateCpuTimesAvailable) {
                return timeInFreqMultiStateCounter.getCountsLocked(i, jArr);
            }
            this.mProcStateTimeMs = null;
            return false;
        }

        public final long[] getCpuFreqTimes(int i) {
            return nullIfAllZeros(this.mCpuFreqTimeMs, i);
        }

        public final long[] getCustomEnergyConsumerBatteryConsumptionUC() {
            EnergyConsumerStats energyConsumerStats = this.mBsi.mGlobalEnergyConsumerStats;
            if (energyConsumerStats == null) {
                return null;
            }
            EnergyConsumerStats energyConsumerStats2 = this.mUidEnergyConsumerStats;
            return energyConsumerStats2 == null ? new long[energyConsumerStats.getNumberCustomPowerBuckets()] : energyConsumerStats2.getAccumulatedCustomBucketCharges();
        }

        public final void getDeferredJobsCheckinLineLocked(StringBuilder sb, int i) {
            sb.setLength(0);
            int i2 = this.mJobsDeferredEventCount.mCount.get();
            if (i2 == 0) {
                return;
            }
            int i3 = this.mJobsDeferredCount.mCount.get();
            long j = this.mJobsFreshnessTimeMs.mCount;
            sb.append(i2);
            sb.append(',');
            sb.append(i3);
            sb.append(',');
            sb.append(j);
            for (int i4 = 0; i4 < BatteryStats.JOB_FRESHNESS_BUCKETS.length; i4++) {
                if (this.mJobsFreshnessBuckets[i4] == null) {
                    sb.append(",0");
                } else {
                    sb.append(",");
                    sb.append(this.mJobsFreshnessBuckets[i4].mCount.get());
                }
            }
        }

        public final void getDeferredJobsLineLocked(StringBuilder sb, int i) {
            int i2 = 0;
            sb.setLength(0);
            int i3 = this.mJobsDeferredEventCount.mCount.get();
            if (i3 == 0) {
                return;
            }
            int i4 = this.mJobsDeferredCount.mCount.get();
            long j = this.mJobsFreshnessTimeMs.mCount;
            sb.append("times=");
            sb.append(i3);
            sb.append(", ");
            sb.append("count=");
            sb.append(i4);
            sb.append(", ");
            sb.append("totalLatencyMs=");
            sb.append(j);
            sb.append(", ");
            while (true) {
                long[] jArr = BatteryStats.JOB_FRESHNESS_BUCKETS;
                if (i2 >= jArr.length) {
                    return;
                }
                sb.append("<");
                sb.append(jArr[i2]);
                sb.append("ms=");
                Counter counter = this.mJobsFreshnessBuckets[i2];
                if (counter == null) {
                    sb.append("0");
                } else {
                    sb.append(counter.mCount.get());
                }
                sb.append(" ");
                i2++;
            }
        }

        public final long getDisplayTopActivityTime(int i, long j, int i2) {
            StopwatchTimer[] stopwatchTimerArr;
            StopwatchTimer stopwatchTimer;
            if (i < 0 || i >= this.mBsi.mPerDisplayBatteryStats.length || (stopwatchTimerArr = this.mPerDisplayTopActivityTimer) == null || (stopwatchTimer = stopwatchTimerArr[i]) == null) {
                return 0L;
            }
            return stopwatchTimer.getTotalTimeLocked(j, i2);
        }

        public final long getEnergyConsumptionUC(int i) {
            EnergyConsumerStats energyConsumerStats = this.mBsi.mGlobalEnergyConsumerStats;
            if (energyConsumerStats == null || !energyConsumerStats.isStandardBucketSupported(i)) {
                return -1L;
            }
            EnergyConsumerStats energyConsumerStats2 = this.mUidEnergyConsumerStats;
            if (energyConsumerStats2 == null) {
                return 0L;
            }
            return energyConsumerStats2.getAccumulatedStandardBucketCharge(i);
        }

        public final long getEnergyConsumptionUC(int i, int i2) {
            EnergyConsumerStats energyConsumerStats = this.mBsi.mGlobalEnergyConsumerStats;
            if (energyConsumerStats == null || !energyConsumerStats.isStandardBucketSupported(i)) {
                return -1L;
            }
            EnergyConsumerStats energyConsumerStats2 = this.mUidEnergyConsumerStats;
            if (energyConsumerStats2 == null) {
                return 0L;
            }
            return energyConsumerStats2.getAccumulatedStandardBucketCharge(i, i2);
        }

        public final BatteryStats.Timer getFlashlightTurnedOnTimer() {
            return this.mFlashlightTurnedOnTimer;
        }

        public final BatteryStats.Timer getForegroundActivityTimer() {
            return this.mForegroundActivityTimer;
        }

        public final BatteryStats.Timer getForegroundServiceTimer() {
            return this.mForegroundServiceTimer;
        }

        public final long getFullWifiLockTime(long j, int i) {
            StopwatchTimer stopwatchTimer = this.mFullWifiLockTimer;
            if (stopwatchTimer == null) {
                return 0L;
            }
            return stopwatchTimer.getTotalTimeLocked(j, i);
        }

        public final long getGnssEnergyConsumptionUC() {
            return getEnergyConsumptionUC(6);
        }

        public final ArrayMap getJobCompletionStats() {
            return this.mJobCompletions;
        }

        public final ArrayMap getJobStats() {
            return this.mJobStats.mMap;
        }

        public final int getMobileRadioActiveCount(int i) {
            LongSamplingCounter longSamplingCounter = this.mMobileRadioActiveCount;
            if (longSamplingCounter != null) {
                return (int) longSamplingCounter.mCount;
            }
            return 0;
        }

        public final long getMobileRadioActiveTime(int i) {
            return getMobileRadioActiveTimeInProcessState(0);
        }

        public final TimeMultiStateCounter getMobileRadioActiveTimeCounter() {
            if (this.mMobileRadioActiveTime == null) {
                long elapsedRealtime = this.mBsi.mClock.elapsedRealtime();
                TimeMultiStateCounter timeMultiStateCounter = new TimeMultiStateCounter(this.mBsi.mOnBatteryTimeBase, 5, elapsedRealtime);
                this.mMobileRadioActiveTime = timeMultiStateCounter;
                timeMultiStateCounter.mCounter.setState(BatteryStats.mapUidProcessStateToBatteryConsumerProcessState(this.mProcessState), elapsedRealtime);
                this.mMobileRadioActiveTime.mCounter.updateValue(0L, elapsedRealtime);
            }
            return this.mMobileRadioActiveTime;
        }

        public final long getMobileRadioActiveTimeInProcessState(int i) {
            TimeMultiStateCounter timeMultiStateCounter = this.mMobileRadioActiveTime;
            if (timeMultiStateCounter == null) {
                return 0L;
            }
            return i == 0 ? timeMultiStateCounter.mCounter.getTotalCount() : timeMultiStateCounter.mCounter.getCount(i);
        }

        public final long getMobileRadioApWakeupCount(int i) {
            LongSamplingCounter longSamplingCounter = this.mMobileRadioApWakeupCount;
            if (longSamplingCounter != null) {
                return longSamplingCounter.mCount;
            }
            return 0L;
        }

        public final long getMobileRadioEnergyConsumptionUC() {
            return getEnergyConsumptionUC(7);
        }

        public final long getMobileRadioEnergyConsumptionUC(int i) {
            return getEnergyConsumptionUC(7, i);
        }

        public final BatteryStats.ControllerActivityCounter getModemControllerActivity() {
            return this.mModemControllerActivity;
        }

        public final BatteryStats.Timer getMulticastWakelockStats() {
            return this.mWifiMulticastTimer;
        }

        public final long getNetworkActivityBytes(int i, int i2) {
            LongSamplingCounter[] longSamplingCounterArr = this.mNetworkByteActivityCounters;
            if (longSamplingCounterArr == null || i < 0 || i >= longSamplingCounterArr.length) {
                return 0L;
            }
            return longSamplingCounterArr[i].mCount;
        }

        public final long getNetworkActivityPackets(int i, int i2) {
            LongSamplingCounter[] longSamplingCounterArr = this.mNetworkPacketActivityCounters;
            if (longSamplingCounterArr == null || i < 0 || i >= longSamplingCounterArr.length) {
                return 0L;
            }
            return longSamplingCounterArr[i].mCount;
        }

        public final ControllerActivityCounterImpl getOrCreateBluetoothControllerActivityLocked() {
            if (this.mBluetoothControllerActivity == null) {
                BatteryStatsImpl batteryStatsImpl = this.mBsi;
                this.mBluetoothControllerActivity = new ControllerActivityCounterImpl(1, batteryStatsImpl.mClock, batteryStatsImpl.mOnBatteryTimeBase);
            }
            return this.mBluetoothControllerActivity;
        }

        public final ControllerActivityCounterImpl getOrCreateWifiControllerActivityLocked() {
            if (this.mWifiControllerActivity == null) {
                BatteryStatsImpl batteryStatsImpl = this.mBsi;
                this.mWifiControllerActivity = new ControllerActivityCounterImpl(1, batteryStatsImpl.mClock, batteryStatsImpl.mOnBatteryTimeBase);
            }
            return this.mWifiControllerActivity;
        }

        public final ArrayMap getPackageStats() {
            return this.mPackageStats;
        }

        public final Pkg getPackageStatsLocked(String str) {
            Pkg pkg = (Pkg) this.mPackageStats.get(str);
            if (pkg != null) {
                return pkg;
            }
            Pkg pkg2 = new Pkg(this.mBsi);
            this.mPackageStats.put(str, pkg2);
            return pkg2;
        }

        public final SparseArray getPidStats() {
            return this.mPids;
        }

        public final long getProcessStateTime(int i, long j, int i2) {
            StopwatchTimer stopwatchTimer;
            if (i < 0 || i >= 7 || (stopwatchTimer = this.mProcessStateTimer[i]) == null) {
                return 0L;
            }
            return stopwatchTimer.getTotalTimeLocked(j, i2);
        }

        public final BatteryStats.Timer getProcessStateTimer(int i) {
            if (i < 0 || i >= 7) {
                return null;
            }
            return this.mProcessStateTimer[i];
        }

        public final ArrayMap getProcessStats() {
            return this.mProcessStats;
        }

        public final Proc getProcessStatsLocked(String str) {
            Proc proc = (Proc) this.mProcessStats.get(str);
            if (proc != null) {
                return proc;
            }
            BatteryStatsImpl batteryStatsImpl = this.mBsi;
            Proc proc2 = new Proc();
            proc2.mActive = true;
            proc2.mBsi = batteryStatsImpl;
            batteryStatsImpl.mOnBatteryTimeBase.add(proc2);
            this.mProcessStats.put(str, proc2);
            return proc2;
        }

        public final double getProportionalSystemServiceUsage() {
            return this.mProportionalSystemServiceUsage;
        }

        public final boolean getScreenOffCpuFreqTimes(long[] jArr, int i) {
            TimeInFreqMultiStateCounter timeInFreqMultiStateCounter;
            if (i < 0 || i >= 7 || (timeInFreqMultiStateCounter = this.mProcStateScreenOffTimeMs) == null) {
                return false;
            }
            if (this.mBsi.mPerProcStateCpuTimesAvailable) {
                return timeInFreqMultiStateCounter.getCountsLocked(i, jArr);
            }
            this.mProcStateScreenOffTimeMs = null;
            return false;
        }

        public final long[] getScreenOffCpuFreqTimes(int i) {
            return nullIfAllZeros(this.mScreenOffCpuFreqTimeMs, i);
        }

        public final long getScreenOnEnergyConsumptionUC() {
            return getEnergyConsumptionUC(0);
        }

        public final SparseArray getSensorStats() {
            return this.mSensorStats;
        }

        public final DualTimer getSensorTimerLocked(int i, boolean z) {
            Sensor sensor = (Sensor) this.mSensorStats.get(i);
            if (sensor == null) {
                if (!z) {
                    return null;
                }
                sensor = new Sensor(i);
                this.mSensorStats.put(i, sensor);
            }
            DualTimer dualTimer = sensor.mTimer;
            if (dualTimer != null) {
                return dualTimer;
            }
            ArrayList arrayList = (ArrayList) this.mBsi.mSensorTimers.get(i);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.mBsi.mSensorTimers.put(i, arrayList);
            }
            ArrayList arrayList2 = arrayList;
            BatteryStatsImpl batteryStatsImpl = this.mBsi;
            DualTimer dualTimer2 = new DualTimer(batteryStatsImpl.mClock, this, 3, arrayList2, batteryStatsImpl.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase);
            sensor.mTimer = dualTimer2;
            return dualTimer2;
        }

        public final Pkg.Serv getServiceStatsLocked(String str, String str2) {
            Pkg packageStatsLocked = getPackageStatsLocked(str);
            Pkg.Serv serv = (Pkg.Serv) packageStatsLocked.mServiceStats.get(str2);
            if (serv != null) {
                return serv;
            }
            BatteryStatsImpl batteryStatsImpl = packageStatsLocked.mBsi;
            Pkg.Serv serv2 = new Pkg.Serv();
            serv2.mBsi = batteryStatsImpl;
            batteryStatsImpl.mOnBatteryTimeBase.add(serv2);
            packageStatsLocked.mServiceStats.put(str2, serv2);
            return serv2;
        }

        public final long getSpeakerMediaTime(int i, int i2) {
            LongSamplingCounter[] longSamplingCounterArr = this.mSpeakerMediaTimeCounters;
            if (longSamplingCounterArr != null && i >= 0 && i < longSamplingCounterArr.length) {
                return longSamplingCounterArr[i].mCount;
            }
            return 0L;
        }

        public final ArrayMap getSyncStats() {
            return this.mSyncStats.mMap;
        }

        public final long getSystemCpuTimeUs(int i) {
            return this.mSystemCpuTime.mCount;
        }

        public final long getTimeAtCpuSpeed(int i, int i2, int i3) {
            LongSamplingCounter[] longSamplingCounterArr;
            LongSamplingCounter longSamplingCounter;
            LongSamplingCounter[][] longSamplingCounterArr2 = this.mCpuClusterSpeedTimesUs;
            if (longSamplingCounterArr2 == null || i < 0 || i >= longSamplingCounterArr2.length || (longSamplingCounterArr = longSamplingCounterArr2[i]) == null || i2 < 0 || i2 >= longSamplingCounterArr.length || (longSamplingCounter = longSamplingCounterArr[i2]) == null) {
                return 0L;
            }
            return longSamplingCounter.mCount;
        }

        public final int getUid() {
            return this.mUid;
        }

        public final int getUserActivityCount(int i, int i2) {
            Counter[] counterArr = this.mUserActivityCounters;
            if (counterArr == null) {
                return 0;
            }
            return counterArr[i].mCount.get();
        }

        public final long getUserCpuTimeUs(int i) {
            return this.mUserCpuTime.mCount;
        }

        public final BatteryStats.Timer getVibratorOnTimer() {
            return this.mVibratorOnTimer;
        }

        public final BatteryStats.Timer getVideoTurnedOnTimer() {
            return this.mVideoTurnedOnTimer;
        }

        public final ArrayMap getWakelockStats() {
            return this.mWakelockStats.mMap;
        }

        public final StopwatchTimer getWakelockTimerLocked(Wakelock wakelock, int i) {
            if (i == 0) {
                DualTimer dualTimer = wakelock.mTimerPartial;
                if (dualTimer != null) {
                    return dualTimer;
                }
                BatteryStatsImpl batteryStatsImpl = this.mBsi;
                DualTimer dualTimer2 = new DualTimer(batteryStatsImpl.mClock, this, 0, batteryStatsImpl.mPartialTimers, batteryStatsImpl.mOnBatteryScreenOffTimeBase, this.mOnBatteryScreenOffBackgroundTimeBase);
                wakelock.mTimerPartial = dualTimer2;
                return dualTimer2;
            }
            if (i == 1) {
                StopwatchTimer stopwatchTimer = wakelock.mTimerFull;
                if (stopwatchTimer != null) {
                    return stopwatchTimer;
                }
                BatteryStatsImpl batteryStatsImpl2 = this.mBsi;
                StopwatchTimer stopwatchTimer2 = new StopwatchTimer(batteryStatsImpl2.mClock, this, 1, batteryStatsImpl2.mFullTimers, batteryStatsImpl2.mOnBatteryTimeBase);
                wakelock.mTimerFull = stopwatchTimer2;
                return stopwatchTimer2;
            }
            if (i == 2) {
                StopwatchTimer stopwatchTimer3 = wakelock.mTimerWindow;
                if (stopwatchTimer3 != null) {
                    return stopwatchTimer3;
                }
                BatteryStatsImpl batteryStatsImpl3 = this.mBsi;
                StopwatchTimer stopwatchTimer4 = new StopwatchTimer(batteryStatsImpl3.mClock, this, 2, batteryStatsImpl3.mWindowTimers, batteryStatsImpl3.mOnBatteryTimeBase);
                wakelock.mTimerWindow = stopwatchTimer4;
                return stopwatchTimer4;
            }
            if (i != 18) {
                throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "type="));
            }
            StopwatchTimer stopwatchTimer5 = wakelock.mTimerDraw;
            if (stopwatchTimer5 != null) {
                return stopwatchTimer5;
            }
            BatteryStatsImpl batteryStatsImpl4 = this.mBsi;
            StopwatchTimer stopwatchTimer6 = new StopwatchTimer(batteryStatsImpl4.mClock, this, 18, batteryStatsImpl4.mDrawTimers, batteryStatsImpl4.mOnBatteryTimeBase);
            wakelock.mTimerDraw = stopwatchTimer6;
            return stopwatchTimer6;
        }

        public final int getWifiBatchedScanCount(int i, int i2) {
            StopwatchTimer stopwatchTimer;
            if (i < 0 || i >= 5 || (stopwatchTimer = this.mWifiBatchedScanTimer[i]) == null) {
                return 0;
            }
            return stopwatchTimer.computeCurrentCountLocked();
        }

        public final long getWifiBatchedScanTime(int i, long j, int i2) {
            StopwatchTimer stopwatchTimer;
            if (i < 0 || i >= 5 || (stopwatchTimer = this.mWifiBatchedScanTimer[i]) == null) {
                return 0L;
            }
            return stopwatchTimer.getTotalTimeLocked(j, i2);
        }

        public final BatteryStats.ControllerActivityCounter getWifiControllerActivity() {
            return this.mWifiControllerActivity;
        }

        public final long getWifiEnergyConsumptionUC() {
            return getEnergyConsumptionUC(4);
        }

        public final long getWifiEnergyConsumptionUC(int i) {
            return getEnergyConsumptionUC(4, i);
        }

        public final long getWifiMulticastTime(long j, int i) {
            StopwatchTimer stopwatchTimer = this.mWifiMulticastTimer;
            if (stopwatchTimer == null) {
                return 0L;
            }
            return stopwatchTimer.getTotalTimeLocked(j, i);
        }

        public final long getWifiRadioApWakeupCount(int i) {
            LongSamplingCounter longSamplingCounter = this.mWifiRadioApWakeupCount;
            if (longSamplingCounter != null) {
                return longSamplingCounter.mCount;
            }
            return 0L;
        }

        public final long getWifiRunningTime(long j, int i) {
            StopwatchTimer stopwatchTimer = this.mWifiRunningTimer;
            if (stopwatchTimer == null) {
                return 0L;
            }
            return stopwatchTimer.getTotalTimeLocked(j, i);
        }

        public final long getWifiScanActualTime(long j) {
            DualTimer dualTimer = this.mWifiScanTimer;
            if (dualTimer == null) {
                return 0L;
            }
            return dualTimer.getTotalDurationMsLocked((j + 500) / 1000) * 1000;
        }

        public final int getWifiScanBackgroundCount(int i) {
            DurationTimer durationTimer;
            DualTimer dualTimer = this.mWifiScanTimer;
            if (dualTimer == null || (durationTimer = dualTimer.mSubTimer) == null) {
                return 0;
            }
            return durationTimer.computeCurrentCountLocked();
        }

        public final long getWifiScanBackgroundTime(long j) {
            DurationTimer durationTimer;
            DualTimer dualTimer = this.mWifiScanTimer;
            if (dualTimer == null || (durationTimer = dualTimer.mSubTimer) == null) {
                return 0L;
            }
            return durationTimer.getTotalDurationMsLocked((j + 500) / 1000) * 1000;
        }

        public final BatteryStats.Timer getWifiScanBackgroundTimer() {
            DualTimer dualTimer = this.mWifiScanTimer;
            if (dualTimer == null) {
                return null;
            }
            return dualTimer.mSubTimer;
        }

        public final int getWifiScanCount(int i) {
            DualTimer dualTimer = this.mWifiScanTimer;
            if (dualTimer == null) {
                return 0;
            }
            return dualTimer.computeCurrentCountLocked();
        }

        public final long getWifiScanTime(long j, int i) {
            DualTimer dualTimer = this.mWifiScanTimer;
            if (dualTimer == null) {
                return 0L;
            }
            return dualTimer.getTotalTimeLocked(j, i);
        }

        public final BatteryStats.Timer getWifiScanTimer() {
            return this.mWifiScanTimer;
        }

        public final boolean hasNetworkActivity() {
            return this.mNetworkByteActivityCounters != null;
        }

        public final boolean hasSpeakerActivity() {
            return this.mSpeakerMediaTimeCounters != null;
        }

        public final boolean hasUserActivity() {
            return this.mUserActivityCounters != null;
        }

        public final void initSpeakerTimeCounterLocked() {
            BatteryStatsImpl.detachIfNotNull(this.mSpeakerMediaTimeCounters);
            this.mSpeakerMediaTimeCounters = new LongSamplingCounter[16];
            for (int i = 0; i < 16; i++) {
                this.mSpeakerMediaTimeCounters[i] = new LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            }
        }

        public final void initUserActivityLocked() {
            BatteryStatsImpl.detachIfNotNull(this.mUserActivityCounters);
            this.mUserActivityCounters = new Counter[BatteryStats.Uid.NUM_USER_ACTIVITY_TYPES];
            for (int i = 0; i < BatteryStats.Uid.NUM_USER_ACTIVITY_TYPES; i++) {
                this.mUserActivityCounters[i] = new Counter(this.mBsi.mOnBatteryTimeBase);
            }
        }

        public final void makeDisplayTopActivityTimer(int i) {
            BatteryStatsImpl.detachIfNotNull(this.mPerDisplayTopActivityTimer[i]);
            StopwatchTimer[] stopwatchTimerArr = this.mPerDisplayTopActivityTimer;
            BatteryStatsImpl batteryStatsImpl = this.mBsi;
            stopwatchTimerArr[i] = new StopwatchTimer(batteryStatsImpl.mClock, this, 12, null, batteryStatsImpl.mOnBatteryTimeBase);
        }

        public final void makeProcessState(int i) {
            if (i < 0 || i >= 7) {
                return;
            }
            BatteryStatsImpl.detachIfNotNull(this.mProcessStateTimer[i]);
            StopwatchTimer[] stopwatchTimerArr = this.mProcessStateTimer;
            BatteryStatsImpl batteryStatsImpl = this.mBsi;
            stopwatchTimerArr[i] = new StopwatchTimer(batteryStatsImpl.mClock, this, 12, null, batteryStatsImpl.mOnBatteryTimeBase);
        }

        public final void makeWifiBatchedScanBin(int i) {
            if (i < 0 || i >= 5) {
                return;
            }
            ArrayList arrayList = (ArrayList) this.mBsi.mWifiBatchedScanTimers.get(i);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.mBsi.mWifiBatchedScanTimers.put(i, arrayList);
            }
            ArrayList arrayList2 = arrayList;
            BatteryStatsImpl.detachIfNotNull(this.mWifiBatchedScanTimer[i]);
            StopwatchTimer[] stopwatchTimerArr = this.mWifiBatchedScanTimer;
            BatteryStatsImpl batteryStatsImpl = this.mBsi;
            stopwatchTimerArr[i] = new StopwatchTimer(batteryStatsImpl.mClock, this, 11, arrayList2, batteryStatsImpl.mOnBatteryTimeBase);
        }

        public final void noteActivityPausedLocked(long j) {
            StopwatchTimer stopwatchTimer = this.mForegroundActivityTimer;
            if (stopwatchTimer != null) {
                stopwatchTimer.stopRunningLocked(j);
            }
        }

        public final void noteActivityResumedLocked(long j) {
            if (this.mForegroundActivityTimer == null) {
                BatteryStatsImpl batteryStatsImpl = this.mBsi;
                this.mForegroundActivityTimer = new StopwatchTimer(batteryStatsImpl.mClock, this, 10, null, batteryStatsImpl.mOnBatteryTimeBase);
            }
            this.mForegroundActivityTimer.startRunningLocked(j);
        }

        public final void noteBinderCallStatsLocked(long j, Collection collection) {
            BinderCallStats binderCallStats;
            this.mBinderCallCount += j;
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                BinderCallsStats.CallStat callStat = (BinderCallsStats.CallStat) it.next();
                BinderCallStats binderCallStats2 = sTempBinderCallStats;
                binderCallStats2.binderClass = callStat.binderClass;
                binderCallStats2.transactionCode = callStat.transactionCode;
                int indexOf = this.mBinderCallStats.indexOf(binderCallStats2);
                if (indexOf >= 0) {
                    binderCallStats = (BinderCallStats) this.mBinderCallStats.valueAt(indexOf);
                } else {
                    binderCallStats = new BinderCallStats();
                    binderCallStats.binderClass = callStat.binderClass;
                    binderCallStats.transactionCode = callStat.transactionCode;
                    this.mBinderCallStats.add(binderCallStats);
                }
                binderCallStats.callCount += callStat.incrementalCallCount;
                binderCallStats.recordedCallCount = callStat.recordedCallCount;
                binderCallStats.recordedCpuTimeMicros = callStat.cpuTimeMicros;
            }
        }

        public final void noteBluetoothScanStartedLocked(int i, long j, boolean z) {
            DutyTimer dutyTimer = this.mBluetoothDutyScanTimer;
            if (dutyTimer != null && dutyTimer.isRunningLocked()) {
                this.mBluetoothDutyScanTimer.stopRunningLocked(j);
            }
            createBluetoothDutyScanTimerLocked().mDutyCycle = i;
            DutyTimer dutyTimer2 = this.mBluetoothDutyScanTimer;
            int i2 = dutyTimer2.mNesting;
            dutyTimer2.mNesting = i2 + 1;
            if (i2 == 0) {
                dutyTimer2.mUpdateTime = dutyTimer2.mTimeBase.getRealtime(1000 * j);
                if (dutyTimer2.mTimeBase.mRunning) {
                    dutyTimer2.mCount++;
                    dutyTimer2.mAcquireTime = dutyTimer2.mTotalTimeUs;
                } else {
                    dutyTimer2.mAcquireTime = -1L;
                }
            }
            if (this.mBluetoothScanTimer == null) {
                BatteryStatsImpl batteryStatsImpl = this.mBsi;
                this.mBluetoothScanTimer = new DualTimer(batteryStatsImpl.mClock, this, 19, batteryStatsImpl.mBluetoothScanOnTimers, batteryStatsImpl.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase);
            }
            this.mBluetoothScanTimer.startRunningLocked(j);
            if (z) {
                if (this.mBluetoothUnoptimizedScanTimer == null) {
                    BatteryStatsImpl batteryStatsImpl2 = this.mBsi;
                    this.mBluetoothUnoptimizedScanTimer = new DualTimer(batteryStatsImpl2.mClock, this, 21, null, batteryStatsImpl2.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase);
                }
                this.mBluetoothUnoptimizedScanTimer.startRunningLocked(j);
            }
        }

        public final void noteFullWifiLockAcquiredLocked(long j) {
            if (this.mFullWifiLockOut) {
                return;
            }
            this.mFullWifiLockOut = true;
            if (this.mFullWifiLockTimer == null) {
                BatteryStatsImpl batteryStatsImpl = this.mBsi;
                this.mFullWifiLockTimer = new StopwatchTimer(batteryStatsImpl.mClock, this, 5, batteryStatsImpl.mFullWifiLockTimers, batteryStatsImpl.mOnBatteryTimeBase);
            }
            this.mFullWifiLockTimer.startRunningLocked(j);
        }

        public final void noteFullWifiLockReleasedLocked(long j) {
            if (this.mFullWifiLockOut) {
                this.mFullWifiLockOut = false;
                this.mFullWifiLockTimer.stopRunningLocked(j);
            }
        }

        public final void noteNetworkActivityLocked(int i, long j, long j2) {
            ensureNetworkActivityLocked();
            if (i < 0 || i >= 10) {
                Slog.w("BatteryStatsImpl", BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "Unknown network activity type ", " was specified."), new Throwable());
            } else {
                this.mNetworkByteActivityCounters[i].addCountLocked(j);
                this.mNetworkPacketActivityCounters[i].addCountLocked(j2);
            }
        }

        public final void noteUserActivityLocked(int i) {
            if (this.mUserActivityCounters == null) {
                initUserActivityLocked();
            }
            if (i < 0 || i >= BatteryStats.Uid.NUM_USER_ACTIVITY_TYPES) {
                Slog.w("BatteryStatsImpl", BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "Unknown user activity type ", " was specified."), new Throwable());
            } else {
                this.mUserActivityCounters[i].stepAtomic();
            }
        }

        public final void noteWifiBatchedScanStartedLocked(int i, long j) {
            int i2 = 0;
            while (i > 8 && i2 < 4) {
                i >>= 3;
                i2++;
            }
            int i3 = this.mWifiBatchedScanBinStarted;
            if (i3 == i2) {
                return;
            }
            if (i3 != -1) {
                this.mWifiBatchedScanTimer[i3].stopRunningLocked(j);
            }
            this.mWifiBatchedScanBinStarted = i2;
            if (this.mWifiBatchedScanTimer[i2] == null) {
                makeWifiBatchedScanBin(i2);
            }
            this.mWifiBatchedScanTimer[i2].startRunningLocked(j);
        }

        public final void noteWifiBatchedScanStoppedLocked(long j) {
            int i = this.mWifiBatchedScanBinStarted;
            if (i != -1) {
                this.mWifiBatchedScanTimer[i].stopRunningLocked(j);
                this.mWifiBatchedScanBinStarted = -1;
            }
        }

        public final void noteWifiMulticastDisabledLocked(long j) {
            int i = this.mWifiMulticastWakelockCount;
            if (i == 0) {
                return;
            }
            int i2 = i - 1;
            this.mWifiMulticastWakelockCount = i2;
            if (i2 == 0) {
                this.mWifiMulticastTimer.stopRunningLocked(j);
            }
        }

        public final void noteWifiMulticastEnabledLocked(long j) {
            if (this.mWifiMulticastWakelockCount == 0) {
                if (this.mWifiMulticastTimer == null) {
                    BatteryStatsImpl batteryStatsImpl = this.mBsi;
                    this.mWifiMulticastTimer = new StopwatchTimer(batteryStatsImpl.mClock, this, 7, batteryStatsImpl.mWifiMulticastTimers, batteryStatsImpl.mOnBatteryTimeBase);
                }
                this.mWifiMulticastTimer.startRunningLocked(j);
            }
            this.mWifiMulticastWakelockCount++;
        }

        public final void noteWifiRunningLocked(long j) {
            if (this.mWifiRunning) {
                return;
            }
            this.mWifiRunning = true;
            if (this.mWifiRunningTimer == null) {
                BatteryStatsImpl batteryStatsImpl = this.mBsi;
                this.mWifiRunningTimer = new StopwatchTimer(batteryStatsImpl.mClock, this, 4, batteryStatsImpl.mWifiRunningTimers, batteryStatsImpl.mOnBatteryTimeBase);
            }
            this.mWifiRunningTimer.startRunningLocked(j);
        }

        public final void noteWifiScanStartedLocked(long j) {
            if (this.mWifiScanStarted) {
                return;
            }
            this.mWifiScanStarted = true;
            if (this.mWifiScanTimer == null) {
                BatteryStatsImpl batteryStatsImpl = this.mBsi;
                this.mWifiScanTimer = new DualTimer(batteryStatsImpl.mClock, this, 6, batteryStatsImpl.mWifiScanTimers, batteryStatsImpl.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase);
            }
            this.mWifiScanTimer.startRunningLocked(j);
        }

        public final void noteWifiScanStoppedLocked(long j) {
            if (this.mWifiScanStarted) {
                this.mWifiScanStarted = false;
                this.mWifiScanTimer.stopRunningLocked(j);
            }
        }

        public final void noteWifiStoppedLocked(long j) {
            if (this.mWifiRunning) {
                this.mWifiRunning = false;
                this.mWifiRunningTimer.stopRunningLocked(j);
            }
        }

        public final void readJobCompletionsFromParcelLocked(Parcel parcel) {
            int readInt = parcel.readInt();
            this.mJobCompletions.clear();
            for (int i = 0; i < readInt; i++) {
                String readString = parcel.readString();
                int readInt2 = parcel.readInt();
                if (readInt2 > 0) {
                    SparseIntArray sparseIntArray = new SparseIntArray();
                    for (int i2 = 0; i2 < readInt2; i2++) {
                        sparseIntArray.put(parcel.readInt(), parcel.readInt());
                    }
                    this.mJobCompletions.put(readString, sparseIntArray);
                }
            }
        }

        public final void readJobSummaryFromParcelLocked(String str, Parcel parcel) {
            DualTimer dualTimer = (DualTimer) this.mJobStats.instantiateObject();
            dualTimer.readSummaryFromParcelLocked(parcel);
            this.mJobStats.add(str, dualTimer);
        }

        public final void readSyncSummaryFromParcelLocked(String str, Parcel parcel) {
            DualTimer dualTimer = (DualTimer) this.mSyncStats.instantiateObject();
            dualTimer.readSummaryFromParcelLocked(parcel);
            this.mSyncStats.add(str, dualTimer);
        }

        public final void readWakeSummaryFromParcelLocked(String str, Parcel parcel) {
            Wakelock wakelock = new Wakelock();
            this.mWakelockStats.add(str, wakelock);
            if (parcel.readInt() != 0) {
                getWakelockTimerLocked(wakelock, 1).readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                getWakelockTimerLocked(wakelock, 0).readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                getWakelockTimerLocked(wakelock, 2).readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                getWakelockTimerLocked(wakelock, 18).readSummaryFromParcelLocked(parcel);
            }
        }

        public boolean reset(long j, long j2, int i) {
            this.mOnBatteryBackgroundTimeBase.init(j, j2);
            this.mOnBatteryScreenOffBackgroundTimeBase.init(j, j2);
            boolean z = this.mWifiRunningTimer != null ? (!r8.reset(j2, false)) | this.mWifiRunning : false;
            if (this.mFullWifiLockTimer != null) {
                z = z | (!r1.reset(j2, false)) | this.mFullWifiLockOut;
            }
            if (this.mWifiScanTimer != null) {
                z = z | (!r1.reset(j2, false)) | this.mWifiScanStarted;
            }
            if (this.mWifiBatchedScanTimer != null) {
                for (int i2 = 0; i2 < 5; i2++) {
                    if (this.mWifiBatchedScanTimer[i2] != null) {
                        z |= !r2.reset(j2, false);
                    }
                }
                z |= this.mWifiBatchedScanBinStarted != -1;
            }
            if (this.mWifiMulticastTimer != null) {
                z = z | (!r1.reset(j2, false)) | (this.mWifiMulticastWakelockCount > 0);
            }
            boolean z2 = z | (!BatteryStatsImpl.resetIfNotNull(this.mAudioTurnedOnTimer, j2)) | (!BatteryStatsImpl.resetIfNotNull(this.mVideoTurnedOnTimer, j2)) | (!BatteryStatsImpl.resetIfNotNull(this.mFlashlightTurnedOnTimer, j2)) | (!BatteryStatsImpl.resetIfNotNull(this.mCameraTurnedOnTimer, j2)) | (!BatteryStatsImpl.resetIfNotNull(this.mForegroundActivityTimer, j2)) | (!BatteryStatsImpl.resetIfNotNull(this.mForegroundServiceTimer, j2)) | (!BatteryStatsImpl.resetIfNotNull(this.mAggregatedPartialWakelockTimer, j2)) | (!BatteryStatsImpl.resetIfNotNull(this.mBluetoothDutyScanTimer, j2));
            BatteryStatsImpl.resetIfNotNull(this.mSpeakerMediaTimeCounters, j2);
            if (this.mPerDisplayTopActivityTimer != null) {
                for (int i3 = 0; i3 < this.mBsi.mPerDisplayBatteryStats.length; i3++) {
                    z2 |= !BatteryStatsImpl.resetIfNotNull(this.mPerDisplayTopActivityTimer[i3], j2);
                }
            }
            boolean z3 = z2 | (!BatteryStatsImpl.resetIfNotNull(this.mBluetoothScanTimer, j2)) | (!BatteryStatsImpl.resetIfNotNull(this.mBluetoothUnoptimizedScanTimer, j2));
            BatteryStatsImpl.resetIfNotNull(this.mBluetoothScanResultCounter, j2);
            BatteryStatsImpl.resetIfNotNull(this.mBluetoothScanResultBgCounter, j2);
            if (this.mProcessStateTimer != null) {
                for (int i4 = 0; i4 < 7; i4++) {
                    z3 |= !BatteryStatsImpl.resetIfNotNull(this.mProcessStateTimer[i4], j2);
                }
                z3 |= this.mProcessState != 7;
            }
            BatchTimer batchTimer = this.mVibratorOnTimer;
            if (batchTimer != null) {
                if (batchTimer.reset(j2, false)) {
                    this.mVibratorOnTimer.detach();
                    this.mVibratorOnTimer = null;
                } else {
                    z3 = true;
                }
            }
            BatteryStatsImpl.resetIfNotNull(this.mUserActivityCounters, j2);
            BatteryStatsImpl.resetIfNotNull(this.mNetworkByteActivityCounters, j2);
            BatteryStatsImpl.resetIfNotNull(this.mNetworkPacketActivityCounters, j2);
            BatteryStatsImpl.resetIfNotNull(this.mMobileRadioActiveTime, j2);
            BatteryStatsImpl.resetIfNotNull(this.mMobileRadioActiveCount, j2);
            ControllerActivityCounterImpl controllerActivityCounterImpl = this.mWifiControllerActivity;
            if (controllerActivityCounterImpl != null) {
                controllerActivityCounterImpl.reset(j2);
            }
            ControllerActivityCounterImpl controllerActivityCounterImpl2 = this.mBluetoothControllerActivity;
            if (controllerActivityCounterImpl2 != null) {
                controllerActivityCounterImpl2.reset(j2);
            }
            ControllerActivityCounterImpl controllerActivityCounterImpl3 = this.mModemControllerActivity;
            if (controllerActivityCounterImpl3 != null) {
                controllerActivityCounterImpl3.reset(j2);
            }
            if (i == 4) {
                this.mUidEnergyConsumerStats = null;
            } else {
                EnergyConsumerStats.resetIfNotNull(this.mUidEnergyConsumerStats);
            }
            BatteryStatsImpl.resetIfNotNull(this.mUserCpuTime, j2);
            BatteryStatsImpl.resetIfNotNull(this.mSystemCpuTime, j2);
            LongSamplingCounter[][] longSamplingCounterArr = this.mCpuClusterSpeedTimesUs;
            if (longSamplingCounterArr != null) {
                for (LongSamplingCounter[] longSamplingCounterArr2 : longSamplingCounterArr) {
                    BatteryStatsImpl.resetIfNotNull(longSamplingCounterArr2, j2);
                }
            }
            BatteryStatsImpl.resetIfNotNull(this.mCpuFreqTimeMs, j2);
            BatteryStatsImpl.resetIfNotNull(this.mScreenOffCpuFreqTimeMs, j2);
            BatteryStatsImpl.resetIfNotNull(this.mCpuActiveTimeMs, j2);
            BatteryStatsImpl.resetIfNotNull(this.mCpuClusterTimesMs, j2);
            BatteryStatsImpl.resetIfNotNull(this.mProcStateTimeMs, j2);
            BatteryStatsImpl.resetIfNotNull(this.mProcStateScreenOffTimeMs, j2);
            BatteryStatsImpl.resetIfNotNull(this.mMobileRadioApWakeupCount, j2);
            BatteryStatsImpl.resetIfNotNull(this.mWifiRadioApWakeupCount, j2);
            ArrayMap arrayMap = this.mWakelockStats.mMap;
            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                Wakelock wakelock = (Wakelock) arrayMap.valueAt(size);
                boolean z4 = (!BatteryStatsImpl.resetIfNotNull(wakelock.mTimerFull, j2)) | (!BatteryStatsImpl.resetIfNotNull(wakelock.mTimerPartial, j2)) | (!BatteryStatsImpl.resetIfNotNull(wakelock.mTimerWindow, j2)) | (!BatteryStatsImpl.resetIfNotNull(wakelock.mTimerDraw, j2));
                if (!z4) {
                    BatteryStatsImpl.detachIfNotNull(wakelock.mTimerFull);
                    wakelock.mTimerFull = null;
                    BatteryStatsImpl.detachIfNotNull(wakelock.mTimerPartial);
                    wakelock.mTimerPartial = null;
                    BatteryStatsImpl.detachIfNotNull(wakelock.mTimerWindow);
                    wakelock.mTimerWindow = null;
                    BatteryStatsImpl.detachIfNotNull(wakelock.mTimerDraw);
                    wakelock.mTimerDraw = null;
                }
                if (!z4) {
                    arrayMap.removeAt(size);
                } else {
                    z3 = true;
                }
            }
            long j3 = j2 / 1000;
            this.mWakelockStats.cleanup(j3);
            ArrayMap arrayMap2 = this.mSyncStats.mMap;
            for (int size2 = arrayMap2.size() - 1; size2 >= 0; size2--) {
                DualTimer dualTimer = (DualTimer) arrayMap2.valueAt(size2);
                if (dualTimer.reset(j2, false)) {
                    arrayMap2.removeAt(size2);
                    dualTimer.detach();
                } else {
                    z3 = true;
                }
            }
            this.mSyncStats.cleanup(j3);
            ArrayMap arrayMap3 = this.mJobStats.mMap;
            for (int size3 = arrayMap3.size() - 1; size3 >= 0; size3--) {
                DualTimer dualTimer2 = (DualTimer) arrayMap3.valueAt(size3);
                if (dualTimer2.reset(j2, false)) {
                    arrayMap3.removeAt(size3);
                    dualTimer2.detach();
                } else {
                    z3 = true;
                }
            }
            this.mJobStats.cleanup(j3);
            this.mJobCompletions.clear();
            BatteryStatsImpl.resetIfNotNull(this.mJobsDeferredEventCount, j2);
            BatteryStatsImpl.resetIfNotNull(this.mJobsDeferredCount, j2);
            BatteryStatsImpl.resetIfNotNull(this.mJobsFreshnessTimeMs, j2);
            BatteryStatsImpl.resetIfNotNull(this.mJobsFreshnessBuckets, j2);
            for (int size4 = this.mSensorStats.size() - 1; size4 >= 0; size4--) {
                Sensor sensor = (Sensor) this.mSensorStats.valueAt(size4);
                if (sensor.mTimer.reset(j2, true)) {
                    sensor.mTimer = null;
                    this.mSensorStats.removeAt(size4);
                } else {
                    z3 = true;
                }
            }
            for (int size5 = this.mProcessStats.size() - 1; size5 >= 0; size5--) {
                ((Proc) this.mProcessStats.valueAt(size5)).detach();
            }
            this.mProcessStats.clear();
            for (int size6 = this.mPids.size() - 1; size6 >= 0; size6--) {
                if (((BatteryStats.Uid.Pid) this.mPids.valueAt(size6)).mWakeNesting > 0) {
                    z3 = true;
                } else {
                    this.mPids.removeAt(size6);
                }
            }
            for (int size7 = this.mPackageStats.size() - 1; size7 >= 0; size7--) {
                ((Pkg) this.mPackageStats.valueAt(size7)).detach();
            }
            this.mPackageStats.clear();
            this.mBinderCallCount = 0L;
            this.mBinderCallStats.clear();
            this.mProportionalSystemServiceUsage = 0.0d;
            return !z3;
        }

        public void setProcessStateForTest(int i, long j) {
            this.mProcessState = i;
            ensureMultiStateCounters(j);
            this.mProcStateTimeMs.mCounter.setState(i, j);
            ensureMultiStateCounters(j);
            this.mProcStateScreenOffTimeMs.mCounter.setState(i, j);
            int mapUidProcessStateToBatteryConsumerProcessState = BatteryStats.mapUidProcessStateToBatteryConsumerProcessState(i);
            TimeMultiStateCounter.m842$$Nest$msetState(getCpuActiveTimeCounter(), mapUidProcessStateToBatteryConsumerProcessState, j);
            TimeMultiStateCounter.m842$$Nest$msetState(getMobileRadioActiveTimeCounter(), mapUidProcessStateToBatteryConsumerProcessState, j);
            ControllerActivityCounterImpl controllerActivityCounterImpl = this.mWifiControllerActivity;
            if (controllerActivityCounterImpl != null) {
                ControllerActivityCounterImpl.m840$$Nest$msetState(controllerActivityCounterImpl, mapUidProcessStateToBatteryConsumerProcessState, j);
            }
            ControllerActivityCounterImpl controllerActivityCounterImpl2 = this.mBluetoothControllerActivity;
            if (controllerActivityCounterImpl2 != null) {
                ControllerActivityCounterImpl.m840$$Nest$msetState(controllerActivityCounterImpl2, mapUidProcessStateToBatteryConsumerProcessState, j);
            }
            if (this.mUidEnergyConsumerStats == null && this.mBsi.mEnergyConsumerStatsConfig != null) {
                this.mUidEnergyConsumerStats = new EnergyConsumerStats(this.mBsi.mEnergyConsumerStatsConfig);
            }
            EnergyConsumerStats energyConsumerStats = this.mUidEnergyConsumerStats;
            if (energyConsumerStats != null) {
                energyConsumerStats.setState(mapUidProcessStateToBatteryConsumerProcessState, j);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UidToRemove {
        public final int mEndUid;
        public final int mStartUid;
        public final long mUidRemovalTimestamp;

        public UidToRemove(int i, int i2, long j) {
            this.mStartUid = i;
            this.mEndUid = i2;
            this.mUidRemovalTimestamp = j;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UsageComparator implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            SemSimpleNetworkStats semSimpleNetworkStats = (SemSimpleNetworkStats) obj;
            SemSimpleNetworkStats semSimpleNetworkStats2 = (SemSimpleNetworkStats) obj2;
            if (semSimpleNetworkStats.getTxRxBytes() > semSimpleNetworkStats2.getTxRxBytes()) {
                return -1;
            }
            return semSimpleNetworkStats.getTxRxBytes() < semSimpleNetworkStats2.getTxRxBytes() ? 1 : 0;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class UserInfoProvider {
        public int[] userIds;

        public boolean exists(int i) {
            int[] iArr = this.userIds;
            if (iArr != null) {
                return ArrayUtils.contains(iArr, i);
            }
            return true;
        }

        public final void refreshUserIds() {
            BatteryStatsService.AnonymousClass3 anonymousClass3 = (BatteryStatsService.AnonymousClass3) this;
            if (anonymousClass3.umi == null) {
                anonymousClass3.umi = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
            }
            UserManagerInternal userManagerInternal = anonymousClass3.umi;
            this.userIds = userManagerInternal != null ? userManagerInternal.getUserIds() : null;
        }
    }

    public static void $r8$lambda$4oR0MyxxkPH0JSQXqVxAZgbHyhc(BatteryStatsImpl batteryStatsImpl, PowerStats powerStats) {
        batteryStatsImpl.getClass();
        if (powerStats.durationMs <= 0 || batteryStatsImpl.mIsBuildTypeUser) {
            return;
        }
        synchronized (batteryStatsImpl) {
            batteryStatsImpl.mHistory.recordPowerStats(batteryStatsImpl.mClock.elapsedRealtime(), batteryStatsImpl.mClock.uptimeMillis(), powerStats);
        }
    }

    /* renamed from: -$$Nest$mretrieveBluetoothScanTimesLocked, reason: not valid java name */
    public static void m835$$Nest$mretrieveBluetoothScanTimesLocked(BatteryStatsImpl batteryStatsImpl, BluetoothPowerStatsCollector$$ExternalSyntheticLambda0 bluetoothPowerStatsCollector$$ExternalSyntheticLambda0) {
        long elapsedRealtime = batteryStatsImpl.mClock.elapsedRealtime() * 1000;
        for (int size = batteryStatsImpl.mUidStats.size() - 1; size >= 0; size--) {
            if (((Uid) batteryStatsImpl.mUidStats.valueAt(size)).mBluetoothScanTimer != null) {
                long totalTimeLocked = batteryStatsImpl.mBluetoothScanTimer.getTotalTimeLocked(elapsedRealtime, 0);
                if (totalTimeLocked != 0) {
                    int keyAt = batteryStatsImpl.mUidStats.keyAt(size);
                    long j = (totalTimeLocked + 500) / 1000;
                    BluetoothPowerStatsCollector bluetoothPowerStatsCollector = bluetoothPowerStatsCollector$$ExternalSyntheticLambda0.f$0;
                    int mapUid = bluetoothPowerStatsCollector.mUidResolver.mapUid(keyAt);
                    BluetoothPowerStatsCollector.UidStats uidStats = (BluetoothPowerStatsCollector.UidStats) bluetoothPowerStatsCollector.mUidStats.get(mapUid);
                    if (uidStats == null) {
                        uidStats = new BluetoothPowerStatsCollector.UidStats();
                        bluetoothPowerStatsCollector.mUidStats.put(mapUid, uidStats);
                    }
                    uidStats.scanTime += j;
                }
            }
        }
    }

    /* renamed from: -$$Nest$mretrieveWifiScanTimesLocked, reason: not valid java name */
    public static void m836$$Nest$mretrieveWifiScanTimesLocked(BatteryStatsImpl batteryStatsImpl, WifiPowerStatsCollector$$ExternalSyntheticLambda1 wifiPowerStatsCollector$$ExternalSyntheticLambda1) {
        long j;
        BatteryStatsImpl batteryStatsImpl2 = batteryStatsImpl;
        long elapsedRealtime = batteryStatsImpl2.mClock.elapsedRealtime() * 1000;
        int size = batteryStatsImpl2.mUidStats.size() - 1;
        while (size >= 0) {
            int keyAt = batteryStatsImpl2.mUidStats.keyAt(size);
            Uid uid = (Uid) batteryStatsImpl2.mUidStats.valueAt(size);
            long wifiScanTime = uid.getWifiScanTime(elapsedRealtime, 0);
            long j2 = 0;
            for (int i = 0; i < 5; i++) {
                j2 += uid.getWifiBatchedScanTime(i, elapsedRealtime, 0);
            }
            if (wifiScanTime == 0 && j2 == 0) {
                j = elapsedRealtime;
            } else {
                long j3 = (wifiScanTime + 500) / 1000;
                long j4 = (j2 + 500) / 1000;
                WifiPowerStatsCollector wifiPowerStatsCollector = wifiPowerStatsCollector$$ExternalSyntheticLambda1.f$0;
                WifiPowerStatsCollector.WifiScanTimes wifiScanTimes = (WifiPowerStatsCollector.WifiScanTimes) wifiPowerStatsCollector.mLastScanTimes.get(keyAt);
                if (wifiScanTimes == null) {
                    wifiScanTimes = new WifiPowerStatsCollector.WifiScanTimes();
                    wifiPowerStatsCollector.mLastScanTimes.put(keyAt, wifiScanTimes);
                }
                long max = Math.max(0L, j3 - wifiScanTimes.basicScanTimeMs);
                j = elapsedRealtime;
                long max2 = Math.max(0L, j4 - wifiScanTimes.batchedScanTimeMs);
                if (max != 0 || max2 != 0) {
                    WifiPowerStatsCollector.WifiScanTimes wifiScanTimes2 = wifiPowerStatsCollector.mScanTimes;
                    wifiScanTimes2.basicScanTimeMs += max;
                    wifiScanTimes2.batchedScanTimeMs += max2;
                    int mapUid = wifiPowerStatsCollector.mUidResolver.mapUid(keyAt);
                    long[] jArr = (long[]) wifiPowerStatsCollector.mPowerStats.uidStats.get(mapUid);
                    if (jArr == null) {
                        long[] jArr2 = new long[wifiPowerStatsCollector.mLayout.mUidStatsArrayLength];
                        wifiPowerStatsCollector.mPowerStats.uidStats.put(mapUid, jArr2);
                        WifiPowerStatsLayout wifiPowerStatsLayout = wifiPowerStatsCollector.mLayout;
                        jArr2[wifiPowerStatsLayout.mUidScanTimePosition] = max;
                        jArr2[wifiPowerStatsLayout.mUidBatchScanTimePosition] = max2;
                    } else {
                        WifiPowerStatsLayout wifiPowerStatsLayout2 = wifiPowerStatsCollector.mLayout;
                        int i2 = wifiPowerStatsLayout2.mUidScanTimePosition;
                        jArr[i2] = jArr[i2] + max;
                        int i3 = wifiPowerStatsLayout2.mUidBatchScanTimePosition;
                        jArr[i3] = jArr[i3] + max2;
                    }
                }
                wifiScanTimes.basicScanTimeMs = j3;
                wifiScanTimes.batchedScanTimeMs = j4;
            }
            size--;
            batteryStatsImpl2 = batteryStatsImpl;
            elapsedRealtime = j;
        }
    }

    /* renamed from: -$$Nest$smdetachIfNotNull, reason: not valid java name */
    public static void m837$$Nest$smdetachIfNotNull(ControllerActivityCounterImpl controllerActivityCounterImpl) {
        if (controllerActivityCounterImpl != null) {
            detachIfNotNull(controllerActivityCounterImpl.mIdleTimeMillis);
            controllerActivityCounterImpl.mScanTimeMillis.detach();
            controllerActivityCounterImpl.mSleepTimeMillis.detach();
            detachIfNotNull(controllerActivityCounterImpl.mRxTimeMillis);
            detachIfNotNull(controllerActivityCounterImpl.mTxTimeMillis);
            controllerActivityCounterImpl.mPowerDrainMaMs.detach();
            controllerActivityCounterImpl.mMonitoredRailChargeConsumedMaMs.detach();
        }
    }

    static {
        VERSION = (!Flags.disableSystemServicePowerAttr() ? FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PLATFORM_ROLE_HOLDER_UPDATE_FINISHED : FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PLATFORM_ROLE_HOLDER_UPDATE_FAILED) + 917504;
        MAX_WAKELOCKS_PER_UID = BatteryStats.isLowRamDevice() ? 40 : 200;
        CELL_SIGNAL_STRENGTH_LEVEL_COUNT = BatteryStats.getCellSignalStrengthLevelCount();
        MODEM_TX_POWER_LEVEL_COUNT = BatteryStats.getModemTxPowerLevelCount();
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        ZERO_LONG_COUNTER = anonymousClass1;
        ZERO_LONG_COUNTER_ARRAY = new BatteryStats.LongCounter[]{anonymousClass1};
        SUPPORTED_PER_PROCESS_STATE_STANDARD_ENERGY_BUCKETS = new int[]{3, 7, 4, 5};
    }

    /* JADX WARN: Type inference failed for: r13v15, types: [com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r8v1, types: [com.android.server.power.stats.BatteryStatsImpl$3] */
    public BatteryStatsImpl(BatteryStatsConfig batteryStatsConfig, Clock clock, MonotonicClock monotonicClock, File file, Handler handler, PlatformIdleStateCallback platformIdleStateCallback, EnergyStatsRetriever energyStatsRetriever, UserInfoProvider userInfoProvider, PowerProfile powerProfile, CpuScalingPolicies cpuScalingPolicies, PowerStatsUidResolver powerStatsUidResolver) {
        FrameworkStatsLogger frameworkStatsLogger = new FrameworkStatsLogger();
        BatteryStatsHistory.TraceDelegate traceDelegate = new BatteryStatsHistory.TraceDelegate();
        BatteryStatsHistory.EventLogger eventLogger = new BatteryStatsHistory.EventLogger();
        this.mIsRawSocReported = false;
        this.mIsCoulombCounterReported = false;
        this.mLastBatteryRawUpdateTime = 0L;
        this.mLastBatteryCCUpdateTime = 0L;
        this.mBatteryRaw = -1;
        this.mBatteryCC = -1;
        this.mTmpWakelockStats = new KernelWakelockStats();
        this.mKernelMemoryStats = new LongSparseArray();
        this.mPowerStatsCollectorEnabled = new SparseBooleanArray();
        this.mWifiStatsRetriever = new AnonymousClass2();
        this.mPerProcStateCpuTimesAvailable = true;
        this.mCpuTimeReadsTrackingStartTimeMs = SystemClock.uptimeMillis();
        this.mTmpRpmStats = null;
        this.mLastRpmStatsUpdateTimeMs = -1000L;
        this.mPendingRemovedUids = new LinkedList();
        this.mDeferSetCharging = new Runnable() { // from class: com.android.server.power.stats.BatteryStatsImpl.3
            @Override // java.lang.Runnable
            public final void run() {
                synchronized (BatteryStatsImpl.this) {
                    try {
                        BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
                        if (batteryStatsImpl.mOnBattery) {
                            return;
                        }
                        if (batteryStatsImpl.setChargingLocked(true)) {
                            long uptimeMillis = BatteryStatsImpl.this.mClock.uptimeMillis();
                            BatteryStatsImpl.this.mHistory.writeHistoryItem(BatteryStatsImpl.this.mClock.elapsedRealtime(), uptimeMillis);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mExternalSync = null;
        this.mUserInfoProvider = null;
        this.mUidStats = new SparseArray();
        this.mPartialTimers = new ArrayList();
        this.mFullTimers = new ArrayList();
        this.mWindowTimers = new ArrayList();
        this.mDrawTimers = new ArrayList();
        this.mSensorTimers = new SparseArray();
        this.mWifiRunningTimers = new ArrayList();
        this.mFullWifiLockTimers = new ArrayList();
        this.mWifiMulticastTimers = new ArrayList();
        this.mWifiScanTimers = new ArrayList();
        this.mWifiBatchedScanTimers = new SparseArray();
        this.mAudioTurnedOnTimers = new ArrayList();
        this.mVideoTurnedOnTimers = new ArrayList();
        this.mFlashlightTurnedOnTimers = new ArrayList();
        this.mCameraTurnedOnTimers = new ArrayList();
        this.mBluetoothScanOnTimers = new ArrayList();
        this.mLastPartialTimers = new ArrayList();
        this.mOnBatteryTimeBase = new TimeBase(true);
        this.mOnBatteryScreenOffTimeBase = new TimeBase(true);
        this.mSystemServicesReady = false;
        this.mIsBuildTypeUser = false;
        this.mPendingReportCharging = false;
        this.mIsServiceStateNr = false;
        this.mActiveEvents = new BatteryStats.HistoryEventTracker();
        this.mStepDetailsCalculator = new HistoryStepDetailsCalculatorImpl();
        this.mHaveBatteryLevel = false;
        this.mBatteryPluggedInRealTimeMs = 0L;
        this.mAp_temp = 128;
        this.mPa_temp = 128;
        this.mSkin_temp = 128;
        this.mSub_batt_temp = 128;
        this.mCurrent = 0;
        this.mOtgOnline = 0;
        this.mIsSubScreenOn = false;
        this.mIsSubScreenDoze = false;
        this.mBatterySecTxShareEvent = 0;
        this.mBatterySecOnline = 0;
        this.mBatterySecCurrentEvent = 0;
        this.mBatterySecEvent = 0;
        this.mProtectBatteryMode = -1;
        this.mIgnoreNextExternalStats = false;
        this.mMonotonicEndTime = -1L;
        this.mScreenState = 0;
        this.mScreenBrightnessBin = -1;
        this.mScreenBrightnessTimer = new StopwatchTimer[5];
        this.mDisplayId = 0;
        this.mSubScreenState = 0;
        this.mSubScreenPolicy = -1;
        this.mIsSubScreen = false;
        this.mSubScreenBrightnessTimer = new StopwatchTimer[5];
        this.mTopAppStats = new ConcurrentHashMap();
        this.mAutoBrightnessMode = false;
        this.mScreenAutoBrightnessTimer = new StopwatchTimer[5];
        this.mSubScreenAutoBrightnessTimer = new StopwatchTimer[5];
        this.mScreenHighBrightness = false;
        this.mHighRefreshRateBin = -1;
        this.mHighRefreshRateTimer = new StopwatchTimer[4];
        this.mSubHighRefreshRateTimer = new StopwatchTimer[4];
        this.mDisplayMismatchWtfCount = 0;
        this.mUsbDataState = 0;
        this.mHotspotState = 14;
        this.mGpsSignalQualityBin = -1;
        this.mGpsSignalQualityTimer = new StopwatchTimer[2];
        this.mPhoneSignalStrengthBin = -1;
        this.mPhoneSignalStrengthBinRaw = -1;
        this.mPhoneSignalStrengthsTimer = new StopwatchTimer[CELL_SIGNAL_STRENGTH_LEVEL_COUNT];
        this.mPhoneDataConnectionType = -1;
        this.mPhoneDataConnectionsTimer = new StopwatchTimer[BatteryStats.NUM_DATA_CONNECTION_TYPES];
        this.mNrState = -1;
        this.mActiveRat = 0;
        this.mPerRatBatteryStats = new RadioAccessTechnologyBatteryStats[3];
        this.mNetworkByteActivityCounters = new LongSamplingCounter[10];
        this.mNetworkPacketActivityCounters = new LongSamplingCounter[10];
        this.mHasWifiReporting = false;
        this.mHasBluetoothReporting = false;
        this.mHasModemReporting = false;
        this.mSpeakerMediaTimeCounters = new LongSamplingCounter[16];
        this.mSpeakerCallTimeCounters = new LongSamplingCounter[16];
        this.mHasSpeakerOutReporting = false;
        this.mLastCallbackTime = 0L;
        this.mWifiState = -1;
        this.mWifiStateTimer = new StopwatchTimer[8];
        this.mWifiSupplState = -1;
        this.mWifiSupplStateTimer = new StopwatchTimer[13];
        this.mWifiSignalStrengthBin = -1;
        this.mWifiSignalStrengthsTimer = new StopwatchTimer[5];
        this.mMobileRadioPowerState = 1;
        this.mWifiRadioPowerState = 1;
        this.mBluetoothPowerCalculator = null;
        this.mCpuPowerCalculator = null;
        this.mMobileRadioPowerCalculator = null;
        this.mWifiPowerCalculator = null;
        this.mCharging = true;
        this.mInitStepMode = 0;
        this.mCurStepMode = 0;
        this.mModStepMode = 0;
        this.mDischargeStepTracker = new BatteryStats.LevelStepTracker(200);
        this.mDailyDischargeStepTracker = new BatteryStats.LevelStepTracker(400);
        this.mChargeStepTracker = new BatteryStats.LevelStepTracker(200);
        this.mDailyChargeStepTracker = new BatteryStats.LevelStepTracker(400);
        this.mDailyStartTimeMs = 0L;
        this.mNextMinDailyDeadlineMs = 0L;
        this.mNextMaxDailyDeadlineMs = 0L;
        this.mDailyItems = new ArrayList();
        this.mLastWriteTimeMs = 0L;
        this.mPhoneServiceState = -1;
        this.mPhoneServiceStateRaw = -1;
        this.mPhoneSimStateRaw = -1;
        this.mEstimatedBatteryCapacityMah = -1;
        this.mLastLearnedBatteryCapacityUah = -1;
        this.mMinLearnedBatteryCapacityUah = -1;
        this.mMaxLearnedBatteryCapacityUah = -1;
        this.mBatteryTimeToFullSeconds = -1L;
        this.mAlarmManager = null;
        this.mLongPlugInAlarmHandler = new AlarmManager.OnAlarmListener() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda2
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
                batteryStatsImpl.mHandler.post(new BatteryStatsImpl$$ExternalSyntheticLambda3(1, batteryStatsImpl));
            }
        };
        this.mRpmStats = new HashMap();
        this.mScreenOffRpmStats = new HashMap();
        this.mKernelWakelockStats = new HashMap();
        this.mScreenWakeStats = new HashMap();
        this.mLastWakeupReason = null;
        this.mLastWakeupUptimeMs = 0L;
        this.mLastWakeupElapsedTimeMs = 0L;
        this.mWakeupReasonStats = new HashMap();
        this.mPowerStatsCollectorInjector = new PowerStatsCollectorInjector();
        this.mWifiFullLockNesting = 0;
        this.mWifiScanNesting = 0;
        this.mWifiMulticastNesting = 0;
        this.mWifiNetworkLock = new Object();
        String[] strArr = EmptyArray.STRING;
        this.mWifiIfaces = strArr;
        this.mModemNetworkLock = new Object();
        this.mModemIfaces = strArr;
        this.mLastModemActivityInfo = null;
        this.mLastAudioOutEnergyInfo = new SpeakerOutEnergyInfo(0L, new long[]{0}, new long[]{0}, 0);
        this.mBatteryStatsCallbacks = new RemoteCallbackList();
        this.mNetworkStatsDeltaMap = new HashMap();
        BluetoothActivityInfoCache bluetoothActivityInfoCache = new BluetoothActivityInfoCache();
        bluetoothActivityInfoCache.uidRxBytes = new SparseLongArray();
        bluetoothActivityInfoCache.uidTxBytes = new SparseLongArray();
        this.mLastBluetoothActivityInfo = bluetoothActivityInfoCache;
        this.mFeatureComputeChargeTimeModel = true;
        this.mWriteAsyncRunnable = new BatteryStatsImpl$$ExternalSyntheticLambda3(0, this);
        this.mWriteLock = new ReentrantLock();
        this.mClock = clock;
        this.mLastCallbackTime = clock.elapsedRealtime();
        if (BatteryStats.isKernelStatsAvailable()) {
            this.mCpuUidUserSysTimeReader = new KernelCpuUidTimeReader.KernelCpuUidUserSysTimeReader(true, clock);
            this.mCpuUidFreqTimeReader = new KernelCpuUidTimeReader.KernelCpuUidFreqTimeReader(true, clock);
            this.mCpuUidActiveTimeReader = new KernelCpuUidTimeReader.KernelCpuUidActiveTimeReader(true, clock);
            this.mCpuUidClusterTimeReader = new KernelCpuUidTimeReader.KernelCpuUidClusterTimeReader(true, clock);
            this.mKernelWakelockReader = new KernelWakelockReader();
            if (!Flags.disableSystemServicePowerAttr()) {
                this.mSystemServerCpuThreadReader = new SystemServerCpuThreadReader(KernelSingleProcessCpuThreadReader.create(Process.myPid()));
            }
            this.mKernelMemoryBandwidthStats = new KernelMemoryBandwidthStats();
            this.mTmpRailStats = new RailStats();
        }
        this.mBatteryStatsConfig = batteryStatsConfig;
        this.mMonotonicClock = monotonicClock;
        MyHandler myHandler = new MyHandler(handler.getLooper());
        this.mHandler = myHandler;
        this.mConstants = new Constants(myHandler);
        this.mPowerProfile = powerProfile;
        this.mCpuScalingPolicies = cpuScalingPolicies;
        this.mPowerStatsUidResolver = powerStatsUidResolver;
        this.mFrameworkStatsLogger = frameworkStatsLogger;
        int[] policies = cpuScalingPolicies.getPolicies();
        this.mKernelCpuSpeedReaders = new KernelCpuSpeedReader[policies.length];
        for (int i = 0; i < policies.length; i++) {
            this.mKernelCpuSpeedReaders[i] = new KernelCpuSpeedReader(this.mCpuScalingPolicies.getRelatedCpus(policies[i])[0], this.mCpuScalingPolicies.getFrequencies(policies[i]).length);
        }
        this.mCpuPowerBracketMap = new int[this.mCpuScalingPolicies.getScalingStepCount()];
        int i2 = 0;
        for (int i3 : policies) {
            int length = this.mCpuScalingPolicies.getFrequencies(i3).length;
            int i4 = 0;
            while (i4 < length) {
                this.mCpuPowerBracketMap[i2] = this.mPowerProfile.getCpuPowerBracketForScalingStep(i3, i4);
                i4++;
                i2++;
            }
        }
        if (this.mEstimatedBatteryCapacityMah == -1) {
            this.mEstimatedBatteryCapacityMah = (int) this.mPowerProfile.getBatteryCapacity();
        }
        int numDisplays = this.mPowerProfile.getNumDisplays();
        this.mPerDisplayBatteryStats = new DisplayBatteryStats[numDisplays];
        for (int i5 = 0; i5 < numDisplays; i5++) {
            DisplayBatteryStats[] displayBatteryStatsArr = this.mPerDisplayBatteryStats;
            Clock clock2 = this.mClock;
            TimeBase timeBase = this.mOnBatteryTimeBase;
            DisplayBatteryStats displayBatteryStats = new DisplayBatteryStats();
            displayBatteryStats.screenState = 0;
            displayBatteryStats.screenBrightnessBin = -1;
            displayBatteryStats.screenBrightnessTimers = new StopwatchTimer[5];
            displayBatteryStats.screenStateAtLastEnergyMeasurement = 0;
            displayBatteryStats.screenOnTimer = new StopwatchTimer(clock2, null, -1, null, timeBase);
            displayBatteryStats.screenDozeTimer = new StopwatchTimer(clock2, null, -1, null, timeBase);
            for (int i6 = 0; i6 < 5; i6++) {
                displayBatteryStats.screenBrightnessTimers[i6] = new StopwatchTimer(clock2, null, (-100) - i6, null, timeBase);
            }
            displayBatteryStatsArr[i5] = displayBatteryStats;
        }
        if (file != null) {
            this.mStatsFile = new AtomicFile(new File(file, "batterystats.bin"));
            this.mCheckinFile = new AtomicFile(new File(file, "batterystats-checkin.bin"));
            this.mDailyFile = new AtomicFile(new File(file, "batterystats-daily.xml"));
            this.mSystemDir = file;
        } else {
            this.mStatsFile = null;
            this.mCheckinFile = null;
            this.mDailyFile = null;
            this.mSystemDir = null;
        }
        Constants constants = this.mConstants;
        this.mHistory = new BatteryStatsHistory((Parcel) null, file, constants.MAX_HISTORY_FILES, constants.MAX_HISTORY_BUFFER, this.mStepDetailsCalculator, this.mClock, this.mMonotonicClock, traceDelegate, eventLogger);
        CpuPowerStatsCollector cpuPowerStatsCollector = new CpuPowerStatsCollector(this.mPowerStatsCollectorInjector);
        this.mCpuPowerStatsCollector = cpuPowerStatsCollector;
        cpuPowerStatsCollector.addConsumer(new Consumer() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BatteryStatsImpl.$r8$lambda$4oR0MyxxkPH0JSQXqVxAZgbHyhc(BatteryStatsImpl.this, (PowerStats) obj);
            }
        });
        MobileRadioPowerStatsCollector mobileRadioPowerStatsCollector = new MobileRadioPowerStatsCollector(this.mPowerStatsCollectorInjector);
        this.mMobileRadioPowerStatsCollector = mobileRadioPowerStatsCollector;
        mobileRadioPowerStatsCollector.addConsumer(new Consumer() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BatteryStatsImpl.$r8$lambda$4oR0MyxxkPH0JSQXqVxAZgbHyhc(BatteryStatsImpl.this, (PowerStats) obj);
            }
        });
        WifiPowerStatsCollector wifiPowerStatsCollector = new WifiPowerStatsCollector(this.mPowerStatsCollectorInjector);
        this.mWifiPowerStatsCollector = wifiPowerStatsCollector;
        wifiPowerStatsCollector.addConsumer(new Consumer() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BatteryStatsImpl.$r8$lambda$4oR0MyxxkPH0JSQXqVxAZgbHyhc(BatteryStatsImpl.this, (PowerStats) obj);
            }
        });
        BluetoothPowerStatsCollector bluetoothPowerStatsCollector = new BluetoothPowerStatsCollector(this.mPowerStatsCollectorInjector);
        this.mBluetoothPowerStatsCollector = bluetoothPowerStatsCollector;
        bluetoothPowerStatsCollector.addConsumer(new Consumer() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BatteryStatsImpl.$r8$lambda$4oR0MyxxkPH0JSQXqVxAZgbHyhc(BatteryStatsImpl.this, (PowerStats) obj);
            }
        });
        CameraPowerStatsCollector cameraPowerStatsCollector = new CameraPowerStatsCollector(this.mPowerStatsCollectorInjector, 3, BatteryConsumer.powerComponentIdToString(3), 7, new BinaryStatePowerStatsLayout());
        this.mCameraPowerStatsCollector = cameraPowerStatsCollector;
        cameraPowerStatsCollector.addConsumer(new Consumer() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BatteryStatsImpl.$r8$lambda$4oR0MyxxkPH0JSQXqVxAZgbHyhc(BatteryStatsImpl.this, (PowerStats) obj);
            }
        });
        GnssPowerStatsCollector gnssPowerStatsCollector = new GnssPowerStatsCollector(this.mPowerStatsCollectorInjector, 10, BatteryConsumer.powerComponentIdToString(10), 4, new GnssPowerStatsLayout());
        this.mGnssPowerStatsCollector = gnssPowerStatsCollector;
        gnssPowerStatsCollector.addConsumer(new Consumer() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BatteryStatsImpl.$r8$lambda$4oR0MyxxkPH0JSQXqVxAZgbHyhc(BatteryStatsImpl.this, (PowerStats) obj);
            }
        });
        CustomEnergyConsumerPowerStatsCollector customEnergyConsumerPowerStatsCollector = new CustomEnergyConsumerPowerStatsCollector(this.mPowerStatsCollectorInjector);
        this.mCustomEnergyConsumerPowerStatsCollector = customEnergyConsumerPowerStatsCollector;
        customEnergyConsumerPowerStatsCollector.addConsumer(new Consumer() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BatteryStatsImpl.$r8$lambda$4oR0MyxxkPH0JSQXqVxAZgbHyhc(BatteryStatsImpl.this, (PowerStats) obj);
            }
        });
        this.mStartCount++;
        initTimersAndCounters();
        this.mOnBatteryInternal = false;
        this.mOnBattery = false;
        initTimes(this.mClock.uptimeMillis() * 1000, this.mClock.elapsedRealtime() * 1000);
        String str = Build.ID;
        this.mEndPlatformVersion = str;
        this.mStartPlatformVersion = str;
        initDischarge();
        updateDailyDeadlineLocked();
        this.mPlatformIdleStateCallback = platformIdleStateCallback;
        this.mEnergyConsumerRetriever = energyStatsRetriever;
        this.mUserInfoProvider = userInfoProvider;
        File file2 = new File("/sys/class/power_supply/battery/batt_read_raw_soc");
        if (file2.exists() && file2.canRead()) {
            this.mIsRawSocReported = true;
        }
        File file3 = new File("/sys/class/power_supply/battery/cc_info");
        if (file3.exists() && file3.canRead()) {
            this.mIsCoulombCounterReported = true;
        }
        this.mPowerStatsUidResolver.addListener(new PowerStatsUidResolver.Listener() { // from class: com.android.server.power.stats.BatteryStatsImpl.6
            @Override // com.android.server.power.stats.PowerStatsUidResolver.Listener
            public final void onAfterIsolatedUidRemoved(int i7, int i8) {
                BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
                long elapsedRealtime = batteryStatsImpl.mClock.elapsedRealtime();
                long uptimeMillis = batteryStatsImpl.mClock.uptimeMillis();
                synchronized (batteryStatsImpl) {
                    Uid uidStatsLocked = batteryStatsImpl.getUidStatsLocked(i8, elapsedRealtime, uptimeMillis);
                    SparseArray sparseArray = uidStatsLocked.mChildUids;
                    int indexOfKey = sparseArray == null ? -1 : sparseArray.indexOfKey(i7);
                    if (indexOfKey >= 0) {
                        uidStatsLocked.mChildUids.remove(indexOfKey);
                    }
                }
            }

            @Override // com.android.server.power.stats.PowerStatsUidResolver.Listener
            public final void onBeforeIsolatedUidRemoved(int i7) {
                BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
                long elapsedRealtime = batteryStatsImpl.mClock.elapsedRealtime();
                batteryStatsImpl.mPowerStatsUidResolver.retainIsolatedUid(i7);
                synchronized (batteryStatsImpl) {
                    batteryStatsImpl.mPendingRemovedUids.add(batteryStatsImpl.new UidToRemove(i7, i7, elapsedRealtime));
                }
                BatteryExternalStatsWorker batteryExternalStatsWorker = batteryStatsImpl.mExternalSync;
                if (batteryExternalStatsWorker != null) {
                    synchronized (batteryExternalStatsWorker) {
                        batteryExternalStatsWorker.scheduleSyncLocked(1, "remove-uid");
                    }
                }
            }

            @Override // com.android.server.power.stats.PowerStatsUidResolver.Listener
            public final void onIsolatedUidAdded(int i7, int i8) {
                BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
                long elapsedRealtime = batteryStatsImpl.mClock.elapsedRealtime();
                long uptimeMillis = batteryStatsImpl.mClock.uptimeMillis();
                synchronized (batteryStatsImpl) {
                    Uid uidStatsLocked = batteryStatsImpl.getUidStatsLocked(i8, elapsedRealtime, uptimeMillis);
                    SparseArray sparseArray = uidStatsLocked.mChildUids;
                    if (sparseArray == null) {
                        uidStatsLocked.mChildUids = new SparseArray();
                    } else if (sparseArray.indexOfKey(i7) >= 0) {
                    }
                    uidStatsLocked.mChildUids.put(i7, new Uid.ChildUid(uidStatsLocked));
                }
            }
        });
        this.mDeviceIdleMode = 0;
        this.mFrameworkStatsLogger.getClass();
        FrameworkStatsLog.write(21, 0);
    }

    public static List computeDelta(NetworkStats networkStats, NetworkStats networkStats2) {
        NetworkStats.Entry entry;
        ArrayList arrayList = new ArrayList();
        Iterator it = networkStats.iterator();
        while (it.hasNext()) {
            NetworkStats.Entry entry2 = (NetworkStats.Entry) it.next();
            NetworkStatsDelta networkStatsDelta = new NetworkStatsDelta();
            networkStatsDelta.mUid = entry2.getUid();
            networkStatsDelta.mSet = entry2.getSet();
            if (networkStats2 != null) {
                Iterator it2 = networkStats2.iterator();
                while (it2.hasNext()) {
                    entry = (NetworkStats.Entry) it2.next();
                    if (entry.getUid() == entry2.getUid() && entry.getSet() == entry2.getSet() && entry.getTag() == entry2.getTag() && entry.getMetered() == entry2.getMetered() && entry.getRoaming() == entry2.getRoaming() && entry.getDefaultNetwork() == entry2.getDefaultNetwork()) {
                        break;
                    }
                }
            }
            entry = null;
            if (entry != null) {
                networkStatsDelta.mRxBytes = Math.max(0L, entry2.getRxBytes() - entry.getRxBytes());
                networkStatsDelta.mRxPackets = Math.max(0L, entry2.getRxPackets() - entry.getRxPackets());
                networkStatsDelta.mTxBytes = Math.max(0L, entry2.getTxBytes() - entry.getTxBytes());
                networkStatsDelta.mTxPackets = Math.max(0L, entry2.getTxPackets() - entry.getTxPackets());
            } else {
                networkStatsDelta.mRxBytes = entry2.getRxBytes();
                networkStatsDelta.mRxPackets = entry2.getRxPackets();
                networkStatsDelta.mTxBytes = entry2.getTxBytes();
                networkStatsDelta.mTxPackets = entry2.getTxPackets();
            }
            arrayList.add(networkStatsDelta);
        }
        return arrayList;
    }

    public static WakeLockStats.WakeLockData createWakeLockData(DurationTimer durationTimer, long j) {
        if (durationTimer == null) {
            return WakeLockStats.WakeLockData.EMPTY;
        }
        long totalTimeLocked = durationTimer.getTotalTimeLocked(j * 1000, 0) / 1000;
        if (totalTimeLocked == 0) {
            return WakeLockStats.WakeLockData.EMPTY;
        }
        return new WakeLockStats.WakeLockData(durationTimer.mCount, totalTimeLocked, durationTimer.isRunningLocked() ? durationTimer.getCurrentDurationMsLocked(j) : 0L);
    }

    public static void detachIfNotNull(TimeBaseObs timeBaseObs) {
        if (timeBaseObs != null) {
            timeBaseObs.detach();
        }
    }

    public static void detachIfNotNull(TimeBaseObs[] timeBaseObsArr) {
        if (timeBaseObsArr != null) {
            for (TimeBaseObs timeBaseObs : timeBaseObsArr) {
                detachIfNotNull(timeBaseObs);
            }
        }
    }

    public static void detachIfNotNull(TimeBaseObs[][] timeBaseObsArr) {
        if (timeBaseObsArr != null) {
            for (TimeBaseObs[] timeBaseObsArr2 : timeBaseObsArr) {
                detachIfNotNull(timeBaseObsArr2);
            }
        }
    }

    public static void dumpEnergyConsumerStatsLocked(PrintWriter printWriter, String str, EnergyConsumerStats energyConsumerStats) {
        if (energyConsumerStats == null) {
            return;
        }
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "    ");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printf("%s:\n", new Object[]{str});
        indentingPrintWriter.increaseIndent();
        energyConsumerStats.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
    }

    public static String encodingPersonalInformation(String str) {
        int i;
        String[] split = str.split("\\/|\\#");
        if (split.length == 1) {
            return str;
        }
        try {
            Long.parseLong(split[split.length - 1].replaceAll("[^a-zA-Z0-9]", ""));
            i = 1;
        } catch (NumberFormatException unused) {
            i = 0;
        }
        for (int i2 = 0; i2 < split.length - i; i2++) {
            if (!split[i2].contains("@SyncManager@")) {
                String replaceAll = Patterns.PHONE.matcher(split[i2]).replaceAll("Numbers");
                split[i2] = replaceAll;
                split[i2] = Patterns.EMAIL_ADDRESS.matcher(replaceAll).replaceAll("eM_ADDR");
            }
        }
        String join = String.join("/", (CharSequence[]) Arrays.copyOfRange(split, 0, split.length - i));
        if (i == 0) {
            return join;
        }
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(join, "#");
        m.append(split[split.length - 1]);
        return m.toString();
    }

    public static String[] excludeFromStringArray(String str, String[] strArr) {
        int indexOf = ArrayUtils.indexOf(strArr, str);
        if (indexOf < 0) {
            return strArr;
        }
        String[] strArr2 = new String[strArr.length - 1];
        if (indexOf > 0) {
            System.arraycopy(strArr, 0, strArr2, 0, indexOf);
        }
        if (indexOf < strArr.length - 1) {
            System.arraycopy(strArr, indexOf + 1, strArr2, indexOf, (strArr.length - indexOf) - 1);
        }
        return strArr2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v2, types: [int] */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    public static int getBatteryLevel() {
        ?? r0;
        try {
            FileReader fileReader = new FileReader("/sys/class/power_supply/battery/capacity");
            try {
                r0 = new BufferedReader(fileReader, 8);
                try {
                    String readLine = r0.readLine();
                    if (readLine == null) {
                        Slog.e("BatteryStatsImpl", "Cannot read the path :: /sys/class/power_supply/battery/capacity");
                        r0.close();
                        fileReader.close();
                        r0 = -2;
                    } else {
                        byte parseByte = Byte.parseByte(readLine.trim());
                        r0.close();
                        fileReader.close();
                        r0 = parseByte;
                    }
                } finally {
                }
            } finally {
            }
        } catch (IOException unused) {
            r0 = -1;
        }
        if (r0 < 0) {
            return 100;
        }
        return r0;
    }

    public static int getIntFromFile(String str) {
        try {
            FileReader fileReader = new FileReader(str);
            try {
                BufferedReader bufferedReader = new BufferedReader(fileReader, 8);
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        Slog.e("BatteryStatsImpl", "Cannot read the path :: ".concat(str));
                        bufferedReader.close();
                        fileReader.close();
                        return -2;
                    }
                    int parseInt = Integer.parseInt(readLine.trim());
                    bufferedReader.close();
                    fileReader.close();
                    return parseInt;
                } finally {
                }
            } finally {
            }
        } catch (IOException unused) {
            return -1;
        }
    }

    public static int getPowerManagerWakeLockLevel(int i) {
        if (i == 0) {
            return 1;
        }
        if (i == 1) {
            return 26;
        }
        if (i == 2) {
            Slog.e("BatteryStatsImpl", "Illegal window wakelock type observed in batterystats.");
            return -1;
        }
        if (i == 18) {
            return 128;
        }
        NandswapManager$$ExternalSyntheticOutline0.m(i, "Illegal wakelock type in batterystats: ", "BatteryStatsImpl");
        return -1;
    }

    public static void readDailyItemTagDetailsLocked(TypedXmlPullParser typedXmlPullParser, BatteryStats.DailyItem dailyItem, boolean z, String str) {
        String attributeValue;
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "n", -1);
        if (attributeInt == -1) {
            Slog.w("BatteryStatsImpl", "Missing 'n' attribute at " + typedXmlPullParser.getPositionDescription());
            XmlUtils.skipCurrentTag(typedXmlPullParser);
            return;
        }
        BatteryStats.LevelStepTracker levelStepTracker = new BatteryStats.LevelStepTracker(attributeInt);
        if (z) {
            dailyItem.mChargeSteps = levelStepTracker;
        } else {
            dailyItem.mDischargeSteps = levelStepTracker;
        }
        int depth = typedXmlPullParser.getDepth();
        int i = 0;
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                if (!"s".equals(typedXmlPullParser.getName())) {
                    StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Unknown element under <", str, ">: ");
                    m.append(typedXmlPullParser.getName());
                    Slog.w("BatteryStatsImpl", m.toString());
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                } else if (i < attributeInt && (attributeValue = typedXmlPullParser.getAttributeValue((String) null, "v")) != null) {
                    levelStepTracker.decodeEntryAt(i, attributeValue);
                    i++;
                }
            }
        }
        levelStepTracker.mNumStepDurations = i;
    }

    public static boolean resetIfNotNull(TimeBaseObs timeBaseObs, long j) {
        if (timeBaseObs != null) {
            return timeBaseObs.reset(j, false);
        }
        return true;
    }

    public static boolean resetIfNotNull(TimeBaseObs[] timeBaseObsArr, long j) {
        boolean z = true;
        if (timeBaseObsArr != null) {
            for (TimeBaseObs timeBaseObs : timeBaseObsArr) {
                z &= resetIfNotNull(timeBaseObs, j);
            }
        }
        return z;
    }

    public static void writeDailyLevelSteps(TypedXmlSerializer typedXmlSerializer, String str, BatteryStats.LevelStepTracker levelStepTracker, StringBuilder sb) {
        if (levelStepTracker != null) {
            typedXmlSerializer.startTag((String) null, str);
            typedXmlSerializer.attributeInt((String) null, "n", levelStepTracker.mNumStepDurations);
            for (int i = 0; i < levelStepTracker.mNumStepDurations; i++) {
                typedXmlSerializer.startTag((String) null, "s");
                sb.setLength(0);
                levelStepTracker.encodeEntryAt(i, sb);
                typedXmlSerializer.attribute((String) null, "v", sb.toString());
                typedXmlSerializer.endTag((String) null, "s");
            }
            typedXmlSerializer.endTag((String) null, str);
        }
    }

    public final void addCpuStatsLocked(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        HistoryStepDetailsCalculatorImpl historyStepDetailsCalculatorImpl = this.mStepDetailsCalculator;
        historyStepDetailsCalculatorImpl.mCurStepCpuUserTimeMs += i;
        historyStepDetailsCalculatorImpl.mCurStepCpuSystemTimeMs += i2;
        historyStepDetailsCalculatorImpl.mCurStepStatUserTimeMs += i3;
        historyStepDetailsCalculatorImpl.mCurStepStatSystemTimeMs += i4;
        historyStepDetailsCalculatorImpl.mCurStepStatIOWaitTimeMs += i5;
        historyStepDetailsCalculatorImpl.mCurStepStatIrqTimeMs += i6;
        historyStepDetailsCalculatorImpl.mCurStepStatSoftIrqTimeMs += i7;
        historyStepDetailsCalculatorImpl.mCurStepStatIdleTimeMs += i8;
    }

    public final boolean canReadTimeToFullNow() {
        return this.mFeatureComputeChargeTimeModel;
    }

    public final boolean canTrustSecPowerProfile() {
        int i = 0;
        for (int i2 : this.mCpuScalingPolicies.getPolicies()) {
            i += this.mCpuScalingPolicies.getFrequencies(i2).length;
        }
        return i == this.mPowerProfile.getAllFrequencies();
    }

    public final void checkProtectBatteryType() {
        UiModeManagerService$13$$ExternalSyntheticOutline0.m(new StringBuilder("Something was wrong with protect_battery ("), this.mProtectBatteryMode, ")", "BatteryStatsImpl");
        this.mProtectBatteryMode = 0;
        this.mHistory.setProtectBatteryState(0);
    }

    public final void clearPendingRemovedUidsLocked() {
        long elapsedRealtime = this.mClock.elapsedRealtime() - this.mConstants.UID_REMOVE_DELAY_MS;
        while (!this.mPendingRemovedUids.isEmpty() && ((UidToRemove) this.mPendingRemovedUids.peek()).mUidRemovalTimestamp < elapsedRealtime) {
            UidToRemove uidToRemove = (UidToRemove) this.mPendingRemovedUids.poll();
            uidToRemove.getClass();
            BatteryStatsImpl.this.removeCpuStatsForUidRangeLocked(uidToRemove.mStartUid, uidToRemove.mEndUid);
        }
    }

    public final void clearRemovedUserUidsLocked(int i) {
        int uid = UserHandle.getUid(i, 0);
        int uid2 = UserHandle.getUid(i, 99999);
        this.mUidStats.put(uid, null);
        this.mUidStats.put(uid2, null);
        int indexOfKey = this.mUidStats.indexOfKey(uid);
        int indexOfKey2 = this.mUidStats.indexOfKey(uid2);
        for (int i2 = indexOfKey; i2 <= indexOfKey2; i2++) {
            Uid uid3 = (Uid) this.mUidStats.valueAt(i2);
            if (uid3 != null) {
                uid3.detachFromTimeBase();
            }
        }
        this.mUidStats.removeAtRange(indexOfKey, (indexOfKey2 - indexOfKey) + 1);
        removeCpuStatsForUidRangeLocked(uid, uid2);
    }

    public final void commitCurrentHistoryBatchLocked() {
        this.mHistory.commitCurrentHistoryBatchLocked();
    }

    public final long computeBatteryRealtime(long j, int i) {
        return this.mOnBatteryTimeBase.computeRealtime(j);
    }

    public final long computeBatteryScreenOffRealtime(long j, int i) {
        return this.mOnBatteryScreenOffTimeBase.computeRealtime(j);
    }

    public final long computeBatteryScreenOffUptime(long j, int i) {
        TimeBase timeBase = this.mOnBatteryScreenOffTimeBase;
        return timeBase.getUptime(j) + timeBase.mUptimeUs;
    }

    public final long computeBatteryTimeRemaining(long j) {
        if (!this.mOnBattery) {
            return -1L;
        }
        BatteryStats.LevelStepTracker levelStepTracker = this.mDischargeStepTracker;
        if (levelStepTracker.mNumStepDurations < 1) {
            return -1L;
        }
        long computeTimePerLevel = levelStepTracker.computeTimePerLevel();
        if (computeTimePerLevel <= 0) {
            return -1L;
        }
        return computeTimePerLevel * this.mBatteryLevel * 1000;
    }

    public final long computeBatteryUptime(long j, int i) {
        TimeBase timeBase = this.mOnBatteryTimeBase;
        return timeBase.getUptime(j) + timeBase.mUptimeUs;
    }

    public final long computeChargeTimeRemaining(long j) {
        long j2;
        if (this.mOnBattery) {
            return -1L;
        }
        long j3 = this.mBatteryTimeToFullSeconds;
        if (j3 >= 0) {
            j2 = 1000000;
        } else {
            BatteryStats.LevelStepTracker levelStepTracker = this.mChargeStepTracker;
            if (levelStepTracker.mNumStepDurations < 1) {
                return -1L;
            }
            long computeTimePerLevel = levelStepTracker.computeTimePerLevel();
            if (computeTimePerLevel <= 0) {
                return -1L;
            }
            int i = this.mProtectBatteryMode;
            int i2 = 100;
            if (i != 0) {
                if (i == 1 || i == 2) {
                    i2 = 80;
                } else if (i != 3 && i != 4) {
                    checkProtectBatteryType();
                }
            }
            j3 = computeTimePerLevel * (Math.max(i2, this.mBatteryLevel) - this.mBatteryLevel);
            j2 = 1000;
        }
        return j3 * j2;
    }

    public final long computeRealtime(long j, int i) {
        return (j - this.mRealtimeStartUs) + this.mRealtimeUs;
    }

    public final long computeUptime(long j, int i) {
        return (j - this.mUptimeStartUs) + this.mUptimeUs;
    }

    public final void distributeEnergyToUidsLocked(int i, long j, SparseDoubleArray sparseDoubleArray, double d, long j2) {
        double d2 = 0.0d;
        for (int size = sparseDoubleArray.size() - 1; size >= 0; size--) {
            d2 += sparseDoubleArray.valueAt(size);
        }
        double max = Math.max(d2, d);
        if (max <= 0.0d) {
            return;
        }
        for (int size2 = sparseDoubleArray.size() - 1; size2 >= 0; size2--) {
            Uid uid = (Uid) this.mUidStats.get(sparseDoubleArray.keyAt(size2));
            long valueAt = (long) (((j * sparseDoubleArray.valueAt(size2)) / max) + 0.5d);
            if (uid.mUidEnergyConsumerStats == null) {
                uid.mUidEnergyConsumerStats = new EnergyConsumerStats(uid.mBsi.mEnergyConsumerStatsConfig);
            }
            uid.mUidEnergyConsumerStats.updateStandardBucket(i, valueAt, j2);
        }
    }

    public final void dump(Context context, PrintWriter printWriter, int i, int i2, long j, BatteryStats.BatteryStatsDumpHelper batteryStatsDumpHelper) {
        super.dump(context, printWriter, i, i2, j, batteryStatsDumpHelper);
        synchronized (this) {
            printWriter.print("Per process state tracking available: ");
            printWriter.println(this.mCpuUidFreqTimeReader.isFastCpuTimesReader());
            printWriter.print("Total cpu time reads: ");
            printWriter.println(this.mNumSingleUidCpuTimeReads);
            printWriter.print("Batching Duration (min): ");
            printWriter.println((this.mClock.uptimeMillis() - this.mCpuTimeReadsTrackingStartTimeMs) / 60000);
            printWriter.print("All UID cpu time reads since the later of device start or stats reset: ");
            printWriter.println(this.mNumAllUidCpuTimeReads);
            printWriter.print("UIDs removed since the later of device start or stats reset: ");
            printWriter.println(this.mNumUidsRemoved);
            this.mPowerStatsUidResolver.dump(printWriter);
            printWriter.println();
            dumpConstantsLocked(printWriter);
            printWriter.println();
            CpuPowerStatsCollector cpuPowerStatsCollector = this.mCpuPowerStatsCollector;
            if (cpuPowerStatsCollector.ensureInitialized$1() && cpuPowerStatsCollector.mLayout != null) {
                printWriter.println("CPU power brackets; cluster/freq in MHz(avg current in mA):");
                for (int i3 = 0; i3 < cpuPowerStatsCollector.mLayout.mUidPowerBracketCount; i3++) {
                    printWriter.print("    ");
                    printWriter.print(i3);
                    printWriter.print(": ");
                    printWriter.println(cpuPowerStatsCollector.getCpuPowerBracketDescription(i3));
                }
            }
            printWriter.println();
            dumpEnergyConsumerStatsLocked(printWriter);
        }
    }

    public final void dumpConstantsLocked(PrintWriter printWriter) {
        String str;
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "    ");
        indentingPrintWriter.println("BatteryStats constants:");
        indentingPrintWriter.increaseIndent();
        Constants constants = this.mConstants;
        constants.getClass();
        indentingPrintWriter.print("track_cpu_active_cluster_time");
        indentingPrintWriter.print("=");
        indentingPrintWriter.println(constants.TRACK_CPU_ACTIVE_CLUSTER_TIME);
        indentingPrintWriter.print("kernel_uid_readers_throttle_time");
        indentingPrintWriter.print("=");
        indentingPrintWriter.println(constants.KERNEL_UID_READERS_THROTTLE_TIME);
        indentingPrintWriter.print("external_stats_collection_rate_limit_ms");
        indentingPrintWriter.print("=");
        indentingPrintWriter.println(constants.EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS);
        indentingPrintWriter.print("battery_level_collection_delay_ms");
        indentingPrintWriter.print("=");
        indentingPrintWriter.println(constants.BATTERY_LEVEL_COLLECTION_DELAY_MS);
        indentingPrintWriter.print("procstate_change_collection_delay_ms");
        indentingPrintWriter.print("=");
        indentingPrintWriter.println(constants.PROC_STATE_CHANGE_COLLECTION_DELAY_MS);
        indentingPrintWriter.print("max_history_files");
        indentingPrintWriter.print("=");
        indentingPrintWriter.println(constants.MAX_HISTORY_FILES);
        indentingPrintWriter.print("max_history_buffer_kb");
        indentingPrintWriter.print("=");
        indentingPrintWriter.println(constants.MAX_HISTORY_BUFFER / 1024);
        indentingPrintWriter.print("battery_charged_delay_ms");
        indentingPrintWriter.print("=");
        indentingPrintWriter.println(constants.BATTERY_CHARGED_DELAY_MS);
        indentingPrintWriter.print("battery_charging_enforce_level");
        indentingPrintWriter.print("=");
        indentingPrintWriter.println(constants.BATTERY_CHARGING_ENFORCE_LEVEL);
        indentingPrintWriter.print("per_uid_modem_power_model");
        indentingPrintWriter.print("=");
        int i = constants.PER_UID_MODEM_MODEL;
        if (i == 1) {
            str = "mobile_radio_active_time";
        } else if (i != 2) {
            Slog.w("BatteryStatsImpl", "Unexpected per uid modem model (" + i + ")");
            StringBuilder sb = new StringBuilder("unknown_");
            sb.append(i);
            str = sb.toString();
        } else {
            str = "modem_activity_info_rx_tx";
        }
        indentingPrintWriter.println(str);
        indentingPrintWriter.print("phone_on_external_stats_collection");
        indentingPrintWriter.print("=");
        indentingPrintWriter.println(constants.PHONE_ON_EXTERNAL_STATS_COLLECTION);
        indentingPrintWriter.print("reset_while_plugged_in_minimum_duration_hours");
        indentingPrintWriter.print("=");
        indentingPrintWriter.println(constants.RESET_WHILE_PLUGGED_IN_MINIMUM_DURATION_HOURS);
        indentingPrintWriter.decreaseIndent();
    }

    public final void dumpCpuStatsLocked(PrintWriter printWriter) {
        long j;
        int size = this.mUidStats.size();
        printWriter.println("Per UID CPU user & system time in ms:");
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            int keyAt = this.mUidStats.keyAt(i2);
            Uid uid = (Uid) this.mUidStats.get(keyAt);
            printWriter.print("  ");
            printWriter.print(keyAt);
            printWriter.print(": ");
            printWriter.print(uid.mUserCpuTime.mCount / 1000);
            printWriter.print(" ");
            printWriter.println(uid.mSystemCpuTime.mCount / 1000);
        }
        printWriter.println("Per UID CPU active time in ms:");
        int i3 = 0;
        while (true) {
            j = 0;
            if (i3 >= size) {
                break;
            }
            int keyAt2 = this.mUidStats.keyAt(i3);
            Uid uid2 = (Uid) this.mUidStats.get(keyAt2);
            if (uid2.getCpuActiveTime() > 0) {
                printWriter.print("  ");
                printWriter.print(keyAt2);
                printWriter.print(": ");
                printWriter.println(uid2.getCpuActiveTime());
            }
            i3++;
        }
        printWriter.println("Per UID CPU cluster time in ms:");
        for (int i4 = 0; i4 < size; i4++) {
            int keyAt3 = this.mUidStats.keyAt(i4);
            long[] cpuClusterTimes = ((Uid) this.mUidStats.get(keyAt3)).getCpuClusterTimes();
            if (cpuClusterTimes != null) {
                printWriter.print("  ");
                printWriter.print(keyAt3);
                printWriter.print(": ");
                printWriter.println(Arrays.toString(cpuClusterTimes));
            }
        }
        printWriter.println("Per UID CPU frequency time in ms:");
        for (int i5 = 0; i5 < size; i5++) {
            int keyAt4 = this.mUidStats.keyAt(i5);
            long[] nullIfAllZeros = Uid.nullIfAllZeros(((Uid) this.mUidStats.get(keyAt4)).mCpuFreqTimeMs, 0);
            if (nullIfAllZeros != null) {
                printWriter.print("  ");
                printWriter.print(keyAt4);
                printWriter.print(": ");
                printWriter.println(Arrays.toString(nullIfAllZeros));
            }
        }
        if (Flags.disableSystemServicePowerAttr()) {
            return;
        }
        updateSystemServiceCallStats();
        if (this.mBinderThreadCpuTimesUs != null) {
            printWriter.println("Per UID System server binder time in ms:");
            long[] systemServiceTimeAtCpuSpeeds = getSystemServiceTimeAtCpuSpeeds();
            while (i < size) {
                int keyAt5 = this.mUidStats.keyAt(i);
                double d = ((Uid) this.mUidStats.get(keyAt5)).mProportionalSystemServiceUsage;
                for (int length = systemServiceTimeAtCpuSpeeds.length - 1; length >= 0; length--) {
                    j = (long) ((systemServiceTimeAtCpuSpeeds[length] * d) + j);
                }
                printWriter.print("  ");
                printWriter.print(keyAt5);
                printWriter.print(": ");
                printWriter.println(j / 1000);
                i++;
                j = 0;
            }
        }
    }

    public final void dumpEnergyConsumerStatsLocked(PrintWriter printWriter) {
        printWriter.printf("On-battery energy consumer stats (microcoulombs) \n", new Object[0]);
        EnergyConsumerStats energyConsumerStats = this.mGlobalEnergyConsumerStats;
        if (energyConsumerStats == null) {
            printWriter.printf("    Not supported on this device.\n", new Object[0]);
            return;
        }
        dumpEnergyConsumerStatsLocked(printWriter, "global usage", energyConsumerStats);
        int size = this.mUidStats.size();
        for (int i = 0; i < size; i++) {
            Uid uid = (Uid) this.mUidStats.get(this.mUidStats.keyAt(i));
            dumpEnergyConsumerStatsLocked(printWriter, "uid " + uid.mUid, uid.mUidEnergyConsumerStats);
        }
    }

    public final void dumpPowerProfileLocked(PrintWriter printWriter) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "    ");
        indentingPrintWriter.printf("Power Profile: \n", new Object[0]);
        indentingPrintWriter.increaseIndent();
        this.mPowerProfile.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
    }

    public final void ensureKernelSingleUidTimeReaderLocked() {
        if (this.mPowerStatsCollectorEnabled.get(1) || this.mKernelSingleUidTimeReader != null) {
            return;
        }
        this.mKernelSingleUidTimeReader = new KernelSingleUidTimeReader(this.mCpuScalingPolicies.getScalingStepCount());
        this.mPerProcStateCpuTimesAvailable = this.mCpuUidFreqTimeReader.perClusterTimesAvailable() && this.mKernelSingleUidTimeReader.singleUidCpuTimesAvailable();
    }

    public final int evaluateOverallScreenBrightnessBinLocked() {
        int length = this.mPerDisplayBatteryStats.length;
        int i = -1;
        for (int i2 = 0; i2 < length; i2++) {
            DisplayBatteryStats displayBatteryStats = this.mPerDisplayBatteryStats[i2];
            int i3 = displayBatteryStats.screenState == 2 ? displayBatteryStats.screenBrightnessBin : -1;
            if (i3 > i) {
                i = i3;
            }
        }
        return i;
    }

    public final void fillLowPowerStats() {
        if (this.mPlatformIdleStateCallback == null) {
            return;
        }
        RpmStats rpmStats = new RpmStats();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (elapsedRealtime - this.mLastRpmStatsUpdateTimeMs >= 1000) {
            BatteryStatsService batteryStatsService = (BatteryStatsService) this.mPlatformIdleStateCallback;
            synchronized (batteryStatsService.mPowerStatsLock) {
                if (batteryStatsService.mPowerStatsInternal != null && !((HashMap) batteryStatsService.mEntityNames).isEmpty() && !((HashMap) batteryStatsService.mStateNames).isEmpty()) {
                    try {
                        int i = 0;
                        StateResidencyResult[] stateResidencyResultArr = (StateResidencyResult[]) batteryStatsService.mPowerStatsInternal.getStateResidencyAsync(new int[0]).get(2000L, TimeUnit.MILLISECONDS);
                        if (stateResidencyResultArr != null) {
                            int i2 = 0;
                            while (i2 < stateResidencyResultArr.length) {
                                StateResidencyResult stateResidencyResult = stateResidencyResultArr[i2];
                                RpmStats.PowerStateSubsystem subsystem = rpmStats.getSubsystem((String) ((HashMap) batteryStatsService.mEntityNames).get(Integer.valueOf(stateResidencyResult.id)));
                                int i3 = i;
                                while (true) {
                                    StateResidency[] stateResidencyArr = stateResidencyResult.stateResidencyData;
                                    if (i3 < stateResidencyArr.length) {
                                        StateResidency stateResidency = stateResidencyArr[i3];
                                        subsystem.putState((String) ((Map) ((HashMap) batteryStatsService.mStateNames).get(Integer.valueOf(stateResidencyResult.id))).get(Integer.valueOf(stateResidency.id)), stateResidency.totalTimeInStateMs, (int) stateResidency.totalStateEntryCount);
                                        i3++;
                                        i2 = i2;
                                    }
                                }
                                i2++;
                                i = 0;
                            }
                        }
                    } catch (Exception e) {
                        Slog.e("BatteryStatsService", "Failed to getStateResidencyAsync", e);
                    }
                }
            }
            synchronized (this) {
                this.mTmpRpmStats = rpmStats;
                this.mLastRpmStatsUpdateTimeMs = elapsedRealtime;
            }
        }
    }

    public void forceRecordAllHistory() {
        this.mHistory.forceRecordAllHistory();
        this.mRecordAllHistory = true;
    }

    public final int gatBatteryHistoryResetLevel() {
        int i = this.mProtectBatteryMode;
        if (i != 0) {
            if (i == 1 || i == 2) {
                return 70;
            }
            if (i != 3 && i != 4) {
                checkProtectBatteryType();
                return 90;
            }
        }
        return 90;
    }

    public final long getActiveRadioDurationMs(int i, int i2, int i3, long j) {
        RadioAccessTechnologyBatteryStats radioAccessTechnologyBatteryStats = this.mPerRatBatteryStats[i];
        if (radioAccessTechnologyBatteryStats == null) {
            return 0L;
        }
        StopwatchTimer[][] stopwatchTimerArr = radioAccessTechnologyBatteryStats.perStateTimers;
        int length = stopwatchTimerArr.length;
        if (i2 >= 0 && i2 < length) {
            StopwatchTimer[] stopwatchTimerArr2 = stopwatchTimerArr[i2];
            int length2 = stopwatchTimerArr2.length;
            if (i3 >= 0 && i3 < length2) {
                return stopwatchTimerArr2[i3].getTotalTimeLocked(j * 1000, 0) / 1000;
            }
        }
        return 0L;
    }

    public final long getActiveRxRadioDurationMs(int i, int i2, long j) {
        LongSamplingCounter rxDurationCounter;
        RadioAccessTechnologyBatteryStats radioAccessTechnologyBatteryStats = this.mPerRatBatteryStats[i];
        if (radioAccessTechnologyBatteryStats == null || (rxDurationCounter = radioAccessTechnologyBatteryStats.getRxDurationCounter(i2, false)) == null) {
            return -1L;
        }
        return rxDurationCounter.mCount;
    }

    public final long getActiveTxRadioDurationMs(int i, int i2, int i3, long j) {
        LongSamplingCounter txDurationCounter;
        RadioAccessTechnologyBatteryStats radioAccessTechnologyBatteryStats = this.mPerRatBatteryStats[i];
        if (radioAccessTechnologyBatteryStats == null || (txDurationCounter = radioAccessTechnologyBatteryStats.getTxDurationCounter(i2, i3, false)) == null) {
            return -1L;
        }
        return txDurationCounter.mCount;
    }

    public final int getBatteryCCInfo() {
        int i;
        if (Math.max(this.mClock.elapsedRealtime() - this.mLastBatteryCCUpdateTime, 0L) < 1000 && (i = this.mBatteryCC) >= 0 && this.mIsCoulombCounterReported) {
            return i;
        }
        if (!this.mIsCoulombCounterReported) {
            return 0;
        }
        this.mBatteryCC = getIntFromFile("/sys/class/power_supply/battery/cc_info");
        this.mLastBatteryCCUpdateTime = this.mClock.elapsedRealtime();
        int i2 = this.mBatteryCC;
        if (i2 == -1) {
            return 0;
        }
        return i2;
    }

    public final int getBatteryPermil() {
        int batteryLevel;
        int i;
        if (Math.max(this.mClock.elapsedRealtime() - this.mLastBatteryRawUpdateTime, 0L) < 1000 && (i = this.mBatteryRaw) >= 0 && this.mIsRawSocReported) {
            return i / 10;
        }
        if (this.mIsRawSocReported) {
            this.mBatteryRaw = getIntFromFile("/sys/class/power_supply/battery/batt_read_raw_soc");
            this.mLastBatteryRawUpdateTime = this.mClock.elapsedRealtime();
            int i2 = this.mBatteryRaw;
            if (i2 >= 0) {
                return i2 / 10;
            }
            batteryLevel = getBatteryLevel();
        } else {
            batteryLevel = getBatteryLevel();
        }
        return batteryLevel * 10;
    }

    public final long getBatteryRealtime(long j) {
        return this.mOnBatteryTimeBase.getRealtime(j);
    }

    public final long getBatteryUptime(long j) {
        return this.mOnBatteryTimeBase.getUptime(j);
    }

    public final BluetoothBatteryStats getBluetoothBatteryStats() {
        long elapsedRealtime = this.mClock.elapsedRealtime() * 1000;
        ArrayList arrayList = new ArrayList();
        for (int size = this.mUidStats.size() - 1; size >= 0; size--) {
            Uid uid = (Uid) this.mUidStats.valueAt(size);
            DualTimer dualTimer = uid.mBluetoothScanTimer;
            long totalTimeLocked = dualTimer != null ? dualTimer.getTotalTimeLocked(elapsedRealtime, 0) / 1000 : 0L;
            DualTimer dualTimer2 = uid.mBluetoothUnoptimizedScanTimer;
            long totalTimeLocked2 = dualTimer2 != null ? dualTimer2.getTotalTimeLocked(elapsedRealtime, 0) / 1000 : 0L;
            Counter counter = uid.mBluetoothScanResultCounter;
            int i = counter != null ? counter.mCount.get() : 0;
            ControllerActivityCounterImpl controllerActivityCounterImpl = uid.mBluetoothControllerActivity;
            long countLocked = controllerActivityCounterImpl != null ? controllerActivityCounterImpl.getRxTimeCounter().getCountLocked(0) : 0L;
            long countLocked2 = controllerActivityCounterImpl != null ? controllerActivityCounterImpl.getTxTimeCounters()[0].getCountLocked(0) : 0L;
            if (totalTimeLocked != 0 || totalTimeLocked2 != 0 || i != 0 || countLocked != 0 || countLocked2 != 0) {
                arrayList.add(new BluetoothBatteryStats.UidStats(uid.mUid, totalTimeLocked, totalTimeLocked2, i, countLocked, countLocked2));
            }
        }
        return new BluetoothBatteryStats(arrayList);
    }

    public final BatteryStats.ControllerActivityCounter getBluetoothControllerActivity() {
        return this.mBluetoothActivity;
    }

    public final long getBluetoothEnergyConsumptionUC() {
        return getPowerBucketConsumptionUC(5);
    }

    public final long getBluetoothScanTime(long j, int i) {
        return this.mBluetoothScanTimer.getTotalTimeLocked(j, i);
    }

    public final long getCameraEnergyConsumptionUC() {
        return getPowerBucketConsumptionUC(8);
    }

    public final long getCameraOnTime(long j, int i) {
        return this.mCameraOnTimer.getTotalTimeLocked(j, i);
    }

    public final CellularBatteryStats getCellularBatteryStats() {
        long elapsedRealtime = SystemClock.elapsedRealtime() * 1000;
        ControllerActivityCounterImpl controllerActivityCounterImpl = this.mModemActivity;
        long j = controllerActivityCounterImpl.mSleepTimeMillis.mCount;
        long countLocked = controllerActivityCounterImpl.getIdleTimeCounter().getCountLocked(0);
        long countLocked2 = controllerActivityCounterImpl.getRxTimeCounter().getCountLocked(0);
        long j2 = controllerActivityCounterImpl.mPowerDrainMaMs.mCount;
        long j3 = controllerActivityCounterImpl.mMonitoredRailChargeConsumedMaMs.mCount;
        int i = BatteryStats.NUM_DATA_CONNECTION_TYPES;
        long[] jArr = new long[i];
        for (int i2 = 0; i2 < i; i2++) {
            jArr[i2] = getPhoneDataConnectionTime(i2, elapsedRealtime, 0) / 1000;
        }
        int i3 = CELL_SIGNAL_STRENGTH_LEVEL_COUNT;
        long[] jArr2 = new long[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            jArr2[i4] = getPhoneSignalStrengthTime(i4, elapsedRealtime, 0) / 1000;
        }
        int min = Math.min(MODEM_TX_POWER_LEVEL_COUNT, controllerActivityCounterImpl.getTxTimeCounters().length);
        long[] jArr3 = new long[min];
        int i5 = 0;
        while (i5 < min) {
            jArr3[i5] = controllerActivityCounterImpl.getTxTimeCounters()[i5].getCountLocked(0);
            i5++;
            min = min;
        }
        return new CellularBatteryStats(this.mOnBatteryTimeBase.computeRealtime(elapsedRealtime) / 1000, this.mMobileRadioActiveTimer.getTotalTimeLocked(elapsedRealtime, 0) / 1000, getNetworkActivityPackets(1, 0), getNetworkActivityBytes(1, 0), getNetworkActivityPackets(0, 0), getNetworkActivityBytes(0, 0), j, countLocked, countLocked2, Long.valueOf(j2), jArr, jArr2, jArr3, j3);
    }

    public final BatteryStats.LevelStepTracker getChargeLevelStepTracker() {
        return this.mChargeStepTracker;
    }

    public final long getCpuEnergyConsumptionUC() {
        return getPowerBucketConsumptionUC(3);
    }

    public final CpuScalingPolicies getCpuScalingPolicies() {
        return this.mCpuScalingPolicies;
    }

    public final long getCurrentDailyStartTime() {
        return this.mDailyStartTimeMs;
    }

    public final long[] getCustomEnergyConsumerBatteryConsumptionUC() {
        EnergyConsumerStats energyConsumerStats = this.mGlobalEnergyConsumerStats;
        if (energyConsumerStats == null) {
            return null;
        }
        return energyConsumerStats.getAccumulatedCustomBucketCharges();
    }

    public final String[] getCustomEnergyConsumerNames() {
        synchronized (this) {
            try {
                EnergyConsumerStats.Config config = this.mEnergyConsumerStatsConfig;
                if (config == null) {
                    return new String[0];
                }
                String[] customBucketNames = config.getCustomBucketNames();
                for (int i = 0; i < customBucketNames.length; i++) {
                    if (TextUtils.isEmpty(customBucketNames[i])) {
                        customBucketNames[i] = "CUSTOM_1000" + i;
                    }
                }
                return customBucketNames;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final BatteryStats.LevelStepTracker getDailyChargeLevelStepTracker() {
        return this.mDailyChargeStepTracker;
    }

    public final BatteryStats.LevelStepTracker getDailyDischargeLevelStepTracker() {
        return this.mDailyDischargeStepTracker;
    }

    public final BatteryStats.DailyItem getDailyItemLocked(int i) {
        int size = (this.mDailyItems.size() - 1) - i;
        if (size >= 0) {
            return (BatteryStats.DailyItem) this.mDailyItems.get(size);
        }
        return null;
    }

    public final ArrayList getDailyPackageChanges() {
        return this.mDailyPackageChanges;
    }

    public final int getDeviceIdleModeCount(int i, int i2) {
        if (i == 1) {
            return this.mDeviceIdleModeLightTimer.computeCurrentCountLocked();
        }
        if (i != 2) {
            return 0;
        }
        return this.mDeviceIdleModeFullTimer.computeCurrentCountLocked();
    }

    public final long getDeviceIdleModeTime(int i, long j, int i2) {
        if (i == 1) {
            return this.mDeviceIdleModeLightTimer.getTotalTimeLocked(j, i2);
        }
        if (i != 2) {
            return 0L;
        }
        return this.mDeviceIdleModeFullTimer.getTotalTimeLocked(j, i2);
    }

    public final int getDeviceIdlingCount(int i, int i2) {
        if (i == 1) {
            return this.mDeviceLightIdlingTimer.computeCurrentCountLocked();
        }
        if (i != 2) {
            return 0;
        }
        return this.mDeviceIdlingTimer.computeCurrentCountLocked();
    }

    public final long getDeviceIdlingTime(int i, long j, int i2) {
        if (i == 1) {
            return this.mDeviceLightIdlingTimer.getTotalTimeLocked(j, i2);
        }
        if (i != 2) {
            return 0L;
        }
        return this.mDeviceIdlingTimer.getTotalTimeLocked(j, i2);
    }

    public final int getDischargeAmount(int i) {
        int highDischargeAmountSinceCharge = i == 0 ? getHighDischargeAmountSinceCharge() : getDischargeStartLevel() - getDischargeCurrentLevel();
        if (highDischargeAmountSinceCharge < 0) {
            return 0;
        }
        return highDischargeAmountSinceCharge;
    }

    public final int getDischargeAmountScreenDoze() {
        int i;
        int i2;
        int i3;
        synchronized (this) {
            try {
                i = this.mDischargeAmountScreenDoze;
                if (this.mOnBattery && Display.isDozeState(this.mScreenState) && (i2 = this.mDischargeCurrentLevel) < (i3 = this.mDischargeScreenDozeUnplugLevel)) {
                    i += i3 - i2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final int getDischargeAmountScreenDozeSinceCharge() {
        int i;
        int i2;
        int i3;
        synchronized (this) {
            try {
                i = this.mDischargeAmountScreenDozeSinceCharge;
                if (this.mOnBattery && Display.isDozeState(this.mScreenState) && (i2 = this.mDischargeCurrentLevel) < (i3 = this.mDischargeScreenDozeUnplugLevel)) {
                    i += i3 - i2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final int getDischargeAmountScreenDozeSinceChargePermil() {
        int i;
        int i2;
        synchronized (this) {
            try {
                i = this.mDischargeAmountScreenDozeSinceChargePermil;
                int batteryPermil = getBatteryPermil();
                if (this.mOnBattery && Display.isDozeState(this.mScreenState) && batteryPermil < (i2 = this.mDischargeScreenDozeUnplugLevelPermil)) {
                    i += i2 - batteryPermil;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final int getDischargeAmountScreenOff() {
        int dischargeAmountScreenDoze;
        int i;
        int i2;
        synchronized (this) {
            try {
                int i3 = this.mDischargeAmountScreenOff;
                if (this.mOnBattery && Display.isOffState(this.mScreenState) && (i = this.mDischargeCurrentLevel) < (i2 = this.mDischargeScreenOffUnplugLevel)) {
                    i3 += i2 - i;
                }
                dischargeAmountScreenDoze = i3 + getDischargeAmountScreenDoze();
            } catch (Throwable th) {
                throw th;
            }
        }
        return dischargeAmountScreenDoze;
    }

    public final int getDischargeAmountScreenOffSinceCharge() {
        int dischargeAmountScreenDozeSinceCharge;
        int i;
        int i2;
        synchronized (this) {
            try {
                int i3 = this.mDischargeAmountScreenOffSinceCharge;
                if (this.mOnBattery && Display.isOffState(this.mScreenState) && (i = this.mDischargeCurrentLevel) < (i2 = this.mDischargeScreenOffUnplugLevel)) {
                    i3 += i2 - i;
                }
                dischargeAmountScreenDozeSinceCharge = i3 + getDischargeAmountScreenDozeSinceCharge();
            } catch (Throwable th) {
                throw th;
            }
        }
        return dischargeAmountScreenDozeSinceCharge;
    }

    public final int getDischargeAmountScreenOffSinceChargeCoulombCounter() {
        int i;
        int i2;
        synchronized (this) {
            try {
                i = this.mDischargeAmountScreenOffSinceChargeCoulombCounter;
                int batteryCCInfo = getBatteryCCInfo();
                if (this.mOnBattery && Display.isOffState(this.mScreenState) && batteryCCInfo < (i2 = this.mDischargeScreenOffUnplugLevelCoulombCounter)) {
                    i += i2 - batteryCCInfo;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final int getDischargeAmountScreenOffSinceChargePermil() {
        int i;
        int i2;
        synchronized (this) {
            try {
                i = this.mDischargeAmountScreenOffSinceChargePermil;
                int batteryPermil = getBatteryPermil();
                if (this.mOnBattery && Display.isOffState(this.mScreenState) && batteryPermil < (i2 = this.mDischargeScreenOffUnplugLevelPermil)) {
                    i += i2 - batteryPermil;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final int getDischargeAmountScreenOn() {
        int i;
        int i2;
        int i3;
        synchronized (this) {
            try {
                i = this.mDischargeAmountScreenOn;
                if (this.mOnBattery && Display.isOnState(this.mScreenState) && (i2 = this.mDischargeCurrentLevel) < (i3 = this.mDischargeScreenOnUnplugLevel)) {
                    i += i3 - i2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final int getDischargeAmountScreenOnSinceCharge() {
        int i;
        int i2;
        int i3;
        synchronized (this) {
            try {
                i = this.mDischargeAmountScreenOnSinceCharge;
                if (this.mOnBattery && Display.isOnState(this.mScreenState) && (i2 = this.mDischargeCurrentLevel) < (i3 = this.mDischargeScreenOnUnplugLevel)) {
                    i += i3 - i2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final int getDischargeAmountScreenOnSinceChargeCoulombCounter() {
        int i;
        int i2;
        synchronized (this) {
            try {
                i = this.mDischargeAmountScreenOnSinceChargeCoulombCounter;
                int batteryCCInfo = getBatteryCCInfo();
                if (this.mOnBattery && Display.isOnState(this.mScreenState) && batteryCCInfo < (i2 = this.mDischargeScreenOnUnplugLevelCoulombCounter)) {
                    i += i2 - batteryCCInfo;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final int getDischargeAmountScreenOnSinceChargePermil() {
        int i;
        int i2;
        synchronized (this) {
            try {
                i = this.mDischargeAmountScreenOnSinceChargePermil;
                int batteryPermil = getBatteryPermil();
                if (this.mOnBattery && Display.isOnState(this.mScreenState) && batteryPermil < (i2 = this.mDischargeScreenOnUnplugLevelPermil)) {
                    i += i2 - batteryPermil;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final int getDischargeAmountSubScreenDozeSinceChargePermil() {
        int i;
        int i2;
        synchronized (this) {
            try {
                i = this.mDischargeAmountSubScreenDozeSinceChargePermil;
                int batteryPermil = getBatteryPermil();
                if (this.mOnBattery && this.mIsSubScreen && Display.isDozeState(this.mSubScreenState) && batteryPermil < (i2 = this.mDischargeSubScreenDozeUnplugLevelPermil)) {
                    i += i2 - batteryPermil;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final int getDischargeAmountSubScreenOnSinceChargePermil() {
        int i;
        int i2;
        synchronized (this) {
            try {
                i = this.mDischargeAmountSubScreenOnSinceChargePermil;
                int batteryPermil = getBatteryPermil();
                if (this.mOnBattery && this.mIsSubScreen && Display.isOnState(this.mSubScreenState) && batteryPermil < (i2 = this.mDischargeSubScreenOnUnplugLevelPermil)) {
                    i += i2 - batteryPermil;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final int getDischargeCurrentLevel() {
        int i;
        synchronized (this) {
            i = this.mDischargeCurrentLevel;
        }
        return i;
    }

    public final BatteryStats.LevelStepTracker getDischargeLevelStepTracker() {
        return this.mDischargeStepTracker;
    }

    public final int getDischargeStartLevel() {
        int i;
        synchronized (this) {
            i = this.mDischargeUnplugLevel;
        }
        return i;
    }

    public final int getDisplayCount() {
        return this.mPerDisplayBatteryStats.length;
    }

    public final long getDisplayHighRefreshRateTime(int i, long j, int i2) {
        return this.mHighRefreshRateTimer[i].getTotalTimeLocked(j, i2);
    }

    public final BatteryStats.Timer getDisplayHighRefreshRateTimer(int i) {
        return this.mHighRefreshRateTimer[i];
    }

    public final long getDisplayScreenBrightnessTime(int i, int i2, long j) {
        return this.mPerDisplayBatteryStats[i].screenBrightnessTimers[i2].getTotalTimeLocked(j, 0);
    }

    public final long getDisplayScreenDozeTime(int i, long j) {
        return this.mPerDisplayBatteryStats[i].screenDozeTimer.getTotalTimeLocked(j, 0);
    }

    public final long getDisplayScreenOnTime(int i, long j) {
        return this.mPerDisplayBatteryStats[i].screenOnTimer.getTotalTimeLocked(j, 0);
    }

    public final String getEndPlatformVersion() {
        return this.mEndPlatformVersion;
    }

    public final int getEstimatedBatteryCapacity() {
        return this.mEstimatedBatteryCapacityMah;
    }

    public final long getExternalStatsCollectionRateLimitMs() {
        long j;
        synchronized (this) {
            j = this.mConstants.EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS;
        }
        return j;
    }

    public final long getFlashlightOnCount(int i) {
        return this.mFlashlightOnTimer.computeCurrentCountLocked();
    }

    public final long getFlashlightOnTime(long j, int i) {
        return this.mFlashlightOnTimer.getTotalTimeLocked(j, i);
    }

    public final long getGlobalWifiRunningTime(long j, int i) {
        return this.mGlobalWifiRunningTimer.getTotalTimeLocked(j, i);
    }

    public final long getGnssEnergyConsumptionUC() {
        return getPowerBucketConsumptionUC(6);
    }

    public final long getGpsBatteryDrainMaMs() {
        double d = 0.0d;
        if (this.mPowerProfile.getAveragePower("gps.voltage") / 1000.0d == 0.0d) {
            return 0L;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime() * 1000;
        for (int i = 0; i < this.mGpsSignalQualityTimer.length; i++) {
            d += this.mPowerProfile.getAveragePower("gps.signalqualitybased", i) * (getGpsSignalQualityTime(i, elapsedRealtime, 0) / 1000);
        }
        return (long) d;
    }

    public final GpsBatteryStats getGpsBatteryStats() {
        GpsBatteryStats gpsBatteryStats = new GpsBatteryStats();
        long elapsedRealtime = SystemClock.elapsedRealtime() * 1000;
        gpsBatteryStats.setLoggingDurationMs(this.mOnBatteryTimeBase.computeRealtime(elapsedRealtime) / 1000);
        gpsBatteryStats.setEnergyConsumedMaMs(getGpsBatteryDrainMaMs());
        int length = this.mGpsSignalQualityTimer.length;
        long[] jArr = new long[length];
        for (int i = 0; i < length; i++) {
            jArr[i] = getGpsSignalQualityTime(i, elapsedRealtime, 0) / 1000;
        }
        gpsBatteryStats.setTimeInGpsSignalQualityLevel(jArr);
        return gpsBatteryStats;
    }

    public final long getGpsSignalQualityTime(int i, long j, int i2) {
        if (i < 0) {
            return 0L;
        }
        StopwatchTimer[] stopwatchTimerArr = this.mGpsSignalQualityTimer;
        if (i >= stopwatchTimerArr.length) {
            return 0L;
        }
        return stopwatchTimerArr[i].getTotalTimeLocked(j, i2);
    }

    public final int getHighDischargeAmountSinceCharge() {
        int i;
        int i2;
        int i3;
        synchronized (this) {
            try {
                i = this.mHighDischargeAmountSinceCharge;
                if (this.mOnBattery && (i2 = this.mDischargeCurrentLevel) < (i3 = this.mDischargeUnplugLevel)) {
                    i += i3 - i2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final int getHistoryStringPoolBytes() {
        return this.mHistory.getHistoryStringPoolBytes();
    }

    public final int getHistoryStringPoolSize() {
        return this.mHistory.getHistoryStringPoolSize();
    }

    public final String getHistoryTagPoolString(int i) {
        return this.mHistory.getHistoryTagPoolString(i);
    }

    public final int getHistoryTagPoolUid(int i) {
        return this.mHistory.getHistoryTagPoolUid(i);
    }

    public final int getHistoryTotalSize() {
        Constants constants = this.mConstants;
        return constants.MAX_HISTORY_BUFFER * constants.MAX_HISTORY_FILES;
    }

    public final int getHistoryUsedSize() {
        return this.mHistory.getHistoryUsedSize();
    }

    public final long getInteractiveTime(long j, int i) {
        return this.mInteractiveTimer.getTotalTimeLocked(j, i);
    }

    public final boolean getIsOnBattery() {
        return this.mOnBattery;
    }

    public final LongSparseArray getKernelMemoryStats() {
        return this.mKernelMemoryStats;
    }

    public final Map getKernelWakelockStats() {
        return this.mKernelWakelockStats;
    }

    public final int getLearnedBatteryCapacity() {
        return this.mLastLearnedBatteryCapacityUah;
    }

    public final long getLongestDeviceIdleModeTime(int i) {
        if (i == 1) {
            return this.mLongestLightIdleTimeMs;
        }
        if (i != 2) {
            return 0L;
        }
        return this.mLongestFullIdleTimeMs;
    }

    public final int getLowDischargeAmountSinceCharge() {
        int i;
        int i2;
        int i3;
        synchronized (this) {
            try {
                i = this.mLowDischargeAmountSinceCharge;
                if (this.mOnBattery && (i2 = this.mDischargeCurrentLevel) < (i3 = this.mDischargeUnplugLevel)) {
                    i += (i3 - i2) - 1;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final int getMaxLearnedBatteryCapacity() {
        return this.mMaxLearnedBatteryCapacityUah;
    }

    public final int getMinLearnedBatteryCapacity() {
        return this.mMinLearnedBatteryCapacityUah;
    }

    public final long getMobileActive5GTime(long j, int i) {
        return this.mMobileActive5GTimer.getTotalTimeLocked(j, i);
    }

    public final long getMobileActiveTime(long j, int i) {
        return this.mMobileActiveTimer.getTotalTimeLocked(j, i);
    }

    public final long getMobileRadioActiveAdjustedTime(int i) {
        return this.mMobileRadioActiveAdjustedTime.mCount;
    }

    public final int getMobileRadioActiveCount(int i) {
        return this.mMobileRadioActiveTimer.computeCurrentCountLocked();
    }

    public final long getMobileRadioActiveTime(long j, int i) {
        return this.mMobileRadioActiveTimer.getTotalTimeLocked(j, i);
    }

    public final int getMobileRadioActiveUnknownCount(int i) {
        return (int) this.mMobileRadioActiveUnknownCount.mCount;
    }

    public final long getMobileRadioActiveUnknownTime(int i) {
        return this.mMobileRadioActiveUnknownTime.mCount;
    }

    public final long getMobileRadioEnergyConsumptionUC() {
        return getPowerBucketConsumptionUC(7);
    }

    public final BatteryStats.ControllerActivityCounter getModemControllerActivity() {
        return this.mModemActivity;
    }

    public final long getNetworkActivityBytes(int i, int i2) {
        if (i < 0) {
            return 0L;
        }
        LongSamplingCounter[] longSamplingCounterArr = this.mNetworkByteActivityCounters;
        if (i < longSamplingCounterArr.length) {
            return longSamplingCounterArr[i].mCount;
        }
        return 0L;
    }

    public final long getNetworkActivityPackets(int i, int i2) {
        if (i < 0) {
            return 0L;
        }
        LongSamplingCounter[] longSamplingCounterArr = this.mNetworkPacketActivityCounters;
        if (i < longSamplingCounterArr.length) {
            return longSamplingCounterArr[i].mCount;
        }
        return 0L;
    }

    public final BatteryStats.ModemActivityCounter getNetworkModemControllerActivity() {
        return this.mNetworkModemActivity;
    }

    public final long getNextMaxDailyDeadline() {
        return this.mNextMaxDailyDeadlineMs;
    }

    public final long getNextMinDailyDeadline() {
        return this.mNextMinDailyDeadlineMs;
    }

    public final long getNrNsaTime(long j) {
        return this.mNrNsaTimer.getTotalTimeLocked(j, 0);
    }

    public final int getNumConnectivityChange(int i) {
        return this.mNumConnectivityChange;
    }

    public final int getParcelVersion() {
        return VERSION;
    }

    public final int getPhoneDataConnectionCount(int i, int i2) {
        return this.mPhoneDataConnectionsTimer[i].computeCurrentCountLocked();
    }

    public final long getPhoneDataConnectionTime(int i, long j, int i2) {
        return this.mPhoneDataConnectionsTimer[i].getTotalTimeLocked(j, i2);
    }

    public final BatteryStats.Timer getPhoneDataConnectionTimer(int i) {
        return this.mPhoneDataConnectionsTimer[i];
    }

    public final long getPhoneEnergyConsumptionUC() {
        return getPowerBucketConsumptionUC(9);
    }

    public final int getPhoneOnCount(int i) {
        return this.mPhoneOnTimer.computeCurrentCountLocked();
    }

    public final long getPhoneOnTime(long j, int i) {
        return this.mPhoneOnTimer.getTotalTimeLocked(j, i);
    }

    public final long getPhoneSignalScanningTime(long j, int i) {
        return this.mPhoneSignalScanningTimer.getTotalTimeLocked(j, i);
    }

    public final BatteryStats.Timer getPhoneSignalScanningTimer() {
        return this.mPhoneSignalScanningTimer;
    }

    public final int getPhoneSignalStrengthCount(int i, int i2) {
        return this.mPhoneSignalStrengthsTimer[i].computeCurrentCountLocked();
    }

    public final long getPhoneSignalStrengthTime(int i, long j, int i2) {
        return this.mPhoneSignalStrengthsTimer[i].getTotalTimeLocked(j, i2);
    }

    public final BatteryStats.Timer getPhoneSignalStrengthTimer(int i) {
        return this.mPhoneSignalStrengthsTimer[i];
    }

    public final long getPowerBucketConsumptionUC(int i) {
        EnergyConsumerStats energyConsumerStats = this.mGlobalEnergyConsumerStats;
        if (energyConsumerStats == null) {
            return -1L;
        }
        return energyConsumerStats.getAccumulatedStandardBucketCharge(i);
    }

    public final int getPowerSaveModeEnabledCount(int i) {
        return this.mPowerSaveModeEnabledTimer.computeCurrentCountLocked();
    }

    public final long getPowerSaveModeEnabledTime(long j, int i) {
        return this.mPowerSaveModeEnabledTimer.getTotalTimeLocked(j, i);
    }

    public final RadioAccessTechnologyBatteryStats getRatBatteryStatsLocked(int i) {
        RadioAccessTechnologyBatteryStats radioAccessTechnologyBatteryStats = this.mPerRatBatteryStats[i];
        if (radioAccessTechnologyBatteryStats != null) {
            return radioAccessTechnologyBatteryStats;
        }
        RadioAccessTechnologyBatteryStats radioAccessTechnologyBatteryStats2 = new RadioAccessTechnologyBatteryStats(i == 2 ? 5 : 1, this.mClock, this.mOnBatteryTimeBase);
        this.mPerRatBatteryStats[i] = radioAccessTechnologyBatteryStats2;
        return radioAccessTechnologyBatteryStats2;
    }

    public final Map getRpmStats() {
        return this.mRpmStats;
    }

    public final SamplingTimer getRpmTimerLocked(String str) {
        SamplingTimer samplingTimer = (SamplingTimer) this.mRpmStats.get(str);
        if (samplingTimer != null) {
            return samplingTimer;
        }
        SamplingTimer samplingTimer2 = new SamplingTimer(this.mClock, this.mOnBatteryTimeBase);
        this.mRpmStats.put(str, samplingTimer2);
        return samplingTimer2;
    }

    public final long getScreenAutoBrightnessTime(int i, long j, int i2) {
        return this.mScreenAutoBrightnessTimer[i].getTotalTimeLocked(j, i2);
    }

    public final long getScreenBrightnessTime(int i, long j, int i2) {
        return this.mScreenBrightnessTimer[i].getTotalTimeLocked(j, i2);
    }

    public final BatteryStats.Timer getScreenBrightnessTimer(int i) {
        return this.mScreenBrightnessTimer[i];
    }

    public final int getScreenDischargeAmount(int i, int i2, int i3, boolean z) {
        synchronized (this) {
            if (this.mOnBattery && z && i3 < i2) {
                i += i2 - i3;
            }
        }
        return i;
    }

    public final int getScreenDozeCount(int i) {
        return this.mScreenDozeTimer.computeCurrentCountLocked();
    }

    public final long getScreenDozeEnergyConsumptionUC() {
        return getPowerBucketConsumptionUC(1);
    }

    public final long getScreenDozeTime(long j, int i) {
        return this.mScreenDozeTimer.getTotalTimeLocked(j, i);
    }

    public final long getScreenHighBrightnessTime(long j, int i) {
        return this.mScreenHighBrightnessTimer.getTotalTimeLocked(j, i);
    }

    public final Map getScreenOffRpmStats() {
        return this.mScreenOffRpmStats;
    }

    public final int getScreenOnCount(int i) {
        return this.mScreenOnTimer.computeCurrentCountLocked();
    }

    public final long getScreenOnEnergyConsumptionUC() {
        return getPowerBucketConsumptionUC(0);
    }

    public final long getScreenOnGpsRunningTime(long j, int i) {
        return this.mScreenOnGpsTimer.getTotalTimeLocked(j, i);
    }

    public final long getScreenOnTime(long j, int i) {
        return this.mScreenOnTimer.getTotalTimeLocked(j, i);
    }

    public final Map getScreenWakeStats() {
        return this.mScreenWakeStats;
    }

    public final long getSpeakerCallTime(int i, int i2) {
        if (i < 0) {
            return 0L;
        }
        LongSamplingCounter[] longSamplingCounterArr = this.mSpeakerCallTimeCounters;
        if (i < longSamplingCounterArr.length) {
            return longSamplingCounterArr[i].mCount;
        }
        return 0L;
    }

    public final long getSpeakerMediaTime(int i, int i2) {
        if (i < 0) {
            return 0L;
        }
        LongSamplingCounter[] longSamplingCounterArr = this.mSpeakerMediaTimeCounters;
        if (i < longSamplingCounterArr.length) {
            return longSamplingCounterArr[i].mCount;
        }
        return 0L;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x001f, code lost:
    
        if (r10.mStartClockTimeMs > r8) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long getStartClockTime() {
        /*
            r10 = this;
            monitor-enter(r10)
            com.android.internal.os.Clock r0 = r10.mClock     // Catch: java.lang.Throwable -> L19
            long r8 = r0.currentTimeMillis()     // Catch: java.lang.Throwable -> L19
            r0 = 31536000000(0x757b12c00, double:1.55808542072E-313)
            int r2 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            if (r2 <= 0) goto L1b
            long r2 = r10.mStartClockTimeMs     // Catch: java.lang.Throwable -> L19
            long r0 = r8 - r0
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 < 0) goto L21
            goto L1b
        L19:
            r0 = move-exception
            goto L46
        L1b:
            long r0 = r10.mStartClockTimeMs     // Catch: java.lang.Throwable -> L19
            int r0 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r0 <= 0) goto L42
        L21:
            com.android.internal.os.BatteryStatsHistory r1 = r10.mHistory     // Catch: java.lang.Throwable -> L19
            com.android.internal.os.Clock r0 = r10.mClock     // Catch: java.lang.Throwable -> L19
            long r2 = r0.elapsedRealtime()     // Catch: java.lang.Throwable -> L19
            com.android.internal.os.Clock r0 = r10.mClock     // Catch: java.lang.Throwable -> L19
            long r4 = r0.uptimeMillis()     // Catch: java.lang.Throwable -> L19
            r6 = r8
            r1.recordCurrentTimeChange(r2, r4, r6)     // Catch: java.lang.Throwable -> L19
            com.android.internal.os.Clock r0 = r10.mClock     // Catch: java.lang.Throwable -> L19
            long r0 = r0.elapsedRealtime()     // Catch: java.lang.Throwable -> L19
            long r2 = r10.mRealtimeStartUs     // Catch: java.lang.Throwable -> L19
            r4 = 1000(0x3e8, double:4.94E-321)
            long r2 = r2 / r4
            long r0 = r0 - r2
            long r8 = r8 - r0
            r10.mStartClockTimeMs = r8     // Catch: java.lang.Throwable -> L19
        L42:
            long r0 = r10.mStartClockTimeMs     // Catch: java.lang.Throwable -> L19
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L19
            return r0
        L46:
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L19
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.stats.BatteryStatsImpl.getStartClockTime():long");
    }

    public final int getStartCount() {
        return this.mStartCount;
    }

    public final String getStartPlatformVersion() {
        return this.mStartPlatformVersion;
    }

    public final long getStatsStartRealtime() {
        return this.mRealtimeStartUs;
    }

    public final long getSubDisplayHighRefreshRateTime(int i, long j, int i2) {
        return this.mSubHighRefreshRateTimer[i].getTotalTimeLocked(j, i2);
    }

    public final long getSubScreenAutoBrightnessTime(int i, long j, int i2) {
        return this.mSubScreenAutoBrightnessTimer[i].getTotalTimeLocked(j, i2);
    }

    public final long getSubScreenBrightnessTime(int i, long j, int i2) {
        return this.mSubScreenBrightnessTimer[i].getTotalTimeLocked(j, i2);
    }

    public final BatteryStats.Timer getSubScreenBrightnessTimer(int i) {
        return this.mSubScreenBrightnessTimer[i];
    }

    public final int getSubScreenDozeCount(int i) {
        return this.mSubScreenDozeTimer.computeCurrentCountLocked();
    }

    public final long getSubScreenDozeTime(long j, int i) {
        return this.mSubScreenDozeTimer.getTotalTimeLocked(j, i);
    }

    public final long getSubScreenHighBrightnessTime(long j, int i) {
        return this.mSubScreenHighBrightnessTimer.getTotalTimeLocked(j, i);
    }

    public final int getSubScreenOnCount(int i) {
        return this.mSubScreenOnTimer.computeCurrentCountLocked();
    }

    public final long getSubScreenOnTime(long j, int i) {
        return this.mSubScreenOnTimer.getTotalTimeLocked(j, i);
    }

    public final SystemServerCpuThreadReader.SystemServiceCpuThreadTimes getSystemServiceCpuThreadTimes() {
        SystemServerCpuThreadReader systemServerCpuThreadReader = this.mSystemServerCpuThreadReader;
        int cpuFrequencyCount = systemServerCpuThreadReader.mKernelCpuThreadReader.getCpuFrequencyCount();
        KernelSingleProcessCpuThreadReader.ProcessCpuUsage processCpuUsage = systemServerCpuThreadReader.mKernelCpuThreadReader.getProcessCpuUsage();
        if (processCpuUsage == null) {
            return null;
        }
        SystemServerCpuThreadReader.SystemServiceCpuThreadTimes systemServiceCpuThreadTimes = new SystemServerCpuThreadReader.SystemServiceCpuThreadTimes();
        systemServiceCpuThreadTimes.threadCpuTimesUs = new long[cpuFrequencyCount];
        systemServiceCpuThreadTimes.binderThreadCpuTimesUs = new long[cpuFrequencyCount];
        for (int i = 0; i < cpuFrequencyCount; i++) {
            systemServiceCpuThreadTimes.threadCpuTimesUs[i] = processCpuUsage.threadCpuTimesMillis[i] * 1000;
            systemServiceCpuThreadTimes.binderThreadCpuTimesUs[i] = processCpuUsage.selectedThreadCpuTimesMillis[i] * 1000;
        }
        return systemServiceCpuThreadTimes;
    }

    public final long[] getSystemServiceTimeAtCpuSpeeds() {
        LongSamplingCounterArray longSamplingCounterArray = this.mBinderThreadCpuTimesUs;
        if (longSamplingCounterArray == null) {
            return null;
        }
        return longSamplingCounterArray.getCountsLocked(0);
    }

    public final long getTxPowerSharingTime(long j, int i) {
        return this.mTxPowerSharingTimer.getTotalTimeLocked(j, i);
    }

    public final long getTxSharingDischargeAmount() {
        return this.mTxPowerSharingDischargeMah;
    }

    public final long getUahDischarge(int i) {
        return this.mDischargeCounter.mCount;
    }

    public final long getUahDischargeDeepDoze(int i) {
        return this.mDischargeDeepDozeCounter.mCount;
    }

    public final long getUahDischargeLightDoze(int i) {
        return this.mDischargeLightDozeCounter.mCount;
    }

    public final long getUahDischargeScreenDoze(int i) {
        return this.mDischargeScreenDozeCounter.mCount;
    }

    public final long getUahDischargeScreenOff(int i) {
        return this.mDischargeScreenOffCounter.mCount;
    }

    public final SparseArray getUidStats() {
        return this.mUidStats;
    }

    public final Uid getUidStatsLocked(int i) {
        return getUidStatsLocked(i, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    public final Uid getUidStatsLocked(int i, long j, long j2) {
        Uid uid = (Uid) this.mUidStats.get(i);
        if (uid != null) {
            return uid;
        }
        if (Process.isSdkSandboxUid(i)) {
            Log.wtf("BatteryStatsImpl", "Tracking an SDK Sandbox UID");
        }
        Uid uid2 = new Uid(this, i, j, j2);
        this.mUidStats.put(i, uid2);
        return uid2;
    }

    public final WakeLockStats getWakeLockStats() {
        WakeLockStats.WakeLock wakeLock;
        long elapsedRealtime = this.mClock.elapsedRealtime();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int size = this.mUidStats.size() - 1; size >= 0; size--) {
            Uid uid = (Uid) this.mUidStats.valueAt(size);
            ArrayMap arrayMap = uid.mWakelockStats.mMap;
            int size2 = arrayMap.size();
            while (true) {
                size2--;
                wakeLock = null;
                if (size2 < 0) {
                    break;
                }
                String str = (String) arrayMap.keyAt(size2);
                DualTimer dualTimer = ((Uid.Wakelock) arrayMap.valueAt(size2)).mTimerPartial;
                if (dualTimer != null) {
                    WakeLockStats.WakeLockData createWakeLockData = createWakeLockData(dualTimer, elapsedRealtime);
                    WakeLockStats.WakeLockData createWakeLockData2 = createWakeLockData(dualTimer.mSubTimer, elapsedRealtime);
                    if (WakeLockStats.WakeLock.isDataValid(createWakeLockData, createWakeLockData2)) {
                        wakeLock = new WakeLockStats.WakeLock(uid.mUid, str, false, createWakeLockData, createWakeLockData2);
                    }
                }
                if (wakeLock != null) {
                    arrayList.add(wakeLock);
                }
            }
            DualTimer dualTimer2 = uid.mAggregatedPartialWakelockTimer;
            if (dualTimer2 != null) {
                WakeLockStats.WakeLockData createWakeLockData3 = createWakeLockData(dualTimer2, elapsedRealtime);
                WakeLockStats.WakeLockData createWakeLockData4 = createWakeLockData(dualTimer2.mSubTimer, elapsedRealtime);
                if (WakeLockStats.WakeLock.isDataValid(createWakeLockData3, createWakeLockData4)) {
                    wakeLock = new WakeLockStats.WakeLock(uid.mUid, "wakelockstats_aggregated", true, createWakeLockData3, createWakeLockData4);
                }
            }
            if (wakeLock != null) {
                arrayList2.add(wakeLock);
            }
        }
        return new WakeLockStats(arrayList, arrayList2);
    }

    public final Map getWakeupReasonStats() {
        return this.mWakeupReasonStats;
    }

    public final long getWifiActiveTime(long j, int i) {
        return this.mWifiActiveTimer.getTotalTimeLocked(j, i);
    }

    public final WifiBatteryStats getWifiBatteryStats() {
        long elapsedRealtime = this.mClock.elapsedRealtime() * 1000;
        ControllerActivityCounterImpl controllerActivityCounterImpl = this.mWifiActivity;
        long countLocked = controllerActivityCounterImpl.getIdleTimeCounter().getCountLocked(0);
        long j = controllerActivityCounterImpl.mScanTimeMillis.mCount;
        long countLocked2 = controllerActivityCounterImpl.getRxTimeCounter().getCountLocked(0);
        long countLocked3 = controllerActivityCounterImpl.getTxTimeCounters()[0].getCountLocked(0);
        long max = Math.max(0L, (this.mOnBatteryTimeBase.computeRealtime(this.mClock.elapsedRealtime() * 1000) / 1000) - ((countLocked + countLocked2) + countLocked3));
        long j2 = controllerActivityCounterImpl.mPowerDrainMaMs.mCount;
        long j3 = controllerActivityCounterImpl.mMonitoredRailChargeConsumedMaMs.mCount;
        long j4 = 0;
        for (int i = 0; i < this.mUidStats.size(); i++) {
            j4 += ((Uid) this.mUidStats.valueAt(i)).mWifiScanTimer.mCount;
        }
        long[] jArr = new long[8];
        for (int i2 = 0; i2 < 8; i2++) {
            jArr[i2] = getWifiStateTime(i2, elapsedRealtime, 0) / 1000;
        }
        long[] jArr2 = new long[13];
        for (int i3 = 0; i3 < 13; i3++) {
            jArr2[i3] = getWifiSupplStateTime(i3, elapsedRealtime, 0) / 1000;
        }
        long[] jArr3 = new long[5];
        for (int i4 = 0; i4 < 5; i4++) {
            jArr3[i4] = getWifiSignalStrengthTime(i4, elapsedRealtime, 0) / 1000;
        }
        return new WifiBatteryStats(this.mOnBatteryTimeBase.computeRealtime(elapsedRealtime) / 1000, this.mWifiActiveTimer.getTotalTimeLocked(elapsedRealtime, 0) / 1000, getNetworkActivityPackets(3, 0), getNetworkActivityBytes(3, 0), getNetworkActivityPackets(2, 0), getNetworkActivityBytes(2, 0), max, j, countLocked, countLocked2, countLocked3, j2, j4, jArr, jArr3, jArr2, j3);
    }

    public final BatteryStats.ControllerActivityCounter getWifiControllerActivity() {
        return this.mWifiActivity;
    }

    public final long getWifiEnergyConsumptionUC() {
        return getPowerBucketConsumptionUC(4);
    }

    public final int getWifiMulticastWakelockCount(int i) {
        return this.mWifiMulticastWakelockTimer.computeCurrentCountLocked();
    }

    public final long getWifiMulticastWakelockTime(long j, int i) {
        return this.mWifiMulticastWakelockTimer.getTotalTimeLocked(j, i);
    }

    public final long getWifiOnTime(long j, int i) {
        return this.mWifiOnTimer.getTotalTimeLocked(j, i);
    }

    public final int getWifiSignalStrengthCount(int i, int i2) {
        return this.mWifiSignalStrengthsTimer[i].computeCurrentCountLocked();
    }

    public final long getWifiSignalStrengthTime(int i, long j, int i2) {
        return this.mWifiSignalStrengthsTimer[i].getTotalTimeLocked(j, i2);
    }

    public final BatteryStats.Timer getWifiSignalStrengthTimer(int i) {
        return this.mWifiSignalStrengthsTimer[i];
    }

    public final int getWifiStateCount(int i, int i2) {
        return this.mWifiStateTimer[i].computeCurrentCountLocked();
    }

    public final long getWifiStateTime(int i, long j, int i2) {
        return this.mWifiStateTimer[i].getTotalTimeLocked(j, i2);
    }

    public final BatteryStats.Timer getWifiStateTimer(int i) {
        return this.mWifiStateTimer[i];
    }

    public final int getWifiSupplStateCount(int i, int i2) {
        return this.mWifiSupplStateTimer[i].computeCurrentCountLocked();
    }

    public final long getWifiSupplStateTime(int i, long j, int i2) {
        return this.mWifiSupplStateTimer[i].getTotalTimeLocked(j, i2);
    }

    public final BatteryStats.Timer getWifiSupplStateTimer(int i) {
        return this.mWifiSupplStateTimer[i];
    }

    public final boolean hasBluetoothActivityReporting() {
        return this.mHasBluetoothReporting;
    }

    public final boolean hasModemActivityReporting() {
        return this.mHasModemReporting;
    }

    public final boolean hasSpeakerOutPowerReporting() {
        return this.mHasSpeakerOutReporting;
    }

    public final boolean hasWifiActivityReporting() {
        return this.mHasWifiReporting;
    }

    public final RxTxConsumption incrementPerRatDataLocked(ModemActivityInfo modemActivityInfo, long j) {
        long j2;
        long j3;
        double d;
        double d2;
        int i;
        int i2;
        int i3;
        long[] jArr;
        long[] jArr2;
        ModemActivityInfo modemActivityInfo2 = modemActivityInfo;
        long j4 = j;
        int specificInfoLength = modemActivityInfo.getSpecificInfoLength();
        if (specificInfoLength == 1 && modemActivityInfo2.getSpecificInfoRat(0) == 0 && modemActivityInfo2.getSpecificInfoFrequencyRange(0) == 0) {
            int i4 = CELL_SIGNAL_STRENGTH_LEVEL_COUNT;
            long[] jArr3 = new long[i4];
            int i5 = 0;
            long j5 = 0;
            for (int i6 = 3; i5 < i6; i6 = 3) {
                RadioAccessTechnologyBatteryStats radioAccessTechnologyBatteryStats = this.mPerRatBatteryStats[i5];
                if (radioAccessTechnologyBatteryStats != null) {
                    for (StopwatchTimer[] stopwatchTimerArr : radioAccessTechnologyBatteryStats.perStateTimers) {
                        for (int i7 = 0; i7 < i4; i7++) {
                            long timeSinceMarkLocked = stopwatchTimerArr[i7].getTimeSinceMarkLocked(j4 * 1000) / 1000;
                            jArr3[i7] = jArr3[i7] + timeSinceMarkLocked;
                            j5 += timeSinceMarkLocked;
                        }
                    }
                }
                i5++;
            }
            if (j5 != 0) {
                long j6 = 0;
                long j7 = 0;
                double d3 = 0.0d;
                int i8 = 0;
                double d4 = 0.0d;
                for (int i9 = 3; i8 < i9; i9 = 3) {
                    RadioAccessTechnologyBatteryStats radioAccessTechnologyBatteryStats2 = this.mPerRatBatteryStats[i8];
                    if (radioAccessTechnologyBatteryStats2 == null) {
                        i3 = i4;
                        jArr = jArr3;
                    } else {
                        long j8 = j6;
                        StopwatchTimer[][] stopwatchTimerArr2 = radioAccessTechnologyBatteryStats2.perStateTimers;
                        int length = stopwatchTimerArr2.length;
                        double d5 = d4;
                        double d6 = d3;
                        int i10 = 0;
                        long j9 = j7;
                        long j10 = j8;
                        long j11 = j9;
                        while (i10 < length) {
                            double d7 = d6;
                            long j12 = j11;
                            int i11 = 0;
                            long j13 = 0;
                            while (i11 < i4) {
                                int i12 = i4;
                                StopwatchTimer[][] stopwatchTimerArr3 = stopwatchTimerArr2;
                                int i13 = length;
                                long timeSinceMarkLocked2 = stopwatchTimerArr2[i10][i11].getTimeSinceMarkLocked(j4 * 1000) / 1000;
                                long j14 = jArr3[i11];
                                if (j14 == 0) {
                                    jArr2 = jArr3;
                                } else {
                                    long transmitDurationMillisAtPowerLevel = ((j14 / 2) + (modemActivityInfo2.getTransmitDurationMillisAtPowerLevel(i11) * timeSinceMarkLocked2)) / j14;
                                    jArr2 = jArr3;
                                    radioAccessTechnologyBatteryStats2.getTxDurationCounter(i10, i11, true).addCountLocked(transmitDurationMillisAtPowerLevel);
                                    j13 += timeSinceMarkLocked2;
                                    if (isMobileRadioEnergyConsumerSupportedLocked()) {
                                        d7 += this.mMobileRadioPowerCalculator.calcTxStatePowerMah(i8, i10, i11, transmitDurationMillisAtPowerLevel);
                                        j12 += transmitDurationMillisAtPowerLevel;
                                    }
                                }
                                i11++;
                                j4 = j;
                                stopwatchTimerArr2 = stopwatchTimerArr3;
                                i4 = i12;
                                length = i13;
                                jArr3 = jArr2;
                            }
                            int i14 = i4;
                            StopwatchTimer[][] stopwatchTimerArr4 = stopwatchTimerArr2;
                            int i15 = length;
                            long[] jArr4 = jArr3;
                            long receiveTimeMillis = ((j5 / 2) + (modemActivityInfo.getReceiveTimeMillis() * j13)) / j5;
                            radioAccessTechnologyBatteryStats2.getRxDurationCounter(i10, true).addCountLocked(receiveTimeMillis);
                            if (isMobileRadioEnergyConsumerSupportedLocked()) {
                                j10 += receiveTimeMillis;
                                d5 = this.mMobileRadioPowerCalculator.calcRxStatePowerMah(i8, i10, receiveTimeMillis) + d5;
                            }
                            i10++;
                            j4 = j;
                            stopwatchTimerArr2 = stopwatchTimerArr4;
                            d6 = d7;
                            j11 = j12;
                            i4 = i14;
                            length = i15;
                            jArr3 = jArr4;
                        }
                        i3 = i4;
                        jArr = jArr3;
                        j6 = j10;
                        d3 = d6;
                        j7 = j11;
                        d4 = d5;
                    }
                    i8++;
                    j4 = j;
                    i4 = i3;
                    jArr3 = jArr;
                }
                j2 = j7;
                j3 = j6;
                d = d4;
                d2 = d3;
            } else {
                j2 = 0;
                j3 = 0;
                d = 0.0d;
                d2 = 0.0d;
            }
        } else {
            j2 = 0;
            j3 = 0;
            int i16 = 0;
            d = 0.0d;
            d2 = 0.0d;
            while (i16 < specificInfoLength) {
                int specificInfoRat = modemActivityInfo2.getSpecificInfoRat(i16);
                int specificInfoFrequencyRange = modemActivityInfo2.getSpecificInfoFrequencyRange(i16);
                int mapRadioAccessNetworkTypeToRadioAccessTechnology = MobileRadioPowerStatsCollector.mapRadioAccessNetworkTypeToRadioAccessTechnology(specificInfoRat);
                RadioAccessTechnologyBatteryStats ratBatteryStatsLocked = getRatBatteryStatsLocked(mapRadioAccessNetworkTypeToRadioAccessTechnology);
                long receiveTimeMillis2 = modemActivityInfo2.getReceiveTimeMillis(specificInfoRat, specificInfoFrequencyRange);
                int[] transmitTimeMillis = modemActivityInfo2.getTransmitTimeMillis(specificInfoRat, specificInfoFrequencyRange);
                ratBatteryStatsLocked.getRxDurationCounter(specificInfoFrequencyRange, true).addCountLocked(receiveTimeMillis2);
                if (isMobileRadioEnergyConsumerSupportedLocked()) {
                    j3 += receiveTimeMillis2;
                    d = this.mMobileRadioPowerCalculator.calcRxStatePowerMah(mapRadioAccessNetworkTypeToRadioAccessTechnology, specificInfoFrequencyRange, receiveTimeMillis2) + d;
                }
                int length2 = transmitTimeMillis.length;
                int i17 = 0;
                while (i17 < length2) {
                    long j15 = transmitTimeMillis[i17];
                    int[] iArr = transmitTimeMillis;
                    ratBatteryStatsLocked.getTxDurationCounter(specificInfoFrequencyRange, i17, true).addCountLocked(j15);
                    if (isMobileRadioEnergyConsumerSupportedLocked()) {
                        i = i17;
                        i2 = length2;
                        d2 += this.mMobileRadioPowerCalculator.calcTxStatePowerMah(mapRadioAccessNetworkTypeToRadioAccessTechnology, specificInfoFrequencyRange, i, j15);
                        j2 += j15;
                    } else {
                        i = i17;
                        i2 = length2;
                    }
                    i17 = i + 1;
                    transmitTimeMillis = iArr;
                    length2 = i2;
                }
                i16++;
                modemActivityInfo2 = modemActivityInfo;
            }
        }
        double d8 = d;
        long j16 = j3;
        double d9 = d2;
        long j17 = j2;
        for (int i18 = 0; i18 < 3; i18++) {
            RadioAccessTechnologyBatteryStats radioAccessTechnologyBatteryStats3 = this.mPerRatBatteryStats[i18];
            if (radioAccessTechnologyBatteryStats3 != null) {
                for (StopwatchTimer[] stopwatchTimerArr5 : radioAccessTechnologyBatteryStats3.perStateTimers) {
                    for (int i19 = 0; i19 < 5; i19++) {
                        stopwatchTimerArr5[i19].setMark(j);
                    }
                }
            }
        }
        if (isMobileRadioEnergyConsumerSupportedLocked()) {
            return new RxTxConsumption(d8, j16, d9, j17);
        }
        return null;
    }

    public final void initActiveHistoryEventsLocked(long j, long j2) {
        HashMap stateForEvent;
        for (int i = 0; i < 22; i++) {
            if ((this.mRecordAllHistory || i != 1) && (stateForEvent = this.mActiveEvents.getStateForEvent(i)) != null) {
                for (Map.Entry entry : stateForEvent.entrySet()) {
                    SparseIntArray sparseIntArray = (SparseIntArray) entry.getValue();
                    for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
                        this.mHistory.recordEvent(j, j2, i, (String) entry.getKey(), sparseIntArray.keyAt(i2));
                    }
                }
            }
        }
    }

    public final void initDischarge() {
        this.mLowDischargeAmountSinceCharge = 0;
        this.mHighDischargeAmountSinceCharge = 0;
        this.mDischargeAmountScreenOn = 0;
        this.mDischargeAmountScreenOnSinceCharge = 0;
        this.mDischargeAmountScreenOff = 0;
        this.mDischargeAmountScreenOffSinceCharge = 0;
        this.mDischargeAmountScreenDoze = 0;
        this.mDischargeAmountScreenDozeSinceCharge = 0;
        this.mBatteryRaw = -1;
        this.mBatteryCC = -1;
        this.mDischargeAmountScreenOnSinceChargePermil = 0;
        this.mDischargeAmountScreenOffSinceChargePermil = 0;
        this.mDischargeAmountScreenDozeSinceChargePermil = 0;
        this.mDischargeAmountScreenOnSinceChargeCoulombCounter = 0;
        this.mDischargeAmountScreenOffSinceChargeCoulombCounter = 0;
        this.mDischargeSubScreenOnUnplugLevelPermil = 0;
        this.mDischargeSubScreenDozeUnplugLevelPermil = 0;
        this.mDischargeAmountSubScreenOnSinceChargePermil = 0;
        this.mDischargeAmountSubScreenDozeSinceChargePermil = 0;
        this.mDischargeStepTracker.init();
        this.mChargeStepTracker.init();
        this.mDischargeScreenOffCounter.mCount = 0L;
        this.mDischargeScreenDozeCounter.mCount = 0L;
        this.mDischargeLightDozeCounter.mCount = 0L;
        this.mDischargeDeepDozeCounter.mCount = 0L;
        this.mDischargeCounter.mCount = 0L;
    }

    public final void initEnergyConsumerStatsLocked(boolean[] zArr, String[] strArr) {
        int length = this.mPerDisplayBatteryStats.length;
        for (int i = 0; i < length; i++) {
            DisplayBatteryStats displayBatteryStats = this.mPerDisplayBatteryStats[i];
            displayBatteryStats.screenStateAtLastEnergyMeasurement = displayBatteryStats.screenState;
        }
        if (zArr == null) {
            if (this.mEnergyConsumerStatsConfig != null) {
                resetAllStatsLocked(4, SystemClock.uptimeMillis(), SystemClock.elapsedRealtime());
            }
            this.mEnergyConsumerStatsConfig = null;
            this.mGlobalEnergyConsumerStats = null;
            return;
        }
        int[] iArr = SUPPORTED_PER_PROCESS_STATE_STANDARD_ENERGY_BUCKETS;
        String[] strArr2 = new String[5];
        for (int i2 = 0; i2 < 5; i2++) {
            strArr2[i2] = BatteryConsumer.processStateToString(i2);
        }
        EnergyConsumerStats.Config config = new EnergyConsumerStats.Config(zArr, strArr, iArr, strArr2);
        EnergyConsumerStats.Config config2 = this.mEnergyConsumerStatsConfig;
        if (config2 != null && !config2.isCompatible(config)) {
            resetAllStatsLocked(4, SystemClock.uptimeMillis(), SystemClock.elapsedRealtime());
        }
        this.mEnergyConsumerStatsConfig = config;
        this.mGlobalEnergyConsumerStats = new EnergyConsumerStats(config);
        if (zArr[5]) {
            this.mBluetoothPowerCalculator = new BluetoothPowerCalculator(this.mPowerProfile);
        }
        if (zArr[7]) {
            this.mMobileRadioPowerCalculator = new MobileRadioPowerCalculator(this.mPowerProfile);
        }
        if (zArr[4]) {
            this.mWifiPowerCalculator = new WifiPowerCalculator(this.mPowerProfile);
        }
    }

    public void initTimersAndCounters() {
        this.mScreenOnTimer = new StopwatchTimer(this.mClock, null, -1, null, this.mOnBatteryTimeBase);
        this.mScreenDozeTimer = new StopwatchTimer(this.mClock, null, -1, null, this.mOnBatteryTimeBase);
        for (int i = 0; i < 5; i++) {
            this.mScreenBrightnessTimer[i] = new StopwatchTimer(this.mClock, null, (-100) - i, null, this.mOnBatteryTimeBase);
        }
        this.mSubScreenState = 0;
        this.mSubScreenOnTimer = new StopwatchTimer(this.mClock, null, -1, null, this.mOnBatteryTimeBase);
        this.mSubScreenDozeTimer = new StopwatchTimer(this.mClock, null, -1, null, this.mOnBatteryTimeBase);
        for (int i2 = 0; i2 < 5; i2++) {
            this.mSubScreenBrightnessTimer[i2] = new StopwatchTimer(this.mClock, null, (-100) - i2, null, this.mOnBatteryTimeBase);
        }
        for (int i3 = 0; i3 < 5; i3++) {
            this.mScreenAutoBrightnessTimer[i3] = new StopwatchTimer(this.mClock, null, (-101) - i3, null, this.mOnBatteryTimeBase);
        }
        for (int i4 = 0; i4 < 5; i4++) {
            this.mSubScreenAutoBrightnessTimer[i4] = new StopwatchTimer(this.mClock, null, (-101) - i4, null, this.mOnBatteryTimeBase);
        }
        this.mScreenHighBrightnessTimer = new StopwatchTimer(this.mClock, null, -1, null, this.mOnBatteryTimeBase);
        this.mSubScreenHighBrightnessTimer = new StopwatchTimer(this.mClock, null, -1, null, this.mOnBatteryTimeBase);
        for (int i5 = 0; i5 < 4; i5++) {
            this.mHighRefreshRateTimer[i5] = new StopwatchTimer(this.mClock, null, (-1100) - i5, null, this.mOnBatteryTimeBase);
        }
        for (int i6 = 0; i6 < 4; i6++) {
            this.mSubHighRefreshRateTimer[i6] = new StopwatchTimer(this.mClock, null, (-1100) - i6, null, this.mOnBatteryTimeBase);
        }
        for (int i7 = 0; i7 < 16; i7++) {
            this.mSpeakerMediaTimeCounters[i7] = new LongSamplingCounter(this.mOnBatteryTimeBase);
            this.mSpeakerCallTimeCounters[i7] = new LongSamplingCounter(this.mOnBatteryTimeBase);
        }
        this.mTxPowerSharingTimer = new StopwatchTimer(this.mClock, null, -4, null, this.mOnBatteryTimeBase);
        this.mTxPowerSharingDischargeMah = 0L;
        this.mMobileActiveTimer = new StopwatchTimer(this.mClock, null, -1, null, this.mOnBatteryTimeBase);
        this.mMobileActive5GTimer = new StopwatchTimer(this.mClock, null, -1, null, this.mOnBatteryTimeBase);
        this.mScreenOnGpsTimer = new StopwatchTimer(this.mClock, null, -1, null, this.mOnBatteryTimeBase);
        this.mInteractiveTimer = new StopwatchTimer(this.mClock, null, -10, null, this.mOnBatteryTimeBase);
        this.mPowerSaveModeEnabledTimer = new StopwatchTimer(this.mClock, null, -2, null, this.mOnBatteryTimeBase);
        this.mDeviceIdleModeLightTimer = new StopwatchTimer(this.mClock, null, -11, null, this.mOnBatteryTimeBase);
        this.mDeviceIdleModeFullTimer = new StopwatchTimer(this.mClock, null, -14, null, this.mOnBatteryTimeBase);
        this.mDeviceLightIdlingTimer = new StopwatchTimer(this.mClock, null, -15, null, this.mOnBatteryTimeBase);
        this.mDeviceIdlingTimer = new StopwatchTimer(this.mClock, null, -12, null, this.mOnBatteryTimeBase);
        this.mPhoneOnTimer = new StopwatchTimer(this.mClock, null, -3, null, this.mOnBatteryTimeBase);
        for (int i8 = 0; i8 < CELL_SIGNAL_STRENGTH_LEVEL_COUNT; i8++) {
            this.mPhoneSignalStrengthsTimer[i8] = new StopwatchTimer(this.mClock, null, (-200) - i8, null, this.mOnBatteryTimeBase);
        }
        this.mPhoneSignalScanningTimer = new StopwatchTimer(this.mClock, null, -199, null, this.mOnBatteryTimeBase);
        for (int i9 = 0; i9 < BatteryStats.NUM_DATA_CONNECTION_TYPES; i9++) {
            this.mPhoneDataConnectionsTimer[i9] = new StopwatchTimer(this.mClock, null, (-300) - i9, null, this.mOnBatteryTimeBase);
        }
        this.mNrNsaTimer = new StopwatchTimer(this.mClock, null, -198, null, this.mOnBatteryTimeBase);
        for (int i10 = 0; i10 < 10; i10++) {
            this.mNetworkByteActivityCounters[i10] = new LongSamplingCounter(this.mOnBatteryTimeBase);
            this.mNetworkPacketActivityCounters[i10] = new LongSamplingCounter(this.mOnBatteryTimeBase);
        }
        this.mWifiActivity = new ControllerActivityCounterImpl(1, this.mClock, this.mOnBatteryTimeBase);
        this.mBluetoothActivity = new ControllerActivityCounterImpl(1, this.mClock, this.mOnBatteryTimeBase);
        this.mModemActivity = new ControllerActivityCounterImpl(MODEM_TX_POWER_LEVEL_COUNT, this.mClock, this.mOnBatteryTimeBase);
        this.mNetworkModemActivity = new ModemActivityCounterImpl(this.mOnBatteryTimeBase);
        this.mMobileRadioActiveTimer = new StopwatchTimer(this.mClock, null, -400, null, this.mOnBatteryTimeBase);
        this.mMobileRadioActivePerAppTimer = new StopwatchTimer(this.mClock, null, -401, null, this.mOnBatteryTimeBase);
        this.mMobileRadioActiveAdjustedTime = new LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mMobileRadioActiveUnknownTime = new LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mMobileRadioActiveUnknownCount = new LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mWifiMulticastWakelockTimer = new StopwatchTimer(this.mClock, null, 23, null, this.mOnBatteryTimeBase);
        this.mWifiOnTimer = new StopwatchTimer(this.mClock, null, -4, null, this.mOnBatteryTimeBase);
        this.mGlobalWifiRunningTimer = new StopwatchTimer(this.mClock, null, -5, null, this.mOnBatteryTimeBase);
        for (int i11 = 0; i11 < 8; i11++) {
            this.mWifiStateTimer[i11] = new StopwatchTimer(this.mClock, null, (-600) - i11, null, this.mOnBatteryTimeBase);
        }
        for (int i12 = 0; i12 < 13; i12++) {
            this.mWifiSupplStateTimer[i12] = new StopwatchTimer(this.mClock, null, (-700) - i12, null, this.mOnBatteryTimeBase);
        }
        for (int i13 = 0; i13 < 5; i13++) {
            this.mWifiSignalStrengthsTimer[i13] = new StopwatchTimer(this.mClock, null, (-800) - i13, null, this.mOnBatteryTimeBase);
        }
        this.mWifiActiveTimer = new StopwatchTimer(this.mClock, null, -900, null, this.mOnBatteryTimeBase);
        int i14 = 0;
        while (true) {
            StopwatchTimer[] stopwatchTimerArr = this.mGpsSignalQualityTimer;
            if (i14 >= stopwatchTimerArr.length) {
                this.mAudioOnTimer = new StopwatchTimer(this.mClock, null, -7, null, this.mOnBatteryTimeBase);
                this.mVideoOnTimer = new StopwatchTimer(this.mClock, null, -8, null, this.mOnBatteryTimeBase);
                this.mFlashlightOnTimer = new StopwatchTimer(this.mClock, null, -9, null, this.mOnBatteryTimeBase);
                this.mCameraOnTimer = new StopwatchTimer(this.mClock, null, -13, null, this.mOnBatteryTimeBase);
                this.mBluetoothScanTimer = new StopwatchTimer(this.mClock, null, -14, null, this.mOnBatteryTimeBase);
                this.mDischargeScreenOffCounter = new LongSamplingCounter(this.mOnBatteryScreenOffTimeBase);
                this.mDischargeScreenDozeCounter = new LongSamplingCounter(this.mOnBatteryTimeBase);
                this.mDischargeLightDozeCounter = new LongSamplingCounter(this.mOnBatteryTimeBase);
                this.mDischargeDeepDozeCounter = new LongSamplingCounter(this.mOnBatteryTimeBase);
                this.mDischargeCounter = new LongSamplingCounter(this.mOnBatteryTimeBase);
                this.mDischargeUnplugLevel = 0;
                this.mDischargePlugLevel = -1;
                this.mDischargeCurrentLevel = 0;
                this.mBatteryLevel = 0;
                return;
            }
            stopwatchTimerArr[i14] = new StopwatchTimer(this.mClock, null, (-1000) - i14, null, this.mOnBatteryTimeBase);
            i14++;
        }
    }

    public final void initTimes(long j, long j2) {
        this.mStartClockTimeMs = this.mClock.currentTimeMillis();
        this.mOnBatteryTimeBase.init(j, j2);
        this.mOnBatteryScreenOffTimeBase.init(j, j2);
        this.mRealtimeUs = 0L;
        this.mUptimeUs = 0L;
        this.mRealtimeStartUs = j2;
        this.mUptimeStartUs = j;
        this.mMonotonicStartTime = this.mMonotonicClock.monotonicTime();
    }

    public final boolean isMobileRadioEnergyConsumerSupportedLocked() {
        EnergyConsumerStats energyConsumerStats = this.mGlobalEnergyConsumerStats;
        if (energyConsumerStats == null) {
            return false;
        }
        return energyConsumerStats.isStandardBucketSupported(7);
    }

    public final boolean isProcessStateDataAvailable() {
        boolean isFastCpuTimesReader;
        synchronized (this) {
            isFastCpuTimesReader = this.mCpuUidFreqTimeReader.isFastCpuTimesReader();
        }
        return isFastCpuTimesReader;
    }

    public final BatteryStatsHistoryIterator iterateBatteryStatsHistory(long j, long j2) {
        return this.mHistory.iterate(j, j2);
    }

    public final int mapUid(int i) {
        return Process.isSdkSandboxUid(i) ? Process.getAppUidForSdkSandboxUid(i) : this.mPowerStatsUidResolver.mapUid(i);
    }

    public void markPartialTimersAsEligible() {
        int i;
        if (ArrayUtils.referenceEquals(this.mPartialTimers, this.mLastPartialTimers)) {
            for (int size = this.mPartialTimers.size() - 1; size >= 0; size--) {
                ((StopwatchTimer) this.mPartialTimers.get(size)).mInList = true;
            }
            return;
        }
        int size2 = this.mLastPartialTimers.size() - 1;
        while (true) {
            if (size2 < 0) {
                break;
            }
            ((StopwatchTimer) this.mLastPartialTimers.get(size2)).mInList = false;
            size2--;
        }
        this.mLastPartialTimers.clear();
        int size3 = this.mPartialTimers.size();
        for (i = 0; i < size3; i++) {
            StopwatchTimer stopwatchTimer = (StopwatchTimer) this.mPartialTimers.get(i);
            stopwatchTimer.mInList = true;
            this.mLastPartialTimers.add(stopwatchTimer);
        }
    }

    public void maybeResetWhilePluggedInLocked() {
        long elapsedRealtime = this.mClock.elapsedRealtime();
        if (!this.mNoAutoReset && this.mSystemReady && this.mHistory.isResetEnabled()) {
            long j = this.mBatteryPluggedInRealTimeMs;
            long j2 = this.mConstants.RESET_WHILE_PLUGGED_IN_MINIMUM_DURATION_HOURS * ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
            if (elapsedRealtime >= j + j2 && elapsedRealtime >= (this.mRealtimeStartUs / 1000) + j2) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("Resetting due to long plug in duration. elapsed time = ", elapsedRealtime, " ms, last plug in time = ");
                m.append(this.mBatteryPluggedInRealTimeMs);
                m.append(" ms, last reset time = ");
                m.append(this.mRealtimeStartUs / 1000);
                Slog.i("BatteryStatsImpl", m.toString());
                resetAllStatsAndHistoryLocked(5);
            }
        }
        scheduleNextResetWhilePluggedInCheck();
    }

    public final void maybeUpdateOverallScreenBrightness(int i, int i2, long j, long j2) {
        if (this.mScreenBrightnessBin != i) {
            if (i >= 0) {
                this.mHistory.recordScreenBrightnessEvent(j, j2, i);
            }
            if (this.mScreenState == 2) {
                int i3 = this.mScreenBrightnessBin;
                if (i3 >= 0) {
                    this.mScreenBrightnessTimer[i3].stopRunningLocked(j);
                    this.mSubScreenBrightnessTimer[this.mScreenBrightnessBin].stopRunningLocked(j);
                    this.mScreenAutoBrightnessTimer[this.mScreenBrightnessBin].stopRunningLocked(j);
                    this.mSubScreenAutoBrightnessTimer[this.mScreenBrightnessBin].stopRunningLocked(j);
                }
                if (i >= 0) {
                    boolean z = i2 == 1;
                    this.mScreenBrightnessTimer[i].startRunningLocked(j);
                    if (z) {
                        this.mSubScreenBrightnessTimer[i].startRunningLocked(j);
                    }
                    if (this.mAutoBrightnessMode) {
                        this.mScreenAutoBrightnessTimer[i].startRunningLocked(j);
                        if (z) {
                            this.mSubScreenAutoBrightnessTimer[i].startRunningLocked(j);
                        }
                    }
                }
            }
            this.mScreenBrightnessBin = i;
        }
    }

    public final void noteAlarmStartOrFinishLocked(int i, String str, WorkSource workSource, int i2, long j, long j2) {
        if (this.mRecordAllHistory) {
            if (workSource == null) {
                int mapUid = mapUid(i2);
                if (this.mActiveEvents.updateState(i, str, mapUid, 0)) {
                    this.mHistory.recordEvent(j, j2, i, str, mapUid);
                    return;
                }
                return;
            }
            for (int i3 = 0; i3 < workSource.size(); i3++) {
                int mapUid2 = mapUid(workSource.getUid(i3));
                if (this.mActiveEvents.updateState(i, str, mapUid2, 0)) {
                    this.mHistory.recordEvent(j, j2, i, str, mapUid2);
                }
            }
            List workChains = workSource.getWorkChains();
            if (workChains != null) {
                for (int i4 = 0; i4 < workChains.size(); i4++) {
                    int mapUid3 = mapUid(((WorkSource.WorkChain) workChains.get(i4)).getAttributionUid());
                    if (this.mActiveEvents.updateState(i, str, mapUid3, 0)) {
                        this.mHistory.recordEvent(j, j2, i, str, mapUid3);
                    }
                }
            }
        }
    }

    public final void noteBinderThreadNativeIds(int[] iArr) {
        this.mSystemServerCpuThreadReader.mKernelCpuThreadReader.setSelectedThreadIds(iArr);
    }

    public final void noteBluetoothDutyScanStartedLocked(WorkSource.WorkChain workChain, int i, boolean z, int i2, long j, long j2) {
        if (workChain != null) {
            i = workChain.getAttributionUid();
        }
        int i3 = i;
        if (this.mBluetoothScanNesting == 0) {
            this.mHistory.setBluetoothScanState(true);
            this.mHistory.writeHistoryItem(j, j2);
            this.mBluetoothScanTimer.startRunningLocked(j);
        }
        this.mBluetoothScanNesting++;
        getUidStatsLocked(i3, j, j2).noteBluetoothScanStartedLocked(i2, j, z);
    }

    public final void noteBluetoothDutyScanStoppedLocked(WorkSource.WorkChain workChain, int i, boolean z, int i2, long j, long j2) {
        DualTimer dualTimer;
        if (workChain != null) {
            i = workChain.getAttributionUid();
        }
        int i3 = i;
        int i4 = this.mBluetoothScanNesting - 1;
        this.mBluetoothScanNesting = i4;
        if (i4 == 0) {
            this.mHistory.setBluetoothScanState(false);
            this.mHistory.writeHistoryItem(j, j2);
            this.mBluetoothScanTimer.stopRunningLocked(j);
        }
        Uid uidStatsLocked = getUidStatsLocked(i3, j, j2);
        DutyTimer dutyTimer = uidStatsLocked.mBluetoothDutyScanTimer;
        if (dutyTimer != null && dutyTimer.isRunningLocked()) {
            uidStatsLocked.mBluetoothDutyScanTimer.stopRunningLocked(j);
        }
        DualTimer dualTimer2 = uidStatsLocked.mBluetoothScanTimer;
        if (dualTimer2 != null) {
            dualTimer2.stopRunningLocked(j);
        }
        if (z && (dualTimer = uidStatsLocked.mBluetoothUnoptimizedScanTimer) != null) {
            dualTimer.stopRunningLocked(j);
        }
        if (i2 > 0) {
            uidStatsLocked.noteBluetoothScanStartedLocked(i2, j, z);
        }
    }

    public final void noteBluetoothScanStartedLocked(WorkSource.WorkChain workChain, int i, boolean z, long j, long j2) {
        int mapUid = mapUid(workChain != null ? workChain.getAttributionUid() : i);
        if (this.mBluetoothScanNesting == 0) {
            this.mHistory.recordState2StartEvent(j, j2, 1048576);
            this.mBluetoothScanTimer.startRunningLocked(j);
        }
        this.mBluetoothScanNesting++;
        Uid uidStatsLocked = getUidStatsLocked(mapUid, j, j2);
        if (uidStatsLocked.mBluetoothScanTimer == null) {
            BatteryStatsImpl batteryStatsImpl = uidStatsLocked.mBsi;
            uidStatsLocked.mBluetoothScanTimer = new DualTimer(batteryStatsImpl.mClock, uidStatsLocked, 19, batteryStatsImpl.mBluetoothScanOnTimers, batteryStatsImpl.mOnBatteryTimeBase, uidStatsLocked.mOnBatteryBackgroundTimeBase);
        }
        uidStatsLocked.mBluetoothScanTimer.startRunningLocked(j);
        if (z) {
            if (uidStatsLocked.mBluetoothUnoptimizedScanTimer == null) {
                BatteryStatsImpl batteryStatsImpl2 = uidStatsLocked.mBsi;
                uidStatsLocked.mBluetoothUnoptimizedScanTimer = new DualTimer(batteryStatsImpl2.mClock, uidStatsLocked, 21, null, batteryStatsImpl2.mOnBatteryTimeBase, uidStatsLocked.mOnBatteryBackgroundTimeBase);
            }
            uidStatsLocked.mBluetoothUnoptimizedScanTimer.startRunningLocked(j);
        }
    }

    public final void noteBluetoothScanStoppedLocked(WorkSource.WorkChain workChain, int i, boolean z, long j, long j2) {
        DualTimer dualTimer;
        if (workChain != null) {
            i = workChain.getAttributionUid();
        }
        int mapUid = mapUid(i);
        int i2 = this.mBluetoothScanNesting - 1;
        this.mBluetoothScanNesting = i2;
        if (i2 == 0) {
            this.mHistory.recordState2StopEvent(j, j2, 1048576);
            this.mBluetoothScanTimer.stopRunningLocked(j);
        }
        Uid uidStatsLocked = getUidStatsLocked(mapUid, j, j2);
        DualTimer dualTimer2 = uidStatsLocked.mBluetoothScanTimer;
        if (dualTimer2 != null) {
            dualTimer2.stopRunningLocked(j);
        }
        if (!z || (dualTimer = uidStatsLocked.mBluetoothUnoptimizedScanTimer) == null) {
            return;
        }
        dualTimer.stopRunningLocked(j);
    }

    public final void noteCameraOffLocked(int i, long j, long j2) {
        if (this.mCameraOnNesting == 0) {
            return;
        }
        int mapUid = mapUid(i);
        int i2 = this.mCameraOnNesting - 1;
        this.mCameraOnNesting = i2;
        if (i2 == 0) {
            this.mHistory.recordState2StopEvent(j, j2, 2097152, mapUid, "camera");
            this.mCameraOnTimer.stopRunningLocked(j);
        }
        StopwatchTimer stopwatchTimer = getUidStatsLocked(mapUid, j, j2).mCameraTurnedOnTimer;
        if (stopwatchTimer != null) {
            stopwatchTimer.stopRunningLocked(j);
        }
        if (this.mPowerStatsCollectorEnabled.get(3)) {
            this.mCameraPowerStatsCollector.schedule();
        } else {
            scheduleSyncExternalStatsLocked(64, "camera-off");
        }
    }

    public final void noteCameraOnLocked(int i, long j, long j2) {
        int mapUid = mapUid(i);
        int i2 = this.mCameraOnNesting;
        this.mCameraOnNesting = i2 + 1;
        if (i2 == 0) {
            this.mHistory.recordState2StartEvent(j, j2, 2097152, mapUid, "camera");
            this.mCameraOnTimer.startRunningLocked(j);
        }
        Uid uidStatsLocked = getUidStatsLocked(mapUid, j, j2);
        if (uidStatsLocked.mCameraTurnedOnTimer == null) {
            BatteryStatsImpl batteryStatsImpl = uidStatsLocked.mBsi;
            uidStatsLocked.mCameraTurnedOnTimer = new StopwatchTimer(batteryStatsImpl.mClock, uidStatsLocked, 17, batteryStatsImpl.mCameraTurnedOnTimers, batteryStatsImpl.mOnBatteryTimeBase);
        }
        uidStatsLocked.mCameraTurnedOnTimer.startRunningLocked(j);
        if (this.mPowerStatsCollectorEnabled.get(3)) {
            this.mCameraPowerStatsCollector.schedule();
        } else {
            scheduleSyncExternalStatsLocked(64, "camera-on");
        }
    }

    public final void noteChangeWakelockFromSourceLocked(WorkSource workSource, int i, String str, String str2, int i2, WorkSource workSource2, int i3, String str3, String str4, int i4, boolean z, long j, long j2) {
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList[] diffChains = WorkSource.diffChains(workSource, workSource2);
        int size = workSource2.size();
        for (int i5 = 0; i5 < size; i5++) {
            noteStartWakeLocked(workSource2.getUid(i5), i3, null, str3, str4, i4, z, j, j2);
        }
        if (diffChains != null && (arrayList2 = diffChains[0]) != null) {
            for (int i6 = 0; i6 < arrayList2.size(); i6++) {
                WorkSource.WorkChain workChain = (WorkSource.WorkChain) arrayList2.get(i6);
                noteStartWakeLocked(workChain.getAttributionUid(), i3, workChain, str3, str4, i4, z, j, j2);
            }
        }
        int size2 = workSource.size();
        for (int i7 = 0; i7 < size2; i7++) {
            noteStopWakeLocked(workSource.getUid(i7), i, null, str, str2, i2, j, j2);
        }
        if (diffChains == null || (arrayList = diffChains[1]) == null) {
            return;
        }
        for (int i8 = 0; i8 < arrayList.size(); i8++) {
            WorkSource.WorkChain workChain2 = (WorkSource.WorkChain) arrayList.get(i8);
            noteStopWakeLocked(workChain2.getAttributionUid(), i, workChain2, str, str2, i2, j, j2);
        }
    }

    public final void noteDeviceIdleModeLocked(int i, int i2, String str, long j, long j2) {
        boolean z;
        boolean z2 = i == 2;
        boolean z3 = this.mDeviceIdling;
        if (z3 && !z2 && str == null) {
            z2 = true;
        }
        boolean z4 = i == 1;
        boolean z5 = this.mDeviceLightIdling;
        boolean z6 = (!z5 || z4 || z2 || str != null) ? z4 : true;
        if (str == null || !(z3 || z5)) {
            z = z6;
        } else {
            z = z6;
            this.mHistory.recordEvent(j, j2, 10, str, i2);
        }
        if (this.mDeviceIdling != z2 || this.mDeviceLightIdling != z) {
            int i3 = z2 ? 2 : z ? 1 : 0;
            this.mFrameworkStatsLogger.getClass();
            FrameworkStatsLog.write(22, i3);
        }
        if (this.mDeviceIdling != z2) {
            this.mDeviceIdling = z2;
            int i4 = z2 ? 8 : 0;
            int i5 = this.mModStepMode;
            int i6 = this.mCurStepMode;
            this.mModStepMode = i5 | ((i6 & 8) ^ i4);
            this.mCurStepMode = i4 | (i6 & (-9));
            if (z2) {
                this.mDeviceIdlingTimer.startRunningLocked(j);
            } else {
                this.mDeviceIdlingTimer.stopRunningLocked(j);
            }
        }
        if (this.mDeviceLightIdling != z) {
            this.mDeviceLightIdling = z;
            if (z) {
                this.mDeviceLightIdlingTimer.startRunningLocked(j);
            } else {
                this.mDeviceLightIdlingTimer.stopRunningLocked(j);
            }
        }
        if (this.mDeviceIdleMode != i) {
            this.mHistory.recordDeviceIdleEvent(j, j2, i);
            long j3 = j - this.mLastIdleTimeStartMs;
            this.mLastIdleTimeStartMs = j;
            int i7 = this.mDeviceIdleMode;
            if (i7 == 1) {
                if (j3 > this.mLongestLightIdleTimeMs) {
                    this.mLongestLightIdleTimeMs = j3;
                }
                this.mDeviceIdleModeLightTimer.stopRunningLocked(j);
            } else if (i7 == 2) {
                if (j3 > this.mLongestFullIdleTimeMs) {
                    this.mLongestFullIdleTimeMs = j3;
                }
                this.mDeviceIdleModeFullTimer.stopRunningLocked(j);
            }
            if (i == 1) {
                this.mDeviceIdleModeLightTimer.startRunningLocked(j);
            } else if (i == 2) {
                this.mDeviceIdleModeFullTimer.startRunningLocked(j);
            }
            this.mDeviceIdleMode = i;
            this.mFrameworkStatsLogger.getClass();
            FrameworkStatsLog.write(21, i);
        }
    }

    public final void noteDisplayHighRefreshRateLocked(int i) {
        long elapsedRealtime = this.mClock.elapsedRealtime();
        if (i < 0 || i >= 4) {
            Slog.w("BatteryStatsImpl", "Something was wrong while updating display refresh rate");
            return;
        }
        if (Display.isOnState(this.mScreenState)) {
            int i2 = this.mHighRefreshRateBin;
            if (i2 >= 0 && this.mHighRefreshRateTimer[i2].isRunningLocked()) {
                this.mHighRefreshRateTimer[this.mHighRefreshRateBin].stopRunningLocked(elapsedRealtime);
            }
            if (!this.mHighRefreshRateTimer[i].isRunningLocked()) {
                this.mHighRefreshRateTimer[i].startRunningLocked(elapsedRealtime);
            }
        } else {
            for (int i3 = 0; i3 < 4; i3++) {
                if (i3 != -1) {
                    while (this.mHighRefreshRateTimer[i3].isRunningLocked()) {
                        this.mHighRefreshRateTimer[i3].stopRunningLocked(elapsedRealtime);
                    }
                }
            }
        }
        this.mHighRefreshRateBin = i;
    }

    public final void noteFullWifiLockAcquiredLocked(int i, long j, long j2) {
        if (this.mWifiFullLockNesting == 0) {
            this.mHistory.recordStateStartEvent(j, j2, 268435456);
        }
        this.mWifiFullLockNesting++;
        getUidStatsLocked(i, j, j2).noteFullWifiLockAcquiredLocked(j);
    }

    public final void noteFullWifiLockReleasedLocked(int i, long j, long j2) {
        int i2 = this.mWifiFullLockNesting - 1;
        this.mWifiFullLockNesting = i2;
        if (i2 == 0) {
            this.mHistory.recordStateStopEvent(j, j2, 268435456);
        }
        getUidStatsLocked(i, j, j2).noteFullWifiLockReleasedLocked(j);
    }

    public final void noteGpsChangedLocked(WorkSource workSource, WorkSource workSource2, long j, long j2) {
        for (int i = 0; i < workSource2.size(); i++) {
            noteStartGpsLocked(workSource2.getUid(i), null, j, j2);
        }
        for (int i2 = 0; i2 < workSource.size(); i2++) {
            noteStopGpsLocked(workSource.getUid(i2), null, j, j2);
        }
        ArrayList[] diffChains = WorkSource.diffChains(workSource, workSource2);
        if (diffChains != null) {
            ArrayList arrayList = diffChains[0];
            if (arrayList != null) {
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    noteStartGpsLocked(-1, (WorkSource.WorkChain) arrayList.get(i3), j, j2);
                }
            }
            ArrayList arrayList2 = diffChains[1];
            if (arrayList2 != null) {
                for (int i4 = 0; i4 < arrayList2.size(); i4++) {
                    noteStopGpsLocked(-1, (WorkSource.WorkChain) arrayList2.get(i4), j, j2);
                }
            }
        }
    }

    public final void noteGpsSignalQualityLocked(int i, long j, long j2) {
        if (this.mGpsNesting == 0) {
            return;
        }
        if (i >= 0) {
            StopwatchTimer[] stopwatchTimerArr = this.mGpsSignalQualityTimer;
            if (i < stopwatchTimerArr.length) {
                int i2 = this.mGpsSignalQualityBin;
                if (i2 != i) {
                    if (i2 >= 0) {
                        stopwatchTimerArr[i2].stopRunningLocked(j);
                    }
                    if (!this.mGpsSignalQualityTimer[i].isRunningLocked()) {
                        this.mGpsSignalQualityTimer[i].startRunningLocked(j);
                    }
                    this.mHistory.recordGpsSignalQualityEvent(j, j2, i);
                    this.mGpsSignalQualityBin = i;
                    return;
                }
                return;
            }
        }
        stopAllGpsSignalQualityTimersLocked(j);
    }

    public final void noteJobFinishLocked(int i, int i2, String str, long j, long j2) {
        String encodingPersonalInformation = encodingPersonalInformation(str);
        int mapUid = mapUid(i);
        Uid uidStatsLocked = getUidStatsLocked(mapUid, j, j2);
        DualTimer dualTimer = (DualTimer) uidStatsLocked.mJobStats.stopObject(j, encodingPersonalInformation);
        if (dualTimer != null) {
            dualTimer.stopRunningLocked(j);
        }
        if (uidStatsLocked.mBsi.mOnBatteryTimeBase.mRunning) {
            SparseIntArray sparseIntArray = (SparseIntArray) uidStatsLocked.mJobCompletions.get(encodingPersonalInformation);
            if (sparseIntArray == null) {
                sparseIntArray = new SparseIntArray();
                uidStatsLocked.mJobCompletions.put(encodingPersonalInformation, sparseIntArray);
            }
            sparseIntArray.put(i2, sparseIntArray.get(i2, 0) + 1);
        }
        if (this.mActiveEvents.updateState(16390, encodingPersonalInformation, mapUid, 0)) {
            this.mHistory.recordEvent(j, j2, 16390, encodingPersonalInformation, mapUid);
        }
    }

    public final void noteJobsDeferredLocked(int i, int i2, long j, long j2, long j3) {
        Uid uidStatsLocked = getUidStatsLocked(mapUid(i), j2, j3);
        uidStatsLocked.mJobsDeferredEventCount.addAtomic(1);
        uidStatsLocked.mJobsDeferredCount.addAtomic(i2);
        if (j == 0) {
            return;
        }
        uidStatsLocked.mJobsFreshnessTimeMs.addCountLocked(j);
        int i3 = 0;
        while (true) {
            long[] jArr = BatteryStats.JOB_FRESHNESS_BUCKETS;
            if (i3 >= jArr.length) {
                return;
            }
            if (j < jArr[i3]) {
                Counter[] counterArr = uidStatsLocked.mJobsFreshnessBuckets;
                if (counterArr[i3] == null) {
                    counterArr[i3] = new Counter(uidStatsLocked.mBsi.mOnBatteryTimeBase);
                }
                uidStatsLocked.mJobsFreshnessBuckets[i3].addAtomic(1);
                return;
            }
            i3++;
        }
    }

    public final void noteLongPartialWakeLockFinishInternal(int i, String str, String str2, long j, long j2) {
        int mapUid = mapUid(i);
        String str3 = str2 == null ? str : str2;
        if (this.mActiveEvents.updateState(16404, str3, mapUid, 0)) {
            this.mHistory.recordEvent(j, j2, 16404, encodingPersonalInformation(str3), mapUid);
            if (mapUid != i) {
                this.mPowerStatsUidResolver.releaseIsolatedUid(i);
            }
        }
    }

    public final void noteLongPartialWakeLockStartInternal(int i, String str, String str2, long j, long j2) {
        int mapUid = mapUid(i);
        String str3 = str2 == null ? str : str2;
        if (this.mActiveEvents.updateState(32788, str3, mapUid, 0)) {
            this.mHistory.recordEvent(j, j2, 32788, encodingPersonalInformation(str3), mapUid);
            if (mapUid != i) {
                this.mPowerStatsUidResolver.retainIsolatedUid(i);
            }
        }
    }

    public final void noteMobileRadioPowerStateLocked(int i, int i2, long j, long j2, long j3) {
        boolean z;
        long j4;
        long j5;
        if (this.mMobileRadioPowerState != i) {
            boolean z2 = i == 2 || i == 3;
            if (z2) {
                if (i2 > 0) {
                    int mapUid = mapUid(i2);
                    this.mHistory.recordEvent(j2, j3, 19, "", mapUid);
                    z = z2;
                    j4 = j2;
                    Uid uidStatsLocked = getUidStatsLocked(mapUid, j2, j3);
                    if (uidStatsLocked.mMobileRadioApWakeupCount == null) {
                        uidStatsLocked.mMobileRadioApWakeupCount = new LongSamplingCounter(uidStatsLocked.mBsi.mOnBatteryTimeBase);
                    }
                    uidStatsLocked.mMobileRadioApWakeupCount.addCountLocked(1L);
                } else {
                    z = z2;
                    j4 = j2;
                }
                j5 = j / 1000000;
                this.mMobileRadioActiveStartTimeMs = j5;
                this.mHistory.recordStateStartEvent(j2, j3, 33554432);
                this.mMobileActiveTimer.startRunningLocked(j4);
                if (this.mIsServiceStateNr) {
                    this.mMobileActive5GTimer.startRunningLocked(j4);
                }
            } else {
                z = z2;
                j4 = j2;
                long j6 = j / 1000000;
                long j7 = this.mMobileRadioActiveStartTimeMs;
                if (j6 < j7) {
                    StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("Data connection inactive timestamp ", j6, " is before start time ");
                    m.append(j7);
                    Slog.wtf("BatteryStatsImpl", m.toString());
                    j5 = j4;
                } else {
                    if (j6 < j4) {
                        this.mMobileRadioActiveAdjustedTime.addCountLocked(j4 - j6);
                    }
                    j5 = j6;
                }
                this.mHistory.recordStateStopEvent(j2, j3, 33554432);
                this.mMobileActiveTimer.stopRunningLocked(j4);
                this.mMobileActive5GTimer.stopAllRunningLocked(j4);
            }
            this.mMobileRadioPowerState = i;
            getRatBatteryStatsLocked(this.mActiveRat).noteActive(j4, z);
            if (z) {
                this.mMobileRadioActiveTimer.startRunningLocked(j4);
                this.mMobileRadioActivePerAppTimer.startRunningLocked(j4);
                return;
            }
            this.mMobileRadioActiveTimer.stopRunningLocked(j5);
            this.mMobileRadioActivePerAppTimer.stopRunningLocked(j5);
            MobileRadioPowerStatsCollector mobileRadioPowerStatsCollector = this.mMobileRadioPowerStatsCollector;
            if (mobileRadioPowerStatsCollector.mEnabled) {
                mobileRadioPowerStatsCollector.schedule();
                return;
            }
            ModemActivityInfo modemActivityInfo = this.mLastModemActivityInfo;
            if (modemActivityInfo == null || j4 >= modemActivityInfo.getTimestampMillis() + 600000) {
                this.mExternalSync.scheduleSync(4, "modem-data");
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void noteModemControllerActivity(android.telephony.ModemActivityInfo r45, long r46, long r48, long r50, android.app.usage.NetworkStatsManager r52) {
        /*
            Method dump skipped, instructions count: 1191
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.stats.BatteryStatsImpl.noteModemControllerActivity(android.telephony.ModemActivityInfo, long, long, long, android.app.usage.NetworkStatsManager):void");
    }

    public final void noteNetworkInterfaceForTransports(String str, int[] iArr) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        int displayTransport = BatteryStats.getDisplayTransport(iArr);
        synchronized (this.mModemNetworkLock) {
            try {
                if (displayTransport == 0) {
                    String[] strArr = this.mModemIfaces;
                    if (ArrayUtils.indexOf(strArr, str) < 0) {
                        String[] strArr2 = new String[strArr.length + 1];
                        System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
                        strArr2[strArr.length] = str;
                        strArr = strArr2;
                    }
                    this.mModemIfaces = strArr;
                } else {
                    this.mModemIfaces = excludeFromStringArray(str, this.mModemIfaces);
                }
            } finally {
            }
        }
        synchronized (this.mWifiNetworkLock) {
            try {
                if (displayTransport == 1) {
                    String[] strArr3 = this.mWifiIfaces;
                    if (ArrayUtils.indexOf(strArr3, str) < 0) {
                        String[] strArr4 = new String[strArr3.length + 1];
                        System.arraycopy(strArr3, 0, strArr4, 0, strArr3.length);
                        strArr4[strArr3.length] = str;
                        strArr3 = strArr4;
                    }
                    this.mWifiIfaces = strArr3;
                } else {
                    this.mWifiIfaces = excludeFromStringArray(str, this.mWifiIfaces);
                }
            } finally {
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void notePhoneDataConnectionStateLocked(int r16, boolean r17, int r18, int r19, int r20, long r21, long r23) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.stats.BatteryStatsImpl.notePhoneDataConnectionStateLocked(int, boolean, int, int, int, long, long):void");
    }

    public final void notePhoneOnLocked(long j, long j2) {
        if (this.mPhoneOn) {
            return;
        }
        this.mHistory.recordState2StartEvent(j, j2, 8388608);
        this.mPhoneOn = true;
        this.mPhoneOnTimer.startRunningLocked(j);
        if (this.mConstants.PHONE_ON_EXTERNAL_STATS_COLLECTION) {
            scheduleSyncExternalStatsLocked(4, "phone-on");
            this.mMobileRadioPowerStatsCollector.schedule();
        }
    }

    public final void notePhoneSignalStrengthLocked(SignalStrength signalStrength, long j, long j2) {
        int level;
        int i;
        int level2 = signalStrength.getLevel();
        SparseIntArray sparseIntArray = new SparseIntArray(3);
        List<CellSignalStrength> cellSignalStrengths = signalStrength.getCellSignalStrengths();
        int size = cellSignalStrengths.size();
        for (int i2 = 0; i2 < size; i2++) {
            CellSignalStrength cellSignalStrength = cellSignalStrengths.get(i2);
            if (cellSignalStrength instanceof CellSignalStrengthNr) {
                level = cellSignalStrength.getLevel();
                i = 2;
            } else if (cellSignalStrength instanceof CellSignalStrengthLte) {
                level = cellSignalStrength.getLevel();
                i = 1;
            } else {
                level = cellSignalStrength.getLevel();
                i = 0;
            }
            if (sparseIntArray.get(i, -1) < level) {
                sparseIntArray.put(i, level);
            }
        }
        int size2 = sparseIntArray.size();
        for (int i3 = 0; i3 < size2; i3++) {
            int keyAt = sparseIntArray.keyAt(i3);
            int valueAt = sparseIntArray.valueAt(i3);
            RadioAccessTechnologyBatteryStats ratBatteryStatsLocked = getRatBatteryStatsLocked(keyAt);
            int i4 = ratBatteryStatsLocked.mSignalStrength;
            if (i4 != valueAt) {
                if (ratBatteryStatsLocked.mActive) {
                    int i5 = ratBatteryStatsLocked.mFrequencyRange;
                    StopwatchTimer[][] stopwatchTimerArr = ratBatteryStatsLocked.perStateTimers;
                    stopwatchTimerArr[i5][i4].stopRunningLocked(j);
                    stopwatchTimerArr[ratBatteryStatsLocked.mFrequencyRange][valueAt].startRunningLocked(j);
                    ratBatteryStatsLocked.mSignalStrength = valueAt;
                } else {
                    ratBatteryStatsLocked.mSignalStrength = valueAt;
                }
            }
        }
        updateAllPhoneStateLocked(this.mPhoneServiceStateRaw, this.mPhoneSimStateRaw, level2, j, j2);
    }

    public final void notePowerSaveModeLocked(long j, long j2, boolean z) {
        if (this.mPowerSaveModeEnabled != z) {
            int i = z ? 4 : 0;
            int i2 = this.mModStepMode;
            int i3 = this.mCurStepMode;
            this.mModStepMode = i2 | ((i3 & 4) ^ i);
            this.mCurStepMode = i | (i3 & (-5));
            this.mPowerSaveModeEnabled = z;
            if (z) {
                this.mHistory.recordState2StartEvent(j, j2, Integer.MIN_VALUE);
                this.mPowerSaveModeEnabledTimer.startRunningLocked(j);
            } else {
                this.mHistory.recordState2StopEvent(j, j2, Integer.MIN_VALUE);
                this.mPowerSaveModeEnabledTimer.stopRunningLocked(j);
            }
            this.mFrameworkStatsLogger.getClass();
            FrameworkStatsLog.write(20, z ? 1 : 0);
        }
    }

    public final void noteResetBluetoothScanLocked(long j, long j2) {
        if (this.mBluetoothScanNesting > 0) {
            this.mBluetoothScanNesting = 0;
            this.mHistory.recordState2StopEvent(j, j2, 1048576);
            this.mBluetoothScanTimer.stopAllRunningLocked(j);
            for (int i = 0; i < this.mUidStats.size(); i++) {
                Uid uid = (Uid) this.mUidStats.valueAt(i);
                DutyTimer dutyTimer = uid.mBluetoothDutyScanTimer;
                if (dutyTimer != null) {
                    dutyTimer.stopRunningLocked(j);
                }
                DualTimer dualTimer = uid.mBluetoothScanTimer;
                if (dualTimer != null) {
                    dualTimer.stopAllRunningLocked(j);
                }
                DualTimer dualTimer2 = uid.mBluetoothUnoptimizedScanTimer;
                if (dualTimer2 != null) {
                    dualTimer2.stopAllRunningLocked(j);
                }
            }
        }
    }

    public final void noteResetCameraLocked(long j, long j2) {
        if (this.mCameraOnNesting > 0) {
            this.mCameraOnNesting = 0;
            this.mHistory.recordState2StopEvent(j, j2, 2097152);
            this.mCameraOnTimer.stopAllRunningLocked(j);
            for (int i = 0; i < this.mUidStats.size(); i++) {
                StopwatchTimer stopwatchTimer = ((Uid) this.mUidStats.valueAt(i)).mCameraTurnedOnTimer;
                if (stopwatchTimer != null) {
                    stopwatchTimer.stopAllRunningLocked(j);
                }
            }
        }
        scheduleSyncExternalStatsLocked(64, "camera-reset");
    }

    public final void noteScreenAutoBrightnessLocked(int i) {
        long elapsedRealtime = this.mClock.elapsedRealtime();
        boolean z = i == 1;
        if (this.mAutoBrightnessMode != z) {
            this.mAutoBrightnessMode = z;
            int i2 = this.mScreenBrightnessBin;
            if (i2 < 0 || i2 >= 5) {
                return;
            }
            if (z && Display.isOnState(this.mScreenState)) {
                this.mScreenAutoBrightnessTimer[this.mScreenBrightnessBin].startRunningLocked(elapsedRealtime);
            } else {
                this.mScreenAutoBrightnessTimer[this.mScreenBrightnessBin].stopRunningLocked(elapsedRealtime);
            }
            if (z && this.mIsSubScreen && Display.isOnState(this.mSubScreenState)) {
                this.mSubScreenAutoBrightnessTimer[this.mScreenBrightnessBin].startRunningLocked(elapsedRealtime);
            } else {
                this.mSubScreenAutoBrightnessTimer[this.mScreenBrightnessBin].stopRunningLocked(elapsedRealtime);
            }
        }
    }

    public final void noteScreenBrightnessLocked(int i, int i2, long j, long j2) {
        int evaluateOverallScreenBrightnessBinLocked;
        boolean z = i2 == 1;
        int i3 = (!z || this.mPerDisplayBatteryStats.length <= 1) ? 0 : 1;
        if (i > 255) {
            this.mScreenHighBrightness = true;
            if (this.mScreenState == 2 && !this.mScreenHighBrightnessTimer.isRunningLocked()) {
                this.mScreenHighBrightnessTimer.startRunningLocked(j);
            }
            if (z && !this.mSubScreenHighBrightnessTimer.isRunningLocked()) {
                this.mSubScreenHighBrightnessTimer.startRunningLocked(j);
            }
        } else {
            this.mScreenHighBrightness = false;
            if (this.mScreenHighBrightnessTimer.isRunningLocked()) {
                this.mScreenHighBrightnessTimer.stopRunningLocked(j);
            }
            if (this.mSubScreenHighBrightnessTimer.isRunningLocked()) {
                this.mSubScreenHighBrightnessTimer.stopRunningLocked(j);
            }
        }
        int i4 = i / 51;
        int i5 = i4 >= 0 ? i4 >= 5 ? 4 : i4 : 0;
        DisplayBatteryStats[] displayBatteryStatsArr = this.mPerDisplayBatteryStats;
        int length = displayBatteryStatsArr.length;
        if (i3 < 0 || i3 >= length) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i3, "Unexpected note screen brightness for display ", " (only ");
            m.append(this.mPerDisplayBatteryStats.length);
            m.append(" displays exist...)");
            Slog.wtf("BatteryStatsImpl", m.toString());
            return;
        }
        DisplayBatteryStats displayBatteryStats = displayBatteryStatsArr[i3];
        int i6 = displayBatteryStats.screenBrightnessBin;
        if (i6 == i5) {
            evaluateOverallScreenBrightnessBinLocked = this.mScreenBrightnessBin;
        } else {
            displayBatteryStats.screenBrightnessBin = i5;
            if (displayBatteryStats.screenState == 2) {
                StopwatchTimer[] stopwatchTimerArr = displayBatteryStats.screenBrightnessTimers;
                if (i6 >= 0) {
                    stopwatchTimerArr[i6].stopRunningLocked(j);
                }
                stopwatchTimerArr[i5].startRunningLocked(j);
            }
            evaluateOverallScreenBrightnessBinLocked = evaluateOverallScreenBrightnessBinLocked();
        }
        maybeUpdateOverallScreenBrightness(evaluateOverallScreenBrightnessBinLocked, i2, j, j2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:193:0x0366  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x01ac  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x035e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void noteScreenStateLocked(int r27, int r28, long r29, long r31, long r33) {
        /*
            Method dump skipped, instructions count: 1389
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.stats.BatteryStatsImpl.noteScreenStateLocked(int, int, long, long, long):void");
    }

    public final void noteStartGpsLocked(int i, WorkSource.WorkChain workChain, long j, long j2) {
        if (workChain != null) {
            i = workChain.getAttributionUid();
        }
        int mapUid = mapUid(i);
        if (this.mGpsNesting == 0) {
            this.mHistory.recordStateStartEvent(j, j2, 536870912, i, "gnss");
            if (this.mPowerStatsCollectorEnabled.get(10)) {
                this.mGnssPowerStatsCollector.schedule();
            }
            if (this.mScreenOnTimer.isRunningLocked()) {
                this.mScreenOnGpsTimer.startRunningLocked(j);
            }
        }
        this.mGpsNesting++;
        FrameworkStatsLogger frameworkStatsLogger = this.mFrameworkStatsLogger;
        int mapUid2 = this.mPowerStatsUidResolver.mapUid(i);
        frameworkStatsLogger.getClass();
        if (workChain != null) {
            FrameworkStatsLog.write(6, workChain.getUids(), workChain.getTags(), 1);
        } else {
            FrameworkStatsLog.write_non_chained(6, mapUid2, null, 1);
        }
        getUidStatsLocked(mapUid, j, j2).getSensorTimerLocked(-10000, true).startRunningLocked(j);
    }

    public final void noteStartWakeLocked(int i, int i2, WorkSource.WorkChain workChain, String str, String str2, int i3, boolean z, long j, long j2) {
        int mapUid = mapUid(i);
        if (i3 == 0) {
            String str3 = str2 == null ? str : str2;
            if (this.mRecordAllHistory && this.mActiveEvents.updateState(32773, str3, mapUid, 0)) {
                this.mHistory.recordEvent(j, j2, 32773, str3, mapUid);
            }
            if (this.mWakeLockNesting == 0) {
                this.mWakeLockImportant = !z;
                this.mHistory.recordWakelockStartEvent(j, j2, str3, mapUid);
            } else if (!this.mWakeLockImportant && !z && this.mHistory.maybeUpdateWakelockTag(j, j2, str3, mapUid)) {
                this.mWakeLockImportant = true;
            }
            this.mWakeLockNesting++;
        }
        if (mapUid >= 0) {
            if (mapUid != i) {
                this.mPowerStatsUidResolver.retainIsolatedUid(i);
            }
            if (this.mOnBatteryScreenOffTimeBase.mRunning) {
                this.mExternalSync.scheduleCpuSyncDueToWakelockChange(60000L);
            }
            Uid uidStatsLocked = getUidStatsLocked(mapUid, j, j2);
            uidStatsLocked.getWakelockTimerLocked((Uid.Wakelock) uidStatsLocked.mWakelockStats.startObject(j, encodingPersonalInformation(str)), i3).startRunningLocked(j);
            if (i3 == 0) {
                if (uidStatsLocked.mAggregatedPartialWakelockTimer == null) {
                    BatteryStatsImpl batteryStatsImpl = uidStatsLocked.mBsi;
                    uidStatsLocked.mAggregatedPartialWakelockTimer = new DualTimer(batteryStatsImpl.mClock, uidStatsLocked, 20, null, batteryStatsImpl.mOnBatteryScreenOffTimeBase, uidStatsLocked.mOnBatteryScreenOffBackgroundTimeBase);
                }
                uidStatsLocked.mAggregatedPartialWakelockTimer.startRunningLocked(j);
                if (i2 >= 0) {
                    BatteryStats.Uid.Pid pid = (BatteryStats.Uid.Pid) uidStatsLocked.mPids.get(i2);
                    if (pid == null) {
                        pid = new BatteryStats.Uid.Pid(uidStatsLocked);
                        uidStatsLocked.mPids.put(i2, pid);
                    }
                    int i4 = pid.mWakeNesting;
                    pid.mWakeNesting = i4 + 1;
                    if (i4 == 0) {
                        pid.mWakeStartMs = j;
                    }
                }
            }
            FrameworkStatsLogger frameworkStatsLogger = this.mFrameworkStatsLogger;
            int mapUid2 = this.mPowerStatsUidResolver.mapUid(i);
            int i5 = uidStatsLocked.mProcessState;
            int powerManagerWakeLockLevel = getPowerManagerWakeLockLevel(i3);
            frameworkStatsLogger.getClass();
            if (workChain != null) {
                FrameworkStatsLog.write(10, workChain.getUids(), workChain.getTags(), powerManagerWakeLockLevel, str, 1, i5);
            } else {
                FrameworkStatsLog.write_non_chained(10, mapUid2, null, powerManagerWakeLockLevel, str, 1, i5);
            }
        }
    }

    public final void noteStopGpsLocked(int i, WorkSource.WorkChain workChain, long j, long j2) {
        int attributionUid = workChain != null ? workChain.getAttributionUid() : i;
        int mapUid = mapUid(attributionUid);
        int i2 = this.mGpsNesting - 1;
        this.mGpsNesting = i2;
        if (i2 == 0) {
            this.mHistory.recordStateStopEvent(j, j2, 536870912, attributionUid, "gnss");
            this.mHistory.recordGpsSignalQualityEvent(j, j2, 2);
            stopAllGpsSignalQualityTimersLocked(j);
            this.mGpsSignalQualityBin = -1;
            if (this.mPowerStatsCollectorEnabled.get(10)) {
                this.mGnssPowerStatsCollector.schedule();
            }
            this.mScreenOnGpsTimer.stopRunningLocked(j);
        }
        FrameworkStatsLogger frameworkStatsLogger = this.mFrameworkStatsLogger;
        int mapUid2 = this.mPowerStatsUidResolver.mapUid(attributionUid);
        frameworkStatsLogger.getClass();
        if (workChain != null) {
            FrameworkStatsLog.write(6, workChain.getUids(), workChain.getTags(), 0);
        } else {
            FrameworkStatsLog.write_non_chained(6, mapUid2, null, 0);
        }
        DualTimer sensorTimerLocked = getUidStatsLocked(mapUid, j, j2).getSensorTimerLocked(-10000, false);
        if (sensorTimerLocked != null) {
            sensorTimerLocked.stopRunningLocked(j);
        }
    }

    public final void noteStopTxPowerSharingLocked() {
        long elapsedRealtime = this.mClock.elapsedRealtime();
        this.mClock.uptimeMillis();
        if (this.mTxPowerSharingOn) {
            this.mTxPowerSharingOn = false;
            this.mTxPowerSharingTimer.stopRunningLocked(elapsedRealtime);
            this.mTxPowerSharingDischargeMah += getIntFromFile("/sys/class/power_supply/battery/wc_tx_total_pwr") >= 0 ? r0 : 0;
        }
    }

    public final void noteStopWakeLocked(int i, int i2, WorkSource.WorkChain workChain, String str, String str2, int i3, long j, long j2) {
        BatteryStats.Uid.Pid pid;
        int i4;
        int mapUid = mapUid(i);
        if (i3 == 0) {
            this.mWakeLockNesting--;
            String str3 = str2 == null ? str : str2;
            if (this.mRecordAllHistory && this.mActiveEvents.updateState(16389, str3, mapUid, 0)) {
                this.mHistory.recordEvent(j, j2, 16389, str3, mapUid);
            }
            if (this.mWakeLockNesting == 0) {
                this.mHistory.recordWakelockStopEvent(j, j2, str3, mapUid);
            }
        }
        if (mapUid >= 0) {
            if (this.mOnBatteryScreenOffTimeBase.mRunning) {
                this.mExternalSync.scheduleCpuSyncDueToWakelockChange(60000L);
            }
            Uid uidStatsLocked = getUidStatsLocked(mapUid, j, j2);
            Uid.Wakelock wakelock = (Uid.Wakelock) uidStatsLocked.mWakelockStats.stopObject(j, encodingPersonalInformation(str));
            if (wakelock != null) {
                uidStatsLocked.getWakelockTimerLocked(wakelock, i3).stopRunningLocked(j);
            }
            if (i3 == 0) {
                DualTimer dualTimer = uidStatsLocked.mAggregatedPartialWakelockTimer;
                if (dualTimer != null) {
                    dualTimer.stopRunningLocked(j);
                }
                if (i2 >= 0 && (pid = (BatteryStats.Uid.Pid) uidStatsLocked.mPids.get(i2)) != null && (i4 = pid.mWakeNesting) > 0) {
                    pid.mWakeNesting = i4 - 1;
                    if (i4 == 1) {
                        pid.mWakeSumMs = (j - pid.mWakeStartMs) + pid.mWakeSumMs;
                        pid.mWakeStartMs = 0L;
                    }
                }
            }
            FrameworkStatsLogger frameworkStatsLogger = this.mFrameworkStatsLogger;
            int mapUid2 = this.mPowerStatsUidResolver.mapUid(i);
            int i5 = uidStatsLocked.mProcessState;
            int powerManagerWakeLockLevel = getPowerManagerWakeLockLevel(i3);
            frameworkStatsLogger.getClass();
            if (workChain != null) {
                FrameworkStatsLog.write(10, workChain.getUids(), workChain.getTags(), powerManagerWakeLockLevel, str, 0, i5);
            } else {
                FrameworkStatsLog.write_non_chained(10, mapUid2, null, powerManagerWakeLockLevel, str, 0, i5);
            }
            if (mapUid != i) {
                this.mPowerStatsUidResolver.releaseIsolatedUid(i);
            }
        }
    }

    public final void noteSubDisplayHighRefreshRateLocked(int i, int i2) {
        long elapsedRealtime = this.mClock.elapsedRealtime();
        if (i2 < 0 || i2 >= 4) {
            Slog.w("BatteryStatsImpl", "Something was wrong while updateing sub-display refresh rate");
            return;
        }
        if (!this.mIsSubScreen) {
            stopAllSubDisplayHighRefreshRateTimersLocked(elapsedRealtime);
        } else {
            if (!Display.isOnState(i)) {
                stopAllSubDisplayHighRefreshRateTimersLocked(elapsedRealtime);
                return;
            }
            if (this.mSubHighRefreshRateTimer[this.mHighRefreshRateBin].isRunningLocked()) {
                this.mSubHighRefreshRateTimer[this.mHighRefreshRateBin].stopRunningLocked(elapsedRealtime);
            }
            this.mSubHighRefreshRateTimer[i2].startRunningLocked(elapsedRealtime);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void noteUidProcessStateLocked(int i, int i2, long j, long j2) {
        boolean z;
        int mapUid = mapUid(i);
        if (i == mapUid || !Process.isIsolated(i)) {
            this.mFrameworkStatsLogger.getClass();
            FrameworkStatsLog.write(27, i, ActivityManager.processStateAmToProto(i2));
            Uid uidStatsLocked = getUidStatsLocked(mapUid, j, j2);
            boolean isForegroundService = ActivityManager.isForegroundService(i2);
            int mapToInternalProcessState = BatteryStats.mapToInternalProcessState(i2);
            int i3 = uidStatsLocked.mProcessState;
            if (i3 == mapToInternalProcessState && isForegroundService == uidStatsLocked.mInForegroundService) {
                return;
            }
            if (i3 != mapToInternalProcessState) {
                BatteryStatsImpl batteryStatsImpl = uidStatsLocked.mBsi;
                int i4 = batteryStatsImpl.mPerDisplayBatteryStats.length <= 1 ? 0 : batteryStatsImpl.mIsSubScreen;
                if (i3 != 7) {
                    uidStatsLocked.mProcessStateTimer[i3].stopRunningLocked(j);
                    StopwatchTimer stopwatchTimer = uidStatsLocked.mPerDisplayTopActivityTimer[i4];
                    if (stopwatchTimer != null) {
                        stopwatchTimer.stopRunningLocked(j);
                        uidStatsLocked.mBsi.mTopAppStats.remove(Integer.valueOf(uidStatsLocked.mUid));
                    }
                }
                if (mapToInternalProcessState != 7) {
                    if (uidStatsLocked.mProcessStateTimer[mapToInternalProcessState] == null) {
                        uidStatsLocked.makeProcessState(mapToInternalProcessState);
                    }
                    uidStatsLocked.mProcessStateTimer[mapToInternalProcessState].startRunningLocked(j);
                    if (mapToInternalProcessState == 0) {
                        if (uidStatsLocked.mPerDisplayTopActivityTimer[i4] == null) {
                            uidStatsLocked.makeDisplayTopActivityTimer(i4);
                        }
                        uidStatsLocked.mPerDisplayTopActivityTimer[i4].startRunningLocked(j);
                        uidStatsLocked.mBsi.mTopAppStats.put(Integer.valueOf(uidStatsLocked.mUid), Integer.valueOf(i4));
                    }
                }
                if (!uidStatsLocked.mBsi.mPowerStatsCollectorEnabled.get(1) && uidStatsLocked.mBsi.mCpuUidFreqTimeReader.isFastCpuTimesReader()) {
                    uidStatsLocked.mBsi.updateProcStateCpuTimesLocked(uidStatsLocked.mUid, j, j2);
                    uidStatsLocked.ensureMultiStateCounters(j);
                    LongArrayMultiStateCounter longArrayMultiStateCounter = uidStatsLocked.mProcStateTimeMs.mCounter;
                    uidStatsLocked.ensureMultiStateCounters(j);
                    LongArrayMultiStateCounter longArrayMultiStateCounter2 = uidStatsLocked.mProcStateScreenOffTimeMs.mCounter;
                    longArrayMultiStateCounter.setState(mapToInternalProcessState, j);
                    longArrayMultiStateCounter2.setState(mapToInternalProcessState, j);
                }
                int mapUidProcessStateToBatteryConsumerProcessState = BatteryStats.mapUidProcessStateToBatteryConsumerProcessState(uidStatsLocked.mProcessState);
                uidStatsLocked.mProcessState = mapToInternalProcessState;
                long j3 = j2 * 1000;
                long j4 = 1000 * j;
                uidStatsLocked.mOnBatteryBackgroundTimeBase.setRunning(j3, j4, uidStatsLocked.mBsi.mOnBatteryTimeBase.mRunning && mapToInternalProcessState >= 3);
                uidStatsLocked.mOnBatteryScreenOffBackgroundTimeBase.setRunning(j3, j4, uidStatsLocked.mBsi.mOnBatteryScreenOffTimeBase.mRunning && uidStatsLocked.mProcessState >= 3);
                int mapUidProcessStateToBatteryConsumerProcessState2 = BatteryStats.mapUidProcessStateToBatteryConsumerProcessState(mapToInternalProcessState);
                BatteryStatsImpl batteryStatsImpl2 = uidStatsLocked.mBsi;
                if (batteryStatsImpl2.mSystemReady && batteryStatsImpl2.mPowerStatsCollectorEnabled.get(1)) {
                    BatteryStatsImpl batteryStatsImpl3 = uidStatsLocked.mBsi;
                    if (!batteryStatsImpl3.mIsBuildTypeUser) {
                        batteryStatsImpl3.mHistory.recordProcessStateChange(j, j2, uidStatsLocked.mUid, mapUidProcessStateToBatteryConsumerProcessState2);
                    }
                }
                TimeMultiStateCounter.m842$$Nest$msetState(uidStatsLocked.getCpuActiveTimeCounter(), mapUidProcessStateToBatteryConsumerProcessState2, j);
                TimeMultiStateCounter.m842$$Nest$msetState(uidStatsLocked.getMobileRadioActiveTimeCounter(), mapUidProcessStateToBatteryConsumerProcessState2, j);
                ControllerActivityCounterImpl controllerActivityCounterImpl = uidStatsLocked.mWifiControllerActivity;
                if (controllerActivityCounterImpl != null) {
                    ControllerActivityCounterImpl.m840$$Nest$msetState(controllerActivityCounterImpl, mapUidProcessStateToBatteryConsumerProcessState2, j);
                }
                ControllerActivityCounterImpl controllerActivityCounterImpl2 = uidStatsLocked.mBluetoothControllerActivity;
                if (controllerActivityCounterImpl2 != null) {
                    ControllerActivityCounterImpl.m840$$Nest$msetState(controllerActivityCounterImpl2, mapUidProcessStateToBatteryConsumerProcessState2, j);
                }
                if (uidStatsLocked.mUidEnergyConsumerStats == null && uidStatsLocked.mBsi.mEnergyConsumerStatsConfig != null) {
                    uidStatsLocked.mUidEnergyConsumerStats = new EnergyConsumerStats(uidStatsLocked.mBsi.mEnergyConsumerStatsConfig);
                }
                EnergyConsumerStats energyConsumerStats = uidStatsLocked.mUidEnergyConsumerStats;
                if (energyConsumerStats != null) {
                    energyConsumerStats.setState(mapUidProcessStateToBatteryConsumerProcessState2, j);
                }
                if (mapUidProcessStateToBatteryConsumerProcessState != mapUidProcessStateToBatteryConsumerProcessState2 && ((mapUidProcessStateToBatteryConsumerProcessState != 0 || mapUidProcessStateToBatteryConsumerProcessState2 != 2) && (mapUidProcessStateToBatteryConsumerProcessState != 2 || mapUidProcessStateToBatteryConsumerProcessState2 != 0))) {
                    BatteryStatsImpl batteryStatsImpl4 = uidStatsLocked.mBsi;
                    int i5 = batteryStatsImpl4.mMobileRadioPowerState;
                    int i6 = (i5 == 2 || i5 == 3) ? 14 : 10;
                    BatteryExternalStatsWorker batteryExternalStatsWorker = batteryStatsImpl4.mExternalSync;
                    long j5 = batteryStatsImpl4.mConstants.PROC_STATE_CHANGE_COLLECTION_DELAY_MS;
                    synchronized (batteryExternalStatsWorker) {
                        batteryExternalStatsWorker.mProcessStateSync = batteryExternalStatsWorker.scheduleDelayedSyncLocked(batteryExternalStatsWorker.mProcessStateSync, new BatteryExternalStatsWorker$$ExternalSyntheticLambda3(batteryExternalStatsWorker, i6, 2), j5);
                    }
                }
            }
            if (isForegroundService != uidStatsLocked.mInForegroundService) {
                if (isForegroundService) {
                    if (uidStatsLocked.mForegroundServiceTimer == null) {
                        BatteryStatsImpl batteryStatsImpl5 = uidStatsLocked.mBsi;
                        z = isForegroundService;
                        uidStatsLocked.mForegroundServiceTimer = new StopwatchTimer(batteryStatsImpl5.mClock, uidStatsLocked, 22, null, batteryStatsImpl5.mOnBatteryTimeBase);
                    } else {
                        z = isForegroundService;
                    }
                    uidStatsLocked.mForegroundServiceTimer.startRunningLocked(j);
                } else {
                    z = isForegroundService;
                    StopwatchTimer stopwatchTimer2 = uidStatsLocked.mForegroundServiceTimer;
                    if (stopwatchTimer2 != null) {
                        stopwatchTimer2.stopRunningLocked(j);
                    }
                }
                uidStatsLocked.mInForegroundService = z;
            }
        }
    }

    public final void noteUsbConnectionStateLocked(long j, long j2, boolean z) {
        int i = z ? 2 : 1;
        if (this.mUsbDataState != i) {
            this.mUsbDataState = i;
            if (z) {
                this.mHistory.recordState2StartEvent(j, j2, 262144);
            } else {
                this.mHistory.recordState2StopEvent(j, j2, 262144);
            }
        }
    }

    public final void noteUserActivityLocked(int i, int i2, long j, long j2) {
        if (this.mOnBatteryInternal) {
            getUidStatsLocked(mapUid(i), j, j2).noteUserActivityLocked(i2);
        }
    }

    public final void noteWakeupReasonLocked(long j, long j2, String str) {
        String str2 = this.mLastWakeupReason;
        if (str2 != null) {
            long j3 = j2 - this.mLastWakeupUptimeMs;
            SamplingTimer samplingTimer = (SamplingTimer) this.mWakeupReasonStats.get(str2);
            if (samplingTimer == null) {
                samplingTimer = new SamplingTimer(this.mClock, this.mOnBatteryTimeBase);
                this.mWakeupReasonStats.put(str2, samplingTimer);
            }
            SamplingTimer samplingTimer2 = samplingTimer;
            long j4 = j3 * 1000;
            samplingTimer2.update(samplingTimer2.mCurrentReportedTotalTimeUs + j4, 0L, samplingTimer2.mCurrentReportedCount + 1, j);
            FrameworkStatsLogger frameworkStatsLogger = this.mFrameworkStatsLogger;
            String str3 = this.mLastWakeupReason;
            long j5 = this.mLastWakeupElapsedTimeMs;
            frameworkStatsLogger.getClass();
            FrameworkStatsLog.write(36, str3, j4, j5);
        }
        this.mHistory.recordWakeupEvent(j, j2, str);
        this.mLastWakeupReason = str;
        this.mLastWakeupUptimeMs = j2;
        this.mLastWakeupElapsedTimeMs = j;
    }

    public final void noteWakupAlarmLocked(String str, int i, WorkSource workSource, String str2, long j, long j2) {
        if (workSource == null) {
            if (this.mOnBattery) {
                getUidStatsLocked(mapUid(i), j, j2).getPackageStatsLocked(str).noteWakeupAlarmLocked(str2);
                return;
            }
            return;
        }
        for (int i2 = 0; i2 < workSource.size(); i2++) {
            int uid = workSource.getUid(i2);
            String packageName = workSource.getPackageName(i2);
            if (this.mOnBattery) {
                getUidStatsLocked(mapUid(uid), j, j2).getPackageStatsLocked(packageName != null ? packageName : str).noteWakeupAlarmLocked(str2);
            }
        }
        List workChains = workSource.getWorkChains();
        if (workChains != null) {
            for (int i3 = 0; i3 < workChains.size(); i3++) {
                int attributionUid = ((WorkSource.WorkChain) workChains.get(i3)).getAttributionUid();
                if (this.mOnBattery) {
                    getUidStatsLocked(mapUid(attributionUid), j, j2).getPackageStatsLocked(str).noteWakeupAlarmLocked(str2);
                }
            }
        }
    }

    public final void noteWifiRadioPowerState(int i, int i2, long j, long j2, long j3) {
        if (this.mWifiRadioPowerState != i) {
            if (i == 2 || i == 3) {
                if (i2 > 0) {
                    int mapUid = mapUid(i2);
                    this.mHistory.recordEvent(j2, j3, 19, "", mapUid);
                    Uid uidStatsLocked = getUidStatsLocked(mapUid, j2, j3);
                    if (uidStatsLocked.mWifiRadioApWakeupCount == null) {
                        uidStatsLocked.mWifiRadioApWakeupCount = new LongSamplingCounter(uidStatsLocked.mBsi.mOnBatteryTimeBase);
                    }
                    uidStatsLocked.mWifiRadioApWakeupCount.addCountLocked(1L);
                }
                this.mHistory.recordStateStartEvent(j2, j3, 67108864);
                this.mWifiActiveTimer.startRunningLocked(j2);
            } else {
                this.mHistory.recordStateStopEvent(j2, j3, 67108864);
                this.mWifiActiveTimer.stopRunningLocked(j / 1000000);
            }
            this.mWifiRadioPowerState = i;
        }
    }

    public final void noteWifiRssiChangedLocked(int i, long j, long j2) {
        int calculateSignalLevel = WifiManager.calculateSignalLevel(i, 5);
        int i2 = this.mWifiSignalStrengthBin;
        if (i2 != calculateSignalLevel) {
            if (i2 >= 0) {
                this.mWifiSignalStrengthsTimer[i2].stopRunningLocked(j);
            }
            if (calculateSignalLevel >= 0) {
                if (!this.mWifiSignalStrengthsTimer[calculateSignalLevel].isRunningLocked()) {
                    this.mWifiSignalStrengthsTimer[calculateSignalLevel].startRunningLocked(j);
                }
                this.mHistory.recordWifiSignalStrengthChangeEvent(j, j2, calculateSignalLevel);
            } else {
                for (int i3 = 0; i3 < 5; i3++) {
                    if (i3 != -1) {
                        while (this.mWifiSignalStrengthsTimer[i3].isRunningLocked()) {
                            this.mWifiSignalStrengthsTimer[i3].stopRunningLocked(j);
                        }
                    }
                }
            }
            this.mWifiSignalStrengthBin = calculateSignalLevel;
        }
    }

    public final void noteWifiRunningChangedLocked(WorkSource workSource, WorkSource workSource2, long j, long j2) {
        if (!this.mGlobalWifiRunning) {
            Log.w("BatteryStatsImpl", "noteWifiRunningChangedLocked -- called while WIFI not running");
            return;
        }
        int size = workSource.size();
        for (int i = 0; i < size; i++) {
            getUidStatsLocked(mapUid(workSource.getUid(i)), j, j2).noteWifiStoppedLocked(j);
        }
        List workChains = workSource.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                getUidStatsLocked(mapUid(((WorkSource.WorkChain) workChains.get(i2)).getAttributionUid()), j, j2).noteWifiStoppedLocked(j);
            }
        }
        int size2 = workSource2.size();
        for (int i3 = 0; i3 < size2; i3++) {
            getUidStatsLocked(mapUid(workSource2.getUid(i3)), j, j2).noteWifiRunningLocked(j);
        }
        List workChains2 = workSource2.getWorkChains();
        if (workChains2 != null) {
            for (int i4 = 0; i4 < workChains2.size(); i4++) {
                getUidStatsLocked(mapUid(((WorkSource.WorkChain) workChains2.get(i4)).getAttributionUid()), j, j2).noteWifiRunningLocked(j);
            }
        }
    }

    public final void noteWifiRunningLocked(WorkSource workSource, long j, long j2) {
        if (this.mGlobalWifiRunning) {
            Log.w("BatteryStatsImpl", "noteWifiRunningLocked -- called while WIFI running");
            return;
        }
        this.mHistory.recordState2StartEvent(j, j2, 536870912);
        this.mGlobalWifiRunning = true;
        this.mGlobalWifiRunningTimer.startRunningLocked(j);
        int size = workSource.size();
        for (int i = 0; i < size; i++) {
            getUidStatsLocked(mapUid(workSource.getUid(i)), j, j2).noteWifiRunningLocked(j);
        }
        List workChains = workSource.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                getUidStatsLocked(mapUid(((WorkSource.WorkChain) workChains.get(i2)).getAttributionUid()), j, j2).noteWifiRunningLocked(j);
            }
        }
        WifiPowerStatsCollector wifiPowerStatsCollector = this.mWifiPowerStatsCollector;
        if (wifiPowerStatsCollector.mEnabled) {
            wifiPowerStatsCollector.schedule();
        } else {
            scheduleSyncExternalStatsLocked(2, "wifi-running");
        }
    }

    public final void noteWifiScanStartedLocked(int i, long j, long j2) {
        if (this.mWifiScanNesting == 0) {
            this.mHistory.recordStateStartEvent(j, j2, 134217728);
        }
        this.mWifiScanNesting++;
        getUidStatsLocked(i, j, j2).noteWifiScanStartedLocked(j);
    }

    public final void noteWifiScanStoppedLocked(int i, long j, long j2) {
        int i2 = this.mWifiScanNesting - 1;
        this.mWifiScanNesting = i2;
        if (i2 == 0) {
            this.mHistory.recordStateStopEvent(j, j2, 134217728);
        }
        getUidStatsLocked(i, j, j2).noteWifiScanStoppedLocked(j);
    }

    public final void noteWifiStoppedLocked(WorkSource workSource, long j, long j2) {
        if (!this.mGlobalWifiRunning) {
            Log.w("BatteryStatsImpl", "noteWifiStoppedLocked -- called while WIFI not running");
            return;
        }
        this.mHistory.recordState2StopEvent(j, j2, 536870912);
        this.mGlobalWifiRunning = false;
        this.mGlobalWifiRunningTimer.stopRunningLocked(j);
        int size = workSource.size();
        for (int i = 0; i < size; i++) {
            getUidStatsLocked(mapUid(workSource.getUid(i)), j, j2).noteWifiStoppedLocked(j);
        }
        List workChains = workSource.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                getUidStatsLocked(mapUid(((WorkSource.WorkChain) workChains.get(i2)).getAttributionUid()), j, j2).noteWifiStoppedLocked(j);
            }
        }
        WifiPowerStatsCollector wifiPowerStatsCollector = this.mWifiPowerStatsCollector;
        if (wifiPowerStatsCollector.mEnabled) {
            wifiPowerStatsCollector.schedule();
        } else {
            scheduleSyncExternalStatsLocked(2, "wifi-stopped");
        }
    }

    public final void notifyNetworkStatsUpdatedCallbacksLocked(final List list) {
        int beginBroadcast = this.mBatteryStatsCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                final IBatteryStatsCallback broadcastItem = this.mBatteryStatsCallbacks.getBroadcastItem(i);
                final long elapsedRealtime = this.mClock.elapsedRealtime();
                if (!this.mHandler.post(new Runnable() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
                        List<BatteryStatsImpl.NetworkStatsDelta> list2 = list;
                        long j = elapsedRealtime;
                        IBatteryStatsCallback iBatteryStatsCallback = broadcastItem;
                        batteryStatsImpl.getClass();
                        if (list2 != null) {
                            try {
                                for (BatteryStatsImpl.NetworkStatsDelta networkStatsDelta : list2) {
                                    if (networkStatsDelta.mRxPackets != 0 || networkStatsDelta.mTxPackets != 0) {
                                        int i2 = networkStatsDelta.mUid;
                                        if (!batteryStatsImpl.mNetworkStatsDeltaMap.containsKey(Integer.valueOf(i2))) {
                                            batteryStatsImpl.mNetworkStatsDeltaMap.put(Integer.valueOf(i2), new SemSimpleNetworkStats(networkStatsDelta.mUid));
                                        }
                                        SemSimpleNetworkStats semSimpleNetworkStats = (SemSimpleNetworkStats) batteryStatsImpl.mNetworkStatsDeltaMap.get(Integer.valueOf(i2));
                                        semSimpleNetworkStats.add(new SemSimpleNetworkStats(i2, networkStatsDelta.mTxBytes, networkStatsDelta.mRxBytes));
                                        batteryStatsImpl.mNetworkStatsDeltaMap.put(Integer.valueOf(i2), semSimpleNetworkStats);
                                    }
                                }
                            } catch (RemoteException | RuntimeException unused) {
                                Slog.e("BatteryStatsImpl", "Callback failed to call");
                                return;
                            }
                        }
                        if (j - batteryStatsImpl.mLastCallbackTime > 60000) {
                            ArrayList arrayList = new ArrayList();
                            for (Integer num : batteryStatsImpl.mNetworkStatsDeltaMap.keySet()) {
                                num.intValue();
                                arrayList.add((SemSimpleNetworkStats) batteryStatsImpl.mNetworkStatsDeltaMap.get(num));
                            }
                            arrayList.sort(new BatteryStatsImpl.UsageComparator());
                            iBatteryStatsCallback.notifyNetworkStatsUpdated(arrayList.subList(0, Math.min(10, arrayList.size())));
                            batteryStatsImpl.mLastCallbackTime = j;
                            batteryStatsImpl.mNetworkStatsDeltaMap.clear();
                        }
                    }
                })) {
                    Slog.e("BatteryStatsImpl", "Callback failed to queue");
                }
            } finally {
                this.mBatteryStatsCallbacks.finishBroadcast();
            }
        }
    }

    public final void onCleanupUserLocked(int i, long j) {
        this.mPendingRemovedUids.add(new UidToRemove(UserHandle.getUid(i, 0), UserHandle.getUid(i, 99999), j));
    }

    public final void onSystemReady(Context context) {
        KernelCpuUidTimeReader.KernelCpuUidFreqTimeReader kernelCpuUidFreqTimeReader = this.mCpuUidFreqTimeReader;
        if (kernelCpuUidFreqTimeReader != null) {
            kernelCpuUidFreqTimeReader.onSystemReady();
        }
        PowerStatsCollectorInjector powerStatsCollectorInjector = this.mPowerStatsCollectorInjector;
        powerStatsCollectorInjector.getClass();
        powerStatsCollectorInjector.mPackageManager = context.getPackageManager();
        powerStatsCollectorInjector.mConsumedEnergyRetriever = new PowerStatsCollector.ConsumedEnergyRetrieverImpl((PowerStatsService.LocalService) LocalServices.getService(PowerStatsService.LocalService.class));
        powerStatsCollectorInjector.mNetworkStatsManager = (NetworkStatsManager) context.getSystemService(NetworkStatsManager.class);
        powerStatsCollectorInjector.mTelephonyManager = (TelephonyManager) context.getSystemService(TelephonyManager.class);
        powerStatsCollectorInjector.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
        powerStatsCollectorInjector.mBluetoothStatsRetriever = BatteryStatsImpl.this.new BluetoothStatsRetrieverImpl((BluetoothManager) context.getSystemService(BluetoothManager.class));
        this.mCpuPowerStatsCollector.mEnabled = this.mPowerStatsCollectorEnabled.get(1);
        this.mCpuPowerStatsCollector.schedule();
        MobileRadioPowerStatsCollector mobileRadioPowerStatsCollector = this.mMobileRadioPowerStatsCollector;
        boolean z = false;
        if (this.mPowerStatsCollectorEnabled.get(8)) {
            PackageManager packageManager = mobileRadioPowerStatsCollector.mInjector.mPackageManager;
            mobileRadioPowerStatsCollector.mEnabled = packageManager != null && packageManager.hasSystemFeature("android.hardware.telephony");
        } else {
            mobileRadioPowerStatsCollector.mEnabled = false;
        }
        this.mMobileRadioPowerStatsCollector.schedule();
        WifiPowerStatsCollector wifiPowerStatsCollector = this.mWifiPowerStatsCollector;
        if (this.mPowerStatsCollectorEnabled.get(11)) {
            PackageManager packageManager2 = wifiPowerStatsCollector.mInjector.mPackageManager;
            wifiPowerStatsCollector.mEnabled = packageManager2 != null && packageManager2.hasSystemFeature("android.hardware.wifi");
        } else {
            wifiPowerStatsCollector.mEnabled = false;
        }
        this.mWifiPowerStatsCollector.schedule();
        BluetoothPowerStatsCollector bluetoothPowerStatsCollector = this.mBluetoothPowerStatsCollector;
        if (this.mPowerStatsCollectorEnabled.get(2)) {
            PackageManager packageManager3 = bluetoothPowerStatsCollector.mInjector.mPackageManager;
            if (packageManager3 != null && packageManager3.hasSystemFeature("android.hardware.bluetooth")) {
                z = true;
            }
            bluetoothPowerStatsCollector.mEnabled = z;
        } else {
            bluetoothPowerStatsCollector.mEnabled = false;
        }
        this.mBluetoothPowerStatsCollector.schedule();
        this.mCameraPowerStatsCollector.mEnabled = this.mPowerStatsCollectorEnabled.get(3);
        this.mCameraPowerStatsCollector.schedule();
        this.mGnssPowerStatsCollector.mEnabled = this.mPowerStatsCollectorEnabled.get(10);
        this.mGnssPowerStatsCollector.schedule();
        this.mCustomEnergyConsumerPowerStatsCollector.mEnabled = this.mPowerStatsCollectorEnabled.get(-1);
        this.mCustomEnergyConsumerPowerStatsCollector.schedule();
        this.mSystemReady = true;
    }

    public final void onUserRemovedLocked(int i) {
        BatteryExternalStatsWorker batteryExternalStatsWorker = this.mExternalSync;
        if (batteryExternalStatsWorker != null) {
            synchronized (batteryExternalStatsWorker) {
                try {
                    ScheduledExecutorService scheduledExecutorService = batteryExternalStatsWorker.mExecutorService;
                    BatteryExternalStatsWorker$$ExternalSyntheticLambda3 batteryExternalStatsWorker$$ExternalSyntheticLambda3 = new BatteryExternalStatsWorker$$ExternalSyntheticLambda3(batteryExternalStatsWorker, i, 0);
                    TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                    scheduledExecutorService.schedule(batteryExternalStatsWorker$$ExternalSyntheticLambda3, 2000L, timeUnit);
                    batteryExternalStatsWorker.mExecutorService.schedule(new BatteryExternalStatsWorker$$ExternalSyntheticLambda3(batteryExternalStatsWorker, i, 1), 10000L, timeUnit);
                } catch (RejectedExecutionException e) {
                    CompletableFuture.failedFuture(e);
                } finally {
                }
            }
        }
    }

    public final void prepareForDumpLocked() {
        pullPendingStateUpdatesLocked();
        getStartClockTime();
        if (Flags.disableSystemServicePowerAttr()) {
            return;
        }
        updateSystemServiceCallStats();
    }

    public final void pullPendingStateUpdatesLocked() {
        if (this.mOnBatteryInternal) {
            int i = this.mScreenState;
            updateDischargeScreenLevelsLocked(i, i);
        }
    }

    public final void readDailyItemTagLocked(TypedXmlPullParser typedXmlPullParser) {
        BatteryStats.DailyItem dailyItem = new BatteryStats.DailyItem();
        dailyItem.mStartTime = typedXmlPullParser.getAttributeLong((String) null, "start", 0L);
        dailyItem.mEndTime = typedXmlPullParser.getAttributeLong((String) null, "end", 0L);
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                String name = typedXmlPullParser.getName();
                if (name.equals("dis")) {
                    readDailyItemTagDetailsLocked(typedXmlPullParser, dailyItem, false, "dis");
                } else if (name.equals("chg")) {
                    readDailyItemTagDetailsLocked(typedXmlPullParser, dailyItem, true, "chg");
                } else {
                    String str = "NULL";
                    if (name.equals("upd")) {
                        if (dailyItem.mPackageChanges == null) {
                            dailyItem.mPackageChanges = new ArrayList();
                        }
                        BatteryStats.PackageChange packageChange = new BatteryStats.PackageChange();
                        packageChange.mUpdate = true;
                        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "pkg");
                        if (attributeValue == null) {
                            Slog.w("BatteryStatsImpl", "There was an issue while reading the package name from internal storage.");
                        } else {
                            str = attributeValue;
                        }
                        packageChange.mPackageName = str;
                        packageChange.mVersionCode = typedXmlPullParser.getAttributeLong((String) null, "ver", 0L);
                        dailyItem.mPackageChanges.add(packageChange);
                        XmlUtils.skipCurrentTag(typedXmlPullParser);
                    } else if (name.equals("rem")) {
                        if (dailyItem.mPackageChanges == null) {
                            dailyItem.mPackageChanges = new ArrayList();
                        }
                        BatteryStats.PackageChange packageChange2 = new BatteryStats.PackageChange();
                        packageChange2.mUpdate = false;
                        String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "pkg");
                        if (attributeValue2 == null) {
                            Slog.w("BatteryStatsImpl", "There was an issue while reading the package name from internal storage.");
                        } else {
                            str = attributeValue2;
                        }
                        packageChange2.mPackageName = str;
                        dailyItem.mPackageChanges.add(packageChange2);
                        XmlUtils.skipCurrentTag(typedXmlPullParser);
                    } else {
                        Slog.w("BatteryStatsImpl", "Unknown element under <item>: " + typedXmlPullParser.getName());
                        XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                }
            }
        }
        this.mDailyItems.add(dailyItem);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0036, code lost:
    
        if (r4 != 4) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0043, code lost:
    
        if (r8.getName().equals("item") == false) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0049, code lost:
    
        android.util.Slog.w("BatteryStatsImpl", "Unknown element under <daily-items>: " + r8.getName());
        com.android.internal.util.XmlUtils.skipCurrentTag(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0045, code lost:
    
        readDailyItemTagLocked(r8);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void readDailyItemsLocked(com.android.modules.utils.TypedXmlPullParser r8) {
        /*
            r7 = this;
            java.lang.String r0 = "Failed parsing daily "
            java.lang.String r1 = "BatteryStatsImpl"
        L4:
            int r2 = r8.next()     // Catch: java.lang.IndexOutOfBoundsException -> L25 java.io.IOException -> L27 org.xmlpull.v1.XmlPullParserException -> L29 java.lang.NumberFormatException -> L2b java.lang.NullPointerException -> L2d java.lang.IllegalStateException -> L30
            r3 = 1
            r4 = 2
            if (r2 == r4) goto Lf
            if (r2 == r3) goto Lf
            goto L4
        Lf:
            if (r2 != r4) goto L65
            int r2 = r8.getDepth()     // Catch: java.lang.IndexOutOfBoundsException -> L25 java.io.IOException -> L27 org.xmlpull.v1.XmlPullParserException -> L29 java.lang.NumberFormatException -> L2b java.lang.NullPointerException -> L2d java.lang.IllegalStateException -> L30
        L15:
            int r4 = r8.next()     // Catch: java.lang.IndexOutOfBoundsException -> L25 java.io.IOException -> L27 org.xmlpull.v1.XmlPullParserException -> L29 java.lang.NumberFormatException -> L2b java.lang.NullPointerException -> L2d java.lang.IllegalStateException -> L30
            if (r4 == r3) goto Lc1
            r5 = 3
            if (r4 != r5) goto L33
            int r6 = r8.getDepth()     // Catch: java.lang.IndexOutOfBoundsException -> L25 java.io.IOException -> L27 org.xmlpull.v1.XmlPullParserException -> L29 java.lang.NumberFormatException -> L2b java.lang.NullPointerException -> L2d java.lang.IllegalStateException -> L30
            if (r6 <= r2) goto Lc1
            goto L33
        L25:
            r7 = move-exception
            goto L6e
        L27:
            r7 = move-exception
            goto L7e
        L29:
            r7 = move-exception
            goto L82
        L2b:
            r7 = move-exception
            goto L92
        L2d:
            r7 = move-exception
            goto La2
        L30:
            r7 = move-exception
            goto Lb2
        L33:
            if (r4 == r5) goto L15
            r5 = 4
            if (r4 != r5) goto L39
            goto L15
        L39:
            java.lang.String r4 = r8.getName()     // Catch: java.lang.IndexOutOfBoundsException -> L25 java.io.IOException -> L27 org.xmlpull.v1.XmlPullParserException -> L29 java.lang.NumberFormatException -> L2b java.lang.NullPointerException -> L2d java.lang.IllegalStateException -> L30
            java.lang.String r5 = "item"
            boolean r4 = r4.equals(r5)     // Catch: java.lang.IndexOutOfBoundsException -> L25 java.io.IOException -> L27 org.xmlpull.v1.XmlPullParserException -> L29 java.lang.NumberFormatException -> L2b java.lang.NullPointerException -> L2d java.lang.IllegalStateException -> L30
            if (r4 == 0) goto L49
            r7.readDailyItemTagLocked(r8)     // Catch: java.lang.IndexOutOfBoundsException -> L25 java.io.IOException -> L27 org.xmlpull.v1.XmlPullParserException -> L29 java.lang.NumberFormatException -> L2b java.lang.NullPointerException -> L2d java.lang.IllegalStateException -> L30
            goto L15
        L49:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.IndexOutOfBoundsException -> L25 java.io.IOException -> L27 org.xmlpull.v1.XmlPullParserException -> L29 java.lang.NumberFormatException -> L2b java.lang.NullPointerException -> L2d java.lang.IllegalStateException -> L30
            r4.<init>()     // Catch: java.lang.IndexOutOfBoundsException -> L25 java.io.IOException -> L27 org.xmlpull.v1.XmlPullParserException -> L29 java.lang.NumberFormatException -> L2b java.lang.NullPointerException -> L2d java.lang.IllegalStateException -> L30
            java.lang.String r5 = "Unknown element under <daily-items>: "
            r4.append(r5)     // Catch: java.lang.IndexOutOfBoundsException -> L25 java.io.IOException -> L27 org.xmlpull.v1.XmlPullParserException -> L29 java.lang.NumberFormatException -> L2b java.lang.NullPointerException -> L2d java.lang.IllegalStateException -> L30
            java.lang.String r5 = r8.getName()     // Catch: java.lang.IndexOutOfBoundsException -> L25 java.io.IOException -> L27 org.xmlpull.v1.XmlPullParserException -> L29 java.lang.NumberFormatException -> L2b java.lang.NullPointerException -> L2d java.lang.IllegalStateException -> L30
            r4.append(r5)     // Catch: java.lang.IndexOutOfBoundsException -> L25 java.io.IOException -> L27 org.xmlpull.v1.XmlPullParserException -> L29 java.lang.NumberFormatException -> L2b java.lang.NullPointerException -> L2d java.lang.IllegalStateException -> L30
            java.lang.String r4 = r4.toString()     // Catch: java.lang.IndexOutOfBoundsException -> L25 java.io.IOException -> L27 org.xmlpull.v1.XmlPullParserException -> L29 java.lang.NumberFormatException -> L2b java.lang.NullPointerException -> L2d java.lang.IllegalStateException -> L30
            android.util.Slog.w(r1, r4)     // Catch: java.lang.IndexOutOfBoundsException -> L25 java.io.IOException -> L27 org.xmlpull.v1.XmlPullParserException -> L29 java.lang.NumberFormatException -> L2b java.lang.NullPointerException -> L2d java.lang.IllegalStateException -> L30
            com.android.internal.util.XmlUtils.skipCurrentTag(r8)     // Catch: java.lang.IndexOutOfBoundsException -> L25 java.io.IOException -> L27 org.xmlpull.v1.XmlPullParserException -> L29 java.lang.NumberFormatException -> L2b java.lang.NullPointerException -> L2d java.lang.IllegalStateException -> L30
            goto L15
        L65:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException     // Catch: java.lang.IndexOutOfBoundsException -> L25 java.io.IOException -> L27 org.xmlpull.v1.XmlPullParserException -> L29 java.lang.NumberFormatException -> L2b java.lang.NullPointerException -> L2d java.lang.IllegalStateException -> L30
            java.lang.String r8 = "no start tag found"
            r7.<init>(r8)     // Catch: java.lang.IndexOutOfBoundsException -> L25 java.io.IOException -> L27 org.xmlpull.v1.XmlPullParserException -> L29 java.lang.NumberFormatException -> L2b java.lang.NullPointerException -> L2d java.lang.IllegalStateException -> L30
            throw r7     // Catch: java.lang.IndexOutOfBoundsException -> L25 java.io.IOException -> L27 org.xmlpull.v1.XmlPullParserException -> L29 java.lang.NumberFormatException -> L2b java.lang.NullPointerException -> L2d java.lang.IllegalStateException -> L30
        L6e:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>(r0)
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            android.util.Slog.w(r1, r7)
            goto Lc1
        L7e:
            com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m(r0, r7, r1)
            goto Lc1
        L82:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>(r0)
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            android.util.Slog.w(r1, r7)
            goto Lc1
        L92:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>(r0)
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            android.util.Slog.w(r1, r7)
            goto Lc1
        La2:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>(r0)
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            android.util.Slog.w(r1, r7)
            goto Lc1
        Lb2:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>(r0)
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            android.util.Slog.w(r1, r7)
        Lc1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.stats.BatteryStatsImpl.readDailyItemsLocked(com.android.modules.utils.TypedXmlPullParser):void");
    }

    public final void readDailyStatsLocked() {
        Slog.d("BatteryStatsImpl", "Reading daily items from " + this.mDailyFile.getBaseFile());
        this.mDailyItems.clear();
        try {
            FileInputStream openRead = this.mDailyFile.openRead();
            try {
                readDailyItemsLocked(Xml.resolvePullParser(openRead));
            } catch (IOException unused) {
            } catch (Throwable th) {
                try {
                    openRead.close();
                } catch (IOException unused2) {
                }
                throw th;
            }
            openRead.close();
        } catch (FileNotFoundException | IOException unused3) {
        }
    }

    public void readKernelUidCpuActiveTimesLocked(boolean z) {
        final long uptimeMillis = this.mClock.uptimeMillis();
        final long elapsedRealtime = this.mClock.elapsedRealtime();
        this.mCpuUidActiveTimeReader.readAbsolute(new KernelCpuUidTimeReader.Callback() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda1
            public final void onUidCpuTime(int i, Object obj) {
                BatteryStatsImpl.Uid.ChildUid childUid;
                BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
                long j = elapsedRealtime;
                long j2 = uptimeMillis;
                Long l = (Long) obj;
                int mapUid = batteryStatsImpl.mapUid(i);
                if (!Process.isIsolated(mapUid) && batteryStatsImpl.mUserInfoProvider.exists(UserHandle.getUserId(i))) {
                    BatteryStatsImpl.Uid uidStatsLocked = batteryStatsImpl.getUidStatsLocked(mapUid, j, j2);
                    if (mapUid == i) {
                        BatteryStatsImpl.BinderCallStats binderCallStats = BatteryStatsImpl.Uid.sTempBinderCallStats;
                        uidStatsLocked.getCpuActiveTimeCounter().mCounter.updateValue(l.longValue(), j);
                    } else {
                        SparseArray sparseArray = uidStatsLocked.mChildUids;
                        if (sparseArray == null || (childUid = (BatteryStatsImpl.Uid.ChildUid) sparseArray.get(i)) == null) {
                            return;
                        }
                        BatteryStatsImpl.TimeMultiStateCounter.m841$$Nest$mincrement(uidStatsLocked.getCpuActiveTimeCounter(), childUid.cpuActiveCounter.mCounter.updateValue(l.longValue(), j), j);
                    }
                }
            }
        });
        long uptimeMillis2 = this.mClock.uptimeMillis() - uptimeMillis;
        if (uptimeMillis2 >= 100) {
            Slog.d("BatteryStatsImpl", "Reading cpu active times took " + uptimeMillis2 + "ms");
        }
    }

    public void readKernelUidCpuClusterTimesLocked(final boolean z, final CpuDeltaPowerAccumulator cpuDeltaPowerAccumulator) {
        final long uptimeMillis = this.mClock.uptimeMillis();
        final long elapsedRealtime = this.mClock.elapsedRealtime();
        this.mCpuUidClusterTimeReader.readDelta(cpuDeltaPowerAccumulator != null, new KernelCpuUidTimeReader.Callback() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda7
            public final void onUidCpuTime(int i, Object obj) {
                BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
                long j = elapsedRealtime;
                long j2 = uptimeMillis;
                boolean z2 = z;
                BatteryStatsImpl.CpuDeltaPowerAccumulator cpuDeltaPowerAccumulator2 = cpuDeltaPowerAccumulator;
                long[] jArr = (long[]) obj;
                int mapUid = batteryStatsImpl.mapUid(i);
                if (!Process.isIsolated(mapUid) && batteryStatsImpl.mUserInfoProvider.exists(UserHandle.getUserId(mapUid))) {
                    BatteryStatsImpl.Uid uidStatsLocked = batteryStatsImpl.getUidStatsLocked(mapUid, j, j2);
                    uidStatsLocked.mCpuClusterTimesMs.addCountLocked(jArr, z2);
                    if (cpuDeltaPowerAccumulator2 != null) {
                        double[] orCreateUidCpuClusterCharges = cpuDeltaPowerAccumulator2.getOrCreateUidCpuClusterCharges(uidStatsLocked);
                        for (int i2 = 0; i2 < jArr.length; i2++) {
                            double d = cpuDeltaPowerAccumulator2.mCalculator.mPerClusterPowerEstimators[i2].mAveragePowerMahPerMs * jArr[i2];
                            orCreateUidCpuClusterCharges[i2] = orCreateUidCpuClusterCharges[i2] + d;
                            double[] dArr = cpuDeltaPowerAccumulator2.totalClusterChargesMah;
                            dArr[i2] = dArr[i2] + d;
                        }
                    }
                }
            }
        });
        long uptimeMillis2 = this.mClock.uptimeMillis() - uptimeMillis;
        if (uptimeMillis2 >= 100) {
            Slog.d("BatteryStatsImpl", "Reading cpu cluster times took " + uptimeMillis2 + "ms");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0092, code lost:
    
        if (r0.length != r10) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void readKernelUidCpuFreqTimesLocked(java.util.ArrayList r22, final boolean r23, final boolean r24, final com.android.server.power.stats.BatteryStatsImpl.CpuDeltaPowerAccumulator r25) {
        /*
            Method dump skipped, instructions count: 264
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.stats.BatteryStatsImpl.readKernelUidCpuFreqTimesLocked(java.util.ArrayList, boolean, boolean, com.android.server.power.stats.BatteryStatsImpl$CpuDeltaPowerAccumulator):void");
    }

    public void readKernelUidCpuTimesLocked(ArrayList arrayList, final SparseLongArray sparseLongArray, final boolean z) {
        ArrayList arrayList2 = arrayList;
        SparseLongArray sparseLongArray2 = sparseLongArray;
        this.mTempTotalCpuSystemTimeUs = 0L;
        this.mTempTotalCpuUserTimeUs = 0L;
        int i = 0;
        int size = arrayList2 == null ? 0 : arrayList.size();
        final long uptimeMillis = this.mClock.uptimeMillis();
        final long elapsedRealtime = this.mClock.elapsedRealtime();
        final int i2 = size;
        int i3 = size;
        this.mCpuUidUserSysTimeReader.readDelta(false, new KernelCpuUidTimeReader.Callback() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda8
            public final void onUidCpuTime(int i4, Object obj) {
                BatteryStatsImpl batteryStatsImpl = BatteryStatsImpl.this;
                long j = elapsedRealtime;
                long j2 = uptimeMillis;
                int i5 = i2;
                boolean z2 = z;
                SparseLongArray sparseLongArray3 = sparseLongArray;
                long[] jArr = (long[]) obj;
                batteryStatsImpl.getClass();
                long j3 = jArr[0];
                long j4 = jArr[1];
                int mapUid = batteryStatsImpl.mapUid(i4);
                if (!Process.isIsolated(mapUid) && batteryStatsImpl.mUserInfoProvider.exists(UserHandle.getUserId(mapUid))) {
                    BatteryStatsImpl.Uid uidStatsLocked = batteryStatsImpl.getUidStatsLocked(mapUid, j, j2);
                    batteryStatsImpl.mTempTotalCpuUserTimeUs += j3;
                    batteryStatsImpl.mTempTotalCpuSystemTimeUs += j4;
                    if (i5 > 0) {
                        j3 = (j3 * 50) / 100;
                        j4 = (j4 * 50) / 100;
                    }
                    uidStatsLocked.mUserCpuTime.addCountLocked(j3, z2);
                    uidStatsLocked.mSystemCpuTime.addCountLocked(j4, z2);
                    if (sparseLongArray3 != null) {
                        sparseLongArray3.put(uidStatsLocked.mUid, j3 + j4);
                    }
                }
            }
        });
        long uptimeMillis2 = this.mClock.uptimeMillis() - uptimeMillis;
        if (uptimeMillis2 >= 100) {
            Slog.d("BatteryStatsImpl", "Reading cpu stats took " + uptimeMillis2 + "ms");
        }
        if (i3 > 0) {
            this.mTempTotalCpuUserTimeUs = (this.mTempTotalCpuUserTimeUs * 50) / 100;
            this.mTempTotalCpuSystemTimeUs = (this.mTempTotalCpuSystemTimeUs * 50) / 100;
            while (i < i3) {
                StopwatchTimer stopwatchTimer = (StopwatchTimer) arrayList2.get(i);
                long j = i3 - i;
                int i4 = (int) (this.mTempTotalCpuUserTimeUs / j);
                int i5 = (int) (this.mTempTotalCpuSystemTimeUs / j);
                long j2 = i4;
                stopwatchTimer.mUid.mUserCpuTime.addCountLocked(j2, z);
                long j3 = i5;
                stopwatchTimer.mUid.mSystemCpuTime.addCountLocked(j3, z);
                if (sparseLongArray2 != null) {
                    int i6 = stopwatchTimer.mUid.mUid;
                    sparseLongArray2.put(i6, sparseLongArray2.get(i6, 0L) + j2 + j3);
                }
                Uid.Proc processStatsLocked = stopwatchTimer.mUid.getProcessStatsLocked("*wakelock*");
                int i7 = i4 / 1000;
                int i8 = i5 / 1000;
                if (z) {
                    processStatsLocked.mUserTimeMs += i7;
                    processStatsLocked.mSystemTimeMs += i8;
                }
                this.mTempTotalCpuUserTimeUs -= j2;
                this.mTempTotalCpuSystemTimeUs -= j3;
                i++;
                arrayList2 = arrayList;
                sparseLongArray2 = sparseLongArray;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void readLocked() {
        /*
            Method dump skipped, instructions count: 345
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.stats.BatteryStatsImpl.readLocked():void");
    }

    public NetworkStats readMobileNetworkStatsLocked(NetworkStatsManager networkStatsManager) {
        return networkStatsManager.getMobileUidStats();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void readSummaryFromParcel(Parcel parcel) {
        long j;
        Uid uid;
        Uid uid2;
        Uid uid3;
        int readInt = parcel.readInt();
        int i = VERSION;
        if (readInt != i) {
            Slog.w("BatteryStats", DualAppManagerService$$ExternalSyntheticOutline0.m(readInt, i, "readFromParcel: version got ", ", expected ", "; erasing old stats"));
            return;
        }
        this.mHistory.readSummaryFromParcel(parcel);
        this.mStartCount = parcel.readInt();
        this.mUptimeUs = parcel.readLong();
        this.mRealtimeUs = parcel.readLong();
        this.mStartClockTimeMs = parcel.readLong();
        this.mMonotonicStartTime = parcel.readLong();
        this.mMonotonicEndTime = parcel.readLong();
        this.mStartPlatformVersion = parcel.readString();
        this.mEndPlatformVersion = parcel.readString();
        TimeBase timeBase = this.mOnBatteryTimeBase;
        timeBase.getClass();
        timeBase.mUptimeUs = parcel.readLong();
        timeBase.mRealtimeUs = parcel.readLong();
        TimeBase timeBase2 = this.mOnBatteryScreenOffTimeBase;
        timeBase2.getClass();
        timeBase2.mUptimeUs = parcel.readLong();
        timeBase2.mRealtimeUs = parcel.readLong();
        this.mDischargeUnplugLevel = parcel.readInt();
        this.mDischargePlugLevel = parcel.readInt();
        this.mDischargeCurrentLevel = parcel.readInt();
        this.mBatteryLevel = parcel.readInt();
        this.mEstimatedBatteryCapacityMah = parcel.readInt();
        this.mLastLearnedBatteryCapacityUah = parcel.readInt();
        this.mMinLearnedBatteryCapacityUah = parcel.readInt();
        this.mMaxLearnedBatteryCapacityUah = parcel.readInt();
        this.mLowDischargeAmountSinceCharge = parcel.readInt();
        this.mHighDischargeAmountSinceCharge = parcel.readInt();
        this.mDischargeAmountScreenOnSinceCharge = parcel.readInt();
        this.mDischargeAmountScreenOffSinceCharge = parcel.readInt();
        this.mDischargeAmountScreenDozeSinceCharge = parcel.readInt();
        this.mProtectBatteryMode = parcel.readInt();
        this.mDischargeAmountScreenOnSinceChargePermil = parcel.readInt();
        this.mDischargeAmountScreenOffSinceChargePermil = parcel.readInt();
        this.mDischargeAmountScreenDozeSinceChargePermil = parcel.readInt();
        this.mDischargeAmountSubScreenOnSinceChargePermil = parcel.readInt();
        this.mDischargeAmountSubScreenDozeSinceChargePermil = parcel.readInt();
        this.mDischargeAmountScreenOnSinceChargeCoulombCounter = parcel.readInt();
        this.mDischargeAmountScreenOffSinceChargeCoulombCounter = parcel.readInt();
        this.mDischargeStepTracker.readFromParcel(parcel);
        this.mChargeStepTracker.readFromParcel(parcel);
        this.mDailyDischargeStepTracker.readFromParcel(parcel);
        this.mDailyChargeStepTracker.readFromParcel(parcel);
        LongSamplingCounter longSamplingCounter = this.mDischargeCounter;
        longSamplingCounter.getClass();
        longSamplingCounter.mCount = parcel.readLong();
        LongSamplingCounter longSamplingCounter2 = this.mDischargeScreenOffCounter;
        longSamplingCounter2.getClass();
        longSamplingCounter2.mCount = parcel.readLong();
        LongSamplingCounter longSamplingCounter3 = this.mDischargeScreenDozeCounter;
        longSamplingCounter3.getClass();
        longSamplingCounter3.mCount = parcel.readLong();
        LongSamplingCounter longSamplingCounter4 = this.mDischargeLightDozeCounter;
        longSamplingCounter4.getClass();
        longSamplingCounter4.mCount = parcel.readLong();
        LongSamplingCounter longSamplingCounter5 = this.mDischargeDeepDozeCounter;
        longSamplingCounter5.getClass();
        longSamplingCounter5.mCount = parcel.readLong();
        int readInt2 = parcel.readInt();
        boolean z = 0;
        if (readInt2 > 0) {
            this.mDailyPackageChanges = new ArrayList(readInt2);
            while (readInt2 > 0) {
                readInt2--;
                BatteryStats.PackageChange packageChange = new BatteryStats.PackageChange();
                packageChange.mPackageName = parcel.readString();
                packageChange.mUpdate = parcel.readInt() != 0;
                packageChange.mVersionCode = parcel.readLong();
                this.mDailyPackageChanges.add(packageChange);
            }
        } else {
            this.mDailyPackageChanges = null;
        }
        this.mDailyStartTimeMs = parcel.readLong();
        this.mNextMinDailyDeadlineMs = parcel.readLong();
        this.mNextMaxDailyDeadlineMs = parcel.readLong();
        this.mBatteryTimeToFullSeconds = parcel.readLong();
        EnergyConsumerStats.Config createFromParcel = EnergyConsumerStats.Config.createFromParcel(parcel);
        EnergyConsumerStats createAndReadSummaryFromParcel = EnergyConsumerStats.createAndReadSummaryFromParcel(this.mEnergyConsumerStatsConfig, parcel);
        int i2 = 5;
        if (createFromParcel != null) {
            String[] stateNames = createFromParcel.getStateNames();
            String[] strArr = new String[5];
            for (int i3 = 0; i3 < 5; i3++) {
                strArr[i3] = BatteryConsumer.processStateToString(i3);
            }
            if (Arrays.equals(stateNames, strArr)) {
                this.mEnergyConsumerStatsConfig = createFromParcel;
                this.mGlobalEnergyConsumerStats = createAndReadSummaryFromParcel;
            }
        }
        this.mStartCount++;
        this.mScreenState = 0;
        this.mScreenOnTimer.readSummaryFromParcelLocked(parcel);
        this.mScreenDozeTimer.readSummaryFromParcelLocked(parcel);
        for (int i4 = 0; i4 < 5; i4++) {
            this.mScreenBrightnessTimer[i4].readSummaryFromParcelLocked(parcel);
        }
        int readInt3 = parcel.readInt();
        for (int i5 = 0; i5 < readInt3; i5++) {
            DisplayBatteryStats displayBatteryStats = this.mPerDisplayBatteryStats[i5];
            displayBatteryStats.screenOnTimer.readSummaryFromParcelLocked(parcel);
            displayBatteryStats.screenDozeTimer.readSummaryFromParcelLocked(parcel);
            for (int i6 = 0; i6 < 5; i6++) {
                displayBatteryStats.screenBrightnessTimers[i6].readSummaryFromParcelLocked(parcel);
            }
        }
        this.mSubScreenState = 0;
        this.mSubScreenOnTimer.readSummaryFromParcelLocked(parcel);
        this.mSubScreenDozeTimer.readSummaryFromParcelLocked(parcel);
        for (int i7 = 0; i7 < 5; i7++) {
            this.mSubScreenBrightnessTimer[i7].readSummaryFromParcelLocked(parcel);
        }
        for (int i8 = 0; i8 < 5; i8++) {
            this.mScreenAutoBrightnessTimer[i8].readSummaryFromParcelLocked(parcel);
        }
        for (int i9 = 0; i9 < 5; i9++) {
            this.mSubScreenAutoBrightnessTimer[i9].readSummaryFromParcelLocked(parcel);
        }
        this.mScreenHighBrightnessTimer.readSummaryFromParcelLocked(parcel);
        this.mSubScreenHighBrightnessTimer.readSummaryFromParcelLocked(parcel);
        for (int i10 = 0; i10 < 4; i10++) {
            this.mHighRefreshRateTimer[i10].readSummaryFromParcelLocked(parcel);
        }
        for (int i11 = 0; i11 < 4; i11++) {
            this.mSubHighRefreshRateTimer[i11].readSummaryFromParcelLocked(parcel);
        }
        for (int i12 = 0; i12 < 16; i12++) {
            LongSamplingCounter longSamplingCounter6 = this.mSpeakerMediaTimeCounters[i12];
            longSamplingCounter6.getClass();
            longSamplingCounter6.mCount = parcel.readLong();
            LongSamplingCounter longSamplingCounter7 = this.mSpeakerCallTimeCounters[i12];
            longSamplingCounter7.getClass();
            longSamplingCounter7.mCount = parcel.readLong();
        }
        this.mHasSpeakerOutReporting = parcel.readInt() != 0;
        this.mTxPowerSharingTimer.readSummaryFromParcelLocked(parcel);
        this.mTxPowerSharingDischargeMah = parcel.readLong();
        this.mMobileActiveTimer.readSummaryFromParcelLocked(parcel);
        this.mMobileActive5GTimer.readSummaryFromParcelLocked(parcel);
        this.mScreenOnGpsTimer.readSummaryFromParcelLocked(parcel);
        this.mInteractive = false;
        this.mInteractiveTimer.readSummaryFromParcelLocked(parcel);
        this.mPhoneOn = false;
        this.mPowerSaveModeEnabledTimer.readSummaryFromParcelLocked(parcel);
        this.mLongestLightIdleTimeMs = parcel.readLong();
        this.mLongestFullIdleTimeMs = parcel.readLong();
        this.mDeviceIdleModeLightTimer.readSummaryFromParcelLocked(parcel);
        this.mDeviceIdleModeFullTimer.readSummaryFromParcelLocked(parcel);
        this.mDeviceLightIdlingTimer.readSummaryFromParcelLocked(parcel);
        this.mDeviceIdlingTimer.readSummaryFromParcelLocked(parcel);
        this.mPhoneOnTimer.readSummaryFromParcelLocked(parcel);
        for (int i13 = 0; i13 < CELL_SIGNAL_STRENGTH_LEVEL_COUNT; i13++) {
            this.mPhoneSignalStrengthsTimer[i13].readSummaryFromParcelLocked(parcel);
        }
        this.mPhoneSignalScanningTimer.readSummaryFromParcelLocked(parcel);
        for (int i14 = 0; i14 < BatteryStats.NUM_DATA_CONNECTION_TYPES; i14++) {
            this.mPhoneDataConnectionsTimer[i14].readSummaryFromParcelLocked(parcel);
        }
        this.mNrNsaTimer.readSummaryFromParcelLocked(parcel);
        for (int i15 = 0; i15 < 10; i15++) {
            LongSamplingCounter longSamplingCounter8 = this.mNetworkByteActivityCounters[i15];
            longSamplingCounter8.getClass();
            longSamplingCounter8.mCount = parcel.readLong();
            LongSamplingCounter longSamplingCounter9 = this.mNetworkPacketActivityCounters[i15];
            longSamplingCounter9.getClass();
            longSamplingCounter9.mCount = parcel.readLong();
        }
        int readInt4 = parcel.readInt();
        for (int i16 = 0; i16 < readInt4; i16++) {
            if (parcel.readInt() != 0) {
                RadioAccessTechnologyBatteryStats ratBatteryStatsLocked = getRatBatteryStatsLocked(i16);
                int readInt5 = parcel.readInt();
                int readInt6 = parcel.readInt();
                StopwatchTimer[][] stopwatchTimerArr = ratBatteryStatsLocked.perStateTimers;
                int length = stopwatchTimerArr.length;
                for (int i17 = 0; i17 < readInt5; i17++) {
                    for (int i18 = 0; i18 < readInt6; i18++) {
                        if (i17 >= length || i18 >= 5) {
                            new StopwatchTimer(null, null, -1, null, new TimeBase(false)).readSummaryFromParcelLocked(parcel);
                        } else {
                            stopwatchTimerArr[i17][i18].readSummaryFromParcelLocked(parcel);
                        }
                    }
                }
                if (parcel.readInt() == 1) {
                    for (int i19 = 0; i19 < readInt5; i19++) {
                        for (int i20 = 0; i20 < readInt6; i20++) {
                            if (i19 >= length || i20 >= 5) {
                                new StopwatchTimer(null, null, -1, null, new TimeBase(false)).readSummaryFromParcelLocked(parcel);
                            }
                            LongSamplingCounter txDurationCounter = ratBatteryStatsLocked.getTxDurationCounter(i19, i20, true);
                            txDurationCounter.getClass();
                            txDurationCounter.mCount = parcel.readLong();
                        }
                    }
                }
                if (parcel.readInt() == 1) {
                    for (int i21 = 0; i21 < readInt5; i21++) {
                        if (i21 >= length) {
                            new StopwatchTimer(null, null, -1, null, new TimeBase(false)).readSummaryFromParcelLocked(parcel);
                        } else {
                            LongSamplingCounter rxDurationCounter = ratBatteryStatsLocked.getRxDurationCounter(i21, true);
                            rxDurationCounter.getClass();
                            rxDurationCounter.mCount = parcel.readLong();
                        }
                    }
                }
            }
        }
        this.mMobileRadioPowerState = 1;
        this.mMobileRadioActiveTimer.readSummaryFromParcelLocked(parcel);
        this.mMobileRadioActivePerAppTimer.readSummaryFromParcelLocked(parcel);
        LongSamplingCounter longSamplingCounter10 = this.mMobileRadioActiveAdjustedTime;
        longSamplingCounter10.getClass();
        longSamplingCounter10.mCount = parcel.readLong();
        LongSamplingCounter longSamplingCounter11 = this.mMobileRadioActiveUnknownTime;
        longSamplingCounter11.getClass();
        longSamplingCounter11.mCount = parcel.readLong();
        LongSamplingCounter longSamplingCounter12 = this.mMobileRadioActiveUnknownCount;
        longSamplingCounter12.getClass();
        longSamplingCounter12.mCount = parcel.readLong();
        this.mWifiMulticastWakelockTimer.readSummaryFromParcelLocked(parcel);
        this.mWifiRadioPowerState = 1;
        this.mWifiOn = false;
        this.mWifiOnTimer.readSummaryFromParcelLocked(parcel);
        this.mGlobalWifiRunning = false;
        this.mGlobalWifiRunningTimer.readSummaryFromParcelLocked(parcel);
        for (int i22 = 0; i22 < 8; i22++) {
            this.mWifiStateTimer[i22].readSummaryFromParcelLocked(parcel);
        }
        for (int i23 = 0; i23 < 13; i23++) {
            this.mWifiSupplStateTimer[i23].readSummaryFromParcelLocked(parcel);
        }
        for (int i24 = 0; i24 < 5; i24++) {
            this.mWifiSignalStrengthsTimer[i24].readSummaryFromParcelLocked(parcel);
        }
        this.mWifiActiveTimer.readSummaryFromParcelLocked(parcel);
        this.mWifiActivity.readSummaryFromParcel(parcel);
        int i25 = 0;
        while (true) {
            StopwatchTimer[] stopwatchTimerArr2 = this.mGpsSignalQualityTimer;
            if (i25 >= stopwatchTimerArr2.length) {
                break;
            }
            stopwatchTimerArr2[i25].readSummaryFromParcelLocked(parcel);
            i25++;
        }
        this.mBluetoothActivity.readSummaryFromParcel(parcel);
        this.mModemActivity.readSummaryFromParcel(parcel);
        ModemActivityCounterImpl modemActivityCounterImpl = this.mNetworkModemActivity;
        LongSamplingCounter longSamplingCounter13 = modemActivityCounterImpl.mSleepTimeMillis;
        longSamplingCounter13.getClass();
        longSamplingCounter13.mCount = parcel.readLong();
        LongSamplingCounter longSamplingCounter14 = modemActivityCounterImpl.mIdleTimeMillis;
        longSamplingCounter14.getClass();
        longSamplingCounter14.mCount = parcel.readLong();
        modemActivityCounterImpl.mNrModemActivityInfo.readSummaryFromParcel(parcel);
        modemActivityCounterImpl.mLcModemActivityInfo.readSummaryFromParcel(parcel);
        this.mHasWifiReporting = parcel.readInt() != 0;
        this.mHasBluetoothReporting = parcel.readInt() != 0;
        this.mHasModemReporting = parcel.readInt() != 0;
        this.mNumConnectivityChange = parcel.readInt();
        this.mFlashlightOnNesting = 0;
        this.mFlashlightOnTimer.readSummaryFromParcelLocked(parcel);
        this.mCameraOnNesting = 0;
        this.mCameraOnTimer.readSummaryFromParcelLocked(parcel);
        this.mBluetoothScanNesting = 0;
        this.mBluetoothScanTimer.readSummaryFromParcelLocked(parcel);
        int readInt7 = parcel.readInt();
        if (readInt7 > 10000) {
            throw new ParcelFormatException(VibrationParam$1$$ExternalSyntheticOutline0.m(readInt7, "File corrupt: too many rpm stats "));
        }
        for (int i26 = 0; i26 < readInt7; i26++) {
            if (parcel.readInt() != 0) {
                getRpmTimerLocked(parcel.readString()).readSummaryFromParcelLocked(parcel);
            }
        }
        int readInt8 = parcel.readInt();
        if (readInt8 > 10000) {
            throw new ParcelFormatException(VibrationParam$1$$ExternalSyntheticOutline0.m(readInt8, "File corrupt: too many screen-off rpm stats "));
        }
        for (int i27 = 0; i27 < readInt8; i27++) {
            if (parcel.readInt() != 0) {
                String readString = parcel.readString();
                SamplingTimer samplingTimer = (SamplingTimer) this.mScreenOffRpmStats.get(readString);
                if (samplingTimer == null) {
                    samplingTimer = new SamplingTimer(this.mClock, this.mOnBatteryScreenOffTimeBase);
                    this.mScreenOffRpmStats.put(readString, samplingTimer);
                }
                samplingTimer.readSummaryFromParcelLocked(parcel);
            }
        }
        int readInt9 = parcel.readInt();
        if (readInt9 > 10000) {
            throw new ParcelFormatException(VibrationParam$1$$ExternalSyntheticOutline0.m(readInt9, "File corrupt: too many kernel wake locks "));
        }
        for (int i28 = 0; i28 < readInt9; i28++) {
            if (parcel.readInt() != 0) {
                String readString2 = parcel.readString();
                SamplingTimer samplingTimer2 = (SamplingTimer) this.mKernelWakelockStats.get(readString2);
                if (samplingTimer2 == null) {
                    samplingTimer2 = new SamplingTimer(this.mClock, this.mOnBatteryScreenOffTimeBase);
                    this.mKernelWakelockStats.put(readString2, samplingTimer2);
                }
                samplingTimer2.readSummaryFromParcelLocked(parcel);
            }
        }
        int readInt10 = parcel.readInt();
        if (readInt10 > 10000) {
            throw new ParcelFormatException(VibrationParam$1$$ExternalSyntheticOutline0.m(readInt10, "File corrupt: too many wakeup reasons "));
        }
        for (int i29 = 0; i29 < readInt10; i29++) {
            if (parcel.readInt() != 0) {
                String readString3 = parcel.readString();
                SamplingTimer samplingTimer3 = (SamplingTimer) this.mWakeupReasonStats.get(readString3);
                if (samplingTimer3 == null) {
                    samplingTimer3 = new SamplingTimer(this.mClock, this.mOnBatteryTimeBase);
                    this.mWakeupReasonStats.put(readString3, samplingTimer3);
                }
                samplingTimer3.readSummaryFromParcelLocked(parcel);
            }
        }
        int readInt11 = parcel.readInt();
        for (int i30 = 0; i30 < readInt11; i30++) {
            if (parcel.readInt() != 0) {
                long readLong = parcel.readLong();
                SamplingTimer samplingTimer4 = (SamplingTimer) this.mKernelMemoryStats.get(readLong);
                if (samplingTimer4 == null) {
                    samplingTimer4 = new SamplingTimer(this.mClock, this.mOnBatteryTimeBase);
                    this.mKernelMemoryStats.put(readLong, samplingTimer4);
                }
                samplingTimer4.readSummaryFromParcelLocked(parcel);
            }
        }
        int readInt12 = parcel.readInt();
        for (int i31 = 0; i31 < readInt12; i31++) {
            if (parcel.readInt() != 0) {
                String readString4 = parcel.readString();
                if (!this.mScreenWakeStats.containsKey(readString4)) {
                    this.mScreenWakeStats.put(readString4, new Counter(this.mOnBatteryTimeBase));
                }
                ((Counter) this.mScreenWakeStats.get(readString4)).readSummaryFromParcelLocked(parcel);
            }
        }
        int readInt13 = parcel.readInt();
        if (readInt13 > 10000) {
            throw new ParcelFormatException(VibrationParam$1$$ExternalSyntheticOutline0.m(readInt13, "File corrupt: too many uids "));
        }
        long elapsedRealtime = this.mClock.elapsedRealtime();
        long uptimeMillis = this.mClock.uptimeMillis();
        int i32 = 0;
        while (i32 < readInt13) {
            int readInt14 = parcel.readInt();
            Uid uid4 = r4;
            long j2 = elapsedRealtime;
            Uid uid5 = new Uid(this, readInt14, elapsedRealtime, uptimeMillis);
            this.mUidStats.put(readInt14, uid4);
            TimeBase timeBase3 = uid4.mOnBatteryBackgroundTimeBase;
            timeBase3.getClass();
            timeBase3.mUptimeUs = parcel.readLong();
            timeBase3.mRealtimeUs = parcel.readLong();
            TimeBase timeBase4 = uid4.mOnBatteryScreenOffBackgroundTimeBase;
            timeBase4.getClass();
            timeBase4.mUptimeUs = parcel.readLong();
            timeBase4.mRealtimeUs = parcel.readLong();
            uid4.mWifiRunning = z;
            if (parcel.readInt() != 0) {
                uid4.mWifiRunningTimer.readSummaryFromParcelLocked(parcel);
            }
            uid4.mFullWifiLockOut = z;
            if (parcel.readInt() != 0) {
                uid4.mFullWifiLockTimer.readSummaryFromParcelLocked(parcel);
            }
            uid4.mWifiScanStarted = z;
            if (parcel.readInt() != 0) {
                uid4.mWifiScanTimer.readSummaryFromParcelLocked(parcel);
            }
            uid4.mWifiBatchedScanBinStarted = -1;
            for (int i33 = z ? 1 : 0; i33 < i2; i33++) {
                if (parcel.readInt() != 0) {
                    uid4.makeWifiBatchedScanBin(i33);
                    uid4.mWifiBatchedScanTimer[i33].readSummaryFromParcelLocked(parcel);
                }
            }
            uid4.mWifiMulticastWakelockCount = z ? 1 : 0;
            if (parcel.readInt() != 0) {
                uid4.mWifiMulticastTimer.readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                if (uid4.mAudioTurnedOnTimer == null) {
                    BatteryStatsImpl batteryStatsImpl = uid4.mBsi;
                    uid4.mAudioTurnedOnTimer = new StopwatchTimer(batteryStatsImpl.mClock, uid4, 15, batteryStatsImpl.mAudioTurnedOnTimers, batteryStatsImpl.mOnBatteryTimeBase);
                }
                uid4.mAudioTurnedOnTimer.readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                if (uid4.mVideoTurnedOnTimer == null) {
                    BatteryStatsImpl batteryStatsImpl2 = uid4.mBsi;
                    uid4.mVideoTurnedOnTimer = new StopwatchTimer(batteryStatsImpl2.mClock, uid4, 8, batteryStatsImpl2.mVideoTurnedOnTimers, batteryStatsImpl2.mOnBatteryTimeBase);
                }
                uid4.mVideoTurnedOnTimer.readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                if (uid4.mFlashlightTurnedOnTimer == null) {
                    BatteryStatsImpl batteryStatsImpl3 = uid4.mBsi;
                    uid4.mFlashlightTurnedOnTimer = new StopwatchTimer(batteryStatsImpl3.mClock, uid4, 16, batteryStatsImpl3.mFlashlightTurnedOnTimers, batteryStatsImpl3.mOnBatteryTimeBase);
                }
                uid4.mFlashlightTurnedOnTimer.readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                if (uid4.mCameraTurnedOnTimer == null) {
                    BatteryStatsImpl batteryStatsImpl4 = uid4.mBsi;
                    uid4.mCameraTurnedOnTimer = new StopwatchTimer(batteryStatsImpl4.mClock, uid4, 17, batteryStatsImpl4.mCameraTurnedOnTimers, batteryStatsImpl4.mOnBatteryTimeBase);
                }
                uid4.mCameraTurnedOnTimer.readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                if (uid4.mForegroundActivityTimer == null) {
                    BatteryStatsImpl batteryStatsImpl5 = uid4.mBsi;
                    uid4.mForegroundActivityTimer = new StopwatchTimer(batteryStatsImpl5.mClock, uid4, 10, null, batteryStatsImpl5.mOnBatteryTimeBase);
                }
                uid4.mForegroundActivityTimer.readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                if (uid4.mForegroundServiceTimer == null) {
                    BatteryStatsImpl batteryStatsImpl6 = uid4.mBsi;
                    uid4.mForegroundServiceTimer = new StopwatchTimer(batteryStatsImpl6.mClock, uid4, 22, null, batteryStatsImpl6.mOnBatteryTimeBase);
                }
                uid4.mForegroundServiceTimer.readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                if (uid4.mAggregatedPartialWakelockTimer == null) {
                    BatteryStatsImpl batteryStatsImpl7 = uid4.mBsi;
                    uid4.mAggregatedPartialWakelockTimer = new DualTimer(batteryStatsImpl7.mClock, uid4, 20, null, batteryStatsImpl7.mOnBatteryScreenOffTimeBase, uid4.mOnBatteryScreenOffBackgroundTimeBase);
                }
                uid4.mAggregatedPartialWakelockTimer.readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                uid4.createBluetoothDutyScanTimerLocked().readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                if (uid4.mSpeakerMediaTimeCounters == null) {
                    uid4.initSpeakerTimeCounterLocked();
                }
                for (int i34 = z ? 1 : 0; i34 < 16; i34++) {
                    LongSamplingCounter longSamplingCounter15 = uid4.mSpeakerMediaTimeCounters[i34];
                    longSamplingCounter15.getClass();
                    longSamplingCounter15.mCount = parcel.readLong();
                }
            }
            if (parcel.readInt() != 0) {
                int readInt15 = parcel.readInt();
                PowerProfile powerProfile = this.mPowerProfile;
                if (powerProfile != null && powerProfile.getNumDisplays() != readInt15) {
                    throw new ParcelFormatException("The defined number of displays may have been changed, possibly due to modifications made to the power_profile file.");
                }
                detachIfNotNull(uid4.mPerDisplayTopActivityTimer);
                uid4.mPerDisplayTopActivityTimer = new StopwatchTimer[readInt15];
                for (int i35 = z ? 1 : 0; i35 < readInt15; i35++) {
                    if (parcel.readInt() != 0) {
                        uid4.makeDisplayTopActivityTimer(i35);
                        uid4.mPerDisplayTopActivityTimer[i35].readSummaryFromParcelLocked(parcel);
                    } else {
                        uid4.mPerDisplayTopActivityTimer[i35] = null;
                    }
                }
            }
            if (parcel.readInt() != 0) {
                if (uid4.mBluetoothScanTimer == null) {
                    BatteryStatsImpl batteryStatsImpl8 = uid4.mBsi;
                    uid4.mBluetoothScanTimer = new DualTimer(batteryStatsImpl8.mClock, uid4, 19, batteryStatsImpl8.mBluetoothScanOnTimers, batteryStatsImpl8.mOnBatteryTimeBase, uid4.mOnBatteryBackgroundTimeBase);
                }
                uid4.mBluetoothScanTimer.readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                if (uid4.mBluetoothUnoptimizedScanTimer == null) {
                    BatteryStatsImpl batteryStatsImpl9 = uid4.mBsi;
                    uid4.mBluetoothUnoptimizedScanTimer = new DualTimer(batteryStatsImpl9.mClock, uid4, 21, null, batteryStatsImpl9.mOnBatteryTimeBase, uid4.mOnBatteryBackgroundTimeBase);
                }
                uid4.mBluetoothUnoptimizedScanTimer.readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                if (uid4.mBluetoothScanResultCounter == null) {
                    uid4.mBluetoothScanResultCounter = new Counter(uid4.mBsi.mOnBatteryTimeBase);
                }
                uid4.mBluetoothScanResultCounter.readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                if (uid4.mBluetoothScanResultBgCounter == null) {
                    uid4.mBluetoothScanResultBgCounter = new Counter(uid4.mOnBatteryBackgroundTimeBase);
                }
                uid4.mBluetoothScanResultBgCounter.readSummaryFromParcelLocked(parcel);
            }
            uid4.mProcessState = 7;
            for (int i36 = z ? 1 : 0; i36 < 7; i36++) {
                if (parcel.readInt() != 0) {
                    uid4.makeProcessState(i36);
                    uid4.mProcessStateTimer[i36].readSummaryFromParcelLocked(parcel);
                }
            }
            if (parcel.readInt() != 0) {
                if (uid4.mVibratorOnTimer == null) {
                    BatteryStatsImpl batteryStatsImpl10 = uid4.mBsi;
                    Clock clock = batteryStatsImpl10.mClock;
                    TimeBase timeBase5 = batteryStatsImpl10.mOnBatteryTimeBase;
                    BatchTimer batchTimer = new BatchTimer(clock, timeBase5);
                    batchTimer.mInDischarge = timeBase5.mRunning;
                    uid4.mVibratorOnTimer = batchTimer;
                }
                uid4.mVibratorOnTimer.readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                if (uid4.mUserActivityCounters == null) {
                    uid4.initUserActivityLocked();
                }
                for (int i37 = z ? 1 : 0; i37 < BatteryStats.Uid.NUM_USER_ACTIVITY_TYPES; i37++) {
                    uid4.mUserActivityCounters[i37].readSummaryFromParcelLocked(parcel);
                }
            }
            if (parcel.readInt() != 0) {
                uid4.ensureNetworkActivityLocked();
                for (int i38 = z ? 1 : 0; i38 < 10; i38++) {
                    LongSamplingCounter longSamplingCounter16 = uid4.mNetworkByteActivityCounters[i38];
                    longSamplingCounter16.getClass();
                    longSamplingCounter16.mCount = parcel.readLong();
                    LongSamplingCounter longSamplingCounter17 = uid4.mNetworkPacketActivityCounters[i38];
                    longSamplingCounter17.getClass();
                    longSamplingCounter17.mCount = parcel.readLong();
                }
                if (parcel.readBoolean()) {
                    j = j2;
                    uid4.mMobileRadioActiveTime = TimeMultiStateCounter.m843$$Nest$smreadFromParcel(parcel, this.mOnBatteryTimeBase, j);
                } else {
                    j = j2;
                }
                LongSamplingCounter longSamplingCounter18 = uid4.mMobileRadioActiveCount;
                longSamplingCounter18.getClass();
                longSamplingCounter18.mCount = parcel.readLong();
            } else {
                j = j2;
            }
            LongSamplingCounter longSamplingCounter19 = uid4.mUserCpuTime;
            longSamplingCounter19.getClass();
            longSamplingCounter19.mCount = parcel.readLong();
            LongSamplingCounter longSamplingCounter20 = uid4.mSystemCpuTime;
            longSamplingCounter20.getClass();
            longSamplingCounter20.mCount = parcel.readLong();
            if (parcel.readInt() != 0) {
                int readInt16 = parcel.readInt();
                CpuScalingPolicies cpuScalingPolicies = this.mCpuScalingPolicies;
                int[] policies = cpuScalingPolicies != null ? cpuScalingPolicies.getPolicies() : null;
                if (policies != null && policies.length != readInt16) {
                    throw new ParcelFormatException("Incompatible cpu cluster arrangement");
                }
                detachIfNotNull(uid4.mCpuClusterSpeedTimesUs);
                uid4.mCpuClusterSpeedTimesUs = new LongSamplingCounter[readInt16][];
                int i39 = z ? 1 : 0;
                int i40 = z;
                while (i39 < readInt16) {
                    if (parcel.readInt() != 0) {
                        int readInt17 = parcel.readInt();
                        if (policies != null && this.mCpuScalingPolicies.getFrequencies(policies[i39]).length != readInt17) {
                            throw new ParcelFormatException(VibrationParam$1$$ExternalSyntheticOutline0.m(readInt17, "File corrupt: too many speed bins "));
                        }
                        uid4.mCpuClusterSpeedTimesUs[i39] = new LongSamplingCounter[readInt17];
                        int i41 = i40;
                        while (i41 < readInt17) {
                            if (parcel.readInt() != 0) {
                                uid4.mCpuClusterSpeedTimesUs[i39][i41] = new LongSamplingCounter(this.mOnBatteryTimeBase);
                                LongSamplingCounter longSamplingCounter21 = uid4.mCpuClusterSpeedTimesUs[i39][i41];
                                longSamplingCounter21.getClass();
                                uid3 = uid4;
                                longSamplingCounter21.mCount = parcel.readLong();
                            } else {
                                uid3 = uid4;
                            }
                            i41++;
                            uid4 = uid3;
                        }
                        uid2 = uid4;
                    } else {
                        uid2 = uid4;
                        uid2.mCpuClusterSpeedTimesUs[i39] = null;
                    }
                    i39++;
                    uid4 = uid2;
                    i40 = 0;
                }
                uid = uid4;
            } else {
                uid = uid4;
                detachIfNotNull(uid.mCpuClusterSpeedTimesUs);
                uid.mCpuClusterSpeedTimesUs = null;
            }
            detachIfNotNull(uid.mCpuFreqTimeMs);
            uid.mCpuFreqTimeMs = LongSamplingCounterArray.readSummaryFromParcelLocked(parcel, this.mOnBatteryTimeBase);
            detachIfNotNull(uid.mScreenOffCpuFreqTimeMs);
            uid.mScreenOffCpuFreqTimeMs = LongSamplingCounterArray.readSummaryFromParcelLocked(parcel, this.mOnBatteryScreenOffTimeBase);
            if (parcel.readInt() != 0) {
                uid.mCpuActiveTimeMs = TimeMultiStateCounter.m843$$Nest$smreadFromParcel(parcel, this.mOnBatteryTimeBase, this.mClock.elapsedRealtime());
            }
            LongSamplingCounterArray longSamplingCounterArray = uid.mCpuClusterTimesMs;
            longSamplingCounterArray.getClass();
            longSamplingCounterArray.mCounts = parcel.createLongArray();
            detachIfNotNull(uid.mProcStateTimeMs);
            uid.mProcStateTimeMs = null;
            if (parcel.readInt() != 0) {
                detachIfNotNull(uid.mProcStateTimeMs);
                TimeBase timeBase6 = this.mOnBatteryTimeBase;
                int scalingStepCount = this.mCpuScalingPolicies.getScalingStepCount();
                long elapsedRealtime2 = this.mClock.elapsedRealtime();
                LongArrayMultiStateCounter longArrayMultiStateCounter = (LongArrayMultiStateCounter) LongArrayMultiStateCounter.CREATOR.createFromParcel(parcel);
                uid.mProcStateTimeMs = (longArrayMultiStateCounter.getStateCount() == 8 && longArrayMultiStateCounter.getArrayLength() == scalingStepCount) ? new TimeInFreqMultiStateCounter(timeBase6, longArrayMultiStateCounter, elapsedRealtime2) : null;
            }
            detachIfNotNull(uid.mProcStateScreenOffTimeMs);
            uid.mProcStateScreenOffTimeMs = null;
            if (parcel.readInt() != 0) {
                detachIfNotNull(uid.mProcStateScreenOffTimeMs);
                TimeBase timeBase7 = this.mOnBatteryScreenOffTimeBase;
                int scalingStepCount2 = this.mCpuScalingPolicies.getScalingStepCount();
                long elapsedRealtime3 = this.mClock.elapsedRealtime();
                LongArrayMultiStateCounter longArrayMultiStateCounter2 = (LongArrayMultiStateCounter) LongArrayMultiStateCounter.CREATOR.createFromParcel(parcel);
                uid.mProcStateScreenOffTimeMs = (longArrayMultiStateCounter2.getStateCount() == 8 && longArrayMultiStateCounter2.getArrayLength() == scalingStepCount2) ? new TimeInFreqMultiStateCounter(timeBase7, longArrayMultiStateCounter2, elapsedRealtime3) : null;
            }
            if (parcel.readInt() != 0) {
                detachIfNotNull(uid.mMobileRadioApWakeupCount);
                LongSamplingCounter longSamplingCounter22 = new LongSamplingCounter(this.mOnBatteryTimeBase);
                uid.mMobileRadioApWakeupCount = longSamplingCounter22;
                longSamplingCounter22.mCount = parcel.readLong();
            } else {
                detachIfNotNull(uid.mMobileRadioApWakeupCount);
                uid.mMobileRadioApWakeupCount = null;
            }
            if (parcel.readInt() != 0) {
                detachIfNotNull(uid.mWifiRadioApWakeupCount);
                LongSamplingCounter longSamplingCounter23 = new LongSamplingCounter(this.mOnBatteryTimeBase);
                uid.mWifiRadioApWakeupCount = longSamplingCounter23;
                longSamplingCounter23.mCount = parcel.readLong();
            } else {
                detachIfNotNull(uid.mWifiRadioApWakeupCount);
                uid.mWifiRadioApWakeupCount = null;
            }
            uid.mUidEnergyConsumerStats = EnergyConsumerStats.createAndReadSummaryFromParcel(this.mEnergyConsumerStatsConfig, parcel);
            int readInt18 = parcel.readInt();
            if (readInt18 > MAX_WAKELOCKS_PER_UID + 1) {
                throw new ParcelFormatException(VibrationParam$1$$ExternalSyntheticOutline0.m(readInt18, "File corrupt: too many wake locks "));
            }
            for (int i42 = 0; i42 < readInt18; i42++) {
                uid.readWakeSummaryFromParcelLocked(parcel.readString(), parcel);
            }
            int readInt19 = parcel.readInt();
            if (readInt19 > MAX_WAKELOCKS_PER_UID + 1) {
                throw new ParcelFormatException(VibrationParam$1$$ExternalSyntheticOutline0.m(readInt19, "File corrupt: too many syncs "));
            }
            for (int i43 = 0; i43 < readInt19; i43++) {
                uid.readSyncSummaryFromParcelLocked(parcel.readString(), parcel);
            }
            int readInt20 = parcel.readInt();
            if (readInt20 > MAX_WAKELOCKS_PER_UID + 1) {
                throw new ParcelFormatException(VibrationParam$1$$ExternalSyntheticOutline0.m(readInt20, "File corrupt: too many job timers "));
            }
            for (int i44 = 0; i44 < readInt20; i44++) {
                uid.readJobSummaryFromParcelLocked(parcel.readString(), parcel);
            }
            uid.readJobCompletionsFromParcelLocked(parcel);
            uid.mJobsDeferredEventCount.readSummaryFromParcelLocked(parcel);
            uid.mJobsDeferredCount.readSummaryFromParcelLocked(parcel);
            LongSamplingCounter longSamplingCounter24 = uid.mJobsFreshnessTimeMs;
            longSamplingCounter24.getClass();
            longSamplingCounter24.mCount = parcel.readLong();
            detachIfNotNull(uid.mJobsFreshnessBuckets);
            for (int i45 = 0; i45 < BatteryStats.JOB_FRESHNESS_BUCKETS.length; i45++) {
                if (parcel.readInt() != 0) {
                    uid.mJobsFreshnessBuckets[i45] = new Counter(uid.mBsi.mOnBatteryTimeBase);
                    uid.mJobsFreshnessBuckets[i45].readSummaryFromParcelLocked(parcel);
                }
            }
            int readInt21 = parcel.readInt();
            if (readInt21 > 1000) {
                throw new ParcelFormatException(VibrationParam$1$$ExternalSyntheticOutline0.m(readInt21, "File corrupt: too many sensors "));
            }
            for (int i46 = 0; i46 < readInt21; i46++) {
                int readInt22 = parcel.readInt();
                if (parcel.readInt() != 0) {
                    uid.getSensorTimerLocked(readInt22, true).readSummaryFromParcelLocked(parcel);
                }
            }
            int readInt23 = parcel.readInt();
            if (readInt23 > 10000) {
                throw new ParcelFormatException(VibrationParam$1$$ExternalSyntheticOutline0.m(readInt23, "File corrupt: too many processes "));
            }
            for (int i47 = 0; i47 < readInt23; i47++) {
                Uid.Proc processStatsLocked = uid.getProcessStatsLocked(parcel.readString());
                processStatsLocked.mUserTimeMs = parcel.readLong();
                processStatsLocked.mSystemTimeMs = parcel.readLong();
                processStatsLocked.mForegroundTimeMs = parcel.readLong();
                processStatsLocked.mStarts = parcel.readInt();
                processStatsLocked.mNumCrashes = parcel.readInt();
                processStatsLocked.mNumAnrs = parcel.readInt();
                processStatsLocked.readExcessivePowerFromParcelLocked(parcel);
            }
            int readInt24 = parcel.readInt();
            int i48 = 10000;
            if (readInt24 > 10000) {
                throw new ParcelFormatException(VibrationParam$1$$ExternalSyntheticOutline0.m(readInt24, "File corrupt: too many packages "));
            }
            int i49 = 0;
            while (i49 < readInt24) {
                String readString5 = parcel.readString();
                detachIfNotNull((Uid.Pkg) uid.mPackageStats.get(readString5));
                Uid.Pkg packageStatsLocked = uid.getPackageStatsLocked(readString5);
                int readInt25 = parcel.readInt();
                if (readInt25 > i48) {
                    throw new ParcelFormatException(VibrationParam$1$$ExternalSyntheticOutline0.m(readInt25, "File corrupt: too many wakeup alarms "));
                }
                packageStatsLocked.mWakeupAlarms.clear();
                for (int i50 = 0; i50 < readInt25; i50++) {
                    String readString6 = parcel.readString();
                    Counter counter = new Counter(this.mOnBatteryScreenOffTimeBase);
                    counter.readSummaryFromParcelLocked(parcel);
                    packageStatsLocked.mWakeupAlarms.put(readString6, counter);
                }
                int readInt26 = parcel.readInt();
                if (readInt26 > 10000) {
                    throw new ParcelFormatException(VibrationParam$1$$ExternalSyntheticOutline0.m(readInt26, "File corrupt: too many services "));
                }
                int i51 = 0;
                while (i51 < readInt26) {
                    Uid.Pkg.Serv serviceStatsLocked = uid.getServiceStatsLocked(readString5, parcel.readString());
                    serviceStatsLocked.mStartTimeMs = parcel.readLong();
                    serviceStatsLocked.mStarts = parcel.readInt();
                    serviceStatsLocked.mLaunches = parcel.readInt();
                    i51++;
                    j = j;
                }
                i49++;
                i48 = 10000;
            }
            i32++;
            elapsedRealtime = j;
            z = 0;
            i2 = 5;
        }
        if (Flags.disableSystemServicePowerAttr()) {
            return;
        }
        this.mBinderThreadCpuTimesUs = LongSamplingCounterArray.readSummaryFromParcelLocked(parcel, this.mOnBatteryTimeBase);
    }

    public NetworkStats readWifiNetworkStatsLocked(NetworkStatsManager networkStatsManager) {
        return networkStatsManager.getWifiUidStats();
    }

    public final void recordDailyStatsIfNeededLocked(long j, boolean z) {
        if (j >= this.mNextMaxDailyDeadlineMs) {
            recordDailyStatsLocked();
            return;
        }
        if (z && j >= this.mNextMinDailyDeadlineMs) {
            recordDailyStatsLocked();
        } else if (j < this.mDailyStartTimeMs - BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS) {
            recordDailyStatsLocked();
        }
    }

    public final void recordDailyStatsLocked() {
        boolean z;
        BatteryStats.DailyItem dailyItem = new BatteryStats.DailyItem();
        dailyItem.mStartTime = this.mDailyStartTimeMs;
        dailyItem.mEndTime = this.mClock.currentTimeMillis();
        boolean z2 = true;
        if (this.mDailyDischargeStepTracker.mNumStepDurations > 0) {
            BatteryStats.LevelStepTracker levelStepTracker = this.mDailyDischargeStepTracker;
            dailyItem.mDischargeSteps = new BatteryStats.LevelStepTracker(levelStepTracker.mNumStepDurations, levelStepTracker.mStepDurations);
            z = true;
        } else {
            z = false;
        }
        if (this.mDailyChargeStepTracker.mNumStepDurations > 0) {
            BatteryStats.LevelStepTracker levelStepTracker2 = this.mDailyChargeStepTracker;
            dailyItem.mChargeSteps = new BatteryStats.LevelStepTracker(levelStepTracker2.mNumStepDurations, levelStepTracker2.mStepDurations);
            z = true;
        }
        ArrayList arrayList = this.mDailyPackageChanges;
        if (arrayList != null) {
            dailyItem.mPackageChanges = arrayList;
            this.mDailyPackageChanges = null;
        } else {
            z2 = z;
        }
        this.mDailyDischargeStepTracker.init();
        this.mDailyChargeStepTracker.init();
        updateDailyDeadlineLocked();
        if (z2) {
            long uptimeMillis = SystemClock.uptimeMillis();
            this.mDailyItems.add(dailyItem);
            while (this.mDailyItems.size() > 10) {
                this.mDailyItems.remove(0);
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                writeDailyItemsLocked(Xml.resolveSerializer(byteArrayOutputStream));
                BackgroundThread.getHandler().post(new AnonymousClass7(this, byteArrayOutputStream, SystemClock.uptimeMillis() - uptimeMillis, 0));
            } catch (IOException unused) {
            }
        }
    }

    public final void removeCpuStatsForUidRangeLocked(int i, int i2) {
        if (i == i2) {
            this.mCpuUidUserSysTimeReader.removeUid(i);
            this.mCpuUidFreqTimeReader.removeUid(i);
            if (this.mConstants.TRACK_CPU_ACTIVE_CLUSTER_TIME) {
                this.mCpuUidActiveTimeReader.removeUid(i);
                this.mCpuUidClusterTimeReader.removeUid(i);
            }
            KernelSingleUidTimeReader kernelSingleUidTimeReader = this.mKernelSingleUidTimeReader;
            if (kernelSingleUidTimeReader != null) {
                kernelSingleUidTimeReader.removeUid(i);
            }
            this.mNumUidsRemoved++;
            return;
        }
        if (i >= i2) {
            PendingIntentController$$ExternalSyntheticOutline0.m(i2, i, "End UID ", " is smaller than start UID ", "BatteryStatsImpl");
            return;
        }
        this.mCpuUidFreqTimeReader.removeUidsInRange(i, i2);
        this.mCpuUidUserSysTimeReader.removeUidsInRange(i, i2);
        if (this.mConstants.TRACK_CPU_ACTIVE_CLUSTER_TIME) {
            this.mCpuUidActiveTimeReader.removeUidsInRange(i, i2);
            this.mCpuUidClusterTimeReader.removeUidsInRange(i, i2);
        }
        KernelSingleUidTimeReader kernelSingleUidTimeReader2 = this.mKernelSingleUidTimeReader;
        if (kernelSingleUidTimeReader2 != null) {
            kernelSingleUidTimeReader2.removeUidsInRange(i, i2);
        }
        PowerStatsUidResolver powerStatsUidResolver = this.mPowerStatsUidResolver;
        synchronized (powerStatsUidResolver) {
            try {
                int indexOfKey = powerStatsUidResolver.mIsolatedUids.indexOfKey(i);
                int indexOfKey2 = powerStatsUidResolver.mIsolatedUids.indexOfKey(i2);
                if (indexOfKey < 0) {
                    indexOfKey = ~indexOfKey;
                }
                if (indexOfKey2 < 0) {
                    indexOfKey2 = (~indexOfKey2) - 1;
                }
                if (indexOfKey <= indexOfKey2) {
                    IntArray intArray = new IntArray(indexOfKey2 - indexOfKey);
                    while (indexOfKey <= indexOfKey2) {
                        intArray.add(powerStatsUidResolver.mIsolatedUids.keyAt(indexOfKey));
                        indexOfKey++;
                    }
                    for (int size = intArray.size() - 1; size >= 0; size--) {
                        powerStatsUidResolver.releaseIsolatedUid(intArray.get(size));
                    }
                }
            } finally {
            }
        }
        this.mNumUidsRemoved++;
    }

    public final void removeUidStatsLocked(int i, long j) {
        Uid uid = (Uid) this.mUidStats.get(i);
        if (uid != null) {
            uid.detachFromTimeBase();
        }
        this.mUidStats.remove(i);
        this.mPendingRemovedUids.add(new UidToRemove(i, i, j));
    }

    public final void reportExcessiveCpuLocked(int i, long j, long j2, String str) {
        Uid uid = (Uid) this.mUidStats.get(mapUid(i));
        if (uid != null) {
            Uid.Proc processStatsLocked = uid.getProcessStatsLocked(str);
            if (processStatsLocked.mExcessivePower == null) {
                processStatsLocked.mExcessivePower = new ArrayList();
            }
            BatteryStats.Uid.Proc.ExcessivePower excessivePower = new BatteryStats.Uid.Proc.ExcessivePower();
            excessivePower.type = 2;
            excessivePower.overTime = j;
            excessivePower.usedTime = j2;
            processStatsLocked.mExcessivePower.add(excessivePower);
        }
    }

    public final void resetAllStatsAndHistoryLocked(int i) {
        long uptimeMillis = this.mClock.uptimeMillis();
        long j = uptimeMillis * 1000;
        long elapsedRealtime = this.mClock.elapsedRealtime();
        long j2 = elapsedRealtime * 1000;
        resetAllStatsLocked(i, uptimeMillis, elapsedRealtime);
        pullPendingStateUpdatesLocked();
        this.mHistory.writeHistoryItem(elapsedRealtime, uptimeMillis);
        int i2 = this.mBatteryLevel;
        this.mDischargePlugLevel = i2;
        this.mDischargeUnplugLevel = i2;
        this.mDischargeCurrentLevel = i2;
        TimeBase timeBase = this.mOnBatteryTimeBase;
        if (timeBase.mRunning) {
            timeBase.mUptimeStartUs = j;
            timeBase.mRealtimeStartUs = j2;
        } else {
            timeBase.mPastUptimeUs = 0L;
            timeBase.mPastRealtimeUs = 0L;
        }
        TimeBase timeBase2 = this.mOnBatteryScreenOffTimeBase;
        if (timeBase2.mRunning) {
            timeBase2.mUptimeStartUs = j;
            timeBase2.mRealtimeStartUs = j2;
        } else {
            timeBase2.mPastUptimeUs = 0L;
            timeBase2.mPastRealtimeUs = 0L;
        }
        if (!this.mBatteryPluggedIn) {
            int batteryPermil = getBatteryPermil();
            int batteryCCInfo = getBatteryCCInfo();
            if (Display.isOnState(this.mScreenState)) {
                this.mDischargeScreenOnUnplugLevel = this.mBatteryLevel;
                this.mDischargeScreenDozeUnplugLevel = 0;
                this.mDischargeScreenOffUnplugLevel = 0;
                this.mDischargeScreenOnUnplugLevelPermil = batteryPermil;
                this.mDischargeScreenDozeUnplugLevelPermil = 0;
                this.mDischargeScreenOffUnplugLevelPermil = 0;
                this.mDischargeScreenOnUnplugLevelCoulombCounter = batteryCCInfo;
                this.mDischargeScreenOffUnplugLevelCoulombCounter = 0;
            } else if (Display.isDozeState(this.mScreenState)) {
                this.mDischargeScreenOnUnplugLevel = 0;
                this.mDischargeScreenDozeUnplugLevel = this.mBatteryLevel;
                this.mDischargeScreenOffUnplugLevel = 0;
                this.mDischargeScreenOnUnplugLevelPermil = 0;
                this.mDischargeScreenDozeUnplugLevelPermil = batteryPermil;
                this.mDischargeScreenOffUnplugLevelPermil = 0;
                this.mDischargeScreenOnUnplugLevelCoulombCounter = 0;
                this.mDischargeScreenOffUnplugLevelCoulombCounter = batteryCCInfo;
            } else {
                this.mDischargeScreenOnUnplugLevel = 0;
                this.mDischargeScreenDozeUnplugLevel = 0;
                this.mDischargeScreenOffUnplugLevel = this.mBatteryLevel;
                this.mDischargeScreenOnUnplugLevelPermil = 0;
                this.mDischargeScreenDozeUnplugLevelPermil = 0;
                this.mDischargeScreenOffUnplugLevelPermil = batteryPermil;
                this.mDischargeScreenOnUnplugLevelCoulombCounter = 0;
                this.mDischargeScreenOffUnplugLevelCoulombCounter = batteryCCInfo;
            }
            if (this.mIsSubScreen) {
                if (Display.isOnState(this.mSubScreenState)) {
                    this.mDischargeSubScreenOnUnplugLevelPermil = batteryPermil;
                    this.mDischargeSubScreenDozeUnplugLevelPermil = 0;
                } else if (Display.isDozeState(this.mSubScreenState)) {
                    this.mDischargeSubScreenOnUnplugLevelPermil = 0;
                    this.mDischargeSubScreenDozeUnplugLevelPermil = batteryPermil;
                } else {
                    this.mDischargeSubScreenOnUnplugLevelPermil = 0;
                    this.mDischargeSubScreenDozeUnplugLevelPermil = 0;
                }
            }
            this.mDischargeAmountScreenOn = 0;
            this.mDischargeAmountScreenOff = 0;
            this.mDischargeAmountScreenDoze = 0;
        }
        initActiveHistoryEventsLocked(elapsedRealtime, uptimeMillis);
    }

    public final void resetAllStatsLocked(int i, long j, long j2) {
        int i2;
        BatteryUsageStats batteryUsageStats;
        if (this.mSaveBatteryUsageStatsOnReset && i != 1) {
            synchronized (this) {
                BatteryUsageStatsProvider batteryUsageStatsProvider = this.mBatteryUsageStatsProvider;
                batteryUsageStats = batteryUsageStatsProvider.getBatteryUsageStats(this, new BatteryUsageStatsQuery.Builder().setMaxStatsAgeMs(0L).includePowerModels().includeProcessStateData().build(), batteryUsageStatsProvider.mClock.currentTimeMillis());
            }
            this.mHandler.post(new BatteryStatsImpl$$ExternalSyntheticLambda0(this, this.mMonotonicClock.monotonicTime() - batteryUsageStats.getStatsDuration(), batteryUsageStats));
        }
        long j3 = j * 1000;
        long j4 = j2 * 1000;
        this.mStartCount = 0;
        initTimes(j3, j4);
        this.mScreenOnTimer.reset(j4, false);
        this.mScreenDozeTimer.reset(j4, false);
        for (int i3 = 0; i3 < 5; i3++) {
            this.mScreenBrightnessTimer[i3].reset(j4, false);
        }
        int length = this.mPerDisplayBatteryStats.length;
        for (int i4 = 0; i4 < length; i4++) {
            DisplayBatteryStats displayBatteryStats = this.mPerDisplayBatteryStats[i4];
            displayBatteryStats.screenOnTimer.reset(j4, false);
            displayBatteryStats.screenDozeTimer.reset(j4, false);
            for (int i5 = 0; i5 < 5; i5++) {
                displayBatteryStats.screenBrightnessTimers[i5].reset(j4, false);
            }
        }
        this.mSubScreenOnTimer.reset(j4, false);
        this.mSubScreenDozeTimer.reset(j4, false);
        for (int i6 = 0; i6 < 5; i6++) {
            this.mSubScreenBrightnessTimer[i6].reset(j4, false);
        }
        for (int i7 = 0; i7 < 5; i7++) {
            this.mScreenAutoBrightnessTimer[i7].reset();
        }
        for (int i8 = 0; i8 < 5; i8++) {
            this.mSubScreenAutoBrightnessTimer[i8].reset();
        }
        this.mScreenHighBrightnessTimer.reset();
        this.mSubScreenHighBrightnessTimer.reset();
        for (int i9 = 0; i9 < 4; i9++) {
            this.mHighRefreshRateTimer[i9].reset();
        }
        for (int i10 = 0; i10 < 4; i10++) {
            this.mSubHighRefreshRateTimer[i10].reset(j4, false);
        }
        for (int i11 = 0; i11 < 16; i11++) {
            this.mSpeakerMediaTimeCounters[i11].mCount = 0L;
            this.mSpeakerCallTimeCounters[i11].mCount = 0L;
        }
        this.mTxPowerSharingTimer.reset(j4, false);
        this.mTxPowerSharingDischargeMah = 0L;
        this.mMobileActiveTimer.reset();
        this.mMobileActive5GTimer.reset();
        this.mScreenOnGpsTimer.reset();
        PowerProfile powerProfile = this.mPowerProfile;
        if (powerProfile != null) {
            this.mEstimatedBatteryCapacityMah = (int) powerProfile.getBatteryCapacity();
        } else {
            this.mEstimatedBatteryCapacityMah = -1;
        }
        this.mLastLearnedBatteryCapacityUah = -1;
        this.mMinLearnedBatteryCapacityUah = -1;
        this.mMaxLearnedBatteryCapacityUah = -1;
        this.mInteractiveTimer.reset(j4, false);
        this.mPowerSaveModeEnabledTimer.reset(j4, false);
        this.mLastIdleTimeStartMs = j2;
        this.mLongestLightIdleTimeMs = 0L;
        this.mLongestFullIdleTimeMs = 0L;
        this.mDeviceIdleModeLightTimer.reset(j4, false);
        this.mDeviceIdleModeFullTimer.reset(j4, false);
        this.mDeviceLightIdlingTimer.reset(j4, false);
        this.mDeviceIdlingTimer.reset(j4, false);
        this.mPhoneOnTimer.reset(j4, false);
        this.mAudioOnTimer.reset(j4, false);
        this.mVideoOnTimer.reset(j4, false);
        this.mFlashlightOnTimer.reset(j4, false);
        this.mCameraOnTimer.reset(j4, false);
        this.mBluetoothScanTimer.reset(j4, false);
        for (int i12 = 0; i12 < CELL_SIGNAL_STRENGTH_LEVEL_COUNT; i12++) {
            this.mPhoneSignalStrengthsTimer[i12].reset(j4, false);
        }
        this.mPhoneSignalScanningTimer.reset(j4, false);
        for (int i13 = 0; i13 < BatteryStats.NUM_DATA_CONNECTION_TYPES; i13++) {
            this.mPhoneDataConnectionsTimer[i13].reset(j4, false);
        }
        this.mNrNsaTimer.reset(j4, false);
        for (int i14 = 0; i14 < 10; i14++) {
            this.mNetworkByteActivityCounters[i14].mCount = 0L;
            this.mNetworkPacketActivityCounters[i14].mCount = 0L;
        }
        for (int i15 = 0; i15 < 3; i15++) {
            RadioAccessTechnologyBatteryStats radioAccessTechnologyBatteryStats = this.mPerRatBatteryStats[i15];
            if (radioAccessTechnologyBatteryStats != null) {
                StopwatchTimer[][] stopwatchTimerArr = radioAccessTechnologyBatteryStats.perStateTimers;
                int length2 = stopwatchTimerArr.length;
                int i16 = 0;
                while (i16 < length2) {
                    int i17 = 0;
                    while (i17 < 5) {
                        stopwatchTimerArr[i16][i17].reset(j4, false);
                        LongSamplingCounter[][] longSamplingCounterArr = radioAccessTechnologyBatteryStats.mPerStateTxDurationMs;
                        if (longSamplingCounterArr == null) {
                            i2 = length2;
                        } else {
                            i2 = length2;
                            longSamplingCounterArr[i16][i17].mCount = 0L;
                        }
                        i17++;
                        length2 = i2;
                    }
                    int i18 = length2;
                    LongSamplingCounter[] longSamplingCounterArr2 = radioAccessTechnologyBatteryStats.mPerFrequencyRxDurationMs;
                    if (longSamplingCounterArr2 != null) {
                        longSamplingCounterArr2[i16].mCount = 0L;
                    }
                    i16++;
                    length2 = i18;
                }
            }
        }
        this.mMobileRadioActiveTimer.reset(j4, false);
        this.mMobileRadioActivePerAppTimer.reset(j4, false);
        this.mMobileRadioActiveAdjustedTime.mCount = 0L;
        this.mMobileRadioActiveUnknownTime.mCount = 0L;
        this.mMobileRadioActiveUnknownCount.mCount = 0L;
        this.mWifiOnTimer.reset(j4, false);
        this.mGlobalWifiRunningTimer.reset(j4, false);
        for (int i19 = 0; i19 < 8; i19++) {
            this.mWifiStateTimer[i19].reset(j4, false);
        }
        for (int i20 = 0; i20 < 13; i20++) {
            this.mWifiSupplStateTimer[i20].reset(j4, false);
        }
        for (int i21 = 0; i21 < 5; i21++) {
            this.mWifiSignalStrengthsTimer[i21].reset(j4, false);
        }
        this.mWifiMulticastWakelockTimer.reset(j4, false);
        this.mWifiActiveTimer.reset(j4, false);
        this.mWifiActivity.reset(j4);
        int i22 = 0;
        while (true) {
            StopwatchTimer[] stopwatchTimerArr2 = this.mGpsSignalQualityTimer;
            if (i22 >= stopwatchTimerArr2.length) {
                break;
            }
            stopwatchTimerArr2[i22].reset(j4, false);
            i22++;
        }
        this.mBluetoothActivity.reset(j4);
        this.mModemActivity.reset(j4);
        ModemActivityCounterImpl modemActivityCounterImpl = this.mNetworkModemActivity;
        modemActivityCounterImpl.mSleepTimeMillis.reset();
        modemActivityCounterImpl.mIdleTimeMillis.reset();
        modemActivityCounterImpl.mNrModemActivityInfo.reset();
        modemActivityCounterImpl.mLcModemActivityInfo.reset();
        this.mNumConnectivityChange = 0;
        int i23 = 0;
        while (i23 < this.mUidStats.size()) {
            long j5 = j4;
            if (((Uid) this.mUidStats.valueAt(i23)).reset(j3, j4, i)) {
                ((Uid) this.mUidStats.valueAt(i23)).detachFromTimeBase();
                SparseArray sparseArray = this.mUidStats;
                sparseArray.remove(sparseArray.keyAt(i23));
                i23--;
            }
            i23++;
            j4 = j5;
        }
        long j6 = j4;
        if (this.mRpmStats.size() > 0) {
            Iterator it = this.mRpmStats.values().iterator();
            while (it.hasNext()) {
                this.mOnBatteryTimeBase.remove((SamplingTimer) it.next());
            }
            this.mRpmStats.clear();
        }
        if (this.mScreenOffRpmStats.size() > 0) {
            Iterator it2 = this.mScreenOffRpmStats.values().iterator();
            while (it2.hasNext()) {
                this.mOnBatteryScreenOffTimeBase.remove((SamplingTimer) it2.next());
            }
            this.mScreenOffRpmStats.clear();
        }
        if (this.mKernelWakelockStats.size() > 0) {
            Iterator it3 = this.mKernelWakelockStats.values().iterator();
            while (it3.hasNext()) {
                this.mOnBatteryScreenOffTimeBase.remove((SamplingTimer) it3.next());
            }
            this.mKernelWakelockStats.clear();
        }
        if (this.mKernelMemoryStats.size() > 0) {
            for (int i24 = 0; i24 < this.mKernelMemoryStats.size(); i24++) {
                this.mOnBatteryTimeBase.remove((TimeBaseObs) this.mKernelMemoryStats.valueAt(i24));
            }
            this.mKernelMemoryStats.clear();
        }
        if (this.mWakeupReasonStats.size() > 0) {
            Iterator it4 = this.mWakeupReasonStats.values().iterator();
            while (it4.hasNext()) {
                this.mOnBatteryTimeBase.remove((SamplingTimer) it4.next());
            }
            this.mWakeupReasonStats.clear();
        }
        if (this.mScreenWakeStats.size() > 0) {
            Iterator it5 = this.mScreenWakeStats.values().iterator();
            while (it5.hasNext()) {
                this.mOnBatteryTimeBase.remove((Counter) it5.next());
            }
            this.mScreenWakeStats.clear();
        }
        RailStats railStats = this.mTmpRailStats;
        if (railStats != null) {
            railStats.reset();
        }
        EnergyConsumerStats.resetIfNotNull(this.mGlobalEnergyConsumerStats);
        if (!Flags.disableSystemServicePowerAttr()) {
            resetIfNotNull(this.mBinderThreadCpuTimesUs, j6);
        }
        this.mNumAllUidCpuTimeReads = 0;
        this.mNumUidsRemoved = 0;
        initDischarge();
        this.mHistory.reset();
        int i25 = this.mProtectBatteryMode;
        if (i25 < BatteryStats.NUM_PROTECT_BATTERY_MODE_TYPES) {
            this.mHistory.setProtectBatteryState(Math.max(i25, 0));
        }
        writeSyncLocked();
        if (this.mPowerStatsCollectorEnabled.get(1)) {
            schedulePowerStatsSampleCollection();
        }
        this.mIgnoreNextExternalStats = true;
        this.mExternalSync.scheduleSync(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT, "reset");
        this.mHandler.sendEmptyMessage(4);
    }

    public final void scheduleNextResetWhilePluggedInCheck() {
        if (this.mAlarmManager == null) {
            return;
        }
        long currentTimeMillis = (this.mConstants.RESET_WHILE_PLUGGED_IN_MINIMUM_DURATION_HOURS * ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS) + this.mClock.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);
        calendar.set(14, 0);
        calendar.set(13, 0);
        calendar.set(12, 0);
        calendar.set(11, 2);
        long timeInMillis = calendar.getTimeInMillis();
        if (timeInMillis < currentTimeMillis) {
            timeInMillis += BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        }
        this.mHandler.post(new BatteryStatsImpl$$ExternalSyntheticLambda0(this, this.mAlarmManager, timeInMillis));
    }

    public final void schedulePowerStatsSampleCollection() {
        if (this.mSystemReady) {
            this.mCpuPowerStatsCollector.forceSchedule();
            this.mMobileRadioPowerStatsCollector.forceSchedule();
            this.mWifiPowerStatsCollector.forceSchedule();
            this.mBluetoothPowerStatsCollector.forceSchedule();
            this.mCameraPowerStatsCollector.forceSchedule();
            this.mGnssPowerStatsCollector.forceSchedule();
            this.mCustomEnergyConsumerPowerStatsCollector.forceSchedule();
        }
    }

    public final void scheduleSyncExternalStatsLocked(int i, String str) {
        BatteryExternalStatsWorker batteryExternalStatsWorker = this.mExternalSync;
        if (batteryExternalStatsWorker != null) {
            batteryExternalStatsWorker.scheduleSync(i, str);
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(9:112|(23:(2:114|(26:116|117|118|119|120|121|123|124|(4:214|215|216|217)(6:126|127|128|129|(12:131|132|133|134|135|136|137|138|139|140|(5:142|143|(1:(5:145|146|(3:148|149|(2:151|(3:155|156|157))(2:179|180))(1:181)|153|154)(2:194|195))|158|(3:160|(3:162|(2:164|165)(1:167)|166)|168))(1:197)|169)(3:207|208|209)|170)|171|(1:173)|174|(1:177)|178|52|(1:54)|55|(1:57)(1:107)|58|59|(1:61)|62|(1:64)(2:103|(1:105)(1:106))|65|(2:67|(1:69)(2:70|(1:72)(1:73)))|74))(1:252)|120|121|123|124|(0)(0)|171|(0)|174|(1:177)|178|52|(0)|55|(0)(0)|58|59|(0)|62|(0)(0)|65|(0)|74)|242|(1:246)|248|(1:250)|117|118|119) */
    /* JADX WARN: Code restructure failed: missing block: B:236:0x038c, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:237:0x03fe, code lost:
    
        r0.printStackTrace();
        r2 = r2;
        r8 = r8;
        r19 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:238:0x0258, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:239:0x0259, code lost:
    
        r2 = "BatteryStatsImpl";
        r19 = r7;
        r26 = r26 ? 1 : 0;
        r45 = r17;
        r28 = r5;
        r8 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:247:0x01c3, code lost:
    
        if (r46 >= 80) goto L99;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x04e2  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x049c  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x027b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:173:0x040e  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0442 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:214:0x0243 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:253:0x0539  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x05af  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0469  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x048c  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x04a5  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x04d0  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x050e  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0594  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x077a  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x078e  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x07c2  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x07c5  */
    /* JADX WARN: Type inference failed for: r19v1 */
    /* JADX WARN: Type inference failed for: r19v10 */
    /* JADX WARN: Type inference failed for: r19v11 */
    /* JADX WARN: Type inference failed for: r19v12 */
    /* JADX WARN: Type inference failed for: r19v13 */
    /* JADX WARN: Type inference failed for: r19v14 */
    /* JADX WARN: Type inference failed for: r19v16 */
    /* JADX WARN: Type inference failed for: r19v18 */
    /* JADX WARN: Type inference failed for: r19v22 */
    /* JADX WARN: Type inference failed for: r19v3 */
    /* JADX WARN: Type inference failed for: r19v32 */
    /* JADX WARN: Type inference failed for: r19v4 */
    /* JADX WARN: Type inference failed for: r19v5 */
    /* JADX WARN: Type inference failed for: r19v7 */
    /* JADX WARN: Type inference failed for: r19v8 */
    /* JADX WARN: Type inference failed for: r19v9 */
    /* JADX WARN: Type inference failed for: r22v0, types: [int] */
    /* JADX WARN: Type inference failed for: r22v1 */
    /* JADX WARN: Type inference failed for: r22v13, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r22v2 */
    /* JADX WARN: Type inference failed for: r22v3 */
    /* JADX WARN: Type inference failed for: r22v5 */
    /* JADX WARN: Type inference failed for: r22v6 */
    /* JADX WARN: Type inference failed for: r22v7 */
    /* JADX WARN: Type inference failed for: r22v9 */
    /* JADX WARN: Type inference failed for: r2v65, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r2v66 */
    /* JADX WARN: Type inference failed for: r2v67 */
    /* JADX WARN: Type inference failed for: r2v68 */
    /* JADX WARN: Type inference failed for: r2v70, types: [java.io.PrintWriter] */
    /* JADX WARN: Type inference failed for: r2v71 */
    /* JADX WARN: Type inference failed for: r2v72 */
    /* JADX WARN: Type inference failed for: r2v73 */
    /* JADX WARN: Type inference failed for: r2v76 */
    /* JADX WARN: Type inference failed for: r2v80 */
    /* JADX WARN: Type inference failed for: r2v81 */
    /* JADX WARN: Type inference failed for: r2v82, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v14 */
    /* JADX WARN: Type inference failed for: r8v15 */
    /* JADX WARN: Type inference failed for: r8v16 */
    /* JADX WARN: Type inference failed for: r8v27 */
    /* JADX WARN: Type inference failed for: r8v36 */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setBatteryStateLocked(int r43, int r44, int r45, int r46, int r47, int r48, int r49, int r50, long r51, int r53, int r54, int r55, int r56, int r57, long r58, long r60, long r62) {
        /*
            Method dump skipped, instructions count: 2008
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.stats.BatteryStatsImpl.setBatteryStateLocked(int, int, int, int, int, int, int, int, long, int, int, int, int, int, long, long, long):void");
    }

    public final boolean setChargingLocked(boolean z) {
        this.mHandler.removeCallbacks(this.mDeferSetCharging);
        if (this.mCharging == z) {
            return false;
        }
        this.mCharging = z;
        this.mHistory.setChargingState(z);
        this.mHandler.sendEmptyMessage(3);
        return true;
    }

    public final void setPowerStatsCollectorEnabled(int i, boolean z) {
        synchronized (this) {
            this.mPowerStatsCollectorEnabled.put(i, z);
        }
    }

    public final void setRecordAllHistoryLocked(boolean z) {
        this.mRecordAllHistory = z;
        if (z) {
            HashMap stateForEvent = this.mActiveEvents.getStateForEvent(1);
            if (stateForEvent != null) {
                long elapsedRealtime = this.mClock.elapsedRealtime();
                long uptimeMillis = this.mClock.uptimeMillis();
                for (Map.Entry entry : stateForEvent.entrySet()) {
                    int i = 0;
                    for (SparseIntArray sparseIntArray = (SparseIntArray) entry.getValue(); i < sparseIntArray.size(); sparseIntArray = sparseIntArray) {
                        this.mHistory.recordEvent(elapsedRealtime, uptimeMillis, 32769, (String) entry.getKey(), sparseIntArray.keyAt(i));
                        i++;
                    }
                }
                return;
            }
            return;
        }
        this.mActiveEvents.removeEvents(5);
        this.mActiveEvents.removeEvents(13);
        HashMap stateForEvent2 = this.mActiveEvents.getStateForEvent(1);
        if (stateForEvent2 != null) {
            long elapsedRealtime2 = this.mClock.elapsedRealtime();
            long uptimeMillis2 = this.mClock.uptimeMillis();
            for (Map.Entry entry2 : stateForEvent2.entrySet()) {
                int i2 = 0;
                for (SparseIntArray sparseIntArray2 = (SparseIntArray) entry2.getValue(); i2 < sparseIntArray2.size(); sparseIntArray2 = sparseIntArray2) {
                    this.mHistory.recordEvent(elapsedRealtime2, uptimeMillis2, 16385, (String) entry2.getKey(), sparseIntArray2.keyAt(i2));
                    i2++;
                }
            }
        }
    }

    public final void setTemperatureNCurrentLocked(int i, int i2, int i3, int i4, int i5, long j, long j2) {
        boolean z;
        boolean z2 = true;
        if (this.mAp_temp != i) {
            this.mAp_temp = (byte) i;
            z = true;
        } else {
            z = false;
        }
        if (this.mPa_temp != i2) {
            this.mPa_temp = (byte) i2;
            z = true;
        }
        if (this.mSkin_temp != i3) {
            this.mSkin_temp = (byte) i3;
            z = true;
        }
        if (this.mCurrent != i5) {
            this.mCurrent = (short) i5;
        } else {
            z2 = z;
        }
        if (z2) {
            this.mHistory.setTemperatureNCurrent(this.mAp_temp, this.mPa_temp, this.mSkin_temp, this.mSub_batt_temp, this.mCurrent);
            this.mHistory.writeHistoryItem(j, j2);
        }
    }

    public final boolean startAddingCpuStatsLocked() {
        BatteryExternalStatsWorker batteryExternalStatsWorker = this.mExternalSync;
        synchronized (batteryExternalStatsWorker) {
            try {
                Future future = batteryExternalStatsWorker.mWakelockChangesUpdate;
                if (future != null) {
                    future.cancel(false);
                    batteryExternalStatsWorker.mWakelockChangesUpdate = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this.mOnBatteryInternal;
    }

    public final void startTrackingSystemServerCpuTime() {
        this.mSystemServerCpuThreadReader.mKernelCpuThreadReader.startTrackingThreadCpuTimes();
    }

    public final void stopAllGpsSignalQualityTimersLocked(long j) {
        for (int i = 0; i < this.mGpsSignalQualityTimer.length; i++) {
            if (i != -1) {
                while (this.mGpsSignalQualityTimer[i].isRunningLocked()) {
                    this.mGpsSignalQualityTimer[i].stopRunningLocked(j);
                }
            }
        }
    }

    public final void stopAllSubDisplayHighRefreshRateTimersLocked(long j) {
        for (int i = 0; i < 4; i++) {
            if (i != -1) {
                while (this.mSubHighRefreshRateTimer[i].isRunningLocked()) {
                    this.mSubHighRefreshRateTimer[i].stopRunningLocked(j);
                }
            }
        }
    }

    public final void systemServicesReady(Context context) {
        File[] listFiles;
        Constants constants = this.mConstants;
        ContentResolver contentResolver = context.getContentResolver();
        constants.mResolver = contentResolver;
        contentResolver.registerContentObserver(Settings.Global.getUriFor("battery_stats_constants"), false, constants);
        constants.mResolver.registerContentObserver(Settings.Global.getUriFor("battery_charging_state_update_delay"), false, constants);
        constants.mResolver.registerContentObserver(Settings.Global.getUriFor("battery_charging_state_enforce_level"), false, constants);
        constants.mResolver.registerContentObserver(Settings.Global.getUriFor("protect_battery"), false, constants);
        constants.mResolver.registerContentObserver(Settings.System.getUriFor("screen_brightness_mode"), false, constants);
        constants.mResolver.registerContentObserver(Settings.Secure.getUriFor("refresh_rate_mode"), false, constants);
        constants.updateConstants();
        try {
            BatteryStatsImpl.this.noteScreenAutoBrightnessLocked(Settings.System.getInt(constants.mResolver, "screen_brightness_mode"));
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        constants.updateDisplayHighRefreshRateLocked();
        constants.updateProtectBatteryModeLocked();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.hardware.usb.action.USB_STATE");
        context.registerReceiver(new AnonymousClass4(this, 0), intentFilter);
        synchronized (this) {
            try {
                if (this.mUsbDataState == 0) {
                    Intent registerReceiver = context.registerReceiver(null, intentFilter);
                    noteUsbConnectionStateLocked(this.mClock.elapsedRealtime(), this.mClock.uptimeMillis(), registerReceiver != null && registerReceiver.getBooleanExtra("connected", false));
                }
            } finally {
            }
        }
        IntentFilter m = BatteryService$$ExternalSyntheticOutline0.m("android.net.wifi.WIFI_AP_STATE_CHANGED");
        context.registerReceiver(new AnonymousClass4(this, 1), m);
        synchronized (this) {
            try {
                if (this.mUsbDataState == 14) {
                    Intent registerReceiver2 = context.registerReceiver(null, m);
                    int intExtra = registerReceiver2 != null ? registerReceiver2.getIntExtra("wifi_state", 14) : 14;
                    long elapsedRealtime = this.mClock.elapsedRealtime();
                    long uptimeMillis = this.mClock.uptimeMillis();
                    boolean z = intExtra == 13;
                    if (this.mHotspotState != intExtra) {
                        this.mHotspotState = intExtra;
                        this.mHistory.setWifiApState(z);
                        this.mHistory.writeHistoryItem(elapsedRealtime, uptimeMillis);
                    }
                }
            } finally {
            }
        }
        this.mSystemServicesReady = true;
        if (SystemProperties.get("ro.build.type").equals("user")) {
            this.mIsBuildTypeUser = true;
        }
        long elapsedRealtime2 = this.mClock.elapsedRealtime();
        this.mLastBatteryCCUpdateTime = elapsedRealtime2;
        this.mLastBatteryRawUpdateTime = elapsedRealtime2;
        if (this.mPendingReportCharging) {
            this.mHandler.sendEmptyMessage(3);
            this.mPendingReportCharging = false;
        }
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
        this.mCustomTelephonyCallback = new CustomTelephonyCallback();
        this.mTelephonyManager.registerTelephonyCallback(new HandlerExecutor(this.mHandler), this.mCustomTelephonyCallback);
        String[] list = this.mSystemDir.list();
        if (list != null) {
            for (String str : list) {
                if (str.startsWith("BCheck")) {
                    File file = new File(this.mSystemDir, str);
                    StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(str, " has been deleted (");
                    m2.append(file.delete());
                    m2.append(")");
                    Slog.i("BatteryStatsImpl", m2.toString());
                } else if (str.equals("batterystats.bin")) {
                    Slog.i("BatteryStatsImpl", "stats, " + new File(this.mSystemDir, str).length() + "bytes");
                } else if (str.equals("batterystats-daily.xml")) {
                    Slog.i("BatteryStatsImpl", "daily, " + new File(this.mSystemDir, str).length() + "bytes");
                } else if (str.equals("battery-history") && (listFiles = new File(this.mSystemDir, str).listFiles()) != null) {
                    long j = 0;
                    for (File file2 : listFiles) {
                        j += file2.length();
                    }
                    StringBuilder sb = new StringBuilder("history, ");
                    sb.append(listFiles.length);
                    sb.append("files, ");
                    sb.append(j);
                    DeviceIdleController$$ExternalSyntheticOutline0.m(sb, "bytes", "BatteryStatsImpl");
                }
            }
        }
        if (!new File("/sys/class/power_supply/battery", "time_to_full_now").exists()) {
            this.mFeatureComputeChargeTimeModel = false;
        }
        synchronized (this) {
            try {
                this.mAlarmManager = (AlarmManager) context.getSystemService(AlarmManager.class);
                if (this.mBatteryPluggedIn) {
                    scheduleNextResetWhilePluggedInCheck();
                }
            } finally {
            }
        }
    }

    public final void updateAllPhoneStateLocked(int i, int i2, int i3, long j, long j2) {
        int i4;
        boolean z;
        boolean z2;
        int i5;
        int i6 = i;
        int i7 = i3;
        this.mPhoneServiceStateRaw = i6;
        this.mPhoneSimStateRaw = i2;
        this.mPhoneSignalStrengthBinRaw = i7;
        boolean z3 = true;
        if (i2 == 1 && i6 == 1 && i7 > 0) {
            i6 = 0;
        }
        int i8 = 2097152;
        int i9 = -1;
        if (i6 == 3) {
            i4 = 0;
            z = false;
            z2 = false;
            i7 = -1;
        } else if (i6 == 0 || i6 != 1) {
            i4 = 0;
            z = false;
            z2 = false;
        } else if (this.mPhoneSignalScanningTimer.isRunningLocked()) {
            i7 = 0;
            i4 = 0;
            z2 = false;
            z = true;
        } else {
            this.mPhoneSignalScanningTimer.startRunningLocked(j);
            this.mFrameworkStatsLogger.getClass();
            FrameworkStatsLog.write(94, i6, i2, 0);
            i7 = 0;
            z = true;
            z2 = true;
            i4 = 2097152;
        }
        if (z || !this.mPhoneSignalScanningTimer.isRunningLocked()) {
            i8 = 0;
        } else {
            this.mPhoneSignalScanningTimer.stopRunningLocked(j);
            this.mFrameworkStatsLogger.getClass();
            FrameworkStatsLog.write(94, i6, i2, i7);
            z2 = true;
        }
        if (this.mPhoneServiceState != i6) {
            this.mPhoneServiceState = i6;
            i5 = i6;
            z2 = true;
        } else {
            i5 = -1;
        }
        int i10 = this.mPhoneSignalStrengthBin;
        if (i10 != i7) {
            if (i10 >= 0) {
                this.mPhoneSignalStrengthsTimer[i10].stopRunningLocked(j);
            }
            if (i7 >= 0) {
                if (!this.mPhoneSignalStrengthsTimer[i7].isRunningLocked()) {
                    this.mPhoneSignalStrengthsTimer[i7].startRunningLocked(j);
                }
                this.mFrameworkStatsLogger.getClass();
                FrameworkStatsLog.write(40, i7);
                i9 = i7;
            } else {
                for (int i11 = 0; i11 < CELL_SIGNAL_STRENGTH_LEVEL_COUNT; i11++) {
                    if (i11 != -1) {
                        while (this.mPhoneSignalStrengthsTimer[i11].isRunningLocked()) {
                            this.mPhoneSignalStrengthsTimer[i11].stopRunningLocked(j);
                        }
                    }
                }
                z3 = z2;
            }
            this.mPhoneSignalStrengthBin = i7;
            z2 = z3;
        }
        if (z2) {
            this.mHistory.recordPhoneStateChangeEvent(j, j2, i4, i8, i5, i9);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:99:0x0259, code lost:
    
        if (r27 != 0) goto L90;
     */
    /* JADX WARN: Removed duplicated region for block: B:88:0x036d  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x038e  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x03a3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateBluetoothStateLocked(android.bluetooth.BluetoothActivityEnergyInfo r45, long r46, long r48, long r50) {
        /*
            Method dump skipped, instructions count: 986
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.stats.BatteryStatsImpl.updateBluetoothStateLocked(android.bluetooth.BluetoothActivityEnergyInfo, long, long, long):void");
    }

    public final void updateCameraEnergyConsumerStatsLocked(long j, long j2) {
        long timeSinceMarkLocked;
        EnergyConsumerStats energyConsumerStats = this.mGlobalEnergyConsumerStats;
        if (energyConsumerStats != null && this.mOnBatteryInternal && j > 0) {
            int i = 0;
            if (this.mIgnoreNextExternalStats) {
                int size = this.mUidStats.size();
                while (i < size) {
                    StopwatchTimer stopwatchTimer = ((Uid) this.mUidStats.valueAt(i)).mCameraTurnedOnTimer;
                    if (stopwatchTimer != null) {
                        stopwatchTimer.getTimeSinceMarkLocked(j2 * 1000);
                        stopwatchTimer.setMark(j2);
                    }
                    i++;
                }
                return;
            }
            energyConsumerStats.updateStandardBucket(8, j);
            SparseDoubleArray sparseDoubleArray = new SparseDoubleArray();
            int size2 = this.mUidStats.size();
            while (i < size2) {
                Uid uid = (Uid) this.mUidStats.valueAt(i);
                StopwatchTimer stopwatchTimer2 = uid.mCameraTurnedOnTimer;
                if (stopwatchTimer2 == null) {
                    timeSinceMarkLocked = 0;
                } else {
                    timeSinceMarkLocked = stopwatchTimer2.getTimeSinceMarkLocked(j2 * 1000);
                    stopwatchTimer2.setMark(j2);
                }
                if (timeSinceMarkLocked != 0) {
                    sparseDoubleArray.put(uid.mUid, timeSinceMarkLocked);
                }
                i++;
            }
            distributeEnergyToUidsLocked(8, j, sparseDoubleArray, 0.0d, j2);
        }
    }

    public void updateClusterSpeedTimes(SparseLongArray sparseLongArray, boolean z, CpuDeltaPowerAccumulator cpuDeltaPowerAccumulator) {
        int i;
        SparseLongArray sparseLongArray2 = sparseLongArray;
        long[][] jArr = new long[this.mKernelCpuSpeedReaders.length][];
        long j = 0;
        int i2 = 0;
        while (true) {
            KernelCpuSpeedReader[] kernelCpuSpeedReaderArr = this.mKernelCpuSpeedReaders;
            if (i2 >= kernelCpuSpeedReaderArr.length) {
                break;
            }
            KernelCpuSpeedReader kernelCpuSpeedReader = kernelCpuSpeedReaderArr[i2];
            if (kernelCpuSpeedReader != null) {
                long[] readDelta = kernelCpuSpeedReader.readDelta();
                jArr[i2] = readDelta;
                if (readDelta != null) {
                    for (int length = readDelta.length - 1; length >= 0; length--) {
                        j += jArr[i2][length];
                    }
                }
            }
            i2++;
        }
        if (j != 0) {
            int size = sparseLongArray.size();
            long elapsedRealtime = this.mClock.elapsedRealtime();
            long uptimeMillis = this.mClock.uptimeMillis();
            int i3 = 0;
            while (i3 < size) {
                int i4 = i3;
                Uid uidStatsLocked = getUidStatsLocked(sparseLongArray2.keyAt(i3), elapsedRealtime, uptimeMillis);
                long valueAt = sparseLongArray2.valueAt(i4);
                int[] policies = this.mCpuScalingPolicies.getPolicies();
                LongSamplingCounter[][] longSamplingCounterArr = uidStatsLocked.mCpuClusterSpeedTimesUs;
                if (longSamplingCounterArr == null || longSamplingCounterArr.length != policies.length) {
                    uidStatsLocked.mCpuClusterSpeedTimesUs = new LongSamplingCounter[policies.length][];
                }
                int i5 = 0;
                while (i5 < policies.length) {
                    int length2 = jArr[i5].length;
                    int[] iArr = policies;
                    LongSamplingCounter[][] longSamplingCounterArr2 = uidStatsLocked.mCpuClusterSpeedTimesUs;
                    LongSamplingCounter[] longSamplingCounterArr3 = longSamplingCounterArr2[i5];
                    if (longSamplingCounterArr3 == null || length2 != longSamplingCounterArr3.length) {
                        longSamplingCounterArr2[i5] = new LongSamplingCounter[length2];
                    }
                    LongSamplingCounter[] longSamplingCounterArr4 = longSamplingCounterArr2[i5];
                    int i6 = 0;
                    while (i6 < length2) {
                        int i7 = length2;
                        if (longSamplingCounterArr4[i6] == null) {
                            i = size;
                            longSamplingCounterArr4[i6] = new LongSamplingCounter(this.mOnBatteryTimeBase);
                        } else {
                            i = size;
                        }
                        long j2 = valueAt;
                        long j3 = (jArr[i5][i6] * valueAt) / j;
                        longSamplingCounterArr4[i6].addCountLocked(j3, z);
                        if (cpuDeltaPowerAccumulator != null) {
                            cpuDeltaPowerAccumulator.addCpuClusterSpeedDurationsMs(uidStatsLocked, i5, i6, j3);
                        }
                        i6++;
                        length2 = i7;
                        size = i;
                        valueAt = j2;
                    }
                    i5++;
                    policies = iArr;
                    size = size;
                }
                i3 = i4 + 1;
                sparseLongArray2 = sparseLongArray;
                size = size;
            }
        }
    }

    public final void updateCpuTimeLocked(boolean z, boolean z2, long[] jArr) {
        ArrayList arrayList;
        Uid uid;
        CpuDeltaPowerAccumulator cpuDeltaPowerAccumulator = null;
        if (z2) {
            arrayList = new ArrayList();
            for (int size = this.mPartialTimers.size() - 1; size >= 0; size--) {
                StopwatchTimer stopwatchTimer = (StopwatchTimer) this.mPartialTimers.get(size);
                if (stopwatchTimer.mInList && (uid = stopwatchTimer.mUid) != null && uid.mUid != 1000) {
                    arrayList.add(stopwatchTimer);
                }
            }
        } else {
            arrayList = null;
        }
        markPartialTimersAsEligible();
        int i = 0;
        if (!z) {
            this.mCpuUidUserSysTimeReader.readDelta(false, (KernelCpuUidTimeReader.Callback) null);
            this.mCpuUidFreqTimeReader.readDelta(false, (KernelCpuUidTimeReader.Callback) null);
            this.mNumAllUidCpuTimeReads += 2;
            if (this.mConstants.TRACK_CPU_ACTIVE_CLUSTER_TIME) {
                this.mCpuUidActiveTimeReader.readDelta(false, (KernelCpuUidTimeReader.Callback) null);
                this.mCpuUidClusterTimeReader.readDelta(false, (KernelCpuUidTimeReader.Callback) null);
                this.mNumAllUidCpuTimeReads += 2;
            }
            for (int length = this.mKernelCpuSpeedReaders.length - 1; length >= 0; length--) {
                KernelCpuSpeedReader kernelCpuSpeedReader = this.mKernelCpuSpeedReaders[length];
                if (kernelCpuSpeedReader != null) {
                    kernelCpuSpeedReader.readDelta();
                }
            }
            if (Flags.disableSystemServicePowerAttr()) {
                return;
            }
            this.mSystemServerCpuThreadReader.readDelta();
            return;
        }
        this.mUserInfoProvider.refreshUserIds();
        SparseLongArray sparseLongArray = this.mCpuUidFreqTimeReader.allUidTimesAvailable() ? null : new SparseLongArray();
        EnergyConsumerStats energyConsumerStats = this.mGlobalEnergyConsumerStats;
        if (energyConsumerStats != null && energyConsumerStats.isStandardBucketSupported(3)) {
            if (jArr == null) {
                Slog.wtf("BatteryStatsImpl", "POWER_BUCKET_CPU supported but no EnergyConsumer Cpu Cluster charge reported on updateCpuTimeLocked!");
            } else {
                if (this.mCpuPowerCalculator == null) {
                    this.mCpuPowerCalculator = new CpuPowerCalculator(this.mCpuScalingPolicies, this.mPowerProfile);
                }
                cpuDeltaPowerAccumulator = new CpuDeltaPowerAccumulator(this.mCpuPowerCalculator, this.mCpuScalingPolicies.getPolicies().length);
            }
        }
        readKernelUidCpuTimesLocked(arrayList, sparseLongArray, z);
        if (sparseLongArray != null) {
            updateClusterSpeedTimes(sparseLongArray, z, cpuDeltaPowerAccumulator);
        }
        readKernelUidCpuFreqTimesLocked(arrayList, z, z2, cpuDeltaPowerAccumulator);
        this.mNumAllUidCpuTimeReads += 2;
        if (this.mConstants.TRACK_CPU_ACTIVE_CLUSTER_TIME) {
            readKernelUidCpuActiveTimesLocked(z);
            readKernelUidCpuClusterTimesLocked(z, cpuDeltaPowerAccumulator);
            this.mNumAllUidCpuTimeReads += 2;
        }
        if (!Flags.disableSystemServicePowerAttr()) {
            updateSystemServerThreadStats();
        }
        if (cpuDeltaPowerAccumulator == null || this.mGlobalEnergyConsumerStats == null) {
            return;
        }
        int length2 = jArr.length;
        long j = 0;
        long j2 = 0;
        for (long j3 : jArr) {
            j2 += j3;
        }
        if (j2 <= 0) {
            return;
        }
        long elapsedRealtime = this.mClock.elapsedRealtime();
        this.mGlobalEnergyConsumerStats.updateStandardBucket(3, j2, elapsedRealtime);
        double[] dArr = new double[length2];
        for (int i2 = 0; i2 < length2; i2++) {
            double d = cpuDeltaPowerAccumulator.totalClusterChargesMah[i2];
            if (d <= 0.0d) {
                dArr[i2] = 0.0d;
            } else {
                dArr[i2] = jArr[i2] / d;
            }
        }
        long size2 = cpuDeltaPowerAccumulator.perUidCpuClusterChargesMah.size();
        int i3 = 0;
        while (i3 < size2) {
            Uid uid2 = (Uid) cpuDeltaPowerAccumulator.perUidCpuClusterChargesMah.keyAt(i3);
            double[] dArr2 = (double[]) cpuDeltaPowerAccumulator.perUidCpuClusterChargesMah.valueAt(i3);
            int i4 = i;
            long j4 = j;
            while (i4 < length2) {
                j4 += (long) ((dArr2[i4] * dArr[i4]) + 0.5d);
                i4++;
                j = 0;
            }
            long j5 = j;
            if (j4 < j5) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("Unexpected proportional EnergyConsumer charge (", j4, ") for uid ");
                m.append(uid2.mUid);
                Slog.wtf("BatteryStatsImpl", m.toString());
            } else {
                if (uid2.mUidEnergyConsumerStats == null) {
                    uid2.mUidEnergyConsumerStats = new EnergyConsumerStats(uid2.mBsi.mEnergyConsumerStatsConfig);
                }
                uid2.mUidEnergyConsumerStats.updateStandardBucket(3, j4, elapsedRealtime);
            }
            i3++;
            j = j5;
            i = 0;
        }
    }

    public final void updateCpuTimesForAllUids() {
        LongArrayMultiStateCounter longArrayMultiStateCounter;
        if (this.mPowerStatsCollectorEnabled.get(1)) {
            this.mCpuPowerStatsCollector.schedule();
            return;
        }
        synchronized (this) {
            try {
                if (this.mCpuUidFreqTimeReader.isFastCpuTimesReader()) {
                    ensureKernelSingleUidTimeReaderLocked();
                    SparseArray allUidCpuFreqTimeMs = this.mCpuUidFreqTimeReader.getAllUidCpuFreqTimeMs();
                    for (int size = allUidCpuFreqTimeMs.size() - 1; size >= 0; size--) {
                        int keyAt = allUidCpuFreqTimeMs.keyAt(size);
                        int mapUid = mapUid(keyAt);
                        Uid uid = (Uid) this.mUidStats.get(mapUid);
                        if (uid != null && uid.mProcessState != 7) {
                            long elapsedRealtime = this.mClock.elapsedRealtime();
                            this.mClock.uptimeMillis();
                            uid.ensureMultiStateCounters(elapsedRealtime);
                            LongArrayMultiStateCounter longArrayMultiStateCounter2 = uid.mProcStateTimeMs.mCounter;
                            uid.ensureMultiStateCounters(elapsedRealtime);
                            LongArrayMultiStateCounter longArrayMultiStateCounter3 = uid.mProcStateScreenOffTimeMs.mCounter;
                            if (keyAt != mapUid && !Process.isSdkSandboxUid(keyAt)) {
                                SparseArray sparseArray = uid.mChildUids;
                                Uid.ChildUid childUid = sparseArray == null ? null : (Uid.ChildUid) sparseArray.get(keyAt);
                                if (childUid != null && (longArrayMultiStateCounter = childUid.cpuTimeInFreqCounter) != null) {
                                    if (this.mTmpCpuTimeInFreq == null) {
                                        this.mTmpCpuTimeInFreq = new LongArrayMultiStateCounter.LongArrayContainer(this.mCpuScalingPolicies.getScalingStepCount());
                                    }
                                    LongArrayMultiStateCounter.LongArrayContainer longArrayContainer = this.mTmpCpuTimeInFreq;
                                    this.mKernelSingleUidTimeReader.addDelta(keyAt, longArrayMultiStateCounter, elapsedRealtime, longArrayContainer);
                                    longArrayMultiStateCounter2.addCounts(longArrayContainer);
                                    longArrayMultiStateCounter3.addCounts(longArrayContainer);
                                }
                            }
                            this.mKernelSingleUidTimeReader.addDelta(mapUid, longArrayMultiStateCounter2, elapsedRealtime);
                            this.mKernelSingleUidTimeReader.addDelta(mapUid, longArrayMultiStateCounter3, elapsedRealtime);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateCustomEnergyConsumerStatsLocked(int i, long j, SparseLongArray sparseLongArray) {
        EnergyConsumerStats energyConsumerStats = this.mGlobalEnergyConsumerStats;
        if (energyConsumerStats != null && this.mOnBatteryInternal && !this.mIgnoreNextExternalStats && j > 0) {
            energyConsumerStats.updateCustomBucket(i, j, this.mClock.elapsedRealtime());
            if (sparseLongArray == null) {
                return;
            }
            int size = sparseLongArray.size();
            for (int i2 = 0; i2 < size; i2++) {
                int mapUid = mapUid(sparseLongArray.keyAt(i2));
                long valueAt = sparseLongArray.valueAt(i2);
                if (valueAt != 0) {
                    Uid uid = (Uid) this.mUidStats.get(mapUid);
                    if (uid != null) {
                        BinderCallStats binderCallStats = Uid.sTempBinderCallStats;
                        if (uid.mUidEnergyConsumerStats == null) {
                            uid.mUidEnergyConsumerStats = new EnergyConsumerStats(uid.mBsi.mEnergyConsumerStatsConfig);
                        }
                        uid.mUidEnergyConsumerStats.updateCustomBucket(i, valueAt, uid.mBsi.mClock.elapsedRealtime());
                    } else if (!Process.isIsolated(mapUid)) {
                        StringBuilder m = SystemServiceManager$$ExternalSyntheticOutline0.m(i, "Received EnergyConsumer charge ", j, " for custom bucket ");
                        m.append(" for non-existent uid ");
                        m.append(mapUid);
                        Slog.w("BatteryStatsImpl", m.toString());
                    }
                }
            }
        }
    }

    public final void updateDailyDeadlineLocked() {
        long currentTimeMillis = this.mClock.currentTimeMillis();
        this.mDailyStartTimeMs = currentTimeMillis;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);
        calendar.set(6, calendar.get(6) + 1);
        calendar.set(14, 0);
        calendar.set(13, 0);
        calendar.set(12, 0);
        calendar.set(11, 1);
        this.mNextMinDailyDeadlineMs = calendar.getTimeInMillis();
        calendar.set(11, 3);
        this.mNextMaxDailyDeadlineMs = calendar.getTimeInMillis();
    }

    public final void updateDischargeScreenLevelsLocked(int i, int i2) {
        int i3;
        if (Display.isOnState(i)) {
            int i4 = this.mDischargeScreenOnUnplugLevel - this.mDischargeCurrentLevel;
            if (i4 > 0) {
                this.mDischargeAmountScreenOn += i4;
                this.mDischargeAmountScreenOnSinceCharge += i4;
            }
        } else if (Display.isDozeState(i)) {
            int i5 = this.mDischargeScreenDozeUnplugLevel - this.mDischargeCurrentLevel;
            if (i5 > 0) {
                this.mDischargeAmountScreenDoze += i5;
                this.mDischargeAmountScreenDozeSinceCharge += i5;
            }
        } else if (Display.isOffState(i) && (i3 = this.mDischargeScreenOffUnplugLevel - this.mDischargeCurrentLevel) > 0) {
            this.mDischargeAmountScreenOff += i3;
            this.mDischargeAmountScreenOffSinceCharge += i3;
        }
        if (Display.isOnState(i2)) {
            this.mDischargeScreenOnUnplugLevel = this.mDischargeCurrentLevel;
            this.mDischargeScreenOffUnplugLevel = 0;
            this.mDischargeScreenDozeUnplugLevel = 0;
        } else if (Display.isDozeState(i2)) {
            this.mDischargeScreenOnUnplugLevel = 0;
            this.mDischargeScreenDozeUnplugLevel = this.mDischargeCurrentLevel;
            this.mDischargeScreenOffUnplugLevel = 0;
        } else if (Display.isOffState(i2)) {
            this.mDischargeScreenOnUnplugLevel = 0;
            this.mDischargeScreenDozeUnplugLevel = 0;
            this.mDischargeScreenOffUnplugLevel = this.mDischargeCurrentLevel;
        }
        this.mHandler.post(new BatteryStatsImpl$$ExternalSyntheticLambda6(this, i, i2, 1));
    }

    public final void updateDisplayEnergyConsumerStatsLocked(long[] jArr, int[] iArr, long j) {
        int length;
        if (this.mGlobalEnergyConsumerStats == null) {
            return;
        }
        if (this.mPerDisplayBatteryStats.length == iArr.length) {
            length = iArr.length;
        } else {
            int i = this.mDisplayMismatchWtfCount;
            this.mDisplayMismatchWtfCount = i + 1;
            if (i % 100 == 0) {
                Slog.wtf("BatteryStatsImpl", "Mismatch between PowerProfile reported display count (" + this.mPerDisplayBatteryStats.length + ") and PowerStatsHal reported display count (" + iArr.length + ")");
            }
            DisplayBatteryStats[] displayBatteryStatsArr = this.mPerDisplayBatteryStats;
            length = displayBatteryStatsArr.length < iArr.length ? displayBatteryStatsArr.length : iArr.length;
        }
        int[] iArr2 = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = iArr[i2];
            DisplayBatteryStats displayBatteryStats = this.mPerDisplayBatteryStats[i2];
            iArr2[i2] = displayBatteryStats.screenStateAtLastEnergyMeasurement;
            displayBatteryStats.screenStateAtLastEnergyMeasurement = i3;
        }
        if (this.mOnBatteryInternal) {
            if (this.mIgnoreNextExternalStats) {
                int size = this.mUidStats.size();
                for (int i4 = 0; i4 < size; i4++) {
                    Uid.m846$$Nest$mmarkProcessForegroundTimeUs((Uid) this.mUidStats.valueAt(i4), j, false);
                }
                return;
            }
            long j2 = 0;
            for (int i5 = 0; i5 < length; i5++) {
                long j3 = jArr[i5];
                if (j3 > 0) {
                    int displayPowerBucket = EnergyConsumerStats.getDisplayPowerBucket(iArr2[i5]);
                    this.mGlobalEnergyConsumerStats.updateStandardBucket(displayPowerBucket, j3);
                    if (displayPowerBucket == 0) {
                        j2 += j3;
                    }
                }
            }
            if (j2 <= 0) {
                return;
            }
            SparseDoubleArray sparseDoubleArray = new SparseDoubleArray();
            int size2 = this.mUidStats.size();
            for (int i6 = 0; i6 < size2; i6++) {
                Uid uid = (Uid) this.mUidStats.valueAt(i6);
                long m846$$Nest$mmarkProcessForegroundTimeUs = Uid.m846$$Nest$mmarkProcessForegroundTimeUs(uid, j, true);
                if (m846$$Nest$mmarkProcessForegroundTimeUs != 0) {
                    sparseDoubleArray.put(uid.mUid, m846$$Nest$mmarkProcessForegroundTimeUs);
                }
            }
            distributeEnergyToUidsLocked(0, j2, sparseDoubleArray, 0.0d, j);
        }
    }

    public final void updateGnssEnergyConsumerStatsLocked(long j, long j2) {
        EnergyConsumerStats energyConsumerStats = this.mGlobalEnergyConsumerStats;
        if (energyConsumerStats != null && this.mOnBatteryInternal && j > 0) {
            int i = 0;
            if (this.mIgnoreNextExternalStats) {
                int size = this.mUidStats.size();
                while (i < size) {
                    Uid.m845$$Nest$mmarkGnssTimeUs((Uid) this.mUidStats.valueAt(i), j2);
                    i++;
                }
                return;
            }
            energyConsumerStats.updateStandardBucket(6, j);
            SparseDoubleArray sparseDoubleArray = new SparseDoubleArray();
            int size2 = this.mUidStats.size();
            while (i < size2) {
                Uid uid = (Uid) this.mUidStats.valueAt(i);
                long m845$$Nest$mmarkGnssTimeUs = Uid.m845$$Nest$mmarkGnssTimeUs(uid, j2);
                if (m845$$Nest$mmarkGnssTimeUs != 0) {
                    sparseDoubleArray.put(uid.mUid, m845$$Nest$mmarkGnssTimeUs);
                }
                i++;
            }
            distributeEnergyToUidsLocked(6, j, sparseDoubleArray, 0.0d, j2);
        }
    }

    public final void updateKernelMemoryBandwidthLocked(long j) {
        SamplingTimer samplingTimer;
        this.mKernelMemoryBandwidthStats.updateStats();
        LongSparseLongArray bandwidthEntries = this.mKernelMemoryBandwidthStats.getBandwidthEntries();
        int size = bandwidthEntries.size();
        for (int i = 0; i < size; i++) {
            int indexOfKey = this.mKernelMemoryStats.indexOfKey(bandwidthEntries.keyAt(i));
            if (indexOfKey >= 0) {
                samplingTimer = (SamplingTimer) this.mKernelMemoryStats.valueAt(indexOfKey);
            } else {
                samplingTimer = new SamplingTimer(this.mClock, this.mOnBatteryTimeBase);
                this.mKernelMemoryStats.put(bandwidthEntries.keyAt(i), samplingTimer);
            }
            samplingTimer.update(bandwidthEntries.valueAt(i), 0L, 1, j);
        }
    }

    public final void updateKernelWakelocksLocked(long j) {
        KernelWakelockReader kernelWakelockReader = this.mKernelWakelockReader;
        if (kernelWakelockReader == null) {
            return;
        }
        KernelWakelockStats readKernelWakelockStats = kernelWakelockReader.readKernelWakelockStats(this.mTmpWakelockStats);
        if (readKernelWakelockStats == null) {
            Slog.w("BatteryStatsImpl", "Couldn't get kernel wake lock stats");
            return;
        }
        for (Map.Entry entry : readKernelWakelockStats.entrySet()) {
            String str = (String) entry.getKey();
            KernelWakelockStats.Entry entry2 = (KernelWakelockStats.Entry) entry.getValue();
            SamplingTimer samplingTimer = (SamplingTimer) this.mKernelWakelockStats.get(str);
            if (samplingTimer == null) {
                samplingTimer = new SamplingTimer(this.mClock, this.mOnBatteryScreenOffTimeBase);
                this.mKernelWakelockStats.put(str, samplingTimer);
            }
            samplingTimer.update(entry2.totalTimeUs, entry2.activeTimeUs, entry2.count, j);
            samplingTimer.mUpdateVersion = entry2.version;
        }
        Iterator it = this.mKernelWakelockStats.entrySet().iterator();
        while (it.hasNext()) {
            SamplingTimer samplingTimer2 = (SamplingTimer) ((Map.Entry) it.next()).getValue();
            if (samplingTimer2.mUpdateVersion != readKernelWakelockStats.kernelWakelockVersion) {
                samplingTimer2.mTotalTimeUs = samplingTimer2.computeRunTimeLocked(0L, j);
                samplingTimer2.mCount = samplingTimer2.computeCurrentCountLocked();
                samplingTimer2.mCurrentReportedTotalTimeUs = 0L;
                samplingTimer2.mBaseReportedTotalTimeUs = 0L;
                samplingTimer2.mCurrentReportedCount = 0;
                samplingTimer2.mBaseReportedCount = 0;
                samplingTimer2.mTrackingReportedValues = false;
            }
        }
    }

    public void updateProcStateCpuTimesLocked(int i, long j, long j2) {
        if (this.mPowerStatsCollectorEnabled.get(1)) {
            return;
        }
        ensureKernelSingleUidTimeReaderLocked();
        Uid uidStatsLocked = getUidStatsLocked(i);
        this.mNumSingleUidCpuTimeReads++;
        BinderCallStats binderCallStats = Uid.sTempBinderCallStats;
        uidStatsLocked.ensureMultiStateCounters(j);
        LongArrayMultiStateCounter longArrayMultiStateCounter = uidStatsLocked.mProcStateTimeMs.mCounter;
        uidStatsLocked.ensureMultiStateCounters(j);
        LongArrayMultiStateCounter longArrayMultiStateCounter2 = uidStatsLocked.mProcStateScreenOffTimeMs.mCounter;
        this.mKernelSingleUidTimeReader.addDelta(i, longArrayMultiStateCounter, j);
        this.mKernelSingleUidTimeReader.addDelta(i, longArrayMultiStateCounter2, j);
        if (uidStatsLocked.mChildUids != null) {
            if (this.mTmpCpuTimeInFreq == null) {
                this.mTmpCpuTimeInFreq = new LongArrayMultiStateCounter.LongArrayContainer(this.mCpuScalingPolicies.getScalingStepCount());
            }
            LongArrayMultiStateCounter.LongArrayContainer longArrayContainer = this.mTmpCpuTimeInFreq;
            for (int size = uidStatsLocked.mChildUids.size() - 1; size >= 0; size--) {
                LongArrayMultiStateCounter longArrayMultiStateCounter3 = ((Uid.ChildUid) uidStatsLocked.mChildUids.valueAt(size)).cpuTimeInFreqCounter;
                if (longArrayMultiStateCounter3 != null) {
                    this.mKernelSingleUidTimeReader.addDelta(uidStatsLocked.mChildUids.keyAt(size), longArrayMultiStateCounter3, j, longArrayContainer);
                    longArrayMultiStateCounter.addCounts(longArrayContainer);
                    longArrayMultiStateCounter2.addCounts(longArrayContainer);
                }
            }
        }
    }

    public final void updateRailStatsLocked() {
        CustomEnergyConsumerPowerStatsCollector customEnergyConsumerPowerStatsCollector = this.mCustomEnergyConsumerPowerStatsCollector;
        if (customEnergyConsumerPowerStatsCollector.mEnabled) {
            customEnergyConsumerPowerStatsCollector.schedule();
        } else {
            if (this.mEnergyConsumerRetriever == null || !this.mTmpRailStats.isRailStatsAvailable()) {
                return;
            }
            ((BatteryStatsService) this.mEnergyConsumerRetriever).fillRailDataStats(this.mTmpRailStats);
        }
    }

    public final void updateRpmStatsLocked(long j) {
        RpmStats rpmStats = this.mTmpRpmStats;
        if (rpmStats == null) {
            return;
        }
        for (Map.Entry entry : rpmStats.mPlatformLowPowerStats.entrySet()) {
            String str = (String) entry.getKey();
            getRpmTimerLocked(str).update(((RpmStats.PowerStatePlatformSleepState) entry.getValue()).mTimeMs * 1000, 0L, ((RpmStats.PowerStatePlatformSleepState) entry.getValue()).mCount, j);
            for (Map.Entry entry2 : ((RpmStats.PowerStatePlatformSleepState) entry.getValue()).mVoters.entrySet()) {
                StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, ".");
                m.append((String) entry2.getKey());
                String sb = m.toString();
                getRpmTimerLocked(sb).update(((RpmStats.PowerStateElement) entry2.getValue()).mTimeMs * 1000, 0L, ((RpmStats.PowerStateElement) entry2.getValue()).mCount, j);
            }
        }
        for (Map.Entry entry3 : this.mTmpRpmStats.mSubsystemLowPowerStats.entrySet()) {
            String str2 = (String) entry3.getKey();
            for (Map.Entry entry4 : ((RpmStats.PowerStateSubsystem) entry3.getValue()).mStates.entrySet()) {
                StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(str2, ".");
                m2.append((String) entry4.getKey());
                String sb2 = m2.toString();
                getRpmTimerLocked(sb2).update(((RpmStats.PowerStateElement) entry4.getValue()).mTimeMs * 1000, 0L, ((RpmStats.PowerStateElement) entry4.getValue()).mCount, j);
            }
        }
    }

    public final void updateSemModemActivityInfoLocked(SemModemActivityInfo semModemActivityInfo) {
        Slog.d("BatteryStatsImpl", semModemActivityInfo.toString());
        this.mNetworkModemActivity.mSleepTimeMillis.addCountLocked(semModemActivityInfo.getSleepTimeMillis());
        this.mNetworkModemActivity.mIdleTimeMillis.addCountLocked(semModemActivityInfo.getIdleTimeMillis());
        for (int i = 0; i < 5; i++) {
            this.mNetworkModemActivity.mNrModemActivityInfo.mTxTimeMillis[i].addCountLocked(semModemActivityInfo.getNrTxTimeMillis()[i]);
        }
        this.mNetworkModemActivity.mNrModemActivityInfo.mRxTimeMillis.addCountLocked(semModemActivityInfo.getNrRxTimeMillis());
        this.mNetworkModemActivity.mNrModemActivityInfo.mTxByte.addCountLocked(semModemActivityInfo.getNrTxBytes());
        this.mNetworkModemActivity.mNrModemActivityInfo.mRxByte.addCountLocked(semModemActivityInfo.getNrRxBytes());
        for (int i2 = 0; i2 < 5; i2++) {
            this.mNetworkModemActivity.mLcModemActivityInfo.mTxTimeMillis[i2].addCountLocked(semModemActivityInfo.getLcTxTimeMillis()[i2]);
        }
        this.mNetworkModemActivity.mLcModemActivityInfo.mRxTimeMillis.addCountLocked(semModemActivityInfo.getLcRxTimeMillis());
        this.mNetworkModemActivity.mLcModemActivityInfo.mTxByte.addCountLocked(semModemActivityInfo.getLcTxBytes());
        this.mNetworkModemActivity.mLcModemActivityInfo.mRxByte.addCountLocked(semModemActivityInfo.getLcRxBytes());
    }

    public final void updateSpeakerOutEnergyInfoLocked(SpeakerOutEnergyInfo speakerOutEnergyInfo, long j, long j2) {
        SpeakerOutEnergyInfo speakerOutEnergyInfo2;
        SpeakerOutEnergyInfo speakerOutEnergyInfo3;
        long j3;
        int i;
        char c;
        long j4 = 0;
        char c2 = 16;
        if (speakerOutEnergyInfo == null) {
            speakerOutEnergyInfo3 = null;
        } else {
            long[] jArr = new long[16];
            long[] jArr2 = new long[16];
            for (int i2 = 0; i2 < 16; i2++) {
                jArr[i2] = speakerOutEnergyInfo.getSpeakerMediaTimeMillis()[i2] - this.mLastAudioOutEnergyInfo.getSpeakerMediaTimeMillis()[i2];
                jArr2[i2] = speakerOutEnergyInfo.getSpeakerCallTimeMillis()[i2] - this.mLastAudioOutEnergyInfo.getSpeakerCallTimeMillis()[i2];
            }
            for (int i3 = 0; i3 < 16; i3++) {
                if (jArr[i3] < 0 || jArr2[i3] < 0) {
                    SpeakerOutEnergyInfo speakerOutEnergyInfo4 = new SpeakerOutEnergyInfo(speakerOutEnergyInfo.getTimestamp(), speakerOutEnergyInfo.getSpeakerMediaTimeMillis(), speakerOutEnergyInfo.getSpeakerCallTimeMillis(), speakerOutEnergyInfo.getEnergyUsed());
                    Slog.v("BatteryStatsImpl", "Audio energy data was reset, new energy data is " + speakerOutEnergyInfo4);
                    speakerOutEnergyInfo2 = speakerOutEnergyInfo4;
                    break;
                }
            }
            speakerOutEnergyInfo2 = new SpeakerOutEnergyInfo(speakerOutEnergyInfo.getTimestamp(), jArr, jArr2, speakerOutEnergyInfo.getEnergyUsed() - this.mLastAudioOutEnergyInfo.getEnergyUsed());
            this.mLastAudioOutEnergyInfo = speakerOutEnergyInfo;
            speakerOutEnergyInfo3 = speakerOutEnergyInfo2;
        }
        if (!this.mOnBatteryInternal || speakerOutEnergyInfo3 == null) {
            return;
        }
        this.mHasSpeakerOutReporting = true;
        long j5 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < 16; i5++) {
            long j6 = speakerOutEnergyInfo3.getSpeakerMediaTimeMillis()[i5];
            this.mSpeakerMediaTimeCounters[i5].addCountLocked(j6);
            this.mSpeakerCallTimeCounters[i5].addCountLocked(speakerOutEnergyInfo3.getSpeakerCallTimeMillis()[i5]);
            if (j6 > j5) {
                i4 = i5;
                j5 = j6;
            }
        }
        byte b = i4 > 11 ? (byte) 1 : (byte) 0;
        if (this.mHistory.getHighSpeakerVolumeState() != b) {
            this.mHistory.setHighSpeakerVolumeState(b);
            this.mHistory.writeHistoryItem(j, j2);
        }
        int size = this.mUidStats.size();
        long j7 = 0;
        int i6 = 0;
        while (true) {
            j3 = 1000;
            if (i6 >= size) {
                break;
            }
            StopwatchTimer stopwatchTimer = ((Uid) this.mUidStats.valueAt(i6)).mAudioTurnedOnTimer;
            if (stopwatchTimer != null) {
                j7 += stopwatchTimer == null ? 0L : stopwatchTimer.getTimeSinceMarkLocked(j * 1000) / 1000;
            }
            i6++;
        }
        if (j7 == 0) {
            return;
        }
        int i7 = 0;
        while (i7 < size) {
            Uid uid = (Uid) this.mUidStats.valueAt(i7);
            StopwatchTimer stopwatchTimer2 = uid.mAudioTurnedOnTimer;
            if (stopwatchTimer2 == null) {
                c = c2;
                i = size;
            } else {
                i = size;
                long timeSinceMarkLocked = stopwatchTimer2 == null ? j4 : stopwatchTimer2.getTimeSinceMarkLocked(j * j3) / j3;
                if (timeSinceMarkLocked > j4) {
                    c = 16;
                    int i8 = 0;
                    while (i8 < 16) {
                        if (speakerOutEnergyInfo3.getSpeakerMediaTimeMillis()[i8] > j4) {
                            long j8 = (speakerOutEnergyInfo3.getSpeakerMediaTimeMillis()[i8] * timeSinceMarkLocked) / j7;
                            if (uid.mSpeakerMediaTimeCounters == null) {
                                uid.initSpeakerTimeCounterLocked();
                            }
                            uid.mSpeakerMediaTimeCounters[i8].addCountLocked(j8);
                        }
                        i8++;
                        j4 = 0;
                    }
                    StopwatchTimer stopwatchTimer3 = uid.mAudioTurnedOnTimer;
                    if (stopwatchTimer3 != null) {
                        stopwatchTimer3.setMark(j);
                    }
                } else {
                    c = 16;
                }
            }
            i7++;
            size = i;
            c2 = c;
            j4 = 0;
            j3 = 1000;
        }
    }

    public void updateSystemServerThreadStats() {
        SystemServerCpuThreadReader.SystemServiceCpuThreadTimes readDelta = this.mSystemServerCpuThreadReader.readDelta();
        if (readDelta == null) {
            return;
        }
        if (this.mBinderThreadCpuTimesUs == null) {
            this.mBinderThreadCpuTimesUs = new LongSamplingCounterArray(this.mOnBatteryTimeBase);
        }
        LongSamplingCounterArray longSamplingCounterArray = this.mBinderThreadCpuTimesUs;
        longSamplingCounterArray.addCountLocked(readDelta.binderThreadCpuTimesUs, longSamplingCounterArray.mTimeBase.mRunning);
    }

    public void updateSystemServiceCallStats() {
        long j;
        long j2;
        BatteryStatsImpl batteryStatsImpl = this;
        int i = 0;
        long j3 = 0;
        for (int i2 = 0; i2 < batteryStatsImpl.mUidStats.size(); i2++) {
            ArraySet arraySet = ((Uid) batteryStatsImpl.mUidStats.valueAt(i2)).mBinderCallStats;
            for (int size = arraySet.size() - 1; size >= 0; size--) {
                BinderCallStats binderCallStats = (BinderCallStats) arraySet.valueAt(size);
                i = (int) (i + binderCallStats.recordedCallCount);
                j3 += binderCallStats.recordedCpuTimeMicros;
            }
        }
        int i3 = 0;
        long j4 = 0;
        while (i3 < batteryStatsImpl.mUidStats.size()) {
            Uid uid = (Uid) batteryStatsImpl.mUidStats.valueAt(i3);
            ArraySet arraySet2 = uid.mBinderCallStats;
            int size2 = arraySet2.size() - 1;
            int i4 = 0;
            long j5 = 0;
            while (size2 >= 0) {
                BinderCallStats binderCallStats2 = (BinderCallStats) arraySet2.valueAt(size2);
                long j6 = j4;
                long j7 = binderCallStats2.callCount;
                i4 = (int) (i4 + j7);
                long j8 = binderCallStats2.recordedCallCount;
                if (j8 > 0) {
                    j2 = (j7 * binderCallStats2.recordedCpuTimeMicros) / j8;
                } else if (i > 0) {
                    j2 = (j7 * j3) / i;
                } else {
                    size2--;
                    j4 = j6;
                }
                j5 += j2;
                size2--;
                j4 = j6;
            }
            long j9 = j4;
            long j10 = i4;
            long j11 = uid.mBinderCallCount;
            if (j10 < j11 && i > 0) {
                j5 += ((j11 - j10) * j3) / i;
            }
            uid.mSystemServiceTimeUs = j5;
            j4 = j9 + j5;
            i3++;
            batteryStatsImpl = this;
        }
        long j12 = j4;
        int i5 = 0;
        while (i5 < this.mUidStats.size()) {
            Uid uid2 = (Uid) this.mUidStats.valueAt(i5);
            if (j12 > 0) {
                j = j12;
                uid2.mProportionalSystemServiceUsage = uid2.mSystemServiceTimeUs / j;
            } else {
                j = j12;
                uid2.mProportionalSystemServiceUsage = 0.0d;
            }
            i5++;
            j12 = j;
        }
    }

    public final void updateTimeBasesLocked(int i, long j, long j2, boolean z) {
        boolean z2 = !Display.isOnState(i);
        boolean z3 = z != this.mOnBatteryTimeBase.mRunning;
        boolean z4 = (z && z2) != this.mOnBatteryScreenOffTimeBase.mRunning;
        if (z4 || z3) {
            if (z4) {
                updateKernelWakelocksLocked(j2);
                try {
                    IBatteryPropertiesRegistrar asInterface = IBatteryPropertiesRegistrar.Stub.asInterface(ServiceManager.getService("batteryproperties"));
                    if (asInterface != null) {
                        asInterface.scheduleUpdate();
                    }
                } catch (RemoteException unused) {
                }
            }
            if (z3) {
                updateRpmStatsLocked(j2);
            }
            this.mOnBatteryTimeBase.setRunning(j, j2, z);
            if (z3) {
                for (int size = this.mUidStats.size() - 1; size >= 0; size--) {
                    Uid uid = (Uid) this.mUidStats.valueAt(size);
                    uid.mOnBatteryBackgroundTimeBase.setRunning(j, j2, uid.mBsi.mOnBatteryTimeBase.mRunning && uid.mProcessState >= 3);
                }
            }
            if (z4) {
                this.mOnBatteryScreenOffTimeBase.setRunning(j, j2, z && z2);
                for (int size2 = this.mUidStats.size() - 1; size2 >= 0; size2--) {
                    Uid uid2 = (Uid) this.mUidStats.valueAt(size2);
                    uid2.mOnBatteryScreenOffBackgroundTimeBase.setRunning(j, j2, uid2.mBsi.mOnBatteryScreenOffTimeBase.mRunning && uid2.mProcessState >= 3);
                }
            }
        }
    }

    public final void updateTxPowerSharing() {
        synchronized (this) {
            try {
                if (this.mTxPowerSharingOn) {
                    noteStopTxPowerSharingLocked();
                    long elapsedRealtime = this.mClock.elapsedRealtime();
                    this.mClock.uptimeMillis();
                    if (!this.mTxPowerSharingOn) {
                        this.mTxPowerSharingOn = true;
                        this.mTxPowerSharingTimer.startRunningLocked(elapsedRealtime);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateWifiState(WifiActivityEnergyInfo wifiActivityEnergyInfo, long j, long j2, long j3, NetworkStatsManager networkStatsManager) {
        List<NetworkStatsDelta> list;
        SparseLongArray sparseLongArray;
        SparseLongArray sparseLongArray2;
        long j4;
        long j5;
        long j6;
        double d;
        double d2;
        long j7;
        SparseLongArray sparseLongArray3;
        SparseLongArray sparseLongArray4;
        long j8;
        long j9;
        long j10;
        long j11 = j2;
        if (this.mWifiPowerStatsCollector.mEnabled) {
            return;
        }
        synchronized (this.mWifiNetworkLock) {
            try {
                NetworkStats readWifiNetworkStatsLocked = readWifiNetworkStatsLocked(networkStatsManager);
                if (readWifiNetworkStatsLocked != null) {
                    list = computeDelta(readWifiNetworkStatsLocked, this.mLastWifiNetworkStats);
                    this.mLastWifiNetworkStats = readWifiNetworkStatsLocked;
                } else {
                    list = null;
                }
            } finally {
            }
        }
        synchronized (this) {
            if (list != null) {
                try {
                    notifyNetworkStatsUpdatedCallbacksLocked(list);
                } finally {
                }
            }
            if (this.mOnBatteryInternal && !this.mIgnoreNextExternalStats) {
                long j12 = 0;
                SparseDoubleArray sparseDoubleArray = (this.mGlobalEnergyConsumerStats == null || this.mWifiPowerCalculator == null || j <= 0) ? null : new SparseDoubleArray();
                SparseLongArray sparseLongArray5 = new SparseLongArray();
                SparseLongArray sparseLongArray6 = new SparseLongArray();
                SparseLongArray sparseLongArray7 = new SparseLongArray();
                SparseLongArray sparseLongArray8 = new SparseLongArray();
                if (list != null) {
                    j4 = 0;
                    j5 = 0;
                    for (NetworkStatsDelta networkStatsDelta : list) {
                        if (networkStatsDelta.mRxBytes != j12 || networkStatsDelta.mTxBytes != j12) {
                            int mapUid = mapUid(networkStatsDelta.mUid);
                            SparseLongArray sparseLongArray9 = sparseLongArray7;
                            SparseLongArray sparseLongArray10 = sparseLongArray8;
                            Uid uidStatsLocked = getUidStatsLocked(mapUid, j2, j3);
                            long j13 = networkStatsDelta.mRxBytes;
                            if (j13 != 0) {
                                uidStatsLocked.noteNetworkActivityLocked(2, j13, networkStatsDelta.mRxPackets);
                                if (networkStatsDelta.mSet == 0) {
                                    uidStatsLocked.noteNetworkActivityLocked(8, networkStatsDelta.mRxBytes, networkStatsDelta.mRxPackets);
                                }
                                this.mNetworkByteActivityCounters[2].addCountLocked(networkStatsDelta.mRxBytes);
                                this.mNetworkPacketActivityCounters[2].addCountLocked(networkStatsDelta.mRxPackets);
                                sparseLongArray5.incrementValue(mapUid, networkStatsDelta.mRxPackets);
                                j5 += networkStatsDelta.mRxPackets;
                            }
                            long j14 = networkStatsDelta.mTxBytes;
                            if (j14 != 0) {
                                uidStatsLocked.noteNetworkActivityLocked(3, j14, networkStatsDelta.mTxPackets);
                                if (networkStatsDelta.mSet == 0) {
                                    uidStatsLocked.noteNetworkActivityLocked(9, networkStatsDelta.mTxBytes, networkStatsDelta.mTxPackets);
                                }
                                this.mNetworkByteActivityCounters[3].addCountLocked(networkStatsDelta.mTxBytes);
                                this.mNetworkPacketActivityCounters[3].addCountLocked(networkStatsDelta.mTxPackets);
                                sparseLongArray6.incrementValue(mapUid, networkStatsDelta.mTxPackets);
                                j4 += networkStatsDelta.mTxPackets;
                            }
                            if (sparseDoubleArray != null && wifiActivityEnergyInfo == null && !this.mHasWifiReporting) {
                                long j15 = j11 * 1000;
                                long timeSinceMarkLocked = uidStatsLocked.mWifiRunningTimer.getTimeSinceMarkLocked(j15) / 1000;
                                if (timeSinceMarkLocked > 0) {
                                    uidStatsLocked.mWifiRunningTimer.setMark(j11);
                                }
                                long timeSinceMarkLocked2 = uidStatsLocked.mWifiScanTimer.getTimeSinceMarkLocked(j15) / 1000;
                                if (timeSinceMarkLocked2 > 0) {
                                    uidStatsLocked.mWifiScanTimer.setMark(j11);
                                }
                                long j16 = 0;
                                for (int i = 0; i < 5; i++) {
                                    StopwatchTimer stopwatchTimer = uidStatsLocked.mWifiBatchedScanTimer[i];
                                    if (stopwatchTimer != null) {
                                        long timeSinceMarkLocked3 = stopwatchTimer.getTimeSinceMarkLocked(j15) / 1000;
                                        if (timeSinceMarkLocked3 > 0) {
                                            uidStatsLocked.mWifiBatchedScanTimer[i].setMark(j11);
                                        }
                                        j16 += timeSinceMarkLocked3;
                                    }
                                }
                                sparseDoubleArray.incrementValue(uidStatsLocked.mUid, this.mWifiPowerCalculator.calcPowerWithoutControllerDataMah(networkStatsDelta.mRxPackets, networkStatsDelta.mTxPackets, timeSinceMarkLocked, timeSinceMarkLocked2, j16));
                            }
                            sparseLongArray7 = sparseLongArray9;
                            sparseLongArray8 = sparseLongArray10;
                            j12 = 0;
                        }
                    }
                    sparseLongArray = sparseLongArray7;
                    sparseLongArray2 = sparseLongArray8;
                } else {
                    sparseLongArray = sparseLongArray7;
                    sparseLongArray2 = sparseLongArray8;
                    j4 = 0;
                    j5 = 0;
                }
                double d3 = 0.0d;
                if (wifiActivityEnergyInfo != null) {
                    this.mHasWifiReporting = true;
                    long controllerTxDurationMillis = wifiActivityEnergyInfo.getControllerTxDurationMillis();
                    long controllerRxDurationMillis = wifiActivityEnergyInfo.getControllerRxDurationMillis();
                    wifiActivityEnergyInfo.getControllerScanDurationMillis();
                    long controllerIdleDurationMillis = wifiActivityEnergyInfo.getControllerIdleDurationMillis();
                    int size = this.mUidStats.size();
                    int i2 = 0;
                    long j17 = 0;
                    long j18 = 0;
                    while (i2 < size) {
                        Uid uid = (Uid) this.mUidStats.valueAt(i2);
                        SparseLongArray sparseLongArray11 = sparseLongArray2;
                        long j19 = j11 * 1000;
                        j18 += uid.mWifiScanTimer.getTimeSinceMarkLocked(j19) / 1000;
                        j17 += uid.mFullWifiLockTimer.getTimeSinceMarkLocked(j19) / 1000;
                        i2++;
                        sparseLongArray5 = sparseLongArray5;
                        sparseLongArray2 = sparseLongArray11;
                    }
                    SparseLongArray sparseLongArray12 = sparseLongArray2;
                    SparseLongArray sparseLongArray13 = sparseLongArray5;
                    int i3 = 0;
                    long j20 = controllerRxDurationMillis;
                    long j21 = controllerTxDurationMillis;
                    while (i3 < size) {
                        int i4 = size;
                        Uid uid2 = (Uid) this.mUidStats.valueAt(i3);
                        SparseLongArray sparseLongArray14 = sparseLongArray6;
                        long j22 = j17;
                        long j23 = j11 * 1000;
                        long timeSinceMarkLocked4 = uid2.mWifiScanTimer.getTimeSinceMarkLocked(j23) / 1000;
                        if (timeSinceMarkLocked4 > 0) {
                            uid2.mWifiScanTimer.setMark(j11);
                            long j24 = j18 > controllerRxDurationMillis ? (controllerRxDurationMillis * timeSinceMarkLocked4) / j18 : timeSinceMarkLocked4;
                            if (j18 > controllerTxDurationMillis) {
                                timeSinceMarkLocked4 = (timeSinceMarkLocked4 * controllerTxDurationMillis) / j18;
                            }
                            j7 = j18;
                            long j25 = timeSinceMarkLocked4;
                            sparseLongArray.incrementValue(uid2.mUid, j24);
                            sparseLongArray3 = sparseLongArray;
                            sparseLongArray4 = sparseLongArray12;
                            sparseLongArray4.incrementValue(uid2.mUid, j25);
                            j20 -= j24;
                            j21 -= j25;
                            j9 = j25;
                            j8 = j24;
                        } else {
                            j7 = j18;
                            sparseLongArray3 = sparseLongArray;
                            sparseLongArray4 = sparseLongArray12;
                            j8 = timeSinceMarkLocked4;
                            j9 = j8;
                        }
                        long timeSinceMarkLocked5 = uid2.mFullWifiLockTimer.getTimeSinceMarkLocked(j23) / 1000;
                        if (timeSinceMarkLocked5 > 0) {
                            j11 = j2;
                            uid2.mFullWifiLockTimer.setMark(j11);
                            long j26 = (timeSinceMarkLocked5 * controllerIdleDurationMillis) / j22;
                            ControllerActivityCounterImpl orCreateWifiControllerActivityLocked = uid2.getOrCreateWifiControllerActivityLocked();
                            if (orCreateWifiControllerActivityLocked.mIdleTimeMillis == null) {
                                orCreateWifiControllerActivityLocked.mIdleTimeMillis = orCreateWifiControllerActivityLocked.createTimeMultiStateCounter();
                            }
                            TimeMultiStateCounter.m841$$Nest$mincrement(orCreateWifiControllerActivityLocked.mIdleTimeMillis, j26, j11);
                            j10 = j26;
                        } else {
                            j11 = j2;
                            j10 = 0;
                        }
                        if (sparseDoubleArray != null) {
                            sparseDoubleArray.incrementValue(uid2.mUid, this.mWifiPowerCalculator.calcPowerFromControllerDataMah(j8, j9, j10));
                        }
                        i3++;
                        sparseLongArray12 = sparseLongArray4;
                        size = i4;
                        sparseLongArray6 = sparseLongArray14;
                        j17 = j22;
                        j18 = j7;
                        sparseLongArray = sparseLongArray3;
                    }
                    SparseLongArray sparseLongArray15 = sparseLongArray;
                    SparseLongArray sparseLongArray16 = sparseLongArray6;
                    SparseLongArray sparseLongArray17 = sparseLongArray12;
                    int i5 = 0;
                    while (i5 < sparseLongArray16.size()) {
                        SparseLongArray sparseLongArray18 = sparseLongArray16;
                        sparseLongArray17.incrementValue(sparseLongArray18.keyAt(i5), (sparseLongArray18.valueAt(i5) * j21) / j4);
                        i5++;
                        sparseLongArray16 = sparseLongArray18;
                    }
                    int i6 = 0;
                    while (i6 < sparseLongArray13.size()) {
                        SparseLongArray sparseLongArray19 = sparseLongArray13;
                        SparseLongArray sparseLongArray20 = sparseLongArray15;
                        sparseLongArray20.incrementValue(sparseLongArray19.keyAt(i6), (sparseLongArray19.valueAt(i6) * j20) / j5);
                        i6++;
                        sparseLongArray13 = sparseLongArray19;
                        sparseLongArray15 = sparseLongArray20;
                    }
                    SparseLongArray sparseLongArray21 = sparseLongArray15;
                    for (int i7 = 0; i7 < sparseLongArray17.size(); i7++) {
                        int keyAt = sparseLongArray17.keyAt(i7);
                        long valueAt = sparseLongArray17.valueAt(i7);
                        TimeMultiStateCounter.m841$$Nest$mincrement(ControllerActivityCounterImpl.m839$$Nest$mgetOrCreateTxTimeCounters(getUidStatsLocked(keyAt, j2, j3).getOrCreateWifiControllerActivityLocked())[0], valueAt, j11);
                        if (sparseDoubleArray != null) {
                            sparseDoubleArray.incrementValue(keyAt, this.mWifiPowerCalculator.calcPowerFromControllerDataMah(0L, valueAt, 0L));
                        }
                    }
                    for (int i8 = 0; i8 < sparseLongArray21.size(); i8++) {
                        int keyAt2 = sparseLongArray21.keyAt(i8);
                        long valueAt2 = sparseLongArray21.valueAt(i8);
                        TimeMultiStateCounter.m841$$Nest$mincrement(ControllerActivityCounterImpl.m838$$Nest$mgetOrCreateRxTimeCounter(getUidStatsLocked(sparseLongArray21.keyAt(i8), j2, j3).getOrCreateWifiControllerActivityLocked()), valueAt2, j11);
                        if (sparseDoubleArray != null) {
                            sparseDoubleArray.incrementValue(keyAt2, this.mWifiPowerCalculator.calcPowerFromControllerDataMah(valueAt2, 0L, 0L));
                        }
                    }
                    TimeMultiStateCounter.m841$$Nest$mincrement(ControllerActivityCounterImpl.m838$$Nest$mgetOrCreateRxTimeCounter(this.mWifiActivity), wifiActivityEnergyInfo.getControllerRxDurationMillis(), j11);
                    TimeMultiStateCounter.m841$$Nest$mincrement(ControllerActivityCounterImpl.m839$$Nest$mgetOrCreateTxTimeCounters(this.mWifiActivity)[0], wifiActivityEnergyInfo.getControllerTxDurationMillis(), j11);
                    this.mWifiActivity.mScanTimeMillis.addCountLocked(wifiActivityEnergyInfo.getControllerScanDurationMillis());
                    ControllerActivityCounterImpl controllerActivityCounterImpl = this.mWifiActivity;
                    if (controllerActivityCounterImpl.mIdleTimeMillis == null) {
                        controllerActivityCounterImpl.mIdleTimeMillis = controllerActivityCounterImpl.createTimeMultiStateCounter();
                    }
                    TimeMultiStateCounter.m841$$Nest$mincrement(controllerActivityCounterImpl.mIdleTimeMillis, wifiActivityEnergyInfo.getControllerIdleDurationMillis(), j11);
                    double averagePower = this.mPowerProfile.getAveragePower("wifi.controller.voltage") / 1000.0d;
                    if (averagePower != 0.0d) {
                        d2 = wifiActivityEnergyInfo.getControllerEnergyUsedMicroJoules() / averagePower;
                        this.mWifiActivity.mPowerDrainMaMs.addCountLocked((long) d2);
                    } else {
                        d2 = 0.0d;
                    }
                    long wifiTotalEnergyUseduWs = this.mTmpRailStats != null ? (long) (r5.getWifiTotalEnergyUseduWs() / averagePower) : 0L;
                    this.mWifiActivity.mMonitoredRailChargeConsumedMaMs.addCountLocked(wifiTotalEnergyUseduWs);
                    j6 = j11;
                    this.mHistory.recordWifiConsumedCharge(j2, j3, wifiTotalEnergyUseduWs / 3600000.0d);
                    RailStats railStats = this.mTmpRailStats;
                    if (railStats != null) {
                        railStats.resetWifiTotalEnergyUsed();
                    }
                    if (sparseDoubleArray != null) {
                        d3 = Math.max(d2 / 3600000.0d, this.mWifiPowerCalculator.calcPowerFromControllerDataMah(controllerRxDurationMillis, controllerTxDurationMillis, controllerIdleDurationMillis));
                    }
                } else {
                    j6 = j11;
                }
                if (sparseDoubleArray != null) {
                    this.mGlobalEnergyConsumerStats.updateStandardBucket(4, j);
                    if (this.mHasWifiReporting) {
                        d = d3;
                    } else {
                        long timeSinceMarkLocked6 = this.mGlobalWifiRunningTimer.getTimeSinceMarkLocked(j6 * 1000) / 1000;
                        this.mGlobalWifiRunningTimer.setMark(j6);
                        d = this.mWifiPowerCalculator.mPowerOnPowerEstimator.mAveragePowerMahPerMs * timeSinceMarkLocked6;
                    }
                    distributeEnergyToUidsLocked(4, j, sparseDoubleArray, d, j2);
                }
            }
        }
    }

    public final void writeAsyncLocked() {
        BackgroundThread.getHandler().removeCallbacks(this.mWriteAsyncRunnable);
        BackgroundThread.getHandler().post(this.mWriteAsyncRunnable);
    }

    public final void writeDailyItemsLocked(TypedXmlSerializer typedXmlSerializer) {
        StringBuilder sb = new StringBuilder(64);
        typedXmlSerializer.startDocument((String) null, Boolean.TRUE);
        typedXmlSerializer.startTag((String) null, "daily-items");
        for (int i = 0; i < this.mDailyItems.size(); i++) {
            BatteryStats.DailyItem dailyItem = (BatteryStats.DailyItem) this.mDailyItems.get(i);
            typedXmlSerializer.startTag((String) null, "item");
            typedXmlSerializer.attributeLong((String) null, "start", dailyItem.mStartTime);
            typedXmlSerializer.attributeLong((String) null, "end", dailyItem.mEndTime);
            writeDailyLevelSteps(typedXmlSerializer, "dis", dailyItem.mDischargeSteps, sb);
            writeDailyLevelSteps(typedXmlSerializer, "chg", dailyItem.mChargeSteps, sb);
            if (dailyItem.mPackageChanges != null) {
                for (int i2 = 0; i2 < dailyItem.mPackageChanges.size(); i2++) {
                    BatteryStats.PackageChange packageChange = (BatteryStats.PackageChange) dailyItem.mPackageChanges.get(i2);
                    if (packageChange.mPackageName == null) {
                        Slog.w("BatteryStatsImpl", "There was an issue while writing the package name in internal storage.");
                        packageChange.mPackageName = "NULL";
                    }
                    if (packageChange.mUpdate) {
                        typedXmlSerializer.startTag((String) null, "upd");
                        typedXmlSerializer.attribute((String) null, "pkg", packageChange.mPackageName);
                        typedXmlSerializer.attributeLong((String) null, "ver", packageChange.mVersionCode);
                        typedXmlSerializer.endTag((String) null, "upd");
                    } else {
                        typedXmlSerializer.startTag((String) null, "rem");
                        typedXmlSerializer.attribute((String) null, "pkg", packageChange.mPackageName);
                        typedXmlSerializer.endTag((String) null, "rem");
                    }
                }
            }
            typedXmlSerializer.endTag((String) null, "item");
        }
        typedXmlSerializer.endTag((String) null, "daily-items");
        typedXmlSerializer.endDocument();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.util.concurrent.locks.ReentrantLock] */
    public final void writeParcelToFileLocked(Parcel parcel, AtomicFile atomicFile) {
        this.mWriteLock.lock();
        FileOutputStream fileOutputStream = null;
        try {
            try {
                long uptimeMillis = SystemClock.uptimeMillis();
                fileOutputStream = atomicFile.startWrite();
                fileOutputStream.write(parcel.marshall());
                fileOutputStream.flush();
                atomicFile.finishWrite(fileOutputStream);
                FrameworkStatsLogger frameworkStatsLogger = this.mFrameworkStatsLogger;
                long uptimeMillis2 = SystemClock.uptimeMillis() - uptimeMillis;
                frameworkStatsLogger.getClass();
                EventLogTags.writeCommitSysConfigFile("batterystats", uptimeMillis2);
            } catch (IOException e) {
                Slog.w("BatteryStatsImpl", "Error writing battery statistics", e);
                atomicFile.failWrite(fileOutputStream);
            }
        } finally {
            this.mWriteLock.unlock();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v18, types: [int] */
    /* JADX WARN: Type inference failed for: r15v54 */
    /* JADX WARN: Type inference failed for: r15v55 */
    public final void writeSummaryToParcel(Parcel parcel, boolean z) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        Iterator it;
        Uid.Pkg pkg;
        int i7;
        long j;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        LongSamplingCounter[] longSamplingCounterArr;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18 = 1;
        pullPendingStateUpdatesLocked();
        getStartClockTime();
        long uptimeMillis = this.mClock.uptimeMillis() * 1000;
        long elapsedRealtime = this.mClock.elapsedRealtime() * 1000;
        parcel.writeInt(VERSION);
        this.mHistory.writeSummaryToParcel(parcel, z);
        parcel.writeInt(this.mStartCount);
        boolean z2 = false;
        parcel.writeLong(computeUptime(uptimeMillis, 0));
        parcel.writeLong(computeRealtime(elapsedRealtime, 0));
        parcel.writeLong(this.mStartClockTimeMs);
        parcel.writeLong(this.mMonotonicStartTime);
        parcel.writeLong(this.mMonotonicClock.monotonicTime());
        parcel.writeString(this.mStartPlatformVersion);
        parcel.writeString(this.mEndPlatformVersion);
        this.mOnBatteryTimeBase.writeSummaryToParcel(parcel, uptimeMillis, elapsedRealtime);
        this.mOnBatteryScreenOffTimeBase.writeSummaryToParcel(parcel, uptimeMillis, elapsedRealtime);
        parcel.writeInt(this.mDischargeUnplugLevel);
        parcel.writeInt(this.mDischargePlugLevel);
        parcel.writeInt(this.mDischargeCurrentLevel);
        parcel.writeInt(this.mBatteryLevel);
        parcel.writeInt(this.mEstimatedBatteryCapacityMah);
        parcel.writeInt(this.mLastLearnedBatteryCapacityUah);
        parcel.writeInt(this.mMinLearnedBatteryCapacityUah);
        parcel.writeInt(this.mMaxLearnedBatteryCapacityUah);
        parcel.writeInt(getLowDischargeAmountSinceCharge());
        parcel.writeInt(getHighDischargeAmountSinceCharge());
        parcel.writeInt(getDischargeAmountScreenOnSinceCharge());
        parcel.writeInt(getDischargeAmountScreenOffSinceCharge());
        parcel.writeInt(getDischargeAmountScreenDozeSinceCharge());
        parcel.writeInt(this.mProtectBatteryMode);
        int batteryPermil = getBatteryPermil();
        int batteryCCInfo = getBatteryCCInfo();
        parcel.writeInt(getScreenDischargeAmount(this.mDischargeAmountScreenOnSinceChargePermil, this.mDischargeScreenOnUnplugLevelPermil, batteryPermil, Display.isOnState(this.mScreenState)));
        parcel.writeInt(getScreenDischargeAmount(this.mDischargeAmountScreenOffSinceChargePermil, this.mDischargeScreenOffUnplugLevelPermil, batteryPermil, Display.isOffState(this.mScreenState)));
        parcel.writeInt(getScreenDischargeAmount(this.mDischargeAmountScreenDozeSinceChargePermil, this.mDischargeScreenDozeUnplugLevelPermil, batteryPermil, Display.isDozeState(this.mScreenState)));
        parcel.writeInt(getScreenDischargeAmount(this.mDischargeAmountSubScreenOnSinceChargePermil, this.mDischargeSubScreenOnUnplugLevelPermil, batteryPermil, Display.isOnState(this.mSubScreenState)));
        parcel.writeInt(getScreenDischargeAmount(this.mDischargeAmountSubScreenDozeSinceChargePermil, this.mDischargeSubScreenDozeUnplugLevelPermil, batteryPermil, Display.isDozeState(this.mSubScreenState)));
        int i19 = this.mDischargeAmountScreenOnSinceChargeCoulombCounter;
        int i20 = this.mDischargeScreenOnUnplugLevelCoulombCounter;
        boolean isOnState = Display.isOnState(this.mScreenState);
        synchronized (this) {
            if (this.mOnBattery && isOnState && batteryCCInfo < i20) {
                i19 += i20 - batteryCCInfo;
            }
        }
        parcel.writeInt(i19);
        int i21 = this.mDischargeAmountScreenOffSinceChargeCoulombCounter;
        int i22 = this.mDischargeScreenOffUnplugLevelCoulombCounter;
        boolean isOffState = Display.isOffState(this.mScreenState);
        synchronized (this) {
            if (this.mOnBattery && isOffState && batteryCCInfo < i22) {
                i21 += i22 - batteryCCInfo;
            }
        }
        parcel.writeInt(i21);
        this.mDischargeStepTracker.writeToParcel(parcel);
        this.mChargeStepTracker.writeToParcel(parcel);
        this.mDailyDischargeStepTracker.writeToParcel(parcel);
        this.mDailyChargeStepTracker.writeToParcel(parcel);
        parcel.writeLong(this.mDischargeCounter.mCount);
        parcel.writeLong(this.mDischargeScreenOffCounter.mCount);
        parcel.writeLong(this.mDischargeScreenDozeCounter.mCount);
        parcel.writeLong(this.mDischargeLightDozeCounter.mCount);
        parcel.writeLong(this.mDischargeDeepDozeCounter.mCount);
        ArrayList arrayList = this.mDailyPackageChanges;
        if (arrayList != null) {
            int size = arrayList.size();
            parcel.writeInt(size);
            for (int i23 = 0; i23 < size; i23++) {
                BatteryStats.PackageChange packageChange = (BatteryStats.PackageChange) this.mDailyPackageChanges.get(i23);
                parcel.writeString(packageChange.mPackageName);
                parcel.writeInt(packageChange.mUpdate ? 1 : 0);
                parcel.writeLong(packageChange.mVersionCode);
            }
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(this.mDailyStartTimeMs);
        parcel.writeLong(this.mNextMinDailyDeadlineMs);
        parcel.writeLong(this.mNextMaxDailyDeadlineMs);
        parcel.writeLong(this.mBatteryTimeToFullSeconds);
        EnergyConsumerStats.Config.writeToParcel(this.mEnergyConsumerStatsConfig, parcel);
        EnergyConsumerStats.writeSummaryToParcel(this.mGlobalEnergyConsumerStats, parcel);
        this.mScreenOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mScreenDozeTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        int i24 = 0;
        while (true) {
            i = 5;
            if (i24 >= 5) {
                break;
            }
            this.mScreenBrightnessTimer[i24].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            i24++;
        }
        int length = this.mPerDisplayBatteryStats.length;
        parcel.writeInt(length);
        for (int i25 = 0; i25 < length; i25++) {
            DisplayBatteryStats displayBatteryStats = this.mPerDisplayBatteryStats[i25];
            displayBatteryStats.screenOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            displayBatteryStats.screenDozeTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            for (int i26 = 0; i26 < 5; i26++) {
                displayBatteryStats.screenBrightnessTimers[i26].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            }
        }
        this.mSubScreenOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mSubScreenDozeTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        for (int i27 = 0; i27 < 5; i27++) {
            this.mSubScreenBrightnessTimer[i27].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        }
        for (int i28 = 0; i28 < 5; i28++) {
            this.mScreenAutoBrightnessTimer[i28].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        }
        for (int i29 = 0; i29 < 5; i29++) {
            this.mSubScreenAutoBrightnessTimer[i29].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        }
        this.mScreenHighBrightnessTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mSubScreenHighBrightnessTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        for (int i30 = 0; i30 < 4; i30++) {
            this.mHighRefreshRateTimer[i30].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        }
        for (int i31 = 0; i31 < 4; i31++) {
            this.mSubHighRefreshRateTimer[i31].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        }
        for (int i32 = 0; i32 < 16; i32++) {
            parcel.writeLong(this.mSpeakerMediaTimeCounters[i32].mCount);
            parcel.writeLong(this.mSpeakerCallTimeCounters[i32].mCount);
        }
        parcel.writeInt(this.mHasSpeakerOutReporting ? 1 : 0);
        this.mTxPowerSharingTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        parcel.writeLong(this.mTxPowerSharingDischargeMah);
        this.mMobileActiveTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mMobileActive5GTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mScreenOnGpsTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mInteractiveTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mPowerSaveModeEnabledTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        parcel.writeLong(this.mLongestLightIdleTimeMs);
        parcel.writeLong(this.mLongestFullIdleTimeMs);
        this.mDeviceIdleModeLightTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mDeviceIdleModeFullTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mDeviceLightIdlingTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mDeviceIdlingTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mPhoneOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        for (int i33 = 0; i33 < CELL_SIGNAL_STRENGTH_LEVEL_COUNT; i33++) {
            this.mPhoneSignalStrengthsTimer[i33].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        }
        this.mPhoneSignalScanningTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        for (int i34 = 0; i34 < BatteryStats.NUM_DATA_CONNECTION_TYPES; i34++) {
            this.mPhoneDataConnectionsTimer[i34].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        }
        this.mNrNsaTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        for (int i35 = 0; i35 < 10; i35++) {
            parcel.writeLong(this.mNetworkByteActivityCounters[i35].mCount);
            parcel.writeLong(this.mNetworkPacketActivityCounters[i35].mCount);
        }
        int length2 = this.mPerRatBatteryStats.length;
        parcel.writeInt(length2);
        int i36 = 0;
        while (i36 < length2) {
            RadioAccessTechnologyBatteryStats radioAccessTechnologyBatteryStats = this.mPerRatBatteryStats[i36];
            if (radioAccessTechnologyBatteryStats == null) {
                parcel.writeInt(0);
                i17 = i18;
            } else {
                parcel.writeInt(i18);
                StopwatchTimer[][] stopwatchTimerArr = radioAccessTechnologyBatteryStats.perStateTimers;
                int length3 = stopwatchTimerArr.length;
                parcel.writeInt(length3);
                parcel.writeInt(i);
                int i37 = 0;
                while (i37 < length3) {
                    int i38 = 0;
                    while (i38 < i) {
                        stopwatchTimerArr[i37][i38].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
                        i38 += i18;
                        i = 5;
                    }
                    i37 += i18;
                    i = 5;
                }
                if (radioAccessTechnologyBatteryStats.mPerStateTxDurationMs == null) {
                    parcel.writeInt(0);
                } else {
                    parcel.writeInt(i18);
                    for (int i39 = 0; i39 < length3; i39 += i18) {
                        int i40 = 0;
                        while (i40 < 5) {
                            parcel.writeLong(radioAccessTechnologyBatteryStats.mPerStateTxDurationMs[i39][i40].mCount);
                            i40++;
                            i18 = 1;
                        }
                    }
                }
                i17 = i18;
                if (radioAccessTechnologyBatteryStats.mPerFrequencyRxDurationMs == null) {
                    parcel.writeInt(0);
                } else {
                    parcel.writeInt(i17);
                    for (int i41 = 0; i41 < length3; i41 += i17) {
                        parcel.writeLong(radioAccessTechnologyBatteryStats.mPerFrequencyRxDurationMs[i41].mCount);
                    }
                }
            }
            i36 += i17;
            i18 = i17;
            i = 5;
        }
        this.mMobileRadioActiveTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mMobileRadioActivePerAppTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        parcel.writeLong(this.mMobileRadioActiveAdjustedTime.mCount);
        parcel.writeLong(this.mMobileRadioActiveUnknownTime.mCount);
        parcel.writeLong(this.mMobileRadioActiveUnknownCount.mCount);
        this.mWifiMulticastWakelockTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mWifiOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mGlobalWifiRunningTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        for (int i42 = 0; i42 < 8; i42++) {
            this.mWifiStateTimer[i42].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        }
        for (int i43 = 0; i43 < 13; i43++) {
            this.mWifiSupplStateTimer[i43].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        }
        for (int i44 = 0; i44 < 5; i44++) {
            this.mWifiSignalStrengthsTimer[i44].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        }
        this.mWifiActiveTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mWifiActivity.writeSummaryToParcel(parcel);
        int i45 = 0;
        while (true) {
            StopwatchTimer[] stopwatchTimerArr2 = this.mGpsSignalQualityTimer;
            if (i45 >= stopwatchTimerArr2.length) {
                break;
            }
            stopwatchTimerArr2[i45].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            i45++;
        }
        this.mBluetoothActivity.writeSummaryToParcel(parcel);
        this.mModemActivity.writeSummaryToParcel(parcel);
        ModemActivityCounterImpl modemActivityCounterImpl = this.mNetworkModemActivity;
        parcel.writeLong(modemActivityCounterImpl.mSleepTimeMillis.mCount);
        parcel.writeLong(modemActivityCounterImpl.mIdleTimeMillis.mCount);
        modemActivityCounterImpl.mNrModemActivityInfo.writeSummaryToParcel(parcel);
        modemActivityCounterImpl.mLcModemActivityInfo.writeSummaryToParcel(parcel);
        parcel.writeInt(this.mHasWifiReporting ? 1 : 0);
        parcel.writeInt(this.mHasBluetoothReporting ? 1 : 0);
        parcel.writeInt(this.mHasModemReporting ? 1 : 0);
        parcel.writeInt(this.mNumConnectivityChange);
        this.mFlashlightOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mCameraOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mBluetoothScanTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        parcel.writeInt(this.mRpmStats.size());
        for (Map.Entry entry : this.mRpmStats.entrySet()) {
            Timer timer = (Timer) entry.getValue();
            if (timer != null) {
                parcel.writeInt(1);
                parcel.writeString((String) entry.getKey());
                timer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(0);
            }
        }
        parcel.writeInt(this.mScreenOffRpmStats.size());
        for (Map.Entry entry2 : this.mScreenOffRpmStats.entrySet()) {
            Timer timer2 = (Timer) entry2.getValue();
            if (timer2 != null) {
                parcel.writeInt(1);
                parcel.writeString((String) entry2.getKey());
                timer2.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(0);
            }
        }
        parcel.writeInt(this.mKernelWakelockStats.size());
        for (Map.Entry entry3 : this.mKernelWakelockStats.entrySet()) {
            Timer timer3 = (Timer) entry3.getValue();
            if (timer3 != null) {
                parcel.writeInt(1);
                parcel.writeString((String) entry3.getKey());
                timer3.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(0);
            }
        }
        parcel.writeInt(this.mWakeupReasonStats.size());
        for (Map.Entry entry4 : this.mWakeupReasonStats.entrySet()) {
            SamplingTimer samplingTimer = (SamplingTimer) entry4.getValue();
            if (samplingTimer != null) {
                parcel.writeInt(1);
                parcel.writeString((String) entry4.getKey());
                samplingTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(0);
            }
        }
        parcel.writeInt(this.mKernelMemoryStats.size());
        int i46 = 0;
        while (i46 < this.mKernelMemoryStats.size()) {
            Timer timer4 = (Timer) this.mKernelMemoryStats.valueAt(i46);
            if (timer4 != null) {
                i16 = 1;
                parcel.writeInt(1);
                parcel.writeLong(this.mKernelMemoryStats.keyAt(i46));
                timer4.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                i16 = 1;
                parcel.writeInt(0);
            }
            i46 += i16;
        }
        parcel.writeInt(this.mScreenWakeStats.size());
        for (Map.Entry entry5 : this.mScreenWakeStats.entrySet()) {
            Counter counter = (Counter) entry5.getValue();
            if (counter != null) {
                parcel.writeInt(1);
                parcel.writeString((String) entry5.getKey());
                counter.writeSummaryFromParcelLocked(parcel);
            } else {
                parcel.writeInt(0);
            }
        }
        int size2 = this.mUidStats.size();
        parcel.writeInt(size2);
        int i47 = 0;
        while (i47 < size2) {
            parcel.writeInt(this.mUidStats.keyAt(i47));
            Uid uid = (Uid) this.mUidStats.valueAt(i47);
            uid.mOnBatteryBackgroundTimeBase.writeSummaryToParcel(parcel, uptimeMillis, elapsedRealtime);
            uid.mOnBatteryScreenOffBackgroundTimeBase.writeSummaryToParcel(parcel, uptimeMillis, elapsedRealtime);
            if (uid.mWifiRunningTimer != null) {
                i2 = 1;
                parcel.writeInt(1);
                uid.mWifiRunningTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                i2 = 1;
                parcel.writeInt(z2 ? 1 : 0);
            }
            if (uid.mFullWifiLockTimer != null) {
                parcel.writeInt(i2);
                uid.mFullWifiLockTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(z2 ? 1 : 0);
            }
            if (uid.mWifiScanTimer != null) {
                parcel.writeInt(i2);
                uid.mWifiScanTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(z2 ? 1 : 0);
            }
            for (int i48 = z2 ? 1 : 0; i48 < 5; i48 += i2) {
                if (uid.mWifiBatchedScanTimer[i48] != null) {
                    parcel.writeInt(i2);
                    uid.mWifiBatchedScanTimer[i48].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
                } else {
                    parcel.writeInt(z2 ? 1 : 0);
                }
            }
            if (uid.mWifiMulticastTimer != null) {
                parcel.writeInt(i2);
                uid.mWifiMulticastTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(z2 ? 1 : 0);
            }
            if (uid.mAudioTurnedOnTimer != null) {
                parcel.writeInt(i2);
                uid.mAudioTurnedOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(z2 ? 1 : 0);
            }
            if (uid.mVideoTurnedOnTimer != null) {
                parcel.writeInt(i2);
                uid.mVideoTurnedOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(z2 ? 1 : 0);
            }
            if (uid.mFlashlightTurnedOnTimer != null) {
                parcel.writeInt(i2);
                uid.mFlashlightTurnedOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(z2 ? 1 : 0);
            }
            if (uid.mCameraTurnedOnTimer != null) {
                parcel.writeInt(i2);
                uid.mCameraTurnedOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(z2 ? 1 : 0);
            }
            if (uid.mForegroundActivityTimer != null) {
                parcel.writeInt(i2);
                uid.mForegroundActivityTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(z2 ? 1 : 0);
            }
            if (uid.mForegroundServiceTimer != null) {
                parcel.writeInt(i2);
                uid.mForegroundServiceTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(z2 ? 1 : 0);
            }
            if (uid.mAggregatedPartialWakelockTimer != null) {
                parcel.writeInt(i2);
                uid.mAggregatedPartialWakelockTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(z2 ? 1 : 0);
            }
            if (uid.mBluetoothDutyScanTimer != null) {
                parcel.writeInt(i2);
                uid.mBluetoothDutyScanTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(z2 ? 1 : 0);
            }
            if (uid.mSpeakerMediaTimeCounters == null) {
                parcel.writeInt(z2 ? 1 : 0);
            } else {
                parcel.writeInt(i2);
                for (int i49 = z2 ? 1 : 0; i49 < 16; i49 += i2) {
                    parcel.writeLong(uid.mSpeakerMediaTimeCounters[i49].mCount);
                }
            }
            if (uid.mPerDisplayTopActivityTimer != null) {
                parcel.writeInt(i2);
                parcel.writeInt(uid.mPerDisplayTopActivityTimer.length);
                StopwatchTimer[] stopwatchTimerArr3 = uid.mPerDisplayTopActivityTimer;
                int length4 = stopwatchTimerArr3.length;
                int i50 = z2 ? 1 : 0;
                while (i50 < length4) {
                    StopwatchTimer stopwatchTimer = stopwatchTimerArr3[i50];
                    if (stopwatchTimer != null) {
                        i15 = 1;
                        parcel.writeInt(1);
                        stopwatchTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
                    } else {
                        i15 = 1;
                        parcel.writeInt(z2 ? 1 : 0);
                    }
                    i50 += i15;
                }
                i3 = 1;
            } else {
                i3 = i2;
                parcel.writeInt(z2 ? 1 : 0);
            }
            if (uid.mBluetoothScanTimer != null) {
                parcel.writeInt(i3);
                uid.mBluetoothScanTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(z2 ? 1 : 0);
            }
            if (uid.mBluetoothUnoptimizedScanTimer != null) {
                parcel.writeInt(i3);
                uid.mBluetoothUnoptimizedScanTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(z2 ? 1 : 0);
            }
            if (uid.mBluetoothScanResultCounter != null) {
                parcel.writeInt(i3);
                uid.mBluetoothScanResultCounter.writeSummaryFromParcelLocked(parcel);
            } else {
                parcel.writeInt(z2 ? 1 : 0);
            }
            if (uid.mBluetoothScanResultBgCounter != null) {
                parcel.writeInt(i3);
                uid.mBluetoothScanResultBgCounter.writeSummaryFromParcelLocked(parcel);
            } else {
                parcel.writeInt(z2 ? 1 : 0);
            }
            int i51 = z2 ? 1 : 0;
            while (i51 < 7) {
                if (uid.mProcessStateTimer[i51] != null) {
                    i14 = 1;
                    parcel.writeInt(1);
                    uid.mProcessStateTimer[i51].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
                } else {
                    i14 = 1;
                    parcel.writeInt(z2 ? 1 : 0);
                }
                i51 += i14;
            }
            if (uid.mVibratorOnTimer != null) {
                parcel.writeInt(1);
                uid.mVibratorOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(z2 ? 1 : 0);
            }
            if (uid.mUserActivityCounters == null) {
                parcel.writeInt(z2 ? 1 : 0);
            } else {
                parcel.writeInt(1);
                for (int i52 = z2 ? 1 : 0; i52 < BatteryStats.Uid.NUM_USER_ACTIVITY_TYPES; i52++) {
                    uid.mUserActivityCounters[i52].writeSummaryFromParcelLocked(parcel);
                }
            }
            if (uid.mNetworkByteActivityCounters == null) {
                parcel.writeInt(z2 ? 1 : 0);
            } else {
                parcel.writeInt(1);
                for (int i53 = z2 ? 1 : 0; i53 < 10; i53++) {
                    parcel.writeLong(uid.mNetworkByteActivityCounters[i53].mCount);
                    parcel.writeLong(uid.mNetworkPacketActivityCounters[i53].mCount);
                }
                if (uid.mMobileRadioActiveTime != null) {
                    parcel.writeBoolean(true);
                    uid.mMobileRadioActiveTime.mCounter.writeToParcel(parcel, z2 ? 1 : 0);
                } else {
                    parcel.writeBoolean(z2);
                }
                parcel.writeLong(uid.mMobileRadioActiveCount.mCount);
            }
            parcel.writeLong(uid.mUserCpuTime.mCount);
            parcel.writeLong(uid.mSystemCpuTime.mCount);
            if (uid.mCpuClusterSpeedTimesUs != null) {
                int i54 = 1;
                parcel.writeInt(1);
                parcel.writeInt(uid.mCpuClusterSpeedTimesUs.length);
                LongSamplingCounter[][] longSamplingCounterArr2 = uid.mCpuClusterSpeedTimesUs;
                int length5 = longSamplingCounterArr2.length;
                int i55 = z2 ? 1 : 0;
                boolean z3 = z2;
                while (i55 < length5) {
                    LongSamplingCounter[] longSamplingCounterArr3 = longSamplingCounterArr2[i55];
                    if (longSamplingCounterArr3 != null) {
                        parcel.writeInt(i54);
                        parcel.writeInt(longSamplingCounterArr3.length);
                        int length6 = longSamplingCounterArr3.length;
                        for (?? r15 = z3; r15 < length6; r15++) {
                            int i56 = length6;
                            LongSamplingCounter longSamplingCounter = longSamplingCounterArr3[r15];
                            if (longSamplingCounter != null) {
                                parcel.writeInt(i54);
                                longSamplingCounterArr = longSamplingCounterArr3;
                                parcel.writeLong(longSamplingCounter.mCount);
                            } else {
                                longSamplingCounterArr = longSamplingCounterArr3;
                                parcel.writeInt(0);
                            }
                            i54 = 1;
                            length6 = i56;
                            longSamplingCounterArr3 = longSamplingCounterArr;
                        }
                        i12 = i54;
                        i13 = 0;
                    } else {
                        i12 = i54;
                        i13 = z3;
                        parcel.writeInt(i13);
                    }
                    i55 += i12;
                    z3 = i13;
                    i54 = i12;
                }
                i4 = i54;
            } else {
                i4 = 1;
                parcel.writeInt(z2 ? 1 : 0);
            }
            LongSamplingCounterArray longSamplingCounterArray = uid.mCpuFreqTimeMs;
            int i57 = LongSamplingCounterArray.$r8$clinit;
            if (longSamplingCounterArray != null) {
                parcel.writeInt(i4);
                parcel.writeLongArray(longSamplingCounterArray.mCounts);
                i5 = 0;
            } else {
                i5 = 0;
                parcel.writeInt(0);
            }
            LongSamplingCounterArray longSamplingCounterArray2 = uid.mScreenOffCpuFreqTimeMs;
            if (longSamplingCounterArray2 != null) {
                parcel.writeInt(i4);
                parcel.writeLongArray(longSamplingCounterArray2.mCounts);
            } else {
                parcel.writeInt(i5);
            }
            TimeMultiStateCounter timeMultiStateCounter = uid.mCpuActiveTimeMs;
            if (timeMultiStateCounter != null) {
                parcel.writeInt(timeMultiStateCounter.mCounter.getStateCount());
                uid.mCpuActiveTimeMs.mCounter.writeToParcel(parcel, i5);
            } else {
                parcel.writeInt(i5);
            }
            parcel.writeLongArray(uid.mCpuClusterTimesMs.mCounts);
            TimeInFreqMultiStateCounter timeInFreqMultiStateCounter = uid.mProcStateTimeMs;
            if (timeInFreqMultiStateCounter != null) {
                parcel.writeInt(timeInFreqMultiStateCounter.mCounter.getStateCount());
                i6 = 0;
                uid.mProcStateTimeMs.mCounter.writeToParcel(parcel, 0);
            } else {
                i6 = 0;
                parcel.writeInt(0);
            }
            TimeInFreqMultiStateCounter timeInFreqMultiStateCounter2 = uid.mProcStateScreenOffTimeMs;
            if (timeInFreqMultiStateCounter2 != null) {
                parcel.writeInt(timeInFreqMultiStateCounter2.mCounter.getStateCount());
                uid.mProcStateScreenOffTimeMs.mCounter.writeToParcel(parcel, i6);
            } else {
                parcel.writeInt(i6);
            }
            if (uid.mMobileRadioApWakeupCount != null) {
                parcel.writeInt(1);
                parcel.writeLong(uid.mMobileRadioApWakeupCount.mCount);
            } else {
                parcel.writeInt(0);
            }
            if (uid.mWifiRadioApWakeupCount != null) {
                parcel.writeInt(1);
                parcel.writeLong(uid.mWifiRadioApWakeupCount.mCount);
            } else {
                parcel.writeInt(0);
            }
            EnergyConsumerStats.writeSummaryToParcel(uid.mUidEnergyConsumerStats, parcel);
            ArrayMap arrayMap = uid.mWakelockStats.mMap;
            int size3 = arrayMap.size();
            parcel.writeInt(size3);
            for (int i58 = 0; i58 < size3; i58 += i10) {
                parcel.writeString((String) arrayMap.keyAt(i58));
                Uid.Wakelock wakelock = (Uid.Wakelock) arrayMap.valueAt(i58);
                if (wakelock.mTimerFull != null) {
                    i10 = 1;
                    parcel.writeInt(1);
                    wakelock.mTimerFull.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
                    i11 = 0;
                } else {
                    i10 = 1;
                    i11 = 0;
                    parcel.writeInt(0);
                }
                if (wakelock.mTimerPartial != null) {
                    parcel.writeInt(i10);
                    wakelock.mTimerPartial.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
                } else {
                    parcel.writeInt(i11);
                }
                if (wakelock.mTimerWindow != null) {
                    parcel.writeInt(i10);
                    wakelock.mTimerWindow.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
                } else {
                    parcel.writeInt(i11);
                }
                if (wakelock.mTimerDraw != null) {
                    parcel.writeInt(i10);
                    wakelock.mTimerDraw.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
                } else {
                    parcel.writeInt(i11);
                }
            }
            ArrayMap arrayMap2 = uid.mSyncStats.mMap;
            int size4 = arrayMap2.size();
            parcel.writeInt(size4);
            for (int i59 = 0; i59 < size4; i59++) {
                parcel.writeString((String) arrayMap2.keyAt(i59));
                ((DualTimer) arrayMap2.valueAt(i59)).writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            }
            ArrayMap arrayMap3 = uid.mJobStats.mMap;
            int size5 = arrayMap3.size();
            parcel.writeInt(size5);
            for (int i60 = 0; i60 < size5; i60++) {
                parcel.writeString((String) arrayMap3.keyAt(i60));
                ((DualTimer) arrayMap3.valueAt(i60)).writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            }
            int size6 = uid.mJobCompletions.size();
            parcel.writeInt(size6);
            for (int i61 = 0; i61 < size6; i61++) {
                parcel.writeString((String) uid.mJobCompletions.keyAt(i61));
                SparseIntArray sparseIntArray = (SparseIntArray) uid.mJobCompletions.valueAt(i61);
                int size7 = sparseIntArray.size();
                parcel.writeInt(size7);
                for (int i62 = 0; i62 < size7; i62++) {
                    parcel.writeInt(sparseIntArray.keyAt(i62));
                    parcel.writeInt(sparseIntArray.valueAt(i62));
                }
            }
            uid.mJobsDeferredEventCount.writeSummaryFromParcelLocked(parcel);
            uid.mJobsDeferredCount.writeSummaryFromParcelLocked(parcel);
            parcel.writeLong(uid.mJobsFreshnessTimeMs.mCount);
            int i63 = 0;
            while (i63 < BatteryStats.JOB_FRESHNESS_BUCKETS.length) {
                if (uid.mJobsFreshnessBuckets[i63] != null) {
                    i9 = 1;
                    parcel.writeInt(1);
                    uid.mJobsFreshnessBuckets[i63].writeSummaryFromParcelLocked(parcel);
                } else {
                    i9 = 1;
                    parcel.writeInt(0);
                }
                i63 += i9;
            }
            int size8 = uid.mSensorStats.size();
            parcel.writeInt(size8);
            int i64 = 0;
            while (i64 < size8) {
                parcel.writeInt(uid.mSensorStats.keyAt(i64));
                Uid.Sensor sensor = (Uid.Sensor) uid.mSensorStats.valueAt(i64);
                if (sensor.mTimer != null) {
                    i8 = 1;
                    parcel.writeInt(1);
                    sensor.mTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
                } else {
                    i8 = 1;
                    parcel.writeInt(0);
                }
                i64 += i8;
            }
            int size9 = uid.mProcessStats.size();
            parcel.writeInt(size9);
            int i65 = 0;
            while (i65 < size9) {
                parcel.writeString((String) uid.mProcessStats.keyAt(i65));
                Uid.Proc proc = (Uid.Proc) uid.mProcessStats.valueAt(i65);
                parcel.writeLong(proc.mUserTimeMs);
                parcel.writeLong(proc.mSystemTimeMs);
                parcel.writeLong(proc.mForegroundTimeMs);
                parcel.writeInt(proc.mStarts);
                parcel.writeInt(proc.mNumCrashes);
                parcel.writeInt(proc.mNumAnrs);
                ArrayList arrayList2 = proc.mExcessivePower;
                if (arrayList2 == null) {
                    parcel.writeInt(0);
                } else {
                    int size10 = arrayList2.size();
                    parcel.writeInt(size10);
                    int i66 = 0;
                    while (i66 < size10) {
                        BatteryStats.Uid.Proc.ExcessivePower excessivePower = (BatteryStats.Uid.Proc.ExcessivePower) proc.mExcessivePower.get(i66);
                        parcel.writeInt(excessivePower.type);
                        parcel.writeLong(excessivePower.overTime);
                        parcel.writeLong(excessivePower.usedTime);
                        i66++;
                        size9 = size9;
                    }
                }
                i65++;
                size9 = size9;
            }
            int size11 = uid.mPackageStats.size();
            parcel.writeInt(size11);
            if (size11 > 0) {
                Iterator it2 = uid.mPackageStats.entrySet().iterator();
                while (it2.hasNext()) {
                    Map.Entry entry6 = (Map.Entry) it2.next();
                    parcel.writeString((String) entry6.getKey());
                    Uid.Pkg pkg2 = (Uid.Pkg) entry6.getValue();
                    int size12 = pkg2.mWakeupAlarms.size();
                    parcel.writeInt(size12);
                    for (int i67 = 0; i67 < size12; i67++) {
                        parcel.writeString((String) pkg2.mWakeupAlarms.keyAt(i67));
                        ((Counter) pkg2.mWakeupAlarms.valueAt(i67)).writeSummaryFromParcelLocked(parcel);
                    }
                    int size13 = pkg2.mServiceStats.size();
                    parcel.writeInt(size13);
                    int i68 = 0;
                    while (i68 < size13) {
                        parcel.writeString((String) pkg2.mServiceStats.keyAt(i68));
                        Uid.Pkg.Serv serv = (Uid.Pkg.Serv) pkg2.mServiceStats.valueAt(i68);
                        long uptime = this.mOnBatteryTimeBase.getUptime(uptimeMillis) / 1000;
                        if (serv.mRunning) {
                            it = it2;
                            long j2 = serv.mStartTimeMs + uptime;
                            pkg = pkg2;
                            i7 = size13;
                            j = j2 - serv.mRunningSinceMs;
                        } else {
                            it = it2;
                            j = serv.mStartTimeMs;
                            pkg = pkg2;
                            i7 = size13;
                        }
                        parcel.writeLong(j);
                        parcel.writeInt(serv.mStarts);
                        parcel.writeInt(serv.mLaunches);
                        i68++;
                        it2 = it;
                        pkg2 = pkg;
                        size13 = i7;
                    }
                }
            }
            i47++;
            z2 = false;
        }
        if (Flags.disableSystemServicePowerAttr()) {
            return;
        }
        LongSamplingCounterArray longSamplingCounterArray3 = this.mBinderThreadCpuTimesUs;
        int i69 = LongSamplingCounterArray.$r8$clinit;
        if (longSamplingCounterArray3 == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeLongArray(longSamplingCounterArray3.mCounts);
        }
    }

    public final void writeSyncLocked() {
        BackgroundThread.getHandler().removeCallbacks(this.mWriteAsyncRunnable);
        if (this.mStatsFile == null) {
            Slog.w("BatteryStatsImpl", "writeStatsLocked: no file associated with this instance");
        } else if (!this.mShuttingDown) {
            Parcel obtain = Parcel.obtain();
            try {
                SystemClock.uptimeMillis();
                writeSummaryToParcel(obtain, false);
                this.mLastWriteTimeMs = this.mClock.elapsedRealtime();
                writeParcelToFileLocked(obtain, this.mStatsFile);
            } finally {
                obtain.recycle();
            }
        }
        if (this.mShuttingDown) {
            return;
        }
        this.mHistory.writeHistory();
    }
}
