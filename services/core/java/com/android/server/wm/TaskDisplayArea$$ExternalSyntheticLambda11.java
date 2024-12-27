package com.android.server.wm;

import java.util.function.Consumer;

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
