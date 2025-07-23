package com.android.systemui.accessibility;

import com.android.systemui.accessibility.FullscreenMagnificationController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class FullscreenMagnificationController$3$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                FullscreenMagnificationController.AnonymousClass3 anonymousClass3 = (FullscreenMagnificationController.AnonymousClass3) obj;
                if (anonymousClass3.this$0.getState() == 2) {
                    anonymousClass3.this$0.setState(3);
                    break;
                }
                break;
            default:
                FullscreenMagnificationController.AnonymousClass4 anonymousClass4 = (FullscreenMagnificationController.AnonymousClass4) obj;
                if (anonymousClass4.this$0.getState() == 1) {
                    anonymousClass4.this$0.cleanUpBorder();
                    break;
                }
                break;
        }
    }
}
