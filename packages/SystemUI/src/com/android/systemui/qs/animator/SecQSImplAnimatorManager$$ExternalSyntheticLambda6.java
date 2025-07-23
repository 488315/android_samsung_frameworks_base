package com.android.systemui.qs.animator;

import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecQSImplAnimatorManager$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SecQSImplAnimatorManager$$ExternalSyntheticLambda6(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SecQSImplAnimatorBase secQSImplAnimatorBase = (SecQSImplAnimatorBase) obj;
        switch (this.$r8$classId) {
            case 0:
                secQSImplAnimatorBase.destroyQSViews();
                break;
            case 1:
                secQSImplAnimatorBase.onUserSwitched(-2);
                break;
            case 2:
                secQSImplAnimatorBase.updateAnimators();
                break;
            case 3:
                secQSImplAnimatorBase.onPanelOpened();
                break;
            case 4:
                secQSImplAnimatorBase.onPanelClosed$1();
                break;
            case 5:
                secQSImplAnimatorBase.onRtlChanged();
                break;
            default:
                secQSImplAnimatorBase.getClass();
                QsAnimatorState qsAnimatorState = QsAnimatorState.INSTANCE;
                break;
        }
    }
}
