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
import android.view.inputmethod.Flags;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.function.TriFunction;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;

public class InsetsSourceProvider {
    public static final Rect EMPTY_RECT = new Rect();
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
    public boolean mHasPendingPosition;
    public Insets mInsetsHint;
    public boolean mInsetsHintStale;
    public boolean mIsLeashReadyForDispatching;
    public SparseArray mOverrideFrameProviders;
    public InsetsControlTarget mPendingControlTarget;
    public boolean mSeamlessRotating;
    public boolean mServerVisible;
    public final InsetsSourceProvider$$ExternalSyntheticLambda0 mSetLeashPositionConsumer;
    public final InsetsSource mSource;
    public final InsetsStateController mStateController;
    public WindowContainer mWindowContainer;
    public final Rect mTmpRect = new Rect();
    public final SparseArray mOverrideFrames = new SparseArray();
    public final Rect mSourceFrame = new Rect();
    public final Rect mLastSourceFrame = new Rect();

    public final class ControlAdapter implements AnimationAdapter {
        public SurfaceControl mCapturedLeash;
        public final Point mSurfacePosition;

        public ControlAdapter(Point point) {
            this.mSurfacePosition = point;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final void dump(PrintWriter printWriter, String str) {
            printWriter.print(str + "ControlAdapter mCapturedLeash=");
            printWriter.print(this.mCapturedLeash);
            printWriter.println();
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final void dumpDebug$1(ProtoOutputStream protoOutputStream) {}

        @Override // com.android.server.wm.AnimationAdapter
        public final long getDurationHint() {
            return 0L;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final boolean getShowWallpaper() {
            return false;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final long getStatusBarTransitionsStartTime() {
            return 0L;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final void onAnimationCancelled(SurfaceControl surfaceControl) {
            InsetsSourceProvider insetsSourceProvider = InsetsSourceProvider.this;
            if (insetsSourceProvider.mAdapter == this) {
                insetsSourceProvider.mStateController.removeFromControlMaps(
                        insetsSourceProvider.mControlTarget, insetsSourceProvider, false);
                insetsSourceProvider.mControl = null;
                insetsSourceProvider.mControlTarget = null;
                insetsSourceProvider.mAdapter = null;
                insetsSourceProvider.setClientVisible(
                        (WindowInsets.Type.defaultVisible()
                                        & insetsSourceProvider.mSource.getType())
                                != 0);
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_INSETS_enabled[2]) {
                    ProtoLogImpl_54989576.i(
                            ProtoLogGroup.WM_DEBUG_WINDOW_INSETS,
                            -6857870589074001153L,
                            0,
                            null,
                            String.valueOf(insetsSourceProvider.mSource),
                            String.valueOf(insetsSourceProvider.mControlTarget));
                }
            }
        }

        @Override // com.android.server.wm.AnimationAdapter
        public final void startAnimation(
                SurfaceControl surfaceControl,
                SurfaceControl.Transaction transaction,
                int i,
                SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
            InsetsSourceProvider insetsSourceProvider = InsetsSourceProvider.this;
            if (insetsSourceProvider.mSource.getType() == WindowInsets.Type.ime()
                    && (!Flags.refactorInsetsController()
                            || !insetsSourceProvider.mClientVisible)) {
                transaction.setAlpha(surfaceControl, 1.0f);
                transaction.hide(surfaceControl);
            }
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_INSETS_enabled[2]) {
                ProtoLogImpl_54989576.i(
                        ProtoLogGroup.WM_DEBUG_WINDOW_INSETS,
                        -8601070090234611338L,
                        0,
                        null,
                        String.valueOf(insetsSourceProvider.mSource),
                        String.valueOf(insetsSourceProvider.mControlTarget));
            }
            this.mCapturedLeash = surfaceControl;
            Point point = this.mSurfacePosition;
            transaction.setPosition(surfaceControl, point.x, point.y);
            insetsSourceProvider.getClass();
        }
    }

    public InsetsSourceProvider(
            InsetsSource insetsSource,
            InsetsStateController insetsStateController,
            DisplayContent displayContent) {
        Insets insets = Insets.NONE;
        this.mInsetsHint = insets;
        this.mInsetsHintStale = true;
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
                        insets);
        this.mControllable = (insetsSource.getType() & InsetsPolicy.CONTROLLABLE_TYPES) != 0;
        this.mSetLeashPositionConsumer = new InsetsSourceProvider$$ExternalSyntheticLambda0(this);
    }

    public void dump(PrintWriter printWriter, String str) {
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str);
        m.append(getClass().getSimpleName());
        printWriter.println(m.toString());
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
        if (this.mControllable) {
            printWriter.print(str2 + "mInsetsHint=");
            printWriter.print(this.mInsetsHint);
            if (this.mInsetsHintStale) {
                printWriter.print(" stale");
            }
            printWriter.println();
        }
        printWriter.print(str2);
        printWriter.print("mIsLeashReadyForDispatching=");
        printWriter.print(this.mIsLeashReadyForDispatching);
        printWriter.print(" mHasPendingPosition=");
        printWriter.print(this.mHasPendingPosition);
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
        if (this.mPendingControlTarget != this.mControlTarget) {
            printWriter.print(str2 + "mPendingControlTarget=");
            printWriter.println(this.mPendingControlTarget);
        }
        if (this.mFakeControlTarget != null) {
            printWriter.print(str2 + "mFakeControlTarget=");
            printWriter.println(this.mFakeControlTarget);
        }
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
        SurfaceControl surfaceControl;
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
        if (insetsControlTarget2 != null
                && insetsControlTarget2 != this.mControlTarget
                && insetsControlTarget2.getWindow() != null) {
            this.mPendingControlTarget.getWindow().dumpDebug(protoOutputStream, 1146756268038L, i);
        }
        InsetsControlTarget insetsControlTarget3 = this.mFakeControlTarget;
        if (insetsControlTarget3 != null && insetsControlTarget3.getWindow() != null) {
            this.mFakeControlTarget.getWindow().dumpDebug(protoOutputStream, 1146756268039L, i);
        }
        ControlAdapter controlAdapter = this.mAdapter;
        if (controlAdapter != null && (surfaceControl = controlAdapter.mCapturedLeash) != null) {
            surfaceControl.dumpDebug(protoOutputStream, 1146756268040L);
        }
        protoOutputStream.write(1133871366154L, this.mIsLeashReadyForDispatching);
        protoOutputStream.write(1133871366155L, this.mClientVisible);
        protoOutputStream.write(1133871366156L, this.mServerVisible);
        protoOutputStream.write(1133871366157L, this.mSeamlessRotating);
        protoOutputStream.write(1133871366159L, this.mControllable);
        WindowContainer windowContainer = this.mWindowContainer;
        if (windowContainer != null && windowContainer.asWindowState() != null) {
            this.mWindowContainer.asWindowState().dumpDebug(protoOutputStream, 1146756268048L, i);
        }
        protoOutputStream.end(start);
    }

