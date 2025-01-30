package com.android.wm.shell.splitscreen;

import android.animation.ValueAnimator;
import android.view.Choreographer;
import android.view.SurfaceControl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda10 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ SurfaceControl f$1;
    public final /* synthetic */ SurfaceControl.Transaction f$2;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda10(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, SurfaceControl surfaceControl2) {
        this.$r8$classId = 2;
        this.f$2 = transaction;
        this.f$1 = surfaceControl;
        this.f$0 = surfaceControl2;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.$r8$classId) {
            case 0:
                StageCoordinator stageCoordinator = (StageCoordinator) this.f$0;
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
            case 1:
                StageCoordinator stageCoordinator2 = (StageCoordinator) this.f$0;
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
            default:
                SurfaceControl.Transaction transaction3 = this.f$2;
                SurfaceControl surfaceControl3 = this.f$1;
                SurfaceControl surfaceControl4 = (SurfaceControl) this.f$0;
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                transaction3.setAlpha(surfaceControl3, floatValue);
                transaction3.setAlpha(surfaceControl4, floatValue);
                transaction3.apply();
                break;
        }
    }

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda10(StageCoordinator stageCoordinator, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, int i) {
        this.$r8$classId = i;
        this.f$0 = stageCoordinator;
        this.f$1 = surfaceControl;
        this.f$2 = transaction;
    }
}
