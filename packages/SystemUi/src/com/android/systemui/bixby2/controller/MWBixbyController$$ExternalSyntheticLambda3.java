package com.android.systemui.bixby2.controller;

import android.app.ActivityManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class MWBixbyController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MWBixbyController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ MWBixbyController$$ExternalSyntheticLambda3(MWBixbyController mWBixbyController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = mWBixbyController;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$startTargetTaskToFreeform$2((ActivityManager.RunningTaskInfo) this.f$1);
                break;
            default:
                this.f$0.lambda$getMainStagePositionExt$6((int[]) this.f$1);
                break;
        }
    }
}
