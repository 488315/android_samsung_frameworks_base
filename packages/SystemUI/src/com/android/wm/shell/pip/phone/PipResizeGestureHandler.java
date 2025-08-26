package com.android.wm.shell.pip.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.BatchedInputEventReceiver;
import android.view.Choreographer;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputMonitor;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.window.DesktopModeFlags;
import com.android.internal.policy.TaskResizingAlgorithm;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipPerfHintController;
import com.android.wm.shell.common.pip.PipPinchResizingAlgorithm;
import com.android.wm.shell.common.pip.PipSnapAlgorithm;
import com.android.wm.shell.common.pip.PipUiEventLogger;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class PipResizeGestureHandler {
    public boolean mAllowGesture;
    public final Context mContext;
    public int mCtrlType;
    public int mDelta;
    public final int mDisplayId;
    public boolean mEnableDragCornerResize;
    public boolean mEnablePinchResize;
    public PipResizeInputEventReceiver mInputEventReceiver;
    public InputMonitor mInputMonitor;
    public boolean mIsAttached;
    public boolean mIsEnabled;
    public boolean mIsSysUiStateValid;
    public final ShellExecutor mMainExecutor;
    public final PipMotionHelper mMotionHelper;
    public final Function mMovementBoundsSupplier;
    public int mOhmOffset;
    public final PhonePipMenuController mPhonePipMenuController;
    public final PipBoundsAlgorithm mPipBoundsAlgorithm;
    public final PipBoundsState mPipBoundsState;
    public final PipDismissTargetHandler mPipDismissTargetHandler;
    public PipPerfHintController.PipHighPerfSession mPipHighPerfSession;
    public final PipPerfHintController mPipPerfHintController;
    public final PipTaskOrganizer mPipTaskOrganizer;
    public final PipTouchState mPipTouchState;
    public final PipUiEventLogger mPipUiEventLogger;
    public boolean mThresholdCrossed;
    public float mTouchSlop;
    public final Runnable mUpdateMovementBoundsRunnable;
    public final Region mTmpRegion = new Region();
    public final PointF mDownPoint = new PointF();
    public final PointF mDownSecondPoint = new PointF();
    public final PointF mLastPoint = new PointF();
    public final PointF mLastSecondPoint = new PointF();
    public final Point mMaxSize = new Point();
    public final Point mMinSize = new Point();
    public final Rect mLastResizeBounds = new Rect();
    public final Rect mUserResizeBounds = new Rect();
    public final Rect mDownBounds = new Rect();
    public final Rect mDragCornerSize = new Rect();
    public final Rect mTmpTopLeftCorner = new Rect();
    public final Rect mTmpTopRightCorner = new Rect();
    public final Rect mTmpBottomLeftCorner = new Rect();
    public final Rect mTmpBottomRightCorner = new Rect();
    public final Rect mDisplayBounds = new Rect();
    public boolean mOngoingPinchToResize = false;
    public float mAngle = 0.0f;
    public int mFirstIndex = -1;
    public int mSecondIndex = -1;
    public final PipPinchResizingAlgorithm mPinchResizingAlgorithm = new PipPinchResizingAlgorithm();
    public final PipResizeGestureHandler$$ExternalSyntheticLambda0 mUpdateResizeBoundsCallback = new PipResizeGestureHandler$$ExternalSyntheticLambda0(this, 0);
    public final PipResizeGestureHandler$$ExternalSyntheticLambda1 mResetTouchStateRunnable = new PipResizeGestureHandler$$ExternalSyntheticLambda1(this, 0);

    public class PipResizeInputEventReceiver extends BatchedInputEventReceiver {
        public PipResizeInputEventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper, Choreographer.getInstance());
        }

        public final void onInputEvent(InputEvent inputEvent) throws Resources.NotFoundException {
            PipResizeGestureHandler.this.onInputEvent(inputEvent);
            finishInputEvent(inputEvent, true);
        }
    }

    public PipResizeGestureHandler(Context context, PipBoundsAlgorithm pipBoundsAlgorithm, PipBoundsState pipBoundsState, PipMotionHelper pipMotionHelper, PipTouchState pipTouchState, PipTaskOrganizer pipTaskOrganizer, PipDismissTargetHandler pipDismissTargetHandler, Function<Rect, Rect> function, Runnable runnable, PipUiEventLogger pipUiEventLogger, PhonePipMenuController phonePipMenuController, ShellExecutor shellExecutor, PipPerfHintController pipPerfHintController) {
        this.mContext = context;
        this.mDisplayId = context.getDisplayId();
        this.mMainExecutor = shellExecutor;
        this.mPipPerfHintController = pipPerfHintController;
        this.mPipBoundsAlgorithm = pipBoundsAlgorithm;
        this.mPipBoundsState = pipBoundsState;
        this.mMotionHelper = pipMotionHelper;
        this.mPipTouchState = pipTouchState;
        this.mPipTaskOrganizer = pipTaskOrganizer;
        this.mPipDismissTargetHandler = pipDismissTargetHandler;
        this.mMovementBoundsSupplier = function;
        this.mUpdateMovementBoundsRunnable = runnable;
        this.mPhonePipMenuController = phonePipMenuController;
        this.mPipUiEventLogger = pipUiEventLogger;
    }

    public final void finishResize() throws Resources.NotFoundException {
        if (this.mLastResizeBounds.isEmpty()) {
            resetState();
            return;
        }
        boolean z = this.mOngoingPinchToResize;
        PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
        if (z) {
            Rect rect = new Rect(this.mLastResizeBounds);
            if (this.mLastResizeBounds.width() >= this.mMaxSize.x * 0.9f || this.mLastResizeBounds.height() >= this.mMaxSize.y * 0.9f) {
                Rect rect2 = this.mLastResizeBounds;
                Point point = this.mMaxSize;
                int i = point.x;
                int i2 = point.y;
                int iCenterX = rect2.centerX() - (i / 2);
                int iCenterY = rect2.centerY() - (i2 / 2);
                rect2.set(iCenterX, iCenterY, i + iCenterX, i2 + iCenterY);
            }
            if (this.mLastResizeBounds.width() < this.mMinSize.x || this.mLastResizeBounds.height() < this.mMinSize.y) {
                Rect rect3 = this.mLastResizeBounds;
                Point point2 = this.mMinSize;
                int i3 = point2.x;
                int i4 = point2.y;
                int iCenterX2 = rect3.centerX() - (i3 / 2);
                int iCenterY2 = rect3.centerY() - (i4 / 2);
                rect3.set(iCenterX2, iCenterY2, i3 + iCenterX2, i4 + iCenterY2);
            }
            Rect rect4 = this.mLastResizeBounds;
            PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
            Rect movementBounds = pipBoundsAlgorithm.getMovementBounds(rect4, true);
            Rect rect5 = this.mLastResizeBounds;
            int i5 = rect5.left;
            rect5.offsetTo(Math.abs(i5 - movementBounds.left) < Math.abs(movementBounds.right - i5) ? movementBounds.left : movementBounds.right, this.mLastResizeBounds.top);
            Rect rect6 = this.mLastResizeBounds;
            PipSnapAlgorithm pipSnapAlgorithm = pipBoundsAlgorithm.mSnapAlgorithm;
            float snapFraction = pipSnapAlgorithm.getSnapFraction(0, rect6, movementBounds);
            Rect rect7 = this.mLastResizeBounds;
            Rect movementBounds2 = pipBoundsAlgorithm.getMovementBounds(rect7, true);
            pipSnapAlgorithm.getClass();
            PipSnapAlgorithm.applySnapFraction(rect7, movementBounds2, snapFraction);
            PipTouchState pipTouchState = this.mPipTouchState;
            pipTouchState.mAllowInputEvents = false;
            Rect rect8 = this.mLastResizeBounds;
            float f = this.mAngle;
            pipTaskOrganizer.mPipFinishResizeWCTRunnable = new PipResizeGestureHandler$$ExternalSyntheticLambda1(this, 1);
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -8534472667399376535L, 0, null);
            }
            if (!pipTaskOrganizer.mWaitForFixedRotation) {
                pipTaskOrganizer.scheduleAnimateResizePip(rect, rect8, f, null, 6, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, this.mUpdateResizeBoundsCallback);
            } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 3575265343742099329L, 0, "PipTaskOrganizer");
            }
            if (!pipTouchState.mAllowTouches) {
                HandlerExecutor handlerExecutor = (HandlerExecutor) this.mMainExecutor;
                Handler handler = handlerExecutor.mHandler;
                PipResizeGestureHandler$$ExternalSyntheticLambda1 pipResizeGestureHandler$$ExternalSyntheticLambda1 = this.mResetTouchStateRunnable;
                if (handler.hasCallbacks(pipResizeGestureHandler$$ExternalSyntheticLambda1)) {
                    handlerExecutor.removeCallbacks(pipResizeGestureHandler$$ExternalSyntheticLambda1);
                }
                handlerExecutor.executeDelayed(pipResizeGestureHandler$$ExternalSyntheticLambda1, 500L);
            }
        } else {
            pipTaskOrganizer.scheduleFinishResizePip(this.mLastResizeBounds, 7, this.mUpdateResizeBoundsCallback);
        }
        this.mLastResizeBounds.width();
        int i6 = this.mMinSize.x;
        this.mPipDismissTargetHandler.getClass();
        this.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_RESIZE);
    }

    public Rect getLastResizeBounds() {
        return this.mLastResizeBounds;
    }

    public final boolean isWithinDragResizeRegion(int i, int i2) {
        if (!this.mEnableDragCornerResize) {
            return false;
        }
        Rect bounds = this.mPipBoundsState.getBounds();
        Rect rect = this.mDragCornerSize;
        int i3 = this.mDelta;
        rect.set(0, 0, i3, i3);
        this.mTmpTopLeftCorner.set(this.mDragCornerSize);
        this.mTmpTopRightCorner.set(this.mDragCornerSize);
        this.mTmpBottomLeftCorner.set(this.mDragCornerSize);
        this.mTmpBottomRightCorner.set(this.mDragCornerSize);
        Rect rect2 = this.mTmpTopLeftCorner;
        int i4 = bounds.left;
        int i5 = this.mDelta;
        rect2.offset(i4 - (i5 / 2), bounds.top - (i5 / 2));
        Rect rect3 = this.mTmpTopRightCorner;
        int i6 = bounds.right;
        int i7 = this.mDelta;
        rect3.offset(i6 - (i7 / 2), bounds.top - (i7 / 2));
        Rect rect4 = this.mTmpBottomLeftCorner;
        int i8 = bounds.left;
        int i9 = this.mDelta;
        rect4.offset(i8 - (i9 / 2), bounds.bottom - (i9 / 2));
        Rect rect5 = this.mTmpBottomRightCorner;
        int i10 = bounds.right;
        int i11 = this.mDelta;
        rect5.offset(i10 - (i11 / 2), bounds.bottom - (i11 / 2));
        this.mTmpRegion.setEmpty();
        Region region = this.mTmpRegion;
        Rect rect6 = this.mTmpTopLeftCorner;
        Region.Op op = Region.Op.UNION;
        region.op(rect6, op);
        this.mTmpRegion.op(this.mTmpTopRightCorner, op);
        this.mTmpRegion.op(this.mTmpBottomLeftCorner, op);
        this.mTmpRegion.op(this.mTmpBottomRightCorner, op);
        return this.mTmpRegion.contains(i, i2);
    }

    public void onInputEvent(InputEvent inputEvent) throws Resources.NotFoundException {
        if (this.mEnableDragCornerResize || this.mEnablePinchResize) {
            if (!this.mPipTouchState.mAllowInputEvents) {
                Log.d("PipResizeGestureHandler", "pip input event not allowed");
                return;
            }
            PipBoundsState pipBoundsState = this.mPipBoundsState;
            if (!pipBoundsState.isStashed() && (inputEvent instanceof MotionEvent)) {
                MotionEvent motionEvent = (MotionEvent) inputEvent;
                int actionMasked = motionEvent.getActionMasked();
                Rect bounds = pipBoundsState.getBounds();
                PhonePipMenuController phonePipMenuController = this.mPhonePipMenuController;
                if ((actionMasked == 1 || actionMasked == 3) && !bounds.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY()) && phonePipMenuController.isMenuVisible()) {
                    phonePipMenuController.hideMenu();
                }
                if (this.mEnablePinchResize && this.mOngoingPinchToResize) {
                    onPinchResize(motionEvent);
                    return;
                }
                if (this.mEnableDragCornerResize) {
                    int actionMasked2 = motionEvent.getActionMasked();
                    float x = motionEvent.getX();
                    float y = motionEvent.getY() - this.mOhmOffset;
                    if (actionMasked2 != 0) {
                        if (this.mAllowGesture) {
                            if (actionMasked2 != 1) {
                                if (actionMasked2 == 2) {
                                    if (!this.mThresholdCrossed) {
                                        PointF pointF = this.mDownPoint;
                                        if (Math.hypot(x - pointF.x, y - pointF.y) > this.mTouchSlop) {
                                            this.mThresholdCrossed = true;
                                            this.mDownPoint.set(x, y);
                                            this.mInputMonitor.pilferPointers();
                                        }
                                    }
                                    if (this.mThresholdCrossed) {
                                        if (phonePipMenuController.isMenuVisible()) {
                                            phonePipMenuController.hideMenu(0);
                                        }
                                        Rect bounds2 = pipBoundsState.getBounds();
                                        Rect rect = this.mLastResizeBounds;
                                        PointF pointF2 = this.mDownPoint;
                                        float f = pointF2.x;
                                        float f2 = pointF2.y;
                                        int i = this.mCtrlType;
                                        Point point = this.mMinSize;
                                        rect.set(TaskResizingAlgorithm.resizeDrag(x, y, f, f2, bounds2, i, point.x, point.y, this.mMaxSize, true, this.mDownBounds.width() > this.mDownBounds.height()));
                                        this.mPipBoundsAlgorithm.transformBoundsToAspectRatio(pipBoundsState.mAspectRatio, this.mLastResizeBounds, false, true);
                                        this.mPipTaskOrganizer.scheduleUserResizePip(this.mDownBounds, this.mLastResizeBounds, 0.0f, null);
                                        pipBoundsState.setHasUserResizedPip();
                                        return;
                                    }
                                    return;
                                }
                                if (actionMasked2 != 3) {
                                    if (actionMasked2 != 5) {
                                        return;
                                    }
                                    if (!this.mLastResizeBounds.equals(pipBoundsState.getBounds())) {
                                        finishResize();
                                    }
                                    resetState();
                                    return;
                                }
                            }
                            finishResize();
                            return;
                        }
                        return;
                    }
                    this.mLastResizeBounds.setEmpty();
                    boolean z = this.mIsSysUiStateValid && isWithinDragResizeRegion((int) x, (int) y);
                    this.mAllowGesture = z;
                    if (z) {
                        int i2 = (int) x;
                        int i3 = (int) y;
                        Rect bounds3 = pipBoundsState.getBounds();
                        Rect rect2 = (Rect) this.mMovementBoundsSupplier.apply(bounds3);
                        this.mDisplayBounds.set(rect2.left, rect2.top, bounds3.width() + rect2.right, bounds3.height() + rect2.bottom);
                        if (this.mTmpTopLeftCorner.contains(i2, i3)) {
                            int i4 = bounds3.top;
                            Rect rect3 = this.mDisplayBounds;
                            if (i4 != rect3.top && bounds3.left != rect3.left) {
                                this.mCtrlType = 5 | this.mCtrlType;
                            }
                        }
                        if (this.mTmpTopRightCorner.contains(i2, i3)) {
                            int i5 = bounds3.top;
                            Rect rect4 = this.mDisplayBounds;
                            if (i5 != rect4.top && bounds3.right != rect4.right) {
                                this.mCtrlType |= 6;
                            }
                        }
                        if (this.mTmpBottomRightCorner.contains(i2, i3)) {
                            int i6 = bounds3.bottom;
                            Rect rect5 = this.mDisplayBounds;
                            if (i6 != rect5.bottom && bounds3.right != rect5.right) {
                                this.mCtrlType |= 10;
                            }
                        }
                        if (this.mTmpBottomLeftCorner.contains(i2, i3)) {
                            int i7 = bounds3.bottom;
                            Rect rect6 = this.mDisplayBounds;
                            if (i7 != rect6.bottom && bounds3.left != rect6.left) {
                                this.mCtrlType |= 9;
                            }
                        }
                        this.mDownPoint.set(x, y);
                        this.mDownBounds.set(pipBoundsState.getBounds());
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0113  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onPinchResize(MotionEvent motionEvent) throws Resources.NotFoundException {
        int i;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1 || actionMasked == 3) {
            this.mFirstIndex = -1;
            this.mSecondIndex = -1;
            this.mAllowGesture = false;
            finishResize();
            PipPerfHintController.PipHighPerfSession pipHighPerfSession = this.mPipHighPerfSession;
            if (pipHighPerfSession != null) {
                pipHighPerfSession.close();
                this.mPipHighPerfSession = null;
            }
        }
        if (motionEvent.getPointerCount() != 2) {
            return;
        }
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        Rect bounds = pipBoundsState.getBounds();
        if (actionMasked == 5 && this.mFirstIndex == -1 && this.mSecondIndex == -1 && bounds.contains((int) motionEvent.getRawX(0), (int) motionEvent.getRawY(0)) && bounds.contains((int) motionEvent.getRawX(1), (int) motionEvent.getRawY(1))) {
            this.mAllowGesture = true;
            this.mFirstIndex = 0;
            this.mSecondIndex = 1;
            this.mDownPoint.set(motionEvent.getRawX(0), motionEvent.getRawY(this.mFirstIndex));
            this.mDownSecondPoint.set(motionEvent.getRawX(this.mSecondIndex), motionEvent.getRawY(this.mSecondIndex));
            this.mDownBounds.set(bounds);
            this.mLastPoint.set(this.mDownPoint);
            PointF pointF = this.mLastSecondPoint;
            pointF.set(pointF);
            this.mLastResizeBounds.set(this.mDownBounds);
            PipPerfHintController pipPerfHintController = this.mPipPerfHintController;
            if (pipPerfHintController != null) {
                this.mPipHighPerfSession = pipPerfHintController.startSession(new PipResizeGestureHandler$$ExternalSyntheticLambda0(this, 1), "onPinchResize");
            }
        }
        if (actionMasked != 2 || (i = this.mFirstIndex) == -1 || this.mSecondIndex == -1) {
            return;
        }
        float rawX = motionEvent.getRawX(i);
        float rawY = motionEvent.getRawY(this.mFirstIndex);
        float rawX2 = motionEvent.getRawX(this.mSecondIndex);
        float rawY2 = motionEvent.getRawY(this.mSecondIndex);
        this.mLastPoint.set(rawX, rawY);
        this.mLastSecondPoint.set(rawX2, rawY2);
        if (!this.mThresholdCrossed) {
            PointF pointF2 = this.mDownSecondPoint;
            PointF pointF3 = this.mLastSecondPoint;
            if (((float) Math.hypot(pointF3.x - pointF2.x, pointF3.y - pointF2.y)) <= this.mTouchSlop) {
                PointF pointF4 = this.mDownPoint;
                PointF pointF5 = this.mLastPoint;
                if (((float) Math.hypot(pointF5.x - pointF4.x, pointF5.y - pointF4.y)) > this.mTouchSlop) {
                    pilferPointers();
                    this.mThresholdCrossed = true;
                    this.mDownPoint.set(this.mLastPoint);
                    this.mDownSecondPoint.set(this.mLastSecondPoint);
                    PhonePipMenuController phonePipMenuController = this.mPhonePipMenuController;
                    if (phonePipMenuController.isMenuVisible()) {
                        phonePipMenuController.hideMenu();
                    }
                }
            }
        }
        if (this.mThresholdCrossed) {
            this.mAngle = this.mPinchResizingAlgorithm.calculateBoundsAndAngle(this.mDownPoint, this.mDownSecondPoint, this.mLastPoint, this.mLastSecondPoint, this.mMinSize, this.mMaxSize, this.mDownBounds, this.mLastResizeBounds);
            float f = pipBoundsState.mAspectRatio;
            if (f <= 1.0f) {
                int iRound = Math.round(this.mLastResizeBounds.height() * f);
                Rect rect = this.mLastResizeBounds;
                int i2 = rect.left;
                rect.set(i2, rect.top, iRound + i2, rect.bottom);
            } else {
                int iRound2 = Math.round(this.mLastResizeBounds.width() / f);
                Rect rect2 = this.mLastResizeBounds;
                int i3 = rect2.left;
                int i4 = rect2.top;
                rect2.set(i3, i4, rect2.right, iRound2 + i4);
            }
            this.mPipTaskOrganizer.scheduleUserResizePip(this.mDownBounds, this.mLastResizeBounds, this.mAngle, null);
            pipBoundsState.setHasUserResizedPip();
        }
    }

    public void pilferPointers() {
        this.mInputMonitor.pilferPointers();
    }

    public final void reloadResources() {
        this.mDelta = this.mContext.getResources().getDimensionPixelSize(R.dimen.pip_resize_edge_size);
        this.mEnableDragCornerResize = DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PIP.isTrue();
        this.mTouchSlop = ViewConfiguration.get(this.mContext).getScaledTouchSlop();
    }

    public final void resetState() {
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(3, "PipResizeGestureHandler", new StringBuilder("reset state callers="));
        this.mCtrlType = 0;
        this.mAngle = 0.0f;
        this.mOngoingPinchToResize = false;
        this.mAllowGesture = false;
        this.mThresholdCrossed = false;
    }

    public final void setUserResizeBounds(Rect rect) {
        this.mUserResizeBounds.set(rect);
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
            this.mInputMonitor = ((InputManager) this.mContext.getSystemService(InputManager.class)).monitorGestureInput("pip-resize", this.mDisplayId);
            try {
                this.mMainExecutor.executeBlocking(new PipResizeGestureHandler$$ExternalSyntheticLambda1(this, 2));
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to create input event receiver", e);
            }
        }
    }

    public void updateMaxSize(int i, int i2) {
        this.mMaxSize.set(i, i2);
    }

    public void updateMinSize(int i, int i2) {
        this.mMinSize.set(i, i2);
    }
}
