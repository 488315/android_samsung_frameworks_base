package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewConfiguration;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.freeform.FreeformTaskTransitionHandler;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.DragDetector;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CaptionWindowDecorViewModel implements WindowDecorViewModel {
    public final Context mContext;
    public final DisplayController mDisplayController;
    public final Choreographer mMainChoreographer;
    public final Handler mMainHandler;
    public final SyncTransactionQueue mSyncQueue;
    public TaskOperations mTaskOperations;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final SparseArray mWindowDecorByTaskId = new SparseArray();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CaptionTouchEventListener implements View.OnClickListener, View.OnTouchListener, DragDetector.MotionEventHandler {
        public final DragDetector mDragDetector;
        public int mDragPointerId;
        public final DragPositioningCallback mDragPositioningCallback;
        public boolean mIsDragging;
        public final int mTaskId;
        public final WindowContainerToken mTaskToken;

        public /* synthetic */ CaptionTouchEventListener(CaptionWindowDecorViewModel captionWindowDecorViewModel, ActivityManager.RunningTaskInfo runningTaskInfo, FluidResizeTaskPositioner fluidResizeTaskPositioner) {
            this(runningTaskInfo, (DragPositioningCallback) fluidResizeTaskPositioner);
        }

        @Override // com.android.wm.shell.windowdecor.DragDetector.MotionEventHandler
        public final boolean handleMotionEvent(MotionEvent motionEvent) {
            if (CaptionWindowDecorViewModel.this.mTaskOrganizer.getRunningTaskInfo(this.mTaskId).getWindowingMode() == 1) {
                return false;
            }
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                this.mDragPointerId = motionEvent.getPointerId(0);
                this.mDragPositioningCallback.onDragPositioningStart(motionEvent.getRawX(0), motionEvent.getRawY(0), 0);
                this.mIsDragging = false;
                return false;
            }
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    int findPointerIndex = motionEvent.findPointerIndex(this.mDragPointerId);
                    this.mDragPositioningCallback.onDragPositioningMove(motionEvent.getRawX(findPointerIndex), motionEvent.getRawY(findPointerIndex));
                    this.mIsDragging = true;
                    return true;
                }
                if (actionMasked != 3) {
                    return true;
                }
            }
            int findPointerIndex2 = motionEvent.findPointerIndex(this.mDragPointerId);
            this.mDragPositioningCallback.onDragPositioningEnd(motionEvent.getRawX(findPointerIndex2), motionEvent.getRawY(findPointerIndex2));
            boolean z = this.mIsDragging;
            this.mIsDragging = false;
            return z;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            int id = view.getId();
            if (id == R.id.close_window) {
                CaptionWindowDecorViewModel.this.mTaskOperations.closeTask(this.mTaskToken);
                return;
            }
            if (id == R.id.back_button) {
                TaskOperations taskOperations = CaptionWindowDecorViewModel.this.mTaskOperations;
                Context context = taskOperations.mContext;
                taskOperations.sendBackEvent(0, context.getDisplayId());
                taskOperations.sendBackEvent(1, context.getDisplayId());
                return;
            }
            if (id == R.id.minimize_window) {
                CaptionWindowDecorViewModel.this.mTaskOperations.minimizeTask(this.mTaskToken);
            } else if (id == R.id.maximize_window) {
                CaptionWindowDecorViewModel.this.mTaskOperations.maximizeTask(CaptionWindowDecorViewModel.this.mTaskOrganizer.getRunningTaskInfo(this.mTaskId));
            }
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            if (view.getId() != R.id.caption) {
                return false;
            }
            if (motionEvent.getAction() == 0 && !CaptionWindowDecorViewModel.this.mTaskOrganizer.getRunningTaskInfo(this.mTaskId).isFocused) {
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                windowContainerTransaction.reorder(this.mTaskToken, true);
                CaptionWindowDecorViewModel.this.mSyncQueue.queue(windowContainerTransaction);
            }
            return this.mDragDetector.onMotionEvent(motionEvent);
        }

        private CaptionTouchEventListener(ActivityManager.RunningTaskInfo runningTaskInfo, DragPositioningCallback dragPositioningCallback) {
            this.mDragPointerId = -1;
            this.mTaskId = runningTaskInfo.taskId;
            this.mTaskToken = runningTaskInfo.token;
            this.mDragPositioningCallback = dragPositioningCallback;
            this.mDragDetector = new DragDetector(this);
        }
    }

    public CaptionWindowDecorViewModel(Context context, Handler handler, Choreographer choreographer, ShellTaskOrganizer shellTaskOrganizer, DisplayController displayController, SyncTransactionQueue syncTransactionQueue) {
        this.mContext = context;
        this.mMainHandler = handler;
        this.mMainChoreographer = choreographer;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mDisplayController = displayController;
        this.mSyncQueue = syncTransactionQueue;
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            return;
        }
        this.mTaskOperations = new TaskOperations(null, context, syncTransactionQueue, null);
    }

    public static void setupCaptionColor(ActivityManager.RunningTaskInfo runningTaskInfo, CaptionWindowDecoration captionWindowDecoration) {
        int statusBarColor = runningTaskInfo.taskDescription.getStatusBarColor();
        View view = captionWindowDecoration.mResult.mRootView;
        if (view == null) {
            return;
        }
        View findViewById = ((WindowDecorLinearLayout) view).findViewById(R.id.caption);
        ((GradientDrawable) findViewById.getBackground()).setColor(statusBarColor);
        ColorStateList colorStateList = findViewById.getResources().getColorStateList(((double) Color.valueOf(statusBarColor).luminance()) < 0.5d ? R.color.decor_button_light_color : R.color.decor_button_dark_color, null);
        ((VectorDrawable) findViewById.findViewById(R.id.back_button).getBackground()).setTintList(colorStateList);
        ((VectorDrawable) findViewById.findViewById(R.id.minimize_window).getBackground()).setTintList(colorStateList);
        ((VectorDrawable) findViewById.findViewById(R.id.maximize_window).getBackground()).setTintList(colorStateList);
        ((VectorDrawable) findViewById.findViewById(R.id.close_window).getBackground()).setTintList(colorStateList);
    }

    public final void createWindowDecoration(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        SparseArray sparseArray = this.mWindowDecorByTaskId;
        CaptionWindowDecoration captionWindowDecoration = (CaptionWindowDecoration) sparseArray.get(runningTaskInfo.taskId);
        if (captionWindowDecoration != null) {
            captionWindowDecoration.close();
        }
        CaptionWindowDecoration captionWindowDecoration2 = new CaptionWindowDecoration(this.mContext, this.mDisplayController, this.mTaskOrganizer, runningTaskInfo, surfaceControl, this.mMainHandler, this.mMainChoreographer, this.mSyncQueue);
        sparseArray.put(runningTaskInfo.taskId, captionWindowDecoration2);
        FluidResizeTaskPositioner fluidResizeTaskPositioner = new FluidResizeTaskPositioner(this.mTaskOrganizer, captionWindowDecoration2, this.mDisplayController, null);
        CaptionTouchEventListener captionTouchEventListener = new CaptionTouchEventListener(this, runningTaskInfo, fluidResizeTaskPositioner);
        captionWindowDecoration2.mOnCaptionButtonClickListener = captionTouchEventListener;
        captionWindowDecoration2.mOnCaptionTouchListener = captionTouchEventListener;
        captionWindowDecoration2.mDragPositioningCallback = fluidResizeTaskPositioner;
        DragDetector dragDetector = captionTouchEventListener.mDragDetector;
        captionWindowDecoration2.mDragDetector = dragDetector;
        dragDetector.mTouchSlop = ViewConfiguration.get(captionWindowDecoration2.mContext).getScaledTouchSlop();
        captionWindowDecoration2.relayout(runningTaskInfo, transaction, transaction2, false);
        setupCaptionColor(runningTaskInfo, captionWindowDecoration2);
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void destroyWindowDecoration(ActivityManager.RunningTaskInfo runningTaskInfo) {
        CaptionWindowDecoration captionWindowDecoration = (CaptionWindowDecoration) this.mWindowDecorByTaskId.removeReturnOld(runningTaskInfo.taskId);
        if (captionWindowDecoration == null) {
            return;
        }
        captionWindowDecoration.close();
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskChanging(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        CaptionWindowDecoration captionWindowDecoration = (CaptionWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        boolean z = true;
        if (runningTaskInfo.getWindowingMode() != 5 && (runningTaskInfo.getActivityType() != 1 || runningTaskInfo.configuration.windowConfiguration.getDisplayWindowingMode() != 5)) {
            z = false;
        }
        if (!z) {
            if (captionWindowDecoration != null) {
                destroyWindowDecoration(runningTaskInfo);
            }
        } else if (captionWindowDecoration == null) {
            createWindowDecoration(runningTaskInfo, surfaceControl, transaction, transaction2);
        } else {
            captionWindowDecoration.relayout(runningTaskInfo, transaction, transaction2, false);
        }
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskClosing(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        CaptionWindowDecoration captionWindowDecoration = (CaptionWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (captionWindowDecoration == null) {
            return;
        }
        captionWindowDecoration.relayout(runningTaskInfo, transaction, transaction2, false);
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        CaptionWindowDecoration captionWindowDecoration = (CaptionWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (captionWindowDecoration == null) {
            return;
        }
        captionWindowDecoration.relayout(runningTaskInfo);
        setupCaptionColor(runningTaskInfo, captionWindowDecoration);
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final boolean onTaskOpening(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        if (!(runningTaskInfo.getWindowingMode() == 5 || (runningTaskInfo.getActivityType() == 1 && runningTaskInfo.configuration.windowConfiguration.getDisplayWindowingMode() == 5))) {
            return false;
        }
        createWindowDecoration(runningTaskInfo, surfaceControl, transaction, transaction2);
        return true;
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void setFreeformTaskTransitionStarter(FreeformTaskTransitionHandler freeformTaskTransitionHandler) {
        this.mTaskOperations = new TaskOperations(freeformTaskTransitionHandler, this.mContext, this.mSyncQueue, null);
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTransitionFinished(IBinder iBinder) {
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTransitionMerged(IBinder iBinder, IBinder iBinder2) {
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, TransitionInfo.Change change) {
    }
}
