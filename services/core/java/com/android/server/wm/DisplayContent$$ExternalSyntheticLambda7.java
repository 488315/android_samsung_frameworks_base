package com.android.server.wm;

import android.view.WindowManager;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda7 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DisplayContent$$ExternalSyntheticLambda7(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int overrideOrientation;
        switch (this.$r8$classId) {
            case 0:
                WindowManager.LayoutParams layoutParams = ((WindowState) obj).mAttrs;
                return ((layoutParams.privateFlags & Integer.MIN_VALUE) == 0 || (layoutParams.multiWindowFlags & 16) == 0) ? false : true;
            case 1:
                Task task = (Task) obj;
                return task.inFreeformWindowingMode() && task.shouldBeVisible(null);
            case 2:
                Task task2 = (Task) obj;
                return !task2.isActivityTypeHome() || task2.hasChild();
            case 3:
                WindowState windowState = (WindowState) obj;
                return windowState.isOnScreen() && windowState.isSecureLocked();
            case 4:
                ActivityRecord activityRecord = (ActivityRecord) obj;
                return (activityRecord.finishing || (overrideOrientation = activityRecord.getOverrideOrientation()) == -2 || overrideOrientation == 3) ? false : true;
            case 5:
                WindowState windowState2 = (WindowState) obj;
                return windowState2.isVisible() && (windowState2.mAttrs.samsungFlags & 67108864) != 0;
            case 6:
                WindowState windowState3 = (WindowState) obj;
                return windowState3.isVisible() && windowState3.isSecureLocked();
            default:
                return ((ActivityRecord) obj).inTransitionSelfOrParent();
        }
    }
}
