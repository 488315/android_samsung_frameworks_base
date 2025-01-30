package com.android.p038wm.shell.windowdecor;

import android.content.Context;
import android.graphics.Rect;
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
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.WindowManagerGlobal;
import com.android.internal.view.BaseIWindow;
import com.android.p038wm.shell.common.DisplayLayout;
import com.android.p038wm.shell.windowdecor.DragDetector;
import com.android.p038wm.shell.windowdecor.FreeformDimInputListener;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FreeformDimInputListener implements AutoCloseable {
    public final Choreographer mChoreographer;
    public final SurfaceControl mDecorationSurface;
    public final int mDisplayId;
    public final DragDetector mDragDetector;
    public int mDragPointerId;
    public final BaseIWindow mFakeWindow;
    public final FreeformCaptionTouchState mFreeformCaptionTouchState;
    public final Handler mHandler;
    public final InputChannel mInputChannel;
    public final TaskDimInputEventReceiver mInputEventReceiver;
    public boolean mLayerBoosted;
    public final int mTaskId;
    public final TaskPositioner mTaskPositioner;
    public boolean mTouchableState;
    public final IWindowSession mWindowSession;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TaskDimInputEventReceiver extends InputEventReceiver implements DragDetector.MotionEventHandler {
        public final Choreographer mChoreographer;
        public final RunnableC4167xec3dc213 mConsumeBatchEventRunnable;
        public boolean mConsumeBatchEventScheduled;
        public boolean mMoved;

        public /* synthetic */ TaskDimInputEventReceiver(FreeformDimInputListener freeformDimInputListener, InputChannel inputChannel, Handler handler, Choreographer choreographer, int i) {
            this(inputChannel, handler, choreographer);
        }

        /* JADX WARN: Code restructure failed: missing block: B:28:0x0070, code lost:
        
            if (r9 != 1) goto L36;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x0090, code lost:
        
            r3 = 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x0073, code lost:
        
            if (r9 == 1) goto L38;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x0094, code lost:
        
            r3 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x008e, code lost:
        
            if (r9 != 1) goto L36;
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x0092, code lost:
        
            if (r9 == 1) goto L38;
         */
        /* JADX WARN: Removed duplicated region for block: B:31:0x0099  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x00a8  */
        /* JADX WARN: Removed duplicated region for block: B:34:0x009e  */
        @Override // com.android.wm.shell.windowdecor.DragDetector.MotionEventHandler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean handleMotionEvent(MotionEvent motionEvent) {
            char c;
            if (!FreeformDimInputListener.this.mTaskPositioner.mTaskMotionController.mAllowTouches) {
                return false;
            }
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0) {
                char c2 = 65535;
                if (actionMasked != 1) {
                    if (actionMasked == 2) {
                        int findPointerIndex = motionEvent.findPointerIndex(FreeformDimInputListener.this.mDragPointerId);
                        if (findPointerIndex == -1) {
                            Log.e("FreeformDimInputListener", "Invalid pointerId=" + findPointerIndex + " in handleMotionEvent");
                            return false;
                        }
                        TaskPositioner taskPositioner = FreeformDimInputListener.this.mTaskPositioner;
                        MultitaskingWindowDecoration multitaskingWindowDecoration = taskPositioner.mMultiTaskingDecor;
                        if (multitaskingWindowDecoration != null) {
                            boolean isStashed = multitaskingWindowDecoration.mFreeformStashState.isStashed();
                            Rect rect = taskPositioner.mRepositionTaskBounds;
                            TaskMotionController taskMotionController = taskPositioner.mTaskMotionController;
                            if (isStashed && taskPositioner.mHasMoved) {
                                FreeformStashState freeformStashState = multitaskingWindowDecoration.mFreeformStashState;
                                int i = taskMotionController.mMinVisibleWidth + taskMotionController.mStashMoveThreshold;
                                int i2 = freeformStashState.mAnimType;
                                if (!freeformStashState.isLeftStashed()) {
                                    DisplayLayout displayLayout = taskPositioner.mDisplayController.getDisplayLayout(taskPositioner.mWindowDecoration.mTaskInfo.displayId);
                                    Rect rect2 = taskPositioner.mTmpRect;
                                    displayLayout.getStableBounds(rect2, false);
                                    if (rect.left < rect2.right - i) {
                                    }
                                } else if (rect.right > i) {
                                }
                                if (c != 1 ? taskMotionController.scheduleAnimateScaleUp(rect) : c == 0 ? taskMotionController.scheduleAnimateScaleDown(rect) : false) {
                                    c2 = c;
                                }
                            }
                            c = 65535;
                            if (c != 1 ? taskMotionController.scheduleAnimateScaleUp(rect) : c == 0 ? taskMotionController.scheduleAnimateScaleDown(rect) : false) {
                            }
                        }
                        if (c2 == 1) {
                            FreeformDimInputListener.this.updateBoostIfNeeded(true);
                        } else if (c2 == 0) {
                            FreeformDimInputListener.this.updateBoostIfNeeded(false);
                        }
                        if (CoreRune.MW_FREEFORM_MOTION_ANIMATION) {
                            FreeformDimInputListener.this.mFreeformCaptionTouchState.addMovementToVelocityTracker(motionEvent);
                        }
                        FreeformDimInputListener.this.mTaskPositioner.onDragPositioningMove(motionEvent.getRawX(findPointerIndex), motionEvent.getRawY(findPointerIndex));
                        this.mMoved = true;
                    } else if (actionMasked != 3) {
                        return false;
                    }
                }
                int findPointerIndex2 = motionEvent.findPointerIndex(FreeformDimInputListener.this.mDragPointerId);
                if (!this.mMoved) {
                    FreeformDimInputListener freeformDimInputListener = FreeformDimInputListener.this;
                    if (freeformDimInputListener.mDragPointerId != -1) {
                        freeformDimInputListener.updateBoostIfNeeded(false);
                        FreeformDimInputListener.this.mTaskPositioner.resetStashedFreeform(true);
                    }
                }
                if (findPointerIndex2 == -1) {
                    Log.e("FreeformDimInputListener", "Invalid pointerId=" + findPointerIndex2 + " in handleMotionEvent");
                    FreeformDimInputListener.this.mTaskPositioner.onDragPositioningEnd(-1.0f, -1.0f);
                } else {
                    if (CoreRune.MW_FREEFORM_MOTION_ANIMATION) {
                        FreeformDimInputListener.this.mFreeformCaptionTouchState.addMovementToVelocityTracker(motionEvent);
                        FreeformDimInputListener.this.mFreeformCaptionTouchState.computeCurrentVelocity();
                        FreeformDimInputListener freeformDimInputListener2 = FreeformDimInputListener.this;
                        freeformDimInputListener2.mTaskPositioner.mFreeformCaptionTouchState = freeformDimInputListener2.mFreeformCaptionTouchState;
                    }
                    FreeformDimInputListener.this.mTaskPositioner.onDragPositioningEnd(motionEvent.getRawX(findPointerIndex2), motionEvent.getRawY(findPointerIndex2));
                    if (CoreRune.MW_FREEFORM_MOTION_ANIMATION) {
                        FreeformCaptionTouchState freeformCaptionTouchState = FreeformDimInputListener.this.mFreeformCaptionTouchState;
                        VelocityTracker velocityTracker = freeformCaptionTouchState.mVelocityTracker;
                        if (velocityTracker != null) {
                            velocityTracker.recycle();
                            freeformCaptionTouchState.mVelocityTracker = null;
                        }
                        FreeformDimInputListener.this.mTaskPositioner.mFreeformCaptionTouchState = null;
                    }
                }
                FreeformDimInputListener.this.mDragPointerId = -1;
                return false;
            }
            if (CoreRune.MW_FREEFORM_MOTION_ANIMATION) {
                FreeformCaptionTouchState freeformCaptionTouchState2 = FreeformDimInputListener.this.mFreeformCaptionTouchState;
                VelocityTracker velocityTracker2 = freeformCaptionTouchState2.mVelocityTracker;
                if (velocityTracker2 == null) {
                    freeformCaptionTouchState2.mVelocityTracker = VelocityTracker.obtain();
                } else {
                    velocityTracker2.clear();
                }
                FreeformDimInputListener.this.mFreeformCaptionTouchState.addMovementToVelocityTracker(motionEvent);
            }
            FreeformDimInputListener.this.mDragPointerId = motionEvent.getPointerId(0);
            FreeformDimInputListener.this.mTaskPositioner.onDragPositioningStart(motionEvent.getRawX(0), motionEvent.getRawY(0), 0);
            this.mMoved = false;
            return true;
        }

        public final void onBatchedInputEventPending(int i) {
            if (this.mConsumeBatchEventScheduled) {
                return;
            }
            this.mChoreographer.postCallback(0, this.mConsumeBatchEventRunnable, null);
            this.mConsumeBatchEventScheduled = true;
        }

        public final void onInputEvent(InputEvent inputEvent) {
            finishInputEvent(inputEvent, !(inputEvent instanceof MotionEvent) ? false : FreeformDimInputListener.this.mDragDetector.onMotionEvent((MotionEvent) inputEvent));
        }

        /* JADX WARN: Type inference failed for: r1v2, types: [com.android.wm.shell.windowdecor.FreeformDimInputListener$TaskDimInputEventReceiver$$ExternalSyntheticLambda0] */
        private TaskDimInputEventReceiver(InputChannel inputChannel, Handler handler, Choreographer choreographer) {
            super(inputChannel, handler.getLooper());
            this.mChoreographer = choreographer;
            this.mConsumeBatchEventRunnable = new Runnable() { // from class: com.android.wm.shell.windowdecor.FreeformDimInputListener$TaskDimInputEventReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FreeformDimInputListener.TaskDimInputEventReceiver taskDimInputEventReceiver = FreeformDimInputListener.TaskDimInputEventReceiver.this;
                    taskDimInputEventReceiver.mConsumeBatchEventScheduled = false;
                    if (!taskDimInputEventReceiver.consumeBatchedInputEvents(taskDimInputEventReceiver.mChoreographer.getFrameTimeNanos()) || taskDimInputEventReceiver.mConsumeBatchEventScheduled) {
                        return;
                    }
                    taskDimInputEventReceiver.mChoreographer.postCallback(0, taskDimInputEventReceiver.mConsumeBatchEventRunnable, null);
                    taskDimInputEventReceiver.mConsumeBatchEventScheduled = true;
                }
            };
        }
    }

    public FreeformDimInputListener(Context context, Handler handler, Choreographer choreographer, int i, SurfaceControl surfaceControl, TaskPositioner taskPositioner, int i2) {
        IWindowSession windowSession = WindowManagerGlobal.getWindowSession();
        this.mWindowSession = windowSession;
        this.mDragPointerId = -1;
        this.mTouchableState = true;
        this.mFreeformCaptionTouchState = null;
        this.mHandler = handler;
        this.mChoreographer = choreographer;
        this.mTaskId = i2;
        this.mDisplayId = i;
        this.mDecorationSurface = surfaceControl;
        BaseIWindow baseIWindow = new BaseIWindow();
        this.mFakeWindow = baseIWindow;
        baseIWindow.setSession(windowSession);
        Binder binder = new Binder();
        InputChannel inputChannel = new InputChannel();
        this.mInputChannel = inputChannel;
        this.mTaskPositioner = taskPositioner;
        try {
            windowSession.grantInputChannel(i, surfaceControl, baseIWindow, (IBinder) null, 8, QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT, 0, 2, (IBinder) null, binder, "FreeformDimInputListener of " + surfaceControl.toString(), inputChannel);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        TaskDimInputEventReceiver taskDimInputEventReceiver = new TaskDimInputEventReceiver(this, this.mInputChannel, this.mHandler, this.mChoreographer, 0);
        this.mInputEventReceiver = taskDimInputEventReceiver;
        DragDetector dragDetector = new DragDetector(taskDimInputEventReceiver);
        this.mDragDetector = dragDetector;
        dragDetector.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        if (CoreRune.MW_FREEFORM_MOTION_ANIMATION) {
            this.mFreeformCaptionTouchState = new FreeformCaptionTouchState(ViewConfiguration.get(context));
        }
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        updateBoostIfNeeded(false);
        this.mInputEventReceiver.dispose();
        this.mInputChannel.dispose();
        try {
            this.mWindowSession.remove(this.mFakeWindow);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public final void updateBoostIfNeeded(boolean z) {
        if (this.mLayerBoosted != z) {
            this.mLayerBoosted = z;
            Log.d("FreeformDimInputListener", "updateBoostIfNeeded: t #" + this.mTaskId + ", boost=" + z);
            MultiWindowManager.getInstance().setBoostFreeformTaskLayer(this.mTaskId, z);
        }
    }
}
