package com.android.server.am;

import android.R;
import android.app.StatsManager;
import android.app.usage.NetworkStatsManager;
import android.bluetooth.BluetoothActivityEnergyInfo;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.power.stats.PowerEntity;
import android.hardware.power.stats.State;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.BatteryManagerInternal;
import android.os.BatteryStats;
import android.os.BatteryStatsInternal;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.Binder;
import android.os.BluetoothBatteryStats;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.PowerManagerInternal;
import android.os.PowerSaveState;
import android.os.Process;
import android.os.ResultReceiver;
import android.os.SemBatterySipper;
import android.os.SemDevicePowerInfo;
import android.os.SemModemActivityInfo;
import android.os.ServiceManager;
import android.os.SpeakerOutEnergyInfo;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.os.WakeLockStats;
import android.os.WorkSource;
import android.os.connectivity.CellularBatteryStats;
import android.os.connectivity.GpsBatteryStats;
import android.os.connectivity.WifiActivityEnergyInfo;
import android.os.connectivity.WifiBatteryStats;
import android.os.health.HealthStatsParceler;
import android.os.health.HealthStatsWriter;
import android.os.health.PackageHealthStats;
import android.os.health.PidHealthStats;
import android.os.health.ProcessHealthStats;
import android.os.health.ServiceHealthStats;
import android.os.health.UidHealthStats;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.telephony.ModemActivityInfo;
import android.telephony.SignalStrength;
import android.util.AtomicFile;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.app.IBatteryStats;
import com.android.internal.app.IBatteryStatsCallback;
import com.android.internal.os.Clock;
import com.android.internal.os.CpuScalingPolicies;
import com.android.internal.os.CpuScalingPolicyReader;
import com.android.internal.os.MonotonicClock;
import com.android.internal.os.PowerProfile;
import com.android.internal.os.RailStats;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.ParseUtils;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.net.module.util.NetworkCapabilitiesUtils;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.Watchdog;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.net.BaseNetworkObserver;
import com.android.server.pm.UserManagerInternal;
import com.android.server.power.PowerManagerUtil;
import com.android.server.power.stats.AggregatedPowerStatsConfig;
import com.android.server.power.stats.AudioPowerStatsProcessor;
import com.android.server.power.stats.BatteryExternalStatsWorker;
import com.android.server.power.stats.BatteryStatsDumpHelperImpl;
import com.android.server.power.stats.BatteryStatsImpl;
import com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda3;
import com.android.server.power.stats.BatteryUsageStatsProvider;
import com.android.server.power.stats.BluetoothPowerStatsProcessor;
import com.android.server.power.stats.CameraPowerStatsProcessor;
import com.android.server.power.stats.CpuPowerStatsProcessor;
import com.android.server.power.stats.CustomEnergyConsumerPowerStatsProcessor;
import com.android.server.power.stats.FlashlightPowerStatsProcessor;
import com.android.server.power.stats.GnssPowerStatsProcessor;
import com.android.server.power.stats.MobileRadioPowerStatsProcessor;
import com.android.server.power.stats.PhoneCallPowerStatsProcessor;
import com.android.server.power.stats.PowerStatsAggregator;
import com.android.server.power.stats.PowerStatsExporter;
import com.android.server.power.stats.PowerStatsScheduler;
import com.android.server.power.stats.PowerStatsSpan;
import com.android.server.power.stats.PowerStatsStore;
import com.android.server.power.stats.PowerStatsUidResolver;
import com.android.server.power.stats.SemBatteryUsageStatsProvider;
import com.android.server.power.stats.SystemServerCpuThreadReader;
import com.android.server.power.stats.VideoPowerStatsProcessor;
import com.android.server.power.stats.WifiPowerStatsProcessor;
import com.android.server.power.stats.wakeups.CpuWakeupStats;
import com.android.server.powerstats.PowerStatsService;

