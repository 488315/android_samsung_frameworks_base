package com.android.systemui.communal.ui.compose;

import com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class CommunalHubKt$CommunalHub$6$7$1 extends FunctionReferenceImpl implements Function0 {
    public CommunalHubKt$CommunalHub$6$7$1(Object obj) {
        super(0, obj, CommunalEditModeViewModel.class, "onDisclaimerDismissed", "onDisclaimerDismissed()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((CommunalEditModeViewModel) this.receiver).communalInteractor.setDisclaimerDismissed();
        return Unit.INSTANCE;
    }
}
