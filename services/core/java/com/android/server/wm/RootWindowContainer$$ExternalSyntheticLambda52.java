package com.android.server.wm;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public final /* synthetic */ class RootWindowContainer$$ExternalSyntheticLambda52
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ AtomicReference f$2;

    public /* synthetic */ RootWindowContainer$$ExternalSyntheticLambda52(
            String str, int i, AtomicReference atomicReference, int i2) {
        this.$r8$classId = i2;
        this.f$0 = str;
        this.f$1 = i;
        this.f$2 = atomicReference;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                String str = this.f$0;
                int i = this.f$1;
                AtomicReference atomicReference = this.f$2;
                ActivityRecord activityRecord = (ActivityRecord) obj;
                if (activityRecord.packageName.equals(str)
                        && activityRecord.mUserId == i
                        && !activityRecord.isState(ActivityRecord.State.FINISHING)) {
                    atomicReference.set(activityRecord);
                    break;
                }
                break;
            default:
                String str2 = this.f$0;
                int i2 = this.f$1;
                AtomicReference atomicReference2 = this.f$2;
                ((Task) obj)
                        .forAllActivities(
                                new RootWindowContainer$$ExternalSyntheticLambda52(
                                        str2, i2, atomicReference2, 0));
                break;
        }
    }
}
