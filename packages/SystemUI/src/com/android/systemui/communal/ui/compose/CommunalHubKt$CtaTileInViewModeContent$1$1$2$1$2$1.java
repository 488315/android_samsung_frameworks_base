package com.android.systemui.communal.ui.compose;

import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* loaded from: classes2.dex */
final /* synthetic */ class CommunalHubKt$CtaTileInViewModeContent$1$1$2$1$2$1 extends AdaptedFunctionReference implements Function0 {
    public CommunalHubKt$CtaTileInViewModeContent$1$1$2$1$2$1(Object obj) {
        super(0, obj, BaseCommunalViewModel.class, "onOpenWidgetEditor", "onOpenWidgetEditor(Z)V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((BaseCommunalViewModel) this.receiver).onOpenWidgetEditor(false);
        return Unit.INSTANCE;
    }
}
