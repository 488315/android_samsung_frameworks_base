package com.android.server.power.stats;

import android.content.Context;
import android.hardware.power.stats.EnergyConsumer;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SynchronousResultReceiver;
import android.os.SystemClock;
import android.os.connectivity.WifiActivityEnergyInfo;
import android.telephony.TelephonyManager;
import android.util.IntArray;
import android.util.Slog;
import android.util.SparseArray;

import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.powerstats.PowerStatsService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BatteryExternalStatsWorker {
    public Future mBatteryLevelSync;
    public final Injector mInjector;
    public long mLastCollectionTimeStamp;
    public boolean mOnBattery;
    public boolean mOnBatteryScreenOff;
    public int[] mPerDisplayScreenStates;
    public Future mProcessStateSync;
    public int mScreenState;
    public final BatteryStatsImpl mStats;
    public final AnonymousClass1 mSyncTask;
    public Future mWakelockChangesUpdate;
    public final AnonymousClass1 mWriteTask;
    public final ScheduledExecutorService mExecutorService =
            Executors.newSingleThreadScheduledExecutor(
                    new BatteryExternalStatsWorker$$ExternalSyntheticLambda0());
    public int mUpdateFlags = 0;
    public Future mCurrentFuture = null;
    public String mCurrentReason = null;
    public boolean mUseLatestStates = true;
    public final Object mWorkerLock = new Object();
    public WifiManager mWifiManager = null;
    public TelephonyManager mTelephony = null;
    public PowerStatsService.LocalService mPowerStatsInternal = null;
    public WifiActivityEnergyInfo mLastWifiInfo = null;
    public SparseArray mEnergyConsumerTypeToIdMap = null;
    public EnergyConsumerSnapshot mEnergyConsumerSnapshot = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.power.stats.BatteryExternalStatsWorker$3, reason: invalid class name */
    public final class AnonymousClass3 implements Executor {
        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            runnable.run();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(20:0|1|(5:3|(1:158)(1:7)|8|52|13)(1:159)|14|(2:16|(1:18)(2:19|(13:21|22|(3:24|(1:26)(2:39|(1:41))|(3:28|cd|33))|42|(1:44)|45|46|47|48|49|(1:146)(5:52|131|58|59|60)|61|17f)))|153|22|(0)|42|(0)|45|46|47|48|49|(0)|146|61|17f|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0104, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x011e, code lost:

       com.android.server.WallpaperUpdateReceiver$$ExternalSyntheticOutline0.m(r0, "timeout or interrupt reading modem stats: ", "BatteryExternalStatsWorker");
    */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x0126, code lost:

       r17 = null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x0102, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x0106, code lost:

       android.util.Slog.w("BatteryExternalStatsWorker", "exception reading modem stats: " + r0.getCause());
    */
    /* JADX WARN: Removed duplicated region for block: B:24:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x012c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0180 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: -$$Nest$mupdateExternalStatsLocked, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m834$$Nest$mupdateExternalStatsLocked(
            com.android.server.power.stats.BatteryExternalStatsWorker r31,
            java.lang.String r32,
            int r33,
            boolean r34,
            boolean r35,
            int[] r36,
            boolean r37) {
        /*
            Method dump skipped, instructions count: 781
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.stats.BatteryExternalStatsWorker.m834$$Nest$mupdateExternalStatsLocked(com.android.server.power.stats.BatteryExternalStatsWorker,"
                    + " java.lang.String, int, boolean, boolean, int[], boolean):void");
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.power.stats.BatteryExternalStatsWorker$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.server.power.stats.BatteryExternalStatsWorker$1] */
    public BatteryExternalStatsWorker(Injector injector, BatteryStatsImpl batteryStatsImpl) {
        final int i = 0;
        this.mSyncTask =
                new Runnable(
                        this) { // from class:
                                // com.android.server.power.stats.BatteryExternalStatsWorker.1
                    public final /* synthetic */ BatteryExternalStatsWorker this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void
                            run$com$android$server$power$stats$BatteryExternalStatsWorker$1() {
                        int i2;
                        String str;
                        boolean z;
                        boolean z2;
                        int[] iArr;
                        boolean z3;
                        int i3;
                        int i4;
                        Future future;
                        synchronized (this.this$0) {
                            try {
                                BatteryExternalStatsWorker batteryExternalStatsWorker = this.this$0;
                                i2 = batteryExternalStatsWorker.mUpdateFlags;
                                str = batteryExternalStatsWorker.mCurrentReason;
                                z = batteryExternalStatsWorker.mOnBattery;
                                z2 = batteryExternalStatsWorker.mOnBatteryScreenOff;
                                iArr = batteryExternalStatsWorker.mPerDisplayScreenStates;
                                z3 = batteryExternalStatsWorker.mUseLatestStates;
                                batteryExternalStatsWorker.mUpdateFlags = 0;
                                batteryExternalStatsWorker.mCurrentReason = null;
                                batteryExternalStatsWorker.mCurrentFuture = null;
                                batteryExternalStatsWorker.mUseLatestStates = true;
                                i3 = i2 & 127;
                                if (i3 == 127
                                        && (future = batteryExternalStatsWorker.mBatteryLevelSync)
                                                != null) {
                                    future.cancel(false);
                                    batteryExternalStatsWorker.mBatteryLevelSync = null;
                                }
                                i4 = i2 & 1;
                                if (i4 != 0) {
                                    BatteryExternalStatsWorker batteryExternalStatsWorker2 =
                                            this.this$0;
                                    synchronized (batteryExternalStatsWorker2) {
                                        Future future2 =
                                                batteryExternalStatsWorker2.mWakelockChangesUpdate;
                                        if (future2 != null) {
                                            future2.cancel(false);
                                            batteryExternalStatsWorker2.mWakelockChangesUpdate =
                                                    null;
                                        }
                                    }
                                }
                                if ((i2 & 14) == 14) {
                                    this.this$0.cancelSyncDueToProcessStateChange();
                                }
                            } catch (Throwable th) {
                                throw th;
                            } finally {
                            }
                        }
                        try {
                            synchronized (this.this$0.mWorkerLock) {
                                try {
                                    try {
                                        BatteryExternalStatsWorker
                                                .m834$$Nest$mupdateExternalStatsLocked(
                                                        this.this$0, str, i2, z, z2, iArr, z3);
                                        if (i3 == 127) {
                                            synchronized (this.this$0.mStats) {
                                                BatteryStatsImpl batteryStatsImpl2 =
                                                        this.this$0.mStats;
                                                synchronized (batteryStatsImpl2) {
                                                    batteryStatsImpl2.mIgnoreNextExternalStats =
                                                            false;
                                                }
                                            }
                                        }
                                    } catch (Throwable th2) {
                                        if (i3 == 127) {
                                            synchronized (this.this$0.mStats) {
                                                BatteryStatsImpl batteryStatsImpl3 =
                                                        this.this$0.mStats;
                                                synchronized (batteryStatsImpl3) {
                                                    batteryStatsImpl3.mIgnoreNextExternalStats =
                                                            false;
                                                }
                                            }
                                        }
                                        throw th2;
                                    }
                                } finally {
                                }
                            }
                            if (i4 != 0) {
                                this.this$0.mStats.updateCpuTimesForAllUids();
                            }
                            synchronized (this.this$0.mStats) {
                                this.this$0.mStats.clearPendingRemovedUidsLocked();
                            }
                        } catch (Exception e) {
                            Slog.wtf(
                                    "BatteryExternalStatsWorker",
                                    "Error updating external stats: ",
                                    e);
                        }
                        if ((i2 & 128) != 0) {
                            synchronized (this.this$0) {
                                this.this$0.mLastCollectionTimeStamp = 0L;
                            }
                        } else if (i3 == 127) {
                            synchronized (this.this$0) {
                                this.this$0.mLastCollectionTimeStamp =
                                        SystemClock.elapsedRealtime();
                            }
                        }
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i) {
                            case 0:
                                run$com$android$server$power$stats$BatteryExternalStatsWorker$1();
                                return;
                            default:
                                synchronized (this.this$0.mStats) {
                                    this.this$0.mStats.writeAsyncLocked();
                                }
                                return;
                        }
                    }
                };
        final int i2 = 1;
        this.mWriteTask =
                new Runnable(
                        this) { // from class:
                                // com.android.server.power.stats.BatteryExternalStatsWorker.1
                    public final /* synthetic */ BatteryExternalStatsWorker this$0;

                    {
                        this.this$0 = this;
                    }

                    private final void
                            run$com$android$server$power$stats$BatteryExternalStatsWorker$1() {
                        int i22;
                        String str;
                        boolean z;
                        boolean z2;
                        int[] iArr;
                        boolean z3;
                        int i3;
                        int i4;
                        Future future;
                        synchronized (this.this$0) {
                            try {
                                BatteryExternalStatsWorker batteryExternalStatsWorker = this.this$0;
                                i22 = batteryExternalStatsWorker.mUpdateFlags;
                                str = batteryExternalStatsWorker.mCurrentReason;
                                z = batteryExternalStatsWorker.mOnBattery;
                                z2 = batteryExternalStatsWorker.mOnBatteryScreenOff;
                                iArr = batteryExternalStatsWorker.mPerDisplayScreenStates;
                                z3 = batteryExternalStatsWorker.mUseLatestStates;
                                batteryExternalStatsWorker.mUpdateFlags = 0;
                                batteryExternalStatsWorker.mCurrentReason = null;
                                batteryExternalStatsWorker.mCurrentFuture = null;
                                batteryExternalStatsWorker.mUseLatestStates = true;
                                i3 = i22 & 127;
                                if (i3 == 127
                                        && (future = batteryExternalStatsWorker.mBatteryLevelSync)
                                                != null) {
                                    future.cancel(false);
                                    batteryExternalStatsWorker.mBatteryLevelSync = null;
                                }
                                i4 = i22 & 1;
                                if (i4 != 0) {
                                    BatteryExternalStatsWorker batteryExternalStatsWorker2 =
                                            this.this$0;
                                    synchronized (batteryExternalStatsWorker2) {
                                        Future future2 =
                                                batteryExternalStatsWorker2.mWakelockChangesUpdate;
                                        if (future2 != null) {
                                            future2.cancel(false);
                                            batteryExternalStatsWorker2.mWakelockChangesUpdate =
                                                    null;
                                        }
                                    }
                                }
                                if ((i22 & 14) == 14) {
                                    this.this$0.cancelSyncDueToProcessStateChange();
                                }
                            } catch (Throwable th) {
                                throw th;
                            } finally {
                            }
                        }
                        try {
                            synchronized (this.this$0.mWorkerLock) {
                                try {
                                    try {
                                        BatteryExternalStatsWorker
                                                .m834$$Nest$mupdateExternalStatsLocked(
                                                        this.this$0, str, i22, z, z2, iArr, z3);
                                        if (i3 == 127) {
                                            synchronized (this.this$0.mStats) {
                                                BatteryStatsImpl batteryStatsImpl2 =
                                                        this.this$0.mStats;
                                                synchronized (batteryStatsImpl2) {
                                                    batteryStatsImpl2.mIgnoreNextExternalStats =
                                                            false;
                                                }
                                            }
                                        }
                                    } catch (Throwable th2) {
                                        if (i3 == 127) {
                                            synchronized (this.this$0.mStats) {
                                                BatteryStatsImpl batteryStatsImpl3 =
                                                        this.this$0.mStats;
                                                synchronized (batteryStatsImpl3) {
                                                    batteryStatsImpl3.mIgnoreNextExternalStats =
                                                            false;
                                                }
                                            }
                                        }
                                        throw th2;
                                    }
                                } finally {
                                }
                            }
                            if (i4 != 0) {
                                this.this$0.mStats.updateCpuTimesForAllUids();
                            }
                            synchronized (this.this$0.mStats) {
                                this.this$0.mStats.clearPendingRemovedUidsLocked();
                            }
                        } catch (Exception e) {
                            Slog.wtf(
                                    "BatteryExternalStatsWorker",
                                    "Error updating external stats: ",
                                    e);
                        }
                        if ((i22 & 128) != 0) {
                            synchronized (this.this$0) {
                                this.this$0.mLastCollectionTimeStamp = 0L;
                            }
                        } else if (i3 == 127) {
                            synchronized (this.this$0) {
                                this.this$0.mLastCollectionTimeStamp =
                                        SystemClock.elapsedRealtime();
                            }
                        }
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i2) {
                            case 0:
                                run$com$android$server$power$stats$BatteryExternalStatsWorker$1();
                                return;
                            default:
                                synchronized (this.this$0.mStats) {
                                    this.this$0.mStats.writeAsyncLocked();
                                }
                                return;
                        }
                    }
                };
        this.mInjector = injector;
        this.mStats = batteryStatsImpl;
    }

    public static Parcelable awaitControllerInfo(
            SynchronousResultReceiver synchronousResultReceiver) {
        if (synchronousResultReceiver == null) {
            return null;
        }
        try {
            SynchronousResultReceiver.Result awaitResult =
                    synchronousResultReceiver.awaitResult(2000L);
            Bundle bundle = awaitResult.bundle;
            if (bundle != null) {
                bundle.setDefusable(true);
                Parcelable parcelable = awaitResult.bundle.getParcelable("controller_activity");
                if (parcelable != null) {
                    return parcelable;
                }
            }
        } catch (TimeoutException unused) {
            Slog.w(
                    "BatteryExternalStatsWorker",
                    "timeout reading " + synchronousResultReceiver.getName() + " stats");
        }
        return null;
    }

    public static boolean[] getSupportedEnergyBuckets(SparseArray sparseArray) {
        boolean[] zArr = new boolean[10];
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            switch (((EnergyConsumer) sparseArray.valueAt(i)).type) {
                case 1:
                    zArr[5] = true;
                    break;
                case 2:
                    zArr[3] = true;
                    break;
                case 3:
                    zArr[0] = true;
                    zArr[1] = true;
                    zArr[2] = true;
                    break;
                case 4:
                    zArr[6] = true;
                    break;
                case 5:
                    zArr[7] = true;
                    zArr[9] = true;
                    break;
                case 6:
                    zArr[4] = true;
                    break;
                case 7:
                    zArr[8] = true;
                    break;
            }
        }
        return zArr;
    }

    public final void addEnergyConsumerIdLocked(IntArray intArray, int i) {
        int[] iArr = (int[]) this.mEnergyConsumerTypeToIdMap.get(i);
        if (iArr == null) {
            return;
        }
        intArray.addAll(iArr);
    }

    public final void cancelSyncDueToProcessStateChange() {
        synchronized (this) {
            try {
                Future future = this.mProcessStateSync;
                if (future != null) {
                    future.cancel(false);
                    this.mProcessStateSync = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public WifiActivityEnergyInfo extractDeltaLocked(
            WifiActivityEnergyInfo wifiActivityEnergyInfo) {
        long j;
        long j2;
        long j3;
        long j4;
        long j5;
        long j6;
        long j7;
        long j8;
        boolean z;
        if (this.mLastWifiInfo == null) {
            this.mLastWifiInfo = wifiActivityEnergyInfo;
        }
        long timeSinceBootMillis =
                wifiActivityEnergyInfo.getTimeSinceBootMillis()
                        - this.mLastWifiInfo.getTimeSinceBootMillis();
        long controllerScanDurationMillis = this.mLastWifiInfo.getControllerScanDurationMillis();
        long controllerIdleDurationMillis = this.mLastWifiInfo.getControllerIdleDurationMillis();
        long controllerTxDurationMillis = this.mLastWifiInfo.getControllerTxDurationMillis();
        long controllerRxDurationMillis = this.mLastWifiInfo.getControllerRxDurationMillis();
        long controllerEnergyUsedMicroJoules =
                this.mLastWifiInfo.getControllerEnergyUsedMicroJoules();
        long timeSinceBootMillis2 = wifiActivityEnergyInfo.getTimeSinceBootMillis();
        int stackState = wifiActivityEnergyInfo.getStackState();
        long controllerTxDurationMillis2 =
                wifiActivityEnergyInfo.getControllerTxDurationMillis() - controllerTxDurationMillis;
        long controllerRxDurationMillis2 =
                wifiActivityEnergyInfo.getControllerRxDurationMillis() - controllerRxDurationMillis;
        long controllerIdleDurationMillis2 =
                wifiActivityEnergyInfo.getControllerIdleDurationMillis()
                        - controllerIdleDurationMillis;
        long controllerScanDurationMillis2 =
                wifiActivityEnergyInfo.getControllerScanDurationMillis()
                        - controllerScanDurationMillis;
        long j9 = 0;
        if (controllerTxDurationMillis2 < 0
                || controllerRxDurationMillis2 < 0
                || controllerScanDurationMillis2 < 0
                || controllerIdleDurationMillis2 < 0) {
            if (wifiActivityEnergyInfo.getControllerIdleDurationMillis()
                            + wifiActivityEnergyInfo.getControllerRxDurationMillis()
                            + wifiActivityEnergyInfo.getControllerTxDurationMillis()
                    <= timeSinceBootMillis + 750) {
                long controllerEnergyUsedMicroJoules2 =
                        wifiActivityEnergyInfo.getControllerEnergyUsedMicroJoules();
                j = wifiActivityEnergyInfo.getControllerRxDurationMillis();
                long controllerTxDurationMillis3 =
                        wifiActivityEnergyInfo.getControllerTxDurationMillis();
                j3 = wifiActivityEnergyInfo.getControllerIdleDurationMillis();
                j4 = wifiActivityEnergyInfo.getControllerScanDurationMillis();
                j9 = controllerTxDurationMillis3;
                j2 = controllerEnergyUsedMicroJoules2;
            } else {
                j = 0;
                j2 = 0;
                j3 = 0;
                j4 = 0;
            }
            j5 = j;
            controllerTxDurationMillis2 = j9;
            j6 = j2;
            j7 = j3;
            j8 = j4;
            z = true;
        } else {
            z = false;
            j6 =
                    Math.max(
                            0L,
                            wifiActivityEnergyInfo.getControllerEnergyUsedMicroJoules()
                                    - controllerEnergyUsedMicroJoules);
            j8 = controllerScanDurationMillis2;
            j5 = controllerRxDurationMillis2;
            j7 = controllerIdleDurationMillis2;
        }
        this.mLastWifiInfo = wifiActivityEnergyInfo;
        WifiActivityEnergyInfo wifiActivityEnergyInfo2 =
                new WifiActivityEnergyInfo(
                        timeSinceBootMillis2,
                        stackState,
                        controllerTxDurationMillis2,
                        j5,
                        j8,
                        j7,
                        j6);
        if (z) {
            Slog.v(
                    "BatteryExternalStatsWorker",
                    "WiFi energy data was reset, new WiFi energy data is "
                            + wifiActivityEnergyInfo2);
        }
        return wifiActivityEnergyInfo2;
    }

    public CompletableFuture getEnergyConsumersLocked(int i) {
        PowerStatsService.LocalService localService;
        if (this.mEnergyConsumerSnapshot == null
                || (localService = this.mPowerStatsInternal) == null) {
            return null;
        }
        if (i == 127) {
            return localService.getEnergyConsumedAsync(new int[0]);
        }
        IntArray intArray = new IntArray();
        if ((i & 8) != 0) {
            addEnergyConsumerIdLocked(intArray, 1);
        }
        if ((i & 1) != 0) {
            addEnergyConsumerIdLocked(intArray, 2);
        }
        if ((i & 32) != 0) {
            addEnergyConsumerIdLocked(intArray, 3);
        }
        if ((i & 4) != 0) {
            addEnergyConsumerIdLocked(intArray, 5);
        }
        if ((i & 2) != 0) {
            addEnergyConsumerIdLocked(intArray, 6);
        }
        if ((i & 64) != 0) {
            addEnergyConsumerIdLocked(intArray, 7);
        }
        if (intArray.size() == 0) {
            return null;
        }
        return this.mPowerStatsInternal.getEnergyConsumedAsync(intArray.toArray());
    }

    public final SparseArray populateEnergyConsumerSubsystemMapsLocked() {
        byte b;
        PowerStatsService.LocalService localService = this.mPowerStatsInternal;
        SparseArray sparseArray = null;
        if (localService == null) {
            return null;
        }
        EnergyConsumer[] energyConsumerInfo =
                PowerStatsService.this.getPowerStatsHal().getEnergyConsumerInfo();
        if (energyConsumerInfo != null && energyConsumerInfo.length != 0) {
            sparseArray = new SparseArray(energyConsumerInfo.length);
            SparseArray sparseArray2 = new SparseArray();
            for (EnergyConsumer energyConsumer : energyConsumerInfo) {
                if (energyConsumer.ordinal == 0
                        || (b = energyConsumer.type) == 0
                        || b == 2
                        || b == 3) {
                    sparseArray.put(energyConsumer.id, energyConsumer);
                    IntArray intArray = (IntArray) sparseArray2.get(energyConsumer.type);
                    if (intArray == null) {
                        intArray = new IntArray();
                        sparseArray2.put(energyConsumer.type, intArray);
                    }
                    intArray.add(energyConsumer.id);
                } else {
                    StringBuilder sb = new StringBuilder("EnergyConsumer '");
                    sb.append(energyConsumer.name);
                    sb.append("' has unexpected ordinal ");
                    sb.append(energyConsumer.ordinal);
                    sb.append(" for type ");
                    HeapdumpWatcher$$ExternalSyntheticOutline0.m(
                            sb, energyConsumer.type, "BatteryExternalStatsWorker");
                }
            }
            this.mEnergyConsumerTypeToIdMap = new SparseArray(sparseArray2.size());
            int size = sparseArray2.size();
            for (int i = 0; i < size; i++) {
                this.mEnergyConsumerTypeToIdMap.put(
                        sparseArray2.keyAt(i), ((IntArray) sparseArray2.valueAt(i)).toArray());
            }
        }
        return sparseArray;
    }

    public final void scheduleCpuSyncDueToWakelockChange(long j) {
        synchronized (this) {
            this.mWakelockChangesUpdate =
                    scheduleDelayedSyncLocked(
                            this.mWakelockChangesUpdate,
                            new BatteryExternalStatsWorker$$ExternalSyntheticLambda1(0, this),
                            j);
        }
    }

    public final Future scheduleDelayedSyncLocked(Future future, Runnable runnable, long j) {
        if (this.mExecutorService.isShutdown()) {
            return CompletableFuture.failedFuture(new IllegalStateException("worker shutdown"));
        }
        if (future != null) {
            if (j != 0) {
                return future;
            }
            future.cancel(false);
        }
        try {
            return this.mExecutorService.schedule(runnable, j, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    public final synchronized void scheduleRunnable(Runnable runnable) {
        try {
            this.mExecutorService.submit(runnable);
        } catch (RejectedExecutionException e) {
            Slog.e("BatteryExternalStatsWorker", "Couldn't schedule " + runnable, e);
        }
    }

    public final synchronized Future scheduleSync(int i, String str) {
        return scheduleSyncLocked(i, str);
    }

    public final Future scheduleSyncLocked(int i, String str) {
        if (this.mExecutorService.isShutdown()) {
            return CompletableFuture.failedFuture(new IllegalStateException("worker shutdown"));
        }
        if (this.mCurrentFuture == null) {
            this.mUpdateFlags = i;
            this.mCurrentReason = str;
            try {
                this.mCurrentFuture = this.mExecutorService.submit(this.mSyncTask);
            } catch (RejectedExecutionException e) {
                return CompletableFuture.failedFuture(e);
            }
        }
        this.mUpdateFlags = i | this.mUpdateFlags;
        return this.mCurrentFuture;
    }
}
