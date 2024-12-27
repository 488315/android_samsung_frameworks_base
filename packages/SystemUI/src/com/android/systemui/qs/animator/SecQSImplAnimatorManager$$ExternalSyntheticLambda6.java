package com.android.systemui.qs.animator;

import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                secQSImplAnimatorBase.updateAnimators();
                break;
            case 2:
                secQSImplAnimatorBase.onPanelOpened();
                break;
            case 3:
                secQSImplAnimatorBase.onPanelClosed();
                break;
            default:
                secQSImplAnimatorBase.onRtlChanged();
                break;
        }
    }
}
