package android.os.health;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IPowerStatsService;
import android.os.OutcomeReceiver;
import android.os.PowerMonitor;
import android.os.PowerMonitorReadings;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.SynchronousResultReceiver;

import com.android.internal.app.IBatteryStats;
import com.android.internal.hidden_from_bootclasspath.com.android.server.power.optimization.Flags;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

/* loaded from: classes3.dex */
public class SystemHealthManager {
    private static final Comparator<PowerMonitor> POWER_MONITOR_COMPARATOR =
            Comparator.comparingInt(
                    new ToIntFunction() { // from class:
                                          // android.os.health.SystemHealthManager$$ExternalSyntheticLambda1
                        @Override // java.util.function.ToIntFunction
                        public final int applyAsInt(Object obj) {
                            int i;
                            i = ((PowerMonitor) obj).index;
                            return i;
                        }
                    });
    private static final long TAKE_UID_SNAPSHOT_TIMEOUT_MILLIS = 10000;
    private final IBatteryStats mBatteryStats;
    private final PendingUidSnapshots mPendingUidSnapshots;
    private List<PowerMonitor> mPowerMonitorsInfo;
    private final Object mPowerMonitorsLock;
    private final IPowerStatsService mPowerStats;

    private static class PendingUidSnapshots {
        public SynchronousResultReceiver resultReceiver;
        public int[] uids;

        private PendingUidSnapshots() {}
    }

    public SystemHealthManager() {
        this(
                IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats")),
                IPowerStatsService.Stub.asInterface(
                        ServiceManager.getService(Context.POWER_STATS_SERVICE)));
    }

    public SystemHealthManager(IBatteryStats batteryStats, IPowerStatsService powerStats) {
        this.mPowerMonitorsLock = new Object();
        this.mPendingUidSnapshots = new PendingUidSnapshots();
        this.mBatteryStats = batteryStats;
        this.mPowerStats = powerStats;
    }

    public static SystemHealthManager from(Context context) {
        return (SystemHealthManager) context.getSystemService(Context.SYSTEM_HEALTH_SERVICE);
    }

    public HealthStats takeUidSnapshot(int uid) {
        if (!Flags.onewayBatteryStatsService()) {
            try {
                HealthStatsParceler parceler = this.mBatteryStats.takeUidSnapshot(uid);
                return parceler.getHealthStats();
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        }
        HealthStats[] result = takeUidSnapshots(new int[] {uid});
        if (result != null && result.length >= 1) {
            return result[0];
        }
        return null;
    }

    public HealthStats takeMyUidSnapshot() {
        return takeUidSnapshot(Process.myUid());
    }

    public HealthStats[] takeUidSnapshots(int[] uids) {
        SynchronousResultReceiver resultReceiver;
        HealthStatsParceler[] parcelers;
        if (!Flags.onewayBatteryStatsService()) {
            try {
                HealthStatsParceler[] parcelers2 = this.mBatteryStats.takeUidSnapshots(uids);
                int count = uids.length;
                HealthStats[] results = new HealthStats[count];
                for (int i = 0; i < count; i++) {
                    results[i] = parcelers2[i].getHealthStats();
                }
                return results;
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        }
        synchronized (this.mPendingUidSnapshots) {
            if (Arrays.equals(this.mPendingUidSnapshots.uids, uids)) {
                resultReceiver = this.mPendingUidSnapshots.resultReceiver;
            } else {
                this.mPendingUidSnapshots.uids = Arrays.copyOf(uids, uids.length);
                PendingUidSnapshots pendingUidSnapshots = this.mPendingUidSnapshots;
                SynchronousResultReceiver resultReceiver2 =
                        new SynchronousResultReceiver("takeUidSnapshots");
                pendingUidSnapshots.resultReceiver = resultReceiver2;
                try {
                    this.mBatteryStats.takeUidSnapshotsAsync(uids, resultReceiver2);
                    resultReceiver = resultReceiver2;
                } catch (RemoteException ex2) {
                    throw ex2.rethrowFromSystemServer();
                }
            }
        }
        try {
            try {
                SynchronousResultReceiver.Result result = resultReceiver.awaitResult(10000L);
                synchronized (this.mPendingUidSnapshots) {
                    if (this.mPendingUidSnapshots.resultReceiver == resultReceiver) {
                        this.mPendingUidSnapshots.uids = null;
                        this.mPendingUidSnapshots.resultReceiver = null;
                    }
                }
                HealthStats[] results2 = new HealthStats[uids.length];
                if (result.bundle != null
                        && (parcelers =
                                        (HealthStatsParceler[])
                                                result.bundle.getParcelableArray(
                                                        IBatteryStats.KEY_UID_SNAPSHOTS,
                                                        HealthStatsParceler.class))
                                != null
                        && parcelers.length == uids.length) {
                    for (int i2 = 0; i2 < parcelers.length; i2++) {
                        results2[i2] = parcelers[i2].getHealthStats();
                    }
                }
                return results2;
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }
        } catch (Throwable th) {
            synchronized (this.mPendingUidSnapshots) {
                if (this.mPendingUidSnapshots.resultReceiver == resultReceiver) {
                    this.mPendingUidSnapshots.uids = null;
                    this.mPendingUidSnapshots.resultReceiver = null;
                }
                throw th;
            }
        }
    }

    public void getSupportedPowerMonitors(
            Executor executor, final Consumer<List<PowerMonitor>> onResult) {
        final List<PowerMonitor> result;
        synchronized (this.mPowerMonitorsLock) {
            if (this.mPowerMonitorsInfo != null) {
                result = this.mPowerMonitorsInfo;
            } else if (this.mPowerStats == null) {
                this.mPowerMonitorsInfo = List.of();
                result = this.mPowerMonitorsInfo;
            } else {
                result = null;
            }
        }
        if (result != null) {
            if (executor != null) {
                executor.execute(
                        new Runnable() { // from class:
                                         // android.os.health.SystemHealthManager$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                onResult.accept(result);
                            }
                        });
                return;
            } else {
                onResult.accept(result);
                return;
            }
        }
        try {
            this.mPowerStats.getSupportedPowerMonitors(
                    new AnonymousClass1(null, executor, onResult));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
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
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            PowerMonitor[] array =
                    (PowerMonitor[])
                            resultData.getParcelableArray(
                                    IPowerStatsService.KEY_MONITORS, PowerMonitor.class);
            final List<PowerMonitor> result = array != null ? Arrays.asList(array) : List.of();
            synchronized (SystemHealthManager.this.mPowerMonitorsLock) {
                SystemHealthManager.this.mPowerMonitorsInfo = result;
            }
            if (this.val$executor != null) {
                Executor executor = this.val$executor;
                final Consumer consumer = this.val$onResult;
                executor.execute(
                        new Runnable() { // from class:
                                         // android.os.health.SystemHealthManager$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                consumer.accept(result);
                            }
                        });
                return;
            }
            this.val$onResult.accept(result);
        }
    }

