package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class WindowManagerService$$ExternalSyntheticLambda1
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ WindowManagerService$$ExternalSyntheticLambda1(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        switch (i) {
            case 0:
                int i3 = WindowManagerService.MY_PID;
                if (((Task) obj).mTaskId == i2) {
                    break;
                }
                break;
            default:
                int i4 = WindowManagerService.MY_PID;
                if (System.identityHashCode((WindowState) obj) == i2) {
                    break;
                }
                break;
        }
        return true;
    }
}
