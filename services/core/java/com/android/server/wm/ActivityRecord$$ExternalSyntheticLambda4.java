package com.android.server.wm;

import android.view.WindowManager;

import java.util.function.Predicate;

public final /* synthetic */ class ActivityRecord$$ExternalSyntheticLambda4 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ActivityRecord$$ExternalSyntheticLambda4(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int overrideOrientation;
        switch (this.$r8$classId) {
            case 0:
                return WindowManager.LayoutParams.mayUseInputMethod(
                        ((WindowState) obj).mAttrs.flags);
            case 1:
                ActivityRecord activityRecord = (ActivityRecord) obj;
                return activityRecord.isVisibleRequested() && !activityRecord.firstWindowDrawn;
            case 2:
                return ((WindowState) obj).mAttrs.type == 3;
            case 3:
                Task task = (Task) obj;
                return task.isLeafTask() && task.isFocusable() && !task.inPinnedWindowingMode();
            case 4:
                ActivityRecord activityRecord2 = (ActivityRecord) obj;
                return (activityRecord2.finishing
                                || (overrideOrientation = activityRecord2.getOverrideOrientation())
                                        == -2
                                || overrideOrientation == 3)
                        ? false
                        : true;
            case 5:
                return ((ActivityRecord) obj).attachedToProcess();
            default:
                return ((TaskFragment) obj).mIsEmbedded;
        }
    }
}
