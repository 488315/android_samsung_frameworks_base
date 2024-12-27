package com.android.systemui.communal.ui.compose;

import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final /* synthetic */ class CommunalHubKt$DisabledWidgetPlaceholder$1 extends FunctionReferenceImpl implements Function0 {
    public CommunalHubKt$DisabledWidgetPlaceholder$1(Object obj) {
        super(0, obj, BaseCommunalViewModel.class, "onOpenEnableWidgetDialog", "onOpenEnableWidgetDialog()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((BaseCommunalViewModel) this.receiver).onOpenEnableWidgetDialog();
        return Unit.INSTANCE;
    }
}
