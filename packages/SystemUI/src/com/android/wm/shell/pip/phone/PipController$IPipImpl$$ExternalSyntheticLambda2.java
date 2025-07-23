package com.android.wm.shell.pip.phone;

import android.os.SystemClock;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.pip.phone.PipController;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipController$IPipImpl$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ PipController$IPipImpl$$ExternalSyntheticLambda2(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        PipController pipController = (PipController) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = PipController.IPipImpl.$r8$clinit;
                PipAnimationController pipAnimationController = pipController.mPipAnimationController;
                pipAnimationController.mOneShotAnimationType = 1;
                pipAnimationController.mLastOneShotAlphaAnimationTime = SystemClock.uptimeMillis();
                break;
            default:
                int i2 = PipController.IPipImpl.$r8$clinit;
                pipController.setPinnedStackAnimationListener(null);
                break;
        }
    }
}
