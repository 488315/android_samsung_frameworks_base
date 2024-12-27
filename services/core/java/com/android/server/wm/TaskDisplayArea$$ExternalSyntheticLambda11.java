package com.android.server.wm;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class TaskDisplayArea$$ExternalSyntheticLambda11 implements Consumer {
    public final /* synthetic */ ActivityRecord f$0;
    public final /* synthetic */ int[] f$1;

    public /* synthetic */ TaskDisplayArea$$ExternalSyntheticLambda11(
            ActivityRecord activityRecord, int[] iArr) {
        this.f$0 = activityRecord;
        this.f$1 = iArr;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ActivityRecord activityRecord = this.f$0;
        int[] iArr = this.f$1;
        if (((Task) obj).pauseActivityIfNeeded(activityRecord, "pauseBackTasks")) {
            iArr[0] = iArr[0] + 1;
        }
    }
}
