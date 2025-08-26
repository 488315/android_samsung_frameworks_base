package com.android.wm.shell.transition;

import android.R;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.util.RotationUtils;
import android.util.Slog;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.window.ScreenCapture;
import android.window.TransitionInfo;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.internal.policy.TransitionAnimation;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.transition.DefaultSurfaceAnimator;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;

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

    public class LumaAnimation extends Animation {
        public LumaAnimation(long j) {
            setDuration(j);
        }
    }

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
            float fMin = Math.min(3.0f, (Math.max(0.5f, f) - 0.5f) * 10.0f);
            Slog.d("ShellTransitions", "Luma=" + f + " factor=" + fMin);
            this.mInterpolation = fMin > 0.5f ? new AccelerateInterpolator(fMin) : null;
        }

        @Override // com.android.wm.shell.transition.DefaultSurfaceAnimator.AnimationAdapter
        public final void applyTransformation(long j, ValueAnimator valueAnimator) {
            AccelerateInterpolator accelerateInterpolator = this.mInterpolation;
            float interpolation = accelerateInterpolator != null ? accelerateInterpolator.getInterpolation(valueAnimator.getAnimatedFraction()) : valueAnimator.getAnimatedFraction();
            float f = this.mStartLuma;
            float fM$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(this.mEndLuma, f, interpolation, f);
            float[] fArr = this.mColorArray;
            fArr[0] = fM$1;
            fArr[1] = fM$1;
            fArr[2] = fM$1;
            this.mTransaction.setColor(this.mLeash, fArr);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0235  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x023e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ScreenRotationAnimation(Context context, TransactionPool transactionPool, SurfaceControl.Transaction transaction, TransitionInfo.Change change, SurfaceControl surfaceControl, int i, int i2) {
        String str;
        int i3;
        this.mFadeInOutAnimationNeeded = false;
        this.mContext = context;
        this.mTransactionPool = transactionPool;
        this.mAnimHint = i;
        SurfaceControl leash = change.getLeash();
        this.mSurfaceControl = leash;
        int iWidth = change.getStartAbsBounds().width();
        this.mStartWidth = iWidth;
        int iHeight = change.getStartAbsBounds().height();
        this.mStartHeight = iHeight;
        int iWidth2 = change.getEndAbsBounds().width();
        this.mEndWidth = iWidth2;
        int iHeight2 = change.getEndAbsBounds().height();
        this.mEndHeight = iHeight2;
        this.mStartRotation = change.getStartRotation();
        this.mEndRotation = change.getEndRotation();
        SurfaceControl surfaceControlBuild = new SurfaceControl.Builder().setParent(surfaceControl).setEffectLayer().setCallsite("ShellRotationAnimation").setName("Animation leash of screenshot rotation").build();
        this.mAnimLeash = surfaceControlBuild;
        try {
        } catch (Surface.OutOfResourcesException e) {
            Slog.w("ShellTransitions", "Unable to allocate freeze surface", e);
        }
        if (change.getSnapshot() == null) {
            str = "Not use \"BackColorSurface\" layer for change=";
            i3 = iHeight2;
            ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBufferCaptureLayers = ScreenCapture.captureLayers(new ScreenCapture.LayerCaptureArgs.Builder(leash).setCaptureSecureLayers(true).setAllowProtected(true).setSourceCrop(new Rect(0, 0, iWidth, iHeight)).setHintForSeamlessTransition(true).build());
            if (screenshotHardwareBufferCaptureLayers == null) {
                Slog.w("ShellTransitions", "Unable to take screenshot of display");
                return;
            }
            SurfaceControl surfaceControlBuild2 = new SurfaceControl.Builder().setParent(surfaceControlBuild).setBLASTLayer().setSecure(screenshotHardwareBufferCaptureLayers.containsSecureLayers()).setOpaque(true).setCallsite("ShellRotationAnimation").setName("RotationLayer".concat(CoreRune.FW_SHELL_TRANSITION_LOG ? "_WmShell" : "")).build();
            this.mScreenshotLayer = surfaceControlBuild2;
            transaction.setMetadata(surfaceControlBuild2, 30, 1);
            TransitionAnimation.configureScreenshotLayer(transaction, surfaceControlBuild2, screenshotHardwareBufferCaptureLayers);
            HardwareBuffer hardwareBuffer = screenshotHardwareBufferCaptureLayers.getHardwareBuffer();
            transaction.show(surfaceControlBuild2);
            if (CoreRune.FW_SHELL_TRANSITION) {
                if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX && !change.hasFlags(32) && change.hasFlags(1)) {
                }
                hardwareBuffer.close();
            } else {
                if (!(i == 1 || i == 2)) {
                    this.mStartLuma = TransitionAnimation.getBorderLuma(hardwareBuffer, screenshotHardwareBufferCaptureLayers.getColorSpace(), leash);
                }
                hardwareBuffer.close();
            }
            if (change.isFadeInOutRotationNeeded()) {
                this.mFadeInOutAnimationNeeded = true;
            }
            if (this.mScreenshotLayer != null) {
                Matrix matrix = new Matrix();
                int iDeltaRotation = RotationUtils.deltaRotation(this.mEndRotation, this.mStartRotation);
                int i4 = this.mStartHeight;
                int i5 = this.mStartWidth;
                if (iDeltaRotation == 0) {
                    int i6 = this.mEndWidth;
                    boolean z = i6 > i5;
                    int i7 = this.mEndHeight;
                    if (z == (i7 > i4) && (i6 != i5 || i7 != i4)) {
                        float fMax = Math.max(i6 / i5, i7 / i4);
                        matrix.setScale(fMax, fMax);
                    }
                } else if (iDeltaRotation == 1) {
                    matrix.setRotate(90.0f, 0.0f, 0.0f);
                    matrix.postTranslate(i4, 0.0f);
                } else if (iDeltaRotation == 2) {
                    matrix.setRotate(180.0f, 0.0f, 0.0f);
                    matrix.postTranslate(i5, i4);
                } else if (iDeltaRotation == 3) {
                    matrix.setRotate(270.0f, 0.0f, 0.0f);
                    matrix.postTranslate(0.0f, i5);
                }
                float[] fArr = this.mTmpFloats;
                matrix.getValues(fArr);
                transaction.setPosition(this.mScreenshotLayer, fArr[2], fArr[5]);
                transaction.setMatrix(this.mScreenshotLayer, fArr[0], fArr[3], fArr[1], fArr[4]);
            }
            transaction.apply();
        }
        SurfaceControl snapshot = change.getSnapshot();
        this.mScreenshotLayer = snapshot;
        transaction.reparent(snapshot, surfaceControlBuild);
        this.mStartLuma = change.getSnapshotLuma();
        i3 = iHeight2;
        str = "Not use \"BackColorSurface\" layer for change=";
        if ((i2 & 1) != 0) {
            SurfaceControl surfaceControlBuild3 = new SurfaceControl.Builder().setCallsite("ShellRotationAnimation").setParent(surfaceControl).setEffectLayer().setOpaque(true).setName("BackEffect").build();
            this.mBackEffectSurface = surfaceControlBuild3;
            SurfaceControl.Transaction transactionReparent = transaction.reparent(leash, surfaceControlBuild3);
            float f = this.mStartLuma;
            transactionReparent.setColor(surfaceControlBuild3, new float[]{f, f, f}).show(surfaceControlBuild3);
        }
        transaction.setLayer(surfaceControlBuild, 2010000);
        transaction.show(surfaceControlBuild);
        SurfaceControl surfaceControl2 = this.mBackEffectSurface;
        transaction.setCrop(surfaceControl2 != null ? surfaceControl2 : leash, new Rect(0, 0, iWidth2, i3));
        if (CoreRune.FW_SHELL_TRANSITION) {
            if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX && !change.hasFlags(32) && change.hasFlags(1)) {
                Slog.d("ShellTransitions", str + change);
            }
        } else {
            if (!(i == 1 || i == 2)) {
                SurfaceControl surfaceControlBuild4 = new SurfaceControl.Builder().setParent(surfaceControl).setColorLayer().setOpaque(true).setCallsite("ShellRotationAnimation").setName("BackColorSurface").build();
                this.mBackColorSurface = surfaceControlBuild4;
                transaction.setLayer(surfaceControlBuild4, -1);
                float f2 = this.mStartLuma;
                transaction.setColor(surfaceControlBuild4, new float[]{f2, f2, f2});
                transaction.show(surfaceControlBuild4);
            }
        }
        if (change.isFadeInOutRotationNeeded()) {
        }
        if (this.mScreenshotLayer != null) {
        }
        transaction.apply();
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
            int iDeltaRotation = this.mFadeInOutAnimationNeeded ? 0 : RotationUtils.deltaRotation(this.mEndRotation, this.mStartRotation);
            if (iDeltaRotation == 0) {
                this.mRotateExitAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.ft_avd_tooverflow_rectangle_1_pivot_animation);
                this.mRotateEnterAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.task_fragment_clear_top_close_enter);
            } else if (iDeltaRotation == 1) {
                this.mRotateExitAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.ic_bluetooth_transient_animation_0);
                this.mRotateEnterAnimation = AnimationUtils.loadAnimation(this.mContext, CoreRune.FW_SHELL_TRANSITION ? R.anim.voice_activity_open_exit : R.anim.grow_fade_in_from_bottom);
            } else if (iDeltaRotation == 2) {
                this.mRotateExitAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.ft_avd_tooverflow_rectangle_2_pivot_animation);
                this.mRotateEnterAnimation = AnimationUtils.loadAnimation(this.mContext, CoreRune.FW_SHELL_TRANSITION ? R.anim.voice_activity_close_exit : R.anim.ft_avd_tooverflow_rectangle_2_animation);
            } else if (iDeltaRotation == 3) {
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
