package com.android.wm.shell.taskview;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Insets;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Handler;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class TaskView extends SurfaceView implements SurfaceHolder.Callback, ViewTreeObserver.OnComputeInternalInsetsListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Rect mBoundsOnScreen;
    public Insets mCaptionInsets;
    public Handler mHandler;
    public Region mObscuredTouchRegion;
    public boolean mStarted;
    public final TaskViewController mTaskViewController;
    public final TaskViewTaskController mTaskViewTaskController;
    public final int[] mTmpLocation;
    public final Rect mTmpRect;
    public final Rect mTmpRootRect;

    public TaskView(Context context, TaskViewController taskViewController, TaskViewTaskController taskViewTaskController) {
        super(context, null, 0, 0, true);
        this.mTmpRect = new Rect();
        this.mTmpRootRect = new Rect();
        this.mTmpLocation = new int[2];
        this.mBoundsOnScreen = new Rect();
        this.mTaskViewController = taskViewController;
        this.mTaskViewTaskController = taskViewTaskController;
        taskViewTaskController.mTaskViewBase = this;
        this.mHandler = Handler.getMain();
        getHolder().addCallback(this);
    }

    @Override // android.view.SurfaceView, android.view.View
    public final boolean gatherTransparentRegion(Region region) {
        return (!PixelFormat.formatHasAlpha(getViewRootImpl().mWindowAttributes.format) || this.mStarted) ? super.gatherTransparentRegion(region) : gatherTransparentRegionWhenStartTaskView(region);
    }

    @Override // android.view.SurfaceView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnComputeInternalInsetsListener(this);
        this.mHandler = getHandler();
    }

    public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        if (internalInsetsInfo.touchableRegion.isEmpty()) {
            internalInsetsInfo.setTouchableInsets(3);
            View rootView = getRootView();
            rootView.getLocationInWindow(this.mTmpLocation);
            Rect rect = this.mTmpRootRect;
            int[] iArr = this.mTmpLocation;
            rect.set(iArr[0], iArr[1], rootView.getWidth(), rootView.getHeight());
            internalInsetsInfo.touchableRegion.set(this.mTmpRootRect);
        }
        getLocationInWindow(this.mTmpLocation);
        Rect rect2 = this.mTmpRect;
        int[] iArr2 = this.mTmpLocation;
        int i = iArr2[0];
        rect2.set(i, iArr2[1], getWidth() + i, getHeight() + this.mTmpLocation[1]);
        Insets insets = this.mCaptionInsets;
        if (insets != null) {
            this.mTmpRect.inset(insets);
            getBoundsOnScreen(this.mBoundsOnScreen);
            TaskViewTaskController taskViewTaskController = this.mTaskViewTaskController;
            Rect rect3 = this.mBoundsOnScreen;
            Rect rect4 = new Rect(rect3.left, rect3.top, getWidth() + rect3.right, this.mBoundsOnScreen.top + this.mCaptionInsets.top);
            Rect rect5 = taskViewTaskController.mCaptionInsets;
            if (rect5 == null || !rect5.equals(rect4)) {
                taskViewTaskController.mCaptionInsets = rect4;
                taskViewTaskController.applyCaptionInsetsIfNeeded();
            }
        }
        internalInsetsInfo.touchableRegion.op(this.mTmpRect, Region.Op.DIFFERENCE);
        Region region = this.mObscuredTouchRegion;
        if (region != null) {
            internalInsetsInfo.touchableRegion.op(region, Region.Op.UNION);
        }
    }

    @Override // android.view.SurfaceView, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnComputeInternalInsetsListener(this);
        this.mHandler = Handler.getMain();
    }

    public final void runOnViewThread(Runnable runnable) {
        if (this.mHandler.getLooper().isCurrentThread()) {
            runnable.run();
            return;
        }
        Handler handler = getHandler();
        if (this.mHandler.getLooper().getThread() != (handler != null ? handler.getLooper().getThread() : null)) {
            post(runnable);
        } else {
            this.mHandler.post(runnable);
        }
    }

    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    public final void setListener(Executor executor, Listener listener) {
        TaskViewTaskController taskViewTaskController = this.mTaskViewTaskController;
        if (taskViewTaskController.mListener != null) {
            throw new IllegalStateException("Trying to set a listener when one has already been set");
        }
        taskViewTaskController.mListener = listener;
        taskViewTaskController.mListenerExecutor = executor;
    }

    public final void startActivity(PendingIntent pendingIntent, Intent intent, ActivityOptions activityOptions, Rect rect) {
        this.mTaskViewController.startActivity(this.mTaskViewTaskController, pendingIntent, intent, activityOptions, rect);
        this.mStarted = true;
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        getBoundsOnScreen(this.mTmpRect);
        this.mTaskViewController.setTaskBounds(this.mTaskViewTaskController, this.mTmpRect);
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceCreated(SurfaceHolder surfaceHolder) {
        TaskViewTaskController taskViewTaskController = this.mTaskViewTaskController;
        SurfaceControl surfaceControl = getSurfaceControl();
        taskViewTaskController.mSurfaceCreated = true;
        taskViewTaskController.mSurfaceControl = surfaceControl;
        if (surfaceControl != null) {
            taskViewTaskController.mTransaction.setTrustedOverlay(surfaceControl, 1).apply();
        }
        if (taskViewTaskController.mListener != null && !taskViewTaskController.mNotifiedForInitialized) {
            taskViewTaskController.mNotifiedForInitialized = true;
            taskViewTaskController.mListenerExecutor.execute(new TaskViewTaskController$$ExternalSyntheticLambda0(taskViewTaskController, 1));
        }
        taskViewTaskController.mShellExecutor.execute(new TaskViewTaskController$$ExternalSyntheticLambda0(taskViewTaskController, 2));
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        TaskViewTaskController taskViewTaskController = this.mTaskViewTaskController;
        taskViewTaskController.mSurfaceCreated = false;
        taskViewTaskController.mSurfaceControl = null;
        taskViewTaskController.mShellExecutor.execute(new TaskViewTaskController$$ExternalSyntheticLambda0(taskViewTaskController, 3));
    }

    @Override // android.view.View
    public final String toString() {
        return this.mTaskViewTaskController.toString();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Listener {
        void onBackPressedOnTaskRoot(int i);

        void onInitialized();

        void onTaskCreated(int i, ComponentName componentName);

        void onTaskRemovalStarted(int i);

        default void onReleased() {
        }

        default void onTaskVisibilityChanged(int i, boolean z) {
        }
    }
}
