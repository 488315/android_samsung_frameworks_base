package com.android.systemui.qs.animator;

import java.util.function.Consumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class SecQSImplAnimatorManager$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ SecQSImplAnimatorManager$$ExternalSyntheticLambda5(boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        boolean z = this.f$0;
        SecQSImplAnimatorBase secQSImplAnimatorBase = (SecQSImplAnimatorBase) obj;
        switch (i) {
            case 0:
                secQSImplAnimatorBase.setStackScrollerOverscrolling(z);
                break;
            case 1:
                secQSImplAnimatorBase.setQsExpanded(z);
                break;
            default:
                secQSImplAnimatorBase.updatePanelExpanded(z);
                break;
        }
    }
}
