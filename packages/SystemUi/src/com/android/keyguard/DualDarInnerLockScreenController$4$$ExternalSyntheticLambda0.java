package com.android.keyguard;

import com.android.keyguard.DualDarInnerLockScreenController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class DualDarInnerLockScreenController$4$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DualDarInnerLockScreenController.C06504 f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ DualDarInnerLockScreenController$4$$ExternalSyntheticLambda0(DualDarInnerLockScreenController.C06504 c06504, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = c06504;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DualDarInnerLockScreenController.C06504 c06504 = this.f$0;
                DualDarInnerLockScreenController.m351$$Nest$mdismissInnerLockScreen(DualDarInnerLockScreenController.this, this.f$1);
                break;
            default:
                DualDarInnerLockScreenController.C06504 c065042 = this.f$0;
                DualDarInnerLockScreenController.m351$$Nest$mdismissInnerLockScreen(DualDarInnerLockScreenController.this, this.f$1);
                break;
        }
    }
}
