package com.android.systemui.settings.multisim.ui.viewmodel;

import com.android.systemui.settings.multisim.ui.view.MultiSIMPreferredSlotView;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class MultiSIMViewModelImpl$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ SlotsView f$0;

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        int i = MultiSIMViewModelImpl.$r8$clinit;
        MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow = ((MultiSIMPreferredSlotView) this.f$0).mPopupWindow;
        return Boolean.valueOf(prefferedSlotPopupWindow != null && prefferedSlotPopupWindow.isShowing());
    }
}
