package android.os.health;

import android.content.Context;
import android.hardware.power.CpuHeadroomResult;
import android.hardware.power.GpuHeadroomResult;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.CpuHeadroomParams;
import android.os.CpuHeadroomParamsInternal;
import android.os.GpuHeadroomParams;
import android.os.GpuHeadroomParamsInternal;
import android.os.Handler;
import android.os.IHintManager;
import android.os.IPowerStatsService;
import android.os.OutcomeReceiver;
import android.os.PowerMonitor;
import android.os.PowerMonitorReadings;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.SynchronousResultReceiver;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.app.IBatteryStats;
import com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.Flags;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

/* loaded from: classes3.dex */
public class SystemHealthManager {
    private static final Comparator<PowerMonitor> POWER_MONITOR_COMPARATOR = Comparator.comparingInt(new ToIntFunction() { // from class: android.os.health.SystemHealthManager$$ExternalSyntheticLambda1
        @Override // java.util.function.ToIntFunction
        public final int applyAsInt(Object obj) {
            int i;
            i = ((PowerMonitor) obj).index;
            return i;
        }
    });
    private static final String TAG = "SystemHealthManager";
    private static final long TAKE_UID_SNAPSHOT_TIMEOUT_MILLIS = 10000;
    private final IBatteryStats mBatteryStats;
    private final IHintManager mHintManager;
    private final IHintManager.HintManagerClientData mHintManagerClientData;
    private final PendingUidSnapshots mPendingUidSnapshots;
    private List<PowerMonitor> mPowerMonitorsInfo;
    private final Object mPowerMonitorsLock;
    private final IPowerStatsService mPowerStats;

    private static class PendingUidSnapshots {
        public SynchronousResultReceiver resultReceiver;
        public int[] uids;

        private PendingUidSnapshots() {
        }
    }

