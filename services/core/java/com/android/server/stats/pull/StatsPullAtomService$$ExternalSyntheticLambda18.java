package com.android.server.stats.pull;

import android.app.ProcessMemoryState;
import android.util.SparseArray;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class StatsPullAtomService$$ExternalSyntheticLambda18 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SparseArray f$0;

    public /* synthetic */ StatsPullAtomService$$ExternalSyntheticLambda18(int i, SparseArray sparseArray) {
        this.$r8$classId = i;
        this.f$0 = sparseArray;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        SparseArray sparseArray = this.f$0;
        ProcessMemoryState processMemoryState = (ProcessMemoryState) obj;
        switch (i) {
            case 0:
                int i2 = StatsPullAtomService.RANDOM_SEED;
                sparseArray.delete(processMemoryState.pid);
                break;
            default:
                int i3 = StatsPullAtomService.RANDOM_SEED;
                sparseArray.delete(processMemoryState.pid);
                break;
        }
    }
}
