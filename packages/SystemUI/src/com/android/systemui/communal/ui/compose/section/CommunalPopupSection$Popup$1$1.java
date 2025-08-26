package com.android.systemui.communal.ui.compose.section;

import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class CommunalPopupSection$Popup$1$1 extends FunctionReferenceImpl implements Function0 {
    public CommunalPopupSection$Popup$1$1(Object obj) {
        super(0, obj, CommunalViewModel.class, "onHidePopup", "onHidePopup()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((CommunalViewModel) this.receiver).setCurrentPopupType(null);
        return Unit.INSTANCE;
    }
}
