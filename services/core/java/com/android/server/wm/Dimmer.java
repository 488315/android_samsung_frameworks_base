package com.android.server.wm;

import android.graphics.Rect;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import android.view.Surface;
import android.view.SurfaceControl;
import com.android.server.display.DisplayPowerController2;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class Dimmer {
  DimState mDimState;
  public WindowContainer mHost;
  public WindowContainer mLastRequestedDimContainer;
  public final SurfaceAnimatorStarter mSurfaceAnimatorStarter;

  interface SurfaceAnimatorStarter {
    void startAnimation(
        SurfaceAnimator surfaceAnimator,
        SurfaceControl.Transaction transaction,
        AnimationAdapter animationAdapter,
        boolean z,
        int i);
  }

  public class DimAnimatable implements SurfaceAnimator.Animatable {
    public SurfaceControl mDimLayer;

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public void onAnimationLeashCreated(
        SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {}

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public void onAnimationLeashLost(SurfaceControl.Transaction transaction) {}

    public DimAnimatable(SurfaceControl surfaceControl) {
      this.mDimLayer = surfaceControl;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public SurfaceControl.Transaction getSyncTransaction() {
      return Dimmer.this.mHost.getSyncTransaction();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public void commitPendingTransaction() {
      Dimmer.this.mHost.commitPendingTransaction();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public SurfaceControl.Builder makeAnimationLeash() {
      return Dimmer.this.mHost.makeAnimationLeash();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public SurfaceControl getAnimationLeashParent() {
      return Dimmer.this.mHost.getSurfaceControl();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public SurfaceControl getSurfaceControl() {
      return this.mDimLayer;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public SurfaceControl getParentSurfaceControl() {
      return Dimmer.this.mHost.getSurfaceControl();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public int getSurfaceWidth() {
      return Dimmer.this.mHost.getSurfaceWidth();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public int getSurfaceHeight() {
      return Dimmer.this.mHost.getSurfaceHeight();
    }

    public void removeSurface() {
      SurfaceControl surfaceControl = this.mDimLayer;
      if (surfaceControl != null && surfaceControl.isValid()) {
        getSyncTransaction().remove(this.mDimLayer);
      }
      this.mDimLayer = null;
    }
  }

  class DimState {
    public boolean isVisible;
    public SurfaceControl mDimLayer;
    public boolean mDontReset;
    public SurfaceAnimator mSurfaceAnimator;
    public final Rect mDimBounds = new Rect();
    public boolean mAnimateExit = true;
    public boolean mDimming = true;

    public DimState(SurfaceControl surfaceControl) {
      this.mDimLayer = surfaceControl;
      final DimAnimatable dimAnimatable = new DimAnimatable(surfaceControl);
      this.mSurfaceAnimator =
          new SurfaceAnimator(
              dimAnimatable,
              new SurfaceAnimator
                  .OnAnimationFinishedCallback() { // from class:
                                                   // com.android.server.wm.Dimmer$DimState$$ExternalSyntheticLambda0
                @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
                public final void onAnimationFinished(int i, AnimationAdapter animationAdapter) {
                  Dimmer.DimState.this.lambda$new$0(dimAnimatable, i, animationAdapter);
                }
              },
              Dimmer.this.mHost.mWmService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(
        DimAnimatable dimAnimatable, int i, AnimationAdapter animationAdapter) {
      if (this.mDimming) {
        return;
      }
      dimAnimatable.removeSurface();
    }
  }

  public Dimmer(WindowContainer windowContainer) {
    this(
        windowContainer,
        new SurfaceAnimatorStarter() { // from class:
                                       // com.android.server.wm.Dimmer$$ExternalSyntheticLambda0
          @Override // com.android.server.wm.Dimmer.SurfaceAnimatorStarter
          public final void startAnimation(
              SurfaceAnimator surfaceAnimator,
              SurfaceControl.Transaction transaction,
              AnimationAdapter animationAdapter,
              boolean z,
              int i) {
            surfaceAnimator.startAnimation(transaction, animationAdapter, z, i);
          }
        });
  }

  public Dimmer(WindowContainer windowContainer, SurfaceAnimatorStarter surfaceAnimatorStarter) {
    this.mHost = windowContainer;
    this.mSurfaceAnimatorStarter = surfaceAnimatorStarter;
  }

  public WindowContainer getHost() {
    return this.mHost;
  }

  public final SurfaceControl makeDimLayer() {
    return this.mHost
        .makeChildSurface(null)
        .setParent(this.mHost.getSurfaceControl())
        .setColorLayer()
        .setName("Dim Layer for - " + this.mHost.getName())
        .setCallsite("Dimmer.makeDimLayer")
        .build();
  }

  public final DimState getDimState(WindowContainer windowContainer) {
    if (this.mDimState == null) {
      try {
        DimState dimState = new DimState(makeDimLayer());
        this.mDimState = dimState;
        if (windowContainer == null) {
          dimState.mDontReset = true;
        }
      } catch (Surface.OutOfResourcesException unused) {
        Log.w(StartingSurfaceController.TAG, "OutOfResourcesException creating dim surface");
      }
    }
    this.mLastRequestedDimContainer = windowContainer;
    return this.mDimState;
  }

  public final void dim(WindowContainer windowContainer, int i, float f, int i2) {
    DimState dimState = getDimState(windowContainer);
    if (dimState == null) {
      return;
    }
    SurfaceControl.Transaction pendingTransaction = this.mHost.getPendingTransaction();
    pendingTransaction.setRelativeLayer(dimState.mDimLayer, windowContainer.getSurfaceControl(), i);
    pendingTransaction.setAlpha(dimState.mDimLayer, f);
    pendingTransaction.setBackgroundBlurRadius(dimState.mDimLayer, i2);
    dimState.mDimming = true;
  }

  public void dimBelow(WindowContainer windowContainer, float f, int i) {
    dim(windowContainer, -1, f, i);
  }

  public void resetDimStates() {
    DimState dimState = this.mDimState;
    if (dimState == null || dimState.mDontReset) {
      return;
    }
    dimState.mDimming = false;
  }

  public Rect getDimBounds() {
    DimState dimState = this.mDimState;
    if (dimState != null) {
      return dimState.mDimBounds;
    }
    return null;
  }

  public void dontAnimateExit() {
    DimState dimState = this.mDimState;
    if (dimState != null) {
      dimState.mAnimateExit = false;
    }
  }

  public boolean updateDims(SurfaceControl.Transaction transaction) {
    DimState dimState = this.mDimState;
    boolean z = false;
    if (dimState == null) {
      return false;
    }
    if (!dimState.mDimming) {
      if (!dimState.mAnimateExit) {
        if (dimState.mDimLayer.isValid()) {
          transaction.remove(this.mDimState.mDimLayer);
        }
      } else {
        startDimExit(this.mLastRequestedDimContainer, dimState.mSurfaceAnimator, transaction);
      }
      this.mDimState = null;
      return false;
    }
    Rect rect = dimState.mDimBounds;
    WindowContainer windowContainer = this.mLastRequestedDimContainer;
    if (windowContainer instanceof WindowState) {
      WindowState windowState = (WindowState) windowContainer;
      if (windowState.inFreeformWindowingMode()
          && windowState.isDesktopModeEnabled()
          && windowState.getTask() != null
          && windowState.getTask().isTranslucentTask()) {
        rect.setEmpty();
        z = true;
      }
    }
    transaction.setPosition(this.mDimState.mDimLayer, rect.left, rect.top);
    if (z) {
      transaction.setWindowCrop(this.mDimState.mDimLayer, -1, -1);
    } else {
      transaction.setWindowCrop(this.mDimState.mDimLayer, rect.width(), rect.height());
    }
    float cornerRadius = getCornerRadius();
    if (cornerRadius > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      transaction.setCornerRadius(this.mDimState.mDimLayer, cornerRadius);
    }
    DimState dimState2 = this.mDimState;
    if (!dimState2.isVisible) {
      dimState2.isVisible = true;
      transaction.show(dimState2.mDimLayer);
      startDimEnter(this.mLastRequestedDimContainer, this.mDimState.mSurfaceAnimator, transaction);
    }
    return true;
  }

  public final float getCornerRadius() {
    WindowContainer windowContainer = this.mHost;
    if (windowContainer.mDisplayContent == null || !windowContainer.inFreeformWindowingMode()) {
      return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    }
    WindowContainer windowContainer2 = this.mHost;
    return windowContainer2.mWmService.mAtmService.mFreeformController
        .getFreeformCornerRadiusLocked(windowContainer2.mDisplayContent.getDisplayId());
  }

  public final void startDimEnter(
      WindowContainer windowContainer,
      SurfaceAnimator surfaceAnimator,
      SurfaceControl.Transaction transaction) {
    startAnim(
        windowContainer,
        surfaceAnimator,
        transaction,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
        1.0f);
  }

  public final void startDimExit(
      WindowContainer windowContainer,
      SurfaceAnimator surfaceAnimator,
      SurfaceControl.Transaction transaction) {
    startAnim(
        windowContainer,
        surfaceAnimator,
        transaction,
        1.0f,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
  }

  public final void startAnim(
      WindowContainer windowContainer,
      SurfaceAnimator surfaceAnimator,
      SurfaceControl.Transaction transaction,
      float f,
      float f2) {
    this.mSurfaceAnimatorStarter.startAnimation(
        surfaceAnimator,
        transaction,
        new LocalAnimationAdapter(
            new AlphaAnimationSpec(f, f2, getDimDuration(windowContainer)),
            this.mHost.mWmService.mSurfaceAnimationRunner),
        false,
        4);
  }

  public final long getDimDuration(WindowContainer windowContainer) {
    if (windowContainer == null) {
      return 0L;
    }
    AnimationAdapter animation = windowContainer.mSurfaceAnimator.getAnimation();
    if (animation == null) {
      return 200L;
    }
    return animation.getDurationHint();
  }

  public class AlphaAnimationSpec implements LocalAnimationAdapter.AnimationSpec {
    public final long mDuration;
    public final float mFromAlpha;
    public final float mToAlpha;

    public AlphaAnimationSpec(float f, float f2, long j) {
      this.mFromAlpha = f;
      this.mToAlpha = f2;
      this.mDuration = j;
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public long getDuration() {
      return this.mDuration;
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public void apply(
        SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, long j) {
      float fraction = getFraction(j);
      float f = this.mToAlpha;
      float f2 = this.mFromAlpha;
      transaction.setAlpha(surfaceControl, (fraction * (f - f2)) + f2);
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public void dump(PrintWriter printWriter, String str) {
      printWriter.print(str);
      printWriter.print("from=");
      printWriter.print(this.mFromAlpha);
      printWriter.print(" to=");
      printWriter.print(this.mToAlpha);
      printWriter.print(" duration=");
      printWriter.println(this.mDuration);
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public void dumpDebugInner(ProtoOutputStream protoOutputStream) {
      long start = protoOutputStream.start(1146756268035L);
      protoOutputStream.write(1108101562369L, this.mFromAlpha);
      protoOutputStream.write(1108101562370L, this.mToAlpha);
      protoOutputStream.write(1112396529667L, this.mDuration);
      protoOutputStream.end(start);
    }
  }
}
