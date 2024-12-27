package com.android.systemui.communal.ui.compose;

import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class CommunalHubKt$CtaTileInViewModeContent$1$1$1$2 extends AdaptedFunctionReference implements Function0 {
    public CommunalHubKt$CtaTileInViewModeContent$1$1$1$2(Object obj) {
        super(0, obj, BaseCommunalViewModel.class, "onOpenWidgetEditor", "onOpenWidgetEditor(Ljava/lang/String;Z)V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        BaseCommunalViewModel.onOpenWidgetEditor$default((BaseCommunalViewModel) this.receiver, null, false, 3);
        return Unit.INSTANCE;
    }
}
