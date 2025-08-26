package com.android.systemui.qs.animator;

import java.util.function.Consumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class SecQSImplAnimatorManager$$ExternalSyntheticLambda10 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ float f$0;

    public /* synthetic */ SecQSImplAnimatorManager$$ExternalSyntheticLambda10(float f, int i) {
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
                secQSImplAnimatorBase.setQsExpansionPosition(f);
                break;
            case 1:
                secQSImplAnimatorBase.setOverScrollAmount(f);
                break;
            case 2:
                secQSImplAnimatorBase.setOverDragAmount(f);
                break;
            default:
                secQSImplAnimatorBase.setTransitionToFullShadeAmount(f);
                break;
        }
    }
}
