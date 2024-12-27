package com.android.systemui.qs.animator;

import com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class SecQSImplAnimatorManager$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ SecQSImplAnimatorManager$$ExternalSyntheticLambda5() {
        this.$r8$classId = 6;
        this.f$0 = false;
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
            case 2:
                secQSImplAnimatorBase.updatePanelExpanded(z);
                break;
            case 3:
                secQSImplAnimatorBase.getClass();
                QsAnimatorState.INSTANCE.getClass();
                ((SecQSExpansionStateInteractor) QsAnimatorState.qsExpansionStateInteractor$delegate.getValue()).getRepository()._isDetailOpening.updateState(null, Boolean.valueOf(z));
                QsAnimatorState.isDetailOpening = z;
                break;
            case 4:
                secQSImplAnimatorBase.getClass();
                QsAnimatorState.INSTANCE.getClass();
                ((SecQSExpansionStateInteractor) QsAnimatorState.qsExpansionStateInteractor$delegate.getValue()).getRepository()._isDetailShowing.updateState(null, Boolean.valueOf(z));
                QsAnimatorState.isDetailShowing = z;
                break;
            case 5:
                secQSImplAnimatorBase.getClass();
                QsAnimatorState.INSTANCE.getClass();
                ((SecQSExpansionStateInteractor) QsAnimatorState.qsExpansionStateInteractor$delegate.getValue()).getRepository()._isDetailClosing.updateState(null, Boolean.valueOf(z));
                QsAnimatorState.isDetailClosing = z;
                break;
            default:
                secQSImplAnimatorBase.getClass();
                QsAnimatorState.detailTriggeredExpand = z;
                break;
        }
    }

    public /* synthetic */ SecQSImplAnimatorManager$$ExternalSyntheticLambda5(boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = z;
    }
}
