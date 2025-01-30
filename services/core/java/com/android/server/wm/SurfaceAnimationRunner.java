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
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class SurfaceAnimationRunner {
  public final AnimationHandler mAnimationHandler;
  public boolean mAnimationStartDeferred;
  public final Handler mAnimationThreadHandler;
  public final AnimatorFactory mAnimatorFactory;
  public boolean mApplyScheduled;
  public final Runnable mApplyTransactionRunnable;
  public final Object mCancelLock;
  Choreographer mChoreographer;
  public final ExecutorService mEdgeExtensionExecutor;
  public final Object mEdgeExtensionLock;
  public final ArrayMap mEdgeExtensions;
  public final SurfaceControl.Transaction mFrameTransaction;
  public final Object mLock;
  public final ArrayList mPendingAnimationList;
  final ArrayMap mPendingAnimations;
  public final PowerManagerInternal mPowerManagerInternal;
  final ArrayMap mPreProcessingAnimations;
  final ArrayMap mRunningAnimations;
  public final Handler mSurfaceAnimationHandler;

  interface AnimatorFactory {
    ValueAnimator makeAnimator();
  }

  public SurfaceAnimationRunner(Supplier supplier, PowerManagerInternal powerManagerInternal) {
    this(null, null, (SurfaceControl.Transaction) supplier.get(), powerManagerInternal);
  }

  public SurfaceAnimationRunner(
      AnimationHandler.AnimationFrameCallbackProvider animationFrameCallbackProvider,
      AnimatorFactory animatorFactory,
      SurfaceControl.Transaction transaction,
      PowerManagerInternal powerManagerInternal) {
    this.mLock = new Object();
    this.mCancelLock = new Object();
    this.mEdgeExtensionLock = new Object();
    this.mAnimationThreadHandler = AnimationThread.getHandler();
    Handler handler = SurfaceAnimationThread.getHandler();
    this.mSurfaceAnimationHandler = handler;
    this.mApplyTransactionRunnable =
        new Runnable() { // from class:
                         // com.android.server.wm.SurfaceAnimationRunner$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            SurfaceAnimationRunner.this.applyTransaction();
          }
        };
    this.mEdgeExtensionExecutor = Executors.newFixedThreadPool(2);
    this.mPendingAnimations = new ArrayMap();
    this.mPendingAnimationList = new ArrayList();
    this.mPreProcessingAnimations = new ArrayMap();
    this.mRunningAnimations = new ArrayMap();
    this.mEdgeExtensions = new ArrayMap();
    handler.runWithScissors(
        new Runnable() { // from class:
                         // com.android.server.wm.SurfaceAnimationRunner$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            SurfaceAnimationRunner.this.lambda$new$0();
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
              public final ValueAnimator makeAnimator() {
                ValueAnimator lambda$new$1;
                lambda$new$1 = SurfaceAnimationRunner.this.lambda$new$1();
                return lambda$new$1;
              }
            }
            : animatorFactory;
    this.mPowerManagerInternal = powerManagerInternal;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0() {
    this.mChoreographer = Choreographer.getSfInstance();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ ValueAnimator lambda$new$1() {
    return new SfValueAnimator();
  }

  public void deferStartingAnimations() {
    synchronized (this.mLock) {
      this.mAnimationStartDeferred = true;
    }
  }

  public void continueStartingAnimations() {
    synchronized (this.mLock) {
      this.mAnimationStartDeferred = false;
      if (!this.mPendingAnimationList.isEmpty() && this.mPreProcessingAnimations.isEmpty()) {
        this.mChoreographer.postFrameCallback(
            new SurfaceAnimationRunner$$ExternalSyntheticLambda3(this));
      }
    }
  }

  public void startAnimation(
      final LocalAnimationAdapter.AnimationSpec animationSpec,
      final SurfaceControl surfaceControl,
      SurfaceControl.Transaction transaction,
      Runnable runnable) {
    synchronized (this.mLock) {
      final RunningAnimation runningAnimation =
          new RunningAnimation(animationSpec, surfaceControl, runnable);
      runningAnimation.setPosition(
          this.mPendingAnimationList.size() + this.mPreProcessingAnimations.size());
      boolean requiresEdgeExtension = requiresEdgeExtension(animationSpec);
      if (requiresEdgeExtension) {
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
                SurfaceAnimationRunner.this.lambda$startAnimation$2(
                    animationSpec, surfaceControl, runningAnimation);
              }
            });
      }
      if (!requiresEdgeExtension) {
        addAnimationToList(runningAnimation);
        if (!this.mAnimationStartDeferred && this.mPreProcessingAnimations.isEmpty()) {
          this.mChoreographer.postFrameCallback(
              new SurfaceAnimationRunner$$ExternalSyntheticLambda3(this));
        }
      }
      applyTransformation(runningAnimation, transaction, 0L);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$startAnimation$2(
      LocalAnimationAdapter.AnimationSpec animationSpec,
      SurfaceControl surfaceControl,
      RunningAnimation runningAnimation) {
    WindowAnimationSpec asWindowAnimationSpec = animationSpec.asWindowAnimationSpec();
    SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
    edgeExtendWindow(
        surfaceControl,
        asWindowAnimationSpec.getRootTaskBounds(),
        asWindowAnimationSpec.getAnimation(),
        transaction);
    synchronized (this.mLock) {
      if (this.mPreProcessingAnimations.get(surfaceControl) == runningAnimation) {
        synchronized (this.mEdgeExtensionLock) {
          if (!this.mEdgeExtensions.isEmpty()) {
            transaction.apply();
          }
        }
        this.mPreProcessingAnimations.remove(surfaceControl);
        addAnimationToList(runningAnimation);
        if (!this.mAnimationStartDeferred && this.mPreProcessingAnimations.isEmpty()) {
          this.mChoreographer.postFrameCallback(
              new SurfaceAnimationRunner$$ExternalSyntheticLambda3(this));
        }
      }
    }
  }

  public final boolean requiresEdgeExtension(LocalAnimationAdapter.AnimationSpec animationSpec) {
    return animationSpec.asWindowAnimationSpec() != null
        && animationSpec.asWindowAnimationSpec().hasExtension()
        && animationSpec.asWindowAnimationSpec().allowExtension();
  }

  public void onAnimationCancelled(SurfaceControl surfaceControl) {
    synchronized (this.mLock) {
      RunningAnimation animationByLeash = getAnimationByLeash(surfaceControl);
      if (animationByLeash != null) {
        this.mPendingAnimationList.remove(animationByLeash);
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
                             // com.android.server.wm.SurfaceAnimationRunner$$ExternalSyntheticLambda5
              @Override // java.lang.Runnable
              public final void run() {
                SurfaceAnimationRunner.this.lambda$onAnimationCancelled$3(runningAnimation);
              }
            });
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onAnimationCancelled$3(RunningAnimation runningAnimation) {
    runningAnimation.mAnim.cancel();
    applyTransaction();
  }

  public static /* synthetic */ int lambda$startPendingAnimationsLocked$4(
      RunningAnimation runningAnimation, RunningAnimation runningAnimation2) {
    return runningAnimation.mPos - runningAnimation2.mPos;
  }

  public final void startPendingAnimationsLocked() {
    Collections.sort(
        this.mPendingAnimationList,
        new Comparator() { // from class:
                           // com.android.server.wm.SurfaceAnimationRunner$$ExternalSyntheticLambda6
          @Override // java.util.Comparator
          public final int compare(Object obj, Object obj2) {
            int lambda$startPendingAnimationsLocked$4;
            lambda$startPendingAnimationsLocked$4 =
                SurfaceAnimationRunner.lambda$startPendingAnimationsLocked$4(
                    (SurfaceAnimationRunner.RunningAnimation) obj,
                    (SurfaceAnimationRunner.RunningAnimation) obj2);
            return lambda$startPendingAnimationsLocked$4;
          }
        });
    for (int i = 0; i < this.mPendingAnimationList.size(); i++) {
      startAnimationLocked((RunningAnimation) this.mPendingAnimationList.get(i));
    }
    this.mPendingAnimationList.clear();
  }

  public final void startAnimationLocked(final RunningAnimation runningAnimation) {
    final ValueAnimator makeAnimator = this.mAnimatorFactory.makeAnimator();
    makeAnimator.overrideDurationScale(1.0f);
    makeAnimator.setDuration(runningAnimation.mAnimSpec.getDuration());
    makeAnimator.addUpdateListener(
        new ValueAnimator
            .AnimatorUpdateListener() { // from class:
                                        // com.android.server.wm.SurfaceAnimationRunner$$ExternalSyntheticLambda7
          @Override // android.animation.ValueAnimator.AnimatorUpdateListener
          public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            SurfaceAnimationRunner.this.lambda$startAnimationLocked$5(
                runningAnimation, makeAnimator, valueAnimator);
          }
        });
    makeAnimator.addListener(
        new AnimatorListenerAdapter() { // from class:
                                        // com.android.server.wm.SurfaceAnimationRunner.1
          @Override // android.animation.AnimatorListenerAdapter,
                    // android.animation.Animator.AnimatorListener
          public void onAnimationStart(Animator animator) {
            synchronized (SurfaceAnimationRunner.this.mCancelLock) {
              if (!runningAnimation.mCancelled) {
                SurfaceAnimationRunner.this.mFrameTransaction.setAlpha(
                    runningAnimation.mLeash, 1.0f);
              }
            }
          }

          @Override // android.animation.AnimatorListenerAdapter,
                    // android.animation.Animator.AnimatorListener
          public void onAnimationEnd(Animator animator) {
            synchronized (SurfaceAnimationRunner.this.mLock) {
              SurfaceAnimationRunner.this.mRunningAnimations.remove(runningAnimation.mLeash);
              synchronized (SurfaceAnimationRunner.this.mCancelLock) {
                if (!runningAnimation.mCancelled) {
                  SurfaceAnimationRunner.this.mAnimationThreadHandler.post(
                      runningAnimation.mFinishCallback);
                }
              }
            }
          }
        });
    runningAnimation.mAnim = makeAnimator;
    this.mRunningAnimations.put(runningAnimation.mLeash, runningAnimation);
    makeAnimator.start();
    if (runningAnimation.mAnimSpec.canSkipFirstFrame()) {
      makeAnimator.setCurrentPlayTime(this.mChoreographer.getFrameIntervalNanos() / 1000000);
    }
    makeAnimator.doAnimationFrame(this.mChoreographer.getFrameTime());
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$startAnimationLocked$5(
      RunningAnimation runningAnimation,
      ValueAnimator valueAnimator,
      ValueAnimator valueAnimator2) {
    synchronized (this.mCancelLock) {
      if (!runningAnimation.mCancelled) {
        long duration = valueAnimator.getDuration();
        long currentPlayTime = valueAnimator.getCurrentPlayTime();
        if (currentPlayTime <= duration) {
          duration = currentPlayTime;
        }
        applyTransformation(runningAnimation, this.mFrameTransaction, duration);
      }
    }
    scheduleApplyTransaction();
  }

  public final void applyTransformation(
      RunningAnimation runningAnimation, SurfaceControl.Transaction transaction, long j) {
    runningAnimation.mAnimSpec.apply(transaction, runningAnimation.mLeash, j);
  }

  public final void startAnimations(long j) {
    synchronized (this.mLock) {
      if (this.mPreProcessingAnimations.isEmpty()) {
        startPendingAnimationsLocked();
        this.mPowerManagerInternal.setPowerBoost(0, 0);
      }
    }
  }

  public final void scheduleApplyTransaction() {
    if (this.mApplyScheduled) {
      return;
    }
    this.mChoreographer.postCallback(3, this.mApplyTransactionRunnable, null);
    this.mApplyScheduled = true;
  }

  public final void applyTransaction() {
    this.mFrameTransaction.setAnimationTransaction();
    this.mFrameTransaction.setFrameTimelineVsync(this.mChoreographer.getVsyncId());
    this.mFrameTransaction.apply();
    this.mApplyScheduled = false;
  }

  public final void edgeExtendWindow(
      SurfaceControl surfaceControl,
      Rect rect,
      Animation animation,
      SurfaceControl.Transaction transaction) {
    Transformation transformation = new Transformation();
    animation.getTransformationAt(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, transformation);
    Transformation transformation2 = new Transformation();
    animation.getTransformationAt(1.0f, transformation2);
    Insets min = Insets.min(transformation.getInsets(), transformation2.getInsets());
    int height = rect.height();
    int width = rect.width();
    if (min.left < 0) {
      int i = rect.left;
      createExtensionSurface(
          surfaceControl,
          new Rect(i, rect.top, i + 1, rect.bottom),
          new Rect(0, 0, -min.left, height),
          rect.left + min.left,
          rect.top,
          "Left Edge Extension",
          transaction);
    }
    if (min.top < 0) {
      int i2 = rect.left;
      int i3 = rect.top;
      createExtensionSurface(
          surfaceControl,
          new Rect(i2, i3, width, i3 + 1),
          new Rect(0, 0, width, -min.top),
          rect.left,
          rect.top + min.top,
          "Top Edge Extension",
          transaction);
    }
    if (min.right < 0) {
      int i4 = rect.right;
      createExtensionSurface(
          surfaceControl,
          new Rect(i4 - 1, rect.top, i4, rect.bottom),
          new Rect(0, 0, -min.right, height),
          rect.right,
          rect.top,
          "Right Edge Extension",
          transaction);
    }
    if (min.bottom < 0) {
      int i5 = rect.left;
      int i6 = rect.bottom;
      createExtensionSurface(
          surfaceControl,
          new Rect(i5, i6 - 1, rect.right, i6),
          new Rect(0, 0, width, -min.bottom),
          rect.left,
          rect.bottom,
          "Bottom Edge Extension",
          transaction);
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
    Trace.traceBegin(32L, "createExtensionSurface");
    doCreateExtensionSurface(surfaceControl, rect, rect2, i, i2, str, transaction);
    Trace.traceEnd(32L);
  }

  public final void doCreateExtensionSurface(
      SurfaceControl surfaceControl,
      Rect rect,
      Rect rect2,
      int i,
      int i2,
      String str,
      SurfaceControl.Transaction transaction) {
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
      Log.e("SurfaceAnimationRunner", "Failed to create edge extension - edge buffer is null");
      return;
    }
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
      paint.setColor(convertToColor(str));
    } else {
      paint.setShader(bitmapShader);
    }
    Surface surface = new Surface(build);
    Canvas lockHardwareCanvas = surface.lockHardwareCanvas();
    lockHardwareCanvas.drawRect(rect2, paint);
    surface.unlockCanvasAndPost(lockHardwareCanvas);
    surface.release();
    synchronized (this.mEdgeExtensionLock) {
      if (!this.mEdgeExtensions.containsKey(surfaceControl)) {
        transaction.remove(build);
        return;
      }
      transaction.reparent(build, surfaceControl);
      transaction.setLayer(build, Integer.MIN_VALUE);
      transaction.setPosition(build, i, i2);
      transaction.setVisibility(build, true);
      ((ArrayList) this.mEdgeExtensions.get(surfaceControl)).add(build);
    }
  }

  public final class RunningAnimation {
    public ValueAnimator mAnim;
    public final LocalAnimationAdapter.AnimationSpec mAnimSpec;
    public boolean mCancelled;
    public final Runnable mFinishCallback;
    public final SurfaceControl mLeash;
    public int mPos;

    public RunningAnimation(
        LocalAnimationAdapter.AnimationSpec animationSpec,
        SurfaceControl surfaceControl,
        Runnable runnable) {
      this.mAnimSpec = animationSpec;
      this.mLeash = surfaceControl;
      this.mFinishCallback = runnable;
    }

    public void setPosition(int i) {
      this.mPos = i;
    }
  }

  public void onAnimationLeashLost(
      SurfaceControl surfaceControl, SurfaceControl.Transaction transaction) {
    synchronized (this.mEdgeExtensionLock) {
      if (this.mEdgeExtensions.containsKey(surfaceControl)) {
        ArrayList arrayList = (ArrayList) this.mEdgeExtensions.get(surfaceControl);
        for (int i = 0; i < arrayList.size(); i++) {
          transaction.remove((SurfaceControl) arrayList.get(i));
        }
        this.mEdgeExtensions.remove(surfaceControl);
      }
    }
  }

  public class SfValueAnimator extends ValueAnimator {
    public SfValueAnimator() {
      setFloatValues(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f);
    }

    public AnimationHandler getAnimationHandler() {
      return SurfaceAnimationRunner.this.mAnimationHandler;
    }
  }

  public final void addAnimationToList(RunningAnimation runningAnimation) {
    RunningAnimation animationByLeash = getAnimationByLeash(runningAnimation.mLeash);
    if (animationByLeash != null) {
      this.mPendingAnimationList.remove(animationByLeash);
    }
    this.mPendingAnimationList.add(runningAnimation);
  }

  public final RunningAnimation getAnimationByLeash(SurfaceControl surfaceControl) {
    Iterator it = this.mPendingAnimationList.iterator();
    while (it.hasNext()) {
      RunningAnimation runningAnimation = (RunningAnimation) it.next();
      if (runningAnimation.mLeash == surfaceControl) {
        return runningAnimation;
      }
    }
    return null;
  }

  public final int convertToColor(String str) {
    str.hashCode();
    switch (str) {
      case "Right Edge Extension":
        return -16776961;
      case "Left Edge Extension":
        return -65536;
      case "Bottom Edge Extension":
        return -16711681;
      case "Top Edge Extension":
        return -16711936;
      default:
        return -65281;
    }
  }
}
