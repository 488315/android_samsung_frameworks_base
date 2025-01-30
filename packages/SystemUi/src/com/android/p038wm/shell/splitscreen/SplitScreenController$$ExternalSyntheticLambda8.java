package com.android.p038wm.shell.splitscreen;

import com.android.p038wm.shell.splitscreen.SplitScreenController;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SplitScreenController$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ SplitScreenController$$ExternalSyntheticLambda8(boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((SplitScreenController.SplitTwoFingerGestureStarter) obj).mEnabled = this.f$0;
                break;
            default:
                boolean z = this.f$0;
                int i = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                ((SplitScreenController) obj).mStageCoordinator.mExitSplitScreenOnHide = z;
                break;
        }
    }
}
