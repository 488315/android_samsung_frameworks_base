package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda5 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DisplayContent$$ExternalSyntheticLambda5(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return obj == ((WindowState) obj2);
            case 1:
                ((DisplayContent) obj2).getClass();
                return ((WindowState) obj).canBeImeTarget();
            default:
                return DisplayContent.this.isFixedRotationLaunchingApp((ActivityRecord) obj);
        }
    }
}
