package com.android.wm.shell.back;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.RemoteException;
import android.view.Choreographer;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.MotionEvent;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.window.BackEvent;
import android.window.BackMotionEvent;
import android.window.BackNavigationInfo;
import android.window.BackProgressAnimator;
import android.window.IOnBackInvokedCallback;
import com.android.internal.dynamicanimation.animation.FloatValueHolder;
import com.android.internal.dynamicanimation.animation.SpringAnimation;
import com.android.internal.dynamicanimation.animation.SpringForce;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.policy.SystemBarUtils;
import com.android.internal.protolog.ProtoLog;
import com.android.systemui.R;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.animation.Interpolators;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes3.dex */
public abstract class CrossActivityBackAnimation extends ShellBackAnimation {
    public final BackAnimationRunner backAnimationRunner;
    public final BackAnimationBackground background;
    public RemoteAnimationTarget closingTarget;
    public final Context context;
    public float cornerRadius;
    public int customizedBackgroundColor;
    public final float displayBoundsMargin;
    public boolean enteringHasSameLetterbox;
    public RemoteAnimationTarget enteringTarget;
    public IRemoteAnimationFinishedCallback finishCallback;
    public float gestureProgress;
    public boolean isLetterboxed;
    public SurfaceControl leftLetterboxLayer;
    public int letterboxColor;
    public float maxScrimAlpha;
    public SurfaceControl rightLetterboxLayer;
    public final RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer;
    public SurfaceControl scrimLayer;
    public int statusbarHeight;
    public int swipeEdge;
    public final SurfaceControl.Transaction transaction;
    public boolean triggerBack;
    public final RectF startClosingRect = new RectF();
    public final RectF targetClosingRect = new RectF();
    public final RectF currentClosingRect = new RectF();
    public final RectF startEnteringRect = new RectF();
    public final RectF targetEnteringRect = new RectF();
    public final RectF currentEnteringRect = new RectF();
    public final Rect backAnimRect = new Rect();
    public final Rect cropRect = new Rect();
    public final RectF tempRectF = new RectF();
    public final PointF initialTouchPos = new PointF();
    public final Matrix transformMatrix = new Matrix();
    public final float[] tmpFloat9 = new float[9];
    public final BackProgressAnimator progressAnimator = new BackProgressAnimator();
    public final Interpolator gestureInterpolator = Interpolators.BACK_GESTURE;
    public final Interpolator verticalMoveInterpolator = new DecelerateInterpolator();
    public final FloatValueHolder postCommitFlingScale = new FloatValueHolder(100.0f);
    public float lastPostCommitFlingScale = 100.0f;
    public final SpringForce postCommitFlingSpring = new SpringForce(100.0f).setStiffness(200.0f).setDampingRatio(0.75f);
    public final ProgressVelocityTracker velocityTracker = new ProgressVelocityTracker();

    public final class Callback extends IOnBackInvokedCallback.Default {
        public Callback() {
        }

        public final void onBackCancelled() {
            final CrossActivityBackAnimation crossActivityBackAnimation = CrossActivityBackAnimation.this;
            crossActivityBackAnimation.triggerBack = false;
            crossActivityBackAnimation.progressAnimator.onBackCancelled(new Runnable() { // from class: com.android.wm.shell.back.CrossActivityBackAnimation$Callback$onBackCancelled$1
                @Override // java.lang.Runnable
                public final void run() {
                    crossActivityBackAnimation.finishAnimation();
                }
            });
        }

        public final void onBackInvoked() {
            CrossActivityBackAnimation crossActivityBackAnimation = CrossActivityBackAnimation.this;
            crossActivityBackAnimation.triggerBack = true;
            crossActivityBackAnimation.progressAnimator.reset();
            CrossActivityBackAnimation crossActivityBackAnimation2 = CrossActivityBackAnimation.this;
            ProgressVelocityTracker progressVelocityTracker = crossActivityBackAnimation2.velocityTracker;
            progressVelocityTracker.velocityTracker.computeCurrentVelocity(1000);
            crossActivityBackAnimation2.onGestureCommitted(progressVelocityTracker.velocityTracker.getXVelocity());
        }

