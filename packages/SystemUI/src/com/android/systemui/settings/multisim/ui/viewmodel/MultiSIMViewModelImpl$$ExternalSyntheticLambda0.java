package com.android.systemui.settings.multisim.ui.viewmodel;

import com.android.systemui.settings.multisim.ui.view.MultiSIMPreferredSlotView;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class MultiSIMViewModelImpl$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ MultiSIMViewModelImpl f$0;

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow;
        SlotsView slotsView = this.f$0.bindedView;
        return Boolean.valueOf((slotsView == null || (prefferedSlotPopupWindow = ((MultiSIMPreferredSlotView) slotsView).mPopupWindow) == null || !prefferedSlotPopupWindow.isShowing()) ? false : true);
    }
}
