package com.android.keyguard;

import com.android.keyguard.DualDarInnerLockScreenController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class DualDarInnerLockScreenController$4$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DualDarInnerLockScreenController.AnonymousClass4 f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ DualDarInnerLockScreenController$4$$ExternalSyntheticLambda0(DualDarInnerLockScreenController.AnonymousClass4 anonymousClass4, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = anonymousClass4;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DualDarInnerLockScreenController.AnonymousClass4 anonymousClass4 = this.f$0;
                DualDarInnerLockScreenController.m946$$Nest$mdismissInnerLockScreen(DualDarInnerLockScreenController.this, this.f$1);
                break;
            default:
                DualDarInnerLockScreenController.AnonymousClass4 anonymousClass42 = this.f$0;
                DualDarInnerLockScreenController.m946$$Nest$mdismissInnerLockScreen(DualDarInnerLockScreenController.this, this.f$1);
                break;
        }
    }
}
