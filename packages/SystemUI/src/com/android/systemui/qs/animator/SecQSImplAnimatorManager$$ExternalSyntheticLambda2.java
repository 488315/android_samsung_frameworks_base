package com.android.systemui.qs.animator;

import java.util.function.Consumer;

public final /* synthetic */ class SecQSImplAnimatorManager$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ SecQSImplAnimatorManager$$ExternalSyntheticLambda2() {
        this.$r8$classId = 1;
        this.f$0 = -2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        SecQSImplAnimatorBase secQSImplAnimatorBase = (SecQSImplAnimatorBase) obj;
        switch (i) {
            case 0:
                secQSImplAnimatorBase.onNotificationScrolled(i2);
                break;
            case 1:
                secQSImplAnimatorBase.onUserSwitched(i2);
                break;
            default:
                secQSImplAnimatorBase.onStateChanged(i2);
                break;
        }
    }

    public /* synthetic */ SecQSImplAnimatorManager$$ExternalSyntheticLambda2(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }
}
