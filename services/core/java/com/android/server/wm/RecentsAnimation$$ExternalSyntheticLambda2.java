package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class RecentsAnimation$$ExternalSyntheticLambda2 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ RecentsAnimation$$ExternalSyntheticLambda2(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((ActivityRecord) obj).occludesParent(false);
            default:
                return !((Task) obj).getWindowConfiguration().isAlwaysOnTop();
        }
    }
}
