package com.android.wm.shell.pip2.phone;

import android.view.SurfaceControl;
import com.android.wm.shell.pip2.PipSurfaceTransactionHelper;
import com.android.wm.shell.pip2.animation.PipEnterAnimator;

/* loaded from: classes3.dex */
public final /* synthetic */ class PipTransition$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PipTransition$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((PipTransition) obj).finishTransition();
                break;
            case 1:
                SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                transaction.remove((SurfaceControl) obj);
                transaction.apply();
                break;
            default:
                PipEnterAnimator pipEnterAnimator = (PipEnterAnimator) obj;
                if (pipEnterAnimator.mContentOverlay != null) {
                    pipEnterAnimator.mContentOverlay.detach(((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) pipEnterAnimator.mSurfaceControlTransactionFactory).getTransaction());
                    pipEnterAnimator.mContentOverlay = null;
                    break;
                }
                break;
        }
    }
}
