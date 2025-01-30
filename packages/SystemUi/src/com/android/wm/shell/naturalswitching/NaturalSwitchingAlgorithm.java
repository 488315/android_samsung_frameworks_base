package com.android.wm.shell.naturalswitching;

import android.app.ActivityManager;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NaturalSwitchingAlgorithm {
    public int mDragTargetWindowingMode;
    public boolean mNeedToReparentCell;
    public int mPushRegion;
    public int mShrunkWindowingMode;
    public SplitScreenController mSplitScreenController;
    public int mSwapWindowingMode;
    public TaskVisibility mTaskVisibility;
    public int mToPosition;
    public boolean mUseSingleNonTarget;
    public int mDropSide = 0;
    public int mToWindowingMode = 0;
    public int mHalfTarget = 0;
    public int mSplitCreateMode = -1;

    /* JADX WARN: Code restructure failed: missing block: B:66:0x00bc, code lost:
    
        if (r3 != 16) goto L102;
     */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0111  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void update(int i, int i2, boolean z) {
        int i3 = 0;
        updateForPush(0);
        if (this.mDropSide == i && this.mShrunkWindowingMode == i2) {
            return;
        }
        this.mNeedToReparentCell = false;
        this.mDropSide = i;
        this.mShrunkWindowingMode = i2;
        if (i == 1) {
            this.mToWindowingMode = (CoreRune.MW_NATURAL_SWITCHING_PIP && this.mDragTargetWindowingMode == 2) ? 2 : 5;
            this.mSplitCreateMode = -1;
            return;
        }
        if (this.mUseSingleNonTarget) {
            if (this.mTaskVisibility.mSupportOnlyTwoUpMode) {
                if (NaturalSwitchingLayout.isFloating(this.mDragTargetWindowingMode)) {
                    this.mToWindowingMode = 3;
                } else {
                    this.mToWindowingMode = this.mDragTargetWindowingMode;
                }
                boolean isVerticalDivision = this.mSplitScreenController.isVerticalDivision();
                if (isVerticalDivision) {
                    this.mToPosition = this.mDropSide != 2 ? 32 : 8;
                } else {
                    this.mToPosition = this.mDropSide != 4 ? 64 : 16;
                }
                this.mSplitCreateMode = isVerticalDivision ? 2 : 3;
                return;
            }
            if (z) {
                this.mToWindowingMode = 3;
            } else {
                this.mToWindowingMode = this.mDragTargetWindowingMode;
            }
            if (i == 2) {
                i3 = 8;
            } else if (i == 4) {
                i3 = 16;
            } else if (i == 8) {
                i3 = 32;
            } else if (i == 16) {
                i3 = 64;
            }
            this.mToPosition = i3;
            this.mSplitCreateMode = i != 2 ? i != 4 ? i != 8 ? i != 16 ? -1 : 5 : 4 : 3 : 2;
            return;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.mTaskVisibility.mRunningTaskInfo.get(i2);
        if (runningTaskInfo == null) {
            return;
        }
        int stagePosition = runningTaskInfo.configuration.windowConfiguration.getStagePosition();
        boolean isVerticalDivision2 = this.mSplitScreenController.isVerticalDivision();
        this.mToPosition = 0;
        int i4 = this.mDropSide;
        if (i4 != 2) {
            if (i4 != 4) {
                if (i4 != 8) {
                }
            }
            int i5 = 0 | (i4 != 4 ? 64 : 16);
            this.mToPosition = i5;
            if ((stagePosition & 8) != 0) {
                this.mToPosition = i5 | 8;
                this.mSplitCreateMode = isVerticalDivision2 ? 2 : 5;
            } else {
                this.mToPosition = i5 | 32;
                this.mSplitCreateMode = isVerticalDivision2 ? 4 : 5;
            }
            if (!NaturalSwitchingLayout.isFloating(this.mDragTargetWindowingMode)) {
                if (i2 == 13) {
                    this.mToWindowingMode = 13;
                    return;
                } else {
                    this.mToWindowingMode = 12;
                    return;
                }
            }
            int i6 = this.mDragTargetWindowingMode;
            this.mToWindowingMode = i6;
            if (i6 == 12 || i2 == 12) {
                return;
            }
            this.mNeedToReparentCell = true;
            return;
        }
        int i7 = 0 | (i4 != 2 ? 32 : 8);
        this.mToPosition = i7;
        if ((stagePosition & 16) != 0) {
            this.mToPosition = i7 | 16;
            this.mSplitCreateMode = isVerticalDivision2 ? 2 : 3;
        } else {
            this.mToPosition = i7 | 64;
            this.mSplitCreateMode = isVerticalDivision2 ? 2 : 5;
        }
        if (!NaturalSwitchingLayout.isFloating(this.mDragTargetWindowingMode)) {
        }
    }

    public final void updateForPush(int i) {
        if (this.mPushRegion != i) {
            this.mNeedToReparentCell = false;
            this.mPushRegion = i;
            int i2 = 5;
            if (i == 0) {
                if (CoreRune.MW_NATURAL_SWITCHING_PIP && this.mDragTargetWindowingMode == 2) {
                    i2 = 2;
                }
                this.mToWindowingMode = i2;
                this.mSplitCreateMode = -1;
                return;
            }
            this.mToPosition = i != 1 ? i != 2 ? i != 3 ? i != 4 ? 0 : 64 : 32 : 16 : 8;
            this.mToWindowingMode = 3;
            int i3 = this.mDragTargetWindowingMode;
            if (i3 == 12 || NaturalSwitchingLayout.isFloating(i3)) {
                this.mNeedToReparentCell = true;
            }
            if (i == 1) {
                i2 = 4;
            } else if (i != 2) {
                i2 = i != 3 ? i != 4 ? -1 : 3 : 2;
            }
            this.mSplitCreateMode = i2;
            this.mSwapWindowingMode = 0;
            this.mShrunkWindowingMode = 0;
        }
    }
}
