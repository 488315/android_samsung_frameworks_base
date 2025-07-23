package com.android.wm.shell.freeform;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.util.Slog;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecoration;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class FreeformAdjustImeController {
    public boolean mAnimating;
    public final DesktopModeWindowDecoration mDecoration;
    public final DisplayController mDisplayController;
    public boolean mIsAdjusted;
    public int mLastYOffset;
    public int mTargetYOffset;
    public ActivityManager.RunningTaskInfo mTaskInfo;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final SurfaceControl mTaskSurface;
    public int mYOffsetForIme;
    public final Rect mOriginBounds = new Rect();
    public final Rect mAdjustingBounds = new Rect();
    public final Rect mTmpRect = new Rect();
    public final Rect mStartBounds = new Rect();
    public final Rect mDisplayFrame = new Rect();
    public int mLastOrientation = 0;
    public int mLastDisplayRotation = -1;

    public FreeformAdjustImeController(DesktopModeWindowDecoration desktopModeWindowDecoration, DisplayController displayController, ShellTaskOrganizer shellTaskOrganizer, SurfaceControl surfaceControl) {
        this.mDecoration = desktopModeWindowDecoration;
        this.mDisplayController = displayController;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mTaskSurface = surfaceControl;
    }

    public final void adjustConfig(WindowContainerToken windowContainerToken) {
        if (this.mOriginBounds.isEmpty()) {
            Slog.w("FreeformAdjustImeController", "adjustConfig: failed, empty bounds, " + this.mDecoration);
            return;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        this.mTmpRect.set(this.mOriginBounds);
        this.mTmpRect.offset(0, this.mTargetYOffset);
        windowContainerTransaction.setBounds(windowContainerToken, this.mTmpRect);
        Rect rect = this.mTargetYOffset != 0 ? this.mTmpRect : null;
        if (rect == null) {
            this.mAdjustingBounds.setEmpty();
        } else {
            this.mAdjustingBounds.set(rect);
        }
        this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
    }

    public final void imePositionChanged(int i, SurfaceControl.Transaction transaction) {
        if (this.mOriginBounds.isEmpty()) {
            return;
        }
        this.mTmpRect.set(this.mOriginBounds);
        this.mTmpRect.offset(0, i);
        transaction.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
        SurfaceControl surfaceControl = this.mTaskSurface;
        Rect rect = this.mTmpRect;
        transaction.setPosition(surfaceControl, rect.left, rect.top);
        setAdjusted(i != 0);
    }

    public final void onImeEndPositioning(SurfaceControl.Transaction transaction, boolean z) {
        if (!z) {
            float f = this.mLastYOffset;
            int m$1 = (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(this.mTargetYOffset, f, 1.0f, f);
            this.mAnimating = false;
            this.mDecoration.mTaskPositioner.setImeAnimating(false);
            if (this.mLastYOffset == this.mTargetYOffset || m$1 == this.mYOffsetForIme) {
                return;
            }
            this.mYOffsetForIme = m$1;
            imePositionChanged(m$1, transaction);
            return;
        }
        Slog.w("FreeformAdjustImeController", "handleCancel: reset state, " + this.mDecoration);
        this.mTargetYOffset = 0;
        this.mLastYOffset = 0;
        this.mYOffsetForIme = 0;
        imePositionChanged(0, transaction);
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        if (runningTaskInfo != null) {
            adjustConfig(runningTaskInfo.token);
        }
        resetState();
        this.mAnimating = false;
        this.mDecoration.mTaskPositioner.setImeAnimating(false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0074, code lost:
    
        if (r5.mLastDisplayRotation == r2.windowConfiguration.getDisplayRotation()) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onImeStartPositioning(boolean r6, android.app.ActivityManager.RunningTaskInfo r7, boolean r8, int r9) {
        /*
            Method dump skipped, instructions count: 348
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.freeform.FreeformAdjustImeController.onImeStartPositioning(boolean, android.app.ActivityManager$RunningTaskInfo, boolean, int):void");
    }

    public final void resetState() {
        this.mOriginBounds.setEmpty();
        setAdjusted(false);
        this.mTargetYOffset = 0;
        this.mLastYOffset = 0;
        this.mYOffsetForIme = 0;
        this.mAdjustingBounds.setEmpty();
        this.mTaskInfo = null;
    }

    public final void setAdjusted(boolean z) {
        if (this.mIsAdjusted != z) {
            this.mIsAdjusted = z;
            StringBuilder m = RowView$$ExternalSyntheticOutline0.m("setAdjusted: ", ", ", z);
            m.append(this.mDecoration);
            Slog.d("FreeformAdjustImeController", m.toString());
        }
    }
}
