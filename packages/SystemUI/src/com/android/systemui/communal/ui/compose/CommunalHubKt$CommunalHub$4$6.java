package com.android.systemui.communal.ui.compose;

import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class CommunalHubKt$CommunalHub$4$6 extends FunctionReferenceImpl implements Function0 {
    public CommunalHubKt$CommunalHub$4$6(Object obj) {
        super(0, obj, CommunalViewModel.class, "onEnableWidgetDialogCancel", "onEnableWidgetDialogCancel()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        CommunalViewModel communalViewModel = (CommunalViewModel) this.receiver;
        communalViewModel._isEnableWidgetDialogShowing.updateState(null, Boolean.FALSE);
        return Unit.INSTANCE;
    }
}
