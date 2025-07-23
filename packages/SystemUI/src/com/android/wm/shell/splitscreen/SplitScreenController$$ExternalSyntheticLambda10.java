package com.android.wm.shell.splitscreen;

import com.android.wm.shell.splitscreen.SplitScreenController;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenController$$ExternalSyntheticLambda10 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ SplitScreenController$$ExternalSyntheticLambda10(boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        boolean z = this.f$0;
        switch (i) {
            case 0:
                ((SplitScreenController.SplitTwoFingerGestureStarter) obj).mEnabled = z;
                break;
            default:
                int i2 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                ((SplitScreenController) obj).mStageCoordinator.mExitSplitScreenOnHide = z;
                break;
        }
    }
}