    public SystemHealthManager() {
        this(IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats")), IPowerStatsService.Stub.asInterface(ServiceManager.getService(Context.POWER_STATS_SERVICE)), IHintManager.Stub.asInterface(ServiceManager.getService(Context.PERFORMANCE_HINT_SERVICE)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.os.health.SystemHealthManager-IA] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5 */
    public SystemHealthManager(IBatteryStats iBatteryStats, IPowerStatsService iPowerStatsService, IHintManager iHintManager) {
        this.mPowerMonitorsLock = new Object();
        IHintManager.HintManagerClientData hintManagerClientData = 0;
        hintManagerClientData = 0;
        this.mPendingUidSnapshots = new PendingUidSnapshots();
        this.mBatteryStats = iBatteryStats;
        this.mPowerStats = iPowerStatsService;
        this.mHintManager = iHintManager;
        if (iHintManager != null) {
            try {
                hintManagerClientData = iHintManager.getClientData();
            } catch (RemoteException e) {
                Slog.e(TAG, "Failed to get hint manager client data", e);
            }
        }
        this.mHintManagerClientData = hintManagerClientData;
    }

    public float getCpuHeadroom(CpuHeadroomParams cpuHeadroomParams) {
        IHintManager.HintManagerClientData hintManagerClientData;
        if (this.mHintManager == null || (hintManagerClientData = this.mHintManagerClientData) == null || !hintManagerClientData.supportInfo.headroom.isCpuSupported) {
            throw new UnsupportedOperationException();
        }
        if (cpuHeadroomParams != null) {
            if (cpuHeadroomParams.mInternal.tids != null && (cpuHeadroomParams.mInternal.tids.length == 0 || cpuHeadroomParams.mInternal.tids.length > this.mHintManagerClientData.maxCpuHeadroomThreads)) {
                throw new IllegalArgumentException("Invalid number of TIDs: " + cpuHeadroomParams.mInternal.tids.length);
            }
            if (cpuHeadroomParams.mInternal.calculationWindowMillis < this.mHintManagerClientData.supportInfo.headroom.cpuMinCalculationWindowMillis || cpuHeadroomParams.mInternal.calculationWindowMillis > this.mHintManagerClientData.supportInfo.headroom.cpuMaxCalculationWindowMillis) {
                throw new IllegalArgumentException("Invalid calculation window: " + cpuHeadroomParams.mInternal.calculationWindowMillis + ", expect range: [" + this.mHintManagerClientData.supportInfo.headroom.cpuMinCalculationWindowMillis + ", " + this.mHintManagerClientData.supportInfo.headroom.cpuMaxCalculationWindowMillis + NavigationBarInflaterView.SIZE_MOD_END);
            }
        }
        try {
            CpuHeadroomResult cpuHeadroom = this.mHintManager.getCpuHeadroom(cpuHeadroomParams != null ? cpuHeadroomParams.mInternal : new CpuHeadroomParamsInternal());
            if (cpuHeadroom != null && cpuHeadroom.getTag() == 0) {
                return cpuHeadroom.getGlobalHeadroom();
            }
            return Float.NaN;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMaxCpuHeadroomTidsSize() {
        IHintManager.HintManagerClientData hintManagerClientData;
        if (this.mHintManager == null || (hintManagerClientData = this.mHintManagerClientData) == null || !hintManagerClientData.supportInfo.headroom.isCpuSupported) {
            throw new UnsupportedOperationException();
        }
        return this.mHintManagerClientData.maxCpuHeadroomThreads;
    }

    public float getGpuHeadroom(GpuHeadroomParams gpuHeadroomParams) {
        IHintManager.HintManagerClientData hintManagerClientData;
        if (this.mHintManager == null || (hintManagerClientData = this.mHintManagerClientData) == null || !hintManagerClientData.supportInfo.headroom.isGpuSupported) {
            throw new UnsupportedOperationException();
        }
        if (gpuHeadroomParams != null && (gpuHeadroomParams.mInternal.calculationWindowMillis < this.mHintManagerClientData.supportInfo.headroom.gpuMinCalculationWindowMillis || gpuHeadroomParams.mInternal.calculationWindowMillis > this.mHintManagerClientData.supportInfo.headroom.gpuMaxCalculationWindowMillis)) {
            throw new IllegalArgumentException("Invalid calculation window: " + gpuHeadroomParams.mInternal.calculationWindowMillis + ", expect range: [" + this.mHintManagerClientData.supportInfo.headroom.gpuMinCalculationWindowMillis + ", " + this.mHintManagerClientData.supportInfo.headroom.gpuMaxCalculationWindowMillis + NavigationBarInflaterView.SIZE_MOD_END);
        }
        try {
            GpuHeadroomResult gpuHeadroom = this.mHintManager.getGpuHeadroom(gpuHeadroomParams != null ? gpuHeadroomParams.mInternal : new GpuHeadroomParamsInternal());
            if (gpuHeadroom != null && gpuHeadroom.getTag() == 0) {
                return gpuHeadroom.getGlobalHeadroom();
            }
            return Float.NaN;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Pair<Integer, Integer> getCpuHeadroomCalculationWindowRange() {
        IHintManager.HintManagerClientData hintManagerClientData;
        if (this.mHintManager == null || (hintManagerClientData = this.mHintManagerClientData) == null || !hintManagerClientData.supportInfo.headroom.isCpuSupported) {
            throw new UnsupportedOperationException();
        }
        return new Pair<>(Integer.valueOf(this.mHintManagerClientData.supportInfo.headroom.cpuMinCalculationWindowMillis), Integer.valueOf(this.mHintManagerClientData.supportInfo.headroom.cpuMaxCalculationWindowMillis));
    }

    public Pair<Integer, Integer> getGpuHeadroomCalculationWindowRange() {
        IHintManager.HintManagerClientData hintManagerClientData;
        if (this.mHintManager == null || (hintManagerClientData = this.mHintManagerClientData) == null || !hintManagerClientData.supportInfo.headroom.isGpuSupported) {
            throw new UnsupportedOperationException();
        }
        return new Pair<>(Integer.valueOf(this.mHintManagerClientData.supportInfo.headroom.gpuMinCalculationWindowMillis), Integer.valueOf(this.mHintManagerClientData.supportInfo.headroom.gpuMaxCalculationWindowMillis));
    }

    public long getCpuHeadroomMinIntervalMillis() {
        IHintManager.HintManagerClientData hintManagerClientData;
        if (this.mHintManager == null || (hintManagerClientData = this.mHintManagerClientData) == null || !hintManagerClientData.supportInfo.headroom.isCpuSupported) {
            throw new UnsupportedOperationException();
        }
        return this.mHintManagerClientData.supportInfo.headroom.cpuMinIntervalMillis;
    }

    public long getGpuHeadroomMinIntervalMillis() {
        IHintManager.HintManagerClientData hintManagerClientData;
        if (this.mHintManager == null || (hintManagerClientData = this.mHintManagerClientData) == null || !hintManagerClientData.supportInfo.headroom.isGpuSupported) {
            throw new UnsupportedOperationException();
        }
        return this.mHintManagerClientData.supportInfo.headroom.gpuMinIntervalMillis;
    }

    public static SystemHealthManager from(Context context) {
        return (SystemHealthManager) context.getSystemService(Context.SYSTEM_HEALTH_SERVICE);
    }

    public HealthStats takeUidSnapshot(int i) {
        if (!Flags.onewayBatteryStatsService()) {
            try {
                return this.mBatteryStats.takeUidSnapshot(i).getHealthStats();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        HealthStats[] takeUidSnapshots = takeUidSnapshots(new int[]{i});
        if (takeUidSnapshots == null || takeUidSnapshots.length < 1) {
            return null;
        }
        return takeUidSnapshots[0];
    }

    public HealthStats takeMyUidSnapshot() {
        return takeUidSnapshot(Process.myUid());
    }

    public HealthStats[] takeUidSnapshots(int[] iArr) {
        SynchronousResultReceiver synchronousResultReceiver;
        HealthStatsParceler[] healthStatsParcelerArr;
        int i = 0;
        if (!Flags.onewayBatteryStatsService()) {
            try {
                HealthStatsParceler[] takeUidSnapshots = this.mBatteryStats.takeUidSnapshots(iArr);
                int length = iArr.length;
                HealthStats[] healthStatsArr = new HealthStats[length];
                while (i < length) {
                    healthStatsArr[i] = takeUidSnapshots[i].getHealthStats();
                    i++;
                }
                return healthStatsArr;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        synchronized (this.mPendingUidSnapshots) {
            if (Arrays.equals(this.mPendingUidSnapshots.uids, iArr)) {
                synchronousResultReceiver = this.mPendingUidSnapshots.resultReceiver;
            } else {
                this.mPendingUidSnapshots.uids = Arrays.copyOf(iArr, iArr.length);
                PendingUidSnapshots pendingUidSnapshots = this.mPendingUidSnapshots;
                SynchronousResultReceiver synchronousResultReceiver2 = new SynchronousResultReceiver("takeUidSnapshots");
                pendingUidSnapshots.resultReceiver = synchronousResultReceiver2;
                try {
                    this.mBatteryStats.takeUidSnapshotsAsync(iArr, synchronousResultReceiver2);
                    synchronousResultReceiver = synchronousResultReceiver2;
                } catch (RemoteException e2) {
                    throw e2.rethrowFromSystemServer();
                }
            }
        }
        try {
            try {
                SynchronousResultReceiver.Result awaitResult = synchronousResultReceiver.awaitResult(10000L);
                synchronized (this.mPendingUidSnapshots) {
                    if (this.mPendingUidSnapshots.resultReceiver == synchronousResultReceiver) {
                        this.mPendingUidSnapshots.uids = null;
                        this.mPendingUidSnapshots.resultReceiver = null;
                    }
                }
                int i2 = awaitResult.resultCode;
                if (i2 != 0) {
                    if (i2 == 1) {
                        throw new RuntimeException(awaitResult.bundle != null ? awaitResult.bundle.getString("exception") : null);
                    }
                    if (i2 == 2) {
                        throw new SecurityException(awaitResult.bundle != null ? awaitResult.bundle.getString("exception") : null);
                    }
                    throw new RuntimeException("Error code: " + awaitResult.resultCode);
                }
                HealthStats[] healthStatsArr2 = new HealthStats[iArr.length];
                if (awaitResult.bundle != null && (healthStatsParcelerArr = (HealthStatsParceler[]) awaitResult.bundle.getParcelableArray(IBatteryStats.KEY_UID_SNAPSHOTS, HealthStatsParceler.class)) != null && healthStatsParcelerArr.length == iArr.length) {
                    while (i < healthStatsParcelerArr.length) {
                        healthStatsArr2[i] = healthStatsParcelerArr[i].getHealthStats();
                        i++;
                    }
                }
                return healthStatsArr2;
            } catch (TimeoutException e3) {
                throw new RuntimeException(e3);
            }
        } catch (Throwable th) {
            synchronized (this.mPendingUidSnapshots) {
                if (this.mPendingUidSnapshots.resultReceiver == synchronousResultReceiver) {
                    this.mPendingUidSnapshots.uids = null;
                    this.mPendingUidSnapshots.resultReceiver = null;
                }
                throw th;
            }
        }
    }

    public void getSupportedPowerMonitors(Executor executor, final Consumer<List<PowerMonitor>> consumer) {
        final List<PowerMonitor> list;
        synchronized (this.mPowerMonitorsLock) {
            list = this.mPowerMonitorsInfo;
            if (list == null) {
                if (this.mPowerStats == null) {
                    list = Collections.EMPTY_LIST;
                    this.mPowerMonitorsInfo = list;
                } else {
                    list = null;
                }
            }
        }
        if (list == null) {
            try {
                this.mPowerStats.getSupportedPowerMonitors(new AnonymousClass1(null, executor, consumer));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else if (executor != null) {
            executor.execute(new Runnable() { // from class: android.os.health.SystemHealthManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(list);
                }
            });
        } else {
            consumer.accept(list);
        }
    }

    /* renamed from: android.os.health.SystemHealthManager$1, reason: invalid class name */
    class AnonymousClass1 extends ResultReceiver {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Consumer val$onResult;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Handler handler, Executor executor, Consumer consumer) {
            super(handler);
            this.val$executor = executor;
            this.val$onResult = consumer;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, Bundle bundle) {
            PowerMonitor[] powerMonitorArr = (PowerMonitor[]) bundle.getParcelableArray(IPowerStatsService.KEY_MONITORS, PowerMonitor.class);
            final List asList = powerMonitorArr != null ? Arrays.asList(powerMonitorArr) : Collections.EMPTY_LIST;
            synchronized (SystemHealthManager.this.mPowerMonitorsLock) {
                SystemHealthManager.this.mPowerMonitorsInfo = asList;
            }
            Executor executor = this.val$executor;
            if (executor != null) {
                final Consumer consumer = this.val$onResult;
                executor.execute(new Runnable() { // from class: android.os.health.SystemHealthManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(asList);
                    }
                });
            } else {
                this.val$onResult.accept(asList);
            }
        }
    }

    public void getPowerMonitorReadings(List<PowerMonitor> list, Executor executor, final OutcomeReceiver<PowerMonitorReadings, RuntimeException> outcomeReceiver) {
        if (this.mPowerStats == null) {
            final IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Unsupported power monitor");
            if (executor != null) {
                executor.execute(new Runnable() { // from class: android.os.health.SystemHealthManager$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onError(illegalArgumentException);
                    }
                });
                return;
            } else {
                outcomeReceiver.onError(illegalArgumentException);
                return;
            }
        }
        PowerMonitor[] powerMonitorArr = (PowerMonitor[]) list.toArray(new PowerMonitor[list.size()]);
        Arrays.sort(powerMonitorArr, POWER_MONITOR_COMPARATOR);
        int[] iArr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            iArr[i] = powerMonitorArr[i].index;
        }
        try {
            this.mPowerStats.getPowerMonitorReadings(iArr, new AnonymousClass2(this, null, powerMonitorArr, executor, outcomeReceiver));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.os.health.SystemHealthManager$2, reason: invalid class name */
    class AnonymousClass2 extends ResultReceiver {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ OutcomeReceiver val$onResult;
        final /* synthetic */ PowerMonitor[] val$powerMonitorsArray;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(SystemHealthManager systemHealthManager, Handler handler, PowerMonitor[] powerMonitorArr, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$powerMonitorsArray = powerMonitorArr;
            this.val$executor = executor;
            this.val$onResult = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, Bundle bundle) {
            final RuntimeException illegalStateException;
            if (i == 0) {
                final PowerMonitorReadings powerMonitorReadings = new PowerMonitorReadings(this.val$powerMonitorsArray, bundle.getLongArray(IPowerStatsService.KEY_ENERGY), bundle.getLongArray(IPowerStatsService.KEY_TIMESTAMPS), bundle.getInt(IPowerStatsService.KEY_GRANULARITY));
                Executor executor = this.val$executor;
                if (executor != null) {
                    final OutcomeReceiver outcomeReceiver = this.val$onResult;
                    executor.execute(new Runnable() { // from class: android.os.health.SystemHealthManager$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            OutcomeReceiver.this.onResult(powerMonitorReadings);
                        }
                    });
                    return;
                } else {
                    this.val$onResult.onResult(powerMonitorReadings);
                    return;
                }
            }
            if (i == 1) {
                illegalStateException = new IllegalArgumentException("Unsupported power monitor");
            } else {
                illegalStateException = new IllegalStateException("Unrecognized result code " + i);
            }
            Executor executor2 = this.val$executor;
            if (executor2 != null) {
                final OutcomeReceiver outcomeReceiver2 = this.val$onResult;
                executor2.execute(new Runnable() { // from class: android.os.health.SystemHealthManager$2$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onError(illegalStateException);
                    }
                });
            } else {
                this.val$onResult.onError(illegalStateException);
            }
        }
    }
}
