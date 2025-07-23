package com.android.wm.shell.splitscreen;

import android.animation.ValueAnimator;
import android.view.Choreographer;
import android.view.SurfaceControl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda10 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StageCoordinator f$0;
    public final /* synthetic */ SurfaceControl f$1;
    public final /* synthetic */ SurfaceControl.Transaction f$2;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda10(StageCoordinator stageCoordinator, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, int i) {
        this.$r8$classId = i;
        this.f$0 = stageCoordinator;
        this.f$1 = surfaceControl;
        this.f$2 = transaction;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.$r8$classId) {
            case 0:
                StageCoordinator stageCoordinator = this.f$0;
                SurfaceControl surfaceControl = this.f$1;
                SurfaceControl.Transaction transaction = this.f$2;
                if (surfaceControl != null) {
                    stageCoordinator.getClass();
                    if (surfaceControl.isValid()) {
                        transaction.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
                        transaction.setAlpha(surfaceControl, ((Float) valueAnimator.getAnimatedValue()).floatValue());
                        transaction.apply();
                        break;
                    }
                }
                stageCoordinator.mDividerFadeInAnimator.cancel();
                break;
            default:
                StageCoordinator stageCoordinator2 = this.f$0;
                SurfaceControl surfaceControl2 = this.f$1;
                SurfaceControl.Transaction transaction2 = this.f$2;
                if (surfaceControl2 != null) {
                    stageCoordinator2.getClass();
                    if (surfaceControl2.isValid()) {
                        transaction2.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
                        transaction2.setAlpha(surfaceControl2, ((Float) valueAnimator.getAnimatedValue()).floatValue());
                        transaction2.apply();
                        break;
                    }
                }
                stageCoordinator2.mCellDividerFadeInAnimator.cancel();
                break;
        }
    }
}
