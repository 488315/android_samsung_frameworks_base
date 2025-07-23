package com.android.wm.shell.transition;

import android.R;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.RotationUtils;
import android.util.Slog;
import android.view.SurfaceControl;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.transition.DefaultSurfaceAnimator;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ScreenRotationAnimation {
    public final int mAnimHint;
    public final SurfaceControl mAnimLeash;
    public final SurfaceControl mBackColorSurface;
    public final SurfaceControl mBackEffectSurface;
    public final Context mContext;
    public final int mEndHeight;
    public final int mEndRotation;
    public final int mEndWidth;
    public boolean mFadeInOutAnimationNeeded;
    public SurfaceControl.Transaction mFinishTransaction;
    public Animation mRotateAlphaAnimation;
    public Animation mRotateEnterAnimation;
    public Animation mRotateExitAnimation;
    public final SurfaceControl mScreenshotLayer;
    public final int mStartHeight;
    public final float mStartLuma;
    public final int mStartRotation;
    public final int mStartWidth;
    public final SurfaceControl mSurfaceControl;
    public final float[] mTmpFloats = new float[9];
    public final TransactionPool mTransactionPool;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LumaAnimation extends Animation {
        public LumaAnimation(long j) {
            setDuration(j);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LumaAnimationAdapter extends DefaultSurfaceAnimator.AnimationAdapter {
        public final float[] mColorArray;
        public final float mEndLuma;
        public final AccelerateInterpolator mInterpolation;
        public final float mStartLuma;

        public LumaAnimationAdapter(SurfaceControl surfaceControl, float f, float f2) {
            super(surfaceControl);
            this.mColorArray = new float[3];
            this.mStartLuma = f;
            this.mEndLuma = f2;
            float min = Math.min(3.0f, (Math.max(0.5f, f) - 0.5f) * 10.0f);
            Slog.d("ShellTransitions", "Luma=" + f + " factor=" + min);
            this.mInterpolation = min > 0.5f ? new AccelerateInterpolator(min) : null;
        }

        @Override // com.android.wm.shell.transition.DefaultSurfaceAnimator.AnimationAdapter
        public final void applyTransformation(long j, ValueAnimator valueAnimator) {
            AccelerateInterpolator accelerateInterpolator = this.mInterpolation;
            float interpolation = accelerateInterpolator != null ? accelerateInterpolator.getInterpolation(valueAnimator.getAnimatedFraction()) : valueAnimator.getAnimatedFraction();
            float f = this.mStartLuma;
            float m$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(this.mEndLuma, f, interpolation, f);
            float[] fArr = this.mColorArray;
            fArr[0] = m$1;
            fArr[1] = m$1;
            fArr[2] = m$1;
            this.mTransaction.setColor(this.mLeash, fArr);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0235  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01ec A[Catch: OutOfResourcesException -> 0x009e, TRY_LEAVE, TryCatch #0 {OutOfResourcesException -> 0x009e, blocks: (B:3:0x007f, B:5:0x0087, B:7:0x0157, B:9:0x015b, B:10:0x0199, B:13:0x01a7, B:15:0x01b6, B:17:0x01ba, B:19:0x01c2, B:23:0x01ce, B:64:0x01ec, B:66:0x00a1, B:68:0x00d4, B:70:0x00da, B:73:0x0101, B:75:0x0123, B:77:0x0127, B:79:0x012f, B:84:0x0152, B:93:0x0148), top: B:2:0x007f }] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0148 A[Catch: OutOfResourcesException -> 0x009e, TryCatch #0 {OutOfResourcesException -> 0x009e, blocks: (B:3:0x007f, B:5:0x0087, B:7:0x0157, B:9:0x015b, B:10:0x0199, B:13:0x01a7, B:15:0x01b6, B:17:0x01ba, B:19:0x01c2, B:23:0x01ce, B:64:0x01ec, B:66:0x00a1, B:68:0x00d4, B:70:0x00da, B:73:0x0101, B:75:0x0123, B:77:0x0127, B:79:0x012f, B:84:0x0152, B:93:0x0148), top: B:2:0x007f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ScreenRotationAnimation(android.content.Context r22, com.android.wm.shell.shared.TransactionPool r23, android.view.SurfaceControl.Transaction r24, android.window.TransitionInfo.Change r25, android.view.SurfaceControl r26, int r27, int r28) {
        /*
            Method dump skipped, instructions count: 712
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.transition.ScreenRotationAnimation.<init>(android.content.Context, com.android.wm.shell.shared.TransactionPool, android.view.SurfaceControl$Transaction, android.window.TransitionInfo$Change, android.view.SurfaceControl, int, int):void");
    }

    public final boolean buildAnimation(ArrayList arrayList, DefaultTransitionHandler$$ExternalSyntheticLambda7 defaultTransitionHandler$$ExternalSyntheticLambda7, float f, ShellExecutor shellExecutor, int i, int i2) {
        if (this.mScreenshotLayer == null) {
            return false;
        }
        int i3 = this.mAnimHint;
        boolean z = i3 == 1 || i3 == 2;
        if (z) {
            this.mRotateExitAnimation = AnimationUtils.loadAnimation(this.mContext, i3 == 2 ? R.anim.task_fragment_clear_top_close_exit : R.anim.task_fragment_clear_top_open_enter);
            this.mRotateEnterAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.task_fragment_clear_top_close_enter);
            this.mRotateAlphaAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.ft_avd_tooverflow_rectangle_3_pivot_animation);
        } else if ((!CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION && !CoreRune.FW_SHELL_TRANSITION_DISPLAY_CHANGE) || i2 == -1 || i == -1) {
            int deltaRotation = this.mFadeInOutAnimationNeeded ? 0 : RotationUtils.deltaRotation(this.mEndRotation, this.mStartRotation);
            if (deltaRotation == 0) {
                this.mRotateExitAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.ft_avd_tooverflow_rectangle_1_pivot_animation);
                this.mRotateEnterAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.task_fragment_clear_top_close_enter);
            } else if (deltaRotation == 1) {
                this.mRotateExitAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.ic_bluetooth_transient_animation_0);
                this.mRotateEnterAnimation = AnimationUtils.loadAnimation(this.mContext, CoreRune.FW_SHELL_TRANSITION ? R.anim.voice_activity_open_exit : R.anim.grow_fade_in_from_bottom);
            } else if (deltaRotation == 2) {
                this.mRotateExitAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.ft_avd_tooverflow_rectangle_2_pivot_animation);
                this.mRotateEnterAnimation = AnimationUtils.loadAnimation(this.mContext, CoreRune.FW_SHELL_TRANSITION ? R.anim.voice_activity_close_exit : R.anim.ft_avd_tooverflow_rectangle_2_animation);
            } else if (deltaRotation == 3) {
                this.mRotateExitAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.grow_fade_in_center);
                this.mRotateEnterAnimation = AnimationUtils.loadAnimation(this.mContext, CoreRune.FW_SHELL_TRANSITION ? R.anim.voice_activity_open_enter : R.anim.grow_fade_in);
            }
        } else {
            this.mRotateExitAnimation = AnimationUtils.loadAnimation(this.mContext, i);
            this.mRotateEnterAnimation = AnimationUtils.loadAnimation(this.mContext, i2);
        }
        Animation animation = this.mRotateExitAnimation;
        int i4 = this.mEndWidth;
        int i5 = this.mEndHeight;
        int i6 = this.mStartWidth;
        int i7 = this.mStartHeight;
        animation.initialize(i4, i5, i6, i7);
        this.mRotateExitAnimation.restrictDuration(10000L);
        this.mRotateExitAnimation.scaleCurrentDuration(f);
        this.mRotateEnterAnimation.initialize(i4, i5, i6, i7);
        this.mRotateEnterAnimation.restrictDuration(10000L);
        this.mRotateEnterAnimation.scaleCurrentDuration(f);
        if (z) {
            this.mRotateAlphaAnimation.initialize(i4, i5, i6, i7);
            this.mRotateAlphaAnimation.restrictDuration(10000L);
            this.mRotateAlphaAnimation.scaleCurrentDuration(f);
            DefaultSurfaceAnimator.buildSurfaceAnimation(arrayList, this.mRotateAlphaAnimation, this.mAnimLeash, defaultTransitionHandler$$ExternalSyntheticLambda7, this.mTransactionPool, shellExecutor, null, 0.0f, null, null);
            Animation animation2 = this.mRotateEnterAnimation;
            SurfaceControl surfaceControl = this.mBackEffectSurface;
            if (surfaceControl == null) {
                surfaceControl = this.mSurfaceControl;
            }
            DefaultSurfaceAnimator.buildSurfaceAnimation(arrayList, animation2, surfaceControl, defaultTransitionHandler$$ExternalSyntheticLambda7, this.mTransactionPool, shellExecutor, null, 0.0f, null, null);
            return true;
        }
        Animation animation3 = this.mRotateEnterAnimation;
        SurfaceControl surfaceControl2 = this.mBackEffectSurface;
        if (surfaceControl2 == null) {
            surfaceControl2 = this.mSurfaceControl;
        }
        DefaultSurfaceAnimator.buildSurfaceAnimation(arrayList, animation3, surfaceControl2, defaultTransitionHandler$$ExternalSyntheticLambda7, this.mTransactionPool, shellExecutor, null, 0.0f, null, null);
        DefaultSurfaceAnimator.buildSurfaceAnimation(arrayList, this.mRotateExitAnimation, this.mAnimLeash, defaultTransitionHandler$$ExternalSyntheticLambda7, this.mTransactionPool, shellExecutor, null, 0.0f, null, null);
        SurfaceControl surfaceControl3 = this.mBackEffectSurface;
        if (surfaceControl3 != null) {
            float f2 = this.mStartLuma;
            if (f2 > 0.1f) {
                long integer = (long) (this.mContext.getResources().getInteger(R.integer.device_idle_min_deep_maintenance_time_ms) * f);
                LumaAnimation lumaAnimation = new LumaAnimation(integer);
                lumaAnimation.setStartOffset(this.mRotateEnterAnimation.getDuration() - integer);
                DefaultSurfaceAnimator.buildSurfaceAnimation(arrayList, lumaAnimation, defaultTransitionHandler$$ExternalSyntheticLambda7, this.mTransactionPool, shellExecutor, new LumaAnimationAdapter(surfaceControl3, f2, 0.0f));
            }
        }
        return true;
    }
}
