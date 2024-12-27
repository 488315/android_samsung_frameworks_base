package com.android.server.powerstats;

import android.hardware.power.stats.EnergyConsumer;
import android.hardware.power.stats.EnergyConsumerResult;
import android.util.Slog;

import java.util.concurrent.CompletableFuture;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PowerStatsService$LocalService$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PowerStatsService.LocalService f$0;
    public final /* synthetic */ CompletableFuture f$1;
    public final /* synthetic */ int[] f$2;

    public /* synthetic */ PowerStatsService$LocalService$$ExternalSyntheticLambda0(
            PowerStatsService.LocalService localService,
            CompletableFuture completableFuture,
            int[] iArr,
            int i) {
        this.$r8$classId = i;
        this.f$0 = localService;
        this.f$1 = completableFuture;
        this.f$2 = iArr;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PowerStatsService.LocalService localService = this.f$0;
                CompletableFuture completableFuture = this.f$1;
                int[] iArr = this.f$2;
                PowerStatsService powerStatsService = localService.this$0;
                EnergyConsumerResult[] energyConsumed =
                        powerStatsService.getPowerStatsHal().getEnergyConsumed(iArr);
                EnergyConsumer[] energyConsumerInfo = powerStatsService.getEnergyConsumerInfo();
                if (energyConsumerInfo != null) {
                    int length = iArr.length == 0 ? energyConsumerInfo.length : iArr.length;
                    if (energyConsumed == null || length != energyConsumed.length) {
                        StringBuilder sb = new StringBuilder("Requested ids:");
                        if (iArr.length == 0) {
                            sb.append("ALL");
                        }
                        sb.append("[");
                        for (int i = 0; i < iArr.length; i++) {
                            int i2 = iArr[i];
                            sb.append(i2);
                            sb.append("(type:");
                            sb.append((int) energyConsumerInfo[i2].type);
                            sb.append(",ord:");
                            sb.append(energyConsumerInfo[i2].ordinal);
                            sb.append(",name:");
                            sb.append(energyConsumerInfo[i2].name);
                            sb.append(")");
                            if (i != length - 1) {
                                sb.append(", ");
                            }
                        }
                        sb.append("], Received result ids:");
                        if (energyConsumed == null) {
                            sb.append("null");
                        } else {
                            sb.append("[");
                            int length2 = energyConsumed.length;
                            for (int i3 = 0; i3 < length2; i3++) {
                                int i4 = energyConsumed[i3].id;
                                sb.append(i4);
                                sb.append("(type:");
                                sb.append((int) energyConsumerInfo[i4].type);
                                sb.append(",ord:");
                                sb.append(energyConsumerInfo[i4].ordinal);
                                sb.append(",name:");
                                sb.append(energyConsumerInfo[i4].name);
                                sb.append(")");
                                if (i3 != length2 - 1) {
                                    sb.append(", ");
                                }
                            }
                            sb.append("]");
                        }
                        Slog.wtf(
                                "PowerStatsService",
                                "Missing result from getEnergyConsumedAsync call. "
                                        + ((Object) sb));
                    }
                }
                completableFuture.complete(energyConsumed);
                break;
            case 1:
                this.f$1.complete(this.f$0.this$0.getPowerStatsHal().readEnergyMeter(this.f$2));
                break;
            default:
                this.f$1.complete(this.f$0.this$0.getPowerStatsHal().getStateResidency(this.f$2));
                break;
        }
    }
}
