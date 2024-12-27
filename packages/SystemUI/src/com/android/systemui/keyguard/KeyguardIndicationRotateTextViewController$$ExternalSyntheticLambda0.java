package com.android.systemui.keyguard;

import java.util.function.Predicate;

public final /* synthetic */ class KeyguardIndicationRotateTextViewController$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ KeyguardIndicationRotateTextViewController$$ExternalSyntheticLambda0(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        Integer num = (Integer) obj;
        switch (i) {
            case 0:
                if (num.intValue() == i2) {
                }
                break;
            case 1:
                if (num.intValue() == i2) {
                }
                break;
            default:
                if (num.intValue() == i2) {
                }
                break;
        }
        return false;
    }
}
