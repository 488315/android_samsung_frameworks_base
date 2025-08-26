package com.android.wm.shell.windowdecor;

import android.content.Context;
import android.graphics.Region;
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
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManagerGlobal;
import android.window.InputTransferToken;
import com.android.wm.shell.windowdecor.DragDetector;
import com.android.wm.shell.windowdecor.FreeformDimInputListener;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.systemui.splugins.volume.VolumePanelValues;

/* loaded from: classes3.dex */
public class FreeformDimInputListener implements AutoCloseable {
    public final Choreographer mChoreographer;
    public final IBinder mClientToken;
    public final SurfaceControl mDecorationSurface;
    public final int mDisplayId;
    public final DragDetector mDragDetector;
    public int mDragPointerId;
    public final FreeformCaptionTouchState mFreeformCaptionTouchState;
    public final Handler mHandler;
    public final InputChannel mInputChannel;
    public final TaskDimInputEventReceiver mInputEventReceiver;
    public boolean mLayerBoosted;
    public final int mTaskId;
    public final TaskPositioner mTaskPositioner;
    public boolean mTouchableState;
    public final IWindowSession mWindowSession;

    public class TaskDimInputEventReceiver extends InputEventReceiver implements DragDetector.MotionEventHandler {
        public final Choreographer mChoreographer;
        public final FreeformDimInputListener$TaskDimInputEventReceiver$$ExternalSyntheticLambda0 mConsumeBatchEventRunnable;
        public boolean mConsumeBatchEventScheduled;
        public boolean mMoved;
        public boolean mTouchBlocked;