    public InsetsSourceControl getControl(InsetsControlTarget insetsControlTarget) {
        if (insetsControlTarget == this.mControlTarget) {
            return (isLeashReadyForDispatching() || this.mControl == null)
                    ? this.mControl
                    : new InsetsSourceControl(
                            this.mControl.getId(),
                            this.mControl.getType(),
                            (SurfaceControl) null,
                            this.mControl.isInitiallyVisible(),
                            this.mControl.getSurfacePosition(),
                            this.mControl.getInsetsHint());
        }
        if (insetsControlTarget == this.mFakeControlTarget) {
            return this.mFakeControl;
        }
        return null;
    }

    public Insets getInsetsHint() {
        if (!this.mServerVisible) {
            return this.mInsetsHint;
        }
        WindowState asWindowState = this.mWindowContainer.asWindowState();
        if (asWindowState != null && asWindowState.mGivenInsetsPending) {
            return this.mInsetsHint;
        }
        if (this.mInsetsHintStale) {
            this.mInsetsHint =
                    this.mSource.calculateInsets(this.mWindowContainer.getBounds(), true);
            this.mInsetsHintStale = false;
        }
        return this.mInsetsHint;
    }

    public final Point getWindowFrameSurfacePosition() {
        AsyncRotationController asyncRotationController;
        int i;
        WindowState asWindowState = this.mWindowContainer.asWindowState();
        if (asWindowState != null
                && this.mControl != null
                && (asyncRotationController = this.mDisplayContent.getAsyncRotationController())
                        != null
                && (i = asyncRotationController.mTransitionOp) != 0
                && ((i == 1 || i == 3 || TransitionController.SYNC_METHOD == 1)
                        && AsyncRotationController.canBeAsync(asWindowState.mToken))) {
            if (asyncRotationController.mTargetWindowTokens.containsKey(asWindowState.mToken)) {
                return this.mControl.getSurfacePosition();
            }
        }
        Rect bounds =
                asWindowState != null
                        ? asWindowState.mWindowFrames.mFrame
                        : this.mWindowContainer.getBounds();
        Point point = new Point();
        this.mWindowContainer.transformFrameToSurfacePosition(bounds.left, bounds.top, point);
        return point;
    }

