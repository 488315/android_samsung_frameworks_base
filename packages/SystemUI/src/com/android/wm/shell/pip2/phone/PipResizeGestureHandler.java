package com.android.wm.shell.pip2.phone;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.BatchedInputEventReceiver;
import android.view.Choreographer;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputMonitor;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import com.android.internal.policy.TaskResizingAlgorithm;
import com.android.internal.util.Preconditions;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDesktopState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipPerfHintController;
import com.android.wm.shell.common.pip.PipSnapAlgorithm;
import com.android.wm.shell.common.pip.PipUiEventLogger;
import com.android.wm.shell.pip2.animation.PipResizeAnimator;
import com.android.wm.shell.pip2.phone.PipResizeGestureHandler.PipResizeInputEventReceiver;
import com.android.wm.shell.pip2.phone.PipTransitionState;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class PipResizeGestureHandler implements PipTransitionState.PipTransitionStateChangedListener {
    public boolean mAllowGesture;
    public final Context mContext;
    public int mCtrlType;
    public boolean mEnableDragCornerResize;
    public boolean mEnablePinchResize;
    public PipResizeInputEventReceiver mInputEventReceiver;
    public InputMonitor mInputMonitor;
    public boolean mIsAttached;
    public boolean mIsEnabled;
    public final ShellExecutor mMainExecutor;
    public final PhonePipMenuController mPhonePipMenuController;
    public final PipBoundsAlgorithm mPipBoundsAlgorithm;
    public final PipBoundsState mPipBoundsState;
    public final PipDesktopState mPipDesktopState;
    public final PipDisplayLayoutState mPipDisplayLayoutState;
    public final PipDragToResizeHandler mPipDragToResizeHandler;
    public PipPerfHintController.PipHighPerfSession mPipHighPerfSession;
    public final PipPerfHintController mPipPerfHintController;
    public final PipPinchToResizeHandler mPipPinchToResizeHandler;
    public final PipScheduler mPipScheduler;
    public final PipTouchState mPipTouchState;
    public final PipTransitionState mPipTransitionState;
    public final PipUiEventLogger mPipUiEventLogger;
    public boolean mThresholdCrossed;
    public float mTouchSlop;
    public final PointF mDownPoint = new PointF();
    public final PointF mDownSecondPoint = new PointF();
    public final PointF mLastPoint = new PointF();
    public final PointF mLastSecondPoint = new PointF();
    public final Rect mLastResizeBounds = new Rect();
    public final Rect mUserResizeBounds = new Rect();
    public final Rect mDownBounds = new Rect();
    public final Rect mStartBoundsAfterRelease = new Rect();
    public boolean mOngoingPinchToResize = false;
    public boolean mWaitingForBoundsChangeTransition = false;
    public float mAngle = 0.0f;

    public class PipResizeInputEventReceiver extends BatchedInputEventReceiver {
        public PipResizeInputEventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper, Choreographer.getInstance());
        }

        public final void onInputEvent(InputEvent inputEvent) {
            PipResizeGestureHandler.this.onInputEvent(inputEvent);
            finishInputEvent(inputEvent, true);
        }
    }

    public PipResizeGestureHandler(Context context, PipBoundsAlgorithm pipBoundsAlgorithm, PipBoundsState pipBoundsState, PipTouchState pipTouchState, PipScheduler pipScheduler, PipTransitionState pipTransitionState, PipUiEventLogger pipUiEventLogger, PhonePipMenuController phonePipMenuController, Function<Rect, Rect> function, PipDisplayLayoutState pipDisplayLayoutState, PipDesktopState pipDesktopState, ShellExecutor shellExecutor, PipPerfHintController pipPerfHintController) {
        this.mContext = context;
        this.mMainExecutor = shellExecutor;
        this.mPipPerfHintController = pipPerfHintController;
        this.mPipBoundsAlgorithm = pipBoundsAlgorithm;
        this.mPipBoundsState = pipBoundsState;
        this.mPipTouchState = pipTouchState;
        this.mPipScheduler = pipScheduler;
        this.mPipTransitionState = pipTransitionState;
        pipTransitionState.addPipTransitionStateChangedListener(this);
        this.mPhonePipMenuController = phonePipMenuController;
        this.mPipDisplayLayoutState = pipDisplayLayoutState;
        this.mPipDesktopState = pipDesktopState;
        this.mPipUiEventLogger = pipUiEventLogger;
        this.mPipDragToResizeHandler = new PipDragToResizeHandler(context, this, pipBoundsState, phonePipMenuController, pipBoundsAlgorithm, pipScheduler, function);
        this.mPipPinchToResizeHandler = new PipPinchToResizeHandler(this, pipBoundsState, phonePipMenuController, pipScheduler);
    }

    public final void finishResize() {
        if (this.mLastResizeBounds.isEmpty()) {
            this.mCtrlType = 0;
            this.mAngle = 0.0f;
            this.mOngoingPinchToResize = false;
            this.mAllowGesture = false;
            this.mThresholdCrossed = false;
            return;
        }
        this.mStartBoundsAfterRelease.set(this.mLastResizeBounds);
        boolean z = this.mOngoingPinchToResize;
        PipUiEventLogger pipUiEventLogger = this.mPipUiEventLogger;
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        if (!z) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("resize_bounds_change", true);
            pipTransitionState.setState(4, bundle);
            pipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_RESIZE);
            return;
        }
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        Point point = pipBoundsState.mMinSize;
        Point point2 = pipBoundsState.mMaxSize;
        if (this.mLastResizeBounds.width() >= point2.x * 0.9f || this.mLastResizeBounds.height() >= point2.y * 0.9f) {
            Rect rect = this.mLastResizeBounds;
            int i = point2.x;
            int i2 = point2.y;
            int iCenterX = rect.centerX() - (i / 2);
            int iCenterY = rect.centerY() - (i2 / 2);
            rect.set(iCenterX, iCenterY, i + iCenterX, i2 + iCenterY);
        }
        if (this.mLastResizeBounds.width() < point.x || this.mLastResizeBounds.height() < point.y) {
            Rect rect2 = this.mLastResizeBounds;
            int i3 = point.x;
            int i4 = point.y;
            int iCenterX2 = rect2.centerX() - (i3 / 2);
            int iCenterY2 = rect2.centerY() - (i4 / 2);
            rect2.set(iCenterX2, iCenterY2, i3 + iCenterX2, i4 + iCenterY2);
        }
        Rect rect3 = this.mLastResizeBounds;
        PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
        Rect movementBounds = pipBoundsAlgorithm.getMovementBounds(rect3, true);
        Rect rect4 = this.mLastResizeBounds;
        int i5 = rect4.left;
        rect4.offsetTo(Math.abs(i5 - movementBounds.left) < Math.abs(movementBounds.right - i5) ? movementBounds.left : movementBounds.right, this.mLastResizeBounds.top);
        Rect rect5 = this.mLastResizeBounds;
        PipSnapAlgorithm pipSnapAlgorithm = pipBoundsAlgorithm.mSnapAlgorithm;
        float snapFraction = pipSnapAlgorithm.getSnapFraction(0, rect5, movementBounds);
        Rect rect6 = this.mLastResizeBounds;
        Rect movementBounds2 = pipBoundsAlgorithm.getMovementBounds(rect6, true);
        pipSnapAlgorithm.getClass();
        PipSnapAlgorithm.applySnapFraction(rect6, movementBounds2, snapFraction);
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("resize_bounds_change", true);
        pipTransitionState.setState(4, bundle2);
        pipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_RESIZE);
    }

    public Rect getLastResizeBounds() {
        return this.mLastResizeBounds;
    }

    public void onInputEvent(InputEvent inputEvent) {
        Rect rect;
        if ((this.mEnableDragCornerResize || this.mEnablePinchResize) && this.mPipTouchState.mAllowInputEvents) {
            PipBoundsState pipBoundsState = this.mPipBoundsState;
            if (!pipBoundsState.isStashed() && (inputEvent instanceof MotionEvent)) {
                MotionEvent motionEvent = (MotionEvent) inputEvent;
                int actionMasked = motionEvent.getActionMasked();
                Rect bounds = pipBoundsState.getBounds();
                if ((actionMasked == 1 || actionMasked == 3) && !bounds.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
                    PhonePipMenuController phonePipMenuController = this.mPhonePipMenuController;
                    if (phonePipMenuController.isMenuVisible()) {
                        phonePipMenuController.hideMenu();
                    }
                }
                Point point = pipBoundsState.mMinSize;
                Point point2 = pipBoundsState.mMaxSize;
                if (this.mOngoingPinchToResize) {
                    this.mPipPinchToResizeHandler.onPinchResize(motionEvent, this.mDownPoint, this.mDownSecondPoint, this.mDownBounds, this.mLastPoint, this.mLastSecondPoint, this.mLastResizeBounds, this.mTouchSlop, point, point2);
                    return;
                }
                if (this.mEnableDragCornerResize) {
                    Rect rect2 = this.mLastResizeBounds;
                    PointF pointF = this.mDownPoint;
                    Rect rect3 = this.mDownBounds;
                    float f = this.mTouchSlop;
                    PipDragToResizeHandler pipDragToResizeHandler = this.mPipDragToResizeHandler;
                    pipDragToResizeHandler.getClass();
                    int actionMasked2 = motionEvent.getActionMasked();
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    PipBoundsState pipBoundsState2 = pipDragToResizeHandler.mPipBoundsState;
                    PipResizeGestureHandler pipResizeGestureHandler = pipDragToResizeHandler.mPipResizeGestureHandler;
                    if (actionMasked2 != 0) {
                        if (pipResizeGestureHandler.mAllowGesture) {
                            if (actionMasked2 != 1) {
                                if (actionMasked2 == 2) {
                                    if (pipResizeGestureHandler.mThresholdCrossed) {
                                        rect = rect3;
                                    } else {
                                        rect = rect3;
                                        if (Math.hypot(x - pointF.x, y - pointF.y) > f) {
                                            pipResizeGestureHandler.mThresholdCrossed = true;
                                            pointF.set(x, y);
                                            pipResizeGestureHandler.pilferPointers();
                                        }
                                    }
                                    if (pipResizeGestureHandler.mThresholdCrossed) {
                                        PhonePipMenuController phonePipMenuController2 = pipDragToResizeHandler.mPhonePipMenuController;
                                        if (phonePipMenuController2.isMenuVisible()) {
                                            phonePipMenuController2.hideMenu(0);
                                        }
                                        rect2.set(TaskResizingAlgorithm.resizeDrag(x, y, pointF.x, pointF.y, pipBoundsState2.getBounds(), pipResizeGestureHandler.mCtrlType, point.x, point.y, point2, true, rect.width() > rect.height()));
                                        pipDragToResizeHandler.mPipBoundsAlgorithm.transformBoundsToAspectRatio(pipBoundsState2.mAspectRatio, rect2, false, true);
                                        pipDragToResizeHandler.mPipScheduler.scheduleUserResizePip(rect2, 0.0f);
                                        pipBoundsState2.setHasUserResizedPip();
                                        return;
                                    }
                                    return;
                                }
                                if (actionMasked2 != 3) {
                                    if (actionMasked2 != 5) {
                                        return;
                                    }
                                    pipResizeGestureHandler.mAllowGesture = false;
                                    return;
                                }
                            }
                            pipResizeGestureHandler.finishResize();
                            return;
                        }
                        return;
                    }
                    rect2.setEmpty();
                    int i = (int) x;
                    int i2 = (int) y;
                    boolean zIsWithinDragResizeRegion = pipDragToResizeHandler.isWithinDragResizeRegion(i, i2);
                    pipResizeGestureHandler.mAllowGesture = zIsWithinDragResizeRegion;
                    if (zIsWithinDragResizeRegion) {
                        Rect bounds2 = pipBoundsState2.getBounds();
                        int i3 = pipResizeGestureHandler.mCtrlType;
                        Rect rect4 = (Rect) pipDragToResizeHandler.mMovementBoundsSupplier.apply(bounds2);
                        pipDragToResizeHandler.mDisplayBounds.set(rect4.left, rect4.top, bounds2.width() + rect4.right, bounds2.height() + rect4.bottom);
                        if (pipDragToResizeHandler.mTmpTopLeftCorner.contains(i, i2)) {
                            int i4 = bounds2.top;
                            Rect rect5 = pipDragToResizeHandler.mDisplayBounds;
                            if (i4 != rect5.top && bounds2.left != rect5.left) {
                                i3 |= 5;
                            }
                        }
                        if (pipDragToResizeHandler.mTmpTopRightCorner.contains(i, i2)) {
                            int i5 = bounds2.top;
                            Rect rect6 = pipDragToResizeHandler.mDisplayBounds;
                            if (i5 != rect6.top && bounds2.right != rect6.right) {
                                i3 |= 6;
                            }
                        }
                        if (pipDragToResizeHandler.mTmpBottomRightCorner.contains(i, i2)) {
                            int i6 = bounds2.bottom;
                            Rect rect7 = pipDragToResizeHandler.mDisplayBounds;
                            if (i6 != rect7.bottom && bounds2.right != rect7.right) {
                                i3 |= 10;
                            }
                        }
                        if (pipDragToResizeHandler.mTmpBottomLeftCorner.contains(i, i2)) {
                            int i7 = bounds2.bottom;
                            Rect rect8 = pipDragToResizeHandler.mDisplayBounds;
                            if (i7 != rect8.bottom && bounds2.left != rect8.left) {
                                i3 |= 9;
                            }
                        }
                        pipResizeGestureHandler.mCtrlType = i3;
                        pointF.set(x, y);
                        rect3.set(pipBoundsState2.getBounds());
                    }
                }
            }
        }
    }

    @Override // com.android.wm.shell.pip2.phone.PipTransitionState.PipTransitionStateChangedListener
    public final void onPipTransitionStateChanged(int i, int i2, Bundle bundle) {
        if (i2 == 4) {
            if (bundle.getBoolean("resize_bounds_change")) {
                PipBoundsState pipBoundsState = this.mPipBoundsState;
                if (pipBoundsState.getBounds().equals(this.mLastResizeBounds)) {
                    double snapFraction = this.mPipBoundsAlgorithm.getSnapFraction(pipBoundsState.getBounds());
                    this.mLastResizeBounds.offset(0, (snapFraction < 1.5d || snapFraction > 3.5d) ? 1 : -1);
                }
                this.mWaitingForBoundsChangeTransition = true;
                this.mPipScheduler.scheduleAnimateResizePip(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, true, this.mLastResizeBounds);
                return;
            }
            return;
        }
        if (i2 == 5 && this.mWaitingForBoundsChangeTransition) {
            this.mWaitingForBoundsChangeTransition = false;
            PipTransitionState pipTransitionState = this.mPipTransitionState;
            SurfaceControl surfaceControl = pipTransitionState.mPinnedTaskLeash;
            Preconditions.checkState(surfaceControl != null, "No leash cached by mPipTransitionState=" + pipTransitionState);
            SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) bundle.getParcelable("pip_start_tx", SurfaceControl.Transaction.class);
            SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) bundle.getParcelable("pip_finish_tx", SurfaceControl.Transaction.class);
            final Rect rect = (Rect) bundle.getParcelable("pip_dest_bounds", Rect.class);
            PipResizeAnimator pipResizeAnimator = new PipResizeAnimator(this.mContext, surfaceControl, transaction, transaction2, rect, this.mStartBoundsAfterRelease, rect, bundle.getInt("animating_bounds_change_duration", 0), this.mAngle);
            pipResizeAnimator.mAnimationEndCallback = new Runnable() { // from class: com.android.wm.shell.pip2.phone.PipResizeGestureHandler$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    PipResizeGestureHandler pipResizeGestureHandler = this.f$0;
                    Rect rect2 = rect;
                    pipResizeGestureHandler.mUserResizeBounds.set(rect2);
                    pipResizeGestureHandler.mCtrlType = 0;
                    pipResizeGestureHandler.mAngle = 0.0f;
                    pipResizeGestureHandler.mOngoingPinchToResize = false;
                    pipResizeGestureHandler.mAllowGesture = false;
                    pipResizeGestureHandler.mThresholdCrossed = false;
                    PipPerfHintController.PipHighPerfSession pipHighPerfSession = pipResizeGestureHandler.mPipHighPerfSession;
                    if (pipHighPerfSession != null) {
                        pipHighPerfSession.close();
                        pipResizeGestureHandler.mPipHighPerfSession = null;
                    }
                    pipResizeGestureHandler.mPipScheduler.scheduleFinishResizePip(rect2);
                }
            };
            pipResizeAnimator.start();
        }
    }

    public void pilferPointers() {
        this.mInputMonitor.pilferPointers();
    }

    public final void updateIsEnabled() {
        boolean z = this.mIsAttached;
        if (z == this.mIsEnabled) {
            return;
        }
        this.mIsEnabled = z;
        PipResizeInputEventReceiver pipResizeInputEventReceiver = this.mInputEventReceiver;
        if (pipResizeInputEventReceiver != null) {
            pipResizeInputEventReceiver.dispose();
            this.mInputEventReceiver = null;
        }
        InputMonitor inputMonitor = this.mInputMonitor;
        if (inputMonitor != null) {
            inputMonitor.dispose();
            this.mInputMonitor = null;
        }
        if (this.mIsEnabled) {
            this.mInputMonitor = ((InputManager) this.mContext.getSystemService(InputManager.class)).monitorGestureInput("pip-resize", this.mPipDisplayLayoutState.mDisplayId);
            try {
                this.mMainExecutor.executeBlocking(new Runnable() { // from class: com.android.wm.shell.pip2.phone.PipResizeGestureHandler$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        PipResizeGestureHandler pipResizeGestureHandler = this.f$0;
                        pipResizeGestureHandler.getClass();
                        pipResizeGestureHandler.mInputEventReceiver = pipResizeGestureHandler.new PipResizeInputEventReceiver(pipResizeGestureHandler.mInputMonitor.getInputChannel(), Looper.myLooper());
                    }
                });
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to create input event receiver", e);
            }
        }
    }
}
