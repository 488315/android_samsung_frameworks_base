package com.android.wm.shell.naturalswitching;

import android.app.ActivityManager;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public class NaturalSwitchingAlgorithm {
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

    public final int getDefaultFloatingWindowingMode() {
        if (CoreRune.MW_PARALLEL_MULTI_SPLIT && this.mSplitScreenController.isParallelMultiSplit()) {
            return 0;
        }
        return (CoreRune.MW_NATURAL_SWITCHING_PIP && this.mDragTargetWindowingMode == 2) ? 2 : 5;
    }

    /* JADX WARN: Removed duplicated region for block: B:65:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00d4  */
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
        int i4 = -1;
        if (i == 1) {
            this.mToWindowingMode = getDefaultFloatingWindowingMode();
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
                boolean zIsVerticalDivision = this.mSplitScreenController.isVerticalDivision();
                if (zIsVerticalDivision) {
                    this.mToPosition = this.mDropSide == 2 ? 8 : 32;
                } else {
                    this.mToPosition = this.mDropSide == 4 ? 16 : 64;
                }
                this.mSplitCreateMode = zIsVerticalDivision ? 2 : 3;
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
            if (i == 2) {
                i4 = 2;
            } else if (i == 4) {
                i4 = 3;
            } else if (i == 8) {
                i4 = 4;
            } else if (i == 16) {
                i4 = 5;
            }
            this.mSplitCreateMode = i4;
            return;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.mTaskVisibility.mRunningTaskInfo.get(i2);
        if (runningTaskInfo != null) {
            int stagePosition = runningTaskInfo.configuration.windowConfiguration.getStagePosition();
            boolean zIsVerticalDivision2 = this.mSplitScreenController.isVerticalDivision();
            this.mToPosition = 0;
            int i5 = this.mDropSide;
            if (i5 == 2) {
                int i6 = i5 == 2 ? 8 : 32;
                this.mToPosition = i6;
                if ((stagePosition & 16) != 0) {
                    this.mToPosition = i6 | 16;
                    this.mSplitCreateMode = zIsVerticalDivision2 ? 2 : 3;
                } else {
                    this.mToPosition = i6 | 64;
                    this.mSplitCreateMode = zIsVerticalDivision2 ? 2 : 5;
                }
            } else if (i5 == 4) {
                int i7 = i5 == 4 ? 16 : 64;
                this.mToPosition = i7;
                if ((stagePosition & 8) != 0) {
                    this.mToPosition = i7 | 8;
                    this.mSplitCreateMode = zIsVerticalDivision2 ? 2 : 5;
                } else {
                    this.mToPosition = i7 | 32;
                    this.mSplitCreateMode = zIsVerticalDivision2 ? 4 : 5;
                }
            } else if (i5 != 8) {
                if (i5 == 16) {
                }
            }
            if (NaturalSwitchingLayout.isFloating(this.mDragTargetWindowingMode)) {
                if (i2 == 13) {
                    this.mToWindowingMode = 13;
                    return;
                } else {
                    this.mToWindowingMode = 12;
                    return;
                }
            }
            int i8 = this.mDragTargetWindowingMode;
            this.mToWindowingMode = i8;
            if (i8 == 12 || i2 == 12) {
                return;
            }
            this.mNeedToReparentCell = true;
        }
    }

    public final void updateForPush(int i) {
        if (this.mPushRegion != i) {
            this.mNeedToReparentCell = false;
            this.mPushRegion = i;
            int i2 = -1;
            if (i == 0) {
                this.mToWindowingMode = getDefaultFloatingWindowingMode();
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
            } else if (i == 2) {
                i2 = 5;
            } else if (i == 3) {
                i2 = 2;
            } else if (i == 4) {
                i2 = 3;
            }
            this.mSplitCreateMode = i2;
            this.mSwapWindowingMode = 0;
            this.mShrunkWindowingMode = 0;
        }
    }
}
