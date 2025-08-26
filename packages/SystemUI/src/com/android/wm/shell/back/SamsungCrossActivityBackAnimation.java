package com.android.wm.shell.back;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.WindowConfiguration;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Log;
import android.view.Choreographer;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.view.animation.Transformation;
import android.window.BackEvent;
import android.window.BackMotionEvent;
import android.window.BackProgressAnimator;
import android.window.IOnBackInvokedCallback;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.back.SamsungCrossActivityBackAnimation;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.animation.Interpolators;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public class SamsungCrossActivityBackAnimation extends ShellBackAnimation {
    public static final boolean DEBUG_PROGRESS = SystemProperties.getBoolean("persist.debug.samsung.predictive_back.anim.progress", false);
    public static final int POST_ANIMATION_DURATION_MS;
    public static final Interpolator POST_ANIMATION_INTERPOLATOR;
    public static final int POST_DIM_ANIMATION_DURATION_MS;
    public static final Interpolator POST_DIM_ANIMATION_INTERPOLATOR;
    public final BackAnimationRunner mBackAnimationRunner;
    public boolean mBackInProgress;
    public RemoteAnimationTarget mClosingTarget;
    public int mDisplayId;
    public RemoteAnimationTarget mEnteringTarget;
    public IRemoteAnimationFinishedCallback mFinishCallback;
    public Animation mPostScrimDimAnimation;
    public RootTaskDisplayAreaOrganizer mRootTaskDisplayAreaOrganizer;
    public SurfaceControl mScrimDimLayer;
    public final SurfaceControl.Transaction mScrimDimTransaction;
    public final Transformation mTmpTransform;
    public final Rect mStartTaskRect = new Rect();
    public final Rect mClosingStartRect = new Rect();
    public final RectF mClosingCurrentRect = new RectF();
    public final Rect mEnteringStartRect = new Rect();
    public final RectF mEnteringCurrentRect = new RectF();
    public final Matrix mTransformMatrix = new Matrix();
    public final float[] mTmpFloat9 = new float[9];
    public final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();
    public final BackProgressAnimator mProgressAnimator = new BackProgressAnimator();

    public final class Callback extends IOnBackInvokedCallback.Default {
        public static final /* synthetic */ int $r8$clinit = 0;

        public /* synthetic */ Callback(SamsungCrossActivityBackAnimation samsungCrossActivityBackAnimation, int i) {
            this();
        }

        public final void onBackCancelled() {
            final SamsungCrossActivityBackAnimation samsungCrossActivityBackAnimation = SamsungCrossActivityBackAnimation.this;
            samsungCrossActivityBackAnimation.mProgressAnimator.onBackCancelled(new Runnable() { // from class: com.android.wm.shell.back.SamsungCrossActivityBackAnimation$Callback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SamsungCrossActivityBackAnimation samsungCrossActivityBackAnimation2 = samsungCrossActivityBackAnimation;
                    int i = SamsungCrossActivityBackAnimation.Callback.$r8$clinit;
                    boolean z = SamsungCrossActivityBackAnimation.DEBUG_PROGRESS;
                    samsungCrossActivityBackAnimation2.finishAnimation$2();
                }
            });
        }

        public final void onBackInvoked() {
            SamsungCrossActivityBackAnimation.this.mProgressAnimator.reset();
            final SamsungCrossActivityBackAnimation samsungCrossActivityBackAnimation = SamsungCrossActivityBackAnimation.this;
            if (samsungCrossActivityBackAnimation.mEnteringTarget == null || samsungCrossActivityBackAnimation.mClosingTarget == null) {
                samsungCrossActivityBackAnimation.finishAnimation$2();
                return;
            }
            samsungCrossActivityBackAnimation.mEnteringCurrentRect.round(samsungCrossActivityBackAnimation.mEnteringStartRect);
            samsungCrossActivityBackAnimation.mClosingCurrentRect.round(samsungCrossActivityBackAnimation.mClosingStartRect);
            ValueAnimator duration = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(SamsungCrossActivityBackAnimation.POST_ANIMATION_DURATION_MS);
            duration.setInterpolator(SamsungCrossActivityBackAnimation.POST_ANIMATION_INTERPOLATOR);
            final int i = 0;
            duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.back.SamsungCrossActivityBackAnimation$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int i2 = i;
                    SamsungCrossActivityBackAnimation samsungCrossActivityBackAnimation2 = samsungCrossActivityBackAnimation;
                    switch (i2) {
                        case 0:
                            boolean z = SamsungCrossActivityBackAnimation.DEBUG_PROGRESS;
                            samsungCrossActivityBackAnimation2.getClass();
                            float animatedFraction = valueAnimator.getAnimatedFraction();
                            float fMin = Float.min(samsungCrossActivityBackAnimation2.mEnteringStartRect.left + (Math.abs(samsungCrossActivityBackAnimation2.mStartTaskRect.left - samsungCrossActivityBackAnimation2.mEnteringStartRect.left) * animatedFraction) + 0.5f, 0.0f);
                            RectF rectF = samsungCrossActivityBackAnimation2.mEnteringCurrentRect;
                            Rect rect = samsungCrossActivityBackAnimation2.mStartTaskRect;
                            rectF.set(fMin, rect.top, samsungCrossActivityBackAnimation2.mStartTaskRect.width() + fMin, rect.bottom);
                            samsungCrossActivityBackAnimation2.applyTransform(samsungCrossActivityBackAnimation2.mEnteringTarget.leash, samsungCrossActivityBackAnimation2.mEnteringCurrentRect, true);
                            boolean z2 = SamsungCrossActivityBackAnimation.DEBUG_PROGRESS;
                            if (z2) {
                                Log.i("SamsungCrossActivityBack", "updatePostCommitEnteringAnimation, progress=" + animatedFraction + ", mEnteringStartRect=" + samsungCrossActivityBackAnimation2.mEnteringStartRect + ", mEnteringCurrentRect=" + samsungCrossActivityBackAnimation2.mEnteringCurrentRect + ", leash=" + samsungCrossActivityBackAnimation2.mEnteringTarget.leash);
                            }
                            int i3 = samsungCrossActivityBackAnimation2.mStartTaskRect.right;
                            float fMin2 = Float.min(samsungCrossActivityBackAnimation2.mClosingStartRect.left + ((i3 - r6) * animatedFraction) + 0.5f, r4.width());
                            RectF rectF2 = samsungCrossActivityBackAnimation2.mClosingCurrentRect;
                            Rect rect2 = samsungCrossActivityBackAnimation2.mStartTaskRect;
                            rectF2.set(fMin2, rect2.top, samsungCrossActivityBackAnimation2.mStartTaskRect.width() + fMin2, rect2.bottom);
                            samsungCrossActivityBackAnimation2.applyTransform(samsungCrossActivityBackAnimation2.mClosingTarget.leash, samsungCrossActivityBackAnimation2.mClosingCurrentRect, false);
                            if (z2) {
                                Log.i("SamsungCrossActivityBack", "updatePostCommitClosingAnimation, progress=" + animatedFraction + ", mClosingStartRect=" + samsungCrossActivityBackAnimation2.mClosingStartRect + ", mClosingCurrentRect=" + samsungCrossActivityBackAnimation2.mClosingCurrentRect + ", leash=" + samsungCrossActivityBackAnimation2.mClosingTarget.leash);
                            }
                            SamsungCrossActivityBackAnimation.applyTransaction(samsungCrossActivityBackAnimation2.mTransaction);
                            break;
                        default:
                            SurfaceControl surfaceControl = samsungCrossActivityBackAnimation2.mScrimDimLayer;
                            if (surfaceControl != null && surfaceControl.isValid()) {
                                long duration2 = valueAnimator.getDuration();
                                long currentPlayTime = valueAnimator.getCurrentPlayTime();
                                if (currentPlayTime <= duration2) {
                                    duration2 = currentPlayTime;
                                }
                                samsungCrossActivityBackAnimation2.mTmpTransform.clear();
                                samsungCrossActivityBackAnimation2.mPostScrimDimAnimation.getTransformation(duration2, samsungCrossActivityBackAnimation2.mTmpTransform);
                                samsungCrossActivityBackAnimation2.mScrimDimTransaction.setAlpha(samsungCrossActivityBackAnimation2.mScrimDimLayer, samsungCrossActivityBackAnimation2.mTmpTransform.getAlpha());
                                SamsungCrossActivityBackAnimation.applyTransaction(samsungCrossActivityBackAnimation2.mScrimDimTransaction);
                                break;
                            }
                            break;
                    }
                }
            });
            duration.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.back.SamsungCrossActivityBackAnimation.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    SamsungCrossActivityBackAnimation samsungCrossActivityBackAnimation2 = SamsungCrossActivityBackAnimation.this;
                    boolean z = SamsungCrossActivityBackAnimation.DEBUG_PROGRESS;
                    samsungCrossActivityBackAnimation2.finishAnimation$2();
                }
            });
            duration.start();
            ValueAnimator duration2 = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(samsungCrossActivityBackAnimation.mPostScrimDimAnimation.getDuration());
            duration2.setInterpolator(samsungCrossActivityBackAnimation.mPostScrimDimAnimation.getInterpolator());
            final int i2 = 1;
            duration2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.back.SamsungCrossActivityBackAnimation$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int i22 = i2;
                    SamsungCrossActivityBackAnimation samsungCrossActivityBackAnimation2 = samsungCrossActivityBackAnimation;
                    switch (i22) {
                        case 0:
                            boolean z = SamsungCrossActivityBackAnimation.DEBUG_PROGRESS;
                            samsungCrossActivityBackAnimation2.getClass();
                            float animatedFraction = valueAnimator.getAnimatedFraction();
                            float fMin = Float.min(samsungCrossActivityBackAnimation2.mEnteringStartRect.left + (Math.abs(samsungCrossActivityBackAnimation2.mStartTaskRect.left - samsungCrossActivityBackAnimation2.mEnteringStartRect.left) * animatedFraction) + 0.5f, 0.0f);
                            RectF rectF = samsungCrossActivityBackAnimation2.mEnteringCurrentRect;
                            Rect rect = samsungCrossActivityBackAnimation2.mStartTaskRect;
                            rectF.set(fMin, rect.top, samsungCrossActivityBackAnimation2.mStartTaskRect.width() + fMin, rect.bottom);
                            samsungCrossActivityBackAnimation2.applyTransform(samsungCrossActivityBackAnimation2.mEnteringTarget.leash, samsungCrossActivityBackAnimation2.mEnteringCurrentRect, true);
                            boolean z2 = SamsungCrossActivityBackAnimation.DEBUG_PROGRESS;
                            if (z2) {
                                Log.i("SamsungCrossActivityBack", "updatePostCommitEnteringAnimation, progress=" + animatedFraction + ", mEnteringStartRect=" + samsungCrossActivityBackAnimation2.mEnteringStartRect + ", mEnteringCurrentRect=" + samsungCrossActivityBackAnimation2.mEnteringCurrentRect + ", leash=" + samsungCrossActivityBackAnimation2.mEnteringTarget.leash);
                            }
                            int i3 = samsungCrossActivityBackAnimation2.mStartTaskRect.right;
                            float fMin2 = Float.min(samsungCrossActivityBackAnimation2.mClosingStartRect.left + ((i3 - r6) * animatedFraction) + 0.5f, r4.width());
                            RectF rectF2 = samsungCrossActivityBackAnimation2.mClosingCurrentRect;
                            Rect rect2 = samsungCrossActivityBackAnimation2.mStartTaskRect;
                            rectF2.set(fMin2, rect2.top, samsungCrossActivityBackAnimation2.mStartTaskRect.width() + fMin2, rect2.bottom);
                            samsungCrossActivityBackAnimation2.applyTransform(samsungCrossActivityBackAnimation2.mClosingTarget.leash, samsungCrossActivityBackAnimation2.mClosingCurrentRect, false);
                            if (z2) {
                                Log.i("SamsungCrossActivityBack", "updatePostCommitClosingAnimation, progress=" + animatedFraction + ", mClosingStartRect=" + samsungCrossActivityBackAnimation2.mClosingStartRect + ", mClosingCurrentRect=" + samsungCrossActivityBackAnimation2.mClosingCurrentRect + ", leash=" + samsungCrossActivityBackAnimation2.mClosingTarget.leash);
                            }
                            SamsungCrossActivityBackAnimation.applyTransaction(samsungCrossActivityBackAnimation2.mTransaction);
                            break;
                        default:
                            SurfaceControl surfaceControl = samsungCrossActivityBackAnimation2.mScrimDimLayer;
                            if (surfaceControl != null && surfaceControl.isValid()) {
                                long duration22 = valueAnimator.getDuration();
                                long currentPlayTime = valueAnimator.getCurrentPlayTime();
                                if (currentPlayTime <= duration22) {
                                    duration22 = currentPlayTime;
                                }
                                samsungCrossActivityBackAnimation2.mTmpTransform.clear();
                                samsungCrossActivityBackAnimation2.mPostScrimDimAnimation.getTransformation(duration22, samsungCrossActivityBackAnimation2.mTmpTransform);
                                samsungCrossActivityBackAnimation2.mScrimDimTransaction.setAlpha(samsungCrossActivityBackAnimation2.mScrimDimLayer, samsungCrossActivityBackAnimation2.mTmpTransform.getAlpha());
                                SamsungCrossActivityBackAnimation.applyTransaction(samsungCrossActivityBackAnimation2.mScrimDimTransaction);
                                break;
                            }
                            break;
                    }
                }
            });
            duration2.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.back.SamsungCrossActivityBackAnimation.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    SamsungCrossActivityBackAnimation samsungCrossActivityBackAnimation2 = SamsungCrossActivityBackAnimation.this;
                    boolean z = SamsungCrossActivityBackAnimation.DEBUG_PROGRESS;
                    SurfaceControl surfaceControl = samsungCrossActivityBackAnimation2.mScrimDimLayer;
                    if (surfaceControl != null && surfaceControl.isValid()) {
                        samsungCrossActivityBackAnimation2.mScrimDimTransaction.remove(samsungCrossActivityBackAnimation2.mScrimDimLayer);
                        samsungCrossActivityBackAnimation2.mScrimDimLayer = null;
                    }
                    SamsungCrossActivityBackAnimation.applyTransaction(samsungCrossActivityBackAnimation2.mScrimDimTransaction);
                }
            });
            duration2.start();
        }

        public final void onBackProgressed(BackMotionEvent backMotionEvent) {
            if (SamsungCrossActivityBackAnimation.DEBUG_PROGRESS) {
                Log.d("SamsungCrossActivityBack", "onBackProgressed, backEvent=" + backMotionEvent);
            }
            SamsungCrossActivityBackAnimation.this.mProgressAnimator.onBackProgressed(backMotionEvent);
        }

        public final void onBackStarted(BackMotionEvent backMotionEvent) {
            SamsungCrossActivityBackAnimation.this.mProgressAnimator.removeOnBackCancelledFinishCallback();
            final SamsungCrossActivityBackAnimation samsungCrossActivityBackAnimation = SamsungCrossActivityBackAnimation.this;
            samsungCrossActivityBackAnimation.mProgressAnimator.onBackStarted(backMotionEvent, new BackProgressAnimator.ProgressCallback() { // from class: com.android.wm.shell.back.SamsungCrossActivityBackAnimation$Callback$$ExternalSyntheticLambda1
                public final void onProgressUpdate(BackEvent backEvent) {
                    float f;
                    float f2;
                    SamsungCrossActivityBackAnimation samsungCrossActivityBackAnimation2 = samsungCrossActivityBackAnimation;
                    int i = SamsungCrossActivityBackAnimation.Callback.$r8$clinit;
                    if (!samsungCrossActivityBackAnimation2.mBackInProgress) {
                        samsungCrossActivityBackAnimation2.mBackInProgress = true;
                    }
                    float progress = backEvent.getProgress();
                    RemoteAnimationTarget remoteAnimationTarget = samsungCrossActivityBackAnimation2.mEnteringTarget;
                    boolean z = SamsungCrossActivityBackAnimation.DEBUG_PROGRESS;
                    if (remoteAnimationTarget != null && samsungCrossActivityBackAnimation2.mClosingTarget != null) {
                        int iWidth = samsungCrossActivityBackAnimation2.mStartTaskRect.width();
                        float fM$1 = progress <= 0.5f ? 1.0f * progress : DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(progress, 0.5f, 0.19999999f, 0.5f);
                        float f3 = iWidth;
                        float f4 = f3 * fM$1;
                        Rect rect = samsungCrossActivityBackAnimation2.mStartTaskRect;
                        samsungCrossActivityBackAnimation2.mClosingCurrentRect.set(f4, rect.top, f4 + f3, rect.bottom);
                        if (progress <= 0.5f) {
                            f = 0.16f * progress;
                            f2 = 0.33f;
                        } else {
                            f = (progress - 0.5f) * 0.05f;
                            f2 = 0.25f;
                        }
                        float f5 = f2 - f;
                        float f6 = -(f3 * f5);
                        Rect rect2 = samsungCrossActivityBackAnimation2.mStartTaskRect;
                        samsungCrossActivityBackAnimation2.mEnteringCurrentRect.set(f6, rect2.top, f3 + f6, rect2.bottom);
                        if (z) {
                            StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("updateGestureBackProgress, progress=", progress, ", enteringProgress=", f5, ", closingProgress=");
                            sbM.append(fM$1);
                            Log.i("SamsungCrossActivityBack", sbM.toString());
                        }
                        samsungCrossActivityBackAnimation2.applyTransform(samsungCrossActivityBackAnimation2.mClosingTarget.leash, samsungCrossActivityBackAnimation2.mClosingCurrentRect, false);
                        samsungCrossActivityBackAnimation2.applyTransform(samsungCrossActivityBackAnimation2.mEnteringTarget.leash, samsungCrossActivityBackAnimation2.mEnteringCurrentRect, true);
                        SamsungCrossActivityBackAnimation.applyTransaction(samsungCrossActivityBackAnimation2.mTransaction);
                    }
                    if (z) {
                        Log.i("SamsungCrossActivityBack", "onGestureProgress, progress=" + progress + ", interpolatedProgress=" + progress);
                    }
                }
            });
        }

        private Callback() {
        }
    }

    public final class Runner extends IRemoteAnimationRunner.Default {
        public /* synthetic */ Runner(SamsungCrossActivityBackAnimation samsungCrossActivityBackAnimation, int i) {
            this();
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            RemoteAnimationTarget remoteAnimationTarget;
            WindowConfiguration embedActivityConfiguration;
            WindowConfiguration embedActivityConfiguration2;
            ProtoLog.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, "Start samsung custom back animation", new Object[0]);
            for (RemoteAnimationTarget remoteAnimationTarget2 : remoteAnimationTargetArr) {
                int i2 = remoteAnimationTarget2.mode;
                if (i2 == 1) {
                    SamsungCrossActivityBackAnimation.this.mClosingTarget = remoteAnimationTarget2;
                }
                if (i2 == 0) {
                    SamsungCrossActivityBackAnimation.this.mEnteringTarget = remoteAnimationTarget2;
                }
            }
            SamsungCrossActivityBackAnimation samsungCrossActivityBackAnimation = SamsungCrossActivityBackAnimation.this;
            if (samsungCrossActivityBackAnimation.mEnteringTarget == null || (remoteAnimationTarget = samsungCrossActivityBackAnimation.mClosingTarget) == null) {
                ProtoLog.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, "Entering target or closing target is null.", new Object[0]);
            } else {
                samsungCrossActivityBackAnimation.mStartTaskRect.set(remoteAnimationTarget.windowConfiguration.getBounds());
                boolean z = CoreRune.MW_EMBED_ACTIVITY_ANIMATION;
                if (z && (embedActivityConfiguration2 = samsungCrossActivityBackAnimation.mClosingTarget.getEmbedActivityConfiguration()) != null && embedActivityConfiguration2.isEmbedded() && embedActivityConfiguration2.getEmbedActivityMode() != 1) {
                    samsungCrossActivityBackAnimation.mStartTaskRect.set(samsungCrossActivityBackAnimation.mClosingTarget.localBounds);
                }
                samsungCrossActivityBackAnimation.mStartTaskRect.offsetTo(0, 0);
                samsungCrossActivityBackAnimation.mDisplayId = samsungCrossActivityBackAnimation.mClosingTarget.getDisplayId();
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.18f, 0.0f);
                samsungCrossActivityBackAnimation.mPostScrimDimAnimation = alphaAnimation;
                alphaAnimation.setDuration(SamsungCrossActivityBackAnimation.POST_DIM_ANIMATION_DURATION_MS);
                samsungCrossActivityBackAnimation.mPostScrimDimAnimation.setInterpolator(SamsungCrossActivityBackAnimation.POST_DIM_ANIMATION_INTERPOLATOR);
                if (samsungCrossActivityBackAnimation.mScrimDimLayer == null) {
                    SurfaceControl.Builder callsite = new SurfaceControl.Builder().setColorLayer().setName("ScrimDimLayer for predictive_back animation").setOpaque(false).setCallsite("SamsungCrossActivityBackAnimation#ensureScrimDimLayer");
                    samsungCrossActivityBackAnimation.mRootTaskDisplayAreaOrganizer.attachToDisplayArea(samsungCrossActivityBackAnimation.mDisplayId, callsite);
                    SurfaceControl surfaceControlBuild = callsite.build();
                    samsungCrossActivityBackAnimation.mScrimDimLayer = surfaceControlBuild;
                    samsungCrossActivityBackAnimation.mScrimDimTransaction.show(surfaceControlBuild).setAlpha(samsungCrossActivityBackAnimation.mScrimDimLayer, 0.18f).setRelativeLayer(samsungCrossActivityBackAnimation.mScrimDimLayer, samsungCrossActivityBackAnimation.mClosingTarget.leash, -1);
                    if (z && (embedActivityConfiguration = samsungCrossActivityBackAnimation.mClosingTarget.getEmbedActivityConfiguration()) != null && embedActivityConfiguration.isEmbedded() && embedActivityConfiguration.getEmbedActivityMode() != 1) {
                        samsungCrossActivityBackAnimation.mScrimDimTransaction.setWindowCrop(samsungCrossActivityBackAnimation.mScrimDimLayer, samsungCrossActivityBackAnimation.mClosingTarget.localBounds);
                    }
                    SamsungCrossActivityBackAnimation.applyTransaction(samsungCrossActivityBackAnimation.mScrimDimTransaction);
                }
            }
            SamsungCrossActivityBackAnimation.this.mFinishCallback = iRemoteAnimationFinishedCallback;
        }

        private Runner() {
        }
    }

    static {
        int i = SystemProperties.getInt("persist.debug.samsung.predictive_back.anim_duration.scale", 1);
        POST_ANIMATION_DURATION_MS = i * 400;
        POST_ANIMATION_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
        POST_DIM_ANIMATION_DURATION_MS = i * IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend;
        POST_DIM_ANIMATION_INTERPOLATOR = new PathInterpolator(0.0f, 0.0f, 1.0f, 1.0f);
    }

    public SamsungCrossActivityBackAnimation(Context context, Handler handler) {
        Interpolator interpolator = Interpolators.LINEAR;
        this.mScrimDimTransaction = new SurfaceControl.Transaction();
        this.mTmpTransform = new Transformation();
        int i = 0;
        this.mBackInProgress = false;
        this.mDisplayId = 0;
        this.mBackAnimationRunner = new BackAnimationRunner(new Callback(this, i), new Runner(this, i), context, 85, handler);
    }

    public static void applyTransaction(SurfaceControl.Transaction transaction) {
        transaction.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
        transaction.apply();
    }

    public final void applyTransform(SurfaceControl surfaceControl, RectF rectF, boolean z) {
        if (surfaceControl == null || !surfaceControl.isValid()) {
            return;
        }
        this.mTransformMatrix.reset();
        this.mTransformMatrix.postTranslate(rectF.left, rectF.top);
        this.mTransaction.setMatrix(surfaceControl, this.mTransformMatrix, this.mTmpFloat9).setWindowCrop(surfaceControl, this.mStartTaskRect).setLayer(surfaceControl, z ? 0 : 5);
    }

    public final void finishAnimation$2() {
        RemoteAnimationTarget remoteAnimationTarget = this.mEnteringTarget;
        if (remoteAnimationTarget != null) {
            remoteAnimationTarget.leash.release();
            this.mEnteringTarget = null;
        }
        RemoteAnimationTarget remoteAnimationTarget2 = this.mClosingTarget;
        if (remoteAnimationTarget2 != null) {
            remoteAnimationTarget2.leash.release();
            this.mClosingTarget = null;
        }
        applyTransaction(this.mTransaction);
        SurfaceControl surfaceControl = this.mScrimDimLayer;
        if (surfaceControl != null && surfaceControl.isValid()) {
            this.mScrimDimTransaction.remove(this.mScrimDimLayer);
            this.mScrimDimLayer = null;
        }
        applyTransaction(this.mScrimDimTransaction);
        this.mBackInProgress = false;
        this.mTransformMatrix.reset();
        this.mClosingCurrentRect.setEmpty();
        IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = this.mFinishCallback;
        if (iRemoteAnimationFinishedCallback != null) {
            try {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            } catch (RemoteException unused) {
                ProtoLog.e(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, "RemoteException when invoking onAnimationFinished callback", new Object[0]);
            }
            this.mFinishCallback = null;
        }
    }

    @Override // com.android.wm.shell.back.ShellBackAnimation
    public final BackAnimationRunner getRunner() {
        return this.mBackAnimationRunner;
    }
}
