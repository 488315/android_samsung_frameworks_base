package com.android.systemui.navigationbar.views;

import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda3 implements Predicate {
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
