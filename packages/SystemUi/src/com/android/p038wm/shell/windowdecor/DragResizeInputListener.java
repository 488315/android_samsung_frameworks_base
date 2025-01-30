package com.android.p038wm.shell.windowdecor;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.input.InputManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Choreographer;
import android.view.IWindowSession;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.ViewConfiguration;
import android.view.WindowManagerGlobal;
import com.android.internal.view.BaseIWindow;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.common.DragResizePointer;
import com.android.p038wm.shell.windowdecor.DragDetector;
import com.android.p038wm.shell.windowdecor.DragResizeInputListener;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DragResizeInputListener implements AutoCloseable {
    public final DragPositioningCallback mCallback;
    public final Choreographer mChoreographer;
    public int mCornerSize;
    public final SurfaceControl mDecorationSurface;
    public final int mDisplayId;
    public final DragDetector mDragDetector;
    public int mDragPointerId;
    public final BaseIWindow mFakeWindow;
    public final Handler mHandler;
    public int mInnerResizeHandleThickness;
    public final InputChannel mInputChannel;
    public final TaskResizeInputEventReceiver mInputEventReceiver;
    public final InputManager mInputManager;
    public boolean mIsDexEnabled;
    public boolean mIsPointerInput;
    public boolean mIsStylusInput;
    public final Region mLastTouchRegion;
    public final Region mLeftBottomCornerRegion;
    public final Region mLeftTopCornerRegion;
    public MultitaskingWindowDecoration mMultitaskingWindowDecoration;
    public final Rect mPointerLeftBottomCornerBounds;
    public final Rect mPointerLeftTopCornerBounds;
    public int mPointerResizeHandleThickness;
    public final Rect mPointerRightBottomCornerBounds;
    public final Rect mPointerRightTopCornerBounds;
    public final Region mPointerTouchableRegion;
    public int mResizeHandleThickness;
    public final Region mRightBottomCornerRegion;
    public final Region mRightTopCornerRegion;
    public final RunnableC41661 mSetDefaultPointerRunnable;
    public int mTaskHeight;
    public int mTaskWidth;
    public final Rect mTmpBounds;
    public final Rect mTmpCornerRect;
    public final Rect mTmpInnerRect;
    public boolean mTouchableState;
    public final IWindowSession mWindowSession;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TaskResizeInputEventReceiver extends InputEventReceiver implements DragDetector.MotionEventHandler {
        public final Choreographer mChoreographer;
        public final RunnableC4165x4e55fc75 mConsumeBatchEventRunnable;
        public boolean mConsumeBatchEventScheduled;
        public boolean mShouldHandleEvents;

        public /* synthetic */ TaskResizeInputEventReceiver(DragResizeInputListener dragResizeInputListener, InputChannel inputChannel, Handler handler, Choreographer choreographer, int i) {
            this(inputChannel, handler, choreographer);
        }

        public final int calculateCornersCtrlType(float f, float f2) {
            int i = (int) f;
            int i2 = (int) f2;
            DragResizeInputListener dragResizeInputListener = DragResizeInputListener.this;
            if (dragResizeInputListener.mIsPointerInput) {
                if (dragResizeInputListener.mPointerLeftTopCornerBounds.contains(i, i2)) {
                    return 5;
                }
                if (DragResizeInputListener.this.mPointerLeftBottomCornerBounds.contains(i, i2)) {
                    return 9;
                }
                if (DragResizeInputListener.this.mPointerRightTopCornerBounds.contains(i, i2)) {
                    return 6;
                }
                return DragResizeInputListener.this.mPointerRightBottomCornerBounds.contains(i, i2) ? 10 : 0;
            }
            if (dragResizeInputListener.mLeftTopCornerRegion.contains(i, i2)) {
                return 5;
            }
            if (DragResizeInputListener.this.mLeftBottomCornerRegion.contains(i, i2)) {
                return 9;
            }
            if (DragResizeInputListener.this.mRightTopCornerRegion.contains(i, i2)) {
                return 6;
            }
            return DragResizeInputListener.this.mRightBottomCornerRegion.contains(i, i2) ? 10 : 0;
        }

        public final int calculateResizeHandlesCtrlType(float f, float f2) {
            int i;
            DragResizeInputListener dragResizeInputListener = DragResizeInputListener.this;
            if (!dragResizeInputListener.mIsPointerInput) {
                int i2 = dragResizeInputListener.mInnerResizeHandleThickness;
                i = f >= ((float) i2) ? 0 : 1;
                if (f > dragResizeInputListener.mTaskWidth - i2) {
                    i |= 2;
                }
                if (f2 < i2) {
                    i |= 4;
                }
                return f2 > ((float) (dragResizeInputListener.mTaskHeight - i2)) ? i | 8 : i;
            }
            if (!dragResizeInputListener.mPointerTouchableRegion.contains((int) f, (int) f2)) {
                return 0;
            }
            i = (f >= 0.0f || f < ((float) (-DragResizeInputListener.this.mPointerResizeHandleThickness))) ? 0 : 1;
            DragResizeInputListener dragResizeInputListener2 = DragResizeInputListener.this;
            if (f > dragResizeInputListener2.mTaskWidth && f <= r1 + dragResizeInputListener2.mPointerResizeHandleThickness && f2 > 0.0f && f2 <= dragResizeInputListener2.mTaskHeight) {
                i |= 2;
            }
            if (f2 < 0.0f && f2 >= (-dragResizeInputListener2.mPointerResizeHandleThickness)) {
                i |= 4;
            }
            int i3 = dragResizeInputListener2.mTaskHeight;
            return (f2 <= ((float) i3) || f2 > ((float) (i3 + dragResizeInputListener2.mPointerResizeHandleThickness))) ? i : i | 8;
        }

        public final void dispose() {
            super.dispose();
            DragResizeInputListener.this.mCallback.onDragPositioningEnd(-1.0f, -1.0f);
        }

        @Override // com.android.wm.shell.windowdecor.DragDetector.MotionEventHandler
        public final boolean handleMotionEvent(MotionEvent motionEvent) {
            boolean z;
            motionEvent.getSource();
            DragResizeInputListener.this.mIsStylusInput = motionEvent.isFromSource(16386);
            DragResizeInputListener dragResizeInputListener = DragResizeInputListener.this;
            dragResizeInputListener.mIsPointerInput = dragResizeInputListener.mIsStylusInput || motionEvent.isFromSource(8194);
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0) {
                if (actionMasked != 1) {
                    if (actionMasked != 2) {
                        if (actionMasked != 3) {
                            if (actionMasked == 7 || actionMasked == 9) {
                                updateCursorType(motionEvent.getXCursorPosition(), motionEvent.getYCursorPosition());
                                return true;
                            }
                            if (actionMasked == 10) {
                                DragResizeInputListener dragResizeInputListener2 = DragResizeInputListener.this;
                                dragResizeInputListener2.mHandler.postDelayed(dragResizeInputListener2.mSetDefaultPointerRunnable, 100L);
                                return true;
                            }
                        }
                    } else if (this.mShouldHandleEvents) {
                        int findPointerIndex = motionEvent.findPointerIndex(DragResizeInputListener.this.mDragPointerId);
                        if (findPointerIndex != -1) {
                            DragResizeInputListener.this.mCallback.onDragPositioningMove(motionEvent.getRawX(findPointerIndex), motionEvent.getRawY(findPointerIndex));
                            return true;
                        }
                        Log.w("DragResizeInputListener", "Invalid dragPointerIndex=" + findPointerIndex + " in handleMotionEvent");
                    }
                }
                if (this.mShouldHandleEvents) {
                    int findPointerIndex2 = motionEvent.findPointerIndex(DragResizeInputListener.this.mDragPointerId);
                    if (findPointerIndex2 == -1) {
                        Log.w("DragResizeInputListener", "Invalid dragPointerIndex=" + findPointerIndex2 + " in handleMotionEvent");
                        DragResizeInputListener.this.mCallback.onDragPositioningEnd(-1.0f, -1.0f);
                    } else {
                        DragResizeInputListener.this.mCallback.onDragPositioningEnd(motionEvent.getRawX(findPointerIndex2), motionEvent.getRawY(findPointerIndex2));
                    }
                }
                this.mShouldHandleEvents = false;
                DragResizeInputListener.this.mDragPointerId = -1;
                return true;
            }
            float x = motionEvent.getX(0);
            float y = motionEvent.getY(0);
            if ((motionEvent.getFlags() & QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE) == 0 || (motionEvent.getButtonState() & 1) != 0) {
                if (!(calculateCornersCtrlType(x, y) != 0)) {
                    if (!(calculateResizeHandlesCtrlType(x, y) != 0)) {
                        z = false;
                        this.mShouldHandleEvents = z;
                    }
                }
                z = true;
                this.mShouldHandleEvents = z;
            } else {
                this.mShouldHandleEvents = false;
            }
            if (this.mShouldHandleEvents) {
                DragResizeInputListener.this.mDragPointerId = motionEvent.getPointerId(0);
                DragResizeInputListener.this.mCallback.onDragPositioningStart(motionEvent.getRawX(0), motionEvent.getRawY(0), calculateCornersCtrlType(x, y) | calculateResizeHandlesCtrlType(x, y));
                updateCursorType(x, y);
                return true;
            }
            return false;
        }

        public final void onBatchedInputEventPending(int i) {
            if (this.mConsumeBatchEventScheduled) {
                return;
            }
            this.mChoreographer.postCallback(0, this.mConsumeBatchEventRunnable, null);
            this.mConsumeBatchEventScheduled = true;
        }

        public final void onInputEvent(InputEvent inputEvent) {
            finishInputEvent(inputEvent, !(inputEvent instanceof MotionEvent) ? false : DragResizeInputListener.this.mDragDetector.onMotionEvent((MotionEvent) inputEvent));
        }

        public final void updateCursorType(float f, float f2) {
            int i;
            DragResizeInputListener dragResizeInputListener = DragResizeInputListener.this;
            MultitaskingWindowDecoration multitaskingWindowDecoration = dragResizeInputListener.mMultitaskingWindowDecoration;
            if (multitaskingWindowDecoration == null || !multitaskingWindowDecoration.mAdjustState.mIsAdjusted) {
                dragResizeInputListener.mHandler.removeCallbacks(dragResizeInputListener.mSetDefaultPointerRunnable);
                switch (calculateResizeHandlesCtrlType(f, f2) | calculateCornersCtrlType(f, f2)) {
                    case 1:
                    case 2:
                        i = EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_SUCCESSFUL;
                        break;
                    case 3:
                    case 7:
                    default:
                        i = 1000;
                        break;
                    case 4:
                    case 8:
                        i = EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_FAILED;
                        break;
                    case 5:
                    case 10:
                        i = 1017;
                        break;
                    case 6:
                    case 9:
                        i = EnterpriseContainerCallback.CONTAINER_CANCELLED;
                        break;
                }
                DragResizeInputListener dragResizeInputListener2 = DragResizeInputListener.this;
                if (dragResizeInputListener2.mIsStylusInput) {
                    i = DragResizePointer.convertStylusIconType(i);
                } else if (dragResizeInputListener2.mIsDexEnabled) {
                    i = DragResizePointer.convertDexPointerIconType(i);
                }
                DragResizeInputListener.this.mInputManager.setPointerIconType(i);
            }
        }

        /* JADX WARN: Type inference failed for: r1v2, types: [com.android.wm.shell.windowdecor.DragResizeInputListener$TaskResizeInputEventReceiver$$ExternalSyntheticLambda0] */
        private TaskResizeInputEventReceiver(InputChannel inputChannel, Handler handler, Choreographer choreographer) {
            super(inputChannel, handler.getLooper());
            this.mChoreographer = choreographer;
            this.mConsumeBatchEventRunnable = new Runnable() { // from class: com.android.wm.shell.windowdecor.DragResizeInputListener$TaskResizeInputEventReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DragResizeInputListener.TaskResizeInputEventReceiver taskResizeInputEventReceiver = DragResizeInputListener.TaskResizeInputEventReceiver.this;
                    taskResizeInputEventReceiver.mConsumeBatchEventScheduled = false;
                    if (!taskResizeInputEventReceiver.consumeBatchedInputEvents(taskResizeInputEventReceiver.mChoreographer.getFrameTimeNanos()) || taskResizeInputEventReceiver.mConsumeBatchEventScheduled) {
                        return;
                    }
                    taskResizeInputEventReceiver.mChoreographer.postCallback(0, taskResizeInputEventReceiver.mConsumeBatchEventRunnable, null);
                    taskResizeInputEventReceiver.mConsumeBatchEventScheduled = true;
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r1v13, types: [com.android.wm.shell.windowdecor.DragResizeInputListener$1] */
    public DragResizeInputListener(Context context, Handler handler, Choreographer choreographer, int i, SurfaceControl surfaceControl, DragPositioningCallback dragPositioningCallback, ShellTaskOrganizer shellTaskOrganizer) {
        IWindowSession windowSession = WindowManagerGlobal.getWindowSession();
        this.mWindowSession = windowSession;
        this.mLeftTopCornerRegion = new Region();
        this.mRightTopCornerRegion = new Region();
        this.mLeftBottomCornerRegion = new Region();
        this.mRightBottomCornerRegion = new Region();
        this.mTmpBounds = new Rect();
        this.mTmpCornerRect = new Rect();
        this.mTmpInnerRect = new Rect();
        this.mPointerTouchableRegion = new Region();
        this.mPointerLeftTopCornerBounds = new Rect();
        this.mPointerRightTopCornerBounds = new Rect();
        this.mPointerLeftBottomCornerBounds = new Rect();
        this.mPointerRightBottomCornerBounds = new Rect();
        this.mDragPointerId = -1;
        this.mSetDefaultPointerRunnable = new Runnable() { // from class: com.android.wm.shell.windowdecor.DragResizeInputListener.1
            @Override // java.lang.Runnable
            public final void run() {
                DragResizeInputListener.this.mInputManager.setPointerIconType(1000);
            }
        };
        this.mLastTouchRegion = new Region();
        this.mInputManager = (InputManager) context.getSystemService(InputManager.class);
        this.mHandler = handler;
        this.mChoreographer = choreographer;
        this.mDisplayId = i;
        this.mDecorationSurface = surfaceControl;
        BaseIWindow baseIWindow = new BaseIWindow();
        this.mFakeWindow = baseIWindow;
        baseIWindow.setSession(windowSession);
        Binder binder = new Binder();
        InputChannel inputChannel = new InputChannel();
        this.mInputChannel = inputChannel;
        try {
            windowSession.grantInputChannel(i, surfaceControl, baseIWindow, (IBinder) null, 8, QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT, 0, 2, (IBinder) null, binder, "DragResizeInputListener of " + surfaceControl.toString(), inputChannel);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        TaskResizeInputEventReceiver taskResizeInputEventReceiver = new TaskResizeInputEventReceiver(this, this.mInputChannel, this.mHandler, this.mChoreographer, 0);
        this.mInputEventReceiver = taskResizeInputEventReceiver;
        this.mCallback = dragPositioningCallback;
        DragDetector dragDetector = new DragDetector(taskResizeInputEventReceiver);
        this.mDragDetector = dragDetector;
        dragDetector.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        this.mInputEventReceiver.dispose();
        this.mInputChannel.dispose();
        try {
            this.mWindowSession.remove(this.mFakeWindow);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x01d4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean setGeometry(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, boolean z) {
        int i10;
        boolean z2;
        Region region;
        if (CoreRune.MW_CAPTION_SHELL) {
            i10 = i6 + i;
            if (z != this.mTouchableState) {
                this.mTouchableState = z;
                z2 = true;
                if (this.mTaskWidth != i5 && this.mTaskHeight == i10 && this.mResizeHandleThickness == i7 && this.mCornerSize == i8 && this.mPointerResizeHandleThickness == i3 && this.mInnerResizeHandleThickness == i4) {
                    if (z2) {
                        updateTouchableState(z);
                    }
                    return false;
                }
                this.mTaskWidth = i5;
                this.mTaskHeight = i10;
                this.mResizeHandleThickness = i7;
                this.mCornerSize = i8;
                this.mDragDetector.mTouchSlop = i9;
                this.mInnerResizeHandleThickness = i4;
                this.mPointerResizeHandleThickness = i3;
                this.mTmpBounds.set(0, 0, i5, i10);
                int i11 = -i3;
                this.mPointerLeftTopCornerBounds.set(i11, i11, i3, i3);
                Rect rect = this.mPointerRightTopCornerBounds;
                int i12 = this.mTaskWidth;
                rect.set(i12 - i3, i11, i12 + i3, i3);
                Rect rect2 = this.mPointerLeftBottomCornerBounds;
                int i13 = this.mTaskHeight;
                rect2.set(i11, i13 - i3, i3, i13 + i3);
                Rect rect3 = this.mPointerRightBottomCornerBounds;
                int i14 = this.mTaskWidth;
                int i15 = this.mTaskHeight;
                rect3.set(i14 - i3, i15 - i3, i14 + i3, i15 + i3);
                this.mPointerTouchableRegion.set(i11, i11, this.mTaskWidth + i3, this.mTaskHeight + i3);
                this.mPointerTouchableRegion.op(this.mTmpBounds, Region.Op.DIFFERENCE);
                this.mPointerTouchableRegion.op(this.mPointerLeftTopCornerBounds, Region.Op.UNION);
                this.mPointerTouchableRegion.op(this.mPointerRightTopCornerBounds, Region.Op.UNION);
                this.mPointerTouchableRegion.op(this.mPointerLeftBottomCornerBounds, Region.Op.UNION);
                this.mPointerTouchableRegion.op(this.mPointerRightBottomCornerBounds, Region.Op.UNION);
                region = new Region();
                int i16 = this.mResizeHandleThickness;
                region.union(new Rect(-i16, -i16, this.mTaskWidth + i16, 0));
                if (CoreRune.MW_CAPTION_SHELL && i2 != 0) {
                    int i17 = (this.mTaskWidth - i2) / 2;
                    region.op(new Rect(i17, -this.mResizeHandleThickness, i17 + i2, 0), Region.Op.DIFFERENCE);
                }
                region.union(new Rect(-this.mResizeHandleThickness, 0, this.mInnerResizeHandleThickness, this.mTaskHeight));
                int i18 = this.mTaskWidth;
                region.union(new Rect(i18 - this.mInnerResizeHandleThickness, 0, i18 + this.mResizeHandleThickness, this.mTaskHeight));
                int i19 = this.mResizeHandleThickness;
                int i20 = this.mTaskHeight;
                region.union(new Rect(-i19, i20 - this.mInnerResizeHandleThickness, this.mTaskWidth + i19, i20 + i19));
                int i21 = this.mCornerSize;
                int i22 = i21 / 2;
                this.mTmpCornerRect.set(0, 0, i21, i21);
                int i23 = i22 - i4;
                this.mTmpInnerRect.set(0, 0, i23, i23);
                int i24 = -i22;
                this.mTmpCornerRect.offsetTo(i24, i24);
                this.mTmpInnerRect.offsetTo(i4, i4);
                this.mLeftTopCornerRegion.set(this.mTmpCornerRect);
                this.mLeftTopCornerRegion.op(this.mTmpInnerRect, Region.Op.DIFFERENCE);
                this.mTmpCornerRect.offsetTo(this.mTaskWidth - i22, i24);
                this.mTmpInnerRect.offsetTo(this.mTaskWidth - i22, i4);
                this.mRightTopCornerRegion.set(this.mTmpCornerRect);
                this.mRightTopCornerRegion.op(this.mTmpInnerRect, Region.Op.DIFFERENCE);
                this.mTmpCornerRect.offsetTo(i24, this.mTaskHeight - i22);
                this.mTmpInnerRect.offsetTo(i4, this.mTaskHeight - i22);
                this.mLeftBottomCornerRegion.set(this.mTmpCornerRect);
                this.mLeftBottomCornerRegion.op(this.mTmpInnerRect, Region.Op.DIFFERENCE);
                this.mTmpCornerRect.offsetTo(this.mTaskWidth - i22, this.mTaskHeight - i22);
                this.mTmpInnerRect.offsetTo(this.mTaskWidth - i22, this.mTaskHeight - i22);
                this.mRightBottomCornerRegion.set(this.mTmpCornerRect);
                this.mRightBottomCornerRegion.op(this.mTmpInnerRect, Region.Op.DIFFERENCE);
                region.op(this.mLeftTopCornerRegion, Region.Op.UNION);
                region.op(this.mRightTopCornerRegion, Region.Op.UNION);
                region.op(this.mLeftBottomCornerRegion, Region.Op.UNION);
                region.op(this.mRightBottomCornerRegion, Region.Op.UNION);
                if (!CoreRune.MW_CAPTION_SHELL) {
                    this.mLastTouchRegion.set(region);
                    updateTouchableState(z);
                    return true;
                }
                try {
                    this.mWindowSession.updateInputChannel(this.mInputChannel.getToken(), this.mDisplayId, this.mDecorationSurface, 8, QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT, 0, region);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
                return true;
            }
        } else {
            i10 = i6;
        }
        z2 = false;
        if (this.mTaskWidth != i5) {
        }
        this.mTaskWidth = i5;
        this.mTaskHeight = i10;
        this.mResizeHandleThickness = i7;
        this.mCornerSize = i8;
        this.mDragDetector.mTouchSlop = i9;
        this.mInnerResizeHandleThickness = i4;
        this.mPointerResizeHandleThickness = i3;
        this.mTmpBounds.set(0, 0, i5, i10);
        int i112 = -i3;
        this.mPointerLeftTopCornerBounds.set(i112, i112, i3, i3);
        Rect rect4 = this.mPointerRightTopCornerBounds;
        int i122 = this.mTaskWidth;
        rect4.set(i122 - i3, i112, i122 + i3, i3);
        Rect rect22 = this.mPointerLeftBottomCornerBounds;
        int i132 = this.mTaskHeight;
        rect22.set(i112, i132 - i3, i3, i132 + i3);
        Rect rect32 = this.mPointerRightBottomCornerBounds;
        int i142 = this.mTaskWidth;
        int i152 = this.mTaskHeight;
        rect32.set(i142 - i3, i152 - i3, i142 + i3, i152 + i3);
        this.mPointerTouchableRegion.set(i112, i112, this.mTaskWidth + i3, this.mTaskHeight + i3);
        this.mPointerTouchableRegion.op(this.mTmpBounds, Region.Op.DIFFERENCE);
        this.mPointerTouchableRegion.op(this.mPointerLeftTopCornerBounds, Region.Op.UNION);
        this.mPointerTouchableRegion.op(this.mPointerRightTopCornerBounds, Region.Op.UNION);
        this.mPointerTouchableRegion.op(this.mPointerLeftBottomCornerBounds, Region.Op.UNION);
        this.mPointerTouchableRegion.op(this.mPointerRightBottomCornerBounds, Region.Op.UNION);
        region = new Region();
        int i162 = this.mResizeHandleThickness;
        region.union(new Rect(-i162, -i162, this.mTaskWidth + i162, 0));
        if (CoreRune.MW_CAPTION_SHELL) {
            int i172 = (this.mTaskWidth - i2) / 2;
            region.op(new Rect(i172, -this.mResizeHandleThickness, i172 + i2, 0), Region.Op.DIFFERENCE);
        }
        region.union(new Rect(-this.mResizeHandleThickness, 0, this.mInnerResizeHandleThickness, this.mTaskHeight));
        int i182 = this.mTaskWidth;
        region.union(new Rect(i182 - this.mInnerResizeHandleThickness, 0, i182 + this.mResizeHandleThickness, this.mTaskHeight));
        int i192 = this.mResizeHandleThickness;
        int i202 = this.mTaskHeight;
        region.union(new Rect(-i192, i202 - this.mInnerResizeHandleThickness, this.mTaskWidth + i192, i202 + i192));
        int i212 = this.mCornerSize;
        int i222 = i212 / 2;
        this.mTmpCornerRect.set(0, 0, i212, i212);
        int i232 = i222 - i4;
        this.mTmpInnerRect.set(0, 0, i232, i232);
        int i242 = -i222;
        this.mTmpCornerRect.offsetTo(i242, i242);
        this.mTmpInnerRect.offsetTo(i4, i4);
        this.mLeftTopCornerRegion.set(this.mTmpCornerRect);
        this.mLeftTopCornerRegion.op(this.mTmpInnerRect, Region.Op.DIFFERENCE);
        this.mTmpCornerRect.offsetTo(this.mTaskWidth - i222, i242);
        this.mTmpInnerRect.offsetTo(this.mTaskWidth - i222, i4);
        this.mRightTopCornerRegion.set(this.mTmpCornerRect);
        this.mRightTopCornerRegion.op(this.mTmpInnerRect, Region.Op.DIFFERENCE);
        this.mTmpCornerRect.offsetTo(i242, this.mTaskHeight - i222);
        this.mTmpInnerRect.offsetTo(i4, this.mTaskHeight - i222);
        this.mLeftBottomCornerRegion.set(this.mTmpCornerRect);
        this.mLeftBottomCornerRegion.op(this.mTmpInnerRect, Region.Op.DIFFERENCE);
        this.mTmpCornerRect.offsetTo(this.mTaskWidth - i222, this.mTaskHeight - i222);
        this.mTmpInnerRect.offsetTo(this.mTaskWidth - i222, this.mTaskHeight - i222);
        this.mRightBottomCornerRegion.set(this.mTmpCornerRect);
        this.mRightBottomCornerRegion.op(this.mTmpInnerRect, Region.Op.DIFFERENCE);
        region.op(this.mLeftTopCornerRegion, Region.Op.UNION);
        region.op(this.mRightTopCornerRegion, Region.Op.UNION);
        region.op(this.mLeftBottomCornerRegion, Region.Op.UNION);
        region.op(this.mRightBottomCornerRegion, Region.Op.UNION);
        if (!CoreRune.MW_CAPTION_SHELL) {
        }
    }

    public final void updateTouchableState(boolean z) {
        try {
            this.mWindowSession.updateInputChannelWithPointerRegion(this.mInputChannel.getToken(), this.mDisplayId, this.mDecorationSurface, !z ? 24 : 8, QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT, 0, this.mLastTouchRegion, this.mPointerTouchableRegion);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }
}