        public final void onBackProgressed(BackMotionEvent backMotionEvent) {
            CrossActivityBackAnimation.this.triggerBack = backMotionEvent.getTriggerBack();
            CrossActivityBackAnimation.this.progressAnimator.onBackProgressed(backMotionEvent);
        }

        public final void onBackStarted(BackMotionEvent backMotionEvent) {
            CrossActivityBackAnimation.this.progressAnimator.removeOnBackCancelledFinishCallback();
            CrossActivityBackAnimation.this.startBackAnimation(backMotionEvent);
            final CrossActivityBackAnimation crossActivityBackAnimation = CrossActivityBackAnimation.this;
            crossActivityBackAnimation.progressAnimator.onBackStarted(backMotionEvent, new BackProgressAnimator.ProgressCallback() { // from class: com.android.wm.shell.back.CrossActivityBackAnimation$Callback$onBackStarted$1
                public final void onProgressUpdate(BackEvent backEvent) {
                    CrossActivityBackAnimation crossActivityBackAnimation2 = crossActivityBackAnimation;
                    float interpolation = crossActivityBackAnimation2.gestureInterpolator.getInterpolation(backEvent.getProgress());
                    crossActivityBackAnimation2.gestureProgress = interpolation;
                    CrossActivityBackAnimationKt.setInterpolatedRectF(crossActivityBackAnimation2.currentClosingRect, crossActivityBackAnimation2.startClosingRect, crossActivityBackAnimation2.targetClosingRect, interpolation);
                    RectF rectF = crossActivityBackAnimation2.currentClosingRect;
                    float touchY = backEvent.getTouchY();
                    int iHeight = crossActivityBackAnimation2.backAnimRect.height();
                    float f = touchY - crossActivityBackAnimation2.initialTouchPos.y;
                    float f2 = iHeight;
                    float f3 = f2 / 2.0f;
                    float fMax = Math.max(0.0f, ((f2 - rectF.height()) / 2.0f) - crossActivityBackAnimation2.displayBoundsMargin) * ((DecelerateInterpolator) crossActivityBackAnimation2.verticalMoveInterpolator).getInterpolation(Math.min(f3, Math.abs(f)) / f3) * (f < 0.0f ? -1 : 1);
                    crossActivityBackAnimation2.currentClosingRect.offset(0.0f, fMax);
                    RemoteAnimationTarget remoteAnimationTarget = crossActivityBackAnimation2.closingTarget;
                    CrossActivityBackAnimation.applyTransform$default(crossActivityBackAnimation2, remoteAnimationTarget != null ? remoteAnimationTarget.leash : null, crossActivityBackAnimation2.currentClosingRect, 1.0f, null, null, 24);
                    CrossActivityBackAnimationKt.setInterpolatedRectF(crossActivityBackAnimation2.currentEnteringRect, crossActivityBackAnimation2.startEnteringRect, crossActivityBackAnimation2.targetEnteringRect, interpolation);
                    if (crossActivityBackAnimation2.getAllowEnteringYShift()) {
                        crossActivityBackAnimation2.currentEnteringRect.offset(0.0f, fMax);
                    }
                    Transformation preCommitEnteringBaseTransformation = crossActivityBackAnimation2.getPreCommitEnteringBaseTransformation(interpolation);
                    RemoteAnimationTarget remoteAnimationTarget2 = crossActivityBackAnimation2.enteringTarget;
                    CrossActivityBackAnimation.applyTransform$default(crossActivityBackAnimation2, remoteAnimationTarget2 != null ? remoteAnimationTarget2.leash : null, crossActivityBackAnimation2.currentEnteringRect, preCommitEnteringBaseTransformation != null ? preCommitEnteringBaseTransformation.getAlpha() : 1.0f, preCommitEnteringBaseTransformation, null, 16);
                    crossActivityBackAnimation2.applyTransaction();
                    crossActivityBackAnimation2.background.customizeStatusBarAppearance((int) crossActivityBackAnimation2.currentClosingRect.top);
                    long frameTimeMillis = backEvent.getFrameTimeMillis();
                    ProgressVelocityTracker progressVelocityTracker = crossActivityBackAnimation2.velocityTracker;
                    if (progressVelocityTracker.downTime == -1) {
                        progressVelocityTracker.downTime = frameTimeMillis;
                    }
                    progressVelocityTracker.velocityTracker.addMovement(MotionEvent.obtain(progressVelocityTracker.downTime, frameTimeMillis, 2, interpolation, 0.0f, 0));
                }
            });
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class FlingMode {
        public static final /* synthetic */ FlingMode[] $VALUES;
        public static final FlingMode FLING_BOUNCE;
        public static final FlingMode FLING_SHRINK;
        public static final FlingMode NO_FLING;

        static {
            FlingMode flingMode = new FlingMode("NO_FLING", 0);
            NO_FLING = flingMode;
            FlingMode flingMode2 = new FlingMode("FLING_SHRINK", 1);
            FLING_SHRINK = flingMode2;
            FlingMode flingMode3 = new FlingMode("FLING_BOUNCE", 2);
            FLING_BOUNCE = flingMode3;
            FlingMode[] flingModeArr = {flingMode, flingMode2, flingMode3};
            $VALUES = flingModeArr;
            EnumEntriesKt.enumEntries(flingModeArr);
        }

        private FlingMode(String str, int i) {
        }

        public static FlingMode valueOf(String str) {
            return (FlingMode) Enum.valueOf(FlingMode.class, str);
        }

        public static FlingMode[] values() {
            return (FlingMode[]) $VALUES.clone();
        }
    }

    public final class Runner extends IRemoteAnimationRunner.Default {
        public Runner() {
        }

        public final void onAnimationCancelled() {
            CrossActivityBackAnimation.this.finishAnimation();
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            ProtoLog.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, "Start back to activity animation.", new Object[0]);
            for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr) {
                int i2 = remoteAnimationTarget.mode;
                if (i2 == 0) {
                    CrossActivityBackAnimation.this.enteringTarget = remoteAnimationTarget;
                } else if (i2 == 1) {
                    CrossActivityBackAnimation.this.closingTarget = remoteAnimationTarget;
                }
            }
            CrossActivityBackAnimation.this.finishCallback = iRemoteAnimationFinishedCallback;
        }
    }

