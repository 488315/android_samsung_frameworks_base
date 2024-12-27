package com.android.systemui.communal.ui.compose;

import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

final /* synthetic */ class CommunalHubKt$CommunalHub$4$8 extends FunctionReferenceImpl implements Function0 {
    public CommunalHubKt$CommunalHub$4$8(Object obj) {
        super(0, obj, CommunalViewModel.class, "onEnableWorkProfileDialogCancel", "onEnableWorkProfileDialogCancel()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        CommunalViewModel communalViewModel = (CommunalViewModel) this.receiver;
        communalViewModel._isEnableWorkProfileDialogShowing.updateState(null, Boolean.FALSE);
        return Unit.INSTANCE;
    }
}
