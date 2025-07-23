package com.android.wm.shell.naturalswitching;

import com.android.wm.shell.splitscreen.SplitScreenController;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:61:0x00b5, code lost:
    
        if (r0 != 16) goto L89;
     */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0103  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void update(int r13, int r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.naturalswitching.NaturalSwitchingAlgorithm.update(int, int, boolean):void");
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
