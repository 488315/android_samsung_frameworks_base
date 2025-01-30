package com.android.p038wm.shell.desktopmode;

import android.animation.ValueAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceControl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda2 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ SurfaceControl.Transaction f$0;
    public final /* synthetic */ SurfaceControl f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda2(Rect rect, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        this.f$0 = transaction;
        this.f$1 = surfaceControl;
        this.f$2 = rect;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.$r8$classId) {
            case 0:
                SurfaceControl.Transaction transaction = this.f$0;
                SurfaceControl surfaceControl = this.f$1;
                Rect rect = (Rect) this.f$2;
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                transaction.setScale(surfaceControl, floatValue, floatValue);
                float width = rect.width() * floatValue;
                float height = rect.height() * floatValue;
                transaction.setPosition(surfaceControl, rect.centerX() - ((int) (width / 2.0f)), rect.centerY() - ((int) (height / 2.0f)));
                transaction.apply();
                break;
            default:
                EnterDesktopTaskTransitionHandler enterDesktopTaskTransitionHandler = (EnterDesktopTaskTransitionHandler) this.f$2;
                SurfaceControl.Transaction transaction2 = this.f$0;
                SurfaceControl surfaceControl2 = this.f$1;
                enterDesktopTaskTransitionHandler.getClass();
                float floatValue2 = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                Point point = enterDesktopTaskTransitionHandler.mPosition;
                float f = 1.0f - floatValue2;
                transaction2.setPosition(surfaceControl2, point.x * f, point.y * f).setScale(surfaceControl2, floatValue2, floatValue2).show(surfaceControl2).apply();
                break;
        }
    }

    public /* synthetic */ EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda2(EnterDesktopTaskTransitionHandler enterDesktopTaskTransitionHandler, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        this.f$2 = enterDesktopTaskTransitionHandler;
        this.f$0 = transaction;
        this.f$1 = surfaceControl;
    }
}
