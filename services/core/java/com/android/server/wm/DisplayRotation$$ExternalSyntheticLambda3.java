package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class DisplayRotation$$ExternalSyntheticLambda3 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DisplayRotation$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((WindowState) obj).mSeamlesslyRotated;
            default:
                return ((Task) obj).getWindowingMode() == 1;
        }
    }
}
