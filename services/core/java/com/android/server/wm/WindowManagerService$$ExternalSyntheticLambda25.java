package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class WindowManagerService$$ExternalSyntheticLambda25
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WindowManagerService$$ExternalSyntheticLambda25(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Task task = (Task) obj;
                int i = WindowManagerService.MY_PID;
                return task.isActivityTypeHomeOrRecents()
                        && task.getTopVisibleActivity(true, false) != null;
            default:
                return ((ActivityRecord) obj).hasStartingWindow();
        }
    }
}
