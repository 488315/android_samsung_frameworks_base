package com.android.server.wm;

import android.animation.AnimationHandler;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Handler;
import android.os.PowerManagerInternal;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Choreographer;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.window.ScreenCapture;

import com.android.internal.graphics.SfVsyncFrameCallbackProvider;
import com.android.server.AnimationThread;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.wm.SurfaceAnimationRunner.SfValueAnimator;

import com.samsung.android.rune.CoreRune;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class SurfaceAnimationRunner {
    public final AnimationHandler mAnimationHandler;
    public boolean mAnimationStartDeferred;
    public final AnimatorFactory mAnimatorFactory;
    public boolean mApplyScheduled;
    public final SurfaceAnimationRunner$$ExternalSyntheticLambda0 mApplyTransactionRunnable;
    Choreographer mChoreographer;
    public final ExecutorService mEdgeExtensionExecutor;
    public final ArrayMap mEdgeExtensions;
    public final SurfaceControl.Transaction mFrameTransaction;
    final ArrayMap mPendingAnimations;
    public final PowerManagerInternal mPowerManagerInternal;
    final ArrayMap mPreProcessingAnimations;
    final ArrayMap mRunningAnimations;
    public final Handler mSurfaceAnimationHandler;
    public final Object mLock = new Object();
    public final Object mCancelLock = new Object();
    public final Object mEdgeExtensionLock = new Object();
    public final Handler mAnimationThreadHandler = AnimationThread.getHandler();

    interface AnimatorFactory {
        SfValueAnimator makeAnimator();
    }

    public final class RunningAnimation {
        public ValueAnimator mAnim;
        public final LocalAnimationAdapter.AnimationSpec mAnimSpec;
        public boolean mCancelled;
        public final Runnable mFinishCallback;
        public final SurfaceControl mLeash;

        public RunningAnimation(
                LocalAnimationAdapter.AnimationSpec animationSpec,
                SurfaceControl surfaceControl,
                LocalAnimationAdapter$$ExternalSyntheticLambda0
                        localAnimationAdapter$$ExternalSyntheticLambda0) {
            this.mAnimSpec = animationSpec;
            this.mLeash = surfaceControl;
            this.mFinishCallback = localAnimationAdapter$$ExternalSyntheticLambda0;
        }
    }

    public final class SfValueAnimator extends ValueAnimator {
        public SfValueAnimator() {
            setFloatValues(FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f);
        }

        public final AnimationHandler getAnimationHandler() {
            return SurfaceAnimationRunner.this.mAnimationHandler;
        }
    }

    public SurfaceAnimationRunner(
            AnimationHandler.AnimationFrameCallbackProvider animationFrameCallbackProvider,
            AnimatorFactory animatorFactory,
            SurfaceControl.Transaction transaction,
            PowerManagerInternal powerManagerInternal) {
        Handler handler = SurfaceAnimationThread.getHandler();
        this.mSurfaceAnimationHandler = handler;
        final int i = 0;
        this.mApplyTransactionRunnable =
                new Runnable(
                        this) { // from class:
                                // com.android.server.wm.SurfaceAnimationRunner$$ExternalSyntheticLambda0
                    public final /* synthetic */ SurfaceAnimationRunner f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i2 = i;
                        SurfaceAnimationRunner surfaceAnimationRunner = this.f$0;
                        switch (i2) {
                            case 0:
                                surfaceAnimationRunner.mFrameTransaction.setAnimationTransaction();
                                surfaceAnimationRunner.mFrameTransaction.setFrameTimelineVsync(
                                        surfaceAnimationRunner.mChoreographer.getVsyncId());
                                surfaceAnimationRunner.mFrameTransaction.apply();
                                surfaceAnimationRunner.mApplyScheduled = false;
                                break;
                            default:
                                surfaceAnimationRunner.getClass();
                                surfaceAnimationRunner.mChoreographer =
                                        Choreographer.getSfInstance();
                                break;
                        }
                    }
                };
        this.mEdgeExtensionExecutor = Executors.newFixedThreadPool(2);
        this.mPendingAnimations = new ArrayMap();
        this.mPreProcessingAnimations = new ArrayMap();
        this.mRunningAnimations = new ArrayMap();
        this.mEdgeExtensions = new ArrayMap();
        final int i2 = 1;
        handler.runWithScissors(
                new Runnable(
                        this) { // from class:
                                // com.android.server.wm.SurfaceAnimationRunner$$ExternalSyntheticLambda0
                    public final /* synthetic */ SurfaceAnimationRunner f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i22 = i2;
                        SurfaceAnimationRunner surfaceAnimationRunner = this.f$0;
                        switch (i22) {
                            case 0:
                                surfaceAnimationRunner.mFrameTransaction.setAnimationTransaction();
                                surfaceAnimationRunner.mFrameTransaction.setFrameTimelineVsync(
                                        surfaceAnimationRunner.mChoreographer.getVsyncId());
                                surfaceAnimationRunner.mFrameTransaction.apply();
                                surfaceAnimationRunner.mApplyScheduled = false;
                                break;
                            default:
                                surfaceAnimationRunner.getClass();
                                surfaceAnimationRunner.mChoreographer =
                                        Choreographer.getSfInstance();
                                break;
                        }
                    }
                },
                0L);
        this.mFrameTransaction = transaction;
        AnimationHandler animationHandler = new AnimationHandler();
        this.mAnimationHandler = animationHandler;
        animationHandler.setProvider(
                animationFrameCallbackProvider == null
                        ? new SfVsyncFrameCallbackProvider(this.mChoreographer)
                        : animationFrameCallbackProvider);
        this.mAnimatorFactory =
                animatorFactory == null
                        ? new AnimatorFactory() { // from class:
                                                  // com.android.server.wm.SurfaceAnimationRunner$$ExternalSyntheticLambda2
                            @Override // com.android.server.wm.SurfaceAnimationRunner.AnimatorFactory
                            public final SurfaceAnimationRunner.SfValueAnimator makeAnimator() {
                                SurfaceAnimationRunner surfaceAnimationRunner =
                                        SurfaceAnimationRunner.this;
                                surfaceAnimationRunner.getClass();
                                return surfaceAnimationRunner.new SfValueAnimator();
                            }
                        }
                        : animatorFactory;
        this.mPowerManagerInternal = powerManagerInternal;
    }

    public final void continueStartingAnimations() {
        synchronized (this.mLock) {
            try {
                this.mAnimationStartDeferred = false;
                if (!this.mPendingAnimations.isEmpty() && this.mPreProcessingAnimations.isEmpty()) {
                    this.mChoreographer.postFrameCallback(
                            new SurfaceAnimationRunner$$ExternalSyntheticLambda5(this));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void createExtensionSurface(
            SurfaceControl surfaceControl,
            Rect rect,
            Rect rect2,
            int i,
            int i2,
            String str,
            SurfaceControl.Transaction transaction) {
        int i3;
        Trace.traceBegin(32L, "createExtensionSurface");
        ScreenCapture.ScreenshotHardwareBuffer captureLayers =
                ScreenCapture.captureLayers(
                        new ScreenCapture.LayerCaptureArgs.Builder(surfaceControl)
                                .setSourceCrop(rect)
                                .setFrameScale(1.0f)
                                .setPixelFormat(1)
                                .setChildrenOnly(true)
                                .setAllowProtected(true)
                                .setCaptureSecureLayers(true)
                                .build());
        if (captureLayers == null) {
            Log.e(
                    "SurfaceAnimationRunner",
                    "Failed to create edge extension - edge buffer is null");
        } else {
            SurfaceControl build =
                    new SurfaceControl.Builder()
                            .setName(str)
                            .setHidden(true)
                            .setCallsite("DefaultTransitionHandler#startAnimation")
                            .setOpaque(true)
                            .setBufferSize(rect2.width(), rect2.height())
                            .build();
            Bitmap asBitmap = captureLayers.asBitmap();
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            BitmapShader bitmapShader = new BitmapShader(asBitmap, tileMode, tileMode);
            Paint paint = new Paint();
            if (CoreRune.FW_EDGE_EXTENSION_ANIM_DEBUG) {
                switch (str) {
                    case "Right Edge Extension":
                        i3 = -16776961;
                        break;
                    case "Left Edge Extension":
                        i3 = -65536;
                        break;
                    case "Bottom Edge Extension":
                        i3 = -16711681;
                        break;
                    case "Top Edge Extension":
                        i3 = -16711936;
                        break;
                    default:
                        i3 = -65281;
                        break;
                }
                paint.setColor(i3);
            } else {
                paint.setShader(bitmapShader);
            }
            Surface surface = new Surface(build);
            Canvas lockHardwareCanvas = surface.lockHardwareCanvas();
            lockHardwareCanvas.drawRect(rect2, paint);
            surface.unlockCanvasAndPost(lockHardwareCanvas);
            surface.release();
            synchronized (this.mEdgeExtensionLock) {
                try {
                    if (this.mEdgeExtensions.containsKey(surfaceControl)) {
                        transaction.reparent(build, surfaceControl);
                        transaction.setLayer(build, Integer.MIN_VALUE);
                        transaction.setPosition(build, i, i2);
                        transaction.setVisibility(build, true);
                        ((ArrayList) this.mEdgeExtensions.get(surfaceControl)).add(build);
                    } else {
                        transaction.remove(build);
                    }
                } finally {
                }
            }
        }
        Trace.traceEnd(32L);
    }

    public final void onAnimationCancelled(SurfaceControl surfaceControl) {
        synchronized (this.mLock) {
            try {
                if (this.mPendingAnimations.containsKey(surfaceControl)) {
                    this.mPendingAnimations.remove(surfaceControl);
                    return;
                }
                if (this.mPreProcessingAnimations.containsKey(surfaceControl)) {
                    this.mPreProcessingAnimations.remove(surfaceControl);
                    return;
                }
                final RunningAnimation runningAnimation =
                        (RunningAnimation) this.mRunningAnimations.get(surfaceControl);
                if (runningAnimation != null) {
                    this.mRunningAnimations.remove(surfaceControl);
                    synchronized (this.mCancelLock) {
                        runningAnimation.mCancelled = true;
                    }
                    this.mSurfaceAnimationHandler.post(
                            new Runnable() { // from class:
                                             // com.android.server.wm.SurfaceAnimationRunner$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SurfaceAnimationRunner surfaceAnimationRunner =
                                            SurfaceAnimationRunner.this;
                                    SurfaceAnimationRunner.RunningAnimation runningAnimation2 =
                                            runningAnimation;
                                    surfaceAnimationRunner.getClass();
                                    runningAnimation2.mAnim.cancel();
                                    surfaceAnimationRunner.mFrameTransaction
                                            .setAnimationTransaction();
                                    surfaceAnimationRunner.mFrameTransaction.setFrameTimelineVsync(
                                            surfaceAnimationRunner.mChoreographer.getVsyncId());
                                    surfaceAnimationRunner.mFrameTransaction.apply();
                                    surfaceAnimationRunner.mApplyScheduled = false;
                                }
                            });
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startAnimation(
            final LocalAnimationAdapter.AnimationSpec animationSpec,
            final SurfaceControl surfaceControl,
            SurfaceControl.Transaction transaction,
            LocalAnimationAdapter$$ExternalSyntheticLambda0
                    localAnimationAdapter$$ExternalSyntheticLambda0) {
        synchronized (this.mLock) {
            final RunningAnimation runningAnimation =
                    new RunningAnimation(
                            animationSpec,
                            surfaceControl,
                            localAnimationAdapter$$ExternalSyntheticLambda0);
            boolean z =
                    animationSpec.asWindowAnimationSpec() != null
                            && animationSpec.asWindowAnimationSpec().mAnimation.hasExtension();
            if (z) {
                ArrayList arrayList = new ArrayList();
                synchronized (this.mEdgeExtensionLock) {
                    this.mEdgeExtensions.put(surfaceControl, arrayList);
                }
                this.mPreProcessingAnimations.put(surfaceControl, runningAnimation);
                transaction.addTransactionCommittedListener(
                        this.mEdgeExtensionExecutor,
                        new SurfaceControl
                                .TransactionCommittedListener() { // from class:
                                                                  // com.android.server.wm.SurfaceAnimationRunner$$ExternalSyntheticLambda4
                            @Override // android.view.SurfaceControl.TransactionCommittedListener
                            public final void onTransactionCommitted() {
                                SurfaceAnimationRunner.RunningAnimation runningAnimation2;
                                int i;
                                SurfaceAnimationRunner surfaceAnimationRunner =
                                        SurfaceAnimationRunner.this;
                                SurfaceControl surfaceControl2 = surfaceControl;
                                LocalAnimationAdapter.AnimationSpec animationSpec2 = animationSpec;
                                SurfaceAnimationRunner.RunningAnimation runningAnimation3 =
                                        runningAnimation;
                                surfaceAnimationRunner.getClass();
                                if (!surfaceControl2.isValid()) {
                                    Log.e("SurfaceAnimationRunner", "Animation leash is not valid");
                                    synchronized (surfaceAnimationRunner.mEdgeExtensionLock) {
                                        surfaceAnimationRunner.mEdgeExtensions.remove(
                                                surfaceControl2);
                                    }
                                    synchronized (surfaceAnimationRunner.mLock) {
                                        surfaceAnimationRunner.mPreProcessingAnimations.remove(
                                                surfaceControl2);
                                    }
                                    return;
                                }
                                WindowAnimationSpec asWindowAnimationSpec =
                                        animationSpec2.asWindowAnimationSpec();
                                SurfaceControl.Transaction transaction2 =
                                        new SurfaceControl.Transaction();
                                Rect rect = asWindowAnimationSpec.mRootTaskBounds;
                                Animation animation = asWindowAnimationSpec.mAnimation;
                                Transformation transformation = new Transformation();
                                animation.getTransformationAt(
                                        FullScreenMagnificationGestureHandler.MAX_SCALE,
                                        transformation);
                                Transformation transformation2 = new Transformation();
                                animation.getTransformationAt(1.0f, transformation2);
                                Insets min =
                                        Insets.min(
                                                transformation.getInsets(),
                                                transformation2.getInsets());
                                int height = rect.height();
                                int width = rect.width();
                                if (min.left < 0) {
                                    int i2 = rect.left;
                                    runningAnimation2 = runningAnimation3;
                                    i = 0;
                                    surfaceAnimationRunner.createExtensionSurface(
                                            surfaceControl2,
                                            new Rect(i2, rect.top, i2 + 1, rect.bottom),
                                            new Rect(0, 0, -min.left, height),
                                            rect.left + min.left,
                                            rect.top,
                                            "Left Edge Extension",
                                            transaction2);
                                } else {
                                    runningAnimation2 = runningAnimation3;
                                    i = 0;
                                }
                                if (min.top < 0) {
                                    int i3 = rect.left;
                                    int i4 = rect.top;
                                    surfaceAnimationRunner.createExtensionSurface(
                                            surfaceControl2,
                                            new Rect(i3, i4, width, i4 + 1),
                                            new Rect(i, i, width, -min.top),
                                            rect.left,
                                            rect.top + min.top,
                                            "Top Edge Extension",
                                            transaction2);
                                }
                                if (min.right < 0) {
                                    int i5 = rect.right;
                                    surfaceAnimationRunner.createExtensionSurface(
                                            surfaceControl2,
                                            new Rect(i5 - 1, rect.top, i5, rect.bottom),
                                            new Rect(i, i, -min.right, height),
                                            rect.right,
                                            rect.top,
                                            "Right Edge Extension",
                                            transaction2);
                                }
                                if (min.bottom < 0) {
                                    int i6 = rect.left;
                                    int i7 = rect.bottom;
                                    surfaceAnimationRunner.createExtensionSurface(
                                            surfaceControl2,
                                            new Rect(i6, i7 - 1, rect.right, i7),
                                            new Rect(i, i, width, -min.bottom),
                                            rect.left,
                                            rect.bottom,
                                            "Bottom Edge Extension",
                                            transaction2);
                                }
                                synchronized (surfaceAnimationRunner.mLock) {
                                    SurfaceAnimationRunner.RunningAnimation runningAnimation4 =
                                            runningAnimation2;
                                    if (surfaceAnimationRunner.mPreProcessingAnimations.get(
                                                    surfaceControl2)
                                            == runningAnimation4) {
                                        synchronized (surfaceAnimationRunner.mEdgeExtensionLock) {
                                            try {
                                                if (!surfaceAnimationRunner.mEdgeExtensions
                                                        .isEmpty()) {
                                                    transaction2.apply();
                                                }
                                            } finally {
                                            }
                                        }
                                        surfaceAnimationRunner.mPreProcessingAnimations.remove(
                                                surfaceControl2);
                                        surfaceAnimationRunner.mPendingAnimations.put(
                                                surfaceControl2, runningAnimation4);
                                        if (!surfaceAnimationRunner.mAnimationStartDeferred
                                                && surfaceAnimationRunner.mPreProcessingAnimations
                                                        .isEmpty()) {
                                            surfaceAnimationRunner.mChoreographer.postFrameCallback(
                                                    new SurfaceAnimationRunner$$ExternalSyntheticLambda5(
                                                            surfaceAnimationRunner));
                                        }
                                    }
                                }
                            }
                        });
            }
            if (!z) {
                this.mPendingAnimations.put(surfaceControl, runningAnimation);
                if (!this.mAnimationStartDeferred && this.mPreProcessingAnimations.isEmpty()) {
                    this.mChoreographer.postFrameCallback(
                            new SurfaceAnimationRunner$$ExternalSyntheticLambda5(this));
                }
            }
            animationSpec.apply(transaction, surfaceControl, 0L);
        }
    }

    public final void startPendingAnimationsLocked() {
        for (int size = this.mPendingAnimations.size() - 1; size >= 0; size--) {
            final RunningAnimation runningAnimation =
                    (RunningAnimation) this.mPendingAnimations.valueAt(size);
            final SfValueAnimator makeAnimator = this.mAnimatorFactory.makeAnimator();
            makeAnimator.overrideDurationScale(1.0f);
            makeAnimator.setDuration(runningAnimation.mAnimSpec.getDuration());
            makeAnimator.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // com.android.server.wm.SurfaceAnimationRunner$$ExternalSyntheticLambda6
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            SurfaceAnimationRunner surfaceAnimationRunner =
                                    SurfaceAnimationRunner.this;
                            SurfaceAnimationRunner.RunningAnimation runningAnimation2 =
                                    runningAnimation;
                            ValueAnimator valueAnimator2 = makeAnimator;
                            synchronized (surfaceAnimationRunner.mCancelLock) {
                                try {
                                    if (!runningAnimation2.mCancelled) {
                                        long duration = valueAnimator2.getDuration();
                                        long currentPlayTime = valueAnimator2.getCurrentPlayTime();
                                        if (currentPlayTime <= duration) {
                                            duration = currentPlayTime;
                                        }
                                        runningAnimation2.mAnimSpec.apply(
                                                surfaceAnimationRunner.mFrameTransaction,
                                                runningAnimation2.mLeash,
                                                duration);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                            if (surfaceAnimationRunner.mApplyScheduled) {
                                return;
                            }
                            surfaceAnimationRunner.mChoreographer.postCallback(
                                    3, surfaceAnimationRunner.mApplyTransactionRunnable, null);
                            surfaceAnimationRunner.mApplyScheduled = true;
                        }
                    });
            makeAnimator.addListener(
                    new AnimatorListenerAdapter() { // from class:
                                                    // com.android.server.wm.SurfaceAnimationRunner.1
                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            synchronized (SurfaceAnimationRunner.this.mLock) {
                                SurfaceAnimationRunner.this.mRunningAnimations.remove(
                                        runningAnimation.mLeash);
                                synchronized (SurfaceAnimationRunner.this.mCancelLock) {
                                    try {
                                        RunningAnimation runningAnimation2 = runningAnimation;
                                        if (!runningAnimation2.mCancelled) {
                                            SurfaceAnimationRunner.this.mAnimationThreadHandler
                                                    .post(runningAnimation2.mFinishCallback);
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }

                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                            synchronized (SurfaceAnimationRunner.this.mCancelLock) {
                                try {
                                    RunningAnimation runningAnimation2 = runningAnimation;
                                    if (!runningAnimation2.mCancelled) {
                                        SurfaceAnimationRunner.this.mFrameTransaction.setAlpha(
                                                runningAnimation2.mLeash, 1.0f);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    });
            runningAnimation.mAnim = makeAnimator;
            this.mRunningAnimations.put(runningAnimation.mLeash, runningAnimation);
            makeAnimator.start();
            if (runningAnimation.mAnimSpec.canSkipFirstFrame()) {
                makeAnimator.setCurrentPlayTime(
                        this.mChoreographer.getFrameIntervalNanos() / 1000000);
            }
            makeAnimator.doAnimationFrame(this.mChoreographer.getFrameTime());
        }
        this.mPendingAnimations.clear();
    }
}
