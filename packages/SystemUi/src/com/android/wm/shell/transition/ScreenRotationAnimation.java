package com.android.wm.shell.transition;

import android.R;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.util.RotationUtils;
import android.util.Slog;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.window.ScreenCapture;
import android.window.TransitionInfo;
import com.android.internal.policy.TransitionAnimation;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.TransactionPool;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenRotationAnimation {
    public final int mAnimHint;
    public final SurfaceControl mAnimLeash;
    public final SurfaceControl mBackColorSurface;
    public final Context mContext;
    public final int mEndHeight;
    public final int mEndRotation;
    public final int mEndWidth;
    public boolean mFadeInOutAnimationNeeded;
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

    /* JADX WARN: Removed duplicated region for block: B:13:0x0141 A[Catch: OutOfResourcesException -> 0x019b, TRY_LEAVE, TryCatch #2 {OutOfResourcesException -> 0x019b, blocks: (B:6:0x0123, B:13:0x0141, B:64:0x00ab, B:66:0x00c5, B:68:0x00cb, B:71:0x00f2, B:78:0x0116, B:79:0x0120), top: B:63:0x00ab }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x01ac  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x01b5  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0116 A[Catch: OutOfResourcesException -> 0x019b, TryCatch #2 {OutOfResourcesException -> 0x019b, blocks: (B:6:0x0123, B:13:0x0141, B:64:0x00ab, B:66:0x00c5, B:68:0x00cb, B:71:0x00f2, B:78:0x0116, B:79:0x0120), top: B:63:0x00ab }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ScreenRotationAnimation(Context context, SurfaceSession surfaceSession, TransactionPool transactionPool, SurfaceControl.Transaction transaction, TransitionInfo.Change change, SurfaceControl surfaceControl, int i) {
        SurfaceControl surfaceControl2;
        boolean z;
        boolean z2;
        this.mFadeInOutAnimationNeeded = false;
        this.mContext = context;
        this.mTransactionPool = transactionPool;
        this.mAnimHint = i;
        SurfaceControl leash = change.getLeash();
        this.mSurfaceControl = leash;
        int width = change.getStartAbsBounds().width();
        this.mStartWidth = width;
        int height = change.getStartAbsBounds().height();
        this.mStartHeight = height;
        int width2 = change.getEndAbsBounds().width();
        this.mEndWidth = width2;
        int height2 = change.getEndAbsBounds().height();
        this.mEndHeight = height2;
        this.mStartRotation = change.getStartRotation();
        this.mEndRotation = change.getEndRotation();
        SurfaceControl build = new SurfaceControl.Builder(surfaceSession).setParent(surfaceControl).setEffectLayer().setCallsite("ShellRotationAnimation").setName("Animation leash of screenshot rotation").build();
        this.mAnimLeash = build;
        try {
            if (change.getSnapshot() != null) {
                SurfaceControl snapshot = change.getSnapshot();
                this.mScreenshotLayer = snapshot;
                transaction.reparent(snapshot, build);
                this.mStartLuma = change.getSnapshotLuma();
            } else {
                try {
                    ScreenCapture.ScreenshotHardwareBuffer captureLayers = ScreenCapture.captureLayers(new ScreenCapture.LayerCaptureArgs.Builder(leash).setCaptureSecureLayers(true).setAllowProtected(true).setSourceCrop(new Rect(0, 0, width, height)).setHintForSeamlessTransition(true).build());
                    if (captureLayers == null) {
                        Slog.w("ShellTransitions", "Unable to take screenshot of display");
                        return;
                    }
                    SurfaceControl build2 = new SurfaceControl.Builder(surfaceSession).setParent(build).setBLASTLayer().setSecure(captureLayers.containsSecureLayers()).setOpaque(true).setCallsite("ShellRotationAnimation").setName("RotationLayer".concat(CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG ? "_WmShell" : "")).build();
                    this.mScreenshotLayer = build2;
                    TransitionAnimation.configureScreenshotLayer(transaction, build2, captureLayers);
                    HardwareBuffer hardwareBuffer = captureLayers.getHardwareBuffer();
                    transaction.show(build2);
                    if (i != 1 && i != 2) {
                        z = false;
                        if (!z) {
                            this.mStartLuma = TransitionAnimation.getBorderLuma(hardwareBuffer, captureLayers.getColorSpace());
                        }
                        hardwareBuffer.close();
                    }
                    z = true;
                    if (!z) {
                    }
                    hardwareBuffer.close();
                } catch (Surface.OutOfResourcesException e) {
                    e = e;
                    Slog.w("ShellTransitions", "Unable to allocate freeze surface", e);
                    if (change.isFadeInOutRotationNeeded()) {
                    }
                    surfaceControl2 = this.mScreenshotLayer;
                    if (surfaceControl2 != null) {
                    }
                    transaction.apply();
                }
            }
            transaction.setLayer(build, 2010000);
            transaction.show(build);
            transaction.setCrop(leash, new Rect(0, 0, width2, height2));
        } catch (Surface.OutOfResourcesException e2) {
            e = e2;
        }
        if (i != 1 && i != 2) {
            z2 = false;
            if (!z2) {
                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
                    try {
                        if (!change.hasFlags(32)) {
                            if (!change.hasFlags(1)) {
                            }
                        }
                    } catch (Surface.OutOfResourcesException e3) {
                        e = e3;
                        Slog.w("ShellTransitions", "Unable to allocate freeze surface", e);
                        if (change.isFadeInOutRotationNeeded()) {
                        }
                        surfaceControl2 = this.mScreenshotLayer;
                        if (surfaceControl2 != null) {
                        }
                        transaction.apply();
                    }
                }
                SurfaceControl build3 = new SurfaceControl.Builder(surfaceSession).setParent(surfaceControl).setColorLayer().setOpaque(true).setCallsite("ShellRotationAnimation").setName("BackColorSurface").build();
                this.mBackColorSurface = build3;
                transaction.setLayer(build3, -1);
                float f = this.mStartLuma;
                transaction.setColor(build3, new float[]{f, f, f});
                transaction.show(build3);
            }
            if (change.isFadeInOutRotationNeeded()) {
                this.mFadeInOutAnimationNeeded = true;
            }
            surfaceControl2 = this.mScreenshotLayer;
            if (surfaceControl2 != null) {
                Matrix matrix = new Matrix();
                int deltaRotation = RotationUtils.deltaRotation(this.mEndRotation, this.mStartRotation);
                int i2 = this.mStartHeight;
                int i3 = this.mStartWidth;
                if (deltaRotation == 0) {
                    int i4 = this.mEndWidth;
                    boolean z3 = i4 > i3;
                    int i5 = this.mEndHeight;
                    if (z3 == (i5 > i2) && (i4 != i3 || i5 != i2)) {
                        float max = Math.max(i4 / i3, i5 / i2);
                        matrix.setScale(max, max);
                    }
                } else if (deltaRotation == 1) {
                    matrix.setRotate(90.0f, 0.0f, 0.0f);
                    matrix.postTranslate(i2, 0.0f);
                } else if (deltaRotation == 2) {
                    matrix.setRotate(180.0f, 0.0f, 0.0f);
                    matrix.postTranslate(i3, i2);
                } else if (deltaRotation == 3) {
                    matrix.setRotate(270.0f, 0.0f, 0.0f);
                    matrix.postTranslate(0.0f, i3);
                }
                float[] fArr = this.mTmpFloats;
                matrix.getValues(fArr);
                transaction.setPosition(surfaceControl2, fArr[2], fArr[5]);
                transaction.setMatrix(this.mScreenshotLayer, fArr[0], fArr[3], fArr[1], fArr[4]);
            }
            transaction.apply();
        }
        z2 = true;
        if (!z2) {
        }
        if (change.isFadeInOutRotationNeeded()) {
        }
        surfaceControl2 = this.mScreenshotLayer;
        if (surfaceControl2 != null) {
        }
        transaction.apply();
    }

    public final boolean buildAnimation(ArrayList arrayList, DefaultTransitionHandler$$ExternalSyntheticLambda4 defaultTransitionHandler$$ExternalSyntheticLambda4, float f, ShellExecutor shellExecutor, int i, int i2) {
        if (this.mScreenshotLayer == null) {
            return false;
        }
        int i3 = this.mAnimHint;
        boolean z = i3 == 1 || i3 == 2;
        Context context = this.mContext;
        if (z) {
            this.mRotateExitAnimation = AnimationUtils.loadAnimation(context, i3 == 2 ? R.anim.task_fragment_clear_top_close_exit : R.anim.task_fragment_clear_top_open_enter);
            this.mRotateEnterAnimation = AnimationUtils.loadAnimation(context, R.anim.task_fragment_clear_top_close_enter);
            this.mRotateAlphaAnimation = AnimationUtils.loadAnimation(context, R.anim.ft_avd_tooverflow_rectangle_path_2_animation);
        } else if ((!CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION && !CoreRune.FW_CUSTOM_SHELL_TRANSITION_DISPLAY_CHANGE) || i2 == -1 || i == -1) {
            int deltaRotation = this.mFadeInOutAnimationNeeded ? 0 : RotationUtils.deltaRotation(this.mEndRotation, this.mStartRotation);
            if (deltaRotation == 0) {
                this.mRotateExitAnimation = AnimationUtils.loadAnimation(context, R.anim.ft_avd_tooverflow_rectangle_2_pivot_animation);
                this.mRotateEnterAnimation = AnimationUtils.loadAnimation(context, R.anim.task_fragment_clear_top_close_enter);
            } else if (deltaRotation == 1) {
                this.mRotateExitAnimation = AnimationUtils.loadAnimation(context, R.anim.ic_bluetooth_transient_animation_2);
                this.mRotateEnterAnimation = AnimationUtils.loadAnimation(context, CoreRune.FW_CUSTOM_SHELL_TRANSITION ? R.anim.voice_layer_exit : R.anim.ic_bluetooth_transient_animation_1);
            } else if (deltaRotation == 2) {
                this.mRotateExitAnimation = AnimationUtils.loadAnimation(context, R.anim.ft_avd_tooverflow_rectangle_3_pivot_animation);
                this.mRotateEnterAnimation = AnimationUtils.loadAnimation(context, CoreRune.FW_CUSTOM_SHELL_TRANSITION ? R.anim.voice_activity_open_exit : R.anim.ft_avd_tooverflow_rectangle_3_animation);
            } else if (deltaRotation == 3) {
                this.mRotateExitAnimation = AnimationUtils.loadAnimation(context, R.anim.ic_bluetooth_transient_animation_0);
                this.mRotateEnterAnimation = AnimationUtils.loadAnimation(context, CoreRune.FW_CUSTOM_SHELL_TRANSITION ? R.anim.voice_layer_enter : R.anim.grow_fade_in_from_bottom);
            }
        } else {
            this.mRotateExitAnimation = AnimationUtils.loadAnimation(context, i);
            this.mRotateEnterAnimation = AnimationUtils.loadAnimation(context, i2);
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
            DefaultTransitionHandler.buildSurfaceAnimation(arrayList, this.mRotateAlphaAnimation, this.mAnimLeash, defaultTransitionHandler$$ExternalSyntheticLambda4, this.mTransactionPool, shellExecutor, null, 0.0f, null);
            DefaultTransitionHandler.buildSurfaceAnimation(arrayList, this.mRotateEnterAnimation, this.mSurfaceControl, defaultTransitionHandler$$ExternalSyntheticLambda4, this.mTransactionPool, shellExecutor, null, 0.0f, null);
        } else {
            DefaultTransitionHandler.buildSurfaceAnimation(arrayList, this.mRotateEnterAnimation, this.mSurfaceControl, defaultTransitionHandler$$ExternalSyntheticLambda4, this.mTransactionPool, shellExecutor, null, 0.0f, null);
            DefaultTransitionHandler.buildSurfaceAnimation(arrayList, this.mRotateExitAnimation, this.mAnimLeash, defaultTransitionHandler$$ExternalSyntheticLambda4, this.mTransactionPool, shellExecutor, null, 0.0f, null);
        }
        return true;
    }
}
