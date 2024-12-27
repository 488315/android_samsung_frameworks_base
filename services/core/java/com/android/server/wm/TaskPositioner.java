package com.android.server.wm;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.InputApplicationHandle;
import android.view.InputChannel;
import android.view.InputEventReceiver;
import android.view.InputWindowHandle;

import com.android.internal.policy.TaskResizingAlgorithm;

public final class TaskPositioner implements IBinder.DeathRecipient {
    public static AnonymousClass1 sFactory;
    public IBinder mClientCallback;
    public InputChannel mClientChannel;
    public DisplayContent mDisplayContent;
    public InputApplicationHandle mDragApplicationHandle;
    boolean mDragEnded;
    public InputWindowHandle mDragWindowHandle;
    public InputEventReceiver mInputEventReceiver;
    public int mMinVisibleHeight;
    public int mMinVisibleWidth;
    public boolean mPreserveOrientation;
    public boolean mResizing;
    public final WindowManagerService mService;
    public float mStartDragX;
    public float mStartDragY;
    public boolean mStartOrientationWasLandscape;
    Task mTask;
    public final Rect mTmpRect = new Rect();
    public final Rect mWindowOriginalBounds = new Rect();
    public final Rect mWindowDragBounds = new Rect();
    public final Point mMaxVisibleSize = new Point();
    public int mCtrlType = 0;

    /* renamed from: com.android.server.wm.TaskPositioner$1, reason: invalid class name */
    public final class AnonymousClass1 {}

    public TaskPositioner(WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        this.mService.mTaskPositioningController.finishTaskPositioning$1();
    }

    public Rect getWindowDragBounds() {
        return this.mWindowDragBounds;
    }

    public boolean notifyMoveLocked(float f, float f2) {
        if (this.mCtrlType != 0) {
            resizeDrag(f, f2);
            this.mTask.setDragResizing(true);
            return false;
        }
        this.mDisplayContent.getStableRect(this.mTmpRect);
        this.mTmpRect.intersect(this.mTask.getRootTask().getParent().getBounds());
        int i = (int) f;
        int i2 = (int) f2;
        if (!this.mTmpRect.contains(i, i2)) {
            i = Math.min(Math.max(i, this.mTmpRect.left), this.mTmpRect.right);
            i2 = Math.min(Math.max(i2, this.mTmpRect.top), this.mTmpRect.bottom);
        }
        Rect rect = this.mTmpRect;
        int round = Math.round(i - this.mStartDragX);
        int round2 = Math.round(i2 - this.mStartDragY);
        this.mWindowDragBounds.set(this.mWindowOriginalBounds);
        int i3 = rect.right;
        int i4 = this.mMinVisibleWidth;
        this.mWindowDragBounds.offsetTo(
                Math.min(
                        Math.max(
                                this.mWindowOriginalBounds.left + round,
                                (rect.left + i4) - this.mWindowOriginalBounds.width()),
                        i3 - i4),
                Math.min(
                        Math.max(this.mWindowOriginalBounds.top + round2, rect.top),
                        rect.bottom - this.mMinVisibleHeight));
        return false;
    }

    public void resizeDrag(float f, float f2) {
        this.mWindowDragBounds.set(
                TaskResizingAlgorithm.resizeDrag(
                        f,
                        f2,
                        this.mStartDragX,
                        this.mStartDragY,
                        this.mWindowOriginalBounds,
                        this.mCtrlType,
                        this.mMinVisibleWidth,
                        this.mMinVisibleHeight,
                        this.mMaxVisibleSize,
                        this.mPreserveOrientation,
                        this.mStartOrientationWasLandscape));
    }

    public final void startDrag(float f, float f2) {
        final Rect rect = this.mTmpRect;
        this.mTask.getBounds(rect);
        this.mCtrlType = 0;
        this.mStartDragX = f;
        this.mStartDragY = f2;
        this.mPreserveOrientation = false;
        this.mStartOrientationWasLandscape = rect.width() >= rect.height();
        this.mWindowOriginalBounds.set(rect);
        if (this.mResizing) {
            notifyMoveLocked(f, f2);
            this.mService.mH.post(
                    new Runnable() { // from class:
                                     // com.android.server.wm.TaskPositioner$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            TaskPositioner taskPositioner = TaskPositioner.this;
                            taskPositioner.mService.mAtmService.resizeTask(
                                    taskPositioner.mTask.mTaskId, rect, 3);
                        }
                    });
        }
        this.mWindowDragBounds.set(rect);
    }
}
