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
import android.view.ViewConfiguration;
import android.view.WindowManagerGlobal;
import android.window.InputTransferToken;
import com.android.wm.shell.windowdecor.DragDetector;
import com.android.wm.shell.windowdecor.FreeformDimInputListener;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.systemui.splugins.volume.VolumePanelValues;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean handleMotionEvent(android.view.View r10, android.view.MotionEvent r11) {
            /*
                Method dump skipped, instructions count: 385
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.FreeformDimInputListener.TaskDimInputEventReceiver.handleMotionEvent(android.view.View, android.view.MotionEvent):boolean");
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
