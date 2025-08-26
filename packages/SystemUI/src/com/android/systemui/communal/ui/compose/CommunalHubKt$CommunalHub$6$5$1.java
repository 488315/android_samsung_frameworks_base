package com.android.systemui.communal.ui.compose;

import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import com.android.systemui.statusbar.phone.ManagedProfileControllerImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class CommunalHubKt$CommunalHub$6$5$1 extends FunctionReferenceImpl implements Function0 {
    public CommunalHubKt$CommunalHub$6$5$1(Object obj) {
        super(0, obj, CommunalViewModel.class, "onEnableWorkProfileDialogConfirm", "onEnableWorkProfileDialogConfirm()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        CommunalViewModel communalViewModel = (CommunalViewModel) this.receiver;
        ((ManagedProfileControllerImpl) communalViewModel.communalInteractor.managedProfileController).setWorkModeEnabled(true);
        communalViewModel._isEnableWorkProfileDialogShowing.updateState(null, Boolean.FALSE);
        return Unit.INSTANCE;
    }
}
