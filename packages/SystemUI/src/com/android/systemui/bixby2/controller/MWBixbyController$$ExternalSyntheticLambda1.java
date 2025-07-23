package com.android.systemui.bixby2.controller;

import android.app.ActivityManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class MWBixbyController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MWBixbyController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ MWBixbyController$$ExternalSyntheticLambda1(MWBixbyController mWBixbyController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = mWBixbyController;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$getMainStagePositionExt$6((int[]) this.f$1);
                break;
            default:
                this.f$0.lambda$startTargetTaskToFreeform$2((ActivityManager.RunningTaskInfo) this.f$1);
                break;
        }
    }
}
