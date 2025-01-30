package com.android.p038wm.shell.pip.phone;

import android.os.SystemClock;
import com.android.p038wm.shell.pip.PipAnimationController;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PipController$IPipImpl$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ PipController$IPipImpl$$ExternalSyntheticLambda6(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                PipAnimationController pipAnimationController = ((PipController) obj).mPipAnimationController;
                pipAnimationController.mOneShotAnimationType = 1;
                pipAnimationController.mLastOneShotAlphaAnimationTime = SystemClock.uptimeMillis();
                break;
            default:
                ((PipController) obj).setPinnedStackAnimationListener(null);
                break;
        }
    }
}
