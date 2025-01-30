package com.android.wm.shell.pip.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.input.InputManager;
import android.os.Looper;
import android.util.Log;
import android.view.BatchedInputEventReceiver;
import android.view.Choreographer;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputMonitor;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.android.wm.shell.common.DragResizePointer;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.pip.PipBoundsAlgorithm;
import com.android.wm.shell.pip.PipBoundsState;
import com.android.wm.shell.pip.PipSnapAlgorithm;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.PipUiEventLogger;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.systemui.R;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.rune.CoreRune;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PipResizeGestureHandler {
    public boolean mAllowGesture;
    public final Context mContext;
    public int mCtrlType;
    public int mDelta;
    public final int mDisplayId;
    public boolean mEnableDragCornerResize;
    public boolean mEnablePinchResize;
    public PipResizeInputEventReceiver mInputEventReceiver;
    public InputManager mInputManager;
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
    public int mPointerIconType = 1000;
    public final PipPinchResizingAlgorithm mPinchResizingAlgorithm = new PipPinchResizingAlgorithm();
    public final PipResizeGestureHandler$$ExternalSyntheticLambda0 mUpdateResizeBoundsCallback = new PipResizeGestureHandler$$ExternalSyntheticLambda0(this);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PipResizeInputEventReceiver extends BatchedInputEventReceiver {
        public PipResizeInputEventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper, Choreographer.getInstance());
        }

        public final void onInputEvent(InputEvent inputEvent) {
            PipResizeGestureHandler.this.onInputEvent(inputEvent);
            finishInputEvent(inputEvent, true);
        }
    }

    public PipResizeGestureHandler(Context context, PipBoundsAlgorithm pipBoundsAlgorithm, PipBoundsState pipBoundsState, PipMotionHelper pipMotionHelper, PipTouchState pipTouchState, PipTaskOrganizer pipTaskOrganizer, PipDismissTargetHandler pipDismissTargetHandler, Function<Rect, Rect> function, Runnable runnable, PipUiEventLogger pipUiEventLogger, PhonePipMenuController phonePipMenuController, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mDisplayId = context.getDisplayId();
        this.mMainExecutor = shellExecutor;
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

    public final void finishResize() {
        Rect rect = this.mLastResizeBounds;
        if (rect.isEmpty()) {
            resetState();
            return;
        }
        Rect rect2 = new Rect(rect);
        float width = rect.width();
        Point point = this.mMaxSize;
        if (width >= point.x * 0.9f || rect.height() >= point.y * 0.9f) {
            int i = point.x;
            int i2 = point.y;
            int centerX = rect.centerX() - (i / 2);
            int centerY = rect.centerY() - (i2 / 2);
            rect.set(centerX, centerY, i + centerX, i2 + centerY);
        }
        PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
        Rect movementBounds = pipBoundsAlgorithm.getMovementBounds(rect, true);
        snapToMovementBoundsEdge(rect, movementBounds);
        PipSnapAlgorithm pipSnapAlgorithm = pipBoundsAlgorithm.mSnapAlgorithm;
        float snapFraction = pipSnapAlgorithm.getSnapFraction(0, rect, movementBounds);
        Rect movementBounds2 = pipBoundsAlgorithm.getMovementBounds(rect, true);
        pipSnapAlgorithm.getClass();
        PipSnapAlgorithm.applySnapFraction(snapFraction, rect, movementBounds2);
        this.mPipTouchState.mAllowInputEvents = false;
        Rect rect3 = this.mLastResizeBounds;
        float f = this.mAngle;
        PipResizeGestureHandler$$ExternalSyntheticLambda0 pipResizeGestureHandler$$ExternalSyntheticLambda0 = this.mUpdateResizeBoundsCallback;
        PipResizeGestureHandler$$ExternalSyntheticLambda1 pipResizeGestureHandler$$ExternalSyntheticLambda1 = new PipResizeGestureHandler$$ExternalSyntheticLambda1(this, 0);
        PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
        pipTaskOrganizer.mPipFinishResizeWCTRunnable = pipResizeGestureHandler$$ExternalSyntheticLambda1;
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1338996709, 0, "mPipFinishResizeWCTRunnable is set to be called once window updates", null);
        }
        if (!pipTaskOrganizer.mWaitForFixedRotation) {
            pipTaskOrganizer.scheduleAnimateResizePip(rect2, rect3, f, null, 6, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, pipResizeGestureHandler$$ExternalSyntheticLambda0);
        } else if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -260134071, 0, "%s: skip scheduleAnimateResizePip, entering pip deferred", "PipTaskOrganizer");
        }
        rect.width();
        int i3 = this.mMinSize.x;
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
        int i3 = this.mDelta;
        Rect rect = this.mDragCornerSize;
        rect.set(0, 0, i3, i3);
        Rect rect2 = this.mTmpTopLeftCorner;
        rect2.set(rect);
        Rect rect3 = this.mTmpTopRightCorner;
        rect3.set(rect);
        Rect rect4 = this.mTmpBottomLeftCorner;
        rect4.set(rect);
        Rect rect5 = this.mTmpBottomRightCorner;
        rect5.set(rect);
        int centerX = bounds.centerX();
        Rect rect6 = this.mDisplayBounds;
        if (centerX - rect6.left < rect6.right - centerX) {
            int i4 = this.mDelta / 2;
            rect2.set(0, 0, i4, i4);
            int i5 = this.mDelta / 2;
            rect4.set(0, 0, i5, i5);
            rect2.offset(bounds.left, bounds.top);
            rect4.offset(bounds.left, bounds.bottom - (this.mDelta / 2));
            int i6 = bounds.right;
            int i7 = this.mDelta / 2;
            rect3.offset(i6 - i7, bounds.top - i7);
            int i8 = bounds.right;
            int i9 = this.mDelta / 2;
            rect5.offset(i8 - i9, bounds.bottom - i9);
        } else {
            int i10 = this.mDelta / 2;
            rect3.set(0, 0, i10, i10);
            int i11 = this.mDelta / 2;
            rect5.set(0, 0, i11, i11);
            rect3.offset(bounds.right - (this.mDelta / 2), bounds.top);
            int i12 = bounds.right;
            int i13 = this.mDelta / 2;
            rect5.offset(i12 - i13, bounds.bottom - i13);
            int i14 = bounds.left;
            int i15 = this.mDelta / 2;
            rect2.offset(i14 - i15, bounds.top - i15);
            int i16 = bounds.left;
            int i17 = this.mDelta / 2;
            rect4.offset(i16 - i17, bounds.bottom - i17);
        }
        Region region = this.mTmpRegion;
        region.setEmpty();
        region.op(rect2, Region.Op.UNION);
        region.op(rect3, Region.Op.UNION);
        region.op(rect4, Region.Op.UNION);
        region.op(rect5, Region.Op.UNION);
        return region.contains(i, i2);
    }

    public void onInputEvent(InputEvent inputEvent) {
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
                    PointF pointF = this.mDownPoint;
                    Rect rect = this.mDownBounds;
                    Rect rect2 = this.mTmpBottomLeftCorner;
                    Rect rect3 = this.mTmpBottomRightCorner;
                    Rect rect4 = this.mTmpTopRightCorner;
                    Rect rect5 = this.mTmpTopLeftCorner;
                    Rect rect6 = this.mLastResizeBounds;
                    boolean z = false;
                    if (actionMasked2 == 0) {
                        rect6.setEmpty();
                        if (this.mIsSysUiStateValid && isWithinDragResizeRegion((int) x, (int) y)) {
                            z = true;
                        }
                        this.mAllowGesture = z;
                        if (z) {
                            int i = (int) x;
                            int i2 = (int) y;
                            Rect bounds2 = pipBoundsState.getBounds();
                            Rect rect7 = (Rect) this.mMovementBoundsSupplier.apply(bounds2);
                            int i3 = rect7.left;
                            int i4 = rect7.top;
                            int width = bounds2.width() + rect7.right;
                            int height = bounds2.height() + rect7.bottom;
                            Rect rect8 = this.mDisplayBounds;
                            rect8.set(i3, i4, width, height);
                            if (rect5.contains(i, i2) && bounds2.top != rect8.top && bounds2.left != rect8.left) {
                                this.mCtrlType = this.mCtrlType | 1 | 4;
                            }
                            if (rect4.contains(i, i2) && bounds2.top != rect8.top && bounds2.right != rect8.right) {
                                this.mCtrlType = this.mCtrlType | 2 | 4;
                            }
                            if (rect3.contains(i, i2) && bounds2.bottom != rect8.bottom && bounds2.right != rect8.right) {
                                this.mCtrlType = this.mCtrlType | 2 | 8;
                            }
                            if (rect2.contains(i, i2) && bounds2.bottom != rect8.bottom && bounds2.left != rect8.left) {
                                this.mCtrlType = this.mCtrlType | 1 | 8;
                            }
                            pointF.set(x, y);
                            rect.set(pipBoundsState.getBounds());
                            return;
                        }
                        return;
                    }
                    boolean z2 = this.mAllowGesture;
                    PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
                    if (!z2) {
                        boolean z3 = false;
                        if (motionEvent.isHoverEvent()) {
                            int i5 = (int) x;
                            int i6 = (int) y;
                            int i7 = 1000;
                            if (isWithinDragResizeRegion(i5, i6) && this.mPointerIconType != 1000) {
                                this.mPointerIconType = 1000;
                            }
                            boolean isFromSource = motionEvent.isFromSource(16386);
                            if (!(!isWithinDragResizeRegion(i5, i6))) {
                                if (rect5.contains(i5, i6) || rect3.contains(i5, i6)) {
                                    i7 = 1017;
                                } else if (rect4.contains(i5, i6) || rect2.contains(i5, i6)) {
                                    i7 = EnterpriseContainerCallback.CONTAINER_CANCELLED;
                                }
                                if (CoreRune.MT_NEW_DEX && pipTaskOrganizer.mTaskInfo.configuration.isNewDexMode()) {
                                    z3 = true;
                                }
                                if (isFromSource) {
                                    i7 = DragResizePointer.convertStylusIconType(i7);
                                } else if (z3) {
                                    i7 = DragResizePointer.convertDexPointerIconType(i7);
                                }
                            }
                            if (this.mPointerIconType != i7) {
                                this.mPointerIconType = i7;
                                this.mInputManager.setPointerIconType(i7);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    if (actionMasked2 != 1) {
                        if (actionMasked2 == 2) {
                            if (!this.mThresholdCrossed && Math.hypot(x - pointF.x, y - pointF.y) > this.mTouchSlop) {
                                this.mThresholdCrossed = true;
                                pointF.set(x, y);
                                this.mInputMonitor.pilferPointers();
                            }
                            if (this.mThresholdCrossed) {
                                if (phonePipMenuController.isMenuVisible()) {
                                    phonePipMenuController.hideMenu(0);
                                }
                                Rect bounds3 = pipBoundsState.getBounds();
                                int i8 = this.mCtrlType;
                                if (i8 != 0) {
                                    Rect rect9 = this.mLastResizeBounds;
                                    pointF.set((i8 & 1) != 0 ? bounds3.left : bounds3.right, (i8 & 4) != 0 ? bounds3.top : bounds3.bottom);
                                    PointF pointF2 = this.mDownSecondPoint;
                                    int i9 = this.mCtrlType;
                                    pointF2.set((i9 & 1) != 0 ? bounds3.right : bounds3.left, (i9 & 4) != 0 ? bounds3.bottom : bounds3.top);
                                    PointF pointF3 = this.mLastPoint;
                                    pointF3.set(x, y);
                                    PointF pointF4 = this.mLastSecondPoint;
                                    pointF4.set(pointF2);
                                    this.mPinchResizingAlgorithm.calculateBoundsAndAngle(pointF, pointF2, pointF3, pointF4, this.mMinSize, this.mMaxSize, this.mDownBounds, rect9);
                                    this.mPipBoundsAlgorithm.transformBoundsToAspectRatio(rect6, pipBoundsState.mAspectRatio, false, true);
                                    pipTaskOrganizer.scheduleUserResizePip(rect, rect6, 0.0f, null);
                                    pipBoundsState.mHasUserResizedPip = true;
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        if (actionMasked2 != 3) {
                            if (actionMasked2 != 5) {
                                return;
                            }
                            resetState();
                            return;
                        }
                    }
                    finishResize();
                }
            }
        }
    }

    public void onPinchResize(MotionEvent motionEvent) {
        int i;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1 || actionMasked == 3) {
            this.mFirstIndex = -1;
            this.mSecondIndex = -1;
            this.mAllowGesture = false;
            finishResize();
        }
        if (motionEvent.getPointerCount() != 2) {
            return;
        }
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        Rect bounds = pipBoundsState.getBounds();
        PointF pointF = this.mDownSecondPoint;
        PointF pointF2 = this.mDownPoint;
        PointF pointF3 = this.mLastSecondPoint;
        PointF pointF4 = this.mLastPoint;
        Rect rect = this.mDownBounds;
        Rect rect2 = this.mLastResizeBounds;
        if (actionMasked == 5 && this.mFirstIndex == -1 && this.mSecondIndex == -1 && bounds.contains((int) motionEvent.getRawX(0), (int) motionEvent.getRawY(0)) && bounds.contains((int) motionEvent.getRawX(1), (int) motionEvent.getRawY(1))) {
            this.mAllowGesture = true;
            this.mFirstIndex = 0;
            this.mSecondIndex = 1;
            pointF2.set(motionEvent.getRawX(0), motionEvent.getRawY(this.mFirstIndex));
            pointF.set(motionEvent.getRawX(this.mSecondIndex), motionEvent.getRawY(this.mSecondIndex));
            rect.set(bounds);
            pointF4.set(pointF2);
            pointF3.set(pointF3);
            rect2.set(rect);
        }
        if (actionMasked != 2 || (i = this.mFirstIndex) == -1 || this.mSecondIndex == -1) {
            return;
        }
        float rawX = motionEvent.getRawX(i);
        float rawY = motionEvent.getRawY(this.mFirstIndex);
        float rawX2 = motionEvent.getRawX(this.mSecondIndex);
        float rawY2 = motionEvent.getRawY(this.mSecondIndex);
        pointF4.set(rawX, rawY);
        pointF3.set(rawX2, rawY2);
        if (!this.mThresholdCrossed && (((float) Math.hypot(pointF3.x - pointF.x, pointF3.y - pointF.y)) > this.mTouchSlop || ((float) Math.hypot(pointF4.x - pointF2.x, pointF4.y - pointF2.y)) > this.mTouchSlop)) {
            pilferPointers();
            this.mThresholdCrossed = true;
            pointF2.set(pointF4);
            pointF.set(pointF3);
            PhonePipMenuController phonePipMenuController = this.mPhonePipMenuController;
            if (phonePipMenuController.isMenuVisible()) {
                phonePipMenuController.hideMenu();
            }
        }
        if (this.mThresholdCrossed) {
            this.mAngle = this.mPinchResizingAlgorithm.calculateBoundsAndAngle(pointF2, pointF, pointF4, pointF3, this.mMinSize, this.mMaxSize, this.mDownBounds, this.mLastResizeBounds);
            float f = pipBoundsState.mAspectRatio;
            if (f <= 1.0f) {
                int round = Math.round(rect2.height() * f);
                int i2 = rect2.left;
                rect2.set(i2, rect2.top, round + i2, rect2.bottom);
            } else {
                int round2 = Math.round(rect2.width() / f);
                int i3 = rect2.left;
                int i4 = rect2.top;
                rect2.set(i3, i4, rect2.right, round2 + i4);
            }
            this.mPipTaskOrganizer.scheduleUserResizePip(rect, rect2, this.mAngle, null);
            pipBoundsState.mHasUserResizedPip = true;
        }
    }

    public void pilferPointers() {
        this.mInputMonitor.pilferPointers();
    }

    public final void reloadResources() {
        Resources resources = this.mContext.getResources();
        this.mDelta = resources.getDimensionPixelSize(R.dimen.pip_resize_edge_size);
        this.mEnableDragCornerResize = resources.getBoolean(R.bool.config_pipEnableDragCornerResize);
        this.mTouchSlop = ViewConfiguration.get(r0).getScaledTouchSlop();
    }

    public final void resetState() {
        this.mCtrlType = 0;
        this.mAngle = 0.0f;
        this.mOngoingPinchToResize = false;
        this.mAllowGesture = false;
        this.mThresholdCrossed = false;
    }

    public final void setUserResizeBounds(Rect rect) {
        this.mUserResizeBounds.set(rect);
    }

    public final void snapToMovementBoundsEdge(Rect rect, Rect rect2) {
        int i = rect.left;
        rect.offsetTo(Math.abs(i - rect2.left) < Math.abs(rect2.right - i) ? rect2.left : rect2.right, this.mLastResizeBounds.top);
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
                this.mMainExecutor.executeBlocking(new PipResizeGestureHandler$$ExternalSyntheticLambda1(this, 1));
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
