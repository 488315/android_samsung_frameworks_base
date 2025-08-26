package com.android.wm.shell.freeform;

import android.app.ActivityManager;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.Slog;
import android.view.Choreographer;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecoration;

/* loaded from: classes3.dex */
public class FreeformAdjustImeController {
    public boolean mAnimating;
    public final DesktopModeWindowDecorViewModel.DecorViewModelState mDecorViewModelState;
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

    public FreeformAdjustImeController(DesktopModeWindowDecoration desktopModeWindowDecoration, DisplayController displayController, ShellTaskOrganizer shellTaskOrganizer, SurfaceControl surfaceControl, DesktopModeWindowDecorViewModel.DecorViewModelState decorViewModelState) {
        this.mDecoration = desktopModeWindowDecoration;
        this.mDisplayController = displayController;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mTaskSurface = surfaceControl;
        this.mDecorViewModelState = decorViewModelState;
    }

    public final void adjustConfig(WindowContainerToken windowContainerToken) {
        boolean zIsEmpty = this.mOriginBounds.isEmpty();
        DesktopModeWindowDecoration desktopModeWindowDecoration = this.mDecoration;
        if (zIsEmpty) {
            Slog.w("FreeformAdjustImeController", "adjustConfig: failed, empty bounds, " + desktopModeWindowDecoration);
        } else {
            if (desktopModeWindowDecoration.mIsTaskResizing) {
                Slog.d("FreeformAdjustImeController", "adjustConfig: skip by resizing task");
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
    }

    public final void imePositionChanged(int i, SurfaceControl.Transaction transaction) {
        if (this.mOriginBounds.isEmpty()) {
            return;
        }
        this.mTmpRect.set(this.mOriginBounds);
        this.mTmpRect.offset(0, i);
        DesktopModeWindowDecorViewModel.DecorViewModelState decorViewModelState = this.mDecorViewModelState;
        if (decorViewModelState == null || !decorViewModelState.mInTransition) {
            transaction.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
            SurfaceControl surfaceControl = this.mTaskSurface;
            Rect rect = this.mTmpRect;
            transaction.setPosition(surfaceControl, rect.left, rect.top);
        }
        setAdjusted(i != 0);
    }

    public final void onImeEndPositioning(SurfaceControl.Transaction transaction, boolean z) {
        if (!z) {
            float f = this.mLastYOffset;
            int iM$1 = (int) DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(this.mTargetYOffset, f, 1.0f, f);
            this.mAnimating = false;
            this.mDecoration.mTaskPositioner.setImeAnimating(false);
            if (this.mLastYOffset == this.mTargetYOffset || iM$1 == this.mYOffsetForIme) {
                return;
            }
            this.mYOffsetForIme = iM$1;
            imePositionChanged(iM$1, transaction);
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

    /* JADX WARN: Removed duplicated region for block: B:29:0x0076  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onImeStartPositioning(boolean z, ActivityManager.RunningTaskInfo runningTaskInfo, boolean z2, int i) {
        Rect rect = new Rect();
        int i2 = 0;
        if (!z && !z2 && !this.mIsAdjusted) {
            this.mTargetYOffset = 0;
            this.mLastYOffset = 0;
            this.mYOffsetForIme = 0;
        }
        if (z) {
            DesktopModeWindowDecoration desktopModeWindowDecoration = this.mDecoration;
            desktopModeWindowDecoration.mTaskPositioner.getImeStartBounds(rect);
            if (rect.isEmpty()) {
                rect.set(desktopModeWindowDecoration.mTaskInfo.getConfiguration().windowConfiguration.getBounds());
            }
            if (rect.isEmpty()) {
                if (this.mOriginBounds.isEmpty()) {
                    rect.set(runningTaskInfo.getConfiguration().windowConfiguration.getBounds());
                } else {
                    rect.set(this.mOriginBounds);
                }
            }
        } else if (!this.mIsAdjusted) {
            return;
        }
        if (rect.isEmpty()) {
            if (this.mYOffsetForIme != 0) {
                int i3 = this.mLastOrientation;
                Configuration configuration = runningTaskInfo.configuration;
                if (i3 != configuration.orientation || this.mLastDisplayRotation != configuration.windowConfiguration.getDisplayRotation()) {
                    Configuration configuration2 = runningTaskInfo.configuration;
                    this.mLastOrientation = configuration2.orientation;
                    this.mLastDisplayRotation = configuration2.windowConfiguration.getDisplayRotation();
                    rect.set(runningTaskInfo.getConfiguration().windowConfiguration.getBounds());
                    this.mStartBounds.set(rect);
                }
            }
        } else if (!this.mIsAdjusted) {
            this.mStartBounds.set(rect);
        }
        this.mLastYOffset = this.mYOffsetForIme;
        if (z) {
            int i4 = runningTaskInfo.displayId;
            DisplayController displayController = this.mDisplayController;
            InsetsState insetsState = displayController.getInsetsState(i4);
            DisplayLayout displayLayout = displayController.getDisplayLayout(i4);
            if (insetsState == null || displayLayout == null) {
                Slog.e("FreeformAdjustImeController", "getTargetYOffset: insetsState=" + insetsState + " displayLayout=" + displayLayout);
            } else {
                int i5 = this.mStartBounds.bottom - i;
                InsetsSource insetsSourcePeekSource = insetsState.peekSource(InsetsSource.ID_IME);
                boolean z3 = insetsSourcePeekSource != null && insetsSourcePeekSource.getFrame().height() == 0;
                if (i5 > 0 && !z3) {
                    displayLayout.getStableBounds(this.mDisplayFrame, false);
                    Rect rect2 = this.mDisplayFrame;
                    rect2.inset(insetsState.calculateInsets(rect2, WindowInsets.Type.systemBars() | WindowInsets.Type.ime() | WindowInsets.Type.displayCutout(), true));
                    i2 = -Math.min(i5, Math.max(0, this.mStartBounds.top - this.mDisplayFrame.top));
                }
            }
        }
        this.mTargetYOffset = i2;
        this.mAnimating = true;
        this.mDecoration.mTaskPositioner.setImeAnimating(true);
        if (this.mLastYOffset == this.mTargetYOffset) {
            return;
        }
        if (this.mIsAdjusted ? this.mAdjustingBounds.left != this.mStartBounds.left : !(this.mAdjustingBounds.isEmpty() && this.mTargetYOffset >= 0)) {
            this.mOriginBounds.set(rect);
        }
        adjustConfig(runningTaskInfo.getToken());
        this.mTaskInfo = runningTaskInfo;
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

    public final void resetStateIfNeeded(String str) {
        if (this.mIsAdjusted) {
            Slog.d("FreeformAdjustImeController", "resetStateIfNeeded, origin=" + this.mOriginBounds + ", reason=" + str);
            resetState();
        }
    }

    public final void setAdjusted(boolean z) {
        if (this.mIsAdjusted != z) {
            this.mIsAdjusted = z;
            StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("setAdjusted: ", ", ", z);
            sbM.append(this.mDecoration);
            Slog.d("FreeformAdjustImeController", sbM.toString());
        }
    }
}
