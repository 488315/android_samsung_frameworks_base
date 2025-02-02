package com.android.server.wm;

import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Debug;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.InsetsSource;
import android.view.InsetsSourceControl;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.function.TriFunction;
import java.io.PrintWriter;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class InsetsSourceProvider {
  public ControlAdapter mAdapter;
  public boolean mClientVisible;
  public InsetsSourceControl mControl;
  public InsetsControlTarget mControlTarget;
  public final boolean mControllable;
  public final DisplayContent mDisplayContent;
  public final InsetsSourceControl mFakeControl;
  public InsetsControlTarget mFakeControlTarget;
  public int mFlagsFromFrameProvider;
  public int mFlagsFromServer;
  public TriFunction mFrameProvider;
  public boolean mIsLeashReadyForDispatching;
  public SparseArray mOverrideFrameProviders;
  public InsetsControlTarget mPendingControlTarget;
  public boolean mSeamlessRotating;
  public boolean mServerVisible;
  public final InsetsSource mSource;
  public final InsetsStateController mStateController;
  public WindowContainer mWindowContainer;
  public final Rect mTmpRect = new Rect();
  public final SparseArray mOverrideFrames = new SparseArray();
  public final Rect mSourceFrame = new Rect();
  public final Rect mLastSourceFrame = new Rect();
  public Insets mInsetsHint = Insets.NONE;
  public final Consumer mSetLeashPositionConsumer =
      new Consumer() { // from class:
                       // com.android.server.wm.InsetsSourceProvider$$ExternalSyntheticLambda0
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
          InsetsSourceProvider.this.lambda$new$0((SurfaceControl.Transaction) obj);
        }
      };
  public boolean mCropToProvidingInsets = false;

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0(SurfaceControl.Transaction transaction) {
    SurfaceControl leash;
    InsetsSourceControl insetsSourceControl = this.mControl;
    if (insetsSourceControl == null || (leash = insetsSourceControl.getLeash()) == null) {
      return;
    }
    Point surfacePosition = this.mControl.getSurfacePosition();
    transaction.setPosition(leash, surfacePosition.x, surfacePosition.y);
    Slog.d(
        "InsetsSourceProvider",
        "Update insets leash position="
            + surfacePosition
            + ", leash="
            + leash
            + ", caller="
            + Debug.getCallers(3));
  }

  public InsetsSourceProvider(
      InsetsSource insetsSource,
      InsetsStateController insetsStateController,
      DisplayContent displayContent) {
    this.mClientVisible = (WindowInsets.Type.defaultVisible() & insetsSource.getType()) != 0;
    this.mSource = insetsSource;
    this.mDisplayContent = displayContent;
    this.mStateController = insetsStateController;
    this.mFakeControl =
        new InsetsSourceControl(
            insetsSource.getId(),
            insetsSource.getType(),
            (SurfaceControl) null,
            false,
            new Point(),
            Insets.NONE);
    this.mControllable = (insetsSource.getType() & InsetsPolicy.CONTROLLABLE_TYPES) != 0;
  }

  public InsetsSource getSource() {
    return this.mSource;
  }

  public boolean isControllable() {
    return this.mControllable;
  }

  public void setWindowContainer(
      WindowContainer windowContainer, TriFunction triFunction, SparseArray sparseArray) {
    WindowContainer windowContainer2 = this.mWindowContainer;
    if (windowContainer2 != null) {
      if (this.mControllable) {
        windowContainer2.setControllableInsetProvider(null);
      }
      this.mWindowContainer.cancelAnimation();
      this.mWindowContainer.getInsetsSourceProviders().remove(this.mSource.getId());
      this.mSeamlessRotating = false;
      if (this.mDisplayContent.getInsetsPolicy().isTransient(this.mSource.getType())) {
        this.mDisplayContent.getInsetsPolicy().abortTransient();
      }
    }
    if (ProtoLogCache.WM_DEBUG_WINDOW_INSETS_enabled) {
      ProtoLogImpl.d(
          ProtoLogGroup.WM_DEBUG_WINDOW_INSETS,
          -1483435730,
          0,
          (String) null,
          new Object[] {
            String.valueOf(windowContainer),
            String.valueOf(WindowInsets.Type.toString(this.mSource.getType()))
          });
    }
    this.mWindowContainer = windowContainer;
    this.mFrameProvider = triFunction;
    this.mOverrideFrames.clear();
    this.mOverrideFrameProviders = sparseArray;
    if (windowContainer == null) {
      setServerVisible(false);
      this.mSource.setVisibleFrame((Rect) null);
      this.mSource.setInsetsRoundedCornerFrame(false);
      this.mSourceFrame.setEmpty();
      return;
    }
    this.mWindowContainer.getInsetsSourceProviders().put(this.mSource.getId(), this);
    if (this.mControllable) {
      this.mWindowContainer.setControllableInsetProvider(this);
      InsetsControlTarget insetsControlTarget = this.mPendingControlTarget;
      if (insetsControlTarget != null) {
        updateControlForTarget(insetsControlTarget, true);
        this.mPendingControlTarget = null;
      }
    }
  }

  public boolean setFlags(int i, int i2) {
    int i3 = (i & i2) | (this.mFlagsFromServer & (~i2));
    this.mFlagsFromServer = i3;
    int i4 = i3 | this.mFlagsFromFrameProvider;
    if (this.mSource.getFlags() == i4) {
      return false;
    }
    this.mSource.setFlags(i4);
    return true;
  }

  public void updateSourceFrame(Rect rect) {
    Rect rect2;
    WindowContainer windowContainer = this.mWindowContainer;
    if (windowContainer == null) {
      return;
    }
    WindowState asWindowState = windowContainer.asWindowState();
    if (asWindowState == null) {
      if (this.mServerVisible) {
        this.mTmpRect.set(this.mWindowContainer.getBounds());
        TriFunction triFunction = this.mFrameProvider;
        if (triFunction != null) {
          triFunction.apply(
              this.mWindowContainer.getDisplayContent().mDisplayFrames,
              this.mWindowContainer,
              this.mTmpRect);
        }
      } else {
        this.mTmpRect.setEmpty();
      }
      this.mSource.setFrame(this.mTmpRect);
      this.mSource.setVisibleFrame((Rect) null);
      return;
    }
    this.mSourceFrame.set(rect);
    TriFunction triFunction2 = this.mFrameProvider;
    if (triFunction2 != null) {
      int intValue =
          ((Integer)
                  triFunction2.apply(
                      this.mWindowContainer.getDisplayContent().mDisplayFrames,
                      this.mWindowContainer,
                      this.mSourceFrame))
              .intValue();
      this.mFlagsFromFrameProvider = intValue;
      this.mSource.setFlags(intValue | this.mFlagsFromServer);
    }
    updateSourceFrameForServerVisibility();
    SparseArray sparseArray = this.mOverrideFrameProviders;
    if (sparseArray != null) {
      for (int size = sparseArray.size() - 1; size >= 0; size--) {
        int keyAt = this.mOverrideFrameProviders.keyAt(size);
        if (this.mOverrideFrames.contains(keyAt)) {
          rect2 = (Rect) this.mOverrideFrames.get(keyAt);
          rect2.set(rect);
        } else {
          rect2 = new Rect(rect);
        }
        if (((TriFunction) this.mOverrideFrameProviders.get(keyAt)) != null) {
          ((TriFunction) this.mOverrideFrameProviders.get(keyAt))
              .apply(
                  this.mWindowContainer.getDisplayContent().mDisplayFrames,
                  this.mWindowContainer,
                  rect2);
        }
        this.mOverrideFrames.put(keyAt, rect2);
      }
    }
    Rect rect3 = asWindowState.mGivenVisibleInsets;
    if (rect3.left != 0 || rect3.top != 0 || rect3.right != 0 || rect3.bottom != 0) {
      this.mTmpRect.set(rect);
      this.mTmpRect.inset(asWindowState.mGivenVisibleInsets);
      this.mSource.setVisibleFrame(this.mTmpRect);
      return;
    }
    this.mSource.setVisibleFrame((Rect) null);
  }

  public final void updateSourceFrameForServerVisibility() {
    if (this.mServerVisible) {
      this.mSource.setFrame(this.mSourceFrame);
    } else {
      this.mSource.setFrame(0, 0, 0, 0);
    }
  }

  public InsetsSource createSimulatedSource(DisplayFrames displayFrames, Rect rect) {
    InsetsSource insetsSource = new InsetsSource(this.mSource);
    this.mTmpRect.set(rect);
    TriFunction triFunction = this.mFrameProvider;
    if (triFunction != null) {
      triFunction.apply(displayFrames, this.mWindowContainer, this.mTmpRect);
    }
    insetsSource.setFrame(this.mTmpRect);
    insetsSource.setVisibleFrame((Rect) null);
    return insetsSource;
  }

  public void onPostLayout() {
    boolean isVisibleRequested;
    SurfaceControl.Transaction drawTransaction;
    WindowContainer windowContainer = this.mWindowContainer;
    if (windowContainer == null) {
      return;
    }
    WindowState asWindowState = windowContainer.asWindowState();
    boolean z = false;
    boolean z2 = true;
    if (asWindowState != null) {
      isVisibleRequested =
          asWindowState.wouldBeVisibleIfPolicyIgnored() && asWindowState.isVisibleByPolicy();
    } else {
      isVisibleRequested = this.mWindowContainer.isVisibleRequested();
    }
    setServerVisible(isVisibleRequested);
    if (this.mControl != null) {
      Point windowFrameSurfacePosition = getWindowFrameSurfacePosition();
      if (asWindowState.mAttrs.type == 2024) {
        this.mControl.setSkipEnsuringControlPosition(false);
      }
      int i = asWindowState.mAttrs.type;
      if ((i == 2019 || i == 2024)
          && !this.mControl.getSurfacePosition().equals(windowFrameSurfacePosition)) {
        Slog.d(
            "InsetsSourceProvider",
            "onPostLayout, current position="
                + this.mControl.getSurfacePosition()
                + " ->  position="
                + windowFrameSurfacePosition
                + ", controlTarget="
                + this.mControlTarget
                + ", insetsWin="
                + asWindowState);
      }
      if (this.mControl.setSurfacePosition(
              windowFrameSurfacePosition.x, windowFrameSurfacePosition.y)
          && this.mControlTarget != null) {
        if (asWindowState.getWindowFrames().didFrameSizeChange()
            && asWindowState.mWinAnimator.getShown()
            && this.mWindowContainer.okToDisplay()) {
          if (asWindowState.mAttrs.type == 2024
              && asWindowState.getWindowFrames().didFrameHeightChange()) {
            Slog.d(
                "InsetsSourceProvider",
                "onPostLayout, skip ensuring control position for " + this.mControl);
            this.mControl.setSkipEnsuringControlPosition(true);
          }
          asWindowState.applyWithNextDraw(this.mSetLeashPositionConsumer);
        } else {
          SurfaceControl.Transaction syncTransaction = this.mWindowContainer.getSyncTransaction();
          AsyncRotationController asyncRotationController =
              this.mDisplayContent.getAsyncRotationController();
          if (asyncRotationController != null
              && (drawTransaction =
                      asyncRotationController.getDrawTransaction(asWindowState.mToken))
                  != null) {
            syncTransaction = drawTransaction;
          }
          this.mSetLeashPositionConsumer.accept(syncTransaction);
        }
        z = true;
      }
      if (this.mServerVisible && !this.mLastSourceFrame.equals(this.mSource.getFrame())) {
        Insets calculateInsets =
            this.mSource.calculateInsets(this.mWindowContainer.getBounds(), true);
        if (calculateInsets.equals(this.mControl.getInsetsHint())) {
          z2 = z;
        } else {
          this.mControl.setInsetsHint(calculateInsets);
          this.mInsetsHint = calculateInsets;
        }
        this.mLastSourceFrame.set(this.mSource.getFrame());
        z = z2;
      }
      if (z) {
        this.mStateController.notifyControlChanged(this.mControlTarget);
      }
    }
  }

  public final Point getWindowFrameSurfacePosition() {
    AsyncRotationController asyncRotationController;
    WindowState asWindowState = this.mWindowContainer.asWindowState();
    if (asWindowState != null
        && this.mControl != null
        && (asyncRotationController = this.mDisplayContent.getAsyncRotationController()) != null
        && asyncRotationController.shouldFreezeInsetsPosition(asWindowState)) {
      return this.mControl.getSurfacePosition();
    }
    Rect frame =
        asWindowState != null ? asWindowState.getFrame() : this.mWindowContainer.getBounds();
    Point point = new Point();
    this.mWindowContainer.transformFrameToSurfacePosition(frame.left, frame.top, point);
    return point;
  }

  public void updateFakeControlTarget(InsetsControlTarget insetsControlTarget) {
    if (insetsControlTarget == this.mFakeControlTarget) {
      return;
    }
    this.mFakeControlTarget = insetsControlTarget;
    Slog.d(
        "InsetsSourceProvider",
        "updateFakeControlTarget: fakeControl="
            + this.mFakeControl
            + ", fakeTarget="
            + insetsControlTarget);
  }

  public void setCropToProvidingInsetsBounds(SurfaceControl.Transaction transaction) {
    this.mCropToProvidingInsets = true;
    WindowContainer windowContainer = this.mWindowContainer;
    if (windowContainer == null || !windowContainer.mSurfaceAnimator.hasLeash()) {
      return;
    }
    transaction.setWindowCrop(
        this.mWindowContainer.mSurfaceAnimator.mLeash, getProvidingInsetsBoundsCropRect());
  }

  public void removeCropToProvidingInsetsBounds(SurfaceControl.Transaction transaction) {
    this.mCropToProvidingInsets = false;
    WindowContainer windowContainer = this.mWindowContainer;
    if (windowContainer == null || !windowContainer.mSurfaceAnimator.hasLeash()) {
      return;
    }
    transaction.setWindowCrop(this.mWindowContainer.mSurfaceAnimator.mLeash, null);
  }

  public final Rect getProvidingInsetsBoundsCropRect() {
    Rect bounds;
    if (this.mWindowContainer.asWindowState() != null) {
      bounds = this.mWindowContainer.asWindowState().getFrame();
    } else {
      bounds = this.mWindowContainer.getBounds();
    }
    Rect frame = getSource().getFrame();
    int i = frame.left;
    int i2 = bounds.left;
    int i3 = frame.top;
    int i4 = bounds.top;
    return new Rect(i - i2, i3 - i4, frame.right - i2, frame.bottom - i4);
  }

  public void updateControlForTarget(InsetsControlTarget insetsControlTarget, boolean z) {
    int i;
    if (this.mSeamlessRotating) {
      return;
    }
    WindowContainer windowContainer = this.mWindowContainer;
    if (windowContainer != null && windowContainer.getSurfaceControl() == null) {
      setWindowContainer(null, null, null);
    }
    if (this.mWindowContainer == null) {
      this.mPendingControlTarget = insetsControlTarget;
      return;
    }
    if (insetsControlTarget != this.mControlTarget || z) {
      if (insetsControlTarget == null) {
        if (this.mDisplayContent.getDefaultTaskDisplayArea().isSplitScreenVisible()
            && this.mWindowContainer.getWindow() != null) {
          this.mWindowContainer.getWindow().mSurfacePlacementNeeded = true;
        }
        this.mWindowContainer.cancelAnimation();
        setClientVisible((WindowInsets.Type.defaultVisible() & this.mSource.getType()) != 0);
        return;
      }
      if ((this.mSource.getType() == WindowInsets.Type.navigationBars()
              && insetsControlTarget.getWindow() != null
              && insetsControlTarget.getWindow().mToken.isFixedRotationTransforming())
          || this.mWindowContainer.getSyncTransaction() == this.mWindowContainer.mSyncTransaction) {
        return;
      }
      Point windowFrameSurfacePosition = getWindowFrameSurfacePosition();
      if (this.mDisplayContent.getDisplayId() == 2
          && this.mWindowContainer.asWindowState() != null
          && this.mWindowContainer.asWindowState().getWindowFrames().mFrame.isEmpty()
          && this.mWindowContainer.asWindowState().getWindowType() == 2019
          && windowFrameSurfacePosition.y
              != (i =
                  this.mDisplayContent.getBounds().bottom
                      - this.mDisplayContent.getDisplayPolicy().mDexTaskbarHeight)) {
        windowFrameSurfacePosition.y = i;
        Slog.d(
            "InsetsSourceProvider",
            "updateControlForTarget: change surfacePos=" + windowFrameSurfacePosition);
      }
      this.mAdapter = new ControlAdapter(windowFrameSurfacePosition);
      if (this.mSource.getType() == WindowInsets.Type.ime()) {
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent.mRemoteInsetsControlTarget == this.mControlTarget
            && displayContent.getImeInputTarget() == insetsControlTarget
            && this.mClientVisible) {
          setClientVisible(true);
        } else {
          setClientVisible(insetsControlTarget.isRequestedVisible(WindowInsets.Type.ime()));
        }
      }
      this.mWindowContainer.startAnimation(
          this.mWindowContainer.getSyncTransaction(),
          this.mAdapter,
          true ^ this.mClientVisible,
          32);
      this.mIsLeashReadyForDispatching = false;
      SurfaceControl surfaceControl = this.mAdapter.mCapturedLeash;
      this.mControlTarget = insetsControlTarget;
      updateVisibility();
      InsetsSourceControl insetsSourceControl =
          new InsetsSourceControl(
              this.mSource.getId(),
              this.mSource.getType(),
              surfaceControl,
              this.mClientVisible,
              windowFrameSurfacePosition,
              this.mInsetsHint);
      this.mControl = insetsSourceControl;
      if (ProtoLogCache.WM_DEBUG_WINDOW_INSETS_enabled) {
        ProtoLogImpl.d(
            ProtoLogGroup.WM_DEBUG_WINDOW_INSETS,
            416924848,
            0,
            (String) null,
            new Object[] {
              String.valueOf(insetsSourceControl), String.valueOf(this.mControlTarget)
            });
      }
      Slog.d(
          "InsetsSourceProvider",
          "updateControlForTarget: control="
              + this.mControl
              + ", target="
              + this.mControlTarget
              + ", from="
              + Debug.getCallers(5));
    }
  }

  public void startSeamlessRotation() {
    if (this.mSeamlessRotating) {
      return;
    }
    this.mSeamlessRotating = true;
    this.mWindowContainer.cancelAnimation();
  }

  public void finishSeamlessRotation() {
    this.mSeamlessRotating = false;
  }

  public boolean updateClientVisibility(InsetsControlTarget insetsControlTarget) {
    boolean isRequestedVisible = insetsControlTarget.isRequestedVisible(this.mSource.getType());
    if (insetsControlTarget != this.mControlTarget || isRequestedVisible == this.mClientVisible) {
      return false;
    }
    setClientVisible(isRequestedVisible);
    return true;
  }

  public void onSurfaceTransactionApplied() {
    this.mIsLeashReadyForDispatching = true;
  }

  public void setClientVisible(boolean z) {
    if (this.mClientVisible == z) {
      return;
    }
    this.mClientVisible = z;
    updateVisibility();
    this.mDisplayContent.setLayoutNeeded();
    this.mDisplayContent.mWmService.mWindowPlacerLocked.requestTraversal();
  }

  public void setServerVisible(boolean z) {
    this.mServerVisible = z;
    updateSourceFrameForServerVisibility();
    updateVisibility();
  }

  public void updateVisibility() {
    boolean isVisible = this.mSource.isVisible();
    this.mSource.setVisible(this.mServerVisible && this.mClientVisible);
    if (ProtoLogCache.WM_DEBUG_WINDOW_INSETS_enabled) {
      ProtoLogImpl.d(
          ProtoLogGroup.WM_DEBUG_WINDOW_INSETS,
          2070726247,
          0,
          (String) null,
          new Object[] {
            String.valueOf(WindowInsets.Type.toString(this.mSource.getType())),
            String.valueOf(this.mServerVisible),
            String.valueOf(this.mClientVisible)
          });
    }
    if (isVisible != this.mSource.isVisible()) {
      Slog.d(
          "InsetsSourceProvider",
          "updateVisibility: serverVisible="
              + this.mServerVisible
              + ", clientVisible="
              + this.mClientVisible
              + ", source="
              + this.mSource
              + ", controlTarget="
              + this.mControlTarget
              + ", from="
              + Debug.getCallers(10));
    }
  }

  public InsetsSourceControl getControl(InsetsControlTarget insetsControlTarget) {
    if (insetsControlTarget == this.mControlTarget) {
      if (!this.mIsLeashReadyForDispatching && this.mControl != null) {
        return new InsetsSourceControl(
            this.mControl.getId(),
            this.mControl.getType(),
            (SurfaceControl) null,
            this.mControl.isInitiallyVisible(),
            this.mControl.getSurfacePosition(),
            this.mControl.getInsetsHint());
      }
      return this.mControl;
    }
    if (insetsControlTarget == this.mFakeControlTarget) {
      return this.mFakeControl;
    }
    return null;
  }

  public InsetsControlTarget getControlTarget() {
    return this.mControlTarget;
  }

  public InsetsControlTarget getFakeControlTarget() {
    return this.mFakeControlTarget;
  }

  public boolean isClientVisible() {
    return this.mClientVisible;
  }

  public boolean overridesFrame(int i) {
    return this.mOverrideFrames.contains(i);
  }

  public Rect getOverriddenFrame(int i) {
    return (Rect) this.mOverrideFrames.get(i);
  }

  public boolean shouldDeferUpdate() {
    WindowContainer windowContainer = this.mWindowContainer;
    if (windowContainer != null && this.mControl != null) {
      WindowState asWindowState = windowContainer.asWindowState();
      Point windowFrameSurfacePosition = getWindowFrameSurfacePosition();
      if (asWindowState != null
          && asWindowState.mAttrs.type == 2024
          && asWindowState.mInRelayout
          && !this.mControl.getSurfacePosition().equals(windowFrameSurfacePosition)) {
        Slog.d("InsetsSourceProvider", "shouldDeferUpdate: should defer for " + asWindowState);
        return true;
      }
    }
    return false;
  }

  public void dump(PrintWriter printWriter, String str) {
    printWriter.println(str + getClass().getSimpleName());
    String str2 = str + "  ";
    printWriter.print(str2 + "mSource=");
    this.mSource.dump("", printWriter);
    printWriter.print(str2 + "mSourceFrame=");
    printWriter.println(this.mSourceFrame);
    if (this.mOverrideFrames.size() > 0) {
      printWriter.print(str2 + "mOverrideFrames=");
      printWriter.println(this.mOverrideFrames);
    }
    if (this.mControl != null) {
      printWriter.print(str2 + "mControl=");
      this.mControl.dump("", printWriter);
    }
    printWriter.print(str2);
    printWriter.print("mIsLeashReadyForDispatching=");
    printWriter.print(this.mIsLeashReadyForDispatching);
    printWriter.println();
    if (this.mWindowContainer != null) {
      printWriter.print(str2 + "mWindowContainer=");
      printWriter.println(this.mWindowContainer);
    }
    if (this.mAdapter != null) {
      printWriter.print(str2 + "mAdapter=");
      this.mAdapter.dump(printWriter, "");
    }
    if (this.mControlTarget != null) {
      printWriter.print(str2 + "mControlTarget=");
      printWriter.println(this.mControlTarget);
    }
    if (this.mPendingControlTarget != null) {
      printWriter.print(str2 + "mPendingControlTarget=");
      printWriter.println(this.mPendingControlTarget);
    }
    if (this.mFakeControlTarget != null) {
      printWriter.print(str2 + "mFakeControlTarget=");
      printWriter.println(this.mFakeControlTarget);
    }
  }

  public void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
    long start = protoOutputStream.start(j);
    this.mSource.dumpDebug(protoOutputStream, 1146756268033L);
    this.mTmpRect.dumpDebug(protoOutputStream, 1146756268034L);
    this.mFakeControl.dumpDebug(protoOutputStream, 1146756268035L);
    InsetsSourceControl insetsSourceControl = this.mControl;
    if (insetsSourceControl != null) {
      insetsSourceControl.dumpDebug(protoOutputStream, 1146756268036L);
    }
    InsetsControlTarget insetsControlTarget = this.mControlTarget;
    if (insetsControlTarget != null && insetsControlTarget.getWindow() != null) {
      this.mControlTarget.getWindow().dumpDebug(protoOutputStream, 1146756268037L, i);
    }
    InsetsControlTarget insetsControlTarget2 = this.mPendingControlTarget;
    if (insetsControlTarget2 != null && insetsControlTarget2.getWindow() != null) {
      this.mPendingControlTarget.getWindow().dumpDebug(protoOutputStream, 1146756268038L, i);
    }
    InsetsControlTarget insetsControlTarget3 = this.mFakeControlTarget;
    if (insetsControlTarget3 != null && insetsControlTarget3.getWindow() != null) {
      this.mFakeControlTarget.getWindow().dumpDebug(protoOutputStream, 1146756268039L, i);
    }
    ControlAdapter controlAdapter = this.mAdapter;
    if (controlAdapter != null && controlAdapter.mCapturedLeash != null) {
      this.mAdapter.mCapturedLeash.dumpDebug(protoOutputStream, 1146756268040L);
    }
    protoOutputStream.write(1133871366154L, this.mIsLeashReadyForDispatching);
    protoOutputStream.write(1133871366155L, this.mClientVisible);
    protoOutputStream.write(1133871366156L, this.mServerVisible);
    protoOutputStream.write(1133871366157L, this.mSeamlessRotating);
    protoOutputStream.write(1133871366159L, this.mControllable);
    protoOutputStream.end(start);
  }

  public class ControlAdapter implements AnimationAdapter {
    public SurfaceControl mCapturedLeash;
    public final Point mSurfacePosition;

    @Override // com.android.server.wm.AnimationAdapter
    public void dumpDebug(ProtoOutputStream protoOutputStream) {}

    @Override // com.android.server.wm.AnimationAdapter
    public long getDurationHint() {
      return 0L;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public boolean getShowWallpaper() {
      return false;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public long getStatusBarTransitionsStartTime() {
      return 0L;
    }

    public ControlAdapter(Point point) {
      this.mSurfacePosition = point;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public void startAnimation(
        SurfaceControl surfaceControl,
        SurfaceControl.Transaction transaction,
        int i,
        SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
      if (InsetsSourceProvider.this.mSource.getType() == WindowInsets.Type.ime()) {
        transaction.setAlpha(surfaceControl, 1.0f);
        transaction.hide(surfaceControl);
      }
      if (ProtoLogCache.WM_DEBUG_WINDOW_INSETS_enabled) {
        ProtoLogImpl.i(
            ProtoLogGroup.WM_DEBUG_WINDOW_INSETS,
            -1185473319,
            0,
            (String) null,
            new Object[] {
              String.valueOf(InsetsSourceProvider.this.mSource),
              String.valueOf(InsetsSourceProvider.this.mControlTarget)
            });
      }
      this.mCapturedLeash = surfaceControl;
      Point point = this.mSurfacePosition;
      transaction.setPosition(surfaceControl, point.x, point.y);
      if (InsetsSourceProvider.this.mCropToProvidingInsets) {
        transaction.setWindowCrop(
            this.mCapturedLeash, InsetsSourceProvider.this.getProvidingInsetsBoundsCropRect());
      }
    }

    @Override // com.android.server.wm.AnimationAdapter
    public void onAnimationCancelled(SurfaceControl surfaceControl) {
      if (InsetsSourceProvider.this.mAdapter == this) {
        InsetsSourceProvider.this.mStateController.notifyControlRevoked(
            InsetsSourceProvider.this.mControlTarget, InsetsSourceProvider.this);
        InsetsSourceProvider.this.mControl = null;
        InsetsSourceProvider.this.mControlTarget = null;
        InsetsSourceProvider.this.mAdapter = null;
        InsetsSourceProvider.this.setClientVisible(
            (WindowInsets.Type.defaultVisible() & InsetsSourceProvider.this.mSource.getType())
                != 0);
        if (ProtoLogCache.WM_DEBUG_WINDOW_INSETS_enabled) {
          ProtoLogImpl.i(
              ProtoLogGroup.WM_DEBUG_WINDOW_INSETS,
              -1394745488,
              0,
              (String) null,
              new Object[] {
                String.valueOf(InsetsSourceProvider.this.mSource),
                String.valueOf(InsetsSourceProvider.this.mControlTarget)
              });
        }
      }
    }

    @Override // com.android.server.wm.AnimationAdapter
    public void dump(PrintWriter printWriter, String str) {
      printWriter.print(str + "ControlAdapter mCapturedLeash=");
      printWriter.print(this.mCapturedLeash);
      printWriter.println();
    }
  }
}
