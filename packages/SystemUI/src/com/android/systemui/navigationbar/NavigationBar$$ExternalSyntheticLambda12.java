package com.android.systemui.navigationbar;

import java.util.function.Predicate;

public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda12 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                if (((Long) obj).longValue() != 0) {
                }
                break;
            case 1:
                if (((Long) obj).longValue() > 0) {
                }
                break;
            case 2:
                if (((Float) obj).floatValue() > 0.0f) {
                }
                break;
            default:
                if (((Long) obj).longValue() != 0) {
                }
                break;
        }
        return false;
    }
}
