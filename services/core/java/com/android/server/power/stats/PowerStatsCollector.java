package com.android.server.power.stats;

import android.hardware.power.stats.EnergyConsumer;
import android.hardware.power.stats.EnergyConsumerResult;
import android.os.ConditionVariable;
import android.os.Handler;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import com.android.internal.os.Clock;
import com.android.internal.os.PowerStats;
import com.android.server.power.stats.BatteryStatsImpl;
import com.android.server.power.stats.PowerStatsCollector;
import com.android.server.power.stats.PowerStatsUidResolver;
import com.android.server.powerstats.PowerStatsService;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public abstract class PowerStatsCollector {
    public final Clock mClock;
    public boolean mEnabled;
    public final Handler mHandler;
    public final long mThrottlePeriodMs;
    public final PowerStatsUidResolver mUidResolver;
    public final PowerStatsCollector$$ExternalSyntheticLambda0 mCollectAndDeliverStats = new Runnable() { // from class: com.android.server.power.stats.PowerStatsCollector$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            PowerStatsCollector powerStatsCollector = PowerStatsCollector.this;
            PowerStats collectStats = powerStatsCollector.collectStats();
            if (collectStats == null) {
                return;
            }
            List list = powerStatsCollector.mConsumerList;
            for (int size = list.size() - 1; size >= 0; size--) {
                ((Consumer) list.get(size)).accept(collectStats);
            }
        }
    };
    public long mLastScheduledUpdateMs = -1;
    public volatile List mConsumerList = Collections.emptyList();

    /* renamed from: com.android.server.power.stats.PowerStatsCollector$1, reason: invalid class name */
    public final class AnonymousClass1 implements PowerStatsUidResolver.Listener {
        public AnonymousClass1() {
        }

        @Override // com.android.server.power.stats.PowerStatsUidResolver.Listener
        public final void onAfterIsolatedUidRemoved(final int i, int i2) {
            PowerStatsCollector.this.mHandler.post(new Runnable() { // from class: com.android.server.power.stats.PowerStatsCollector$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PowerStatsCollector.AnonymousClass1 anonymousClass1 = PowerStatsCollector.AnonymousClass1.this;
                    PowerStatsCollector.this.onUidRemoved(i);
                }
            });
        }

        @Override // com.android.server.power.stats.PowerStatsUidResolver.Listener
        public final void onBeforeIsolatedUidRemoved(int i) {
        }

        @Override // com.android.server.power.stats.PowerStatsUidResolver.Listener
        public final void onIsolatedUidAdded(int i, int i2) {
        }
    }

    public final class ConsumedEnergyRetrieverImpl {
        public EnergyConsumer[] mEnergyConsumers;
        public final PowerStatsService.LocalService mPowerStatsInternal;

        public ConsumedEnergyRetrieverImpl(PowerStatsService.LocalService localService) {
            this.mPowerStatsInternal = localService;
        }

        public final EnergyConsumerResult[] getConsumedEnergy(int[] iArr) {
            try {
                return (EnergyConsumerResult[]) this.mPowerStatsInternal.getEnergyConsumedAsync(iArr).get(20000L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                Slog.e("PowerStatsCollector", "Could not obtain energy consumers from PowerStatsService", e);
                return null;
            }
        }

        public final long[] getConsumedEnergyUws(int[] iArr) {
            EnergyConsumerResult[] consumedEnergy = getConsumedEnergy(iArr);
            if (consumedEnergy == null) {
                return null;
            }
            long[] jArr = new long[iArr.length];
            for (int i = 0; i < iArr.length; i++) {
                int i2 = iArr[i];
                int length = consumedEnergy.length;
                int i3 = 0;
                while (true) {
                    if (i3 < length) {
                        EnergyConsumerResult energyConsumerResult = consumedEnergy[i3];
                        if (energyConsumerResult.id == i2) {
                            jArr[i] = energyConsumerResult.energyUWs;
                            break;
                        }
                        i3++;
                    }
                }
            }
            return jArr;
        }

        public final int[] getEnergyConsumerIds(int i, String str) {
            if (this.mEnergyConsumers == null) {
                PowerStatsService.LocalService localService = this.mPowerStatsInternal;
                if (localService == null) {
                    this.mEnergyConsumers = new EnergyConsumer[0];
                } else {
                    EnergyConsumer[] energyConsumerInfo = PowerStatsService.this.getPowerStatsHal().getEnergyConsumerInfo();
                    this.mEnergyConsumers = energyConsumerInfo;
                    if (energyConsumerInfo == null) {
                        this.mEnergyConsumers = new EnergyConsumer[0];
                    }
                }
            }
            if (this.mEnergyConsumers.length == 0) {
                return new int[0];
            }
            ArrayList arrayList = new ArrayList();
            for (EnergyConsumer energyConsumer : this.mEnergyConsumers) {
                if (energyConsumer.type == i && (str == null || str.equals(energyConsumer.name))) {
                    arrayList.add(energyConsumer);
                }
            }
            if (arrayList.isEmpty()) {
                return new int[0];
            }
            arrayList.sort(Comparator.comparing(new PowerStatsCollector$ConsumedEnergyRetrieverImpl$$ExternalSyntheticLambda0()));
            int size = arrayList.size();
            int[] iArr = new int[size];
            for (int i2 = 0; i2 < size; i2++) {
                iArr[i2] = ((EnergyConsumer) arrayList.get(i2)).id;
            }
            return iArr;
        }
    }

    public PowerStatsCollector(BatteryStatsImpl.MyHandler myHandler, long j, PowerStatsUidResolver powerStatsUidResolver, Clock clock) {
        this.mHandler = myHandler;
        this.mThrottlePeriodMs = j;
        this.mUidResolver = powerStatsUidResolver;
        powerStatsUidResolver.addListener(new AnonymousClass1());
        this.mClock = clock;
    }

    public static long uJtoUc(int i, long j) {
        return ((j * 1000) + (i / 2)) / i;
    }

    public final void addConsumer(Consumer consumer) {
        synchronized (this) {
            try {
                if (this.mConsumerList.contains(consumer)) {
                    return;
                }
                ArrayList arrayList = new ArrayList(this.mConsumerList);
                arrayList.add(consumer);
                this.mConsumerList = Collections.unmodifiableList(arrayList);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void collectAndDump(PrintWriter printWriter) {
        Thread currentThread = Thread.currentThread();
        Handler handler = this.mHandler;
        if (currentThread == handler.getLooper().getThread()) {
            throw new RuntimeException("Calling this method from the handler thread would cause a deadlock");
        }
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        if (!this.mEnabled) {
            indentingPrintWriter.print(getClass().getSimpleName());
            indentingPrintWriter.println(": disabled");
            return;
        }
        final ArrayList arrayList = new ArrayList();
        ?? r2 = new Consumer() { // from class: com.android.server.power.stats.PowerStatsCollector$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.add((PowerStats) obj);
            }
        };
        addConsumer(r2);
        try {
            if (forceSchedule()) {
                ConditionVariable conditionVariable = new ConditionVariable();
                handler.post(new BatteryStatsImpl$$ExternalSyntheticLambda3(2, conditionVariable));
                conditionVariable.block();
            }
            removeConsumer(r2);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((PowerStats) it.next()).dump(indentingPrintWriter);
            }
        } catch (Throwable th) {
            removeConsumer(r2);
            throw th;
        }
    }

    public PowerStats collectStats() {
        return null;
    }

    public boolean forceSchedule() {
        if (!this.mEnabled) {
            return false;
        }
        Handler handler = this.mHandler;
        PowerStatsCollector$$ExternalSyntheticLambda0 powerStatsCollector$$ExternalSyntheticLambda0 = this.mCollectAndDeliverStats;
        handler.removeCallbacks(powerStatsCollector$$ExternalSyntheticLambda0);
        handler.postAtFrontOfQueue(powerStatsCollector$$ExternalSyntheticLambda0);
        return true;
    }

    public void onUidRemoved(int i) {
    }

    public final void removeConsumer(PowerStatsCollector$$ExternalSyntheticLambda1 powerStatsCollector$$ExternalSyntheticLambda1) {
        synchronized (this) {
            ArrayList arrayList = new ArrayList(this.mConsumerList);
            arrayList.remove(powerStatsCollector$$ExternalSyntheticLambda1);
            this.mConsumerList = Collections.unmodifiableList(arrayList);
        }
    }

    public boolean schedule() {
        if (!this.mEnabled) {
            return false;
        }
        long uptimeMillis = this.mClock.uptimeMillis();
        long j = this.mLastScheduledUpdateMs;
        if (uptimeMillis - j < this.mThrottlePeriodMs && j >= 0) {
            return false;
        }
        this.mLastScheduledUpdateMs = uptimeMillis;
        this.mHandler.post(this.mCollectAndDeliverStats);
        return true;
    }
}