    static {
        new Companion(null);
    }

    public CrossActivityBackAnimation(Context context, BackAnimationBackground backAnimationBackground, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, SurfaceControl.Transaction transaction, Handler handler) {
        this.context = context;
        this.background = backAnimationBackground;
        this.rootTaskDisplayAreaOrganizer = rootTaskDisplayAreaOrganizer;
        this.transaction = transaction;
        this.cornerRadius = ScreenDecorationsUtils.getWindowCornerRadius(context);
        this.statusbarHeight = SystemBarUtils.getStatusBarHeight(context);
        this.backAnimationRunner = new BackAnimationRunner(new Callback(), new Runner(), context, 84, handler);
        this.displayBoundsMargin = context.getResources().getDimension(R.dimen.cross_task_back_vertical_margin);
    }

    public static /* synthetic */ void applyTransform$default(CrossActivityBackAnimation crossActivityBackAnimation, SurfaceControl surfaceControl, RectF rectF, float f, Transformation transformation, FlingMode flingMode, int i) {
        if ((i & 8) != 0) {
            transformation = null;
        }
        Transformation transformation2 = transformation;
        if ((i & 16) != 0) {
            flingMode = FlingMode.NO_FLING;
        }
        crossActivityBackAnimation.applyTransform(surfaceControl, rectF, f, transformation2, flingMode);
    }

    public final void applyTransaction() {
        this.transaction.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
        this.transaction.apply();
    }

