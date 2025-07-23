package com.android.wm.shell.pip2.phone;

import android.os.Bundle;
import com.android.wm.shell.common.pip.PipPerfHintController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipMotionHelper$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PipMotionHelper f$0;

    public /* synthetic */ PipMotionHelper$$ExternalSyntheticLambda2(PipMotionHelper pipMotionHelper, int i) {
        this.$r8$classId = i;
        this.f$0 = pipMotionHelper;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        PipMotionHelper pipMotionHelper = this.f$0;
        switch (i) {
            case 0:
                if (!pipMotionHelper.mDismissalPending && !pipMotionHelper.mSpringingToTouch && !pipMotionHelper.mMagnetizedPip.getObjectStuckToTarget()) {
                    pipMotionHelper.setAnimatingToBounds(pipMotionHelper.mPipBoundsState.mMotionBoundsState.mBoundsInMotion);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("fling_bounds_change", true);
                    pipMotionHelper.mPipTransitionState.setState(4, bundle);
                    break;
                } else {
                    pipMotionHelper.settlePipBoundsAfterPhysicsAnimation(true);
                    PipPerfHintController.PipHighPerfSession pipHighPerfSession = pipMotionHelper.mPipHighPerfSession;
                    if (pipHighPerfSession != null) {
                        pipHighPerfSession.close();
                        pipMotionHelper.mPipHighPerfSession = null;
                        break;
                    }
                }
                break;
            default:
                pipMotionHelper.dismissPip(false);
                break;
        }
    }
}
