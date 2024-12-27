package com.android.server.wm;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ChangeTransitionController$$ExternalSyntheticLambda4
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ChangeTransitionController$$ExternalSyntheticLambda4(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ActivityRecord activityRecord = (ActivityRecord) obj;
                if (activityRecord.noDisplay || !activityRecord.mVisible) {}
                break;
            case 1:
                Task task = (Task) obj;
                if (!task.isVisible() || !task.isLeafTask()) {}
                break;
            case 2:
                WindowState windowState = (WindowState) obj;
                if (!windowState.isVisible() || !windowState.mAttrs.isFullscreen()) {}
                break;
            default:
                WindowState windowState2 = (WindowState) obj;
                if (!windowState2.mIsChildWindow
                        || !windowState2.isOnScreen()
                        || !windowState2.mAnimatingExit) {}
                break;
        }
        return false;
    }
}
