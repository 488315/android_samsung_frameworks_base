package com.android.server.wm;

import android.app.ActivityOptions;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class PopOverState {
    public final ActivityRecord mActivityRecord;
    public boolean mIsActivated;
    public boolean mLastOccludesParent;
    public ActivityOptions mOptions;
    public ActivityOptions mOptionsInherited;
    public int mOriginTaskId = -1;
    public int mOriginTaskIdInherited = -1;

    public PopOverState(ActivityRecord activityRecord) {
        this.mActivityRecord = activityRecord;
    }

    public void setupOptions(ActivityOptions activityOptions, ActivityRecord activityRecord) {
        ActivityOptions activityOptions2;
        if (supportsPopOver(this.mActivityRecord.info)) {
            if (activityOptions.isPopOver()) {
                this.mOptions = new ActivityOptions(activityOptions.toBundle());
                if (activityRecord == null || activityRecord.getTask() == null) {
                    return;
                }
                this.mOriginTaskId = activityRecord.getTask().mTaskId;
                return;
            }
            if (!activityOptions.mPopOverInheritOptions || activityRecord == null || activityRecord.getTask() == null || activityRecord.getTask().mTaskId == -1 || (activityOptions2 = activityRecord.mPopOverState.mOptions) == null || !activityOptions2.mPopOverInheritOptions) {
                return;
            }
            this.mOptionsInherited = activityOptions2;
            this.mOriginTaskIdInherited = activityRecord.getTask().mTaskId;
        }
    }

    public void applyOptionsInherited() {
        if (this.mOptionsInherited == null || this.mActivityRecord.getTask() == null) {
            return;
        }
        int i = this.mActivityRecord.getTask().mTaskId;
        int i2 = this.mOriginTaskIdInherited;
        if (i != i2) {
            return;
        }
        this.mOptions = this.mOptionsInherited;
        this.mOptionsInherited = null;
        this.mOriginTaskId = i2;
        this.mOriginTaskIdInherited = -1;
    }

    public void adjustOptions(int[] iArr, int[] iArr2, Point[] pointArr, int[] iArr3) {
        if (!hasOptions() || this.mActivityRecord.getTask() == null) {
            return;
        }
        Rect bounds = PopOverBoundsCalculator.getBounds(this.mActivityRecord);
        if (iArr == null && iArr2 == null && pointArr == null && iArr3 == null) {
            clearOptions();
        } else {
            ActivityOptions activityOptions = this.mOptions;
            if (activityOptions == null) {
                activityOptions = this.mOptionsInherited;
            }
            if (iArr != null) {
                activityOptions.mPopOverWidthDp = (int[]) iArr.clone();
            }
            if (iArr2 != null) {
                activityOptions.mPopOverHeightDp = (int[]) iArr2.clone();
            }
            if (pointArr != null) {
                activityOptions.mPopOverAnchorMarginDp = (Point[]) pointArr.clone();
            }
            if (iArr3 != null) {
                activityOptions.mPopOverAnchorPosition.equals(iArr3.clone());
                activityOptions.mPopOverAnchorPosition = (int[]) iArr3.clone();
            }
        }
        this.mActivityRecord.recomputeConfiguration();
        this.mActivityRecord.ensureActivityConfiguration(0, true, true);
        Rect bounds2 = PopOverBoundsCalculator.getBounds(this.mActivityRecord);
        if (bounds.width() == bounds2.width() && bounds.height() == bounds2.height()) {
            return;
        }
        ActivityRecord activityRecord = this.mActivityRecord;
        activityRecord.mAtmService.mChangeTransitController.handlePopOverChangeTransitionRequest(activityRecord, bounds);
    }

    public boolean hasOptions() {
        return (this.mOptions == null && this.mOptionsInherited == null) ? false : true;
    }

    public ActivityOptions getOptions() {
        return this.mOptions;
    }

    public void clearOptions() {
        this.mOptions = null;
        this.mOptionsInherited = null;
        this.mOriginTaskId = -1;
        this.mOriginTaskIdInherited = -1;
    }

    public void setLastOccludesParent(boolean z) {
        this.mLastOccludesParent = z;
    }

    public void toggle() {
        if (this.mActivityRecord.isEmbedded()) {
            inactivate();
        } else if (isInLargeSizeTask()) {
            activate();
        } else {
            inactivate();
        }
    }

    public boolean isInLargeSizeTask() {
        return this.mActivityRecord.getTask() != null && this.mActivityRecord.getTask().getConfiguration().smallestScreenWidthDp >= 600;
    }

    public final void activate() {
        if (this.mIsActivated || this.mOptions == null || this.mActivityRecord.getTask() == null || this.mActivityRecord.getTask().mTaskId != this.mOriginTaskId || this.mActivityRecord.isRootOfTask()) {
            return;
        }
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord.mReparenting) {
            return;
        }
        this.mIsActivated = true;
        activityRecord.mOccludesParent = false;
        if (CoreRune.FW_SUPPORT_OCCLUDES_PARENT_CHANGE_CALLBACK) {
            updateOccludeTargetIfNeeded();
        }
        if (!this.mActivityRecord.getTask().getLastDispatchedWindowFocusInTask()) {
            this.mActivityRecord.getTask().updateWindowFocusInTask();
        }
        if (!this.mActivityRecord.getDisplayContent().mWaitingForConfig && this.mActivityRecord.isVisible() && this.mActivityRecord.getTask().shouldBeVisible(null)) {
            if (this.mActivityRecord.getTask().inSplitScreenWindowingMode()) {
                this.mActivityRecord.getParent().getParent().asTask().mPendingEnsureVisibleForPopOver = true;
            } else if (this.mActivityRecord.getTask().inFreeformWindowingMode()) {
                this.mActivityRecord.getTask().mPendingEnsureVisibleForPopOver = true;
            }
        }
    }

    public final void inactivate() {
        if (this.mIsActivated) {
            this.mIsActivated = false;
            this.mActivityRecord.mOccludesParent = this.mLastOccludesParent;
            if (CoreRune.FW_SUPPORT_OCCLUDES_PARENT_CHANGE_CALLBACK) {
                updateOccludeTargetIfNeeded();
            }
            this.mActivityRecord.forAllWindows(new Consumer() { // from class: com.android.server.wm.PopOverState$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((WindowState) obj).clearPopOverDimmer();
                }
            }, true);
        }
    }

    public boolean isActivated() {
        return this.mIsActivated;
    }

    public int getState() {
        if (isActivated()) {
            return shouldRemoveOutlineEffect() ? 3 : 1;
        }
        return 2;
    }

    public boolean shouldRemoveOutlineEffect() {
        return !this.mLastOccludesParent && isAboveAnotherOpaquePopOver();
    }

    public boolean isAboveAnotherOpaquePopOver() {
        return (this.mActivityRecord.getTask() == null || this.mActivityRecord.getTask().getActivity(new Predicate() { // from class: com.android.server.wm.PopOverState$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$isAboveAnotherOpaquePopOver$1;
                lambda$isAboveAnotherOpaquePopOver$1 = PopOverState.this.lambda$isAboveAnotherOpaquePopOver$1((ActivityRecord) obj);
                return lambda$isAboveAnotherOpaquePopOver$1;
            }
        }, this.mActivityRecord, false, true) == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$isAboveAnotherOpaquePopOver$1(ActivityRecord activityRecord) {
        if (activityRecord.mPopOverState.isActivated() && !activityRecord.finishing) {
            PopOverState popOverState = activityRecord.mPopOverState;
            if (popOverState.mOptions == this.mOptions && popOverState.mLastOccludesParent) {
                return true;
            }
        }
        return false;
    }

    public boolean isBelowAnotherOpaquePopOver() {
        final Rect bounds = this.mActivityRecord.getBounds();
        return (this.mActivityRecord.getTask() == null || this.mActivityRecord.getTask().getActivity(new Predicate() { // from class: com.android.server.wm.PopOverState$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$isBelowAnotherOpaquePopOver$2;
                lambda$isBelowAnotherOpaquePopOver$2 = PopOverState.lambda$isBelowAnotherOpaquePopOver$2(bounds, (ActivityRecord) obj);
                return lambda$isBelowAnotherOpaquePopOver$2;
            }
        }, this.mActivityRecord, false, false) == null) ? false : true;
    }

    public static /* synthetic */ boolean lambda$isBelowAnotherOpaquePopOver$2(Rect rect, ActivityRecord activityRecord) {
        return activityRecord.mPopOverState.isActivated() && !activityRecord.finishing && activityRecord.getBounds().contains(rect) && activityRecord.mPopOverState.mLastOccludesParent;
    }

    public final void updateOccludeTargetIfNeeded() {
        if (this.mLastOccludesParent) {
            ActivityRecord activityRecord = this.mActivityRecord;
            TransitionController transitionController = activityRecord.mDisplayContent.mTransitionController;
            activityRecord.mWmService.mExt.updateOccludeTargetIfNeeded(activityRecord.getDisplayContent());
        }
    }

    public void dump(PrintWriter printWriter, String str) {
        if (this.mOptions == null) {
            return;
        }
        printWriter.println(str + "PopOver=" + this.mIsActivated);
        printWriter.println(str + " size=land(" + this.mOptions.mPopOverWidthDp[0] + "," + this.mOptions.mPopOverHeightDp[0] + ")/port(" + this.mOptions.mPopOverWidthDp[1] + "," + this.mOptions.mPopOverHeightDp[1] + ")");
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" margin=land(");
        sb.append(this.mOptions.mPopOverAnchorMarginDp[0]);
        sb.append(")/port(");
        sb.append(this.mOptions.mPopOverAnchorMarginDp[1]);
        sb.append(")");
        printWriter.println(sb.toString());
        printWriter.println(str + " position=land(0x" + Integer.toHexString(this.mOptions.mPopOverAnchorPosition[0]) + ")/port(0x" + Integer.toHexString(this.mOptions.mPopOverAnchorPosition[1]) + ")");
        if (this.mIsActivated) {
            printWriter.println(str + " inherit=" + this.mOptions.mPopOverInheritOptions + ", mLastOccludesParent=" + this.mLastOccludesParent + ", isBelowAnotherOpaquePopOver=" + isBelowAnotherOpaquePopOver() + ", isAboveAnotherOpaquePopOver=" + isAboveAnotherOpaquePopOver());
        }
    }

    public static boolean supportsPopOver(ActivityInfo activityInfo) {
        Bundle bundle = activityInfo.metaData;
        if (bundle != null && bundle.containsKey("com.samsung.android.supports_pop_over")) {
            return activityInfo.metaData.getBoolean("com.samsung.android.supports_pop_over", true);
        }
        Bundle bundle2 = activityInfo.applicationInfo.metaData;
        if (bundle2 == null || !bundle2.containsKey("com.samsung.android.supports_pop_over")) {
            return true;
        }
        return activityInfo.applicationInfo.metaData.getBoolean("com.samsung.android.supports_pop_over", true);
    }
}
