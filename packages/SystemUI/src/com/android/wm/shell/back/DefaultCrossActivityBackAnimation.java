package com.android.wm.shell.back;

import android.content.Context;
import android.graphics.RectF;
import android.os.Handler;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.systemui.R;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.back.CrossActivityBackAnimation;
import com.android.wm.shell.shared.animation.Interpolators;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DefaultCrossActivityBackAnimation extends CrossActivityBackAnimation {
    public final boolean allowEnteringYShift;
    public final float enteringStartOffset;
    public final Interpolator postCommitInterpolator;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public DefaultCrossActivityBackAnimation(Context context, BackAnimationBackground backAnimationBackground, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, Handler handler) {
        super(context, backAnimationBackground, rootTaskDisplayAreaOrganizer, new SurfaceControl.Transaction(), handler);
        this.postCommitInterpolator = Interpolators.EMPHASIZED;
        this.enteringStartOffset = context.getResources().getDimension(R.dimen.cross_activity_back_entering_start_offset);
        this.allowEnteringYShift = true;
    }

    @Override // com.android.wm.shell.back.CrossActivityBackAnimation
    public final boolean getAllowEnteringYShift() {
        return this.allowEnteringYShift;
    }

    @Override // com.android.wm.shell.back.CrossActivityBackAnimation
    public final long getPostCommitAnimationDuration() {
        return 450L;
    }

    @Override // com.android.wm.shell.back.CrossActivityBackAnimation
    public final void onGestureCommitted(float f) {
        this.startClosingRect.set(this.currentClosingRect);
        this.startEnteringRect.set(this.currentEnteringRect);
        this.targetEnteringRect.set(this.backAnimRect);
        this.targetClosingRect.set(this.backAnimRect);
        this.targetClosingRect.offset(this.currentClosingRect.left + this.enteringStartOffset, 0.0f);
        super.onGestureCommitted(f);
    }

    @Override // com.android.wm.shell.back.CrossActivityBackAnimation
    public final void onPostCommitProgress(float f) {
        super.onPostCommitProgress(f);
        float max = Math.max(1.0f - (5 * f), 0.0f);
        float interpolation = ((PathInterpolator) this.postCommitInterpolator).getInterpolation(f);
        CrossActivityBackAnimationKt.setInterpolatedRectF(this.currentClosingRect, this.startClosingRect, this.targetClosingRect, interpolation);
        RemoteAnimationTarget remoteAnimationTarget = this.closingTarget;
        SurfaceControl surfaceControl = remoteAnimationTarget != null ? remoteAnimationTarget.leash : null;
        RectF rectF = this.currentClosingRect;
        CrossActivityBackAnimation.FlingMode flingMode = CrossActivityBackAnimation.FlingMode.FLING_BOUNCE;
        CrossActivityBackAnimation.applyTransform$default(this, surfaceControl, rectF, max, null, flingMode, 8);
        CrossActivityBackAnimationKt.setInterpolatedRectF(this.currentEnteringRect, this.startEnteringRect, this.targetEnteringRect, interpolation);
        RemoteAnimationTarget remoteAnimationTarget2 = this.enteringTarget;
        CrossActivityBackAnimation.applyTransform$default(this, remoteAnimationTarget2 != null ? remoteAnimationTarget2.leash : null, this.currentEnteringRect, 1.0f, null, flingMode, 8);
        applyTransaction();
    }

    @Override // com.android.wm.shell.back.CrossActivityBackAnimation
    public final void preparePreCommitClosingRectMovement(int i) {
        this.startClosingRect.set(this.backAnimRect);
        this.targetClosingRect.set(this.startClosingRect);
        CrossActivityBackAnimationKt.scaleCentered$default(this.targetClosingRect, 0.9f);
        if (i != 1) {
            RectF rectF = this.targetClosingRect;
            rectF.offset((this.startClosingRect.right - rectF.right) - this.displayBoundsMargin, 0.0f);
        }
    }

    @Override // com.android.wm.shell.back.CrossActivityBackAnimation
    public final void preparePreCommitEnteringRectMovement() {
        this.startEnteringRect.set(this.startClosingRect);
        this.startEnteringRect.offset(-this.enteringStartOffset, 0.0f);
        this.targetEnteringRect.set(this.startEnteringRect);
        CrossActivityBackAnimationKt.scaleCentered$default(this.targetEnteringRect, 0.9f);
    }
}