import com.samsung.android.os.SemCompanionDeviceBatteryInfo;
import com.samsung.android.server.battery.DeviceBatteryInfoService;
import com.samsung.android.server.battery.DeviceBatteryInfoService$$ExternalSyntheticLambda1;
import com.samsung.android.server.battery.DeviceBatteryInfoService$$ExternalSyntheticLambda5;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BatteryStatsService extends IBatteryStats.Stub
        implements PowerManagerInternal.LowPowerModeListener,
                BatteryStatsImpl.PlatformIdleStateCallback,
                BatteryStatsImpl.EnergyStatsRetriever,
                Watchdog.Monitor {
    public static IBatteryStats sService;
    public final AnonymousClass1 mActivityChangeObserver;
    public BatteryManagerInternal mBatteryManagerInternal;
    public final BatteryStatsImpl.BatteryStatsConfig mBatteryStatsConfig;
    public final BatteryUsageStatsProvider mBatteryUsageStatsProvider;
    public final AtomicFile mConfigFile;
    public final Context mContext;
    public final CpuScalingPolicies mCpuScalingPolicies;
    public final CpuWakeupStats mCpuWakeupStats;
    public DeviceBatteryInfoService mDeviceBatteryInfoServiceInternal;
    public final BatteryStatsDumpHelperImpl mDumpHelper;
    public final Map mEntityNames;
    public final Handler mHandler;
    public int mLastPowerStateFromRadio;
    public int mLastPowerStateFromWifi;
    public final Object mLock;
    public final MonotonicClock mMonotonicClock;
    public final AnonymousClass2 mNetworkCallback;
    public final PowerProfile mPowerProfile;
    public PowerStatsService.LocalService mPowerStatsInternal;
    public final Object mPowerStatsLock;
    public final PowerStatsScheduler mPowerStatsScheduler;
    public final PowerStatsStore mPowerStatsStore;
    public final SemBatteryUsageStatsProvider mSemBatteryUsageStatsProvider;
    public final Map mStateNames;
    public final BatteryStatsImpl mStats;
    public boolean mSystemServicesReady;
    public final AnonymousClass3 mUserManagerUserInfoProvider;
    public final BatteryExternalStatsWorker mWorker;
    public final PowerStatsUidResolver mPowerStatsUidResolver = new PowerStatsUidResolver();
    public volatile boolean mMonitorEnabled = true;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.am.BatteryStatsService$3, reason: invalid class name */
    public final class AnonymousClass3 extends BatteryStatsImpl.UserInfoProvider {
        public UserManagerInternal umi;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService extends BatteryStatsInternal {
        public LocalService() {}

        @Override // android.os.BatteryStatsInternal
        public final List getBatteryUsageStats(List list) {
            return BatteryStatsService.this.getBatteryUsageStats(list);
        }

        @Override // android.os.BatteryStatsInternal
        public final String[] getMobileIfaces() {
            String[] strArr;
            BatteryStatsImpl batteryStatsImpl = BatteryStatsService.this.mStats;
            synchronized (batteryStatsImpl.mModemNetworkLock) {
                strArr = batteryStatsImpl.mModemIfaces;
            }
            return (String[]) strArr.clone();
        }

        @Override // android.os.BatteryStatsInternal
        public final SystemServerCpuThreadReader.SystemServiceCpuThreadTimes
                getSystemServiceCpuThreadTimes() {
            return BatteryStatsService.this.mStats.getSystemServiceCpuThreadTimes();
        }

        @Override // android.os.BatteryStatsInternal
        public final String[] getWifiIfaces() {
            String[] strArr;
            BatteryStatsImpl batteryStatsImpl = BatteryStatsService.this.mStats;
            synchronized (batteryStatsImpl.mWifiNetworkLock) {
                strArr = batteryStatsImpl.mWifiIfaces;
            }
            return (String[]) strArr.clone();
        }

        @Override // android.os.BatteryStatsInternal
        public final void noteBinderCallStats(int i, long j, Collection collection) {
            synchronized (BatteryStatsService.this.mLock) {
                BatteryStatsService batteryStatsService = BatteryStatsService.this;
                Handler handler = batteryStatsService.mHandler;
                final BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
                Objects.requireNonNull(batteryStatsImpl);
                handler.sendMessage(
                        PooledLambda.obtainMessage(
                                new QuintConsumer() { // from class:
                                                      // com.android.server.am.BatteryStatsService$LocalService$$ExternalSyntheticLambda0
                                    public final void accept(
                                            Object obj,
                                            Object obj2,
                                            Object obj3,
                                            Object obj4,
                                            Object obj5) {
                                        BatteryStatsImpl batteryStatsImpl2 = BatteryStatsImpl.this;
                                        int intValue = ((Integer) obj).intValue();
                                        long longValue = ((Long) obj2).longValue();
                                        Collection collection2 = (Collection) obj3;
                                        long longValue2 = ((Long) obj4).longValue();
                                        long longValue3 = ((Long) obj5).longValue();
                                        synchronized (batteryStatsImpl2) {
                                            batteryStatsImpl2
                                                    .getUidStatsLocked(
                                                            intValue, longValue2, longValue3)
                                                    .noteBinderCallStatsLocked(
                                                            longValue, collection2);
                                        }
                                    }
                                },
                                Integer.valueOf(i),
                                Long.valueOf(j),
                                collection,
                                Long.valueOf(SystemClock.elapsedRealtime()),
                                Long.valueOf(SystemClock.uptimeMillis())));
            }
        }

        @Override // android.os.BatteryStatsInternal
        public final void noteBinderThreadNativeIds(int[] iArr) {
            synchronized (BatteryStatsService.this.mLock) {
                BatteryStatsService.this.mStats.noteBinderThreadNativeIds(iArr);
            }
        }

        @Override // android.os.BatteryStatsInternal
        public final void noteCpuWakingNetworkPacket(Network network, long j, int i) {
            if (i < 0) {
                NandswapManager$$ExternalSyntheticOutline0.m(
                        i, "Invalid uid for waking network packet: ", "BatteryStatsService");
                return;
            }
            BatteryStatsService batteryStatsService = BatteryStatsService.this;
            NetworkCapabilities networkCapabilities =
                    ((ConnectivityManager)
                                    batteryStatsService.mContext.getSystemService(
                                            ConnectivityManager.class))
                            .getNetworkCapabilities(network);
            int i2 =
                    networkCapabilities.hasTransport(1)
                            ? 2
                            : networkCapabilities.hasTransport(0) ? 5 : -1;
            if (i2 != -1) {
                batteryStatsService.noteCpuWakingActivity(j, new int[] {i}, i2);
                return;
            }
            Slog.wtf(
                    "BatteryStatsService",
                    "Could not map transport for network: "
                            + network
                            + " while attributing wakeup by packet sent to uid: "
                            + i);
        }

        @Override // android.os.BatteryStatsInternal
        public final void noteJobsDeferred(int i, int i2, long j) {
            BatteryStatsService batteryStatsService = BatteryStatsService.this;
            synchronized (batteryStatsService.mLock) {
                batteryStatsService.mHandler.post(
                        new BatteryStatsService$$ExternalSyntheticLambda21(
                                i,
                                i2,
                                j,
                                SystemClock.elapsedRealtime(),
                                SystemClock.uptimeMillis(),
                                batteryStatsService));
            }
        }

        @Override // android.os.BatteryStatsInternal
        public final void noteWakingAlarmBatch(long j, int... iArr) {
            BatteryStatsService.this.noteCpuWakingActivity(j, iArr, 1);
        }

        @Override // android.os.BatteryStatsInternal
        public final void noteWakingSoundTrigger(long j, int i) {
            BatteryStatsService.this.noteCpuWakingActivity(j, new int[] {i}, 3);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StatsPullAtomCallbackImpl implements StatsManager.StatsPullAtomCallback {
        public StatsPullAtomCallbackImpl() {}

        public final int onPullAtom(int i, List list) {
            long parseLong;
            long startClockTime;
            BatteryUsageStats batteryUsageStats;
            FileInputStream openRead;
            switch (i) {
                case FrameworkStatsLog.BATTERY_USAGE_STATS_BEFORE_RESET /* 10111 */:
                    BatteryStatsService batteryStatsService = BatteryStatsService.this;
                    synchronized (batteryStatsService.mConfigFile) {
                        Properties properties = new Properties();
                        try {
                            openRead = batteryStatsService.mConfigFile.openRead();
                        } catch (IOException e) {
                            Slog.e(
                                    "BatteryStatsService",
                                    "Cannot load config file " + batteryStatsService.mConfigFile,
                                    e);
                        }
                        try {
                            properties.load(openRead);
                            if (openRead != null) {
                                openRead.close();
                            }
                            parseLong =
                                    Long.parseLong(
                                            properties.getProperty(
                                                    "BATTERY_USAGE_STATS_BEFORE_RESET_TIMESTAMP",
                                                    "0"));
                        } finally {
                        }
                    }
                    synchronized (BatteryStatsService.this.mStats) {
                        startClockTime = BatteryStatsService.this.mStats.getStartClockTime();
                    }
                    BatteryUsageStats batteryUsageStats2 =
                            (BatteryUsageStats)
                                    ((ArrayList)
                                                    BatteryStatsService.this.getBatteryUsageStats(
                                                            List.of(
                                                                    new BatteryUsageStatsQuery
                                                                                    .Builder()
                                                                            .setMaxStatsAgeMs(0L)
                                                                            .includeProcessStateData()
                                                                            .includeVirtualUids()
                                                                            .aggregateSnapshots(
                                                                                    parseLong,
                                                                                    startClockTime)
                                                                            .build())))
                                            .get(0);
                    BatteryStatsService batteryStatsService2 = BatteryStatsService.this;
                    synchronized (batteryStatsService2.mConfigFile) {
                        Properties properties2 = new Properties();
                        try {
                            openRead = batteryStatsService2.mConfigFile.openRead();
                        } catch (IOException e2) {
                            Slog.e(
                                    "BatteryStatsService",
                                    "Cannot load config file " + batteryStatsService2.mConfigFile,
                                    e2);
                        }
                        try {
                            properties2.load(openRead);
                            if (openRead != null) {
                                openRead.close();
                            }
                            properties2.put(
                                    "BATTERY_USAGE_STATS_BEFORE_RESET_TIMESTAMP",
                                    String.valueOf(startClockTime));
                            FileOutputStream fileOutputStream = null;
                            try {
                                fileOutputStream = batteryStatsService2.mConfigFile.startWrite();
                                properties2.store(fileOutputStream, "Statsd atom pull timestamps");
                                batteryStatsService2.mConfigFile.finishWrite(fileOutputStream);
                            } catch (IOException e3) {
                                batteryStatsService2.mConfigFile.failWrite(fileOutputStream);
                                Slog.e(
                                        "BatteryStatsService",
                                        "Cannot save config file "
                                                + batteryStatsService2.mConfigFile,
                                        e3);
                            }
                        } finally {
                        }
                    }
                    batteryUsageStats = batteryUsageStats2;
                    break;
                case FrameworkStatsLog.BATTERY_USAGE_STATS_SINCE_RESET /* 10112 */:
                    batteryUsageStats =
                            (BatteryUsageStats)
                                    ((ArrayList)
                                                    BatteryStatsService.this.getBatteryUsageStats(
                                                            List.of(
                                                                    new BatteryUsageStatsQuery
                                                                                    .Builder()
                                                                            .setMaxStatsAgeMs(0L)
                                                                            .includeProcessStateData()
                                                                            .includeVirtualUids()
                                                                            .includePowerModels()
                                                                            .setMinConsumedPowerThreshold(
                                                                                    DeviceConfig
                                                                                            .getFloat(
                                                                                                    "backstage_power",
                                                                                                    "min_consumed_power_threshold",
                                                                                                    FullScreenMagnificationGestureHandler
                                                                                                            .MAX_SCALE))
                                                                            .build())))
                                            .get(0);
                    break;
                case FrameworkStatsLog
                        .BATTERY_USAGE_STATS_SINCE_RESET_USING_POWER_PROFILE_MODEL /* 10113 */:
                    batteryUsageStats =
                            (BatteryUsageStats)
                                    ((ArrayList)
                                                    BatteryStatsService.this.getBatteryUsageStats(
                                                            List.of(
                                                                    new BatteryUsageStatsQuery
                                                                                    .Builder()
                                                                            .setMaxStatsAgeMs(0L)
                                                                            .includeProcessStateData()
                                                                            .includeVirtualUids()
                                                                            .powerProfileModeledOnly()
                                                                            .includePowerModels()
                                                                            .build())))
                                            .get(0);
                    break;
                default:
                    throw new UnsupportedOperationException(
                            VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown tagId="));
            }
            list.add(FrameworkStatsLog.buildStatsEvent(i, batteryUsageStats.getStatsProto()));
            return 0;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WakeupReasonThread extends Thread {
        public CharsetDecoder mDecoder;
        public CharBuffer mUtf16Buffer;
        public ByteBuffer mUtf8Buffer;

        public WakeupReasonThread() {
            super("BatteryStats_wakeupReason");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Process.setThreadPriority(-2);
            CharsetDecoder newDecoder = StandardCharsets.UTF_8.newDecoder();
            CodingErrorAction codingErrorAction = CodingErrorAction.REPLACE;
            this.mDecoder =
                    newDecoder
                            .onMalformedInput(codingErrorAction)
                            .onUnmappableCharacter(codingErrorAction)
                            .replaceWith("?");
            this.mUtf8Buffer = ByteBuffer.allocateDirect(512);
            this.mUtf16Buffer = CharBuffer.allocate(512);
            while (true) {
                try {
                    String waitWakeup = waitWakeup();
                    if (waitWakeup == null) {
                        return;
                    }
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    long uptimeMillis = SystemClock.uptimeMillis();
                    Trace.instantForTrack(
                            131072L, "wakeup_reason", elapsedRealtime + " " + waitWakeup);
                    BatteryStatsService.this.awaitCompletion();
                    BatteryStatsService.this.mCpuWakeupStats.noteWakeupTimeAndReason(
                            elapsedRealtime, uptimeMillis, waitWakeup);
                    synchronized (BatteryStatsService.this.mStats) {
                        BatteryStatsService.this.mStats.noteWakeupReasonLocked(
                                elapsedRealtime, uptimeMillis, waitWakeup);
                    }
                } catch (RuntimeException e) {
                    Slog.e("BatteryStatsService", "Failure reading wakeup reasons", e);
                    return;
                }
            }
        }

        public final String waitWakeup() {
            this.mUtf8Buffer.clear();
            this.mUtf16Buffer.clear();
            this.mDecoder.reset();
            int nativeWaitWakeup = BatteryStatsService.nativeWaitWakeup(this.mUtf8Buffer);
            if (nativeWaitWakeup < 0) {
                return null;
            }
            if (nativeWaitWakeup == 0) {
                return "unknown";
            }
            this.mUtf8Buffer.limit(nativeWaitWakeup);
            this.mDecoder.decode(this.mUtf8Buffer, this.mUtf16Buffer, true);
            this.mUtf16Buffer.flip();
            return this.mUtf16Buffer.toString();
        }
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.server.am.BatteryStatsService$2] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.server.am.BatteryStatsService$1] */
    /* JADX WARN: Type inference failed for: r14v3, types: [com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda16] */
    public BatteryStatsService(Context context, File file) {
        AggregatedPowerStatsConfig.PowerComponent powerComponent;
        CharsetDecoder newDecoder = StandardCharsets.UTF_8.newDecoder();
        CodingErrorAction codingErrorAction = CodingErrorAction.REPLACE;
        newDecoder
                .onMalformedInput(codingErrorAction)
                .onUnmappableCharacter(codingErrorAction)
                .replaceWith("?");
        this.mLock = new Object();
        this.mPowerStatsLock = new Object();
        this.mPowerStatsInternal = null;
        this.mEntityNames = new HashMap();
        this.mStateNames = new HashMap();
        this.mLastPowerStateFromRadio = 1;
        this.mLastPowerStateFromWifi = 1;
        this.mActivityChangeObserver =
                new BaseNetworkObserver() { // from class:
                    // com.android.server.am.BatteryStatsService.1
                    public final void interfaceClassDataActivityChanged(
                            int i, boolean z, long j, int i2) {
                        int i3 = z ? 3 : 1;
                        if (j <= 0) {
                            j = SystemClock.elapsedRealtimeNanos();
                        }
                        if (i == 0) {
                            BatteryStatsService.this.noteMobileRadioPowerState(i3, j, i2);
                        } else if (i != 1) {
                            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                    i,
                                    "Received unexpected transport in"
                                        + " interfaceClassDataActivityChanged unexpected type: ",
                                    "BatteryStatsService");
                        } else {
                            BatteryStatsService.this.noteWifiRadioPowerState(i3, j, i2);
                        }
                    }
                };
        this.mSystemServicesReady = false;
        this.mNetworkCallback =
                new ConnectivityManager
                        .NetworkCallback() { // from class:
                                             // com.android.server.am.BatteryStatsService.2
                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onCapabilitiesChanged(
                            Network network, NetworkCapabilities networkCapabilities) {
                        BatteryStatsService.this.noteConnectivityChanged(
                                NetworkCapabilitiesUtils.getDisplayTransport(
                                        networkCapabilities.getTransportTypes()),
                                networkCapabilities.hasCapability(21) ? "CONNECTED" : "SUSPENDED");
                    }

                    @Override // android.net.ConnectivityManager.NetworkCallback
                    public final void onLost(Network network) {
                        BatteryStatsService.this.noteConnectivityChanged(-1, "DISCONNECTED");
                    }
                };
        this.mContext = context;
        this.mUserManagerUserInfoProvider = new AnonymousClass3();
        this.mHandler =
                new Handler(
                        KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("batterystats-handler")
                                .getLooper());
        this.mMonotonicClock = new MonotonicClock(new File(file, "monotonic_clock.xml"));
        this.mPowerProfile = new PowerProfile(context);
        this.mCpuScalingPolicies = new CpuScalingPolicyReader().read();
        boolean z =
                context.getResources()
                        .getBoolean(R.bool.config_bg_current_drain_auto_restrict_abusive_apps);
        boolean z2 = context.getResources().getBoolean(R.bool.config_batterymeterDualTone);
        BatteryStatsImpl.BatteryStatsConfig.Builder builder =
                new BatteryStatsImpl.BatteryStatsConfig.Builder();
        builder.mResetOnUnplugHighBatteryLevel = z;
        builder.mResetOnUnplugAfterSignificantCharge = z2;
        String string = context.getResources().getString(R.string.ext_media_new_notification_title);
        if (string != null) {
            Matcher matcher = Pattern.compile("([^:]+):(\\d+)\\s*").matcher(string);
            while (matcher.find()) {
                String group = matcher.group(1);
                try {
                    long parseLong = Long.parseLong(matcher.group(2));
                    if (group.equals("*")) {
                        builder.mDefaultPowerStatsThrottlePeriod = parseLong;
                    } else {
                        ((HashMap) builder.mPowerStatsThrottlePeriods)
                                .put(group, Long.valueOf(parseLong));
                    }
                } catch (NumberFormatException unused) {
                    throw new IllegalArgumentException(
                            "Invalid config_powerStatsThrottlePeriods format: ".concat(string));
                }
            }
        }
        BatteryStatsImpl.BatteryStatsConfig batteryStatsConfig =
                new BatteryStatsImpl.BatteryStatsConfig(builder);
        this.mBatteryStatsConfig = batteryStatsConfig;
        BatteryStatsImpl batteryStatsImpl =
                new BatteryStatsImpl(
                        batteryStatsConfig,
                        Clock.SYSTEM_CLOCK,
                        this.mMonotonicClock,
                        file,
                        this.mHandler,
                        this,
                        this,
                        this.mUserManagerUserInfoProvider,
                        this.mPowerProfile,
                        this.mCpuScalingPolicies,
                        this.mPowerStatsUidResolver);
        this.mStats = batteryStatsImpl;
        BatteryExternalStatsWorker batteryExternalStatsWorker =
                new BatteryExternalStatsWorker(
                        new BatteryExternalStatsWorker.Injector(context), batteryStatsImpl);
        this.mWorker = batteryExternalStatsWorker;
        batteryStatsImpl.mExternalSync = batteryExternalStatsWorker;
        long integer =
                this.mContext.getResources().getInteger(R.integer.config_sideFpsToastTimeout)
                        * 1000;
        BatteryStatsImpl.StopwatchTimer stopwatchTimer = batteryStatsImpl.mPhoneSignalScanningTimer;
        if (stopwatchTimer != null) {
            stopwatchTimer.mTimeoutUs = integer;
        }
        if (!com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization
                .Flags.disableSystemServicePowerAttr()) {
            batteryStatsImpl.startTrackingSystemServerCpuTime();
        }
        AggregatedPowerStatsConfig aggregatedPowerStatsConfig = new AggregatedPowerStatsConfig();
        AggregatedPowerStatsConfig.PowerComponent trackPowerComponent =
                aggregatedPowerStatsConfig.trackPowerComponent(1);
        trackPowerComponent.trackDeviceStates(0, 1);
        trackPowerComponent.trackUidStates(0, 1, 2);
        final int i = 1;
        trackPowerComponent.mProcessorSupplier =
                new Supplier(
                        this) { // from class:
                                // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda16
                    public final /* synthetic */ BatteryStatsService f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Supplier
                    public final Object get() {
                        int i2 = i;
                        BatteryStatsService batteryStatsService = this.f$0;
                        switch (i2) {
                            case 0:
                                return Long.valueOf(
                                        batteryStatsService.mStats.mHistory.getStartTime());
                            case 1:
                                IBatteryStats iBatteryStats = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new CpuPowerStatsProcessor(
                                        batteryStatsService.mCpuScalingPolicies,
                                        batteryStatsService.mPowerProfile);
                            case 2:
                                IBatteryStats iBatteryStats2 = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new MobileRadioPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 3:
                                return new WifiPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 4:
                                return new BluetoothPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 5:
                                return new AudioPowerStatsProcessor(
                                        4,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower("audio"));
                            case 6:
                                return new VideoPowerStatsProcessor(
                                        5,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower("video"));
                            case 7:
                                return new FlashlightPowerStatsProcessor(
                                        6,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower(
                                                "camera.flashlight"));
                            case 8:
                                return new CameraPowerStatsProcessor(
                                        3,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower(
                                                "camera.avg"));
                            default:
                                IBatteryStats iBatteryStats3 = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new GnssPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile,
                                        batteryStatsService.mPowerStatsUidResolver);
                        }
                    }
                };
        AggregatedPowerStatsConfig.PowerComponent trackPowerComponent2 =
                aggregatedPowerStatsConfig.trackPowerComponent(8);
        trackPowerComponent2.trackDeviceStates(0, 1);
        trackPowerComponent2.trackUidStates(0, 1, 2);
        final int i2 = 2;
        trackPowerComponent2.mProcessorSupplier =
                new Supplier(
                        this) { // from class:
                                // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda16
                    public final /* synthetic */ BatteryStatsService f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Supplier
                    public final Object get() {
                        int i22 = i2;
                        BatteryStatsService batteryStatsService = this.f$0;
                        switch (i22) {
                            case 0:
                                return Long.valueOf(
                                        batteryStatsService.mStats.mHistory.getStartTime());
                            case 1:
                                IBatteryStats iBatteryStats = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new CpuPowerStatsProcessor(
                                        batteryStatsService.mCpuScalingPolicies,
                                        batteryStatsService.mPowerProfile);
                            case 2:
                                IBatteryStats iBatteryStats2 = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new MobileRadioPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 3:
                                return new WifiPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 4:
                                return new BluetoothPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 5:
                                return new AudioPowerStatsProcessor(
                                        4,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower("audio"));
                            case 6:
                                return new VideoPowerStatsProcessor(
                                        5,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower("video"));
                            case 7:
                                return new FlashlightPowerStatsProcessor(
                                        6,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower(
                                                "camera.flashlight"));
                            case 8:
                                return new CameraPowerStatsProcessor(
                                        3,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower(
                                                "camera.avg"));
                            default:
                                IBatteryStats iBatteryStats3 = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new GnssPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile,
                                        batteryStatsService.mPowerStatsUidResolver);
                        }
                    }
                };
        int i3 = 0;
        while (true) {
            if (i3 >= ((ArrayList) aggregatedPowerStatsConfig.mPowerComponents).size()) {
                powerComponent = null;
                break;
            }
            powerComponent =
                    (AggregatedPowerStatsConfig.PowerComponent)
                            ((ArrayList) aggregatedPowerStatsConfig.mPowerComponents).get(i3);
            if (powerComponent.mPowerComponentId == 8) {
                break;
            } else {
                i3++;
            }
        }
        if (powerComponent == null) {
            throw new IllegalArgumentException("Parent component 8 is not configured");
        }
        AggregatedPowerStatsConfig.PowerComponent trackPowerComponent3 =
                aggregatedPowerStatsConfig.trackPowerComponent(14);
        trackPowerComponent3.mTrackedDeviceStates = powerComponent.mTrackedDeviceStates;
        trackPowerComponent3.mTrackedUidStates = powerComponent.mTrackedUidStates;
        final int i4 = 1;
        trackPowerComponent3.mProcessorSupplier =
                new Supplier() { // from class:
                                 // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda51
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        switch (i4) {
                            case 0:
                                return new CustomEnergyConsumerPowerStatsProcessor();
                            default:
                                return new PhoneCallPowerStatsProcessor();
                        }
                    }
                };
        AggregatedPowerStatsConfig.PowerComponent trackPowerComponent4 =
                aggregatedPowerStatsConfig.trackPowerComponent(11);
        trackPowerComponent4.trackDeviceStates(0, 1);
        trackPowerComponent4.trackUidStates(0, 1, 2);
        final int i5 = 3;
        trackPowerComponent4.mProcessorSupplier =
                new Supplier(
                        this) { // from class:
                                // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda16
                    public final /* synthetic */ BatteryStatsService f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Supplier
                    public final Object get() {
                        int i22 = i5;
                        BatteryStatsService batteryStatsService = this.f$0;
                        switch (i22) {
                            case 0:
                                return Long.valueOf(
                                        batteryStatsService.mStats.mHistory.getStartTime());
                            case 1:
                                IBatteryStats iBatteryStats = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new CpuPowerStatsProcessor(
                                        batteryStatsService.mCpuScalingPolicies,
                                        batteryStatsService.mPowerProfile);
                            case 2:
                                IBatteryStats iBatteryStats2 = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new MobileRadioPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 3:
                                return new WifiPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 4:
                                return new BluetoothPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 5:
                                return new AudioPowerStatsProcessor(
                                        4,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower("audio"));
                            case 6:
                                return new VideoPowerStatsProcessor(
                                        5,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower("video"));
                            case 7:
                                return new FlashlightPowerStatsProcessor(
                                        6,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower(
                                                "camera.flashlight"));
                            case 8:
                                return new CameraPowerStatsProcessor(
                                        3,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower(
                                                "camera.avg"));
                            default:
                                IBatteryStats iBatteryStats3 = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new GnssPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile,
                                        batteryStatsService.mPowerStatsUidResolver);
                        }
                    }
                };
        AggregatedPowerStatsConfig.PowerComponent trackPowerComponent5 =
                aggregatedPowerStatsConfig.trackPowerComponent(2);
        trackPowerComponent5.trackDeviceStates(0, 1);
        trackPowerComponent5.trackUidStates(0, 1, 2);
        final int i6 = 4;
        trackPowerComponent5.mProcessorSupplier =
                new Supplier(
                        this) { // from class:
                                // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda16
                    public final /* synthetic */ BatteryStatsService f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Supplier
                    public final Object get() {
                        int i22 = i6;
                        BatteryStatsService batteryStatsService = this.f$0;
                        switch (i22) {
                            case 0:
                                return Long.valueOf(
                                        batteryStatsService.mStats.mHistory.getStartTime());
                            case 1:
                                IBatteryStats iBatteryStats = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new CpuPowerStatsProcessor(
                                        batteryStatsService.mCpuScalingPolicies,
                                        batteryStatsService.mPowerProfile);
                            case 2:
                                IBatteryStats iBatteryStats2 = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new MobileRadioPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 3:
                                return new WifiPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 4:
                                return new BluetoothPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 5:
                                return new AudioPowerStatsProcessor(
                                        4,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower("audio"));
                            case 6:
                                return new VideoPowerStatsProcessor(
                                        5,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower("video"));
                            case 7:
                                return new FlashlightPowerStatsProcessor(
                                        6,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower(
                                                "camera.flashlight"));
                            case 8:
                                return new CameraPowerStatsProcessor(
                                        3,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower(
                                                "camera.avg"));
                            default:
                                IBatteryStats iBatteryStats3 = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new GnssPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile,
                                        batteryStatsService.mPowerStatsUidResolver);
                        }
                    }
                };
        AggregatedPowerStatsConfig.PowerComponent trackPowerComponent6 =
                aggregatedPowerStatsConfig.trackPowerComponent(4);
        trackPowerComponent6.trackDeviceStates(0, 1);
        trackPowerComponent6.trackUidStates(0, 1, 2);
        final int i7 = 5;
        trackPowerComponent6.mProcessorSupplier =
                new Supplier(
                        this) { // from class:
                                // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda16
                    public final /* synthetic */ BatteryStatsService f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Supplier
                    public final Object get() {
                        int i22 = i7;
                        BatteryStatsService batteryStatsService = this.f$0;
                        switch (i22) {
                            case 0:
                                return Long.valueOf(
                                        batteryStatsService.mStats.mHistory.getStartTime());
                            case 1:
                                IBatteryStats iBatteryStats = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new CpuPowerStatsProcessor(
                                        batteryStatsService.mCpuScalingPolicies,
                                        batteryStatsService.mPowerProfile);
                            case 2:
                                IBatteryStats iBatteryStats2 = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new MobileRadioPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 3:
                                return new WifiPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 4:
                                return new BluetoothPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 5:
                                return new AudioPowerStatsProcessor(
                                        4,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower("audio"));
                            case 6:
                                return new VideoPowerStatsProcessor(
                                        5,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower("video"));
                            case 7:
                                return new FlashlightPowerStatsProcessor(
                                        6,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower(
                                                "camera.flashlight"));
                            case 8:
                                return new CameraPowerStatsProcessor(
                                        3,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower(
                                                "camera.avg"));
                            default:
                                IBatteryStats iBatteryStats3 = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new GnssPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile,
                                        batteryStatsService.mPowerStatsUidResolver);
                        }
                    }
                };
        AggregatedPowerStatsConfig.PowerComponent trackPowerComponent7 =
                aggregatedPowerStatsConfig.trackPowerComponent(5);
        trackPowerComponent7.trackDeviceStates(0, 1);
        trackPowerComponent7.trackUidStates(0, 1, 2);
        final int i8 = 6;
        trackPowerComponent7.mProcessorSupplier =
                new Supplier(
                        this) { // from class:
                                // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda16
                    public final /* synthetic */ BatteryStatsService f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Supplier
                    public final Object get() {
                        int i22 = i8;
                        BatteryStatsService batteryStatsService = this.f$0;
                        switch (i22) {
                            case 0:
                                return Long.valueOf(
                                        batteryStatsService.mStats.mHistory.getStartTime());
                            case 1:
                                IBatteryStats iBatteryStats = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new CpuPowerStatsProcessor(
                                        batteryStatsService.mCpuScalingPolicies,
                                        batteryStatsService.mPowerProfile);
                            case 2:
                                IBatteryStats iBatteryStats2 = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new MobileRadioPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 3:
                                return new WifiPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 4:
                                return new BluetoothPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 5:
                                return new AudioPowerStatsProcessor(
                                        4,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower("audio"));
                            case 6:
                                return new VideoPowerStatsProcessor(
                                        5,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower("video"));
                            case 7:
                                return new FlashlightPowerStatsProcessor(
                                        6,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower(
                                                "camera.flashlight"));
                            case 8:
                                return new CameraPowerStatsProcessor(
                                        3,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower(
                                                "camera.avg"));
                            default:
                                IBatteryStats iBatteryStats3 = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new GnssPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile,
                                        batteryStatsService.mPowerStatsUidResolver);
                        }
                    }
                };
        AggregatedPowerStatsConfig.PowerComponent trackPowerComponent8 =
                aggregatedPowerStatsConfig.trackPowerComponent(6);
        trackPowerComponent8.trackDeviceStates(0, 1);
        trackPowerComponent8.trackUidStates(0, 1, 2);
        final int i9 = 7;
        trackPowerComponent8.mProcessorSupplier =
                new Supplier(
                        this) { // from class:
                                // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda16
                    public final /* synthetic */ BatteryStatsService f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Supplier
                    public final Object get() {
                        int i22 = i9;
                        BatteryStatsService batteryStatsService = this.f$0;
                        switch (i22) {
                            case 0:
                                return Long.valueOf(
                                        batteryStatsService.mStats.mHistory.getStartTime());
                            case 1:
                                IBatteryStats iBatteryStats = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new CpuPowerStatsProcessor(
                                        batteryStatsService.mCpuScalingPolicies,
                                        batteryStatsService.mPowerProfile);
                            case 2:
                                IBatteryStats iBatteryStats2 = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new MobileRadioPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 3:
                                return new WifiPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 4:
                                return new BluetoothPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 5:
                                return new AudioPowerStatsProcessor(
                                        4,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower("audio"));
                            case 6:
                                return new VideoPowerStatsProcessor(
                                        5,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower("video"));
                            case 7:
                                return new FlashlightPowerStatsProcessor(
                                        6,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower(
                                                "camera.flashlight"));
                            case 8:
                                return new CameraPowerStatsProcessor(
                                        3,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower(
                                                "camera.avg"));
                            default:
                                IBatteryStats iBatteryStats3 = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new GnssPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile,
                                        batteryStatsService.mPowerStatsUidResolver);
                        }
                    }
                };
        AggregatedPowerStatsConfig.PowerComponent trackPowerComponent9 =
                aggregatedPowerStatsConfig.trackPowerComponent(3);
        trackPowerComponent9.trackDeviceStates(0, 1);
        trackPowerComponent9.trackUidStates(0, 1, 2);
        final int i10 = 8;
        trackPowerComponent9.mProcessorSupplier =
                new Supplier(
                        this) { // from class:
                                // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda16
                    public final /* synthetic */ BatteryStatsService f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Supplier
                    public final Object get() {
                        int i22 = i10;
                        BatteryStatsService batteryStatsService = this.f$0;
                        switch (i22) {
                            case 0:
                                return Long.valueOf(
                                        batteryStatsService.mStats.mHistory.getStartTime());
                            case 1:
                                IBatteryStats iBatteryStats = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new CpuPowerStatsProcessor(
                                        batteryStatsService.mCpuScalingPolicies,
                                        batteryStatsService.mPowerProfile);
                            case 2:
                                IBatteryStats iBatteryStats2 = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new MobileRadioPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 3:
                                return new WifiPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 4:
                                return new BluetoothPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 5:
                                return new AudioPowerStatsProcessor(
                                        4,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower("audio"));
                            case 6:
                                return new VideoPowerStatsProcessor(
                                        5,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower("video"));
                            case 7:
                                return new FlashlightPowerStatsProcessor(
                                        6,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower(
                                                "camera.flashlight"));
                            case 8:
                                return new CameraPowerStatsProcessor(
                                        3,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower(
                                                "camera.avg"));
                            default:
                                IBatteryStats iBatteryStats3 = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new GnssPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile,
                                        batteryStatsService.mPowerStatsUidResolver);
                        }
                    }
                };
        AggregatedPowerStatsConfig.PowerComponent trackPowerComponent10 =
                aggregatedPowerStatsConfig.trackPowerComponent(10);
        trackPowerComponent10.trackDeviceStates(0, 1);
        trackPowerComponent10.trackUidStates(0, 1, 2);
        final int i11 = 9;
        trackPowerComponent10.mProcessorSupplier =
                new Supplier(
                        this) { // from class:
                                // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda16
                    public final /* synthetic */ BatteryStatsService f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Supplier
                    public final Object get() {
                        int i22 = i11;
                        BatteryStatsService batteryStatsService = this.f$0;
                        switch (i22) {
                            case 0:
                                return Long.valueOf(
                                        batteryStatsService.mStats.mHistory.getStartTime());
                            case 1:
                                IBatteryStats iBatteryStats = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new CpuPowerStatsProcessor(
                                        batteryStatsService.mCpuScalingPolicies,
                                        batteryStatsService.mPowerProfile);
                            case 2:
                                IBatteryStats iBatteryStats2 = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new MobileRadioPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 3:
                                return new WifiPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 4:
                                return new BluetoothPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile);
                            case 5:
                                return new AudioPowerStatsProcessor(
                                        4,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower("audio"));
                            case 6:
                                return new VideoPowerStatsProcessor(
                                        5,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower("video"));
                            case 7:
                                return new FlashlightPowerStatsProcessor(
                                        6,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower(
                                                "camera.flashlight"));
                            case 8:
                                return new CameraPowerStatsProcessor(
                                        3,
                                        batteryStatsService.mPowerStatsUidResolver,
                                        batteryStatsService.mPowerProfile.getAveragePower(
                                                "camera.avg"));
                            default:
                                IBatteryStats iBatteryStats3 = BatteryStatsService.sService;
                                batteryStatsService.getClass();
                                return new GnssPowerStatsProcessor(
                                        batteryStatsService.mPowerProfile,
                                        batteryStatsService.mPowerStatsUidResolver);
                        }
                    }
                };
        final int i12 = 0;
        aggregatedPowerStatsConfig.mCustomPowerStatsProcessorFactory =
                new Supplier() { // from class:
                                 // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda51
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        switch (i12) {
                            case 0:
                                return new CustomEnergyConsumerPowerStatsProcessor();
                            default:
                                return new PhoneCallPowerStatsProcessor();
                        }
                    }
                };
        AggregatedPowerStatsConfig.PowerComponent powerComponent2 =
                new AggregatedPowerStatsConfig.PowerComponent(-1);
        aggregatedPowerStatsConfig.mCustomPowerComponent = powerComponent2;
        powerComponent2.trackDeviceStates(0, 1);
        powerComponent2.trackUidStates(0, 1, 2);
        PowerStatsStore powerStatsStore =
                new PowerStatsStore(
                        file,
                        102400L,
                        this.mHandler,
                        new PowerStatsStore.DefaultSectionReader(aggregatedPowerStatsConfig));
        this.mPowerStatsStore = powerStatsStore;
        Context context2 = this.mContext;
        long integer2 =
                context2.getResources()
                        .getInteger(R.integer.config_bg_current_drain_location_min_duration);
        long integer3 =
                context2.getResources().getInteger(R.integer.config_shortPressOnPowerBehavior);
        BatteryStatsService$$ExternalSyntheticLambda14
                batteryStatsService$$ExternalSyntheticLambda14 =
                        new BatteryStatsService$$ExternalSyntheticLambda14(this);
        BatteryStatsImpl batteryStatsImpl2 = this.mStats;
        Objects.requireNonNull(batteryStatsImpl2);
        BatteryStatsService$$ExternalSyntheticLambda15
                batteryStatsService$$ExternalSyntheticLambda15 =
                        new BatteryStatsService$$ExternalSyntheticLambda15(0, batteryStatsImpl2);
        PowerStatsAggregator powerStatsAggregator =
                new PowerStatsAggregator(aggregatedPowerStatsConfig, this.mStats.mHistory);
        Clock clock = Clock.SYSTEM_CLOCK;
        final int i13 = 0;
        this.mPowerStatsScheduler =
                new PowerStatsScheduler(
                        batteryStatsService$$ExternalSyntheticLambda15,
                        powerStatsAggregator,
                        integer2,
                        integer3,
                        powerStatsStore,
                        batteryStatsService$$ExternalSyntheticLambda14,
                        clock,
                        this.mMonotonicClock,
                        new Supplier(
                                this) { // from class:
                                        // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda16
                            public final /* synthetic */ BatteryStatsService f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.util.function.Supplier
                            public final Object get() {
                                int i22 = i13;
                                BatteryStatsService batteryStatsService = this.f$0;
                                switch (i22) {
                                    case 0:
                                        return Long.valueOf(
                                                batteryStatsService.mStats.mHistory.getStartTime());
                                    case 1:
                                        IBatteryStats iBatteryStats = BatteryStatsService.sService;
                                        batteryStatsService.getClass();
                                        return new CpuPowerStatsProcessor(
                                                batteryStatsService.mCpuScalingPolicies,
                                                batteryStatsService.mPowerProfile);
                                    case 2:
                                        IBatteryStats iBatteryStats2 = BatteryStatsService.sService;
                                        batteryStatsService.getClass();
                                        return new MobileRadioPowerStatsProcessor(
                                                batteryStatsService.mPowerProfile);
                                    case 3:
                                        return new WifiPowerStatsProcessor(
                                                batteryStatsService.mPowerProfile);
                                    case 4:
                                        return new BluetoothPowerStatsProcessor(
                                                batteryStatsService.mPowerProfile);
                                    case 5:
                                        return new AudioPowerStatsProcessor(
                                                4,
                                                batteryStatsService.mPowerStatsUidResolver,
                                                batteryStatsService.mPowerProfile.getAveragePower(
                                                        "audio"));
                                    case 6:
                                        return new VideoPowerStatsProcessor(
                                                5,
                                                batteryStatsService.mPowerStatsUidResolver,
                                                batteryStatsService.mPowerProfile.getAveragePower(
                                                        "video"));
                                    case 7:
                                        return new FlashlightPowerStatsProcessor(
                                                6,
                                                batteryStatsService.mPowerStatsUidResolver,
                                                batteryStatsService.mPowerProfile.getAveragePower(
                                                        "camera.flashlight"));
                                    case 8:
                                        return new CameraPowerStatsProcessor(
                                                3,
                                                batteryStatsService.mPowerStatsUidResolver,
                                                batteryStatsService.mPowerProfile.getAveragePower(
                                                        "camera.avg"));
                                    default:
                                        IBatteryStats iBatteryStats3 = BatteryStatsService.sService;
                                        batteryStatsService.getClass();
                                        return new GnssPowerStatsProcessor(
                                                batteryStatsService.mPowerProfile,
                                                batteryStatsService.mPowerStatsUidResolver);
                                }
                            }
                        },
                        this.mHandler);
        BatteryUsageStatsProvider batteryUsageStatsProvider =
                new BatteryUsageStatsProvider(
                        context,
                        new PowerStatsExporter(
                                powerStatsStore,
                                new PowerStatsAggregator(
                                        aggregatedPowerStatsConfig, this.mStats.mHistory)),
                        this.mPowerProfile,
                        this.mCpuScalingPolicies,
                        powerStatsStore,
                        clock);
        this.mBatteryUsageStatsProvider = batteryUsageStatsProvider;
        this.mSemBatteryUsageStatsProvider =
                new SemBatteryUsageStatsProvider(this.mStats, batteryUsageStatsProvider);
        BatteryStatsImpl batteryStatsImpl3 = this.mStats;
        batteryStatsImpl3.mSaveBatteryUsageStatsOnReset = true;
        batteryStatsImpl3.mBatteryUsageStatsProvider = batteryUsageStatsProvider;
        batteryStatsImpl3.mPowerStatsStore = powerStatsStore;
        this.mDumpHelper = new BatteryStatsDumpHelperImpl(batteryUsageStatsProvider);
        this.mCpuWakeupStats = new CpuWakeupStats(context, this.mHandler);
        this.mConfigFile = new AtomicFile(new File(file, "battery_usage_stats_config"));
    }

    public static void dumpHelp$1(PrintWriter printWriter) {
        BatteryService$$ExternalSyntheticOutline0.m(
                printWriter,
                "Battery stats (batterystats) dump options:",
                "  [--checkin] [--proto] [--history] [--history-start] [--charged] [-c]",
                "  [--daily] [--reset] [--reset-all] [--write] [--new-daily] [--read-daily]",
                "  [-h] [<package.name>]");
        BatteryService$$ExternalSyntheticOutline0.m(
                printWriter,
                "  --checkin: generate output for a checkin report; will write (and clear) the",
                "             last old completed stats when they had been reset.",
                "  -c: write the current stats in checkin format.",
                "  --proto: write the current aggregate stats (without history) in proto format.");
        BatteryService$$ExternalSyntheticOutline0.m(
                printWriter,
                "  --history: show only history data.",
                "  --history-start <num>: show only history data starting at given time offset.",
                "  --history-create-events <num>: create <num> of battery history events.",
                "  --charged: only output data since last charged.");
        BatteryService$$ExternalSyntheticOutline0.m(
                printWriter,
                "  --daily: only output full daily data.",
                "  --reset: reset the stats, clearing all current data.",
                "  --reset-all: reset the stats, clearing all current and past data.",
                "  --write: force write current collected stats to disk.");
        BatteryService$$ExternalSyntheticOutline0.m(
                printWriter,
                "  --new-daily: immediately create and write new daily stats record.",
                "  --read-daily: read-load last written daily stats.",
                "  --settings: dump the settings key/values related to batterystats",
                "  --cpu: dump cpu stats for debugging purpose");
        BatteryService$$ExternalSyntheticOutline0.m(
                printWriter,
                "  --wakeups: dump CPU wakeup history and attribution.",
                "  --power-profile: dump the power profile constants",
                "  --usage: write battery usage stats. Optional arguments:",
                "     --proto: output as a binary protobuffer");
        printWriter.println(
                "     --model power-profile: use the power profile model even if measured energy is"
                    + " available");
        if (com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization
                .Flags.streamlinedBatteryStats()) {
            printWriter.println(
                    "  --sample: collect and dump a sample of stats for debugging purpose");
        }
        BatteryService$$ExternalSyntheticOutline0.m(
                printWriter,
                "  <package.name>: optional name of package to filter output by.",
                "  -h: print this help text.",
                "Battery stats (batterystats) commands:",
                "  enable|disable <option>");
        BatteryService$$ExternalSyntheticOutline0.m(
                printWriter,
                "    Enable or disable a running option.  Option state is not saved across boots.",
                "    Options are:",
                "      full-history: include additional detailed events in battery history:",
                "          wake_lock_in, alarms and proc events");
        printWriter.println("      no-auto-reset: don't automatically reset stats when unplugged");
        printWriter.println(
                "      pretend-screen-off: pretend the screen is off, even if screen state"
                    + " changes");
    }

    private native void getRailEnergyPowerStats(RailStats railStats);

    public static IBatteryStats getService() {
        IBatteryStats iBatteryStats = sService;
        if (iBatteryStats != null) {
            return iBatteryStats;
        }
        IBatteryStats asInterface =
                IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats"));
        sService = asInterface;
        return asInterface;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeWaitWakeup(ByteBuffer byteBuffer);

    public final void awaitCompletion() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        this.mHandler.post(new BatteryStatsService$$ExternalSyntheticLambda15(1, countDownLatch));
        try {
            countDownLatch.await();
        } catch (InterruptedException unused) {
        }
    }

    public final long computeBatteryScreenOffRealtimeMs() {
        long computeRealtime;
        computeBatteryScreenOffRealtimeMs_enforcePermission();
        synchronized (this.mStats) {
            computeRealtime =
                    this.mStats.mOnBatteryScreenOffTimeBase.computeRealtime(
                                    SystemClock.elapsedRealtimeNanos() / 1000)
                            / 1000;
        }
        return computeRealtime;
    }

    public final long computeBatteryTimeRemaining() {
        long computeBatteryTimeRemaining;
        synchronized (this.mStats) {
            try {
                computeBatteryTimeRemaining =
                        this.mStats.computeBatteryTimeRemaining(SystemClock.elapsedRealtime());
                if (computeBatteryTimeRemaining >= 0) {
                    computeBatteryTimeRemaining /= 1000;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return computeBatteryTimeRemaining;
    }

    public final long computeChargeTimeRemaining() {
        long computeChargeTimeRemaining;
        BatteryStatsImpl batteryStatsImpl = this.mStats;
        if (batteryStatsImpl.mFeatureComputeChargeTimeModel) {
            Context context = this.mContext;
            batteryStatsImpl.getClass();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(
                    "com.samsung.server.BatteryService.action.SEC_BATTERY_REMAINING_CHARGING_TIME_CHANGED");
            Intent registerReceiver = context.registerReceiver(null, intentFilter);
            long longExtra =
                    registerReceiver != null
                            ? registerReceiver.getLongExtra("remaining_charging_time", -1L)
                            : -1L;
            return longExtra >= 0 ? longExtra * 1000 : longExtra;
        }
        synchronized (batteryStatsImpl) {
            try {
                computeChargeTimeRemaining =
                        this.mStats.computeChargeTimeRemaining(SystemClock.elapsedRealtime());
                if (computeChargeTimeRemaining >= 0) {
                    computeChargeTimeRemaining /= 1000;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return computeChargeTimeRemaining;
    }

    public final int doEnableOrDisable(PrintWriter printWriter, int i, String[] strArr, boolean z) {
        int i2 = i + 1;
        if (i2 >= strArr.length) {
            printWriter.println(
                    "Missing option argument for ".concat(z ? "--enable" : "--disable"));
            dumpHelp$1(printWriter);
            return -1;
        }
        if ("full-wake-history".equals(strArr[i2]) || "full-history".equals(strArr[i2])) {
            awaitCompletion();
            synchronized (this.mStats) {
                this.mStats.setRecordAllHistoryLocked(z);
            }
        } else if ("no-auto-reset".equals(strArr[i2])) {
            awaitCompletion();
            synchronized (this.mStats) {
                this.mStats.mNoAutoReset = z;
            }
        } else {
            if (!"pretend-screen-off".equals(strArr[i2])) {
                printWriter.println("Unknown enable/disable option: " + strArr[i2]);
                dumpHelp$1(printWriter);
                return -1;
            }
            awaitCompletion();
            synchronized (this.mStats) {
                BatteryStatsImpl batteryStatsImpl = this.mStats;
                if (batteryStatsImpl.mPretendScreenOff != z) {
                    batteryStatsImpl.mPretendScreenOff = z;
                    batteryStatsImpl.noteScreenStateLocked(
                            batteryStatsImpl.mPerDisplayBatteryStats[0].screenState,
                            -1,
                            batteryStatsImpl.mClock.elapsedRealtime(),
                            batteryStatsImpl.mClock.uptimeMillis(),
                            batteryStatsImpl.mClock.currentTimeMillis());
                }
            }
        }
        return i2;
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.mMonitorEnabled = false;
        try {
            dumpUnmonitored(fileDescriptor, printWriter, strArr);
        } finally {
            this.mMonitorEnabled = true;
        }
    }

    public final void dumpCpuStats(PrintWriter printWriter) {
        awaitCompletion();
        synchronized (this.mStats) {
            this.mStats.dumpCpuStatsLocked(printWriter);
        }
    }

    public final void dumpMeasuredEnergyStats(PrintWriter printWriter) {
        awaitCompletion();
        syncStats("dump");
        synchronized (this.mStats) {
            this.mStats.dumpEnergyConsumerStatsLocked(printWriter);
        }
    }

    public final void dumpPowerProfile(PrintWriter printWriter) {
        synchronized (this.mStats) {
            this.mStats.dumpPowerProfileLocked(printWriter);
        }
    }

    public final void dumpSettings(PrintWriter printWriter) {
        awaitCompletion();
        synchronized (this.mStats) {
            this.mStats.dumpConstantsLocked(printWriter);
            printWriter.println("Flags:");
            printWriter.println(
                    "    com.android.server.power.optimization.streamlined_battery_stats: "
                            + com.android.internal.hidden_from_bootclasspath.com.android.server
                                    .power.optimization.Flags.streamlinedBatteryStats());
        }
    }

    public final void dumpUnmonitored(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        long j;
        int i;
        boolean z;
        int i2;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        long j2;
        if (DumpUtils.checkDumpAndUsageStatsPermission(
                        this.mContext, "BatteryStatsService", printWriter)
                && this.mSystemServicesReady) {
            long j3 = -1;
            int i3 = -1;
            if (strArr != null) {
                int i4 = 0;
                z = false;
                i2 = 0;
                z2 = false;
                z3 = false;
                z4 = false;
                z5 = false;
                while (i4 < strArr.length) {
                    String str = strArr[i4];
                    if ("--checkin".equals(str)) {
                        z2 = true;
                        z4 = true;
                    } else if ("--history".equals(str)) {
                        i2 |= 8;
                    } else {
                        j2 = j3;
                        if ("--history-start".equals(str)) {
                            i2 |= 8;
                            i4++;
                            if (i4 >= strArr.length) {
                                printWriter.println("Missing time argument for --history-since");
                                dumpHelp$1(printWriter);
                                return;
                            } else {
                                z5 = true;
                                j2 = ParseUtils.parseLong(strArr[i4], 0L);
                            }
                        } else {
                            if ("--history-create-events".equals(str)) {
                                i4++;
                                if (i4 >= strArr.length) {
                                    printWriter.println(
                                            "Missing events argument for --history-create-events");
                                    dumpHelp$1(printWriter);
                                    return;
                                }
                                long parseLong = ParseUtils.parseLong(strArr[i4], 0L);
                                awaitCompletion();
                                synchronized (this.mStats) {
                                    BatteryStatsImpl batteryStatsImpl = this.mStats;
                                    long elapsedRealtime =
                                            batteryStatsImpl.mClock.elapsedRealtime();
                                    long uptimeMillis = batteryStatsImpl.mClock.uptimeMillis();
                                    for (long j4 = 0; j4 < parseLong; j4++) {
                                        batteryStatsImpl.noteLongPartialWakeLockStartInternal(
                                                1000,
                                                "name1",
                                                "historyName1",
                                                elapsedRealtime,
                                                uptimeMillis);
                                        batteryStatsImpl.noteLongPartialWakeLockFinishInternal(
                                                1000,
                                                "name1",
                                                "historyName1",
                                                elapsedRealtime,
                                                uptimeMillis);
                                    }
                                    printWriter.println("Battery history create events started.");
                                }
                            } else if ("-c".equals(str)) {
                                i2 |= 16;
                                z2 = true;
                            } else if ("--proto".equals(str)) {
                                z3 = true;
                            } else if ("--charged".equals(str)) {
                                i2 |= 2;
                            } else if ("--daily".equals(str)) {
                                i2 |= 4;
                            } else if ("--reset-all".equals(str)) {
                                awaitCompletion();
                                synchronized (this.mStats) {
                                    this.mStats.resetAllStatsAndHistoryLocked(2);
                                    this.mPowerStatsStore.reset();
                                    printWriter.println("Battery stats and history reset.");
                                }
                            } else if ("--reset".equals(str)) {
                                awaitCompletion();
                                synchronized (this.mStats) {
                                    this.mStats.resetAllStatsAndHistoryLocked(2);
                                    printWriter.println("Battery stats reset.");
                                }
                            } else if ("--write".equals(str)) {
                                awaitCompletion();
                                syncStats("dump");
                                synchronized (this.mStats) {
                                    this.mStats.writeSyncLocked();
                                    printWriter.println("Battery stats written.");
                                }
                            } else if ("--new-daily".equals(str)) {
                                awaitCompletion();
                                synchronized (this.mStats) {
                                    this.mStats.recordDailyStatsLocked();
                                    printWriter.println("New daily stats written.");
                                }
                            } else if ("--read-daily".equals(str)) {
                                awaitCompletion();
                                synchronized (this.mStats) {
                                    this.mStats.readDailyStatsLocked();
                                    printWriter.println("Last daily stats read.");
                                }
                            } else {
                                if ("--enable".equals(str) || "enable".equals(str)) {
                                    int doEnableOrDisable =
                                            doEnableOrDisable(printWriter, i4, strArr, true);
                                    if (doEnableOrDisable < 0) {
                                        return;
                                    }
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("Enabled: "),
                                            strArr[doEnableOrDisable],
                                            printWriter);
                                    return;
                                }
                                if ("--disable".equals(str) || "disable".equals(str)) {
                                    int doEnableOrDisable2 =
                                            doEnableOrDisable(printWriter, i4, strArr, false);
                                    if (doEnableOrDisable2 < 0) {
                                        return;
                                    }
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("Disabled: "),
                                            strArr[doEnableOrDisable2],
                                            printWriter);
                                    return;
                                }
                                if ("-h".equals(str)) {
                                    dumpHelp$1(printWriter);
                                    return;
                                }
                                if ("--settings".equals(str)) {
                                    dumpSettings(printWriter);
                                    return;
                                }
                                if ("--cpu".equals(str)) {
                                    dumpCpuStats(printWriter);
                                    return;
                                }
                                if ("--measured-energy".equals(str)) {
                                    dumpMeasuredEnergyStats(printWriter);
                                    return;
                                }
                                if ("--power-profile".equals(str)) {
                                    dumpPowerProfile(printWriter);
                                    return;
                                }
                                if ("--usage".equals(str)) {
                                    int i5 = i4 + 1;
                                    boolean z6 = false;
                                    int i6 = 0;
                                    while (i5 < strArr.length) {
                                        String str2 = strArr[i5];
                                        str2.getClass();
                                        if (str2.equals("--model")) {
                                            i5++;
                                            if (i5 >= strArr.length) {
                                                printWriter.println("--model without a value");
                                                dumpHelp$1(printWriter);
                                                return;
                                            } else {
                                                if (!"power-profile".equals(strArr[i5])) {
                                                    printWriter.println(
                                                            "Unknown power model: " + strArr[i5]);
                                                    dumpHelp$1(printWriter);
                                                    return;
                                                }
                                                i6 = 1;
                                            }
                                        } else if (str2.equals("--proto")) {
                                            z6 = true;
                                        }
                                        i5++;
                                    }
                                    dumpUsageStats(fileDescriptor, printWriter, i6, z6);
                                    return;
                                }
                                if ("--wakeups".equals(str)) {
                                    this.mCpuWakeupStats.dump(
                                            new IndentingPrintWriter(printWriter, "  "),
                                            SystemClock.elapsedRealtime());
                                    return;
                                }
                                if (com.android.internal.hidden_from_bootclasspath.com.android
                                                .server.power.optimization.Flags
                                                .streamlinedBatteryStats()
                                        && "--sample".equals(str)) {
                                    BatteryStatsImpl batteryStatsImpl2 = this.mStats;
                                    batteryStatsImpl2.mCpuPowerStatsCollector.collectAndDump(
                                            printWriter);
                                    batteryStatsImpl2.mMobileRadioPowerStatsCollector
                                            .collectAndDump(printWriter);
                                    batteryStatsImpl2.mWifiPowerStatsCollector.collectAndDump(
                                            printWriter);
                                    batteryStatsImpl2.mBluetoothPowerStatsCollector.collectAndDump(
                                            printWriter);
                                    batteryStatsImpl2.mCameraPowerStatsCollector.collectAndDump(
                                            printWriter);
                                    batteryStatsImpl2.mGnssPowerStatsCollector.collectAndDump(
                                            printWriter);
                                    batteryStatsImpl2.mCustomEnergyConsumerPowerStatsCollector
                                            .collectAndDump(printWriter);
                                    return;
                                }
                                if (com.android.internal.hidden_from_bootclasspath.com.android
                                                .server.power.optimization.Flags
                                                .streamlinedBatteryStats()
                                        && "--aggregated".equals(str)) {
                                    final PowerStatsScheduler powerStatsScheduler =
                                            this.mPowerStatsScheduler;
                                    Handler handler = powerStatsScheduler.mHandler;
                                    if (handler.getLooper().isCurrentThread()) {
                                        throw new IllegalStateException(
                                                "Should not be executed on the bg handler thread.");
                                    }
                                    powerStatsScheduler.schedulePowerStatsAggregation();
                                    ConditionVariable conditionVariable = new ConditionVariable();
                                    powerStatsScheduler.mHandler.post(
                                            new BatteryStatsImpl$$ExternalSyntheticLambda3(
                                                    2, conditionVariable));
                                    conditionVariable.block();
                                    final IndentingPrintWriter indentingPrintWriter =
                                            new IndentingPrintWriter(printWriter);
                                    handler.post(
                                            new Runnable() { // from class:
                                                             // com.android.server.power.stats.PowerStatsScheduler$$ExternalSyntheticLambda1
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    PowerStatsScheduler powerStatsScheduler2 =
                                                            PowerStatsScheduler.this;
                                                    IndentingPrintWriter indentingPrintWriter2 =
                                                            indentingPrintWriter;
                                                    powerStatsScheduler2.mPowerStatsStore.dump(
                                                            indentingPrintWriter2);
                                                    powerStatsScheduler2.mPowerStatsAggregator
                                                            .aggregatePowerStats(
                                                                    powerStatsScheduler2
                                                                            .getLastSavedSpanEndMonotonicTime(),
                                                                    -1L,
                                                                    new PowerStatsScheduler$$ExternalSyntheticLambda2(
                                                                            1,
                                                                            indentingPrintWriter2));
                                                }
                                            });
                                    ConditionVariable conditionVariable2 = new ConditionVariable();
                                    powerStatsScheduler.mHandler.post(
                                            new BatteryStatsImpl$$ExternalSyntheticLambda3(
                                                    2, conditionVariable2));
                                    conditionVariable2.block();
                                    return;
                                }
                                if ("--store".equals(str)) {
                                    this.mPowerStatsStore.dump(
                                            new IndentingPrintWriter(printWriter, "  "));
                                    return;
                                }
                                if ("--store-toc".equals(str)) {
                                    PowerStatsStore powerStatsStore = this.mPowerStatsStore;
                                    IndentingPrintWriter indentingPrintWriter2 =
                                            new IndentingPrintWriter(printWriter, "  ");
                                    powerStatsStore.getClass();
                                    indentingPrintWriter2.println("Power stats store TOC");
                                    indentingPrintWriter2.increaseIndent();
                                    Iterator it = powerStatsStore.getTableOfContents().iterator();
                                    while (it.hasNext()) {
                                        ((PowerStatsSpan.Metadata) it.next())
                                                .dump(indentingPrintWriter2, true);
                                    }
                                    indentingPrintWriter2.decreaseIndent();
                                    return;
                                }
                                if ("-a".equals(str)) {
                                    i2 |= 32;
                                } else if (str.length() > 0 && str.charAt(0) == '-') {
                                    printWriter.println("Unknown option: ".concat(str));
                                    dumpHelp$1(printWriter);
                                    return;
                                } else {
                                    try {
                                        i3 =
                                                this.mContext
                                                        .getPackageManager()
                                                        .getPackageUidAsUser(
                                                                str, UserHandle.getCallingUserId());
                                    } catch (PackageManager.NameNotFoundException unused) {
                                        printWriter.println("Unknown package: ".concat(str));
                                        dumpHelp$1(printWriter);
                                        return;
                                    }
                                }
                            }
                            z = true;
                        }
                        i4++;
                        j3 = j2;
                    }
                    j2 = j3;
                    i4++;
                    j3 = j2;
                }
                j = j3;
                i = i3;
            } else {
                j = -1;
                i = -1;
                z = false;
                i2 = 0;
                z2 = false;
                z3 = false;
                z4 = false;
                z5 = false;
            }
            if (z) {
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (BatteryStats.checkWifiOnly(this.mContext)) {
                    i2 |= 64;
                }
                awaitCompletion();
                syncStats("dump");
                if (i >= 0 && (i2 & 10) == 0) {
                    i2 = (2 | i2) & (-17);
                }
                if (z3) {
                    List<ApplicationInfo> installedApplications =
                            this.mContext.getPackageManager().getInstalledApplications(4325376);
                    if (z4) {
                        synchronized (this.mStats.mCheckinFile) {
                            try {
                                if (this.mStats.mCheckinFile.exists()) {
                                    try {
                                        byte[] readFully = this.mStats.mCheckinFile.readFully();
                                        if (readFully != null) {
                                            Parcel obtain = Parcel.obtain();
                                            obtain.unmarshall(readFully, 0, readFully.length);
                                            obtain.setDataPosition(0);
                                            BatteryStatsImpl batteryStatsImpl3 =
                                                    new BatteryStatsImpl(
                                                            this.mBatteryStatsConfig,
                                                            Clock.SYSTEM_CLOCK,
                                                            this.mMonotonicClock,
                                                            null,
                                                            this.mStats.mHandler,
                                                            null,
                                                            null,
                                                            this.mUserManagerUserInfoProvider,
                                                            this.mPowerProfile,
                                                            this.mCpuScalingPolicies,
                                                            new PowerStatsUidResolver());
                                            batteryStatsImpl3.readSummaryFromParcel(obtain);
                                            obtain.recycle();
                                            batteryStatsImpl3.dumpProtoLocked(
                                                    this.mContext,
                                                    fileDescriptor,
                                                    installedApplications,
                                                    i2,
                                                    j,
                                                    this.mDumpHelper);
                                            this.mStats.mCheckinFile.delete();
                                            return;
                                        }
                                    } catch (ParcelFormatException | IOException e) {
                                        Slog.w(
                                                "BatteryStatsService",
                                                "Failure reading checkin file "
                                                        + this.mStats.mCheckinFile.getBaseFile(),
                                                e);
                                    }
                                }
                            } finally {
                            }
                        }
                    }
                    awaitCompletion();
                    synchronized (this.mStats) {
                        try {
                            this.mStats.dumpProtoLocked(
                                    this.mContext,
                                    fileDescriptor,
                                    installedApplications,
                                    i2,
                                    j,
                                    this.mDumpHelper);
                            if (z5) {
                                this.mStats.writeAsyncLocked();
                            }
                        } finally {
                        }
                    }
                    return;
                }
                if (!z2) {
                    awaitCompletion();
                    this.mStats.dump(this.mContext, printWriter, i2, i, j, this.mDumpHelper);
                    if (z5) {
                        this.mStats.writeAsyncLocked();
                    }
                    printWriter.println();
                    this.mCpuWakeupStats.dump(
                            new IndentingPrintWriter(printWriter, "  "),
                            SystemClock.elapsedRealtime());
                    this.mDeviceBatteryInfoServiceInternal.dump(printWriter);
                    return;
                }
                List<ApplicationInfo> installedApplications2 =
                        this.mContext.getPackageManager().getInstalledApplications(4325376);
                if (z4) {
                    synchronized (this.mStats.mCheckinFile) {
                        try {
                            if (this.mStats.mCheckinFile.exists()) {
                                try {
                                    byte[] readFully2 = this.mStats.mCheckinFile.readFully();
                                    if (readFully2 != null) {
                                        Parcel obtain2 = Parcel.obtain();
                                        obtain2.unmarshall(readFully2, 0, readFully2.length);
                                        obtain2.setDataPosition(0);
                                        BatteryStatsImpl batteryStatsImpl4 =
                                                new BatteryStatsImpl(
                                                        this.mBatteryStatsConfig,
                                                        Clock.SYSTEM_CLOCK,
                                                        this.mMonotonicClock,
                                                        null,
                                                        this.mStats.mHandler,
                                                        null,
                                                        null,
                                                        this.mUserManagerUserInfoProvider,
                                                        this.mPowerProfile,
                                                        this.mCpuScalingPolicies,
                                                        new PowerStatsUidResolver());
                                        batteryStatsImpl4.readSummaryFromParcel(obtain2);
                                        obtain2.recycle();
                                        batteryStatsImpl4.dumpCheckin(
                                                this.mContext,
                                                printWriter,
                                                installedApplications2,
                                                i2,
                                                j,
                                                this.mDumpHelper);
                                        this.mStats.mCheckinFile.delete();
                                        return;
                                    }
                                } catch (ParcelFormatException | IOException e2) {
                                    Slog.w(
                                            "BatteryStatsService",
                                            "Failure reading checkin file "
                                                    + this.mStats.mCheckinFile.getBaseFile(),
                                            e2);
                                }
                            }
                        } finally {
                        }
                    }
                }
                awaitCompletion();
                this.mStats.dumpCheckin(
                        this.mContext,
                        printWriter,
                        installedApplications2,
                        i2,
                        j,
                        this.mDumpHelper);
                if (z5) {
                    this.mStats.writeAsyncLocked();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void dumpUsageStats(
            FileDescriptor fileDescriptor, PrintWriter printWriter, int i, boolean z) {
        awaitCompletion();
        syncStats("dump");
        BatteryUsageStatsQuery.Builder includePowerModels =
                new BatteryUsageStatsQuery.Builder()
                        .setMaxStatsAgeMs(0L)
                        .includeProcessStateData()
                        .includePowerModels();
        if (i == 1) {
            includePowerModels.powerProfileModeledOnly();
        }
        BatteryUsageStatsQuery build = includePowerModels.build();
        synchronized (this.mStats) {
            this.mStats.prepareForDumpLocked();
        }
        if (com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization
                .Flags.streamlinedBatteryStats()) {
            BatteryStatsImpl batteryStatsImpl = this.mStats;
            batteryStatsImpl.schedulePowerStatsSampleCollection();
            ConditionVariable conditionVariable = new ConditionVariable();
            batteryStatsImpl.mHandler.post(
                    new BatteryStatsImpl$$ExternalSyntheticLambda3(2, conditionVariable));
            conditionVariable.block();
        }
        BatteryUsageStatsProvider batteryUsageStatsProvider = this.mBatteryUsageStatsProvider;
        BatteryUsageStats batteryUsageStats =
                batteryUsageStatsProvider.getBatteryUsageStats(
                        this.mStats, build, batteryUsageStatsProvider.mClock.currentTimeMillis());
        if (z) {
            batteryUsageStats.dumpToProto(fileDescriptor);
        } else {
            batteryUsageStats.dump(printWriter, "  ");
        }
    }

    public final void fillRailDataStats(RailStats railStats) {
        getRailEnergyPowerStats(railStats);
    }

    public final long getAwakeTimeBattery() {
        getAwakeTimeBattery_enforcePermission();
        BatteryStatsImpl batteryStatsImpl = this.mStats;
        return batteryStatsImpl.mOnBatteryTimeBase.getUptime(
                batteryStatsImpl.mClock.uptimeMillis() * 1000);
    }

    public final long getAwakeTimePlugged() {
        getAwakeTimePlugged_enforcePermission();
        BatteryStatsImpl batteryStatsImpl = this.mStats;
        return (batteryStatsImpl.mClock.uptimeMillis() * 1000)
                - batteryStatsImpl.mOnBatteryTimeBase.getUptime(
                        batteryStatsImpl.mClock.uptimeMillis() * 1000);
    }

    public final List getBatteryUsageStats(List list) {
        long j;
        getBatteryUsageStats_enforcePermission();
        awaitCompletion();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        BatteryExternalStatsWorker batteryExternalStatsWorker = this.mWorker;
        synchronized (batteryExternalStatsWorker) {
            j = batteryExternalStatsWorker.mLastCollectionTimeStamp;
        }
        long j2 = Long.MAX_VALUE;
        for (int size = list.size() - 1; size >= 0; size--) {
            j2 = Math.min(j2, ((BatteryUsageStatsQuery) list.get(size)).getMaxStatsAge());
        }
        if (elapsedRealtime - j > j2) {
            syncStats("get-stats");
            if (com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization
                    .Flags.streamlinedBatteryStats()) {
                BatteryStatsImpl batteryStatsImpl = this.mStats;
                batteryStatsImpl.schedulePowerStatsSampleCollection();
                ConditionVariable conditionVariable = new ConditionVariable();
                batteryStatsImpl.mHandler.post(
                        new BatteryStatsImpl$$ExternalSyntheticLambda3(2, conditionVariable));
                conditionVariable.block();
            }
        }
        BatteryUsageStatsProvider batteryUsageStatsProvider = this.mBatteryUsageStatsProvider;
        BatteryStatsImpl batteryStatsImpl2 = this.mStats;
        batteryUsageStatsProvider.getClass();
        ArrayList arrayList = new ArrayList(list.size());
        synchronized (batteryStatsImpl2) {
            batteryStatsImpl2.prepareForDumpLocked();
        }
        long currentTimeMillis = batteryUsageStatsProvider.mClock.currentTimeMillis();
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(
                    batteryUsageStatsProvider.getBatteryUsageStats(
                            batteryStatsImpl2,
                            (BatteryUsageStatsQuery) list.get(i),
                            currentTimeMillis));
        }
        return arrayList;
    }

    public final BluetoothBatteryStats getBluetoothBatteryStats() {
        BluetoothBatteryStats bluetoothBatteryStats;
        getBluetoothBatteryStats_enforcePermission();
        awaitCompletion();
        synchronized (this.mStats) {
            bluetoothBatteryStats = this.mStats.getBluetoothBatteryStats();
        }
        return bluetoothBatteryStats;
    }

    public final CellularBatteryStats getCellularBatteryStats() {
        CellularBatteryStats cellularBatteryStats;
        getCellularBatteryStats_enforcePermission();
        awaitCompletion();
        synchronized (this.mStats) {
            cellularBatteryStats = this.mStats.getCellularBatteryStats();
        }
        return cellularBatteryStats;
    }

    public final SemCompanionDeviceBatteryInfo getDeviceBatteryInfo(String str) {
        Slog.i("BatteryStatsService", "getDeviceBatteryInfo address: " + str);
        return this.mDeviceBatteryInfoServiceInternal.getDeviceBatteryInfo(str);
    }

    public final SemCompanionDeviceBatteryInfo[] getDeviceBatteryInfos() {
        SemCompanionDeviceBatteryInfo[] semCompanionDeviceBatteryInfoArr;
        Slog.i("BatteryStatsService", "semGetBatteryInfos()");
        DeviceBatteryInfoService deviceBatteryInfoService = this.mDeviceBatteryInfoServiceInternal;
        deviceBatteryInfoService.requirePermissions();
        Slog.i("DeviceBatteryInfoService", "semGetBatteryInfos()");
        synchronized (deviceBatteryInfoService.mBatteryInfosLock) {
            try {
                HashMap hashMap = deviceBatteryInfoService.mBatteryInfos;
                if (hashMap != null) {
                    hashMap.forEach(new DeviceBatteryInfoService$$ExternalSyntheticLambda1());
                    semCompanionDeviceBatteryInfoArr =
                            (SemCompanionDeviceBatteryInfo[])
                                    deviceBatteryInfoService
                                            .mBatteryInfos
                                            .values()
                                            .toArray(
                                                    new SemCompanionDeviceBatteryInfo
                                                            [deviceBatteryInfoService.mBatteryInfos
                                                                    .size()]);
                } else {
                    semCompanionDeviceBatteryInfoArr = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return semCompanionDeviceBatteryInfoArr;
    }

    public final GpsBatteryStats getGpsBatteryStats() {
        GpsBatteryStats gpsBatteryStats;
        getGpsBatteryStats_enforcePermission();
        awaitCompletion();
        synchronized (this.mStats) {
            gpsBatteryStats = this.mStats.getGpsBatteryStats();
        }
        return gpsBatteryStats;
    }

    public final HealthStatsParceler getHealthStatsForUidLocked(int i) {
        HealthStatsBatteryStatsWriter healthStatsBatteryStatsWriter =
                new HealthStatsBatteryStatsWriter();
        HealthStatsWriter healthStatsWriter = new HealthStatsWriter(UidHealthStats.CONSTANTS);
        BatteryStats.Uid uid = (BatteryStats.Uid) this.mStats.mUidStats.get(i);
        if (uid != null) {
            BatteryStatsImpl batteryStatsImpl = this.mStats;
            long j = healthStatsBatteryStatsWriter.mNowRealtimeMs * 1000;
            healthStatsWriter.addMeasurement(
                    10001, batteryStatsImpl.computeBatteryRealtime(j, 0) / 1000);
            long j2 = healthStatsBatteryStatsWriter.mNowUptimeMs * 1000;
            healthStatsWriter.addMeasurement(
                    10002, batteryStatsImpl.computeBatteryUptime(j2, 0) / 1000);
            healthStatsWriter.addMeasurement(
                    10003, batteryStatsImpl.computeBatteryScreenOffRealtime(j, 0) / 1000);
            healthStatsWriter.addMeasurement(
                    10004, batteryStatsImpl.computeBatteryScreenOffUptime(j2, 0) / 1000);
            for (Map.Entry entry : uid.getWakelockStats().entrySet()) {
                String str = (String) entry.getKey();
                BatteryStats.Uid.Wakelock wakelock = (BatteryStats.Uid.Wakelock) entry.getValue();
                healthStatsBatteryStatsWriter.addTimers(
                        healthStatsWriter,
                        FrameworkStatsLog.SUBSYSTEM_SLEEP_STATE,
                        str,
                        wakelock.getWakeTime(1));
                healthStatsBatteryStatsWriter.addTimers(
                        healthStatsWriter,
                        FrameworkStatsLog.BLUETOOTH_BYTES_TRANSFER,
                        str,
                        wakelock.getWakeTime(0));
                healthStatsBatteryStatsWriter.addTimers(
                        healthStatsWriter,
                        FrameworkStatsLog.BLUETOOTH_ACTIVITY_INFO,
                        str,
                        wakelock.getWakeTime(2));
                healthStatsBatteryStatsWriter.addTimers(
                        healthStatsWriter, 10008, str, wakelock.getWakeTime(18));
            }
            for (Map.Entry entry2 : uid.getSyncStats().entrySet()) {
                healthStatsBatteryStatsWriter.addTimers(
                        healthStatsWriter,
                        FrameworkStatsLog.CPU_TIME_PER_UID,
                        (String) entry2.getKey(),
                        (BatteryStats.Timer) entry2.getValue());
            }
            for (Map.Entry entry3 : uid.getJobStats().entrySet()) {
                healthStatsBatteryStatsWriter.addTimers(
                        healthStatsWriter,
                        FrameworkStatsLog.CPU_TIME_PER_UID_FREQ,
                        (String) entry3.getKey(),
                        (BatteryStats.Timer) entry3.getValue());
            }
            SparseArray sensorStats = uid.getSensorStats();
            int size = sensorStats.size();
            for (int i2 = 0; i2 < size; i2++) {
                int keyAt = sensorStats.keyAt(i2);
                if (keyAt == -10000) {
                    healthStatsBatteryStatsWriter.addTimer(
                            healthStatsWriter,
                            FrameworkStatsLog.WIFI_ACTIVITY_INFO,
                            ((BatteryStats.Uid.Sensor) sensorStats.valueAt(i2)).getSensorTime());
                } else if (keyAt == -10001) {
                    healthStatsBatteryStatsWriter.addTimer(
                            healthStatsWriter,
                            10065,
                            ((BatteryStats.Uid.Sensor) sensorStats.valueAt(i2)).getSensorTime());
                } else {
                    healthStatsBatteryStatsWriter.addTimers(
                            healthStatsWriter,
                            FrameworkStatsLog.MODEM_ACTIVITY_INFO,
                            Integer.toString(keyAt),
                            ((BatteryStats.Uid.Sensor) sensorStats.valueAt(i2)).getSensorTime());
                }
            }
            SparseArray pidStats = uid.getPidStats();
            int size2 = pidStats.size();
            for (int i3 = 0; i3 < size2; i3++) {
                HealthStatsWriter healthStatsWriter2 =
                        new HealthStatsWriter(PidHealthStats.CONSTANTS);
                BatteryStats.Uid.Pid pid = (BatteryStats.Uid.Pid) pidStats.valueAt(i3);
                if (pid != null) {
                    healthStatsWriter2.addMeasurement(20001, pid.mWakeNesting);
                    healthStatsWriter2.addMeasurement(20002, pid.mWakeSumMs);
                    healthStatsWriter2.addMeasurement(20002, pid.mWakeStartMs);
                }
                healthStatsWriter.addStats(
                        FrameworkStatsLog.PROCESS_MEMORY_STATE,
                        Integer.toString(pidStats.keyAt(i3)),
                        healthStatsWriter2);
            }
            for (Map.Entry entry4 : uid.getProcessStats().entrySet()) {
                HealthStatsWriter healthStatsWriter3 =
                        new HealthStatsWriter(ProcessHealthStats.CONSTANTS);
                BatteryStats.Uid.Proc proc = (BatteryStats.Uid.Proc) entry4.getValue();
                healthStatsWriter3.addMeasurement(30001, proc.getUserTime(0));
                healthStatsWriter3.addMeasurement(30002, proc.getSystemTime(0));
                healthStatsWriter3.addMeasurement(30003, proc.getStarts(0));
                healthStatsWriter3.addMeasurement(30004, proc.getNumCrashes(0));
                healthStatsWriter3.addMeasurement(30005, proc.getNumAnrs(0));
                healthStatsWriter3.addMeasurement(30006, proc.getForegroundTime(0));
                healthStatsWriter.addStats(
                        FrameworkStatsLog.SYSTEM_ELAPSED_REALTIME,
                        (String) entry4.getKey(),
                        healthStatsWriter3);
            }
            Iterator it = uid.getPackageStats().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry5 = (Map.Entry) it.next();
                HealthStatsWriter healthStatsWriter4 =
                        new HealthStatsWriter(PackageHealthStats.CONSTANTS);
                BatteryStats.Uid.Pkg pkg = (BatteryStats.Uid.Pkg) entry5.getValue();
                for (Map.Entry entry6 : pkg.getServiceStats().entrySet()) {
                    HealthStatsWriter healthStatsWriter5 =
                            new HealthStatsWriter(ServiceHealthStats.CONSTANTS);
                    BatteryStats.Uid.Pkg.Serv serv = (BatteryStats.Uid.Pkg.Serv) entry6.getValue();
                    healthStatsWriter5.addMeasurement(50001, serv.getStarts(0));
                    healthStatsWriter5.addMeasurement(50002, serv.getLaunches(0));
                    healthStatsWriter4.addStats(
                            40001, (String) entry6.getKey(), healthStatsWriter5);
                    it = it;
                }
                Iterator it2 = it;
                for (Map.Entry entry7 : pkg.getWakeupAlarmStats().entrySet()) {
                    if (((BatteryStats.Counter) entry7.getValue()) != null) {
                        healthStatsWriter4.addMeasurements(
                                40002, (String) entry7.getKey(), r7.getCountLocked(0));
                    }
                }
                healthStatsWriter.addStats(
                        FrameworkStatsLog.SYSTEM_UPTIME,
                        (String) entry5.getKey(),
                        healthStatsWriter4);
                it = it2;
            }
            BatteryStats.ControllerActivityCounter wifiControllerActivity =
                    uid.getWifiControllerActivity();
            if (wifiControllerActivity != null) {
                healthStatsWriter.addMeasurement(
                        FrameworkStatsLog.CPU_ACTIVE_TIME,
                        wifiControllerActivity.getIdleTimeCounter().getCountLocked(0));
                healthStatsWriter.addMeasurement(
                        FrameworkStatsLog.CPU_CLUSTER_TIME,
                        wifiControllerActivity.getRxTimeCounter().getCountLocked(0));
                long j3 = 0;
                for (BatteryStats.LongCounter longCounter :
                        wifiControllerActivity.getTxTimeCounters()) {
                    j3 += longCounter.getCountLocked(0);
                }
                healthStatsWriter.addMeasurement(10018, j3);
                healthStatsWriter.addMeasurement(
                        FrameworkStatsLog.REMAINING_BATTERY_CAPACITY,
                        wifiControllerActivity.getPowerCounter().getCountLocked(0));
            }
            BatteryStats.ControllerActivityCounter bluetoothControllerActivity =
                    uid.getBluetoothControllerActivity();
            if (bluetoothControllerActivity != null) {
                healthStatsWriter.addMeasurement(
                        FrameworkStatsLog.FULL_BATTERY_CAPACITY,
                        bluetoothControllerActivity.getIdleTimeCounter().getCountLocked(0));
                healthStatsWriter.addMeasurement(
                        FrameworkStatsLog.TEMPERATURE,
                        bluetoothControllerActivity.getRxTimeCounter().getCountLocked(0));
                long j4 = 0;
                for (BatteryStats.LongCounter longCounter2 :
                        bluetoothControllerActivity.getTxTimeCounters()) {
                    j4 += longCounter2.getCountLocked(0);
                }
                healthStatsWriter.addMeasurement(FrameworkStatsLog.BINDER_CALLS, j4);
                healthStatsWriter.addMeasurement(
                        FrameworkStatsLog.BINDER_CALLS_EXCEPTIONS,
                        bluetoothControllerActivity.getPowerCounter().getCountLocked(0));
            }
            BatteryStats.ControllerActivityCounter modemControllerActivity =
                    uid.getModemControllerActivity();
            if (modemControllerActivity != null) {
                healthStatsWriter.addMeasurement(
                        FrameworkStatsLog.LOOPER_STATS,
                        modemControllerActivity.getIdleTimeCounter().getCountLocked(0));
                healthStatsWriter.addMeasurement(
                        FrameworkStatsLog.DISK_STATS,
                        modemControllerActivity.getRxTimeCounter().getCountLocked(0));
                long j5 = 0;
                for (BatteryStats.LongCounter longCounter3 :
                        modemControllerActivity.getTxTimeCounters()) {
                    j5 += longCounter3.getCountLocked(0);
                }
                healthStatsWriter.addMeasurement(FrameworkStatsLog.DIRECTORY_USAGE, j5);
                healthStatsWriter.addMeasurement(
                        FrameworkStatsLog.APP_SIZE,
                        modemControllerActivity.getPowerCounter().getCountLocked(0));
            }
            healthStatsWriter.addMeasurement(
                    FrameworkStatsLog.CATEGORY_SIZE, uid.getWifiRunningTime(j, 0) / 1000);
            healthStatsWriter.addMeasurement(
                    FrameworkStatsLog.PROC_STATS, uid.getFullWifiLockTime(j, 0) / 1000);
            healthStatsWriter.addTimer(
                    FrameworkStatsLog.BATTERY_VOLTAGE,
                    uid.getWifiScanCount(0),
                    uid.getWifiScanTime(j, 0) / 1000);
            healthStatsWriter.addMeasurement(
                    FrameworkStatsLog.NUM_FINGERPRINTS_ENROLLED,
                    uid.getWifiMulticastTime(j, 0) / 1000);
            healthStatsBatteryStatsWriter.addTimer(
                    healthStatsWriter, FrameworkStatsLog.DISK_IO, uid.getAudioTurnedOnTimer());
            healthStatsBatteryStatsWriter.addTimer(
                    healthStatsWriter,
                    FrameworkStatsLog.POWER_PROFILE,
                    uid.getVideoTurnedOnTimer());
            healthStatsBatteryStatsWriter.addTimer(
                    healthStatsWriter,
                    FrameworkStatsLog.PROC_STATS_PKG_PROC,
                    uid.getFlashlightTurnedOnTimer());
            healthStatsBatteryStatsWriter.addTimer(
                    healthStatsWriter,
                    FrameworkStatsLog.PROCESS_CPU_TIME,
                    uid.getCameraTurnedOnTimer());
            healthStatsBatteryStatsWriter.addTimer(
                    healthStatsWriter, 10036, uid.getForegroundActivityTimer());
            healthStatsBatteryStatsWriter.addTimer(
                    healthStatsWriter,
                    FrameworkStatsLog.CPU_TIME_PER_THREAD_FREQ,
                    uid.getBluetoothScanTimer());
            healthStatsBatteryStatsWriter.addTimer(
                    healthStatsWriter,
                    FrameworkStatsLog.ON_DEVICE_POWER_MEASUREMENT,
                    uid.getProcessStateTimer(0));
            healthStatsBatteryStatsWriter.addTimer(
                    healthStatsWriter,
                    FrameworkStatsLog.DEVICE_CALCULATED_POWER_USE,
                    uid.getProcessStateTimer(1));
            healthStatsBatteryStatsWriter.addTimer(
                    healthStatsWriter, 10040, uid.getProcessStateTimer(4));
            healthStatsBatteryStatsWriter.addTimer(
                    healthStatsWriter, 10041, uid.getProcessStateTimer(2));
            healthStatsBatteryStatsWriter.addTimer(
                    healthStatsWriter,
                    FrameworkStatsLog.PROCESS_MEMORY_HIGH_WATER_MARK,
                    uid.getProcessStateTimer(3));
            healthStatsBatteryStatsWriter.addTimer(
                    healthStatsWriter,
                    FrameworkStatsLog.BATTERY_LEVEL,
                    uid.getProcessStateTimer(6));
            healthStatsBatteryStatsWriter.addTimer(
                    healthStatsWriter,
                    FrameworkStatsLog.BUILD_INFORMATION,
                    uid.getVibratorOnTimer());
            healthStatsWriter.addMeasurement(
                    FrameworkStatsLog.BATTERY_CYCLE_COUNT, uid.getUserActivityCount(0, 0));
            healthStatsWriter.addMeasurement(
                    FrameworkStatsLog.DEBUG_ELAPSED_CLOCK, uid.getUserActivityCount(1, 0));
            healthStatsWriter.addMeasurement(
                    FrameworkStatsLog.DEBUG_FAILING_ELAPSED_CLOCK, uid.getUserActivityCount(2, 0));
            healthStatsWriter.addMeasurement(
                    FrameworkStatsLog.NUM_FACES_ENROLLED, uid.getNetworkActivityBytes(0, 0));
            healthStatsWriter.addMeasurement(
                    FrameworkStatsLog.ROLE_HOLDER, uid.getNetworkActivityBytes(1, 0));
            healthStatsWriter.addMeasurement(
                    FrameworkStatsLog.DANGEROUS_PERMISSION_STATE,
                    uid.getNetworkActivityBytes(2, 0));
            healthStatsWriter.addMeasurement(10051, uid.getNetworkActivityBytes(3, 0));
            healthStatsWriter.addMeasurement(
                    FrameworkStatsLog.TIME_ZONE_DATA_INFO, uid.getNetworkActivityBytes(4, 0));
            healthStatsWriter.addMeasurement(
                    FrameworkStatsLog.EXTERNAL_STORAGE_INFO, uid.getNetworkActivityBytes(5, 0));
            healthStatsWriter.addMeasurement(10054, uid.getNetworkActivityPackets(0, 0));
            healthStatsWriter.addMeasurement(10055, uid.getNetworkActivityPackets(1, 0));
            healthStatsWriter.addMeasurement(
                    FrameworkStatsLog.SYSTEM_ION_HEAP_SIZE, uid.getNetworkActivityPackets(2, 0));
            healthStatsWriter.addMeasurement(
                    FrameworkStatsLog.APPS_ON_EXTERNAL_STORAGE_INFO,
                    uid.getNetworkActivityPackets(3, 0));
            healthStatsWriter.addMeasurement(
                    FrameworkStatsLog.FACE_SETTINGS, uid.getNetworkActivityPackets(4, 0));
            healthStatsWriter.addMeasurement(
                    FrameworkStatsLog.COOLING_DEVICE, uid.getNetworkActivityPackets(5, 0));
            healthStatsWriter.addTimer(
                    FrameworkStatsLog.PROCESS_SYSTEM_ION_HEAP_SIZE,
                    uid.getMobileRadioActiveCount(0),
                    uid.getMobileRadioActiveTime(0));
            healthStatsWriter.addMeasurement(10062, uid.getUserCpuTimeUs(0) / 1000);
            healthStatsWriter.addMeasurement(10063, uid.getSystemCpuTimeUs(0) / 1000);
            healthStatsWriter.addMeasurement(FrameworkStatsLog.PROCESS_MEMORY_SNAPSHOT, 0L);
        }
        return new HealthStatsParceler(healthStatsWriter);
    }

    public final long getScreenOffDischargeMah() {
        long j;
        getScreenOffDischargeMah_enforcePermission();
        synchronized (this.mStats) {
            j = this.mStats.mDischargeScreenOffCounter.mCount / 1000;
        }
        return j;
    }

    public final SemBatterySipper getSemBatteryUsageStats() {
        ArrayList arrayList;
        getSemBatteryUsageStats_enforcePermission();
        awaitCompletion();
        SemBatteryUsageStatsProvider semBatteryUsageStatsProvider =
                this.mSemBatteryUsageStatsProvider;
        BatteryStats batteryStats = semBatteryUsageStatsProvider.mStats;
        long elapsedRealtime =
                (batteryStats instanceof BatteryStatsImpl
                                ? ((BatteryStatsImpl) batteryStats).mClock.elapsedRealtime()
                                : SystemClock.elapsedRealtime())
                        * 1000;
        BatteryStats batteryStats2 = semBatteryUsageStatsProvider.mStats;
        long uptimeMillis =
                (batteryStats2 instanceof BatteryStatsImpl
                                ? ((BatteryStatsImpl) batteryStats2).mClock.uptimeMillis()
                                : SystemClock.uptimeMillis())
                        * 1000;
        SemDevicePowerInfo semDevicePowerInfo = new SemDevicePowerInfo(0.0d);
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        synchronized (semBatteryUsageStatsProvider.mStats) {
            try {
                BatteryUsageStatsProvider batteryUsageStatsProvider =
                        semBatteryUsageStatsProvider.mBatteryUsageStatsProvider;
                BatteryUsageStats batteryUsageStats =
                        batteryUsageStatsProvider.getBatteryUsageStats(
                                (BatteryStatsImpl) semBatteryUsageStatsProvider.mStats,
                                new BatteryUsageStatsQuery.Builder()
                                        .setMaxStatsAgeMs(0L)
                                        .includePowerModels()
                                        .build(),
                                batteryUsageStatsProvider.mClock.currentTimeMillis());
                if (batteryUsageStats != null) {
                    semBatteryUsageStatsProvider.updateBatteryUsage(
                            batteryUsageStats,
                            elapsedRealtime,
                            uptimeMillis,
                            semDevicePowerInfo,
                            arrayList2);
                    semBatteryUsageStatsProvider.updateWakeupReasonInfoToList(
                            semBatteryUsageStatsProvider.mStats, arrayList3);
                    semBatteryUsageStatsProvider.updateKernelWakelockInfoToList(
                            semBatteryUsageStatsProvider.mStats, arrayList4);
                    arrayList = arrayList5;
                    semBatteryUsageStatsProvider.updateScreenWakeInfoToList(
                            semBatteryUsageStatsProvider.mStats, arrayList);
                } else {
                    arrayList = arrayList5;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return new SemBatterySipper(
                semDevicePowerInfo, arrayList2, arrayList3, arrayList4, arrayList);
    }

    public final int getServiceType() {
        return 9;
    }

    public final WakeLockStats getWakeLockStats() {
        WakeLockStats wakeLockStats;
        getWakeLockStats_enforcePermission();
        awaitCompletion();
        synchronized (this.mStats) {
            wakeLockStats = this.mStats.getWakeLockStats();
        }
        return wakeLockStats;
    }

    public final WifiBatteryStats getWifiBatteryStats() {
        WifiBatteryStats wifiBatteryStats;
        getWifiBatteryStats_enforcePermission();
        awaitCompletion();
        synchronized (this.mStats) {
            wifiBatteryStats = this.mStats.getWifiBatteryStats();
        }
        return wifiBatteryStats;
    }

    public final boolean isCharging() {
        boolean z;
        synchronized (this.mStats) {
            z = this.mStats.mCharging;
        }
        return z;
    }

    @Override // com.android.server.Watchdog.Monitor
    public final void monitor() {
        if (this.mMonitorEnabled) {
            synchronized (this.mLock) {
            }
            synchronized (this.mStats) {
            }
        }
    }

    public final void noteBleDutyScanStarted(WorkSource workSource, boolean z, int i) {
        noteBleDutyScanStarted_enforcePermission();
        WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda48(
                            this,
                            workSource2,
                            z,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            1));
        }
    }

    public final void noteBleDutyScanStopped(WorkSource workSource, boolean z, int i) {
        noteBleDutyScanStopped_enforcePermission();
        WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda48(
                            this,
                            workSource2,
                            z,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            0));
        }
    }

    public final void noteBleScanReset() {
        noteBleScanReset_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda0(
                            this, SystemClock.elapsedRealtime(), SystemClock.uptimeMillis(), 6));
        }
    }

    public final void noteBleScanResults(WorkSource workSource, int i) {
        noteBleScanResults_enforcePermission();
        WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda84(
                            this,
                            workSource2,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            0));
        }
    }

    public final void noteBleScanStarted(WorkSource workSource, boolean z) {
        noteBleScanStarted_enforcePermission();
        WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda7(
                            this,
                            workSource2,
                            z,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            0));
        }
    }

    public final void noteBleScanStopped(WorkSource workSource, boolean z) {
        noteBleScanStopped_enforcePermission();
        WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda7(
                            this,
                            workSource2,
                            z,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            1));
        }
    }

    public final void noteBluetoothControllerActivity(
            BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo) {
        noteBluetoothControllerActivity_enforcePermission();
        if (bluetoothActivityEnergyInfo == null || !bluetoothActivityEnergyInfo.isValid()) {
            Slog.e(
                    "BatteryStatsService",
                    "invalid bluetooth data given: " + bluetoothActivityEnergyInfo);
        } else {
            synchronized (this.mLock) {
                this.mHandler.post(
                        new BatteryStatsService$$ExternalSyntheticLambda28(
                                this,
                                bluetoothActivityEnergyInfo,
                                SystemClock.elapsedRealtime(),
                                SystemClock.uptimeMillis()));
            }
        }
    }

    public final void noteChangeWakelockFromSource(
            WorkSource workSource,
            final int i,
            final String str,
            final String str2,
            final int i2,
            WorkSource workSource2,
            final int i3,
            final String str3,
            final String str4,
            final int i4,
            final boolean z) {
        noteChangeWakelockFromSource_enforcePermission();
        WorkSource workSource3 = workSource != null ? new WorkSource(workSource) : null;
        final WorkSource workSource4 = workSource2 != null ? new WorkSource(workSource2) : null;
        synchronized (this.mLock) {
            try {
                try {
                    final long elapsedRealtime = SystemClock.elapsedRealtime();
                    final long uptimeMillis = SystemClock.uptimeMillis();
                    final WorkSource workSource5 = workSource3;
                    this.mHandler.post(
                            new Runnable() { // from class:
                                             // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda33
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BatteryStats batteryStats = BatteryStatsService.this;
                                    WorkSource workSource6 = workSource5;
                                    int i5 = i;
                                    String str5 = str;
                                    String str6 = str2;
                                    int i6 = i2;
                                    WorkSource workSource7 = workSource4;
                                    int i7 = i3;
                                    String str7 = str3;
                                    String str8 = str4;
                                    int i8 = i4;
                                    boolean z2 = z;
                                    long j = elapsedRealtime;
                                    long j2 = uptimeMillis;
                                    BatteryStats batteryStats2 = batteryStats.mStats;
                                    synchronized (batteryStats2) {
                                        try {
                                            try {
                                                batteryStats.mStats
                                                        .noteChangeWakelockFromSourceLocked(
                                                                workSource6,
                                                                i5,
                                                                str5,
                                                                str6,
                                                                i6,
                                                                workSource7,
                                                                i7,
                                                                str7,
                                                                str8,
                                                                i8,
                                                                z2,
                                                                j,
                                                                j2);
                                            } catch (Throwable th) {
                                                th = th;
                                                batteryStats = batteryStats2;
                                                throw th;
                                            }
                                        } catch (Throwable th2) {
                                            th = th2;
                                            throw th;
                                        }
                                    }
                                }
                            });
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    public final void noteConnectivityChanged(int i, String str) {
        noteConnectivityChanged_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda8(
                            this,
                            i,
                            str,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            8));
        }
    }

    public final void noteCpuWakingActivity(final long j, final int[] iArr, final int i) {
        Objects.requireNonNull(iArr);
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda17
                    @Override // java.lang.Runnable
                    public final void run() {
                        BatteryStatsService batteryStatsService = BatteryStatsService.this;
                        int i2 = i;
                        batteryStatsService.mCpuWakeupStats.noteWakingActivity(j, iArr, i2);
                    }
                });
    }

    public final void noteCurrentTimeChanged() {
        synchronized (this.mLock) {
            final long currentTimeMillis = System.currentTimeMillis();
            final long elapsedRealtime = SystemClock.elapsedRealtime();
            final long uptimeMillis = SystemClock.uptimeMillis();
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda11
                        @Override // java.lang.Runnable
                        public final void run() {
                            BatteryStatsService batteryStatsService = BatteryStatsService.this;
                            long j = currentTimeMillis;
                            long j2 = elapsedRealtime;
                            long j3 = uptimeMillis;
                            synchronized (batteryStatsService.mStats) {
                                BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
                                batteryStatsImpl.mHistory.recordCurrentTimeChange(j2, j3, j);
                                batteryStatsImpl.mStartClockTimeMs =
                                        j
                                                - (batteryStatsImpl.mClock.elapsedRealtime()
                                                        - (batteryStatsImpl.mRealtimeStartUs
                                                                / 1000));
                            }
                        }
                    });
        }
    }

    public final void noteDeviceIdleMode(int i, String str, int i2) {
        noteDeviceIdleMode_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda82(
                            this,
                            i,
                            str,
                            i2,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            1));
        }
    }

    public final void noteDualScreenBrightness(final int i, final int i2, final int i3) {
        noteDualScreenBrightness_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = SystemClock.elapsedRealtime();
            final long uptimeMillis = SystemClock.uptimeMillis();
            this.mHandler.post(
                    new Runnable(
                            i,
                            i2,
                            i3,
                            elapsedRealtime,
                            uptimeMillis,
                            this) { // from class:
                                    // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda79
                        public final /* synthetic */ BatteryStatsService f$0;
                        public final /* synthetic */ int f$1;
                        public final /* synthetic */ int f$2;
                        public final /* synthetic */ int f$3;
                        public final /* synthetic */ long f$4;
                        public final /* synthetic */ long f$5;

                        {
                            this.f$0 = this;
                            this.f$3 = i3;
                            this.f$4 = elapsedRealtime;
                            this.f$5 = uptimeMillis;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            BatteryStatsService batteryStatsService = this.f$0;
                            int i4 = this.f$1;
                            int i5 = this.f$3;
                            long j = this.f$4;
                            long j2 = this.f$5;
                            synchronized (batteryStatsService.mStats) {
                                batteryStatsService.mStats.noteScreenBrightnessLocked(
                                        i4, i5, j, j2);
                            }
                        }
                    });
        }
        FrameworkStatsLog.write(9, i);
    }

    public final void noteDualScreenState(final int i, final int i2, final int i3) {
        noteDualScreenState_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = SystemClock.elapsedRealtime();
            final long uptimeMillis = SystemClock.uptimeMillis();
            final long currentTimeMillis = System.currentTimeMillis();
            this.mHandler.post(
                    new Runnable(
                            i,
                            i2,
                            i3,
                            elapsedRealtime,
                            uptimeMillis,
                            currentTimeMillis,
                            this) { // from class:
                                    // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda5
                        public final /* synthetic */ BatteryStatsService f$0;
                        public final /* synthetic */ int f$1;
                        public final /* synthetic */ int f$2;
                        public final /* synthetic */ int f$3;
                        public final /* synthetic */ long f$4;
                        public final /* synthetic */ long f$5;
                        public final /* synthetic */ long f$6;

                        {
                            this.f$0 = this;
                            this.f$3 = i3;
                            this.f$4 = elapsedRealtime;
                            this.f$5 = uptimeMillis;
                            this.f$6 = currentTimeMillis;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            BatteryStatsService batteryStatsService = this.f$0;
                            int i4 = this.f$1;
                            int i5 = this.f$3;
                            long j = this.f$4;
                            long j2 = this.f$5;
                            long j3 = this.f$6;
                            synchronized (batteryStatsService.mStats) {
                                batteryStatsService.mStats.noteScreenStateLocked(i4, i5, j, j2, j3);
                            }
                        }
                    });
        }
        FrameworkStatsLog.write(29, i);
    }

    public final void noteEvent(int i, String str, int i2) {
        noteEvent_enforcePermission();
        if (str == null) {
            Slog.wtfStack("BatteryStatsService", "noteEvent called with null name. code = " + i);
        } else {
            synchronized (this.mLock) {
                this.mHandler.post(
                        new BatteryStatsService$$ExternalSyntheticLambda82(
                                this,
                                i,
                                str,
                                i2,
                                SystemClock.elapsedRealtime(),
                                SystemClock.uptimeMillis(),
                                2));
            }
        }
    }

    public final void noteFlashlightOff(int i) {
        noteFlashlightOff_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda2(
                            this,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            14));
        }
        FrameworkStatsLog.write_non_chained(26, i, null, 0);
    }

    public final void noteFlashlightOn(int i) {
        noteFlashlightOn_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda2(
                            this,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            12));
        }
        FrameworkStatsLog.write_non_chained(26, i, null, 1);
    }

    public final void noteFullWifiLockAcquired(int i) {
        noteFullWifiLockAcquired_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda2(
                            this, i, SystemClock.elapsedRealtime(), SystemClock.uptimeMillis(), 0));
        }
    }

    public final void noteFullWifiLockAcquiredFromSource(WorkSource workSource) {
        noteFullWifiLockAcquiredFromSource_enforcePermission();
        WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda3(
                            this,
                            workSource2,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            1));
        }
    }

    public final void noteFullWifiLockReleased(int i) {
        noteFullWifiLockReleased_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda2(
                            this,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            16));
        }
    }

    public final void noteFullWifiLockReleasedFromSource(WorkSource workSource) {
        noteFullWifiLockReleasedFromSource_enforcePermission();
        WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda3(
                            this,
                            workSource2,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            3));
        }
    }

    public final void noteGpsChanged(WorkSource workSource, WorkSource workSource2) {
        noteGpsChanged_enforcePermission();
        WorkSource workSource3 = workSource != null ? new WorkSource(workSource) : null;
        WorkSource workSource4 = workSource2 != null ? new WorkSource(workSource2) : null;
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda71(
                            this,
                            workSource3,
                            workSource4,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            1));
        }
    }

    public final void noteGpsSignalQuality(int i) {
        noteGpsSignalQuality_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda2(
                            this, i, SystemClock.elapsedRealtime(), SystemClock.uptimeMillis(), 3));
        }
    }

    public final void noteInteractive(final boolean z) {
        noteInteractive_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = SystemClock.elapsedRealtime();
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda120
                        @Override // java.lang.Runnable
                        public final void run() {
                            BatteryStatsService batteryStatsService = BatteryStatsService.this;
                            boolean z2 = z;
                            long j = elapsedRealtime;
                            synchronized (batteryStatsService.mStats) {
                                BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
                                if (batteryStatsImpl.mInteractive != z2) {
                                    batteryStatsImpl.mInteractive = z2;
                                    if (z2) {
                                        batteryStatsImpl.mInteractiveTimer.startRunningLocked(j);
                                    } else {
                                        batteryStatsImpl.mInteractiveTimer.stopRunningLocked(j);
                                    }
                                }
                            }
                        }
                    });
        }
    }

    public final void noteJobFinish(String str, int i, int i2) {
        noteJobFinish_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda82(
                            this,
                            i,
                            str,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            i2));
        }
    }

    public final void noteJobStart(String str, int i) {
        noteJobStart_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda8(
                            this,
                            str,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            6));
        }
    }

    public final void noteLongPartialWakelockFinish(String str, String str2, int i) {
        noteLongPartialWakelockFinish_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda6(
                            this,
                            str,
                            str2,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            5));
        }
    }

    public final void noteLongPartialWakelockFinishFromSource(
            String str, String str2, WorkSource workSource) {
        noteLongPartialWakelockFinishFromSource_enforcePermission();
        WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda46(
                            1,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            workSource2,
                            this,
                            str,
                            str2));
        }
    }

    public final void noteLongPartialWakelockStart(String str, String str2, int i) {
        noteLongPartialWakelockStart_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda6(
                            this,
                            str,
                            str2,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            3));
        }
    }

    public final void noteLongPartialWakelockStartFromSource(
            String str, String str2, WorkSource workSource) {
        noteLongPartialWakelockStartFromSource_enforcePermission();
        WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda46(
                            0,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            workSource2,
                            this,
                            str,
                            str2));
        }
    }

    public final void noteMobileRadioPowerState(int i, long j, int i2) {
        noteMobileRadioPowerState_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda21(
                            i,
                            i2,
                            1,
                            j,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            this));
        }
        FrameworkStatsLog.write_non_chained(12, i2, null, i);
    }

    public final void noteModemControllerActivity(ModemActivityInfo modemActivityInfo) {
        noteModemControllerActivity_enforcePermission();
        if (modemActivityInfo == null) {
            Slog.e("BatteryStatsService", "invalid modem data given: " + modemActivityInfo);
        } else {
            synchronized (this.mLock) {
                this.mHandler.post(
                        new BatteryStatsService$$ExternalSyntheticLambda61(
                                this,
                                modemActivityInfo,
                                SystemClock.elapsedRealtime(),
                                SystemClock.uptimeMillis(),
                                (NetworkStatsManager)
                                        this.mContext.getSystemService(NetworkStatsManager.class)));
            }
        }
    }

    public final void noteNetworkInterfaceForTransports(final String str, final int[] iArr) {
        noteNetworkInterfaceForTransports_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda107
                        @Override // java.lang.Runnable
                        public final void run() {
                            BatteryStatsService batteryStatsService = BatteryStatsService.this;
                            batteryStatsService.mStats.noteNetworkInterfaceForTransports(str, iArr);
                        }
                    });
        }
    }

    public final void noteNetworkStatsEnabled() {
        noteNetworkStatsEnabled_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(new BatteryStatsService$$ExternalSyntheticLambda34(this, 3));
        }
    }

    public final void notePackageInstalled(final long j, final String str) {
        synchronized (this.mLock) {
            final long elapsedRealtime = SystemClock.elapsedRealtime();
            final long uptimeMillis = SystemClock.uptimeMillis();
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda41
                        @Override // java.lang.Runnable
                        public final void run() {
                            BatteryStatsService batteryStatsService = BatteryStatsService.this;
                            String str2 = str;
                            long j2 = j;
                            long j3 = elapsedRealtime;
                            long j4 = uptimeMillis;
                            synchronized (batteryStatsService.mStats) {
                                BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
                                batteryStatsImpl.mHistory.recordEvent(j3, j4, 11, str2, (int) j2);
                                BatteryStats.PackageChange packageChange =
                                        new BatteryStats.PackageChange();
                                packageChange.mPackageName = str2;
                                packageChange.mUpdate = true;
                                packageChange.mVersionCode = j2;
                                if (batteryStatsImpl.mDailyPackageChanges == null) {
                                    batteryStatsImpl.mDailyPackageChanges = new ArrayList();
                                }
                                batteryStatsImpl.mDailyPackageChanges.add(packageChange);
                            }
                        }
                    });
        }
    }

    public final void notePackageUninstalled(String str) {
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda28(
                            this,
                            str,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            4));
        }
    }

    public final void notePhoneDataConnectionState(
            final int i, final boolean z, final int i2, final int i3, final int i4) {
        notePhoneDataConnectionState_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = SystemClock.elapsedRealtime();
            final long uptimeMillis = SystemClock.uptimeMillis();
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda88
                        @Override // java.lang.Runnable
                        public final void run() {
                            BatteryStatsService batteryStatsService = BatteryStatsService.this;
                            int i5 = i;
                            boolean z2 = z;
                            int i6 = i2;
                            int i7 = i3;
                            int i8 = i4;
                            long j = elapsedRealtime;
                            long j2 = uptimeMillis;
                            synchronized (batteryStatsService.mStats) {
                                batteryStatsService.mStats.notePhoneDataConnectionStateLocked(
                                        i5, z2, i6, i7, i8, j, j2);
                            }
                        }
                    });
        }
    }

    public final void notePhoneOff() {
        notePhoneOff_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda0(
                            this, SystemClock.elapsedRealtime(), SystemClock.uptimeMillis(), 8));
        }
    }

    public final void notePhoneOn() {
        notePhoneOn_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda0(
                            this, SystemClock.elapsedRealtime(), SystemClock.uptimeMillis(), 7));
        }
    }

    public final void notePhoneSignalStrength(SignalStrength signalStrength) {
        notePhoneSignalStrength_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda28(
                            this,
                            signalStrength,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis()));
        }
    }

    public final void notePhoneState(int i) {
        notePhoneState_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda2(
                            this,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            13));
        }
    }

    public final void noteProcessAnr(int i, String str) {
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda8(
                            this,
                            str,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            2));
        }
    }

    public final void noteProcessCrash(int i, String str) {
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda8(
                            this,
                            str,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            1));
        }
        FrameworkStatsLog.write(28, i, str, 2);
    }

    public final void noteProcessFinish(int i, String str) {
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda8(
                            this,
                            str,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            0));
        }
        FrameworkStatsLog.write(28, i, str, 0);
    }

    public final void noteProcessStart(int i, String str) {
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda8(
                            this,
                            str,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            7));
        }
        FrameworkStatsLog.write(28, i, str, 1);
    }

    public final void noteResetAudio() {
        noteResetAudio_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda0(
                            this, SystemClock.elapsedRealtime(), SystemClock.uptimeMillis(), 3));
        }
        FrameworkStatsLog.write_non_chained(23, -1, null, 2);
    }

    public final void noteResetCamera() {
        noteResetCamera_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda0(
                            this, SystemClock.elapsedRealtime(), SystemClock.uptimeMillis(), 0));
        }
        FrameworkStatsLog.write_non_chained(25, -1, null, 2);
    }

    public final void noteResetFlashlight() {
        noteResetFlashlight_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda0(
                            this, SystemClock.elapsedRealtime(), SystemClock.uptimeMillis(), 1));
        }
        FrameworkStatsLog.write_non_chained(26, -1, null, 2);
    }

    public final void noteResetGps() {
        noteResetGps_enforcePermission();
        synchronized (this.mStats) {
            BatteryStatsImpl batteryStatsImpl = this.mStats;
            long elapsedRealtime = batteryStatsImpl.mClock.elapsedRealtime();
            for (int i = 0; i < batteryStatsImpl.mUidStats.size(); i++) {
                BatteryStatsImpl.DualTimer sensorTimerLocked =
                        ((BatteryStatsImpl.Uid) batteryStatsImpl.mUidStats.valueAt(i))
                                .getSensorTimerLocked(-10001, false);
                if (sensorTimerLocked != null) {
                    sensorTimerLocked.stopRunningLocked(elapsedRealtime);
                }
            }
            FrameworkStatsLog.write(FrameworkStatsLog.GPS_ENGINE_STATE_CHANGED, -1, 2);
        }
    }

    public final void noteResetVideo() {
        noteResetVideo_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda0(
                            this, SystemClock.elapsedRealtime(), SystemClock.uptimeMillis(), 2));
        }
        FrameworkStatsLog.write_non_chained(24, -1, null, 2);
    }

    public final void noteScreenBrightness(int i) {
        noteScreenBrightness_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda2(
                            this, i, SystemClock.elapsedRealtime(), SystemClock.uptimeMillis(), 7));
        }
        FrameworkStatsLog.write(9, i);
    }

    public final void noteScreenState(int i) {
        noteScreenState_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda90(
                            i,
                            0,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            System.currentTimeMillis(),
                            this));
        }
        FrameworkStatsLog.write(29, i);
    }

    public final void noteServiceStartLaunch(int i, String str, String str2) {
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda6(
                            this,
                            i,
                            str,
                            str2,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            2));
        }
    }

    public final void noteServiceStartRunning(int i, String str, String str2) {
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda6(
                            this,
                            i,
                            str,
                            str2,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            1));
        }
    }

    public final void noteServiceStopLaunch(int i, String str, String str2) {
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda6(
                            this,
                            i,
                            str,
                            str2,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            0));
        }
    }

    public final void noteServiceStopRunning(int i, String str, String str2) {
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda6(
                            this,
                            i,
                            str,
                            str2,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            4));
        }
    }

    public final void noteStartAudio(int i) {
        noteStartAudio_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda2(
                            this,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            18));
        }
        FrameworkStatsLog.write_non_chained(23, i, null, 1);
    }

    public final void noteStartCamera(int i) {
        noteStartCamera_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda2(
                            this, i, SystemClock.elapsedRealtime(), SystemClock.uptimeMillis(), 1));
        }
        if (PowerManagerUtil.SEC_FEATURE_USE_WIRELESS_POWER_SHARING
                && i != 1000
                && i != 1001
                && i != 5021) {
            Slog.d("BatteryStatsService", "set setWirelessPowerSharingExternelEvent");
            this.mBatteryManagerInternal.setWirelessPowerSharingExternelEvent(1, 1);
        }
        FrameworkStatsLog.write_non_chained(25, i, null, 1);
    }

    public final void noteStartGps(int i) {
        noteStartGps_enforcePermission();
        synchronized (this.mStats) {
            BatteryStatsImpl batteryStatsImpl = this.mStats;
            int mapUid = batteryStatsImpl.mapUid(i);
            batteryStatsImpl
                    .getUidStatsLocked(mapUid)
                    .getSensorTimerLocked(-10001, true)
                    .startRunningLocked(batteryStatsImpl.mClock.elapsedRealtime());
            FrameworkStatsLog.write(FrameworkStatsLog.GPS_ENGINE_STATE_CHANGED, i, 1);
        }
    }

    public final void noteStartSensor(int i, int i2) {
        noteStartSensor_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda32(
                            i,
                            i2,
                            0,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            this));
        }
        FrameworkStatsLog.write_non_chained(5, i, (String) null, i2, 1);
    }

    public final void noteStartTxPowerSharing() {
        noteStartTxPowerSharing_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(new BatteryStatsService$$ExternalSyntheticLambda34(this, 1));
        }
    }

    public final void noteStartVideo(int i) {
        noteStartVideo_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda2(
                            this,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            17));
        }
        FrameworkStatsLog.write_non_chained(24, i, null, 1);
    }

    public final void noteStartWakelock(
            final int i,
            final int i2,
            final String str,
            final String str2,
            final int i3,
            final boolean z) {
        noteStartWakelock_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = SystemClock.elapsedRealtime();
            final long uptimeMillis = SystemClock.uptimeMillis();
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            BatteryStatsService batteryStatsService = BatteryStatsService.this;
                            int i4 = i;
                            int i5 = i2;
                            String str3 = str;
                            String str4 = str2;
                            int i6 = i3;
                            boolean z2 = z;
                            long j = elapsedRealtime;
                            long j2 = uptimeMillis;
                            synchronized (batteryStatsService.mStats) {
                                batteryStatsService.mStats.noteStartWakeLocked(
                                        i4, i5, null, str3, str4, i6, z2, j, j2);
                            }
                        }
                    });
        }
    }

    public final void noteStartWakelockFromSource(
            WorkSource workSource,
            final int i,
            final String str,
            final String str2,
            final int i2,
            final boolean z) {
        noteStartWakelockFromSource_enforcePermission();
        final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = SystemClock.elapsedRealtime();
            final long uptimeMillis = SystemClock.uptimeMillis();
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda20
                        @Override // java.lang.Runnable
                        public final void run() {
                            BatteryStatsService batteryStatsService = BatteryStatsService.this;
                            WorkSource workSource3 = workSource2;
                            int i3 = i;
                            String str3 = str;
                            String str4 = str2;
                            int i4 = i2;
                            boolean z2 = z;
                            long j = elapsedRealtime;
                            long j2 = uptimeMillis;
                            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
                            synchronized (batteryStatsImpl) {
                                try {
                                    try {
                                        BatteryStatsImpl batteryStatsImpl2 =
                                                batteryStatsService.mStats;
                                        batteryStatsImpl2.getClass();
                                        int size = workSource3.size();
                                        int i5 = 0;
                                        while (i5 < size) {
                                            int i6 = i5;
                                            BatteryStatsImpl batteryStatsImpl3 = batteryStatsImpl;
                                            long j3 = j2;
                                            long j4 = j;
                                            int i7 = i4;
                                            boolean z3 = z2;
                                            String str5 = str3;
                                            String str6 = str4;
                                            batteryStatsImpl2.noteStartWakeLocked(
                                                    workSource3.getUid(i5),
                                                    i3,
                                                    null,
                                                    str3,
                                                    str4,
                                                    i4,
                                                    z2,
                                                    j4,
                                                    j3);
                                            i5 = i6 + 1;
                                            i4 = i7;
                                            batteryStatsImpl = batteryStatsImpl3;
                                            j2 = j3;
                                            j = j4;
                                            z2 = z3;
                                            str3 = str5;
                                            str4 = str6;
                                        }
                                        BatteryStatsImpl batteryStatsImpl4 = batteryStatsImpl;
                                        long j5 = j2;
                                        long j6 = j;
                                        int i8 = i4;
                                        boolean z4 = z2;
                                        String str7 = str3;
                                        String str8 = str4;
                                        List workChains = workSource3.getWorkChains();
                                        if (workChains != null) {
                                            for (int i9 = 0; i9 < workChains.size(); i9++) {
                                                WorkSource.WorkChain workChain =
                                                        (WorkSource.WorkChain) workChains.get(i9);
                                                batteryStatsImpl2.noteStartWakeLocked(
                                                        workChain.getAttributionUid(),
                                                        i3,
                                                        workChain,
                                                        str7,
                                                        str8,
                                                        i8,
                                                        z4,
                                                        j6,
                                                        j5);
                                            }
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        BatteryStatsImpl batteryStatsImpl5 = batteryStatsImpl;
                                        throw th;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    throw th;
                                }
                            }
                        }
                    });
        }
    }

    public final void noteStopAudio(int i) {
        noteStopAudio_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda2(
                            this, i, SystemClock.elapsedRealtime(), SystemClock.uptimeMillis(), 2));
        }
        FrameworkStatsLog.write_non_chained(23, i, null, 0);
    }

    public final void noteStopCamera(int i) {
        noteStopCamera_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda2(
                            this,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            11));
        }
        if (PowerManagerUtil.SEC_FEATURE_USE_WIRELESS_POWER_SHARING
                && i != 1000
                && i != 1001
                && i != 5021) {
            Slog.d("BatteryStatsService", "set setWirelessPowerSharingExternelEvent");
            this.mBatteryManagerInternal.setWirelessPowerSharingExternelEvent(1, 0);
        }
        FrameworkStatsLog.write_non_chained(25, i, null, 0);
    }

    public final void noteStopGps(int i) {
        noteStopGps_enforcePermission();
        synchronized (this.mStats) {
            BatteryStatsImpl batteryStatsImpl = this.mStats;
            int mapUid = batteryStatsImpl.mapUid(i);
            long elapsedRealtime = batteryStatsImpl.mClock.elapsedRealtime();
            BatteryStatsImpl.DualTimer sensorTimerLocked =
                    batteryStatsImpl.getUidStatsLocked(mapUid).getSensorTimerLocked(-10001, false);
            if (sensorTimerLocked != null) {
                sensorTimerLocked.stopRunningLocked(elapsedRealtime);
            }
            FrameworkStatsLog.write(FrameworkStatsLog.GPS_ENGINE_STATE_CHANGED, i, 0);
        }
    }

    public final void noteStopSensor(int i, int i2) {
        noteStopSensor_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda32(
                            i,
                            i2,
                            3,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            this));
        }
        FrameworkStatsLog.write_non_chained(5, i, (String) null, i2, 0);
    }

    public final void noteStopTxPowerSharing() {
        noteStopTxPowerSharing_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(new BatteryStatsService$$ExternalSyntheticLambda34(this, 2));
        }
    }

    public final void noteStopVideo(int i) {
        noteStopVideo_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda2(
                            this, i, SystemClock.elapsedRealtime(), SystemClock.uptimeMillis(), 6));
        }
        FrameworkStatsLog.write_non_chained(24, i, null, 0);
    }

    public final void noteStopWakelock(
            final int i, final int i2, final String str, final String str2, final int i3) {
        noteStopWakelock_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = SystemClock.elapsedRealtime();
            final long uptimeMillis = SystemClock.uptimeMillis();
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            BatteryStatsService batteryStatsService = BatteryStatsService.this;
                            int i4 = i;
                            int i5 = i2;
                            String str3 = str;
                            String str4 = str2;
                            int i6 = i3;
                            long j = elapsedRealtime;
                            long j2 = uptimeMillis;
                            synchronized (batteryStatsService.mStats) {
                                batteryStatsService.mStats.noteStopWakeLocked(
                                        i4, i5, null, str3, str4, i6, j, j2);
                            }
                        }
                    });
        }
    }

    public final void noteStopWakelockFromSource(
            WorkSource workSource, final int i, final String str, final String str2, final int i2) {
        noteStopWakelockFromSource_enforcePermission();
        final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
        synchronized (this.mLock) {
            final long elapsedRealtime = SystemClock.elapsedRealtime();
            final long uptimeMillis = SystemClock.uptimeMillis();
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda39
                        @Override // java.lang.Runnable
                        public final void run() {
                            BatteryStatsService batteryStatsService = BatteryStatsService.this;
                            WorkSource workSource3 = workSource2;
                            int i3 = i;
                            String str3 = str;
                            String str4 = str2;
                            int i4 = i2;
                            long j = elapsedRealtime;
                            long j2 = uptimeMillis;
                            BatteryStatsImpl batteryStatsImpl = batteryStatsService.mStats;
                            synchronized (batteryStatsImpl) {
                                try {
                                    try {
                                        BatteryStatsImpl batteryStatsImpl2 =
                                                batteryStatsService.mStats;
                                        batteryStatsImpl2.getClass();
                                        int size = workSource3.size();
                                        int i5 = 0;
                                        while (i5 < size) {
                                            int i6 = i5;
                                            BatteryStatsImpl batteryStatsImpl3 = batteryStatsImpl;
                                            long j3 = j2;
                                            long j4 = j;
                                            String str5 = str4;
                                            int i7 = i4;
                                            batteryStatsImpl2.noteStopWakeLocked(
                                                    workSource3.getUid(i5),
                                                    i3,
                                                    null,
                                                    str3,
                                                    str4,
                                                    i4,
                                                    j,
                                                    j3);
                                            i5 = i6 + 1;
                                            batteryStatsImpl = batteryStatsImpl3;
                                            j2 = j3;
                                            j = j4;
                                            str4 = str5;
                                            i4 = i7;
                                        }
                                        BatteryStatsImpl batteryStatsImpl4 = batteryStatsImpl;
                                        long j5 = j2;
                                        long j6 = j;
                                        String str6 = str4;
                                        int i8 = i4;
                                        List workChains = workSource3.getWorkChains();
                                        if (workChains != null) {
                                            for (int i9 = 0; i9 < workChains.size(); i9++) {
                                                WorkSource.WorkChain workChain =
                                                        (WorkSource.WorkChain) workChains.get(i9);
                                                batteryStatsImpl2.noteStopWakeLocked(
                                                        workChain.getAttributionUid(),
                                                        i3,
                                                        workChain,
                                                        str3,
                                                        str6,
                                                        i8,
                                                        j6,
                                                        j5);
                                            }
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        BatteryStatsImpl batteryStatsImpl5 = batteryStatsImpl;
                                        throw th;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    throw th;
                                }
                            }
                        }
                    });
        }
    }

    public final void noteSyncFinish(String str, int i) {
        noteSyncFinish_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda8(
                            this,
                            str,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            5));
        }
        FrameworkStatsLog.write_non_chained(7, i, (String) null, str, 0);
    }

    public final void noteSyncStart(String str, int i) {
        noteSyncStart_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda8(
                            this,
                            str,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            4));
        }
        FrameworkStatsLog.write_non_chained(7, i, (String) null, str, 1);
    }

    public final void noteUpdateNetworkStats(String str) {
        noteUpdateNetworkStats_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(new BatteryStatsService$$ExternalSyntheticLambda104(this, str, 1));
        }
    }

    public final void noteUserActivity(int i, int i2) {
        noteUserActivity_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda32(
                            i,
                            i2,
                            1,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            this));
        }
    }

    public final void noteVibratorOff(int i) {
        noteVibratorOff_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda2(
                            this, i, SystemClock.elapsedRealtime(), SystemClock.uptimeMillis(), 4));
        }
    }

    public final void noteVibratorOn(int i, long j) {
        noteVibratorOn_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda90(
                            i,
                            1,
                            j,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            this));
        }
    }

    public final void noteWakeUp(String str, int i) {
        noteWakeUp_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda8(
                            this,
                            str,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            9));
        }
    }

    public final void noteWakeupSensorEvent(long j, int i, int i2) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000) {
            throw new SecurityException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            callingUid, "Calling uid ", " is not system uid"));
        }
        long millis = TimeUnit.NANOSECONDS.toMillis(j);
        Sensor sensorByHandle =
                ((SensorManager) this.mContext.getSystemService(SensorManager.class))
                        .getSensorByHandle(i2);
        if (sensorByHandle == null) {
            BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                    i2,
                    "Unknown sensor handle ",
                    " received in noteWakeupSensorEvent",
                    "BatteryStatsService");
            return;
        }
        if (i >= 0) {
            noteCpuWakingActivity(millis, new int[] {i}, 4);
            return;
        }
        Slog.wtf(
                "BatteryStatsService",
                "Invalid uid " + i + " for sensor event with sensor: " + sensorByHandle);
    }

    public final void noteWifiBatchedScanStartedFromSource(WorkSource workSource, int i) {
        noteWifiBatchedScanStartedFromSource_enforcePermission();
        WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda84(
                            this,
                            workSource2,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            1));
        }
    }

    public final void noteWifiBatchedScanStoppedFromSource(WorkSource workSource) {
        noteWifiBatchedScanStoppedFromSource_enforcePermission();
        WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda3(
                            this,
                            workSource2,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            0));
        }
    }

    public final void noteWifiControllerActivity(WifiActivityEnergyInfo wifiActivityEnergyInfo) {
        noteWifiControllerActivity_enforcePermission();
        if (wifiActivityEnergyInfo == null || !wifiActivityEnergyInfo.isValid()) {
            Slog.e("BatteryStatsService", "invalid wifi data given: " + wifiActivityEnergyInfo);
        } else {
            synchronized (this.mLock) {
                this.mHandler.post(
                        new BatteryStatsService$$ExternalSyntheticLambda61(
                                this,
                                wifiActivityEnergyInfo,
                                SystemClock.elapsedRealtime(),
                                SystemClock.uptimeMillis(),
                                (NetworkStatsManager)
                                        this.mContext.getSystemService(NetworkStatsManager.class)));
            }
        }
    }

    public final void noteWifiMulticastDisabled(int i) {
        noteWifiMulticastDisabled_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda2(
                            this, i, SystemClock.elapsedRealtime(), SystemClock.uptimeMillis(), 5));
        }
    }

    public final void noteWifiMulticastEnabled(int i) {
        noteWifiMulticastEnabled_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda2(
                            this,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            10));
        }
    }

    public final void noteWifiOff() {
        noteWifiOff_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda0(
                            this, SystemClock.elapsedRealtime(), SystemClock.uptimeMillis(), 4));
        }
        FrameworkStatsLog.write(113, 0);
    }

    public final void noteWifiOn() {
        noteWifiOn_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda0(
                            this, SystemClock.elapsedRealtime(), SystemClock.uptimeMillis(), 5));
        }
        FrameworkStatsLog.write(113, 1);
    }

    public final void noteWifiRadioPowerState(int i, long j, int i2) {
        noteWifiRadioPowerState_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda21(
                            i,
                            i2,
                            0,
                            j,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            this));
        }
        FrameworkStatsLog.write_non_chained(13, i2, null, i);
    }

    public final void noteWifiRssiChanged(int i) {
        noteWifiRssiChanged_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda2(
                            this, i, SystemClock.elapsedRealtime(), SystemClock.uptimeMillis(), 9));
        }
    }

    public final void noteWifiRunning(WorkSource workSource) {
        noteWifiRunning_enforcePermission();
        WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda3(
                            this,
                            workSource2,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            6));
        }
        FrameworkStatsLog.write(114, workSource, 1);
    }

    public final void noteWifiRunningChanged(WorkSource workSource, WorkSource workSource2) {
        noteWifiRunningChanged_enforcePermission();
        WorkSource workSource3 = workSource != null ? new WorkSource(workSource) : null;
        WorkSource workSource4 = workSource2 != null ? new WorkSource(workSource2) : null;
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda71(
                            this,
                            workSource3,
                            workSource4,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            0));
        }
        FrameworkStatsLog.write(114, workSource2, 1);
        FrameworkStatsLog.write(114, workSource, 0);
    }

    public final void noteWifiScanStarted(int i) {
        noteWifiScanStarted_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda2(
                            this,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            19));
        }
    }

    public final void noteWifiScanStartedFromSource(WorkSource workSource) {
        noteWifiScanStartedFromSource_enforcePermission();
        WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda3(
                            this,
                            workSource2,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            4));
        }
    }

    public final void noteWifiScanStopped(int i) {
        noteWifiScanStopped_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda2(
                            this,
                            i,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            15));
        }
    }

    public final void noteWifiScanStoppedFromSource(WorkSource workSource) {
        noteWifiScanStoppedFromSource_enforcePermission();
        WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda3(
                            this,
                            workSource2,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            5));
        }
    }

    public final void noteWifiState(int i, String str) {
        noteWifiState_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda43(
                            this, i, str, SystemClock.elapsedRealtime()));
        }
    }

    public final void noteWifiStopped(WorkSource workSource) {
        noteWifiStopped_enforcePermission();
        WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : workSource;
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda3(
                            this,
                            workSource2,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            2));
        }
        FrameworkStatsLog.write(114, workSource, 0);
    }

    public final void noteWifiSupplicantStateChanged(int i, boolean z) {
        noteWifiSupplicantStateChanged_enforcePermission();
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda2(
                            i, SystemClock.elapsedRealtime(), SystemClock.uptimeMillis(), this, z));
        }
    }

    public final void onLowPowerModeChanged(PowerSaveState powerSaveState) {
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda28(
                            this,
                            powerSaveState,
                            SystemClock.elapsedRealtime(),
                            SystemClock.uptimeMillis(),
                            1));
        }
    }

    public final void populatePowerEntityMaps() {
        PowerEntity[] powerEntityInfo =
                PowerStatsService.this.getPowerStatsHal().getPowerEntityInfo();
        if (powerEntityInfo == null) {
            return;
        }
        for (PowerEntity powerEntity : powerEntityInfo) {
            HashMap hashMap = new HashMap();
            int i = 0;
            while (true) {
                State[] stateArr = powerEntity.states;
                if (i < stateArr.length) {
                    State state = stateArr[i];
                    hashMap.put(Integer.valueOf(state.id), state.name);
                    i++;
                }
            }
            ((HashMap) this.mEntityNames).put(Integer.valueOf(powerEntity.id), powerEntity.name);
            ((HashMap) this.mStateNames).put(Integer.valueOf(powerEntity.id), hashMap);
        }
    }

    public final boolean registerBatteryStatsCallback(IBatteryStatsCallback iBatteryStatsCallback) {
        boolean register;
        registerBatteryStatsCallback_enforcePermission();
        synchronized (this.mStats) {
            register = this.mStats.mBatteryStatsCallbacks.register(iBatteryStatsCallback, null);
        }
        return register;
    }

    public final void registerDeviceBatteryInfoChanged(String str) {
        Slog.i("BatteryStatsService", "registerDeviceBatteryInfoChanged package: " + str);
        DeviceBatteryInfoService deviceBatteryInfoService = this.mDeviceBatteryInfoServiceInternal;
        deviceBatteryInfoService.requirePermissions();
        if (str == null) {
            Slog.i(
                    "DeviceBatteryInfoService",
                    "registerDeviceBatteryInfoChanged : packageName is null");
            throw new IllegalArgumentException();
        }
        Slog.i(
                "DeviceBatteryInfoService",
                "mRegisteredPackage size :"
                        + ((ArrayList) deviceBatteryInfoService.mRegisteredPackage).size());
        boolean z = ((ArrayList) deviceBatteryInfoService.mRegisteredPackage).size() == 0;
        Slog.i("DeviceBatteryInfoService", "registerDeviceBatteryInfoChanged() : ".concat(str));
        if (!((ArrayList) deviceBatteryInfoService.mRegisteredPackage).contains(str)) {
            ((ArrayList) deviceBatteryInfoService.mRegisteredPackage).add(str);
        }
        if (z) {
            deviceBatteryInfoService.mWatchBatteryManager.notifyPackageRegistered(true);
        }
    }

    public final void removeIsolatedUid(int i, int i2) {
        PowerStatsUidResolver powerStatsUidResolver = this.mPowerStatsUidResolver;
        synchronized (powerStatsUidResolver) {
            try {
                int i3 = powerStatsUidResolver.mIsolatedUids.get(i, -1);
                if (i3 != i2) {
                    Slog.wtf(
                            "PowerStatsUidResolver",
                            "Attempt to remove an isolated UID "
                                    + i
                                    + " with the parent UID "
                                    + i2
                                    + ". The registered parent UID is "
                                    + i3);
                } else {
                    List list = powerStatsUidResolver.mListeners;
                    for (int size = list.size() - 1; size >= 0; size--) {
                        ((PowerStatsUidResolver.Listener) list.get(size))
                                .onBeforeIsolatedUidRemoved(i);
                    }
                    powerStatsUidResolver.releaseIsolatedUid(i);
                }
            } finally {
            }
        }
        FrameworkStatsLog.write(43, -1, i, 0);
    }

    public final void removeUid(int i) {
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda43(
                            this, i, SystemClock.elapsedRealtime(), 0));
        }
    }

    public final void resetBattery(boolean z) {
        resetBattery_enforcePermission();
        this.mBatteryManagerInternal.resetBattery(z);
    }

    public final void scheduleWriteToDisk() {
        synchronized (this.mLock) {
            this.mHandler.post(new BatteryStatsService$$ExternalSyntheticLambda34(this, 0));
        }
    }

    public final void setBatteryLevel(int i, boolean z) {
        setBatteryLevel_enforcePermission();
        this.mBatteryManagerInternal.setBatteryLevel(i, z);
    }

    public final void setBatteryState(
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            int i6,
            int i7,
            int i8,
            long j,
            int i9,
            int i10,
            int i11,
            int i12,
            boolean z) {
        setBatteryState_enforcePermission();
        synchronized (this.mLock) {
            try {
                try {
                    this.mHandler.post(
                            new BatteryStatsService$$ExternalSyntheticLambda94(
                                    this, i3, i, i2, i4, i5, i6, i7, i8, j, i9, i10, i11, i12, z,
                                    0));
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    public final void setChargerAcOnline(boolean z, boolean z2) {
        setChargerAcOnline_enforcePermission();
        this.mBatteryManagerInternal.setChargerAcOnline(z, z2);
    }

    public final boolean setChargingStateUpdateDelayMillis(int i) {
        setChargingStateUpdateDelayMillis_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return Settings.Global.putLong(
                    this.mContext.getContentResolver(), "battery_charging_state_update_delay", i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setDeviceBatteryInfo(
            final String str, final SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo) {
        Slog.i("BatteryStatsService", "setDeviceBatteryInfo()");
        final DeviceBatteryInfoService deviceBatteryInfoService =
                this.mDeviceBatteryInfoServiceInternal;
        deviceBatteryInfoService.getClass();
        Slog.i("DeviceBatteryInfoService", "setDeviceBatteryInfo()");
        deviceBatteryInfoService.mContext.enforceCallingOrSelfPermission(
                "com.samsung.android.permission.SEM_BATTERY_INFO_PROVIDER", "Permission Require");
        if (str == null) {
            Slog.i("DeviceBatteryInfoService", "address is null");
            throw new IllegalArgumentException();
        }
        if (deviceBatteryInfoService.containsBatteryInfo(str)) {
            deviceBatteryInfoService.mHandler.post(
                    new DeviceBatteryInfoService$$ExternalSyntheticLambda5(
                            deviceBatteryInfoService, str, semCompanionDeviceBatteryInfo, 0));
            return;
        }
        final int callingPid = Binder.getCallingPid();
        HermesService$3$$ExternalSyntheticOutline0.m(
                callingPid, "addBatteryInfo / callingPid :", "DeviceBatteryInfoService");
        deviceBatteryInfoService.mHandler.post(
                new Runnable() { // from class:
                                 // com.samsung.android.server.battery.DeviceBatteryInfoService$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        DeviceBatteryInfoService deviceBatteryInfoService2 =
                                DeviceBatteryInfoService.this;
                        String str2 = str;
                        SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo2 =
                                semCompanionDeviceBatteryInfo;
                        int i = callingPid;
                        deviceBatteryInfoService2.getClass();
                        Slog.i(
                                "DeviceBatteryInfoService",
                                "address : " + DeviceBatteryInfoUtil.getAddressForLog(str2));
                        deviceBatteryInfoService2.addBatteryInfo(
                                semCompanionDeviceBatteryInfo2.getAddress(),
                                semCompanionDeviceBatteryInfo2);
                        String packageNameByPid =
                                deviceBatteryInfoService2.mActivityManagerInternal
                                        .getPackageNameByPid(i);
                        Slog.i("DeviceBatteryInfoService", "packageName : " + packageNameByPid);
                        if (semCompanionDeviceBatteryInfo2.getDeviceType() == 4
                                || semCompanionDeviceBatteryInfo2.getDeviceType() == 6) {
                            WatchBatteryManager watchBatteryManager =
                                    deviceBatteryInfoService2.mWatchBatteryManager;
                            synchronized (watchBatteryManager.mWatchPackageMap) {
                                try {
                                    if (!watchBatteryManager.mWatchPackageMap.containsKey(
                                            packageNameByPid)) {
                                        watchBatteryManager.mWatchPackageMap.put(
                                                packageNameByPid, str2);
                                    }
                                } finally {
                                }
                            }
                            synchronized (watchBatteryManager.mProviderUriMap) {
                                try {
                                    if (!watchBatteryManager.mProviderUriMap.containsKey(
                                            packageNameByPid)) {
                                        watchBatteryManager.mProviderUriMap.put(
                                                packageNameByPid,
                                                Uri.parse(
                                                        "content://"
                                                                + packageNameByPid
                                                                + ".BatteryInfoProvider"));
                                    }
                                } finally {
                                }
                            }
                            watchBatteryManager.mConnected = true;
                            if (watchBatteryManager.mRegistered
                                    && (watchBatteryManager.mScreenOn
                                            || watchBatteryManager.mAodShowState == 1)) {
                                watchBatteryManager.requestBatteryDataSync(1);
                                watchBatteryManager.mSyncState = 1;
                            }
                            deviceBatteryInfoService2.packageAddressMap.put(packageNameByPid, str2);
                        } else if (semCompanionDeviceBatteryInfo2.getDeviceType() == 7) {
                            deviceBatteryInfoService2.packageAddressMap.put(packageNameByPid, str2);
                        }
                        if (deviceBatteryInfoService2.packageReceiverMap.containsKey(
                                packageNameByPid)) {
                            return;
                        }
                        DeviceBatteryInfoService.AnonymousClass1 anonymousClass1 =
                                new DeviceBatteryInfoService.AnonymousClass1(
                                        deviceBatteryInfoService2, 1);
                        IntentFilter intentFilter = new IntentFilter();
                        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
                        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
                        intentFilter.addDataScheme("package");
                        intentFilter.addDataSchemeSpecificPart(packageNameByPid, 0);
                        deviceBatteryInfoService2.mContext.registerReceiver(
                                anonymousClass1,
                                intentFilter,
                                null,
                                deviceBatteryInfoService2.mHandler);
                        deviceBatteryInfoService2.packageReceiverMap.put(
                                packageNameByPid, anonymousClass1);
                    }
                });
    }

    public final void setTemperatureNCurrent(
            final int i, final int i2, final int i3, final int i4, final int i5) {
        setTemperatureNCurrent_enforcePermission();
        synchronized (this.mLock) {
            final long elapsedRealtime = SystemClock.elapsedRealtime();
            final long uptimeMillis = SystemClock.uptimeMillis();
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda115
                        @Override // java.lang.Runnable
                        public final void run() {
                            BatteryStatsService batteryStatsService = BatteryStatsService.this;
                            int i6 = i;
                            int i7 = i2;
                            int i8 = i3;
                            int i9 = i4;
                            int i10 = i5;
                            long j = elapsedRealtime;
                            long j2 = uptimeMillis;
                            synchronized (batteryStatsService.mStats) {
                                batteryStatsService.mStats.setTemperatureNCurrentLocked(
                                        i6, i7, i8, i9, i10, j, j2);
                            }
                        }
                    });
        }
    }

    public final boolean shouldCollectExternalStats() {
        long j;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        BatteryExternalStatsWorker batteryExternalStatsWorker = this.mWorker;
        synchronized (batteryExternalStatsWorker) {
            j = batteryExternalStatsWorker.mLastCollectionTimeStamp;
        }
        return elapsedRealtime - j > this.mStats.getExternalStatsCollectionRateLimitMs();
    }

    public final void shutdown() {
        Slog.w("BatteryStats", "Writing battery stats before shutdown...");
        awaitCompletion();
        syncStats("shutdown");
        synchronized (this.mStats) {
            BatteryStatsImpl batteryStatsImpl = this.mStats;
            batteryStatsImpl.mHistory.recordShutdownEvent(
                    batteryStatsImpl.mClock.elapsedRealtime(),
                    batteryStatsImpl.mClock.uptimeMillis(),
                    batteryStatsImpl.mClock.currentTimeMillis());
            batteryStatsImpl.writeSyncLocked();
            batteryStatsImpl.mShuttingDown = true;
        }
        BatteryExternalStatsWorker batteryExternalStatsWorker = this.mWorker;
        synchronized (batteryExternalStatsWorker) {
            batteryExternalStatsWorker.mExecutorService.shutdownNow();
        }
        this.mMonotonicClock.write();
    }

    public final void suspendBatteryInput() {
        suspendBatteryInput_enforcePermission();
        this.mBatteryManagerInternal.suspendBatteryInput();
    }

    public final void syncStats(String str) {
        while (true) {
            try {
                this.mWorker.scheduleSync(127, str).get();
                return;
            } catch (InterruptedException unused) {
            } catch (CancellationException | ExecutionException unused2) {
                return;
            }
        }
    }

    public final HealthStatsParceler takeUidSnapshot(int i) {
        HealthStatsParceler healthStatsForUidLocked;
        if (i != Binder.getCallingUid()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.BATTERY_STATS", null);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                awaitCompletion();
                if (shouldCollectExternalStats()) {
                    syncStats("get-health-stats-for-uids");
                }
                synchronized (this.mStats) {
                    healthStatsForUidLocked = getHealthStatsForUidLocked(i);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return healthStatsForUidLocked;
            } catch (Exception e) {
                Slog.w(
                        "BatteryStatsService",
                        "Crashed while writing for takeUidSnapshot(" + i + ")",
                        e);
                throw e;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final HealthStatsParceler[] takeUidSnapshots(int[] iArr) {
        HealthStatsParceler[] healthStatsParcelerArr;
        int callingUid = Binder.getCallingUid();
        int length = iArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            if (iArr[i] != callingUid) {
                this.mContext.enforceCallingOrSelfPermission(
                        "android.permission.BATTERY_STATS", null);
                break;
            }
            i++;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                awaitCompletion();
                if (shouldCollectExternalStats()) {
                    syncStats("get-health-stats-for-uids");
                }
                synchronized (this.mStats) {
                    try {
                        int length2 = iArr.length;
                        healthStatsParcelerArr = new HealthStatsParceler[length2];
                        for (int i2 = 0; i2 < length2; i2++) {
                            healthStatsParcelerArr[i2] = getHealthStatsForUidLocked(iArr[i2]);
                        }
                    } finally {
                    }
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return healthStatsParcelerArr;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public final void takeUidSnapshotsAsync(final int[] iArr, final ResultReceiver resultReceiver) {
        int callingUid = Binder.getCallingUid();
        int length = iArr.length;
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = true;
                break;
            } else if (iArr[i] != callingUid) {
                break;
            } else {
                i++;
            }
        }
        if (!z) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.BATTERY_STATS", null);
        }
        final Future scheduleSync =
                shouldCollectExternalStats()
                        ? this.mWorker.scheduleSync(127, "get-health-stats-for-uids")
                        : null;
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda98
                    @Override // java.lang.Runnable
                    public final void run() {
                        BatteryStatsService batteryStatsService = BatteryStatsService.this;
                        Future future = scheduleSync;
                        int[] iArr2 = iArr;
                        ResultReceiver resultReceiver2 = resultReceiver;
                        IBatteryStats iBatteryStats = BatteryStatsService.sService;
                        batteryStatsService.getClass();
                        if (future != null) {
                            try {
                                future.get();
                            } catch (InterruptedException | ExecutionException e) {
                                Slog.e("BatteryStatsService", "Sync failed", e);
                            }
                        }
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            try {
                                int length2 = iArr2.length;
                                HealthStatsParceler[] healthStatsParcelerArr =
                                        new HealthStatsParceler[length2];
                                synchronized (batteryStatsService.mStats) {
                                    for (int i2 = 0; i2 < length2; i2++) {
                                        try {
                                            healthStatsParcelerArr[i2] =
                                                    batteryStatsService.getHealthStatsForUidLocked(
                                                            iArr2[i2]);
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                                Bundle bundle = new Bundle(1);
                                bundle.putParcelableArray("uid_snapshots", healthStatsParcelerArr);
                                resultReceiver2.send(0, bundle);
                            } catch (Exception e2) {
                                throw e2;
                            }
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                });
    }

    public final void unRegisterDeviceBatteryInfoChanged(String str) {
        Slog.i("BatteryStatsService", "unRegisterDeviceBatteryInfoChanged package: " + str);
        DeviceBatteryInfoService deviceBatteryInfoService = this.mDeviceBatteryInfoServiceInternal;
        deviceBatteryInfoService.requirePermissions();
        if (str == null) {
            Slog.i(
                    "DeviceBatteryInfoService",
                    "unRegisterDeviceBatteryInfoChanged : packageName is null");
            throw new IllegalArgumentException();
        }
        Slog.i("DeviceBatteryInfoService", "unRegisterDeviceBatteryInfoChanged() : ".concat(str));
        if (((ArrayList) deviceBatteryInfoService.mRegisteredPackage).contains(str)) {
            ((ArrayList) deviceBatteryInfoService.mRegisteredPackage).remove(str);
        }
        Slog.i(
                "DeviceBatteryInfoService",
                "mRegisteredPackage size :"
                        + ((ArrayList) deviceBatteryInfoService.mRegisteredPackage).size());
        if (((ArrayList) deviceBatteryInfoService.mRegisteredPackage).size() == 0) {
            deviceBatteryInfoService.mWatchBatteryManager.notifyPackageRegistered(false);
        }
    }

    public final void unplugBattery(boolean z) {
        unplugBattery_enforcePermission();
        this.mBatteryManagerInternal.unplugBattery(z);
    }

    public final boolean unregisterBatteryStatsCallback(
            IBatteryStatsCallback iBatteryStatsCallback) {
        boolean unregister;
        unregisterBatteryStatsCallback_enforcePermission();
        synchronized (this.mStats) {
            unregister = this.mStats.mBatteryStatsCallbacks.unregister(iBatteryStatsCallback);
        }
        return unregister;
    }

    public final void unsetDeviceBatteryInfo(final String str) {
        Slog.i("BatteryStatsService", "removeDeviceBatteryInfo()");
        final DeviceBatteryInfoService deviceBatteryInfoService =
                this.mDeviceBatteryInfoServiceInternal;
        deviceBatteryInfoService.getClass();
        Slog.i("DeviceBatteryInfoService", "unsetDeviceBatteryInfo()");
        deviceBatteryInfoService.mContext.enforceCallingOrSelfPermission(
                "com.samsung.android.permission.SEM_BATTERY_INFO_PROVIDER", "Permission Require");
        if (str == null) {
            Slog.i("DeviceBatteryInfoService", "address is null");
            throw new IllegalArgumentException();
        }
        if (!deviceBatteryInfoService.containsBatteryInfo(str)) {
            Slog.i("DeviceBatteryInfoService", "device is not exist");
        } else {
            final int callingPid = Binder.getCallingPid();
            deviceBatteryInfoService.mHandler.post(
                    new Runnable() { // from class:
                        // com.samsung.android.server.battery.DeviceBatteryInfoService$$ExternalSyntheticLambda0
                        /* JADX WARN: Removed duplicated region for block: B:13:0x0065 A[Catch: Exception -> 0x004e, TRY_LEAVE, TryCatch #0 {Exception -> 0x004e, blocks: (B:3:0x000d, B:5:0x0039, B:8:0x0041, B:10:0x0048, B:11:0x005a, B:13:0x0065, B:18:0x0050), top: B:2:0x000d }] */
                        /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final void run() {
                            /*
                                r6 = this;
                                com.samsung.android.server.battery.DeviceBatteryInfoService r0 = com.samsung.android.server.battery.DeviceBatteryInfoService.this
                                int r1 = r2
                                java.lang.String r6 = r3
                                r0.getClass()
                                java.lang.String r2 = "DeviceBatteryInfoService"
                                java.lang.String r3 = "address : "
                                android.app.ActivityManagerInternal r4 = r0.mActivityManagerInternal     // Catch: java.lang.Exception -> L4e
                                java.lang.String r1 = r4.getPackageNameByPid(r1)     // Catch: java.lang.Exception -> L4e
                                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L4e
                                r4.<init>(r3)     // Catch: java.lang.Exception -> L4e
                                java.lang.String r3 = com.samsung.android.server.battery.DeviceBatteryInfoUtil.getAddressForLog(r6)     // Catch: java.lang.Exception -> L4e
                                r4.append(r3)     // Catch: java.lang.Exception -> L4e
                                java.lang.String r3 = " packageName : "
                                r4.append(r3)     // Catch: java.lang.Exception -> L4e
                                r4.append(r1)     // Catch: java.lang.Exception -> L4e
                                java.lang.String r3 = r4.toString()     // Catch: java.lang.Exception -> L4e
                                android.util.Slog.i(r2, r3)     // Catch: java.lang.Exception -> L4e
                                com.samsung.android.os.SemCompanionDeviceBatteryInfo r3 = r0.getDeviceBatteryInfo(r6)     // Catch: java.lang.Exception -> L4e
                                int r4 = r3.getDeviceType()     // Catch: java.lang.Exception -> L4e
                                r5 = 4
                                if (r4 == r5) goto L50
                                int r4 = r3.getDeviceType()     // Catch: java.lang.Exception -> L4e
                                r5 = 6
                                if (r4 != r5) goto L41
                                goto L50
                            L41:
                                int r3 = r3.getDeviceType()     // Catch: java.lang.Exception -> L4e
                                r4 = 7
                                if (r3 != r4) goto L5a
                                java.util.HashMap r3 = r0.packageAddressMap     // Catch: java.lang.Exception -> L4e
                                r3.remove(r1)     // Catch: java.lang.Exception -> L4e
                                goto L5a
                            L4e:
                                r6 = move-exception
                                goto L78
                            L50:
                                com.samsung.android.server.battery.WatchBatteryManager r3 = r0.mWatchBatteryManager     // Catch: java.lang.Exception -> L4e
                                r3.removeWatchPackageInfo(r1)     // Catch: java.lang.Exception -> L4e
                                java.util.HashMap r3 = r0.packageAddressMap     // Catch: java.lang.Exception -> L4e
                                r3.remove(r1)     // Catch: java.lang.Exception -> L4e
                            L5a:
                                r0.removeBatteryInfo(r6)     // Catch: java.lang.Exception -> L4e
                                java.util.HashMap r6 = r0.packageReceiverMap     // Catch: java.lang.Exception -> L4e
                                boolean r6 = r6.containsKey(r1)     // Catch: java.lang.Exception -> L4e
                                if (r6 == 0) goto L7d
                                java.util.HashMap r6 = r0.packageReceiverMap     // Catch: java.lang.Exception -> L4e
                                java.lang.Object r6 = r6.get(r1)     // Catch: java.lang.Exception -> L4e
                                android.content.BroadcastReceiver r6 = (android.content.BroadcastReceiver) r6     // Catch: java.lang.Exception -> L4e
                                android.content.Context r3 = r0.mContext     // Catch: java.lang.Exception -> L4e
                                r3.unregisterReceiver(r6)     // Catch: java.lang.Exception -> L4e
                                java.util.HashMap r6 = r0.packageReceiverMap     // Catch: java.lang.Exception -> L4e
                                r6.remove(r1)     // Catch: java.lang.Exception -> L4e
                                goto L7d
                            L78:
                                java.lang.String r0 = "Exception occurred : "
                                com.android.server.BootReceiver$$ExternalSyntheticOutline0.m(r6, r0, r2)
                            L7d:
                                return
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.samsung.android.server.battery.DeviceBatteryInfoService$$ExternalSyntheticLambda0.run():void");
                        }
                    });
        }
    }

    public final void updateSemModemActivityInfo(SemModemActivityInfo semModemActivityInfo) {
        updateSemModemActivityInfo_enforcePermission();
        if (semModemActivityInfo == null) {
            Slog.e("BatteryStatsService", "Invalid SemModemActivityInfo, Null");
            return;
        }
        synchronized (this.mLock) {
            this.mHandler.post(
                    new BatteryStatsService$$ExternalSyntheticLambda104(
                            this, semModemActivityInfo, 0));
        }
    }

    public final void updateSpeakerOutEnergyInfo(SpeakerOutEnergyInfo speakerOutEnergyInfo) {
        updateSpeakerOutEnergyInfo_enforcePermission();
        if (speakerOutEnergyInfo == null) {
            Slog.e(
                    "BatteryStatsService",
                    "invalid SpeakerOutEnergyInfo given: " + speakerOutEnergyInfo);
        } else {
            synchronized (this.mLock) {
                this.mHandler.post(
                        new BatteryStatsService$$ExternalSyntheticLambda28(
                                this,
                                speakerOutEnergyInfo,
                                SystemClock.elapsedRealtime(),
                                SystemClock.uptimeMillis()));
            }
        }
    }
}