    public boolean isLeashReadyForDispatching() {
        return this.mIsLeashReadyForDispatching;
    }

    public void onPostLayout() {
        InsetsSourceControl insetsSourceControl;
        WindowContainer windowContainer = this.mWindowContainer;
        if (windowContainer == null) {
            return;
        }
        WindowState asWindowState = windowContainer.asWindowState();
        boolean isVisibleRequested =
                asWindowState != null
                        ? asWindowState.wouldBeVisibleIfPolicyIgnored()
                                && asWindowState.isVisibleByPolicy()
                        : this.mWindowContainer.isVisibleRequested();
        if (Flags.refactorInsetsController()
                && (insetsSourceControl = this.mControl) != null
                && insetsSourceControl.getType() == WindowInsets.Type.ime()
                && !this.mServerVisible
                && isVisibleRequested
                && asWindowState != null) {
            isVisibleRequested = asWindowState.isDrawn() && !asWindowState.mGivenInsetsPending;
        }
        boolean z = this.mServerVisible != isVisibleRequested;
        setServerVisible(isVisibleRequested);
        updateInsetsControlPosition(asWindowState, z);
    }

    public void setClientVisible(boolean z) {
        if (this.mClientVisible == z) {
            return;
        }
        this.mClientVisible = z;
        updateVisibility();
        DisplayContent displayContent = this.mDisplayContent;
        displayContent.setLayoutNeeded();
        displayContent.mWmService.mWindowPlacerLocked.requestTraversal();
    }

    public final boolean setFlags(int i) {
        int i2 = (i & 4) | (this.mFlagsFromServer & (-5));
        this.mFlagsFromServer = i2;
        int i3 = i2 | this.mFlagsFromFrameProvider;
        if (this.mSource.getFlags() == i3) {
            return false;
        }
        this.mSource.setFlags(i3);
        return true;
    }

    public void setServerVisible(boolean z) {
        this.mServerVisible = z;
        updateSourceFrameForServerVisibility();
        updateVisibility();
    }

    public final void setWindowContainer(
            WindowContainer windowContainer, TriFunction triFunction, SparseArray sparseArray) {
        WindowContainer windowContainer2 = this.mWindowContainer;
        boolean z = this.mControllable;
        if (windowContainer2 != null) {
            if (z) {
                windowContainer2.setControllableInsetProvider(null);
            }
            this.mWindowContainer.cancelAnimation();
            this.mWindowContainer.getInsetsSourceProviders().remove(this.mSource.getId());
            this.mSeamlessRotating = false;
            this.mHasPendingPosition = false;
            DisplayContent displayContent = this.mDisplayContent;
            if (displayContent.mInsetsPolicy.isTransient(this.mSource.getType())) {
                displayContent.mInsetsPolicy.abortTransient();
            }
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_INSETS_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_WINDOW_INSETS,
                    1522894362518893789L,
                    0,
                    null,
                    String.valueOf(windowContainer),
                    String.valueOf(WindowInsets.Type.toString(this.mSource.getType())));
        }
        this.mWindowContainer = windowContainer;
        this.mFrameProvider = triFunction;
        this.mOverrideFrames.clear();
        this.mOverrideFrameProviders = sparseArray;
        if (windowContainer == null) {
            setServerVisible(false);
            this.mSource.setVisibleFrame((Rect) null);
            this.mSource.setFlags(0, -1);
            this.mSourceFrame.setEmpty();
            return;
        }
        this.mWindowContainer.getInsetsSourceProviders().put(this.mSource.getId(), this);
        if (z) {
            this.mWindowContainer.setControllableInsetProvider(this);
            InsetsControlTarget insetsControlTarget = this.mPendingControlTarget;
            if (insetsControlTarget != this.mControlTarget) {
                InsetsStateController insetsStateController = this.mStateController;
                insetsStateController.onControlTargetChanged(insetsControlTarget, this, false);
                insetsStateController.notifyPendingInsetsControlChanged();
            }
        }
    }