    public final void applyTransform(SurfaceControl surfaceControl, RectF rectF, float f, Transformation transformation, FlingMode flingMode) {
        Matrix matrix;
        float f2;
        if (surfaceControl == null || !surfaceControl.isValid()) {
            return;
        }
        this.tempRectF.set(rectF);
        if (flingMode != FlingMode.NO_FLING) {
            float fMin = Math.min(this.postCommitFlingScale.getValue() / 100.0f, flingMode == FlingMode.FLING_BOUNCE ? 1.0f : this.lastPostCommitFlingScale);
            this.lastPostCommitFlingScale = fMin;
            CrossActivityBackAnimationKt.scaleCentered$default(this.tempRectF, fMin);
        }
        float fWidth = this.tempRectF.width() / this.backAnimRect.width();
        if (transformation == null || (matrix = transformation.getMatrix()) == null) {
            matrix = this.transformMatrix;
            matrix.reset();
        }
        if (this.isLetterboxed && this.enteringHasSameLetterbox) {
            RemoteAnimationTarget remoteAnimationTarget = this.closingTarget;
            remoteAnimationTarget.getClass();
            f2 = remoteAnimationTarget.localBounds.left;
        } else {
            f2 = 0.0f;
        }
        matrix.postScale(fWidth, fWidth, f2, 0.0f);
        RectF rectF2 = this.tempRectF;
        matrix.postTranslate(rectF2.left, rectF2.top);
        this.transaction.setAlpha(surfaceControl, f).setMatrix(surfaceControl, matrix, this.tmpFloat9).setCrop(surfaceControl, this.cropRect).setCornerRadius(surfaceControl, this.cornerRadius);
    }

    public final SurfaceControl ensureLetterbox(Rect rect) {
        SurfaceControl.Builder hidden = new SurfaceControl.Builder().setName("Cross-Activity back animation letterbox").setCallsite("CrossActivityBackAnimation").setColorLayer().setOpaque(true).setHidden(false);
        this.rootTaskDisplayAreaOrganizer.attachToDisplayArea(0, hidden);
        SurfaceControl surfaceControlBuild = hidden.build();
        SurfaceControl.Transaction crop = this.transaction.setColor(surfaceControlBuild, new float[]{Color.red(this.letterboxColor) / 255.0f, Color.green(this.letterboxColor) / 255.0f, Color.blue(this.letterboxColor) / 255.0f}).setCrop(surfaceControlBuild, rect);
        RemoteAnimationTarget remoteAnimationTarget = this.closingTarget;
        remoteAnimationTarget.getClass();
        crop.setRelativeLayer(surfaceControlBuild, remoteAnimationTarget.leash, 1).show(surfaceControlBuild);
        return surfaceControlBuild;
    }

