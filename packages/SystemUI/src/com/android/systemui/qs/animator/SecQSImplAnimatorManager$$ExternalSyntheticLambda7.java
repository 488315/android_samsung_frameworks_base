package com.android.systemui.qs.animator;

import java.util.function.Consumer;

public final /* synthetic */ class SecQSImplAnimatorManager$$ExternalSyntheticLambda7 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ float f$0;

    public /* synthetic */ SecQSImplAnimatorManager$$ExternalSyntheticLambda7(float f, int i) {
        this.$r8$classId = i;
        this.f$0 = f;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        float f = this.f$0;
        SecQSImplAnimatorBase secQSImplAnimatorBase = (SecQSImplAnimatorBase) obj;
        switch (i) {
            case 0:
                secQSImplAnimatorBase.setTransitionToFullShadeAmount(f);
                break;
            case 1:
                secQSImplAnimatorBase.setQsExpansionPosition(f);
                break;
            default:
                secQSImplAnimatorBase.setOverScrollAmount(f);
                break;
        }
    }
}