    public boolean updateClientVisibility(InsetsControlTarget insetsControlTarget) {
        boolean isRequestedVisible = insetsControlTarget.isRequestedVisible(this.mSource.getType());
        if (insetsControlTarget != this.mControlTarget
                || isRequestedVisible == this.mClientVisible) {
            return false;
        }
        setClientVisible(isRequestedVisible);
        return true;
    }

    public void updateControlForTarget(InsetsControlTarget insetsControlTarget, boolean z) {
        boolean z2;
        int i;
        if (this.mSeamlessRotating) {
            return;
        }
        this.mPendingControlTarget = insetsControlTarget;
        WindowContainer windowContainer = this.mWindowContainer;
        if (windowContainer != null && windowContainer.getSurfaceControl() == null) {
            setWindowContainer(null, null, null);
        }
        WindowContainer windowContainer2 = this.mWindowContainer;
        if (windowContainer2 == null) {
            return;
        }
        if ((insetsControlTarget != this.mControlTarget || z) && !this.mHasPendingPosition) {
            if (insetsControlTarget == null) {
                windowContainer2.cancelAnimation();
                setClientVisible(
                        (WindowInsets.Type.defaultVisible() & this.mSource.getType()) != 0);
                return;
            }
            Point windowFrameSurfacePosition = getWindowFrameSurfacePosition();
            DisplayContent displayContent = this.mDisplayContent;
            if (displayContent.mDisplayId == 2
                    && this.mWindowContainer.asWindowState() != null
                    && this.mWindowContainer.asWindowState().mWindowFrames.mFrame.isEmpty()
                    && this.mWindowContainer.asWindowState().mAttrs.type == 2019
                    && windowFrameSurfacePosition.y
                            != (i =
                                    displayContent.getBounds().bottom
                                            - displayContent.mDisplayPolicy.mDexTaskbarHeight)) {
                windowFrameSurfacePosition.y = i;
            }
            this.mAdapter = new ControlAdapter(windowFrameSurfacePosition);
            if (this.mSource.getType() == WindowInsets.Type.ime()) {
                z2 =
                        displayContent.mRemoteInsetsControlTarget == this.mControlTarget
                                && displayContent.mImeInputTarget == insetsControlTarget
                                && this.mClientVisible;
                setClientVisible(insetsControlTarget.isRequestedVisible(WindowInsets.Type.ime()));
            } else {
                z2 = false;
            }
            this.mWindowContainer.startAnimation(
                    this.mWindowContainer.getSyncTransaction(),
                    this.mAdapter,
                    !this.mClientVisible,
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
                            this.mSource.getType() == WindowInsets.Type.ime()
                                    ? z2
                                    : this.mClientVisible,
                            windowFrameSurfacePosition,
                            getInsetsHint());
            this.mControl = insetsSourceControl;
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_INSETS_enabled[0]) {
                ProtoLogImpl_54989576.d(
                        ProtoLogGroup.WM_DEBUG_WINDOW_INSETS,
                        6243049416211184258L,
                        0,
                        null,
                        String.valueOf(insetsSourceControl),
                        String.valueOf(this.mControlTarget));
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

    public final void updateInsetsControlPosition(WindowState windowState, boolean z) {
        AsyncRotationController asyncRotationController;
        AsyncRotationController.Operation operation;
        if (this.mControl == null) {
            return;
        }
        Point windowFrameSurfacePosition = getWindowFrameSurfacePosition();
        boolean z2 = false;
        if (this.mControl.setSurfacePosition(
                        windowFrameSurfacePosition.x, windowFrameSurfacePosition.y)
                && this.mControlTarget != null) {
            InsetsSourceProvider$$ExternalSyntheticLambda0
                    insetsSourceProvider$$ExternalSyntheticLambda0 = this.mSetLeashPositionConsumer;
            if (windowState != null
                    && windowState.mWindowFrames.didFrameSizeChange()
                    && windowState.mWinAnimator.getShown()
                    && this.mWindowContainer.okToDisplay()) {
                this.mHasPendingPosition = true;
                windowState.applyWithNextDraw(0, insetsSourceProvider$$ExternalSyntheticLambda0);
            } else {
                SurfaceControl.Transaction syncTransaction =
                        this.mWindowContainer.getSyncTransaction();
                if (windowState != null
                        && (asyncRotationController =
                                        this.mDisplayContent.getAsyncRotationController())
                                != null) {
                    WindowToken windowToken = windowState.mToken;
                    SurfaceControl.Transaction transaction = null;
                    if (asyncRotationController.mTransitionOp != 0
                            && (operation =
                                            (AsyncRotationController.Operation)
                                                    asyncRotationController.mTargetWindowTokens.get(
                                                            windowToken))
                                    != null) {
                        if (operation.mDrawTransaction == null) {
                            operation.mDrawTransaction = new SurfaceControl.Transaction();
                        }
                        transaction = operation.mDrawTransaction;
                    }
                    if (transaction != null) {
                        syncTransaction = transaction;
                    }
                }
                insetsSourceProvider$$ExternalSyntheticLambda0.accept(syncTransaction);
            }
            z2 = true;
        }
        Insets insetsHint = getInsetsHint();
        if (!this.mControl.getInsetsHint().equals(insetsHint)) {
            this.mControl.setInsetsHint(insetsHint);
            z2 = true;
        }
        if ((Flags.refactorInsetsController() && z) ? true : z2) {
            this.mStateController.notifyControlChanged(this.mControlTarget);
        }
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
                                            this.mWindowContainer.getDisplayContent()
                                                    .mDisplayFrames,
                                            this.mWindowContainer,
                                            this.mSourceFrame))
                            .intValue();
            this.mFlagsFromFrameProvider = intValue;
            this.mSource.setFlags(intValue | this.mFlagsFromServer);
        }
        updateSourceFrameForServerVisibility();
        if (!this.mLastSourceFrame.equals(this.mSourceFrame)) {
            this.mLastSourceFrame.set(this.mSourceFrame);
            this.mInsetsHintStale = true;
        }
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
        if (rect3.left == 0 && rect3.top == 0 && rect3.right == 0 && rect3.bottom == 0) {
            this.mSource.setVisibleFrame((Rect) null);
        } else {
            this.mTmpRect.set(rect);
            this.mTmpRect.inset(asWindowState.mGivenVisibleInsets);
            this.mSource.setVisibleFrame(this.mTmpRect);
        }
        if (CoreRune.FW_MINIMIZED_IME_INSET_ANIM && asWindowState.mIsImWindow) {
            this.mSource.setMinimizedInsetHint(asWindowState.mMinimizedInsets);
        }
    }

    public final void updateSourceFrameForServerVisibility() {
        Rect rect = this.mServerVisible ? this.mSourceFrame : EMPTY_RECT;
        if (this.mSource.getFrame().equals(rect)) {
            return;
        }
        this.mSource.setFrame(rect);
        WindowContainer windowContainer = this.mWindowContainer;
        if (windowContainer != null) {
            this.mSource.updateSideHint(windowContainer.getBounds());
        }
    }

    public void updateVisibility() {
        boolean isVisible = this.mSource.isVisible();
        this.mSource.setVisible(this.mServerVisible && this.mClientVisible);
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WINDOW_INSETS_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_WINDOW_INSETS,
                    -8234068212532234206L,
                    0,
                    null,
                    String.valueOf(WindowInsets.Type.toString(this.mSource.getType())),
                    String.valueOf(this.mServerVisible),
                    String.valueOf(this.mClientVisible));
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
}
