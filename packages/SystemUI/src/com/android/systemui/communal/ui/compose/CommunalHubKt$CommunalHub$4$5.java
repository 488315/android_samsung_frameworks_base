package com.android.systemui.communal.ui.compose;

import android.content.Intent;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class CommunalHubKt$CommunalHub$4$5 extends FunctionReferenceImpl implements Function0 {
    public CommunalHubKt$CommunalHub$4$5(Object obj) {
        super(0, obj, CommunalViewModel.class, "onEnableWidgetDialogConfirm", "onEnableWidgetDialogConfirm()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        CommunalViewModel communalViewModel = (CommunalViewModel) this.receiver;
        CommunalInteractor communalInteractor = communalViewModel.communalInteractor;
        communalInteractor.getClass();
        communalInteractor.activityStarter.postStartActivityDismissingKeyguard(new Intent("android.settings.COMMUNAL_SETTINGS").addFlags(335544320), 0);
        communalViewModel._isEnableWidgetDialogShowing.updateState(null, Boolean.FALSE);
        return Unit.INSTANCE;
    }
}
