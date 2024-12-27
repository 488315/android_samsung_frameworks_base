package com.android.server.power.stats;

import android.hardware.power.stats.EnergyConsumer;

import com.android.internal.os.PowerStats;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.powerstats.PowerStatsService;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class CustomEnergyConsumerPowerStatsCollector extends PowerStatsCollector {
    public static final EnergyConsumerPowerStatsLayout sLayout =
            new EnergyConsumerPowerStatsLayout();
    public List mCollectors;
    public final BatteryStatsImpl.PowerStatsCollectorInjector mInjector;

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public CustomEnergyConsumerPowerStatsCollector(
            com.android.server.power.stats.BatteryStatsImpl.PowerStatsCollectorInjector r8) {
        /*
            r7 = this;
            com.android.server.power.stats.BatteryStatsImpl r0 = com.android.server.power.stats.BatteryStatsImpl.this
            com.android.server.power.stats.BatteryStatsImpl$MyHandler r2 = r0.mHandler
            com.android.server.power.stats.PowerStatsUidResolver r5 = r0.mPowerStatsUidResolver
            com.android.internal.os.Clock r6 = r0.mClock
            r3 = 0
            r1 = r7
            r1.<init>(r2, r3, r5, r6)
            r7.mInjector = r8
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.stats.CustomEnergyConsumerPowerStatsCollector.<init>(com.android.server.power.stats.BatteryStatsImpl$PowerStatsCollectorInjector):void");
    }

    @Override // com.android.server.power.stats.PowerStatsCollector
    public final void collectAndDump(PrintWriter printWriter) {
        ensureInitialized();
        for (int i = 0; i < ((ArrayList) this.mCollectors).size(); i++) {
            ((EnergyConsumerPowerStatsCollector) ((ArrayList) this.mCollectors).get(i))
                    .collectAndDump(printWriter);
        }
    }

    public final void ensureInitialized() {
        String str;
        if (this.mCollectors != null) {
            return;
        }
        PowerStatsCollector.ConsumedEnergyRetrieverImpl consumedEnergyRetrieverImpl =
                this.mInjector.mConsumedEnergyRetriever;
        int[] energyConsumerIds = consumedEnergyRetrieverImpl.getEnergyConsumerIds(0, null);
        this.mCollectors = new ArrayList(energyConsumerIds.length);
        int i = 1000;
        int i2 = 0;
        while (i2 < energyConsumerIds.length) {
            int i3 = energyConsumerIds[i2];
            if (consumedEnergyRetrieverImpl.mEnergyConsumers == null) {
                PowerStatsService.LocalService localService =
                        consumedEnergyRetrieverImpl.mPowerStatsInternal;
                if (localService == null) {
                    consumedEnergyRetrieverImpl.mEnergyConsumers = new EnergyConsumer[0];
                } else {
                    EnergyConsumer[] energyConsumerInfo =
                            PowerStatsService.this.getPowerStatsHal().getEnergyConsumerInfo();
                    consumedEnergyRetrieverImpl.mEnergyConsumers = energyConsumerInfo;
                    if (energyConsumerInfo == null) {
                        consumedEnergyRetrieverImpl.mEnergyConsumers = new EnergyConsumer[0];
                    }
                }
            }
            EnergyConsumer[] energyConsumerArr = consumedEnergyRetrieverImpl.mEnergyConsumers;
            int length = energyConsumerArr.length;
            int i4 = 0;
            while (true) {
                if (i4 >= length) {
                    NandswapManager$$ExternalSyntheticOutline0.m(
                            i3, "Unsupported energy consumer ID ", "PowerStatsCollector");
                    str = "unsupported";
                    break;
                }
                EnergyConsumer energyConsumer = energyConsumerArr[i4];
                if (energyConsumer.id == i3) {
                    String str2 = energyConsumer.name;
                    if (str2 == null || str2.isBlank()) {
                        str2 = "CUSTOM_" + energyConsumer.id;
                    }
                    int length2 = str2.length();
                    StringBuilder sb = new StringBuilder(length2);
                    for (int i5 = 0; i5 < length2; i5++) {
                        char charAt = str2.charAt(i5);
                        if (Character.isWhitespace(charAt)) {
                            sb.append(' ');
                        } else if (Character.isISOControl(charAt)) {
                            sb.append('_');
                        } else {
                            sb.append(charAt);
                        }
                    }
                    str = sb.toString();
                } else {
                    i4++;
                }
            }
            EnergyConsumerPowerStatsCollector energyConsumerPowerStatsCollector =
                    new EnergyConsumerPowerStatsCollector(
                            this.mInjector, i, str, energyConsumerIds[i2], sLayout);
            energyConsumerPowerStatsCollector.mEnabled = true;
            energyConsumerPowerStatsCollector.addConsumer(
                    new Consumer() { // from class:
                                     // com.android.server.power.stats.CustomEnergyConsumerPowerStatsCollector$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            CustomEnergyConsumerPowerStatsCollector
                                    customEnergyConsumerPowerStatsCollector =
                                            CustomEnergyConsumerPowerStatsCollector.this;
                            PowerStats powerStats = (PowerStats) obj;
                            if (powerStats == null) {
                                customEnergyConsumerPowerStatsCollector.getClass();
                                return;
                            }
                            List list = customEnergyConsumerPowerStatsCollector.mConsumerList;
                            for (int size = list.size() - 1; size >= 0; size--) {
                                ((Consumer) list.get(size)).accept(powerStats);
                            }
                        }
                    });
            ((ArrayList) this.mCollectors).add(energyConsumerPowerStatsCollector);
            i2++;
            i++;
        }
    }

    @Override // com.android.server.power.stats.PowerStatsCollector
    public final boolean forceSchedule() {
        ensureInitialized();
        boolean z = false;
        for (int i = 0; i < ((ArrayList) this.mCollectors).size(); i++) {
            z |=
                    ((EnergyConsumerPowerStatsCollector) ((ArrayList) this.mCollectors).get(i))
                            .forceSchedule();
        }
        return z;
    }

    @Override // com.android.server.power.stats.PowerStatsCollector
    public final boolean schedule() {
        if (!this.mEnabled) {
            return false;
        }
        ensureInitialized();
        boolean z = false;
        for (int i = 0; i < ((ArrayList) this.mCollectors).size(); i++) {
            z |=
                    ((EnergyConsumerPowerStatsCollector) ((ArrayList) this.mCollectors).get(i))
                            .schedule();
        }
        return z;
    }
}