        public /* synthetic */ TaskDimInputEventReceiver(FreeformDimInputListener freeformDimInputListener, InputChannel inputChannel, Handler handler, Choreographer choreographer, int i) {
            this(inputChannel, handler, choreographer);
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0024, code lost:
        
            if (r1 != 3) goto L18;
         */
        @Override // com.android.wm.shell.windowdecor.DragDetector.MotionEventHandler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean handleMotionEvent(View view, MotionEvent motionEvent) {
            boolean z = CoreRune.MW_CAPTION_FREEFORM_MOTION;
            if (!z || FreeformDimInputListener.this.mTaskPositioner.isAllowTouches()) {
                int actionMasked = motionEvent.getActionMasked();
                if (actionMasked == 0) {
                    this.mTouchBlocked = false;
                    if (MultiWindowCoreState.MW_FREEFORM_CORNER_GESTURE_ENABLED && FreeformDimInputListener.this.mTaskPositioner.isStashedAtNavigationBarPosition() && MultiWindowManager.getInstance().isCornerGestureRunning()) {
                        Log.d("FreeformDimInputListener", "handleMotionEvent: skip, reason=corner_gesture_running");
                        this.mTouchBlocked = true;
                        return false;
                    }
                    if (z) {
                        FreeformCaptionTouchState freeformCaptionTouchState = FreeformDimInputListener.this.mFreeformCaptionTouchState;
                        VelocityTracker velocityTracker = freeformCaptionTouchState.mVelocityTracker;
                        if (velocityTracker == null) {
                            freeformCaptionTouchState.mVelocityTracker = VelocityTracker.obtain();
                        } else {
                            velocityTracker.clear();
                        }
                        FreeformDimInputListener.this.mFreeformCaptionTouchState.addMovementToVelocityTracker(motionEvent);
                    }
                    FreeformDimInputListener.this.mDragPointerId = motionEvent.getPointerId(0);
                    float rawX = motionEvent.getRawX(0);
                    float rawY = motionEvent.getRawY(0);
                    FreeformDimInputListener freeformDimInputListener = FreeformDimInputListener.this;
                    freeformDimInputListener.mTaskPositioner.onDragPositioningStart(0, rawX, rawY, freeformDimInputListener.mDisplayId);
                    this.mMoved = false;
                    return true;
                }
                if (actionMasked != 1) {
                    if (actionMasked == 2) {
                        if (!this.mTouchBlocked) {
                            int iFindPointerIndex = motionEvent.findPointerIndex(FreeformDimInputListener.this.mDragPointerId);
                            if (iFindPointerIndex == -1) {
                                Log.e("FreeformDimInputListener", "Invalid pointerId=" + iFindPointerIndex + " in handleMotionEvent");
                                return false;
                            }
                            if (CoreRune.MW_CAPTION_FREEFORM_STASH) {
                                int iChangeFreeformScaleIfNeeded = FreeformDimInputListener.this.mTaskPositioner.changeFreeformScaleIfNeeded();
                                if (iChangeFreeformScaleIfNeeded == 1) {
                                    FreeformDimInputListener.this.updateBoostIfNeeded(true);
                                } else if (iChangeFreeformScaleIfNeeded == 0) {
                                    FreeformDimInputListener.this.updateBoostIfNeeded(false);
                                }
                            }
                            if (z) {
                                FreeformDimInputListener.this.mFreeformCaptionTouchState.addMovementToVelocityTracker(motionEvent);
                            }
                            float rawX2 = motionEvent.getRawX(iFindPointerIndex);
                            float rawY2 = motionEvent.getRawY(iFindPointerIndex);
                            FreeformDimInputListener freeformDimInputListener2 = FreeformDimInputListener.this;
                            freeformDimInputListener2.mTaskPositioner.onDragPositioningMove(rawX2, rawY2, freeformDimInputListener2.mDisplayId);
                            this.mMoved = true;
                            return true;
                        }
                    }
                }
                if (this.mTouchBlocked) {
                    this.mTouchBlocked = false;
                    return false;
                }
                int iFindPointerIndex2 = motionEvent.findPointerIndex(FreeformDimInputListener.this.mDragPointerId);
                if (CoreRune.MW_CAPTION_FREEFORM_STASH && !this.mMoved) {
                    FreeformDimInputListener freeformDimInputListener3 = FreeformDimInputListener.this;
                    if (freeformDimInputListener3.mDragPointerId != -1) {
                        freeformDimInputListener3.updateBoostIfNeeded(false);
                        FreeformDimInputListener.this.mTaskPositioner.resetStashedFreeform(true);
                    }
                }
                if (iFindPointerIndex2 == -1) {
                    Log.e("FreeformDimInputListener", "Invalid pointerId=" + iFindPointerIndex2 + " in handleMotionEvent");
                    FreeformDimInputListener freeformDimInputListener4 = FreeformDimInputListener.this;
                    freeformDimInputListener4.mTaskPositioner.onDragPositioningEnd(-1.0f, -1.0f, freeformDimInputListener4.mDisplayId);
                } else {
                    if (z) {
                        FreeformDimInputListener.this.mFreeformCaptionTouchState.addMovementToVelocityTracker(motionEvent);
                        FreeformCaptionTouchState freeformCaptionTouchState2 = FreeformDimInputListener.this.mFreeformCaptionTouchState;
                        VelocityTracker velocityTracker2 = freeformCaptionTouchState2.mVelocityTracker;
                        if (velocityTracker2 != null) {
                            velocityTracker2.computeCurrentVelocity(1000, freeformCaptionTouchState2.mMaximumFlingVelocity);
                            freeformCaptionTouchState2.mVelocity.set(freeformCaptionTouchState2.mVelocityTracker.getXVelocity(), freeformCaptionTouchState2.mVelocityTracker.getYVelocity());
                        }
                        FreeformDimInputListener freeformDimInputListener5 = FreeformDimInputListener.this;
                        freeformDimInputListener5.mTaskPositioner.setFreeformCaptionTouchState(freeformDimInputListener5.mFreeformCaptionTouchState);
                    }
                    float rawX3 = motionEvent.getRawX(iFindPointerIndex2);
                    float rawY3 = motionEvent.getRawY(iFindPointerIndex2);
                    FreeformDimInputListener freeformDimInputListener6 = FreeformDimInputListener.this;
                    freeformDimInputListener6.mTaskPositioner.onDragPositioningEnd(rawX3, rawY3, freeformDimInputListener6.mDisplayId);
                    if (z) {
                        FreeformCaptionTouchState freeformCaptionTouchState3 = FreeformDimInputListener.this.mFreeformCaptionTouchState;
                        VelocityTracker velocityTracker3 = freeformCaptionTouchState3.mVelocityTracker;
                        if (velocityTracker3 != null) {
                            velocityTracker3.recycle();
                            freeformCaptionTouchState3.mVelocityTracker = null;
                        }
                        FreeformDimInputListener.this.mTaskPositioner.setFreeformCaptionTouchState(null);
                    }
                }
                FreeformDimInputListener.this.mDragPointerId = -1;
                return false;
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
            finishInputEvent(inputEvent, !(inputEvent instanceof MotionEvent) ? false : FreeformDimInputListener.this.mDragDetector.onMotionEvent(null, (MotionEvent) inputEvent));
        }

        /* JADX WARN: Type inference failed for: r1v2, types: [com.android.wm.shell.windowdecor.FreeformDimInputListener$TaskDimInputEventReceiver$$ExternalSyntheticLambda0] */
        private TaskDimInputEventReceiver(InputChannel inputChannel, Handler handler, Choreographer choreographer) {
            super(inputChannel, handler.getLooper());
            this.mChoreographer = choreographer;
            this.mConsumeBatchEventRunnable = new Runnable() { // from class: com.android.wm.shell.windowdecor.FreeformDimInputListener$TaskDimInputEventReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FreeformDimInputListener.TaskDimInputEventReceiver taskDimInputEventReceiver = this.f$0;
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

    public FreeformDimInputListener(Context context, Handler handler, Choreographer choreographer, int i, SurfaceControl surfaceControl, TaskPositioner taskPositioner, int i2, InputChannel inputChannel) {
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
        Binder binder = new Binder();
        this.mClientToken = binder;
        InputTransferToken inputTransferToken = new InputTransferToken();
        this.mTaskPositioner = taskPositioner;
        if (inputChannel == null) {
            InputChannel inputChannel2 = new InputChannel();
            this.mInputChannel = inputChannel2;
            try {
                windowSession.grantInputChannel(i, surfaceControl, binder, (InputTransferToken) null, 8, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS, 0, 2, (IBinder) null, inputTransferToken, "FreeformDimInputListener of " + surfaceControl.toString(), inputChannel2);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        } else {
            this.mInputChannel = inputChannel;
        }
        TaskDimInputEventReceiver taskDimInputEventReceiver = new TaskDimInputEventReceiver(this, this.mInputChannel, this.mHandler, this.mChoreographer, 0);
        this.mInputEventReceiver = taskDimInputEventReceiver;
        DragDetector dragDetector = new DragDetector(taskDimInputEventReceiver, 0L, ViewConfiguration.get(context).getScaledTouchSlop());
        this.mDragDetector = dragDetector;
        dragDetector.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        if (CoreRune.MW_CAPTION_FREEFORM_MOTION) {
            this.mFreeformCaptionTouchState = new FreeformCaptionTouchState(ViewConfiguration.get(context));
        }
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        if (CoreRune.MW_CAPTION_FREEFORM_STASH) {
            updateBoostIfNeeded(false);
        }
        this.mInputEventReceiver.dispose();
        this.mInputChannel.dispose();
        try {
            this.mWindowSession.remove(this.mClientToken);
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

    public final void updateTouchableState(boolean z) {
        if (z == this.mTouchableState) {
            return;
        }
        this.mTouchableState = z;
        try {
            this.mWindowSession.updateInputChannel(this.mInputChannel.getToken(), this.mDisplayId, this.mDecorationSurface, z ? 8 : 24, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS, 0, (Region) null);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }
}