    public void finishAnimation() {
        SurfaceControl surfaceControl;
        RemoteAnimationTarget remoteAnimationTarget = this.enteringTarget;
        if (remoteAnimationTarget != null) {
            SurfaceControl surfaceControl2 = remoteAnimationTarget.leash;
            if (surfaceControl2 != null && surfaceControl2.isValid()) {
                this.transaction.setCornerRadius(remoteAnimationTarget.leash, 0.0f);
                if (!this.triggerBack) {
                    this.transaction.setAlpha(remoteAnimationTarget.leash, 0.0f);
                }
                remoteAnimationTarget.leash.release();
            }
            this.enteringTarget = null;
        }
        RemoteAnimationTarget remoteAnimationTarget2 = this.closingTarget;
        if (remoteAnimationTarget2 != null && (surfaceControl = remoteAnimationTarget2.leash) != null) {
            surfaceControl.release();
        }
        this.closingTarget = null;
        SurfaceControl.Transaction transaction = this.transaction;
        BackAnimationBackground backAnimationBackground = this.background;
        SurfaceControl surfaceControl3 = backAnimationBackground.mBackgroundSurface;
        if (surfaceControl3 != null) {
            if (surfaceControl3.isValid()) {
                transaction.remove(backAnimationBackground.mBackgroundSurface);
            }
            backAnimationBackground.mBackgroundSurface = null;
            backAnimationBackground.mIsRequestingStatusBarAppearance = false;
        }
        applyTransaction();
        this.transformMatrix.reset();
        this.initialTouchPos.set(0.0f, 0.0f);
        try {
            IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = this.finishCallback;
            if (iRemoteAnimationFinishedCallback != null) {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.finishCallback = null;
        SurfaceControl surfaceControl4 = this.scrimLayer;
        if (surfaceControl4 != null && surfaceControl4.isValid()) {
            this.transaction.remove(surfaceControl4);
            applyTransaction();
        }
        this.scrimLayer = null;
        SurfaceControl surfaceControl5 = this.leftLetterboxLayer;
        if ((surfaceControl5 != null && surfaceControl5.isValid()) || ((surfaceControl5 = this.rightLetterboxLayer) != null && surfaceControl5.isValid())) {
            this.transaction.remove(surfaceControl5);
            applyTransaction();
        }
        this.leftLetterboxLayer = null;
        this.rightLetterboxLayer = null;
        this.isLetterboxed = false;
        this.enteringHasSameLetterbox = false;
        this.lastPostCommitFlingScale = 100.0f;
        this.gestureProgress = 0.0f;
        this.triggerBack = false;
        ProgressVelocityTracker progressVelocityTracker = this.velocityTracker;
        progressVelocityTracker.velocityTracker.clear();
        progressVelocityTracker.downTime = -1L;
    }

    public abstract boolean getAllowEnteringYShift();

    public abstract long getPostCommitAnimationDuration();

    public Transformation getPreCommitEnteringBaseTransformation(float f) {
        return null;
    }

    @Override // com.android.wm.shell.back.ShellBackAnimation
    public final BackAnimationRunner getRunner() {
        return this.backAnimationRunner;
    }

    @Override // com.android.wm.shell.back.ShellBackAnimation
    public final void onConfigurationChanged() {
        this.cornerRadius = ScreenDecorationsUtils.getWindowCornerRadius(this.context);
        this.statusbarHeight = SystemBarUtils.getStatusBarHeight(this.context);
    }

    public void onGestureCommitted(float f) {
        RemoteAnimationTarget remoteAnimationTarget = this.closingTarget;
        if ((remoteAnimationTarget != null ? remoteAnimationTarget.leash : null) != null) {
            RemoteAnimationTarget remoteAnimationTarget2 = this.enteringTarget;
            if ((remoteAnimationTarget2 != null ? remoteAnimationTarget2.leash : null) != null) {
                remoteAnimationTarget2.getClass();
                if (remoteAnimationTarget2.leash.isValid()) {
                    RemoteAnimationTarget remoteAnimationTarget3 = this.closingTarget;
                    remoteAnimationTarget3.getClass();
                    if (remoteAnimationTarget3.leash.isValid()) {
                        int i = this.swipeEdge;
                        float f2 = f * 100.0f * 0.100000024f * ((i == 0 || i == 1) ? 2.0f : 1.0f);
                        if (this.gestureProgress < 0.1f && f2 < 120.0f) {
                            f2 = 120.0f;
                        }
                        SpringAnimation spring = new SpringAnimation(this.postCommitFlingScale, 100.0f).setStartVelocity(-RangesKt___RangesKt.coerceIn(f2, 0.0f, 1000.0f)).setStartValue(100.0f).setSpring(this.postCommitFlingSpring);
                        spring.start();
                        spring.doAnimationFrame(Choreographer.getInstance().getLastFrameTimeNanos() / 1000000);
                        ValueAnimator duration = ValueAnimator.ofFloat(1.0f, 0.0f).setDuration(getPostCommitAnimationDuration());
                        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.back.CrossActivityBackAnimation.onGestureCommitted.1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                float animatedFraction = valueAnimator.getAnimatedFraction();
                                CrossActivityBackAnimation.this.onPostCommitProgress(animatedFraction);
                                if (animatedFraction > 0.8f) {
                                    CrossActivityBackAnimation.this.background.mCustomizer.customizeStatusBarAppearance(null);
                                }
                            }
                        });
                        duration.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.back.CrossActivityBackAnimation.onGestureCommitted.2
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                CrossActivityBackAnimation.this.background.mCustomizer.customizeStatusBarAppearance(null);
                                CrossActivityBackAnimation.this.finishAnimation();
                            }
                        });
                        duration.start();
                        return;
                    }
                }
            }
        }
        finishAnimation();
    }

    public void onPostCommitProgress(float f) {
        SurfaceControl surfaceControl = this.scrimLayer;
        if (surfaceControl != null) {
            this.transaction.setAlpha(surfaceControl, (1.0f - f) * this.maxScrimAlpha);
        }
    }

    @Override // com.android.wm.shell.back.ShellBackAnimation
    public boolean prepareNextAnimation(BackNavigationInfo.CustomAnimationInfo customAnimationInfo, int i) {
        this.letterboxColor = i;
        return false;
    }

    public abstract void preparePreCommitClosingRectMovement(int i);

    public abstract void preparePreCommitEnteringRectMovement();

    /* JADX WARN: Removed duplicated region for block: B:12:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void startBackAnimation(BackMotionEvent backMotionEvent) {
        boolean z;
        int i;
        Rect rect;
        Rect bounds;
        if (this.enteringTarget == null || this.closingTarget == null) {
            ProtoLog.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, "Entering target or closing target is null.", new Object[0]);
            return;
        }
        this.swipeEdge = backMotionEvent.getSwipeEdge();
        this.triggerBack = backMotionEvent.getTriggerBack();
        this.initialTouchPos.set(backMotionEvent.getTouchX(), backMotionEvent.getTouchY());
        this.transaction.setAnimationTransaction();
        RemoteAnimationTarget remoteAnimationTarget = this.closingTarget;
        remoteAnimationTarget.getClass();
        boolean zIsTopActivityLetterboxed = remoteAnimationTarget.taskInfo.appCompatTaskInfo.isTopActivityLetterboxed();
        this.isLetterboxed = zIsTopActivityLetterboxed;
        if (zIsTopActivityLetterboxed) {
            RemoteAnimationTarget remoteAnimationTarget2 = this.closingTarget;
            remoteAnimationTarget2.getClass();
            Rect rect2 = remoteAnimationTarget2.localBounds;
            RemoteAnimationTarget remoteAnimationTarget3 = this.enteringTarget;
            remoteAnimationTarget3.getClass();
            z = rect2.equals(remoteAnimationTarget3.localBounds);
        }
        this.enteringHasSameLetterbox = z;
        if (!this.isLetterboxed || z) {
            Rect rect3 = this.backAnimRect;
            RemoteAnimationTarget remoteAnimationTarget4 = this.closingTarget;
            remoteAnimationTarget4.getClass();
            rect3.set(remoteAnimationTarget4.localBounds);
        } else {
            Rect rect4 = this.backAnimRect;
            RemoteAnimationTarget remoteAnimationTarget5 = this.closingTarget;
            remoteAnimationTarget5.getClass();
            rect4.set(remoteAnimationTarget5.windowConfiguration.getBounds());
        }
        this.backAnimRect.offsetTo(0, 0);
        preparePreCommitClosingRectMovement(backMotionEvent.getSwipeEdge());
        preparePreCommitEnteringRectMovement();
        RemoteAnimationTarget remoteAnimationTarget6 = this.closingTarget;
        remoteAnimationTarget6.getClass();
        Rect bounds2 = remoteAnimationTarget6.windowConfiguration.getBounds();
        int backgroundColor = this.customizedBackgroundColor;
        if (backgroundColor == 0) {
            if (this.isLetterboxed) {
                backgroundColor = this.letterboxColor;
            } else {
                RemoteAnimationTarget remoteAnimationTarget7 = this.enteringTarget;
                if (remoteAnimationTarget7 != null) {
                    ActivityManager.TaskDescription taskDescription = remoteAnimationTarget7.taskInfo.taskDescription;
                    taskDescription.getClass();
                    backgroundColor = taskDescription.getBackgroundColor();
                } else {
                    i = 0;
                }
            }
            i = backgroundColor;
        } else {
            i = backgroundColor;
        }
        SurfaceControl.Transaction transaction = this.transaction;
        int i2 = this.statusbarHeight;
        RemoteAnimationTarget remoteAnimationTarget8 = this.closingTarget;
        remoteAnimationTarget8.getClass();
        if (remoteAnimationTarget8.windowConfiguration.tasksAreFloating()) {
            RemoteAnimationTarget remoteAnimationTarget9 = this.closingTarget;
            remoteAnimationTarget9.getClass();
            rect = remoteAnimationTarget9.localBounds;
        } else {
            rect = null;
        }
        Rect rect5 = rect;
        float f = this.cornerRadius;
        RemoteAnimationTarget remoteAnimationTarget10 = this.closingTarget;
        remoteAnimationTarget10.getClass();
        remoteAnimationTarget10.taskInfo.getDisplayId();
        this.background.ensureBackground(bounds2, i, transaction, i2, rect5, f);
        if (this.scrimLayer == null) {
            boolean z2 = (this.context.getResources().getConfiguration().uiMode & 48) == 32;
            SurfaceControl.Builder hidden = new SurfaceControl.Builder().setName("Cross-Activity back animation scrim").setCallsite("CrossActivityBackAnimation").setColorLayer().setOpaque(false).setHidden(false);
            this.rootTaskDisplayAreaOrganizer.attachToDisplayArea(0, hidden);
            this.scrimLayer = hidden.build();
            float[] fArr = {0.0f, 0.0f, 0.0f};
            this.maxScrimAlpha = z2 ? 0.8f : 0.2f;
            if (this.isLetterboxed) {
                RemoteAnimationTarget remoteAnimationTarget11 = this.closingTarget;
                remoteAnimationTarget11.getClass();
                bounds = remoteAnimationTarget11.windowConfiguration.getBounds();
            } else {
                RemoteAnimationTarget remoteAnimationTarget12 = this.closingTarget;
                remoteAnimationTarget12.getClass();
                bounds = remoteAnimationTarget12.localBounds;
            }
            SurfaceControl.Transaction color = this.transaction.setColor(this.scrimLayer, fArr);
            SurfaceControl surfaceControl = this.scrimLayer;
            surfaceControl.getClass();
            SurfaceControl.Transaction alpha = color.setAlpha(surfaceControl, this.maxScrimAlpha);
            SurfaceControl surfaceControl2 = this.scrimLayer;
            surfaceControl2.getClass();
            SurfaceControl.Transaction crop = alpha.setCrop(surfaceControl2, bounds);
            SurfaceControl surfaceControl3 = this.scrimLayer;
            surfaceControl3.getClass();
            RemoteAnimationTarget remoteAnimationTarget13 = this.closingTarget;
            remoteAnimationTarget13.getClass();
            crop.setRelativeLayer(surfaceControl3, remoteAnimationTarget13.leash, -1).show(this.scrimLayer);
        }
        if (this.isLetterboxed && this.enteringHasSameLetterbox) {
            Rect rect6 = this.cropRect;
            RemoteAnimationTarget remoteAnimationTarget14 = this.closingTarget;
            remoteAnimationTarget14.getClass();
            int i3 = remoteAnimationTarget14.localBounds.left;
            RemoteAnimationTarget remoteAnimationTarget15 = this.closingTarget;
            remoteAnimationTarget15.getClass();
            int i4 = remoteAnimationTarget15.localBounds.right;
            RemoteAnimationTarget remoteAnimationTarget16 = this.closingTarget;
            remoteAnimationTarget16.getClass();
            rect6.set(i3, 0, i4, remoteAnimationTarget16.windowConfiguration.getBounds().height());
            RemoteAnimationTarget remoteAnimationTarget17 = this.closingTarget;
            if (remoteAnimationTarget17 != null) {
                if (remoteAnimationTarget17.localBounds.left != 0 && this.leftLetterboxLayer == null) {
                    this.leftLetterboxLayer = ensureLetterbox(new Rect(0, remoteAnimationTarget17.windowConfiguration.getBounds().top, remoteAnimationTarget17.localBounds.left, remoteAnimationTarget17.windowConfiguration.getBounds().bottom));
                }
                if (remoteAnimationTarget17.localBounds.right != remoteAnimationTarget17.windowConfiguration.getBounds().right && this.rightLetterboxLayer == null) {
                    this.rightLetterboxLayer = ensureLetterbox(new Rect(remoteAnimationTarget17.localBounds.right, remoteAnimationTarget17.windowConfiguration.getBounds().top, remoteAnimationTarget17.windowConfiguration.getBounds().right, remoteAnimationTarget17.windowConfiguration.getBounds().bottom));
                }
            }
        } else {
            this.cropRect.set(this.backAnimRect);
        }
        applyTransaction();
    }
}