    public void getPowerMonitorReadings(
            List<PowerMonitor> powerMonitors,
            Executor executor,
            final OutcomeReceiver<PowerMonitorReadings, RuntimeException> onResult) {
        if (this.mPowerStats == null) {
            final IllegalArgumentException error =
                    new IllegalArgumentException("Unsupported power monitor");
            if (executor != null) {
                executor.execute(
                        new Runnable() { // from class:
                                         // android.os.health.SystemHealthManager$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                OutcomeReceiver.this.onError(error);
                            }
                        });
                return;
            } else {
                onResult.onError(error);
                return;
            }
        }
        PowerMonitor[] powerMonitorsArray =
                (PowerMonitor[]) powerMonitors.toArray(new PowerMonitor[powerMonitors.size()]);
        Arrays.sort(powerMonitorsArray, POWER_MONITOR_COMPARATOR);
        int[] indices = new int[powerMonitors.size()];
        for (int i = 0; i < powerMonitors.size(); i++) {
            indices[i] = powerMonitorsArray[i].index;
        }
        try {
            this.mPowerStats.getPowerMonitorReadings(
                    indices, new AnonymousClass2(null, powerMonitorsArray, executor, onResult));
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
        AnonymousClass2(
                Handler handler,
                PowerMonitor[] powerMonitorArr,
                Executor executor,
                OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$powerMonitorsArray = powerMonitorArr;
            this.val$executor = executor;
            this.val$onResult = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            final RuntimeException error;
            if (resultCode == 0) {
                final PowerMonitorReadings result =
                        new PowerMonitorReadings(
                                this.val$powerMonitorsArray,
                                resultData.getLongArray(IPowerStatsService.KEY_ENERGY),
                                resultData.getLongArray(IPowerStatsService.KEY_TIMESTAMPS));
                if (this.val$executor != null) {
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$onResult;
                    executor.execute(
                            new Runnable() { // from class:
                                             // android.os.health.SystemHealthManager$2$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    OutcomeReceiver.this.onResult(result);
                                }
                            });
                    return;
                }
                this.val$onResult.onResult(result);
                return;
            }
            if (resultCode == 1) {
                error = new IllegalArgumentException("Unsupported power monitor");
            } else {
                error = new IllegalStateException("Unrecognized result code " + resultCode);
            }
            if (this.val$executor != null) {
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$onResult;
                executor2.execute(
                        new Runnable() { // from class:
                                         // android.os.health.SystemHealthManager$2$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                OutcomeReceiver.this.onError(error);
                            }
                        });
                return;
            }
            this.val$onResult.onError(error);
        }
    }
}
