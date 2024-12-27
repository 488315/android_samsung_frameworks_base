package com.android.server.stats.pull;

import android.app.AppOpsManager;
import android.os.Bundle;
import android.os.SynchronousResultReceiver;
import android.uwb.UwbActivityEnergyInfo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public final /* synthetic */ class StatsPullAtomService$$ExternalSyntheticLambda11
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StatsPullAtomService$$ExternalSyntheticLambda11(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                int i2 = StatsPullAtomService.RANDOM_SEED;
                ((AtomicInteger) obj2).incrementAndGet();
                break;
            case 1:
                ((CompletableFuture) obj2).complete((AppOpsManager.HistoricalOps) obj);
                break;
            default:
                int i3 = StatsPullAtomService.RANDOM_SEED;
                Bundle bundle = new Bundle();
                bundle.putParcelable("controller_activity", (UwbActivityEnergyInfo) obj);
                ((SynchronousResultReceiver) obj2).send(0, bundle);
                break;
        }
    }
}
